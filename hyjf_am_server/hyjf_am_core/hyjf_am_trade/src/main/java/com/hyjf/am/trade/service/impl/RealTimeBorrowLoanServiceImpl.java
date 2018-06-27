/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.common.GetOrderIdUtils;
import com.hyjf.am.trade.dao.mapper.auto.AccountBorrowMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowApicronMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowFinmanNewChargeMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowInfoMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowProjectTypeMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowRecoverMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowRecoverPlanMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowRepayMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowRepayPlanMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowTenderMapper;
import com.hyjf.am.trade.dao.mapper.auto.CalculateInvestInterestMapper;
import com.hyjf.am.trade.dao.mapper.auto.FreezeListMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.WebCalculateInvestInterestCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountBorrow;
import com.hyjf.am.trade.dao.model.auto.AccountBorrowExample;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowApicronExample;
import com.hyjf.am.trade.dao.model.auto.BorrowExample;
import com.hyjf.am.trade.dao.model.auto.BorrowFinmanNewCharge;
import com.hyjf.am.trade.dao.model.auto.BorrowFinmanNewChargeExample;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowInfoExample;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectTypeExample;
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
import com.hyjf.am.trade.dao.model.auto.CalculateInvestInterest;
import com.hyjf.am.trade.dao.model.auto.CalculateInvestInterestExample;
import com.hyjf.am.trade.dao.model.auto.FreezeList;
import com.hyjf.am.trade.dao.model.auto.FreezeListExample;
import com.hyjf.am.trade.mq.MailProducer;
import com.hyjf.am.trade.mq.Producer;
import com.hyjf.am.trade.mq.SmsProducer;
import com.hyjf.am.trade.service.RealTimeBorrowLoanService;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.common.util.calculate.InterestInfo;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

/**
 * @author dxj
 * @version RealTimeBorrowLoanServiceImpl.java, v0.1 2018年6月23日 上午10:09:12
 */
public class RealTimeBorrowLoanServiceImpl implements RealTimeBorrowLoanService {

	private static final Logger logger = LoggerFactory.getLogger(RealTimeBorrowLoanServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private BorrowTenderMapper borrowTenderMapper;

    @Autowired
    private BorrowApicronMapper borrowApicronMapper;

    @Autowired
    private CalculateInvestInterestMapper calculateInvestInterestMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BorrowInfoMapper borrowInfoMapper;

	@Autowired
	private WebCalculateInvestInterestCustomizeMapper webCalculateInvestInterestCustomizeMapper;

    @Autowired
    private BorrowProjectTypeMapper borrowProjectTypeMapper;

    @Autowired
    private BorrowFinmanNewChargeMapper borrowFinmanNewChargeMapper;

    @Autowired
    private FreezeListMapper freezeListMapper;

    @Autowired
    private BorrowRepayMapper borrowRepayMapper;

    @Autowired
    private BorrowRecoverMapper borrowRecoverMapper;

    @Autowired
    private BorrowRecoverPlanMapper borrowRecoverPlanMapper;

    @Autowired
    private BorrowRepayPlanMapper borrowRepayPlanMapper;

    @Autowired
    private AccountBorrowMapper accountBorrowMapper;

    @Autowired
    private AdminAccountCustomizeMapper adminAccountCustomizeMapper;

    @Autowired
    private AccountListMapper accountListMapper;
    
	@Autowired
	private MailProducer mailProducer;
    
	@Autowired
	private SmsProducer smsProducer;
    

	@Override
	public BankCallBean requestLoans(BorrowApicron apicron) {

		int borrowUserId = apicron.getUserId();// 放款用户userId
		String borrowNid = apicron.getBorrowNid();// 项目编号
		try {
			// 取得借款人账户信息
			Account borrowAccount = this.getAccountByUserId(borrowUserId);
			if (borrowAccount == null) {
				throw new Exception("借款人账户不存在。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
			}
			// 借款人在汇付的账户信息
//			BankOpenAccount borrowerAccount = this.getBankOpenAccount(borrowUserId);
//			if (borrowerAccount == null) {
//				throw new Exception("借款人未开户。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
//			}
			//TODO:确认电子账号是否正确
			
			String borrowAccountId = borrowAccount.getAccountId();// 借款人相应的银行账号
			// 取得借款详情
			Borrow borrow = this.getBorrowByNid(borrowNid);
			// 取得借款详情
			BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
			if (borrow == null || borrowInfo == null) {
				throw new Exception("借款详情不存在。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
			}

			// 获取标的费率信息
			String borrowClass = this.getBorrowProjectClass(borrowInfo.getProjectType()+ "");
			String queryBorrowStyle = null;
			if ("endday".equals(borrow.getBorrowStyle())) {//天标
				queryBorrowStyle = "endday";
			}else {
				queryBorrowStyle = "month";
			}
			BorrowFinmanNewCharge borrowFinmanNewCharge = this.selectBorrowApr(borrowClass,borrowInfo.getInstCode(),borrowInfo.getAssetType(),queryBorrowStyle,borrow.getBorrowPeriod());
			if(borrowFinmanNewCharge == null || borrowFinmanNewCharge.getChargeMode() == null){
				logger.info("获取标的费率信息失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]"+borrowClass);
				throw new RuntimeException("获取标的费率信息失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
			}
			//受托支付 add by cwyang
			Integer entrustedFlg = borrowInfo.getEntrustedFlg();
			if (1 == entrustedFlg) {//标的为受托支付时,实时放款改为放给受托指定的收款人
				Integer entrustedUserId = borrowInfo.getEntrustedUserId();
				if (entrustedUserId == null || entrustedUserId == 0) {
					throw new Exception("========================标的号:" + borrowNid + ",受托支付用户id为空,无法实时放款!------------------");
				}
				Account entrustedAccount = this.getAccountByUserId(entrustedUserId);
				borrowAccountId = entrustedAccount.getAccountId();
			}
			Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 借款期数
			// 服务费率
			BigDecimal serviceFeeRate = Validator.isNull(borrow.getServiceFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getServiceFeeRate());
			String borrowStyle = borrow.getBorrowStyle();// 项目还款方式
			// 取得投资详情列表
			List<BorrowTender> tenderList = this.getBorrowTenderList(borrowNid);
			BigDecimal curServiceFee = BigDecimal.ZERO;
			boolean isLast= false;
			if (tenderList != null && tenderList.size() > 0) {
				int txCounts = tenderList.size();// 交易笔数
				BigDecimal txAmountSum = BigDecimal.ZERO;// 交易总金额
				BigDecimal serviceFeeSum = BigDecimal.ZERO;// 交易总服务费
				Map map = new HashMap<>();
				map.put("accountId", borrowAccountId);
				/** 循环投资详情列表 */
				for (int i = 0; i < tenderList.size(); i++) {
					// 放款信息
					BorrowTender borrowTender = tenderList.get(i);
					BigDecimal txAmount = borrowTender.getAccount();// 交易金额
					txAmountSum = txAmountSum.add(txAmount);// 交易总金额
//					BigDecimal serviceFee = getUserFee(serviceFeeRate, txAmount, borrowStyle, borrowPeriod,borrow,curUserFee);// 放款服务费

					// 服务费
					BigDecimal serviceFee = BigDecimal.ZERO;
					// 每笔服务费都按照服务费率单独计算与服务费总额做比较，小于的情况下服务费按照比
					if(i == tenderList.size() -1){
						isLast = true;
					}
					if(borrowFinmanNewCharge.getChargeMode().intValue()==2){
						serviceFee = getUserFeeByChargeMode(serviceFeeRate, txAmount, borrowStyle, borrowPeriod, curServiceFee, borrowFinmanNewCharge.getServiceFeeTotal(),isLast);
						curServiceFee = curServiceFee.add(serviceFee);
					}else{
						serviceFee = getUserFee(serviceFeeRate, txAmount, borrowStyle, borrowPeriod);
					}
					logger.info("借款编号：" + borrowNid + "当前收服务费: "+serviceFee+" 当前已收："+curServiceFee);

					serviceFeeSum = serviceFeeSum.add(serviceFee);// 总服务费
				}
				map.put("txAmountSum", txAmountSum.toString());
				map.put("serviceFeeSum", serviceFeeSum.toString());
				// 拼接相应的放款参数
				if (apicron.getFailTimes() == 0) {
					apicron.setBatchAmount(txAmountSum);
					apicron.setBatchCounts(txCounts);
					apicron.setBatchServiceFee(serviceFeeSum);
					apicron.setSucAmount(BigDecimal.ZERO);
					apicron.setSucCounts(0);
					apicron.setFailAmount(txAmountSum);
					apicron.setFailCounts(txCounts);
				}
				apicron.setServiceFee(serviceFeeSum);
				apicron.setTxAmount(txAmountSum);
				apicron.setTxCounts(txCounts);
				apicron.setData(" ");
				//放款
				BankCallBean loanResult = this.requestLoans(apicron, map);
				if (Validator.isNotNull(loanResult)) {
					String retCode = loanResult.getRetCode();
					if (StringUtils.isNotBlank(retCode)) {
						if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode) || BankCallConstant.RESPCODE_REALTIMELOAN_REPEAT.equals(retCode)) {
							return loanResult;
						} else {
							throw new Exception("实时放款失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "],银行返回retCode = " + retCode);
						}
					} else {
						throw new Exception("放款请求失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
					}
				} else {
					throw new Exception("放款请求失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.info("=============== 放款错误,异常信息:" + e.getMessage());
		}
		return null;
	}

	/**
	 * 自动扣款（放款）(调用江西银行满标接口)
	 * @param apicron
	 * @param map
	 * @return
	 */
	@Override
	public BankCallBean requestLoans(BorrowApicron apicron, Map map) {

		int userId = apicron.getUserId();// 放款用户userId
		String borrowNid = apicron.getBorrowNid();// 项目编号
		// 获取共同参数
		String channel = BankCallConstant.CHANNEL_PC;
		try {
			String logOrderId = GetOrderIdUtils.getOrderId2(apicron.getUserId());
			String orderDate = GetOrderIdUtils.getOrderDate();
			String batchNo = GetOrderIdUtils.getBatchNo();// 获取放款批次号
			String txDate = GetOrderIdUtils.getTxDate();
			String txTime = GetOrderIdUtils.getTxTime();
			String seqNo = GetOrderIdUtils.getSeqNo(6);
			apicron.setBatchNo(batchNo);
			apicron.setTxDate(Integer.parseInt(txDate));
			apicron.setTxTime(Integer.parseInt(txTime));
			apicron.setSeqNo(Integer.parseInt(seqNo));
			apicron.setBankSeqNo(txDate + txTime + seqNo);
			//只保留原始订单号用于查询
			if (apicron.getOrdid() == null || "".equals(apicron.getOrdid())) {
				apicron.setOrdid(logOrderId);
			}
			String accountId = (String) map.get("accountId");
			String txAmount = (String) map.get("txAmountSum");
			String feeAmount = (String) map.get("serviceFeeSum");
			// 更新任务API状态为进行中
			boolean apicronFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_SENDING);
			if (!apicronFlag) {
				throw new Exception("更新放款任务为进行中失败。[用户ID：" + userId + "]," + "[借款编号：" + borrowNid + "]");
			}
			for (int i = 0; i < 3; i++) {
				// 调用放款接口
				BankCallBean loanBean = new BankCallBean();
				loanBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
				loanBean.setTxCode(BankCallConstant.TXCODE_AUTOLEND_PAY);// 消息类型(实时接口自动放款)
				loanBean.setTxDate(txDate);
				loanBean.setTxTime(txTime);
				loanBean.setSeqNo(seqNo);
				loanBean.setChannel(channel);
				loanBean.setAccountId(accountId);//借款人电子账号
				loanBean.setOrderId(logOrderId);
				loanBean.setTxAmount(txAmount);//投资总额
				loanBean.setFeeAmount(feeAmount);//手续费金额
				loanBean.setRiskAmount("0");//风险准备金
				loanBean.setProductId(borrowNid);//标的号
				loanBean.setLogUserId(String.valueOf(userId));
				loanBean.setLogOrderId(logOrderId);
				loanBean.setLogOrderDate(orderDate);
				loanBean.setLogRemark("实时接口自动放款请求");
				loanBean.setLogClient(0);
				BankCallBean loanResult = BankCallUtils.callApiBg(loanBean);
				if (Validator.isNotNull(loanResult)) {
					String retCode = StringUtils.isNotBlank(loanResult.getRetCode()) ? loanResult.getRetCode() : "";
					if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode) || BankCallConstant.RESPCODE_REALTIMELOAN_REPEAT.equals(retCode)) {//放款成功或放款重复
						// 更新任务API状态
						boolean apicronResultFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_SUCCESS);
						if (apicronResultFlag) {
							return loanResult;
						} else {
							throw new Exception("更新状态为（放款处理成功）失败。[用户ID：" + userId + "]," + "[借款编号：" + borrowNid + "]");
						}
					} else if ("".equals(retCode) || !BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {//重新查询处理结果
                        String oldOrdid = apicron.getOrdid();
                        if(StringUtils.isNotBlank(oldOrdid)){
                            loanBean.setOrderId(oldOrdid);
                        }
                        BankCallBean result = queryAutoLendResult(loanBean);
						if (result != null) {
							// 更新任务API状态
							boolean apicronResultFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_SUCCESS);
							if (apicronResultFlag) {
								loanResult.setRetCode(BankCallConstant.RESPCODE_SUCCESS);
								return loanResult;
							} else {
								throw new Exception("更新状态为（放款处理成功）失败。[用户ID：" + userId + "]," + "[借款编号：" + borrowNid + "]");
							}
						}else{
							boolean apicronResultFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_FAIL);
							if (apicronResultFlag) {
								return loanResult;
							} else {
								throw new Exception("更新状态为（放款处理失败）失败。[用户ID：" + userId + "]," + "[借款编号：" + borrowNid + "]");
							}
						}
					}else{// 放款失败
						// 更新任务API状态
						boolean apicronResultFlag = this.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_FAIL);
						if (apicronResultFlag) {
							return loanResult;
						} else {
							throw new Exception("更新状态为（放款失败）失败。[用户ID：" + userId + "]," + "[借款编号：" + borrowNid + "]");
						}
					}
				} else {
					// 掉单,重新请求
					continue;
				}
			}
		} catch (Exception e) {
			logger.info("==============cwyang 放款异常:" + e.getMessage());
		}
		return null;
	}

	/**
	 * 更新放款明细
	 * @param apicron
	 * @param bean
	 * @return
	 */
	@Override
	public boolean updateBatchDetailsQuery(BorrowApicron apicron, BankCallBean bean) {

		String borrowNid = apicron.getBorrowNid();// 項目编号
		Borrow borrow = this.getBorrowByNid(borrowNid);
		BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
		// 放款成功后后续操作
		try {
			boolean loanFlag = this.borrowLoans(apicron, borrow, borrowInfo, bean);
			if (loanFlag) {
				try {
					apicron = borrowApicronMapper.selectByPrimaryKey(apicron.getId());
					boolean borrowFlag = this.updateBorrowStatus(apicron, borrow, borrowInfo);
					if (borrowFlag) {
						return true;
					} else {
						throw new Exception("放款后，更新相应的标的状态失败,项目编号：" + borrowNid);
					}
				} catch (Exception e) {
					logger.info("==========cwyang 变更借款人数据信息异常!异常:" + e.getMessage());
				}
			}
		} catch (Exception e1) {
			logger.info("==========cwyang 还款数据变更异常!异常:" + e1.getMessage());
		}
		return false;
	}

	/**
	 * 自动放款
	 * @param apicron
	 * @param borrow
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	private boolean borrowLoans(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BankCallBean bean) throws Exception {
		/** 基本变量 */
		int nowTime = GetDate.getNowTime10();
		String borrowNid = apicron.getBorrowNid();// 借款编号
		BorrowApicronExample example = new BorrowApicronExample();
		example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
		apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_DOING);
		apicron.setUpdateTime(new Date());
		boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
		if (!apicronFlag) {
			throw new Exception("更新放款任务失败。[项目编号：" + borrowNid + "]");
		}
		borrow.setReverifyStatus(CustomConstants.BANK_BATCH_STATUS_DOING);
		boolean borrowFlag = this.borrowMapper.updateByPrimaryKey(borrow) > 0 ? true : false;
		if (!borrowFlag) {
			throw new Exception("更新borrow失败。[项目编号：" + borrowNid + "]");
		}
		String queryBorrowStyle = null;
		if ("endday".equals(borrow.getBorrowStyle())) {//天标
			queryBorrowStyle  = "endday";
		}else {
			queryBorrowStyle = "month";
		}
		// 获取标的费率信息
		String borrowClass = this.getBorrowProjectClass(borrowInfo.getProjectType()+ "");
		BorrowFinmanNewCharge borrowFinmanNewCharge = this.selectBorrowApr(borrowClass,borrowInfo.getInstCode(),borrowInfo.getAssetType(),queryBorrowStyle,borrow.getBorrowPeriod());
		if(borrowFinmanNewCharge == null || borrowFinmanNewCharge.getChargeMode() == null){
			logger.info("获取标的费率信息失败。[用户ID：" + borrowInfo.getUserId() + "]," + "[借款编号：" + borrowNid + "]"+borrowClass);
			throw new RuntimeException("获取标的费率信息失败。[用户ID：" + borrowInfo.getUserId() + "]," + "[借款编号：" + borrowInfo.getUserId() + "]");
		}
		// 取得投资详情列表
		List<BorrowTender> tenderList = this.getBorrowTenderList(borrowNid);
		BigDecimal curServiceFee = BigDecimal.ZERO;
		boolean isLast= false;
		BigDecimal recoverInterestSum = BigDecimal.ZERO;
		if (tenderList != null && tenderList.size() > 0) {
			for (int i = 0; i < tenderList.size(); i++) {
				try {
					// 投资明细
					BorrowTender borrowTender = tenderList.get(i);
					String tenderOrderId = borrowTender.getNid();// 投资订单号
					if (bean == null) {//放款失败
						// 更新投资详情表
						borrowTender.setStatus(2); // 状态 0，未放款，1，已放款 2放款失败
//						borrowTender.setTenderStatus(2); // 投资状态 0，未放款，1，已放款 2放款失败 //TODO:待确认是否需要删除
//						borrowTender.setApiStatus(2); // 放款状态 0，未放款，1，已放款 2放款失败
						boolean borrowTenderFlag = this.borrowTenderMapper.updateByPrimaryKeySelective(borrowTender) > 0 ? true : false;
						if (!borrowTenderFlag) {
							throw new Exception("投资详情(huiyingdai_borrow_tender)更新失败!" + "[投资订单号：" + tenderOrderId + "]");
						}
						System.out.println("-----------放款结束，放款失败---" + borrowNid + "--------投资订单号" + tenderOrderId);
					}else{
						//TODO 还款计划
						if(i == tenderList.size() -1){
							isLast = true;
						}
						boolean repayFlag = this.updateRepayPlanInfo(borrow, borrowInfo, borrowTender,curServiceFee,borrowFinmanNewCharge,isLast);
						curServiceFee = curServiceFee.add(borrowTender.getLoanFee());
						//end
						BigDecimal recoverInterest = null;
						if (repayFlag) {
						    //TODO 生成投资人居间服务协议


							 Map result = this.updateBorrowLoans(apicron, borrow, borrowInfo, borrowTender);
							 boolean tenderFlag = (boolean) result.get("result");
							 recoverInterest = (BigDecimal) result.get("recoverInterest");
							 recoverInterestSum = recoverInterestSum.add(recoverInterest);
							if (!tenderFlag) {
								throw new Exception("更新相应的投资记录失败！项目编号:" + borrowNid + "]" + "[投资订单号：" + tenderOrderId + "]");
							}
						}else{
							throw new Exception("标的号:" + borrowNid + ",放款完成保存还款计划失败!");
						}

						//双十二气球活动   
			           // actBalloonTender(borrowTender);
						
						//crm投资推送 //TODO: 坐等CRM 队列更新
//				        rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME,
//				                RabbitMQConstants.ROUTINGKEY_POSTINTERFACE_CRM, JSON.toJSONString(borrowTender));

					}
				} catch (Exception e) {
					logger.info("================cwyang 还款变更投资人数据异常!异常:" + e.getMessage());
					continue;
				}
			}
			List<CalculateInvestInterest> calculates = this.calculateInvestInterestMapper.selectByExample(new CalculateInvestInterestExample());
			if (calculates != null && calculates.size() > 0) {
				CalculateInvestInterest calculateNew = new CalculateInvestInterest();
				calculateNew.setInterestSum(recoverInterestSum);
				calculateNew.setId(calculates.get(0).getId());
				this.webCalculateInvestInterestCustomizeMapper.updateCalculateInvestByPrimaryKey(calculateNew);
			}
			//上市活动  //TODO: 这个活动不需要这么玩了
//			CommonSoaUtils.listBorrow(borrow.getBorrowNid());
			return true;
		} else {
			logger.info("未查询到相应的投资记录，项目编号:" + borrowNid + "]");
			return true;
		}
	}

	/**
	 * 取得满标日志
	 *
	 * @return
	 */
	private AccountBorrow getAccountBorrow(String borrowNid) {
		AccountBorrowExample example = new AccountBorrowExample();
		AccountBorrowExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<AccountBorrow> list = this.accountBorrowMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	

	/**
	 * 取出账户信息
	 *
	 * @param userId
	 * @return
	 */
	public Account getAccountByUserId(Integer userId) {
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andUserIdEqualTo(userId);
		List<Account> listAccount = this.accountMapper.selectByExample(accountExample);
		if (listAccount != null && listAccount.size() > 0) {
			return listAccount.get(0);
		}
		return null;
	}

	/**
	 * 取得借款列表
	 * @param borrowNid
	 * @return
	 */
	public List<BorrowTender> getBorrowTenderList(String borrowNid) {
		BorrowTenderExample example = new BorrowTenderExample();
		BorrowTenderExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andStatusNotEqualTo(1);
		example.setOrderByClause(" id asc ");
		List<BorrowTender> list = this.borrowTenderMapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 项目类型
	 * 
	 * @return
	 * @author Administrator
	 */
	private String getBorrowProjectClass(String borrowCd) {
		BorrowProjectTypeExample example = new BorrowProjectTypeExample();
		BorrowProjectTypeExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(CustomConstants.FLAG_STATUS_ENABLE);
		cra.andBorrowCdEqualTo(borrowCd);

		List<BorrowProjectType> list = this.borrowProjectTypeMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0).getBorrowClass();
		}
		return "";
	}
	
	/**
	 * 根据项目类型，期限，获取借款利率
	 * 
	 * @return
	 */
	private BorrowFinmanNewCharge selectBorrowApr(String projectType,String instCode, Integer instProjectType, String borrowStyle,Integer chargetime) {
		BorrowFinmanNewChargeExample example = new BorrowFinmanNewChargeExample();
		BorrowFinmanNewChargeExample.Criteria cra = example.createCriteria();
		cra.andProjectTypeEqualTo(projectType);
		cra.andInstCodeEqualTo(instCode);
		cra.andAssetTypeEqualTo(instProjectType);
		cra.andManChargeTimeTypeEqualTo(borrowStyle);
		cra.andManChargeTimeEqualTo(chargetime);
		cra.andStatusEqualTo(0);
		
		List<BorrowFinmanNewCharge> list = this.borrowFinmanNewChargeMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
		
	}
	
	/**
	 * 取得服务费,按金额算
	 *
	 * @param serviceFeeRate
	 *            服务费率
	 * @param account
	 *            金额
	 * @param borrowStyle
	 *            还款类型
	 * @param borrowPeriod
	 *            期数
	 * @return
	 */
	private BigDecimal getUserFeeByChargeMode(BigDecimal serviceFeeRate, BigDecimal account, String borrowStyle, Integer borrowPeriod, BigDecimal curServiceFee, BigDecimal totalServiceFee,boolean isLast) {
		BigDecimal userFee = BigDecimal.ZERO;

		// 计算放款金额
		if (serviceFeeRate == null || account == null || totalServiceFee == null
				|| curServiceFee.compareTo(totalServiceFee)>=0) {
			return userFee;
		}
		// 按天计息时,服务费乘以天数
		if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
			userFee = serviceFeeRate.multiply(account).multiply(new BigDecimal(borrowPeriod)).setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			userFee = serviceFeeRate.multiply(account).setScale(2, BigDecimal.ROUND_DOWN);
		}
		
		BigDecimal tmpFee = curServiceFee.add(userFee);

		if(isLast){
			if(tmpFee.compareTo(totalServiceFee) >=0){
				userFee = totalServiceFee.subtract(curServiceFee);
			}else{
				BigDecimal lastFee = totalServiceFee.subtract(curServiceFee);
				if(account.compareTo(lastFee)>=0){
					userFee = lastFee;
				}else{
//					userFee = userFee;
				}
			}

		}else{

			if(tmpFee.compareTo(totalServiceFee) >=0){
				userFee = totalServiceFee.subtract(curServiceFee);
			}
		}


		return userFee;
	}
	
	/**
	 * 取得服务费（服务费不四舍五入） 去尾
	 *
	 * @param serviceFee
	 *            服务费率
	 * @param account
	 *            金额
	 * @param borrowStyle
	 *            还款类型
	 * @param borrowPeriod
	 *            期数
	 * @return
	 */
	private BigDecimal getUserFee(BigDecimal serviceFee, BigDecimal account, String borrowStyle, Integer borrowPeriod) {
		BigDecimal userFee = BigDecimal.ZERO;

		// 计算放款金额
		if (serviceFee == null || account == null) {
			return userFee;
		}
		// 按天计息时,服务费乘以天数
		if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
			userFee = serviceFee.multiply(account).multiply(new BigDecimal(borrowPeriod)).setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			userFee = serviceFee.multiply(account).setScale(2, BigDecimal.ROUND_DOWN);
		}

		return userFee;
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
	 * 取出冻结订单
	 *
	 * @return
	 */
	private FreezeList getFreezeList(String ordId) {
		FreezeListExample example = new FreezeListExample();
		FreezeListExample.Criteria criteria = example.createCriteria();
		criteria.andOrdidEqualTo(ordId);
		List<FreezeList> list = this.freezeListMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 取得借款信息
	 *
	 * @return
	 */
	private BorrowRepay getBorrowRepay(String borrowNid) {
		BorrowRepayExample example = new BorrowRepayExample();
		BorrowRepayExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<BorrowRepay> list = this.borrowRepayMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 取得借款计划信息
	 *
	 * @return
	 */
	public BorrowRepayPlan getBorrowRepayPlan(String borrowNid, Integer period) {
		BorrowRepayPlanExample example = new BorrowRepayPlanExample();
		BorrowRepayPlanExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andRepayPeriodEqualTo(period);
		List<BorrowRepayPlan> list = this.borrowRepayPlanMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 更新借款API任务表
	 *
	 * @param apicron
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateBorrowApicron(BorrowApicron apicron, int status) throws Exception {

		if (status == 99) {
			throw new RuntimeException();
		}
		int nowTime = GetDate.getNowTime10();
		String borrowNid = apicron.getBorrowNid();
		BorrowApicronExample example = new BorrowApicronExample();
		example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
		apicron.setStatus(status);
//		apicron.setUpdateTime(nowTime); //TODO:不存，待测试确认
		boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
		if (!apicronFlag) {
			throw new Exception("更新放款任务失败。[项目编号：" + borrowNid + "]");
		}
		Borrow borrow = this.getBorrowByNid(borrowNid);
		borrow.setReverifyStatus(status);
		boolean borrowFlag = this.borrowMapper.updateByPrimaryKey(borrow) > 0 ? true : false;
		if (!borrowFlag) {
			throw new Exception("更新borrow失败。[项目编号：" + borrowNid + "]");
		}
		return borrowFlag;
	}

	/**
	 * 查询满标自动放款结果
	 * @param paramBean
	 * @return
	 */
	private BankCallBean queryAutoLendResult(BankCallBean paramBean) {
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);
		// 调用满标自动放款查询
		BankCallBean bean = new BankCallBean();
		// 版本号
		bean.setVersion(BankCallConstant.VERSION_10);
		// 交易代码
		bean.setTxCode(BankCallConstant.TXCODE_AUTOLEND_PAY_QUERY);
		// 渠道
		bean.setChannel(paramBean.getChannel());
		// 交易日期
		bean.setTxDate(txDate);
		// 交易时间
		bean.setTxTime(txTime);
		// 流水号
		bean.setSeqNo(seqNo);
		// 借款人电子账号
		bean.setAccountId(paramBean.getAccountId());
		// 申请订单号(满标放款交易订单号)
		bean.setLendPayOrderId(paramBean.getOrderId());
		// 标的编号
		bean.setProductId(paramBean.getProductId());
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
		bean.setLogUserId(paramBean.getLogUserId());
		bean.setLogOrderId(paramBean.getLogOrderId());
		bean.setLogRemark("满标自动放款查询");
		BankCallBean loanResult = BankCallUtils.callApiBg(bean);
		if (loanResult != null) {
			String retCode = loanResult.getRetCode();
			if (StringUtils.isNotBlank(retCode)) {
				if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
					return loanResult;
				}else{
					logger.info("===============cwyang 放款失败!放款标的:" + bean.getProductId() + ",返回码:" + retCode);
				}
			}
		}else{
			logger.info("===============cwyang 批次放款结果查询失败!放款标的:" + bean.getProductId());
		}
		return null;
	}

	/**
	 * 获取还款计划(原复审逻辑)
	 * @param borrowTender 
	 * @param borrow 
	 * @param borrowFinmanNewCharge
	 * @return
	 */
	private boolean updateRepayPlanInfo(Borrow borrow, BorrowInfo borrowInfo, BorrowTender borrowTender,BigDecimal curServiceFee, BorrowFinmanNewCharge borrowFinmanNewCharge,boolean isLast) {
		// 保存放款数据
		boolean loanFlag = true;
		try {
			String borrowStyle = borrow.getBorrowStyle();// 项目还款方式
			Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 借款期数
			// 年利率
			BigDecimal borrowApr = borrow.getBorrowApr();
			// 月利率(算出管理费用[上限])
			BigDecimal borrowMonthRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
			// 月利率(算出管理费用[下限])
			BigDecimal borrowManagerScaleEnd = Validator.isNull(borrowInfo.getBorrowManagerScaleEnd()) ? BigDecimal.ZERO : new BigDecimal(borrowInfo.getBorrowManagerScaleEnd());
			// 差异费率
			BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
			// 初审时间
			int borrowVerifyTime = borrow.getVerifyTime();
			// 项目类型
			Integer projectType = borrowInfo.getProjectType();
			// 借款人ID
			Integer borrowUserid = borrowInfo.getUserId();
			String borrowNid = borrow.getBorrowNid();
			String nid = borrow.getBorrowNid() + "_" + borrowInfo.getUserId() + "_1";
			// 是否月标(true:月标, false:天标)
			boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
					|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
			// 服务费率
			BigDecimal serviceFeeRate = Validator.isNull(borrow.getServiceFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getServiceFeeRate());
			// 投资人的账户信息
			int tenderUserId = borrowTender.getUserId();
			// 投资金额
			BigDecimal account = borrowTender.getAccount();
			// 借款人在汇付的账户信息
			Account tenderAccount = this.getAccountByUserId(tenderUserId);
			if (tenderAccount == null) {
				throw new RuntimeException("投资人未开户。[投资人ID：" + borrowTender.getUserId() + "]，" + "[投资订单号：" + borrowTender.getNid() + "]");
			}
			String ordId = borrowTender.getNid();// 投资订单号
			// 取出冻结订单信息
			FreezeList freezeList = getFreezeList(ordId);
			if (Validator.isNull(freezeList)) {
				throw new RuntimeException("冻结订单表(huiyingdai_freezeList)查询失败！, " + "投资订单号[" + ordId + "]");
			}
			// 若此笔订单已经解冻
			if (freezeList.getStatus() == 1) {
				throw new RuntimeException("冻结订单表(huiyingdai_freezeList)已经解冻！, " + "投资订单号[" + ordId + "]");
			}
			// 放款订单号
			String loanOrderId = GetOrderIdUtils.getOrderId2(borrowTender.getUserId());
			// 放款订单时间
			String loanOrderDate = GetOrderIdUtils.getOrderDate();
			// 业务授权码
			String authCode = borrowTender.getAuthCode();
			
			// 服务费
//			BigDecimal serviceFee = getUserFee(serviceFeeRate, account, borrowStyle, borrowPeriod,borrow,cruUserFee);

			// 服务费
			BigDecimal serviceFee = BigDecimal.ZERO;
			// 每笔服务费都按照服务费率单独计算与服务费总额做比较，小于的情况下服务费按照比
			if(borrowFinmanNewCharge.getChargeMode().intValue()==2){
				serviceFee = getUserFeeByChargeMode(serviceFeeRate, account, borrowStyle, borrowPeriod, curServiceFee, borrowFinmanNewCharge.getServiceFeeTotal(),isLast);
			}else{
				serviceFee = getUserFee(serviceFeeRate, account, borrowStyle, borrowPeriod);
			}
			logger.info("借款编号：" + borrowNid + "当前收服务费: "+serviceFee+" 当前已收："+curServiceFee);

			// 利息
			BigDecimal interestTender = BigDecimal.ZERO;
			// 本金
			BigDecimal capitalTender = BigDecimal.ZERO;
			// 本息
			BigDecimal accountTender = BigDecimal.ZERO;
			// 管理费
			BigDecimal recoverFee = BigDecimal.ZERO;
			// 估计还款时间
			Integer recoverTime = null;
			Integer nowTime = GetDate.getNowTime10();
			// 计算利息
			InterestInfo interestInfo = CalculatesUtil.getInterestInfo(account, borrowPeriod, borrowApr, borrowStyle, nowTime , borrowMonthRate, borrowManagerScaleEnd,
					projectType, differentialRate, borrowVerifyTime);
			if (interestInfo != null) {
				interestTender = interestInfo.getRepayAccountInterest(); // 利息
				capitalTender = interestInfo.getRepayAccountCapital(); // 本金
				accountTender = interestInfo.getRepayAccount(); // 本息
				recoverTime = interestInfo.getRepayTime(); // 估计还款时间
				recoverFee = interestInfo.getFee(); // 总管理费
			}
			// 写入还款明细表(huiyingdai_borrow_recover)
			BorrowRecover borrowRecover = new BorrowRecover();
			borrowRecover.setStatus(0); // 状态
			borrowRecover.setUserId(borrowTender.getUserId()); // 投资人
			borrowRecover.setBorrowNid(borrowNid); // 借款编号
			borrowRecover.setNid(ordId); // 投资订单号
			borrowRecover.setBorrowUserid(borrowUserid); // 借款人
			borrowRecover.setTenderId(borrowTender.getId()); // 投资表主键ID
			borrowRecover.setRecoverStatus(0); // 还款状态
			borrowRecover.setRecoverPeriod(isMonth ? borrowPeriod : 1); // 还款期数
			borrowRecover.setRecoverTime(recoverTime); // 估计还款时间
			borrowRecover.setRecoverAccount(accountTender); // 预还金额
			borrowRecover.setRecoverInterest(interestTender); // 预还利息
			borrowRecover.setRecoverCapital(capitalTender); // 预还本金
			borrowRecover.setRecoverAccountYes(BigDecimal.ZERO); // 实还金额
			borrowRecover.setRecoverInterestYes(BigDecimal.ZERO); // 实还利息
			borrowRecover.setRecoverCapitalYes(BigDecimal.ZERO); // 实还本金
			borrowRecover.setRecoverAccountWait(accountTender); // 未还金额
			borrowRecover.setRecoverInterestWait(interestTender); // 未还利息
			borrowRecover.setRecoverCapitalWait(capitalTender); // 未还本金
			borrowRecover.setRecoverType("wait"); // 还款状态:等待
			borrowRecover.setRecoverFee(recoverFee); // 账户管理费
			borrowRecover.setRecoverFeeYes(BigDecimal.ZERO);
			borrowRecover.setRecoverLateFee(BigDecimal.ZERO); // 逾期费用收入
//			borrowRecover.setRecoverWeb(0); // 网站待还
//			borrowRecover.setRecoverWebTime("");
//			borrowRecover.setRecoverVouch(0); // 担保人还款
			borrowRecover.setAdvanceStatus(0); // 提前还款
//			borrowRecover.setAheadDays(0); // 提前还款天数
			borrowRecover.setChargeDays(0); // 罚息天数
			borrowRecover.setChargeInterest(BigDecimal.ZERO); // 罚息总额
			borrowRecover.setLateDays(0); // 逾期天数
			borrowRecover.setLateInterest(BigDecimal.ZERO); // 逾期利息
//			borrowRecover.setLateForfeit(BigDecimal.ZERO); // 逾期滞纳金
//			borrowRecover.setLateReminder(BigDecimal.ZERO); // 逾期崔收费
//			borrowRecover.setCreateTime(nowTime);
			borrowRecover.setAddIp(borrowTender.getAddIp());
			borrowRecover.setAuthCode(authCode);
			borrowRecover.setRecoverServiceFee(serviceFee);
			boolean borrowRecoverFlag = borrowRecoverMapper.insertSelective(borrowRecover) > 0 ? true : false;
			if (!borrowRecoverFlag) {
				throw new RuntimeException("还款明细表(huiyingdai_borrow_tender)写入失败!" + "[投资订单号：" + ordId + "]");
			}
			// 更新投资详情表
			borrowTender.setLoanOrdid(loanOrderId);
			borrowTender.setLoanOrderDate(loanOrderDate);
			borrowTender.setRecoverAccountWait(borrowRecover.getRecoverAccount()); // 待收总额
			borrowTender.setRecoverAccountAll(borrowRecover.getRecoverAccount()); // 收款总额
			borrowTender.setRecoverAccountInterestWait(borrowRecover.getRecoverInterest()); // 待收利息
			borrowTender.setRecoverAccountInterest(borrowRecover.getRecoverInterest()); // 收款总利息
			borrowTender.setRecoverAccountCapitalWait(borrowRecover.getRecoverCapital()); // 待收本金
			borrowTender.setLoanAmount(account.subtract(serviceFee)); // 实际放款金额
			borrowTender.setLoanFee(serviceFee); // 服务费
			borrowTender.setRecoverFee(recoverFee);// 管理费
			borrowTender.setStatus(0); // 状态
										// 0，未放款，1，已放款
//			borrowTender.setTenderStatus(0); // 投资状态
//												// 0，未放款，1，已放款
//			borrowTender.setApiStatus(0); // 放款状态
//											// 0，未放款，1，已放款
//			borrowTender.setWeb(0); // 写入网站收支明细
			boolean borrowTenderFlag = borrowTenderMapper.updateByPrimaryKeySelective(borrowTender) > 0 ? true : false;
			if (!borrowTenderFlag) {
				throw new RuntimeException("投资详情(huiyingdai_borrow_tender)更新失败!" + "[投资订单号：" + ordId + "]");
			}
			// 更新借款表
			borrow = this.getBorrowByNid(borrowNid);
			borrow.setRepayAccountAll(borrow.getRepayAccountAll().add(borrowRecover.getRecoverAccount())); // 应还款总额
			borrow.setRepayAccountInterest(borrow.getRepayAccountInterest().add(borrowRecover.getRecoverInterest())); // 总还款利息
			borrow.setRepayAccountCapital(borrow.getRepayAccountCapital().add(borrowRecover.getRecoverCapital())); // 总还款本金
			borrow.setRepayAccountWait(borrow.getRepayAccountWait().add(borrowRecover.getRecoverAccount())); // 未还款总额
			borrow.setRepayAccountInterestWait(borrow.getRepayAccountInterestWait().add(borrowRecover.getRecoverInterest())); // 未还款利息
			borrow.setRepayAccountCapitalWait(borrow.getRepayAccountCapitalWait().add(borrowRecover.getRecoverCapital())); // 未还款本金
			borrow.setRepayLastTime(DateUtils.getRepayDate(borrowStyle, new Date(), borrowPeriod, borrowPeriod)); // 最后还款时间
			borrow.setRepayNextTime(recoverTime); // 下次还款时间
//			borrow.setRepayEachTime("每月" + GetDate.getServerDateTime(15, new Date()) + "日");// 每次还款的时间
			boolean borrowFlags = this.borrowMapper.updateByPrimaryKeySelective(borrow) > 0 ? true : false;
			if (!borrowFlags) {
				throw new RuntimeException("借款详情(huiyingdai_borrow)更新失败!" + "[投资订单号：" + ordId + "]");
			}
			boolean isInsert = false;
			// 插入每个标的总的还款信息
			BorrowRepay borrowRepay = getBorrowRepay(borrowNid);
			if (borrowRepay == null) {
				isInsert = true;
				borrowRepay = new BorrowRepay();
				borrowRepay.setStatus(0); // 状态
				borrowRepay.setUserId(borrowUserid); // 借款人ID
				borrowRepay.setBorrowNid(borrowNid); // 借款标号
				borrowRepay.setNid(nid); // 标识
				borrowRepay.setRepayType("wait"); // 还款状态(等待)
				borrowRepay.setRepayFee(BigDecimal.ZERO); // 还款费用
//				borrowRepay.setRepayDays(""); // 还款时间间距
//				borrowRepay.setRepayStep(0); // 还款步骤
//				borrowRepay.setRepayActionTime(0); // 执行还款的时间
				borrowRepay.setRepayStatus(0); // 还款状态
				borrowRepay.setRepayPeriod(isMonth ? borrowPeriod : 1); // 还款期数
				borrowRepay.setRepayTime(recoverTime); // 估计还款时间
//				borrowRepay.setRepayYestime(""); // 实际还款时间
				borrowRepay.setRepayAccountAll(BigDecimal.ZERO); // 还款总额，加上费用
				borrowRepay.setRepayAccount(BigDecimal.ZERO); // 预还金额
				borrowRepay.setRepayInterest(BigDecimal.ZERO); // 预还利息
				borrowRepay.setRepayCapital(BigDecimal.ZERO); // 预还本金
				borrowRepay.setRepayAccountYes(BigDecimal.ZERO); // 实还金额
				borrowRepay.setLateRepayDays(0); // 逾期的天数
				borrowRepay.setRepayInterestYes(BigDecimal.ZERO); // 实还利息
				borrowRepay.setRepayCapitalYes(BigDecimal.ZERO); // 实还本金
				borrowRepay.setRepayCapitalWait(BigDecimal.ZERO);// 未还本金
				borrowRepay.setRepayInterestWait(BigDecimal.ZERO); // 未还利息
//				borrowRepay.setRepayWeb(0); // 网站待还
//				borrowRepay.setRepayWebTime(""); //
//				borrowRepay.setRepayWebStep(0); // 提前还款
//				borrowRepay.setRepayWebAccount(BigDecimal.ZERO); // 网站垫付金额
//				borrowRepay.setRepayVouch(0); // 担保人还款
				borrowRepay.setAdvanceStatus(0); // 进展
//				borrowRepay.setLateRepayStatus(0); // 是否逾期还款
				borrowRepay.setLateDays(0); // 逾期天数
				borrowRepay.setLateInterest(BigDecimal.ZERO); // 逾期利息
//				borrowRepay.setLateForfeit(BigDecimal.ZERO); // 逾期滞纳金
//				borrowRepay.setLateReminder(BigDecimal.ZERO); // 逾期崔收费
				borrowRepay.setDelayDays(0); // 逾期天数
				borrowRepay.setDelayInterest(BigDecimal.ZERO); // 逾期利息
				borrowRepay.setDelayRemark(""); // 备注
//				borrowRepay.setAddtime(String.valueOf(nowTime)); // 发标时间
				borrowRepay.setAddIp(borrow.getAddIp()); // 发标ip
//				borrowRepay.setCreateTime(nowTime); // 创建时间
				borrowRepay.setChargeDays(0);
				borrowRepay.setChargeInterest(BigDecimal.ZERO);
			}
			borrowRepay.setRepayFee(borrowRepay.getRepayFee().add(borrowRecover.getRecoverFee())); // 还款费用
			borrowRepay.setRepayAccount(borrowRepay.getRepayAccount().add(borrowRecover.getRecoverAccount())); // 预还金额
			borrowRepay.setRepayInterest(borrowRepay.getRepayInterest().add(borrowRecover.getRecoverInterest())); // 预还利息
			borrowRepay.setRepayCapital(borrowRepay.getRepayCapital().add(borrowRecover.getRecoverCapital())); // 预还本金
			boolean borrowRepayFlag = false;
			if (isInsert) {
				borrowRepayFlag = this.borrowRepayMapper.insertSelective(borrowRepay) > 0 ? true : false;
			} else {
				borrowRepayFlag = this.borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay) > 0 ? true : false;
			}
			if (!borrowRepayFlag) {
				throw new RuntimeException("每个标的总的还款信息(huiyingdai_borrow_repay)" + (isInsert ? "插入" : "更新") + "失败!" + "[投资订单号：" + ordId + "]");
			}
			// 是否分期
			if (isMonth) {
				// 更新分期还款计划表(huiyingdai_borrow_recover_plan)
				if (interestInfo != null && interestInfo.getListMonthly() != null) {
					BorrowRecoverPlan recoverPlan = null;
					InterestInfo monthly = null;
					for (int j = 0; j < interestInfo.getListMonthly().size(); j++) {
						monthly = interestInfo.getListMonthly().get(j);
						recoverPlan = new BorrowRecoverPlan();
						recoverPlan.setStatus(0); // 状态
						recoverPlan.setUserId(tenderUserId); // 投资人id
						recoverPlan.setBorrowNid(borrowNid); // 借款订单id
						recoverPlan.setNid(ordId); // 投资订单号
						recoverPlan.setBorrowUserid(borrowUserid); // 借款人ID
						recoverPlan.setTenderId(borrowTender.getId()); // 借款人ID
						recoverPlan.setRecoverStatus(0); //
						recoverPlan.setRecoverPeriod(j + 1); // 还款期数
						recoverPlan.setRecoverTime(monthly.getRepayTime()); // 估计还款时间
						recoverPlan.setRecoverAccount(monthly.getRepayAccount()); // 预还金额
						recoverPlan.setRecoverInterest(monthly.getRepayAccountInterest()); // 预还利息
						recoverPlan.setRecoverCapital(monthly.getRepayAccountCapital()); // 预还本金
						recoverPlan.setRecoverFee(monthly.getFee()); // 预还管理费
						recoverPlan.setRecoverFeeYes(BigDecimal.ZERO);
						recoverPlan.setRecoverYestime(""); // 实际还款时间
						recoverPlan.setRecoverAccountYes(BigDecimal.ZERO); // 实还金额
						recoverPlan.setRecoverInterestYes(BigDecimal.ZERO); // 实还利息
						recoverPlan.setRecoverCapitalYes(BigDecimal.ZERO); // 实还本金
						recoverPlan.setRecoverAccountWait(monthly.getRepayAccount()); // 未还金额
						recoverPlan.setRecoverCapitalWait(monthly.getRepayAccountCapital()); // 未还本金
						recoverPlan.setRecoverInterestWait(monthly.getRepayAccountInterest()); // 未还利息
						recoverPlan.setRecoverType("wait"); // 等待
						recoverPlan.setRecoverLateFee(BigDecimal.ZERO); // 逾期管理费
//						recoverPlan.setRecoverWeb(0); // 网站待还
//						recoverPlan.setRecoverWebTime(""); //
//						recoverPlan.setRecoverVouch(0); // 担保人还款
						recoverPlan.setAdvanceStatus(0); //
//						recoverPlan.setAheadDays(0); // 提前还款天数
						recoverPlan.setChargeDays(0); // 罚息天数
						recoverPlan.setChargeInterest(BigDecimal.ZERO); // 罚息总额
						recoverPlan.setLateDays(0); // 逾期天数
						recoverPlan.setLateInterest(BigDecimal.ZERO); // 逾期利息
//						recoverPlan.setLateForfeit(BigDecimal.ZERO); // 逾期滞纳金
//						recoverPlan.setLateReminder(BigDecimal.ZERO); // 逾期崔收费
						recoverPlan.setDelayDays(0); // 延期天数
						recoverPlan.setDelayInterest(BigDecimal.ZERO); // 延期利息
						recoverPlan.setDelayRate(BigDecimal.ZERO); // 延期费率
//						recoverPlan.setCreateTime(nowTime); // 创建时间
						recoverPlan.setAddIp(borrowTender.getAddIp());
						recoverPlan.setSendmail(0);
						boolean borrowRecoverPlanFlag = this.borrowRecoverPlanMapper.insertSelective(recoverPlan) > 0 ? true : false;
						if (!borrowRecoverPlanFlag) {
							throw new RuntimeException("分期还款计划表(huiyingdai_borrow_recover_plan)写入失败!" + "[投资订单号：" + ordId + "]，" + "[期数：" + j + 1 + "]");
						}
						// 更新总的还款计划表(huiyingdai_borrow_repay_plan)
						isInsert = false;
						BorrowRepayPlan repayPlan = getBorrowRepayPlan(borrowNid, j + 1);
						if (repayPlan == null) {
							isInsert = true;
							repayPlan = new BorrowRepayPlan();
							repayPlan.setStatus(0); // 状态
							repayPlan.setUserId(borrowUserid); // 借款人
							repayPlan.setBorrowNid(borrowNid); // 借款订单id
							repayPlan.setNid(nid); // 标识
							repayPlan.setRepayType("wait"); // 还款类型
							repayPlan.setRepayFee(BigDecimal.ZERO); // 还款费用
//							repayPlan.setRepayDays(""); // 还款时间间距
//							repayPlan.setRepayStep(0); // 还款步骤
							repayPlan.setRepayActionTime(""); // 执行还款的时间
							repayPlan.setRepayStatus(0); // 还款状态
							repayPlan.setRepayPeriod(j + 1); // 还款期数
							repayPlan.setRepayTime(recoverPlan.getRecoverTime()); // 估计还款时间
//							repayPlan.setRepayYestime(""); // 实际还款时间
							repayPlan.setRepayAccountAll(BigDecimal.ZERO); // 还款总额，加上费用
							repayPlan.setRepayAccount(BigDecimal.ZERO); // 预还金额
							repayPlan.setRepayInterest(BigDecimal.ZERO); // 预还利息
							repayPlan.setRepayCapital(BigDecimal.ZERO); // 预还本金
							repayPlan.setRepayAccountYes(BigDecimal.ZERO); // 实还金额
							repayPlan.setRepayInterestYes(BigDecimal.ZERO); // 实还利息
							repayPlan.setRepayCapitalYes(BigDecimal.ZERO); // 实还本金
							repayPlan.setRepayCapitalWait(BigDecimal.ZERO); // 未还本金
							repayPlan.setRepayInterestWait(BigDecimal.ZERO); // 未还利息
//							repayPlan.setRepayWeb(0); // 网站待还
//							repayPlan.setRepayWebTime("");
//							repayPlan.setRepayWebStep(0); // 提前还款
//							repayPlan.setRepayWebAccount(BigDecimal.ZERO); // 网站垫付金额
//							repayPlan.setRepayVouch(0); // 担保人还款
							repayPlan.setAdvanceStatus(0); // 进展
//							repayPlan.setLateRepayStatus(0); // 是否逾期还款
							repayPlan.setLateDays(0); // 逾期天数
							repayPlan.setLateInterest(BigDecimal.ZERO); // 逾期利息
//							repayPlan.setLateForfeit(BigDecimal.ZERO); // 逾期滞纳金
//							repayPlan.setLateReminder(BigDecimal.ZERO); // 逾期崔收费
							repayPlan.setLateRepayDays(0); // 逾期还款天数
							repayPlan.setDelayDays(0); // 延期天数
							repayPlan.setDelayInterest(BigDecimal.ZERO); // 延期利息
							repayPlan.setDelayRemark(""); // 延期备注
//							repayPlan.setAddtime(String.valueOf(nowTime)); // 借款成功时间
							repayPlan.setAddIp(borrowTender.getAddIp());
//							repayPlan.setCreateTime(nowTime); // 创建时间
							repayPlan.setChargeDays(0);
							repayPlan.setChargeInterest(BigDecimal.ZERO);
						}
						repayPlan.setRepayFee(repayPlan.getRepayFee().add(recoverPlan.getRecoverFee())); // 还款费用
						repayPlan.setRepayAccount(repayPlan.getRepayAccount().add(recoverPlan.getRecoverAccount())); // 预还金额
						repayPlan.setRepayInterest(repayPlan.getRepayInterest().add(recoverPlan.getRecoverInterest())); // 预还利息
						repayPlan.setRepayCapital(repayPlan.getRepayCapital().add(recoverPlan.getRecoverCapital())); // 预还本金
						boolean repayPlanFlag = false;
						if (isInsert) {
							repayPlanFlag = this.borrowRepayPlanMapper.insertSelective(repayPlan) > 0 ? true : false;
						} else {
							repayPlanFlag = this.borrowRepayPlanMapper.updateByPrimaryKeySelective(repayPlan) > 0 ? true : false;
						}
						if (!repayPlanFlag) {
							throw new RuntimeException("总的还款计划表(huiyingdai_borrow_repay_plan)写入失败!" + "[投资订单号：" + ordId + "]，" + "[期数：" + j + 1 + "]");
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			loanFlag = false; 
		}
		return loanFlag;
	}

	/**
	 * 更新放款完成状态
	 * 
	 * @param borrow
	 *
	 * @param apicron
	 * @param borrow
	 * @throws Exception
	 */
	private boolean updateBorrowStatus(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo) throws Exception {

		int nowTime = GetDate.getNowTime10();// 当前时间
		apicron = this.borrowApicronMapper.selectByPrimaryKey(apicron.getId());
		String borrowNid = apicron.getBorrowNid();// 项目编号
		String nid = apicron.getNid();// 放款唯一号
		int borrowUserId = apicron.getUserId();// 借款人
		int txDate = Validator.isNotNull(apicron.getTxDate()) ? apicron.getTxDate() : 0;// 批次时间yyyyMMdd
		int txTime = Validator.isNotNull(apicron.getTxTime()) ? apicron.getTxTime() : 0;// 批次时间hhmmss
		String seqNo = Validator.isNotNull(apicron.getSeqNo()) ? String.valueOf(apicron.getSeqNo()) : null;// 流水号
		String bankSeqNo = Validator.isNotNull(apicron.getBankSeqNo()) ? String.valueOf(apicron.getBankSeqNo()) : null;// 银行唯一订单号
		int borrowId = borrow.getId();// 标的记录主键
		String borrowStyle = borrow.getBorrowStyle();// 项目还款方式
		//add by cwyang 2017-10-24 获得是否为受托支付
		Integer entrustedFlg = null;
		Integer entrustedUserId = null;
		if (borrowInfo.getEntrustedFlg() != null) {
			entrustedFlg = borrowInfo.getEntrustedFlg(); 
			entrustedUserId = borrowInfo.getEntrustedUserId();//受托支付userID
		}
		//end
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		int tenderCount = apicron.getTxCounts();// 放款总笔数
		// 查询失败的放款订单
		BorrowTenderExample tenderFailExample = new BorrowTenderExample();
		tenderFailExample.createCriteria().andBorrowNidEqualTo(borrowNid).andStatusNotEqualTo(1);
		int failCount = this.borrowTenderMapper.countByExample(tenderFailExample);
		Borrow newBorrow = new Borrow();
		BorrowRepay newBorrowRepay = new BorrowRepay();
		BorrowRepayPlan newBorrowRepayPlan = new BorrowRepayPlan();
		int status = 3;
		if (failCount == 0) {
			status = 4;
			// 更新BorrowRepay
			newBorrowRepay.setStatus(1); // 已放款
			BorrowRepayExample repayExample = new BorrowRepayExample();
			repayExample.createCriteria().andBorrowNidEqualTo(borrowNid);
			boolean borrowRepayFlag = this.borrowRepayMapper.updateByExampleSelective(newBorrowRepay, repayExample) > 0 ? true : false;
			if (!borrowRepayFlag) {
				throw new RuntimeException("更新还款总表borrowrepay失败，项目编号:" + borrowNid + "]");
			}
			if (isMonth) {
				// 更新BorrowRepayPlan
				newBorrowRepayPlan.setStatus(1); // 已放款
				BorrowRepayPlanExample repayPlanExample = new BorrowRepayPlanExample();
				repayPlanExample.createCriteria().andBorrowNidEqualTo(borrowNid);
				boolean borrowRepayPlanFlag = this.borrowRepayPlanMapper.updateByExampleSelective(newBorrowRepayPlan, repayPlanExample) > 0 ? true : false;
				if (!borrowRepayPlanFlag) {
					throw new RuntimeException("更新还款分期表borrowrepayplan失败，项目编号:" + borrowNid + "]");
				}
			}
			// 放款总信息表
			AccountBorrow accountBorrow = getAccountBorrow(borrowNid);
			if (Validator.isNull(accountBorrow)) {
				throw new RuntimeException("查询相应的放款日志accountBorrow失败，项目编号:" + borrowNid + "]");
			}
			BigDecimal amount = accountBorrow.getMoney();
			BigDecimal realAcmount = accountBorrow.getBalance();
			BigDecimal interest = accountBorrow.getInterest();
			BigDecimal manageFee = accountBorrow.getManageFee();
			boolean isEntrusted = false;
			if (entrustedFlg != null && 1 == entrustedFlg) {//属于受托支付,需要插入受托账户的资金明细
				if (entrustedUserId == null) {
					throw new RuntimeException("------------标的号:" + borrowNid + "属于受托支付,受托支付用户id为空!");
				}
				isEntrusted = true;
			}
			// 更新借款人账户表(原复审业务)
			Account borrowAccount = new Account();
			Integer userId = borrowUserId;
			if (isEntrusted) {//受托支付,借款人记录待还金额,受托支付指定的账户接收放款金额
				userId = entrustedUserId;
				borrowAccount.setUserId(userId);
				borrowAccount.setBankTotal(realAcmount);// 累加到账户总资产
				borrowAccount.setBankBalance(realAcmount); // 累加到可用余额
				borrowAccount.setBankBalanceCash(realAcmount);// 江西银行可用余额
				borrowAccount.setBankWaitRepay(BigDecimal.ZERO); // 待还金额
				borrowAccount.setBankWaitCapital(BigDecimal.ZERO); // 待还本金
				borrowAccount.setBankWaitInterest(BigDecimal.ZERO);// 待还利息
				boolean borrowAccountFlag = this.adminAccountCustomizeMapper.updateOfLoansBorrow(borrowAccount) > 0 ? true : false;
				if (!borrowAccountFlag) {
					throw new RuntimeException("受托支付指定收款子账户资金记录(huiyingdai_account)更新失败!" + "[项目编号：" + borrowNid + "]");
				}
				Account borrowuserAccount = new Account();
				borrowuserAccount.setUserId(borrowUserId);
				borrowuserAccount.setBankTotal(BigDecimal.ZERO);// 累加到账户总资产
				borrowuserAccount.setBankBalance(BigDecimal.ZERO); // 累加到可用余额
				borrowuserAccount.setBankBalanceCash(BigDecimal.ZERO);// 江西银行可用余额
				borrowuserAccount.setBankWaitRepay(amount.add(interest).add(manageFee)); // 待还金额
				borrowuserAccount.setBankWaitCapital(amount); // 待还本金
				borrowuserAccount.setBankWaitInterest(interest);// 待还利息
				boolean accountFlag = this.adminAccountCustomizeMapper.updateOfLoansBorrow(borrowuserAccount) > 0 ? true : false;
				if (!accountFlag) {
					throw new RuntimeException("借款人账户资金记录(huiyingdai_account)更新失败!" + "[项目编号：" + borrowNid + "]");
				}
			}else{//非受托支付
				borrowAccount.setUserId(userId);
				borrowAccount.setBankTotal(realAcmount);// 累加到账户总资产
				borrowAccount.setBankBalance(realAcmount); // 累加到可用余额
				borrowAccount.setBankWaitRepay(amount.add(interest).add(manageFee)); // 待还金额
				borrowAccount.setBankWaitCapital(amount); // 待还本金
				borrowAccount.setBankWaitInterest(interest);// 待还利息
				borrowAccount.setBankBalanceCash(realAcmount);// 江西银行可用余额
				boolean borrowAccountFlag = this.adminAccountCustomizeMapper.updateOfLoansBorrow(borrowAccount) > 0 ? true : false;
				if (!borrowAccountFlag) {
					throw new RuntimeException("借款人资金记录(huiyingdai_account)更新失败!" + "[项目编号：" + borrowNid + "]");
				}
			}
			
			// 取得借款人账户信息
			borrowAccount = getAccountByUserId(userId);
//			BankOpenAccount borrowOpenAccount = this.getBankOpenAccount(userId);
			// 插入借款人的收支明细表(原复审业务)
			AccountList accountList = new AccountList();
			accountList.setBankAwait(borrowAccount.getBankAwait());
			accountList.setBankAwaitCapital(borrowAccount.getBankAwaitCapital());
			accountList.setBankAwaitInterest(borrowAccount.getBankAwaitInterest());
			accountList.setBankBalance(borrowAccount.getBankBalance());
			accountList.setBankFrost(borrowAccount.getBankFrost());
			accountList.setBankInterestSum(borrowAccount.getBankInterestSum());
			accountList.setBankTotal(borrowAccount.getBankTotal());
			accountList.setBankWaitCapital(borrowAccount.getBankWaitCapital());
			accountList.setBankWaitInterest(borrowAccount.getBankWaitInterest());
			accountList.setBankWaitRepay(borrowAccount.getBankWaitRepay());
			accountList.setPlanBalance(borrowAccount.getPlanBalance());//汇计划账户可用余额
			accountList.setPlanFrost(borrowAccount.getPlanFrost());
			accountList.setAccountId(borrowAccount.getAccountId());
			accountList.setTxDate(txDate);
			accountList.setTxTime(txTime);
			accountList.setSeqNo(seqNo);
			accountList.setBankSeqNo(bankSeqNo);
			accountList.setCheckStatus(1);
			accountList.setTradeStatus(1);
			accountList.setIsBank(1);
			// 非银行相关
			accountList.setNid(nid); // 交易凭证号生成规则BorrowNid_userid_期数
			accountList.setUserId(userId); // 借款人id
			accountList.setAmount(realAcmount); // 操作金额
			accountList.setType(1); // 收支类型1收入2支出3冻结
			accountList.setTrade("borrow_success"); // 交易类型
			accountList.setTradeCode("balance"); // 操作识别码
			accountList.setTotal(borrowAccount.getTotal()); // 资金总额
			accountList.setBalance(borrowAccount.getBalance()); // 可用金额
			accountList.setFrost(borrowAccount.getFrost()); // 冻结金额
			accountList.setAwait(borrowAccount.getAwait()); // 待收金额
			accountList.setRepay(borrowAccount.getRepay()); // 待还金额
//			accountList.setCreateTime(nowTime); // 创建时间
			accountList.setOperator(CustomConstants.OPERATOR_AUTO_LOANS); // 操作员
			accountList.setRemark(borrowNid);
			accountList.setIp(""); // 操作IP
//			accountList.setBaseUpdate(0);
			boolean accountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
			if (!accountListFlag) {
				throw new RuntimeException("插入借款人放款交易明細accountList表失败，项目编号:" + borrowNid + "]");
			}
			// 更新Borrow
			newBorrow.setRecoverLastTime(nowTime); // 最后一笔放款时间
			newBorrow.setStatus(status);
			newBorrow.setReverifyStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
			BorrowExample borrowExample = new BorrowExample();
			borrowExample.createCriteria().andIdEqualTo(borrowId);
			boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
			if (!borrowFlag) {
				throw new RuntimeException("更新borrow表失败，项目编号:" + borrowNid + "]");
			}
			BorrowApicronExample example = new BorrowApicronExample();
			example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
			apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_SUCCESS);
//			apicron.setUpdateTime(nowTime);
			boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
			if (!apicronFlag) {
				throw new RuntimeException("更新状态为(放款成功)失败，项目编号:" + borrowNid + "]");
			}
			// add by hsy 优惠券放款请求加入到消息队列 start
            Map<String, String> params = new HashMap<String, String>();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            // 借款项目编号
            params.put("borrowNid", borrowNid);
//            rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_COUPONLOANS, JSONObject.toJSONString(params));
            //TODO: 优惠券放款队列
            
            // add by hsy 优惠券放款请求加入到消息队列 end
			//发送短信
			try {
				this.sendSmsForBorrower(borrowUserId, borrowNid);
				this.sendSmsForManager(borrowNid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (failCount == tenderCount) {
			// 更新Borrow
			newBorrow.setStatus(status);
			newBorrow.setReverifyStatus(CustomConstants.BANK_BATCH_STATUS_FAIL);
			BorrowExample borrowExample = new BorrowExample();
			borrowExample.createCriteria().andIdEqualTo(borrowId);
			boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
			if (!borrowFlag) {
				throw new RuntimeException("更新borrow表失败，项目编号:" + borrowNid + "]");
			}
			BorrowApicronExample example = new BorrowApicronExample();
			example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
			apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_FAIL);
//			apicron.setUpdateTime(new Date());
			boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
			if (!apicronFlag) {
				throw new RuntimeException("更新状态为(放款成功)失败，项目编号:" + borrowNid + "]");
			}
		} else {
			// 更新Borrow
			newBorrow.setStatus(status);
			newBorrow.setReverifyStatus(CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
			BorrowExample borrowExample = new BorrowExample();
			borrowExample.createCriteria().andIdEqualTo(borrowId);
			boolean borrowFlag = this.borrowMapper.updateByExampleSelective(newBorrow, borrowExample) > 0 ? true : false;
			if (!borrowFlag) {
				throw new RuntimeException("更新borrow表失败，项目编号:" + borrowNid + "]");
			}
			BorrowApicronExample example = new BorrowApicronExample();
			example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
			apicron.setStatus(CustomConstants.BANK_BATCH_STATUS_PART_FAIL);
//			apicron.setUpdateTime(nowTime);
			boolean apicronFlag = this.borrowApicronMapper.updateByExampleSelective(apicron, example) > 0 ? true : false;
			if (!apicronFlag) {
				throw new RuntimeException("更新状态为(放款成功)失败，项目编号:" + borrowNid + "]");
			}
		}
		return true;
	}

	/**
	 * 更新投资人相关信息
	 * @param apicron
	 * @param borrow
	 * @param borrowTender
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map updateBorrowLoans(BorrowApicron apicron, Borrow borrow, BorrowInfo borrowInfo, BorrowTender borrowTender) throws Exception {

		Map result = new HashMap<>();
		int nowTime = GetDate.getNowTime10();
		String borrowNid = apicron.getBorrowNid();// 借款编号
		Integer borrowUserid = apicron.getUserId();// 借款人ID
		String nid = apicron.getNid();// 标识ID
		String batchNo = apicron.getBatchNo();// 放款批次号
		String txDate = Validator.isNotNull(apicron.getTxDate()) ? String.valueOf(apicron.getTxDate()) : null;// 批次时间yyyyMMdd
		String txTime = Validator.isNotNull(apicron.getTxTime()) ? String.valueOf(apicron.getTxTime()) : null;// 批次时间hhmmss
		String seqNo = Validator.isNotNull(apicron.getSeqNo()) ? String.valueOf(apicron.getSeqNo()) : null;// 流水号
		String bankSeqNo = Validator.isNotNull(apicron.getBankSeqNo()) ? String.valueOf(apicron.getBankSeqNo()) : null;// 银行唯一订单号
		String tenderOrderId = borrowTender.getNid();// 投资订单号
		int tenderUserId = borrowTender.getUserId();// 投资人userId
		BigDecimal serviceFee = borrowTender.getLoanFee();// 服务费
		BigDecimal tenderAccount = borrowTender.getAccount();// 投资金额
		String authCode = borrowTender.getAuthCode();//授权码
		/** 标的基本数据 */
		String borrowStyle = borrow.getBorrowStyle();// 还款方式
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		logger.info("-----------放款开始，--项目编号" + borrowNid + "---------" + tenderOrderId);

		//start

		logger.info("--------------放款状态为成功,开始处理,[投资订单号：" + tenderOrderId + "]--项目编号" + borrowNid);
		// 取出冻结订单信息
		FreezeList freezeList = getFreezeList(tenderOrderId);
		if (Validator.isNull(freezeList)) {
			throw new RuntimeException("冻结订单表(huiyingdai_freezeList)查询失败！, " + "投资订单号[" + tenderOrderId + "]");
		}
		// 若此笔订单已经解冻
		if (freezeList.getStatus() == 1) {
			logger.info("--------------订单已解冻,开始处理,[投资订单号：" + tenderOrderId + "]--项目编号" + borrowNid);
			result.put("result", true);
			return result;
		}
		freezeList.setStatus(1);
		boolean flag = this.freezeListMapper.updateByPrimaryKeySelective(freezeList) > 0 ? true : false;// 更新订单为已经解冻
		if (!flag) {
			throw new RuntimeException("冻结订单表(huiyingdai_freezeList)更新失败!" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 写入还款明细表(huiyingdai_borrow_recover)
		BorrowRecover borrowRecover = this.selectBorrowRecover(tenderOrderId);
		if (Validator.isNull(borrowRecover)) {
			throw new RuntimeException("未查詢到相应的放款明细总表(borrowRecover)!" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 如果放款状态已经更新
		if (borrowRecover.getStatus() == 1) {
			logger.info("--------------订单放款状态已更新,开始处理,[投资订单号：" + tenderOrderId + "]--项目编号" + borrowNid);
			result.put("result", true);
			return result;
		}
		BigDecimal awaitCapital = borrowRecover.getRecoverCapital();// 待收本金
		BigDecimal awaitInterest = borrowRecover.getRecoverInterest();// 待收利息
		BigDecimal managerFee = borrowRecover.getRecoverFee();// 管理费
		BigDecimal awaitAccount = borrowRecover.getRecoverAccount();// 待收本息
		borrowRecover.setStatus(1); // 放款状态
		borrowRecover.setAuthCode(authCode);// 授權碼
		borrowRecover.setLoanBatchNo(batchNo);// 放款批次号
//		borrowRecover.setAddtime(String.valueOf(nowTime));
		boolean borrowRecoverFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover) > 0 ? true : false;
		if (!borrowRecoverFlag) {
			throw new RuntimeException("放款明细表(huiyingdai_borrow_recover)更新失败!" + "[投资订单号：" + tenderOrderId + "]");
		}
		// [principal: 等额本金, month:等额本息,month:等额本息,end:先息后本]
		if (isMonth) {
			// 更新分期放款计划表(huiyingdai_borrow_recover_plan)
			BorrowRecoverPlan recoverPlan = new BorrowRecoverPlan();
			recoverPlan.setStatus(1); // 状态
			recoverPlan.setAuthCode(authCode);
//			recoverPlan.setAddtime(String.valueOf(nowTime));
			recoverPlan.setLoanBatchNo(batchNo);
			BorrowRecoverPlanExample recoverPlanExample = new BorrowRecoverPlanExample();
			recoverPlanExample.createCriteria().andNidEqualTo(tenderOrderId);
			boolean recoverPlanFlag = this.borrowRecoverPlanMapper.updateByExampleSelective(recoverPlan, recoverPlanExample) > 0 ? true : false;
			if (!recoverPlanFlag) {
				throw new RuntimeException("分期放款计划表(huiyingdai_borrow_recover_plan)更新失败!" + "[投资订单号：" + tenderOrderId + "]");
			}
		}
		// 更新投资详情表
		borrowTender.setStatus(1); // 状态 0，未放款，1，已放款
//		borrowTender.setTenderStatus(1); // 投资状态 0，未放款，1，已放款
//		borrowTender.setApiStatus(1); // 放款状态 0，未放款，1，已放款
//		borrowTender.setWeb(2); // 写入网站收支明细
		boolean borrowTenderFlag = this.borrowTenderMapper.updateByPrimaryKeySelective(borrowTender) > 0 ? true : false;
		if (!borrowTenderFlag) {
			throw new RuntimeException("投资详情(huiyingdai_borrow_tender)更新失败!" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 写入借款满标日志(原复审业务)
		boolean isInsert = false;
		AccountBorrow accountBorrow = getAccountBorrow(borrowNid);
		if (accountBorrow == null) {
			isInsert = true;
			accountBorrow = new AccountBorrow();
			accountBorrow.setNid(nid); // 生成规则：BorrowNid_userid_期数
			accountBorrow.setBorrowNid(borrowNid); // 借款编号
			accountBorrow.setUserId(borrowUserid); // 借款人编号
//			accountBorrow.setCreateTime(nowTime); // 创建时间
			accountBorrow.setMoney(tenderAccount);// 总收入金额
			accountBorrow.setFee(serviceFee);// 计算服务费
			accountBorrow.setBalance(tenderAccount.subtract(serviceFee)); // 实际到账金额
			accountBorrow.setInterest(awaitInterest);
			accountBorrow.setManageFee(managerFee);
			accountBorrow.setRemark("借款成功[" + borrow.getBorrowNid() + "]，扣除服务费{" + accountBorrow.getFee().toString() + "}元");
//			accountBorrow.setUpdateTime(nowTime); // 更新时间
		} else {
			accountBorrow.setMoney(accountBorrow.getMoney().add(tenderAccount));// 总收入金额
			accountBorrow.setFee(accountBorrow.getFee().add(serviceFee));// 计算服务费
			accountBorrow.setBalance(accountBorrow.getBalance().add(tenderAccount.subtract(serviceFee))); // 实际到账金额
			accountBorrow.setInterest(accountBorrow.getInterest().add(awaitInterest));
			accountBorrow.setManageFee(accountBorrow.getManageFee().add(managerFee));
			accountBorrow.setRemark("借款成功[" + borrow.getBorrowNid() + "]，扣除服务费{" + accountBorrow.getFee().toString() + "}元");
//			accountBorrow.setUpdateTime(nowTime); // 更新时间
		}
		boolean accountBorrowFlag = false;
		if (isInsert) {
			accountBorrowFlag = this.accountBorrowMapper.insertSelective(accountBorrow) > 0 ? true : false;
		} else {
			accountBorrowFlag = this.accountBorrowMapper.updateByPrimaryKeySelective(accountBorrow) > 0 ? true : false;
		}
		if (!accountBorrowFlag) {
			throw new RuntimeException("借款满标日志(huiyingdai_account_borrow)更新失败!" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 更新账户信息(投资人)
		// 更新账户信息(投资人)
		Account accountTender = new Account();
		accountTender.setUserId(tenderUserId);
		accountTender.setBankTotal(awaitInterest);// 投资人资金总额 +利息
		accountTender.setBankFrost(tenderAccount);// 投资人冻结金额+投资金额(等额本金时投资金额可能会大于计算出的本金之和)
		accountTender.setBankAwait(awaitAccount);// 投资人待收金额+利息+ 本金
		accountTender.setBankAwaitCapital(awaitCapital);// 投资人待收本金
		accountTender.setBankAwaitInterest(awaitInterest);// 投资人待收利息
		accountTender.setBankInvestSum(tenderAccount);// 投资人累计投资
		accountTender.setBankFrostCash(tenderAccount);// 江西银行冻结金额
		boolean investaccountFlag = this.adminAccountCustomizeMapper.updateOfLoansTender(accountTender) > 0 ? true : false;
		if (!investaccountFlag) {
			throw new Exception("投资人资金记录(huiyingdai_account)更新失败!" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 取得账户信息(投资人)
		accountTender = this.getAccountByUserId(tenderUserId);
		if (Validator.isNull(accountTender) || accountTender.getAccountId() == null) {
			throw new Exception("投资人账户信息不存在。[投资人ID：" + tenderUserId + "]，" + "[投资订单号：" + tenderOrderId + "]");
		}
//		BankOpenAccount tenderOpenAccount = this.getBankOpenAccount(tenderUserId);
		// 写入收支明细
		AccountList accountList = new AccountList();
		/** 投资人银行相关 */
		accountList.setBankAwait(accountTender.getBankAwait());
		accountList.setBankAwaitCapital(accountTender.getBankAwaitCapital());
		accountList.setBankAwaitInterest(accountTender.getBankAwaitInterest());
		accountList.setBankBalance(accountTender.getBankBalance());
		accountList.setBankFrost(accountTender.getBankFrost());
		accountList.setBankInterestSum(accountTender.getBankInterestSum());
		accountList.setBankInvestSum(accountTender.getBankInvestSum());
		accountList.setBankTotal(accountTender.getBankTotal());
		accountList.setBankWaitCapital(accountTender.getBankWaitCapital());
		accountList.setBankWaitInterest(accountTender.getBankWaitInterest());
		accountList.setBankWaitRepay(accountTender.getBankWaitRepay());
		accountList.setPlanBalance(accountTender.getPlanBalance());//汇计划账户可用余额
		accountList.setPlanFrost(accountTender.getPlanFrost());
		accountList.setAccountId(accountTender.getAccountId());
		accountList.setCheckStatus(0);
		accountList.setTradeStatus(1);
		accountList.setIsBank(1);
		accountList.setTxDate(Integer.parseInt(txDate));
		accountList.setTxTime(Integer.parseInt(txTime));
		accountList.setSeqNo(seqNo);
		accountList.setBankSeqNo(bankSeqNo);
		/** 投资人非银行相关 */
		accountList.setNid(tenderOrderId); // 投资订单号
		accountList.setUserId(tenderUserId); // 投资人
		accountList.setAmount(tenderAccount); // 投资本金
		accountList.setType(2); // 2支出
		accountList.setTrade("tender_success"); // 投资成功
		accountList.setTradeCode("balance"); // 余额操作
		accountList.setTotal(accountTender.getTotal()); // 投资人资金总额
		accountList.setBalance(accountTender.getBalance()); // 投资人可用金额
		accountList.setFrost(accountTender.getFrost()); // 投资人冻结金额
		accountList.setAwait(accountTender.getAwait()); // 投资人待收金额
		accountList.setRemark("投资放款");
//		accountList.setCreateTime(nowTime); // 创建时间
//		accountList.setBaseUpdate(nowTime); // 更新时间
		accountList.setOperator(CustomConstants.OPERATOR_AUTO_LOANS); // 操作者
		accountList.setIp(borrow.getAddIp()); // 操作IP
		accountList.setRemark(borrowNid);
//		accountList.setIsUpdate(0);
//		accountList.setBaseUpdate(0);
//		accountList.setInterest(BigDecimal.ZERO); // 利息
		accountList.setWeb(0); // PC
		boolean tenderAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
		if (!tenderAccountListFlag) {
			throw new Exception("投资人收支明细(huiyingdai_account_list)写入失败!" + "[投资订单号：" + tenderOrderId + "]");
		}
		// 服务费大于0时,插入网站收支明细
		if (serviceFee.compareTo(BigDecimal.ZERO) > 0) {
			//TODO: 消息队列发送
			// 插入网站收支明细记录
//			AccountWebList accountWebList = new AccountWebList();
//			accountWebList.setOrdid(tenderOrderId);// 订单号
//			accountWebList.setBorrowNid(borrowNid); // 投资编号
//			accountWebList.setUserId(borrowTender.getUserId()); // 投资者
//			accountWebList.setAmount(serviceFee); // 服务费
//			accountWebList.setType(CustomConstants.TYPE_IN); // 类型1收入2支出
//			accountWebList.setTrade(CustomConstants.TRADE_LOANFEE); // 服务费
//			accountWebList.setTradeType(CustomConstants.TRADE_LOANFEE_NM); // 服务费
//			accountWebList.setCreateTime(borrowRecover.getCreateTime());
//			accountWebList.setRemark(borrowNid);
//			AccountWebListExample webListExample = new AccountWebListExample();
//			webListExample.createCriteria().andOrdidEqualTo(nid).andTradeEqualTo(CustomConstants.TRADE_LOANFEE);
//			int webListCount = this.accountWebListMapper.countByExample(webListExample);
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
//					throw new RuntimeException("网站收支记录(huiyingdai_account_web_list)插入失败!" + "[投资订单号：" + tenderOrderId + "]");
//				}
//			} else {
//				throw new RuntimeException("网站收支记录(huiyingdai_account_web_list)已存在!" + "[投资订单号：" + tenderOrderId + "]");
//			}
		}

		apicron.setSucCounts(apicron.getSucCounts() + 1);
		if (serviceFee.compareTo(BigDecimal.ZERO) > 0) {
			apicron.setFailAmount(apicron.getFailAmount().subtract(borrowTender.getLoanAmount().add(serviceFee)));
			apicron.setSucAmount(apicron.getSucAmount().add(borrowTender.getLoanAmount().add(serviceFee)));
		}else{
			apicron.setFailAmount(apicron.getFailAmount().subtract(borrowTender.getLoanAmount()));
			apicron.setSucAmount(apicron.getSucAmount().add(borrowTender.getLoanAmount()));
		}
		apicron.setFailCounts(apicron.getFailCounts() - 1);
		boolean apicronSuccessFlag = this.borrowApicronMapper.updateByPrimaryKeySelective(apicron) > 0 ? true : false;
		if (!apicronSuccessFlag) {
			throw new RuntimeException("批次放款记录(borrowApicron)更新失败!" + "[项目编号：" + borrowNid + "]");
		}
		createTenderGenerateContract(borrow,borrowTender,awaitInterest);
		try {
//			this.sendMail(borrowRecover);
			this.sendMessage(borrowRecover);
			this.sendSms(borrowRecover);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("-----------放款结束，放款成功---" + borrowNid + "---------投资订单号" + tenderOrderId);
		result.put("result", true);
		result.put("recoverInterest", awaitInterest);

		return result;
		
	}

	
	private void sendSmsForBorrower(int borrowUserId, String borrowNid) {
		// 借款人发送成功短信
		AccountBorrow accountBorrow = this.getAccountBorrow(borrowNid);
		Map<String, String> borrowerReplaceStrs = new HashMap<String, String>();
		borrowerReplaceStrs.put("val_borrownid", borrowNid);
		borrowerReplaceStrs.put("val_amount", accountBorrow.getBalance().toString());
		SmsMessage borrowerSmsMessage = new SmsMessage(borrowUserId, borrowerReplaceStrs, null, null, MessageConstant.SMSSENDFORUSER, null, CustomConstants.PARAM_TPL_JIEKUAN_SUCCESS,
				CustomConstants.CHANNEL_TYPE_NORMAL);
		try {
			mailProducer.messageSend(new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(borrowerSmsMessage)));
		} catch (MQException e2) {
			logger.error("发送邮件失败..", e2);
		}

	}

	private void sendSmsForManager(String borrowNid) {
		// 管理员发送成功短信
		Map<String, String> replaceStrs = new HashMap<String, String>();
		replaceStrs.put("val_title", borrowNid);
		replaceStrs.put("val_time", GetDate.formatTime());
		SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMSSENDFORMANAGER, null, CustomConstants.PARAM_TPL_FANGKUAN_SUCCESS, CustomConstants.CHANNEL_TYPE_NORMAL);

		try {
			mailProducer.messageSend(new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
		} catch (MQException e2) {
			logger.error("发送邮件失败..", e2);
		}

	}

	/**
	 * 根据投资订单号查询相应的放款信息
	 * 
	 * @param orderId
	 * @return
	 */
	private BorrowRecover selectBorrowRecover(String orderId) {
		BorrowRecoverExample example = new BorrowRecoverExample();
		example.createCriteria().andNidEqualTo(orderId);
		List<BorrowRecover> borrowRecovers = this.borrowRecoverMapper.selectByExample(example);
		if (borrowRecovers != null && borrowRecovers.size() == 1) {
			return borrowRecovers.get(0);
		}
		return null;
	}

	/**
	 * 生成投资居间服务协议
	 * @param borrow
	 * 标的信息
	 * @param borrowTender
	 * @param recoverInterest
	 */
	private void createTenderGenerateContract(Borrow borrow, BorrowTender borrowTender, BigDecimal recoverInterest) {
		String nid = borrowTender.getNid();
		// TODO: 
		try {
//			Integer userId = borrowTender.getUserId();
//			String userName = borrowTender.getTenderUserName();
//			String signDate =GetDate.getDataString(GetDate.date_sdf);
//			FddGenerateContractBean bean = new FddGenerateContractBean();
//			bean.setTenderUserId(userId);
//			bean.setOrdid(nid);
//			bean.setTransType(1);
//			bean.setBorrowNid(borrow.getBorrowNid());
//			bean.setSignDate(signDate);
//			bean.setTenderUserName(userName);
//			bean.setTenderType(0);
//			bean.setTenderInterest(recoverInterest);
//			rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
		}catch (Exception e){
			logger.info("-----------------生成居间服务协议失败，ordid:" + nid + ",异常信息：" + e.getMessage());
		}
	}

	/**
	 * 推送消息
	 * 
	 * @param borrowRecover
	 * @author Administrator
	 */

	private void sendMessage(BorrowRecover borrowRecover) {
//		int userId = borrowRecover.getUserId();
//		BigDecimal amount = borrowRecover.getRecoverAccount();
//		BigDecimal capital = borrowRecover.getRecoverCapital();
//		BigDecimal interest = borrowRecover.getRecoverInterest();
//		Map<String, String> msg = new HashMap<String, String>();
//		msg.put(VAL_TITLE, borrowRecover.getBorrowNid());
//		msg.put(VAL_AMOUNT, amount.toString());// 待收金额
//		msg.put(VAL_BALANCE, capital.toString()); // 投资本金
//		msg.put(VAL_PROFIT, interest.toString()); // 预期收益
//		msg.put(VAL_USERID, String.valueOf(userId));
//		if (Validator.isNotNull(msg.get(VAL_USERID)) && Validator.isNotNull(msg.get(VAL_AMOUNT)) && new BigDecimal(msg.get(VAL_AMOUNT)).compareTo(BigDecimal.ZERO) > 0) {
//			Users users = getUsersByUserId(Integer.valueOf(msg.get(VAL_USERID)));
//			if (users == null) {
//				return;
//			} else {
//				UsersInfo userInfo = this.getUsersInfoByUserId(Integer.valueOf(msg.get(VAL_USERID)));
//				if (StringUtils.isEmpty(userInfo.getTruename())) {
//					msg.put(VAL_NAME, users.getUsername());
//				} else if (userInfo.getTruename().length() > 1) {
//					msg.put(VAL_NAME, userInfo.getTruename().substring(0, 1));
//				} else {
//					msg.put(VAL_NAME, userInfo.getTruename());
//				}
//				Integer sex = userInfo.getSex();
//				if (Validator.isNotNull(sex)) {
//					if (sex.intValue() == 2) {
//						msg.put(VAL_SEX, "女士");
//					} else {
//						msg.put(VAL_SEX, "先生");
//					}
//				}
//				AppMsMessage smsMessage = new AppMsMessage(Integer.valueOf(msg.get(VAL_USERID)), msg, null, MessageDefine.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_TOUZI_SUCCESS);
//				appMsProcesser.gather(smsMessage);
//			}
//		}
	}
	
	/**
	 * 发送短信(投资成功)
	 *
	 * @param borrowRecover
	 */
	private void sendSms(BorrowRecover borrowRecover) {
		
//		int userId = borrowRecover.getUserId();
//		BigDecimal capital = borrowRecover.getRecoverCapital();
//		BigDecimal interest = borrowRecover.getRecoverInterest();
//		int repayTime = Integer.parseInt(borrowRecover.getRecoverTime());
//		String dateStr = GetDate.getDateMyTimeInMillis(repayTime);
//		//判断是否分期 增加分期短信模板 add by cwyang 2017-8-8
//		String nid = borrowRecover.getNid();
//		logger.info("==========cwyang开始发送短信=========订单号:" + nid);
//		BorrowRecoverPlan planInfo = isRecoverPlan(nid);
//		String planDateStr = null; //下一期时间
//		String lastPlanDateStr = null;//最后一期时间
//		boolean isPlan = false;
//		if (planInfo != null) {
//			isPlan = true;
//			int planTime = Integer.parseInt(planInfo.getRecoverTime());
//			planDateStr = GetDate.getDateMyTimeInMillis(planTime);
//			BorrowRecoverPlan lastPlanInfo = getBorrowgetrecoverLastTime(nid,borrowRecover.getBorrowNid());
//			int lastPlantime = Integer.parseInt(lastPlanInfo.getRecoverTime());
//			lastPlanDateStr = GetDate.getDateMyTimeInMillis(lastPlantime);
//			logger.info("==========cwyang启用分期短信模板=========订单号:" + nid + ",下期还款时间:" + planDateStr + ",最后还款时间:" + lastPlanDateStr);
//		}else{
//			logger.info("==========cwyang不启用分期短信模板=========订单号:" + nid);
//		}
//		Map<String, String> msg = new HashMap<String, String>();
//		msg.put(VAL_BALANCE, capital.toString());
//		msg.put(VAL_PROFIT, interest.toString());
//		msg.put(VAL_RECOVERTIME, dateStr);
//		msg.put(VAL_USERID, String.valueOf(userId));
//		if (isPlan) {
//			msg.put(VAL_NEXTRECOVERTIME, planDateStr);
//			msg.put(VAL_RECOVERTIME, lastPlanDateStr);
//		}
//		Users users = getUsersByUserId(userId);
//		if (users == null || Validator.isNull(users.getMobile()) || (users.getInvestSms() != null && users.getInvestSms() == 1)) {
//			return;
//		}
//		System.err.println("userid=" + msg.get(VAL_USERID) + ";开始发送短信,投资金额" + msg.get(VAL_BALANCE));
//		SmsMessage smsMessage = null;
//		System.err.println("判断是否为分期模板:planDateStr = "+ planDateStr);
//		if (isPlan) {
//			smsMessage = new SmsMessage(Integer.valueOf(msg.get(VAL_USERID)), msg, null, null, MessageDefine.SMSSENDFORUSER, null, CustomConstants.PARAM_TPL_TOUZI_PLAN_SUCCESS,
//					CustomConstants.CHANNEL_TYPE_NORMAL);
//		}else{
//			smsMessage = new SmsMessage(Integer.valueOf(msg.get(VAL_USERID)), msg, null, null, MessageDefine.SMSSENDFORUSER, null, CustomConstants.PARAM_TPL_TOUZI_SUCCESS,
//					CustomConstants.CHANNEL_TYPE_NORMAL);
//		}
//		smsProcesser.gather(smsMessage);
	}

}
