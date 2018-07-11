/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.admin.client.ProtocolsClient;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;

/**
 * @author fuqiang
 * @version ProtocolsClientImpl, v0.1 2018/7/10 17:18
 */
@Service
public class ProtocolsClientImpl implements ProtocolsClient {
	@Override
	public FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequestBean request) {
		return null;
	}

	@Override
	public FddTempletCustomizeResponse insertAction(ProtocolsRequestBean requestBean) {
		return null;
	}

	@Override
	public FddTempletCustomizeResponse updateAction(ProtocolsRequestBean requestBean) {
		return null;
	}
}
