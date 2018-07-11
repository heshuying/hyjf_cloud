/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.admin.client.StzfWhiteConfigClient;
import com.hyjf.am.response.trade.STZHWhiteListResponse;

/**
 * @author fuqiang
 * @version StzfWhiteConfigClientImpl, v0.1 2018/7/10 9:45
 */
@Service
public class StzfWhiteConfigClientImpl implements StzfWhiteConfigClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public STZHWhiteListResponse selectSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-TRADE/am-trade/stzfwhiteconfig/selectSTZHWhiteList", requestBean,
				STZHWhiteListResponse.class);
	}

	@Override
	public STZHWhiteListResponse insertSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-TRADE/am-trade/stzfwhiteconfig/insertSTZHWhiteList", requestBean,
				STZHWhiteListResponse.class);
	}

	@Override
	public STZHWhiteListResponse updateSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-TRADE/am-trade/stzfwhiteconfig/updateSTZHWhiteList", requestBean,
				STZHWhiteListResponse.class);
	}
}
