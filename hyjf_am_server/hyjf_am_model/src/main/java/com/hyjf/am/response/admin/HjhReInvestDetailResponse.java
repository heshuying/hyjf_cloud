package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;

/**
 * 汇计划-计划资金-复投详情
 * @Author : huanghui
 */
public class HjhReInvestDetailResponse extends Response<HjhReInvestDetailVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
