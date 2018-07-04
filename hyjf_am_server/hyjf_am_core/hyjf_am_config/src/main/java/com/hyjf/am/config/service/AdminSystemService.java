package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;



public interface AdminSystemService {

	public List<AdminSystem> selectLeftMenuTree(AdminSystem adminSystem);

//	public List<Integer> selectAdminGroup(AdminSystem adminSystem);

	public List<AdminSystem> getUserPermission(AdminSystem adminSystem);

	public AdminSystem getUserInfo(AdminSystem adminSystem);
	public List<Tree> selectLeftMenuTree2(String id);
	public AdminSystem getUserInfoByUserId(Integer userId);
}
