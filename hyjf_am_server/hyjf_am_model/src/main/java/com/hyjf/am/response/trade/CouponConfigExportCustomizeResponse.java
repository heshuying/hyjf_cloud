/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.coupon.CouponConfigExportCustomizeVO;

/**
 * @author yaoyong
 * @version CouponConfigExportCustomizeResponse, v0.1 2018/9/15 14:27
 */
public class CouponConfigExportCustomizeResponse extends Response<CouponConfigExportCustomizeVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
