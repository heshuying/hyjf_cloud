/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BasePage;

/**
 * @author fq
 * @version DataCenterCouponRequest, v0.1 2018/7/20 9:21
 */
public class DataCenterCouponRequest extends BasePage {
    /** 优惠券类型 */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
