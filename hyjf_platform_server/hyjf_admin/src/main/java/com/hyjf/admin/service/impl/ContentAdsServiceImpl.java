package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.service.ContentAdsService;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import com.hyjf.am.vo.admin.AdsVO;
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
    public ContentAdsResponse infoaction(Integer id) {
        return amMarketClient.infoaction(id);
    }

    @Override
    public ContentAdsResponse updateAction(ContentAdsRequest request) {
        return amMarketClient.updateAction(request);
    }

    @Override
    public ContentAdsResponse statusaction(Integer id) {
        ContentAdsResponse adsresponse = amMarketClient.infoaction(id);
        AdsVO adsVO = adsresponse.getResult().getRecordList().get(0);
        if (adsVO.getStatus() == 1) {
            adsVO.setStatus(0);
        } else {
            adsVO.setStatus(1);
        }
        ContentAdsRequest request = new ContentAdsRequest();
        request.setAds(adsVO);
        ContentAdsResponse response = amMarketClient.updateAction(request);
        return response;
    }

    @Override
    public ContentAdsResponse deleteById(Integer id) {
        return amMarketClient.deleteById(id);
    }

    @Override
    public ContentAdsResponse adsTypeList() {
        return amMarketClient.getAdsTypeList();
    }
}
