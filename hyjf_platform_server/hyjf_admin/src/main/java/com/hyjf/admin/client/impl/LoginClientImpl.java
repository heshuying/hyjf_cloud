package com.hyjf.admin.client.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.LoginClient;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.TreeResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;

@Service
public class LoginClientImpl implements LoginClient {
	private static final Logger logger = LoggerFactory.getLogger(LoginClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<AdminSystemVO> getUserPermission(String userName) {
		AdminSystemResponse adminSystemResponse = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/adminSystem/getpermissions/" + userName,
						AdminSystemResponse.class)
				.getBody();
		if (adminSystemResponse != null) {
			return adminSystemResponse.getResultList();
		}
		return null;

	}

	@Override
	public AdminSystemResponse getUserInfo(AdminSystemRequest adminSystemRequest) {
		AdminSystemResponse adminSystemResponse = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/adminSystem/getuser/" + adminSystemRequest,
						AdminSystemResponse.class)
				.getBody();
		if (adminSystemResponse != null) {
			return adminSystemResponse;
		}
		return null;
	}

	@Override
	public List<TreeVO> selectLeftMenuTree2(String userId) {
		TreeResponse treeResponse = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/adminSystem/selectLeftMenuTree/" + userId, TreeResponse.class)
				.getBody();
		if (treeResponse != null) {
			return treeResponse.getResultList();
		}
		return null;

	}

}
