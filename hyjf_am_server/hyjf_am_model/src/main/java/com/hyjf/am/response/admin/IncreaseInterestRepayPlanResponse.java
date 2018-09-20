/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.IncreaseInterestRepayDetailVO;


/**
 * @author wenxin
 * @version IncreaseInterestRepayPlanResponse.java, v0.1 2018年8月30日
 */
public class IncreaseInterestRepayPlanResponse extends Response<IncreaseInterestRepayDetailVO>{
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
