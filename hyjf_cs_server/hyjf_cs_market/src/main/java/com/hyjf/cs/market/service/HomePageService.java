/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service;

import com.hyjf.am.vo.market.AppAdsCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version HomePageService, v0.1 2018/7/26 10:17
 */
public interface HomePageService {

    /**
     * 查询首页banner
     * @param ads
     * @return
     */
    List<AppAdsCustomizeVO> searchBannerList(Map<String, Object> ads);
}
