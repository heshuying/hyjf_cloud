package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;

import java.util.Map;

/**
 * @author hesy
 * @version MyCouponListResponse, v0.1 2018/6/23 12:00
 */
public class RepayDetailPageResponse extends Response {
    Map<String, Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
