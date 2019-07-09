package com.hyjf.am.vo.trade.repay;

/**
 * 还款计划列表VO
 * @author hesy
 * @version RepayPlanListVO, v0.1 2018/6/27 15:46
 */
public class RepayPlanListVO {
    /** 借款编号*/
    private String borrowNid;
    /** 预计还款时间*/
    private String repayTime;
    /** 待还本金*/
    private String repayCapital;
    /** 待还利息*/
    private String repayInterest;
    /** 待还服务费*/
    private String repayFee;
    /** 已还本息*/
    private String repayAccountYes;
    /** 实际还款时间*/
    private String repayYestime;
    /** 当前期次*/
    private String period;
    /** 还款状态*/
    private String repayStatus;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getRepayCapital() {
        return repayCapital;
    }

    public void setRepayCapital(String repayCapital) {
        this.repayCapital = repayCapital;
    }

    public String getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(String repayInterest) {
        this.repayInterest = repayInterest;
    }

    public String getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(String repayFee) {
        this.repayFee = repayFee;
    }

    public String getRepayAccountYes() {
        return repayAccountYes;
    }

    public void setRepayAccountYes(String repayAccountYes) {
        this.repayAccountYes = repayAccountYes;
    }

    public String getRepayYestime() {
        return repayYestime;
    }

    public void setRepayYestime(String repayYestime) {
        this.repayYestime = repayYestime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }
}
