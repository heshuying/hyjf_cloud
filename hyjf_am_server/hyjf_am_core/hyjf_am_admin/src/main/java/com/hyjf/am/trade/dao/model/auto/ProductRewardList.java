package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductRewardList implements Serializable {
    private Integer id;

    /**
     * 推荐人user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 汇天利用户总收益
     *
     * @mbggenerated
     */
    private BigDecimal totalInterest;

    /**
     * 推荐人提成金额
     *
     * @mbggenerated
     */
    private BigDecimal reward;

    /**
     * 发放状态:0未发放，1已发放
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 发放时间
     *
     * @mbggenerated
     */
    private Integer giveTime;

    /**
     * 活动月份
     *
     * @mbggenerated
     */
    private String actMonth;

    /**
     * 提成说明
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 唯一标识
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

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

    public BigDecimal getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(BigDecimal totalInterest) {
        this.totalInterest = totalInterest;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGiveTime() {
        return giveTime;
    }

    public void setGiveTime(Integer giveTime) {
        this.giveTime = giveTime;
    }

    public String getActMonth() {
        return actMonth;
    }

    public void setActMonth(String actMonth) {
        this.actMonth = actMonth == null ? null : actMonth.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}