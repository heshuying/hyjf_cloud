/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

/**
 * @author PC-LIUSHOUYI
 * @version HjhRepayResponseOld, v0.1 2018/6/26 14:13
 */
public class HjhRepayResponse extends Response<HjhRepayVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
