/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version AccountMobileSynchRequest, v0.1 2018/6/22 18:39
 */
@ApiModel(value = "银行卡号手机号同步表model")
public class AccountMobileSynchRequest extends BasePage implements Serializable {
    @ApiModelProperty(value = "银行卡号手机号同步表查询请求参数")
    private AccountMobileSynchVO accountMobileSynchVO;

    private BankCardRequest bankCardRequest;

    private int updateFlag;

    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

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

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
