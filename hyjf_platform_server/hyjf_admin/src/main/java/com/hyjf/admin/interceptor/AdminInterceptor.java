/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.interceptor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.config.AdminSystemResponse;

/**
 * @author DongZeShan
 * @version AdminInterceptor.java, v0.1 2018年6月21日 下午7:59:25
 */

public class AdminInterceptor implements HandlerInterceptor {
	// 判断用户是否登陆
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!"/login/login".equals(request.getRequestURI())) {
			try {
				((AdminSystemResponse) request.getSession().getAttribute("user")).getResult();
			} catch (NullPointerException e) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
				try {
					JSONObject res = new JSONObject();
					res.put("success", "99");
					res.put("msg", "未登录");
					PrintWriter out = response.getWriter();
					out.append(res.toString());
					return false;
				} catch (Exception ex) {
					ex.printStackTrace();
					response.sendError(500);
					return false;
				}
			}
		}

		if (handler instanceof HandlerMethod) {
			AuthorityAnnotation authorityAnnotation = ((HandlerMethod) handler)
					.getMethodAnnotation(AuthorityAnnotation.class);
			// controller没有添加authorityAnnotation注解
			if (authorityAnnotation == null) {
				return true;
			}
			//获取该角色 权限列表
			List<String> perm = (List<String>) request.getSession().getAttribute("permission");
			for (String string : perm) {
				if (string.equals(authorityAnnotation.key() + ":" + authorityAnnotation.value())) {
					return true;
				}
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			JSONObject res = new JSONObject();
			res.put("success", "99");
			res.put("msg", "您没有权限使用此接口");
			PrintWriter out = response.getWriter();
			out.append(res.toString());
			System.out.println("权限的key为:" + authorityAnnotation.key() + "权限的val:" + authorityAnnotation.value());
			return false;

		}

		return true;

	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

}
