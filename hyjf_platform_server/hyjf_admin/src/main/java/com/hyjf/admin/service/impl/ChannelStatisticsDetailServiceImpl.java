package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.ChannelStatisticsDetailClient;
import com.hyjf.admin.service.ChannelStatisticsDetailService;
import com.hyjf.am.response.admin.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.ChannelStatisticsDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelStatisticsDetailServiceImpl  implements ChannelStatisticsDetailService {
	@Autowired
	private ChannelStatisticsDetailClient channelStatisticsDetailClient;
	@Override
	public ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request){
		return  channelStatisticsDetailClient.searchAction(request);
	}

}
