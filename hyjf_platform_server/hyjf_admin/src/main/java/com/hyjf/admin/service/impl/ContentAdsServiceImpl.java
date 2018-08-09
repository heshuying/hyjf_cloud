package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.client.ContentAdsClient;
import com.hyjf.admin.service.ContentAdsService;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 内容中心 -广告管理
 *
 * @author：yinhui
 * @Date: 2018/7/19  14:22
 */
@Service
public class ContentAdsServiceImpl implements ContentAdsService {

    @Autowired
    private AmMarketClient amMarketClient;

    @Override
    public ContentAdsResponse searchAction(ContentAdsRequest request) {
        return amMarketClient.searchAction(request);
    }

    @Override
    public ContentAdsResponse inserAction(ContentAdsRequest request) {
        return amMarketClient.inserAction(request);
    }

    @Override
    public ContentAdsResponse updateAction(ContentAdsRequest request) {
        return amMarketClient.updateAction(request);
    }

    @Override
    public ContentAdsResponse deleteById(Integer id) {
        return amMarketClient.deleteById(id);
    }
}
