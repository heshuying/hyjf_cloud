package com.hyjf.am.user.service;

import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.user.dao.model.auto.Users;
import com.hyjf.common.exception.ServiceException;

/**
 * @author xiasq
 * @version UserService, v0.1 2018/1/21 22:42
 */
public interface UserService {
	Users register(RegisterUserRequest userRequest) throws ServiceException;

	Users findUserByUserId(int userId);

	/**
	 * 生成唯一用户id
	 * 
	 * @param mobile
	 * @return
	 */
	String generateUniqueUsername(String mobile);

	/**
	 * 根据手机号查找用户信息
	 * @param mobile
	 * @return
	 */
	Users findUserByMobile(String mobile);

	/**
	 * 根据推荐人手机号或userId查询推荐人
	 * @param reffer
	 * @return
	 */
	Users findUserByRecommendName(String reffer);
}
