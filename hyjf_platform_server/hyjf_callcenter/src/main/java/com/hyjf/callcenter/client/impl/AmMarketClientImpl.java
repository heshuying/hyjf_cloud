package com.hyjf.callcenter.client.impl;

import com.hyjf.callcenter.client.AmMarketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangjun
 * @version AmMarketClientImpl, v0.1 2018/7/6 17:15
 */
@Service
public class AmMarketClientImpl implements AmMarketClient {
	private static Logger logger = LoggerFactory.getLogger(AmMarketClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

}
