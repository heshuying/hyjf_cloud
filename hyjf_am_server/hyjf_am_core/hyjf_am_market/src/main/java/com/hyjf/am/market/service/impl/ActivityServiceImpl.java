package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.ActivityListMapper;
import com.hyjf.am.market.dao.mapper.customize.market.ActivityListCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.dao.model.auto.ActivityListExample;
import com.hyjf.am.market.dao.model.customize.app.ActivityListCustomize;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.market.service.ActivityService;

import javax.validation.Valid;
import java.util.HashMap;
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
    @Autowired
    private ActivityListCustomizeMapper activityListCustomizeMapper;

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
    public Map<String, Object> insertRecord(ActivityList activityList) {
        activityList.setCreateTime(GetDate.getTimestamp());
        activityList.setUpdateTime(GetDate.getTimestamp());
        int insert = activityListMapper.insertSelective(activityList);
        Map<String,Object> map = new HashMap<>();
        if (insert > 0) {
            map.put("success", true);
        } else {
            map.put("msg", "添加失败");
        }
        return map;
    }

    @Override
    public Map<String, Object> updateActivity(ActivityList activityList) {
        activityList.setUpdateTime(GetDate.getTimestamp());
        int update = activityListMapper.updateByPrimaryKey(activityList);
        Map<String,Object> map = new HashMap<>();
        if (update > 0) {
            map.put("success", true);
        } else {
            map.put("msg", "修改失败");
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteActivity(int id) {
        int delete = activityListMapper.deleteByPrimaryKey(id);
        Map<String,Object> map = new HashMap<>();
        if (delete > 0) {
            map.put("success", true);
        } else {
            map.put("msg", "删除失败");
        }
        return map;
    }

    /**
     * 获取有效活动列表
     * @param request
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<ActivityListCustomize> selectRecordListValid(ActivityListRequest request, int limitStart, int limitEnd) {
        ActivityListCustomize example = new ActivityListCustomize();
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setNowTime(GetDate.getNowTime10());
        return activityListCustomizeMapper.queryActivityListValid(example);
    }


}
