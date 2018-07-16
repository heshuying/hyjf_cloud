/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.ContentQualifyRequestBean;
import com.hyjf.admin.client.ContentQualifyClient;
import com.hyjf.admin.service.ContentQualifyService;
import com.hyjf.am.response.admin.ContentQualifyResponse;
import com.hyjf.am.vo.config.ContentQualifyVO;

/**
 * @author fuqiang
 * @version ContentQualifyServiceImpl, v0.1 2018/7/11 9:34
 */
@Service
public class ContentQualifyServiceImpl implements ContentQualifyService {
	@Autowired
	private ContentQualifyClient contentQualifyClient;

	@Override
	public ContentQualifyResponse searchAction(ContentQualifyRequestBean requestBean) {
		return contentQualifyClient.searchAction(requestBean);
	}

	@Override
	public ContentQualifyResponse insertAction(ContentQualifyRequestBean requestBean) {
		return contentQualifyClient.insertAction(requestBean);
	}

	@Override
	public ContentQualifyResponse updateAction(ContentQualifyRequestBean requestBean) {
		return contentQualifyClient.updateAction(requestBean);
	}

	@Override
	public ContentQualifyResponse updateStatus(ContentQualifyRequestBean requestBean) {
		if (requestBean != null && requestBean.getId() != null) {
			Integer id = requestBean.getId();
			ContentQualifyVO record = contentQualifyClient.getRecord(id);
			if (record.getStatus() == 1) {
				record.setStatus(0);
			} else {
				record.setStatus(1);
			}
			BeanUtils.copyProperties(record, requestBean);
			return contentQualifyClient.updateAction(requestBean);
		}
		return null;
	}

	@Override
	public ContentQualifyResponse deleteById(Integer id) {
		return contentQualifyClient.deleteById(id);
	}
}
