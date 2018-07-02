/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

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
    List<ActivityListVO> getRecordList(ActivityListRequest activityListRequest);

    /**
     * 添加活动信息
     * @param request
     * @return
     */
    int insertRecord(ActivityListRequest request);

    /**
     * 根据活动id查询活动信息
     * @param id
     * @return
     */
    ActivityListVO selectActivityById(int id);

    /**
     * 修改活动信息
     * @param request
     * @return
     */
    int updateActivity(ActivityListRequest request);

    /**
     * 删除活动配置信息
     * @param id
     * @return
     */
    int deleteActivity(int id);
}
