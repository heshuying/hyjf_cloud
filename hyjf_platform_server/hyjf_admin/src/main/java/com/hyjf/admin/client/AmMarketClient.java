package com.hyjf.admin.client;


import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;
import com.hyjf.am.vo.admin.DuibaPointsVO;
import com.hyjf.am.vo.market.ActivityListVO;

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
     * 获取活动详情
     * @param id
     * @return
     */
    ActivityListResponse getInfoById(Integer id);

    /**
     * 获取新年活动奖励明细列表
     * @param requestBean
     * @return
     */
    NewYearActivityRewardResponse selectAwardList(NewYearNineteenRequestBean requestBean);

    /**
     * 获取奖励明细详情
     * @param request
     * @return
     */
    NewYearActivityRewardResponse selectAwardInfo(NewYearNineteenRequestBean request);

    /**
     * 修改奖励发放状态
     * @param request
     * @return
     */
    BooleanResponse updateStatus(NewYearNineteenRequestBean request);

    /**
     * 查询兑吧积分明细
     * @param requestBean
     * @return
     */
    DuibaPointsResponse selectDuibaPointsList(DuibaPointsRequest requestBean);

    /**
     * 兑吧积分账户修改明细
     *
     * @param requestBean
     * @return
     */
    DuibaPointsModifyResponse selectDuibaPointsModifyList(DuibaPointsRequest requestBean);

    /**
     * 插入积分审批表
     *
     * @param duibaPointsModifyVO
     * @return
     */
    boolean insertPointsModifyList(DuibaPointsModifyVO duibaPointsModifyVO);

    /**
     * 更新兑吧积分调整审批状态
     *
     * @param requestBean
     * @return
     */
    boolean updatePointsModifyStatus(DuibaPointsRequest requestBean);

    /**
     * 插入兑吧交易明细表
     *
     * @param duibaPointsVO
     * @return
     */
    boolean insertDuibaPoints(DuibaPointsVO duibaPointsVO);

    /**
     * 根据订单号获取订单详情
     *
     * @param orderId
     * @return
     */
    DuibaPointsModifyVO selectDuibaPointsModifyByOrdid(String orderId);
}
