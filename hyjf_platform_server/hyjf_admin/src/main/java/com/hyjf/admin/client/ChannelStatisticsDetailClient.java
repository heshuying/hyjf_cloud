package com.hyjf.admin.client;

import com.hyjf.am.response.admin.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.ChannelStatisticsDetailRequest;

public interface ChannelStatisticsDetailClient {

	public ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request);

}
