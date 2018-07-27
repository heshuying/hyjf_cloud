package com.hyjf.cs.market.client;

import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListBeanVO;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 11:18
 * @Description: AmMarketClient
 */
public interface AmMarketClient {
    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据条件查询活动列表总数
     * @Date 11:04 2018/7/26
     * @Param activityListRequest
     * @return
     */
    Integer queryActivityCount(ActivityListRequest activityListRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据条件分页查询数据
     * @Date 11:05 2018/7/26
     * @Param activityListRequest
     * @return List
     */
    List<ActivityListBeanVO> queryActivityList(ActivityListRequest activityListRequest);
}
