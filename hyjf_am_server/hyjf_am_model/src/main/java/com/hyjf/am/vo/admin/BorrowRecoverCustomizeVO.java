package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author pangchengchao
 * @version BorrowRecoverCustomizeVO, v0.1 2018/7/2 14:50
 */
public class BorrowRecoverCustomizeVO extends BaseVO implements Serializable {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;



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

    /**
     * accountYes
     *
     * @return the accountYes
     */

    public String getAccountYes() {
        return accountYes;
    }

    /**
     * @param accountYes
     *            the accountYes to set
     */

    public void setAccountYes(String accountYes) {
        this.accountYes = accountYes;
    }

    /**
     * accountPrice
     *
     * @return the accountPrice
     */

    public String getAccountPrice() {
        return accountPrice;
    }

    /**
     * @param accountPrice
     *            the accountPrice to set
     */

    public void setAccountPrice(String accountPrice) {
        this.accountPrice = accountPrice;
    }

    /**
     * loanFee
     *
     * @return the loanFee
     */

    public String getLoanFee() {
        return loanFee;
    }

    /**
     * @param loanFee
     *            the loanFee to set
     */

    public void setLoanFee(String loanFee) {
        this.loanFee = loanFee;
    }

    /**
     * userId
     *
     * @return the userId
     */

    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * borrowProjectTypeName
     *
     * @return the borrowProjectTypeName
     */

    public String getBorrowProjectTypeName() {
        return borrowProjectTypeName;
    }

    /**
     * @param borrowProjectTypeName
     *            the borrowProjectTypeName to set
     */

    public void setBorrowProjectTypeName(String borrowProjectTypeName) {
        this.borrowProjectTypeName = borrowProjectTypeName;
    }

    /**
     * borrowPeriod
     *
     * @return the borrowPeriod
     */

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    /**
     * @param borrowPeriod
     *            the borrowPeriod to set
     */

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    /**
     * borrowApr
     *
     * @return the borrowApr
     */

    public String getBorrowApr() {
        return borrowApr;
    }

    /**
     * @param borrowApr
     *            the borrowApr to set
     */

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    /**
     * borrowStyleName
     *
     * @return the borrowStyleName
     */

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    /**
     * @param borrowStyleName
     *            the borrowStyleName to set
     */

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName;
    }

    /**
     * tenderUsername
     *
     * @return the tenderUsername
     */

    public String getTenderUsername() {
        return tenderUsername;
    }

    /**
     * @param tenderUsername
     *            the tenderUsername to set
     */

    public void setTenderUsername(String tenderUsername) {
        this.tenderUsername = tenderUsername;
    }

    /**
     * tenderUserId
     *
     * @return the tenderUserId
     */

    public String getTenderUserId() {
        return tenderUserId;
    }

    /**
     * @param tenderUserId
     *            the tenderUserId to set
     */

    public void setTenderUserId(String tenderUserId) {
        this.tenderUserId = tenderUserId;
    }


    /**
     * borrowName
     *
     * @return the borrowName
     */

    public String getBorrowName() {
        return borrowName;
    }

    /**
     * @param borrowName
     *            the borrowName to set
     */

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    /**
     * borrowNid
     *
     * @return the borrowNid
     */

    public String getBorrowNid() {
        return borrowNid;
    }

    /**
     * @param borrowNid
     *            the borrowNid to set
     */

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    /**
     * orderNum
     *
     * @return the orderNum
     */

    public String getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     *            the orderNum to set
     */

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * username
     *
     * @return the username
     */

    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * account
     *
     * @return the account
     */

    public String getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * servicePrice
     *
     * @return the servicePrice
     */

    public String getServicePrice() {
        return servicePrice;
    }

    /**
     * @param servicePrice
     *            the servicePrice to set
     */

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    /**
     * recoverPrice
     *
     * @return the recoverPrice
     */

    public String getRecoverPrice() {
        return recoverPrice;
    }

    /**
     * @param recoverPrice
     *            the recoverPrice to set
     */

    public void setRecoverPrice(String recoverPrice) {
        this.recoverPrice = recoverPrice;
    }

    /**
     * recoverPriceOver
     *
     * @return the recoverPriceOver
     */

    public String getRecoverPriceOver() {
        return recoverPriceOver;
    }

    /**
     * @param recoverPriceOver
     *            the recoverPriceOver to set
     */

    public void setRecoverPriceOver(String recoverPriceOver) {
        this.recoverPriceOver = recoverPriceOver;
    }

    /**
     * isRecover
     *
     * @return the isRecover
     */

    public String getIsRecover() {
        return isRecover;
    }

    /**
     * @param isRecover
     *            the isRecover to set
     */

    public void setIsRecover(String isRecover) {
        this.isRecover = isRecover;
    }

    /**
     * timeRecover
     *
     * @return the timeRecover
     */

    public String getTimeRecover() {
        return timeRecover;
    }

    /**
     * @param timeRecover
     *            the timeRecover to set
     */

    public void setTimeRecover(String timeRecover) {
        this.timeRecover = timeRecover;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    /**
     * limitStart
     *
     * @return the limitStart
     */

    public int getLimitStart() {
        return limitStart;
    }

    /**
     * @param limitStart
     *            the limitStart to set
     */

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    /**
     * limitEnd
     *
     * @return the limitEnd
     */

    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * @param limitEnd
     *            the limitEnd to set
     */

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getLoanOrdid() {
        return loanOrdid;
    }

    public void setLoanOrdid(String loanOrdid) {
        this.loanOrdid = loanOrdid;
    }

    public String getLoanBatchNo() {
        return loanBatchNo;
    }

    public void setLoanBatchNo(String loanBatchNo) {
        this.loanBatchNo = loanBatchNo;
    }


    public String getPlanNid() {
        return planNid;
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

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
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


    /**
     * instName
     * @return the instName
     */

    public String getInstName() {
        return instName;

    }

    /**
     * @param instName the instName to set
     */

    public void setInstName(String instName) {
        this.instName = instName;

    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

}
