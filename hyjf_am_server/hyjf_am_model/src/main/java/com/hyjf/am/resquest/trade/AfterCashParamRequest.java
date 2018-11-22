/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.BankCardVO;

import java.io.Serializable;

/**
 * @author jun
 * @version WithdrawHandlerAfterCashParamRequest, v0.1 2018/11/21 17:51
 */
public class AfterCashParamRequest implements Serializable {

    private BankCallBeanVO bankCallBeanVO;

    private AccountWithdrawVO accountWithdrawVO;

    private BankCardVO bankCardVO;

    private String withdrawFee;

    public BankCallBeanVO getBankCallBeanVO() {
        return bankCallBeanVO;
    }

    public void setBankCallBeanVO(BankCallBeanVO bankCallBeanVO) {
        this.bankCallBeanVO = bankCallBeanVO;
    }

    public void setAccountWithdrawVO(AccountWithdrawVO accountWithdrawVO) {
        this.accountWithdrawVO = accountWithdrawVO;
    }

    public AccountWithdrawVO getAccountWithdrawVO() {
        return accountWithdrawVO;
    }

    public void setBankCardVO(BankCardVO bankCardVO) {
        this.bankCardVO = bankCardVO;
    }

    public BankCardVO getBankCardVO() {
        return bankCardVO;
    }

    public void setWithdrawFee(String withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public String getWithdrawFee() {
        return withdrawFee;
    }
}
