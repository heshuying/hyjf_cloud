package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductListLog implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 产品ID
     *
     * @mbggenerated
     */
    private Integer productId;

    /**
     * 投资金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 投资时间
     *
     * @mbggenerated
     */
    private Integer investTime;

    /**
     * 计息时间
     *
     * @mbggenerated
     */
    private Integer interestTime;

    /**
     * 有效天数
     *
     * @mbggenerated
     */
    private Integer validDays;

    /**
     * 已赎回金额
     *
     * @mbggenerated
     */
    private BigDecimal redeemed;

    /**
     * 剩余金额
     *
     * @mbggenerated
     */
    private BigDecimal restAmount;

    /**
     * 推荐人ID
     *
     * @mbggenerated
     */
    private Integer referee;

    /**
     * 大区ID
     *
     * @mbggenerated
     */
    private Integer area;

    /**
     * 分公司ID
     *
     * @mbggenerated
     */
    private Integer company;

    /**
     * 部门ID
     *
     * @mbggenerated
     */
    private Integer department;

    /**
     * 投资资金状态
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 订单日期
     *
     * @mbggenerated
     */
    private String orderDate;

    /**
     * 利息
     *
     * @mbggenerated
     */
    private BigDecimal interest;

    private String tenderStatus;

    /**
     * 客户端0PC，1微信2安卓APP，3IOS APP，4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 是否为对公账户投资 0：否 1：是
     *
     * @mbggenerated
     */
    private Integer isNew;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Integer investTime) {
        this.investTime = investTime;
    }

    public Integer getInterestTime() {
        return interestTime;
    }

    public void setInterestTime(Integer interestTime) {
        this.interestTime = interestTime;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public BigDecimal getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(BigDecimal redeemed) {
        this.redeemed = redeemed;
    }

    public BigDecimal getRestAmount() {
        return restAmount;
    }

    public void setRestAmount(BigDecimal restAmount) {
        this.restAmount = restAmount;
    }

    public Integer getReferee() {
        return referee;
    }

    public void setReferee(Integer referee) {
        this.referee = referee;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate == null ? null : orderDate.trim();
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(String tenderStatus) {
        this.tenderStatus = tenderStatus == null ? null : tenderStatus.trim();
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }
}