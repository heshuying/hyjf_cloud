/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.ContentJobRequestBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.ContentJobService;
import com.hyjf.am.response.config.JobResponse;
import com.hyjf.am.vo.config.JobsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version ContentJobServiceImpl, v0.1 2018/7/12 14:14
 */
@Service
public class ContentJobServiceImpl implements ContentJobService {
	@Autowired
	private AmConfigClient amConfigClient;

	@Override
	public JobResponse searchAction(ContentJobRequestBean requestBean) {
		return amConfigClient.searchAction(requestBean);
	}

	@Override
	public int insertAction(ContentJobRequestBean requestBean) {
		return amConfigClient.insertAction(requestBean);
	}

	@Override
	public int updateAction(ContentJobRequestBean requestBean) {
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public int updateStatus(ContentJobRequestBean requestBean) {
		if (requestBean != null && requestBean.getId() != null) {
			Integer id = requestBean.getId();
			JobsVo record = amConfigClient.getJobsRecord(id);
			ContentJobRequestBean bean = new ContentJobRequestBean();
			BeanUtils.copyProperties(record, bean);
			bean.setStatus(requestBean.getStatus());
			return amConfigClient.updateAction(requestBean);
		}
		return 0;
	}

	@Override
	public int deleteById(Integer id) {
		return amConfigClient.deleteJobById(id);
	}
}
