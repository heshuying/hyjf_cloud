package com.hyjf.cs.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyjf.common.web.WebViewUser;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.vo.RegisterVO;

/**
 * 
 * @author Administrator
 *
 */
public interface BankOpenService {
	
	UserVO register(RegisterVO registerVO, HttpServletRequest request, HttpServletResponse response) throws ReturnMessageException;

	boolean existUser(String mobile);

	WebViewUser getWebViewUserByUserId(Integer userId);

	void sendSmsCode(String validCodeType, String mobile, HttpServletRequest request) throws MQException;
}
