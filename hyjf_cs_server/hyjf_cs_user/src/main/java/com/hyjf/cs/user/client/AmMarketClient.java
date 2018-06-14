package com.hyjf.cs.user.client;

import com.hyjf.am.vo.market.AdsVO;

/**
 * @author xiasq
 * @version AmMarketClient, v0.1 2018/5/14 16:11
 */
public interface AmMarketClient {
    /**
     * 根据活动id查询活动
     * @param integer
     * @return
     */
    AdsVO findAdsById(Integer activityId);
}
