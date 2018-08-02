package com.hyjf.admin.client;


import com.hyjf.am.vo.market.ActivityListVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmMarketClient, v0.1 2018/5/14 16:11
 */
public interface AmMarketClient {

    List<ActivityListVO> getActivityList();
}
