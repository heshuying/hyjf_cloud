package com.hyjf.am.market.dao.mapper.customize.app;

import com.hyjf.am.vo.market.ActivityListBeanVO;
import com.hyjf.am.vo.market.ActivityListCustomizeVO;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 15:12
 * @Description: AppActivityListCustomizeMapper
 */
public interface AppActivityListCustomizeMapper {
    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  APP根据条件查询活动列表总数
     * @Date 15:37 2018/7/26
     * @Param activityListCustomize
     * @return
     */
    Integer queryactivitycount(ActivityListCustomizeVO activityListCustomize);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  APP根据条件分页查询数据
     * @Date 15:37 2018/7/26
     * @Param activityListCustomize
     * @return
     */
    List<ActivityListCustomizeVO> queryActivityList(ActivityListCustomizeVO activityListCustomize);
}
