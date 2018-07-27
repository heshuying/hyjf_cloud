/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.admin.coupon.CouponUserCustomizeVO;

/**
 * @author yaoyong
 * @version CouponUserCustomizeResponse, v0.1 2018/7/23 16:21
 */
public class CouponUserCustomizeResponse extends AdminResponse<CouponUserCustomizeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
