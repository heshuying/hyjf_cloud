/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.am.resquest.config.STZHWhiteListRequestBean;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.StzfWhiteConfigService;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version StzfWhiteConfigServiceImpl, v0.1 2018/7/10 9:21
 */
@Service
public class StzfWhiteConfigServiceImpl implements StzfWhiteConfigService {

	@Autowired
	private AmTradeClient amTradeClient;
	@Autowired
	private AmAdminClient amAdminClient;

	@Override
	public STZHWhiteListResponse selectSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
		return amTradeClient.selectSTZHWhiteList(requestBean);
	}

	@Override
	public STZHWhiteListResponse insertSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
		return amTradeClient.insertSTZHWhiteList(requestBean);
	}

	@Override
	public STZHWhiteListResponse updateSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
		return amTradeClient.updateSTZHWhiteList(requestBean);
	}

	@Override
	public HjhInstConfigVO selectHjhInstConfig(String instcode) {
		return amTradeClient.selectHjhInstConfig(instcode);
	}

    @Override
    public STZHWhiteListResponse selectSTZHWhiteById(Integer id) {
        return amTradeClient.selectSTZHWhiteById(id);
    }

	@Override
	public List<HjhInstConfigVO> getRegionList() {
		return amTradeClient.selectHjhInstConfigList();
	}

    @Override
    public STZHWhiteListResponse getUserByUserName(STZHWhiteListRequestBean requestBean) {
        return amAdminClient.getUserByUserName(requestBean);
    }
}
