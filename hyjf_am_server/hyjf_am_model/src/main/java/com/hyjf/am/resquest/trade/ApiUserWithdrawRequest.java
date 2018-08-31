/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.user.BankCardVO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: ApiUserWithdrawRequest, v0.1 2018/8/30 15:19
 */
public class ApiUserWithdrawRequest {
    private Integer userId;
    private Integer status;
    private Integer limitStart = -1;
    private Integer limitEnd = -1;
    private String errorMsg;
    private BankCallBeanVO bankCallBeanVO;
    private Map<String,String> params = new HashMap<String, String>();
    private BankCardVO bankCardVO;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BankCallBeanVO getBankCallBeanVO() {
        return bankCallBeanVO;
    }

    public void setBankCallBeanVO(BankCallBeanVO bankCallBeanVO) {
        this.bankCallBeanVO = bankCallBeanVO;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public BankCardVO getBankCardVO() {
        return bankCardVO;
    }

    public void setBankCardVO(BankCardVO bankCardVO) {
        this.bankCardVO = bankCardVO;
    }
}
