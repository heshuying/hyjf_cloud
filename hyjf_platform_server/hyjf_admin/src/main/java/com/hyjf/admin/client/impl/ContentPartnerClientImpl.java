/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.am.response.config.LinkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.beans.request.ContentPartnerRequestBean;
import com.hyjf.admin.client.ContentPartnerClient;
import com.hyjf.am.vo.config.LinkVO;

/**
 * @author fuqiang
 * @version ContentPartnerClientImpl, v0.1 2018/7/12 10:18
 */
@Service
public class ContentPartnerClientImpl implements ContentPartnerClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public LinkResponse searchAction(ContentPartnerRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentpartner/searchaction", requestBean,
				LinkResponse.class);
	}

	@Override
	public LinkResponse insertAction(ContentPartnerRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentpartner/insertaction", requestBean,
				LinkResponse.class);
	}

	@Override
	public LinkResponse updateAction(ContentPartnerRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentpartner/updateaction", requestBean,
				LinkResponse.class);
	}

	@Override
	public LinkVO getRecord(Integer id) {
		LinkResponse response = restTemplate.getForObject(
				"http://AM-CONFIG/am-config/content/contentpartner/getrecord/" + id, LinkResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
}
