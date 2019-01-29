package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AppointmentRecodLog implements Serializable {
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 违约分值
     *
     * @mbggenerated
     */
    private Integer recod;

    /**
     * 违约总计分值
     *
     * @mbggenerated
     */
    private Integer recodTotal;

    /**
     * 违约标号
     *
     * @mbggenerated
     */
    private String recodNid;

    /**
     * 违约金额
     *
     * @mbggenerated
     */
    private BigDecimal recodMoney;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String apointOrderId;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String recodRemark;

    /**
     * 违约类型：0取消预约，1金额不足
     *
     * @mbggenerated
     */
    private Integer recodType;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

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

    public Integer getRecod() {
        return recod;
    }

    public void setRecod(Integer recod) {
        this.recod = recod;
    }

    public Integer getRecodTotal() {
        return recodTotal;
    }

    public void setRecodTotal(Integer recodTotal) {
        this.recodTotal = recodTotal;
    }

    public String getRecodNid() {
        return recodNid;
    }

    public void setRecodNid(String recodNid) {
        this.recodNid = recodNid == null ? null : recodNid.trim();
    }

    public BigDecimal getRecodMoney() {
        return recodMoney;
    }

    public void setRecodMoney(BigDecimal recodMoney) {
        this.recodMoney = recodMoney;
    }

    public String getApointOrderId() {
        return apointOrderId;
    }

    public void setApointOrderId(String apointOrderId) {
        this.apointOrderId = apointOrderId == null ? null : apointOrderId.trim();
    }

    public String getRecodRemark() {
        return recodRemark;
    }

    public void setRecodRemark(String recodRemark) {
        this.recodRemark = recodRemark == null ? null : recodRemark.trim();
    }

    public Integer getRecodType() {
        return recodType;
    }

    public void setRecodType(Integer recodType) {
        this.recodType = recodType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
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