package com.hyjf.am.trade.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.trade.AccountCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.AccountWebListProducer;
import com.hyjf.am.trade.mq.AppMessageProducer;
import com.hyjf.am.trade.mq.Producer;
import com.hyjf.am.trade.mq.SmsProducer;
import com.hyjf.am.trade.service.IncreaseInterestInvestService;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.statistics.AccountWebListVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

/**
 * @author xiasq
 * @version IncreaseInterestInvestServiceImpl, v0.1 2018/6/19 11:57
 */
@Service
public class IncreaseInterestInvestServiceImpl implements IncreaseInterestInvestService {
	private static final Logger logger = LoggerFactory.getLogger(IncreaseInterestInvestServiceImpl.class);

	@Autowired
	IncreaseInterestLoanMapper increaseInterestLoanMapper;
	@Autowired
	IncreaseInterestLoanDetailMapper increaseInterestLoanDetailMapper;
	@Autowired
	IncreaseInterestRepayMapper increaseInterestRepayMapper;
	@Autowired
	IncreaseInterestRepayDetailMapper increaseInterestRepayDetailMapper;
	@Autowired
	IncreaseInterestInvestMapper increaseInterestInvestMapper;
	@Autowired
	BorrowMapper borrowMapper;
	@Autowired
	AccountListMapper accountListMapper;
	@Autowired
	AccountMapper accountMapper;
	@Autowired
	AccountCustomizeMapper accountCustomizeMapper;
	@Autowired
	AccountWebListProducer accountWebListProducer;
	@Autowired
	AppMessageProducer appMessageProducer;
	@Autowired
	SmsProducer smsProducer;

	/**
	 * 根据借款编号检索还款信息
	 * 
	 * @param borrowNid
	 * @return
	 */
	@Override
	public List<IncreaseInterestLoan> selectIncreaseInterestLoanList(String borrowNid) {
		IncreaseInterestLoanExample example = new IncreaseInterestLoanExample();
		IncreaseInterestLoanExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowNid);
		return this.increaseInterestLoanMapper.selectByExample(example);
	}

	/**
	 * 根据借款编号检索借款信息
	 * 
	 * @param borrowNid
	 * @return
	 */
	@Override
	public Borrow selectBorrowByNid(String borrowNid) {
		BorrowExample example = new BorrowExample();
		BorrowExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowNid);
		List<Borrow> borrowList = this.borrowMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(borrowList)) {
			return borrowList.get(0);
		}
		return null;
	}

	/**
	 * 根据借款编号,还款期数,还款方式取得还款金额
	 * 
	 * @param borrowNid
	 * @param borrowStyle
	 * @param periodNow
	 * @return
	 */
	@Override
	public BigDecimal selectBorrowAccountWithPeriod(String borrowNid, String borrowStyle, Integer periodNow) {
		BigDecimal account = BigDecimal.ZERO;
		// 是否分期(true:分期, false:单期)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		if (isMonth) {
			IncreaseInterestLoanDetailExample example = new IncreaseInterestLoanDetailExample();
			IncreaseInterestLoanDetailExample.Criteria cra = example.createCriteria();
			cra.andBorrowNidEqualTo(borrowNid);
			cra.andRepayStatusEqualTo(0);
			cra.andRepayPeriodEqualTo(periodNow);
			List<IncreaseInterestLoanDetail> list = this.increaseInterestLoanDetailMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				for (IncreaseInterestLoanDetail increaseInterestLoanDetail : list) {
					account = account.add(increaseInterestLoanDetail.getLoanInterest());
				}
			}
		} else {
			IncreaseInterestLoanExample example = new IncreaseInterestLoanExample();
			IncreaseInterestLoanExample.Criteria cra2 = example.createCriteria();
			cra2.andBorrowNidEqualTo(borrowNid);
			cra2.andRepayStatusEqualTo(0);
			List<IncreaseInterestLoan> list = this.increaseInterestLoanMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				for (IncreaseInterestLoan increaseInterestLoan : list) {
					account = account.add(increaseInterestLoan.getLoanInterest());
				}
			}
		}
		return account;
	}

	/**
	 * 融通宝加息还款
	 * 
	 * @param apicron
	 * @param increaseInterestLoan
	 * @param investUserCustId
	 *            投资人电子账号
	 * @return
	 */
	@Override
	public void increaseInterestRepay(BorrowApicronVO apicron, IncreaseInterestLoan increaseInterestLoan,
                                      String account, String companyAccount) {
		logger.info("-----------融通宝加息还款开始---" + apicron.getBorrowNid() + "---------");

		/** 基本变量 */
		// 还款订单号
		String repayOrderId = null;
		// 还款订单日期
		String repayOrderDate = null;
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 借款编号
		String borrowNid = apicron.getBorrowNid();
		// 当前期数
		Integer periodNow = apicron.getPeriodNow();
		/** 标的基本数据 */
		// 取得借款详情
		Borrow borrow = this.selectBorrowByNid(borrowNid);
		// 取得还款详情
		IncreaseInterestRepay increaseInterestRepay = selectIncreaseInterestRepay(borrowNid);
		// 还款期数
		Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
		// 还款方式
		String borrowStyle = borrow.getBorrowStyle();
		// 剩余还款期数
		Integer periodNext = borrowPeriod - periodNow;
		// 还款利息
		BigDecimal repayInterest = BigDecimal.ZERO;
		// 还款时间
		Integer repayTime = null;
		// 投资订单号
		String investOrderId = increaseInterestLoan.getInvestOrderId();
		// 投资人用户ID
		Integer investUserId = increaseInterestLoan.getUserId();
		// 取得融通宝加息投资信息
		IncreaseInterestInvest increaseInterestInvest = selectIncreaseInterestInvestInfo(investOrderId);

		// 交易流水号
		Integer seqNo = 0;
		// 交易日期
		Integer txDate = 0;
		// 交易时间
		Integer txTime = 0;
		// 融通宝放款详情
		IncreaseInterestLoanDetail increaseInterestLoanDetail = null;

		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);

		// [principal: 等额本金, month:等额本息, month:等额本息, endmonth:先息后本]
		if (isMonth) {
			// 取得分期还款计划表
			increaseInterestLoanDetail = selectIncreaseInterestLoanDetail(borrowNid, periodNow, investUserId,
					increaseInterestLoan.getInvestId());
			if (increaseInterestLoanDetail != null) {
				// 保存还款订单号
				if (StringUtils.isBlank(increaseInterestLoanDetail.getRepayOrderId())) {
					// 还款订单号
					repayOrderId = GetOrderIdUtils.getOrderId2(investUserId);
					// 还款订单日期
					repayOrderDate = GetOrderIdUtils.getOrderDate();
					// 设置还款订单号
					increaseInterestLoanDetail.setRepayOrderId(repayOrderId);
					// 设置还款时间
					increaseInterestLoanDetail.setRepayOrderDate(repayOrderDate);
					// 更新还款信息
					boolean increaseInterestLoanDetailFlag = this
							.updateIncreaseInterestLoanDetail(increaseInterestLoanDetail) > 0 ? true : false;
					if (!increaseInterestLoanDetailFlag) {
						throw new RuntimeException(
								"添加还款订单号，更新hyjf_increase_interest_loan_detail表失败" + "，[投资订单号:" + investOrderId + "]");
					}
					// 更新融通宝加息项目放款总表
					increaseInterestLoan.setRepayOrderId(repayOrderId);
					increaseInterestLoan.setRepayOrderDate(repayOrderDate);
					// 更新还款信息
					boolean increaseInterestLoanFlag = this.updateIncreaseInterestLoan(increaseInterestLoan) > 0 ? true
							: false;
					if (!increaseInterestLoanFlag) {
						throw new RuntimeException(
								"添加还款订单号，更新hyjf_increase_interest_loan表失败" + "，[投资订单号:" + investOrderId + "]");
					}
				} else {
					// 还款订单号
					repayOrderId = increaseInterestLoanDetail.getRepayOrderId();
					// 还款订单日期
					repayOrderDate = increaseInterestLoanDetail.getRepayOrderDate();
				}
				// 还款时间
				repayTime = increaseInterestLoanDetail.getRepayTime();
				// 还款利息
				repayInterest = increaseInterestLoanDetail.getRepayInterestWait();
			} else {
				throw new RuntimeException("分期还款计划表数据不存在。[借款编号:" + borrowNid + "]，" + "[投资订单号:" + investOrderId + "]，"
						+ "[期数:" + periodNow + "]");
			}
		}
		// [endday: 按天计息, end:按月计息]
		else {
			// 保存还款订单号
			if (StringUtils.isBlank(increaseInterestLoan.getRepayOrderId())) {
				// 还款订单号
				repayOrderId = GetOrderIdUtils.getOrderId2(investUserId);
				// 还款订单日期
				repayOrderDate = GetOrderIdUtils.getOrderDate();
				// 设置还款订单号
				increaseInterestLoan.setRepayOrderId(repayOrderId);
				// 设置还款时间
				increaseInterestLoan.setRepayOrderDate(repayOrderDate);
				// 更新还款信息
				boolean increaseInterestLoanFlag = this.updateIncreaseInterestLoan(increaseInterestLoan) > 0 ? true
						: false;
				if (!increaseInterestLoanFlag) {
					throw new RuntimeException(
							"添加还款订单号，更新hyjf_increase_interest_loan表失败" + "，[投资订单号:" + investOrderId + "]");
				}
			} else {
				// 还款订单号
				repayOrderId = increaseInterestLoan.getRepayOrderId();
				// 还款订单日期
				repayOrderDate = increaseInterestLoan.getRepayOrderDate();
			}

			// 还款时间
			repayTime = increaseInterestLoan.getRepayTime();
			// 还款利息
			repayInterest = increaseInterestLoan.getRepayInterestWait();
		}
		// 判断该收支明细是否存在时,跳出本次循环
		if (countAccountListByNid(repayOrderId) > 0) {
			return;
		}

		// 调用银行接口
		if (repayInterest.compareTo(BigDecimal.ZERO) > 0) {
			BankCallBean transferBean = transfer(investUserId, account, repayInterest, repayOrderId, companyAccount);
			String respCode = transferBean == null ? "" : transferBean.getRetCode();
			// 调用接口失败时(000以外)
			if (!BankCallStatusConstant.RESPCODE_SUCCESS.equals(respCode)) {
				String message = transferBean == null ? "" : transferBean.getRetMsg();
				logger.error("融通宝自动还款调用转账接口失败。" + message + "，[投资订单号:" + investOrderId + "]");
				throw new RuntimeException(
						"融通宝自动还款调用转账接口失败。" + respCode + ":" + message + "，[投资订单号:" + investOrderId + "]");
			}
			txDate = StringUtils.isNotBlank(transferBean.getTxDate()) ? Integer.parseInt(transferBean.getTxDate()) : 0;
			txTime = StringUtils.isNotBlank(transferBean.getTxTime()) ? Integer.parseInt(transferBean.getTxTime()) : 0;
			seqNo = StringUtils.isNotBlank(transferBean.getSeqNo()) ? Integer.parseInt(transferBean.getSeqNo()) : 0;

		} else {
			logger.info("融通宝自动还款。" + "[还款金额:" + repayInterest + "，[投资订单号:" + investOrderId + "]");
		}

		// 判断该收支明细是否存在时,跳出本次循环
		if (countAccountListByNid(repayOrderId) == 0) {
			// 更新账户信息(投资人)
			Account investUserAccount = new Account();
			investUserAccount.setUserId(investUserId);
			// 投资人可用余额
			investUserAccount.setBankBalance(repayInterest);
			// 投资人待收金额
			investUserAccount.setBankAwait(repayInterest);
			// 投资人待收收益
			investUserAccount.setBankAwaitInterest(repayInterest);
			// 江西银行可用余额
			investUserAccount.setBankBalanceCash(repayInterest);
			// 累计收益
			investUserAccount.setBankInterestSum(repayInterest);
			// 还款后更新投资人的账户信息
			boolean investAccountFlag = this.accountCustomizeMapper.updateAccountAfterRepay(investUserAccount) > 0
					? true
					: false;
			if (investAccountFlag) {
				// 取得账户信息(投资人)
				investUserAccount = this.selectAccountByUserId(investUserId);
				if (investUserAccount != null) {
					// 写入收支明细
					AccountList accountList = new AccountList();
					accountList.setNid(repayOrderId); // 还款订单号
					accountList.setUserId(investUserId); // 投资人
					accountList.setAmount(repayInterest); // 投资总收入
					accountList.setAccountId(account);// 投资人客户号
					accountList.setType(1); // 1收入
					accountList.setTrade("increase_interest_repay_yes"); // 投资成功
					accountList.setTradeCode("balance"); // 余额操作
					accountList.setTxDate(txDate);
					accountList.setTxTime(txTime);
					accountList.setSeqNo(String.valueOf(seqNo));
					accountList.setBankSeqNo(String.valueOf(txDate) + String.valueOf(txTime) + String.valueOf(seqNo));
					accountList.setBankTotal(investUserAccount.getBankTotal()); // 银行总资产
					accountList.setBankBalance(investUserAccount.getBankBalance()); // 银行可用余额
					accountList.setBankFrost(investUserAccount.getBankFrost());// 银行冻结金额
					accountList.setBankWaitCapital(investUserAccount.getBankWaitCapital());// 银行待还本金
					accountList.setBankWaitInterest(investUserAccount.getBankWaitInterest());// 银行待还利息
					accountList.setBankAwaitCapital(investUserAccount.getBankAwaitCapital());// 银行待收本金
					accountList.setBankAwaitInterest(investUserAccount.getBankAwaitInterest());// 银行待收利息
					accountList.setBankAwait(investUserAccount.getBankAwait());// 银行待收总额
					accountList.setBankInterestSum(investUserAccount.getBankInterestSum()); // 银行累计收益
					accountList.setBankInvestSum(investUserAccount.getBankInvestSum());// 银行累计投资
					accountList.setBankWaitRepay(investUserAccount.getBankWaitRepay());// 银行待还金额
					accountList.setTotal(investUserAccount.getTotal());
					accountList.setBalance(investUserAccount.getBalance());
					accountList.setFrost(investUserAccount.getFrost());
					accountList.setAwait(investUserAccount.getAwait());
					accountList.setRepay(investUserAccount.getRepay());
					accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作者
					accountList.setRemark(borrowNid);
					accountList.setIp(borrow.getAddip()); // 操作IP
					accountList.setWeb(0); // PC
					accountList.setIsBank(1);
					accountList.setCheckStatus(0);
					boolean investAccountListFlag = insertAccountList(accountList) > 0 ? true : false;
					if (investAccountListFlag) {
						// 更新还款明细表
						// 分期并且不是最后一期
						if (increaseInterestLoanDetail != null && Validator.isNotNull(periodNext) && periodNext > 0) {
							increaseInterestLoan.setRepayStatus(0); // 未还款
							// 取得分期还款计划表下一期的还款
							IncreaseInterestLoanDetail loanDetail = selectIncreaseInterestLoanDetail(borrowNid,
									periodNow + 1, investUserId, increaseInterestLoan.getInvestId());
							increaseInterestLoan.setRepayTime(loanDetail.getRepayTime()); // 计算下期时间
						} else {
							increaseInterestLoan.setRepayStatus(1); // 已还款
							increaseInterestLoan.setRepayActionTime(nowTime); // 实际还款时间
							increaseInterestLoan.setRepayTime(repayTime);
						}
						// 分期时
						if (increaseInterestLoanDetail != null) {
							increaseInterestLoan.setRepayPeriod(periodNext);
						}
						increaseInterestLoan
								.setRepayInterestYes(increaseInterestLoan.getRepayInterestYes().add(repayInterest));
						increaseInterestLoan.setRepayInterestWait(
								increaseInterestLoan.getRepayInterestWait().subtract(repayInterest));
						increaseInterestLoan.setWeb(2); // 写入网站收支
						boolean increaseInterestLoanFlag = this.increaseInterestLoanMapper
								.updateByPrimaryKeySelective(increaseInterestLoan) > 0 ? true : false;
						if (increaseInterestLoanFlag) {
							// 更新总的还款明细
							// 分期并且不是最后一期
							if (increaseInterestLoanDetail != null && Validator.isNotNull(periodNext)
									&& periodNext > 0) {
								increaseInterestRepay.setRepayStatus(0); // 未还款
								// 取得分期还款计划表下一期的还款
								IncreaseInterestLoanDetail loanDetail = selectIncreaseInterestLoanDetail(borrowNid,
										periodNow + 1, investUserId, increaseInterestLoan.getInvestId());
								increaseInterestRepay.setRepayTime(loanDetail.getRepayTime()); // 计算下期时间
							} else {
								increaseInterestRepay.setRepayActionTime(nowTime); // 实际还款时间
								increaseInterestRepay.setRepayTime(repayTime);
							}
							// 分期时
							if (increaseInterestLoanDetail != null) {
								increaseInterestRepay.setRepayPeriod(periodNow);// 当前还款期数
								increaseInterestRepay.setRemainPeriod(periodNext);// 剩余还款期数
							} else {
								// 不分期
								increaseInterestRepay.setRepayPeriod(1);
								increaseInterestRepay.setRemainPeriod(0);
							}
							// 更新总的还款明细
							increaseInterestRepay.setRepayInterestYes(
									increaseInterestRepay.getRepayInterestYes().add(repayInterest));
							increaseInterestRepay.setWeb(0);
							boolean increaseInterestRepayFlag = this.increaseInterestRepayMapper
									.updateByPrimaryKeySelective(increaseInterestRepay) > 0 ? true : false;
							if (increaseInterestRepayFlag) {
								increaseInterestInvest.setRepayInterestYes(
										increaseInterestInvest.getRepayInterestYes().add(repayInterest));
								increaseInterestInvest.setRepayInterestWait(
										increaseInterestInvest.getRepayInterestWait().subtract(repayInterest));
								increaseInterestInvest.setRepayTimes(increaseInterestInvest.getRepayTimes() + 1);
								boolean increaseInterestInvestFlag = this.increaseInterestInvestMapper
										.updateByPrimaryKeySelective(increaseInterestInvest) > 0 ? true : false;
								if (increaseInterestInvestFlag) {
									// 分期时
									if (increaseInterestLoanDetail != null) {
										// 更新还款计划表
										increaseInterestLoanDetail.setRepayStatus(1);
										increaseInterestLoanDetail.setRepayActionTime(nowTime);
										increaseInterestLoanDetail.setRepayInterestYes(repayInterest);
										increaseInterestLoanDetail.setRepayInterestWait(BigDecimal.ZERO);
										increaseInterestLoanDetail.setRepayOrderDate(repayOrderDate);
										increaseInterestLoanDetail.setRepayOrderId(repayOrderId);
										boolean increaseInterestLoanDetailFlag = this.increaseInterestLoanDetailMapper
												.updateByPrimaryKeySelective(increaseInterestLoanDetail) > 0 ? true
														: false;
										if (increaseInterestLoanDetailFlag) {
											// 更新总的还款计划
											IncreaseInterestRepayDetail increaseInterestRepayDetail = selectIncreaseInterestRepayDetail(
													borrowNid, periodNow);
											if (increaseInterestRepayDetail != null) {
												increaseInterestRepayDetail.setRepayStatus(1);
												increaseInterestRepayDetail.setRepayActionTime(nowTime);
												increaseInterestRepayDetail
														.setRepayInterestYes(increaseInterestRepayDetail
																.getRepayInterestYes().add(repayInterest));
												increaseInterestRepayDetail
														.setRepayInterestWait(increaseInterestRepayDetail
																.getRepayInterestWait().subtract(repayInterest));
												increaseInterestRepayDetail.setOrderId(repayOrderId);
												increaseInterestRepayDetail.setOrderDate(repayOrderDate);
												boolean increaseInterestRepayDetailFlag = this.increaseInterestRepayDetailMapper
														.updateByPrimaryKeySelective(increaseInterestRepayDetail) > 0
																? true
																: false;
												if (!increaseInterestRepayDetailFlag) {
													throw new RuntimeException(
															"融通宝加息还款详情表(hyjf_increase_interest_repay_detail)更新失败!"
																	+ "[投资订单号:" + investOrderId + "]");
												}

											} else {
												throw new RuntimeException(
														"融通宝加息还款详情表(hyjf_increase_interest_repay_detail)查询失败!"
																+ "[投资订单号:" + investOrderId + "]");
											}
										} else {
											throw new RuntimeException("融通宝加息投资表(hyjf_increase_interest_invest)更新失败!"
													+ "[投资订单号:" + investOrderId + "]");
										}
									}
									// 写入网站收支
									if (repayInterest.compareTo(BigDecimal.ZERO) > 0) {
										// 插入网站收支明细记录
										AccountWebListVO accountWebList = new AccountWebListVO();
										accountWebList.setOrdid(increaseInterestInvest.getOrderId() + "_" + periodNow);// 订单号
										accountWebList.setBorrowNid(borrowNid); // 投资编号
										accountWebList.setUserId(investUserId); // 借款人
										accountWebList.setAmount(repayInterest); // 产品加息收益
										accountWebList.setType(CustomConstants.TYPE_OUT); // 类型1收入,2支出
										accountWebList.setTrade(CustomConstants.TRADE_REPAY); // 产品加息收益
										accountWebList.setTradeType(CustomConstants.TRADE_REPAY_NM); // 产品加息收益
										accountWebList.setRemark(borrowNid); // 项目编号
										accountWebList.setCreateTime(nowTime);

										try {
											accountWebListProducer.messageSend(
													new Producer.MassageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC,
															JSON.toJSONBytes(accountWebList)));
										} catch (MQException e) {
											logger.error("网站收支记录(huiyingdai_account_web_list)更新失败!" + "[投资订单号:"
													+ investOrderId + "]", e);
										}
									}

									Map<String, String> msg = new HashMap<String, String>();
									msg.put("userId", String.valueOf(investUserId));
									msg.put("val_amount", repayInterest == null ? "0.00" : repayInterest.toString());
									msg.put("val_profit", repayInterest == null ? "0.00" : repayInterest.toString());

									SmsMessage smsMessage = new SmsMessage(investUserId, msg, null, null,
											MessageConstant.SMS_SEND_FOR_USER, null,
											CustomConstants.PARAM_TPL_JIAXIHUANKUAN,
											CustomConstants.CHANNEL_TYPE_NORMAL);
									AppMsMessage appMsMessage = new AppMsMessage(investUserId, msg, null,
											MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_JIAXIHUANKUAN);
									try {
										smsProducer.messageSend(new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC,
												JSON.toJSONBytes(smsMessage)));
										appMessageProducer.messageSend(new Producer.MassageContent(
												MQConstant.APP_MESSAGE_TOPIC, JSON.toJSONBytes(appMsMessage)));
									} catch (MQException e) {
										logger.error("融通宝还款成功发送消息通知失败...", e);
									}

								} else {
									throw new RuntimeException("融通宝加息投资表(hyjf_increase_interest_invest)更新失败!"
											+ "[投资订单号:" + investOrderId + "]");
								}
							} else {
								throw new RuntimeException(
										"总的还款明细表(hyjf_increase_interest_repay)更新失败!" + "[投资订单号:" + investOrderId + "]");
							}
						} else {
							throw new RuntimeException(
									"融通宝加息项目放款总表(hyjf_increase_interest_loan)更新失败!" + "[投资订单号:" + investOrderId + "]");
						}
					} else {
						throw new RuntimeException(
								"收支明细(huiyingdai_account_list)写入失败!" + "[投资订单号:" + investOrderId + "]");
					}
				} else {
					throw new RuntimeException(
							"投资人账户信息不存在。[投资人ID:" + investUserId + "]，" + "[投资订单号:" + investOrderId + "]");
				}
			} else {
				throw new RuntimeException("投资人资金记录(huiyingdai_account)更新失败!" + "[投资订单号:" + investOrderId + "]");
			}
		}
		logger.info(
				"-----------融通宝加息还款结束-----------" + apicron.getBorrowNid() + "-----[还款订单号:----" + repayOrderId + "]");
	}

	@Override
	public void updateRepay(String borrowNid, Integer periodNow, Integer borrowUserId) {
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 标的项目详情
		Borrow borrow = selectBorrowByNid(borrowNid);
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle())
				|| CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle());
		// 查询未债转的数据
		IncreaseInterestLoanExample example = new IncreaseInterestLoanExample();
		example.createCriteria().andBorrowNidEqualTo(borrowNid).andRepayStatusEqualTo(0);
		int increaseInterestLoanCnt = this.increaseInterestLoanMapper.countByExample(example);
		int repayStatus = 0;
		if (increaseInterestLoanCnt == 0) {
			repayStatus = 1;
		}
		// 借款人还款表更新
		IncreaseInterestRepay increaseInterestRepay = new IncreaseInterestRepay();
		increaseInterestRepay.setRepayActionTime(nowTime);
		if (increaseInterestLoanCnt == 0) {
			increaseInterestRepay.setRepayStatus(repayStatus); // 已还款
			increaseInterestRepay.setRepayActionTime(nowTime); // 实际还款时间
		} else {
			increaseInterestRepay.setRepayStatus(repayStatus);// 未还款
			if (isMonth) {
				// 分期的场合，根据借款编号和还款期数从还款计划表中取得还款时间
				IncreaseInterestRepayDetailExample increaseInterestRepayDetailExample = new IncreaseInterestRepayDetailExample();
				IncreaseInterestRepayDetailExample.Criteria increaseInterestRepayDetailCriteria = increaseInterestRepayDetailExample
						.createCriteria();
				increaseInterestRepayDetailCriteria.andBorrowNidEqualTo(borrowNid);
				increaseInterestRepayDetailCriteria.andRepayPeriodEqualTo(periodNow + 1);
				List<IncreaseInterestRepayDetail> increaseInterestRepayDetails = increaseInterestRepayDetailMapper
						.selectByExample(increaseInterestRepayDetailExample);
				if (increaseInterestRepayDetails != null && increaseInterestRepayDetails.size() > 0) {
					IncreaseInterestRepayDetail IncreaseInterestRepayDetail = increaseInterestRepayDetails.get(0);
					// 设置下期还款时间
					increaseInterestRepay.setRepayTime(IncreaseInterestRepayDetail.getRepayTime());
				}
			}
		}
		// 更新IncreaseInterestRepay
		IncreaseInterestRepayExample increaseInterestRepayExample = new IncreaseInterestRepayExample();
		increaseInterestRepayExample.createCriteria().andBorrowNidEqualTo(borrowNid).andRepayStatusEqualTo(0);
		this.increaseInterestRepayMapper.updateByExampleSelective(increaseInterestRepay, increaseInterestRepayExample);
	}

	/**
	 * 根据项目编号检索融通宝加息还款信息
	 *
	 * @Title selectIncreaseInterestRepay
	 * @param borrowNid
	 * @return
	 */
	private IncreaseInterestRepay selectIncreaseInterestRepay(String borrowNid) {
		IncreaseInterestRepayExample example = new IncreaseInterestRepayExample();
		IncreaseInterestRepayExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowNid);
		List<IncreaseInterestRepay> list = this.increaseInterestRepayMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据投资订单号检索融通宝加息投资信息
	 *
	 * @Title selectIncreaseInterestInvestInfo
	 * @param investOrderId
	 * @return
	 */
	private IncreaseInterestInvest selectIncreaseInterestInvestInfo(String investOrderId) {
		IncreaseInterestInvestExample example = new IncreaseInterestInvestExample();
		IncreaseInterestInvestExample.Criteria cra = example.createCriteria();
		cra.andOrderIdEqualTo(investOrderId);
		List<IncreaseInterestInvest> list = this.increaseInterestInvestMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据标的编号,还款期数,投资用户Id,投资Id检索还款信息
	 *
	 * @Title selectIncreaseInterestLoanDetail
	 * @param borrowNid
	 * @param periodNow
	 * @param investUserId
	 * @param investId
	 * @return
	 */
	private IncreaseInterestLoanDetail selectIncreaseInterestLoanDetail(String borrowNid, Integer periodNow,
			Integer investUserId, Integer investId) {
		IncreaseInterestLoanDetailExample example = new IncreaseInterestLoanDetailExample();
		IncreaseInterestLoanDetailExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowNid);
		cra.andRepayPeriodEqualTo(periodNow);
		cra.andUserIdEqualTo(investUserId);
		cra.andInvestIdEqualTo(investId);
		cra.andRepayStatusEqualTo(0);// 未转账
		List<IncreaseInterestLoanDetail> list = this.increaseInterestLoanDetailMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据还款订单号检索交易明细
	 *
	 * @Title countAccountListByNid
	 * @param repayOrderId
	 * @return
	 */
	private int countAccountListByNid(String repayOrderId) {
		AccountListExample accountListExample = new AccountListExample();
		accountListExample.createCriteria().andNidEqualTo(repayOrderId).andTradeEqualTo("increase_interest_repay_yes");
		return this.accountListMapper.countByExample(accountListExample);
	}

	/**
	 * 更新融通宝加息项目放款详情表
	 *
	 * @Title updateIncreaseInterestLoanDetail
	 * @param increaseInterestLoanDetail
	 * @return
	 */
	private int updateIncreaseInterestLoanDetail(IncreaseInterestLoanDetail increaseInterestLoanDetail) {
		int cnt = this.increaseInterestLoanDetailMapper.updateByPrimaryKeySelective(increaseInterestLoanDetail);
		return cnt;
	}

	/**
	 * 更新融通宝加息项目放款总表
	 *
	 * @Title updateIncreaseInterestLoan
	 * @param increaseInterestLoan
	 * @return
	 */
	private int updateIncreaseInterestLoan(IncreaseInterestLoan increaseInterestLoan) {
		int cnt = this.increaseInterestLoanMapper.updateByPrimaryKeySelective(increaseInterestLoan);
		return cnt;
	}

	/**
	 * 调用银行转账接口
	 *
	 * @Title transfer
	 * @param borrowNid
	 * @param investUserId
	 * @param valueOf
	 * @param repayOrderDate
	 * @param repayInterest
	 * @return
	 */
	private BankCallBean transfer(Integer investUserId, String investUserCustId, BigDecimal repayInterest,
			String repayOrderId, String merrp) {
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);// 交易代码
		bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
		bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
		bean.setAccountId(merrp);// hyjf电子账号
		bean.setTxAmount(repayInterest.toString());
		bean.setForAccountId(investUserCustId);
		bean.setDesLineFlag("1");
		bean.setDesLine(repayOrderId);// add by cwyang 用于红包发放的银行对账依据,对应accountList 表的Nid
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(investUserId));// 订单号
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
		bean.setLogUserId(String.valueOf(investUserId)); // 操作者ID
		bean.setLogRemark("产品加息收益"); // 备注
		bean.setLogClient(0);// 平台
		return BankCallUtils.callApiBg(bean);
	}

	/**
	 * 写入收支明细
	 *
	 * @param accountList
	 * @return
	 */
	private int insertAccountList(AccountList accountList) {
		// 写入收支明细
		return this.accountListMapper.insertSelective(accountList);
	}

	/**
	 * 根据借款人userId检索借款人账户信息
	 *
	 * @Title selectAccountByUserId
	 * @param borrowUserId
	 * @return
	 */
	private Account selectAccountByUserId(Integer borrowUserId) {
		AccountExample example = new AccountExample();
		AccountExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(borrowUserId);
		List<Account> listAccount = this.accountMapper.selectByExample(example);
		if (listAccount != null && listAccount.size() > 0) {
			return listAccount.get(0);
		}
		return null;
	}

	/**
	 * 根据借款编号,还款期数检索当期还款详情
	 *
	 * @Title selectIncreaseInterestRepayDetail
	 * @param borrowNid
	 * @param periodNow
	 * @return
	 */
	private IncreaseInterestRepayDetail selectIncreaseInterestRepayDetail(String borrowNid, Integer periodNow) {
		IncreaseInterestRepayDetailExample example = new IncreaseInterestRepayDetailExample();
		IncreaseInterestRepayDetailExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowNid);
		cra.andRepayPeriodEqualTo(periodNow);
		List<IncreaseInterestRepayDetail> list = this.increaseInterestRepayDetailMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}
}
