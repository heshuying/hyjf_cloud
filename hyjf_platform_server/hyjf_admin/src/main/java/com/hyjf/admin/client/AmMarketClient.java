package com.hyjf.admin.client;


import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;
import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.market.AdsVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmMarketClient, v0.1 2018/5/14 16:11
 */
public interface AmMarketClient {

    List<ActivityListVO> getActivityList();


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

     AppBannerResponse findAppBannerList(AppBannerRequest request);

     AppBannerResponse insertAppBannerList(AdsVO adsVO);

     AppBannerResponse updateAppBannerList(AdsVO adsVO);

     AppBannerResponse updateAppBannerStatus(AdsVO adsVO);

     AppBannerResponse deleteAppBanner(AdsVO adsVO);

    ContentAdsResponse searchAction(ContentAdsRequest request);

    ContentAdsResponse inserAction(ContentAdsRequest request);

    ContentAdsResponse infoaction(Integer id);

    ContentAdsResponse updateAction(ContentAdsRequest request);

    ContentAdsResponse deleteById(Integer id);

    /**
     * 获取广告类型下拉列表
     * @return
     */
    ContentAdsResponse getAdsTypeList();

    /**
     * 根据活动ID获取活动title
     * @param activityId 活动ID
     * @return title
     */
    CouponTenderResponse getActivityById(Integer activityId);

    List<ActivityListCustomizeVO> getActivityList(ActivityListCustomizeVO request);

    /**
     * 获取发送消息列表
     * @param bean
     * @return
     */
    MessagePushNoticesResponse getRecordList(MessagePushNoticesRequest bean);

    /**
     * 增加发送消息列表
     * @param bean
     * @return
     */
    MessagePushNoticesResponse insertRecord(MessagePushNoticesRequest bean);

    /**
     * 删除消息列表
     * @param bean
     * @return
     */
    MessagePushNoticesResponse deleteRecord(MessagePushNoticesRequest bean);

    /**
     * 删除消息列表
     * @param bean
     * @return
     */
    MessagePushNoticesResponse updateRecord(MessagePushNoticesRequest bean);

    /**
     * 获取发送历史消息列表
     * @param request
     * @return
     */
    MessagePushHistoryResponse getRecordList(MessagePushHistoryRequest request);
    /**
     * 获取消息推送标签列表
     * @return
     */
    MessagePushTagResponse getAllPushTagList();

    /**
     * 根据id查询单条消息
     * @param bean
     * @return
     */
    MessagePushNoticesResponse getRecord(MessagePushNoticesRequest bean);


    /**
     * 获取标签列表
     * @return
     */
    MessagePushTagResponse getTagList();

    /**
     * 根据id获取广告
     * @param adsVO
     * @return
     */
    AppBannerResponse getRecordById(AdsVO adsVO);

    /**
     * 获取活动详情
     * @param id
     * @return
     */
    ActivityListResponse getInfoById(Integer id);
}
