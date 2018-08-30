/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author yaoyong
 * @version CouponConfigCustomizeResponse, v0.1 2018/7/5 14:37
 */
public class CouponConfigCustomizeResponse extends Response<CouponConfigCustomizeVO> {
    private int count;

    private List<String> couponStatus;

    private List<ParamNameVO> couponTypes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(List<String> couponStatus) {
        this.couponStatus = couponStatus;
    }

    public List<ParamNameVO> getCouponTypes() {
        return couponTypes;
    }

    public void setCouponTypes(List<ParamNameVO> couponTypes) {
        this.couponTypes = couponTypes;
    }
}
