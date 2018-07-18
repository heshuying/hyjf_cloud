package com.hyjf.cs.trade.client;

import java.util.List;

import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;

/**
 * 配置中心请求
 */
public interface AmConfigClient {
	/**
	 * 获取银行返回码
	 * @param retCode
	 * @return
	 */
    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);
	/**
	 * 调用银行接口
	 * @param accountwithdraw
	 * @return
	 */
	BankCallBeanVO bankCallFundTransQuery(AccountWithdrawVO accountwithdraw);
	
	/**
	 * 提现费率
	 * @param bankCode
	 * @return
	 */
	List<FeeConfigVO> getFeeConfig(String bankCode);
}
