/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;

/**
 * @author fq
 * @version DataCenterCouponResponse, v0.1 2018/7/19 9:49
 */
public class DataCenterCouponResponse extends Response<DataCenterCouponCustomizeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
