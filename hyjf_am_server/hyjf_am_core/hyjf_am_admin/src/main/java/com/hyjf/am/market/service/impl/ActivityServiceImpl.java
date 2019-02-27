package com.hyjf.am.market.service.impl;

import com.hyjf.am.admin.config.SystemConfig;
import com.hyjf.am.market.dao.mapper.auto.ActivityListMapper;
import com.hyjf.am.market.dao.mapper.customize.app.AppActivityListCustomizeMapper;
import com.hyjf.am.market.dao.mapper.customize.market.ActivityListCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.dao.model.auto.ActivityListExample;
import com.hyjf.am.market.dao.model.customize.app.ActivityListCustomize;
import com.hyjf.am.market.service.ActivityService;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListBeanVO;
import com.hyjf.am.vo.market.ActivityListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiasq
 * @version ActivityServiceImpl, v0.1 2018/4/19 15:42
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    ActivityListMapper activityListMapper;
    @Resource
    private ActivityListCustomizeMapper ActivityListCustomizeMapper;
    @Resource
    private AppActivityListCustomizeMapper AppActivityListCustomizeMapper;
    @Resource
    private SystemConfig systemConfig;

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
        if (StringUtils.isNotBlank(request.getTitle()) ) {
            criteria.andTitleEqualTo(request.getTitle());
        }
        if (request.getStartTime() != null && request.getEndTime() != null) {
            String startTime = request.getStartTime() + " 00:00:00";
            String endTime = request.getEndTime() + " 23:59:59";
            criteria.andTimeStartBetween(GetDate.strYYYYMMDDHHMMSS2Timestamp2(startTime), GetDate.strYYYYMMDDHHMMSS2Timestamp2(endTime));
        }
        if (request.getStartCreate() != null && request.getEndCreate() != null) {
            String startCreate = request.getStartCreate() + " 00:00:00";
            String endCreate = request.getEndCreate() + " 23:59:59";
            criteria.andCreateTimeBetween(GetDate.stringToDate(startCreate), GetDate.stringToDate(endCreate));
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
        if (StringUtils.isNotBlank(request.getTitle())) {
            example.createCriteria().andTitleEqualTo(request.getTitle());
        }
        if (request.getStartTime() != null && request.getEndTime() != null) {
            String startTime = request.getStartTime() + " 00:00:00";
            String endTime = request.getEndTime() + " 23:59:59";
            example.createCriteria().andTimeStartBetween(GetDate.strYYYYMMDDHHMMSS2Timestamp2(startTime), GetDate.strYYYYMMDDHHMMSS2Timestamp2(endTime));
        }
        if (request.getStartCreate() != null && request.getEndCreate() != null) {
            String startCreate = request.getStartCreate() + " 00:00:00";
            String endCreate = request.getEndCreate() + " 23:59:59";
            example.createCriteria().andCreateTimeBetween(GetDate.stringToDate(startCreate), GetDate.stringToDate(endCreate));
        }
        example.setOrderByClause("create_time Desc");
        return activityListMapper.selectByExample(example);
    }

    /**
     * 根据日期条件查询活动列表
     * @return
     */
    @Override
    public List<ActivityList> getActivity(int day) {
        ActivityListExample example = new ActivityListExample();
        ActivityListExample.Criteria criteria = example.createCriteria();
        criteria.andTimeStartLessThan(day);
        criteria.andTimeEndGreaterThan(day);
        example.setOrderByClause(" create_time desc ");
        return activityListMapper.selectByExample(example);
    }

    /**
     *
     * @return
     */
    @Override
    public List<ActivityList> getActivityList() {
        ActivityListExample example = new ActivityListExample();
        return activityListMapper.selectByExample(example);
    }

    /**
     * 获取活动详情
     * @param id
     * @return
     */
    @Override
    public ActivityList getActivityInfo(Integer id) {
        ActivityList list = activityListMapper.selectByPrimaryKey(id);
        return list;
    }

    /**
     * 添加活动
     *
     * @param activityList
     * @return
     */
    @Override
    public int insertRecord(ActivityList activityList) {
        activityList.setCreateTime(GetDate.getDate());
        activityList.setUpdateTime(GetDate.getDate());
        int insert = activityListMapper.insertSelective(activityList);
        return insert;
    }

    @Override
    public int updateActivity(ActivityList activityList) {
        activityList.setUpdateTime(GetDate.getDate());
        int update = activityListMapper.updateByPrimaryKey(activityList);
        return update;
    }

    @Override
    public int deleteActivity(int id) {
        int delete = activityListMapper.deleteByPrimaryKey(id);
        return delete;
    }

    /**
     * 获取有效活动列表
     * @param request
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<ActivityListCustomize> selectRecordListValid(ActivityListCustomize request, int limitStart, int limitEnd) {
        ActivityListCustomize example = new ActivityListCustomize();
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setNowTime(GetDate.getNowTime10());
        return ActivityListCustomizeMapper.queryActivityListValid(example);
    }

    @Override
    public Integer queryactivitycount(ActivityListRequest activityListRequest) {
        ActivityListCustomizeVO activityListCustomize = dealActivityParam(activityListRequest);
        return AppActivityListCustomizeMapper.queryactivitycount(activityListCustomize);
    }

    @Override
    public List<ActivityListBeanVO> queryActivityList(ActivityListRequest activityListRequest) {
        ActivityListCustomizeVO activityListCustomize = dealActivityParam(activityListRequest);
        List<ActivityListCustomizeVO> activitys = AppActivityListCustomizeMapper.queryActivityList(activityListCustomize);
        List<ActivityListBeanVO> beanList= CommonUtils.convertBeanList(activitys,ActivityListBeanVO.class);
        return beanList;
    }

    public ActivityListCustomizeVO dealActivityParam(ActivityListRequest activityListRequest){
        ActivityListCustomizeVO activityListCustomize =new ActivityListCustomizeVO();
        activityListCustomize.setPlatform(activityListRequest.getPlatform());
        activityListCustomize.setHost(systemConfig.getWebHost());
        Integer page = activityListRequest.getPage();
        Integer pageSize = activityListRequest.getPageSize();

        int offSet = (page - 1) * pageSize;
        if (offSet == 0 || offSet > 0) {
            activityListCustomize.setLimitStart(offSet);
        }
        if (pageSize > 0) {
            activityListCustomize.setLimitEnd(pageSize);
        }
        return activityListCustomize;
    }


}
