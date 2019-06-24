/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.client.impl;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.response.user.WebViewUserResponse;
import com.hyjf.am.resquest.user.LoginUserRequest;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.wbs.client.AmUserClient;

/**
 * @author cui
 * @version AmUserClientImpl, v0.1 2019/4/17 16:57
 */
@Service
public class AmUserClientImpl implements AmUserClient {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${am.user.service.name}")
	private String userService;

	@Autowired
	private RestTemplate restTemplate;


	@Override
	public WebViewUserVO getWebViewUserByUserId(Integer userId, String channel) {
		WebViewUserResponse response = restTemplate
				.getForEntity(userService + "/user/getWebViewUserByUserId/"+ userId+"/"+channel, WebViewUserResponse.class).getBody();
		return response.getResult();
	}

	@Override
	public void updateUser(UserVO u, String ipAddr) {
		LoginUserRequest request = new LoginUserRequest(u.getUserId(), ipAddr,u);
		String url = userService+"/user/updateUser";
		logger.info("url:{}", url);
		restTemplate.postForEntity(url, request, BooleanResponse.class);
	}
}
