package com.hyjf.admin.service.impl;

import java.util.List;

import com.hyjf.admin.client.AmConfigClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyjf.admin.service.LoginService;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl  implements LoginService {
	@Autowired
	private AmConfigClient amConfigClient;
	
	@Override
	public List<AdminSystemVO> getUserPermission(String userName) {
		
		return amConfigClient.getUserPermission(userName);
	}

	@Override
	public AdminSystemResponse getUserInfo(AdminSystemRequest adminSystemRequest) {

		return amConfigClient.getUserInfo(adminSystemRequest);
	}

	@Override
	public List<TreeVO> selectLeftMenuTree2(String id) {
		return amConfigClient.selectLeftMenuTree2(id);
	}

}
