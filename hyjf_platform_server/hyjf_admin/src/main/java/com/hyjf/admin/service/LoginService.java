package com.hyjf.admin.service;

import java.util.List;

import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;



public interface LoginService {

	//查询用户权限
	public List<AdminSystemVO> getUserPermission(String userName);
	//查询用户信息
	public AdminSystemResponse getUserInfo(AdminSystemRequest adminSystemRequest);
	//查询菜单权限
	public List<TreeVO> selectLeftMenuTree2(String id);

}
