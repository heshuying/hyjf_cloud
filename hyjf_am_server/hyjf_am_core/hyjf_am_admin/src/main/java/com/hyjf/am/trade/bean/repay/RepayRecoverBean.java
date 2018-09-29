package com.hyjf.am.trade.bean.repay;

import com.hyjf.am.trade.dao.model.auto.BorrowRecover;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RepayRecoverBean extends BorrowRecover implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -2372632484178256288L;

    private BigDecimal recoverTotal;

    private BigDecimal recoverCapitalOld;

    private BigDecimal creditAmountOld;

    /**
     * 债转还款
     */
    private List<RepayCreditRepayBean> creditRepayList = new ArrayList<RepayCreditRepayBean>();

    /**
     * 汇计划债转还款
     */
    private List<HjhDebtCreditRepayBean> hjhCreditRepayList = new ArrayList<HjhDebtCreditRepayBean>();

    public BigDecimal getRecoverTotal() {
        return recoverTotal;
    }

    public void setRecoverTotal(BigDecimal recoverTotal) {
        this.recoverTotal = recoverTotal;
    }

    public List<RepayCreditRepayBean> getCreditRepayList() {
        return creditRepayList;
    }

    public void setCreditRepayList(List<RepayCreditRepayBean> creditRepayList) {
        this.creditRepayList = creditRepayList;
    }

    public List<HjhDebtCreditRepayBean> getHjhCreditRepayList() {
        return hjhCreditRepayList;
    }

    public void setHjhCreditRepayList(List<HjhDebtCreditRepayBean> hjhCreditRepayList) {
        this.hjhCreditRepayList = hjhCreditRepayList;
    }

    public BigDecimal getRecoverCapitalOld() {
        return recoverCapitalOld;
    }

    public void setRecoverCapitalOld(BigDecimal recoverCapitalOld) {
        this.recoverCapitalOld = recoverCapitalOld;
    }

    public BigDecimal getCreditAmountOld() {
        return creditAmountOld;
    }

    public void setCreditAmountOld(BigDecimal creditAmountOld) {
        this.creditAmountOld = creditAmountOld;
    }
}
