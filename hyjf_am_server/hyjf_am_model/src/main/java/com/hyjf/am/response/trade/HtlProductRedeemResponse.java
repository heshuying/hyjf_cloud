/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.HtlProductRedeemVO;

/**
 * @author: sunpeikai
 * @version: HtlProductRedeemResponse, v0.1 2018/7/25 15:43
 */
public class HtlProductRedeemResponse extends Response<HtlProductRedeemVO> {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
