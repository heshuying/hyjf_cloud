package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.market.AdsResponse;
import com.hyjf.am.vo.market.AdsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.cs.user.client.AmMarketClient;

/**
 * @author xiasq
 * @version AmMarketClientImpl, v0.1 2018/5/14 16:11
 */
@Service
public class AmMarketClientImpl implements AmMarketClient {
	private static Logger logger = LoggerFactory.getLogger(AmMarketClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 根据活动id查询活动
	 * 
	 * @param integer
	 * @return
	 */
	@Override
	public AdsVO findAdsById(Integer activityId) {
		AdsResponse response = restTemplate
				.getForEntity("http://AM-MARKET/am-market/activity/findAdsById/" + activityId, AdsResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
}
