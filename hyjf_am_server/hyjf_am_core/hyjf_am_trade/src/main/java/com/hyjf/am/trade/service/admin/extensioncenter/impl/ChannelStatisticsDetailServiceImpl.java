/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.extensioncenter.impl;

import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.trade.dao.mapper.customize.admin.ChannelStatisticsDetailCustomizeMapper;
import com.hyjf.am.trade.service.admin.extensioncenter.ChannelStatisticsDetailService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
		List<ChannelStatisticsDetailVO> list = channelStatisticsDetailCustomizeMapper.queryRecordList(request);
		response.setResultList(list);
		return response;
	}

}
