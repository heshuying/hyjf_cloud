/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AmHomePageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AmHomePageClientImpl, v0.1 2018/7/26 10:26
 */
@Cilent
public class AmHomePageClientImpl implements AmHomePageClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询首页banner
     * @param ads
     * @return
     */
    @Override
    public List<AppAdsCustomizeVO> searchBannerList(Map<String, Object> ads) {
        AppAdsCustomizeResponse response = restTemplate.postForObject(
                "http://AM-MARKET/am-market/am-market/homepage/getStartPage", ads,
                AppAdsCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
