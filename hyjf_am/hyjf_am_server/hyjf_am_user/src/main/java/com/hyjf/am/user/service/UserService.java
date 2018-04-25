package com.hyjf.am.user.service;

import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.user.dao.model.auto.Users;
import com.hyjf.am.user.dao.model.auto.UsersInfo;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ServiceException;

/**
 * @author xiasq
 * @version UserService, v0.1 2018/1/21 22:42
 */
public interface UserService {
	/**
	 * 注册
	 * @param userRequest
	 * @return
	 * @throws ServiceException
	 */
	Users register(RegisterUserRequest userRequest) throws ServiceException;

	/**
	 * 获取用户
	 * @param userId
	 * @return
	 */
	Users findUserByUserId(int userId);

	/**
	 * 获取用户
	 * @param userId
	 * @return
	 */
	UsersInfo findUsersInfo(int userId);

	/**
	 * 生成唯一用户id
	 * 
	 * @param mobile
	 * @return
	 */
	String generateUniqueUsername(String mobile);

	/**
	 * 根据手机号查找用户信息
	 * 
	 * @param mobile
	 * @return
	 */
	Users findUserByMobile(String mobile);

	/**
	 * 根据username或者mobiel查询用户
	 * @param condition
	 * @return
	 */
	Users findUserByUsernameOrMobile(String condition);

	/**
	 * 根据推荐人手机号或userId查询推荐人
	 * 
	 * @param reffer
	 * @return
	 */
	Users findUserByRecommendName(String reffer);

	/**
	 * 组装user信息
	 * 
	 * @param userVO
	 * @return
	 */
	UserVO assembleUserVO(UserVO userVO);

	void updateLoginUser(int userId, String ip);
}
