/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayPlanResponse, v0.1 2018/6/22 15:39
 */
public class BorrowRepayPlanResponse extends Response<BorrowRepayPlanVO> {
    private BorrowRepayPlanBeanVO borrowRepayPlanBeanVO;

    public BorrowRepayPlanBeanVO getBorrowRepayPlanBeanVO() {
        return borrowRepayPlanBeanVO;
    }

    public void setBorrowRepayPlanBeanVO(BorrowRepayPlanBeanVO borrowRepayPlanBeanVO) {
        this.borrowRepayPlanBeanVO = borrowRepayPlanBeanVO;
    }
}
