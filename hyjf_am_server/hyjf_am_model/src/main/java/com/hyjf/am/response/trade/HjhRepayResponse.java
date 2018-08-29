/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

import java.math.BigDecimal;

/**
 * @author PC-LIUSHOUYI
 * @version HjhRepayResponseOld, v0.1 2018/6/26 14:13
 */
public class HjhRepayResponse extends Response<HjhRepayVO> {

    private int count;

    private BigDecimal sumAccedeAccount;

    private BigDecimal sumRepayInterest;

    private BigDecimal sumActualRevenue;

    private BigDecimal sumActualPayTotal;

    private BigDecimal sumLqdServiceFee;

    private HjhRepayVO sumHjhRepayVO;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getSumAccedeAccount() {
        return sumAccedeAccount;
    }

    public void setSumAccedeAccount(BigDecimal sumAccedeAccount) {
        this.sumAccedeAccount = sumAccedeAccount;
    }

    public BigDecimal getSumRepayInterest() {
        return sumRepayInterest;
    }

    public void setSumRepayInterest(BigDecimal sumRepayInterest) {
        this.sumRepayInterest = sumRepayInterest;
    }

    public BigDecimal getSumActualRevenue() {
        return sumActualRevenue;
    }

    public void setSumActualRevenue(BigDecimal sumActualRevenue) {
        this.sumActualRevenue = sumActualRevenue;
    }

    public BigDecimal getSumActualPayTotal() {
        return sumActualPayTotal;
    }

    public void setSumActualPayTotal(BigDecimal sumActualPayTotal) {
        this.sumActualPayTotal = sumActualPayTotal;
    }

    public BigDecimal getSumLqdServiceFee() {
        return sumLqdServiceFee;
    }

    public void setSumLqdServiceFee(BigDecimal sumLqdServiceFee) {
        this.sumLqdServiceFee = sumLqdServiceFee;
    }

    public HjhRepayVO getSumHjhRepayVO() {
        return sumHjhRepayVO;
    }

    public void setSumHjhRepayVO(HjhRepayVO sumHjhRepayVO) {
        this.sumHjhRepayVO = sumHjhRepayVO;
    }
}
