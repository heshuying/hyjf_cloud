package com.hyjf.am.trade.dao.model.customize.trade;

public class TenderCreditRepayPlanCustomize {
    /* 用户名称 */
    private String userId;
    /* 出让人id */
    private String creditUserId;
    /* 状态 */
    private String status;
    /* 原标标号 */
    private String bidNid;
    /* 债转标号 */
    private String creditNid;
    /* 债转投标单号 */
    private String creditTenderNid;
    /* 认购单号 */
    private String assignNid;
    /* 投资本金 */
    private String assignCapital;
    /* 回收总额 */
    private String assignAccount;
    /* 债转利息 */
    private String assignInterest;
    /* 垫付利息 */
    private String assignInterestAdvance;
    /* 下次还款时间 */
    private String assignRepayNextTime;
    /* 最终实际还款时间 */
    private String assignRepayYesTime;
    /* 还款期数 */
    private String assignRepayPeriod;
    /* 原标还款期数*/
    private String recoverPeriod;
    /* 认购日期 */
    private String assignCreateDate;
    /* 添加时间 */
    private String addTime;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getCreditUserId() {
        return creditUserId;
    }
    public void setCreditUserId(String creditUserId) {
        this.creditUserId = creditUserId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getBidNid() {
        return bidNid;
    }
    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }
    public String getCreditNid() {
        return creditNid;
    }
    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }
    public String getCreditTenderNid() {
        return creditTenderNid;
    }
    public void setCreditTenderNid(String creditTenderNid) {
        this.creditTenderNid = creditTenderNid;
    }
    public String getAssignNid() {
        return assignNid;
    }
    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }
    public String getAssignCapital() {
        return assignCapital;
    }
    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }
    public String getAssignAccount() {
        return assignAccount;
    }
    public void setAssignAccount(String assignAccount) {
        this.assignAccount = assignAccount;
    }
    public String getAssignInterest() {
        return assignInterest;
    }
    public void setAssignInterest(String assignInterest) {
        this.assignInterest = assignInterest;
    }
    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }
    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }
    public String getAssignRepayNextTime() {
        return assignRepayNextTime;
    }
    public void setAssignRepayNextTime(String assignRepayNextTime) {
        this.assignRepayNextTime = assignRepayNextTime;
    }
    public String getAssignRepayYesTime() {
        return assignRepayYesTime;
    }
    public void setAssignRepayYesTime(String assignRepayYesTime) {
        this.assignRepayYesTime = assignRepayYesTime;
    }
    public String getAssignRepayPeriod() {
        return assignRepayPeriod;
    }
    public void setAssignRepayPeriod(String assignRepayPeriod) {
        this.assignRepayPeriod = assignRepayPeriod;
    }
    public String getRecoverPeriod() {
        return recoverPeriod;
    }
    public void setRecoverPeriod(String recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }
    public String getAssignCreateDate() {
        return assignCreateDate;
    }
    public void setAssignCreateDate(String assignCreateDate) {
        this.assignCreateDate = assignCreateDate;
    }
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
