package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;

/**
 * @author lisheng
 * @version DataSearchCustomizeResponse, v0.1 2018/8/23 14:58
 */

public class DataSearchCustomizeResponse extends Response<DataSearchCustomizeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
