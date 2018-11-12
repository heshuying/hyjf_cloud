/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.response.user.EvalationCustomizeResponse;
import com.hyjf.am.response.user.EvalationResultResponse;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.user.EvalationCustomizeVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AmHomePageClient;
import org.springframework.beans.factory.annotation.Autowired;
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
                "http://AM-MARKET/am-market/homepage/getStartPage", ads,
                AppAdsCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取评分标准列表
     * @return
     */
    @Override
    public List<EvalationCustomizeVO> getEvalationRecord() {
        EvalationCustomizeResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/user/getEvalationRecord", EvalationCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
