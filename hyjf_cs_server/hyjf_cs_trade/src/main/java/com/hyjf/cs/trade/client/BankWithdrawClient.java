package com.hyjf.cs.trade.client;


import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.BankCardVO;

import java.util.List;

/**
 * 江西银行提现掉单异常处理Client
 * create by jijun 20180614
 */
public interface BankWithdrawClient {


    List<AccountWithdrawVO> selectBankWithdrawList();

    BankCallBeanVO bankCallFundTransQuery(AccountWithdrawVO accountWithdraw);

    BankCardVO selectBankCardByUserId(int userId);

    int getAccountlistCntByOrdId(String ordId, String cash_success);

	AccountVO getAccount(int userId);

	List<FeeConfigVO> getFeeConfig(String bankCode);

	BankCardVO getBankInfo(Integer userId, String bankId);

	Boolean handlerAfterCash(BankCallBeanVO bean, AccountWithdrawVO accountwithdraw, BankCardVO bankCard,String withdrawFee);
}
