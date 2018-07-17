package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.ChannelStatisticsDetailClient;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChannelStatisticsDetailClientImpl implements ChannelStatisticsDetailClient {

	@Autowired
	private RestTemplate restTemplate;
	@Override
	public ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/extensioncenter/channelstatisticsdetail/searchaction",
				request, ChannelStatisticsDetailResponse.class);
		
	}

}
