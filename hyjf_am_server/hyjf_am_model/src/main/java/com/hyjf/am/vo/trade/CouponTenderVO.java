package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;

import java.io.Serializable;

public class CouponTenderVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer couponGrantId;

    private String orderId;

    private Integer attribute;

    private String addUser;

    private String updateUser;

    private Integer delFlag;

    private Integer addTime;

    private Integer updateTime;

    // 优惠券投资用   start

    private BorrowTenderCpnVO borrowTenderCpn;

    private CouponTenderVO couponTender;

    private CouponRealTenderVO couponRealTender;

    private CouponUserVO couponUser;

    // 优惠券投资用   end

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponGrantId() {
        return couponGrantId;
    }

    public void setCouponGrantId(Integer couponGrantId) {
        this.couponGrantId = couponGrantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public BorrowTenderCpnVO getBorrowTenderCpn() {
        return borrowTenderCpn;
    }

    public void setBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn) {
        this.borrowTenderCpn = borrowTenderCpn;
    }

    public CouponTenderVO getCouponTender() {
        return couponTender;
    }

    public void setCouponTender(CouponTenderVO couponTender) {
        this.couponTender = couponTender;
    }

    public CouponRealTenderVO getCouponRealTender() {
        return couponRealTender;
    }

    public void setCouponRealTender(CouponRealTenderVO couponRealTender) {
        this.couponRealTender = couponRealTender;
    }

    public CouponUserVO getCouponUser() {
        return couponUser;
    }

    public void setCouponUser(CouponUserVO couponUser) {
        this.couponUser = couponUser;
    }

}