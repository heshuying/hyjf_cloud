/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.AemsBorrowRepayPlanCustomizeVO;
import com.hyjf.am.vo.trade.PlanLockCustomizeVO;

/**
 * @author liuyang
 * @version AemsBorrowRepayPlanCustomizeResponse, v0.1 2018/12/12 14:40
 */
public class AemsBorrowRepayPlanCustomizeResponse extends Response<AemsBorrowRepayPlanCustomizeVO> {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
