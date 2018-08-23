package com.hyjf.callcenter.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.callcenter.client.AmMarketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangjun
 * @version AmMarketClientImpl, v0.1 2018/7/6 17:15
 */
@Service
public class AmMarketClientImpl implements AmMarketClient {
	private static Logger logger = LoggerFactory.getLogger(AmMarketClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 查询优惠券内容(活动发放)
	 * @param activityId
	 * @return String
	 * @author wangjun
	 */
	@Override
	public String getCouponContent(int activityId){
		ActivityListResponse response = restTemplate
				.getForEntity("http://AM-MARKET/am-market/activity/selectActivity/"+ activityId,
						ActivityListResponse.class).getBody();
		if(Response.isSuccess(response)){
			return (response.getResult() != null ? response.getResult().getTitle() : null);
		}
		return null;
	}

}
