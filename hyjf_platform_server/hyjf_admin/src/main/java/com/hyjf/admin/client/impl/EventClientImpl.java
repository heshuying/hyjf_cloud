/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.beans.request.EventRequestBean;
import com.hyjf.admin.client.EventClient;
import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.vo.config.EventVO;

/**
 * @author fuqiang
 * @version EventClientImpl, v0.1 2018/7/11 17:52
 */
@Service
public class EventClientImpl implements EventClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public EventResponse searchAction(EventRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentevent/searchaction",
				requestBean, EventResponse.class);
	}

	@Override
	public EventResponse insertAction(EventRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentevent/insertaction",
				requestBean, EventResponse.class);
	}

	@Override
	public EventResponse updateAction(EventRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentevent/updateaction",
				requestBean, EventResponse.class);
	}

	@Override
	public EventVO getRecord(Integer id) {
		EventResponse response = restTemplate.getForObject(
				"http://AM-CONFIG/am-config/content/contentevent/getrecord/" + id,
				EventResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public EventResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentevent/delete/" + id,
				EventResponse.class);
	}
}
