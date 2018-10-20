package com.hyjf.admin.beans.repaybean;

import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RepayRecoverBean extends BorrowRecoverVO implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -2372632484178256288L;

    private BigDecimal recoverTotal;

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
}
