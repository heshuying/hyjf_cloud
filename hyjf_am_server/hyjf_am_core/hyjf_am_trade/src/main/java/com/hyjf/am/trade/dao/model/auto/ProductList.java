package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductList implements Serializable {
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
     * 出借金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 出借时间
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
     * 出借资金状态
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

    /**
     * 客户端0PC，1微信2安卓APP，3IOS APP，4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 当前汇天利账户可用本金
     *
     * @mbggenerated
     */
    private BigDecimal balance;

    /**
     * 放款id
     *
     * @mbggenerated
     */
    private String loansId;

    /**
     * 放款order_date
     *
     * @mbggenerated
     */
    private String loansDate;

    /**
     * 是否为对公账户出借 0：否 1：是
     *
     * @mbggenerated
     */
    private Integer isNew;

    /**
     * 出借状态：0成功，1未付款，2失败    默认0
     *
     * @mbggenerated
     */
    private Integer investStatus;

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

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getLoansId() {
        return loansId;
    }

    public void setLoansId(String loansId) {
        this.loansId = loansId == null ? null : loansId.trim();
    }

    public String getLoansDate() {
        return loansDate;
    }

    public void setLoansDate(String loansDate) {
        this.loansDate = loansDate == null ? null : loansDate.trim();
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getInvestStatus() {
        return investStatus;
    }

    public void setInvestStatus(Integer investStatus) {
        this.investStatus = investStatus;
    }
}