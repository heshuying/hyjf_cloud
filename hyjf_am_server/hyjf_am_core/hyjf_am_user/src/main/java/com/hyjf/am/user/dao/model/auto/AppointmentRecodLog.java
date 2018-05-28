package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AppointmentRecodLog implements Serializable {
    private Integer id;

    private Integer recod;

    private Integer recodTotal;

    private String recodNid;

    private BigDecimal recodMoney;

    private String apointOrderId;

    private String recodRemark;

    private Integer recodType;

    private Integer userId;

    private String userName;

    private Date createTime;

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