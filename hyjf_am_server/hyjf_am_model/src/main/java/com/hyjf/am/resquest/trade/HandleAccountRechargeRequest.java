/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.bank.BankCallBeanVO;

import java.util.Map;

/**
 * @author: sunpeikai
 * @version: HandleAccountRechargeRequest, v0.1 2018/8/28 16:56
 */
public class HandleAccountRechargeRequest {
    // 银行返回参数
    private BankCallBeanVO bankCallBeanVO;
    // 银行返回错误代码对应的错误信息
    private String errorMsg;
    private String ip;

    public BankCallBeanVO getBankCallBeanVO() {
        return bankCallBeanVO;
    }

    public void setBankCallBeanVO(BankCallBeanVO bankCallBeanVO) {
        this.bankCallBeanVO = bankCallBeanVO;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
