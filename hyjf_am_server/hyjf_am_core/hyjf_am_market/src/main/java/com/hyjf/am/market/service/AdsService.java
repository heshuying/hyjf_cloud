package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.customize.app.AppAdsCustomize;

import java.util.Map;

/**
 * @author xiasq
 * @version AdsService, v0.1 2018/4/19 15:42
 */
public interface AdsService {
    void updateActivityEndStatus();

    Ads findActivityById(Integer id);


    AppAdsCustomize searchBanner(Map<String, Object> ads);
}
