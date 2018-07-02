package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.ActivityList;
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
     * @param mapParam
     * @return
     */
    int countActivityList(Map<String, Object> mapParam);

    /**
     * 获取活动列表
     * @param mapParam
     * @param offset
     * @param limit
     * @return
     */
    List<ActivityList> getRecordList(Map<String, Object> mapParam, int offset, int limit);

    /**
     * 添加活动
     * @param activityList
     * @return
     */
    int insertRecord(ActivityList activityList);

    /**
     * 更改活动
     * @param activityList
     * @return
     */
    int updateActivity(ActivityList activityList);

    /**
     * 删除活动
     * @param id
     * @return
     */
    int deleteActivity(int id);
}
