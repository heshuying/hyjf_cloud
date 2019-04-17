package com.hyjf.wbs.client;

import com.hyjf.am.vo.user.UserVO;

/**
 * @author cui
 * @version AmUserClient, v0.1 2019/4/17 12:44
 */
public interface AmUserClient {

	/**
	 * 根据userId查询User
	 * @param userId
	 * @return
	 */
	UserVO findUserById(int userId);


}
