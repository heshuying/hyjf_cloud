/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.admin.client.ProtocolsClient;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;

/**
 * @author fuqiang
 * @version ProtocolsServiceImpl, v0.1 2018/7/10 16:54
 */
@Service
public class ProtocolsServiceImpl implements ProtocolsService {
	@Autowired
	private ProtocolsClient protocolsClient;

	@Override
	public FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequestBean request) {
		return protocolsClient.selectFddTempletList(request);
	}

	@Override
	public FddTempletCustomizeResponse insertAction(ProtocolsRequestBean requestBean) {
		return protocolsClient.insertAction(requestBean);
	}

	@Override
	public FddTempletCustomizeResponse updateAction(ProtocolsRequestBean requestBean) {
		return protocolsClient.updateAction(requestBean);
	}
}
