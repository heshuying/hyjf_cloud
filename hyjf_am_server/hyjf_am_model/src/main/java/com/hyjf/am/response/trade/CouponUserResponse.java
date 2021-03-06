/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;

/**
 * @author yaoy
 * @version CouponUserResponse, v0.1 2018/6/19 18:36
 */
public class CouponUserResponse extends Response<CouponUserVO> {

    private  Integer count;

    private boolean isSend;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean getIsSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
