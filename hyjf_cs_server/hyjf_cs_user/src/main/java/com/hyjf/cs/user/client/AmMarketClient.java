package com.hyjf.cs.user.client;

import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.market.DuiBaPointsDetailRequest;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.market.DuiBaPointsDetailVO;

import java.util.List;

/**
 * @author xiasq
 * @version AmMarketClient, v0.1 2018/5/14 16:11
 */
public interface AmMarketClient {
    /**
     * 根据活动id查询活动
     * @param
     * @return
     */
    AdsVO findAdsById(Integer activityId);

    ActivityListVO selectActivityList(int activityId);

    AppAdsCustomizeVO searchBanner(AdsRequest adsRequest);

    List<ActivityListVO> getActivityList();

    DuiBaPointsDetailVO getPointsDetail(DuiBaPointsDetailRequest request);
}
