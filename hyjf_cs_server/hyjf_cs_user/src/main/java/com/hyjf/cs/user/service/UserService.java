package com.hyjf.cs.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.vo.RegisterVO;

/**
 * @author xiasq
 * @version UserService, v0.1 2018/4/11 9:34
 */
public interface UserService {
	/**
	 * 注册
	 * 
	 * @param registerVO
	 * @param request
	 * @param response
	 * @return
	 * @throws ReturnMessageException
	 */
	UserVO register(RegisterVO registerVO, HttpServletRequest request, HttpServletResponse response)
			throws ReturnMessageException;

	/**
	 * 用户存在检查
	 * 
	 * @param mobile
	 * @return
	 */
	boolean existUser(String mobile);

	/**
	 *
	 * @param loginUserName
	 *            可以是手机号或者用户名
	 * @param loginPassword
	 * @param ip
	 */
	UserVO login(String loginUserName, String loginPassword, String ip);
}
