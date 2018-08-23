/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.AdsCustomizeMapper;
import com.hyjf.am.market.dao.mapper.auto.AdsMapper;
import com.hyjf.am.market.dao.mapper.customize.app.AppAdsCustomizeMapper;
import com.hyjf.am.market.dao.model.customize.app.AppAdsCustomize;
import com.hyjf.am.market.service.AppHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AppHomePageServiceImpl, v0.1 2018/7/26 10:58
 */
@Service
public class AppHomePageServiceImpl implements AppHomePageService {

    @Autowired
    AppAdsCustomizeMapper appAdsCustomizeMapper;

    /**
     * 查询首页的bannner列表
     */
    @Override
    public List<AppAdsCustomize> searchBannerList(Map<String, Object> ads) {
        List<AppAdsCustomize> adsList = appAdsCustomizeMapper.selectAdsList(ads);
        return adsList;
    }
}
