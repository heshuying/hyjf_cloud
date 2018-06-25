/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.user.AccountMobileSynchVO;

import java.io.Serializable;

/**
 * @author wangjun
 * @version AccountMobileSynchRequest, v0.1 2018/6/22 18:39
 */
public class AccountMobileSynchRequest implements Serializable {
    private AccountMobileSynchVO accountMobileSynchVO;

    private BankCardRequest bankCardRequest;

    private int updateFlag;

    public AccountMobileSynchVO getAccountMobileSynchVO() {
        return accountMobileSynchVO;
    }

    public void setAccountMobileSynchVO(AccountMobileSynchVO accountMobileSynchVO) {
        this.accountMobileSynchVO = accountMobileSynchVO;
    }

    public BankCardRequest getBankCardRequest() {
        return bankCardRequest;
    }

    public void setBankCardRequest(BankCardRequest bankCardRequest) {
        this.bankCardRequest = bankCardRequest;
    }

    public int getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(int updateFlag) {
        this.updateFlag = updateFlag;
    }
}
