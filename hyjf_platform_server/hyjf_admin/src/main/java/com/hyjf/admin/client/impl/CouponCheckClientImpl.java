/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.CouponCheckClient;
import com.hyjf.am.response.admin.CouponCheckResponse;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;
import com.hyjf.am.vo.config.CouponCheckVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author yaoyong
 * @version CouponCheckClientImpl, v0.1 2018/7/4 11:12
 */
@Service
public class CouponCheckClientImpl implements CouponCheckClient {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public CouponCheckResponse getCouponList(AdminCouponCheckRequest adminCouponCheckRequest) {
        String url = "http://AM-CONFIG/am-config/checkList/getCheckList";
        CouponCheckResponse response = restTemplate.postForEntity(url, adminCouponCheckRequest, CouponCheckResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponCheckResponse deleteCouponList(AdminCouponCheckRequest acr) {
        String url = "http://AM-CONFIG/am-config/checkList/deleteCheckList";
        CouponCheckResponse response = restTemplate.postForEntity(url, acr, CouponCheckResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponCheckResponse insert(AdminCouponCheckRequest accr) {
        String url = "http://AM-CONFIG/am-config/checkList/insertCoupon";
        CouponCheckResponse response = restTemplate.postForEntity(url, accr, CouponCheckResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponCheckVO selectCoupon(Integer id) {
        String url = "http://AM-CONFIG/am-config/checkList/selectCoupon/" + id;
        CouponCheckVO couponCheckVO = restTemplate.getForEntity(url,CouponCheckVO.class).getBody();
        if (couponCheckVO != null) {
            return couponCheckVO;
        }
        return null;
    }

    @Override
    public JSONObject getBatchCoupons(Map<String, String> params) {
        String url = "http://AM-USER/am-user/checkCoupon/getBatchCoupons";
        return restTemplate.postForEntity(url,params,JSONObject.class).getBody();
    }

    @Override
    public boolean updateCoupon(AdminCouponCheckRequest request) {
        String url = "http://AM-CONFIG/am-config/checkList/updateCoupon";
        return restTemplate.postForEntity(url,request,Boolean.class).getBody();
    }
}
