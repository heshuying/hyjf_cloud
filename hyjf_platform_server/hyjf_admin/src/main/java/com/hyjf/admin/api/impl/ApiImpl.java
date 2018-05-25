package com.hyjf.admin.api.impl;

import com.hyjf.admin.api.Api;
import com.hyjf.admin.api.ApiUtil;
import com.hyjf.am.response.config.GatewayApiConfigResponse;
import com.hyjf.am.vo.config.GatewayApiConfigVO;
import com.hyjf.ribbon.EurekaInvokeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiasq
 * @version ApiImpl, v0.1 2018/5/22 11:19
 */
@Service
public class ApiImpl implements Api {
	private Logger logger = LoggerFactory.getLogger(ApiImpl.class);
	private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();

	@Override
	public List<GatewayApiConfigVO> test() {
		GatewayApiConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/gateConfig/findAll", GatewayApiConfigResponse.class)
				.getBody();
		logger.info("response is : {}", response.getResultList());

		return response.getResultList();
	}
}
