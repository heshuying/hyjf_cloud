/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.vo.config.AdminSystemVO;

import io.swagger.annotations.Api;

/**
 * @author DongZeShan
 * @version BaseController.java, v0.1 2018年6月21日 下午7:26:10
 * admin 基础类
 */
@Api(value = "admin基类")
@RestController
public class BaseController {
	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	//redis存有用户信息的key
	public static final String user="user";
	//取得session中用户信息
	public AdminSystemVO getUser(HttpServletRequest request) {
		AdminSystemVO ar=null;
		ar=(AdminSystemVO) request.getSession().getAttribute(user);
		return ar;
	}
	//存入session中用户信息
	public void setUser(HttpServletRequest request,AdminSystemVO prs) {
		request.getSession().setAttribute(user, prs);
	}
	// 移除session
	public void removeUser(HttpServletRequest request) {
		request.getSession().removeAttribute(user);
	}
}
