package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.CouponTenderClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@Service
public class CouponTenderClientImpl implements CouponTenderClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CouponTenderResponse countRecordHzt(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztcountcoupontender", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse queryInvestTotalHzt(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztquerynvesttotal", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHzt(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztgetrecordlist", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getCouponTenderDetailCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztcoupontenderdetail", paramMap, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getCouponRecoverCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztcouponrecover", paramMap, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getActivityById(Integer activityId) {

        String url = "http://AM-MARKET/am-market/activity/hztgetactivitytitle/" + activityId;
        CouponTenderResponse response = restTemplate.getForEntity(url, CouponTenderResponse.class).getBody();
        if (response != null) {
                return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getAdminUserByUserId(String userId) {
        String url = "http://AM-CONFIG/am-config/adminSystem/hztgetusername/" + userId;
        CouponTenderResponse response = restTemplate.getForEntity(url, CouponTenderResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHjh(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hjhcountcoupontender", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse queryInvestTotalHjh(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hjhquerynvesttotal", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHjh(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hjhgetrecordlist", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getHjhCouponTenderDetailCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hjhcoupontenderdetail", paramMap, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getHjhCouponRecoverCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hjhcouponrecover", paramMap, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/countrecordhztdj", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/getrecordlisthztdj", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse queryHztInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/queryhztinvesttotal", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse queryHztRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/queryhztrecoverinteresttotle", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/countrecordhztty", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/getrecordlisthztty", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/countrecordhztjx", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/getrecordlisthztjx", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }
}
