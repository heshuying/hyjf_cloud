/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.borrow.BorrowRepayBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayResponse, v0.1 2018/6/22 19:10
 */
public class BorrowRepayResponse extends Response<BorrowRepayVO> {
    private BorrowRepayBeanVO borrowRepayBeanVO;

    public BorrowRepayBeanVO getBorrowRepayBeanVO() {
        return borrowRepayBeanVO;
    }

    public void setBorrowRepayBeanVO(BorrowRepayBeanVO borrowRepayBeanVO) {
        this.borrowRepayBeanVO = borrowRepayBeanVO;
    }
}
