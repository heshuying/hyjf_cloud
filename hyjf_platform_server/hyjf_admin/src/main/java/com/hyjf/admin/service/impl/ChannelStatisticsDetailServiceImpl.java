package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.client.ChannelStatisticsDetailClient;
import com.hyjf.admin.service.ChannelStatisticsDetailService;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelStatisticsDetailServiceImpl  implements ChannelStatisticsDetailService {
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmTradeClient amTradeClient;
	@Override
	public ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request){
		ChannelStatisticsDetailResponse amUserResponse = amUserClient.searchChannelStatisticsDetail(request);
		List<ChannelStatisticsDetailVO> list = amUserResponse.getResultList();
		if(!CollectionUtils.isEmpty(list)){
			Integer [] userIds = new Integer[list.size()];
			for (int i=0;i<list.size();i++){
				userIds[i] = list.get(i).getUserId();
			}
			request.setUserIds(userIds);
			ChannelStatisticsDetailResponse amTradeResponse = amUserClient.searchChannelStatisticsDetail(request);
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
