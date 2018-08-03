/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.cs.message.client.AmTradeClient;

/**
 * @author fq
 * @version AmTradeClientImpl, v0.1 2018/7/31 11:42
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public TotalInvestAndInterestVO getTotalInvestAndInterest() {
		TotalInvestAndInterestResponse response = restTemplate.getForObject(
				"http://AM-TRADE/am-trade/totalinvestandinterest/gettotalinvestandinterest",
				TotalInvestAndInterestResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
}
