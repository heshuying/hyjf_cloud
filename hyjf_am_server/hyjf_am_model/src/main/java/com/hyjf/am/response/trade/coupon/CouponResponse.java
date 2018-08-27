package com.hyjf.am.response.trade.coupon;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;

public class CouponResponse extends Response<CouponUserForAppCustomizeVO> {
    private Integer totalRecord;

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }
}
