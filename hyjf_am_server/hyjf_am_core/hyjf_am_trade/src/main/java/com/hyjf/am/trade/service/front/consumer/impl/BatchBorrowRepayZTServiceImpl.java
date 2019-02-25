/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.consumer.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.consumer.BatchBorrowRepayZTService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author dxj
 * @version BatchBorrowRepayZTServiceImpl.java, v0.1 2018年6月23日 上午10:09:12
 */
@Service
public class BatchBorrowRepayZTServiceImpl extends BaseServiceImpl implements BatchBorrowRepayZTService {

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

	@Override
	public Map requestRepay(BorrowApicron apicron) {

		int repayUserId = apicron.getUserId();// 还款用户userId
		String borrowNid = apicron.getBorrowNid();// 借款编号
		Integer periodNow = apicron.getPeriodNow();// 当前期数
		int isApicronRepayOrgFlag = Validator.isNull(apicron.getIsRepayOrgFlag()) ? 0 : apicron.getIsRepayOrgFlag();// 是否是担保机构还款
		// 取得借款人账户信息
		Account repayAccount = this.getAccountByUserId(repayUserId);
		if (repayAccount == null || repayAccount.getAccountId() == null) {
			throw new RuntimeException("借款人账户不存在！[用户ID：" + repayUserId + "]，[借款编号：" + borrowNid + "]");
		}
		String repayAccountId = repayAccount.getAccountId();// 借款人相应的银行账号
		// 取得借款详情
		Borrow borrow = this.getBorrowByNid(borrowNid);
		// 取得借款详情
		BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
		if (borrow == null || borrowInfo == null) {
			throw new RuntimeException("借款详情不存在！[借款人ID：" + repayUserId + "]，[借款编号：" + borrowNid + "]");
		}
		String borrowStyle = borrow.getBorrowStyle();
		// 标的是否可用担保机构还款
		int isRepayOrgFlag = Validator.isNull(borrowInfo.getIsRepayOrgFlag()) ? 0 : borrowInfo.getIsRepayOrgFlag();
		// 是否分期(true:分期, false:不分期)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// 取得放款记录列表
		List<BorrowRecover> listRecovers = this.getBorrowRecoverList(borrowNid, apicron);
		// 是否有待还款记录
		if (listRecovers == null || listRecovers.size() == 0) {
			throw new RuntimeException("待还款记录不存在！[借款编号：" + borrowNid + "]，[期数：" + periodNow + "]");
		}
		//是否先息后本
		boolean isEndMonth = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// 未分期还款请求列表
		List<BorrowRecover> recoverList = new ArrayList<BorrowRecover>();
		// 分期还款请求列表
		List<BorrowRecoverPlan> recoverPlanList = new ArrayList<BorrowRecoverPlan>();
		// 债转还款请求列表
		List<CreditRepay> creditRepayList = new ArrayList<CreditRepay>();
		try {
			/** 循环出借详情列表 */
			for (int i = 0; i < listRecovers.size(); i++) {
				// 获取还款信息
				BorrowRecover borrowRecover = listRecovers.get(i);
				// 出借订单号
				String tenderOrderId = borrowRecover.getNid();
				// 分期的还款信息
				BorrowRecoverPlan borrowRecoverPlan = null;
				if (isMonth) {
					// 取得放款记录分期数据
					borrowRecoverPlan = this.getBorrowRecoverPlan(borrowNid, periodNow, borrowRecover.getUserId(), tenderOrderId);
					if (Validator.isNull(borrowRecoverPlan)) {
						throw new RuntimeException("放款记录分期数据不存在！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]，[期数：" + periodNow + "]");
					}
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
					
				} else {// endday: 按天计息, end:按月计息
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
					List<CreditRepay> subCreditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, periodNow);
					if (subCreditRepayList != null && subCreditRepayList.size() > 0) {
						for (int j = 0; j < subCreditRepayList.size(); j++) {
							CreditRepay creditRepay = subCreditRepayList.get(j);
							if (StringUtils.isBlank(creditRepay.getCreditRepayOrderId())) {
								// 设置还款订单号
								creditRepay.setCreditRepayOrderId(GetOrderIdUtils.getOrderId2(creditRepay.getUserId()));
								// 设置还款时间
								creditRepay.setCreditRepayOrderDate(GetOrderIdUtils.getOrderDate());
								// 更新还款信息
								boolean flag = this.creditRepayMapper.updateByPrimaryKeySelective(creditRepay) > 0 ? true : false;
								if (!flag) {
									throw new RuntimeException("债转还款表(ht_credit_repay)更新还款订单号失败！[承接订单号：" + creditRepay.getAssignNid() + "]，[出借订单号：" + tenderOrderId + "]");
								}
							}
							if (isMonth && Validator.isNotNull(borrowRecoverPlan)) {
								borrowRecoverPlan.setRecoverInterestWait(borrowRecoverPlan.getRecoverInterestWait().subtract(creditRepay.getAssignInterest()));
								borrowRecoverPlan.setRecoverCapitalWait(borrowRecoverPlan.getRecoverCapitalWait().subtract(creditRepay.getAssignCapital()));
								borrowRecoverPlan.setRecoverAccountWait(borrowRecoverPlan.getRecoverAccountWait().subtract(creditRepay.getAssignAccount()));
								borrowRecoverPlan.setRecoverFee(borrowRecoverPlan.getRecoverFee().subtract(creditRepay.getManageFee()));
//								if(!isEndMonth){   modify by yangchangwei 提前还款变更先息后本不区分
									borrowRecoverPlan.setChargeInterest(borrowRecoverPlan.getChargeInterest().subtract(creditRepay.getChargeInterest()));
//								}
								borrowRecoverPlan.setDelayInterest(borrowRecoverPlan.getDelayInterest().subtract(creditRepay.getDelayInterest()));
								borrowRecoverPlan.setLateInterest(borrowRecoverPlan.getLateInterest().subtract(creditRepay.getLateInterest()));
							} else {
								borrowRecover.setRecoverInterestWait(borrowRecover.getRecoverInterestWait().subtract(creditRepay.getAssignInterest()));
								borrowRecover.setRecoverCapitalWait(borrowRecover.getRecoverCapitalWait().subtract(creditRepay.getAssignCapital()));
								borrowRecover.setRecoverAccountWait(borrowRecover.getRecoverAccountWait().subtract(creditRepay.getAssignAccount()));
								borrowRecover.setRecoverFee(borrowRecover.getRecoverFee().subtract(creditRepay.getManageFee()));
								borrowRecover.setChargeInterest(borrowRecover.getChargeInterest().subtract(creditRepay.getChargeInterest()));
								borrowRecover.setDelayInterest(borrowRecover.getDelayInterest().subtract(creditRepay.getDelayInterest()));
								borrowRecover.setLateInterest(borrowRecover.getLateInterest().subtract(creditRepay.getLateInterest()));
							}
							creditRepayList.add(creditRepay);
						}
						// 如果是完全承接，不添加相应的出让人还款记录
						//  判断是否完全承接 add by cwyang 
						boolean overFlag = isOverUndertake(borrowRecover,isMonth);
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
			// 调用相应的批次还款接口
            Map resultMap = null;
			// 如果是垫付机构还款
			if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
				// 调用相应的担保机构还款接口
				logger.info("【直投还款请求】借款编号：{}，批次代偿接口请求开始！", borrowNid);
				if (isMonth) {
					resultMap = this.requestOrgRepay(borrow, repayAccountId, apicron, null, recoverPlanList, creditRepayList);
				}else{
					resultMap = this.requestOrgRepay(borrow, repayAccountId, apicron, recoverList, null, creditRepayList);
				}
			} else {
				// 调用相应的非担保机构还款接口
				logger.info("【直投还款请求】借款编号：{}，批次还款接口请求开始！", borrowNid);
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
			logger.error("【直投还款请求】还款请求时发生系统异常！", e);
			throw new RuntimeException();
		}
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
		repayBean.setLogRemark("直投还款批次状态查询");
		repayBean.setLogClient(0);
		BankCallBean queryResult = BankCallUtils.callApiBg(repayBean);
		if (queryResult != null && StringUtils.isNotBlank(queryResult.getRetCode())) {
			String retCode = queryResult.getRetCode();
			logger.info("【直投还款】借款编号：{}，批次状态查询处理状态：{}", apicron.getBorrowNid(), retCode);
			if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
				return queryResult;
			}
			logger.info("【直投还款】借款编号：{}，批次状态查询失败描述：{}", apicron.getBorrowNid(), queryResult.getRetMsg());
		}
		return null;
	}
	
	/**
	 * 根据主键从主库查询ht_borrow_apicron表
	 * 这里不加select是想直接从主库查询
	 * @param id
	 * @return
	 */
	@Override
	public BorrowApicron selApiCronByPrimaryKey(int id) {
		return borrowApicronMapper.selectByPrimaryKey(id);
	}

	/**
	 * 非担保机构还款
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
	private Map requestRepay(Borrow borrow, String borrowAccountId, BorrowApicron apicron, List<BorrowRecover> borrowRecoverList, List<BorrowRecoverPlan> borrowRecoverPlanList,
			List<CreditRepay> creditRepayList) throws Exception {
		String borrowNid = borrow.getBorrowNid();
		boolean delFlag = false;
		// 还款请求组装数据
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
				CreditRepay creditRepay = creditRepayList.get(i);
				int assignUserId = creditRepay.getUserId();// 承接用户userId
				String creditRepayOrderId = creditRepay.getCreditRepayOrderId();// 债转还款订单号
				BigDecimal txAmount = creditRepay.getAssignCapital();// 交易金额
				BigDecimal intAmount = creditRepay.getAssignInterest().add(creditRepay.getChargeInterest()).add(creditRepay.getDelayInterest()).add(creditRepay.getLateInterest());// 交易利息
				BigDecimal serviceFee = creditRepay.getManageFee();// 还款管理费
				repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
				txAmountSum = txAmountSum.add(txAmount);// 交易总金额
				serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
				String authCode = creditRepay.getAuthCode();// 出借授权码
				// 承接用户的开户信息
				Account bankOpenAccount = this.getAccountByUserId(assignUserId);
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
				txAmountSum = txAmountSum.add(txAmount);// 交易总金额
				repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
				serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
				String authCode = borrowRecover.getAuthCode();// 出借授权码
				// 出借用户的开户信息
				Account bankOpenAccount = this.getAccountByUserId(tenderUserId);
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
					repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
					txAmountSum = txAmountSum.add(txAmount);// 交易总金额
					serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
					String authCode = borrowRecoverPlan.getAuthCode();// 出借授权码
					// 出借用户的开户信息
					Account bankOpenAccount = this.getAccountByUserId(tenderUserId);
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
			logger.error("【直投还款请求】还款请求发生系统异常！", e);
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
	 * 批次还款请求
	 *
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map requestRepay(BorrowApicron apicron, JSONArray subPacksJson) {
		Map map = new HashMap<>();
		boolean delFalg = false;
		int userId = apicron.getUserId();// 还款用户userId
		String borrowNid = apicron.getBorrowNid();// 借款编号
		// 还款子参数
		String subPacks = subPacksJson.toJSONString();
		// 获取共同参数
		String notifyUrl = systemConfig.getRepayVerifyUrl();
		String retNotifyURL = systemConfig.getRepayResultUrl();
		
		String channel = BankCallConstant.CHANNEL_PC;
		for (int i = 0; i < 3; i++) {
			try {
				String logOrderId = GetOrderIdUtils.getOrderId2(apicron.getUserId());
				String orderDate = GetOrderIdUtils.getOrderDate();
				String batchNo = GetOrderIdUtils.getBatchNo();// 获取还款批次号
				String txDate = GetOrderIdUtils.getTxDate();
				String txTime = GetOrderIdUtils.getTxTime();
				String seqNo = GetOrderIdUtils.getSeqNo(6);
				apicron.setBatchNo(batchNo);
				apicron.setTxDate(Integer.parseInt(txDate));
				apicron.setTxTime(Integer.parseInt(txTime));
				apicron.setSeqNo(Integer.parseInt(seqNo));
				apicron.setBankSeqNo(txDate + txTime + seqNo);
				// 更新任务API状态为进行中
				boolean apicronFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_SENDING);
				if (!apicronFlag) {
					throw new Exception("批次还款任务表(ht_borrow_apicron)更新任务状态(还款请求中)失败！[用户ID：" + userId + "]，[借款编号：" + borrowNid + "]");
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
				BankCallBean repayResult = BankCallUtils.callApiBg(repayBean);
				if (Validator.isNotNull(repayResult)) {
					String received = StringUtils.isNotBlank(repayResult.getReceived()) ? repayResult.getReceived() : "";
					if (BankCallConstant.RECEIVED_SUCCESS.equals(received)) {
						try {
							// 更新任务API状态
							boolean apicronResultFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_SENDED);
							if (apicronResultFlag) {
								map.put("result", repayResult);
								map.put("delFlag", delFalg);
								return map;
							}
						} catch (Exception e) {
							logger.error("【直投批次还款】还款请求成功后,更新任务状态(还款请求成功)发生异常!", e);
						}
						map.put("result", repayResult);
						map.put("delFlag", true);
						return map;
					} else {
						continue;
					}
				} else {
					continue;
				}
			} catch (Exception e) {
				logger.error("【直投批次还款】还款请求时发生系统异常！", e);
			}
		}
		map.put("result", null);
		map.put("delFlag", delFalg);
		return map;
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
	 * 
	 * @param borrowRecoverPlan
	 * @return
	 */
	private boolean updateBorrowRecoverPlan(BorrowRecoverPlan borrowRecoverPlan) {
		boolean flag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
		return flag;
	}

	
	private Account getAccountByUserId(Integer userId) {
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andUserIdEqualTo(userId);
		List<Account> listAccount = this.accountMapper.selectByExample(accountExample);
		if (listAccount != null && listAccount.size() > 0) {
			return listAccount.get(0);
		}
		return null;
	}

	/**
	 * 取得未还款的放款明细列表
	 *
	 * @return
	 */
	private List<BorrowRecover> getBorrowRecoverList(String borrowNid,BorrowApicron apicron) {
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

	/***
	 * 查询相应的债转还款记录
	 * 
	 * @param borrowNid
	 * @param tenderOrderId
	 * @param periodNow
	 * @return
	 */
	private List<CreditRepay> selectCreditRepay(String borrowNid, String tenderOrderId, Integer periodNow) {
		CreditRepayExample example = new CreditRepayExample();
		CreditRepayExample.Criteria crt = example.createCriteria();
		crt.andBidNidEqualTo(borrowNid);
		crt.andCreditTenderNidEqualTo(tenderOrderId);
		crt.andRecoverPeriodEqualTo(periodNow);
		crt.andStatusNotEqualTo(1);
		List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(example);
		return creditRepayList;
	}

	/**
	 * 判断是否完全承接  true:未完全承接
	 * @param borrowRecover
	 * @param isMonth
	 * @return
	 */
	private boolean isOverUndertake(BorrowRecover borrowRecover, boolean isMonth) {
		if (borrowRecover.getRecoverCapital().compareTo(borrowRecover.getCreditAmount()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception {

		String borrowNid = apicron.getBorrowNid();
		apicron.setStatus(status);
		apicron.setUpdateTime(new Date());
		boolean apicronFlag = this.borrowApicronMapper.updateByPrimaryKeySelective(apicron) > 0 ? true : false;
		if (!apicronFlag) {
			throw new Exception("批次还款任务表(ht_borrow_apicron)更新失败！[借款编号：" + borrowNid + "]");
		}
		
		Borrow borrow = this.getBorrowByNid(borrowNid);
		borrow.setRepayStatus(status);
		boolean borrowFlag = this.borrowMapper.updateByPrimaryKey(borrow) > 0 ? true : false;
		if (!borrowFlag) {
			throw new Exception("标的表(ht_borrow)更新失败！[借款编号：" + borrowNid + "]");
		}
		
		return borrowFlag;
	}
	
	@Override
	public int reapyBatchDetailsUpdate(BorrowApicron apicron) {

		String borrowNid = apicron.getBorrowNid();// 项目编号
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
					int borrowStatus = ((BatchBorrowRepayZTService)AopContext.currentProxy()).updateBorrowStatus(apicron, borrow, borrowInfo);
					return borrowStatus;
				} catch (Exception e) {
					logger.error("【直投还款】更新借款人数据时发生系统异常！", e);
				}
			}
		} catch (Exception e1) {
			logger.error("【直投还款】更新出借人数据时发生系统异常！", e1);
		}
		return CustomConstants.BANK_BATCH_STATUS_FAIL;
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
		// 获取共同参数
		String channel = BankCallConstant.CHANNEL_PC;
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
	 * 自动还款
	 *
	 * @throws Exception
	 */
	private boolean debtRepays(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, List<BankCallBean> resultBeans) throws Exception {

		/** 基本变量 */
		String borrowNid = apicron.getBorrowNid();// 借款编号
		int periodNow = apicron.getPeriodNow();// 当前还款期数
		String borrowStyle = borrow.getBorrowStyle();
		int borrowPeriod = borrow.getBorrowPeriod();// 项目期数
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
			List<BorrowRecover> borrowRecoverList = this.getBorrowRecoverList(borrowNid, apicron);
			if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
                logger.info("【直投还款】借款编号：{}，开始更新出借明细，共{}条。", borrowNid, borrowRecoverList.size());
				// 遍历进行还款 
				for (int i = 0; i < borrowRecoverList.size(); i++) {
                    logger.info("【直投还款】借款编号：{}，开始更新第{}条出借明细。", borrowNid, i + 1);
					// 出借明细
					BorrowRecover borrowRecover = borrowRecoverList.get(i);
					String tenderOrderId = borrowRecover.getNid();// 出借订单号
					String repayOrderId = borrowRecover.getRepayOrdid();// 还款订单号
					if(1 == apicron.getIsAllrepay()){//一次性结清
						repayOrderId = getAllRepayOrdid(borrowNid,periodNow,borrowRecover.getNid());
					}
					BigDecimal creditAmount = borrowRecover.getCreditAmount();// 债转金额
					JSONObject repayDetail = repayResults.get(repayOrderId);
					// 如果发生了债转，处理相应的债转还款
					if (creditAmount.compareTo(BigDecimal.ZERO) > 0) {
                        logger.info("【直投还款】借款编号：{}，发生债转。", borrowNid);
						List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, periodNow);
						if (creditRepayList != null && creditRepayList.size() > 0) {
                            logger.info("【直投还款】借款编号：{}，原始出借发生债转，出借订单号：{}，债转还款共{}条。", borrowNid, tenderOrderId, creditRepayList.size());
							boolean creditRepayAllFlag = true;
							boolean creditEndAllFlag = true;
							for (int j = 0; j < creditRepayList.size(); j++) {
								CreditRepay creditRepay = creditRepayList.get(j);
								String assignOrderId = creditRepay.getAssignNid();
								String creditRepayOrderId = creditRepay.getCreditRepayOrderId();
								JSONObject assignRepayDetail = repayResults.get(creditRepayOrderId);
								if (Validator.isNull(assignRepayDetail)) {
									logger.error("【直投还款】银行端未查询到相应的还款明细！出借订单号：{}，债转还款订单号：{}", tenderOrderId, creditRepayOrderId);
									creditRepayAllFlag = false;// 有债转处理失败，不进行后续还款更新
									continue;
								}
								try {
									String txState = assignRepayDetail.getString(BankCallConstant.PARAM_TXSTATE);// 交易状态
									// 如果处理状态为成功
									if (txState.equals(BankCallConstant.BATCH_TXSTATE_TYPE_SUCCESS)) {
										// 调用债转还款
										boolean creditRepayFlag = ((BatchBorrowRepayZTService)AopContext.currentProxy()).updateCreditRepay(apicron, borrow, borrowInfo, borrowRecover, creditRepay, assignRepayDetail);
										if (creditRepayFlag) {
											// 结束债转已在updateCreditRepay中处理 update by wgx in 2019/01/10
										} else {
											creditEndAllFlag = false;
										}
									} else {
										creditRepayAllFlag = false;
										// 更新债转还款表
										boolean borrowTenderFlag = this.updateCreditRepay(creditRepay);
										if (!borrowTenderFlag) {
											throw new Exception("债转还款表(ht_credit_repay)更新失败！[承接订单号：" + assignOrderId + "]，[还款期数：" + periodNow + "]");
										}
									}
								} catch (Exception e) {
									logger.error("【直投还款】更新承接人的还款数据发生异常！", e);
									creditRepayAllFlag = false;
									continue;
								}
							}
							if (creditRepayAllFlag) {
								// 如果不是全部债转
								logger.info("【直投还款】开始判断是否为完全承接！出借订单号：{}", tenderOrderId);
								if (borrowRecover.getRecoverCapital().compareTo(borrowRecover.getCreditAmount()) > 0) {
									logger.info("【直投还款】非完全承接！原始金额：{}，债转金额：{}", borrowRecover.getRecoverCapital(), borrowRecover.getCreditAmount());
									if (Validator.isNull(repayDetail)) {
										logger.error("【直投还款】银行端未查询到相应的还款明细！出借订单号：{}", tenderOrderId);
										continue;
									}
									String txState = repayDetail.getString(BankCallConstant.PARAM_TXSTATE);// 交易状态
									// 如果处理状态为成功
									if (txState.equals(BankCallConstant.BATCH_TXSTATE_TYPE_SUCCESS)) {
										try {
											((BatchBorrowRepayZTService)AopContext.currentProxy()).updateTenderRepay(apicron, borrow, borrowInfo, borrowRecover, repayDetail, creditEndAllFlag);
											// 结束债转已在updateTenderRepay中处理 update by wgx in 2019/01/10
										} catch (Exception e) {
											logger.error("【直投还款】更新出借人非完全承接的还款数据发生异常！", e);
											continue;
										}
									} else {
										try {
											// 更新放款记录表
											boolean recoverFlag = ((BatchBorrowRepayZTService)AopContext.currentProxy()).updateRecover(apicron, borrow, borrowRecover);
											if (!recoverFlag) {
												throw new Exception("还款失败!" + "[出借订单号：" + tenderOrderId + "]");
											}
										} catch (Exception e) {
											logger.error("【直投还款】更新放款记录表的还款状态(还款失败)发生异常！", e);
											continue;
										}
									}
								} else {
									logger.info("【直投还款】完全承接！原始金额：{}，债转金额：{}", borrowRecover.getRecoverCapital(), borrowRecover.getCreditAmount());
									try {
										boolean tenderRepayFlag = ((BatchBorrowRepayZTService)AopContext.currentProxy()).updateTenderRepayStatus(apicron, borrow, borrowRecover);
										if (!tenderRepayFlag) {
											throw new Exception("更新相应的还款信息失败!" + "[出借订单号：" + tenderOrderId + "]");
										}
									} catch (Exception e) {
										logger.error("【直投还款】更新出借人完全承接的还款数据发生异常！", e);
										continue;
									}
								}
							} else {
								continue;
							}
						}
					} else {
						if (Validator.isNull(repayDetail)) {
							logger.error("【直投还款】银行端未查询到相应的还款明细！出借订单号：{}", tenderOrderId);
							continue;
						}
						String txState = repayDetail.getString(BankCallConstant.PARAM_TXSTATE);// 交易状态
						logger.info("【直投还款】借款编号：{}，交易状态为：{}", borrowNid, txState);
						// 如果处理状态为成功
						if (txState.equals(BankCallConstant.BATCH_TXSTATE_TYPE_SUCCESS)) {
							try {
								((BatchBorrowRepayZTService)AopContext.currentProxy()).updateTenderRepay(apicron, borrow, borrowInfo, borrowRecover, repayDetail, true);
								// 结束债转已在updateTenderRepay中处理 update by wgx in 2019/01/10
							} catch (Exception e) {
								logger.error("【直投还款】更新出借人的还款数据发生系统异常！", e);
								continue;
							}
						} else {
							try {
								// 更新放款记录表
								boolean recoverFlag = ((BatchBorrowRepayZTService)AopContext.currentProxy()).updateRecover(apicron, borrow, borrowRecover);
								if (!recoverFlag) {
									throw new Exception("还款失败！[出借订单号：" + tenderOrderId + "]");
								}
							} catch (Exception e) {
								logger.error("【直投还款】更新放款记录表的还款状态发生系统异常！", e);
								continue;
							}
						}
					}
				}
			} else {
				logger.info("【直投还款】借款编号：{}，未查询到待处理的还款记录。", borrowNid);
				return true;
			}
		} else {
			throw new Exception("银行交易明细查询失败！[借款编号：" + borrowNid + "]");
		}
		return true;
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

	@Override
	public boolean updateCreditRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover, CreditRepay creditRepay, JSONObject assignRepayDetail) throws Exception {

		/** 还款信息 */
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 借款编号
		String borrowNid = apicron.getBorrowNid();
		// 还款人(借款人或垫付机构)ID
		Integer repayUserId = apicron.getUserId();
		logger.info("【直投还款/承接人】借款编号：{}，开始更新承接人的还款数据。还款人ID：{}，承接订单号：{}", borrowNid, repayUserId, creditRepay.getCreditNid());
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
		// 项目总期数
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
		Integer creditNid = Validator.isNull(creditRepay.getCreditNid()) ? null : Integer.parseInt(creditRepay.getCreditNid());
		// 承接订单号
		String assignNid = creditRepay.getAssignNid();
		// 承接用户userId
		int assignUserId = creditRepay.getUserId();
		// 还款订单号
		String repayOrderId = creditRepay.getCreditRepayOrderId();
		// 还款本息(实际)
		BigDecimal assignAccount = creditRepay.getAssignAccount();
		// 还款本金(实际)
		BigDecimal assignCapital = creditRepay.getAssignCapital();
		// 还款利息(实际)
		BigDecimal assignInterest = creditRepay.getAssignInterest();
		// 管理费
		BigDecimal assignManageFee = creditRepay.getManageFee();
		// 提前还款少还利息
		BigDecimal chargeInterest = creditRepay.getChargeInterest();
		// 延期利息
		BigDecimal delayInterest = creditRepay.getDelayInterest();
		// 逾期利息
		BigDecimal lateInterest = creditRepay.getLateInterest();
		// 还款本息(实际)
		BigDecimal repayAccount = assignAccount.add(lateInterest).add(delayInterest).add(chargeInterest);
		// 还款本金(实际)
		BigDecimal repayCapital = assignCapital;
		// 还款利息(实际)
		BigDecimal repayInterest = assignInterest.add(lateInterest).add(delayInterest).add(chargeInterest);
		// 管理费
		BigDecimal manageFee = assignManageFee;
		/** 基本变量 */
		// 剩余还款期数
		Integer periodNext = borrowPeriod - periodNow;
		// 取得还款详情
		BorrowRepay borrowRepay = getBorrowRepay(borrowNid);
		// 出借信息
		BorrowTender borrowTender = getBorrowTender(tenderOrderId);
		// 查询相应的债权转让
		BorrowCredit borrowCredit = this.getBorrowCredit(creditNid);
		// 出借用户开户信息
		Account assignBankAccount = getAccountByUserId(assignUserId);
		// 出借用户银行账户
		String assignAccountId = assignBankAccount.getAccountId();
		// 判断该收支明细存在时,跳出本次循环
		if (countCreditAccountListByNid(repayOrderId)) {
			logger.info("【直投还款/承接人】承接人收支明细已存在！还款订单号:{}", repayOrderId);
			return true;
		}
		// 查询相应的债权承接记录
		CreditTender creditTender = this.getCreditTender(assignNid);
		if (Validator.isNull(creditTender)) {
			throw new Exception("债权承接记录不存在！[承接人用户ID：" + assignUserId + "]，[承接订单号：" + assignNid + "]");
		}
		// 是否分期
		// true:分期 principal: 等额本金，month:等额本息，endmonth:先息后本
		// false:不分期 endday:按天计息，end:按月计息
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// 分期还款计划表
		BorrowRecoverPlan borrowRecoverPlan = null;
		// 债转的下次还款时间
		int creditRepayNextTime = creditRepay.getAssignRepayNextTime();
		// 更新账户信息(承接人)
		Account assignUserAccount = new Account();
		assignUserAccount.setUserId(assignUserId);
		assignUserAccount.setBankTotal(lateInterest.add(delayInterest).add(chargeInterest));// 承接人资金总额
		assignUserAccount.setBankBalance(repayAccount);// 承接人可用余额
		assignUserAccount.setBankAwait(assignAccount);// 承接人待收金额
		assignUserAccount.setBankAwaitCapital(assignCapital);
		assignUserAccount.setBankAwaitInterest(assignInterest);
		assignUserAccount.setBankInterestSum(repayInterest);
		assignUserAccount.setBankBalanceCash(repayAccount);
		boolean investAccountFlag = this.adminAccountCustomizeMapper.updateOfRepayTender(assignUserAccount) > 0 ? true : false;
		if (!investAccountFlag) {
			throw new Exception("账户信息表(ht_account)更新失败！[承接人用户ID：" + assignUserId + "]，[承接订单号：" + assignNid + "]");
		}
		// 取得承接人账户信息
		assignUserAccount = this.getAccountByUserId(creditRepay.getUserId());
		if (Validator.isNull(assignAccount)) {
			throw new Exception("承接人账户信息不存在！[承接人用户ID：" + assignUserId + "]，[承接订单号：" + assignNid + "]");
		}
		// 写入收支明细
		AccountList accountList = new AccountList();
		accountList.setNid(repayOrderId); // 还款订单号
		accountList.setUserId(assignUserId); // 承接人
		accountList.setAmount(repayAccount); // 承接总收入
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
		accountList.setType(1); // 1收入
		accountList.setTrade("credit_tender_recover_yes"); // 投标成功
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
		boolean assignAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
		if (!assignAccountListFlag) {
			throw new Exception("收支明细表(ht_account_list)写入失败！[承接人用户ID：" + assignUserId + "]，[承接订单号：" + assignNid + "]");
		}
		// 更新相应的债转出借表
		// 债转已还款总额
		creditTender.setAssignRepayAccount(creditTender.getAssignRepayAccount().add(repayAccount));
		// 债转已还款本金
		creditTender.setAssignRepayCapital(creditTender.getAssignRepayCapital().add(repayCapital));
		// 债转已还款利息
		creditTender.setAssignRepayInterest(creditTender.getAssignRepayInterest().add(repayInterest));
		// 债转最近还款时间
		creditTender.setAssignRepayLastTime(!isMonth ? nowTime : 0);
		// 债转下次还款时间
		creditTender.setAssignRepayNextTime(!isMonth ? 0 : creditRepayNextTime);
		// 债转还款状态
		if (isMonth) {
			// 取得放款分期列表
			borrowRecoverPlan = getBorrowRecoverPlan(borrowNid, periodNow, tenderUserId, tenderOrderId);
			if (borrowRecoverPlan == null) {
				throw new Exception("放款分期数据不存在！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]，[期数：" + periodNow + "]");
			}
			// 债转状态
			if (borrowRecoverPlan != null && Validator.isNotNull(periodNext) && periodNext > 0) {
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
		creditTender.setRecoverPeriod(periodNow);
		boolean creditTenderFlag = this.creditTenderMapper.updateByPrimaryKeySelective(creditTender) > 0 ? true : false;
		if (!creditTenderFlag) {
			throw new Exception("债转出借表(ht_credit_tender)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]");
		}
		creditRepay.setAssignRepayAccount(creditRepay.getAssignRepayAccount().add(repayAccount));
		creditRepay.setAssignRepayCapital(creditRepay.getAssignRepayCapital().add(repayCapital));
		creditRepay.setAssignRepayInterest(creditRepay.getAssignRepayInterest().add(repayInterest));
		creditRepay.setAssignRepayLastTime(nowTime);
		creditRepay.setAssignRepayYesTime(nowTime);
		creditRepay.setManageFee(manageFee);
		creditRepay.setStatus(1);
		boolean creditRepayFlag = this.creditRepayMapper.updateByPrimaryKeySelective(creditRepay) > 0 ? true : false;
		if (!creditRepayFlag) {
			throw new Exception("债转还款表(ht_credit_repay)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]");
		}
		// 债转总表数据更新
		// 更新债转已还款总额
		borrowCredit.setCreditRepayAccount(borrowCredit.getCreditRepayAccount().add(repayAccount));
		// 更新债转已还款本金
		borrowCredit.setCreditRepayCapital(borrowCredit.getCreditRepayCapital().add(repayCapital));
		// 更新债转已还款利息
		borrowCredit.setCreditRepayInterest(borrowCredit.getCreditRepayInterest().add(repayInterest));
		// 债转下次还款时间
		borrowCredit.setCreditRepayNextTime(isMonth ? creditRepayNextTime : 0);
		if (borrowCredit.getCreditStatus() == 0) {
			borrowCredit.setCreditStatus(1);
		}
		// 更新债转总表
		boolean borrowCreditFlag = this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit) > 0 ? true : false;
		// 债转总表更新成功
		if (!borrowCreditFlag) {
			throw new Exception("债转标的表(ht_borrow_credit)更新失败！[债转编号：" + creditNid + "]，[承接订单号：" + assignNid + "]");
		}
		// 更新还款表（不分期）
		borrowRecover.setRepayBatchNo(repayBatchNo);
		borrowRecover.setRecoverAccountYes(borrowRecover.getRecoverAccountYes().add(repayAccount)); // 已还款总额
		// 已还款本金
		borrowRecover.setRecoverCapitalYes(borrowRecover.getRecoverCapitalYes().add(repayCapital));
		// 已还款利息
		borrowRecover.setRecoverInterestYes(borrowRecover.getRecoverInterestYes().add(repayInterest));
		// 待还金额
		borrowRecover.setRecoverAccountWait(borrowRecover.getRecoverAccountWait().subtract(assignAccount));
		// 待还本金
		borrowRecover.setRecoverCapitalWait(borrowRecover.getRecoverCapitalWait().subtract(assignCapital));
		// 待还利息
		borrowRecover.setRecoverInterestWait(borrowRecover.getRecoverInterestWait().subtract(assignInterest));
		// 已还款提前还款利息
		borrowRecover.setRepayChargeInterest(borrowRecover.getRepayChargeInterest().add(chargeInterest));
		// 已还款延期还款利息
		borrowRecover.setRepayDelayInterest(borrowRecover.getRepayDelayInterest().add(delayInterest));
		// 已还款逾期还款利息
		borrowRecover.setRepayLateInterest(borrowRecover.getRepayLateInterest().add(lateInterest));
		// 已还款管理费
		borrowRecover.setRecoverFeeYes(borrowRecover.getRecoverFeeYes().add(manageFee));
		// 更新还款表
		boolean creditBorrowRecoverFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover) > 0 ? true : false;
		if (!creditBorrowRecoverFlag) {
			throw new Exception("放款记录总表(ht_borrow_recover)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]");
		}
		// 更新总的还款明细
		borrowRepay.setRepayAccountAll(borrowRepay.getRepayAccountAll().add(repayAccount).add(manageFee));
		borrowRepay.setRepayAccountYes(borrowRepay.getRepayAccountYes().add(repayAccount));
		borrowRepay.setRepayCapitalYes(borrowRepay.getRepayCapitalYes().add(repayCapital));
		borrowRepay.setRepayInterestYes(borrowRepay.getRepayInterestYes().add(repayInterest));
		// 逾期天数
		borrowRepay.setLateRepayDays(borrowRecover.getLateDays());
		// 提前天数
		borrowRepay.setChargeDays(borrowRecover.getChargeDays());
		// 延期天数
		borrowRepay.setDelayDays(borrowRecover.getDelayDays());
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
			// 待还本金
			borrowRecoverPlan.setRecoverCapitalWait(borrowRecoverPlan.getRecoverCapitalWait().subtract(assignCapital));
			// 待还利息
			borrowRecoverPlan.setRecoverInterestWait(borrowRecoverPlan.getRecoverInterestWait().subtract(assignInterest));
			// 已还款提前还款利息
			borrowRecoverPlan.setRepayChargeInterest(borrowRecoverPlan.getRepayChargeInterest().add(chargeInterest));
			// 已还款延期还款利息
			borrowRecoverPlan.setRepayDelayInterest(borrowRecoverPlan.getRepayDelayInterest().add(delayInterest));
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
				// 逾期天数
				borrowRepayPlan.setLateRepayDays(borrowRecoverPlan.getLateDays());
				// 提前天数
				borrowRepayPlan.setChargeDays(borrowRecoverPlan.getChargeDays());
				// 延期天数
				borrowRepayPlan.setDelayDays(borrowRecoverPlan.getDelayDays());
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
		// 更新标的表
        borrow = getBorrowByNid(borrowNid);
		Borrow newBrrow = new Borrow();
		newBrrow.setId(borrow.getId());
		BigDecimal borrowManager = borrow.getBorrowManager() == null ? BigDecimal.ZERO : new BigDecimal(borrow.getBorrowManager());
		newBrrow.setBorrowManager(borrowManager.add(manageFee).toString());
		// 总还款利息
		newBrrow.setRepayAccountYes(borrow.getRepayAccountYes().add(repayAccount));
		// 总还款利息
		newBrrow.setRepayAccountInterestYes(borrow.getRepayAccountInterestYes().add(repayInterest));
		// 总还款本金
		newBrrow.setRepayAccountCapitalYes(borrow.getRepayAccountCapitalYes().add(repayCapital));
		// 未还款总额
		newBrrow.setRepayAccountWait(borrow.getRepayAccountWait().subtract(assignAccount));
		// 未还款本金
		newBrrow.setRepayAccountCapitalWait(borrow.getRepayAccountCapitalWait().subtract(assignCapital));
		// 未还款利息
		newBrrow.setRepayAccountInterestWait(borrow.getRepayAccountInterestWait().subtract(assignInterest));
		// 项目的管理费
		newBrrow.setRepayFeeNormal(borrow.getRepayFeeNormal().add(manageFee));
		boolean borrowFlag = this.borrowMapper.updateByPrimaryKeySelective(newBrrow) > 0 ? true : false;
		if (!borrowFlag) {
            throw new Exception("标的表(ht_borrow)更新失败！[借款编号：" + borrowNid + "]，[承接订单号：" + assignNid + "]");
		}
		//BeanUtils.copyProperties(newBrrow, borrow);// 更新还款金额数据 update by wgx 2019/02/22
		logger.info("【智投还款/承接人】借款编号：{}，更新标的表完毕。总还款：{}，未还款总额：{}",
				borrowNid, borrow.getRepayAccountYes(), borrow.getRepayAccountWait());
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
		// 结束债权
        if (!isMonth || (isMonth && periodNext == 0)) {
            boolean debtOverFlag = this.requestDebtEnd(creditRepay.getUserId(), assignRepayDetail, assignNid, borrowInfo);
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
            // 插入网站收支明细记录
            AccountWebListVO accountWebList = new AccountWebListVO();
            accountWebList.setOrdid(creditRepay.getAssignNid() + "_" + periodNow);// 订单号
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
                logger.info("【直投还款/承接人】发送收支明细。还款人id：{}，管理费：{}", repayUserId, manageFee);
                commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebList));
            } catch (MQException e) {
                logger.error("【直投还款/承接人】发送收支明细时发生系统异常！", e);
            }
        }
        try {
            // 发送短信
            this.sendSms(assignUserId, borrowNid, repayCapital, repayInterest);
            // 推送消息
            this.sendMessage(assignUserId, borrowNid, repayAccount, repayInterest);
        } catch (Exception e) {
            logger.error("【直投还款/承接人】发送短信和推送消息时发生系统异常！", e);
        }
		logger.info("【直投还款/承接人】承接人的还款数据更新结束。承接订单号：{}，还款订单号：{}", assignNid, repayOrderId);
		return true;
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
	 * @param
	 * @return
	 */
	private boolean countAccountListByNid(String nid) {
		AccountListExample accountListExample = new AccountListExample();
		accountListExample.createCriteria().andNidEqualTo(nid).andTradeEqualTo("tender_recover_yes");
		return this.accountListMapper.countByExample(accountListExample) > 0 ? true : false;
	}

	/**
	 * 判断承接人收支明细是否存在
	 *
	 * @param
	 * @return
	 */
	private boolean countCreditAccountListByNid(String nid) {
		AccountListExample accountListExample = new AccountListExample();
		accountListExample.createCriteria().andNidEqualTo(nid).andTradeEqualTo("credit_tender_recover_yes");
		return this.accountListMapper.countByExample(accountListExample) > 0 ? true : false;
	}

	/**
	 * 取得总的还款计划表
	 *
	 * @param
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
	 * 根据承接订单号获取汇转让信息
	 * 
	 * @param creditNid
	 * @return
	 */
	private BorrowCredit getBorrowCredit(int creditNid) {
		BorrowCreditExample example = new BorrowCreditExample();
		BorrowCreditExample.Criteria crt = example.createCriteria();
		crt.andCreditNidEqualTo(creditNid);
		List<BorrowCredit> borrowCreditList = this.borrowCreditMapper.selectByExample(example);
		if (borrowCreditList != null && borrowCreditList.size() == 1) {
			return borrowCreditList.get(0);
		}
		return null;
	}

	/**
	 * 结束相应的债权
	 * 
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	private boolean requestDebtEnd(Integer userId, JSONObject repayDetail, String orgOrderId, BorrowInfo borrowInfo) {

		String accountId = repayDetail.getString(BankCallConstant.PARAM_FORACCOUNTID);// 出借人账户
		String forAccountId = repayDetail.getString(BankCallConstant.PARAM_ACCOUNTID);// 借款人账户
		String productId = repayDetail.getString(BankCallConstant.PARAM_PRODUCTID);// 借款编号
		String authCode = repayDetail.getString(BankCallConstant.PARAM_AUTHCODE);// 出借授权码
		// 垫付机构还款时,结束无法结束债权
		Integer borrowUserId = borrowInfo.getUserId();
        logger.info("【直投还款】借款编号：{}，结束债权。借款人：{}-{}，出借人：{}-{}，授权码：{}，原始订单号：{}。",
                productId, borrowUserId, forAccountId, userId, accountId, authCode, orgOrderId);
		// 根据用户ID查询借款人用户电子账户号
		Account borrowUserAccount = this.getAccountByUserId(borrowUserId);
		if(borrowUserAccount==null || borrowUserAccount.getAccountId() == null){
			logger.error("【直投还款】结束债权获取借款人电子账户号失败！用户ID:{}", borrowUserId);
			return false;
		}
		// 借款人电子账户号
		forAccountId = borrowUserAccount.getAccountId();
		try {
			String logOrderId = GetOrderIdUtils.getOrderId2(userId);
			BankCreditEnd record = new BankCreditEnd();
			record.setUserId(borrowUserId);
			record.setTenderUserId(userId);
			record.setAccountId(forAccountId);
			record.setTenderAccountId(accountId);
			record.setOrderId(logOrderId);
			record.setBorrowNid(productId);
			record.setAuthCode(authCode);
			record.setCreditEndType(1); // 结束债权类型（1:还款，2:散标债转，3:计划债转）'
			record.setStatus(0);
			record.setOrgOrderId(orgOrderId);
			record.setCreateUser(userId);
			record.setUpdateUser(userId);
			this.bankCreditEndMapper.insertSelective(record);
			return true;
		} catch (Exception e) {
			logger.error("【直投还款】结束债权时发生系统异常！", e);
		}
		return false;
	}

	/**
	 * 更新相应的债转还款为债权结束
	 * 
	 * @param creditRepay
	 * @return
	 */
	private boolean updateDebtStatus(CreditRepay creditRepay) {

		int userId = creditRepay.getUserId();
		String assignNid = creditRepay.getAssignNid();
		CreditRepayExample example = new CreditRepayExample();
		example.createCriteria().andUserIdEqualTo(userId).andAssignNidEqualTo(assignNid);
		CreditRepay repay = new CreditRepay();
		repay.setDebtStatus(1);
		boolean flag = this.creditRepayMapper.updateByExampleSelective(repay, example) > 0 ? true : false;
		return flag;
	}

	/**
	 * 更新相应的原始出借为债权结束
	 * 
	 * @param borrowRecover
	 * @param isMonth
	 * @return
	 */
	private boolean updateDebtStatus(BorrowRecover borrowRecover, boolean isMonth) {
		borrowRecover.setDebtStatus(1);
		return this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover) > 0 ? true : false;
	}

	/**
	 * 更新相应的债转还款记录
	 *
	 * @param creditRepay
	 * @return
	 * @throws Exception
	 */
	private boolean updateCreditRepay(CreditRepay creditRepay) throws Exception {
		// 更新债转还款表
		creditRepay.setStatus(2); // 状态 0未还款1已还款2还款失败
		boolean creditRepayFlag = this.creditRepayMapper.updateByPrimaryKeySelective(creditRepay) > 0 ? true : false;
		if (!creditRepayFlag) {
			throw new Exception("债转还款表(ht_credit_repay)更新失败！[承接订单号：" + creditRepay.getAssignNid() + "]，[期数：" + creditRepay.getRecoverPeriod() + "]");
		}
		return true;
	}

	/**
	 * 更新还款完成状态
	 * 
	 * @param borrow
	 *
	 * @throws Exception
	 */
	@Override
	public int updateBorrowStatus(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo) throws Exception {

		int nowTime = GetDate.getNowTime10();
		String borrowNid = borrow.getBorrowNid();
		int borrowUserId = borrowInfo.getUserId();
		String borrowStyle = borrow.getBorrowStyle();// 项目还款方式
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
		Account repayBankAccount = this.getAccountByUserId(repayUserId);
		String repayAccountId = repayBankAccount.getAccountId();
		String apicronNid = apicron.getNid();
		logger.info("【直投还款】借款编号：{}，出借人和承接人数据更新完成，更新借款人数据开始。还款期数：{}",borrowNid,periodNow);
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
		Integer repayYesTime = 0;
        // 当期已还利息
		BigDecimal recoverInterestAmount = BigDecimal.ZERO;
		// 标的总表信息
		Borrow newBorrow = new Borrow();
		if (isMonth) {
			// 查询recover
			BorrowRecoverPlanExample recoverPlanExample = new BorrowRecoverPlanExample();
			recoverPlanExample.createCriteria().andBorrowNidEqualTo(borrowNid).andRecoverPeriodEqualTo(periodNow).andRecoverStatusNotEqualTo(1);
			// 原始投资的失败笔数
			failCount = this.borrowRecoverPlanMapper.countByExample(recoverPlanExample);
			// 如果还款全部完成
			if (failCount == 0) {
				boolean isAllRepay = apicron.getIsAllrepay() == null ? false : apicron.getIsAllrepay() == 1;
                // 首先判断当前期是否是一次性还款中唯一一期需要更新的 update by wgx 2019/02/19
                boolean isLastUpdate = isLastAllRepay(borrowNid, periodNow, isAllRepay);
				if (isLastUpdate || (!isAllRepay && periodNext == 0)) {
					repayType = TYPE_WAIT_YES;
					repayStatus = 1;
					repayYesTime = nowTime;
					status = 5;
				}
				// 还款总表
				BorrowRepay borrowRepay = this.getBorrowRepay(borrowNid);
				borrowRepay.setRepayType(repayType);
				borrowRepay.setRepayStatus(repayStatus); // 已还款
				borrowRepay.setRepayPeriod(isMonth ? periodNext : 1);
				borrowRepay.setRepayActionTime(nowTime);// 实际还款时间
				// 分期的场合，根据借款编号和还款期数从还款计划表中取得还款时间
				BorrowRepayPlanExample example = new BorrowRepayPlanExample();
				BorrowRepayPlanExample.Criteria repayPlanCriteria = example.createCriteria();
				repayPlanCriteria.andBorrowNidEqualTo(borrowNid);
				repayPlanCriteria.andRepayPeriodEqualTo(periodNow + 1);
				List<BorrowRepayPlan> replayPlan = borrowRepayPlanMapper.selectByExample(example);
				if (replayPlan.size() > 0) {
					BorrowRepayPlan borrowRepayPlanNext = replayPlan.get(0);
					if (borrowRepayPlanNext != null) {
						// 取得下期还款时间
						Integer repayTime = borrowRepayPlanNext.getRepayTime();
						// 设置下期还款时间
						borrowRepay.setRepayTime(repayTime);
						// 设置下期还款时间
						newBorrow.setRepayNextTime(repayTime);
					}
				} else {
					// 还款成功最后时间
					borrowRepay.setRepayYestime(repayYesTime);
				}
				// 更新相应的还款计划表
				BorrowRepayPlan borrowRepayPlan = getBorrowRepayPlan(borrowNid, periodNow);
				if (Validator.isNull(borrowRepayPlan)) {
					throw new Exception("未查询到相应的分期还款记录！[借款编号：" + borrowNid + "]，[还款期数：" + periodNow + "]");
				}
				borrowRepayPlan.setRepayType(TYPE_WAIT_YES);
				borrowRepayPlan.setRepayActionTime(String.valueOf(nowTime));
				borrowRepayPlan.setRepayStatus(1);
				borrowRepayPlan.setRepayYestime(nowTime);
				boolean borrowRepayPlanFlag = this.borrowRepayPlanMapper.updateByPrimaryKeySelective(borrowRepayPlan) > 0 ? true : false;
				if (!borrowRepayPlanFlag) {
					throw new Exception("还款记录分期表(ht_borrow_repay_plan)更新失败！[借款编号：" + borrowNid + "]，[还款期数：" + periodNow + "]");
				}
				// 更新BorrowRepay
				BorrowRepayExample repayExample = new BorrowRepayExample();
				repayExample.createCriteria().andBorrowNidEqualTo(borrowNid);
				boolean borrowRepayFlag = this.borrowRepayMapper.updateByExampleSelective(borrowRepay, repayExample) > 0 ? true : false;
				if (!borrowRepayFlag) {
					throw new Exception("还款记录分期表(ht_borrow_repay_plan)更新失败！[借款编号：" + borrowNid + "]，[还款期数：" + periodNow + "]");
				}
				Account repayUserAccount = this.getAccountByUserId(repayUserId);
				BigDecimal repayAccount = borrowRepayPlan.getRepayAccountYes();
				BigDecimal manageFee = borrowRepayPlan.getRepayFee();
				BigDecimal repayAccountWait = borrowRepayPlan.getRepayAccount();
				BigDecimal repayCapitalWait = borrowRepayPlan.getRepayCapital();
				BigDecimal repayInterestWait = borrowRepayPlan.getRepayInterest();
                recoverInterestAmount = borrowRepayPlan.getRepayInterest();
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
				repayUserAccount = this.getAccountByUserId(repayUserId);
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
				repayAccountList.setPlanBalance(repayUserAccount.getPlanBalance());//汇计划账户可用余额
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
				if(!isAllRepay){//非一次性还款时插入资金明细
					boolean repayAccountListFlag = this.accountListMapper.insertSelective(repayAccountList) > 0 ? true : false;
					if (!repayAccountListFlag) {
						throw new Exception("收支明细表(ht_account_list)写入失败，[借款人用户ID：" + repayUserId + "]，[借款编号:" + borrowNid + "]");
					}
				}
				// 非一次性还款和一次性还款最后一期才更新
				if(!isAllRepay || isLastUpdate){
					// 更新Borrow
					newBorrow.setRepayFullStatus(repayStatus);
					newBorrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
					newBorrow.setStatus(status);
					BorrowExample borrowExample = new BorrowExample();
					borrowExample.createCriteria().andIdEqualTo(borrowId);
					boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
					if (!borrowFlag) {
						throw new Exception("最后一期还款成功后，标的表(ht_borrow)更新失败！[借款编号：" + borrowNid + "]，还款期数：" + periodNow + "]");
					}
				}
				BorrowApicronExample apicronExample = new BorrowApicronExample();
				apicronExample.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
				apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
				apicron.setUpdateTime(new Date());
				boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, apicronExample) > 0 ? true : false;
				if (!apicronFlag) {
					throw new Exception("批次还款任务表(ht_borrow_apicron)更新失败！[借款编号：" + borrowNid + "]");
				}
				if(isLastUpdate){//一次性还款判断是否整个标的还款，还款后新增交易明细 add by cwyang 2018-5-21
                    BigDecimal sum = getRepayPlanAccountSum(borrowNid);
                    logger.info("【直投还款】借款编号：{}，一次性还款插入交易明细。总还款金额：{}", borrowNid, sum);
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
                    repayAllAccountList.setPlanBalance(repayUserAccount.getPlanBalance());//汇计划账户可用余额
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
			logger.info("【直投还款】借款编号：{}，原始投资失败笔数为：{}", borrowNid, failCount);
			if (failCount == 0) {
				repayType = TYPE_WAIT_YES;
				repayStatus = 1;
				repayYesTime = nowTime;
				status = 5;
				// 还款总表
				BorrowRepay borrowRepay = this.getBorrowRepay(borrowNid);
                recoverInterestAmount = borrowRepay.getRepayInterestYes();
				borrowRepay.setRepayType(repayType);
				borrowRepay.setRepayStatus(repayStatus); // 已还款
				borrowRepay.setRepayPeriod(isMonth ? periodNext : 1);
				borrowRepay.setRepayActionTime(nowTime);// 实际还款时间
				borrowRepay.setRepayYestime(repayYesTime);// 还款成功最后时间
				// 更新BorrowRepay
				boolean repayFlag = this.borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay) > 0 ? true : false;
				if (!repayFlag) {
					throw new Exception("还款记录总表(ht_borrow_repay)更新失败！[借款编号：" + borrowNid + "]");
				}
				Account repayUserAccount = this.getAccountByUserId(repayUserId);
				BigDecimal repayAccount = borrowRepay.getRepayAccountYes();
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
				repayUserAccount = this.getAccountByUserId(repayUserId);
				// 插入借款人的收支明细表(原复审业务)
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
				repayAccountList.setAmount(borrowRepay.getRepayAccountYes().add(borrowRepay.getRepayFee())); // 操作金额
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
				boolean repayAccountListFlag = this.accountListMapper.insertSelective(repayAccountList) > 0 ? true : false;
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
				apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
				apicron.setUpdateTime(new Date());
				boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, apicronExample) > 0 ? true : false;
				if (!apicronFlag) {
					throw new Exception("批次还款任务表(ht_borrow_apicron)更新失败！[借款编号：" + borrowNid + "]");
				}
			}
		}
		if(failCount == 0){
			// add by hsy 优惠券还款请求加入到消息队列 start
			Map<String, String> params = new HashMap<String, String>();
			params.put("mqMsgId", GetCode.getRandomCode(10));
			// 借款编号
			params.put("borrowNid", borrowNid);
			// 当前期
			params.put("periodNow", String.valueOf(periodNow));
			//核对参数
			try {
				logger.info("【直投还款/借款人】借款编号:{}，发送优惠券还款队列。", borrowNid);
				commonProducer.messageSend(new MessageContent(MQConstant.HZT_COUPON_REPAY_TOPIC, UUID.randomUUID().toString(), params));
			} catch (MQException e) {
				logger.error("【直投还款/借款人】发送优惠券还款队列时发生系统异常！", e);
			}
			// add by hsy 优惠券还款请求加入到消息队列 end

			// insert by zhangjp 增加优惠券还款区分 start
//				CommonSoaUtils.couponRepay(borrowNid, periodNow);
			// insert by zhangjp 增加优惠券还款区分 end
			try {
				this.sendSmsForManager(borrowNid);
			} catch (Exception e) {
				logger.error("【直投还款/借款人】发送短信时发生系统异常！", e);
			}
		} else if (failCount == repayCount || apicron.getSucCounts() == 0) {
			// 因为还款总比数(repayCount)=债转总比数+原始投资总比数-完全承接总比数。
			// 所以原来的判断原始投资失败总比数(failCount)=还款总比数(repayCount)不能判断所有的还款失败情况
			// update by wgx in 2019/01/29
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
			return CustomConstants.BANK_BATCH_STATUS_PART_FAIL;
		}
		// 更新运营数据
		JSONObject repayParams = new JSONObject();
		repayParams.put("type", 2);// 还款
		repayParams.put("recoverInterestAmount", recoverInterestAmount);// 当期已收利息
		logger.info("【直投还款/借款人】当期已收利息：{}", recoverInterestAmount);
		//发送运营数据队列
        try {
			commonProducer.messageSend(new MessageContent(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC, UUID.randomUUID().toString(), repayParams));
        }catch (MQException e){
			logger.error("【直投还款/借款人】借款编号：{}，发送运营数据更新MQ失败！", borrowNid, e);
        }
		logger.info("【直投还款/借款人】借款编号：{}，更新借款人的还款数据结束。还款期数：{}", apicron.getBorrowNid(), periodNow);
		return CustomConstants.BANK_BATCH_STATUS_SUCCESS;
	}

	/**
	 *  还款后变更出借人资金明细
	 * @param apicron
	 * @param borrow
	 * @param borrowInfo
	 * @param borrowRecover
	 * @param repayDetail
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateTenderRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover, JSONObject repayDetail,boolean creditEndAllFlag) throws Exception {

		logger.info("【直投还款/出借人】借款编号：{}，开始更新出借人相关的还款数据。", apicron.getBorrowNid());
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
		Integer periodNow = apicron.getPeriodNow();
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
		Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();
		/** 出借人数据 */
		// 出借订单号
		String tenderOrderId = borrowRecover.getNid();
		// 出借人用户ID
		Integer tenderUserId = borrowRecover.getUserId();
		// 还款时间
		Integer recoverTime = borrowRecover.getRecoverTime();
		// 还款订单号
		String repayOrderId = borrowRecover.getRepayOrdid();
		/** 基本变量 */
		// 剩余还款期数
		Integer periodNext = borrowPeriod - periodNow;
		// 取得还款详情
		BorrowRepay borrowRepay = getBorrowRepay(borrowNid);
		// 出借信息
		BorrowTender borrowTender = getBorrowTender(tenderOrderId);
		// 出借用户开户信息
		Account tenderBankAccount = getAccountByUserId(tenderUserId);
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
		// 延期天数
		Integer lateDays = 0;
		// 逾期利息
		BigDecimal lateInterest = BigDecimal.ZERO;
		// 延期天数
		Integer delayDays = 0;
		// 延期利息
		BigDecimal delayInterest = BigDecimal.ZERO;
		// 提前天数
		Integer chargeDays = 0;
		// 提前还款少还利息
		BigDecimal chargeInterest = BigDecimal.ZERO;
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
			}
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
			lateInterest = borrowRecoverPlan.getLateInterest().subtract(borrowRecoverPlan.getRepayLateInterest());
			// 延期天数
			delayDays = borrowRecoverPlan.getDelayDays();
			// 延期利息
			delayInterest = borrowRecoverPlan.getDelayInterest().subtract(borrowRecoverPlan.getRepayDelayInterest());
			// 提前天数
			chargeDays = borrowRecoverPlan.getChargeDays();
			// 提前还款少还利息
			chargeInterest = borrowRecoverPlan.getChargeInterest().subtract(borrowRecoverPlan.getRepayChargeInterest());
			// 实际还款本息
			repayAccount = recoverAccountWait.add(lateInterest).add(delayInterest).add(chargeInterest);
			// 实际还款本金
			repayCapital = recoverCapitalWait;
			// 实际还款利息
			repayInterest = recoverInterestWait.add(lateInterest).add(delayInterest).add(chargeInterest);
			// 还款管理费
			manageFee = recoverFee.subtract(recoverFeeYes);
		} else { // endday: 按天计息, end:按月计息
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
			lateInterest = borrowRecover.getLateInterest().subtract(borrowRecover.getRepayLateInterest());
			// 延期天数
			delayDays = borrowRecover.getDelayDays();
			// 延期利息
			delayInterest = borrowRecover.getDelayInterest().subtract(borrowRecover.getRepayDelayInterest());
			// 提前天数
			chargeDays = borrowRecover.getChargeDays();
			// 提前还款少还利息
			chargeInterest = borrowRecover.getChargeInterest().subtract(borrowRecover.getRepayChargeInterest());
			// 实际还款本息
			repayAccount = recoverAccountWait.add(lateInterest).add(delayInterest).add(chargeInterest);
			// 实际还款本金
			repayCapital = recoverCapitalWait;
			// 实际还款利息
			repayInterest = recoverInterestWait.add(lateInterest).add(delayInterest).add(chargeInterest);
			// 还款管理费
			manageFee = recoverFee.subtract(recoverFeeYes);
		}
		// 判断该收支明细存在时,跳出本次循环
		if (countAccountListByNid(repayOrderId)) {
            logger.error("【直投还款/出借人】出借人收支明细已经存在！还款订单号：{}", repayOrderId);
			return true;
		}
		// 更新账户信息(出借人)
		Account tenderAccount = new Account();
		tenderAccount.setUserId(tenderUserId);
		tenderAccount.setBankTotal(lateInterest.add(delayInterest).add(chargeInterest));// 出借人资金总额
		tenderAccount.setBankBalance(repayAccount);// 出借人可用余额
		tenderAccount.setBankAwait(recoverAccountWait);// 出借人待收金额
		tenderAccount.setBankAwaitCapital(recoverCapitalWait);
		tenderAccount.setBankAwaitInterest(recoverInterestWait);
		tenderAccount.setBankInterestSum(repayInterest);
		tenderAccount.setBankBalanceCash(repayAccount);
		boolean investAccountFlag = this.adminAccountCustomizeMapper.updateOfRepayTender(tenderAccount) > 0 ? true : false;
		if (!investAccountFlag) {
			throw new Exception("账户信息表(ht_account)更新失败！[出借人ID：" + tenderUserId + "][出借订单号：" + tenderOrderId + "]");
		}
		// 取得账户信息(出借人)
		tenderAccount = this.getAccountByUserId(borrowTender.getUserId());
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
		accountList.setType(1); // 1收入
		accountList.setTrade("tender_recover_yes"); // 投标成功
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
			throw new Exception("收支明细表(ht_account_list)写入失败！[出借订单号：" + tenderOrderId + "]");
		}
		// 更新放款记录
		// 分期并且不是最后一期
		if (borrowRecoverPlan != null && Validator.isNotNull(periodNext) && periodNext > 0) {
			borrowRecover.setRecoverStatus(0); // 未还款
			// 取得放款记录分期表下一期的放款信息
			BorrowRecoverPlan borrowRecoverPlanNext = getBorrowRecoverPlan(borrowNid, periodNow + 1, tenderUserId, tenderOrderId);
			borrowRecover.setRecoverTime(borrowRecoverPlanNext.getRecoverTime()); // 计算下期时间
			borrowRecover.setRecoverType(TYPE_WAIT);
		} else {
			borrowRecover.setRecoverStatus(1); // 已还款
			borrowRecover.setRecoverYestime(nowTime); // 实际还款时间
			borrowRecover.setRecoverTime(recoverTime);
			borrowRecover.setRecoverType(TYPE_YES);
		}
		// 分期时
		if (borrowRecoverPlan != null) {
			borrowRecover.setRecoverPeriod(periodNext); // 还款期数
		} 
		borrowRecover.setRepayBatchNo(repayBatchNo);
		borrowRecover.setRecoverAccountYes(borrowRecover.getRecoverAccountYes().add(repayAccount));
		borrowRecover.setRecoverCapitalYes(borrowRecover.getRecoverCapitalYes().add(repayCapital));
		borrowRecover.setRecoverInterestYes(borrowRecover.getRecoverInterestYes().add(repayInterest));
		borrowRecover.setRecoverAccountWait(borrowRecover.getRecoverAccountWait().subtract(recoverAccountWait));
		borrowRecover.setRecoverCapitalWait(borrowRecover.getRecoverCapitalWait().subtract(recoverCapitalWait));
		borrowRecover.setRecoverInterestWait(borrowRecover.getRecoverInterestWait().subtract(recoverInterestWait));
		borrowRecover.setRepayChargeInterest(borrowRecover.getRepayChargeInterest().add(chargeInterest));
		borrowRecover.setRepayDelayInterest(borrowRecover.getRepayDelayInterest().add(delayInterest));
		borrowRecover.setRepayLateInterest(borrowRecover.getRepayLateInterest().add(lateInterest));
		borrowRecover.setRecoverFeeYes(borrowRecover.getRecoverFeeYes().add(manageFee));
		borrowRecover.setWeb(2); // 写入网站收支
		boolean borrowRecoverFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover) > 0 ? true : false;
		if (!borrowRecoverFlag) {
			throw new Exception("放款记录总表(ht_borrow_recover)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
		}
		if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
			// 查询相应的债权转让
			List<BorrowCredit> borrowCredits = this.getBorrowCredit(tenderOrderId, periodNow - 1);
			if (borrowCredits != null && borrowCredits.size() > 0) {
				for (int i = 0; i < borrowCredits.size(); i++) {
					// 获取相应的债转记录
					BorrowCredit borrowCredit = borrowCredits.get(i);
					// 债转编号
					int creditNid = borrowCredit.getCreditNid();
					// 债转状态
					if (borrowRecoverPlan != null && Validator.isNotNull(periodNext) && periodNext > 0) {
						borrowCredit.setRepayStatus(0);
					} else {
						borrowCredit.setRepayStatus(1);
						// 债转最后还款时间
						borrowCredit.setCreditRepayYesTime(isMonth ? 0 : nowTime);
					}
					// 债转还款期
					borrowCredit.setRecoverPeriod(periodNow);
					// 债转最近还款时间
					borrowCredit.setCreditRepayLastTime(nowTime);
					// 更新债转标的表
					boolean borrowCreditFlag = this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit) > 0 ? true : false;
					if (!borrowCreditFlag) {
						throw new Exception("债转标的表(ht_borrow_credit)更新失败！[债转编号：" + creditNid + "]");
					}
				}
			}
		}
		// 更新还款记录总的信息
		borrowRepay.setRepayAccountAll(borrowRepay.getRepayAccountAll().add(repayAccount).add(manageFee));
		borrowRepay.setRepayAccountYes(borrowRepay.getRepayAccountYes().add(repayAccount));
		borrowRepay.setRepayCapitalYes(borrowRepay.getRepayCapitalYes().add(repayCapital));
		borrowRepay.setRepayInterestYes(borrowRepay.getRepayInterestYes().add(repayInterest));
		borrowRepay.setLateDays(lateDays);
		borrowRepay.setLateInterest(borrowRepay.getLateInterest().add(lateInterest));
		borrowRepay.setDelayDays(delayDays);
		borrowRepay.setDelayInterest(borrowRepay.getDelayInterest().add(delayInterest));
		borrowRepay.setChargeDays(chargeDays);
		borrowRepay.setChargeInterest(borrowRepay.getChargeInterest().add(chargeInterest));
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
		// 更新标的表
        borrow = getBorrowByNid(borrowNid);
		Borrow newBrrow = new Borrow();
		newBrrow.setId(borrow.getId());
		BigDecimal borrowManager = borrow.getBorrowManager() == null ? BigDecimal.ZERO : new BigDecimal(borrow.getBorrowManager());
		newBrrow.setBorrowManager(borrowManager.add(manageFee).toString());
		newBrrow.setRepayAccountYes(borrow.getRepayAccountYes().add(repayAccount)); // 总还款利息
		newBrrow.setRepayAccountCapitalYes(borrow.getRepayAccountCapitalYes().add(repayCapital)); // 总还款本金
		newBrrow.setRepayAccountInterestYes(borrow.getRepayAccountInterestYes().add(repayInterest)); // 总还款利息
		newBrrow.setRepayAccountWait(borrow.getRepayAccountWait().subtract(recoverAccountWait)); // 未还款总额
		newBrrow.setRepayAccountCapitalWait(borrow.getRepayAccountCapitalWait().subtract(recoverCapitalWait)); // 未还款本金
		newBrrow.setRepayAccountInterestWait(borrow.getRepayAccountInterestWait().subtract(recoverInterestWait)); // 未还款利息
		newBrrow.setRepayFeeNormal(borrow.getRepayFeeNormal().add(manageFee));
		boolean borrowFlag = this.borrowMapper.updateByPrimaryKeySelective(newBrrow) > 0 ? true : false;
		if (!borrowFlag) {
			throw new Exception("标的表(ht_borrow)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
		}
		//BeanUtils.copyProperties(newBrrow, borrow);// 更新还款金额数据 update by wgx 2019/02/22
		logger.info("【智投还款/承接人】借款编号：{}，更新标的表完毕。总还款：{}，未还款总额：{}",
				borrowNid, borrow.getRepayAccountYes(), borrow.getRepayAccountWait());
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
		// 分期时
		if (Validator.isNotNull(borrowRecoverPlan)) {
			// 更新还款计划表
			borrowRecoverPlan.setRepayBatchNo(repayBatchNo);
			borrowRecoverPlan.setRecoverStatus(1); // 已还款
			borrowRecoverPlan.setRecoverYestime(String.valueOf(nowTime));
			borrowRecoverPlan.setRecoverAccountYes(borrowRecoverPlan.getRecoverAccountYes().add(repayAccount));
			borrowRecoverPlan.setRecoverCapitalYes(borrowRecoverPlan.getRecoverCapitalYes().add(repayCapital));
			borrowRecoverPlan.setRecoverInterestYes(borrowRecoverPlan.getRecoverInterestYes().add(repayInterest));
			borrowRecoverPlan.setRecoverAccountWait(borrowRecoverPlan.getRecoverAccountWait().subtract(recoverAccountWait));
			borrowRecoverPlan.setRecoverCapitalWait(borrowRecoverPlan.getRecoverCapitalWait().subtract(recoverCapitalWait));
			borrowRecoverPlan.setRecoverInterestWait(borrowRecoverPlan.getRecoverInterestWait().subtract(recoverInterestWait));
			borrowRecoverPlan.setRepayChargeInterest(borrowRecoverPlan.getRepayChargeInterest().add(chargeInterest));
			borrowRecoverPlan.setRepayDelayInterest(borrowRecoverPlan.getRepayDelayInterest().add(delayInterest));
			borrowRecoverPlan.setRepayLateInterest(borrowRecoverPlan.getRepayLateInterest().add(lateInterest));
			borrowRecoverPlan.setRecoverFeeYes(borrowRecoverPlan.getRecoverFeeYes().add(manageFee));
			borrowRecoverPlan.setRecoverType(TYPE_YES);
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
				borrowRepayPlan.setDelayDays(delayDays);
				borrowRepayPlan.setDelayInterest(borrowRepayPlan.getDelayInterest().add(delayInterest));
				borrowRepayPlan.setChargeDays(chargeDays);
				borrowRepayPlan.setChargeInterest(borrowRepayPlan.getChargeInterest().add(chargeInterest));
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
			throw new Exception("批次还款任务表(ht_borrow_apicron)更新失败！[借款编号：" + borrowNid + "]");
		}
        // 结束债权
		if (!isMonth || (isMonth && periodNext == 0)) {
			if (creditEndAllFlag) {
				boolean debtOverFlag = this.requestDebtEnd(borrowRecover.getUserId(), repayDetail,borrowRecover.getNid(), borrowInfo);
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
            // 网站收支明细记录
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
            //网站收支明细队列
            try {
                logger.info("【直投还款/出借人】发送网站收支明细。还款人ID：{}，管理费：{}", repayUserId, manageFee);
                commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebList));
            } catch (MQException e) {
                logger.error("【直投还款/出借人】发送网站收支明细时发生系统异常！", e);
            }
        }
        try {
            // 发送短信
            this.sendSms(tenderUserId, borrowNid, repayCapital, repayInterest);
            // 推送消息
            this.sendMessage(tenderUserId, borrowNid, repayAccount, repayInterest);
        } catch (Exception e) {
            logger.error("【直投还款/出借人】发送短信和推送消息时发生系统异常！", e);
        }
		// 直投类还款成功之后， 判断是风车理财的出借，发送到队列，准备回调通知
		if(CustomConstants.WRB_CHANNEL_CODE.equals(borrowTender.getTenderFrom())){
			Map<String, String> params = new HashMap<>();
			params.put("userId", String.valueOf(borrowTender.getUserId()));
			//出借订单号
			params.put("nid", borrowTender.getNid());
			// 2-回款回调
			params.put("returnType", "2");
			// 还款时间
			params.put("backTime", GetDate.formatDateTime(System.currentTimeMillis()));
			// 还款金额
			params.put("backMoney", repayAccount.toString());
			//风车理财队列
			try {
                logger.info("【直投还款/出借人】风车理财发送到队列。出借订单号：{}，还款金额：{}", borrowTender.getNid(), repayAccount.toString());
                commonProducer.messageSend(new MessageContent(MQConstant.WRB_QUEUE_CALLBACK_NOTIFY_TOPIC, UUID.randomUUID().toString(), params));
			} catch (Exception e) {
				logger.error("风车理财还款通知入列失败...", e);
			}
		}
		logger.info("【直投还款/出借人】借款编号：{}，更新出借人的还款数据结束。还款订单号：{}", apicron.getBorrowNid(), repayOrderId);
		return true;
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
				borrowRecoverPlan.setRecoverStatus(2);
				boolean flag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
				if (!flag) {
					throw new Exception("放款记录分期表(ht_borrow_recover_plan)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]，[期数：" + periodNow + "]");
				}
			}
		} else {
			borrowRecover.setRecoverStatus(2);
			boolean flag = this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover) > 0 ? true : false;
			if (!flag) {
				throw new Exception("放款记录总表(ht_borrow_recover)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
			}
		}
		return true;
	}

	@Override
	public boolean updateTenderRepayStatus(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception {

		logger.info("【直投还款/出借人】借款编号：{}，开始更新出借人相关的还款数据。", apicron.getBorrowNid());
		/** 还款信息 */
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 借款编号
		String borrowNid = apicron.getBorrowNid();
		// 还款期数
		Integer periodNow = apicron.getPeriodNow();
		String repayBatchNo = apicron.getBatchNo();
		/** 标的基本数据 */
		// 还款期数
		Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();
		/** 出借人数据 */
		// 出借订单号
		String tenderOrderId = borrowRecover.getNid();
		// 出借人用户ID
		Integer tenderUserId = borrowRecover.getUserId();
		// 还款时间
		Integer recoverTime = borrowRecover.getRecoverTime();
		// 还款订单号
		String repayOrderId = borrowRecover.getRepayOrdid();
		/** 基本变量 */
		// 剩余还款期数
		Integer periodNext = borrowPeriod - periodNow;
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
			}
			// 还款订单号
			repayOrderId = borrowRecoverPlan.getRepayOrderId();
			// 应还款时间
			recoverTime = borrowRecoverPlan.getRecoverTime();
		} else { // endday: 按天计息, end:按月计息
			// 还款订单号
			repayOrderId = borrowRecover.getRepayOrdid();
			// 还款时间
			recoverTime = borrowRecover.getRecoverTime();
		}
		// 更新放款记录
		// 分期并且不是最后一期
		if (borrowRecoverPlan != null && Validator.isNotNull(periodNext) && periodNext > 0) {
			borrowRecover.setRecoverStatus(0); // 未还款
			// 取得放款记录分期表下一期的放款信息
			BorrowRecoverPlan borrowRecoverPlanNext = getBorrowRecoverPlan(borrowNid, periodNow + 1, tenderUserId, tenderOrderId);
			borrowRecover.setRecoverTime(borrowRecoverPlanNext.getRecoverTime()); // 计算下期时间
			borrowRecover.setRecoverType(TYPE_WAIT);
		} else {
			borrowRecover.setRecoverStatus(1); // 已还款
			borrowRecover.setRecoverYestime(nowTime); // 实际还款时间
			borrowRecover.setRecoverTime(recoverTime);
			borrowRecover.setRecoverType(TYPE_YES);
		}
		// 分期时
		if (borrowRecoverPlan != null) {
			borrowRecover.setRecoverPeriod(periodNext);
		}
		borrowRecover.setRepayBatchNo(repayBatchNo);
		boolean borrowRecoverFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover) > 0 ? true : false;
		if (!borrowRecoverFlag) {
			throw new Exception("放款记录总表(ht_borrow_recover)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
		}
		if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
			// 查询相应的债权转让
			List<BorrowCredit> borrowCredits = this.getBorrowCredit(tenderOrderId, periodNow - 1);
			if (borrowCredits != null && borrowCredits.size() > 0) {
				for (int i = 0; i < borrowCredits.size(); i++) {
					// 获取相应的债转记录
					BorrowCredit borrowCredit = borrowCredits.get(i);
					// 债转编号
					int creditNid = borrowCredit.getCreditNid();
					// 债转状态
					if (borrowRecoverPlan != null && Validator.isNotNull(periodNext) && periodNext > 0) {
						borrowCredit.setRepayStatus(0);
					} else {
						borrowCredit.setRepayStatus(1);
						// 债转最后还款时间
						borrowCredit.setCreditRepayYesTime(isMonth ? 0 : nowTime);
					}
					// 债转还款期
					borrowCredit.setRecoverPeriod(periodNow);
					// 债转最近还款时间
					borrowCredit.setCreditRepayLastTime(nowTime);
					// 更新债转标的表
					boolean borrowCreditFlag = this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit) > 0 ? true : false;
					if (!borrowCreditFlag) {
						throw new Exception("债转标的表(ht_borrow_credit)更新失败！[债转编号：" + creditNid + "]，[出借订单号：" + tenderOrderId + "]");
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
			boolean borrowRecoverPlanFlag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
			if (!borrowRecoverPlanFlag) {
				throw new Exception("放款记录分期表(ht_borrow_recover_plan)更新失败！[借款编号：" + borrowNid + "]，[出借订单号：" + tenderOrderId + "]");
			}
			// 更新还款记录分期信息
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
		logger.info("【直投还款/出借人】借款编号：{}，更新出借人的还款数据结束。还款订单号：{}", apicron.getBorrowNid(), repayOrderId);
		return true;
	}

	private List<BorrowCredit> getBorrowCredit(String tenderOrderId, Integer periodNow) {
		BorrowCreditExample example = new BorrowCreditExample();
		BorrowCreditExample.Criteria crt = example.createCriteria();
		crt.andTenderNidEqualTo(tenderOrderId);
		crt.andRecoverPeriodEqualTo(periodNow);
		List<BorrowCredit> borrowCredits = this.borrowCreditMapper.selectByExample(example);
		return borrowCredits;
	}

	/**
	 * 发送短信(还款成功)
	 *
	 * @param userId
	 */
	private void sendSms(int userId, String borrowNid, BigDecimal repayCapital, BigDecimal repayInterest) {
		if (Validator.isNotNull(userId) && Validator.isNotNull(repayCapital)) {
 			Map<String, String> msg = new HashMap<>();
			msg.put("userId", String.valueOf(userId));
			msg.put("val_borrownid", borrowNid);
			msg.put("val_capital", repayCapital.toString());
			msg.put("val_interest", repayInterest.toString());
			SmsMessage smsMessage = new SmsMessage(Integer.valueOf(userId), msg, null, null, MessageConstant.SMS_SEND_FOR_USER, null, CustomConstants.PARAM_TPL_SHOUDAOHUANKUAN,
					CustomConstants.CHANNEL_TYPE_NORMAL);
			try {
				commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, String.valueOf(userId), smsMessage));
			} catch (MQException e2) {
				logger.error("【直投还款】发送短信失败..", e2);
			}
		}
	}

	/**
	 * 推送消息
	 * 
	 * @author Administrator
	 */
	private void sendMessage(int userId, String borrowNid, BigDecimal repayAccount, BigDecimal repayInterest) {
		if (Validator.isNotNull(userId) && Validator.isNotNull(repayAccount)) {
			Map<String, String> msg = new HashMap<>();
			msg.put("userId", String.valueOf(userId));
			msg.put("val_borrownid", borrowNid);
			msg.put("val_amount", repayAccount.toString());
			msg.put("val_interest", repayInterest.toString());

			AppMsMessage smsMessage = new AppMsMessage(Integer.valueOf(msg.get("userId")), msg, null,
					MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_SHOUDAOHUANKUAN);
			try {
				commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, String.valueOf(userId),
						smsMessage));
			} catch (MQException e) {
				logger.error("【直投还款】发送app消息失败..", e);
			}
		}
	}

	private BigDecimal getRepayPlanAccountSum(String borrowNid) {
		BorrowApicronExample apicronExample = new BorrowApicronExample();
		apicronExample.createCriteria().andBorrowNidEqualTo(borrowNid).andIsAllrepayEqualTo(1);
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
	 * @return
	 */
    private boolean isLastAllRepay(String borrowNid, Integer periodNow, boolean isAllRepay) {
        if (!isAllRepay) {
            return false;
        }
        BorrowApicronExample example = new BorrowApicronExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid).andApiTypeEqualTo(1)
                .andPeriodNowNotEqualTo(periodNow).andStatusNotEqualTo(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
        int borrowApicronCount = this.borrowApicronMapper.countByExample(example);
        if (borrowApicronCount > 0) {
            return false;
        }
        return true;
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
			logger.error("【直投还款】发送短信失败..", e2);
		}
	}

    /**
     * 调用机构垫付还款
     * @author wgx
     * @date 2018/10/18
     */
    private Map requestOrgRepay(Borrow borrow, String orgAccountId, BorrowApicron apicron, List<BorrowRecover> borrowRecoverList, List<BorrowRecoverPlan> borrowRecoverPlanList,
                                List<CreditRepay> creditRepayList) throws Exception {
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
                CreditRepay creditRepay = creditRepayList.get(i);
                int assignUserId = creditRepay.getUserId();// 承接用户userId
                String creditRepayOrderId = creditRepay.getCreditRepayOrderId();// 债转还款订单号
                BigDecimal txAmount = creditRepay.getAssignCapital();// 交易金额
                BigDecimal intAmount = creditRepay.getAssignInterest().add(creditRepay.getChargeInterest()).add(creditRepay.getDelayInterest()).add(creditRepay.getLateInterest());// 交易利息
                BigDecimal serviceFee = creditRepay.getManageFee();// 还款管理费
                repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
                txAmountSum = txAmountSum.add(txAmount);// 交易总金额
                serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
                String authCode = creditRepay.getAuthCode();// 出借授权码
                // 承接用户的开户信息
                Account bankOpenAccount = this.getAccountByUserId(assignUserId);
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
                JSONObject subJson = new JSONObject();// 结果集对象
                int tenderUserId = borrowRecover.getUserId();// 出借用户userId
                String recoverRepayOrderId = borrowRecover.getRepayOrdid();// 还款订单号
                BigDecimal txAmount = borrowRecover.getRecoverCapitalWait();// 交易金额
                BigDecimal intAmount = borrowRecover.getRecoverInterestWait().add(borrowRecover.getChargeInterest()).add(borrowRecover.getDelayInterest()).add(borrowRecover.getLateInterest());// 交易利息
                BigDecimal serviceFee = borrowRecover.getRecoverFee();// 还款管理费
                txAmountSum = txAmountSum.add(txAmount);// 交易总金额
                repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
                serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
                String authCode = borrowRecover.getAuthCode();// 出借授权码
                // 出借用户的开户信息
                Account bankOpenAccount = this.getAccountByUserId(tenderUserId);
                // 判断出借用户开户信息
                if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
                    subJson = getOrgSubJson(orgAccountId, borrowNid, recoverRepayOrderId, txAmount, intAmount, serviceFee, authCode, bankOpenAccount);
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
                    JSONObject subJson = new JSONObject();// 结果集对象
                    int tenderUserId = borrowRecoverPlan.getUserId();// 出借用户userId
                    String recoverPlanRepayOrderId = borrowRecoverPlan.getRepayOrderId();
                    BigDecimal txAmount = borrowRecoverPlan.getRecoverCapitalWait();// 交易金额
                    BigDecimal intAmount = borrowRecoverPlan.getRecoverInterestWait().add(borrowRecoverPlan.getChargeInterest()).add(borrowRecoverPlan.getDelayInterest())
                            .add(borrowRecoverPlan.getLateInterest());// 交易利息
                    BigDecimal serviceFee = borrowRecoverPlan.getRecoverFee();// 还款管理费
                    repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
                    txAmountSum = txAmountSum.add(txAmount);// 交易总金额
                    serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
                    String authCode = borrowRecoverPlan.getAuthCode();// 出借授权码
                    // 出借用户的开户信息
                    Account bankOpenAccount = this.getAccountByUserId(tenderUserId);
                    // 判断出借用户开户信息
                    if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
                        subJson = getOrgSubJson(orgAccountId, borrowNid, recoverPlanRepayOrderId, txAmount, intAmount, serviceFee, authCode, bankOpenAccount);
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
			logger.error("【直投批次代偿】还款请求失败！", e);
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
    private JSONObject getOrgSubJson(String accountId, String borrowNid, String recoverRepayOrderId, BigDecimal txAmount, BigDecimal intAmount, BigDecimal serviceFee, String authCode, Account bankOpenAccount) {
		JSONObject subJson = new JSONObject();
    	String forAccountId = bankOpenAccount.getAccountId();// 银行账户
        subJson.put(BankCallConstant.PARAM_ACCOUNTID, accountId);           // 存管子账户             accountId    担保户
        subJson.put(BankCallConstant.PARAM_ORDERID, recoverRepayOrderId);   // 订单号                 orderId      由P2P生成，必须保证唯一
        subJson.put(BankCallConstant.PARAM_TXAMOUNT, txAmount.toString());  // 交易金额               txAmount     代偿本金
        subJson.put(BankCallConstant.PARAM_RISKAMOUNT, "");                 // 风险准备金             riskAmount   暂不做使用，请送空
        subJson.put(BankCallConstant.PARAM_INTAMOUNT, intAmount.toString());// 交易利息               intAmount
        subJson.put(BankCallConstant.PARAM_TXFEEIN, "0.00");                // 收款手续费             txFeeIn      向出借人收取的手续费
        subJson.put(BankCallConstant.PARAM_FINEAMOUNT, "");                 // 罚息金额               fineAmount   暂不做使用，请送空
        subJson.put(BankCallConstant.PARAM_FORACCOUNTID, forAccountId);     // 对手存管子账户         forAccountId 出借人账号
        subJson.put(BankCallConstant.PARAM_PRODUCTID, borrowNid);           // 借款编号                 productId    出借人投标成功的借款编号
        subJson.put(BankCallConstant.PARAM_AUTHCODE, authCode);             // 授权码                 authCode     出借人投标成功的授权号
        subJson.put(BankCallConstant.PARAM_SEQFLAG, "");                    // 流水是否经过借款人标志 seqFlag      担保人资金是否过借款人标志 1：经过;空：不经过；
        subJson.put(BankCallConstant.PARAM_TXFEEOUT, serviceFee.toString());// 转出方手续费金额       txFeeOut     扣收担保人手续费
        return subJson;
    }

    /**
     * 批次代偿（合规要求）
     * @author wgx
     * @date 2018/10/18
     */
    private Map requestOrgRepay(BorrowApicron apicron, JSONArray subPacksJson) {
        Map map = new HashMap<>();
        boolean delFalg = false;
        int userId = apicron.getUserId();// 还款用户userId
        String borrowNid = apicron.getBorrowNid();// 借款编号
        // 还款子参数
        String subPacks = subPacksJson.toJSONString();
        // 获取共同参数
        String notifyUrl = systemConfig.getRepayVerifyUrl();
        String retNotifyURL = systemConfig.getRepayResultUrl();
        String channel = BankCallConstant.CHANNEL_PC;
        for (int i = 0; i < 3; i++) {
            try {
                String logOrderId = GetOrderIdUtils.getOrderId2(apicron.getUserId());
                String orderDate = GetOrderIdUtils.getOrderDate();
                String batchNo = GetOrderIdUtils.getBatchNo();// 获取还款批次号
                String txDate = GetOrderIdUtils.getTxDate();
                String txTime = GetOrderIdUtils.getTxTime();
                String seqNo = GetOrderIdUtils.getSeqNo(6);
                apicron.setBatchNo(batchNo);
                apicron.setTxDate(Integer.parseInt(txDate));
                apicron.setTxTime(Integer.parseInt(txTime));
                apicron.setSeqNo(Integer.parseInt(seqNo));
                apicron.setBankSeqNo(txDate + txTime + seqNo);
                // 更新任务API状态为进行中
                boolean apicronFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_SENDING);
                if (!apicronFlag) {
                    throw new Exception("【直投批次代偿】更新还款任务为进行中失败！[借款人用户ID：" + userId + "]，[借款编号：" + borrowNid + "]");
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
                repayBean.setLogRemark("批次代偿还款请求");
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
                                map.put("delFlag", delFalg);
                                return map;
                            }
                        } catch (Exception e) {
							logger.error("【直投批次代偿】还款请求成功后,更新任务状态(还款请求成功)异常！还款用户ID：{}，", userId, e);
                        }
                        map.put("result", repayResult);
                        map.put("delFlag", true);
                        return map;
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("result", null);
        map.put("delFlag", delFalg);
        return map;
    }
}
