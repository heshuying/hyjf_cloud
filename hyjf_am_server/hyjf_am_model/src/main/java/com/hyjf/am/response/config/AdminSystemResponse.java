package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.AdminMenuVO;
import com.hyjf.am.vo.config.AdminSystemVO;

/**
 * @author dongzeshan
 * @version AccountListResponse, v0.1 2018/6/20 10:52
 */
public class AdminSystemResponse extends Response<AdminSystemVO> {
	private AdminMenuVO adminMenu;

	public AdminMenuVO getAdminMenu() {
		return adminMenu;
	}

	public void setAdminMenu(AdminMenuVO adminMenu) {
		this.adminMenu = adminMenu;
	}
	
}
