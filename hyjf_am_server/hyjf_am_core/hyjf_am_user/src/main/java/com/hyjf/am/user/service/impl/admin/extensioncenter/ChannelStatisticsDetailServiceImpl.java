/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl.admin.extensioncenter;

import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.user.dao.mapper.customize.ChannelStatisticsDetailCustomizeMapper;
import com.hyjf.am.user.service.admin.extensioncenter.ChannelStatisticsDetailService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import com.hyjf.common.paginator.Paginator;
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
		int count = channelStatisticsDetailCustomizeMapper.countRecordTotal(request);
		response.setCount(count);
		if(count>0){
			Paginator paginator = new Paginator(request.getCurrPage(), count);
			request.setLimitStart(paginator.getOffset());
			request.setLimitEnd(paginator.getLimit());
			List<ChannelStatisticsDetailVO> list = channelStatisticsDetailCustomizeMapper.queryRecordList(request);
			response.setResultList(list);
		}
		return response;
	}

}
