package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.ActivityListMapper;
import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.dao.model.auto.ActivityListExample;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.market.service.ActivityService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version ActivityServiceImpl, v0.1 2018/4/19 15:42
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityListMapper activityListMapper;

    /**
     * 活动是否过期
     */
    @Override
    public ActivityList selectActivityList(int activityId) {
        ActivityList activityList = activityListMapper.selectByPrimaryKey(activityId);
        return activityList;
    }

    /**
     * 根据条件获取活动列表总数
     *
     * @param request
     * @return
     */
    @Override
    public int countActivityList(ActivityListRequest request) {
        int activitycount = 0;
        ActivityListExample example = new ActivityListExample();
        ActivityListExample.Criteria criteria = example.createCriteria();
        if (request.getTitle() != null || request.getStartTime() != 0 ) {
            criteria.andTitleEqualTo(request.getTitle()).
                    andTimeStartEqualTo(request.getStartTime()).
                    andTimeEndEqualTo(request.getEndTime()).
                    andCreateTimeBetween(GetDate.str2Timestamp(request.getStartCreate()), GetDate.str2Timestamp(request.getEndCreate()));
        }
        activitycount = activityListMapper.countByExample(example);
        return activitycount;

    }

    /**
     * 根据条件查询活动列表
     *
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<ActivityList> getRecordList(ActivityListRequest request, int offset, int limit) {
        ActivityListExample example = new ActivityListExample();
        if (offset != -1) {
            example.setLimitStart(offset);
            example.setLimitEnd(limit);
        }
        example.setOrderByClause("`create_time` Desc");
        return activityListMapper.selectByExample(example);
    }

    /**
     * 添加活动
     *
     * @param activityList
     * @return
     */
    @Override
    public int insertRecord(ActivityList activityList) {
        activityList.setCreateTime(GetDate.getTimestamp());
        activityList.setUpdateTime(GetDate.getTimestamp());
        int insertFlag = activityListMapper.insertSelective(activityList);
        return insertFlag;
    }

    @Override
    public int updateActivity(ActivityList activityList) {
        activityList.setUpdateTime(GetDate.getTimestamp());
        int updateFlag = activityListMapper.updateByPrimaryKeySelective(activityList);
        return updateFlag;
    }

    @Override
    public int deleteActivity(int id) {
        int deleteFlag = activityListMapper.deleteByPrimaryKey(id);
        return deleteFlag;
    }


}
