/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.ContentLandingPageRequestBean;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.admin.client.ContentLandingPageClient;
import com.hyjf.admin.client.WhereaboutsPageClient;
import com.hyjf.am.response.config.LandingPageResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author tanyy
 * @version WhereaboutsPageClientImpl, v0.1 2018/7/16 14:27
 */
@Service
public class WhereaboutsPageClientImpl implements WhereaboutsPageClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public WhereaboutsPageResponse searchAction(WhereaboutsPageRequestBean requestBean) {
		WhereaboutsPageResponse amUserResponse = restTemplate.postForObject("http://AM-USER/am-user/content/whereaboutspage/searchaction",
				requestBean, WhereaboutsPageResponse.class);
		return  amUserResponse;

	}

	@Override
	public WhereaboutsPageResponse insertAction(WhereaboutsPageRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-USER/am-user/content/whereaboutspage/insert",
				requestBean, WhereaboutsPageResponse.class);
	}

	@Override
	public WhereaboutsPageResponse updateAction(WhereaboutsPageRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-USER/am-user/content/whereaboutspage/update",
				requestBean, WhereaboutsPageResponse.class);
	}


	@Override
	public WhereaboutsPageResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://AM-USER/am-user/content/whereaboutspage/delete/" + id,
				WhereaboutsPageResponse.class);
	}
}
