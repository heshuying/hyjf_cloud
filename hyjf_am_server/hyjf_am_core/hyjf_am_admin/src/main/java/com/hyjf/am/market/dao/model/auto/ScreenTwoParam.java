package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScreenTwoParam implements Serializable {
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 1:新客组;2:老客组;3:运营部;
     *
     * @mbggenerated
     */
    private Integer flag;

    /**
     * 坐席姓名
     *
     * @mbggenerated
     */
    private String customerName;

    /**
     * 查询时间(yyyy-MM-dd)
     *
     * @mbggenerated
     */
    private Date queryTime;

    /**
     * 增资
     *
     * @mbggenerated
     */
    private BigDecimal capitalIncrease;

    /**
     * 提现率
     *
     * @mbggenerated
     */
    private BigDecimal cashWithdrawalRate;

    /**
     * 当前站岗资金
     *
     * @mbggenerated
     */
    private BigDecimal nowBalance;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }

    public BigDecimal getCapitalIncrease() {
        return capitalIncrease;
    }

    public void setCapitalIncrease(BigDecimal capitalIncrease) {
        this.capitalIncrease = capitalIncrease;
    }

    public BigDecimal getCashWithdrawalRate() {
        return cashWithdrawalRate;
    }

    public void setCashWithdrawalRate(BigDecimal cashWithdrawalRate) {
        this.cashWithdrawalRate = cashWithdrawalRate;
    }

    public BigDecimal getNowBalance() {
        return nowBalance;
    }

    public void setNowBalance(BigDecimal nowBalance) {
        this.nowBalance = nowBalance;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}