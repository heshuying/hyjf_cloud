package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.dao.model.customize.app.ActivityListCustomize;
import com.hyjf.am.resquest.market.ActivityListRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version ActivityService, v0.1 2018/4/19 15:42
 */
public interface ActivityService {
    ActivityList selectActivityList(int activityId);

    /**
     *查询活动列表条数
     * @param request
     * @return
     */
    int countActivityList(ActivityListRequest request);

    /**
     * 获取活动列表
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    List<ActivityList> getRecordList(ActivityListRequest request, int offset, int limit);

    /**
     * 添加活动
     * @param activityList
     * @return
     */
    Map<String, Object> insertRecord(ActivityList activityList);

    /**
     * 更改活动
     * @param activityList
     * @return
     */
    Map<String, Object> updateActivity(ActivityList activityList);

    /**
     * 删除活动
     * @param id
     * @return
     */
    Map<String, Object> deleteActivity(int id);

    /**
     * 获取有效活动
     * @param request
     * @return
     */
    List<ActivityListCustomize> selectRecordListValid(ActivityListRequest request, int i, int i1);
}
