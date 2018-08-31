package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.admin.coupon.CouponTenderVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author：yinhui
 * @Date: 2018/8/31  11:28
 */
public class CouponBackMoneyContResponse {


    public CouponTenderVo couponTenderVo;

    @ApiModelProperty(value = "总条数")
    public Integer count;

    public CouponTenderVo getCouponTenderVo() {
        return couponTenderVo;
    }

    public void setCouponTenderVo(CouponTenderVo couponTenderVo) {
        this.couponTenderVo = couponTenderVo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
