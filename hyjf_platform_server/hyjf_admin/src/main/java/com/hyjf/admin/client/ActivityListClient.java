/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListVO;

import java.util.List;

/**
 * @author yaoy
 * @version ActivityListClient, v0.1 2018/6/26 19:24
 */
public interface ActivityListClient {
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
}
