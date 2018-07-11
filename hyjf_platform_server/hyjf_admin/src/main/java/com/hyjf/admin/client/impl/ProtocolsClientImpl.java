/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.admin.client.ProtocolsClient;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import org.springframework.web.client.RestTemplate;

/**
 * @author fuqiang
 * @version ProtocolsClientImpl, v0.1 2018/7/10 17:18
 */
@Service
public class ProtocolsClientImpl implements ProtocolsClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequestBean request) {
		return restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/selectfddtempletlist", request, FddTempletCustomizeResponse.class);
	}

	@Override
	public FddTempletCustomizeResponse insertAction(ProtocolsRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/insertaction", requestBean, FddTempletCustomizeResponse.class);
	}

	@Override
	public FddTempletCustomizeResponse updateAction(ProtocolsRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/updateaction", requestBean, FddTempletCustomizeResponse.class);
	}
}
