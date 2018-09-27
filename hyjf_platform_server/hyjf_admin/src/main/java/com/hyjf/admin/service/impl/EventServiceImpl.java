/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.EventRequestBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.EventService;
import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.vo.config.EventVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version EventServiceImpl, v0.1 2018/7/11 17:36
 */
@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private AmConfigClient amConfigClient;

	@Override
	public EventResponse searchAction(EventRequestBean requestBean) {
		return amConfigClient.searchAction(requestBean);
	}

	@Override
	public int insertAction(EventRequestBean requestBean) {
		return amConfigClient.insertAction(requestBean);
	}

	@Override
	public int updateAction(EventRequestBean requestBean) {
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public int updateStatus(EventRequestBean requestBean) {
		Integer id = requestBean.getId();
		EventVO record = amConfigClient.getEventRecord(id);
		EventRequestBean bean = new EventRequestBean();
		BeanUtils.copyProperties(record, bean);
		bean.setStatus(requestBean.getStatus());
		return amConfigClient.updateAction(bean);
	}

	@Override
	public int deleteById(Integer id) {
		return amConfigClient.deleteEventById(id);
	}

	@Override
	public EventVO selectById(EventRequestBean requestBean) {
		return amConfigClient.getEventRecord(requestBean.getId());
	}
}
