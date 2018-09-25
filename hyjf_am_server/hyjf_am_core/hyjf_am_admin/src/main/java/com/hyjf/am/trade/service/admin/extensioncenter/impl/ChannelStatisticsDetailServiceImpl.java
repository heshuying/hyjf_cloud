/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.extensioncenter.impl;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.trade.dao.mapper.customize.ChannelStatisticsDetailCustomizeMapper;
import com.hyjf.am.trade.service.admin.extensioncenter.ChannelStatisticsDetailService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.user.dao.mapper.auto.UtmPlatMapper;
import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.dao.model.auto.UtmPlatExample;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tanyy
 * @version ChannelStatisticsDetailServiceImpl, v0.1 2018/7/16 14:34
 */
@Service
public class ChannelStatisticsDetailServiceImpl extends BaseServiceImpl implements ChannelStatisticsDetailService {
	@Autowired
	private ChannelStatisticsDetailCustomizeMapper channelStatisticsDetailCustomizeMapper;
	@Autowired
	private UtmPlatMapper utmPlatMapper;
	@Override
	public ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request) {
		ChannelStatisticsDetailResponse response = new ChannelStatisticsDetailResponse();
		List<ChannelStatisticsDetailVO> list = channelStatisticsDetailCustomizeMapper.queryRecordList(request);
		response.setResultList(list);
		return response;
	}
	@Override
	public IntegerResponse countList(ChannelStatisticsDetailRequest request){
		IntegerResponse response = new IntegerResponse();
		Integer count = channelStatisticsDetailCustomizeMapper.countList(request);
		response.setResultInt(count);
		return response;
	}
	@Override
	public UtmPlatResponse selectPcutmList(){
		UtmPlatResponse response = new UtmPlatResponse();
		UtmPlatExample utmPlat = new UtmPlatExample();
		UtmPlatExample.Criteria cra = utmPlat.createCriteria();
		// app渠道类别
		cra.andSourceTypeEqualTo(0);
		List<UtmPlatVO> toList = new ArrayList<>();
		List<UtmPlat> list = utmPlatMapper.selectByExample(utmPlat);
		for(UtmPlat utm:list){
			UtmPlatVO vo = new UtmPlatVO();
			BeanUtils.copyProperties(utm,vo);
			toList.add(vo);
		}
		response.setResultList(toList);
		return response;
	}

	@Override
	public UtmPlatResponse selectAppUtmList(){
		UtmPlatResponse response = new UtmPlatResponse();
		UtmPlatExample utmPlat = new UtmPlatExample();
		UtmPlatExample.Criteria cra = utmPlat.createCriteria();
		// app渠道类别
		cra.andSourceTypeEqualTo(1);
		List<UtmPlatVO> toList = new ArrayList<>();
		List<UtmPlat> list = utmPlatMapper.selectByExample(utmPlat);
		for(UtmPlat utm:list){
			UtmPlatVO vo = new UtmPlatVO();
			BeanUtils.copyProperties(utm,vo);
			toList.add(vo);
		}
		response.setResultList(toList);
		return response;
	}
}
