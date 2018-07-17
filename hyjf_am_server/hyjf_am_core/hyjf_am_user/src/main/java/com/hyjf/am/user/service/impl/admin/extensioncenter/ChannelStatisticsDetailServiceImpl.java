/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl.admin.extensioncenter;

import com.hyjf.am.response.admin.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.ChannelStatisticsDetailRequest;
import com.hyjf.am.user.dao.mapper.customize.ChannelStatisticsDetailCustomizeMapper;
import com.hyjf.am.user.service.admin.extensioncenter.ChannelStatisticsDetailService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanyy
 * @version ChannelStatisticsDetailServiceImpl, v0.1 2018/7/16 14:34
 */
@Service
public class ChannelStatisticsDetailServiceImpl extends BaseServiceImpl implements ChannelStatisticsDetailService {
	@Autowired
	private ChannelStatisticsDetailCustomizeMapper channelStatisticsDetailCustomizeMapper;

	@Override
	public ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request) {
		ChannelStatisticsDetailResponse response = new ChannelStatisticsDetailResponse();
		//channelStatisticsDetailCustomizeMapper.queryRecordList();
		return response;
	}

}
