package com.hyjf.am.vo.trade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lisheng
 * @version RepaymentPlanVO, v0.1 2019/3/20 16:55
 */

public class RepaymentPlanVO implements Serializable {
    private Integer id;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 标的编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 汇计划加入订单号
     *
     * @mbggenerated
     */
    private String accedeOrderId;

    /**
     * 坐席姓名
     *
     * @mbggenerated
     */
    private String customerName;

    /**
     * 坐席分组 0:其他,1:新客组,2:老客组,3:惠众
     *
     * @mbggenerated
     */
    private Integer customerGroup;

    /**
     * 待回款金额
     *
     * @mbggenerated
     */
    private BigDecimal money;

    /**
     * 待回款时间,精确到日 yyyy-mm-dd
     *
     * @mbggenerated
     */
    private Date repaymentTime;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Integer getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(Integer customerGroup) {
        this.customerGroup = customerGroup;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
