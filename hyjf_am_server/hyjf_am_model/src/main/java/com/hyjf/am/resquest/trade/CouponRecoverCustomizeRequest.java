/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yaoyong
 * @version CouponRecoverCustomizeRequest, v0.1 2018/8/13 17:39
 */
public class CouponRecoverCustomizeRequest implements Serializable {
    private BigDecimal recoverAccount;

    private BigDecimal recoverInterest;

    private BigDecimal recoverCapital;

    private String orderId;

    private String seqNo;

    private Integer tenderUserId;

    private String recoverNid;

    private BankOpenAccountVO bankOpenAccountVO;

    private CouponRecoverVO couponRecoverVO;

    private CouponRecoverCustomizeVO currentRecoverVO;

    private BorrowTenderCpnVO borrowTenderCpn;

    private UserInfoCustomizeVO userInfoCustomize;

    private UserVO user;

    public BigDecimal getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(BigDecimal recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getTenderUserId() {
        return tenderUserId;
    }

    public void setTenderUserId(Integer tenderUserId) {
        this.tenderUserId = tenderUserId;
    }

    public BankOpenAccountVO getBankOpenAccountVO() {
        return bankOpenAccountVO;
    }

    public void setBankOpenAccountVO(BankOpenAccountVO bankOpenAccountVO) {
        this.bankOpenAccountVO = bankOpenAccountVO;
    }

    public CouponRecoverVO getCouponRecoverVO() {
        return couponRecoverVO;
    }

    public void setCouponRecoverVO(CouponRecoverVO couponRecoverVO) {
        this.couponRecoverVO = couponRecoverVO;
    }

    public CouponRecoverCustomizeVO getCurrentRecoverVO() {
        return currentRecoverVO;
    }

    public void setCurrentRecoverVO(CouponRecoverCustomizeVO currentRecoverVO) {
        this.currentRecoverVO = currentRecoverVO;
    }

    public String getRecoverNid() {
        return recoverNid;
    }

    public void setRecoverNid(String recoverNid) {
        this.recoverNid = recoverNid;
    }

    public BorrowTenderCpnVO getBorrowTenderCpn() {
        return borrowTenderCpn;
    }

    public void setBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn) {
        this.borrowTenderCpn = borrowTenderCpn;
    }

    public BigDecimal getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(BigDecimal recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public BigDecimal getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(BigDecimal recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public UserInfoCustomizeVO getUserInfoCustomize() {
        return userInfoCustomize;
    }

    public void setUserInfoCustomize(UserInfoCustomizeVO userInfoCustomize) {
        this.userInfoCustomize = userInfoCustomize;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}
