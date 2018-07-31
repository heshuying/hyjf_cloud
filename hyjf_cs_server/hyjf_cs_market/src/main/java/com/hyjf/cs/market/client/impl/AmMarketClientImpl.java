package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.response.trade.CalculateInvestInterestResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListBeanVO;
import com.hyjf.cs.market.client.AmMarketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 11:18
 * @Description: AmMarketClientImpl
 */
@Service
public class AmMarketClientImpl implements AmMarketClient {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Integer queryActivityCount(ActivityListRequest activityListRequest) {
        ActivityListResponse response = restTemplate.postForObject(
                "http://AM-MARKET/am-market/activity/queryactivitycount",activityListRequest,
                ActivityListResponse.class);
        if (response != null) {
            return response.getCount();
        }
        return null;
    }

    @Override
    public List<ActivityListBeanVO> queryActivityList(ActivityListRequest activityListRequest) {
        ActivityListResponse response = restTemplate.postForObject(
                "http://AM-MARKET/am-market/activity/queryactivitylist",activityListRequest,
                ActivityListResponse.class);
        if (response != null) {
            return response.getActivityList();
        }
        return null;
    }
}
