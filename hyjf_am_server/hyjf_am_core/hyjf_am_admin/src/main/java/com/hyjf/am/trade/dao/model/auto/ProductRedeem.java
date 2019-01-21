package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductRedeem implements Serializable {
    /**
     * Id
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
     * 订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 赎回金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 时间
     *
     * @mbggenerated
     */
    private Integer redeemTime;

    /**
     * 利息
     *
     * @mbggenerated
     */
    private BigDecimal interest;

    /**
     * 总额
     *
     * @mbggenerated
     */
    private BigDecimal total;

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

    private String remark;

    private Integer client;

    /**
     * 赎回状态：0成功，1失败  默认0
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 备注信息（如失败信息）
     *
     * @mbggenerated
     */
    private String comments;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getRedeemTime() {
        return redeemTime;
    }

    public void setRedeemTime(Integer redeemTime) {
        this.redeemTime = redeemTime;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }
}