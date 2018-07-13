/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.ContentJobRequestBean;
import com.hyjf.admin.client.ContentJobClient;
import com.hyjf.admin.service.ContentJobService;
import com.hyjf.am.response.config.JobResponse;
import com.hyjf.am.vo.config.JobsVo;

/**
 * @author fuqiang
 * @version ContentJobServiceImpl, v0.1 2018/7/12 14:14
 */
@Service
public class ContentJobServiceImpl implements ContentJobService {
	@Autowired
	private ContentJobClient contentJobClient;

	@Override
	public JobResponse searchAction(ContentJobRequestBean requestBean) {
		return contentJobClient.searchAction(requestBean);
	}

	@Override
	public JobResponse insertAction(ContentJobRequestBean requestBean) {
		return contentJobClient.insertAction(requestBean);
	}

	@Override
	public JobResponse updateAction(ContentJobRequestBean requestBean) {
		return contentJobClient.updateAction(requestBean);
	}

	@Override
	public JobResponse updateStatus(ContentJobRequestBean requestBean) {
		if (requestBean != null && requestBean.getId() != null) {
			Integer id = requestBean.getId();
			JobsVo record = contentJobClient.getRecord(id);
			if (record.getStatus() == 1) {
				record.setStatus(0);
			} else {
				record.setStatus(1);
			}
			BeanUtils.copyProperties(record, requestBean);
			return contentJobClient.updateAction(requestBean);
		}
		return null;
	}
}
