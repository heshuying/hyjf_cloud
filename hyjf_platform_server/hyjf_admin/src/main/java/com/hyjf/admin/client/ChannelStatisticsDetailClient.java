package com.hyjf.admin.client;

import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;

public interface ChannelStatisticsDetailClient {

	public ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request);

}
