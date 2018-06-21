package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.Ads;

/**
 * @author xiasq
 * @version AdsService, v0.1 2018/4/19 15:42
 */
public interface AdsService {
    void updateActivityEndStatus();

    Ads findActivityById(Integer id);
}
