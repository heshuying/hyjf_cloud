package com.hyjf.admin.client;


import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmMarketClient, v0.1 2018/5/14 16:11
 */
public interface AmMarketClient {

    List<ActivityListVO> getActivityList();

    /**
     * 获取活动title
     * @param activityId 活动id
     * @return
     */
    String getActivityTitle(Integer activityId);

    /**
     * 查询活动列表
     * @param activityListRequest
     * @return
     */
    ActivityListResponse getRecordList(ActivityListRequest activityListRequest);

    /**
     * 添加活动信息
     * @param request
     * @return
     */
    ActivityListResponse insertRecord(ActivityListRequest request);

    /**
     * 根据活动id查询活动信息
     * @param activityListRequest
     * @return
     */
    ActivityListResponse selectActivityById(ActivityListRequest activityListRequest);

    /**
     * 修改活动信息
     * @param request
     * @return
     */
    ActivityListResponse updateActivity(ActivityListRequest request);

    /**
     * 删除活动配置信息
     * @param request
     * @return
     */
    ActivityListResponse deleteActivity(ActivityListRequest request);

    public AppBannerResponse findAppBannerList(AppBannerRequest request);

    public AppBannerResponse insertAppBannerList(AdsWithBLOBsVO ads);

    public AppBannerResponse updateAppBannerList(AdsWithBLOBsVO ads);

    public AppBannerResponse updateAppBannerStatus(AdsWithBLOBsVO ads);

    public AppBannerResponse deleteAppBanner(AdsWithBLOBsVO ads);

    ContentAdsResponse searchAction(ContentAdsRequest request);

    ContentAdsResponse inserAction(ContentAdsRequest request);

    ContentAdsResponse updateAction(ContentAdsRequest request);

    ContentAdsResponse deleteById(Integer id);

    /**
     * 根据活动ID获取活动title
     * @param activityId 活动ID
     * @return title
     */
    CouponTenderResponse getActivityById(Integer activityId);

    List<ActivityListCustomizeVO> getActivityList(ActivityListCustomizeVO request);
}
