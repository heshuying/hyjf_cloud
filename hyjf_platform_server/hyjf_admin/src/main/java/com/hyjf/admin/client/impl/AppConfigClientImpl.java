package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AppConfigClient;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author lisheng
 * @version AppConfigClientImpl, v0.1 2018/7/11 14:41
 */
@Service
public class AppConfigClientImpl implements AppConfigClient {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public AppBannerResponse findAppBannerList(AppBannerRequest request) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-MARKET/am-market/appconfig/getRecordList" ,request,
                        AppBannerResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBannerResponse insertAppBannerList(AdsWithBLOBsVO ads) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-MARKET/am-market/appconfig/insertRecord" ,ads,
                        AppBannerResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBannerResponse updateAppBannerList(AdsWithBLOBsVO ads) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-MARKET/am-market/appconfig/updateRecord" ,ads,
                        AppBannerResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBannerResponse updateAppBannerStatus(AdsWithBLOBsVO ads) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-MARKET/am-market/appconfig/updateStatus" ,ads,
                        AppBannerResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBannerResponse deleteAppBanner(AdsWithBLOBsVO ads) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-MARKET/am-market/appconfig/deleteAppBanner" ,ads,
                        AppBannerResponse.class)
                .getBody();
        return response;
    }
}
