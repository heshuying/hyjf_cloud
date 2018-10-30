package com.hyjf.cs.message.client.impl;

import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.response.market.AdsResponse;
import com.hyjf.cs.message.client.AmMarketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



/**
 * @author lisheng
 * @version AmMarketClientImpl, v0.1 2018/7/31 15:11
 */
@Service
public class AmMarketClientImpl implements AmMarketClient {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ActivityListResponse getActivity(int day) {
        ActivityListResponse response = restTemplate
                .getForEntity("http://AM-MARKET/am-market/activity/getActivity/"+day+"/",
                        ActivityListResponse.class)
                .getBody();
        return response;
    }
    @Override
    public AdsResponse getAdsList(String adsType){
        AdsResponse response = restTemplate
                .getForEntity("http://AM-MARKET//am-market/ads/getAdsList/"+adsType+"/",
                        AdsResponse.class)
                .getBody();
        return response;
    }


}
