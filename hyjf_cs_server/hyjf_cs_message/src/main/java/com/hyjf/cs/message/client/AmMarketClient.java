package com.hyjf.cs.message.client;

import com.hyjf.am.response.admin.MessagePushNoticesResponse;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import com.hyjf.am.vo.market.ActivityListVO;

import java.util.List;

/**
 * @author lisheng
 * @version AmMarketClient, v0.1 2018/7/31 15:10
 */

public interface AmMarketClient {

    ActivityListResponse getActivity(int day);



}
