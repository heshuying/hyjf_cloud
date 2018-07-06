/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.trade.borrow.BorrowVO;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowFireRequest, v0.1 2018/7/4 13:47
 */
public class BorrowFireRequest implements Serializable {
    private BorrowVO borrowVO;

    private String verifyStatus;

    private String ontime;

    public BorrowVO getBorrowVO() {
        return borrowVO;
    }

    public void setBorrowVO(BorrowVO borrowVO) {
        this.borrowVO = borrowVO;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getOntime() {
        return ontime;
    }

    public void setOntime(String ontime) {
        this.ontime = ontime;
    }
}
