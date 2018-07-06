/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;

/**
 * @author yaoyong
 * @version CouponConfigCustomizeResponse, v0.1 2018/7/5 14:37
 */
public class CouponConfigCustomizeResponse extends Response<CouponConfigCustomizeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
