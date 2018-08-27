package com.hyjf.am.response.trade.coupon;

import com.hyjf.am.response.Response;

public class CouponResponse extends Response {
    private Integer totalRecord;

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }
}
