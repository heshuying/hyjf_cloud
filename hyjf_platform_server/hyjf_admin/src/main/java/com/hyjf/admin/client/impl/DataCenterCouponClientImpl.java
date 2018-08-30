package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.client.DataCenterCouponClient;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.admin.DataCenterCouponCustomizeResponse;
import com.hyjf.am.response.admin.DataCenterCouponResponse;
import com.hyjf.am.resquest.trade.DadaCenterCouponCustomizeRequest;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/30  14:28
 */
@Service
public class DataCenterCouponClientImpl implements DataCenterCouponClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<DataCenterCouponCustomizeVO> getDataCenterCouponList(DadaCenterCouponRequestBean requestBean, String type) {
        if (requestBean != null) {
            requestBean.setType(type);
        }
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/datacenter/coupon/getdatacentercouponlist",
                requestBean, DataCenterCouponResponse.class).getResultList();
    }

    @Override
    public String getActivityTitle(Integer activityId) {
        CouponTenderResponse response = restTemplate
                .getForEntity("http://AM-ADMIN/am-admin/datacenter/coupon/hztgetactivitytitle/" + activityId,
                        CouponTenderResponse.class)
                .getBody();
        if (response != null) {
            return response.getAttrbute();
        }
        return null;
    }


    @Override
    public List<DataCenterCouponCustomizeVO> getRecordListDJ(DataCenterCouponCustomizeVO dataCenterCouponCustomize) {
        DataCenterCouponCustomizeResponse response = restTemplate.postForObject(
                "http://AM-ADMIN/am-admin/datacenter/coupon/get_record_list_dj", dataCenterCouponCustomize,
                DataCenterCouponCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<DataCenterCouponCustomizeVO> getRecordListJX(DataCenterCouponCustomizeVO dataCenterCouponCustomize) {
        DadaCenterCouponCustomizeRequest request = new DadaCenterCouponCustomizeRequest();
        DataCenterCouponCustomizeResponse response = restTemplate.postForObject(
                "http://AM-ADMIN/am-admin/datacenter/coupon/get_record_list_jx", request,
                DataCenterCouponCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
