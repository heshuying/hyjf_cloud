/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.trade.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

/**
 * @author yaoy
 * @version RepayDataRecoverRequest, v0.1 2018/6/25 17:41
 */
public class RepayDataRecoverRequest extends Request {

    private CouponRecoverCustomizeVO couponRecoverCustomizeVO;

    private BankOpenAccountVO bankOpenAccountVO;

    private BorrowTenderCpnVO borrowTenderCpnVO;

    private String userId;

    private String couponUserCode;

    private String ip;

    private int count;

    private String borrowNid;

    private String borrowStyle;

    private int periodNow;

    public CouponRecoverCustomizeVO getCouponRecoverCustomizeVO() {
        return couponRecoverCustomizeVO;
    }

    public void setCouponRecoverCustomizeVO(CouponRecoverCustomizeVO couponRecoverCustomizeVO) {
        this.couponRecoverCustomizeVO = couponRecoverCustomizeVO;
    }

    public BankOpenAccountVO getBankOpenAccountVO() {
        return bankOpenAccountVO;
    }

    public void setBankOpenAccountVO(BankOpenAccountVO bankOpenAccountVO) {
        this.bankOpenAccountVO = bankOpenAccountVO;
    }

    public BorrowTenderCpnVO getBorrowTenderCpnVO() {
        return borrowTenderCpnVO;
    }

    public void setBorrowTenderCpnVO(BorrowTenderCpnVO borrowTenderCpnVO) {
        this.borrowTenderCpnVO = borrowTenderCpnVO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public int getPeriodNow() {
        return periodNow;
    }

    public void setPeriodNow(int periodNow) {
        this.periodNow = periodNow;
    }
}
