package com.hyjf.am.trade.dao.model.customize.admin;

import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepayPlanBean, v0.1 2018/7/6 11:23
 */
public class BorrowRepayPlanBean extends BorrowRepayPlan implements Serializable {
    /** 用户还款详情 */
    private List<BorrowRecoverPlan> recoverPlanList;

    private String repayTimeStr;

    private String borrowStatus;

    private String ip;

    public List<BorrowRecoverPlan> getRecoverPlanList() {
        return recoverPlanList;
    }

    public void setRecoverPlanList(List<BorrowRecoverPlan> recoverPlanList) {
        this.recoverPlanList = recoverPlanList;
    }

    public String getRepayTimeStr() {
        return repayTimeStr;
    }

    public void setRepayTimeStr(String repayTimeStr) {
        this.repayTimeStr = repayTimeStr;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
