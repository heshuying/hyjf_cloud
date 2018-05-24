package com.hyjf.admin.impl;

import com.hyjf.admin.Api;
import com.hyjf.am.response.config.GatewayApiConfigResponse;
import com.hyjf.ribbon.EurekaInvokeClient;
import com.hyjf.ribbon.EurekaRestTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiasq
 * @version ApiImpl, v0.1 2018/5/22 11:19
 */
@Service
public class ApiImpl implements Api {
	private Logger logger = LoggerFactory.getLogger(ApiImpl.class);

	private RestTemplate client = EurekaInvokeClient.getInstance().build();
	private RestTemplate restTemplate = EurekaRestTemplateBuilder.build();

	@Override
	public void test() {
		GatewayApiConfigResponse response = client
				.getForEntity("http://AM-CONFIG/am-config/gateConfig/findAll", GatewayApiConfigResponse.class)
				.getBody();
		logger.info("response is : {}", response.getResultList());
	}
}
