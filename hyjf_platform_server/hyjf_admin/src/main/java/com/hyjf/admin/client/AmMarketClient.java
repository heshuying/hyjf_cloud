package com.hyjf.admin.client;


import com.hyjf.am.vo.market.ActivityListVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmMarketClient, v0.1 2018/5/14 16:11
 */
public interface AmMarketClient {

    List<ActivityListVO> getActivityList();

    /**
     * 获取活动title
     * @param activityId 活动id
     * @return
     */
    String getActivityTitle(Integer activityId);
}
