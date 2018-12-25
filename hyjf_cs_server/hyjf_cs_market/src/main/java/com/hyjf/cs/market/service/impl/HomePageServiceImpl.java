/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.user.EvalationCustomizeVO;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.client.AmUserClient;
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
    private AmMarketClient amMarketClient;

    @Autowired
    private AmUserClient amUserClient;

    /**
     * 查询首页banner
     * @param ads
     * @return
     */
    @Override
    public List<AppAdsCustomizeVO> searchBannerList(Map<String, Object> ads) {
        return amMarketClient.searchBannerList(ads);
    }

    /**
     * 获取评分标准列表
     * @return
     * @author Michael
     */
    @Override
    public List<EvalationCustomizeVO> getEvalationRecord() {
        return amUserClient.getEvalationRecord();
    }
}
