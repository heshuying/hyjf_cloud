/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.ContentEnvironmentRequestBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.ContentEnvironmentService;
import com.hyjf.am.response.admin.ContentEnvironmentResponse;
import com.hyjf.am.vo.config.ContentEnvironmentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public int insertAction(ContentEnvironmentRequestBean requestBean) {
		return amConfigClient.insertAction(requestBean);
	}

	@Override
	public int updateAction(ContentEnvironmentRequestBean requestBean) {
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public int updateStatus(ContentEnvironmentRequestBean requestBean) {
		if (requestBean != null && requestBean.getId() != null) {
			Integer id = requestBean.getId();
			ContentEnvironmentVO record = amConfigClient.getRecord(id);
			ContentEnvironmentRequestBean bean = new ContentEnvironmentRequestBean();
			BeanUtils.copyProperties(record, bean);
			bean.setStatus(requestBean.getStatus());
			return amConfigClient.updateAction(requestBean);
		}
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		return amConfigClient.deleteContentEnvironmentById(id);
	}
}
