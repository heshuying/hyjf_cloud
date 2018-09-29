/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.cs.market.client.AmHomePageClient;
import com.hyjf.cs.market.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version HomePageServiceImpl, v0.1 2018/7/26 10:22
 */
@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private AmHomePageClient amHomePageClient;

    /**
     * 查询首页banner
     * @param ads
     * @return
     */
    @Override
    public List<AppAdsCustomizeVO> searchBannerList(Map<String, Object> ads) {
        return amHomePageClient.searchBannerList(ads);
    }
}
