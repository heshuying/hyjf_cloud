/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.ContentEnvironmentRequestBean;
import com.hyjf.admin.client.ContentEnvironmentClient;
import com.hyjf.admin.service.ContentEnvironmentService;
import com.hyjf.am.response.admin.ContentEnvironmentResponse;
import com.hyjf.am.vo.config.ContentEnvironmentVO;

/**
 * @author fuqiang
 * @version ContentEnvironmentServiceImpl, v0.1 2018/7/11 11:15
 */
@Service
public class ContentEnvironmentServiceImpl implements ContentEnvironmentService {
	@Autowired
	private AmConfigClient amConfigClient;

	@Override
	public ContentEnvironmentResponse searchAction(ContentEnvironmentRequestBean requestBean) {
		return amConfigClient.searchAction(requestBean);
	}

	@Override
	public ContentEnvironmentResponse insertAction(ContentEnvironmentRequestBean requestBean) {
		return amConfigClient.insertAction(requestBean);
	}

	@Override
	public ContentEnvironmentResponse updateAction(ContentEnvironmentRequestBean requestBean) {
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public ContentEnvironmentResponse updateStatus(ContentEnvironmentRequestBean requestBean) {
		Integer id = requestBean.getId();
		ContentEnvironmentVO record = amConfigClient.getRecord(id);
		if (record.getStatus() == 1) {
			record.setStatus(0);
		} else if (record.getStatus() == 0) {
			record.setStatus(1);
		}
		BeanUtils.copyProperties(record, requestBean);
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public ContentEnvironmentResponse deleteById(Integer id) {
		return amConfigClient.deleteContentEnvironmentById(id);
	}
}
