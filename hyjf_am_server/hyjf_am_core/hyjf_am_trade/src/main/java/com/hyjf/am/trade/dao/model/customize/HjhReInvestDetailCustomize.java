package com.hyjf.am.trade.dao.model.customize;

public class HjhReInvestDetailCustomize {

    //前画面传入
    private String planNid;
    private String date;
    //计划出借订单号（检索）
    private String accedeOrderIdSrch;
    //用户名（检索）
    private String userNameSrch;
    //借款编号（检索）
    private String borrowNidSrch;
    //借款期限(锁定期)（检索）
    private String lockPeriodSrch;
    //出借方式（检索）
    private String investTypeSrch;
    //还款方式（检索）
    private String borrowStyleSrch;

    //计划订单号
    private String accedeOrderId;
    //用户名
    private String userName;
    //推荐人
    private String inviteUserName;
    //用户属性
    private String userAttribute;
    //借款编号
    private String borrowNid;
    //年化利率
    private String expectApr;
    //借款期限(锁定期)
    private String borrowPeriod;
    private String isMonth;
    //出借金额
    private String accedeAccount;
    //还款方式
    private String borrowStyle;
    //出借方式
    private String investType;
    //计息时间
    private String countInterestTime;
    //出借时间
    private String addTime;

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccedeOrderIdSrch() {
        return accedeOrderIdSrch;
    }

    public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
        this.accedeOrderIdSrch = accedeOrderIdSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getLockPeriodSrch() {
        return lockPeriodSrch;
    }

    public void setLockPeriodSrch(String lockPeriodSrch) {
        this.lockPeriodSrch = lockPeriodSrch;
    }

    public String getInvestTypeSrch() {
        return investTypeSrch;
    }

    public void setInvestTypeSrch(String investTypeSrch) {
        this.investTypeSrch = investTypeSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInviteUserName() {
        return inviteUserName;
    }

    public void setInviteUserName(String inviteUserName) {
        this.inviteUserName = inviteUserName;
    }

    public String getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(String userAttribute) {
        this.userAttribute = userAttribute;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getExpectApr() {
        return expectApr;
    }

    public void setExpectApr(String expectApr) {
        this.expectApr = expectApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth;
    }

    public String getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(String accedeAccount) {
        this.accedeAccount = accedeAccount;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getCountInterestTime() {
        return countInterestTime;
    }

    public void setCountInterestTime(String countInterestTime) {
        this.countInterestTime = countInterestTime;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
