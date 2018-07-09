package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.cs.trade.client.AmAdsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 广告client
 * @author zhangyk
 * @date 2018/7/5 15:15
 */
@Service
public class AmAdsClientImpl implements AmAdsClient {


    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<AppAdsCustomizeVO> getBannerList(AdsRequest request) {
        AppAdsCustomizeResponse response = restTemplate.postForEntity("http://AM-MARKET/am-market/ads/searchBanner",request,AppAdsCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }
}
