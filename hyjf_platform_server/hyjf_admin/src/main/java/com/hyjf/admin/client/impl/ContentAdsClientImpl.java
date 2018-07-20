package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.ContentAdsClient;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 内容中心 -广告管理
 * @author：yinhui
 * @Date: 2018/7/19  14:25
 */
@Service
public class ContentAdsClientImpl implements ContentAdsClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ContentAdsResponse searchAction(ContentAdsRequest request) {
        return restTemplate.postForEntity("http://AM-MARKET/am-market/content/contentads/searchaction",
                request, ContentAdsResponse.class).getBody();
    }

    @Override
    public ContentAdsResponse inserAction(ContentAdsRequest request) {
        return restTemplate.postForObject("http://AM-MARKET/am-market/content/contentads/inseraction",
                request, ContentAdsResponse.class);
    }

    @Override
    public ContentAdsResponse updateAction(ContentAdsRequest request) {
        return restTemplate.postForObject("http://AM-MARKET/am-market/content/contentads/updateaction",
                request, ContentAdsResponse.class);
    }

    @Override
    public ContentAdsResponse deleteById(Integer id) {
        return restTemplate.getForObject("http://AM-MARKET/am-market/content/contentads/delete/" + id, ContentAdsResponse.class);
    }
}
