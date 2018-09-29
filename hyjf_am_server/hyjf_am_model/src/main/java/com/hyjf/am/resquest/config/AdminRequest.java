package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.admin.AdminCustomizeVO;

import java.util.List;

/**
 * @author dongzeshan
 * @version AccountListResponse, v0.1 2018/6/20 10:52
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
public class AdminRequest extends AdminCustomizeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3424437222447388803L;
	private int adminId;
	private List<Integer> ids;
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	
	
}
