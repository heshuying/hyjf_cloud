/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.EventRequestBean;
import com.hyjf.admin.client.EventClient;
import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.vo.config.EventVO;

/**
 * @author fuqiang
 * @version EventClientImpl, v0.1 2018/7/11 17:52
 */
@Service
public class EventClientImpl implements EventClient {
	@Override
	public EventResponse searchAction(EventRequestBean requestBean) {
		return null;
	}

	@Override
	public EventResponse insertAction(EventRequestBean requestBean) {
		return null;
	}

	@Override
	public EventResponse updateAction(EventRequestBean requestBean) {
		return null;
	}

	@Override
	public EventVO getRecord(Integer id) {
		return null;
	}
}
