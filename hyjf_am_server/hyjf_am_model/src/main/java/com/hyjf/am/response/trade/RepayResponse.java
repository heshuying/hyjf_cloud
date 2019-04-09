package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.RepaymentPlanVO;

/**
 * @author lisheng
 * @version RepayResponse, v0.1 2019/3/20 15:03
 */

public class RepayResponse extends Response<RepaymentPlanVO> {
    Integer count=0;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
