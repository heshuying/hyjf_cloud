package com.hyjf.admin.client.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.vo.market.ActivityListVO;

/**
 * @author zhangqingqing
 * @version AmMarketClientImpl, v0.1 2018/5/14 16:11
 */
@Service
public class AmMarketClientImpl implements AmMarketClient {
	private static Logger logger = LoggerFactory.getLogger(AmMarketClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<ActivityListVO> getActivityList() {
		ActivityListResponse response = restTemplate.getForEntity(
				"http://AM-MARKET/am-market/activity/getActivityList",
				ActivityListResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public String getActivityTitle(Integer activityId) {
		CouponTenderResponse response = restTemplate
				.getForEntity("http://AM-MARKET/am-market/activity/hztgetactivitytitle/" + activityId,
						CouponTenderResponse.class)
				.getBody();
		if (response != null) {
			return response.getAttrbute();
		}
		return null;
	}
}
