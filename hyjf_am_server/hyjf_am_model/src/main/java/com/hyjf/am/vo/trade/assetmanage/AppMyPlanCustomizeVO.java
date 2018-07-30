package com.hyjf.am.vo.trade.assetmanage;

import java.io.Serializable;

/**
 * @author pangchengchao
 * @version AppMyPlanCustomizeVO, v0.1 2018/7/26 10:28
 */
public class AppMyPlanCustomizeVO implements Serializable {
    private static final long serialVersionUID = 3646345172032909096L;

    private String userId;
    /** 计划的状态 */
    private String type;
    /** 项目id */
    private String borrowNid;
    /** 项目名称 */
    private String borrowName;
    /** 订单编号 */
    private String orderId;
    /** 锁定期限 */
    private String lockPeriod;
    /** 投资金额 */
    private String account;
    /** 待收金额 */
    private String interest;
    /** 待收总额 */
    private String money;
    /** 加入金额 */
    private String accedeAmount;
    /** 已得收益 */
    private String receivedInterest;
    /** 计划应还时间 */
    private String recoverTime;
    /** 预计年化收益率 */
    private String expectApr;
    /** 状态名称 */
    private String statusName;
    /** 项目详情url */
    private String borrowUrl;
    /** 投资合同url */
    private String contactUrl;
    /**标的第一项 */
    private String borrowTheFirst;
    /**标的第一项描述 */
    private String borrowTheFirstDesc;
    /**标的第二项 */
    private String borrowTheSecond;
    /**标的第二项描述 */
    private String borrowTheSecondDesc;
    /**标的第三项 */
    private String borrowTheThird;
    /**标的第三项描述 */
    private String borrowTheThirdDesc;





    /** 优惠券专用属性 */
    /** 标签：无，加息券，体验金 */
    private String label;
    /** 优惠券编号 */
    private String couponCode;
    /** 用券金额 */
    private String couponAmount;
    /** 收益期限 */
    private String couponProfitTime;
    /** 支付时间 */
    private String repayTime;
    /** 加入时间 */
    private String createTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(String lockPeriod) {
        this.lockPeriod = lockPeriod;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getExpectApr() {
        return expectApr;
    }

    public void setExpectApr(String expectApr) {
        this.expectApr = expectApr;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getBorrowUrl() {
        return borrowUrl;
    }

    public void setBorrowUrl(String borrowUrl) {
        this.borrowUrl = borrowUrl;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getCouponProfitTime() {
        return couponProfitTime;
    }

    public void setCouponProfitTime(String couponProfitTime) {
        this.couponProfitTime = couponProfitTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccedeAmount() {
        return accedeAmount;
    }

    public void setAccedeAmount(String accedeAmount) {
        this.accedeAmount = accedeAmount;
    }

    public String getReceivedInterest() {
        return receivedInterest;
    }

    public void setReceivedInterest(String receivedInterest) {
        this.receivedInterest = receivedInterest;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBorrowTheFirst() {
        return borrowTheFirst;
    }

    public void setBorrowTheFirst(String borrowTheFirst) {
        this.borrowTheFirst = borrowTheFirst;
    }

    public String getBorrowTheFirstDesc() {
        return borrowTheFirstDesc;
    }

    public void setBorrowTheFirstDesc(String borrowTheFirstDesc) {
        this.borrowTheFirstDesc = borrowTheFirstDesc;
    }

    public String getBorrowTheSecond() {
        return borrowTheSecond;
    }

    public void setBorrowTheSecond(String borrowTheSecond) {
        this.borrowTheSecond = borrowTheSecond;
    }

    public String getBorrowTheSecondDesc() {
        return borrowTheSecondDesc;
    }

    public void setBorrowTheSecondDesc(String borrowTheSecondDesc) {
        this.borrowTheSecondDesc = borrowTheSecondDesc;
    }
}
