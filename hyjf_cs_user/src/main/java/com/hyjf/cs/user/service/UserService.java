package com.hyjf.cs.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyjf.am.user.util.WebViewUser;
import com.hyjf.am.user.vo.UserVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.vo.RegisterVO;

/**
 * @author xiasq
 * @version UserService, v0.1 2018/4/11 9:34
 */
public interface UserService {
	UserVO register(RegisterVO registerVO, HttpServletRequest request, HttpServletResponse response) throws ReturnMessageException;

	boolean existUser(String mobile);

	WebViewUser getWebViewUserByUserId(Integer userId);

	void sendSmsCode(String validCodeType, String mobile, HttpServletRequest request) throws MQException;
}
