package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class CouponRealTender implements Serializable {
    private Integer id;

    private String couponTenderId;

    private String realTenderId;

    private Integer createUserId;

    private Integer delFlag;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponTenderId() {
        return couponTenderId;
    }

    public void setCouponTenderId(String couponTenderId) {
        this.couponTenderId = couponTenderId == null ? null : couponTenderId.trim();
    }

    public String getRealTenderId() {
        return realTenderId;
    }

    public void setRealTenderId(String realTenderId) {
        this.realTenderId = realTenderId == null ? null : realTenderId.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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