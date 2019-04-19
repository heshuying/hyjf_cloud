package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WithdrawRuleConfig implements Serializable {
    private Integer id;

    /**
     * 用户类型 1个人 2企业
     *
     * @mbggenerated
     */
    private Integer customerType;

    /**
     * 最小金额
     *
     * @mbggenerated
     */
    private BigDecimal minMoney;

    /**
     * 最大金额
     *
     * @mbggenerated
     */
    private BigDecimal maxMoney;

    /**
     * 工作日开始时间
     *
     * @mbggenerated
     */
    private String startTime;

    /**
     * 工作日结束时间
     *
     * @mbggenerated
     */
    private String endTime;

    /**
     * 可否提现 1可以 0不可以 
     *
     * @mbggenerated
     */
    private Integer couldWithdraw;

    /**
     * 通道
     *
     * @mbggenerated
     */
    private String routeCode;

    /**
     * 联行号 1有 0无
     *
     * @mbggenerated
     */
    private Integer payAllianceCode;

    /**
     * 0删除 1可用
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createBy;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateBy;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public BigDecimal getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(BigDecimal minMoney) {
        this.minMoney = minMoney;
    }

    public BigDecimal getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(BigDecimal maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getCouldWithdraw() {
        return couldWithdraw;
    }

    public void setCouldWithdraw(Integer couldWithdraw) {
        this.couldWithdraw = couldWithdraw;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode == null ? null : routeCode.trim();
    }

    public Integer getPayAllianceCode() {
        return payAllianceCode;
    }

    public void setPayAllianceCode(Integer payAllianceCode) {
        this.payAllianceCode = payAllianceCode;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}