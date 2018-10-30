package com.hyjf.cs.message.client;

import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.response.market.AdsResponse;
import com.hyjf.am.response.market.AppBannerResponse;

/**
 * @author lisheng
 * @version AmMarketClient, v0.1 2018/7/31 15:10
 */

public interface AmMarketClient {

    ActivityListResponse getActivity(int day);

    /**
     *
     * 根据广告类型获取广告
     * @author liuyang
     * @param adsType
     * @return
     */
    AdsResponse getAdsList(String adsType);

}
