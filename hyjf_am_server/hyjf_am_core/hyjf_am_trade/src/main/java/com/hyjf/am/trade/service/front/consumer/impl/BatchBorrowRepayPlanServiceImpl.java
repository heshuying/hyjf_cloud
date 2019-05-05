package com.hyjf.am.trade.service.front.consumer.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.consumer.BatchBorrowRepayPlanService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 自动扣款(还款服务)
 *
 * @author dxj
 * 
 */
@Service
public class BatchBorrowRepayPlanServiceImpl extends BaseServiceImpl implements BatchBorrowRepayPlanService {

	/** 等待 */
	private static final String TYPE_WAIT = "wait";
	/** 完成 */
	private static final String TYPE_YES = "yes";
	/** 部分完成 */
	private static final String TYPE_WAIT_YES = "wait_yes";
    
	@Autowired
	private CommonProducer commonProducer;

    @Autowired
    SystemConfig systemConfig;

	@Value("${aems.notify.url}")
    private String aemsNotifyUrl;


	@Override
	public boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception {

		String borrowNid = apicron.getBorrowNid();
        BorrowApicron newApicron = new BorrowApicron();
		if (CustomConstants.BANK_BATCH_STATUS_SENDED == status || CustomConstants.BANK_BATCH_STATUS_SEND_FAIL == status) {
			BeanUtils.copyProperties(apicron, newApicron);
			newApicron.setUpdateTime(new Date());
		} else if (CustomConstants.BANK_BATCH_STATUS_FAIL == status) {
			newApicron.setFailTimes(apicron.getFailTimes());
			newApicron.setData(apicron.getData());
		}
		newApicron.setId(apicron.getId());
        newApicron.setStatus(status);
		boolean apicronFlag = this.borrowApicronMapper.updateByPrimaryKeySelective(newApicron) > 0 ? true : false;
		if (!apicronFlag) {
			throw new Exception("批次还款任务表(ht_borrow_apicron)更新失败！[借款编号：" + borrowNid + "]");
		}
		Borrow borrow = this.getBorrowByNid(borrowNid);
		Borrow newBorrow = new Borrow();
		newBorrow.setId(borrow.getId());
		newBorrow.setRepayStatus(status);
		boolean borrowFlag = this.borrowMapper.updateByPrimaryKeySelective(newBorrow) > 0 ? true : false;
		if (!borrowFlag) {
			throw new Exception("标的表(ht_borrow)更新失败！[借款编号：" + borrowNid + "]");
		}
		try {
			if (CustomConstants.BANK_BATCH_STATUS_SENDED == status || CustomConstants.BANK_BATCH_STATUS_SEND_FAIL == status) {
				logger.info("【智投还款】借款编号：{}，插入还款任务日志表数据。", borrowNid);
				BorrowApicronLog log = new BorrowApicronLog();
				BeanUtils.copyProperties(apicron, log);
				log.setId(null);
				log.setStatus(status);
				log.setUpdateTime(null);
				borrowApicronLogMapper.insert(log);
			} else {
				this.updateBorrowApicronLog(apicron, status);
			}
		} catch (Exception e) {
			logger.error("同步还款任务日志表发生异常！", e);
		}
		return borrowFlag;
	}

	@Override
	public BankCallBean batchQuery(BorrowApicron apicron) {
		// 获取共同参数
		String batchNo = apicron.getBatchNo();// 还款请求批次号
		String batchTxDate = String.valueOf(apicron.getTxDate());// 还款请求日期
		int userId = apicron.getUserId();
		String channel = BankCallConstant.CHANNEL_PC;

		String logOrderId = GetOrderIdUtils.getOrderId2(userId);
		String orderDate = GetOrderIdUtils.getOrderDate();
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);
		// 调用还款接口
		BankCallBean repayBean = new BankCallBean();
		repayBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		repayBean.setTxCode(BankCallConstant.TXCODE_BATCH_QUERY);// 消息类型(批量还款)
		repayBean.setTxDate(txDate);
		repayBean.setTxTime(txTime);
		repayBean.setSeqNo(seqNo);
		repayBean.setChannel(channel);
		repayBean.setBatchNo(batchNo);
		repayBean.setBatchTxDate(batchTxDate);
		repayBean.setLogUserId(String.valueOf(apicron.getUserId()));
		repayBean.setLogOrderId(logOrderId);
		repayBean.setLogOrderDate(orderDate);
		repayBean.setLogRemark("智投还款批次状态查询");
		repayBean.setLogClient(0);
		BankCallBean queryResult = BankCallUtils.callApiBg(repayBean);
		if (queryResult != null && StringUtils.isNotBlank(queryResult.getRetCode())) {
			String retCode = queryResult.getRetCode();
			logger.info("【智投还款】借款编号：{}，批次状态查询处理状态：{}", apicron.getBorrowNid(), retCode);
			if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
				return queryResult;
			}
			logger.info("【智投还款】借款编号：{}，批次状态查询失败描述：{}", apicron.getBorrowNid(), queryResult.getRetMsg());
		}
		return null;
	}
	
	
	/**
	 * 根据主键从主库查询ht_borrow_apicron 表
	 * 这里不加select是想直接从主库查询
	 * @param id
	 * @return
	 */
	@Override
	public BorrowApicron selApiCronByPrimaryKey(int id) {
		return borrowApicronMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateRecover(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception {
		int periodNow = apicron.getPeriodNow();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();
		String borrowNid = borrow.getBorrowNid();
		int tenderUserId = borrowRecover.getUserId();
		String tenderOrderId = borrowRecover.getNid();
		// 是否分期(true:分期, false:不分期)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		if (isMonth) {
			// 取得放款记录分期信息
			BorrowRecoverPlan borrowRecoverPlan = getBorrowRecoverPlan(borrowNid, periodNow, tenderUserId, tenderOrderId);
			if (Validator.isNull(borrowRecoverPlan)) {
				throw new RuntimeException("放款记录分期数据不存在！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]，[期数：" + periodNow + "]");
			}else {
				BorrowRecoverPlan newBorrowRecoverPlan = new BorrowRecoverPlan();
				newBorrowRecoverPlan.setId(borrowRecoverPlan.getId());
				newBorrowRecoverPlan.setRecoverStatus(2);
				boolean flag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(newBorrowRecoverPlan) > 0 ? true : false;
				if (!flag) {
					throw new Exception("放款记录分期表(ht_borrow_recover_plan)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]，[期数：" + periodNow + "]");
				}
			}
		} else {
            BorrowRecover newBorrowRecover = new BorrowRecover();
            newBorrowRecover.setId(borrowRecover.getId());
            newBorrowRecover.setRecoverStatus(2);
			boolean flag = this.borrowRecoverMapper.updateByPrimaryKeySelective(newBorrowRecover) > 0 ? true : false;
			if (!flag) {
				throw new Exception("放款记录总表(ht_borrow_recover)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
			}
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map requestRepay(BorrowApicron apicron) {

		int repayUserId = apicron.getUserId();// 还款用户userId
		String borrowNid = apicron.getBorrowNid();// 借款编号
		Integer periodNow = apicron.getPeriodNow();// 当前期数
		int isApicronRepayOrgFlag = Validator.isNull(apicron.getIsRepayOrgFlag()) ? 0 : apicron.getIsRepayOrgFlag();// 是否是担保机构还款
		// 取得借款人账户信息
		Account repayAccount = this.getAccount(repayUserId);
		if (repayAccount == null) {
			throw new RuntimeException("借款人账户不存在！[用户ID：" + repayUserId + "]，[借款编号：" + borrowNid + "]");
		}
		// 借款人在汇付的账户信息
		Account repayBankAccount = this.getAccount(repayUserId);
		if (repayBankAccount == null) {
			throw new RuntimeException("借款人未开户！[借款人ID：" + repayUserId + "]，[借款编号：" + borrowNid + "]");
		}
		String repayAccountId = repayBankAccount.getAccountId();// 借款人相应的银行账号
		// 取得借款详情
		Borrow borrow = this.getBorrowByNid(borrowNid);
		if (borrow == null) {
			throw new RuntimeException("借款信息不存在！[用户ID：" + repayUserId + "]，[借款编号：" + borrowNid + "]");
		}
		String borrowStyle = borrow.getBorrowStyle();
		// 标的是否可用担保机构还款
		int isRepayOrgFlag = 0;
		if (isApicronRepayOrgFlag == 1) {
			BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
			isRepayOrgFlag = Validator.isNull(borrowInfo.getIsRepayOrgFlag()) ? 0 : borrowInfo.getIsRepayOrgFlag();
		}
		// 是否分期(true:分期, false:不分期)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// 取得放款记录列表
		List<BorrowRecover> listRecovers = this.getBorrowRecoverList(borrowNid,apicron);
		// 是否有待还款记录
		if (listRecovers == null || listRecovers.size() == 0) {
			throw new RuntimeException("待还款记录不存在！[借款编号：" + borrowNid + "]，[期数：" + periodNow + "]");
		}
		// 非分期还款请求列表
		List<BorrowRecover> recoverList = new ArrayList<BorrowRecover>();
		// 分期还款请求列表
		List<BorrowRecoverPlan> recoverPlanList = new ArrayList<BorrowRecoverPlan>();
		// 债转还款请求列表
		List<HjhDebtCreditRepay> creditRepayList = new ArrayList<HjhDebtCreditRepay>();
		try {
			/** 循环出借详情列表 */
			for (int i = 0; i < listRecovers.size(); i++) {
				// 获取还款信息
				BorrowRecover borrowRecover = listRecovers.get(i);
				// 出借订单号
				String tenderOrderId = borrowRecover.getNid();
				// 分期的还款信息
				BorrowRecoverPlan borrowRecoverPlan = null;
				BigDecimal recoverPlanAccount = BigDecimal.ZERO;
				BigDecimal sumCreditAccount = BigDecimal.ZERO;
				if (isMonth) {
					// 取得分期还款计划表
					borrowRecoverPlan = this.getBorrowRecoverPlan(borrowNid, periodNow, borrowRecover.getUserId(), tenderOrderId);
					if (Validator.isNull(borrowRecoverPlan)) {
						throw new RuntimeException("放款记录分期数据不存在！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]，[期数：" + periodNow + "]");
					}
				 	recoverPlanAccount = borrowRecoverPlan.getRecoverAccount();
					// 保存还款订单号
					if (StringUtils.isBlank(borrowRecoverPlan.getRepayOrderId())) {
						// 设置还款订单号
						borrowRecoverPlan.setRepayOrderId(GetOrderIdUtils.getOrderId2(borrowRecoverPlan.getUserId()));
						// 设置还款时间
						borrowRecoverPlan.setRepayOrderDate(GetOrderIdUtils.getOrderDate());
						// 更新还款信息
						boolean recoverPlanFlag = this.updateBorrowRecoverPlan(borrowRecoverPlan);
						if (!recoverPlanFlag) {
							throw new RuntimeException("放款记录分期表(ht_borrow_recover_plan)更新还款订单号失败！[出借订单号：" + tenderOrderId + "]");
						}
						// 设置还款订单号
						borrowRecover.setRepayOrdid(borrowRecoverPlan.getRepayOrderId());
						// 设置还款时间
						borrowRecover.setRepayOrddate(borrowRecoverPlan.getRepayOrderDate());
						// 更新还款信息
						boolean flag = this.updateBorrowRecover(borrowRecover);
						if (!flag) {
							throw new RuntimeException("放款记录总表(ht_borrow_recover)更新还款订单号失败！[出借订单号：" + tenderOrderId + "]");
						}
					}
				} else { // endday: 按天计息, end:按月计息
					// 保存还款订单号
					if (StringUtils.isBlank(borrowRecover.getRepayOrdid())) {
						// 设置还款订单号
						borrowRecover.setRepayOrdid(GetOrderIdUtils.getOrderId2(borrowRecover.getUserId()));
						// 设置还款时间
						borrowRecover.setRepayOrddate(GetOrderIdUtils.getOrderDate());
						// 更新还款信息
						boolean flag = this.updateBorrowRecover(borrowRecover);
						if (!flag) {
							throw new RuntimeException("放款记录总表(ht_borrow_recover)更新还款订单号失败！[出借订单号：" + tenderOrderId + "]");
						}
					}
				}
				if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
					// 查询此笔债转承接的还款情况
					List<HjhDebtCreditRepay> subCreditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, periodNow,1);
					if (subCreditRepayList != null && subCreditRepayList.size() > 0) {
						for (int j = 0; j < subCreditRepayList.size(); j++) {
							HjhDebtCreditRepay creditRepay = subCreditRepayList.get(j);
							if (StringUtils.isBlank(creditRepay.getCreditRepayOrderId())) {
								// 设置还款订单号
								creditRepay.setCreditRepayOrderId(GetOrderIdUtils.getOrderId2(creditRepay.getUserId()));
								// 设置还款时间
								creditRepay.setCreditRepayOrderDate(GetOrderIdUtils.getOrderDate());
								// 更新还款信息
								boolean flag = this.hjhDebtCreditRepayMapper.updateByPrimaryKeySelective(creditRepay) > 0 ? true : false;
								if (!flag) {
									throw new RuntimeException("债转还款表(ht_hjh_debt_credit_repay)更新还款订单号失败！[承接订单号：" + creditRepay.getAssignOrderId() + "]，[出借订单号：" + tenderOrderId + "]");
								}
							}
							sumCreditAccount = sumCreditAccount.add(creditRepay.getRepayAccount());
							if (isMonth && Validator.isNotNull(borrowRecoverPlan)) {
								borrowRecoverPlan.setRecoverInterestWait(borrowRecoverPlan.getRecoverInterestWait().subtract(creditRepay.getRepayInterest()));
								borrowRecoverPlan.setRecoverCapitalWait(borrowRecoverPlan.getRecoverCapitalWait().subtract(creditRepay.getRepayCapital()));
								borrowRecoverPlan.setRecoverAccountWait(borrowRecoverPlan.getRecoverAccountWait().subtract(creditRepay.getRepayAccount()));
								borrowRecoverPlan.setRecoverFee(borrowRecoverPlan.getRecoverFee().subtract(creditRepay.getManageFee()));
								borrowRecoverPlan.setChargeInterest(borrowRecoverPlan.getChargeInterest().subtract(creditRepay.getRepayAdvanceInterest()));
								borrowRecoverPlan.setDelayInterest(borrowRecoverPlan.getDelayInterest().subtract(creditRepay.getRepayDelayInterest()));
								borrowRecoverPlan.setLateInterest(borrowRecoverPlan.getLateInterest().subtract(creditRepay.getRepayLateInterest()));
							} else {
								borrowRecover.setRecoverInterestWait(borrowRecover.getRecoverInterestWait().subtract(creditRepay.getRepayInterest()));
								borrowRecover.setRecoverCapitalWait(borrowRecover.getRecoverCapitalWait().subtract(creditRepay.getRepayCapital()));
								borrowRecover.setRecoverAccountWait(borrowRecover.getRecoverAccountWait().subtract(creditRepay.getRepayAccount()));
								borrowRecover.setRecoverFee(borrowRecover.getRecoverFee().subtract(creditRepay.getManageFee()));
								borrowRecover.setChargeInterest(borrowRecover.getChargeInterest().subtract(creditRepay.getRepayAdvanceInterest()));
								borrowRecover.setDelayInterest(borrowRecover.getDelayInterest().subtract(creditRepay.getRepayDelayInterest()));
								borrowRecover.setLateInterest(borrowRecover.getLateInterest().subtract(creditRepay.getRepayLateInterest()));
							}
							creditRepayList.add(creditRepay);
						}
						// 判断是否完全承接 add by cwyang 
						boolean overFlag = isOverUndertake(borrowRecover,recoverPlanAccount,sumCreditAccount,isMonth);
						//如果是完全承接，不添加相应的出让人还款记录
						if (overFlag) {
							if (isMonth) {
								recoverPlanList.add(borrowRecoverPlan);
							} else {
								recoverList.add(borrowRecover);
							}
						}
					}
				} else {
					// 未债转部分添加到还款结果列表
					if (isMonth) {
						recoverPlanList.add(borrowRecoverPlan);
					} else {
						recoverList.add(borrowRecover);
					}
				}
			}
			BankCallBean repayResult = null;
			boolean delFlag = false;
			Map resultMap = null;
			// 调用相应的批次还款接口
			// 如果是垫付机构还款
			if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
				// 调用相应的担保机构还款接口
				logger.info("【智投还款请求】借款编号：{}，批次代偿接口请求开始！", borrowNid);
				if (isMonth) {
					resultMap = this.requestOrgRepay(borrow, repayAccountId, apicron, null, recoverPlanList, creditRepayList);
				}else{
					resultMap = this.requestOrgRepay(borrow, repayAccountId, apicron, recoverList, null, creditRepayList);
				}
			} else {
				// 调用相应的非担保机构还款接口
				logger.info("【智投还款请求】借款编号：{}，批次还款接口请求开始！", borrowNid);
				if (isMonth) {
					resultMap = this.requestRepay(borrow, repayAccountId, apicron, null, recoverPlanList, creditRepayList);
				}else{
					resultMap = this.requestRepay(borrow, repayAccountId, apicron, recoverList, null, creditRepayList);
				}
			}
			repayResult = (BankCallBean) resultMap.get("result");
			delFlag = (boolean) resultMap.get("delFlag");
			Map map = new HashMap<>();
			map.put("delFlag", delFlag);
			if(Validator.isNull(repayResult) || StringUtils.isBlank(repayResult.getReceived()) 
					|| !BankCallConstant.RECEIVED_SUCCESS.equals(repayResult.getReceived())){
				map.put("result", false);
			} else {
				map.put("result", true);
			}
			return map;
		} catch (Exception e) {
			logger.error("【智投还款请求】还款请求时发生系统异常！", e);
			throw new RuntimeException();
			
		}
	}

	/**
	 * 判断是否完全承接  true:未完全承接
	 * @param borrowRecover
	 * @param recoverPlanCapital
	 * @param isMonth
	 * @return
	 */
	private boolean isOverUndertake(BorrowRecover borrowRecover,BigDecimal recoverPlanCapital, BigDecimal creditSumCapital, boolean isMonth) {
		if (isMonth) {//分期标的
			//分期待还金额 大于 承接待还金额
			if (recoverPlanCapital.compareTo(creditSumCapital) > 0) {
				return true;
			}
		}else{
			if (borrowRecover.getRecoverCapital().compareTo(borrowRecover.getCreditAmount()) > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查询还款请求明细，并且进行更新操作
	 * 
	 * @param apicron
	 * @return
	 */
	@Override
	public int reapyBatchDetailsUpdate(BorrowApicron apicron) {

		String borrowNid = apicron.getBorrowNid();// 借款编号
		Borrow borrow = this.getBorrowByNid(borrowNid);
		BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
		// 调用批次查询接口查询批次返回结果
		List<BankCallBean> resultBeans = this.queryBatchDetails(apicron);
		// 还款成功后后续操作
		try {
			// 给标的的出借人更新明细，加钱，结束债权
			boolean repayFlag = this.debtRepays(apicron, borrow, borrowInfo, resultBeans);
			if (repayFlag) {
				try {
					// 更新标的借款人余额，交易明细，标的表批次表的状态
					int borrowStatus = ((BatchBorrowRepayPlanService)AopContext.currentProxy()).updateBorrowStatus(apicron, borrow, borrowInfo);
					return borrowStatus;
				} catch (Exception e) {
					logger.error("【智投还款】更新借款人数据时发生系统异常！", e);
					// 更新借款人失败时将“还款处理中”状态改为“还款部分失败”状态 update by wgx 2019/02/26
					try {
						BorrowApicron newBorrowApicron = new BorrowApicron();
						newBorrowApicron.setId(apicron.getId());
						newBorrowApicron.setStatus(CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
						this.borrowApicronMapper.updateByPrimaryKeySelective(newBorrowApicron);
						try {
							this.updateBorrowApicronLog(apicron, CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
						} catch (Exception e1) {
							logger.error("同步还款任务日志表发生异常！", e1);
						}
					} catch (Exception e2) {
						logger.error("【智投还款】借款编号：{}，批次还款任务“还款处理中”状态修改失败！", borrowNid, e);
					}
				}
			}
		} catch (Exception e1) {
			logger.error("【智投还款】更新出借人数据时发生系统异常！", e1);
		}
		return CustomConstants.BANK_BATCH_STATUS_FAIL;

	}

	/**
	 * 更新相应的债转还款记录
	 * 
	 * @param apicron
	 * 
	 * @param creditRepay
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateCreditRepay(BorrowApicron apicron, HjhDebtCreditRepay creditRepay) throws Exception {
		// 更新债转还款表
		HjhDebtCreditRepay newCreditRepay = new HjhDebtCreditRepay();
		newCreditRepay.setId(creditRepay.getId());
		newCreditRepay.setRepayStatus(2);// 状态 0未还款1已还款2还款失败
		int flag = this.hjhDebtCreditRepayMapper.updateByPrimaryKeySelective(newCreditRepay);
		if (flag > 0) {
			throw new Exception("债转还款表(ht_hjh_debt_credit_repay)更新失败！[承接订单号：" + creditRepay.getAssignOrderId() + "]，[期数：" + creditRepay.getRepayPeriod() + "]");
		}
		return true;
	}

	/***
	 * 查询相应的债转还款记录
	 * 
	 * @param borrowNid
	 * @param tenderOrderId
	 * @param periodNow
	 * @param flag
	 * @return
	 */
	private List<HjhDebtCreditRepay> selectCreditRepay(String borrowNid, String tenderOrderId, Integer periodNow, Integer flag) {

		HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample(); 
		//		DebtCreditRepayExample example = new DebtCreditRepayExample();
		HjhDebtCreditRepayExample.Criteria crt = example.createCriteria();
		crt.andBorrowNidEqualTo(borrowNid);
		crt.andInvestOrderIdEqualTo(tenderOrderId);
		crt.andRepayPeriodEqualTo(periodNow);
		if (flag == 1) {//还款申请查询
			crt.andRepayStatusEqualTo(0);
			crt.andDelFlagEqualTo(0);
			crt.andRepayAccountGreaterThanOrEqualTo(BigDecimal.ZERO);
		}else{
			// crt.andRepayStatusNotEqualTo(1);// 更新还款时，已还款状态在外面筛选 update by wgx 2019/02/25
			crt.andDelFlagEqualTo(0);
			crt.andRepayAccountGreaterThanOrEqualTo(BigDecimal.ZERO);
		}
		List<HjhDebtCreditRepay> creditRepayList = this.hjhDebtCreditRepayMapper.selectByExample(example);
		return creditRepayList;
	}

	/**
	 * 
	 * @param borrowRecoverPlan
	 * @return
	 */
	private boolean updateBorrowRecoverPlan(BorrowRecoverPlan borrowRecoverPlan) {
		boolean flag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
		return flag;
	}

	private BorrowRecoverPlan getBorrowRecoverPlan(String borrowNid, Integer periodNow, Integer userId, String tenderOrderId) {

		BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
		BorrowRecoverPlanExample.Criteria crt = example.createCriteria();
		crt.andBorrowNidEqualTo(borrowNid);
		crt.andRecoverPeriodEqualTo(periodNow);
		crt.andUserIdEqualTo(userId);
		crt.andNidEqualTo(tenderOrderId);
		List<BorrowRecoverPlan> borrowRecoverPlans = this.borrowRecoverPlanMapper.selectByExample(example);
		if (borrowRecoverPlans != null && borrowRecoverPlans.size() == 1) {
			return borrowRecoverPlans.get(0);
		}
		return null;
	}

	/**
	 *  非担保机构还款
	 * 
	 * @param borrow
	 * @param borrowAccountId
	 * @param apicron
	 * @param borrowRecoverList
	 * @param borrowRecoverPlanList
	 * @param creditRepayList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map requestRepay(Borrow borrow, String borrowAccountId, BorrowApicron apicron, List<BorrowRecover> borrowRecoverList, List<BorrowRecoverPlan> borrowRecoverPlanList,
			List<HjhDebtCreditRepay> creditRepayList) throws Exception {
		String borrowNid = borrow.getBorrowNid();
		boolean delFlag = false;
		Map map = new HashMap<>();
		int txCounts = 0;// 交易笔数
		BigDecimal txAmountSum = BigDecimal.ZERO;// 交易总金额
		BigDecimal repayAmountSum = BigDecimal.ZERO;// 交易总金额
		BigDecimal serviceFeeSum = BigDecimal.ZERO;// 交易总服务费
		JSONArray subPacksJson = new JSONArray();// 拼接结果
		// 债转还款结果
		if (creditRepayList != null && creditRepayList.size() > 0) {
			// 遍历债权还款记录
			for (int i = 0; i < creditRepayList.size(); i++) {
				// 还款信息
				HjhDebtCreditRepay creditRepay = creditRepayList.get(i);
				int assignUserId = creditRepay.getUserId();// 承接用户userId
				String creditRepayOrderId = creditRepay.getCreditRepayOrderId();// 债转还款订单号
				BigDecimal txAmount = creditRepay.getRepayCapital();// 交易金额
				BigDecimal intAmount = creditRepay.getRepayInterest().add(creditRepay.getRepayAdvanceInterest()).add(creditRepay.getRepayDelayInterest()).add(creditRepay.getRepayLateInterest());// 交易利息
				BigDecimal serviceFee = creditRepay.getManageFee();// 还款管理费
				logger.info("【智投还款请求】借款编号：{}，任务表变更金额。增加金额：{}+{}+{}，原始金额：{}，债转订单号：{}", borrowNid, txAmount, intAmount, serviceFee, repayAmountSum, creditRepay.getCreditNid());
				repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
				txAmountSum = txAmountSum.add(txAmount);// 交易总金额
				serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
				String authCode = creditRepay.getAuthCode();// 出借授权码
				// 承接用户的开户信息
				Account bankOpenAccount = this.getAccount(assignUserId);
				// 判断承接用户开户信息
				if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
					JSONObject subJson = getSubJson(borrowAccountId, borrowNid, creditRepayOrderId, txAmount, intAmount, serviceFee, authCode, bankOpenAccount);
					subPacksJson.add(subJson);
				} else {
					throw new Exception("债转请求还款时，未查询到承接人的银行开户信息！[用户ID：" + assignUserId + "]");
				}
			}
			txCounts = txCounts + creditRepayList.size();
		}
		// 判断标的的放款信息
		if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
			// 遍历标的放款信息
			for (int i = 0; i < borrowRecoverList.size(); i++) {
				// 获取不分期的放款信息
				BorrowRecover borrowRecover = borrowRecoverList.get(i);
				int tenderUserId = borrowRecover.getUserId();// 出借用户userId
				String recoverRepayOrderId = borrowRecover.getRepayOrdid();// 还款订单号
				BigDecimal txAmount = borrowRecover.getRecoverCapitalWait();// 交易金额
				BigDecimal intAmount = borrowRecover.getRecoverInterestWait().add(borrowRecover.getChargeInterest()).add(borrowRecover.getDelayInterest()).add(borrowRecover.getLateInterest());// 交易利息
				BigDecimal serviceFee = borrowRecover.getRecoverFee();// 还款管理费
				logger.info("【智投还款请求】借款编号：{}，任务表变更金额。增加金额：{}+{}+{}，原始金额：{}，出借订单号：{}", borrowNid, txAmount, intAmount, serviceFee, repayAmountSum, borrowRecover.getNid());
				txAmountSum = txAmountSum.add(txAmount);// 交易总金额
				repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
				serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
				String authCode = borrowRecover.getAuthCode();// 出借授权码
				// 出借用户的开户信息
				Account bankOpenAccount = this.getAccount(tenderUserId);
				// 判断出借用户开户信息
				if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
					JSONObject subJson = getSubJson(borrowAccountId, borrowNid, recoverRepayOrderId, txAmount, intAmount, serviceFee, authCode, bankOpenAccount);
					subPacksJson.add(subJson);
				} else {
					throw new Exception("非分期原始投资请求还款时，未查询到出借人的银行开户信息！[用户ID：" + tenderUserId + "]");
				}
			}
			txCounts = txCounts + borrowRecoverList.size();
		} else {
			if (borrowRecoverPlanList != null && borrowRecoverPlanList.size() > 0) {
				for (int i = 0; i < borrowRecoverPlanList.size(); i++) {
					// 获取分期的放款信息
					BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlanList.get(i);
					int tenderUserId = borrowRecoverPlan.getUserId();// 出借用户userId
					String recoverPlanRepayOrderId = borrowRecoverPlan.getRepayOrderId();
					BigDecimal txAmount = borrowRecoverPlan.getRecoverCapitalWait();// 交易金额
					BigDecimal intAmount = borrowRecoverPlan.getRecoverInterestWait().add(borrowRecoverPlan.getChargeInterest()).add(borrowRecoverPlan.getDelayInterest())
							.add(borrowRecoverPlan.getLateInterest());// 交易利息
					BigDecimal serviceFee = borrowRecoverPlan.getRecoverFee();// 还款管理费
					logger.info("【智投还款请求】借款编号：{}，任务表变更金额。增加金额：{}+{}+{}，原始金额：{}，出借订单号：{}", borrowNid, txAmount, intAmount, serviceFee, repayAmountSum, borrowRecoverPlan.getNid());
					repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
					txAmountSum = txAmountSum.add(txAmount);// 交易总金额
					serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
					String authCode = borrowRecoverPlan.getAuthCode();// 出借授权码
					// 出借用户的开户信息
					Account bankOpenAccount = this.getAccount(tenderUserId);
					// 判断出借用户开户信息
					if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
						JSONObject subJson = getSubJson(borrowAccountId, borrowNid, recoverPlanRepayOrderId, txAmount, intAmount, serviceFee, authCode, bankOpenAccount);
						subPacksJson.add(subJson);
					} else {
						throw new Exception("分期原始投资请求还款时，未查询到出借人的银行开户信息！[用户ID：" + tenderUserId + "]");
					}
				}
				txCounts = txCounts + borrowRecoverPlanList.size();
			}
		}
		// 拼接相应的还款参数
		if (apicron.getFailTimes() == 0) {
			apicron.setBatchAmount(repayAmountSum);
			apicron.setBatchCounts(txCounts);
			apicron.setBatchServiceFee(serviceFeeSum);
			apicron.setSucAmount(BigDecimal.ZERO);
			apicron.setSucCounts(0);
			apicron.setFailAmount(repayAmountSum);
			apicron.setFailCounts(txCounts);
		}
		apicron.setServiceFee(serviceFeeSum);
		apicron.setTxAmount(txAmountSum);
		apicron.setTxCounts(txCounts);
		apicron.setData(" ");
 		Map resultMap = this.requestRepay(apicron, subPacksJson);
		BankCallBean repayResult = (BankCallBean) resultMap.get("result");
		delFlag = (boolean) resultMap.get("delFlag");
		try {
			if (Validator.isNotNull(repayResult)) {
				String received = repayResult.getReceived();
				if (StringUtils.isNotBlank(received)) {
					if (BankCallConstant.RECEIVED_SUCCESS.equals(received)) {
						map.put("result", repayResult);
						map.put("delFlag", delFlag);
						return map;
					} else {
						throw new Exception("批次还款失败！[返回结果：" + received + "]，[用户ID：" + apicron.getUserId() + "]，[借款编号：" + borrowNid + "]");
					}
				} else {
					throw new Exception("批次还款返回结果为空！[用户ID：" + apicron.getUserId() + "]，[借款编号：" + borrowNid + "]");
				}
			} else {
				throw new Exception("批次还款请求失败！[用户ID：" + apicron.getUserId() + "]，[借款编号：" + borrowNid + "]");
			}
		} catch (Exception e) {
			logger.error("【智投还款请求】还款请求发生系统异常！", e);
		}
		map.put("result", repayResult);
		map.put("delFlag", delFlag);
		return map;
	}

	/**
	 * 批次还款请求数组
	 */
	private JSONObject getSubJson(String borrowAccountId, String borrowNid, String creditRepayOrderId, BigDecimal txAmount, BigDecimal intAmount, BigDecimal serviceFee, String authCode, Account bankOpenAccount) {
		JSONObject subJson = new JSONObject();
		String accountId = bankOpenAccount.getAccountId();// 银行账户
		subJson.put(BankCallConstant.PARAM_ACCOUNTID, borrowAccountId);// 融资人账号
		subJson.put(BankCallConstant.PARAM_ORDERID, creditRepayOrderId);// 订单号
		subJson.put(BankCallConstant.PARAM_TXAMOUNT, txAmount.toString());// 交易金额
		subJson.put(BankCallConstant.PARAM_INTAMOUNT, intAmount.toString());// 交易金额
		subJson.put(BankCallConstant.PARAM_TXFEEOUT, serviceFee.toString());
		subJson.put(BankCallConstant.PARAM_FORACCOUNTID, accountId);
		subJson.put(BankCallConstant.PARAM_PRODUCTID, borrowNid);
		subJson.put(BankCallConstant.PARAM_AUTHCODE, authCode);
		return subJson;
	}

	/**
	 * 批次还款（借款人）
	 *
	 * @param apicron
	 * @return
	 */
	private Map requestRepay(BorrowApicron apicron, JSONArray subPacksJson) {
		Map map = new HashMap<>();
		int userId = apicron.getUserId();// 还款用户userId
		String borrowNid = apicron.getBorrowNid();// 借款编号
		// 还款子参数
		String subPacks = subPacksJson.toJSONString();
		// 获取共同参数
		String notifyUrl = systemConfig.getRepayVerifyUrl();
		String retNotifyURL = systemConfig.getRepayResultUrl();
		String channel = BankCallConstant.CHANNEL_PC;
		// 只请求一次银行接口，请求返回异常任务表更新状态为还款请求失败(7)，由batch进行异常处理 update by wgx 2019/03/15
		try {
			String logOrderId = GetOrderIdUtils.getOrderId2(apicron.getUserId());
			String orderDate = GetOrderIdUtils.getOrderDate();
			String batchNo = GetOrderIdUtils.getBatchNo();// 获取还款批次号
			String txDate = GetOrderIdUtils.getTxDate();
			String txTime = GetOrderIdUtils.getTxTime();
			String seqNo = GetOrderIdUtils.getSeqNo(6);
			try{
				apicron.setBatchNo(batchNo);
				apicron.setTxDate(Integer.parseInt(txDate));
				apicron.setTxTime(Integer.parseInt(txTime));
				apicron.setSeqNo(Integer.parseInt(seqNo));
				apicron.setBankSeqNo(txDate + txTime + seqNo);
				// 更新任务API状态为进行中
				boolean apicronFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_SENDING);
				if (!apicronFlag) {
					throw new Exception("更新请求银行信息失败！[用户ID：" + userId + "]，[借款编号：" + borrowNid + "]");
				}
			} catch (Exception e) {
				logger.error("【智投批次还款】还款请求时发生系统异常！", e);
				map.put("result", null);// 请求失败
				map.put("delFlag", false);// 未请求银行&更新任务表失败，删除防重redis
				return map;
			}
			// 调用还款接口
			BankCallBean repayBean = new BankCallBean();
			repayBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
			repayBean.setTxCode(BankCallConstant.TXCODE_BATCH_REPAY);// 消息类型(批量还款)
			repayBean.setTxDate(txDate);
			repayBean.setTxTime(txTime);
			repayBean.setSeqNo(seqNo);
			repayBean.setChannel(channel);
			repayBean.setBatchNo(apicron.getBatchNo());
			repayBean.setTxAmount(String.valueOf(apicron.getTxAmount()));
			repayBean.setTxCounts(String.valueOf(apicron.getTxCounts()));
			repayBean.setNotifyURL(notifyUrl);
			repayBean.setRetNotifyURL(retNotifyURL);
			repayBean.setSubPacks(subPacks);
			repayBean.setLogUserId(String.valueOf(userId));
			repayBean.setLogOrderId(logOrderId);
			repayBean.setLogOrderDate(orderDate);
			repayBean.setLogRemark("批次还款请求");
			repayBean.setLogClient(0);
			logger.info("【智投批次还款】借款编号：{}，还款请求开始！", borrowNid);
			BankCallBean repayResult = BankCallUtils.callApiBg(repayBean);
			if (Validator.isNotNull(repayResult)) {
				String received = StringUtils.isNotBlank(repayResult.getReceived()) ? repayResult.getReceived() : "";
				if (BankCallConstant.RECEIVED_SUCCESS.equals(received)) {
					try {
						// 更新任务API状态
						boolean apicronResultFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_SENDED);
						if (apicronResultFlag) {
							map.put("result", repayResult);
							map.put("delFlag", false);// 请求银行返回成功&更新任务表成功，删除防重redis
							return map;
						}
					} catch (Exception e) {
						logger.error("【智投批次还款】还款请求成功后,更新任务状态(还款请求成功)发生异常！", e);
					}
					map.put("result", repayResult);
					map.put("delFlag", true);// 请求银行返回成功&更新任务表失败，不删除防重redis
					return map;
				}
				map.put("result", null);// 请求失败
				map.put("delFlag", false);// 请求银行返回失败，删除防重redis
			}
		} catch (Exception e) {
			logger.error("【智投批次还款】还款请求银行时发生系统异常！", e);
		}
		// update by wgx 2019/03/18
		map.put("result", null);// 请求失败
		map.put("delFlag", true);// 请求银行返回空｜请求银行返回异常，不删除防重redis
		return map;
	}


	/**
	 * 自动还款
	 *
	 * @throws Exception
	 */
	private boolean debtRepays(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, List<BankCallBean> resultBeans) throws Exception {

		/** 基本变量 */
		String borrowNid = apicron.getBorrowNid();// 借款编号
		int periodNow = apicron.getPeriodNow();// 当前还款期数
		String borrowStyle = borrow.getBorrowStyle();
		int borrowPeriod = borrow.getBorrowPeriod();// 借款期数
		// 是否分期(true:分期, false:不分期)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// 剩余还款期数
		Integer periodNext = borrowPeriod - periodNow;
		boolean apicronFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_DOING);
		if (!apicronFlag) {
			throw new Exception("批次还款任务表(ht_borrow_apicron)更新失败！[银行唯一订单号：" + apicron.getBankSeqNo() + "]");
		}
		if (Validator.isNotNull(resultBeans) && resultBeans.size() > 0) {
			Map<String, JSONObject> repayResults = new HashMap<String, JSONObject>();
			for (int i = 0; i < resultBeans.size(); i++) {
				BankCallBean resultBean = resultBeans.get(i);
				String subPacks = resultBean.getSubPacks();
				if (StringUtils.isNotBlank(subPacks)) {
					JSONArray repayDetails = JSONObject.parseArray(subPacks);
					for (int j = 0; j < repayDetails.size(); j++) {
						JSONObject repayDetail = repayDetails.getJSONObject(j);
						String repayOrderId = repayDetail.getString(BankCallConstant.PARAM_ORDERID);
						repayResults.put(repayOrderId, repayDetail);
					}
				}
			}
			// 取得出借详情列表
			List<BorrowRecover> borrowRecoverList = this.getBorrowRecoverList(borrowNid,apicron);
			if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
				logger.info("【智投还款】借款编号：{}，开始更新出借明细，共{}条。", borrowNid, borrowRecoverList.size());
				// 遍历进行还款
				for (int i = 0; i < borrowRecoverList.size(); i++) {
					logger.info("【智投还款】借款编号：{}，开始更新第{}条出借明细。", borrowNid, i + 1);
					// 出借明细
					BorrowRecover borrowRecover = borrowRecoverList.get(i);
					String tenderOrderId = borrowRecover.getNid();// 出借订单号
					BigDecimal recoverPlanAccount = BigDecimal.ZERO;
					BigDecimal sumCreditAccount = BigDecimal.ZERO;
					if (isMonth) {
						BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
						example.createCriteria().andNidEqualTo(tenderOrderId).andRecoverPeriodEqualTo(periodNow);
						List<BorrowRecoverPlan> planList = this.borrowRecoverPlanMapper.selectByExample(example);
						BorrowRecoverPlan planInfo = planList.get(0);
						recoverPlanAccount = planInfo.getRecoverAccount();
					}
					String repayOrderId = borrowRecover.getRepayOrdid();// 还款订单号
					BigDecimal creditAmount = borrowRecover.getCreditAmount();// 债转金额
					if(1 == apicron.getIsAllrepay() || apicron.getLastPeriod() > 1){//一次性结清/可能是多期还款
						repayOrderId = getAllRepayOrdid(borrowNid,periodNow,borrowRecover.getNid());
					}
					JSONObject repayDetail = repayResults.get(repayOrderId);
					// 如果发生了债转，处理相应的债转还款
					if (creditAmount.compareTo(BigDecimal.ZERO) > 0) {
						logger.info("【智投还款】借款编号：{}，发生债转。", borrowNid);
						List<HjhDebtCreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, periodNow,0);
						if (creditRepayList != null && creditRepayList.size() > 0) {
							logger.info("【智投还款】借款编号：{}，原始出借发生债转，出借订单号：{}，债转还款共{}条。", borrowNid, tenderOrderId, creditRepayList.size());
							boolean creditRepayAllFlag = true;
							boolean creditEndAllFlag = true;
							BigDecimal sumCreditCapital = BigDecimal.ZERO;
							for (int j = 0; j < creditRepayList.size(); j++) {
								HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
								sumCreditAccount = sumCreditAccount.add(creditRepay.getRepayAccount());
								sumCreditCapital = sumCreditCapital.add(creditRepay.getRepayCapital());//累积承接本金
								String assignOrderId = creditRepay.getAssignOrderId();
								int assignUserId = creditRepay.getUserId();
								String creditRepayOrderId = creditRepay.getCreditRepayOrderId();
								JSONObject assignRepayDetail = repayResults.get(creditRepayOrderId);
								if(creditRepay.getRepayStatus() == 1){
									logger.info("【智投还款】已还款的债转不进行处理！债转还款订单号：{}", creditRepayOrderId);
								    continue;
                                }
								if (Validator.isNull(assignRepayDetail)) {
									logger.error("【智投还款】银行端未查询到相应的还款明细！出借订单号：{}，债转还款订单号:{}", tenderOrderId, creditRepayOrderId);
									creditRepayAllFlag = false;// 有债转处理失败，不进行后续还款更新
									continue;
								}
								try {
									String txState = assignRepayDetail.getString(BankCallConstant.PARAM_TXSTATE);// 交易状态
									// 如果处理状态为成功
									if (txState.equals(BankCallConstant.BATCH_TXSTATE_TYPE_SUCCESS)) {
										// 调用债转还款
										boolean creditRepayFlag = ((BatchBorrowRepayPlanService)AopContext.currentProxy()).updateCreditRepay(apicron, borrow, borrowInfo, borrowRecover, creditRepay, assignRepayDetail);
										if (creditRepayFlag) {
											// 结束债转已在updateCreditRepay中处理 update by wgx in 2019/01/10
										} else {
                                            creditEndAllFlag = false;
											//throw new Exception("更新相应的债转还款失败!" + "[承接用户：" + assignUserId + "]" + "[承接订单号：" + assignOrderId + "]" + "[还款期数：" + periodNow + "]");
										}
									} else {
										creditRepayAllFlag = false;
										// 更新债转还款表
										boolean borrowTenderFlag = ((BatchBorrowRepayPlanService)AopContext.currentProxy()).updateCreditRepay(apicron, creditRepay);
										if (!borrowTenderFlag) {
											throw new Exception("债转还款表(ht_hjh_debt_credit_repay)更新失败！[承接订单号：" + assignOrderId + "]，[还款期数：" + periodNow + "]");
										}
									}
								} catch (Exception e) {
									logger.error("【智投还款】更新承接人的还款数据发生异常！", e);
                                    creditRepayAllFlag = false;
									continue;
								}
							}
							if (creditRepayAllFlag) {
								logger.info("【智投还款】开始判断是否为完全承接！[出借订单号：" + tenderOrderId + "],原始金额：" + recoverPlanAccount + "，债转金额：" + sumCreditAccount);
								// 判断是否完全承接 add by cwyang
								boolean overFlag = isOverUndertake(borrowRecover,recoverPlanAccount,sumCreditAccount,isMonth);
								if (overFlag) {
									logger.info("【智投还款】非完全承接！[出借订单号：" + tenderOrderId + "],原始金额：" + recoverPlanAccount + "，债转金额：" + sumCreditAccount);
									// 如果不是全部债转
									if (Validator.isNull(repayDetail)) {
										logger.error("【智投还款】银行端未查询到相应的还款明细！出借订单号：{}", tenderOrderId);
										continue;
									}
									String txState = repayDetail.getString(BankCallConstant.PARAM_TXSTATE);// 交易状态
									// 如果处理状态为成功
									if (txState.equals(BankCallConstant.BATCH_TXSTATE_TYPE_SUCCESS)) {
										try {
											boolean tenderRepayFlag = ((BatchBorrowRepayPlanService)AopContext.currentProxy()).updateTenderRepay(apicron, borrow, borrowInfo, borrowRecover, repayDetail, true, sumCreditCapital,creditEndAllFlag);
											// 结束债转已在updateTenderRepay中处理 update by wgx in 2019/01/10
										} catch (Exception e) {
											logger.error("【智投还款】更新出借人非完全承接的还款数据发生异常！", e);
											continue;
										}
									} else {
										try {
											// 更新放款记录表
											boolean recoverFlag = ((BatchBorrowRepayPlanService)AopContext.currentProxy()).updateRecover(apicron, borrow, borrowRecover);
											if (!recoverFlag) {
												throw new Exception("还款失败！[出借订单号：" + tenderOrderId + "]");
											}
										} catch (Exception e) {
											logger.error("【智投还款】更新放款记录表的还款状态(还款失败)发生异常！", e);
											continue;
										}
									}
								} else {
									logger.info("【智投还款】完全承接！出借订单号：{}，原始金额：{}，债转金额：{}", tenderOrderId,recoverPlanAccount, sumCreditAccount);
									try {
										boolean tenderRepayFlag = ((BatchBorrowRepayPlanService)AopContext.currentProxy()).updateTenderRepayStatus(apicron, borrow, borrowRecover);
										if (!tenderRepayFlag) {
											throw new Exception("更新相应的还款信息失败！[出借订单号：" + tenderOrderId + "]");
										}
									} catch (Exception e) {
										logger.error("【智投还款】更新出借人完全承接的还款数据发生异常！", e);
										continue;
									}
								}
							} else {
								continue;
							}
						}
					} else {
						if (Validator.isNull(repayDetail)) {
							logger.error("【智投还款】银行端未查询到相应的还款明细！出借订单号：{}", tenderOrderId);
							continue;
						}
						String txState = repayDetail.getString(BankCallConstant.PARAM_TXSTATE);// 交易状态
						// 如果处理状态为成功
						if (txState.equals(BankCallConstant.BATCH_TXSTATE_TYPE_SUCCESS)) {
							try {
								((BatchBorrowRepayPlanService)AopContext.currentProxy()).updateTenderRepay(apicron, borrow, borrowInfo, borrowRecover, repayDetail, false, null,true);
								// 结束债转已在updateTenderRepay中处理 update by wgx in 2019/01/10
							} catch (Exception e) {
								logger.error("【智投还款】更新出借人的还款数据发生系统异常！", e);
								continue;
							}
						} else {
							try {
								// 更新放款记录表
								boolean recoverFlag = ((BatchBorrowRepayPlanService)AopContext.currentProxy()).updateRecover(apicron, borrow, borrowRecover);
								if (!recoverFlag) {
									throw new Exception("还款失败！[出借订单号：" + tenderOrderId + "]");
								}
							} catch (Exception e) {
								logger.error("【智投还款】更新放款记录表的还款状态发生系统异常！", e);
								continue;
							}
						}
					}
				}
			} else {
				logger.info("【智投还款】借款编号：{}，未查询到待处理的还款记录。", borrowNid);
				return true;
			}
		} else {
			throw new Exception("银行交易明细查询失败！[借款编号：" + borrowNid + "]");
		}
		return true;
	}

	/**
	 * 更新相应的原始出借为债权结束
	 * 
	 * @param borrowRecover
	 * @param isMonth
	 * @return
	 */
	private boolean updateDebtStatus(BorrowRecover borrowRecover, boolean isMonth) {
        BorrowRecover newBorrowRecover = new BorrowRecover();
        newBorrowRecover.setId(borrowRecover.getId());
        newBorrowRecover.setDebtStatus(1);
		return this.borrowRecoverMapper.updateByPrimaryKeySelective(newBorrowRecover) > 0 ? true : false;
	}

	/**
	 * 获得一次性结清的当期还款的还款订单号
	 * @param borrowNid
	 * @param periodNow
	 * @param nid
	 * @return
	 */
	private String getAllRepayOrdid(String borrowNid, int periodNow, String nid) {

		BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
		example.createCriteria().andBorrowNidEqualTo(borrowNid).andRecoverPeriodEqualTo(periodNow).andNidEqualTo(nid);
		List<BorrowRecoverPlan> recoverPlans = this.borrowRecoverPlanMapper.selectByExample(example);
		String repayOrderId = recoverPlans.get(0).getRepayOrderId();
		return repayOrderId;
	}

	/**
	 * 更新相应的债转还款为债权结束
	 * 
	 * @param creditRepay
	 * @return
	 */
	private boolean updateDebtStatus(HjhDebtCreditRepay creditRepay) {

		int userId = creditRepay.getUserId();
		String assignNid = creditRepay.getAssignOrderId();
		HjhDebtCreditRepay repay = new HjhDebtCreditRepay();
		repay.setDebtStatus(1);
		HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
		example.createCriteria().andUserIdEqualTo(userId).andAssignOrderIdEqualTo(assignNid);
		boolean flag = this.hjhDebtCreditRepayMapper.updateByExampleSelective(repay, example ) > 0 ? true : false;
		return flag;
	}

	/**
	 * 结束相应的债权
	 * 
	 * @param repayDetail
	 * @param orgOrderId
	 * @param orgOrderId
	 * @return
	 */
	private boolean requestDebtEnd(Integer userId, JSONObject repayDetail,String orgOrderId, Borrow borrow) {

		String accountId = repayDetail.getString(BankCallConstant.PARAM_FORACCOUNTID);// 出借人账户
		String forAccountId = repayDetail.getString(BankCallConstant.PARAM_ACCOUNTID);// 借款人账户
		String productId = repayDetail.getString(BankCallConstant.PARAM_PRODUCTID);// 借款编号
		String authCode = repayDetail.getString(BankCallConstant.PARAM_AUTHCODE);// 出借授权码
		try {
			String logOrderId = GetOrderIdUtils.getOrderId2(userId);
			// 垫付机构还款时,结束无法结束债权
			Integer borrowUserId = borrow.getUserId();
			logger.info("【智投还款】借款编号：{}，结束债权。借款人：{}-{}，出借人：{}-{}，授权码：{}，原始订单号：{}。",
					productId, borrowUserId, forAccountId, userId, accountId, authCode, orgOrderId);
			// 根据用户ID查询借款人用户电子账户号
			Account borrowUserAccount = this.getAccount(borrowUserId);
			if(borrowUserAccount==null){
				logger.error("【智投还款】结束债权获取借款人电子账户号失败！用户ID:{}", borrowUserId);
				return false;
			}
			// 借款人电子账户号
			forAccountId = borrowUserAccount.getAccountId();
			BankCreditEnd record = new BankCreditEnd();
			record.setUserId(borrow.getUserId());
			record.setTenderUserId(userId);
			record.setAccountId(forAccountId);
			record.setTenderAccountId(accountId);
			record.setOrderId(logOrderId);
			record.setBorrowNid(productId);
			record.setAuthCode(authCode);
			record.setCreditEndType(1); // 结束债权类型（1:还款，2:散标债转，3:智投债转）'
			record.setStatus(0);
			record.setOrgOrderId(orgOrderId);
			record.setCreateUser(userId);
			record.setUpdateUser(userId);
			this.bankCreditEndMapper.insertSelective(record);
			return true;
		} catch (Exception e) {
			logger.error("【智投还款】结束债权时发生系统异常！", e);
		}
		return false;
	}

	@Override
	public boolean updateTenderRepayStatus(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception {

		logger.info("【智投还款/出借人】借款编号：{}，开始更新出借人相关的还款数据。", apicron.getBorrowNid());
		/** 还款信息 */
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 借款编号
		String borrowNid = apicron.getBorrowNid();
		// 还款期数
		int periodNow = apicron.getPeriodNow();
		String repayBatchNo = apicron.getBatchNo();

		/** 标的基本数据 */

		// 还款期数
		int borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();

		/** 出借人数据 */
		// 出借订单号
		String tenderOrderId = borrowRecover.getNid();
		// 出借人用户ID
		Integer tenderUserId = borrowRecover.getUserId();
		// 还款时间
		int recoverTime = borrowRecover.getRecoverTime();
		// 还款订单号
		String repayOrderId = borrowRecover.getRepayOrdid();

		/** 基本变量 */
		// 剩余还款期数
		int periodNext = borrowPeriod - periodNow;
		// 分期还款计划表
		BorrowRecoverPlan borrowRecoverPlan = null;
		// 是否分期(true:分期, false:不分期)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		if (isMonth) { // principal: 等额本金，month:等额本息，endmonth:先息后本
			// 取得放款分期信息
			borrowRecoverPlan = getBorrowRecoverPlan(borrowNid, periodNow, tenderUserId, tenderOrderId);
			if (Validator.isNull(borrowRecoverPlan)) {
				throw new Exception("放款记录分期数据不存在！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]，[期数：" + periodNow + "]");
			}else {
				// 还款订单号
				repayOrderId = borrowRecoverPlan.getRepayOrderId();
				// 应还款时间
				recoverTime = borrowRecoverPlan.getRecoverTime();
			}
		} else { // endday: 按天计息, end:按月计息
			// 还款订单号
			repayOrderId = borrowRecover.getRepayOrdid();
			// 还款时间
			recoverTime = borrowRecover.getRecoverTime();
		}
		boolean isAllRepay = apicron.getIsAllrepay() == null ? false : apicron.getIsAllrepay() == 1;// 是否是一次性还款
		Integer lastPeriod = apicron.getLastPeriod() == null ? 0 :apicron.getLastPeriod();// 同时提交还款的最后一期
		isAllRepay = isAllRepay || lastPeriod == borrowPeriod;// 多期还款的最后一期是标的的最后一期，是一次性还款
		// 首先判断当前期是否是一次性还款中唯一一期需要更新的 update by wgx 2019/02/28
		boolean isLastUpdate = isLastAllRepay(borrowNid, periodNow, tenderUserId, tenderOrderId, isAllRepay);
		// 更新放款记录
		BorrowRecover newBorrowRecover = new BorrowRecover();
		newBorrowRecover.setId(borrowRecover.getId());
		if (borrowRecoverPlan != null && periodNext > 0 && !isLastUpdate) {
			newBorrowRecover.setRecoverStatus(0); // 未还款
			if(!isAllRepay && lastPeriod > periodNow) {// 非一次性还款的多期还款，下期还款直接更新为提交的最后一期的下一期
				logger.info("【智投还款/出借人】借款编号：{}，开始更新多期还款完全承接第{}期的数据，提交的最后期数：{}。", borrowNid, periodNow, lastPeriod);
				BorrowRecoverPlan borrowRecoverPlanNext = getBorrowRecoverPlan(borrowNid, lastPeriod + 1, tenderUserId, tenderOrderId);
				newBorrowRecover.setRecoverTime(null == borrowRecoverPlanNext ? null : borrowRecoverPlanNext.getRecoverTime()); // 计算下期时间
			} else {
				// 取得放款记录分期表下一期的放款信息
				BorrowRecoverPlan borrowRecoverPlanNext = getBorrowRecoverPlan(borrowNid, periodNow + 1, tenderUserId, tenderOrderId);
				newBorrowRecover.setRecoverTime(null == borrowRecoverPlanNext ? null : borrowRecoverPlanNext.getRecoverTime()); // 计算下期时间
			}
			newBorrowRecover.setRecoverType(TYPE_WAIT);
		} else if(borrowRecoverPlan != null && periodNext == 0 && isAllRepay && !isLastUpdate){ // 一次性还款最后一期且还有其他期未还完
			newBorrowRecover.setRecoverStatus(0); // 未还款
			newBorrowRecover.setRecoverYestime(nowTime); // 实际还款时间
			newBorrowRecover.setRecoverTime(recoverTime);
			newBorrowRecover.setRecoverType(TYPE_WAIT);
		} else {
			newBorrowRecover.setRecoverStatus(1); // 已还款
			newBorrowRecover.setRecoverYestime(nowTime); // 实际还款时间
			newBorrowRecover.setRecoverTime(recoverTime);
			newBorrowRecover.setRecoverType(TYPE_YES);
		}
		// 分期时
		if (borrowRecoverPlan != null) {
			newBorrowRecover.setRecoverPeriod(periodNext);
        } else if (borrowRecover.getAdvanceStatus() == 30) {
            newBorrowRecover.setAdvanceStatus(3);// 不分期逾期标的，已还款后重置
        }
		newBorrowRecover.setRepayBatchNo(repayBatchNo);
		boolean borrowRecoverFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(newBorrowRecover) > 0 ? true : false;
		if (!borrowRecoverFlag) {
			throw new Exception("放款记录总表(ht_borrow_recover)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
		}
		if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
			// 查询相应的债权转让
			List<HjhDebtCredit> borrowCredits = this.getBorrowCredit(tenderOrderId, periodNow - 1);
			boolean isLastUpdate2 = isLastAllRepay(borrowNid, periodNow, tenderUserId, tenderOrderId, isAllRepay);
			if(isLastUpdate2 && !isLastUpdate){
				logger.error("【智投还款/出借人】借款编号：{}，一次性还款重新更新放款记录总表(ht_borrow_recover)状态为成功", apicron.getBorrowNid());
				newBorrowRecover.setRecoverStatus(1); // 已还款
				newBorrowRecover.setRecoverYestime(nowTime); // 实际还款时间
				newBorrowRecover.setRecoverTime(recoverTime);
				newBorrowRecover.setRecoverType(TYPE_YES);
				this.borrowRecoverMapper.updateByPrimaryKeySelective(newBorrowRecover);
			}
			if (borrowCredits != null && borrowCredits.size() > 0) {
				for (int i = 0; i < borrowCredits.size(); i++) {
					// 获取相应的债转记录
					HjhDebtCredit borrowCredit = borrowCredits.get(i);
					// 债转编号
					String creditNid = borrowCredit.getCreditNid();
					// 债转状态
					if (borrowRecoverPlan != null && !isLastUpdate2 && (isAllRepay || periodNext > 0)) {
						borrowCredit.setRepayStatus(1);
					} else {
						borrowCredit.setRepayStatus(2);
						// 债转最后还款时间
						borrowCredit.setCreditRepayYesTime(isMonth ? 0 : nowTime);
					}
					// 债转还款期
					borrowCredit.setRepayPeriod(periodNow);
					// 债转最近还款时间
					borrowCredit.setCreditRepayLastTime(nowTime);
					// 更新债转标的表
					boolean borrowCreditFlag = this.hjhDebtCreditMapper.updateByPrimaryKeySelective(borrowCredit) > 0 ? true : false;
					if (!borrowCreditFlag) {
						throw new Exception("债转标的表(ht_hjh_debt_credit)更新失败！[债转编号：" + creditNid + "]，[出借订单号：" + tenderOrderId + "]");
					}
				}
			}
		}
		// 分期时
		if (Validator.isNotNull(borrowRecoverPlan)) {
			// 更新放款记录分期信息
			borrowRecoverPlan.setRepayBatchNo(repayBatchNo);
			borrowRecoverPlan.setRecoverStatus(1);
			borrowRecoverPlan.setRecoverYestime(String.valueOf(nowTime));
			borrowRecoverPlan.setRecoverType(TYPE_YES);
			if(borrowRecoverPlan.getAdvanceStatus() == 30){
				borrowRecoverPlan.setAdvanceStatus(3);// 逾期标的，已还款后重置
			}
			boolean borrowRecoverPlanFlag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
			if (!borrowRecoverPlanFlag) {
				throw new Exception("放款记录分期表(ht_borrow_recover_plan)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
			}
			// 更新总的还款计划
			BorrowRepayPlan borrowRepayPlan = getBorrowRepayPlan(borrowNid, periodNow);
			if (Validator.isNotNull(borrowRepayPlan)) {
				borrowRepayPlan.setRepayType(TYPE_WAIT_YES);
				borrowRepayPlan.setRepayActionTime(String.valueOf(nowTime));
				borrowRepayPlan.setRepayStatus(1);
				borrowRepayPlan.setRepayYestime(nowTime);
				boolean borrowRepayPlanFlag = this.borrowRepayPlanMapper.updateByPrimaryKeySelective(borrowRepayPlan) > 0 ? true : false;
				if (!borrowRepayPlanFlag) {
					throw new Exception("还款记录分期表(ht_borrow_repay_plan)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
				}
			} else {
				throw new Exception("分期还款记录不存在！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
			}

		}
		logger.info("【智投还款/出借人】借款编号：{}，更新出借人的还款数据结束。还款订单号：{}", apicron.getBorrowNid(), repayOrderId);
		return true;
	}

	private List<HjhDebtCredit> getBorrowCredit(String tenderOrderId, Integer periodNow) {
		
		HjhDebtCreditExample example = new HjhDebtCreditExample();
		example.createCriteria().andInvestOrderIdEqualTo(tenderOrderId).andRepayPeriodEqualTo(periodNow);
		List<HjhDebtCredit> borrowCredits = this.hjhDebtCreditMapper.selectByExample(example);
		return borrowCredits;
	}

	/**
	 * 原始出借还款
	 * @param apicron
	 * @param borrow
	 * @param borrowInfo
	 * @param borrowRecover
	 * @param repayDetail
	 * @param isCredit
	 * @param sumCreditCapital
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateTenderRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover, JSONObject repayDetail, boolean isCredit, BigDecimal sumCreditCapital, boolean creditEndAllFlag) throws Exception {

		logger.info("【智投还款/出借人】借款编号：{}，开始更新出借人相关的还款数据。", apicron.getBorrowNid());
		/** 还款信息 */
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 借款编号
		String borrowNid = apicron.getBorrowNid();
		// 还款人ID(借款人或垫付机构)
		Integer repayUserId = apicron.getUserId();
		// 还款人用户名
		String repayUserName = apicron.getUserName();
		// 是否是担保机构还款
		int isApicronRepayOrgFlag = Validator.isNull(apicron.getIsRepayOrgFlag()) ? 0 : apicron.getIsRepayOrgFlag();
		// 还款期数
		int periodNow = apicron.getPeriodNow();
		String repayBatchNo = apicron.getBatchNo();
		int txDate = Validator.isNotNull(apicron.getTxDate()) ? apicron.getTxDate() : 0;// 批次时间yyyyMMdd
		int txTime = Validator.isNotNull(apicron.getTxTime()) ? apicron.getTxTime() : 0;// 批次时间HHmmss
		String seqNo = Validator.isNotNull(apicron.getSeqNo()) ? String.valueOf(apicron.getSeqNo()) : null;// 流水号
		String bankSeqNo = Validator.isNotNull(apicron.getBankSeqNo()) ? String.valueOf(apicron.getBankSeqNo()) : null;// 银行唯一订单号
		String orderId = repayDetail.getString(BankCallConstant.PARAM_ORDERID);// 还款订单号
		BigDecimal txAmount = repayDetail.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT);// 操作金额
		String forAccountId = repayDetail.getString(BankCallConstant.PARAM_FORACCOUNTID);// 出借人银行账户
		/** 标的基本数据 */
		// 标的是否可以担保机构还款
		int isRepayOrgFlag = Validator.isNull(borrowInfo.getIsRepayOrgFlag()) ? 0 : borrowInfo.getIsRepayOrgFlag();
		// 还款期数
		int borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();
		/** 出借人数据 */
		// 出借订单号
		String tenderOrderId = borrowRecover.getNid();
		// 出借人用户ID
		Integer tenderUserId = borrowRecover.getUserId();
		// 还款时间
		int recoverTime = borrowRecover.getRecoverTime();
		// 还款订单号
		String repayOrderId = borrowRecover.getRepayOrdid();
		//智投加入订单号
		String accedeOrderId = borrowRecover.getAccedeOrderId();
		/** 基本变量 */
		// 剩余还款期数
		int periodNext = borrowPeriod - periodNow;
		// 出借用户开户信息
		Account tenderBankAccount = this.getAccount(tenderUserId);
		// 出借用户银行账户
		String tenderAccountId = tenderBankAccount.getAccountId();
		// 应收管理费
		BigDecimal recoverFee = BigDecimal.ZERO;
		// 已还款管理费
		BigDecimal recoverFeeYes = BigDecimal.ZERO;
		// 待还款本息
		BigDecimal recoverAccountWait = BigDecimal.ZERO;
		// 待还款本金
		BigDecimal recoverCapitalWait = BigDecimal.ZERO;
		// 待还款利息
		BigDecimal recoverInterestWait = BigDecimal.ZERO;
		// 逾期天数
		Integer lateDays = 0;
		// 逾期利息
		BigDecimal lateInterest = BigDecimal.ZERO;
		// 提前天数
		Integer chargeDays = 0;
		// 提前还款少还利息
		BigDecimal chargeInterest = BigDecimal.ZERO;
        // 提前还款罚息
        BigDecimal chargePenaltyInterest = BigDecimal.ZERO;
		// 还款本息(实际)
		BigDecimal repayAccount = BigDecimal.ZERO;
		// 还款本金(实际)
		BigDecimal repayCapital = BigDecimal.ZERO;
		// 还款利息(实际)
		BigDecimal repayInterest = BigDecimal.ZERO;
		// 管理费
		BigDecimal manageFee = BigDecimal.ZERO;
		// 放款分期明细
		BorrowRecoverPlan borrowRecoverPlan = null;
		// 是否分期(true:分期, false:不分期)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		if (isMonth) {// principal: 等额本金，month:等额本息，endmonth:先息后本
			// 取得放款分期信息
			borrowRecoverPlan = getBorrowRecoverPlan(borrowNid, periodNow, tenderUserId, tenderOrderId);
			if (Validator.isNull(borrowRecoverPlan)) {
				throw new Exception("放款记录分期表(ht_borrow_recover_plan)数据不存在！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]，[期数：" + periodNow + "]");
			}else{
				// 是否先息后本
			    // boolean isStyle = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
				// 还款订单号
				repayOrderId = borrowRecoverPlan.getRepayOrderId();
				// 应还款时间
				recoverTime = borrowRecoverPlan.getRecoverTime();
				// 应收管理费
				recoverFee = borrowRecoverPlan.getRecoverFee();
				// 已还款管理费
				recoverFeeYes = borrowRecoverPlan.getRecoverFeeYes();
				// 待还款本息
				recoverAccountWait = borrowRecoverPlan.getRecoverAccountWait();
				// 待还款本金
				recoverCapitalWait = borrowRecoverPlan.getRecoverCapitalWait();
				// 应还款利息
				recoverInterestWait = borrowRecoverPlan.getRecoverInterestWait();
				// 逾期天数
				lateDays = borrowRecoverPlan.getLateDays();
				// 逾期利息
				lateInterest = borrowRecoverPlan.getLateInterest();
				// 提前天数
				chargeDays = borrowRecoverPlan.getChargeDays();
				// 提前还款少还利息
				chargeInterest = borrowRecoverPlan.getChargeInterest().subtract(borrowRecoverPlan.getRepayChargeInterest());
				// 提前还款罚息
				chargePenaltyInterest = borrowRecoverPlan.getChargePenaltyInterest().subtract(borrowRecoverPlan.getRepayChargePenaltyInterest());
				// 实际还款本息
				repayAccount = recoverAccountWait.add(lateInterest).add(chargeInterest);
				// 实际还款本金
				repayCapital = recoverCapitalWait;
				// 实际还款利息
				repayInterest = recoverInterestWait.add(lateInterest).add(chargeInterest);
				// 还款管理费
				manageFee = recoverFee.subtract(recoverFeeYes);
			}
		} else { // endday: 按天计息, end:按月计息
			borrowRecover = selectBorrowRecoverByNid(borrowRecover.getNid());// 非完全承接需要更新已还款债转数据
			// 还款订单号
			repayOrderId = borrowRecover.getRepayOrdid();
			// 还款时间
			recoverTime = borrowRecover.getRecoverTime();
			// 管理费
			recoverFee = borrowRecover.getRecoverFee();
			// 已还款管理费
			recoverFeeYes = borrowRecover.getRecoverFeeYes();
			// 待还款本息
			recoverAccountWait = borrowRecover.getRecoverAccountWait();
			// 待还款本金
			recoverCapitalWait = borrowRecover.getRecoverCapitalWait();
			// 应还款利息
			recoverInterestWait = borrowRecover.getRecoverInterestWait();
			// 逾期天数
			lateDays = borrowRecover.getLateDays();
			// 逾期利息
			lateInterest = borrowRecover.getLateInterest();
			// 提前天数
			chargeDays = borrowRecover.getChargeDays();
			// 提前还款少还利息
			chargeInterest = borrowRecover.getChargeInterest().subtract(borrowRecover.getRepayChargeInterest());
			// 提前还款罚息
			chargePenaltyInterest = borrowRecover.getChargePenaltyInterest().subtract(borrowRecover.getRepayChargePenaltyInterest());
			// 实际还款本息
			repayAccount = recoverAccountWait.add(lateInterest).add(chargeInterest);
			// 实际还款本金
			repayCapital = recoverCapitalWait;
			// 实际还款利息
			repayInterest = recoverInterestWait.add(lateInterest).add(chargeInterest);
			// 还款管理费
			manageFee = recoverFee.subtract(recoverFeeYes);
		}
		// 判断该收支明细存在时,跳出本次循环
		if (countAccountListByNid(repayOrderId)) {
			logger.error("【智投还款/出借人】借款编号：{}，出借人收支明细已经存在。还款订单号：{}", borrowNid, repayOrderId);
			return true;
		}
		//redis
		//判断是否清算期3天内还款
		logger.info("【智投还款/出借人】借款编号：{}，开始处理订单资金。订单号：{}", borrowNid, accedeOrderId);
		//更新智投加入订单号的资金
		HjhAccedeExample accedeExample = new HjhAccedeExample();
		accedeExample.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
		List<HjhAccede> accedeList = this.hjhAccedeMapper.selectByExample(accedeExample);
		HjhAccede hjhAccede = accedeList.get(0);
		
		BigDecimal frostAccount = hjhAccede.getFrostAccount();
		BigDecimal availableInvestAccount = hjhAccede.getAvailableInvestAccount();
		logger.info("【智投还款/出借人】借款编号：{}，订单号：{}，处理前冻结金额：{}", borrowNid, accedeOrderId, frostAccount);
		logger.info("【智投还款/出借人】借款编号：{}，订单号：{}，处理前订单可用金额：{}", borrowNid, accedeOrderId, availableInvestAccount);
		HjhRepayExample repayExample = new HjhRepayExample();
		repayExample.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);

		HjhAccede paramInfo = new HjhAccede();
		paramInfo.setId(hjhAccede.getId());
		String dateStr = "";
		if(hjhAccede.getEndDate() != null){
			dateStr = GetDate.dateToString(hjhAccede.getEndDate());
		}
		logger.info("【智投还款/出借人】借款编号：{}，订单号：{}，结束时间：{}", borrowNid, accedeOrderId, dateStr);
		if (hjhAccede.getEndDate() != null && isForstTime(hjhAccede.getEndDate())) {
			logger.info("【智投还款/出借人】借款编号：{}，开始增加冻结金额。订单号：{}，原冻结金额：{}，增加金额：{}", borrowNid, accedeOrderId, frostAccount, repayAccount);
			paramInfo.setFrostAccount(repayAccount);//智投冻结金额
			List<HjhRepay> repayList = this.hjhRepayMapper.selectByExample(repayExample);
			HjhRepay hjhRepay = repayList.get(0);
			HjhRepay repayParam = new HjhRepay();
			repayParam.setId(hjhRepay.getId());
			repayParam.setRepayTotal(repayAccount);
			repayParam.setPlanRepayCapital(repayCapital);
			repayParam.setPlanRepayInterest(repayInterest);
			repayParam.setRepayAlready(repayAccount);
			this.hjhPlanCustomizeMapper.updateHjhRepayForHjhRepay(repayParam);
			logger.info("【智投还款/出借人】借款编号：{}，开始增加还款。订单号：{}，原回款金额：{}，增加金额：{}", borrowNid, accedeOrderId, hjhRepay.getRepayAlready(), repayAccount);
		}else{
			logger.info("【智投还款/出借人】借款编号：{}，开始增加可用金额。订单号：{}", borrowNid, accedeOrderId);
			paramInfo.setAvailableInvestAccount(repayAccount);//智投订单可用金额
		}
		this.hjhPlanCustomizeMapper.updateHjhAccedeForHjhProcess(paramInfo);
		// 更新账户信息(出借人)
		Account tenderAccount = new Account();
		tenderAccount.setUserId(tenderUserId);
		logger.info("【智投还款/出借人】借款编号：{}，订单号：{}，账户金额结束时间：{}", borrowNid, accedeOrderId, dateStr);
		if (hjhAccede.getEndDate() != null && isForstTime(hjhAccede.getEndDate())) {
			tenderAccount.setPlanFrost(repayAccount);//智投冻结金额
		}else{
			tenderAccount.setPlanBalance(repayAccount);//智投可用金额
		}
		tenderAccount.setBankBalanceCash(repayAccount);// 出借人银行可用余额
		boolean investAccountFlag = this.adminAccountCustomizeMapper.updateOfRepayPlanTender(tenderAccount) > 0 ? true : false;
		if (!investAccountFlag) {
			throw new Exception("账户信息表(ht_account)更新失败！[出借人ID：" + tenderUserId + "]，[出借订单号：" + tenderOrderId + "]");
		}
		// 取得账户信息(出借人)
		tenderAccount = this.getAccount(tenderUserId);
		if (Validator.isNull(tenderAccount)) {
			throw new Exception("出借人账户信息不存在！[用户ID：" + tenderUserId + "]，[出借订单号：" + tenderOrderId + "]");
		}
		// 写入收支明细
		AccountList accountList = new AccountList();
		accountList.setNid(repayOrderId); // 还款订单号
		accountList.setUserId(tenderUserId); // 出借人
		accountList.setAmount(repayAccount); // 出借总收入
		/** 银行相关 */
		accountList.setAccountId(tenderAccountId);
		accountList.setBankAwait(tenderAccount.getBankAwait());
		accountList.setBankAwaitCapital(tenderAccount.getBankAwaitCapital());
		accountList.setBankAwaitInterest(tenderAccount.getBankAwaitInterest());
		accountList.setBankBalance(tenderAccount.getBankBalance());
		accountList.setBankFrost(tenderAccount.getBankFrost());
		accountList.setBankInterestSum(tenderAccount.getBankInterestSum());
		accountList.setBankInvestSum(tenderAccount.getBankInvestSum());
		accountList.setBankTotal(tenderAccount.getBankTotal());
		accountList.setBankWaitCapital(tenderAccount.getBankWaitCapital());
		accountList.setBankWaitInterest(tenderAccount.getBankWaitInterest());
		accountList.setBankWaitRepay(tenderAccount.getBankWaitRepay());
		accountList.setPlanBalance(tenderAccount.getPlanBalance());
		accountList.setPlanFrost(tenderAccount.getPlanFrost());
		accountList.setIsShow(1);//不显示交易明细
		// 如果是机构垫付还款
		if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
			if (forAccountId.equals(tenderAccountId) && repayOrderId.equals(orderId) && txAmount.compareTo(repayAccount) == 0) {
				accountList.setCheckStatus(1);
			} else {
				accountList.setCheckStatus(0);
			}
		} else {
			if (forAccountId.equals(tenderAccountId) && repayOrderId.equals(orderId) && txAmount.compareTo(repayCapital) == 0) {
				accountList.setCheckStatus(1);
			} else {
				accountList.setCheckStatus(0);
			}
		}
		accountList.setTradeStatus(1);// 交易状态 0:失败 1:成功
		accountList.setIsBank(1);
		accountList.setTxDate(txDate);
		accountList.setTxTime(txTime);
		accountList.setSeqNo(seqNo);
		accountList.setBankSeqNo(bankSeqNo);
		/** 非银行相关 */
		if (hjhAccede.getEndDate() != null && isForstTime(hjhAccede.getEndDate())) {
			accountList.setType(3); // 冻结
		}else{
			accountList.setType(1); // 1收入
		}
		if (hjhAccede.getEndDate() != null && isForstTime(hjhAccede.getEndDate())) {
			accountList.setTrade("hjh_repay_frost"); // 收到还款冻结
		}else{
			accountList.setTrade("hjh_repay_balance"); // 收到还款复投
		}
		accountList.setTradeCode("balance"); // 余额操作
		accountList.setTotal(tenderAccount.getTotal()); // 出借人资金总额
		accountList.setBalance(tenderAccount.getBalance()); // 出借人可用金额
		accountList.setPlanFrost(tenderAccount.getPlanFrost());// 汇添金冻结金额
		accountList.setPlanBalance(tenderAccount.getPlanBalance());// 汇添金可用金额
		accountList.setFrost(tenderAccount.getFrost()); // 出借人冻结金额
		accountList.setAwait(tenderAccount.getAwait()); // 出借人待收金额
		accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作者
		accountList.setRemark(borrowNid);
		accountList.setIp(borrow.getAddIp()); // 操作IP
		accountList.setWeb(0); // PC
		boolean investAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
		if (!investAccountListFlag) {
			throw new Exception("收支明细(ht_account_list)写入失败！[出借订单号：" + tenderOrderId + "]");
		}
		// 更新还款金额数据 update by wgx 2019/03/07
		logger.info("【智投还款/出借人】借款编号：{}，开始更新标的表还款金额。总还款增加：{}，未还款总额减少：{}，未还款利息减少：{}",
				borrowNid, repayAccount, recoverAccountWait, recoverInterestWait);
		Borrow updateBorrow = new Borrow();
		updateBorrow.setId(borrow.getId());
		updateBorrow.setRepayAccountYes(repayAccount);// 总还款增加
		updateBorrow.setRepayAccountCapitalYes(repayCapital);// 总还款本金增加
		updateBorrow.setRepayAccountInterestYes(repayInterest);// 总还款利息增加
		updateBorrow.setRepayAccountWait(recoverAccountWait);// 未还款总额减少
		updateBorrow.setRepayAccountCapitalWait(recoverCapitalWait);// 未还款本金减少
		updateBorrow.setRepayAccountInterestWait(recoverInterestWait);// 未还款利息减少
		updateBorrow.setRepayFeeNormal(manageFee);// 借款的管理费增加
		boolean borrowFlag = this.borrowCustomizeMapper.updateRepayOfBorrow(updateBorrow) > 0 ? true : false;
		if (!borrowFlag) {
			throw new Exception("标的表(ht_borrow)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
		}
		boolean isAllRepay = apicron.getIsAllrepay() == null ? false : apicron.getIsAllrepay() == 1;// 是否是一次性还款
		Integer lastPeriod = apicron.getLastPeriod() == null ? 0 :apicron.getLastPeriod();// 同时提交还款的最后一期
		isAllRepay = isAllRepay || lastPeriod == borrowPeriod;// 多期还款的最后一期是标的的最后一期，是一次性还款
		// 首先判断当前期是否是一次性还款中唯一一期需要更新的 update by wgx 2019/02/28
		boolean isLastUpdate = isLastAllRepay(borrowNid, periodNow, tenderUserId, tenderOrderId, isAllRepay);
		// 更新放款记录
		BorrowRecover newBorrowRecover = new BorrowRecover();
		newBorrowRecover.setId(borrowRecover.getId());
		logger.info("【智投还款/出借人】借款编号：{}，开始更新放款记录总表。未还款总额：{}，还款额：{}",
				borrowNid, borrowRecover.getRecoverAccountWait(), recoverAccountWait);
		if (borrowRecoverPlan != null && periodNext > 0 && !isLastUpdate) {// 分期并且不是最后一期,而且不是一次性还款最后一期需要更新的
			newBorrowRecover.setRecoverStatus(0); // 未还款
			if(!isAllRepay && lastPeriod > periodNow) {// 非一次性还款的多期还款，下期还款直接更新为提交的最后一期的下一期
				logger.info("【智投还款/出借人】借款编号：{}，开始更新多期还款第{}期的数据，提交的最后期数：{}。", borrowNid, periodNow, lastPeriod);
				BorrowRecoverPlan borrowRecoverPlanNext = getBorrowRecoverPlan(borrowNid, lastPeriod + 1, tenderUserId, tenderOrderId);
				newBorrowRecover.setRecoverTime(null == borrowRecoverPlanNext ? null : borrowRecoverPlanNext.getRecoverTime()); // 计算下期时间
			} else {
				// 取得分期还款计划表下一期的还款
				BorrowRecoverPlan borrowRecoverPlanNext = getBorrowRecoverPlan(borrowNid, periodNow + 1, tenderUserId, tenderOrderId);
				newBorrowRecover.setRecoverTime(null == borrowRecoverPlanNext ? null : borrowRecoverPlanNext.getRecoverTime()); // 计算下期时间
			}
			newBorrowRecover.setRecoverType(TYPE_WAIT);
		} else if(borrowRecoverPlan != null && periodNext == 0 && isAllRepay && !isLastUpdate){ // 一次性还款最后一期且还有其他期未还完
			newBorrowRecover.setRecoverStatus(0); // 未还款
			newBorrowRecover.setRecoverYestime(nowTime); // 实际还款时间
			newBorrowRecover.setRecoverTime(recoverTime);
			newBorrowRecover.setRecoverType(TYPE_WAIT);
		} else {
			newBorrowRecover.setRecoverStatus(1); // 已还款
			newBorrowRecover.setRecoverYestime(nowTime); // 实际还款时间
			newBorrowRecover.setRecoverTime(recoverTime);
			newBorrowRecover.setRecoverType(TYPE_YES);
		}
		// 分期时
		if (borrowRecoverPlan != null) {
			newBorrowRecover.setRecoverPeriod(periodNext);
		} else if (borrowRecover.getAdvanceStatus() == 30) {
            newBorrowRecover.setAdvanceStatus(3);// 不分期逾期标的，已还款后重置
        }
		newBorrowRecover.setWeb(2); // 写入网站收支
		// 先更新还款状态 update by wgx 2019/03/11
		boolean borrowRecoverFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(newBorrowRecover) > 0 ? true : false;
		if (!borrowRecoverFlag) {
			throw new Exception("放款记录总表(huiyingdai_borrow_recover)更新失败！" + "[出借订单号：" + tenderOrderId + "]");
		}
		newBorrowRecover.setRepayBatchNo(repayBatchNo);
		newBorrowRecover.setRecoverAccountYes(repayAccount);
		newBorrowRecover.setRecoverCapitalYes(repayCapital);
		newBorrowRecover.setRecoverInterestYes(repayInterest);
		newBorrowRecover.setRecoverAccountWait(recoverAccountWait);
		newBorrowRecover.setRecoverCapitalWait(recoverCapitalWait);
		newBorrowRecover.setRecoverInterestWait(recoverInterestWait);
		newBorrowRecover.setRepayChargeInterest(chargeInterest);
		newBorrowRecover.setRepayChargePenaltyInterest(chargePenaltyInterest);
		newBorrowRecover.setRepayLateInterest(lateInterest);
		newBorrowRecover.setRecoverFeeYes(borrowRecover.getRecoverFeeYes().add(manageFee));
		// 再更新已还待还 update by wgx 2019/03/11
		boolean borrowRecoverAccountFlag = batchBorrowRecoverCustomizeMapper.updateRepayOfBorrowRecover(newBorrowRecover) > 0 ? true : false;
		if (!borrowRecoverAccountFlag) {
			throw new Exception("放款记录总表(ht_borrow_recover)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
		}
		if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
			// 查询相应的债权转让
			List<HjhDebtCredit> borrowCredits = this.getBorrowCredit(tenderOrderId, periodNow - 1);
			if (borrowCredits != null && borrowCredits.size() > 0) {
				for (int i = 0; i < borrowCredits.size(); i++) {
					// 获取相应的债转记录
					HjhDebtCredit borrowCredit = borrowCredits.get(i);
					// 债转编号
					String creditNid = borrowCredit.getCreditNid();
					// 债转状态
					if (borrowRecoverPlan != null && !isLastUpdate && (isAllRepay || periodNext > 0)) {// 分期并且不是一次性还款最后一期需要更新的
						borrowCredit.setRepayStatus(0);
					} else {
						borrowCredit.setRepayStatus(1);
						// 债转最后还款时间
						borrowCredit.setCreditRepayYesTime(isMonth ? 0 : nowTime);
					}
					// 债转还款期
					borrowCredit.setRepayPeriod(periodNow);
					// 债转最近还款时间
					borrowCredit.setCreditRepayLastTime(nowTime);
					// 更新债转标的表
					boolean borrowCreditFlag = this.hjhDebtCreditMapper.updateByPrimaryKeySelective(borrowCredit) > 0 ? true : false;
					if (!borrowCreditFlag) {
						throw new Exception("债转标的表(ht_hjh_debt_credit)更新失败！[债转编号：" + creditNid + "]");
					}
				}
			}
		}
		// 取得还款详情
		BorrowRepay borrowRepay = getBorrowRepayAsc(borrowNid, apicron);
		// 更新还款记录总的信息
		borrowRepay.setRepayAccountAll(borrowRepay.getRepayAccountAll().add(repayAccount).add(manageFee));
		borrowRepay.setRepayAccountYes(borrowRepay.getRepayAccountYes().add(repayAccount));
		borrowRepay.setRepayCapitalYes(borrowRepay.getRepayCapitalYes().add(repayCapital));
		borrowRepay.setRepayInterestYes(borrowRepay.getRepayInterestYes().add(repayInterest));
		borrowRepay.setLateDays(lateDays);
		borrowRepay.setLateInterest(borrowRepay.getLateInterest().add(lateInterest));
		borrowRepay.setChargeDays(chargeDays);
		borrowRepay.setChargeInterest(borrowRepay.getChargeInterest().add(chargeInterest));
		borrowRepay.setChargePenaltyInterest(borrowRepay.getChargePenaltyInterest().add(chargePenaltyInterest));
		// 用户是否提前还款
		borrowRepay.setAdvanceStatus(borrowRecover.getAdvanceStatus());
		// 还款来源
		if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
			// 还款来源（1、借款人还款，2、机构垫付，3、保证金垫付）
			borrowRepay.setRepayMoneySource(2);
		} else {
			borrowRepay.setRepayMoneySource(1);
		}
		// 实际还款人（借款人、垫付机构、保证金）的用户ID
		borrowRepay.setRepayUserId(repayUserId);
		// 实际还款人（借款人、垫付机构、保证金）的用户名
		borrowRepay.setRepayUsername(repayUserName);
		boolean borrowRepayFlag = this.borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay) > 0 ? true : false;
		if (!borrowRepayFlag) {
			throw new Exception("还款记录总表(ht_borrow_repay)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
		}
        // 出借信息
        BorrowTender borrowTender = getBorrowTender(tenderOrderId);
		// 更新出借表
		borrowTender.setRecoverAccountYes(borrowTender.getRecoverAccountYes().add(repayAccount));
		borrowTender.setRecoverAccountCapitalYes(borrowTender.getRecoverAccountCapitalYes().add(repayCapital));
		borrowTender.setRecoverAccountInterestYes(borrowTender.getRecoverAccountInterestYes().add(repayInterest));
		borrowTender.setRecoverAccountWait(borrowTender.getRecoverAccountWait().subtract(recoverAccountWait));
		borrowTender.setRecoverAccountCapitalWait(borrowTender.getRecoverAccountCapitalWait().subtract(recoverCapitalWait));
		borrowTender.setRecoverAccountInterestWait(borrowTender.getRecoverAccountInterestWait().subtract(recoverInterestWait));
		boolean borrowTenderFlag = borrowTenderMapper.updateByPrimaryKeySelective(borrowTender) > 0 ? true : false;
		if (!borrowTenderFlag) {
			throw new Exception("出借表(ht_borrow_tender)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
		}
		HjhDebtDetail debtDetail = this.getDebtDetail(tenderOrderId, periodNow, tenderUserId);
		//更新债权明细表
		// 更新DebtDetail表
		debtDetail .setRepayActionTime(nowTime);
		// 已还款
		debtDetail.setRepayStatus(1);
		// 账户管理费
		debtDetail.setManageFee(manageFee);
		// 已还本金
		debtDetail.setRepayCapitalYes(repayCapital);
		// 账户服务费
		// debtDetail.setServiceFee(big);
		// 已还利息
		debtDetail.setRepayInterestYes(repayInterest);
		// 未收本金
		debtDetail.setRepayCapitalWait(BigDecimal.ZERO);
		// 未收利息
		debtDetail.setRepayInterestWait(BigDecimal.ZERO);
		// 提前还款状态
		debtDetail.setAdvanceStatus(borrowRecover.getAdvanceStatus());
		// 提前还款天数
		debtDetail.setAdvanceDays(borrowRepay.getChargeDays());
		// 提前还款利息
		debtDetail.setAdvanceInterest(chargeInterest);
		// 逾期天数
		debtDetail.setLateDays(borrowRecover.getLateDays());
		// 逾期利息
		debtDetail.setLateInterest(lateInterest);
		// 还款订单号
		debtDetail.setRepayOrderId(repayOrderId);
		// 还款日期
		debtDetail.setRepayOrderDate(borrowRecover.getRepayOrddate());
		// 债权更新时间
//		debtDetail.setUpdateTime(nowTime);
		//结束债权
		debtDetail.setDelFlag(1);
		// 到期公允价值
		debtDetail.setExpireFairValue(BigDecimal.ZERO);
		int flag = this.hjhDebtDetailMapper.updateByPrimaryKey(debtDetail);
		if (flag > 0) {
			logger.info("【智投还款/出借人】债权详情表(hjh_debt_detail)更新完成。出借订单号:{}", tenderOrderId);
		}
		// 分期时
		if (Validator.isNotNull(borrowRecoverPlan)) {
			// 更新还款计划表
			borrowRecoverPlan.setRepayBatchNo(repayBatchNo);
			borrowRecoverPlan.setRecoverStatus(1);
			borrowRecoverPlan.setRecoverYestime(String.valueOf(nowTime));
			borrowRecoverPlan.setRecoverAccountYes(borrowRecoverPlan.getRecoverAccountYes().add(repayAccount));
			borrowRecoverPlan.setRecoverCapitalYes(borrowRecoverPlan.getRecoverCapitalYes().add(repayCapital));
			borrowRecoverPlan.setRecoverInterestYes(borrowRecoverPlan.getRecoverInterestYes().add(repayInterest));
			borrowRecoverPlan.setRecoverAccountWait(borrowRecoverPlan.getRecoverAccountWait().subtract(recoverAccountWait));
			borrowRecoverPlan.setRecoverCapitalWait(borrowRecoverPlan.getRecoverCapitalWait().subtract(recoverCapitalWait));
			borrowRecoverPlan.setRecoverInterestWait(borrowRecoverPlan.getRecoverInterestWait().subtract(recoverInterestWait));
			borrowRecoverPlan.setRepayChargeInterest(borrowRecoverPlan.getRepayChargeInterest().add(chargeInterest));
			borrowRecoverPlan.setRepayChargePenaltyInterest(borrowRecoverPlan.getRepayChargePenaltyInterest().add(chargePenaltyInterest));
			borrowRecoverPlan.setRepayLateInterest(borrowRecoverPlan.getRepayLateInterest().add(lateInterest));
			borrowRecoverPlan.setRecoverFeeYes(borrowRecoverPlan.getRecoverFeeYes().add(manageFee));
			borrowRecoverPlan.setRecoverType(TYPE_YES);
			if(borrowRecoverPlan.getAdvanceStatus() == 30){
				borrowRecoverPlan.setAdvanceStatus(3);// 逾期标的，已还款后重置
			}
			boolean borrowRecoverPlanFlag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
			if (!borrowRecoverPlanFlag) {
				throw new Exception("放款记录分期表(ht_borrow_recover_plan)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
			}
			// 更新总的还款计划
			BorrowRepayPlan borrowRepayPlan = getBorrowRepayPlan(borrowNid, periodNow);
			if (Validator.isNotNull(borrowRepayPlan)) {
				borrowRepayPlan.setRepayType(TYPE_WAIT_YES);
				borrowRepayPlan.setRepayActionTime(String.valueOf(nowTime));
				borrowRepayPlan.setRepayStatus(1);
				borrowRepayPlan.setRepayYestime(nowTime);
				borrowRepayPlan.setRepayAccountAll(borrowRepayPlan.getRepayAccountAll().add(repayAccount).add(manageFee));
				borrowRepayPlan.setRepayAccountYes(borrowRepayPlan.getRepayAccountYes().add(repayAccount));
				borrowRepayPlan.setRepayCapitalYes(borrowRepayPlan.getRepayCapitalYes().add(repayCapital));
				borrowRepayPlan.setRepayInterestYes(borrowRepayPlan.getRepayInterestYes().add(repayInterest));
				borrowRepayPlan.setLateDays(lateDays);
				borrowRepayPlan.setLateInterest(borrowRepayPlan.getLateInterest().add(lateInterest));
				borrowRepayPlan.setChargeDays(chargeDays);
				borrowRepayPlan.setChargeInterest(borrowRepayPlan.getChargeInterest().add(chargeInterest));
				borrowRepayPlan.setChargePenaltyInterest(borrowRepayPlan.getChargePenaltyInterest().add(chargePenaltyInterest));
				// 用户是否提前还款
				borrowRepayPlan.setAdvanceStatus(borrowRecoverPlan.getAdvanceStatus());
				// 还款来源
				if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
					// 还款来源（1、借款人还款，2、机构垫付，3、保证金垫付）
					borrowRepayPlan.setRepayMoneySource(2);
				} else {
					borrowRepayPlan.setRepayMoneySource(1);
				}
				// 实际还款人（借款人、垫付机构、保证金）的用户ID
				borrowRepayPlan.setRepayUserId(repayUserId);
				// 实际还款人（借款人、垫付机构、保证金）的用户名
				borrowRepayPlan.setRepayUsername(repayUserName);
				boolean borrowRepayPlanFlag = this.borrowRepayPlanMapper.updateByPrimaryKeySelective(borrowRepayPlan) > 0 ? true : false;
				if (!borrowRepayPlanFlag) {
					throw new Exception("还款记录分期表(ht_borrow_repay_plan)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
				}
			} else {
				throw new Exception("分期还款记录不存在！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
			}
		}
		// 更新批次还款任务
		apicron.setSucAmount(apicron.getSucAmount().add(repayAccount.add(manageFee)));
		apicron.setSucCounts(apicron.getSucCounts() + 1);
		apicron.setFailAmount(apicron.getFailAmount().subtract(repayAccount.add(manageFee)));
		apicron.setFailCounts(apicron.getFailCounts() - 1);
		boolean apicronSuccessFlag = this.borrowApicronMapper.updateByPrimaryKeySelective(apicron) > 0 ? true : false;
		if (!apicronSuccessFlag) {
			throw new Exception("批次还款任务表(ht_borrow_apicron)更新失败！[借款编号：" + borrowNid + "]，[还款期数：" + periodNow + "]");
		}
		// 结束债权
		if (!isMonth || (isMonth && periodNext == 0 && !isAllRepay) || isLastUpdate) {
			// 结束债权
			if (creditEndAllFlag) {
				boolean debtOverFlag = this.requestDebtEnd(borrowRecover.getUserId(), repayDetail, borrowRecover.getNid(), borrow);
				if (debtOverFlag) {
					// 更新相应的债权状态为结束
					boolean debtStatusFlag = this.updateDebtStatus(borrowRecover, isMonth);
					if (!debtStatusFlag) {
						throw new Exception("更新原始出借为债权结束状态失败![出借订单号：" + tenderOrderId + "]，[还款期数：" + periodNow + "]");
					}
				} else {
					throw new Exception("结束原始出借债权失败![出借订单号：" + tenderOrderId + "]，[还款期数：" + periodNow + "]");
				}
			}
		}
		// 管理费大于0时,插入网站收支明细
		// 在出借人整笔还款数据更新完成后再发送mq update by wgx 2019/01/25
		if (manageFee.compareTo(BigDecimal.ZERO) > 0) {
			// 插入网站收支明细记录
			AccountWebListVO accountWebList = new AccountWebListVO();
			accountWebList.setOrdid(borrowTender.getNid() + "_" + periodNow);// 订单号
			accountWebList.setBorrowNid(borrowNid); // 出借编号
			accountWebList.setUserId(repayUserId); // 借款人
			accountWebList.setAmount(Double.valueOf(manageFee.toString())); // 管理费
			accountWebList.setType(CustomConstants.TYPE_IN); // 类型1收入,2支出
			accountWebList.setTrade(CustomConstants.TRADE_REPAYFEE); // 管理费
			accountWebList.setTradeType(CustomConstants.TRADE_REPAYFEE_NM); // 账户管理费
			accountWebList.setRemark(borrowNid); // 出借编号
			accountWebList.setCreateTime(nowTime);
			accountWebList.setFlag(1);
			// 网站收支明细队列
			try {
				logger.info("【智投还款/出借人】发送网站收支明细。还款人ID：{}，管理费：{}", repayUserId, manageFee);
				commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebList));
			} catch (MQException e) {
				logger.error("【智投还款/出借人】发送网站收支明细时发生系统异常！", e);
			}
		}
		// ames用户还款通知
        try {
			logger.info("-----------资产是aems推送的，borrowNid为：" + borrowNid);
			//判断是否是 aems推送的资产
			if(aemsAssetsFlag(borrowNid)){
				logger.info("-----------调用aems用户还款开始---------------------------------------------" );
				// ames用户还款通知
				aemsRepayNotify(periodNow, borrowNid, borrowRepay, recoverFee, recoverCapitalWait, recoverInterestWait, lateInterest, chargeInterest, repayAccount,repayOrderId);
				logger.info("-----------调用aems用户还款结束---------------------------------------------" );
			}
        }catch(Exception e){
            logger.error("【智投还款/出借人】ames发送用户还款通知时发生系统异常！", e);
        }
        logger.info("【智投还款/出借人】借款编号：{}，更新出借人的还款数据结束。还款订单号：{}，智投加入订单号：{}，判断复投时间：{}", apicron.getBorrowNid(), repayOrderId, accedeOrderId, dateStr);
        return true;
	}

	/**
	 *判断是否是 aems推送的资产
	 * @param borrowNid
	 * @return
	 */
	public boolean aemsAssetsFlag(String  borrowNid){
		HjhPlanAssetExample example = new HjhPlanAssetExample();
		example.createCriteria().andBorrowNidEqualTo(borrowNid);
		int count =hjhPlanAssetMapper.countByExample(example);
		if(count > 0){
			return true;
		}
		return false;
	}

	/**
	 * ames用户还款通知
	 * @param borrowPeriod
	 * @param borrowNid
	 * @param borrowRepay
	 * @param recoverFee
	 * @param recoverCapitalWait
	 * @param recoverInterestWait
	 * @param lateInterest
	 * @param chargeInterest
	 * @param repayAccount
	 */
	private void aemsRepayNotify(Integer borrowPeriod, String borrowNid, BorrowRepay borrowRepay, BigDecimal recoverFee, BigDecimal recoverCapitalWait, BigDecimal recoverInterestWait, BigDecimal lateInterest, BigDecimal chargeInterest, BigDecimal repayAccount,String repayOrderId) {
        logger.info("aems还款异步回调开始......borrowNid is {}", borrowNid);
		Integer userId = borrowRepay.getUserId();
		RUser user = getRUser(userId);
		if (user != null) {
			Map<String, String> params = new HashMap<>();
			params.put("status", "000");
			params.put("statusDesc", "还款成功");
			// 项目编号
			params.put("productId", borrowNid);
			// 用户电子账户
			params.put("accountId", getAccount(userId).getAccountId());
			// 还款成功期数
			params.put("periods", borrowPeriod.toString());
			// 应还本金
			params.put("repayAccount", recoverCapitalWait.toString());
			// 应还利息
			params.put("repayAccountInterest", recoverInterestWait.toString());
			// 应还服务费
			params.put("serviceFee", recoverFee.toString());
			// 提前还款减息
			params.put("reduceInterest", chargeInterest.toString());
			// 逾期违约金
			params.put("dueServiceFee", lateInterest.toString());
			// 还款总额
			params.put("repayAccountAll", repayAccount.toString());
			// 还款订单号
			params.put("repayOrderId", repayOrderId);
            logger.info("aems还款异步回调......params is {}", JSONObject.toJSONString(params));
            HttpDeal.postJson(aemsNotifyUrl + "/aems/api/user_repay/async_callback", JSONObject.toJSONString(params));
		}
	}

	/**
	 * 判断还款是否需要放到订单冻结金额中
	 * @param endDate
	 * @return
	 */
	private boolean isForstTime(Date endDate){
		Date nowDate = new Date();
		Date countDate = GetDate.countDate(endDate,5, -3);
		long time = nowDate.getTime();
		long countTime = countDate.getTime();//智投结束日前3天
		if (time >= countTime) {
			return true;
		}
		return false;
	}

	/**
	 *  债转承接还款
	 * @param apicron
	 * @param borrow
	 * @param borrowInfo
	 * @param borrowRecover
	 * @param creditRepay
	 * @param assignRepayDetail
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateCreditRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover, HjhDebtCreditRepay creditRepay, JSONObject assignRepayDetail) throws Exception {

		/** 还款信息 */
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 借款编号
		String borrowNid = apicron.getBorrowNid();
		// 还款人(借款人或垫付机构)ID
		Integer repayUserId = apicron.getUserId();
		logger.info("【智投还款/承接人】借款编号：{}，开始更新承接人的还款数据。还款人ID：{}，债转订单号：{}", borrowNid, repayUserId, creditRepay.getCreditNid());
		// 还款人用户名
		String repayUserName = apicron.getUserName();
		// 当前期数
		Integer periodNow = apicron.getPeriodNow();
		// 是否是担保机构还款
		int isApicronRepayOrgFlag = Validator.isNull(apicron.getIsRepayOrgFlag()) ? 0 : apicron.getIsRepayOrgFlag();
		String repayBatchNo = apicron.getBatchNo();
		int txDate = Validator.isNotNull(apicron.getTxDate()) ? apicron.getTxDate() : 0;// 批次时间yyyyMMdd
		int txTime = Validator.isNotNull(apicron.getTxTime()) ? apicron.getTxTime() : 0;// 批次时间HHmmss
		String seqNo = Validator.isNotNull(apicron.getSeqNo()) ? String.valueOf(apicron.getSeqNo()) : null;// 流水号
		String bankSeqNo = Validator.isNotNull(apicron.getBankSeqNo()) ? String.valueOf(apicron.getBankSeqNo()) : null;// 银行唯一订单号
		String orderId = assignRepayDetail.getString(BankCallConstant.PARAM_ORDERID);// 还款订单号
		BigDecimal txAmount = assignRepayDetail.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT);// 操作金额
		String forAccountId = assignRepayDetail.getString(BankCallConstant.PARAM_FORACCOUNTID);// 出借人银行账户
		/** 标的基本数据 */
		// 标的是否可用担保机构还款
		int isRepayOrgFlag = Validator.isNull(borrowInfo.getIsRepayOrgFlag()) ? 0 : borrowInfo.getIsRepayOrgFlag();
		// 借款总期数
		Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();
		/** 还款信息 */
		// 出借订单号
		String tenderOrderId = borrowRecover.getNid();
		// 出借人用户ID
		Integer tenderUserId = borrowRecover.getUserId();
		/** 债权还款信息 */
		// 债权编号
		String creditNid = Validator.isNull(creditRepay.getCreditNid()) ? null : creditRepay.getCreditNid();
		// 承接订单号
		String assignNid = creditRepay.getAssignOrderId();
		// 承接用户userId
		int assignUserId = creditRepay.getUserId();
		// 还款订单号
		String repayOrderId = creditRepay.getCreditRepayOrderId();
		// 还款本息(实际)
		BigDecimal assignAccount = creditRepay.getRepayAccount();
		// 还款本金(实际)
		BigDecimal assignCapital = creditRepay.getRepayCapital();
		// 还款利息(实际)
		BigDecimal assignInterest = creditRepay.getRepayInterest();
		// 管理费
		BigDecimal assignManageFee = creditRepay.getManageFee();
		// 提前还款少还利息
		BigDecimal chargeInterest = creditRepay.getRepayAdvanceInterest();
		// 提前还款罚息
		BigDecimal chargePenaltyInterest = creditRepay.getRepayAdvancePenaltyInterest();
		// 逾期利息
		BigDecimal lateInterest = creditRepay.getRepayLateInterest();
		// 还款本息(实际)
		BigDecimal repayAccount = assignAccount.add(lateInterest).add(chargeInterest);
		// 还款本金(实际)
		BigDecimal repayCapital = assignCapital;
		// 还款利息(实际)
		BigDecimal repayInterest = assignInterest.add(lateInterest).add(chargeInterest);
		// 管理费
		BigDecimal manageFee = assignManageFee;
		/** 基本变量 */
		// 剩余还款期数
		Integer periodNext = borrowPeriod - periodNow;
		// 出借用户开户信息
		Account assignBankAccount = this.getAccount(assignUserId);
		// 出借用户银行账户
		String assignAccountId = assignBankAccount.getAccountId();
		// 判断该收支明细存在时,跳出本次循环
        if (countCreditAccountListByNid(repayOrderId)) {
            logger.error("【智投还款/承接人】借款编号：{}，承接人收支明细已存在！债转订单号：{}", borrowNid, repayOrderId);
            return true;
        }
		// 查询相应的债权承接记录
		HjhDebtCreditTender creditTender = this.getCreditTenderHjh(assignNid);
		if (Validator.isNull(creditTender)) {
			throw new Exception("债权承接记录不存在！[承接人用户ID：" + assignUserId + "]，[承接订单号：" + assignNid + "]");
		}
		// 是否分期
		// true:分期 principal: 等额本金，month:等额本息，endmonth:先息后本
		// false:不分期 endday:按天计息，end:按月计息
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		//获得债转详情
		HjhDebtDetail debtDetail = this.getDebtDetail(assignNid, periodNow,assignUserId);
		// 分期还款计划表
		BorrowRecoverPlan borrowRecoverPlan = null;
		//承接智投订单号
		String assignPlanOrderId = creditRepay.getAssignPlanOrderId();
		HjhAccedeExample accedeExample = new HjhAccedeExample();
		accedeExample.createCriteria().andAccedeOrderIdEqualTo(assignPlanOrderId);
		List<HjhAccede> accedeList = this.hjhAccedeMapper.selectByExample(accedeExample);
		HjhAccede hjhAccede = accedeList.get(0);
		BigDecimal frostAccount = hjhAccede.getFrostAccount();
		BigDecimal availableInvestAccount = hjhAccede.getAvailableInvestAccount();
		logger.info("【智投还款/承接人】借款编号：{}，债转编号：{}，承接智投订单号：{}，处理前冻结金额：{}", borrowNid, creditNid, assignPlanOrderId, frostAccount);
		logger.info("【智投还款/承接人】借款编号：{}，债转编号：{}，承接智投订单号：{}，处理前订单可用金额：{}", borrowNid, creditNid, assignPlanOrderId, availableInvestAccount);
		HjhAccede paramInfo = new HjhAccede();
		paramInfo.setId(hjhAccede.getId());

		String dateStr = "";
		if(hjhAccede.getEndDate() != null){
			GetDate.dateToString(hjhAccede.getEndDate());
		}
		logger.info("【智投还款/承接人】借款编号：{}，债转编号：{}，承接智投订单号：{}，结束时间：{}", borrowNid, creditNid, assignPlanOrderId, dateStr);
		if (hjhAccede.getEndDate() != null && isForstTime(hjhAccede.getEndDate())) {
			logger.info("【智投还款/承接人】借款编号：{}，开始增加冻结金额。债转编号：{}，承接智投订单号：{}，原冻结金额：{}，增加金额：{}", borrowNid, creditNid, assignPlanOrderId, frostAccount, repayAccount);
			HjhRepayExample repayExample = new HjhRepayExample();
			repayExample.createCriteria().andAccedeOrderIdEqualTo(assignPlanOrderId);
			List<HjhRepay> repayList = this.hjhRepayMapper.selectByExample(repayExample);
			HjhRepay hjhRepay = repayList.get(0);
			HjhRepay repayParam = new HjhRepay();
			repayParam.setId(hjhRepay.getId());
			paramInfo.setFrostAccount(repayAccount);//智投冻结金额
			repayParam.setRepayTotal(repayAccount);
			repayParam.setPlanRepayCapital(repayCapital);
			repayParam.setPlanRepayInterest(repayInterest);
			repayParam.setRepayAlready(repayAccount);
			this.hjhPlanCustomizeMapper.updateHjhRepayForHjhRepay(repayParam);
			logger.info("【智投还款/承接人】借款编号：{}，开始增加还款。债转编号：{}，承接智投订单号：{}，原冻结金额：{}，增加金额：{}", borrowNid, creditNid, assignPlanOrderId, hjhRepay.getRepayAlready(), repayAccount);
		}else{
			logger.info("【智投还款/承接人】借款编号：{}，开始增加可用金额。债转编号：{}，承接智投订单号：{}", borrowNid, creditNid, assignPlanOrderId);
			paramInfo.setAvailableInvestAccount(repayAccount);//智投订单可用金额
		}
		this.hjhPlanCustomizeMapper.updateHjhAccedeForHjhProcess(paramInfo);
		// 债转的下次还款时间
		int creditRepayNextTime = creditRepay.getAssignRepayNextTime();
		// 更新账户信息(出借人)
		Account assignUserAccount = new Account();
		assignUserAccount.setUserId(assignUserId);
		if (hjhAccede.getEndDate() != null && isForstTime(hjhAccede.getEndDate())) {
			assignUserAccount.setPlanFrost(repayAccount);//汇智投冻结金额
		}else{
			assignUserAccount.setPlanBalance(repayAccount);//汇智投可用金额
		}
		assignUserAccount.setBankBalanceCash(repayAccount);// 出借人银行可用余额
		boolean investAccountFlag = this.adminAccountCustomizeMapper.updateOfRepayPlanTender(assignUserAccount) > 0 ? true : false;
		if (!investAccountFlag) {
			throw new Exception("账户信息表(ht_account)更新失败！[承接人ID：" + tenderUserId + "]，[承接订单号：" + assignNid + "]");
		}
		// 取得承接人账户信息
		assignUserAccount = this.getAccount(creditRepay.getUserId());
		if (Validator.isNull(assignAccount)) {
			throw new Exception("承接人账户信息不存在！[用户ID：" + creditRepay.getUserId() + "]，[承接订单号：" + assignNid + "]");
		}
		// 写入收支明细
		AccountList accountList = new AccountList();
		accountList.setNid(repayOrderId); // 还款订单号
		accountList.setUserId(assignUserId); // 出借人
		accountList.setAmount(repayAccount); // 出借总收入
		/** 银行相关 */
		accountList.setAccountId(assignAccountId);
		accountList.setBankAwait(assignUserAccount.getBankAwait());
		accountList.setBankAwaitCapital(assignUserAccount.getBankAwaitCapital());
		accountList.setBankAwaitInterest(assignUserAccount.getBankAwaitInterest());
		accountList.setBankBalance(assignUserAccount.getBankBalance());
		accountList.setBankFrost(assignUserAccount.getBankFrost());
		accountList.setBankInterestSum(assignUserAccount.getBankInterestSum());
		accountList.setBankInvestSum(assignUserAccount.getBankInvestSum());
		accountList.setBankTotal(assignUserAccount.getBankTotal());
		accountList.setBankWaitCapital(assignUserAccount.getBankWaitCapital());
		accountList.setBankWaitInterest(assignUserAccount.getBankWaitInterest());
		accountList.setBankWaitRepay(assignUserAccount.getBankWaitRepay());
		// 如果是机构垫付还款
		if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
			if (forAccountId.equals(assignAccountId) && repayOrderId.equals(orderId) && txAmount.compareTo(repayAccount) == 0) {
				accountList.setCheckStatus(1);
			} else {
				accountList.setCheckStatus(0);
			}
		} else {
			if (forAccountId.equals(assignAccountId) && repayOrderId.equals(orderId) && txAmount.compareTo(repayCapital) == 0) {
				accountList.setCheckStatus(1);
			} else {
				accountList.setCheckStatus(0);
			}
		}
		accountList.setTradeStatus(1);// 交易状态 0:失败 1:成功
		accountList.setIsBank(1);
		accountList.setTxDate(txDate);
		accountList.setTxTime(txTime);
		accountList.setSeqNo(seqNo);
		accountList.setBankSeqNo(bankSeqNo);
		/** 非银行相关 */
		if (hjhAccede.getEndDate() != null && isForstTime(hjhAccede.getEndDate())) {
			accountList.setType(3); // 冻结
		}else{
			accountList.setType(1); // 1收入
		}
		
		if (hjhAccede.getEndDate() != null && isForstTime(hjhAccede.getEndDate())) {
			accountList.setTrade("credit_tender_recover_forst"); // 投标成功冻结
		}else{
			accountList.setTrade("credit_tender_recover_yes"); // 投标成功
		}
		accountList.setTradeCode("balance"); // 余额操作
		accountList.setTotal(assignUserAccount.getTotal()); // 出借人资金总额
		accountList.setBalance(assignUserAccount.getBalance()); // 出借人可用金额
		accountList.setPlanFrost(assignUserAccount.getPlanFrost());// 汇添金冻结金额
		accountList.setPlanBalance(assignUserAccount.getPlanBalance());// 汇添金可用金额
		accountList.setFrost(assignUserAccount.getFrost()); // 出借人冻结金额
		accountList.setAwait(assignUserAccount.getAwait()); // 出借人待收金额
		accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作者
		accountList.setRemark(borrowNid);
		accountList.setIp(borrow.getAddIp()); // 操作IP
		accountList.setWeb(0); // PC
		accountList.setIsShow(1);//出借人不显示
		boolean assignAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
		if (!assignAccountListFlag) {
			throw new Exception("收支明细表(ht_account_list)写入失败！[承接人用户ID：" + assignUserId + "]，[承接订单号：" + assignNid + "]");
		}
		// 更新还款金额数据 update by wgx 2019/03/07
		logger.info("【智投还款/承接人】借款编号：{}，开始更新标的表。总还款增加：{}，未还款总额减少：{}，未还款利息减少：{}",
				borrowNid, repayAccount, assignAccount, assignInterest);
		Borrow updateBorrow = new Borrow();
		updateBorrow.setId(borrow.getId());
		updateBorrow.setRepayAccountYes(repayAccount);// 总还款增加
		updateBorrow.setRepayAccountInterestYes(repayInterest);// 总还款利息增加
		updateBorrow.setRepayAccountCapitalYes(repayCapital);// 总还款本金增加
		updateBorrow.setRepayAccountWait(assignAccount);// 未还款总额减少
		updateBorrow.setRepayAccountCapitalWait(assignCapital);// 未还款本金减少
		updateBorrow.setRepayAccountInterestWait(assignInterest);// 未还款利息减少
		updateBorrow.setRepayFeeNormal(manageFee);// 借款的管理费增加
		boolean borrowFlag = this.borrowCustomizeMapper.updateRepayOfBorrow(updateBorrow) > 0 ? true : false;
		if (!borrowFlag) {
			throw new Exception("标的表(ht_borrow)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]");
		}
		boolean isAllRepay = apicron.getIsAllrepay() == null ? false : apicron.getIsAllrepay() == 1;// 是否是一次性还款
		Integer lastPeriod = apicron.getLastPeriod() == null ? 0 : apicron.getLastPeriod();// 同时提交还款的最后一期
		isAllRepay = isAllRepay || lastPeriod == borrowPeriod;// 多期还款的最后一期是标的的最后一期，是一次性还款
		// 更新相应的债转出借表
		// 债转已还款总额
		creditTender.setRepayAccountYes(creditTender.getRepayAccountYes().add(repayAccount));
		// 债转已还款本金
		creditTender.setRepayCapitalYes(creditTender.getRepayCapitalYes().add(repayCapital));
		// 债转已还款利息
		creditTender.setRepayInterestYes(creditTender.getRepayInterestYes().add(repayInterest));
		// 债转最近还款时间
		creditTender.setAssignRepayLastTime(!isMonth ? nowTime : 0);
		// 债转下次还款时间
		if (lastPeriod == 0 || periodNow == lastPeriod) {// 多期还款只有最后一期才更新
			if (!isAllRepay || periodNow == borrowPeriod) {// 一次性还款只有最后一期更新
				creditTender.setAssignRepayNextTime(!isMonth ? 0 : creditRepayNextTime);
			}
		}
		// 债转还款状态
		boolean isLastUpdate = false;
		if (isMonth) {
			// 取得放款分期列表
			borrowRecoverPlan = getBorrowRecoverPlan(borrowNid, periodNow, tenderUserId, tenderOrderId);
			if (borrowRecoverPlan == null) {
				throw new Exception("放款分期数据不存在！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]，[期数：" + periodNow + "]");
			}
			// 首先判断当前期是否是一次性还款中唯一一期需要更新的 update by wgx 2019/02/28
			isLastUpdate = isLastAllCreditRepay(borrowNid, periodNow, creditRepay, isAllRepay);
			// 债转状态
			if (borrowRecoverPlan != null && !isLastUpdate && (isAllRepay || periodNext > 0)) {
				creditTender.setStatus(0);
			} else {
				creditTender.setStatus(1);
				// 债转最后还款时间
				creditTender.setAssignRepayYesTime(nowTime);
			}
		} else {
			creditTender.setStatus(1);
			// 债转最后还款时间
			creditTender.setAssignRepayYesTime(nowTime);
		}
		// 债转还款期
		creditTender.setRepayPeriod(periodNow);
		boolean creditTenderFlag = this.hjhDebtCreditTenderMapper.updateByPrimaryKeySelective(creditTender) > 0 ? true : false;
		if (!creditTenderFlag) {
			throw new Exception("债转出借表(ht_hjh_debt_credit_tender)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]");
		}
		creditRepay.setReceiveAccountYes(creditRepay.getReceiveAccountYes().add(repayAccount));
		creditRepay.setReceiveCapitalYes(creditRepay.getReceiveCapitalYes().add(repayCapital));
		creditRepay.setReceiveInterestYes(creditRepay.getReceiveInterestYes().add(repayInterest));
		creditRepay.setAssignRepayLastTime(nowTime);
		creditRepay.setAssignRepayYesTime(nowTime);
		creditRepay.setManageFee(manageFee);
		creditRepay.setRepayStatus(1);
		if(creditRepay.getAdvanceStatus() == 30){
			creditRepay.setAdvanceStatus(3);// 逾期标的，已还款后重置
		}
		boolean creditRepayFlag = this.hjhDebtCreditRepayMapper.updateByPrimaryKeySelective(creditRepay) > 0 ? true : false;
		if (!creditRepayFlag) {
			throw new Exception("债转还款表(ht_hjh_debt_credit_repay)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]");
		}
		//更新债权明细表
		// 更新DebtDetail表
		debtDetail.setRepayActionTime(nowTime);
		// 已还款
		debtDetail.setRepayStatus(1);
		// 账户管理费
		debtDetail.setManageFee(manageFee);
		// 已还本金
		debtDetail.setRepayCapitalYes(repayCapital);
		// 账户服务费
		// debtDetail.setServiceFee(big);
		// 已还利息
		debtDetail.setRepayInterestYes(repayInterest);
		// 未收本金
		debtDetail.setRepayCapitalWait(BigDecimal.ZERO);
		// 未收利息
		debtDetail.setRepayInterestWait(BigDecimal.ZERO);
		// 提前还款状态
		debtDetail.setAdvanceStatus(creditRepay.getAdvanceStatus());
		// 提前还款天数
		debtDetail.setAdvanceDays(creditRepay.getAdvanceDays());
		// 提前还款利息
		debtDetail.setAdvanceInterest(chargeInterest);
		// 逾期天数
		debtDetail.setLateDays(creditRepay.getLateDays());
		// 逾期利息
		debtDetail.setLateInterest(lateInterest);
		// 还款订单号
		debtDetail.setRepayOrderId(repayOrderId);
		// 还款日期
		debtDetail.setRepayOrderDate(creditRepay.getCreditRepayOrderDate());
		// 债权更新时间
		// debtDetail.setUpdateTime(nowTime);
		//结束债权
		debtDetail.setDelFlag(1);
		// 到期公允价值
		debtDetail.setExpireFairValue(BigDecimal.ZERO);
		int flag = this.hjhDebtDetailMapper.updateByPrimaryKey(debtDetail);
		if (flag > 0) {
			logger.info("【智投还款/承接人】借款编号：{}，债权详情表(ht_hjh_debt_detail)更新成功。承接订单号：{}", borrowNid, assignNid);
		}
		// 更新还款表（不分期）
		BorrowRecover newBorrowRecover = new BorrowRecover();
		newBorrowRecover.setId(borrowRecover.getId());
		newBorrowRecover.setRepayBatchNo(repayBatchNo);
		newBorrowRecover.setRecoverAccountYes(repayAccount); // 已还款总额
		// 已还款本金
		newBorrowRecover.setRecoverCapitalYes(repayCapital);
		// 已还款利息
		newBorrowRecover.setRecoverInterestYes(repayInterest);
		// 待还金额
		newBorrowRecover.setRecoverAccountWait(assignAccount);
		// 待还本金
		newBorrowRecover.setRecoverCapitalWait(assignCapital);
		// 待还利息
		newBorrowRecover.setRecoverInterestWait(assignInterest);
		// 已还款提前还款利息
		newBorrowRecover.setRepayChargeInterest(chargeInterest);
		// 已还款提前还款罚息
        newBorrowRecover.setRepayChargePenaltyInterest(chargePenaltyInterest);
		// 已还款逾期还款利息
		newBorrowRecover.setRepayLateInterest(lateInterest);
		// 已还款管理费
		newBorrowRecover.setRecoverFeeYes(manageFee);
        logger.info("【智投还款/承接人】借款编号：{}，开始更新放款记录总表。未还款总额：{}，还款额：{}",
                borrowNid, borrowRecover.getRecoverAccountWait(), assignAccount);
		// 更新还款表
		boolean creditBorrowRecoverFlag = batchBorrowRecoverCustomizeMapper.updateRepayOfBorrowRecover(newBorrowRecover) > 0 ? true : false;
		if (!creditBorrowRecoverFlag) {
			throw new Exception("放款记录总表(ht_borrow_recover)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]");
		}
        // 查询相应的债权转让
        HjhDebtCredit borrowCredit = this.getBorrowCredit(creditNid);
        // 债转总表数据更新
        // 更新债转已还款总额
        borrowCredit.setRepayAccount(borrowCredit.getRepayAccount().add(repayAccount));
        // 更新债转已还款本金
        borrowCredit.setRepayCapital(borrowCredit.getRepayCapital().add(repayCapital));
        // 更新债转已还款利息
        borrowCredit.setRepayInterest(borrowCredit.getRepayInterest().add(repayInterest));
        // 债转下次还款时间
		if (lastPeriod == 0 || periodNow == lastPeriod) {// 多期还款只有最后一期才更新
			if (!isAllRepay || periodNow == borrowPeriod) {// 一次性还款只有最后一期更新
				borrowCredit.setCreditRepayNextTime(isMonth ? creditRepayNextTime : 0);
			}
		}
        if (borrowCredit.getCreditStatus() == 0) {
            borrowCredit.setCreditStatus(1);
        }
        // 更新债转总表
        boolean borrowCreditFlag = this.hjhDebtCreditMapper.updateByPrimaryKeySelective(borrowCredit) > 0 ? true : false;
        logger.info("【智投还款/承接人】借款编号：{}，债转编号：{}，债转标的表(ht_hjh_debt_credit)更新成功。", borrowNid, creditNid);
        // 债转总表更新成功
        if (!borrowCreditFlag) {
            throw new Exception("债转标的表(ht_hjh_debt_credit)更新失败！[债转编号：" + creditNid + "]，[承接订单号：" + assignNid + "]");
        }
		// 取得还款详情
		BorrowRepay borrowRepay = getBorrowRepayAsc(borrowNid, apicron);
		// 更新总的还款明细
		borrowRepay.setRepayAccountAll(borrowRepay.getRepayAccountAll().add(repayAccount).add(manageFee));
		borrowRepay.setRepayAccountYes(borrowRepay.getRepayAccountYes().add(repayAccount));
		borrowRepay.setRepayCapitalYes(borrowRepay.getRepayCapitalYes().add(repayCapital));
		borrowRepay.setRepayInterestYes(borrowRepay.getRepayInterestYes().add(repayInterest));
        // 已还款提前还款利息
        borrowRepay.setChargeInterest(borrowRepay.getChargeInterest().add(chargeInterest));
        // 已还款提前还款罚息
        borrowRepay.setChargePenaltyInterest(borrowRepay.getChargePenaltyInterest().add(chargePenaltyInterest));
        // 已还款逾期还款利息
        borrowRepay.setLateInterest(borrowRepay.getLateInterest().add(lateInterest));
		// 逾期天数
		borrowRepay.setLateRepayDays(borrowRecover.getLateDays());
		// 提前天数
		borrowRepay.setChargeDays(borrowRecover.getChargeDays());
		// 用户是否提前还款
		borrowRepay.setAdvanceStatus(borrowRecover.getAdvanceStatus());
		// 还款来源
		if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
			// 还款来源（1、借款人还款，2、机构垫付，3、保证金垫付）
			borrowRepay.setRepayMoneySource(2);
		} else {
			borrowRepay.setRepayMoneySource(1);
		}
		// 实际还款人（借款人、垫付机构、保证金）的用户ID
		borrowRepay.setRepayUserId(repayUserId);
		// 实际还款人（借款人、垫付机构、保证金）的用户名
		borrowRepay.setRepayUsername(repayUserName);
		boolean borrowRepayFlag = this.borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay) > 0 ? true : false;
		if (!borrowRepayFlag) {
			throw new Exception("还款记录总表(ht_borrow_repay)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]");
		}
		// 如果分期
		if (isMonth) {
			// 更新还款表（分期）
			// 已还款总额
			borrowRecoverPlan.setRepayBatchNo(repayBatchNo);
			borrowRecoverPlan.setRecoverAccountYes(borrowRecoverPlan.getRecoverAccountYes().add(repayAccount));
			// 已还款本金
			borrowRecoverPlan.setRecoverCapitalYes(borrowRecoverPlan.getRecoverCapitalYes().add(repayCapital));
			// 已还款利息
			borrowRecoverPlan.setRecoverInterestYes(borrowRecoverPlan.getRecoverInterestYes().add(repayInterest));
			// 待还金额
			borrowRecoverPlan.setRecoverAccountWait(borrowRecoverPlan.getRecoverAccountWait().subtract(assignAccount));
			logger.info("【智投还款/承接人】借款编号：{}，开始更新债转还款记录任务表金额。原金额：{}，扣减金额：{}",
					borrowNid, borrowRecoverPlan.getRecoverAccountWait(), assignAccount);
			// 待还本金
			borrowRecoverPlan.setRecoverCapitalWait(borrowRecoverPlan.getRecoverCapitalWait().subtract(assignCapital));
			// 待还利息
			borrowRecoverPlan.setRecoverInterestWait(borrowRecoverPlan.getRecoverInterestWait().subtract(assignInterest));
			// 已还款提前还款利息
			borrowRecoverPlan.setRepayChargeInterest(borrowRecoverPlan.getRepayChargeInterest().add(chargeInterest));
            // 已还款提前还款罚息
            borrowRecoverPlan.setRepayChargePenaltyInterest(borrowRecoverPlan.getRepayChargePenaltyInterest().add(chargePenaltyInterest));
			// 已还款逾期还款利息
			borrowRecoverPlan.setRepayLateInterest(borrowRecoverPlan.getRepayLateInterest().add(lateInterest));
			// 已还款管理费
			borrowRecoverPlan.setRecoverFeeYes(borrowRecoverPlan.getRecoverFeeYes().add(manageFee));
			// 更新还款计划表
			boolean borrowRecoverPlanFlag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
			if (!borrowRecoverPlanFlag) {
				throw new Exception("放款记录分期表(ht_borrow_recover_plan)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]，[期数：" + periodNow + "]");
			}
			// 更新总的还款计划
			BorrowRepayPlan borrowRepayPlan = getBorrowRepayPlan(borrowNid, periodNow);
			if (borrowRepayPlan != null) {
				// 还款总额
				borrowRepayPlan.setRepayAccountAll(borrowRepayPlan.getRepayAccountAll().add(repayAccount).add(manageFee));
				// 已还金额
				borrowRepayPlan.setRepayAccountYes(borrowRepayPlan.getRepayAccountYes().add(repayAccount));
				// 已还利息
				borrowRepayPlan.setRepayInterestYes(borrowRepayPlan.getRepayInterestYes().add(repayInterest));
				// 已还本金
				borrowRepayPlan.setRepayCapitalYes(borrowRepayPlan.getRepayCapitalYes().add(repayCapital));
                // 已还款提前还款利息
                borrowRepayPlan.setChargeInterest(borrowRepayPlan.getChargeInterest().add(chargeInterest));
                // 已还款提前还款罚息
                borrowRepayPlan.setChargePenaltyInterest(borrowRepayPlan.getChargePenaltyInterest().add(chargePenaltyInterest));
                // 已还款逾期还款利息
                borrowRepayPlan.setLateInterest(borrowRepayPlan.getLateInterest().add(lateInterest));
				// 逾期天数
				borrowRepayPlan.setLateRepayDays(borrowRecoverPlan.getLateDays());
				// 提前天数
				borrowRepayPlan.setChargeDays(borrowRecoverPlan.getChargeDays());
				// 用户是否提前还款
				borrowRepayPlan.setAdvanceStatus(borrowRecoverPlan.getAdvanceStatus());
				// 还款来源
				if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
					// 还款来源（1、借款人还款，2、机构垫付，3、保证金垫付）
					borrowRepayPlan.setRepayMoneySource(2);
				} else {
					borrowRepayPlan.setRepayMoneySource(1);
				}
				// 实际还款人（借款人、垫付机构、保证金）的用户ID
				borrowRepayPlan.setRepayUserId(repayUserId);
				// 实际还款人（借款人、垫付机构、保证金）的用户名
				borrowRepayPlan.setRepayUsername(repayUserName);
				boolean borrowRepayPlanFlag = this.borrowRepayPlanMapper.updateByPrimaryKeySelective(borrowRepayPlan) > 0 ? true : false;
				if (!borrowRepayPlanFlag) {
					throw new Exception("还款记录分期表(ht_borrow_repay_plan)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]，[期数：" + periodNow + "]");
				}
			} else {
				throw new Exception("分期还款记录不存在！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]，[期数：" + periodNow + "]");
			}
		}
        // 出借信息
        BorrowTender borrowTender = getBorrowTender(tenderOrderId);
		// 更新出借表
		// 已还款金额
		borrowTender.setRecoverAccountYes(borrowTender.getRecoverAccountYes().add(repayAccount));
		// 已还款利息
		borrowTender.setRecoverAccountInterestYes(borrowTender.getRecoverAccountInterestYes().add(repayInterest));
		// 已还款本金
		borrowTender.setRecoverAccountCapitalYes(borrowTender.getRecoverAccountCapitalYes().add(repayCapital));
		// 待还金额
		borrowTender.setRecoverAccountWait(borrowTender.getRecoverAccountWait().subtract(assignAccount));
		// 待还本金
		borrowTender.setRecoverAccountCapitalWait(borrowTender.getRecoverAccountCapitalWait().subtract(assignCapital));
		// 待还利息
		borrowTender.setRecoverAccountInterestWait(borrowTender.getRecoverAccountInterestWait().subtract(assignInterest));
		boolean borrowTenderFlag = borrowTenderMapper.updateByPrimaryKeySelective(borrowTender) > 0 ? true : false;
		if (!borrowTenderFlag) {
			throw new Exception("出借表(ht_borrow_tender)更新失败！[出借订单号：" + tenderOrderId + "]，[承接订单号：" + assignNid + "]");
		}
		// 更新批次还款任务
		apicron.setSucAmount(apicron.getSucAmount().add(repayAccount.add(manageFee)));
		apicron.setSucCounts(apicron.getSucCounts() + 1);
		apicron.setFailAmount(apicron.getFailAmount().subtract(repayAccount.add(manageFee)));
		apicron.setFailCounts(apicron.getFailCounts() - 1);
		boolean apicronSuccessFlag = this.borrowApicronMapper.updateByPrimaryKeySelective(apicron) > 0 ? true : false;
		if (!apicronSuccessFlag) {
			throw new Exception("批次还款任务表(ht_borrow_apicron)更新失败！[借款编号：" + borrowNid + "]，[还款期数：" + periodNow + "]");
		}
		if (!isMonth || (isMonth && periodNext == 0 && !isAllRepay) || isLastUpdate) {
			// 结束债权
			boolean debtOverFlag = this.requestDebtEnd(creditRepay.getUserId(), assignRepayDetail, assignNid, borrow);
			if (debtOverFlag) {
				// 更新相应的债权状态为结束
				boolean debtStatusFlag = this.updateDebtStatus(creditRepay);
				if (!debtStatusFlag) {
					throw new Exception("更新相应的债转为债权结束失败！[承接用户：" + assignUserId + "]，[承接订单号：" + assignNid + "]，[还款期数：" + periodNow + "]");
				}
			} else {
				throw new Exception("结束债权失败！[承接用户：" + assignUserId + "]，[承接订单号：" + assignNid + "]，[还款期数：" + periodNow + "]");
			}
		}
		// 管理费大于0时,插入网站收支明细
		// 在出借人整笔还款数据更新完成后再发送mq update by wgx 2019/01/25
		if (manageFee.compareTo(BigDecimal.ZERO) > 0) {
			AccountWebListVO accountWebList = new AccountWebListVO();
			accountWebList.setOrdid(creditRepay.getAssignOrderId() + "_" + periodNow);// 订单号
			accountWebList.setBorrowNid(borrowNid); // 出借编号
			accountWebList.setUserId(repayUserId); // 借款人
			accountWebList.setAmount(Double.valueOf(manageFee.toString())); // 管理费
			accountWebList.setType(CustomConstants.TYPE_IN); // 类型1收入,2支出
			accountWebList.setTrade(CustomConstants.TRADE_REPAYFEE); // 管理费
			accountWebList.setTradeType(CustomConstants.TRADE_REPAYFEE_NM); // 账户管理费
			accountWebList.setRemark(borrowNid); // 出借编号
			accountWebList.setCreateTime(nowTime);
			accountWebList.setFlag(1);
			//网站首支明细队列
			try {
				logger.info("【智投还款/承接人】借款编号：{}，发送收支明细。还款人id：{}，管理费：{}", borrowNid, repayUserId, manageFee);
				commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebList));
			} catch (MQException e) {
				logger.error("【智投还款/承接人】发送收支明细时发生系统异常！", e);
			}
		}
		logger.info("【智投还款/承接人】借款编号：{}，承接人的还款数据更新结束。承接订单号：{}，还款订单号：{}", borrowNid, assignNid, repayOrderId);
		return true;
	}

	/**
	 * 获得债权明细记录
	 * @param tenderOrderId
	 * @param periodNow
	 * @param assignUserId 
	 * @return
	 */
	private HjhDebtDetail getDebtDetail(String tenderOrderId, Integer periodNow, int assignUserId) {
		
		HjhDebtDetailExample example = new HjhDebtDetailExample();
		example.createCriteria().andOrderIdEqualTo(tenderOrderId).andRepayPeriodEqualTo(periodNow).andStatusEqualTo(1).andUserIdEqualTo(assignUserId);
		List<HjhDebtDetail> detailList = this.hjhDebtDetailMapper.selectByExample(example );
		if (detailList != null && detailList.size() > 0) {
			return detailList.get(0);
		}
		return null;
	}

	/**
	 * 根据承接订单号获取汇转让信息
	 * 
	 * @param creditNid
	 * @return
	 */
	private HjhDebtCredit getBorrowCredit(String creditNid) {
		HjhDebtCreditExample example = new HjhDebtCreditExample();
		HjhDebtCreditExample.Criteria crt = example.createCriteria();
		crt.andCreditNidEqualTo(creditNid);
		List<HjhDebtCredit> borrowCreditList = this.hjhDebtCreditMapper.selectByExample(example);
		if (borrowCreditList != null && borrowCreditList.size() == 1) {
			return borrowCreditList.get(0);
		}
		return null;
	}

	/**
	 * 获取承接信息
	 * 
	 * @param assignNid
	 * @return
	 */
	private HjhDebtCreditTender getCreditTenderHjh(String assignNid) {
		
		HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
		example.createCriteria().andAssignOrderIdEqualTo(assignNid);
		List<HjhDebtCreditTender> creditTenderList = this.hjhDebtCreditTenderMapper.selectByExample(example);
		if (creditTenderList != null && creditTenderList.size() == 1) {
			return creditTenderList.get(0);
		}
		return null;
	}

	/**
	 * 更新还款完成状态
	 * 
	 * @param borrow
	 *
	 * @param apicron
	 * @param apicron
	 * @throws Exception
	 */
	@Override
	public int updateBorrowStatus(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo) throws Exception {

		int nowTime = GetDate.getNowTime10();
		String borrowNid = borrow.getBorrowNid();
		int borrowUserId = borrow.getUserId();
		String borrowStyle = borrow.getBorrowStyle();// 借款还款方式
		int borrowId = borrow.getId();// 标的记录主键
		// 标的是否可用担保机构还款
		int isRepayOrgFlag = Validator.isNull(borrowInfo.getIsRepayOrgFlag()) ? 0 : borrowInfo.getIsRepayOrgFlag();
		apicron = this.borrowApicronMapper.selectByPrimaryKey(apicron.getId());
		int repayUserId = apicron.getUserId();
		int periodNow = apicron.getPeriodNow();
		int repayCount = apicron.getTxCounts();// 还款总笔数
		int txDate = Validator.isNotNull(apicron.getTxDate()) ? apicron.getTxDate() : 0;// 批次时间yyyyMMdd
		int txTime = Validator.isNotNull(apicron.getTxTime()) ? apicron.getTxTime() : 0;// 批次时间hhmmss
		String seqNo = Validator.isNotNull(apicron.getSeqNo()) ? String.valueOf(apicron.getSeqNo()) : null;// 流水号
		String bankSeqNo = Validator.isNotNull(apicron.getBankSeqNo()) ? String.valueOf(apicron.getBankSeqNo()) : null;// 银行唯一订单号
		// 是否是担保机构还款
		int isApicronRepayOrgFlag = Validator.isNull(apicron.getIsRepayOrgFlag()) ? 0 : apicron.getIsRepayOrgFlag();
		// 还款人账户信息
		Account repayBankAccount = this.getAccount(repayUserId);
		String repayAccountId = repayBankAccount.getAccountId();
		String apicronNid = apicron.getNid();
		logger.info("【智投还款】借款编号：{}，出借人和承接人数据更新完成，更新借款人数据开始。还款期数：{}",borrowNid,periodNow);
		// 还款期数
		int borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 剩余还款期数
		int periodNext = borrowPeriod - periodNow;
		// 是否分期(true:分期, false:不分期)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		int failCount = 0;
		int repayStatus = 0;
		int status = 4;
		String repayType = TYPE_WAIT;
		int repayYesTime = 0;
		// 标的总表信息
		Borrow newBorrow = new Borrow();
		if (isMonth) {
			// 查询recover
			BorrowRecoverPlanExample recoverPlanExample = new BorrowRecoverPlanExample();
			recoverPlanExample.createCriteria().andBorrowNidEqualTo(borrowNid).andRecoverPeriodEqualTo(periodNow).andRecoverStatusNotEqualTo(1);
			// 原始投资的失败笔数
			failCount = this.borrowRecoverPlanMapper.countByExample(recoverPlanExample);
			logger.info("【智投还款】借款编号：{}，原始投资失败笔数为：{}", borrowNid, failCount);
			// 如果还款全部完成
			if (failCount == 0) {
				// // 首先更新还款状态，主要为了锁住当前标的记录
				newBorrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
				BorrowExample borrowExample = new BorrowExample();
				borrowExample.createCriteria().andIdEqualTo(borrowId);
				boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
				if (!borrowFlag) {
					throw new Exception("最后一期还款成功后，标的表(ht_borrow)更新还款状态失败！[借款编号：" + borrowNid + "]，还款期数：" + periodNow + "]");
				}
				boolean isAllRepay = apicron.getIsAllrepay() == null ? false : apicron.getIsAllrepay() == 1;
				Integer lastPeriod = apicron.getLastPeriod() == null ? 0 :apicron.getLastPeriod();// 同时提交还款的最后一期
				isAllRepay = isAllRepay || lastPeriod == borrowPeriod;// 多期还款的最后一期是标的的最后一期，是一次性还款
                // 首先判断当前期是否是一次性还款中唯一一期需要更新的 update by wgx 2019/02/19
                boolean isLastUpdate = isLastAllRepay(borrowNid, periodNow, isAllRepay, lastPeriod);
				if ((isAllRepay && isLastUpdate) || (!isAllRepay && periodNext == 0)) {
					repayType = TYPE_WAIT_YES;
					repayStatus = 1;
					repayYesTime = nowTime;
					status = 5;
				}
				// 非一次性还款或一次性还款其他期都更新完毕才更新
				if(!isAllRepay || isLastUpdate){
					// 更新Borrow
					newBorrow.setRepayFullStatus(repayStatus);
					if (lastPeriod == 0 || isAllRepay) {// 逾期还款且未全部还完不更新标的状态
						newBorrow.setStatus(status);
					}
					BorrowExample borrowExample2 = new BorrowExample();
					borrowExample2.createCriteria().andIdEqualTo(borrowId);
					boolean borrowFlag2 = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample2) > 0 ? true : false;
					if (!borrowFlag2) {
						throw new Exception("最后一期还款成功后，标的表(ht_borrow)更新失败！[借款编号：" + borrowNid + "]，还款期数：" + periodNow + "]");
					}
				}
				// 更细还款总表状态
				BorrowRepay newBorrowRepay = new BorrowRepay();
				newBorrowRepay.setRepayType(repayType);
				newBorrowRepay.setRepayStatus(repayStatus); // 已还款
				newBorrowRepay.setRepayPeriod(isMonth ? lastPeriod > periodNow ? borrowPeriod - lastPeriod : periodNext : 1);
				newBorrowRepay.setRepayActionTime(nowTime);// 实际还款时间
				// 分期的场合，根据借款编号和还款期数从还款计划表中取得还款时间
				BorrowRepayPlanExample example = new BorrowRepayPlanExample();
				BorrowRepayPlanExample.Criteria repayPlanCriteria = example.createCriteria();
				repayPlanCriteria.andBorrowNidEqualTo(borrowNid);
				repayPlanCriteria.andRepayPeriodEqualTo(lastPeriod > periodNow ? lastPeriod + 1 : periodNow + 1);
				List<BorrowRepayPlan> replayPlan = borrowRepayPlanMapper.selectByExample(example);
				if ((!isLastUpdate || (!isAllRepay && isLastUpdate)) && replayPlan.size() > 0) {
					BorrowRepayPlan borrowRepayPlanNext = replayPlan.get(0);
					if (borrowRepayPlanNext != null) {
						// 取得下期还款时间
						int repayTime = borrowRepayPlanNext.getRepayTime();
						// 设置下期还款时间
						newBorrowRepay.setRepayTime(repayTime);
						// 设置下期还款时间
						newBorrow.setRepayNextTime(repayTime);
					}
				} else {
					// 还款成功最后时间
					newBorrowRepay.setRepayYestime(repayYesTime);
				}
				// 更新BorrowRepay
				BorrowRepayExample repayExample = new BorrowRepayExample();
				repayExample.createCriteria().andBorrowNidEqualTo(borrowNid);
				boolean borrowRepayFlag = this.borrowRepayMapper.updateByExampleSelective(newBorrowRepay, repayExample) > 0 ? true : false;
				if (!borrowRepayFlag) {
					throw new Exception("还款记录分期表(ht_borrow_repay_plan)更新失败！[借款编号：" + borrowNid + "]，[还款期数：" + periodNow + "]");
				}
				// 更新相应的还款计划表
				BorrowRepayPlan borrowRepayPlan = getBorrowRepayPlan(borrowNid, periodNow);
				BorrowRepayPlan newBorrowRepayPlan = new BorrowRepayPlan();
				if (Validator.isNull(borrowRepayPlan)) {
					throw new Exception("未查询到相应的分期还款记录！[借款编号：" + borrowNid + "]，[还款期数：" + periodNow + "]");
				}
				newBorrowRepayPlan.setId(borrowRepayPlan.getId());
				newBorrowRepayPlan.setRepayType(TYPE_WAIT_YES);
				newBorrowRepayPlan.setRepayActionTime(String.valueOf(nowTime));
				newBorrowRepayPlan.setRepayStatus(1);
				newBorrowRepayPlan.setRepayYestime(nowTime);
				if(borrowRepayPlan.getAdvanceStatus() == 30){
					newBorrowRepayPlan.setAdvanceStatus(3);// 逾期标的，已还款后重置
				}
				boolean borrowRepayPlanFlag = this.borrowRepayPlanMapper.updateByPrimaryKeySelective(newBorrowRepayPlan) > 0 ? true : false;
				if (!borrowRepayPlanFlag) {
					throw new Exception("还款记录分期表(ht_borrow_repay_plan)更新失败！[借款编号：" + borrowNid + "]，[还款期数：" + periodNow + "]");
				}
				Account repayUserAccount = this.getAccount(repayUserId);
				BigDecimal repayAccount = borrowRepayPlan.getRepayAccountYes();
				/*BigDecimal repayCapital = borrowRepayPlan.getRepayCapitalYes();*/
				BigDecimal manageFee = borrowRepayPlan.getRepayFee();
				BigDecimal repayAccountWait = borrowRepayPlan.getRepayAccount();
				BigDecimal repayCapitalWait = borrowRepayPlan.getRepayCapital();
				BigDecimal repayInterestWait = borrowRepayPlan.getRepayInterest();
				// 如果是机构垫付还款
				if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
					// 更新垫付机构的Account表的待还款金额
					Account newRepayUserAccount = new Account();
					newRepayUserAccount.setUserId(repayUserAccount.getUserId());
					newRepayUserAccount.setBankTotal(repayAccount.add(manageFee));//add by cwyang 资金总额减少
					newRepayUserAccount.setBankFrost(repayAccount.add(manageFee));
					newRepayUserAccount.setBankAwait(BigDecimal.ZERO);
					newRepayUserAccount.setBankAwaitCapital(BigDecimal.ZERO);
					newRepayUserAccount.setBankWaitRepay(BigDecimal.ZERO);
					newRepayUserAccount.setBankFrostCash(repayAccount.add(manageFee));
					boolean repayUserFlag = this.adminAccountCustomizeMapper.updateOfRepayOrgUser(newRepayUserAccount) > 0 ? true : false;
					if (!repayUserFlag) {
						throw new Exception("账户信息表(ht_account)更新失败！[垫付机构用户ID：" + borrowUserId + "]");
					}
					//modify by cwyang 借款人账户还款成功后减去管理费+本金+利息
					BigDecimal waitRepay = repayCapitalWait.add(manageFee).add(repayInterestWait);
					// modify by cwyang 调整对应的金额变化
					Account borrowUserAccount = new Account();
					borrowUserAccount.setUserId(borrowUserId);
					borrowUserAccount.setBankTotal(BigDecimal.ZERO);
					borrowUserAccount.setBankFrost(BigDecimal.ZERO);
					borrowUserAccount.setBankWaitRepay(waitRepay);
					borrowUserAccount.setBankWaitCapital(repayCapitalWait);
					borrowUserAccount.setBankWaitInterest(repayInterestWait);
					borrowUserAccount.setBankWaitRepayOrg(BigDecimal.ZERO);
					borrowUserAccount.setBankFrostCash(BigDecimal.ZERO);
					boolean borrowUserFlag = this.adminAccountCustomizeMapper.updateOfRepayBorrowUser(borrowUserAccount) > 0 ? true : false;
					if (!borrowUserFlag) {
						throw new Exception("账户信息表(ht_account)更新失败！[借款人用户ID：" + borrowUserId + "]");
					}
				} else {
					Account newRepayUserAccount = new Account();
					newRepayUserAccount.setUserId(repayUserId);
					newRepayUserAccount.setBankTotal(repayAccount.add(manageFee));
					newRepayUserAccount.setBankFrost(repayAccount.add(manageFee));
					newRepayUserAccount.setBankWaitRepay(repayAccountWait.add(manageFee));
					newRepayUserAccount.setBankWaitCapital(repayCapitalWait);
					newRepayUserAccount.setBankWaitInterest(repayInterestWait);
					newRepayUserAccount.setBankWaitRepayOrg(BigDecimal.ZERO);
					newRepayUserAccount.setBankFrostCash(repayAccount.add(manageFee));
					boolean repayUserFlag = this.adminAccountCustomizeMapper.updateOfRepayBorrowUser(newRepayUserAccount) > 0 ? true : false;
					if (!repayUserFlag) {
						throw new Exception("账户信息表(ht_account)更新失败！[借款人用户ID：" + repayUserId + "]");
					}
				}
				// 插入还款交易明细
				repayUserAccount = this.getAccount(repayUserId);
				// 插入借款人的收支明细表
				AccountList repayAccountList = new AccountList();
				repayAccountList.setBankAwait(repayUserAccount.getBankAwait());
				repayAccountList.setBankAwaitCapital(repayUserAccount.getBankAwaitCapital());
				repayAccountList.setBankAwaitInterest(repayUserAccount.getBankAwaitInterest());
				repayAccountList.setBankBalance(repayUserAccount.getBankBalance());
				repayAccountList.setBankFrost(repayUserAccount.getBankFrost());
				repayAccountList.setBankInterestSum(repayUserAccount.getBankInterestSum());
				repayAccountList.setBankTotal(repayUserAccount.getBankTotal());
				repayAccountList.setBankWaitCapital(repayUserAccount.getBankWaitCapital());
				repayAccountList.setBankWaitInterest(repayUserAccount.getBankWaitInterest());
				repayAccountList.setBankWaitRepay(repayUserAccount.getBankWaitRepay());
				repayAccountList.setPlanBalance(repayUserAccount.getPlanBalance());//智投账户可用余额
				repayAccountList.setPlanFrost(repayUserAccount.getPlanFrost());
				repayAccountList.setAccountId(repayAccountId);
				repayAccountList.setTxDate(txDate);
				repayAccountList.setTxTime(txTime);
				repayAccountList.setSeqNo(seqNo);
				repayAccountList.setBankSeqNo(bankSeqNo);
				repayAccountList.setCheckStatus(1);
				repayAccountList.setTradeStatus(1);
				repayAccountList.setIsBank(1);
				// 非银行相关
				repayAccountList.setNid(apicronNid); // 交易凭证号生成规则BorrowNid_userid_期数
				repayAccountList.setUserId(repayUserId); // 借款人id
				repayAccountList.setAmount(borrowRepayPlan.getRepayAccountYes().add(borrowRepayPlan.getRepayFee())); // 操作金额
				repayAccountList.setType(2); // 收支类型1收入2支出3冻结
				repayAccountList.setTrade("repay_success"); // 交易类型
				repayAccountList.setTradeCode("balance"); // 操作识别码
				repayAccountList.setTotal(repayUserAccount.getTotal()); // 资金总额
				repayAccountList.setBalance(repayUserAccount.getBalance()); // 可用金额
				repayAccountList.setFrost(repayUserAccount.getFrost()); // 冻结金额
				repayAccountList.setAwait(repayUserAccount.getAwait()); // 待收金额
				repayAccountList.setRepay(repayUserAccount.getRepay()); // 待还金额
				repayAccountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作员
				repayAccountList.setRemark(borrowNid);
				repayAccountList.setIp(""); // 操作IP
				if(!isAllRepay && lastPeriod == 0){//非一次性 & 非多期还款时插入资金明细
					boolean repayAccountListFlag = this.accountListMapper.insertSelective(repayAccountList) > 0 ? true : false;
					if (!repayAccountListFlag) {
						throw new Exception("收支明细表(ht_account_list)写入失败，[借款人用户ID：" + repayUserId + "]，[借款编号:" + borrowNid + "]");
					}
				}
				BorrowApicronExample apicronExample = new BorrowApicronExample();
				apicronExample.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
				BorrowApicron newBorrowApiCron = new BorrowApicron();
				newBorrowApiCron.setStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
				boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(newBorrowApiCron, apicronExample) > 0 ? true : false;
				if (!apicronFlag) {
					throw new Exception("批次还款任务表(ht_borrow_apicron)更新失败！[借款编号：" + borrowNid + "]");
				}
				try {
					this.updateBorrowApicronLog(apicron, CustomConstants.BANK_BATCH_STATUS_SUCCESS);
				} catch (Exception e) {
					logger.error("同步还款任务日志表发生异常！", e);
				}
				if(isLastUpdate){//智投一次性还款判断是否整个标的还款，还款后新增交易明细 add by cwyang 2018-5-21
                    BigDecimal sum = getRepayPlanAccountSum(borrowNid, isAllRepay, lastPeriod);
                    logger.info("【智投还款】借款编号：{}，{}还款插入交易明细。总还款金额：{}", isAllRepay ? "一次性" : "多期", borrowNid, sum);
                    AccountList repayAllAccountList = new AccountList();
                    repayAllAccountList.setBankAwait(repayUserAccount.getBankAwait());
                    repayAllAccountList.setBankAwaitCapital(repayUserAccount.getBankAwaitCapital());
                    repayAllAccountList.setBankAwaitInterest(repayUserAccount.getBankAwaitInterest());
                    repayAllAccountList.setBankBalance(repayUserAccount.getBankBalance());
                    repayAllAccountList.setBankFrost(repayUserAccount.getBankFrost());
                    repayAllAccountList.setBankInterestSum(repayUserAccount.getBankInterestSum());
                    repayAllAccountList.setBankTotal(repayUserAccount.getBankTotal());
                    repayAllAccountList.setBankWaitCapital(repayUserAccount.getBankWaitCapital());
                    repayAllAccountList.setBankWaitInterest(repayUserAccount.getBankWaitInterest());
                    repayAllAccountList.setBankWaitRepay(repayUserAccount.getBankWaitRepay());
                    repayAllAccountList.setPlanBalance(repayUserAccount.getPlanBalance());//智投账户可用余额
                    repayAllAccountList.setPlanFrost(repayUserAccount.getPlanFrost());
                    repayAllAccountList.setAccountId(repayAccountId);
                    repayAllAccountList.setTxDate(txDate);
                    repayAllAccountList.setTxTime(txTime);
                    repayAllAccountList.setSeqNo(seqNo);
                    repayAllAccountList.setBankSeqNo(bankSeqNo);
                    repayAllAccountList.setCheckStatus(1);
                    repayAllAccountList.setTradeStatus(1);
                    repayAllAccountList.setIsBank(1);
                    // 非银行相关
                    repayAllAccountList.setNid(apicronNid); // 交易凭证号生成规则BorrowNid_userid_期数
                    repayAllAccountList.setUserId(repayUserId); // 借款人id
                    repayAllAccountList.setAmount(sum); // 操作金额
                    repayAllAccountList.setType(2); // 收支类型1收入2支出3冻结
                    repayAllAccountList.setTrade("repay_success"); // 交易类型
                    repayAllAccountList.setTradeCode("balance"); // 操作识别码
                    repayAllAccountList.setTotal(repayUserAccount.getTotal()); // 资金总额
                    repayAllAccountList.setBalance(repayUserAccount.getBalance()); // 可用金额
                    repayAllAccountList.setFrost(repayUserAccount.getFrost()); // 冻结金额
                    repayAllAccountList.setAwait(repayUserAccount.getAwait()); // 待收金额
                    repayAllAccountList.setRepay(repayUserAccount.getRepay()); // 待还金额
                    repayAllAccountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作员
                    repayAllAccountList.setRemark(borrowNid);
                    repayAllAccountList.setIp(""); // 操作IP
                    boolean repayAccountListFlag = this.accountListMapper.insertSelective(repayAllAccountList) > 0 ? true : false;
                    if (!repayAccountListFlag) {
                        throw new Exception("收支明细表(ht_account_list)写入失败，[借款人用户ID：" + repayUserId + "]，[借款编号:" + borrowNid + "]");
                    }
				}
			}
		} else {
            // 查询recover
            BorrowRecoverExample recoverExample = new BorrowRecoverExample();
            recoverExample.createCriteria().andBorrowNidEqualTo(borrowNid).andRecoverStatusNotEqualTo(1);
            failCount = this.borrowRecoverMapper.countByExample(recoverExample);
            logger.info("【智投还款】借款编号：{}，原始投资失败笔数为：{}", borrowNid, failCount);
            if (failCount == 0) {
                repayType = TYPE_WAIT_YES;
                repayStatus = 1;
                repayYesTime = nowTime;
                status = 5;
                // 还款总表
                BorrowRepay borrowRepay = this.getBorrowRepayAsc(borrowNid, apicron);
                borrowRepay.setRepayType(repayType);
                borrowRepay.setRepayStatus(repayStatus); // 已还款
                borrowRepay.setRepayPeriod(isMonth ? periodNext : 1);
                borrowRepay.setRepayActionTime(nowTime);// 实际还款时间
                borrowRepay.setRepayYestime(repayYesTime);// 还款成功最后时间
                if(borrowRepay.getAdvanceStatus() == 30){
                    borrowRepay.setAdvanceStatus(3);// 不分期逾期标的，已还款后重置
                }
                // 更新BorrowRepay
                boolean repayFlag = this.borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay) > 0 ? true : false;
                if (!repayFlag) {
                    throw new Exception("还款记录总表(ht_borrow_repay)更新失败！[借款编号：" + borrowNid + "]");
                }
                Account repayUserAccount = this.getAccount(repayUserId);
                BigDecimal repayAccount = borrowRepay.getRepayAccountYes();
                //BigDecimal repayCapital = borrowRepay.getRepayCapitalYes();
                BigDecimal manageFee = borrowRepay.getRepayFee();
                BigDecimal repayAccountWait = borrowRepay.getRepayAccount();
                BigDecimal repayCapitalWait = borrowRepay.getRepayCapital();
                BigDecimal repayInterestWait = borrowRepay.getRepayInterest();
                // 如果是机构垫付还款
                if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
                    // 更新垫付机构的Account表的待还款金额
                    // 此处为机构垫付临时逻辑
                    Account newRepayUserAccount = new Account();
                    newRepayUserAccount.setUserId(repayUserAccount.getUserId());
                    newRepayUserAccount.setBankTotal(repayAccount.add(manageFee));//add by cwyang 资金总额减少
                    newRepayUserAccount.setBankFrost(repayAccount.add(manageFee));
                    newRepayUserAccount.setBankAwait(BigDecimal.ZERO);
                    newRepayUserAccount.setBankAwaitCapital(BigDecimal.ZERO);
                    newRepayUserAccount.setBankWaitRepay(BigDecimal.ZERO);
                    newRepayUserAccount.setBankFrostCash(repayAccount.add(manageFee));
                    boolean repayUserFlag = this.adminAccountCustomizeMapper.updateOfRepayOrgUser(newRepayUserAccount) > 0 ? true : false;
                    if (!repayUserFlag) {
                        throw new Exception("账户信息表(ht_account)更新失败！[垫付机构用户ID：" + repayUserAccount.getUserId() + "]");
                    }
                    Account borrowUserAccount = new Account();
                    borrowUserAccount.setUserId(borrowUserId);
                    borrowUserAccount.setBankTotal(BigDecimal.ZERO);
                    borrowUserAccount.setBankFrost(BigDecimal.ZERO);
                    borrowUserAccount.setBankWaitRepay(repayAccountWait.add(manageFee));
                    borrowUserAccount.setBankWaitCapital(repayCapitalWait);
                    borrowUserAccount.setBankWaitInterest(repayInterestWait);
                    borrowUserAccount.setBankWaitRepayOrg(BigDecimal.ZERO);
                    borrowUserAccount.setBankFrostCash(BigDecimal.ZERO);
                    boolean borrowUserFlag = this.adminAccountCustomizeMapper.updateOfRepayBorrowUser(borrowUserAccount) > 0 ? true : false;
                    if (!borrowUserFlag) {
                        throw new Exception("账户信息表(ht_account)更新失败！[借款人用户ID：" + borrowUserId + "]");
                    }
                } else {
                    Account newRepayUserAccount = new Account();
                    newRepayUserAccount.setUserId(repayUserId);
                    newRepayUserAccount.setBankTotal(repayAccount.add(manageFee));
                    newRepayUserAccount.setBankFrost(repayAccount.add(manageFee));
                    newRepayUserAccount.setBankWaitRepay(repayAccountWait.add(manageFee));
                    newRepayUserAccount.setBankWaitCapital(repayCapitalWait);
                    newRepayUserAccount.setBankWaitInterest(repayInterestWait);
                    newRepayUserAccount.setBankWaitRepayOrg(BigDecimal.ZERO);
                    newRepayUserAccount.setBankFrostCash(repayAccount.add(manageFee));
                    boolean repayUserFlag = this.adminAccountCustomizeMapper.updateOfRepayBorrowUser(newRepayUserAccount) > 0 ? true : false;
                    if (!repayUserFlag) {
                        throw new Exception("账户信息表(ht_account)更新失败！[借款人用户ID：" + repayUserId + "]");
                    }
                }
                // 插入还款交易明细
                repayUserAccount = this.getAccount(repayUserId);
                // 插入借款人的收支明细表(原复审业务)
                AccountList accountList = new AccountList();
                accountList.setBankAwait(repayUserAccount.getBankAwait());
                accountList.setBankAwaitCapital(repayUserAccount.getBankAwaitCapital());
                accountList.setBankAwaitInterest(repayUserAccount.getBankAwaitInterest());
                accountList.setBankBalance(repayUserAccount.getBankBalance());
                accountList.setBankFrost(repayUserAccount.getBankFrost());
                accountList.setBankInterestSum(repayUserAccount.getBankInterestSum());
                accountList.setBankTotal(repayUserAccount.getBankTotal());
                accountList.setBankWaitCapital(repayUserAccount.getBankWaitCapital());
                accountList.setBankWaitInterest(repayUserAccount.getBankWaitInterest());
                accountList.setBankWaitRepay(repayUserAccount.getBankWaitRepay());
                accountList.setAccountId(repayAccountId);
                accountList.setTxDate(txDate);
                accountList.setTxTime(txTime);
                accountList.setSeqNo(seqNo);
                accountList.setBankSeqNo(bankSeqNo);
                accountList.setCheckStatus(1);
                accountList.setTradeStatus(1);
                accountList.setIsBank(1);
                // 非银行相关
                accountList.setNid(apicronNid); // 交易凭证号生成规则BorrowNid_userid_期数
                accountList.setUserId(repayUserId); // 借款人id
                accountList.setAmount(borrowRepay.getRepayAccountYes().add(borrowRepay.getRepayFee())); // 操作金额
                accountList.setType(2); // 收支类型1收入2支出3冻结
                accountList.setTrade("repay_success"); // 交易类型
                accountList.setTradeCode("balance"); // 操作识别码
                accountList.setTotal(repayUserAccount.getTotal()); // 资金总额
                accountList.setBalance(repayUserAccount.getBalance()); // 可用金额
                accountList.setFrost(repayUserAccount.getFrost()); // 冻结金额
                accountList.setAwait(repayUserAccount.getAwait()); // 待收金额
                accountList.setRepay(repayUserAccount.getRepay()); // 待还金额
                accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作员
                accountList.setRemark(borrowNid);
                accountList.setIp(""); // 操作IP
                boolean repayAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
                if (!repayAccountListFlag) {
                    throw new Exception("收支明细表(ht_account_list)写入失败，[借款人用户ID：" + repayUserId + "]，[借款编号:" + borrowNid + "]");
                }
                // 标的总表信息
                Borrow newBrrow = new Borrow();
                newBrrow.setRepayFullStatus(repayStatus);
                newBrrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
                newBrrow.setStatus(status);
                BorrowExample borrowExample = new BorrowExample();
                borrowExample.createCriteria().andIdEqualTo(borrowId);
                boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBrrow, borrowExample) > 0 ? true : false;
                if (!borrowFlag) {
                    throw new Exception("非分期还款成功后，标的表(ht_borrow)更新失败！[借款编号：" + borrowNid + "]，还款期数：" + periodNow + "]");
                }
                BorrowApicronExample apicronExample = new BorrowApicronExample();
                apicronExample.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
				BorrowApicron newBorrowApiCron = new BorrowApicron();
				newBorrowApiCron.setStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
                boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(newBorrowApiCron, apicronExample) > 0 ? true : false;
                if (!apicronFlag) {
                    throw new Exception("批次还款任务表(ht_borrow_apicron)更新失败！[借款编号：" + borrowNid + "]");
                }
				try {
					this.updateBorrowApicronLog(apicron, CustomConstants.BANK_BATCH_STATUS_SUCCESS);
				} catch (Exception e) {
					logger.error("同步还款任务日志表发生异常！", e);
				}
            }
        }
        if(failCount == 0){
            // add by hsy 优惠券还款请求加入到消息队列 start
            // delete by wgx and hsy 2019/03/13 智投优惠券是在退出计划时发送
            // Map<String, String> params = new HashMap<String, String>();
            // params.put("mqMsgId", GetCode.getRandomCode(10));
            // 借款编号
            // params.put("borrowNid", borrowNid);
            // 当前期
            // params.put("periodNow", String.valueOf(periodNow));

            // 核对参数
            // try {
            //     logger.info("【智投还款/借款人】借款编号:{}，发送优惠券还款延时队列。", borrowNid);
            //     commonProducer.messageSendDelay(new MessageContent(MQConstant.HZT_COUPON_REPAY_TOPIC, UUID.randomUUID().toString(), params), 1);
            // } catch (MQException e) {
            //    logger.error("【智投还款/借款人】发送优惠券还款队列时发生系统异常！", e);
            // }
            // 优惠券还款请求
            // rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_COUPON, RabbitMQConstants.ROUTINGKEY_COUPONREPAY, JSONObject.toJSONString(params));
            // add by hsy 优惠券还款请求加入到消息队列 end
            // insert by zhangjp 增加优惠券还款区分 start
            //CommonSoaUtils.couponRepay(borrowNid, periodNow);
            // insert by zhangjp 增加优惠券还款区分 end
            //add by cwyang 更新智投资产状态
            updatePlanAsset(borrowNid, status);
            //add by cwyang 更新智投相关机构的风险准备金
            //判断是否最后一期
            //String instCode = borrowInfo.getInstCode();
            //modify by cwyang 根据配置好的保证金配置进行保证金回滚 迁移by liushouyi (合规改造删除 2018-12-03)
            //Integer repayCapitalType = borrow.getRepayCapitalType();
            //回滚标识位：0 到期回滚、1 分期回滚、 2 不回滚
            /*if ("0".equals(repayCapitalType.toString())) {
                if (periodNext == 0) {
                    updateInstitutionData(borrow, borrowInfo);
                }
            } else if ("1".equals(repayCapitalType.toString()) && !"10000000".equals(instCode)){
                //分期回滚
                updateInstitutionDataMonth(instCode,repayCapitalWait,borrowNid);
            }*/
            try {
                this.sendSmsForManager(borrowNid);
            } catch (Exception e) {
                logger.error("【智投还款/借款人】发送短信时发生系统异常！", e);
            }
        }else if (failCount == repayCount || apicron.getSucCounts() == 0) {
            // 更新Borrow
            newBorrow.setStatus(status);
            newBorrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_FAIL);
            BorrowExample borrowExample = new BorrowExample();
            borrowExample.createCriteria().andIdEqualTo(borrowId);
            boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
            if (!borrowFlag) {
                throw new Exception("标的表(ht_borrow)更新状态(还款失败)失败，借款编号:" + borrowNid + "]");
            }
            BorrowApicronExample example = new BorrowApicronExample();
            example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
            apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_FAIL);
            apicron.setUpdateTime(new Date());
            boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
            if (!apicronFlag) {
                throw new Exception("批次还款任务表(ht_borrow_apicron)更新状态(还款失败)失败，借款编号:" + borrowNid + "]");
            }
			try {
				this.updateBorrowApicronLog(apicron, CustomConstants.BANK_BATCH_STATUS_FAIL);
			} catch (Exception e) {
				logger.error("同步还款任务日志表发生异常！", e);
			}
            return CustomConstants.BANK_BATCH_STATUS_FAIL;
        } else {
            // 更新Borrow
            newBorrow.setStatus(status);
            newBorrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
            BorrowExample borrowExample = new BorrowExample();
            borrowExample.createCriteria().andIdEqualTo(borrowId);
            boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
            if (!borrowFlag) {
                throw new Exception("标的表(ht_borrow)更新状态(还款失败)失败，借款编号:" + borrowNid + "]");
            }
            BorrowApicronExample example = new BorrowApicronExample();
            example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
            apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
            apicron.setUpdateTime(new Date());
            boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
            if (!apicronFlag) {
                throw new Exception("批次还款任务表(ht_borrow_apicron)更新状态(还款失败)失败，借款编号:" + borrowNid + "]");
            }
			try {
				this.updateBorrowApicronLog(apicron, CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
			} catch (Exception e) {
				logger.error("同步还款任务日志表发生异常！", e);
			}
            return CustomConstants.BANK_BATCH_STATUS_PART_FAIL;
        }
		logger.info("【智投还款/借款人】借款编号：{}，更新借款人的还款数据结束。还款期数：{}", apicron.getBorrowNid(), periodNow);
		return CustomConstants.BANK_BATCH_STATUS_SUCCESS;
	}

	private BigDecimal getRepayPlanAccountSum(String borrowNid, boolean isAllRepay, int lastPeriod) throws Exception {
		BorrowApicronExample apicronExample = new BorrowApicronExample();
		BorrowApicronExample.Criteria criteria = apicronExample.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		if (lastPeriod > 0) {
			logger.info("【智投还款】借款编号：{}，查看多期还款还款任务。还款最后一期：{}", borrowNid, lastPeriod);
			criteria.andLastPeriodEqualTo(lastPeriod);
		} else if (isAllRepay) {
			criteria.andIsAllrepayEqualTo(1);
		} else {
			logger.error("【智投还款】借款编号：{}，既不是一次性还款，也不是多期还款！", borrowNid);
			throw new Exception("查询还款任务表失败，[借款编号:" + borrowNid + "]");
		}
		List<BorrowApicron> apicrons = this.borrowApicronMapper.selectByExample(apicronExample);
		List periodList = new ArrayList();
		for (BorrowApicron apicron: apicrons) {
			periodList.add(apicron.getPeriodNow());
		}
		BorrowRepayPlanExample example = new BorrowRepayPlanExample();
		example.createCriteria().andBorrowNidEqualTo(borrowNid).andRepayPeriodIn(periodList);
		List<BorrowRepayPlan> repayPlans = this.borrowRepayPlanMapper.selectByExample(example);
		BigDecimal sumAccount = new BigDecimal(0);
		if(repayPlans != null && repayPlans.size() >0){
			for (int i = 0; i < repayPlans.size(); i++) {
				sumAccount = sumAccount.add(repayPlans.get(i).getRepayAccountYes().add(repayPlans.get(i).getRepayFee()));
			}
		}
		return sumAccount;
	}

	/**
	 * 判断一次性还款除当前期外是否都已还款成功
	 * 根据还款任务表查询
	 * @return
	 */
    private boolean isLastAllRepay(String borrowNid, Integer periodNow, boolean isAllRepay, int lastPeriod) {
		if (!isAllRepay && lastPeriod == 0) {
			return false;
		}
		BorrowApicronExample example = new BorrowApicronExample();
		BorrowApicronExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andLastPeriodEqualTo(lastPeriod);
		criteria.andApiTypeEqualTo(1);
		criteria.andPeriodNowNotEqualTo(periodNow);
		criteria.andStatusNotEqualTo(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
		int borrowApicronCount = this.borrowApicronMapper.countByExample(example);
		if(borrowApicronCount > 0){
			return false;
		}
        logger.info("【智投还款】借款编号：{}，标的表(ht_borrow)可以更新为还款成功。一次性还款当前更新期数：{}", borrowNid, periodNow);
		return true;
	}

	/**
	 * 判断一次性还款债转除当前期外是否都已还款成功
	 * 根据债转还款表来查询
	 * @return
	 */
	private boolean isLastAllCreditRepay(String borrowNid, Integer periodNow, HjhDebtCreditRepay creditRepay, boolean isAllRepay) {
		if (!isAllRepay) {
			return false;
		}
		HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
		HjhDebtCreditRepayExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andRepayStatusNotEqualTo(1);
		criteria.andRepayPeriodNotEqualTo(periodNow);
		criteria.andUserIdEqualTo(creditRepay.getUserId());
		criteria.andAssignOrderIdEqualTo(creditRepay.getAssignOrderId());
		criteria.andDelFlagEqualTo(0);
		int borrowRecoverPlanCount = this.hjhDebtCreditRepayMapper.countByExample(example);
		if(borrowRecoverPlanCount > 0){
			return false;
		}
		logger.info("【智投还款】借款编号：{}，债转出借表(ht_hjh_debt_credit_tender)可以更新为已还款。一次性还款当前更新期数：{}", borrowNid, periodNow);
		return true;
	}

	/**
	 * 判断一次性还款除当前期外是否都已还款成功
	 * 根据放款分期表来查询
	 * @return
	 */
	private boolean isLastAllRepay(String borrowNid, Integer periodNow, Integer userId, String tenderOrderId, boolean isAllRepay) {
		if (!isAllRepay) {
			return false;
		}
		BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
		BorrowRecoverPlanExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andRecoverStatusNotEqualTo(1);
		criteria.andRecoverPeriodNotEqualTo(periodNow);
		criteria.andUserIdEqualTo(userId);
		criteria.andNidEqualTo(tenderOrderId);
		int borrowRecoverPlanCount = this.borrowRecoverPlanMapper.countByExample(example);
		if(borrowRecoverPlanCount > 0){
			return false;
		}
		logger.info("【智投还款】借款编号：{}，放款记录总表(ht_borrow_recover)可以更新为还款成功。一次性还款当前更新期数：{}", borrowNid, periodNow);
		return true;
	}

	/**
	 * 变更资产表对应状态
	 * @param borrowNid
	 * @param status
	 */
	private void updatePlanAsset(String borrowNid, int status) {
		int assetStatus = 0;
		//判断还款是否分期
		if (4 == status){//分期还款标的
			assetStatus = 11;//还款中
		}else if(5 == status){
			assetStatus = 12;//还款完成
		}
		HjhPlanAssetExample example = new HjhPlanAssetExample();
		example.createCriteria().andBorrowNidEqualTo(borrowNid);
		List<HjhPlanAsset> planAssetList = this.hjhPlanAssetMapper.selectByExample(example);
		if (planAssetList != null && planAssetList.size() > 0) {
			HjhPlanAsset hjhPlanAsset = planAssetList.get(0);
			hjhPlanAsset.setStatus(assetStatus);//已还款
			int count = this.hjhPlanAssetMapper.updateByPrimaryKey(hjhPlanAsset);
			if (count > 0) {
				logger.info("====================cwyang 变更对应资产表状态完成!资产标的号:" + borrowNid);
			}
		}
	}

	private void sendSmsForManager(String borrowNid) {
		// 发送成功短信
		Map<String, String> replaceStrs = new HashMap<String, String>();
		replaceStrs.put("val_title", borrowNid);
		replaceStrs.put("val_time", GetDate.formatTime());
		SmsMessage smsMessage =
                new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, null,
                		CustomConstants.PARAM_TPL_HUANKUAN_SUCCESS, CustomConstants.CHANNEL_TYPE_NORMAL);
        try {
			commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
        } catch (MQException e2) {
			logger.error("【智投还款】发送短信失败..", e2);
        }
	}

	/**
	 * 更新相应的还款信息总表
	 * 
	 * @param borrowRecover
	 * @return
	 */
	private boolean updateBorrowRecover(BorrowRecover borrowRecover) {
		boolean flag = this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover) > 0 ? true : false;
		return flag;
	}

	/**
	 * 查询相应的还款详情
	 * 
	 * @param apicron
	 * @return
	 */
	private List<BankCallBean> queryBatchDetails(BorrowApicron apicron) {

		int txCounts = apicron.getTxCounts();// 总交易笔数
		String batchTxDate = String.valueOf(apicron.getTxDate());
		String batchNo = apicron.getBatchNo();// 批次号
		int pageSize = 50;// 每页笔数
		int pageTotal = txCounts / pageSize + 1;// 总页数
		List<BankCallBean> results = new ArrayList<BankCallBean>();
		String channel = BankCallConstant.CHANNEL_PC;
		logger.info("【智投还款】借款编号：{}，进行批次明细查询。批次号：{}，页数：{}，总数：{}", apicron.getBorrowNid(), batchNo, pageTotal, txCounts);
		for (int i = 1; i <= pageTotal; i++) {
			// 循环三次查询结果
			for (int j = 0; j < 3; j++) {
				String logOrderId = GetOrderIdUtils.getOrderId2(apicron.getUserId());
				String orderDate = GetOrderIdUtils.getOrderDate();
				String txDate = GetOrderIdUtils.getTxDate();
				String txTime = GetOrderIdUtils.getTxTime();
				String seqNo = GetOrderIdUtils.getSeqNo(6);
				BankCallBean repayBean = new BankCallBean();
				repayBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
				repayBean.setTxCode(BankCallConstant.TXCODE_BATCH_DETAILS_QUERY);// 消息类型(批量还款)
				repayBean.setTxDate(txDate);
				repayBean.setTxTime(txTime);
				repayBean.setSeqNo(seqNo);
				repayBean.setChannel(channel);
				repayBean.setBatchTxDate(batchTxDate);
				repayBean.setBatchNo(batchNo);
				repayBean.setType(BankCallConstant.DEBT_BATCH_TYPE_ALL);
				repayBean.setPageNum(String.valueOf(i));
				repayBean.setPageSize(String.valueOf(pageSize));
				repayBean.setLogUserId(String.valueOf(apicron.getUserId()));
				repayBean.setLogOrderId(logOrderId);
				repayBean.setLogOrderDate(orderDate);
				repayBean.setLogRemark("查询批次交易明细！");
				repayBean.setLogClient(0);
				// 调用还款接口
				BankCallBean repayResult = BankCallUtils.callApiBg(repayBean);
				if (Validator.isNotNull(repayResult)) {
					String retCode = StringUtils.isNotBlank(repayResult.getRetCode()) ? repayResult.getRetCode() : "";
					logger.info("【智投还款】借款编号：{}，第{}页批次明细查询完成。批次号：{}，返回码：{}", apicron.getBorrowNid(), i, batchNo, retCode);
					if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
						results.add(repayResult);
						break;
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
		}
		return results;
	}

	/**
	 * 取得未还款的放款明细列表
	 *
	 * @return
	 */
	@Override
	public List<BorrowRecover> getBorrowRecoverList(String borrowNid,BorrowApicron apicron) {
		BorrowRecoverExample example = new BorrowRecoverExample();
		BorrowRecoverExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		// 一次性还款的情况，因为最后一期会更新导致前期更新不到位 by dxj&wanggongxi
		if(apicron.getIsAllrepay().intValue() == 0) {
			criteria.andRecoverStatusNotEqualTo(1); // 0初始 1还款成功 2还款失败
		}
		example.setOrderByClause(" id asc ");
		List<BorrowRecover> list = this.borrowRecoverMapper.selectByExample(example);
		return list;
	}

	/**
	 * 取得借款列表
	 *
	 * @return
	 */
	private BorrowTender getBorrowTender(String tenderOrderId) {
		BorrowTenderExample example = new BorrowTenderExample();
		example.createCriteria().andNidEqualTo(tenderOrderId);
		List<BorrowTender> borrowTenders = this.borrowTenderMapper.selectByExample(example);
		if (borrowTenders != null && borrowTenders.size() == 1) {
			return borrowTenders.get(0);
		}
		return null;
	}

	/**
	 * 判断出借人收支明细是否存在
	 *
	 * @param nid
	 * @return
	 */
	private boolean countAccountListByNid(String nid) {
		AccountListExample accountListExample = new AccountListExample();
		List<String> tradeList = new ArrayList();
		tradeList.add("hjh_repay_frost");
		tradeList.add("hjh_repay_balance");
		accountListExample.createCriteria().andNidEqualTo(nid).andTradeIn(tradeList);
		return this.accountListMapper.countByExample(accountListExample) > 0 ? true : false;
	}

	/**
	 * 判断承接人收支明细是否存在
	 *
	 * @return
	 */
	private boolean countCreditAccountListByNid(String nid) {
		AccountListExample accountListExample = new AccountListExample();
		List<String> tradeList = new ArrayList();
		tradeList.add("credit_tender_recover_forst");
		tradeList.add("credit_tender_recover_yes");
		accountListExample.createCriteria().andNidEqualTo(nid).andTradeIn(tradeList);
		return this.accountListMapper.countByExample(accountListExample) > 0 ? true : false;
	}

	/**
	 * 取得总的还款计划表
	 *
	 * @param borrowNid
	 * @param period
	 * @return
	 */
	private BorrowRepayPlan getBorrowRepayPlan(String borrowNid, Integer period) {
		BorrowRepayPlanExample example = new BorrowRepayPlanExample();
		example.createCriteria().andBorrowNidEqualTo(borrowNid).andRepayPeriodEqualTo(period);
		List<BorrowRepayPlan> list = this.borrowRepayPlanMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 取得还款信息
	 *
	 * @return
	 */
	private BorrowRepay getBorrowRepayAsc(String borrowNid, BorrowApicron apicron) {
		BorrowRepayExample example = new BorrowRepayExample();
		BorrowRepayExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		// 一次性还款的情况，因为最后一期会更新导致前期更新不到位 by dxj&wanggongxi
		if(apicron.getIsAllrepay().intValue() == 0) {
			criteria.andRepayStatusEqualTo(0); // 0初始 1还款成功 2还款失败
		}
		example.setOrderByClause(" id asc ");
		List<BorrowRepay> list = this.borrowRepayMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 批次代偿（合规要求）
	 * @author wgx
	 * @date 2018/10/18
	 */
	private Map requestOrgRepay(BorrowApicron apicron, JSONArray subPacksJson) {
		Map map = new HashMap<>();
		int userId = apicron.getUserId();// 还款用户userId
		String borrowNid = apicron.getBorrowNid();// 借款编号
		// 还款子参数
		String subPacks = subPacksJson.toJSONString();
		// 获取共同参数
		String notifyUrl = systemConfig.getRepayVerifyUrl();
		String retNotifyURL = systemConfig.getRepayResultUrl();
		String channel = BankCallConstant.CHANNEL_PC;
		// 只请求一次银行接口，请求返回异常任务表更新状态为还款请求失败(7)，由batch进行异常处理 update by wgx 2019/03/15
        try {
            String logOrderId = GetOrderIdUtils.getOrderId2(apicron.getUserId());
            String orderDate = GetOrderIdUtils.getOrderDate();
            String batchNo = GetOrderIdUtils.getBatchNo();// 获取还款批次号
            String txDate = GetOrderIdUtils.getTxDate();
            String txTime = GetOrderIdUtils.getTxTime();
            String seqNo = GetOrderIdUtils.getSeqNo(6);
            try{
				apicron.setBatchNo(batchNo);
				apicron.setTxDate(Integer.parseInt(txDate));
				apicron.setTxTime(Integer.parseInt(txTime));
				apicron.setSeqNo(Integer.parseInt(seqNo));
				apicron.setBankSeqNo(txDate + txTime + seqNo);
				// 更新任务API状态为进行中
				boolean apicronFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_SENDING);
				if (!apicronFlag) {
					throw new Exception("更新请求银行信息失败！[还款用户ID：" + userId + "]，[借款编号：" + borrowNid + "]");
				}
			} catch (Exception e) {
				logger.error("【智投批次代偿】还款请求时发生系统异常！", e);
				map.put("result", null);// 请求失败
				map.put("delFlag", false);// 未请求银行&更新任务表失败，删除防重redis
				return map;
			}
            // 调用还款接口
            BankCallBean repayBean = new BankCallBean();
            repayBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
            repayBean.setTxCode(BankCallConstant.TXCODE_BATCH_SUBST_REPAY);// 消息类型(批次代偿)
            repayBean.setTxDate(txDate);
            repayBean.setTxTime(txTime);
            repayBean.setSeqNo(seqNo);
            repayBean.setChannel(channel);
            repayBean.setBatchNo(apicron.getBatchNo());// 批次号
            repayBean.setTxAmount(String.valueOf(apicron.getTxAmount()));// 交易金额
            repayBean.setTxCounts(String.valueOf(apicron.getTxCounts()));// 交易笔数
            repayBean.setNotifyURL(notifyUrl);// 后台通知连接
            repayBean.setRetNotifyURL(retNotifyURL);// 业务结果通知
            repayBean.setSubPacks(subPacks);// 请求数组
            repayBean.setLogUserId(String.valueOf(userId));
            repayBean.setLogOrderId(logOrderId);
            repayBean.setLogOrderDate(orderDate);
            repayBean.setLogRemark("批次还款请求");
            repayBean.setLogClient(0);
            BankCallBean repayResult = BankCallUtils.callApiBg(repayBean);
            if (Validator.isNotNull(repayResult)) {
                String received = StringUtils.isNotBlank(repayResult.getReceived()) ? repayResult.getReceived() : "";
                if (BankCallConstant.RECEIVED_SUCCESS.equals(received)) {
                    try {
                        // 更新任务API状态
                        boolean apicronResultFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_SENDED);
                        if (apicronResultFlag) {
                            map.put("result", repayResult);
                            map.put("delFlag", false);// 请求银行返回成功&更新任务表成功，删除防重redis
                            return map;
                        }
                    } catch (Exception e) {
                        logger.error("【智投批次代偿】还款请求成功后,更新任务状态(还款请求成功)异常！还款用户ID：{}，", userId, e);
                    }
                    map.put("result", repayResult);
                    map.put("delFlag", true);// 请求银行返回成功&更新任务表失败，不删除防重redis
                    return map;
                }
				map.put("result", null);// 请求失败
				map.put("delFlag", false);// 请求银行返回失败，删除防重redis
				return map;
            }
        } catch (Exception e) {
			logger.error("【智投批次代偿】还款请求银行时发生系统异常！", e);
        }
		// update by wgx 2019/03/18
		map.put("result", null);// 请求失败
		map.put("delFlag", true);// 请求银行返回空｜请求银行返回异常，不删除防重redis
		return map;
	}

	/**
	 * 调用机构垫付还款
	 * @author wgx
	 * @date 2018/10/18
	 */
	private Map requestOrgRepay(Borrow borrow, String orgAccountId, BorrowApicron apicron, List<BorrowRecover> borrowRecoverList, List<BorrowRecoverPlan> borrowRecoverPlanList,
								List<HjhDebtCreditRepay> creditRepayList) throws Exception {
		String borrowNid = borrow.getBorrowNid();
		boolean delFlag = false;
		Map map = new HashMap<>();
		int txCounts = 0;// 交易笔数
		BigDecimal txAmountSum = BigDecimal.ZERO;// 交易总金额
		BigDecimal repayAmountSum = BigDecimal.ZERO;// 交易总金额
		BigDecimal serviceFeeSum = BigDecimal.ZERO;// 交易总服务费
		JSONArray subPacksJson = new JSONArray();// 拼接结果
		// 债转还款结果
		if (creditRepayList != null && creditRepayList.size() > 0) {
			// 遍历债权还款记录
			for (int i = 0; i < creditRepayList.size(); i++) {
				// 还款信息
				HjhDebtCreditRepay creditRepay = creditRepayList.get(i);
				int assignUserId = creditRepay.getUserId();// 承接用户userId
				String creditRepayOrderId = creditRepay.getCreditRepayOrderId();// 债转还款订单号
				BigDecimal txAmount = creditRepay.getRepayCapital();// 交易金额
				BigDecimal intAmount = creditRepay.getRepayInterest().add(creditRepay.getRepayAdvanceInterest()).add(creditRepay.getRepayDelayInterest()).add(creditRepay.getRepayLateInterest());// 交易利息
				BigDecimal serviceFee = creditRepay.getManageFee();// 还款管理费
				logger.info("【智投批次代偿】借款编号：{}，任务表变更金额。增加金额：{}+{}+{}，原始金额：{}，债转订单号：{}", borrowNid, txAmount, intAmount, serviceFee, repayAmountSum, creditRepay.getCreditNid());
				repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
				txAmountSum = txAmountSum.add(txAmount);// 交易总金额
				serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
				String authCode = creditRepay.getAuthCode();// 出借授权码
				// 承接用户的开户信息
				Account bankOpenAccount = this.getAccount(assignUserId);
				// 判断承接用户开户信息
				if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
					JSONObject subJson = getOrgSubJson(orgAccountId, borrowNid, creditRepayOrderId, txAmount, intAmount, serviceFee, authCode, bankOpenAccount);
					subPacksJson.add(subJson);
				} else {
					throw new Exception("未查询到承接人的银行开户信息！[用户ID：" + assignUserId + "]");
				}
			}
			txCounts = txCounts + creditRepayList.size();
		}
		// 判断标的的放款信息
		if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
			// 遍历标的放款信息
			for (int i = 0; i < borrowRecoverList.size(); i++) {
				// 获取不分期的放款信息
				BorrowRecover borrowRecover = borrowRecoverList.get(i);
				int tenderUserId = borrowRecover.getUserId();// 出借用户userId
				String recoverRepayOrderId = borrowRecover.getRepayOrdid();// 还款订单号
				BigDecimal txAmount = borrowRecover.getRecoverCapitalWait();// 交易金额
				BigDecimal intAmount = borrowRecover.getRecoverInterestWait().add(borrowRecover.getChargeInterest()).add(borrowRecover.getDelayInterest()).add(borrowRecover.getLateInterest());// 交易利息
				BigDecimal serviceFee = borrowRecover.getRecoverFee();// 还款管理费
				logger.info("【智投批次代偿】借款编号：{}，任务表变更金额。增加金额：{}+{}+{}，原始金额：{}，债转订单号：{}", borrowNid, txAmount, intAmount, serviceFee, repayAmountSum, borrowRecover.getNid());
				txAmountSum = txAmountSum.add(txAmount);// 交易总金额
				repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
				serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
				String authCode = borrowRecover.getAuthCode();// 出借授权码
				// 出借用户的开户信息
				Account bankOpenAccount = this.getAccount(tenderUserId);
				// 判断出借用户开户信息
				if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
					JSONObject subJson = getOrgSubJson(orgAccountId, borrowNid, recoverRepayOrderId, txAmount, intAmount, serviceFee, authCode, bankOpenAccount);
					subPacksJson.add(subJson);
				} else {
					throw new Exception("未查询到出借人的银行开户信息！[用户ID：" + tenderUserId + "]");
				}
			}
			txCounts = txCounts + borrowRecoverList.size();
		} else {
			if (borrowRecoverPlanList != null && borrowRecoverPlanList.size() > 0) {
				for (int i = 0; i < borrowRecoverPlanList.size(); i++) {
					// 获取分期的放款信息
					BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlanList.get(i);
					int tenderUserId = borrowRecoverPlan.getUserId();// 出借用户userId
					String recoverPlanRepayOrderId = borrowRecoverPlan.getRepayOrderId();
					BigDecimal txAmount = borrowRecoverPlan.getRecoverCapitalWait();// 交易金额
					BigDecimal intAmount = borrowRecoverPlan.getRecoverInterestWait().add(borrowRecoverPlan.getChargeInterest()).add(borrowRecoverPlan.getDelayInterest())
							.add(borrowRecoverPlan.getLateInterest());// 交易利息
					BigDecimal serviceFee = borrowRecoverPlan.getRecoverFee();// 还款管理费
					logger.info("【智投批次代偿】借款编号：{}，任务表变更金额。增加金额：{}+{}+{}，原始金额：{}，债转订单号：{}", borrowNid, txAmount, intAmount, serviceFee, repayAmountSum, borrowRecoverPlan.getNid());
					repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
					txAmountSum = txAmountSum.add(txAmount);// 交易总金额
					serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
					String authCode = borrowRecoverPlan.getAuthCode();// 出借授权码
					// 出借用户的开户信息
					Account bankOpenAccount = this.getAccount(tenderUserId);
					// 判断出借用户开户信息
					if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
						JSONObject subJson = getOrgSubJson(orgAccountId, borrowNid, recoverPlanRepayOrderId, txAmount, intAmount, serviceFee, authCode, bankOpenAccount);
						subPacksJson.add(subJson);
					} else {
						throw new Exception("未查询到出借人的银行开户信息！[用户ID：" + tenderUserId + "]");
					}
				}
				txCounts = txCounts + borrowRecoverPlanList.size();
			}
		}
		// 拼接相应的还款参数
		if (apicron.getFailTimes() == 0) {
			apicron.setBatchAmount(repayAmountSum);
			apicron.setBatchCounts(txCounts);
			apicron.setBatchServiceFee(serviceFeeSum);
			apicron.setSucAmount(BigDecimal.ZERO);
			apicron.setSucCounts(0);
			apicron.setFailAmount(repayAmountSum);
			apicron.setFailCounts(txCounts);
		}
		apicron.setServiceFee(serviceFeeSum);
		apicron.setTxAmount(txAmountSum);
		apicron.setTxCounts(txCounts);
		apicron.setData(" ");
		Map resultMap = this.requestOrgRepay(apicron, subPacksJson);
		BankCallBean repayResult = (BankCallBean) resultMap.get("result");
		delFlag = (boolean) resultMap.get("delFlag");
		try {
			if (Validator.isNotNull(repayResult)) {
				String received = repayResult.getReceived();
				if (StringUtils.isNotBlank(received)) {
					if (BankCallConstant.RECEIVED_SUCCESS.equals(received)) {
						map.put("result", repayResult);
						map.put("delFlag", delFlag);
						return map;
					} else {
						throw new Exception("批次代偿失败！[返回结果：" + received + "]，[用户ID：" + apicron.getUserId() + "]，[借款编号：" + borrowNid + "]");
					}
				} else {
					throw new Exception("批次代偿返回结果为空！[用户ID：" + apicron.getUserId() + "]，[借款编号：" + borrowNid + "]");
				}
			} else {
				throw new Exception("批次代偿请求失败！[用户ID：" + apicron.getUserId() + "]，[借款编号：" + borrowNid + "]");
			}
		} catch (Exception e) {
			logger.error("【智投批次代偿】还款请求失败！", e);
		}
		map.put("result", repayResult);
		map.put("delFlag", delFlag);
		return map;
	}
	/**
	 * 批次代偿请求数组
	 * @author wgx
	 * @date 2018/10/18
	 */
	private JSONObject getOrgSubJson(String accountId, String borrowNid, String orderId, BigDecimal txAmount, BigDecimal intAmount, BigDecimal serviceFee, String authCode, Account bankOpenAccount) {
		JSONObject subJson = new JSONObject();
		String forAccountId = bankOpenAccount.getAccountId();// 银行账户
		subJson.put(BankCallConstant.PARAM_ACCOUNTID, accountId);           // 存管子账户             accountId    担保户
		subJson.put(BankCallConstant.PARAM_ORDERID, orderId);               // 订单号                 orderId      由P2P生成，必须保证唯一
		subJson.put(BankCallConstant.PARAM_TXAMOUNT, txAmount.toString());  // 交易金额               txAmount     代偿本金
		subJson.put(BankCallConstant.PARAM_RISKAMOUNT, "");                 // 风险准备金             riskAmount   暂不做使用，请送空
		subJson.put(BankCallConstant.PARAM_INTAMOUNT, intAmount.toString());// 交易利息               intAmount
		subJson.put(BankCallConstant.PARAM_TXFEEIN, "0.00");                // 收款手续费             txFeeIn      向出借人收取的手续费
		subJson.put(BankCallConstant.PARAM_FINEAMOUNT, "");                 // 罚息金额               fineAmount   暂不做使用，请送空
		subJson.put(BankCallConstant.PARAM_FORACCOUNTID, forAccountId);     // 对手存管子账户         forAccountId 出借人账号
		subJson.put(BankCallConstant.PARAM_PRODUCTID, borrowNid);           // 标的号                 productId    出借人投标成功的标的号
		subJson.put(BankCallConstant.PARAM_AUTHCODE, authCode);             // 授权码                 authCode     出借人投标成功的授权号
		subJson.put(BankCallConstant.PARAM_SEQFLAG, "");                    // 流水是否经过借款人标志 seqFlag      担保人资金是否过借款人标志。1：经过;空：不经过；
		subJson.put(BankCallConstant.PARAM_TXFEEOUT, serviceFee.toString());// 转出方手续费金额       txFeeOut     扣收担保人手续费
		return subJson;
	}
}
