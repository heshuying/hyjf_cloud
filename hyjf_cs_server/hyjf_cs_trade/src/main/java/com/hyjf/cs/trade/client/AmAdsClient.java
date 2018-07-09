package com.hyjf.cs.trade.client;


import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;

import java.util.List;

public interface AmAdsClient {

    /**
     * 查询广告列表
     * @author zhangyk
     * @date 2018/7/5 15:32
     */
    List<AppAdsCustomizeVO> getBannerList(AdsRequest request);
}
