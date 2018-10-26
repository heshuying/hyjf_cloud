/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountBorrow;
import com.hyjf.am.trade.dao.model.auto.AccountBorrowExample;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowApicronExample;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleExample;
import com.hyjf.am.trade.dao.model.auto.CouponRecover;
import com.hyjf.am.trade.dao.model.auto.CouponRecoverExample;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvest;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvestExample;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestLoan;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestLoanDetail;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepay;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepayDetail;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepayDetailExample;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestRepayExample;
import com.hyjf.am.trade.dao.model.customize.WebProjectRepayListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserInvestListCustomize;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.AppMessageProducer;
import com.hyjf.am.trade.mq.producer.MailProducer;
import com.hyjf.am.trade.mq.producer.SmsProducer;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.IncreaseinterestLoansService;
import com.hyjf.am.trade.utils.PdfGenerator;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.util.calculate.InterestInfo;
import com.hyjf.common.validator.Validator;

/**
 * 产品加息放款
 * @author dxj
 * @version IncreaseinterestLoansServiceImpl.java, v0.1 2018年6月19日 上午11:27:25
 */
@Service
public class IncreaseinterestLoansServiceImpl extends BaseServiceImpl implements IncreaseinterestLoansService {
	
	/** 用户ID */
	private static final String VAL_USERID = "userId";

	/** 用户名 */
	private static final String VAL_NAME = "val_name";

	/** 投资订单号 */
	private static final String VAL_ORDER_ID = "order_id";

	/** 性别 */
	private static final String VAL_SEX = "val_sex";

	/** 放款金额 */
	private static final String VAL_AMOUNT = "val_amount";
	/**
	 * 加息收益
	 */
	private static final String VAL_PROFIT = "val_profit";

	/** 放款时间 */
	private static final String VAL_LOAN_TIME = "loan_time";

	/**下一期还款日*/
	private static final String VAL_NEXTRECOVERTIME = "val_nextrecovertime";

	/**最后还款日*/
	private static final String VAL_RECOVERTIME = "val_recovertime";

	/** 还款明细ID */
	private static final String PARAM_BORROWRECOVERID = "param_borrowrecoverid";

	/** 优惠券投资 */
	private static final String COUPON_TYPE = "coupon_type";

	/** 优惠券投资订单编号 */
	private static final String TENDER_NID = "tender_nid";

	/** 任务状态:未执行 */
	private static final Integer STATUS_WAIT = 0;

	/** 任务状态:已完成 */
	private static final Integer STATUS_SUCCESS = 1;

	/** 任务状态:执行中 */
	private static final Integer STATUS_RUNNING = 2;

	/** 任务状态:错误 */
	private static final Integer STATUS_ERROR = 9;
    
	@Autowired
	private MailProducer mailProducer;
    
	@Autowired
	private SmsProducer smsProducer;

	@Autowired
	private AppMessageProducer appMessageProducer;
	
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 自动放款
	 *
	 * @throws Exception
	 */
//	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.NESTED, rollbackFor = Exception.class)
	@Override
	public List<Map<String, String>> updateBorrowLoans(BorrowApicron apicron, IncreaseInterestInvest borrowTender) throws Exception {

		logger.info("-----------融通宝加息放款开始---" + apicron.getBorrowNid() + "---------" + borrowTender.getLoanOrderId());
		List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();
		/** 基本变量 */
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 借款编号
		String borrowNid = apicron.getBorrowNid();
		// 借款人ID
		Integer borrowUserid = apicron.getUserId();
		// 借款人信息
//		Users borrowUser = this.getUsersByUserId(borrowUserid);
		/** 标的基本数据 */
		// 取得标的详情
		Borrow borrow = getBorrow(borrowNid);
		BorrowInfo borrowInfo = getBorrowInfoByNid(borrowNid);
		Map<String, String> msg = new HashMap<String, String>();
		retMsgList.add(msg);
		// 借款期数
		Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();
		BorrowStyle borrowStyleMain = this.getborrowStyleByNid(borrowStyle);
		String borrowStyleName = borrowStyleMain.getName();
		// 年利率
		BigDecimal borrowApr = borrow.getBorrowApr();
		// 加息年利率
		BigDecimal extraYieldApr = borrowInfo.getBorrowExtraYield();
		// 月利率(算出管理费用[上限])
		BigDecimal borrowMonthRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
		// 月利率(算出管理费用[下限])
		BigDecimal borrowManagerScaleEnd = Validator.isNull(borrowInfo.getBorrowManagerScaleEnd()) ? BigDecimal.ZERO : new BigDecimal(borrowInfo.getBorrowManagerScaleEnd());
		// 差异费率
		BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
		// 初审时间
		int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
		// 借款成功时间
		Integer borrowSuccessTime = borrow.getReverifyTime();
		// 项目类型
		Integer projectType = borrow.getProjectType();
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		// 投资人用户ID
		Integer outUserId = borrowTender.getUserId();
		// 投资费用
		BigDecimal tenderAccount = BigDecimal.ZERO;
		// 利息
		BigDecimal interestTender = BigDecimal.ZERO;
		String loanOrderId = GetOrderIdUtils.getOrderId2(borrowTender.getUserId());
		String loanOrderDate = GetOrderIdUtils.getOrderDate();
		// 估计还款时间
		Integer recoverTime = null;
		// 投资订单号
		String ordId = borrowTender.getOrderId();
		// 投资金额
		tenderAccount = borrowTender.getAccount();
		// 计算利息
		InterestInfo interestInfo = CalculatesUtil.getInterestInfo(tenderAccount, borrowPeriod, extraYieldApr, borrowStyle, borrowSuccessTime, borrowMonthRate, borrowManagerScaleEnd, projectType,
				differentialRate, borrowVerifyTime);
		if (interestInfo != null) {
			interestTender = interestInfo.getRepayAccountInterest(); // 利息
			recoverTime = interestInfo.getRepayTime(); // 估计还款时间

		}
		// 写入还款明细表(hyjf_increase_interest_loan)
		IncreaseInterestLoan increaseInterestLoan = new IncreaseInterestLoan();
		increaseInterestLoan.setUserId(borrowTender.getUserId()); // 投资人
		increaseInterestLoan.setUserName(borrowTender.getCreateUserName());
		increaseInterestLoan.setBorrowNid(borrowNid); // 借款编号
		increaseInterestLoan.setInvestId(borrowTender.getId());// 投资id
		increaseInterestLoan.setInvestOrderId(ordId); // 投资订单号
		increaseInterestLoan.setInvestAccount(borrowTender.getAccount());// 投资金额
		increaseInterestLoan.setBorrowUserId(borrowUserid); // 借款人
		increaseInterestLoan.setBorrowUserName(borrow.getBorrowUserName()); // 借款人
		increaseInterestLoan.setBorrowApr(borrowApr);
		increaseInterestLoan.setBorrowStyleName(borrowStyleName);
		increaseInterestLoan.setBorrowExtraYield(extraYieldApr);
		increaseInterestLoan.setRepayPeriod(0); // 还款期数
		increaseInterestLoan.setBorrowStyle(borrowStyle);
		increaseInterestLoan.setBorrowPeriod(borrowPeriod);
		increaseInterestLoan.setLoanInterest(interestTender);
		increaseInterestLoan.setRemainPeriod(isMonth ? borrowPeriod : 1);// 剩余期限
		increaseInterestLoan.setRepayTime(recoverTime); // 估计还款时间
		increaseInterestLoan.setRepayInterestWait(interestTender); // 预还利息
		increaseInterestLoan.setRepayInterestYes(BigDecimal.ZERO); // 实还利息
		increaseInterestLoan.setRepayStatus(0);// 还款状态 0未还款 1还款中 2已还款
		increaseInterestLoan.setWeb(0);
//		increaseInterestLoan.setCreateTime(nowTime);
		increaseInterestLoan.setCreateUserId(borrowTender.getUserId());
		increaseInterestLoan.setCreateUserName(borrowTender.getCreateUserName());
		increaseInterestLoan.setAddIp(borrowTender.getAddIp());
		boolean borrowRecoverFlag = this.insertBorrowRecover(increaseInterestLoan) > 0 ? true : false;
		if (borrowRecoverFlag) {
			// 更新投资详情表
			IncreaseInterestInvest newIncreaseInterestInvest = new IncreaseInterestInvest();
			newIncreaseInterestInvest.setId(borrowTender.getId()); // ID
			newIncreaseInterestInvest.setLoanOrderId(loanOrderId);
			newIncreaseInterestInvest.setLoanOrderDate(loanOrderDate);
			newIncreaseInterestInvest.setLoanAmount(interestTender); // 实际放款金额
			newIncreaseInterestInvest.setRepayInterest(interestTender);
			newIncreaseInterestInvest.setRepayInterestYes(new BigDecimal("0"));
			newIncreaseInterestInvest.setRepayInterestWait(interestTender);
			newIncreaseInterestInvest.setRepayTimes(0);// 已还款次数
			//add by cwyang 20180730 新增应还款时间 散标加息需求
			newIncreaseInterestInvest.setRepayTime(recoverTime);//应还款时间
			newIncreaseInterestInvest.setStatus(1); // 状态 0，未放款，1，已放款
//			newIncreaseInterestInvest.setClient(0);
			newIncreaseInterestInvest.setAddIp(borrowTender.getAddIp());
			newIncreaseInterestInvest.setWeb(2); // 写入网站收支明细
//			newIncreaseInterestInvest.setUpdateTime(GetDate.getNowTime10());
			newIncreaseInterestInvest.setUpdateUserId(borrowTender.getUserId());
			newIncreaseInterestInvest.setUpdateUserName(borrowTender.getCreateUserName());
			boolean borrowTenderFlag = this.updateBorrowTender(newIncreaseInterestInvest) > 0 ? true : false;
			if (borrowTenderFlag) {
				// 插入每个标的总的还款信息
				boolean isInsert = false;
				IncreaseInterestRepay increaseInterestRepay = getIncreaseInterestRepay(borrowNid);
				if (increaseInterestRepay == null) {
					isInsert = true;
					increaseInterestRepay = new IncreaseInterestRepay();
					increaseInterestRepay.setUserId(borrowUserid); // 借款人ID
					increaseInterestRepay.setUserName(borrow.getBorrowUserName());
					increaseInterestRepay.setBorrowNid(borrowNid); // 借款标号
					increaseInterestRepay.setInvestId(borrowTender.getId());
					increaseInterestRepay.setInvestOrderId(ordId);
					increaseInterestRepay.setInvestAccount(borrowTender.getAccount());
					increaseInterestRepay.setBorrowApr(borrowApr);
					increaseInterestRepay.setBorrowStyleName(borrowStyleName);
					increaseInterestRepay.setBorrowExtraYield(extraYieldApr);
					increaseInterestRepay.setBorrowPeriod(borrowPeriod);
					increaseInterestRepay.setBorrowStyle(borrowStyle);
					increaseInterestRepay.setRepayStatus(0); // 还款状态
					increaseInterestRepay.setRepayPeriod(1); // 还款期数
					increaseInterestRepay.setRepayTime(recoverTime); // 估计还款时间
					increaseInterestRepay.setOrderId("");
					increaseInterestRepay.setOrderDate("");
					increaseInterestRepay.setRemainPeriod(isMonth ? borrowPeriod : 1);
					increaseInterestRepay.setAlreadyRepayPeriod(0);
					increaseInterestRepay.setRepayInterest(BigDecimal.ZERO); // 预还利息
					increaseInterestRepay.setRepayInterestYes(BigDecimal.ZERO); // 实还利息
					increaseInterestRepay.setRepayInterestWait(BigDecimal.ZERO); // 未还利息
					increaseInterestRepay.setAddIp(borrow.getAddIp()); // 发标ip
					increaseInterestRepay.setWeb(0);
//					increaseInterestRepay.setCreateTime(nowTime); // 创建时间
					increaseInterestRepay.setCreateUserId(borrowUserid);
					increaseInterestRepay.setCreateUserName(borrow.getBorrowUserName());
					//add by yangchangwei 20180730 产品加息需求新增
					increaseInterestRepay.setBorrowAccount(borrow.getAccount());//借款金额
					increaseInterestRepay.setLoanActionTime(nowTime);//实际放款时间
				}
				increaseInterestRepay.setRepayInterest(increaseInterestRepay.getRepayInterest().add(interestTender)); // 预还利息
				increaseInterestRepay.setRepayInterestWait(increaseInterestRepay.getRepayInterestWait().add(interestTender)); // 待还利息
				int borrowRepayCnt = isInsert ? this.increaseInterestRepayMapper.insertSelective(increaseInterestRepay) : this.increaseInterestRepayMapper
						.updateByPrimaryKeySelective(increaseInterestRepay);
				Integer lastRecoverTime = recoverTime;
				logger.info("------------加息放款获得最后一期还款时间，标的号：" + borrowNid +",投资订单号：" + ordId);
				if (borrowRepayCnt > 0 ? true : false) {
					// [principal: 等额本金, month:等额本息,
					// month:等额本息,end:先息后本]
					if (isMonth) {
						// 更新分期还款计划表(increaseInterestLoanDetail)
						if (interestInfo != null && interestInfo.getListMonthly() != null) {
							IncreaseInterestLoanDetail increaseInterestLoanDetail = null;
							InterestInfo monthly = null;
							for (int j = 0; j < interestInfo.getListMonthly().size(); j++) {
								monthly = interestInfo.getListMonthly().get(j);
								if(j+1 == borrowPeriod){//最后一期的数据
									lastRecoverTime = monthly.getRepayTime();
                                    logger.info("------------加息放款获得最后一期还款时间，标的号：" + borrowNid +",投资订单号：" + ordId + ",还款时间：" + lastRecoverTime);
                                }
								increaseInterestLoanDetail = new IncreaseInterestLoanDetail();
								increaseInterestLoanDetail.setUserId(outUserId); // 投资人id
								increaseInterestLoanDetail.setBorrowNid(borrowNid); // 借款订单id
								increaseInterestLoanDetail.setUserName(borrowTender.getCreateUserName());
								increaseInterestLoanDetail.setBorrowUserId(borrowUserid); // 借款人ID
								increaseInterestLoanDetail.setBorrowUserName(borrow.getBorrowUserName()); // 借款人ID
								increaseInterestLoanDetail.setBorrowStyleName(borrowStyleName);
								increaseInterestLoanDetail.setBorrowStyle(borrowStyle);
								increaseInterestLoanDetail.setInvestId(borrowTender.getId());
								increaseInterestLoanDetail.setInvestOrderId(ordId);
								increaseInterestLoanDetail.setInvestAccount(borrowTender.getAccount());
								increaseInterestLoanDetail.setLoanInterest(monthly.getRepayAccountInterest()); // 预还利息
								increaseInterestLoanDetail.setRepayPeriod(j + 1);
								increaseInterestLoanDetail.setRepayTime(monthly.getRepayTime());// 估计还款时间
								increaseInterestLoanDetail.setRepayInterestYes(BigDecimal.ZERO); // 实还利息
								increaseInterestLoanDetail.setRepayInterestWait(monthly.getRepayAccountInterest()); // 未还利息
								increaseInterestLoanDetail.setRepayStatus(0);// 还款状态
																				// 0未还款
																				// 1还款中
																				// 2已还款
//								increaseInterestLoanDetail.setCreateTime(nowTime); // 创建时间
								increaseInterestLoanDetail.setBorrowApr(borrowApr);
								increaseInterestLoanDetail.setBorrowExtraYield(extraYieldApr);
								increaseInterestLoanDetail.setBorrowPeriod(borrowPeriod);
								increaseInterestLoanDetail.setCreateUserId(borrowTender.getUserId());
								increaseInterestLoanDetail.setCreateUserName(borrowTender.getCreateUserName());
								increaseInterestLoanDetail.setAddIp(borrowTender.getAddIp());
								increaseInterestLoanDetail.setWeb(0);
								boolean borrowRecoverPlanFlag = this.increaseInterestLoanDetailMapper.insertSelective(increaseInterestLoanDetail) > 0 ? true : false;
								if (borrowRecoverPlanFlag) {
									// 更新总的还款计划表(increaseInterestRepayDetail)
									isInsert = false;
									IncreaseInterestRepayDetail increaseInterestRepayDetail = getIncreaseInterestRepayPlan(borrowNid, j + 1);
									if (increaseInterestRepayDetail == null) {
										isInsert = true;
										increaseInterestRepayDetail = new IncreaseInterestRepayDetail();
										increaseInterestRepayDetail.setUserId(borrowUserid); // 借款人ID
										increaseInterestRepayDetail.setUserName(borrow.getBorrowUserName());
										increaseInterestRepayDetail.setBorrowNid(borrowNid); // 借款标号
										increaseInterestRepayDetail.setInvestId(borrowTender.getId());
										increaseInterestRepayDetail.setInvestOrderId(ordId);
										increaseInterestRepayDetail.setInvestAccount(borrowTender.getAccount());
										increaseInterestRepayDetail.setBorrowApr(borrowApr);
										increaseInterestRepayDetail.setBorrowStyleName(borrowStyleName);
										increaseInterestRepayDetail.setBorrowExtraYield(extraYieldApr);
										increaseInterestRepayDetail.setBorrowPeriod(borrowPeriod);
										increaseInterestRepayDetail.setBorrowStyle(borrowStyle);
										increaseInterestRepayDetail.setRepayStatus(0); // 还款状态
										increaseInterestRepayDetail.setRepayPeriod(j + 1); // 还款期数
										increaseInterestRepayDetail.setRepayTime(monthly.getRepayTime()); // 估计还款时间
										increaseInterestRepayDetail.setOrderId("");
										increaseInterestRepayDetail.setOrderDate("");
										increaseInterestRepayDetail.setRepayInterest(BigDecimal.ZERO); // 预还利息
										increaseInterestRepayDetail.setRepayInterestYes(BigDecimal.ZERO); // 实还利息
										increaseInterestRepayDetail.setRepayInterestWait(BigDecimal.ZERO); // 未还利息
										increaseInterestRepayDetail.setAddIp(borrow.getAddIp()); // 发标ip
										increaseInterestRepayDetail.setWeb(0);
//										increaseInterestRepayDetail.setCreateTime(nowTime); // 创建时间
										increaseInterestRepayDetail.setCreateUserId(borrowUserid);
										increaseInterestRepayDetail.setCreateUserName(borrow.getBorrowUserName());//这是借款人用户？？
									}
									increaseInterestRepayDetail.setRepayInterest(increaseInterestRepayDetail.getRepayInterest().add(increaseInterestLoanDetail.getLoanInterest())); // 应还利息
									increaseInterestRepayDetail.setRepayInterestWait(increaseInterestRepayDetail.getRepayInterestWait().add(increaseInterestLoanDetail.getLoanInterest())); // 待还利息

									int borrowRepayPlanCnt = isInsert ? this.increaseInterestRepayDetailMapper.insertSelective(increaseInterestRepayDetail) : this.increaseInterestRepayDetailMapper
											.updateByPrimaryKeySelective(increaseInterestRepayDetail);
									if (borrowRepayPlanCnt > 0 ? false : true) {
										throw new Exception("分期还款计划表(increaseInterestRepayDetail)写入失败!" + "[投资订单号：" + ordId + "]，" + "[期数：" + j + 1 + "]");
									}

								} else {
									throw new Exception("分期放款款计划表(huiyingdai_borrow_recover_plan)写入失败!" + "[投资订单号：" + ordId + "]，" + "[期数：" + j + 1 + "]");
								}
							}
						}
					}
					// 更新账户信息(投资人)
					Account account = new Account();
					account.setUserId(borrowTender.getUserId());
					// 投资人资金总额 + 利息
					account.setBankTotal(interestTender);
					// 投资人待收金额 + 利息+ 本金
					account.setBankAwait(interestTender);
					// 投资人待收利息
					account.setBankAwaitInterest(interestTender);
					boolean investaccountFlag = this.adminAccountCustomizeMapper.updateOfRTBLoansTender(account) > 0 ? true : false;
					if (!investaccountFlag) {
						throw new Exception("投资人资金记录(huiyingdai_account)更新失败!" + "[投资订单号：" + ordId + "]");
					}else{
						Map<String, String> map = new HashMap<>();
						map.put(VAL_PROFIT,interestTender.toString());
						map.put(VAL_NEXTRECOVERTIME,GetDate.getDateMyTimeInMillis(recoverTime));
						map.put(VAL_RECOVERTIME,GetDate.getDateMyTimeInMillis(lastRecoverTime));
						map.put(VAL_USERID,String.valueOf(borrowTender.getUserId()));
						List<Map<String, String>> msgMap = new ArrayList<>();
						msgMap.add(map);
						// 发送短信
						sendSms(msgMap);
						// 推送消息
						sendMessage(msgMap);
					}
				} else {
					throw new Exception("的总的还款信息(increaseInterestRepay)" + (isInsert ? "插入" : "更新") + "失败!" + "[投资订单号：" + ordId + "]");
				}
			} else {
				throw new Exception("投资详情(IncreaseInterestInvest)更新失败!" + "[投资订单号：" + ordId + "]");
			}
		} else {
			throw new Exception("总的放款明细表(increaseInterestLoan)写入失败!" + "[投资订单号：" + ordId + "]");
		}
		logger.info("-----------放款结束---" + apicron.getBorrowNid() + "---------" + borrowTender.getLoanOrderId());
		return retMsgList;
	}

	/**
	 * 取得借款API任务表
	 *
	 * @return
	 */
	public List<BorrowApicron> getBorrowApicronList(Integer status, Integer apiType) {
		BorrowApicronExample example = new BorrowApicronExample();
		BorrowApicronExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(6);
		criteria.andExtraYieldStatusEqualTo(status);
		criteria.andApiTypeEqualTo(apiType);
		example.setOrderByClause(" id asc ");
		List<BorrowApicron> list = this.borrowApicronMapper.selectByExample(example);

		return list;
	}

	/**
	 * 取得借款API任务表
	 *
	 * @return
	 */
	public List<BorrowApicron> getBorrowApicronListWithRepayStatus(Integer status, Integer apiType) {
		BorrowApicronExample example = new BorrowApicronExample();
		BorrowApicronExample.Criteria criteria = example.createCriteria();
		criteria.andExtraYieldRepayStatusEqualTo(status);
		criteria.andApiTypeEqualTo(apiType);
		example.setOrderByClause(" id asc ");
		List<BorrowApicron> list = this.borrowApicronMapper.selectByExample(example);

		return list;
	}

	/**
	 * 更新借款API任务表
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateBorrowApicron(Integer id, Integer status) {
		BorrowApicron record = new BorrowApicron();
		record.setId(id);
		record.setExtraYieldStatus(status);
		if (record.getWebStatus() == null) {
			record.setWebStatus(0);
		}
//		record.setUpdateTime(GetDate.getMyTimeInMillis());
		return this.borrowApicronMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 更新借款API任务表
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateBorrowApicronOfRepayStatus(Integer id, Integer status) {
		BorrowApicron record = new BorrowApicron();
		record.setId(id);
		record.setExtraYieldRepayStatus(status);
//		record.setUpdateTime(GetDate.getMyTimeInMillis());
		return this.borrowApicronMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 更新借款API任务表
	 *
	 * @param id
	 * @param status
	 * @param data
	 * @return
	 */
	public int updateBorrowApicron(Integer id, Integer status, String data) {
		BorrowApicron record = new BorrowApicron();
		record.setId(id);
		record.setExtraYieldStatus(status);
		if (Validator.isNotNull(data) || status == 1) {
			record.setData(data);
		}
		if (record.getWebStatus() == null) {
			record.setWebStatus(0);
		}
//		record.setUpdateTime(GetDate.getMyTimeInMillis());
		return this.borrowApicronMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 取得借款列表
	 *
	 * @return
	 */
	public List<IncreaseInterestInvest> getBorrowTenderList(String borrowNid) {
		IncreaseInterestInvestExample example = new IncreaseInterestInvestExample();
		IncreaseInterestInvestExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andStatusEqualTo(0);
		example.setOrderByClause(" id asc ");
		List<IncreaseInterestInvest> list = this.increaseInterestInvestMapper.selectByExample(example);
		return list;
	}

	/**
	 * 取得借款信息
	 *
	 * @return
	 */
	public IncreaseInterestRepay getIncreaseInterestRepay(String borrowNid) {
		IncreaseInterestRepayExample example = new IncreaseInterestRepayExample();
		IncreaseInterestRepayExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<IncreaseInterestRepay> list = this.increaseInterestRepayMapper.selectByExample(example);

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
	public IncreaseInterestRepayDetail getIncreaseInterestRepayPlan(String borrowNid, Integer period) {
		IncreaseInterestRepayDetailExample example = new IncreaseInterestRepayDetailExample();
		IncreaseInterestRepayDetailExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		criteria.andRepayPeriodEqualTo(period);
		List<IncreaseInterestRepayDetail> list = this.increaseInterestRepayDetailMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 取得满标日志
	 *
	 * @return
	 */
	public AccountBorrow getAccountBorrow(String borrowNid) {
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
	 * 更新放款状态
	 *
	 * @param accountList
	 * @return
	 */
	public int updateBorrowTender(IncreaseInterestInvest borrowTender) {
		return increaseInterestInvestMapper.updateByPrimaryKeySelective(borrowTender);
	}

	/**
	 * 写入还款明细
	 *
	 * @param accountList
	 * @return
	 */
	private int insertBorrowRecover(IncreaseInterestLoan borrowRecover) {
		return increaseInterestLoanMapper.insertSelective(borrowRecover);
	}

	/**
	 * 借款列表
	 * 
	 * @return
	 */
	public List<BorrowCustomizeVO> selectBorrowList(BorrowCommonCustomizeVO borrowCommonCustomize) {
		return this.borrowCustomizeMapper.selectBorrowList(borrowCommonCustomize);
	}

	public List<WebUserInvestListCustomize> selectUserInvestList(String borrowNid, String userId, String nid, int limitStart, int limitEnd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("borrowNid", borrowNid);
		params.put("userId", userId);
		params.put("nid", nid);
		if (limitStart == 0 || limitStart > 0) {
			params.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			params.put("limitEnd", limitEnd);
		}
		List<WebUserInvestListCustomize> list = webUserInvestListCustomizeMapper.selectUserInvestList(params);
		return list;
	}

	public int countProjectRepayPlanRecordTotal(String borrowNid, String userId, String nid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("borrowNid", borrowNid);
		params.put("nid", nid);
		int total = webUserInvestListCustomizeMapper.countProjectRepayPlanRecordTotal(params);
		return total;
	}

	public List<WebProjectRepayListCustomize> selectProjectRepayPlanList(String borrowNid, String userId, String nid, int offset, int limit) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("borrowNid", borrowNid);
		params.put("nid", nid);
		List<WebProjectRepayListCustomize> projectRepayList = webUserInvestListCustomizeMapper.selectProjectRepayPlanList(params);
		return projectRepayList;
	}

	/**
	 * 发送短信(投资成功)
	 *
	 * @param msgList
	 */
	public void sendSms(List<Map<String, String>> msgList) {
		if (msgList != null && msgList.size() > 0) {
			for (Map<String, String> msg : msgList) {
				if (Validator.isNotNull(msg.get(VAL_USERID)) && Validator.isNotNull(msg.get(VAL_PROFIT)) && new BigDecimal(msg.get(VAL_PROFIT)).compareTo(BigDecimal.ZERO) > 0) {
//					Users users = getUsersByUserId(Integer.valueOf(msg.get(VAL_USERID)));
//					if (users == null || Validator.isNull(users.getMobile()) || (users.getInvestSms() != null && users.getInvestSms() == 1)) {
//						return;
//					}
//					UsersInfo userInfo = this.getUsersInfoByUserId(Integer.valueOf(msg.get(VAL_USERID)));
//					if (StringUtils.isEmpty(userInfo.getTruename())) {
//						msg.put(VAL_NAME, users.getUsername());
//					} else if (userInfo.getTruename().length() > 1) {
//						msg.put(VAL_NAME, userInfo.getTruename().substring(0, 1));
//					} else {
//						msg.put(VAL_NAME, userInfo.getTruename());
//					}
//					Integer sex = userInfo.getSex();
//					if (Validator.isNotNull(sex)) {
//						if (sex.intValue() == 2) {
//							msg.put(VAL_SEX, "女士");
//						} else {
//							msg.put(VAL_SEX, "先生");
//						}
//					}
					logger.info("userid=" + msg.get(VAL_USERID) + ";开始发送短信,发送金额" + msg.get(VAL_PROFIT));
					SmsMessage smsMessage = new SmsMessage(Integer.valueOf(msg.get(VAL_USERID)), msg, null, null, MessageConstant.SMS_SEND_FOR_USER, null, CustomConstants.PARAM_TPL_JIAXIFANGKUAN,
							CustomConstants.CHANNEL_TYPE_NORMAL);

					try {
						smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, msg.get(VAL_USERID), JSON.toJSONBytes(smsMessage)));
					} catch (MQException e2) {
						logger.error("发送短信失败..", e2);
					}
					
//					smsProcesser.gather(smsMessage);
				}
			}
		}
	}

	/**
	 * 发送邮件(投资成功)
	 *
	 * @param borrowNid
	 */
	public void sendMail(List<Map<String, String>> msgList, String borrowNid) {
		if (msgList != null && msgList.size() > 0 && Validator.isNotNull(borrowNid)) {
			for (Map<String, String> msg : msgList) {
				try {
					// 向每个投资人发送邮件
					if (Validator.isNotNull(msg.get(VAL_USERID)) && NumberUtils.isNumber(msg.get(VAL_USERID))) {
						String userId = msg.get(VAL_USERID);
						String orderId = msg.get(VAL_ORDER_ID);
//						Users users = getUsersByUserId(Integer.valueOf(userId));
//						if (users == null || Validator.isNull(users.getEmail())) {
//							return;
//						}
//						String email = users.getEmail();
//						msg.put(VAL_NAME, users.getUsername());
//						UsersInfo usersInfo = this.getUsersInfoByUserId(Integer.valueOf(userId));
//						if (Validator.isNotNull(usersInfo) && Validator.isNotNull(usersInfo.getSex())) {
//							if (usersInfo.getSex() % 2 == 0) {
//								msg.put(VAL_SEX, "女士");
//							} else {
//								msg.put(VAL_SEX, "先生");
//							}
//						}
						String fileName = borrowNid + "_" + orderId + ".pdf";
						String filePath = systemConfig.getHYJF_MAKEPDF_TEMPPATH() + "BorrowLoans_" + GetDate.getMillis() + StringPool.FORWARD_SLASH;
						// 查询借款人用户名
						BorrowCommonCustomizeVO borrowCommonCustomize = new BorrowCommonCustomizeVO();
						// 借款编码
						borrowCommonCustomize.setBorrowNidSrch(borrowNid);
						List<BorrowCustomizeVO> recordList = this.selectBorrowList(borrowCommonCustomize);
						if (recordList != null && recordList.size() == 1) {
							Map<String, Object> contents = new HashMap<String, Object>();
							 contents.put("record", recordList.get(0));
							contents.put("borrowNid", borrowNid);
							contents.put("nid", orderId);
							// 借款人用户名
							contents.put("borrowUsername", recordList.get(0).getUsername().substring(0,1)+"**");
							// 本笔的放款完成时间 (协议签订日期)
							contents.put("recoverTime", msg.get(VAL_LOAN_TIME));
							// 用户投资列表
							List<WebUserInvestListCustomize> userInvestList = this.selectUserInvestList(borrowNid, userId, orderId, -1, -1);
							if (userInvestList != null && userInvestList.size() == 1) {
								contents.put("userInvest", userInvestList.get(0));
							} else {
								logger.info("标的投资信息异常（0条或者大于1条信息）,下载汇盈金服互联网金融服务平台居间服务协议PDF失败。投资订单号:" + orderId);
								return;
							}
							// 如果是分期还款，查询分期信息
							String borrowStyle = recordList.get(0).getBorrowStyle();// 还款模式
							if (borrowStyle != null) {
							  //计算预期收益
		                        BigDecimal earnings = new BigDecimal("0");
		                        // 收益率
		                        
		                        String borrowAprString = StringUtils.isEmpty(recordList.get(0).getBorrowApr())?"0.00":recordList.get(0).getBorrowApr().replace("%", "");
		                        BigDecimal borrowApr = new BigDecimal(borrowAprString);
		                        //投资金额
		                        String accountString = StringUtils.isEmpty(recordList.get(0).getAccount())?"0.00":recordList.get(0).getAccount().replace(",", "");
		                        BigDecimal account = new BigDecimal(accountString);
		                       // 周期
		                        String borrowPeriodString = StringUtils.isEmpty(recordList.get(0).getBorrowPeriod())?"0":recordList.get(0).getBorrowPeriod();
		                        String regEx="[^0-9]";   
		                        Pattern p = Pattern.compile(regEx);   
		                        Matcher m = p.matcher(borrowPeriodString); 
		                        borrowPeriodString = m.replaceAll("").trim();
		                        Integer borrowPeriod = Integer.valueOf(borrowPeriodString);
		                        if (StringUtils.equals("endday", borrowStyle)){
		                            // 还款方式为”按天计息，到期还本还息“：预期收益=投资金额*年化收益÷365*锁定期；
		                            earnings = DuePrincipalAndInterestUtils.getDayInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
		                        } else {
		                            // 还款方式为”按月计息，到期还本还息“：预期收益=投资金额*年化收益÷12*月数；
		                            earnings = DuePrincipalAndInterestUtils.getMonthInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);

		                        }
		                        contents.put("earnings", earnings);
								if ("month".equals(borrowStyle) || "principal".equals(borrowStyle) || "endmonth".equals(borrowStyle)) {
									int recordTotal = this.countProjectRepayPlanRecordTotal(borrowNid, userId, orderId);
									if (recordTotal > 0) {
										Paginator paginator = new Paginator(1, recordTotal);
										List<WebProjectRepayListCustomize> repayList = this.selectProjectRepayPlanList(borrowNid, userId, orderId, paginator.getOffset(), paginator.getLimit());
										contents.put("paginator", paginator);
										contents.put("repayList", repayList);
									} else {
										Paginator paginator = new Paginator(1, recordTotal);
										contents.put("paginator", paginator);
										contents.put("repayList", "");
									}
								}
							}
							String pdfUrl = new PdfGenerator().generateLocal(fileName, CustomConstants.TENDER_CONTRACT, contents);
							if (StringUtils.isNotEmpty(pdfUrl)) {
								File path = new File(filePath);
								if (!path.exists()) {
									path.mkdirs();
								}
								FileUtil.getRemoteFile(pdfUrl.substring(0, pdfUrl.length() - 1), filePath + fileName);
							}
//							String[] emails = { email };
							String[] emails = null; //TODO: 却让你邮件功能是否有根据userId取出邮件地址
							MailMessage message = new MailMessage(Integer.valueOf(userId), msg, "汇盈金服互联网金融服务平台居间服务协议", null, new String[] { filePath + fileName }, emails,
									CustomConstants.EMAILPARAM_TPL_LOANS, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
//							mailMessageProcesser.gather(message);
							
							try {
								mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, userId, JSON.toJSONBytes(message)));
							} catch (Exception e2) {
								logger.error("发送邮件失败..", e2);
							}
							
							
							// modify by zhangjp 优惠券放款相关 start
							// 是否优惠券投资
							if (StringUtils.equals(msg.get(COUPON_TYPE), "1")) {
								CouponRecoverExample example = new CouponRecoverExample();
								example.createCriteria().andTenderIdEqualTo(msg.get(TENDER_NID));
								CouponRecover rc = new CouponRecover();
								rc.setNoticeFlg(1);
								// 将所有该笔投资的放款记录（分期或不分期）都改成通知状态
								this.couponRecoverMapper.updateByExampleSelective(rc, example);
							} else {
								// 更新BorrowRecover邮件发送状态
								String borrowRecoverId = msg.get(PARAM_BORROWRECOVERID);
								if (Validator.isNotNull(borrowRecoverId) && NumberUtils.isNumber(borrowRecoverId)) {
									BorrowRecover borrowRecover = new BorrowRecover();
									borrowRecover.setId(Integer.valueOf(borrowRecoverId));
									borrowRecover.setSendmail(1);
									this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover);
								}
							}
							// modify by zhangjp 优惠券放款相关 end
						} else {
							logger.info("标的信息异常（0条或者大于1条信息）,下载汇盈金服互联网金融服务平台居间服务协议PDF失败。");
							return;
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	/**
	 * 推送消息
	 * 
	 * @param msgList
	 * @author Administrator
	 */

	@Override
	public void sendMessage(List<Map<String, String>> msgList) {
		if (msgList != null && msgList.size() > 0) {
			for (Map<String, String> msg : msgList) {
				if (Validator.isNotNull(msg.get(VAL_USERID)) && Validator.isNotNull(msg.get(VAL_PROFIT)) && new BigDecimal(msg.get(VAL_PROFIT)).compareTo(BigDecimal.ZERO) > 0) {
//					Users users = getUsersByUserId(Integer.valueOf(msg.get(VAL_USERID)));
//					if (users == null) {
//						return;
//					} else {
//						UsersInfo userInfo = this.getUsersInfoByUserId(Integer.valueOf(msg.get(VAL_USERID)));
//						if (StringUtils.isEmpty(userInfo.getTruename())) {
//							msg.put(VAL_NAME, users.getUsername());
//						} else if (userInfo.getTruename().length() > 1) {
//							msg.put(VAL_NAME, userInfo.getTruename().substring(0, 1));
//						} else {
//							msg.put(VAL_NAME, userInfo.getTruename());
//						}
//						Integer sex = userInfo.getSex();
//						if (Validator.isNotNull(sex)) {
//							if (sex.intValue() == 2) {
//								msg.put(VAL_SEX, "女士");
//							} else {
//								msg.put(VAL_SEX, "先生");
//							}
//						}
						AppMsMessage smsMessage = new AppMsMessage(Integer.valueOf(msg.get(VAL_USERID)), msg, null, MessageConstant.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_JIAXIFANGKUAN);
//						appMsProcesser.gather(smsMessage);

						try {
							appMessageProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, msg.get(VAL_USERID),
									JSON.toJSONBytes(smsMessage)));
						} catch (MQException e) {
							logger.error("发送app消息失败..", e);
						}
						
					}
				}
			}
		
	}

	private BorrowStyle getborrowStyleByNid(String borrowStyle) {
		BorrowStyleExample example = new BorrowStyleExample();
		BorrowStyleExample.Criteria cri = example.createCriteria();
		cri.andNidEqualTo(borrowStyle);
		List<BorrowStyle> style = borrowStyleMapper.selectByExample(example);
		return style.get(0);
	}

	/**
	 * 调用放款接口
	 *
	 * @return
	 */
	@Override
	public boolean loans() {

		boolean result = false;
		// 取得未放款任务
		List<BorrowApicron> listApicron = getBorrowApicronList(STATUS_WAIT, 0);
		
		if (listApicron != null && listApicron.size() > 0) {
			// 循环进行放款
			for (BorrowApicron apicron : listApicron) {
				int errorCnt = 0;
				Long startTime = GetDate.getMillis();
				// 错误信息
				StringBuffer sbError = new StringBuffer();
				try {
					logger.info("融通宝加息自动放款任务开始。[订单号：" + apicron.getBorrowNid() + "]");
					// 更新任务API状态为进行中
					updateBorrowApicron(apicron.getId(), STATUS_RUNNING);
					// 借款编号
					String borrowNid = apicron.getBorrowNid();
					// 借款人ID
					Integer borrowUserId = apicron.getUserId();
					// 取得投资详情列表
					List<IncreaseInterestInvest> listTender = getBorrowTenderList(borrowNid);
					if (listTender != null && listTender.size() > 0) {
						// 取得借款人账户信息
						Account borrowAccount = getAccountByUserId(borrowUserId);
						if (borrowAccount == null || StringUtils.isEmpty(borrowAccount.getAccountId())) {
							throw new Exception("借款人账户不存在。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
						}
						// 借款人在银行的账户信息
//						BankOpenAccount borrowUserCust = increaseinterestLoansService.getBankOpenAccount(borrowUserId);
//						if (borrowUserCust == null || StringUtils.isEmpty(borrowUserCust.getAccount())) {
//							throw new Exception("借款人未开户。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
//						}
						// 取得借款详情
						Borrow borrow = getBorrow(borrowNid);
						if (borrow == null) {
							throw new Exception("借款详情不存在。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
						}
						// 投资信息
						IncreaseInterestInvest borrowTender = null;
						// 投资总件数
						int size = listTender.size();
						/** 循环投资详情列表 */
						for (int i = 0; i < size; i++) {
							borrowTender = listTender.get(i);
							try {
								if (Validator.isNull(borrowTender.getLoanOrderId())) {
									// 设置放款订单号
									borrowTender.setLoanOrderId(GetOrderIdUtils.getOrderId2(borrowTender.getUserId()));
									// 设置放款时间
									borrowTender.setOrderDate(GetOrderIdUtils.getOrderDate());
									// 更新放款信息
									updateBorrowTender(borrowTender);
								}
								((IncreaseinterestLoansService)AopContext.currentProxy()).updateBorrowLoans(apicron, borrowTender);
							} catch (Exception e) {
								sbError.append(e.getMessage()).append("<br/>");
								logger.error(e.getMessage());
								errorCnt++;
							}
						}

						// 有错误时
						if (errorCnt > 0) {
							throw new Exception("融通宝加息放款时发生错误。" + "[借款编号：" + borrowNid + "]," + "[错误件数：" + errorCnt + "]");
						}

						// 更新任务API状态为完成
						updateBorrowApicron(apicron.getId(), STATUS_SUCCESS);
					}
				} catch (Exception e) {
//					int runCnt = 1;
//					if (runTimes.containsKey(apicron.getBorrowNid())) {
//						TimesBean bean = runTimes.get(apicron.getBorrowNid());
//						bean.setCnt(bean.getCnt() + 1);
//						bean.setTime(GetDate.getMyTimeInMillis());
//						runCnt = bean.getCnt();
//						runTimes.put(apicron.getBorrowNid(), bean);
//					} else {
//						TimesBean bean = new TimesBean();
//						bean.setCnt(runCnt);
//						bean.setTime(GetDate.getMyTimeInMillis());
//						bean.setStatus(1);
//						runTimes.put(apicron.getBorrowNid(), bean);
//					}
//					LogUtil.errorLog(THIS_CLASS, methodName, e);
//					if (runCnt >= 3) {
//						// 清除重新放款任务
//						runTimes.remove(apicron.getBorrowNid());
//						if (sbError.length() == 0) {
//							sbError.append(e.getMessage());
//						}
//						// 更新任务API状态为错误
//						increaseinterestLoansService.updateBorrowApicron(apicron.getId(), STATUS_ERROR, sbError.toString());
//						LogUtil.endLog(THIS_CLASS, methodName, "融通宝自动放款任务发生错误。[标号：" + apicron.getBorrowNid() + "]");
//					} else {
//						// 更新任务API状态为重新执行
//						increaseinterestLoansService.updateBorrowApicron(apicron.getId(), STATUS_WAIT);
//					}
					// 取得是否线上
					String online = "生产环境";
					String toMail[] = systemConfig.getLoadRepayMailAddrs();
					if (systemConfig.isEnvTest()) {
						online = "测试环境";
					}
					// 发送错误邮件
					StringBuffer msg = new StringBuffer();
					msg.append("借款标号：").append(apicron.getBorrowNid()).append("<br/>");
					msg.append("放款时间：").append(GetDate.formatTime()).append("<br/>");
//					msg.append("执行次数：").append("第" + 1 + "次").append("<br/>");
					msg.append("错误信息：").append(e.getMessage()).append("<br/>");
					msg.append("详细错误信息：<br/>").append(sbError.toString());
					
					MailMessage message = new MailMessage(null, null, "[" + online + "] " + apicron.getBorrowNid() + " 融通宝加息放款失败", msg.toString(), null, toMail, null,
							MessageConstant.MAILSENDFORMAILINGADDRESSMSG);
					
					try {
						mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, apicron.getBorrowNid(), JSON.toJSONBytes(message)));
					} catch (Exception e2) {
						logger.error("发送邮件失败..", e2);
					}
					
					
				} finally {
					Long endTime = GetDate.getMillis();
					logger.info("融通宝加息自动放款任务结束。[订单号：" + apicron.getBorrowNid() + "], 耗时：" + (endTime - startTime) / 1000 + "s");
				}
			}
		}

		result = true;
		return result;
		
	}

	
	
	
}
