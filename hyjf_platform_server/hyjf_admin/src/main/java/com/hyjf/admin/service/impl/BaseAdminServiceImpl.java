package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmDataCollectClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseAdminServiceImpl extends BaseServiceImpl implements BaseAdminService {

	public final Logger logger = LoggerFactory.getLogger(BaseAdminServiceImpl.class);

	@Autowired
	public AmUserClient amUserClient;

	@Autowired
	public AmTradeClient amTradeClient;

	@Autowired
	public AmConfigClient amConfigClient;

	@Autowired
	public AmDataCollectClient amDataCollectClient;

	@Autowired
	public SystemConfig systemConfig;

	@Override
	public UserVO getUserByUserName(String userName) {
		UserVO user = amUserClient.getUserByUserName(userName);
		return user;
	}
}
