package com.hyjf.am.vo.admin.config;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WithdrawRuleConfigVO implements Serializable {
    private Integer id;

    /**
     * 用户类型 1个人 2企业
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "用户类型 0个人 1企业")
    private Integer customerType;

    /**
     * 最小金额
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "最小金额")
    private BigDecimal minMoney;

    /**
     * 最大金额
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "最大金额")
    private BigDecimal maxMoney;

    /**
     * 工作日开始时间
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "工作日开始时间，格式：24h:mi:ss")
    private String startTime;

    /**
     * 工作日结束时间
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "工作日结束时间，格式：24h:mi:ss")
    private String endTime;

    /**
     * 是否节假日 1是 0否
     */
    @ApiModelProperty(value = "是否节假日 1是 0否")
    private Integer isHoliday;

    /**
     * 可否提现 1可以 0不可以 
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "可否提现 0不可以 1可以")
    private Integer couldWithdraw;

    /**
     * 通道
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "通道 1默认空 2大额通道 3小额 0不传")
    private String routeCode;

    /**
     * 联行号 1有 0无
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "联行号 0无 1有")
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

    public Integer getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(Integer isHoliday) {
        this.isHoliday = isHoliday;
    }
}