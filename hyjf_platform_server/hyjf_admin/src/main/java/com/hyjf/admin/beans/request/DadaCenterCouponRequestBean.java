/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

/**
 * @author fq
 * @version DadaCenterCouponRequestBean, v0.1 2018/7/19 9:45
 */
public class DadaCenterCouponRequestBean extends BaseRequest {
    /** 优惠券类型 */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
