/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.AccountListExample;
import com.hyjf.am.trade.dao.model.auto.BankCreditEnd;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowApicronExample;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.BorrowCreditExample;
import com.hyjf.am.trade.dao.model.auto.BorrowExample;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverExample;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlanExample;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayExample;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlanExample;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderExample;
import com.hyjf.am.trade.dao.model.auto.CreditRepay;
import com.hyjf.am.trade.dao.model.auto.CreditRepayExample;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.auto.CreditTenderExample;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.AppMessageProducer;
import com.hyjf.am.trade.mq.producer.MailProducer;
import com.hyjf.am.trade.mq.producer.SmsProducer;
import com.hyjf.am.trade.service.front.batch.BatchBorrowRepayZTService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
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
	private MailProducer mailProducer;
    
	@Autowired
	private SmsProducer smsProducer;

	@Autowired
	private AppMessageProducer appMessageProducer;

    @Autowired
    SystemConfig systemConfig;
    
    
	@Override
	public Map requestRepay(BorrowApicron apicron) {

		int repayUserId = apicron.getUserId();// 还款用户userId
		String borrowNid = apicron.getBorrowNid();// 项目编号
		Integer periodNow = apicron.getPeriodNow();// 当前期数
		//int isApicronRepayOrgFlag = Validator.isNull(apicron.getIsRepayOrgFlag()) ? 0 : apicron.getIsRepayOrgFlag();// 是否是担保机构还款
		// 取得借款人账户信息
		Account repayAccount = this.getAccountByUserId(repayUserId);
		if (repayAccount == null || repayAccount.getAccountId() == null) {
			throw new RuntimeException("借款人账户不存在。[用户ID：" + repayUserId + "]," + "[借款编号：" + borrowNid + "]");
		}
		String repayAccountId = repayAccount.getAccountId();// 借款人相应的银行账号
		// 取得借款详情
		Borrow borrow = this.getBorrowByNid(borrowNid);
		// 取得借款详情
		BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
		if (borrow == null || borrowInfo == null) {
			throw new RuntimeException("借款详情不存在。[用户ID：" + repayUserId + "]," + "[借款编号：" + borrowNid + "]");
		}
		String borrowStyle = borrow.getBorrowStyle();
		// 标的是否可用担保机构还款
		//int isRepayOrgFlag = Validator.isNull(borrow.getIsRepayOrgFlag()) ? 0 : borrow.getIsRepayOrgFlag();
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// 取得投资详情列表
		List<BorrowRecover> listRecovers = this.getBorrowRecoverList(borrowNid);
		// 是否有待还款记录
		if (listRecovers == null || listRecovers.size() == 0) {
			//return false;
			throw new RuntimeException("还款记录表数据不存在。[借款编号：" + borrowNid + "]，" + "[期数：" + periodNow + "]");
		}
		//是否先息后本
		boolean isEndMonth = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// 还款请求列表
		List<BorrowRecover> recoverList = new ArrayList<BorrowRecover>();
		// 取得投资详情列表
		List<BorrowRecoverPlan> recoverPlanList = new ArrayList<BorrowRecoverPlan>();
		// 查询此笔债转承接的还款情况
		List<CreditRepay> creditRepayList = new ArrayList<CreditRepay>();
		try {
			/** 循环投资详情列表 */
			for (int i = 0; i < listRecovers.size(); i++) {
				// 获取还款信息
				BorrowRecover borrowRecover = listRecovers.get(i);
				// 投资订单号
				String tenderOrderId = borrowRecover.getNid();
				// 分期的还款信息
				BorrowRecoverPlan borrowRecoverPlan = null;
				if (isMonth) {
					// 取得分期还款计划表
					borrowRecoverPlan = this.getBorrowRecoverPlan(borrowNid, periodNow, borrowRecover.getUserId(), tenderOrderId);
					if (Validator.isNull(borrowRecoverPlan)) {
						throw new RuntimeException("分期还款计划表数据不存在。[借款编号：" + borrowNid + "]，" + "[投资订单号：" + tenderOrderId + "]，" + "[期数：" + periodNow + "]");
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
							throw new RuntimeException("添加还款订单号，更新borrow_recover_plan表失败" + "，[投资订单号：" + tenderOrderId + "]");
						}
						// 设置还款订单号
						borrowRecover.setRepayOrdid(borrowRecoverPlan.getRepayOrderId());
						// 设置还款时间
						borrowRecover.setRepayOrddate(borrowRecoverPlan.getRepayOrderDate());
						// 更新还款信息
						boolean flag = this.updateBorrowRecover(borrowRecover);
						if (!flag) {
							throw new RuntimeException("添加还款订单号，更新borrow_recover表失败" + "，[投资订单号：" + tenderOrderId + "]");
						}
					}
					
				}
				// [endday: 按天计息, end:按月计息]
				else {
					// 保存还款订单号
					if (StringUtils.isBlank(borrowRecover.getRepayOrdid())) {
						// 设置还款订单号
						borrowRecover.setRepayOrdid(GetOrderIdUtils.getOrderId2(borrowRecover.getUserId()));
						// 设置还款时间
						borrowRecover.setRepayOrddate(GetOrderIdUtils.getOrderDate());
						// 更新还款信息
						boolean flag = this.updateBorrowRecover(borrowRecover);
						if (!flag) {
							throw new RuntimeException("添加还款订单号，更新borrow_recover表失败" + "，[投资订单号：" + tenderOrderId + "]");
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
									throw new RuntimeException("添加还款订单号，更新credit_repay表失败" + "，[投资订单号：" + tenderOrderId + "]");
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
			if (isMonth) {
				// 如果是垫付机构还款
				/*if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
					// 调用相应的担保机构还款接口
					repayResult = this.requestOrgRepay(borrow, repayAccountId, apicron, null, recoverPlanList, creditRepayList);
				} else {*/
					// 调用相应的担保机构还款接口
				 Map resultMap = this.requestRepay(borrow, borrowInfo, repayAccountId, apicron, null, recoverPlanList, creditRepayList);
				 repayResult = (BankCallBean) resultMap.get("result");
				 delFlag = (boolean) resultMap.get("delFlag");
				/*}*/
			} else {
				// 如果是垫付机构还款
				/*if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
					// 调用相应的担保机构还款接口
					repayResult = this.requestOrgRepay(borrow, repayAccountId, apicron, recoverList, null, creditRepayList);
				} else {*/
					// 调用相应的担保机构还款接口
					Map resultMap = this.requestRepay(borrow, borrowInfo, repayAccountId, apicron, recoverList, null, creditRepayList);
					repayResult = (BankCallBean) resultMap.get("result");
					delFlag = (boolean) resultMap.get("delFlag");
				/*}*/
			}
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
			e.printStackTrace();
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
			logger.info(apicron.getBorrowNid()+" 直投还款批次状态查询返回  "+retCode+"  "+queryResult.getRetMsg());
			if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
				return queryResult;
			}
		}
		
		return null;
	}
	
	/**
	 * 根据主键从主库查询apicron 表
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
	private Map requestRepay(Borrow borrow, BorrowInfo borrowInfo, String borrowAccountId, BorrowApicron apicron, List<BorrowRecover> borrowRecoverList, List<BorrowRecoverPlan> borrowRecoverPlanList,
			List<CreditRepay> creditRepayList) throws Exception {
		String borrowNid = borrow.getBorrowNid();
		boolean delFlag = false;
		//TODO 还款请求组装数据
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
				JSONObject subJson = new JSONObject();// 结果集对象
				int assignUserId = creditRepay.getUserId();// 承接用户userId
				String creditRepayOrderId = creditRepay.getCreditRepayOrderId();// 债转还款订单号
				BigDecimal txAmount = creditRepay.getAssignCapital();// 交易金额
				BigDecimal intAmount = creditRepay.getAssignInterest().add(creditRepay.getChargeInterest()).add(creditRepay.getDelayInterest()).add(creditRepay.getLateInterest());// 交易利息
				BigDecimal serviceFee = creditRepay.getManageFee();// 还款管理费
				repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
				txAmountSum = txAmountSum.add(txAmount);// 交易总金额
				serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
				String authCode = creditRepay.getAuthCode();// 投资授权码
				// 承接用户的开户信息
				Account bankOpenAccount = this.getAccountByUserId(assignUserId);
				// 判断承接用户开户信息
				if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
					String accountId = bankOpenAccount.getAccountId();// 银行账户
					subJson.put(BankCallConstant.PARAM_ACCOUNTID, borrowAccountId);// 融资人账号
					subJson.put(BankCallConstant.PARAM_ORDERID, creditRepayOrderId);// 订单号
					subJson.put(BankCallConstant.PARAM_TXAMOUNT, txAmount.toString());// 交易金额
					subJson.put(BankCallConstant.PARAM_INTAMOUNT, intAmount.toString());// 交易金额
					subJson.put(BankCallConstant.PARAM_TXFEEOUT, serviceFee.toString());
					subJson.put(BankCallConstant.PARAM_FORACCOUNTID, accountId);
					subJson.put(BankCallConstant.PARAM_PRODUCTID, borrowNid);
					subJson.put(BankCallConstant.PARAM_AUTHCODE, authCode);
					subPacksJson.add(subJson);
				} else {
					throw new Exception("还款时未查询到用户的银行开户信息。[用户userId：" + assignUserId + "]");
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
				int tenderUserId = borrowRecover.getUserId();// 投资用户userId
				String recoverRepayOrderId = borrowRecover.getRepayOrdid();// 还款订单号
				BigDecimal txAmount = borrowRecover.getRecoverCapitalWait();// 交易金额
				BigDecimal intAmount = borrowRecover.getRecoverInterestWait().add(borrowRecover.getChargeInterest()).add(borrowRecover.getDelayInterest()).add(borrowRecover.getLateInterest());// 交易利息
				BigDecimal serviceFee = borrowRecover.getRecoverFee();// 还款管理费
				txAmountSum = txAmountSum.add(txAmount);// 交易总金额
				repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
				serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
				String authCode = borrowRecover.getAuthCode();// 投资授权码
				// 投资用户的开户信息
				Account bankOpenAccount = this.getAccountByUserId(tenderUserId);
				// 判断投资用户开户信息
				if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
					String accountId = bankOpenAccount.getAccountId();// 银行账户
					subJson.put(BankCallConstant.PARAM_ACCOUNTID, borrowAccountId);// 融资人账号
					subJson.put(BankCallConstant.PARAM_ORDERID, recoverRepayOrderId);// 订单号
					subJson.put(BankCallConstant.PARAM_TXAMOUNT, txAmount.toString());// 交易金额
					subJson.put(BankCallConstant.PARAM_INTAMOUNT, intAmount.toString());// 交易金额
					subJson.put(BankCallConstant.PARAM_TXFEEOUT, serviceFee.toString());
					subJson.put(BankCallConstant.PARAM_FORACCOUNTID, accountId);
					subJson.put(BankCallConstant.PARAM_PRODUCTID, borrowNid);
					subJson.put(BankCallConstant.PARAM_AUTHCODE, authCode);
					subPacksJson.add(subJson);
				} else {
					throw new Exception("还款时未查询到用户的银行开户信息。[用户userId：" + tenderUserId + "]");
				}
			}
			txCounts = txCounts + borrowRecoverList.size();
		} else {
			if (borrowRecoverPlanList != null && borrowRecoverPlanList.size() > 0) {
				for (int i = 0; i < borrowRecoverPlanList.size(); i++) {
					// 获取分期的放款信息
					BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlanList.get(i);
					JSONObject subJson = new JSONObject();// 结果集对象
					int tenderUserId = borrowRecoverPlan.getUserId();// 投资用户userId
					String recoverPlanRepayOrderId = borrowRecoverPlan.getRepayOrderId();
					BigDecimal txAmount = borrowRecoverPlan.getRecoverCapitalWait();// 交易金额
					BigDecimal intAmount = borrowRecoverPlan.getRecoverInterestWait().add(borrowRecoverPlan.getChargeInterest()).add(borrowRecoverPlan.getDelayInterest())
							.add(borrowRecoverPlan.getLateInterest());// 交易利息
					BigDecimal serviceFee = borrowRecoverPlan.getRecoverFee();// 还款管理费
					repayAmountSum = repayAmountSum.add(txAmount).add(intAmount).add(serviceFee);
					txAmountSum = txAmountSum.add(txAmount);// 交易总金额
					serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
					String authCode = borrowRecoverPlan.getAuthCode();// 投资授权码
					// 投资用户的开户信息
					Account bankOpenAccount = this.getAccountByUserId(tenderUserId);
					// 判断投资用户开户信息
					if (Validator.isNotNull(bankOpenAccount) && StringUtils.isNotBlank(bankOpenAccount.getAccountId())) {
						String accountId = bankOpenAccount.getAccountId();// 银行账户
						subJson.put(BankCallConstant.PARAM_ACCOUNTID, borrowAccountId);// 融资人账号
						subJson.put(BankCallConstant.PARAM_ORDERID, recoverPlanRepayOrderId);// 订单号
						subJson.put(BankCallConstant.PARAM_TXAMOUNT, txAmount.toString());// 交易金额
						subJson.put(BankCallConstant.PARAM_INTAMOUNT, intAmount.toString());// 交易金额
						subJson.put(BankCallConstant.PARAM_TXFEEOUT, serviceFee.toString());
						subJson.put(BankCallConstant.PARAM_FORACCOUNTID, accountId);
						subJson.put(BankCallConstant.PARAM_PRODUCTID, borrowNid);
						subJson.put(BankCallConstant.PARAM_AUTHCODE, authCode);
						subPacksJson.add(subJson);
					} else {
						throw new Exception("还款时未查询到用户的银行开户信息。[用户userId：" + tenderUserId + "]");
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
						throw new Exception("更新放款任务为放款请求失败失败。[用户ID：" + apicron.getUserId() + "]," + "[借款编号：" + borrowNid + "]");
					}
				} else {
					throw new Exception("放款请求失败。[用户ID：" + apicron.getUserId() + "]," + "[借款编号：" + borrowNid + "]");
				}
			} else {
				throw new Exception("放款请求失败。[用户ID：" + apicron.getUserId()  + "]," + "[借款编号：" + borrowNid + "]");
			}
		} catch (Exception e) {
			logger.info("-----------------------------------放款请求失败,错误信息:" + e.getMessage());
		}
		map.put("result", repayResult);
		map.put("delFlag", delFlag);
		return map;
	}

	/**
	 * 自动扣款（还款）(调用汇付天下接口)
	 *
	 * @param outCustId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map requestRepay(BorrowApicron apicron, JSONArray subPacksJson) {
		Map map = new HashMap<>();
		boolean delFalg = false;
		int userId = apicron.getUserId();// 还款用户userId
		String borrowNid = apicron.getBorrowNid();// 项目编号
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
					throw new Exception("更新还款任务为进行中失败。[用户ID：" + userId + "]," + "[借款编号：" + borrowNid + "]");
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
							logger.info("------------------------标的号:" + borrowNid + ",还款请求成功后,变更任务状态异常!");
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

	private Borrow getBorrowByNid(String borrowNid) {
		BorrowExample example = new BorrowExample();
		BorrowExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<Borrow> list = borrowMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 取得还款明细列表
	 *
	 * @return
	 */
	private List<BorrowRecover> getBorrowRecoverList(String borrowNid) {
		BorrowRecoverExample example = new BorrowRecoverExample();
		BorrowRecoverExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andRecoverStatusNotEqualTo(1); // 0初始 1还款成功 2还款失败
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
	 * @param i
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
	 * @param borrowRecoverPlan
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
//		apicron.setUpdateTime(nowTime);
		boolean apicronFlag = this.borrowApicronMapper.updateByPrimaryKeySelective(apicron) > 0 ? true : false;
		if (!apicronFlag) {
			throw new Exception("更新还款任务失败。[项目编号：" + borrowNid + "]");
		}
		
		Borrow borrow = this.getBorrowByNid(borrowNid);
		borrow.setRepayStatus(status);
		boolean borrowFlag = this.borrowMapper.updateByPrimaryKey(borrow) > 0 ? true : false;
		if (!borrowFlag) {
			throw new Exception("更新borrow失败。[项目编号：" + borrowNid + "]");
		}
		
		return borrowFlag;
	}
	
	@Override
	public boolean reapyBatchDetailsUpdate(BorrowApicron apicron) {

		String borrowNid = apicron.getBorrowNid();// 項目编号
		Borrow borrow = this.getBorrowByNid(borrowNid);
		BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
		// 调用批次查询接口查询批次返回结果
		List<BankCallBean> resultBeans = this.queryBatchDetails(apicron);
		// 还款成功后后续操作
		try {
			// 给标的的投资人更新明细，加钱，结束债权
			boolean repayFlag = this.debtRepays(apicron, borrow, borrowInfo, resultBeans);
			if (repayFlag) {
				try {
					// 更新标的借款人余额，交易明细，标的表批次表的状态
					boolean borrowFlag = this.updateBorrowStatus(apicron, borrow, borrowInfo);
					if (borrowFlag) {
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;

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
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// 剩余还款期数
		Integer periodNext = borrowPeriod - periodNow;
		boolean apicronFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_DOING);
		if (!apicronFlag) {
			throw new Exception("更新borrowApicron表失败，" + "[银行唯一订单号：" + apicron.getBankSeqNo() + "]");
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
			// 取得投资详情列表
			List<BorrowRecover> borrowRecoverList = this.getBorrowRecoverList(borrowNid);
			if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
				// 遍历进行还款 
				for (int i = 0; i < borrowRecoverList.size(); i++) {
					// 投资明细
					BorrowRecover borrowRecover = borrowRecoverList.get(i);
					String tenderOrderId = borrowRecover.getNid();// 投资订单号
					String repayOrderId = borrowRecover.getRepayOrdid();// 还款订单号
					if(1 == apicron.getIsAllrepay()){//一次性结清
						repayOrderId = getAllRepayOrdid(borrowNid,periodNow,borrowRecover.getNid());
					}
					BigDecimal creditAmount = borrowRecover.getCreditAmount();// 债转金额
					JSONObject repayDetail = repayResults.get(repayOrderId);
					// 如果发生了债转，处理相应的债转还款
					if (creditAmount.compareTo(BigDecimal.ZERO) > 0) {
						List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, periodNow);
						if (creditRepayList != null && creditRepayList.size() > 0) {
							boolean creditRepayAllFlag = true;
							boolean creditEndAllFlag = true;
							for (int j = 0; j < creditRepayList.size(); j++) {
								CreditRepay creditRepay = creditRepayList.get(j);
								String assignOrderId = creditRepay.getAssignNid();
								int assignUserId = creditRepay.getUserId();
								String creditRepayOrderId = creditRepay.getCreditRepayOrderId();
								JSONObject assignRepayDetail = repayResults.get(creditRepayOrderId);
								if (Validator.isNull(assignRepayDetail)) {
									logger.info("银行端未查詢到相应的还款明细!" + "[投资订单号：" + tenderOrderId + "]");
									continue;
								}
								try {
									String txState = assignRepayDetail.getString(BankCallConstant.PARAM_TXSTATE);// 交易状态
									// 如果处理状态为成功
									if (txState.equals(BankCallConstant.BATCH_TXSTATE_TYPE_SUCCESS)) {
										// 调用债转还款
										boolean creditRepayFlag = this.updateCreditRepay(apicron, borrow, borrowInfo, borrowRecover, creditRepay, assignRepayDetail);
										if (creditRepayFlag) {
											if (!isMonth || (isMonth && periodNext == 0)) {
												// 结束债权
												boolean debtOverFlag = this.requestDebtEnd(creditRepay.getUserId(), assignRepayDetail,assignOrderId, borrow, borrowInfo);
												if (debtOverFlag) {
													// 更新相应的债权状态为结束
													boolean debtStatusFlag = this.updateDebtStatus(creditRepay);
													if (!debtStatusFlag) {
														creditEndAllFlag = false;
														throw new Exception("更新相应的债转为债权结束失败!" + "[承接用户：" + assignUserId + "]" + "[承接订单号：" + assignOrderId + "]" + "[还款期数：" + periodNow + "]");
													}
												} else {
													throw new Exception("结束债权失败!" + "[承接用户：" + assignUserId + "]" + "[承接订单号：" + assignOrderId + "]" + "[还款期数：" + periodNow + "]");
												}
											}
										} else {
											creditRepayAllFlag = false;
											throw new Exception("更新相应的债转还款失败!" + "[承接用户：" + assignUserId + "]" + "[承接订单号：" + assignOrderId + "]" + "[还款期数：" + periodNow + "]");
										}
									} else {
										creditRepayAllFlag = false;
										// 更新投资详情表
										boolean borrowTenderFlag = this.updateCreditRepay(apicron, creditRepay);
										if (!borrowTenderFlag) {
											throw new Exception("更新相应的creditrepay失败!" + "[承接订单号：" + assignOrderId + "]" + "[还款期数：" + periodNow + "]");
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
									continue;
								}
							}
							if (creditRepayAllFlag) {
								// 如果不是全部债转
								if (borrowRecover.getRecoverCapital().compareTo(borrowRecover.getCreditAmount()) > 0) {
									if (Validator.isNull(repayDetail)) {
										logger.info("银行端未查詢到相应的还款明细!" + "[投资订单号：" + tenderOrderId + "]");
										continue;
									}
									String txState = repayDetail.getString(BankCallConstant.PARAM_TXSTATE);// 交易状态
									// 如果处理状态为成功
									if (txState.equals(BankCallConstant.BATCH_TXSTATE_TYPE_SUCCESS)) {
										try {
											boolean tenderRepayFlag = this.updateTenderRepay(apicron, borrow, borrowInfo, borrowRecover, repayDetail);
											if (tenderRepayFlag) {
												if (!isMonth || (isMonth && periodNext == 0)) {
													// 结束债权
													if (creditEndAllFlag) {
														boolean debtOverFlag = this.requestDebtEnd(borrowRecover.getUserId(), repayDetail,borrowRecover.getNid(), borrow, borrowInfo);
														if (debtOverFlag) {
															// 更新相应的债权状态为结束
															boolean debtStatusFlag = this.updateDebtStatus(borrowRecover, isMonth);
															if (!debtStatusFlag) {
																throw new Exception("更新相应的投资为债权结束失败!" + "[投资订单号：" + tenderOrderId + "]" + "[还款期数：" + periodNow + "]");
															}
														} else {
															throw new Exception("结束债权失败!" + "[投资订单号：" + tenderOrderId + "]" + "[还款期数：" + periodNow + "]");
														}
													}
												}
											} else {
												throw new Exception("还款失败!" + "[投资订单号：" + tenderOrderId + "]");
											}
										} catch (Exception e) {
											e.printStackTrace();
											continue;
										}
									} else {
										try {
											// 更新投资详情表
											boolean recoverFlag = this.updateRecover(apicron, borrow, borrowRecover);
											if (!recoverFlag) {
												throw new Exception("还款失败!" + "[投资订单号：" + tenderOrderId + "]");
											}
										} catch (Exception e) {
											e.printStackTrace();
											continue;
										}
									}
								} else {
									try {
										boolean tenderRepayFlag = this.updateTenderRepayStatus(apicron, borrow, borrowRecover);
										if (!tenderRepayFlag) {
											throw new Exception("更新相应的还款信息失败!" + "[投资订单号：" + tenderOrderId + "]");
										}
									} catch (Exception e) {
										e.printStackTrace();
										continue;
									}
								}
							} else {
								continue;
							}
						}
					} else {
						if (Validator.isNull(repayDetail)) {
							logger.info("银行端未查詢到相应的还款明细!" + "[投资订单号：" + tenderOrderId + "]");
							continue;
						}
						String txState = repayDetail.getString(BankCallConstant.PARAM_TXSTATE);// 交易状态
						logger.info("============标的号:" + borrowNid + "交易状态为:" + txState);
						// 如果处理状态为成功
						if (txState.equals(BankCallConstant.BATCH_TXSTATE_TYPE_SUCCESS)) {
							try {
								boolean tenderRepayFlag = this.updateTenderRepay(apicron, borrow, borrowInfo, borrowRecover, repayDetail);
								if (tenderRepayFlag) {
									if (!isMonth || (isMonth && periodNext == 0)) {
										boolean debtOverFlag = this.requestDebtEnd(borrowRecover.getUserId(), repayDetail,borrowRecover.getNid(), borrow, borrowInfo);
										if (debtOverFlag) {
											// 更新相应的债权状态为结束
											boolean debtStatusFlag = this.updateDebtStatus(borrowRecover, isMonth);
											if (!debtStatusFlag) {
												throw new Exception("更新相应的投资为债权结束失败!" + "[投资订单号：" + tenderOrderId + "]" + "[还款期数：" + periodNow + "]");
											}
										} else {
											throw new Exception("结束债权失败!" + "[投资订单号：" + tenderOrderId + "]" + "[还款期数：" + periodNow + "]");
										}
									}
								} else {
									throw new Exception("还款失败!" + "[投资订单号：" + tenderOrderId + "]");
								}
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}
						} else {
							try {
								// 更新投资详情表
								boolean recoverFlag = this.updateRecover(apicron, borrow, borrowRecover);
								if (!recoverFlag) {
									throw new Exception("还款失败!" + "[投资订单号：" + tenderOrderId + "]");
								}
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}
						}
					}
				}
			} else {
				logger.info("未查询到相应的还款记录，项目编号:" + borrowNid + "]");
				return true;
			}
		} else {
			throw new Exception("银行交易明细查询失败！，项目编号:" + borrowNid + "]");
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

	private boolean updateCreditRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover, CreditRepay creditRepay, JSONObject assignRepayDetail) throws Exception {

		logger.info("------债转还款承接部分开始---承接订单号：" + creditRepay.getCreditNid() + "---------");

		/** 还款信息 */
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 借款编号
		String borrowNid = apicron.getBorrowNid();
		// 还款人(借款人或垫付机构)ID
		Integer repayUserId = apicron.getUserId();
		// 还款人用户名
		String repayUserName = apicron.getUserName();
		// 当前期数
		Integer periodNow = apicron.getPeriodNow();
		// 是否是担保机构还款
		int isApicronRepayOrgFlag = Validator.isNull(apicron.getIsRepayOrgFlag()) ? 0 : apicron.getIsRepayOrgFlag();
		String repayBatchNo = apicron.getBatchNo();
		int txDate = Validator.isNotNull(apicron.getTxDate()) ? apicron.getTxDate() : 0;// 批次时间yyyyMMdd
		int txTime = Validator.isNotNull(apicron.getTxTime()) ? apicron.getTxTime() : 0;// 批次时间hhmmss
		String seqNo = Validator.isNotNull(apicron.getSeqNo()) ? String.valueOf(apicron.getSeqNo()) : null;// 流水号
		String bankSeqNo = Validator.isNotNull(apicron.getBankSeqNo()) ? String.valueOf(apicron.getBankSeqNo()) : null;// 银行唯一订单号

		String orderId = assignRepayDetail.getString(BankCallConstant.PARAM_ORDERID);// 还款订单号
		BigDecimal txAmount = assignRepayDetail.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT);// 操作金额
		String forAccountId = assignRepayDetail.getString(BankCallConstant.PARAM_FORACCOUNTID);// 投资人银行账户
		/** 标的基本数据 */
		// 标的是否可用担保机构还款
		int isRepayOrgFlag = Validator.isNull(borrowInfo.getIsRepayOrgFlag()) ? 0 : borrowInfo.getIsRepayOrgFlag();
		// 项目总期数
		Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();

		/** 还款信息 */
		// 投资订单号
		String tenderOrderId = borrowRecover.getNid();
		// 投资人用户ID
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
		// 投资信息
		BorrowTender borrowTender = getBorrowTender(tenderOrderId);
		// 查询相应的债权转让
		BorrowCredit borrowCredit = this.getBorrowCredit(creditNid);
		// 投资用户开户信息
		Account assignBankAccount = getAccountByUserId(assignUserId);
		// 投资用户银行账户
		String assignAccountId = assignBankAccount.getAccountId();
		// 查询相应的债权承接记录
		CreditTender creditTender = this.getCreditTender(assignNid);
		if (Validator.isNull(creditTender)) {
			throw new Exception("投资人未开户。[用户ID：" + repayUserId + "]，" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// 分期还款计划表
		BorrowRecoverPlan borrowRecoverPlan = null;
		// 判断该收支明细是否存在时,跳出本次循环
		if (countCreditAccountListByNid(repayOrderId)) {
			return true;
		}
		// 债转的下次还款时间
		int creditRepayNextTime = creditRepay.getAssignRepayNextTime();
		// 更新账户信息(投资人)
		Account assignUserAccount = new Account();
		assignUserAccount.setUserId(assignUserId);
		assignUserAccount.setBankTotal(lateInterest.add(delayInterest).add(chargeInterest));// 投资人资金总额
		assignUserAccount.setBankBalance(repayAccount);// 投资人可用余额
		assignUserAccount.setBankAwait(assignAccount);// 投资人待收金额
		assignUserAccount.setBankAwaitCapital(assignCapital);
		assignUserAccount.setBankAwaitInterest(assignInterest);
		assignUserAccount.setBankInterestSum(repayInterest);
		assignUserAccount.setBankBalanceCash(repayAccount);
		boolean investAccountFlag = this.adminAccountCustomizeMapper.updateOfRepayTender(assignUserAccount) > 0 ? true : false;
		if (!investAccountFlag) {
			throw new Exception("承接人资金记录(huiyingdai_account)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 取得承接人账户信息
		assignUserAccount = this.getAccountByUserId(creditRepay.getUserId());
		if (Validator.isNull(assignAccount)) {
			throw new Exception("承接人账户信息不存在。[投资人ID：" + borrowTender.getUserId() + "]，" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 写入收支明细
		AccountList accountList = new AccountList();
		accountList.setNid(repayOrderId); // 还款订单号
		accountList.setUserId(assignUserId); // 投资人
		accountList.setAmount(repayAccount); // 投资总收入
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
		accountList.setTrade("credit_tender_recover_yes"); // 投资成功
		accountList.setTradeCode("balance"); // 余额操作
		accountList.setTotal(assignUserAccount.getTotal()); // 投资人资金总额
		accountList.setBalance(assignUserAccount.getBalance()); // 投资人可用金额
		accountList.setPlanFrost(assignUserAccount.getPlanFrost());// 汇添金冻结金额
		accountList.setPlanBalance(assignUserAccount.getPlanBalance());// 汇添金可用金额
		accountList.setFrost(assignUserAccount.getFrost()); // 投资人冻结金额
		accountList.setAwait(assignUserAccount.getAwait()); // 投资人待收金额
//		accountList.setCreateTime(nowTime); // 创建时间
//		accountList.setBaseUpdate(nowTime); // 更新时间
		accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作者
		accountList.setRemark(borrowNid);
		accountList.setIp(borrow.getAddIp()); // 操作IP
//		accountList.setIsUpdate(0);
//		accountList.setBaseUpdate(0);
//		accountList.setInterest(BigDecimal.ZERO); // 利息
		accountList.setWeb(0); // PC
		boolean assignAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
		if (!assignAccountListFlag) {
			throw new Exception("收支明细(huiyingdai_account_list)写入失败！" + "[承接订单号：" + assignNid + "]");
		}
		// 更新相应的债转投资表
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
			// 取得分期还款计划表
			borrowRecoverPlan = getBorrowRecoverPlan(borrowNid, periodNow, tenderUserId, tenderOrderId);
			if (borrowRecoverPlan == null) {
				throw new Exception("分期还款计划表数据不存在。[借款编号：" + borrowNid + "]，" + "[承接订单号：" + creditRepay.getAssignNid() + "]" + "[期数：" + periodNow + "]");
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
			throw new Exception("债转投资表(huiyingdai_credit_tender)更新失败！" + "[承接订单号：" + creditRepay.getAssignNid() + "]");
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
			throw new Exception("承接人还款表(huiyingdai_credit_repay)更新失败！" + "[债转编号：" + creditRepay.getCreditNid() + "]");
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
			throw new Exception("债转记录(huiyingdai_borrow_credit)更新失败！" + "[承接订单号：" + creditRepay.getCreditNid() + "]");
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
			throw new Exception("投资人还款表(huiyingdai_borrow_recover)更新失败！" + "[债转编号：" + creditRepay.getCreditNid() + "]");
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
			throw new Exception("总的还款明细表(huiyingdai_borrow_repay)更新失败！" + "[项目编号：" + borrowNid + "]");
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
				throw new Exception("分期还款计划表更新失败。[借款编号：" + borrowNid + "]，" + "[承接订单号：" + creditRepay.getAssignNid() + "]" + "[期数：" + periodNow + "]");
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
					throw new Exception("还款计划表(huiyingdai_borrow_repay_plan)更新失败！" + "[承接订单号：" + creditRepay.getAssignNid() + "]" + "[期数：" + periodNow + "]");
				}
			} else {
				throw new Exception("还款计划表(huiyingdai_borrow_repay_plan)查询失败！" + "[承接订单号：" + creditRepay.getAssignNid() + "]" + "[期数：" + periodNow + "]");
			}

		}
		// 更新借款表
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
			throw new Exception("借款详情(huiyingdai_borrow)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 更新投资表
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
			throw new Exception("投资表(huiyingdai_borrow_tender)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 管理费大于0时,插入网站收支明细
		if (manageFee.compareTo(BigDecimal.ZERO) > 0) {
			// 插入网站收支明细记录
			
			// TODO: 网站收支改成消息队列了
//			AccountWebList accountWebList = new AccountWebList();
//			accountWebList.setOrdid(creditRepay.getAssignNid() + "_" + periodNow);// 订单号
//			accountWebList.setBorrowNid(borrowNid); // 投资编号
//			accountWebList.setUserId(repayUserId); // 借款人
//			accountWebList.setAmount(manageFee); // 管理费
//			accountWebList.setType(CustomConstants.TYPE_IN); // 类型1收入,2支出
//			accountWebList.setTrade(CustomConstants.TRADE_REPAYFEE); // 管理费
//			accountWebList.setTradeType(CustomConstants.TRADE_REPAYFEE_NM); // 账户管理费
//			accountWebList.setRemark(borrowNid); // 投资编号
//			accountWebList.setCreateTime(nowTime);
//			AccountWebListExample example = new AccountWebListExample();
//			example.createCriteria().andOrdidEqualTo(accountWebList.getOrdid()).andTradeEqualTo(CustomConstants.TRADE_REPAYFEE);
//			int webListCount = this.accountWebListMapper.countByExample(example);
//			if (webListCount == 0) {
//				Integer userId = accountWebList.getUserId();
//				UsersInfo usersInfo = getUsersInfoByUserId(userId);
//				if (usersInfo != null) {
//					Integer attribute = usersInfo.getAttribute();
//					if (attribute != null) {
//						// 查找用户的的推荐人
//						Users users = getUsersByUserId(userId);
//						Integer refUserId = users.getReferrer();
//						SpreadsUsersExample spreadsUsersExample = new SpreadsUsersExample();
//						SpreadsUsersExample.Criteria spreadsUsersExampleCriteria = spreadsUsersExample.createCriteria();
//						spreadsUsersExampleCriteria.andUserIdEqualTo(userId);
//						List<SpreadsUsers> sList = spreadsUsersMapper.selectByExample(spreadsUsersExample);
//						if (sList != null && !sList.isEmpty()) {
//							refUserId = sList.get(0).getSpreadsUserid();
//						}
//						// 如果是线上员工或线下员工，推荐人的userId和username不插
//						if (users != null && (attribute == 2 || attribute == 3)) {
//							// 查找用户信息
//							EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(userId);
//							if (employeeCustomize != null) {
//								accountWebList.setRegionName(employeeCustomize.getRegionName());
//								accountWebList.setBranchName(employeeCustomize.getBranchName());
//								accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
//							}
//						}
//						// 如果是无主单，全插
//						else if (users != null && (attribute == 1)) {
//							// 查找用户推荐人
//							EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
//							if (employeeCustomize != null) {
//								accountWebList.setRegionName(employeeCustomize.getRegionName());
//								accountWebList.setBranchName(employeeCustomize.getBranchName());
//								accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
//							}
//						}
//						// 如果是有主单
//						else if (users != null && (attribute == 0)) {
//							// 查找用户推荐人
//							EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
//							if (employeeCustomize != null) {
//								accountWebList.setRegionName(employeeCustomize.getRegionName());
//								accountWebList.setBranchName(employeeCustomize.getBranchName());
//								accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
//							}
//						}
//					}
//					accountWebList.setTruename(usersInfo.getTruename());
//					accountWebList.setFlag(1);
//				}
//				boolean accountWebListFlag = this.accountWebListMapper.insertSelective(accountWebList) > 0 ? true : false;
//				if (!accountWebListFlag) {
//					throw new Exception("网站收支记录(huiyingdai_account_web_list)更新失败！" + "[投资订单号：" + borrowTender.getNid() + "]");
//				}
//			} else {
//				throw new Exception("网站收支记录(huiyingdai_account_web_list)已存在!" + "[投资订单号：" + borrowTender.getNid() + "]");
//			}
		}
		apicron.setSucAmount(apicron.getSucAmount().add(repayAccount.add(manageFee)));
		apicron.setSucCounts(apicron.getSucCounts() + 1);
		apicron.setFailAmount(apicron.getFailAmount().subtract(repayAccount.add(manageFee)));
		apicron.setFailCounts(apicron.getFailCounts() - 1);
		boolean apicronSuccessFlag = this.borrowApicronMapper.updateByPrimaryKeySelective(apicron) > 0 ? true : false;
		if (!apicronSuccessFlag) {
			throw new Exception("批次还款记录(borrowApicron)更新失败!" + "[项目编号：" + borrowNid + "]");
		}
		// 发送短信
		this.sendSms(assignUserId, borrowNid, repayCapital, repayInterest);
		// 推送消息
		this.sendMessage(assignUserId, borrowNid, repayAccount, repayInterest);
		logger.info("------债转还款承接部分完成---承接订单号：" + borrowCredit.getCreditNid() + "---------还款订单号" + repayOrderId);
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
	 * 判断该收支明细是否存在
	 *
	 * @param accountList
	 * @return
	 */
	private boolean countAccountListByNid(String nid) {
		AccountListExample accountListExample = new AccountListExample();
		accountListExample.createCriteria().andNidEqualTo(nid).andTradeEqualTo("tender_recover_yes");
		return this.accountListMapper.countByExample(accountListExample) > 0 ? true : false;
	}

	/**
	 * 判断该收支明细是否存在
	 *
	 * @param accountList
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
	 * @param borrowRepayPlan
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
	 * @param integer
	 * @param string
	 * @param assignRepayDetail
	 * @return
	 */
	private boolean requestDebtEnd(Integer userId, JSONObject repayDetail,String orgOrderId, Borrow borrow, BorrowInfo borrowInfo) {

		String accountId = repayDetail.getString(BankCallConstant.PARAM_FORACCOUNTID);// 投资人账户
		String forAccountId = repayDetail.getString(BankCallConstant.PARAM_ACCOUNTID);// 借款人账户
		String productId = repayDetail.getString(BankCallConstant.PARAM_PRODUCTID);// 项目编号
		String authCode = repayDetail.getString(BankCallConstant.PARAM_AUTHCODE);// 投资授权码

		// 获取共同参数
		String channel = BankCallConstant.CHANNEL_PC;
		// 垫付机构还款时,结束无法结束债权
		Integer borrowUserId = borrowInfo.getUserId();
		// 根据用户ID查询借款人用户电子账户号
		Account borrowUserAccount = this.getAccountByUserId(borrowUserId);
		if(borrowUserAccount==null || borrowUserAccount.getAccountId() == null){
			logger.info("获取借款人电子账户号失败:借款人ID:["+borrowUserId+"].");
			return false;
		}
		// 借款人电子账户号
		forAccountId = borrowUserAccount.getAccountId();
		// 查询相应的债权状态
		BankCallBean debtQuery = this.debtStatusQuery(userId, accountId, orgOrderId);
		if (Validator.isNotNull(debtQuery)) {
			String queryRetCode = StringUtils.isNotBlank(debtQuery.getRetCode()) ? debtQuery.getRetCode() : "";
			if (BankCallConstant.RESPCODE_SUCCESS.equals(queryRetCode)) {
				String state = StringUtils.isNotBlank(debtQuery.getState()) ? debtQuery.getState() : "";
				if (StringUtils.isNotBlank(state)) {
					if ("4".equals(state)) {
						return true;
					} else if ("2".equals(state)) {

						try {
							
							String logOrderId = GetOrderIdUtils.getOrderId2(userId);
							String orderDate = GetOrderIdUtils.getOrderDate();
							String txDate = GetOrderIdUtils.getTxDate();
							String txTime = GetOrderIdUtils.getTxTime();
							String seqNo = GetOrderIdUtils.getSeqNo(6);
							// 调用还款接口
//							BankCallBean debtEndBean = new BankCallBean();
//							debtEndBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
//							debtEndBean.setTxCode(BankCallConstant.TXCODE_CREDIT_END);// 消息类型(批量还款)
//							debtEndBean.setInstCode(instCode);// 机构代码
//							debtEndBean.setBankCode(bankCode);
//							debtEndBean.setTxDate(txDate);
//							debtEndBean.setTxTime(txTime);
//							debtEndBean.setSeqNo(seqNo);
//							debtEndBean.setChannel(channel);
//							debtEndBean.setAccountId(forAccountId);// 借款人电子账户号
//							debtEndBean.setOrderId(logOrderId);
//							debtEndBean.setForAccountId(accountId);// 投资人电子账户号
//							debtEndBean.setProductId(productId);
//							debtEndBean.setAuthCode(authCode);
//							debtEndBean.setLogUserId(String.valueOf(userId));
//							debtEndBean.setLogOrderId(logOrderId);
//							debtEndBean.setLogOrderDate(orderDate);
//							debtEndBean.setLogRemark("结束债权请求");
//							debtEndBean.setLogClient(0);
//							BankCallBean repayResult = BankCallUtils.callApiBg(debtEndBean);
//							if (Validator.isNotNull(repayResult)) {
//								String retCode = StringUtils.isNotBlank(repayResult.getRetCode()) ? repayResult.getRetCode() : "";
//								if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
//									return true;
//								} else {
//									continue;
//								}
//							} else {
//								continue;
//							}
							
							logger.info(productId+" 直投还款结束债权  借款人: "+borrowUserId+"-"+forAccountId+" 投资人: "+userId+"-"+accountId+" 授权码: "+authCode+" 原始订单号: "+orgOrderId);
							BankCreditEnd record = new BankCreditEnd();
							record.setUserId(borrowUserId);
//							record.setUsername(borrowRecover);
							record.setTenderUserId(userId);
//							record.setTenderUsername(tenderUsername);
							record.setAccountId(forAccountId);
							record.setTenderAccountId(accountId);
							record.setOrderId(logOrderId);
							record.setBorrowNid(productId);
							record.setAuthCode(authCode);
							record.setCreditEndType(1); // 结束债权类型（1:还款，2:散标债转，3:计划债转）'
							record.setStatus(0);
							record.setOrgOrderId(orgOrderId);
							
							int nowTime = GetDate.getNowTime10();
							record.setCreateUser(userId);
//							record.setCreateTime(nowTime);
							record.setUpdateUser(userId);
//							record.setUpdateTime(nowTime);
							
							this.bankCreditEndMapper.insertSelective(record);
							return true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					}
				}
			}
		}
		return false;
	}

	/**
	 * 查询相应的债权的状态
	 * 
	 * @param userId
	 * @param accountId
	 * @param orderId
	 * @return
	 */
	private BankCallBean debtStatusQuery(int userId, String accountId, String orderId) {
		// 获取共同参数
		String channel = BankCallConstant.CHANNEL_PC;
		// 查询相应的债权状态
		for (int i = 0; i < 3; i++) {
			try {
				String logOrderId = GetOrderIdUtils.getOrderId2(userId);
				String orderDate = GetOrderIdUtils.getOrderDate();
				String txDate = GetOrderIdUtils.getTxDate();
				String txTime = GetOrderIdUtils.getTxTime();
				String seqNo = GetOrderIdUtils.getSeqNo(6);
				// 调用还款接口
				BankCallBean debtEndBean = new BankCallBean();
				debtEndBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
				debtEndBean.setTxCode(BankCallConstant.TXCODE_BID_APPLY_QUERY);// 消息类型(批量还款)
				debtEndBean.setTxDate(txDate);
				debtEndBean.setTxTime(txTime);
				debtEndBean.setSeqNo(seqNo);
				debtEndBean.setChannel(channel);
				debtEndBean.setAccountId(accountId);
				debtEndBean.setOrgOrderId(orderId);
				debtEndBean.setLogUserId(String.valueOf(userId));
				debtEndBean.setLogOrderId(logOrderId);
				debtEndBean.setLogOrderDate(orderDate);
				debtEndBean.setLogRemark("结束债权请求");
				debtEndBean.setLogClient(0);
				BankCallBean statusResult = BankCallUtils.callApiBg(debtEndBean);
				if (Validator.isNotNull(statusResult)) {
					String retCode = StringUtils.isNotBlank(statusResult.getRetCode()) ? statusResult.getRetCode() : "";
					if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
						return statusResult;
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
		return null;
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
	 * 更新相应的原始投资为债权结束
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
	 * @param apicron
	 * 
	 * @param creditRepay
	 * @return
	 * @throws Exception
	 */
	private boolean updateCreditRepay(BorrowApicron apicron, CreditRepay creditRepay) throws Exception {
		// 更新投资详情表
		creditRepay.setStatus(2); // 状态 0未还款1已还款2还款失败
		boolean creditRepayFlag = this.creditRepayMapper.updateByPrimaryKeySelective(creditRepay) > 0 ? true : false;
		if (!creditRepayFlag) {
			throw new Exception("债转还款记录(credit_repay)更新失败!" + "[投资订单号：" + creditRepay.getAssignNid() + "]" + ",期数：" + creditRepay.getRecoverPeriod());
		}
		return true;
	}

	/**
	 * 更新还款完成状态
	 * 
	 * @param borrow
	 *
	 * @param borrowNid
	 * @param periodNow
	 * @throws Exception
	 */
	private boolean updateBorrowStatus(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo) throws Exception {

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
		int repayCount = apicron.getTxCounts();// 放款总笔数
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
		logger.info("-----------还款完成，更新状态开始---" + borrowNid + "---------【还款期数】" + periodNow);
		// 还款期数
		Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 剩余还款期数
		Integer periodNext = borrowPeriod - periodNow;
		// 是否月标(true:月标, false:天标)
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
			failCount = this.borrowRecoverPlanMapper.countByExample(recoverPlanExample);
			// 如果还款全部完成
			if (failCount == 0) {
				if (periodNext == 0) {
					repayType = TYPE_WAIT_YES;
					repayStatus = 1;
					repayYesTime = nowTime;
					status = 5;
				}
				// 还款总表
				BorrowRepay borrowRepay = this.getBorrowRepay(borrowNid);
				borrowRepay.setRepayType(repayType);
				borrowRepay.setRepayStatus(repayStatus); // 已还款
//				borrowRepay.setRepayDays("0");
//				borrowRepay.setRepayStep(4);
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
					throw new Exception("未查询到相应的分期还款borrowRepayPlan记录，项目编号：" + borrowNid + ",还款期数：" + periodNow);
				}
				borrowRepayPlan.setRepayType(TYPE_WAIT_YES);
//				borrowRepayPlan.setRepayDays("0");
//				borrowRepayPlan.setRepayStep(4);
				borrowRepayPlan.setRepayActionTime(String.valueOf(nowTime));
				borrowRepayPlan.setRepayStatus(1);
				borrowRepayPlan.setRepayYestime(nowTime);
				boolean borrowRepayPlanFlag = this.borrowRepayPlanMapper.updateByPrimaryKeySelective(borrowRepayPlan) > 0 ? true : false;
				if (!borrowRepayPlanFlag) {
					throw new Exception("更新相应的分期还款borrowRepayPlan记录失败，项目编号：" + borrowNid + ",还款期数：" + periodNow);
				}
				// 更新BorrowRepay
				BorrowRepayExample repayExample = new BorrowRepayExample();
				repayExample.createCriteria().andBorrowNidEqualTo(borrowNid);
				boolean borrowRepayFlag = this.borrowRepayMapper.updateByExampleSelective(borrowRepay, repayExample) > 0 ? true : false;
				if (!borrowRepayFlag) {
					throw new Exception("更新相应的分期还款borrowRepay记录失败，项目编号：" + borrowNid + ",还款期数：" + periodNow);
				}
				Account repayUserAccount = this.getAccountByUserId(repayUserId);
				BigDecimal repayAccount = borrowRepayPlan.getRepayAccountYes();
				/*BigDecimal repayCapital = borrowRepayPlan.getRepayCapitalYes();*/
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
						throw new Exception("垫付机构账户(huiyingdai_account)更新失败！" + "[借款人用户ID：" + borrowUserId + "]");
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
						throw new Exception("借款人账户(huiyingdai_account)更新失败！" + "[借款人用户ID：" + borrowUserId + "]");
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
						throw new Exception("借款人账户(huiyingdai_account)更新失败！" + "[借款人用户ID：" + borrowUserId + "]");
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
//				repayAccountList.setCreateTime(nowTime); // 创建时间
				repayAccountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作员
				repayAccountList.setRemark(borrowNid);
				repayAccountList.setIp(""); // 操作IP
//				repayAccountList.setBaseUpdate(0);
				Integer isAllrepay = apicron.getIsAllrepay() == null ? 0 : apicron.getIsAllrepay();
				if(0 == isAllrepay){//非一次性还款时插入资金明细
					boolean repayAccountListFlag = this.accountListMapper.insertSelective(repayAccountList) > 0 ? true : false;
					if (!repayAccountListFlag) {
						throw new Exception("插入还款人还款款交易明細accountList表失败，项目编号:" + borrowNid + "]");
					}
				}
				// 更新Borrow
				newBorrow.setRepayFullStatus(repayStatus);
				newBorrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
				newBorrow.setStatus(status);
				BorrowExample borrowExample = new BorrowExample();
				borrowExample.createCriteria().andIdEqualTo(borrowId);
				boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
				if (!borrowFlag) {
					throw new Exception("不分期还款成功后，更新相应的borrow表失败," + "项目编号:" + borrowNid + ",还款期数：" + periodNow);
				}
				BorrowApicronExample apicronExample = new BorrowApicronExample();
				apicronExample.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
				apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
//				apicron.setUpdateTime(nowTime);
				boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, apicronExample) > 0 ? true : false;
				if (!apicronFlag) {
					throw new Exception("更新还款任务失败。[项目编号：" + borrowNid + "]");
				}
				if(1 == isAllrepay){//一次性还款判断是否整个标的还款，还款后新增交易明细 add by cwyang 2018-5-21
					boolean result = isLastAllRepay(apicron);
					BigDecimal sum = getRepayPlanAccountSum(borrowNid);
					if(result){
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
//						repayAllAccountList.setCreateTime(nowTime); // 创建时间
						repayAllAccountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作员
						repayAllAccountList.setRemark(borrowNid);
						repayAllAccountList.setIp(""); // 操作IP
//						repayAllAccountList.setBaseUpdate(0);
						boolean repayAccountListFlag = this.accountListMapper.insertSelective(repayAllAccountList) > 0 ? true : false;
						if (!repayAccountListFlag) {
							throw new Exception("插入还款人还款款交易明細accountList表失败，项目编号:" + borrowNid + "]");
						}
					}

				}
				
				// add by hsy 优惠券还款请求加入到消息队列 start
                Map<String, String> params = new HashMap<String, String>();
                params.put("mqMsgId", GetCode.getRandomCode(10));
                // 借款项目编号
                params.put("borrowNid", borrowNid);
                // 当前期
                params.put("periodNow", String.valueOf(periodNow));
                
                //TODO:优惠券还款队列
//                rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_COUPONREPAY, JSONObject.toJSONString(params));
                // add by hsy 优惠券还款请求加入到消息队列 end

				// insert by zhangjp 增加优惠券还款区分 start
//				CommonSoaUtils.couponRepay(borrowNid, periodNow);
				// insert by zhangjp 增加优惠券还款区分 end
				try {
					this.sendSmsForManager(borrowNid);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (failCount == repayCount) {
				// 更新Borrow
				newBorrow.setStatus(status);
				newBorrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_FAIL);
				BorrowExample borrowExample = new BorrowExample();
				borrowExample.createCriteria().andIdEqualTo(borrowId);
				boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
				if (!borrowFlag) {
					throw new Exception("更新borrow表失败，项目编号:" + borrowNid + "]");
				}
				BorrowApicronExample example = new BorrowApicronExample();
				example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
				apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_FAIL);
//				apicron.setUpdateTime(nowTime);
				boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
				if (!apicronFlag) {
					throw new Exception("更新状态为(放款成功)失败，项目编号:" + borrowNid + "]");
				}
			} else {
				// 更新Borrow
				newBorrow.setStatus(status);
				newBorrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
				BorrowExample borrowExample = new BorrowExample();
				borrowExample.createCriteria().andIdEqualTo(borrowId);
				boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
				if (!borrowFlag) {
					throw new Exception("更新borrow表失败，项目编号:" + borrowNid + "]");
				}
				BorrowApicronExample example = new BorrowApicronExample();
				example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
				apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
//				apicron.setUpdateTime(nowTime);
				boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
				if (!apicronFlag) {
					throw new Exception("更新状态为(放款成功)失败，项目编号:" + borrowNid + "]");
				}
			}
		} else {
			// 查询recover
			BorrowRecoverExample recoverExample = new BorrowRecoverExample();
			recoverExample.createCriteria().andBorrowNidEqualTo(borrowNid).andRecoverStatusNotEqualTo(1);
			failCount = this.borrowRecoverMapper.countByExample(recoverExample);
			logger.info("=============标的号:" + borrowNid + "的失败笔数为:" + failCount);
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
//				borrowRepay.setRepayDays("0");
//				borrowRepay.setRepayStep(4);
				borrowRepay.setRepayPeriod(isMonth ? periodNext : 1);
				borrowRepay.setRepayActionTime(nowTime);// 实际还款时间
				borrowRepay.setRepayYestime(repayYesTime);// 还款成功最后时间
				// 更新BorrowRepay
				boolean repayFlag = this.borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay) > 0 ? true : false;
				if (!repayFlag) {
					throw new Exception("不分期还款成功后，更新相应的borrowrepay表失败," + "项目编号:" + borrowNid);
				}
				Account repayUserAccount = this.getAccountByUserId(repayUserId);
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
						throw new Exception("垫付机构账户(huiyingdai_account)更新失败！" + "[借款人用户ID：" + borrowUserId + "]");
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
						throw new Exception("借款人账户(huiyingdai_account)更新失败！" + "[借款人用户ID：" + borrowUserId + "]");
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
						throw new Exception("借款人账户(huiyingdai_account)更新失败！" + "[借款人用户ID：" + borrowUserId + "]");
					}
				}
				// 插入还款交易明细
				repayUserAccount = this.getAccountByUserId(repayUserId);
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
//				accountList.setCreateTime(nowTime); // 创建时间
				accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作员
				accountList.setRemark(borrowNid);
				accountList.setIp(""); // 操作IP
//				accountList.setBaseUpdate(0);
				boolean repayAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
				if (!repayAccountListFlag) {
					throw new Exception("插入还款人还款款交易明細accountList表失败，项目编号:" + borrowNid + "]");
				}
				/*if (isRepayOrgFlag == 1 && isApicronRepayOrgFlag == 1) {
					BankOpenAccount borrowBankAccount = this.getBankOpenAccount(borrowUserId);
					String borrowAccountId = borrowBankAccount.getAccount();
					// 插入还款交易明细
					Account borrowAccount = this.getAccountByUserId(borrowUserId);
					// 插入借款人的收支明细表(原复审业务)
					AccountList borrowAccountList = new AccountList();
					borrowAccountList.setBankAwait(borrowAccount.getBankAwait());
					borrowAccountList.setBankAwaitCapital(borrowAccount.getBankAwaitCapital());
					borrowAccountList.setBankAwaitInterest(borrowAccount.getBankAwaitInterest());
					borrowAccountList.setBankBalance(borrowAccount.getBankBalance());
					borrowAccountList.setBankFrost(borrowAccount.getBankFrost());
					borrowAccountList.setBankInterestSum(borrowAccount.getBankInterestSum());
					borrowAccountList.setBankTotal(borrowAccount.getBankTotal());
					borrowAccountList.setBankWaitCapital(borrowAccount.getBankWaitCapital());
					borrowAccountList.setBankWaitInterest(borrowAccount.getBankWaitInterest());
					borrowAccountList.setBankWaitRepay(borrowAccount.getBankWaitRepay());
					borrowAccountList.setAccountId(borrowAccountId);
					borrowAccountList.setTxDate(txDate);
					borrowAccountList.setTxTime(txTime);
					borrowAccountList.setSeqNo(seqNo);
					borrowAccountList.setBankSeqNo(bankSeqNo);
					borrowAccountList.setCheckStatus(1);
					borrowAccountList.setTradeStatus(1);
					borrowAccountList.setIsBank(1);
					// 非银行相关
					borrowAccountList.setNid(apicronNid); // 交易凭证号生成规则BorrowNid_userid_期数
					borrowAccountList.setUserId(borrowUserId); // 借款人id
					borrowAccountList.setAmount(borrowRepay.getRepayAccountYes().add(borrowRepay.getRepayFee())); // 操作金额
					borrowAccountList.setType(2); // 收支类型1收入2支出3冻结
					borrowAccountList.setTrade("borrow_repay"); // 交易类型
					borrowAccountList.setTradeCode("balance"); // 操作识别码
					borrowAccountList.setTotal(borrowAccount.getTotal()); // 资金总额
					borrowAccountList.setBalance(borrowAccount.getBalance()); // 可用金额
					borrowAccountList.setFrost(borrowAccount.getFrost()); // 冻结金额
					borrowAccountList.setAwait(borrowAccount.getAwait()); // 待收金额
					borrowAccountList.setRepay(borrowAccount.getRepay()); // 待还金额
					borrowAccountList.setCreateTime(nowTime); // 创建时间
					borrowAccountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作员
					borrowAccountList.setRemark(borrowNid);
					borrowAccountList.setIp(""); // 操作IP
					borrowAccountList.setBaseUpdate(0);
					boolean borrowAccountListFlag = this.accountListMapper.insertSelective(borrowAccountList) > 0 ? true : false;
					if (!borrowAccountListFlag) {
						throw new Exception("插入借款人款人还款款交易明細accountList表失败，项目编号:" + borrowNid + "]");
					}
				}*/
				// 标的总表信息
				Borrow newBrrow = new Borrow();
				newBrrow.setRepayFullStatus(repayStatus);
				newBrrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
				newBrrow.setStatus(status);
				BorrowExample borrowExample = new BorrowExample();
				borrowExample.createCriteria().andIdEqualTo(borrowId);
				boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBrrow, borrowExample) > 0 ? true : false;
				if (!borrowFlag) {
					throw new Exception("不分期还款成功后，更新相应的borrow表失败," + "项目编号:" + borrowNid);
				}
				BorrowApicronExample apicronExample = new BorrowApicronExample();
				apicronExample.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
				apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
//				apicron.setUpdateTime(nowTime);
				boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, apicronExample) > 0 ? true : false;
				if (!apicronFlag) {
					throw new Exception("更新还款任务失败。[项目编号：" + borrowNid + "]");
				}
				
                // add by hsy 优惠券还款请求加入到消息队列 start
                Map<String, String> params = new HashMap<String, String>();
                params.put("mqMsgId", GetCode.getRandomCode(10));
                // 借款项目编号
                params.put("borrowNid", borrowNid);
                // 当前期
                params.put("periodNow", String.valueOf(periodNow));
                //TODO:优惠券还款
                
//                rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_COUPONREPAY, JSONObject.toJSONString(params));
                // add by hsy 优惠券还款请求加入到消息队列 end
                
				// insert by zhangjp 增加优惠券还款区分 start
//				CommonSoaUtils.couponRepay(borrowNid, periodNow);
				// insert by zhangjp 增加优惠券还款区分 end
				try {
					this.sendSmsForManager(borrowNid);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (failCount == repayCount) {
				// 更新Borrow
				newBorrow.setStatus(status);
				newBorrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_FAIL);
				BorrowExample borrowExample = new BorrowExample();
				borrowExample.createCriteria().andIdEqualTo(borrowId);
				boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
				if (!borrowFlag) {
					throw new Exception("更新borrow表失败，项目编号:" + borrowNid + "]");
				}
				BorrowApicronExample example = new BorrowApicronExample();
				example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
				apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_FAIL);
//				apicron.setUpdateTime(nowTime);
				boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
				if (!apicronFlag) {
					throw new Exception("更新状态为(放款成功)失败，项目编号:" + borrowNid + "]");
				}
			} else {
				// 更新Borrow
				newBorrow.setStatus(status);
				newBorrow.setRepayStatus(CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
				BorrowExample borrowExample = new BorrowExample();
				borrowExample.createCriteria().andIdEqualTo(borrowId);
				boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
				if (!borrowFlag) {
					throw new Exception("更新borrow表失败，项目编号:" + borrowNid + "]");
				}
				BorrowApicronExample example = new BorrowApicronExample();
				example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
				apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
//				apicron.setUpdateTime(nowTime);
				boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
				if (!apicronFlag) {
					throw new Exception("更新状态为(放款成功)失败，项目编号:" + borrowNid + "]");
				}
			}
		}

		// 更新运营数据
		JSONObject repayParams = new JSONObject();
		repayParams.put("type", 2);// 还款
		repayParams.put("recoverInterestAmount", recoverInterestAmount);// 当期已收利息
		logger.info("-----------已收利息---" + recoverInterestAmount);
		
		//TODO: 更新运营数据队列
//		rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_COUPON, RabbitMQConstants.ROUTINGKEY_OPERATION_DATA,
//				JSONObject.toJSONString(repayParams));

		logger.info("-----------还款完成，更新状态完成---" + borrowNid + "---------【还款期数】" + periodNow);
		return true;
	}


	//TODO 还款后变更投资人资金明细
	private boolean updateTenderRepay(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowRecover borrowRecover, JSONObject repayDetail) throws Exception {

		logger.info("-----------还款开始---" + apicron.getBorrowNid() + "---------");

		/** 还款信息 */
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 借款编号
		String borrowNid = apicron.getBorrowNid();
		// 还款人ID(借款人或代付机构)
		Integer repayUserId = apicron.getUserId();
		// 还款人用户名
		String repayUserName = apicron.getUserName();
		// 是否是担保机构还款
		int isApicronRepayOrgFlag = Validator.isNull(apicron.getIsRepayOrgFlag()) ? 0 : apicron.getIsRepayOrgFlag();
		// 还款期数
		Integer periodNow = apicron.getPeriodNow();
		String repayBatchNo = apicron.getBatchNo();
		int txDate = Validator.isNotNull(apicron.getTxDate()) ? apicron.getTxDate() : 0;// 批次时间yyyyMMdd
		int txTime = Validator.isNotNull(apicron.getTxTime()) ? apicron.getTxTime() : 0;// 批次时间hhmmss
		String seqNo = Validator.isNotNull(apicron.getSeqNo()) ? String.valueOf(apicron.getSeqNo()) : null;// 流水号
		String bankSeqNo = Validator.isNotNull(apicron.getBankSeqNo()) ? String.valueOf(apicron.getBankSeqNo()) : null;// 银行唯一订单号

		String orderId = repayDetail.getString(BankCallConstant.PARAM_ORDERID);// 还款订单号
		BigDecimal txAmount = repayDetail.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT);// 操作金额
		String forAccountId = repayDetail.getString(BankCallConstant.PARAM_FORACCOUNTID);// 投资人银行账户
		/** 标的基本数据 */
		// 标的是否可以担保机构还款
		int isRepayOrgFlag = Validator.isNull(borrowInfo.getIsRepayOrgFlag()) ? 0 : borrowInfo.getIsRepayOrgFlag();
		// 还款期数
		Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();
		/** 投资人数据 */
		// 投资订单号
		String tenderOrderId = borrowRecover.getNid();
		// 投资人用户ID
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
		// 投资信息
		BorrowTender borrowTender = getBorrowTender(tenderOrderId);
		// 投资用户开户信息
		Account tenderBankAccount = getAccountByUserId(tenderUserId);
		// 投资用户银行账户
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

		// 分期还款计划表
		BorrowRecoverPlan borrowRecoverPlan = null;
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// [principal: 等额本金, month:等额本息, month:等额本息, endmonth:先息后本]
		if (isMonth) {
			// 取得分期还款计划表
			borrowRecoverPlan = getBorrowRecoverPlan(borrowNid, periodNow, tenderUserId, tenderOrderId);
			if (Validator.isNull(borrowRecoverPlan)) {
				throw new Exception("分期还款计划表数据不存在。[借款编号：" + borrowNid + "]，" + "[投资订单号：" + tenderOrderId + "]，" + "[期数：" + periodNow + "]");
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
		}
		// [endday: 按天计息, end:按月计息]
		else {
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
		// 判断该收支明细是否存在时,跳出本次循环
		if (countAccountListByNid(repayOrderId)) {
			return true;
		}
		// 更新账户信息(投资人)
		Account tenderAccount = new Account();
		tenderAccount.setUserId(tenderUserId);
		tenderAccount.setBankTotal(lateInterest.add(delayInterest).add(chargeInterest));// 投资人资金总额
		tenderAccount.setBankBalance(repayAccount);// 投资人可用余额
		tenderAccount.setBankAwait(recoverAccountWait);// 投资人待收金额
		tenderAccount.setBankAwaitCapital(recoverCapitalWait);
		tenderAccount.setBankAwaitInterest(recoverInterestWait);
		tenderAccount.setBankInterestSum(repayInterest);
		tenderAccount.setBankBalanceCash(repayAccount);
		boolean investAccountFlag = this.adminAccountCustomizeMapper.updateOfRepayTender(tenderAccount) > 0 ? true : false;
		if (!investAccountFlag) {
			throw new Exception("投资人资金记录(huiyingdai_account)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 取得账户信息(投资人)
		tenderAccount = this.getAccountByUserId(borrowTender.getUserId());
		if (Validator.isNull(tenderAccount)) {
			throw new Exception("投资人账户信息不存在。[投资人ID：" + borrowTender.getUserId() + "]，" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 写入收支明细
		AccountList accountList = new AccountList();
		accountList.setNid(repayOrderId); // 还款订单号
		accountList.setUserId(tenderUserId); // 投资人
		accountList.setAmount(repayAccount); // 投资总收入
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
		accountList.setTrade("tender_recover_yes"); // 投资成功
		accountList.setTradeCode("balance"); // 余额操作
		accountList.setTotal(tenderAccount.getTotal()); // 投资人资金总额
		accountList.setBalance(tenderAccount.getBalance()); // 投资人可用金额
		accountList.setPlanFrost(tenderAccount.getPlanFrost());// 汇添金冻结金额
		accountList.setPlanBalance(tenderAccount.getPlanBalance());// 汇添金可用金额
		accountList.setFrost(tenderAccount.getFrost()); // 投资人冻结金额
		accountList.setAwait(tenderAccount.getAwait()); // 投资人待收金额
//		accountList.setCreateTime(nowTime); // 创建时间
//		accountList.setBaseUpdate(nowTime); // 更新时间
		accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作者
		accountList.setRemark(borrowNid);
		accountList.setIp(borrow.getAddIp()); // 操作IP
//		accountList.setIsUpdate(0);
//		accountList.setBaseUpdate(0);
//		accountList.setInterest(BigDecimal.ZERO); // 利息
		accountList.setWeb(0); // PC
		boolean investAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
		if (!investAccountListFlag) {
			throw new Exception("收支明细(huiyingdai_account_list)写入失败！" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 更新还款明细表
		// 分期并且不是最后一期
		if (borrowRecoverPlan != null && Validator.isNotNull(periodNext) && periodNext > 0) {
			borrowRecover.setRecoverStatus(0); // 未还款
			// 取得分期还款计划表下一期的还款
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
			throw new Exception("还款明细(huiyingdai_borrow_recover)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
		}else{  
			logger.info("---------------------------------cwyang 标的号:" + borrowNid + ",还款订单号:" + borrowRecover.getRepayOrdid() + ",投资数据变更成功!");
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
					// 更新债转总表
					boolean borrowCreditFlag = this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit) > 0 ? true : false;
					// 债转总表更新成功
					if (!borrowCreditFlag) {
						throw new Exception("债转记录(huiyingdai_borrow_credit)更新失败！" + "[承接订单号：" + creditNid + "]");
					}
				}
			}
		}
		// 更新总的还款明细
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
			throw new Exception("总的还款明细表(huiyingdai_borrow_repay)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 更新借款表
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
			throw new Exception("借款详情(huiyingdai_borrow)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 更新投资表
		borrowTender.setRecoverAccountYes(borrowTender.getRecoverAccountYes().add(repayAccount));
		borrowTender.setRecoverAccountCapitalYes(borrowTender.getRecoverAccountCapitalYes().add(repayCapital));
		borrowTender.setRecoverAccountInterestYes(borrowTender.getRecoverAccountInterestYes().add(repayInterest));
		borrowTender.setRecoverAccountWait(borrowTender.getRecoverAccountWait().subtract(recoverAccountWait));
		borrowTender.setRecoverAccountCapitalWait(borrowTender.getRecoverAccountCapitalWait().subtract(recoverCapitalWait));
		borrowTender.setRecoverAccountInterestWait(borrowTender.getRecoverAccountInterestWait().subtract(recoverInterestWait));
		boolean borrowTenderFlag = borrowTenderMapper.updateByPrimaryKeySelective(borrowTender) > 0 ? true : false;
		if (!borrowTenderFlag) {
			throw new Exception("投资表(huiyingdai_borrow_tender)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
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
			borrowRecoverPlan.setRepayDelayInterest(borrowRecoverPlan.getRepayDelayInterest().add(delayInterest));
			borrowRecoverPlan.setRepayLateInterest(borrowRecoverPlan.getRepayLateInterest().add(lateInterest));
			borrowRecoverPlan.setRecoverFeeYes(borrowRecoverPlan.getRecoverFeeYes().add(manageFee));
			borrowRecoverPlan.setRecoverType(TYPE_YES);
			boolean borrowRecoverPlanFlag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
			if (!borrowRecoverPlanFlag) {
				throw new Exception("还款分期计划表(huiyingdai_borrow_recover_plan)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
			}
			// 更新总的还款计划
			BorrowRepayPlan borrowRepayPlan = getBorrowRepayPlan(borrowNid, periodNow);
			if (Validator.isNotNull(borrowRepayPlan)) {
				borrowRepayPlan.setRepayType(TYPE_WAIT_YES);
//				borrowRepayPlan.setRepayDays("0");
//				borrowRepayPlan.setRepayStep(4);
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
					throw new Exception("还款分期计划表(huiyingdai_borrow_repay_plan)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
				}
			} else {
				throw new Exception("还款分期计划表(huiyingdai_borrow_repay_plan)查询失败！" + "[投资订单号：" + tenderOrderId + "]");
			}

		}
		// 管理费大于0时,插入网站收支明细
		if (manageFee.compareTo(BigDecimal.ZERO) > 0) {
			// 插入网站收支明细记录
			//TODO: 发送网站收支明细队列
//			AccountWebList accountWebList = new AccountWebList();
//			accountWebList.setOrdid(borrowTender.getNid() + "_" + periodNow);// 订单号
//			accountWebList.setBorrowNid(borrowNid); // 投资编号
//			accountWebList.setUserId(repayUserId); // 借款人
//			accountWebList.setAmount(manageFee); // 管理费
//			accountWebList.setType(CustomConstants.TYPE_IN); // 类型1收入,2支出
//			accountWebList.setTrade(CustomConstants.TRADE_REPAYFEE); // 管理费
//			accountWebList.setTradeType(CustomConstants.TRADE_REPAYFEE_NM); // 账户管理费
//			accountWebList.setRemark(borrowNid); // 投资编号
//			accountWebList.setCreateTime(nowTime);
//			AccountWebListExample example = new AccountWebListExample();
//			example.createCriteria().andOrdidEqualTo(accountWebList.getOrdid()).andTradeEqualTo(CustomConstants.TRADE_REPAYFEE);
//			int webListCount = this.accountWebListMapper.countByExample(example);
//			if (webListCount == 0) {
//				Integer userId = accountWebList.getUserId();
//				UsersInfo usersInfo = getUsersInfoByUserId(userId);
//				if (usersInfo != null) {
//					Integer attribute = usersInfo.getAttribute();
//					if (attribute != null) {
//						// 查找用户的的推荐人
//						Users users = getUsersByUserId(userId);
//						Integer refUserId = users.getReferrer();
//						SpreadsUsersExample spreadsUsersExample = new SpreadsUsersExample();
//						SpreadsUsersExample.Criteria spreadsUsersExampleCriteria = spreadsUsersExample.createCriteria();
//						spreadsUsersExampleCriteria.andUserIdEqualTo(userId);
//						List<SpreadsUsers> sList = spreadsUsersMapper.selectByExample(spreadsUsersExample);
//						if (sList != null && !sList.isEmpty()) {
//							refUserId = sList.get(0).getSpreadsUserid();
//						}
//						// 如果是线上员工或线下员工，推荐人的userId和username不插
//						if (users != null && (attribute == 2 || attribute == 3)) {
//							// 查找用户信息
//							EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(userId);
//							if (employeeCustomize != null) {
//								accountWebList.setRegionName(employeeCustomize.getRegionName());
//								accountWebList.setBranchName(employeeCustomize.getBranchName());
//								accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
//							}
//						}
//						// 如果是无主单，全插
//						else if (users != null && (attribute == 1)) {
//							// 查找用户推荐人
//							EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
//							if (employeeCustomize != null) {
//								accountWebList.setRegionName(employeeCustomize.getRegionName());
//								accountWebList.setBranchName(employeeCustomize.getBranchName());
//								accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
//							}
//						}
//						// 如果是有主单
//						else if (users != null && (attribute == 0)) {
//							// 查找用户推荐人
//							EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
//							if (employeeCustomize != null) {
//								accountWebList.setRegionName(employeeCustomize.getRegionName());
//								accountWebList.setBranchName(employeeCustomize.getBranchName());
//								accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
//							}
//						}
//					}
//					accountWebList.setTruename(usersInfo.getTruename());
//					accountWebList.setFlag(1);
//				}
//				boolean accountWebListFlag = this.accountWebListMapper.insertSelective(accountWebList) > 0 ? true : false;
//				if (!accountWebListFlag) {
//					throw new Exception("网站收支记录(huiyingdai_account_web_list)更新失败！" + "[投资订单号：" + borrowTender.getNid() + "]");
//				}
//			} else {
//				throw new Exception("网站收支记录(huiyingdai_account_web_list)已存在!" + "[投资订单号：" + borrowTender.getNid() + "]");
//			}
		}
		apicron.setSucAmount(apicron.getSucAmount().add(repayAccount.add(manageFee)));
		apicron.setSucCounts(apicron.getSucCounts() + 1);
		apicron.setFailAmount(apicron.getFailAmount().subtract(repayAccount.add(manageFee)));
		apicron.setFailCounts(apicron.getFailCounts() - 1);
		boolean apicronSuccessFlag = this.borrowApicronMapper.updateByPrimaryKeySelective(apicron) > 0 ? true : false;
		if (!apicronSuccessFlag) {
			throw new Exception("批次放款记录(borrowApicron)更新失败!" + "[项目编号：" + borrowNid + "]");
		}
		try {
			// 发送短信
			this.sendSms(tenderUserId, borrowNid, repayCapital, repayInterest);
			// 推送消息
			this.sendMessage(tenderUserId, borrowNid, repayAccount, repayInterest);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 直投类还款成功之后， 判断是风车理财的投资，发送到队列，准备回调通知
		if(CustomConstants.WRB_CHANNEL_CODE.equals(borrowTender.getTenderFrom())){
			Map<String, String> params = new HashMap<>();
			params.put("userId", String.valueOf(borrowTender.getUserId()));
			//投资订单号
			params.put("nid", borrowTender.getNid());
			// 2-回款回调
			params.put("returnType", "2");
			// 还款时间
			params.put("backTime", GetDate.formatDateTime(System.currentTimeMillis()));
			// 还款金额
			params.put("backMoney", repayAccount.toString());
			//TODO: 风车理财队列
			try {
//				rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_WRB_CALLBACK_NOTIFY, JSONObject.toJSONString(params));
			} catch (Exception e) {
				logger.error("风车理财还款通知入列失败...", e);
			}
		}

		logger.info("-----------还款结束---" + apicron.getBorrowNid() + "---------" + repayOrderId);
		return true;
	}

	private boolean updateRecover(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception {
		int periodNow = apicron.getPeriodNow();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();
		String borrowNid = borrow.getBorrowNid();
		int tenderUserId = borrowRecover.getUserId();
		String tenderOrderId = borrowRecover.getNid();
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		if (isMonth) {
			// 取得分期还款计划表
			BorrowRecoverPlan borrowRecoverPlan = getBorrowRecoverPlan(borrowNid, periodNow, tenderUserId, tenderOrderId);
			borrowRecoverPlan.setRecoverStatus(2);
			boolean flag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
			if (!flag) {
				throw new Exception("更新相应的还款明细失败！项目编号:" + borrowNid + "]");
			}
		} else {
			borrowRecover.setRecoverStatus(2);
			boolean flag = this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover) > 0 ? true : false;
			if (!flag) {
				throw new Exception("更新相应的还款明细失败！项目编号:" + borrowNid + "]");
			}
		}
		return true;
	}

	private boolean updateTenderRepayStatus(BorrowApicron apicron, Borrow borrow, BorrowRecover borrowRecover) throws Exception {

		logger.info("-----------还款开始---" + apicron.getBorrowNid() + "---------");
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

		/** 投资人数据 */
		// 投资订单号
		String tenderOrderId = borrowRecover.getNid();
		// 投资人用户ID
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
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// [principal: 等额本金, month:等额本息, month:等额本息, endmonth:先息后本]
		if (isMonth) {
			// 取得分期还款计划表
			borrowRecoverPlan = getBorrowRecoverPlan(borrowNid, periodNow, tenderUserId, tenderOrderId);
			if (Validator.isNull(borrowRecoverPlan)) {
				throw new Exception("分期还款计划表数据不存在。[借款编号：" + borrowNid + "]，" + "[投资订单号：" + tenderOrderId + "]，" + "[期数：" + periodNow + "]");
			}
			// 还款订单号
			repayOrderId = borrowRecoverPlan.getRepayOrderId();
			// 应还款时间
			recoverTime = borrowRecoverPlan.getRecoverTime();
		}
		// [endday: 按天计息, end:按月计息]
		else {
			// 还款订单号
			repayOrderId = borrowRecover.getRepayOrdid();
			// 还款时间
			recoverTime = borrowRecover.getRecoverTime();
		}

		// 更新还款明细表
		// 分期并且不是最后一期
		if (borrowRecoverPlan != null && Validator.isNotNull(periodNext) && periodNext > 0) {
			borrowRecover.setRecoverStatus(0); // 未还款
			// 取得分期还款计划表下一期的还款
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
			throw new Exception("还款明细(huiyingdai_borrow_recover)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
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
					// 更新债转总表
					boolean borrowCreditFlag = this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit) > 0 ? true : false;
					// 债转总表更新成功
					if (!borrowCreditFlag) {
						throw new Exception("债转记录(huiyingdai_borrow_credit)更新失败！" + "[承接订单号：" + creditNid + "]");
					}
				}
			}
		}
		// 分期时
		if (Validator.isNotNull(borrowRecoverPlan)) {
			// 更新还款计划表
			borrowRecoverPlan.setRepayBatchNo(repayBatchNo);
			borrowRecoverPlan.setRecoverStatus(1);
			borrowRecoverPlan.setRecoverYestime(String.valueOf(nowTime));
			borrowRecoverPlan.setRecoverType(TYPE_YES);
			boolean borrowRecoverPlanFlag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan) > 0 ? true : false;
			if (!borrowRecoverPlanFlag) {
				throw new Exception("还款分期计划表(huiyingdai_borrow_recover_plan)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
			}
			// 更新总的还款计划
			BorrowRepayPlan borrowRepayPlan = getBorrowRepayPlan(borrowNid, periodNow);
			if (Validator.isNotNull(borrowRepayPlan)) {
				borrowRepayPlan.setRepayType(TYPE_WAIT_YES);
//				borrowRepayPlan.setRepayDays("0");
//				borrowRepayPlan.setRepayStep(4);
				borrowRepayPlan.setRepayActionTime(String.valueOf(nowTime));
				borrowRepayPlan.setRepayStatus(1);
				borrowRepayPlan.setRepayYestime(nowTime);
				boolean borrowRepayPlanFlag = this.borrowRepayPlanMapper.updateByPrimaryKeySelective(borrowRepayPlan) > 0 ? true : false;
				if (!borrowRepayPlanFlag) {
					throw new Exception("还款分期计划表(huiyingdai_borrow_repay_plan)更新失败！" + "[投资订单号：" + tenderOrderId + "]");
				}
			} else {
				throw new Exception("还款分期计划表(huiyingdai_borrow_repay_plan)查询失败！" + "[投资订单号：" + tenderOrderId + "]");
			}

		}
		logger.info("-----------还款结束---" + apicron.getBorrowNid() + "---------" + repayOrderId);
		return true;
	}

	/**
	 * 获取承接信息
	 * 
	 * @param assignNid
	 * @return
	 */
	private CreditTender getCreditTender(String assignNid) {
		CreditTenderExample example = new CreditTenderExample();
		CreditTenderExample.Criteria crt = example.createCriteria();
		crt.andAssignNidEqualTo(assignNid);
		List<CreditTender> creditTenderList = this.creditTenderMapper.selectByExample(example);
		if (creditTenderList != null && creditTenderList.size() == 1) {
			return creditTenderList.get(0);
		}
		return null;
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
	 * @param bigDecimal
	 * @param string
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
				smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, String.valueOf(userId), JSON.toJSONBytes(smsMessage)));
			} catch (MQException e2) {
				logger.error("发送邮件失败..", e2);
			}
		}
	}

	/**
	 * 推送消息
	 * 
	 * @param msgList
	 * @author Administrator
	 * @param bigDecimal
	 * @param string
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
				appMessageProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, String.valueOf(userId),
						JSON.toJSONBytes(smsMessage)));
			} catch (MQException e) {
				logger.error("发送app消息失败..", e);
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
	 * 判断是否一次性还款都已还款成功
	 * @param apicron
	 * @return
	 */
	private boolean isLastAllRepay(BorrowApicron apicron) {

		String borrowNid = apicron.getBorrowNid();
		BorrowApicronExample examle = new BorrowApicronExample();
		examle.createCriteria().andBorrowNidEqualTo(borrowNid).andApiTypeEqualTo(1).andStatusNotEqualTo(6);
		List<BorrowApicron> borrowApicrons = this.borrowApicronMapper.selectByExample(examle);
		if(borrowApicrons != null && borrowApicrons.size() > 0){
			return  false;
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
			smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
		} catch (MQException e2) {
			logger.error("发送邮件失败..", e2);
		}
	}
    
    
	
}
