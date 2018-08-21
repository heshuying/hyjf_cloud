package com.hyjf.am.trade.dao.model.customize;

public class TenderCreditAssignedStatisticCustomize {
    /**
     * 投资本金总额
     */
    private String assignCapital;
    /**
     * 垫付利息总额
     */
    private String assignInterestAdvance;
    /**
     * 服务费总额
     */
    private String creditFee;
    /**
     * 债转金额
     */
    private String creditCapital;
    /**
     * 实际到账金额总计
     */
    private String moneyGet;
    /**
     * 原标nid
     */
    private String bidNid;
    /**
     * 转让时间
     */
    private String createTime;

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public String getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(String creditFee) {
        this.creditFee = creditFee;
    }

    public String getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(String creditCapital) {
        this.creditCapital = creditCapital;
    }

    public String getMoneyGet() {
        return moneyGet;
    }

    public void setMoneyGet(String moneyGet) {
        this.moneyGet = moneyGet;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
