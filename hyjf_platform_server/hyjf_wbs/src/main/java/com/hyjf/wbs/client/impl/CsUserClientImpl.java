/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.exception.CheckException;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.wbs.client.CsUserClient;
import com.hyjf.wbs.qvo.csuser.LoginRequestVO;

/**
 * @author cui
 * @version CsUserClientImpl, v0.1 2019/4/18 18:12
 */
@Service
public class CsUserClientImpl implements CsUserClient {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${cs.user.service.name}")
	private String userService;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public WebViewUserVO login(LoginRequestVO user) {
		String url = "/hyjf-web/user/login";
		WebResult webResult = restTemplate.getForEntity(url, WebResult.class).getBody();
		if (webResult != null) {
			if (WebResult.SUCCESS.equals(webResult.getStatus())) {
				return (WebViewUserVO) webResult.getData();
			}

		}
		throw new CheckException("用户登录失败！");
	}
}
