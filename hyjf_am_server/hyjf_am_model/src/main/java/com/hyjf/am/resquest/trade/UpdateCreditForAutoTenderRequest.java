/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;

/**
 * @author liubin
 * @version UpdateCreditForAutoTenderRequest, v0.1 2018/7/5 18:12
 */
public class UpdateCreditForAutoTenderRequest {
    private String creditNid;
    private String accedeOrderId;
    private String planNid;
    private BankCallBeanVO bankCallBeanVO;
    private String tenderUsrcustid;
    private String sellerUsrcustid;
    private HjhCreditCalcResultVO resultVO;

    public UpdateCreditForAutoTenderRequest() {
    }

    public UpdateCreditForAutoTenderRequest(String creditNid, String accedeOrderId, String planNid, BankCallBeanVO bankCallBeanVO, String tenderUsrcustid, String sellerUsrcustid, HjhCreditCalcResultVO resultVO) {
        this.creditNid = creditNid;
        this.accedeOrderId = accedeOrderId;
        this.planNid = planNid;
        this.bankCallBeanVO = bankCallBeanVO;
        this.tenderUsrcustid = tenderUsrcustid;
        this.sellerUsrcustid = sellerUsrcustid;
        this.resultVO = resultVO;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public BankCallBeanVO getBankCallBeanVO() {
        return bankCallBeanVO;
    }

    public void setBankCallBeanVO(BankCallBeanVO bankCallBeanVO) {
        this.bankCallBeanVO = bankCallBeanVO;
    }

    public String getTenderUsrcustid() {
        return tenderUsrcustid;
    }

    public void setTenderUsrcustid(String tenderUsrcustid) {
        this.tenderUsrcustid = tenderUsrcustid;
    }

    public String getSellerUsrcustid() {
        return sellerUsrcustid;
    }

    public void setSellerUsrcustid(String sellerUsrcustid) {
        this.sellerUsrcustid = sellerUsrcustid;
    }

    public HjhCreditCalcResultVO getResultVO() {
        return resultVO;
    }

    public void setResultVO(HjhCreditCalcResultVO resultVO) {
        this.resultVO = resultVO;
    }
}
