/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.am.response.config.LinkResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.ContentPartnerRequestBean;
import com.hyjf.admin.client.ContentPartnerClient;
import com.hyjf.admin.service.ContentPartnerService;
import com.hyjf.am.vo.config.LinkVO;

/**
 * @author fuqiang
 * @version ContentPartnerServiceImpl, v0.1 2018/7/12 9:54
 */
@Service
public class ContentPartnerServiceImpl implements ContentPartnerService {
	@Autowired
	private ContentPartnerClient contentPartnerClient;

	@Override
	public LinkResponse searchAction(ContentPartnerRequestBean requestBean) {
		return contentPartnerClient.searchAction(requestBean);
	}

	@Override
	public LinkResponse insertAction(ContentPartnerRequestBean requestBean) {
		return contentPartnerClient.insertAction(requestBean);
	}

	@Override
	public LinkResponse updateAction(ContentPartnerRequestBean requestBean) {
		return contentPartnerClient.updateAction(requestBean);
	}

	@Override
	public LinkResponse updateStatus(ContentPartnerRequestBean requestBean) {
		Integer id = requestBean.getId();
		LinkVO record = contentPartnerClient.getRecord(id);
		if (record.getStatus() == 1) {
			record.setStatus(0);
		} else if (record.getStatus() == 0) {
			record.setStatus(1);
		}
		BeanUtils.copyProperties(record, requestBean);
		return contentPartnerClient.updateAction(requestBean);
	}
}
