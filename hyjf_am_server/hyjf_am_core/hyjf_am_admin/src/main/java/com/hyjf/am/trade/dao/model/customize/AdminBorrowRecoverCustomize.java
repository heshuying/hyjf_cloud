package com.hyjf.am.trade.dao.model.customize;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverCustomize, v0.1 2018/7/2 18:36
 */
public class AdminBorrowRecoverCustomize {
    /**
     * 借款标题
     */
    private String borrowName;
    /**
     * 资产来源
     */
    private String instName;
    /**
     * 借款编号
     */
    private String borrowNid;
    /**
     * 计划编号
     */
    private String planNid;
    /**
     * 投资订单号
     */
    private String orderNum;
    /**
     * 投资人
     */
    private String username;
    /**
     * 投资金额
     */
    private String account;
    /**
     * 应放款金额
     */
    private String accountYes;
    /**
     * 投资金额
     */
    private String accountPrice;
    /**
     * 应收服务费
     */
    private String loanFee;
    /**
     * 放款订单号
     */
    private String loanOrdid;
    /**
     * 合作机构编号
     */
    private String instCode;
    /**
     * 实收服务费
     */
    private String servicePrice;
    /**
     * 应放款
     */
    private String recoverPrice;
    /**
     * 已放款
     */
    private String recoverPriceOver;
    /**
     * 放款状态
     */
    private String isRecover;
    /**
     * 投资时间
     */
    private String timeRecover;
    /**
     * 放款时间
     */
    private String createTime;

    /**
     * 借款人用户ID
     */
    private String userId;

    /**
     * 项目类型
     */
    private String borrowProjectTypeName;

    /**
     * 借款期限
     */
    private String borrowPeriod;

    /**
     * 年化收益
     */
    private String borrowApr;

    /**
     * 还款方式
     */
    private String borrowStyleName;

    /**
     * 投资人用户名
     */
    private String tenderUsername;

    /**
     * 投资人用户ID
     */
    private String tenderUserId;
    /**
     * 放款订单号
     */
    private String loanBatchNo;

    /**
     * 推荐人ID（投资时）
     */
    private String tenderReferrerUserId;
    /**
     * 投资人用户属性（投资时）
     */
    private String tenderUserAttribute;
    /**
     * 推荐人用户属性（投资时）
     */
    private String inviteUserAttribute;
    /**
     * 推荐人（投资时）
     */
    private String tenderReferrerUsername;

    /**
     * 一级分部（投资时）
     */
    private String departmentLevel1Name;

    /**
     * 二级分部（投资时）
     */
    private String departmentLevel2Name;

    /**
     * 团队（投资时）
     */
    private String teamName;

    /**
     * 受托支付标志 0 否；1是
     */
    private Integer entrustedFlg;

    /**
     * 受托支付用户名
     */
    private String entrustedUserName;


    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountYes() {
        return accountYes;
    }

    public void setAccountYes(String accountYes) {
        this.accountYes = accountYes;
    }

    public String getAccountPrice() {
        return accountPrice;
    }

    public void setAccountPrice(String accountPrice) {
        this.accountPrice = accountPrice;
    }

    public String getLoanFee() {
        return loanFee;
    }

    public void setLoanFee(String loanFee) {
        this.loanFee = loanFee;
    }

    public String getLoanOrdid() {
        return loanOrdid;
    }

    public void setLoanOrdid(String loanOrdid) {
        this.loanOrdid = loanOrdid;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getRecoverPrice() {
        return recoverPrice;
    }

    public void setRecoverPrice(String recoverPrice) {
        this.recoverPrice = recoverPrice;
    }

    public String getRecoverPriceOver() {
        return recoverPriceOver;
    }

    public void setRecoverPriceOver(String recoverPriceOver) {
        this.recoverPriceOver = recoverPriceOver;
    }

    public String getIsRecover() {
        return isRecover;
    }

    public void setIsRecover(String isRecover) {
        this.isRecover = isRecover;
    }

    public String getTimeRecover() {
        return timeRecover;
    }

    public void setTimeRecover(String timeRecover) {
        this.timeRecover = timeRecover;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBorrowProjectTypeName() {
        return borrowProjectTypeName;
    }

    public void setBorrowProjectTypeName(String borrowProjectTypeName) {
        this.borrowProjectTypeName = borrowProjectTypeName;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName;
    }

    public String getTenderUsername() {
        return tenderUsername;
    }

    public void setTenderUsername(String tenderUsername) {
        this.tenderUsername = tenderUsername;
    }

    public String getTenderUserId() {
        return tenderUserId;
    }

    public void setTenderUserId(String tenderUserId) {
        this.tenderUserId = tenderUserId;
    }

    public String getLoanBatchNo() {
        return loanBatchNo;
    }

    public void setLoanBatchNo(String loanBatchNo) {
        this.loanBatchNo = loanBatchNo;
    }

    public String getTenderReferrerUserId() {
        return tenderReferrerUserId;
    }

    public void setTenderReferrerUserId(String tenderReferrerUserId) {
        this.tenderReferrerUserId = tenderReferrerUserId;
    }

    public String getTenderUserAttribute() {
        return tenderUserAttribute;
    }

    public void setTenderUserAttribute(String tenderUserAttribute) {
        this.tenderUserAttribute = tenderUserAttribute;
    }

    public String getInviteUserAttribute() {
        return inviteUserAttribute;
    }

    public void setInviteUserAttribute(String inviteUserAttribute) {
        this.inviteUserAttribute = inviteUserAttribute;
    }

    public String getTenderReferrerUsername() {
        return tenderReferrerUsername;
    }

    public void setTenderReferrerUsername(String tenderReferrerUsername) {
        this.tenderReferrerUsername = tenderReferrerUsername;
    }

    public String getDepartmentLevel1Name() {
        return departmentLevel1Name;
    }

    public void setDepartmentLevel1Name(String departmentLevel1Name) {
        this.departmentLevel1Name = departmentLevel1Name;
    }

    public String getDepartmentLevel2Name() {
        return departmentLevel2Name;
    }

    public void setDepartmentLevel2Name(String departmentLevel2Name) {
        this.departmentLevel2Name = departmentLevel2Name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getEntrustedFlg() {
        return entrustedFlg;
    }

    public void setEntrustedFlg(Integer entrustedFlg) {
        this.entrustedFlg = entrustedFlg;
    }

    public String getEntrustedUserName() {
        return entrustedUserName;
    }

    public void setEntrustedUserName(String entrustedUserName) {
        this.entrustedUserName = entrustedUserName;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
