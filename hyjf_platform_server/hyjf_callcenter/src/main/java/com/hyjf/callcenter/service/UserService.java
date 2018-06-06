package com.hyjf.callcenter.service;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.beans.Users;

/**
 * @author libin
 * @version UserService, v0.1 2018/6/5
 */
public interface UserService {
	
	/**
	 * 查询用户接口类
	 * @author libin
	 * @version v0.1
	 * @since v0.1 2018/6/5
	 */
	UserVO getUser(UserBean bean);

}
