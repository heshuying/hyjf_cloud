/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.customize.app.AppAdsCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AppHomePageService, v0.1 2018/7/26 10:58
 */
public interface AppHomePageService {

    /**
     * 查询首页的bannner列表
     */
    List<AppAdsCustomize> searchBannerList(Map<String, Object> ads);
}
