/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client;

import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.user.EvalationCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version HomePageClient, v0.1 2018/7/26 10:24
 */
public interface AmHomePageClient {

    /**
     * 查询首页banner
     * @param ads
     * @return
     */
    List<AppAdsCustomizeVO> searchBannerList(Map<String, Object> ads);

    /**
     * 获取评分标准列表
     * @return
     */
    List<EvalationCustomizeVO> getEvalationRecord();
}
