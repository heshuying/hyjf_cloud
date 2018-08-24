package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;

/**
 * 汇计划-计划资金
 * @Author : huanghui
 */
public class HjhPlanCapitalResponse extends Response<HjhPlanCapitalVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
