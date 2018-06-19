package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.bean.CheckResult;
import com.hyjf.cs.trade.client.BankWithdrawExceptionClient;
import com.hyjf.cs.trade.service.BankWithdrawExceptionService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 江西银行提现掉单异常处理Service实现类
 * @author jijun 20180614
 *
 */
@Service
public class BankWithdrawExceptionServiceImpl extends BaseServiceImpl  implements BankWithdrawExceptionService {
	Logger logger = LoggerFactory.getLogger(BankWithdrawExceptionServiceImpl.class);

	@Autowired
	private BankWithdrawExceptionClient bankWithdrawExceptionClient;//银行提现掉单

	// 提现状态:提现中
	private static final int WITHDRAW_STATUS_DEFAULT = 0;
	// 提现状态:提现中
	private static final int WITHDRAW_STATUS_WAIT = 1;
	// 提现状态:失败
	private static final int WITHDRAW_STATUS_FAIL = 3;
	// 提现状态:成功
	private static final int WITHDRAW_STATUS_SUCCESS = 2;
	@Value("${hyjf.bank.fee}")
	private String FEETMP;


	@Override
	public void withdraw(){
		List<AccountWithdrawVO> withdrawList = this.bankWithdrawExceptionClient.selectBankWithdrawList();
		if (CollectionUtils.isNotEmpty(withdrawList)){
			for (AccountWithdrawVO accountWithdraw : withdrawList) {
				this.updateWithdraw(accountWithdraw);
			}
		}
	}


	/**
	 * 更新处理中的提现记录
	 * @param accountWithdraw
	 */
	private void updateWithdraw(AccountWithdrawVO accountWithdraw) {
		try {
			//调用银行接口
			BankCallBeanVO bean = bankWithdrawExceptionClient.bankCallFundTransQuery(accountWithdraw);
			if (bean != null) {
				//调用后平台操作
				this.handlerAfterCash(bean, accountWithdraw);
			}else{
				throw new Exception("调用银行接口,银行返回NULL,充值订单号:"
						+ accountWithdraw.getNid()
						+ ",用户Id:" + accountWithdraw.getUserId());
			}
		} catch (Exception e) {
			logger.info("更新处理中的提现记录出錯...");
		}
	}


	/**
	 * 银行接口返回与平台记录匹配验证
	 * @param bean
	 * @param accountWithdraw
	 * @return
	 */
	private CheckResult checkCallRetAndHyjf(BankCallBeanVO bean, AccountWithdrawVO accountWithdraw) {
		CheckResult result = new CheckResult();

		Boolean resultBool = true;
		String resultMsg = null;

		//匹配验证
		//提现金额
		BigDecimal txAmount = new BigDecimal(bean.getTxAmount());
		if (!txAmount.equals(accountWithdraw.getCredited())) {
			resultBool = false;
			resultMsg = "本地记录的提现金额与银行返回的交易金额不一致:本地记录的提现金额:" + accountWithdraw.getTotal() + ",银行返回的充值金额:" + txAmount;
		}

		//匹配结果
		result.setResultBool(resultBool);
		result.setResultMsg(resultMsg);
		return result;
	}


	/**
	 *
	 * @param accountWithdraw
	 * @param userId
	 * @param ordId
	 * @return 已被更新false，未更新true
	 */
	private CheckResult checkConcurrencyDB(AccountWithdrawVO accountWithdraw, int userId, String ordId) {
		CheckResult result = new CheckResult();

		Boolean resultBool = true;
		String resultMsg = null;
		String msg = "此笔充提现状态已发生变化,提现订单号:" + ordId + ",用户Id:" + userId;

		//匹配验证
		// 如果信息已被处理
		if (accountWithdraw.getStatus() == WITHDRAW_STATUS_SUCCESS) {
			resultBool = false;
			resultMsg = msg;
		}
		// 查询是否已经处理过
		int accountlistCnt = this.bankWithdrawExceptionClient.getAccountlistCntByOrdId(ordId,"cash_success");
		// 如果信息已被处理
		if (accountlistCnt != 0) {
			resultBool = false;
			resultMsg = msg;
		}

		//匹配结果
		result.setResultBool(resultBool);
		result.setResultMsg(resultMsg);
		return result;
	}



	/**
	 * 用户提现回调方法
	 * @param bean
	 * @param accountWithdraw
	 * @throws Exception
	 */
	private void handlerAfterCash(BankCallBeanVO bean, AccountWithdrawVO accountWithdraw) throws Exception {

		// 当前时间
		int nowTime = GetDate.getNowTime10();
		// 用户ID
		int userId = accountWithdraw.getUserId();
		// 提现订单号
		String ordId = accountWithdraw.getNid();
		// 根据用户ID查询用户银行卡信息
		String bankId=bean.getBankCode();
		BankCardVO bankCard = this.bankWithdrawExceptionClient.selectBankCardByUserId(userId);

		// 1.该银行接口的 业务是否成功
		// 返回值=000成功 ,大额提现返回值为 CE999028
		// 并且Result = "00"
		// 冲正撤销标志为0
		// 返回retcode的错误码和result返回其他这两个都是有可能的，具体的是哪个和银行内部的操作有关，所以retcode和result这个你们都需要判断下
		// 其它:无该交易或者处理失败
		if ((BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode()) || "CE999028".equals(bean.getRetCode()))
				&& "00".equals(bean.getResult())
				&& !("1".equals(bean.getOrFlag()))) {

			//2.银行接口返回与本地记录匹配验证
			CheckResult rtCheck = this.checkCallRetAndHyjf(bean,accountWithdraw);
			if (!rtCheck.isResultBool()) {
				// 验证失败，异常信息抛出
				throw new Exception(rtCheck.getResultMsg());
			}

			//3.DB防并发处理
			rtCheck = this.checkConcurrencyDB(accountWithdraw, userId, ordId);;
			if (!rtCheck.isResultBool()) {
				// 记录被其他进程处理，日志信息输出
				logger.info(this.getClass().getName(), "handlerAfterCash",
						rtCheck.getResultMsg());
				return;
			}


			//4.DB更新操作
			// 提现金额
			BigDecimal transAmt = new BigDecimal(bean.getTxAmount());
			
			String fee = this.getWithdrawFee(userId,bankCard == null ? "" : String.valueOf(bankCard.getBankId()),transAmt);
			// 提现手续费
			BigDecimal feeAmt = new BigDecimal(fee);
			// 总的交易金额
			BigDecimal total = transAmt.add(feeAmt);
			// 更新订单信息
			accountWithdraw.setFee((CustomUtil.formatAmount(feeAmt.toString()))); // 更新手续费
			accountWithdraw.setCredited(transAmt); // 更新到账金额
			accountWithdraw.setTotal(total); // 更新到总额
			accountWithdraw.setStatus(WITHDRAW_STATUS_SUCCESS);// 4:成功
			accountWithdraw.setUpdateTime(nowTime);
			accountWithdraw.setAccount(bean.getAccountId());
			accountWithdraw.setReason("");

			boolean isAccountwithdrawFlag=this.bankWithdrawExceptionClient.updateAccountwithdraw(accountWithdraw);
			if (!isAccountwithdrawFlag) {
				throw new Exception("提现后,更新用户提现记录表失败!" + "提现订单号:" + ordId + ",用户ID:" + userId);
			}
			AccountVO newAccount = new AccountVO();
			// 更新账户信息
			newAccount.setUserId(userId);// 用户Id
			newAccount.setBankTotal(total); // 累加到账户总资产
			newAccount.setBankBalance(total); // 累加可用余额
			newAccount.setBankBalanceCash(total);// 江西银行可用余额
			
			//更新银行提现
			boolean isAccountUpdateFlag = this.bankWithdrawExceptionClient.updateBankWithdraw(newAccount);
			if (!isAccountUpdateFlag) {
				throw new Exception("提现后,更新用户Account表失败!" + "提现订单号:" + ordId + ",用户ID:" + userId);
			}
			// 重新获取用户信息
			AccountVO account = this.bankWithdrawExceptionClient.getAccount(userId);
			// 写入收支明细
			AccountListVO accountList = new AccountListVO();
			accountList.setNid(ordId);
			accountList.setUserId(userId);
			accountList.setAmount(total);
			accountList.setType(2);
			accountList.setTrade("cash_success");
			accountList.setTradeCode("balance");
			accountList.setTotal(account.getTotal());
			accountList.setBalance(account.getBalance());
			accountList.setFrost(account.getFrost());
			accountList.setAwait(account.getAwait());
			accountList.setRepay(account.getRepay());
			accountList.setBankTotal(account.getBankTotal()); // 银行总资产
			accountList.setBankBalance(account.getBankBalance()); // 银行可用余额
			accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
			accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
			accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
			accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
			accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
			accountList.setBankAwait(account.getBankAwait());// 银行待收总额
			accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
			accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计投资
			accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
			accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
			accountList.setPlanFrost(account.getPlanFrost());
			accountList.setSeqNo(bean.getSeqNo());
			accountList.setTxDate(Integer.parseInt(bean.getTxDate()));
			accountList.setTxTime(Integer.parseInt(bean.getTxTime()));
			accountList.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
			accountList.setAccountId(bean.getAccountId());
			accountList.setRemark(accountWithdraw.getRemark());//TODO
			accountList.setCreateTime(nowTime);
			accountList.setBaseUpdate(nowTime);
			accountList.setOperator(String.valueOf(userId));
			accountList.setIp(accountWithdraw.getAddip());//TODO
			accountList.setIsUpdate(0);
			accountList.setBaseUpdate(0);
			accountList.setInterest(null);
			accountList.setIsBank(1);
			accountList.setWeb(0);
			accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
			accountList.setTradeStatus(1);// 0失败1成功2失败
			
			boolean isAccountListFlag = this.bankWithdrawExceptionClient.addAccountList(accountList);
			if (!isAccountListFlag) {
				throw new Exception("提现成功后,插入交易明细表失败~!" + "提现订单号:" + ordId + ",用户ID:" + userId);
			}
		} else {
			// 提现失败,更新处理中订单状态为失败
			JSONObject paraMap = new JSONObject();
			paraMap.put("ordId",ordId);
			paraMap.put("accountWithdraw",accountWithdraw);
			paraMap.put("bankCallBeanVO",bean);
			this.bankWithdrawExceptionClient.selectAndUpdateAccountWithdraw(paraMap);
		}


	}


	private String getWithdrawFee(Integer userId, String bankId, BigDecimal amount) {
		BankCardVO bankCard = this.bankWithdrawExceptionClient.getBankInfo(userId, bankId);
		if (FEETMP == null) {
			FEETMP = "1";
		}
		if (bankCard != null) {
			String bankCode = bankCard.getBank();
			// 取得费率
			List<FeeConfigVO> listFeeConfig = this.bankWithdrawExceptionClient.getFeeConfig(bankCode);
			
			if (listFeeConfig != null && listFeeConfig.size() > 0) {
				FeeConfigVO feeConfig = listFeeConfig.get(0);
				BigDecimal takout = BigDecimal.ZERO;
				BigDecimal percent = BigDecimal.ZERO;
				if (Validator.isNotNull(feeConfig.getNormalTakeout()) && NumberUtils.isNumber(feeConfig.getNormalTakeout())) {
					takout = new BigDecimal(feeConfig.getNormalTakeout());
				}
				return takout.add(percent).toString();
			} else {
				return FEETMP;
			}
		} else {
			return FEETMP;
		}
	}
	

}
