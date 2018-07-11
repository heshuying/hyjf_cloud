/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.beans.request.ContentQualifyRequestBean;
import com.hyjf.admin.client.ContentQualifyClient;
import com.hyjf.am.response.admin.ContentQualifyResponse;
import com.hyjf.am.vo.config.ContentQualifyVO;

/**
 * @author fuqiang
 * @version ContentQualifyClientImpl, v0.1 2018/7/11 9:54
 */
@Service
public class ContentQualifyClientImpl implements ContentQualifyClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ContentQualifyResponse searchAction(ContentQualifyRequestBean requestBean) {
		return null;
	}

	@Override
	public ContentQualifyResponse insertAction(ContentQualifyRequestBean requestBean) {
		return null;
	}

	@Override
	public ContentQualifyResponse updateAction(ContentQualifyRequestBean requestBean) {
		return null;
	}

	@Override
	public ContentQualifyVO getRecord(Integer id) {
		return null;
	}
}
