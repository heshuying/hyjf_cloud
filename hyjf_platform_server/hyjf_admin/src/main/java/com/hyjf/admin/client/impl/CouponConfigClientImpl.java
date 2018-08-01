/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.CouponConfigClient;
import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yaoyong
 * @version CouponConfigClientImpl, v0.1 2018/7/5 11:51
 */
@Service
public class CouponConfigClientImpl implements CouponConfigClient {

    @Autowired
    RestTemplate restTemplate;
    /**
     * 获取优惠券发行列表
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigCustomizeResponse getRecordList(CouponConfigRequest couponConfigRequest) {
        String url = "http://AM-TRADE/am-trade/couponConfig/getRecordList";
        CouponConfigCustomizeResponse response = restTemplate.postForEntity(url,couponConfigRequest,CouponConfigCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 获取编辑页信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse getCouponConfig(CouponConfigRequest couponConfigRequest) {
        String url = "http://AM-TRADE/am-trade/couponConfig/getCouponConfig";
        CouponConfigResponse response = restTemplate.postForEntity(url,couponConfigRequest,CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 保存修改信息
     * @param request
     * @return
     */
    @Override
    public CouponConfigResponse saveCouponConfig(CouponConfigRequest request) {
        String url = "http://AM-TRADE/am-trade/couponConfig/saveCouponConfig";
        CouponConfigResponse response = restTemplate.postForEntity(url,request,CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 添加发行信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse insertAction(CouponConfigRequest couponConfigRequest) {
        String url = "http://AM-TRADE/am-trade/couponConfig/insertCouponConfig";
        CouponConfigResponse response = restTemplate.postForEntity(url,couponConfigRequest,CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 删除发行信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse deleteAction(CouponConfigRequest couponConfigRequest) {
        String url = "http://AM-TRADE/am-trade/couponConfig/deleteCouponConfig";
        CouponConfigResponse response = restTemplate.postForEntity(url,couponConfigRequest,CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 获取审核信息
     * @param ccfr
     * @return
     */
    @Override
    public CouponConfigResponse getAuditInfo(CouponConfigRequest ccfr) {
        String url = "http://AM-TRADE/am-trade/couponConfig/getAuditInfo";
        CouponConfigResponse response = restTemplate.postForEntity(url,ccfr,CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 修改审核信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse updateAuditInfo(CouponConfigRequest couponConfigRequest) {
        String url = "http://AM-TRADE/am-trade/couponConfig/updateAuditInfo";
        CouponConfigResponse response = restTemplate.postForEntity(url,couponConfigRequest,CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据优惠券编号查询
     * @param cur
     * @return
     */
    @Override
    public CouponUserResponse getIssueNumber(CouponUserRequest cur) {
        String url = "http://AM-TRADE/am-trade/couponUser/getIssueNumber";
        CouponUserResponse response = restTemplate.postForEntity(url,cur,CouponUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
}
