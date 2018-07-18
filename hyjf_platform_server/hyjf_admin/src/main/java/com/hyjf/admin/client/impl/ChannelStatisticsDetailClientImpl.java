package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.ChannelStatisticsDetailClient;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ChannelStatisticsDetailClientImpl implements ChannelStatisticsDetailClient {

	@Autowired
	private RestTemplate restTemplate;
	@Override
	public ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request) {
		ChannelStatisticsDetailResponse amUserResponse = restTemplate.postForObject("http://AM-USER/am-user/extensioncenter/channelstatisticsdetail/searchaction",
				request, ChannelStatisticsDetailResponse.class);
		List<ChannelStatisticsDetailVO> list = amUserResponse.getResultList();
		if(!CollectionUtils.isEmpty(list)){
			Integer [] userIds = new Integer[list.size()];
			for (int i=0;i<list.size();i++){
				userIds[i] = list.get(i).getUserId();
			}
			request.setUserIds(userIds);
			ChannelStatisticsDetailResponse amTradeResponse = restTemplate.postForObject("http://AM-TRADE/am-trade/extensioncenter/channelstatisticsdetail/searchaction",
					request, ChannelStatisticsDetailResponse.class);
			if(!CollectionUtils.isEmpty( amTradeResponse.getResultList())) {
				for (int i = 0; i < list.size(); i++) {
					ChannelStatisticsDetailVO vo = list.get(i);
					for (int j = 0; j < amTradeResponse.getResultList().size(); j++) {
						if (vo.getUserId().equals(amTradeResponse.getResultList().get(j).getUserId())) {
							vo.setCumulativeInvest(amTradeResponse.getResultList().get(j).getCumulativeInvest());
							vo.setHtjInvest(amTradeResponse.getResultList().get(j).getHtjInvest());
							vo.setInvestAmount(amTradeResponse.getResultList().get(j).getInvestAmount());
						}
					}
				}
				amUserResponse.setResultList(list);
			}
		}
		return  amUserResponse;
		
	}

}
