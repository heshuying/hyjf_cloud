package com.hyjf.callcenter.service;

import com.hyjf.am.vo.trade.RUserVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;

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

	/**
	 * 根据用户ID查询推荐人信息
	 * @param userId
	 * @return
	 */
	RUserVO getRefereerInfoByUserId(Integer userId);

}
