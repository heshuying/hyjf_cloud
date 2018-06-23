package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

public class CouponRealTenderVO extends BaseVO implements Serializable {
    private Integer id;

    private String couponTenderId;

    private String realTenderId;

    private String addUser;

    private Integer delFlag;

    private String updateUser;

    private Date addTime;

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

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}