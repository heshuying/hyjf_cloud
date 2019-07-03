package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.customize.app.AppAdsCustomize;
import com.hyjf.am.market.dao.model.customize.app.AppFindAdCustomize;
import com.hyjf.am.resquest.market.AdsRequest;

import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version AdsService, v0.1 2018/4/19 15:42
 */
public interface AdsService {
    void updateActivityEndStatus();

    Ads findActivityById(Integer id);


    List<AppAdsCustomize> searchBanner(Map<String, Object> ads);

    List<Ads> getBannerList(AdsRequest request);

    /**
     * 根据广告类型获取广告
     * @author liuyang
     * @param adsType
     * @return
     */
    public List<Ads> getAdsList(String adsType);

    /**
     * 获取发现页广告位
     * @param request
     * @return
     * @author wgx
     */
    AppFindAdCustomize searchAppFindAdCustomize(AdsRequest request);
}
