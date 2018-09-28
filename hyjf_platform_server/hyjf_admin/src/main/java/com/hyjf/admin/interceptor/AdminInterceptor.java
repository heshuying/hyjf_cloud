/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.interceptor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;

/**
 * @author DongZeShan
 * @version AdminInterceptor.java, v0.1 2018年6月21日 下午7:59:25
 */

public class AdminInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

	/**
	 * 判断用户是否登陆
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("admin接收到请求,请求接口为:" + request.getRequestURI());
		String username = "";
		try {
			username = ((AdminSystemVO) request.getSession().getAttribute("user")).getUsername();
			String val = RedisUtils.get("admin@" + username);
			if (val != null && !val.equals(request.getHeader("Cookies"))) {
				request.getSession().removeAttribute("user");
//				JSONObject res = new JSONObject();
//				res.put("success", "99");
//				res.put("msg", "未登录,登陆超时,其他地方已登陆");
//				PrintWriter out = response.getWriter();
//				out.append(res.toString());
				throw new ReturnMessageException(MsgEnum.ERR_USER_LOGIN_EXPIRE);
				//return false;
			} else {
				RedisUtils.set("admin@" + username, val, 3600);
			}

		} catch (NullPointerException e) {
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("application/json; charset=utf-8");
//			try {
//				JSONObject res = new JSONObject();
//				res.put("success", "99");
//				res.put("msg", "未登录");
//				PrintWriter out = response.getWriter();
//				out.append(res.toString());
//				return false;
				throw new ReturnMessageException(MsgEnum.ERR_USER_LOGIN_EXPIRE);
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				response.sendError(500);
//				return false;
//			}

		}

		if (handler instanceof HandlerMethod) {
			AuthorityAnnotation authorityAnnotation = ((HandlerMethod) handler)
					.getMethodAnnotation(AuthorityAnnotation.class);
			// controller没有添加authorityAnnotation注解
			if (authorityAnnotation == null) {
				return true;
			}
			// 获取该角色 权限列表
			List<String> perm = (List<String>) request.getSession().getAttribute("permission");
			for (String string : perm) {
				if (string.equals(authorityAnnotation.key() + ":" + authorityAnnotation.value())) {
					return true;
				}
			}
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("application/json; charset=utf-8");
//			JSONObject res = new JSONObject();
//			res.put("success", "99");
//			res.put("msg", "您没有权限使用此接口");
//			PrintWriter out = response.getWriter();
//			out.append(res.toString());
			
			logger.info("权限的key为:" + authorityAnnotation.key() + "权限的val:" + authorityAnnotation.value());
			throw new ReturnMessageException(MsgEnum.ERR_USER_AUTHORITY);
	//		return false;

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
