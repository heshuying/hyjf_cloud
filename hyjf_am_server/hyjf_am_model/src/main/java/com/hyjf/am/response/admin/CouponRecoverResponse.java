/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.admin.coupon.CertCouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;

import java.util.List;

/**
 * @author jun
 * @version CouponRecoverResponse, v0.1 2018/7/11 9:58
 */
public class CouponRecoverResponse extends AdminResponse<CouponRecoverVO> {
    private List<CertCouponRecoverVO> certCouponRecoverVOList;

    public List<CertCouponRecoverVO> getCertCouponRecoverVOList() {
        return certCouponRecoverVOList;
    }

    public void setCertCouponRecoverVOList(List<CertCouponRecoverVO> certCouponRecoverVOList) {
        this.certCouponRecoverVOList = certCouponRecoverVOList;
    }
}
