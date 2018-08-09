/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.admin.client.StzfWhiteConfigClient;
import com.hyjf.admin.service.StzfWhiteConfigService;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.vo.user.HjhInstConfigVO;

/**
 * @author fuqiang
 * @version StzfWhiteConfigServiceImpl, v0.1 2018/7/10 9:21
 */
@Service
public class StzfWhiteConfigServiceImpl implements StzfWhiteConfigService {

	@Autowired
	private StzfWhiteConfigClient stzfWhiteConfigClient;

	@Override
	public STZHWhiteListResponse selectSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
		return stzfWhiteConfigClient.selectSTZHWhiteList(requestBean);
	}

	@Override
	public STZHWhiteListResponse insertSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
		return stzfWhiteConfigClient.insertSTZHWhiteList(requestBean);
	}

	@Override
	public STZHWhiteListResponse updateSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
		return stzfWhiteConfigClient.updateSTZHWhiteList(requestBean);
	}

	@Override
	public HjhInstConfigVO selectHjhInstConfig(String instcode) {
		return stzfWhiteConfigClient.selectHjhInstConfig(instcode);
	}
}
