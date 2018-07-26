/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.bank.BankCallBeanVO;

/**
 * @author liubin
 * @version UpdateCreditForAutoTenderRequest, v0.1 2018/7/5 18:12
 */
public class UpdateBorrowForAutoTenderRequest {
    private String borrowNid;
    private String accedeOrderId;
    private BankCallBeanVO bankCallBeanVO;

    public UpdateBorrowForAutoTenderRequest() {
    }

    public UpdateBorrowForAutoTenderRequest(String borrowNid, String accedeOrderId, BankCallBeanVO bankCallBeanVO) {
        this.borrowNid = borrowNid;
        this.accedeOrderId = accedeOrderId;
        this.bankCallBeanVO = bankCallBeanVO;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public BankCallBeanVO getBankCallBeanVO() {
        return bankCallBeanVO;
    }

    public void setBankCallBeanVO(BankCallBeanVO bankCallBeanVO) {
        this.bankCallBeanVO = bankCallBeanVO;
    }
}
