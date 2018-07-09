package com.hyjf.admin.service;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.service.BaseService;


public interface BaseAdminService extends BaseService{

	/**
	 * 查询用户对象
	 * @param userName
	 * @return
	 */
	UserVO getUserByUserName(String userName);

}
