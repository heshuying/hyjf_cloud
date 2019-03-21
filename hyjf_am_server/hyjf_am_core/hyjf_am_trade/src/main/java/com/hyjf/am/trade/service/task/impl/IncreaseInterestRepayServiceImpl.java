/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.IncreaseInterestRepayService;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.message.SmsMessage;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 产品加息还款
 * @author dxj
 * @version IncreaseInterestRepayServiceImpl.java, v0.1 2018年6月19日 上午11:27:25
 */
@Service
public class IncreaseInterestRepayServiceImpl extends BaseServiceImpl implements IncreaseInterestRepayService {

	/** 用户ID */
	private static final String VAL_USERID = "userId";

	/** 性别 */
	private static final String VAL_SEX = "val_sex";

	/** 用户名 */
	private static final String VAL_NAME = "val_name";

	/** 放款金额 */
	private static final String VAL_AMOUNT = "val_amount";

	/** 预期收益 */
	private static final String VAL_PROFIT = "val_profit";

	/** 项目标题 */
	private static final String VAL_TITLE = "val_title";

	/** 任务状态:未执行 */
	private static final Integer STATUS_WAIT = 0;

	/** 任务状态:已完成 */
	private static final Integer STATUS_SUCCESS = 1;

	/** 任务状态:执行中 */
	private static final Integer STATUS_RUNNING = 2;

	/** 任务状态:错误 */
	private static final Integer STATUS_ERROR = 9;
    
	@Autowired
	private CommonProducer commonProducer;
	
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 检索未执行的还款任务
	 * 
	 * @Title selectBorrowApicronList
	 * @param statusWait
	 * @param status
	 * @return
	 */
	@Override
	public List<BorrowApicron> selectBorrowApicronList(Integer statusWait, int status) {
		BorrowApicronExample example = new BorrowApicronExample();
		BorrowApicronExample.Criteria cra = example.createCriteria();
		cra.andExtraYieldRepayStatusEqualTo(statusWait);
		cra.andApiTypeEqualTo(status);
		// 标的还款已完成
		cra.andStatusEqualTo(6);
		example.setOrderByClause(" id asc ");
		List<BorrowApicron> list = this.borrowApicronMapper.selectByExample(example);
		return list;
	}

	/**
	 * 更新还款任务
	 * 
	 * @Title updateBorrowApicron
	 * @param id
	 * @param statusError
	 * @param errorMsg
	 */
	@Override
	public int updateBorrowApicron(Integer id, Integer statusError, String errorMsg) {
		BorrowApicron record = new BorrowApicron();
		record.setId(id);
		record.setExtraYieldRepayStatus(statusError);
		if (Validator.isNotNull(errorMsg) || statusError == 1) {
			record.setData(errorMsg);
		}
		if (record.getWebStatus() == null) {
			record.setWebStatus(0);
		}
//		record.setUpdateTime(GetDate.getMyTimeInMillis());
		return this.borrowApicronMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 更新还款任务状态
	 * 
	 * @Title updateBorrowApicron
	 * @param id
	 * @param statusWait
	 */
	@Override
	public int updateBorrowApicron(Integer id, Integer statusWait) {
		return updateBorrowApicron(id, statusWait, null);
	}

	/**
	 * 根据借款编号检索还款信息
	 * 
	 * @Title selectIncreaseInterestLoanList
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
	 * 根据借款人userId检索借款人账户信息
	 * 
	 * @Title selectAccountByUserId
	 * @param borrowUserId
	 * @return
	 */
	@Override
	public Account selectAccountByUserId(Integer borrowUserId) {
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
	 * 根据借款编号,还款期数,还款方式取得还款金额
	 * 
	 * @Title selectBorrowAccountWithPeriod
	 * @param borrowNid
	 * @param borrowStyle
	 * @param periodNow
	 * @return
	 */
	@Override
	public BigDecimal selectBorrowAccountWithPeriod(String borrowNid, String borrowStyle, Integer periodNow) {

		BigDecimal account = BigDecimal.ZERO;
		// 是否分期(true:分期, false:单期)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		if (isMonth) {
			IncreaseInterestLoanDetailExample example = new IncreaseInterestLoanDetailExample();
			IncreaseInterestLoanDetailExample.Criteria cra = example.createCriteria();
			cra.andBorrowNidEqualTo(borrowNid);
			cra.andRepayStatusEqualTo(0);
			cra.andRepayPeriodEqualTo(periodNow);
			List<IncreaseInterestLoanDetail> list = this.increaseInterestLoanDetailMapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				for (IncreaseInterestLoanDetail increaseInterestLoanDetail : list) {
					account = account.add(increaseInterestLoanDetail.getLoanInterest());
				}
			}
		} else {
			IncreaseInterestLoanExample example2 = new IncreaseInterestLoanExample();
			IncreaseInterestLoanExample.Criteria cra2 = example2.createCriteria();
			cra2.andBorrowNidEqualTo(borrowNid);
			cra2.andRepayStatusEqualTo(0);
			List<IncreaseInterestLoan> list2 = this.increaseInterestLoanMapper.selectByExample(example2);
			if (list2 != null && list2.size() > 0) {
				for (IncreaseInterestLoan increaseInterestLoan : list2) {
					account = account.add(increaseInterestLoan.getLoanInterest());
				}
			}
		}
		return account;
	}

	/**
	 * 检索公司子账户可用余额
	 * 
	 * @Title selectCompanyAccount
	 * @return
	 */
	@Override
	public BigDecimal selectCompanyAccount() {
		// 账户可用余额
		BigDecimal balance = BigDecimal.ZERO;
		// 查询商户子账户余额
		String merrpAccount = systemConfig.getMerrpAccount();
		
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);// 交易代码
		bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
		bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
		bean.setAccountId(merrpAccount);// 电子账号
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(0));// 订单号
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
		bean.setLogUserId("0");
		bean.setLogClient(0);// 平台
		try {
			BankCallBean resultBean = BankCallUtils.callApiBg(bean);
			if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
				// 账户余额
				balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
			}
		} catch (Exception e) {
			logger.error("加息还款中发生系统", e);
		}
		return balance;
	}

	/**
	 * 自动还款
	 * 
	 * @Title updateBorrowRepay
	 * @param apicron
	 * @param increaseInterestLoan
	 * @param borrowUserCust
	 * @return
	 */
	@Override
	public List<Map<String, String>> updateBorrowRepay(BorrowApicron apicron,Borrow borrow, IncreaseInterestLoan increaseInterestLoan, String borrowUserCust) {
		String methodName = "updateBorrowRepay";
		logger.info("-----------融通宝加息还款开始---" + apicron.getBorrowNid() + "---------");
		List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();
		Map<String, String> msg = new HashMap<String, String>();
		retMsgList.add(msg);
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
//		Borrow borrow = selectBorrowInfo(borrowNid);
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
		// 出借订单号
		String investOrderId = increaseInterestLoan.getInvestOrderId();
		// 出借人用户ID
		Integer investUserId = increaseInterestLoan.getUserId();
		// 取得融通宝加息出借信息
		IncreaseInterestInvest increaseInterestInvest = selectIncreaseInterestInvestInfo(investOrderId);
		// 出借人在汇付的账户信息
		Account investUserCust = this.getAccount(investUserId);
		// 交易流水号
		Integer seqNo = 0;
		// 交易日期
		Integer txDate = 0;
		// 交易时间
		Integer txTime = 0;
		// 融通宝放款详情
		IncreaseInterestLoanDetail increaseInterestLoanDetail = null;
		if (investUserCust == null || StringUtils.isEmpty(investUserCust.getAccountId())) {
			throw new RuntimeException("出借人未开户。[用户ID:" + investUserId + "]，" + "[融通宝加息出借订单号:" + investOrderId + "]");
		}
		// 出借人客户号
		String investUserCustId = investUserCust.getAccountId();

		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);

		// [principal: 等额本金, month:等额本息, month:等额本息, endmonth:先息后本]
		if (isMonth) {
			// 取得分期还款计划表
			increaseInterestLoanDetail = selectIncreaseInterestLoanDetail(borrowNid, periodNow, investUserId, increaseInterestLoan.getInvestId());
			if (increaseInterestLoanDetail != null) {
				// 保存还款订单号
				if (StringUtils.isBlank(increaseInterestLoanDetail.getRepayOrderId())) {
					// 还款订单号
					repayOrderId = GetOrderIdUtils.getOrderId2(increaseInterestLoan.getUserId());
					// 还款订单日期
					repayOrderDate = GetOrderIdUtils.getOrderDate();
					// 设置还款订单号
					increaseInterestLoanDetail.setRepayOrderId(repayOrderId);
					// 设置还款时间
					increaseInterestLoanDetail.setRepayOrderDate(repayOrderDate);
					// 更新还款信息
					boolean increaseInterestLoanDetailFlag = this.updateIncreaseInterestLoanDetail(increaseInterestLoanDetail) > 0 ? true : false;
					if (!increaseInterestLoanDetailFlag) {
						throw new RuntimeException("添加还款订单号，更新hyjf_increase_interest_loan_detail表失败" + "，[出借订单号:" + investOrderId + "]");
					}
					// 更新融通宝加息项目放款总表
					increaseInterestLoan.setRepayOrderId(repayOrderId);
					increaseInterestLoan.setRepayOrderDate(repayOrderDate);
					// 更新还款信息
					boolean increaseInterestLoanFlag = this.updateIncreaseInterestLoan(increaseInterestLoan) > 0 ? true : false;
					if (!increaseInterestLoanFlag) {
						throw new RuntimeException("添加还款订单号，更新hyjf_increase_interest_loan表失败" + "，[出借订单号:" + investOrderId + "]");
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
				throw new RuntimeException("分期还款计划表数据不存在。[借款编号:" + borrowNid + "]，" + "[出借订单号:" + investOrderId + "]，" + "[期数:" + periodNow + "]");
			}
		}
		// [endday: 按天计息, end:按月计息]
		else {
			// 保存还款订单号
			if (StringUtils.isBlank(increaseInterestLoan.getRepayOrderId())) {
				// 还款订单号
				repayOrderId = GetOrderIdUtils.getOrderId2(increaseInterestLoan.getUserId());
				// 还款订单日期
				repayOrderDate = GetOrderIdUtils.getOrderDate();
				// 设置还款订单号
				increaseInterestLoan.setRepayOrderId(repayOrderId);
				// 设置还款时间
				increaseInterestLoan.setRepayOrderDate(repayOrderDate);
				// 更新还款信息
				boolean increaseInterestLoanFlag = this.updateIncreaseInterestLoan(increaseInterestLoan) > 0 ? true : false;
				if (!increaseInterestLoanFlag) {
					throw new RuntimeException("添加还款订单号，更新hyjf_increase_interest_loan表失败" + "，[出借订单号:" + investOrderId + "]");
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
			return retMsgList;
		}
		// 获取公司子账户金额
		BigDecimal account = selectCompanyAccount();

		//调用银行接口
		if (repayInterest.compareTo(BigDecimal.ZERO) > 0 && account.compareTo(repayInterest) > 0) {
			BankCallBean transferBean = transfer(investUserId, investUserCustId, repayInterest,repayOrderId);
			String respCode = transferBean == null ? "" : transferBean.getRetCode();
			// 调用接口失败时(000以外)
			if (!BankCallStatusConstant.RESPCODE_SUCCESS.equals(respCode)) {
				String message = transferBean == null ? "" : transferBean.getRetMsg();
				logger.error("融通宝自动还款调用转账接口失败。" + message + "，[出借订单号:" + investOrderId + "]");
				throw new RuntimeException("融通宝自动还款调用转账接口失败。" + respCode + ":" + message + "，[出借订单号:" + investOrderId + "]");
			}
			txDate = StringUtils.isNotBlank(transferBean.getTxDate()) ? Integer.parseInt(transferBean.getTxDate()) : 0;
			txTime = StringUtils.isNotBlank(transferBean.getTxTime()) ? Integer.parseInt(transferBean.getTxTime()) : 0;
			seqNo = StringUtils.isNotBlank(transferBean.getSeqNo()) ? Integer.parseInt(transferBean.getSeqNo()) : 0;
		} else {
			//logger.info("融通宝自动还款。" + "[还款金额:" + repayInterest + "，[公司子账户可用余额:" + account + "，[出借订单号:" + investOrderId + "]");
			logger.error("融通宝自动还款。" + "[还款金额:" + repayInterest + "，[公司子账户可用余额:" + account + "，[出借订单号:" + investOrderId + "]");
			throw  new RuntimeException("融通宝自动还款。" + "[还款金额:" + repayInterest + "，[公司子账户可用余额:" + account + "，[出借订单号:" + investOrderId + "]");
		}

		// 判断该收支明细是否存在时,跳出本次循环
		if (countAccountListByNid(repayOrderId) == 0) {
			// 更新账户信息(出借人)
			Account investUserAccount = new Account();
			investUserAccount.setUserId(investUserId);
			// 出借人可用余额
			investUserAccount.setBankBalance(repayInterest);
			// 出借人待收金额
			investUserAccount.setBankAwait(repayInterest);
			// 出借人待收收益
			investUserAccount.setBankAwaitInterest(repayInterest);
			// 江西银行可用余额
			investUserAccount.setBankBalanceCash(repayInterest);
			// 累计收益
			investUserAccount.setBankInterestSum(repayInterest);
			// 还款后更新出借人的账户信息
			boolean investAccountFlag = this.batchAccountCustomizeMapper.updateAccountAfterRepay(investUserAccount) > 0 ? true : false;
			if (investAccountFlag) {
				// 取得账户信息(出借人)
				investUserAccount = this.selectAccountByUserId(investUserId);
				if (investUserAccount != null) {
					// 写入收支明细
					AccountList accountList = new AccountList();
					accountList.setNid(repayOrderId); // 还款订单号
					accountList.setUserId(investUserId); // 出借人
					accountList.setAmount(repayInterest); // 出借总收入
					accountList.setAccountId(investUserCust.getAccountId());// 出借人客户号
					accountList.setType(1); // 1收入
					accountList.setTrade("increase_interest_repay_yes"); // 投标成功
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
					accountList.setBankInvestSum(investUserAccount.getBankInvestSum());// 银行累计出借
					accountList.setBankWaitRepay(investUserAccount.getBankWaitRepay());// 银行待还金额
					accountList.setTotal(investUserAccount.getTotal());
					accountList.setBalance(investUserAccount.getBalance());
					accountList.setFrost(investUserAccount.getFrost());
					accountList.setAwait(investUserAccount.getAwait());
					accountList.setRepay(investUserAccount.getRepay());
//					accountList.setCreateTime(nowTime); // 创建时间
//					accountList.setBaseUpdate(nowTime); // 更新时间
					accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作者
					accountList.setRemark(borrowNid);
					accountList.setIp(borrow.getAddIp()); // 操作IP
//					accountList.setIsUpdate(0);
//					accountList.setBaseUpdate(0);
//					accountList.setInterest(BigDecimal.ZERO); // 利息
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
							IncreaseInterestLoanDetail loanDetail = selectIncreaseInterestLoanDetail(borrowNid, periodNow + 1, investUserId, increaseInterestLoan.getInvestId());
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
						increaseInterestLoan.setRepayInterestYes(increaseInterestLoan.getRepayInterestYes().add(repayInterest));
						increaseInterestLoan.setRepayInterestWait(increaseInterestLoan.getRepayInterestWait().subtract(repayInterest));
						increaseInterestLoan.setWeb(2); // 写入网站收支
						boolean increaseInterestLoanFlag = this.increaseInterestLoanMapper.updateByPrimaryKeySelective(increaseInterestLoan) > 0 ? true : false;
						if (increaseInterestLoanFlag) {
							// 更新总的还款明细
							// 分期并且不是最后一期
							logger.info(investUserId+" ---------加息还款判断是否分期，标的号：" + borrowNid + ",periodNext:" + periodNext);
							if (increaseInterestLoanDetail != null && Validator.isNotNull(periodNext) && periodNext > 0) {
								increaseInterestRepay.setRepayStatus(0); // 未还款
								// 取得分期还款计划表下一期的还款
								IncreaseInterestLoanDetail loanDetail = selectIncreaseInterestLoanDetail(borrowNid, periodNow + 1, investUserId, increaseInterestLoan.getInvestId());
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
							increaseInterestRepay.setRepayInterestYes(increaseInterestRepay.getRepayInterestYes().add(repayInterest));
							increaseInterestRepay.setWeb(0);
							boolean increaseInterestRepayFlag = this.increaseInterestRepayMapper.updateByPrimaryKeySelective(increaseInterestRepay) > 0 ? true : false;
							if (increaseInterestRepayFlag) {
								increaseInterestInvest.setRepayInterestYes(increaseInterestInvest.getRepayInterestYes().add(repayInterest));
								increaseInterestInvest.setRepayInterestWait(increaseInterestInvest.getRepayInterestWait().subtract(repayInterest));
								increaseInterestInvest.setRepayTimes(increaseInterestInvest.getRepayTimes() + 1);
								// 分期时并且是最后一期
								if (increaseInterestLoanDetail != null && Validator.isNotNull(periodNext) && periodNext == 0) {
									increaseInterestInvest.setRepayActionTime(nowTime);// add by cwyang 20180730 产品加息需求新增实际还款时间
								} else {
									// 不分期
									increaseInterestInvest.setRepayActionTime(nowTime);// add by cwyang 20180730 产品加息需求新增实际还款时间

								}

								boolean increaseInterestInvestFlag = this.increaseInterestInvestMapper.updateByPrimaryKeySelective(increaseInterestInvest) > 0 ? true : false;
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
										boolean increaseInterestLoanDetailFlag = this.increaseInterestLoanDetailMapper.updateByPrimaryKeySelective(increaseInterestLoanDetail) > 0 ? true : false;
										if (increaseInterestLoanDetailFlag) {
											// 更新总的还款计划
											IncreaseInterestRepayDetail increaseInterestRepayDetail = selectIncreaseInterestRepayDetail(borrowNid, periodNow);
											if (increaseInterestRepayDetail != null) {
												increaseInterestRepayDetail.setRepayStatus(1);
												increaseInterestRepayDetail.setRepayActionTime(nowTime);
												increaseInterestRepayDetail.setRepayInterestYes(increaseInterestRepayDetail.getRepayInterestYes().add(repayInterest));
												increaseInterestRepayDetail.setRepayInterestWait(increaseInterestRepayDetail.getRepayInterestWait().subtract(repayInterest));
												increaseInterestRepayDetail.setOrderId(repayOrderId);
												increaseInterestRepayDetail.setOrderDate(repayOrderDate);
												boolean increaseInterestRepayDetailFlag = this.increaseInterestRepayDetailMapper.updateByPrimaryKeySelective(increaseInterestRepayDetail) > 0 ? true
														: false;
												if (!increaseInterestRepayDetailFlag) {
													throw new RuntimeException("融通宝加息还款详情表(hyjf_increase_interest_repay_detail)更新失败!" + "[出借订单号:" + investOrderId + "]");
												}

											} else {
												throw new RuntimeException("融通宝加息还款详情表(hyjf_increase_interest_repay_detail)查询失败!" + "[出借订单号:" + investOrderId + "]");
											}
										} else {
											throw new RuntimeException("融通宝加息出借表(hyjf_increase_interest_invest)更新失败!" + "[出借订单号:" + investOrderId + "]");
										}
									}
									// 写入网站收支
									if (repayInterest.compareTo(BigDecimal.ZERO) > 0) {
										// 插入网站收支明细记录
										AccountWebListVO accountWebList = new AccountWebListVO();
										accountWebList.setOrdid(increaseInterestInvest.getOrderId() + "_" + periodNow);// 订单号
										accountWebList.setBorrowNid(borrowNid); // 出借编号
										accountWebList.setUserId(investUserId); // 借款人
										accountWebList.setAmount(Double.valueOf(repayInterest.toString())); // 产品加息收益
										accountWebList.setType(CustomConstants.TYPE_OUT); // 类型1收入,2支出
										accountWebList.setTrade(CustomConstants.TRADE_REPAY); // 产品加息收益
										accountWebList.setTradeType(CustomConstants.TRADE_REPAY_NM); // 产品加息收益
										accountWebList.setRemark(borrowNid); // 项目编号
										accountWebList.setCreateTime(nowTime);

										//网站首支明细队列
										try {
											logger.info("发送收支明细---" + investUserId + "---------" + repayInterest);
											commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebList));
							            } catch (MQException e) {
							                logger.error("加息还款中发生系统", e);
							            }
										
										msg.put(VAL_TITLE, borrowNid);
										msg.put(VAL_USERID, String.valueOf(increaseInterestLoan.getUserId()));
										msg.put(VAL_AMOUNT, repayInterest == null ? "0.00" : repayInterest.toString());
										msg.put(VAL_PROFIT, repayInterest == null ? "0.00" : repayInterest.toString());
									}
								} else {
									throw new RuntimeException("融通宝加息出借表(hyjf_increase_interest_invest)更新失败!" + "[出借订单号:" + investOrderId + "]");
								}
							} else {
								throw new RuntimeException("总的还款明细表(hyjf_increase_interest_repay)更新失败!" + "[出借订单号:" + investOrderId + "]");
							}
						} else {
							throw new RuntimeException("融通宝加息项目放款总表(hyjf_increase_interest_loan)更新失败!" + "[出借订单号:" + investOrderId + "]");
						}
					} else {
						throw new RuntimeException("收支明细(huiyingdai_account_list)写入失败!" + "[出借订单号:" + investOrderId + "]");
					}
				} else {
					throw new RuntimeException("出借人账户信息不存在。[出借人ID:" + investUserId + "]，" + "[出借订单号:" + investOrderId + "]");
				}
			} else {
				throw new RuntimeException("出借人资金记录(huiyingdai_account)更新失败!" + "[出借订单号:" + investOrderId + "]");
			}
		}
		logger.info("-----------融通宝加息还款结束-----------" + apicron.getBorrowNid() + "-----[还款订单号:----" + repayOrderId + "]");
		return retMsgList;
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
		List<IncreaseInterestRepay> increaseInterestRepayList = this.increaseInterestRepayMapper.selectByExample(example);
		if (increaseInterestRepayList != null && increaseInterestRepayList.size() > 0) {
			return increaseInterestRepayList.get(0);
		}
		return null;
	}

	/**
	 * 根据出借订单号检索融通宝加息出借信息
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
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据标的编号,还款期数,出借用户Id,出借Id检索还款信息
	 * 
	 * @Title selectIncreaseInterestLoanDetail
	 * @param borrowNid
	 * @param periodNow
	 * @param investUserId
	 * @param investId
	 * @return
	 */
	private IncreaseInterestLoanDetail selectIncreaseInterestLoanDetail(String borrowNid, Integer periodNow, Integer investUserId, Integer investId) {
		IncreaseInterestLoanDetailExample example = new IncreaseInterestLoanDetailExample();
		IncreaseInterestLoanDetailExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowNid);
		cra.andRepayPeriodEqualTo(periodNow);
		cra.andUserIdEqualTo(investUserId);
		cra.andInvestIdEqualTo(investId);
		cra.andRepayStatusEqualTo(0);// 未转账
		List<IncreaseInterestLoanDetail> list = this.increaseInterestLoanDetailMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
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
	 * 调用银行红包发放接口
	 * @param investUserId
	 * @param investUserCustId
	 * @param repayInterest
	 * @param repayOrderId
	 * @return
	 */
	private BankCallBean transfer(Integer investUserId, String investUserCustId, BigDecimal repayInterest,String repayOrderId) {
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);// 交易代码
		bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
		bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
		bean.setAccountId(systemConfig.getMerrpAccount());// 电子账号
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

	/**
	 * 还款成功后,更新标的状态
	 *
	 * @Title updateBorrowStatus
	 * @param borrow
	 * @param periodNow
	 * @param borrowUserId
	 */
	@Override
	public void updateBorrowStatus(Borrow borrow, Integer periodNow, Integer borrowUserId) {
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 标的项目详情
//		Borrow borrow = selectBorrowInfo(borrowNid);
		// 是否月标(true:月标, false:天标)
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle());
		// 查询未债转的数据
		IncreaseInterestLoanExample example = new IncreaseInterestLoanExample();
		example.createCriteria().andBorrowNidEqualTo(borrow.getBorrowNid()).andRepayStatusEqualTo(0);
		int increaseInterestLoanCnt = this.increaseInterestLoanMapper.countByExample(example);
		int repayStatus = 0;
		if (increaseInterestLoanCnt == 0) {
			repayStatus = 1;
		}
		// 借款人还款表更新
		IncreaseInterestRepay increaseInterestRepay = new IncreaseInterestRepay();
//		increaseInterestRepay.setRepayActionTime(String.valueOf(nowTime));
		if (increaseInterestLoanCnt == 0) {
			increaseInterestRepay.setRepayStatus(repayStatus); // 已还款
			increaseInterestRepay.setRepayActionTime(nowTime); // 实际还款时间
		} else {
			increaseInterestRepay.setRepayStatus(repayStatus);// 未还款
			if (isMonth) {
				// 分期的场合，根据借款编号和还款期数从还款计划表中取得还款时间
				IncreaseInterestRepayDetailExample increaseInterestRepayDetailExample = new IncreaseInterestRepayDetailExample();
				IncreaseInterestRepayDetailExample.Criteria increaseInterestRepayDetailCriteria = increaseInterestRepayDetailExample.createCriteria();
				increaseInterestRepayDetailCriteria.andBorrowNidEqualTo(borrow.getBorrowNid());
				increaseInterestRepayDetailCriteria.andRepayPeriodEqualTo(periodNow + 1);
				List<IncreaseInterestRepayDetail> increaseInterestRepayDetails = increaseInterestRepayDetailMapper.selectByExample(increaseInterestRepayDetailExample);
				if (increaseInterestRepayDetails != null && increaseInterestRepayDetails.size() > 0) {
					IncreaseInterestRepayDetail IncreaseInterestRepayDetail = increaseInterestRepayDetails.get(0);
					// 设置下期还款时间
					increaseInterestRepay.setRepayTime(IncreaseInterestRepayDetail.getRepayTime());
				}
			}
		}
		// 更新IncreaseInterestRepay
		IncreaseInterestRepayExample increaseInterestRepayExample = new IncreaseInterestRepayExample();
		increaseInterestRepayExample.createCriteria().andBorrowNidEqualTo(borrow.getBorrowNid()).andRepayStatusEqualTo(0);
		this.increaseInterestRepayMapper.updateByExampleSelective(increaseInterestRepay, increaseInterestRepayExample);
	}

	/**
	 * 还款成功后,发送短信
	 * 
	 * @Title sendSms
	 * @param msgList
	 */
	@Override
	public void sendSms(List<Map<String, String>> msgList) {
		if (msgList != null && msgList.size() > 0) {
			for (Map<String, String> msg : msgList) {
				if (Validator.isNotNull(msg.get(VAL_USERID)) && NumberUtils.isCreatable(msg.get(VAL_USERID)) && Validator.isNotNull(msg.get(VAL_AMOUNT))) {
//					Users users = getUsersByUserId(Integer.valueOf(msg.get(VAL_USERID)));
//					if (users == null || Validator.isNull(users.getMobile()) || (users.getRecieveSms() != null && users.getRecieveSms() == 1)) {
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
					logger.info("userid=" + msg.get(VAL_USERID) + ";开始发送短信,发送金额" + msg.get(VAL_AMOUNT));
					SmsMessage smsMessage = new SmsMessage(Integer.valueOf(msg.get(VAL_USERID)), msg, null, null, MessageConstant.SMS_SEND_FOR_USER, null, CustomConstants.PARAM_TPL_JIAXIHUANKUAN,
							CustomConstants.CHANNEL_TYPE_NORMAL);

					try {
						commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, msg.get(VAL_USERID), smsMessage));
					} catch (MQException e2) {
						logger.error("发送短信失败..", e2);
					}
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
				if (Validator.isNotNull(msg.get(VAL_USERID)) && Validator.isNotNull(msg.get(VAL_AMOUNT)) && new BigDecimal(msg.get(VAL_AMOUNT)).compareTo(BigDecimal.ZERO) > 0) {
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
//						
//					}
					
					AppMsMessage smsMessage = new AppMsMessage(Integer.valueOf(msg.get(VAL_USERID)), msg, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_JIAXIHUANKUAN);
					try {
						commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, msg.get(VAL_USERID),
								smsMessage));
					} catch (MQException e) {
						logger.error("发送app消息失败..", e);
					}
					
				}
			}
		}
	}

	/**
	 * 一次性还款获得优先处理任务
	 * @param borrowNid
	 * @return
	 */
	@Override
	public BorrowApicron getRepayPeriodSort(String borrowNid) {
		BorrowApicronExample example = new BorrowApicronExample();
		example.createCriteria().andBorrowNidEqualTo(borrowNid).andApiTypeEqualTo(1).andExtraYieldRepayStatusNotEqualTo(1);
		example.setOrderByClause("period_now");
		List<BorrowApicron> apicronList = this.borrowApicronMapper.selectByExample(example);
		if (apicronList != null && apicronList.size() > 0){
			BorrowApicron borrowApicron = apicronList.get(0);
			return borrowApicron;
		}
		return null;
	}

	/**
	 * 调用还款接口
	 *
	 * @return
	 */
	@Override
	public boolean repay() {
		
		boolean result = false;


		// 取得未还款任务
		List<BorrowApicron> listApicron = selectBorrowApicronList(STATUS_WAIT, 1);
		// 还款任务不为空的情况
		if (listApicron != null && listApicron.size() > 0) {
			// 循环进行还款
			for (BorrowApicron apicron : listApicron) {
				//判断是否为一次性还款，并且排序正确
				if(1 == apicron.getIsAllrepay().intValue() && !sortRepay(apicron)){
					continue;
				}
				int errorCnt = 0;
				Long startTime = GetDate.getMillis();
				// 错误信息
				StringBuffer sbError = new StringBuffer();
				try {
					logger.info("融通宝加息自动还款任务开始。[借款编号:" + apicron.getBorrowNid() + "]");
					// 更新任务API状态为进行中
					updateBorrowApicron(apicron.getId(), STATUS_RUNNING);
					// 借款编号
					String borrowNid = apicron.getBorrowNid();
					// 借款人ID
					Integer borrowUserId = apicron.getUserId();
					// 取得还款明细列表
					List<IncreaseInterestLoan> increaseInterestLoans = selectIncreaseInterestLoanList(borrowNid);

					if (increaseInterestLoans != null && increaseInterestLoans.size() > 0) {
						// 取得借款人账户信息
						Account borrowAccount = selectAccountByUserId(borrowUserId);
						if (borrowAccount == null || StringUtils.isEmpty(borrowAccount.getAccountId())) {
							throw new Exception("借款人账户不存在。[用户ID：" + borrowUserId + "]，" + "[借款编号：" + borrowNid + "]");
						}
						// 借款人在银行的账户信息
//						BankOpenAccount borrowUserCust = getBankOpenAccount(borrowUserId);
//						if (borrowUserCust == null || StringUtils.isEmpty(borrowUserCust.getAccount())) {
//							throw new Exception("借款人未开户。[用户ID：" + borrowUserId + "]，" + "[借款编号：" + borrowNid + "]");
//						}
						// 取得借款详情
						Borrow borrow = getBorrowByNid(borrowNid);
						if (borrow == null) {
							throw new Exception("借款详情不存在。[用户ID：" + borrowUserId + "]，" + "[借款编号：" + borrowNid + "]");
						}

						// 取得还款金额
						BigDecimal repayAccount = selectBorrowAccountWithPeriod(borrowNid, borrow.getBorrowStyle(), apicron.getPeriodNow());

						// 查询公司子账户金额
						BigDecimal account = this.selectCompanyAccount();
						// 还款金额大于公司子账户可用余额
						if (repayAccount.compareTo(account) > 0) {
							logger.info("公司子账户可用金额不足。" + "[借款编号：" + borrowNid + "]，" + "[可用余额：" + account + "]，" + "[还款金额：" + repayAccount + "]");
							throw new Exception("公司子账户可用金额不足。" + "[借款编号：" + borrowNid + "]，" + "[可用余额：" + account + "]，" + "[还款金额：" + repayAccount + "]");
						}

						IncreaseInterestLoan increaseInterestLoan = null;
						// 循环还款列表
						for (int i = 0; i < increaseInterestLoans.size(); i++) {
							increaseInterestLoan = increaseInterestLoans.get(i);
							try {
								// 自动还款
								List<Map<String, String>> msgList = ((IncreaseInterestRepayService)AopContext.currentProxy()).updateBorrowRepay(apicron,borrow, increaseInterestLoan, borrowAccount.getAccountId());
								// 发送短信
								sendSms(msgList);
								// 推送消息
								sendMessage(msgList);
							} catch (Exception e) {
								errorCnt++;
								sbError.append(errorCnt).append(".").append(e.getMessage()).append("<br/>");
								logger.error(e.getMessage());
							}
						}

						// 还款有错误时
						if (errorCnt > 0) {
							throw new Exception("融通宝加息还款时发生错误。" + "[借款编号：" + borrowNid + "]，" + "[错误件数：" + errorCnt + "]");
						} else {
							// 更新最后还款状态
							this.updateBorrowStatus(borrow, apicron.getPeriodNow(), borrowUserId);
							// 发送成功短信
							Map<String, String> replaceStrs = new HashMap<String, String>();
							replaceStrs.put("val_title", borrow.getBorrowNid());
							replaceStrs.put("val_time", GetDate.formatTime());
							SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_HUANKUAN_SUCCESS,
									CustomConstants.CHANNEL_TYPE_NORMAL);
							try {
								commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, borrowNid, smsMessage));
							} catch (MQException e2) {
								logger.error("发送短信失败..", e2);
							}
						}
					} else {
						logger.info("还款明细件数为0件。[标号：" + borrowNid + "]");
					}
					// 更新任务API状态为完成
					this.updateBorrowApicron(apicron.getId(), STATUS_SUCCESS);
				} catch (Exception e) {
					logger.error("加息还款中发生系统", e);
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
//					logger.error(THIS_CLASS, methodName, e);
//					if (runCnt >= 3) {
//						// 清除重新还款任务
//						runTimes.remove(apicron.getBorrowNid());
//						if (sbError.length() == 0) {
//							sbError.append(e.getMessage());
//						}
//						// 更新任务API状态为错误
//						updateBorrowApicron(apicron.getId(), STATUS_ERROR, sbError.toString());
//						// 发送失败短信
//						Borrow borrow = selectBorrowInfo(apicron.getBorrowNid());
//						// 是否分期(true:分期, false:单期)
//						boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())
//								|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle());
//						Map<String, String> replaceStrs = new HashMap<String, String>();
//						replaceStrs.put("val_title", borrow.getBorrowNid());
//						replaceStrs.put("val_period", isMonth ? "第" + apicron.getPeriodNow() + "期" : "");
//						replaceStrs.put("val_package_error", String.valueOf(errorCnt));
//						SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMSSENDFORMANAGER, null, CustomConstants.PARAM_TPL_HUANKUAN_FAILD,
//								CustomConstants.CHANNEL_TYPE_NORMAL);
//						smsProcesser.gather(smsMessage);
//					} else {
//						// 更新任务API状态为重新执行
//						updateBorrowApicron(apicron.getId(), STATUS_WAIT);
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
					msg.append("当前期数：").append(apicron.getPeriodNow()).append("<br/>");
					msg.append("还款时间：").append(GetDate.formatTime()).append("<br/>");
//					msg.append("执行次数：").append("第" + runCnt + "次").append("<br/>");
					msg.append("错误信息：").append(e.getMessage()).append("<br/>");
					msg.append("详细错误信息：<br/>").append(sbError.toString());
//					String[] toMail = new String[] {};
					
					MailMessage message = new MailMessage(null, null, "[" + online + "] " + apicron.getBorrowNid() + "-" + apicron.getPeriodNow() + "融通宝加息还款失败",
							msg.toString(), null, toMail, null, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
					try {
						commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, apicron.getBorrowNid(), message));
					} catch (Exception e2) {
						logger.error("发送邮件失败..", e2);
					}
					
					logger.info("融通宝加息自动还款任务发生错误。[订单号：" + apicron.getBorrowNid() + "]");
				} finally {
					Long endTime = GetDate.getMillis();
					logger.info("融通宝加息自动还款任务结束。[订单号：" + apicron.getBorrowNid() + "]， 耗时：" + (endTime - startTime) / 1000 + "s");
				}

			}
		}
	
		result = true;
		return result;
	}

	/**
	 * 对需要做还款处理的还款任务进行排序，按还款期数逐条执行
	 * add by cwyang 2018-0810
	 * @param borrowApicron
	 * @return
	 */
	private boolean sortRepay(BorrowApicron borrowApicron) {

		String borrowNid = borrowApicron.getBorrowNid();
		Integer periodNow = borrowApicron.getPeriodNow();
		BorrowApicron apicron = getRepayPeriodSort(borrowNid);
		if (apicron != null){
			Integer period = apicron.getPeriodNow();
			if (periodNow == period){
				return true;
			}
		}
		return false;
	}

}
