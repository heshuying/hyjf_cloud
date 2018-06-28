/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.common.GetOrderIdUtils;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowApicronMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowInfoMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowRecoverMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowRecoverPlanMapper;
import com.hyjf.am.trade.dao.mapper.auto.CreditRepayMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhDebtCreditRepayMapper;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowApicronExample;
import com.hyjf.am.trade.dao.model.auto.BorrowExample;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowInfoExample;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverExample;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlanExample;
import com.hyjf.am.trade.dao.model.auto.CreditRepay;
import com.hyjf.am.trade.dao.model.auto.CreditRepayExample;
import com.hyjf.am.trade.service.BatchBorrowRepayPlanService;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

/**
 * @author dxj
 * @version BatchBorrowRepayPlanServiceImpl.java, v0.1 2018年6月23日 上午10:09:12
 */
public class BatchBorrowRepayPlanServiceImpl implements BatchBorrowRepayPlanService {

	private static final Logger logger = LoggerFactory.getLogger(BatchBorrowRepayPlanServiceImpl.class);

    @Autowired
    private BorrowApicronMapper borrowApicronMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BorrowInfoMapper borrowInfoMapper;

    @Autowired
    private BorrowRecoverMapper borrowRecoverMapper;

    @Autowired
    private BorrowRecoverPlanMapper borrowRecoverPlanMapper;

    @Autowired
    private HjhDebtCreditRepayMapper hjhDebtCreditRepayMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CreditRepayMapper creditRepayMapper;

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
								if(!isEndMonth){
									borrowRecoverPlan.setChargeInterest(borrowRecoverPlan.getChargeInterest().subtract(creditRepay.getChargeInterest()));
								}
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
		for (int i = 0; i < 3; i++) {
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
			repayBean.setLogRemark("批次状态查询");
			repayBean.setLogClient(0);
			BankCallBean queryResult = BankCallUtils.callApiBg(repayBean);
			if (Validator.isNotNull(queryResult)) {
				String retCode = StringUtils.isNotBlank(queryResult.getRetCode()) ? queryResult.getRetCode() : "";
				if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)||BankCallConstant.RESPCODE_BATCHNO_NOTEXIST.equals(retCode)) {
					return queryResult;
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
		return null;
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

	private BorrowInfo getBorrowInfoByNid(String borrowNid) {
		BorrowInfoExample example = new BorrowInfoExample();
		BorrowInfoExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<BorrowInfo> list = borrowInfoMapper.selectByExample(example);
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
		int nowTime = GetDate.getNowTime10();
		String borrowNid = apicron.getBorrowNid();
		BorrowApicronExample example = new BorrowApicronExample();
		example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
		apicron.setStatus(status);
//		apicron.setUpdateTime(nowTime);
		boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
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
    
    
	
}
