/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.CouponCheckResponse;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;
import com.hyjf.am.vo.config.CouponCheckVO;

import java.util.Map;

/**
 * @author yaoyong
 * @version CouponCheckClient, v0.1 2018/7/4 11:11
 */
public interface CouponCheckClient {
    /**
     * 获取优惠券列表
     * @param adminCouponCheckRequest
     * @return
     */
    CouponCheckResponse getCouponList(AdminCouponCheckRequest adminCouponCheckRequest);

    /**
     * 删除优惠券信息
     * @param acr
     * @return
     */
    CouponCheckResponse deleteCouponList(AdminCouponCheckRequest acr);

    /**
     * 插入优惠券信息
     * @param accr
     * @return
     */
    CouponCheckResponse insert(AdminCouponCheckRequest accr);

    /**
     * 根据id查询优惠券
     * @param id
     * @return
     */
    CouponCheckVO selectCoupon(Integer id);

    /**
     * 批量审核优惠券
     * @param params
     * @param params
     * @return
     */
    JSONObject getBatchCoupons(Map<String, String> params);

    /**
     * 修改审核状态
     * @param request
     * @return
     */
    boolean updateCoupon(AdminCouponCheckRequest request);
}
