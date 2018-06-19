/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.trade.borrow.BorrowVO;

/**
 * @author fuqiang
 * @version BorrowRegistRequest, v0.1 2018/6/14 12:01
 */
public class BorrowRegistRequest {
    private BorrowVO borrowVO;

    private int status;

    private int registStatus;

    public BorrowRegistRequest() {
    }

    public BorrowRegistRequest(BorrowVO borrowVO, int status, int registStatus) {
        this.borrowVO = borrowVO;
        this.status = status;
        this.registStatus = registStatus;
    }

    public BorrowVO getBorrowVO() {
        return borrowVO;
    }

    public void setBorrowVO(BorrowVO borrowVO) {
        this.borrowVO = borrowVO;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRegistStatus() {
        return registStatus;
    }

    public void setRegistStatus(int registStatus) {
        this.registStatus = registStatus;
    }
}
