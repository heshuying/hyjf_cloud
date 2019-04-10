/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.HjhPlanCapitalActualVO;

/**
 * 实际资金计划
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalActualResponse, v0.1 2019/4/3 14:30
 */
public class HjhPlanCapitalActualResponse extends Response<HjhPlanCapitalActualVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
