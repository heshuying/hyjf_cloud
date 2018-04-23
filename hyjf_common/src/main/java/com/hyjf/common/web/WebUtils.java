/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.common.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyjf.common.util.GetOrderIdUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.hyjf.common.util.CustomConstants;

/**
 * web工具类
 * 
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年3月28日
 * @see 下午2:56:31
 */
public class WebUtils {

	/** cookieDomain */
	private static final String cookieDomain = "";

	/** cookiePath */
	private static final String cookiePath = "/";

	private static String letter = "abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";

	private static Random random = new Random();

	@Value("${hyjf.web.domain.name}")
	private static String domainName;

	private WebUtils() {
	}

	/**
	 *
	 * 重定向到制定页面
	 * 
	 * @author renxingchen
	 * @param response
	 * @param xmlHttpRequest
	 * @throws IOException
	 *             todo 这是干啥
	 */
	// public static void redirectTargetPage(HttpServletResponse response,
	// String xmlHttpRequest, String url,
	// String errorCode) throws IOException {
	// if (null != xmlHttpRequest) {// 说明是ajax请求
	// WebBaseAjaxResultBean resultBean = new WebBaseAjaxResultBean();
	// // 构建错误信息
	// resultBean.setErrorCode(errorCode);
	// JSON.writeJSONStringTo(resultBean, response.getWriter(),
	// SerializerFeature.WriteMapNullValue);
	// } else {
	// // 跳转登录页面
	// response.sendRedirect(CustomConstants.HOST + url);
	// }
	// }

	/**
	 *
	 * 获取session
	 * 
	 * @author renxingchen
	 * @param request
	 * @return
	 */
	public static HyjfSession getHyjfSession(HttpServletRequest request, HttpServletResponse response, boolean arg0) {
		String sessionId = WebUtils.getCookie(request, CustomConstants.SESSIONID);
		String result = "";
		if (StringUtils.isNotEmpty(sessionId)) {
			result = RedisUtils.get(sessionId);
		} else {
			sessionId = WebUtils.createSessionId();
			WebUtils.addCookie(request, response, CustomConstants.SESSIONID, sessionId, null, domainName);
		}
		HyjfSession session;
		if (StringUtils.isEmpty(result) && arg0) {
			session = new HyjfSession();
			RedisUtils.set(CustomConstants.SESSIONID, JSON.toJSONString(session), CustomConstants.SESSION_EXPIRE);
		} else {
			session = JSON.parseObject(result, HyjfSession.class);
		}
		return session;
	}

	/**
	 *
	 * 将登陆后的用户信息保存在session中
	 * 
	 * @author renxingchen
	 * @param request
	 * @param user
	 * @return
	 */
	public static boolean sessionLogin(HttpServletRequest request, HttpServletResponse response, WebViewUser user) {
		String sessionId = getCookie(request, CustomConstants.SESSIONID);
		String result = "";
		if (StringUtils.isEmpty(sessionId)) {
			sessionId = createSessionId();
			WebUtils.addCookie(request, response, CustomConstants.SESSIONID, sessionId, null, domainName);
		} else {
			result = RedisUtils.get(sessionId);
		}
		HyjfSession session;
		if (StringUtils.isEmpty(result)) {// 如果没有获取到用户的session
											// 创建新的session并将用户信息写入
			session = new HyjfSession();
			session.setUser(user);
			if ("1".equals(String
					.valueOf(RedisUtils.set(sessionId, JSON.toJSONString(session), CustomConstants.SESSION_EXPIRE)))) {
				addDomainCookie(request, response, user);
				return true;
			} else {
				return false;
			}
		} else {// 获取到用户的session 将用户信息写入session
			session = JSON.parseObject(result, HyjfSession.class);
			session.setUser(user);
			if ("1".equals(String
					.valueOf(RedisUtils.set(sessionId, JSON.toJSONString(session), CustomConstants.SESSION_EXPIRE)))) {
				addDomainCookie(request, response, user);
				return true;
			} else {
				return false;
			}
		}
	}

	private static void addDomainCookie(HttpServletRequest request, HttpServletResponse response, WebViewUser user) {
		WebUtils.addCookie(request, response, "hyjfUsername", user.getUsername(), CustomConstants.SESSION_EXPIRE,
				domainName);// 将用户名写入cookie用来进行页面展示
		WebUtils.addCookie(request, response, "sex", String.valueOf(user.getSex()), CustomConstants.SESSION_EXPIRE,
				domainName);// 将用户性别写入cookie
		WebUtils.addCookie(request, response, "roleId", String.valueOf(user.getRoleId()),
				CustomConstants.SESSION_EXPIRE, domainName);// 将用户类型写入cookie
		if (StringUtils.isNotEmpty(user.getIconurl())) {// 如果用户个性头像不为空，则将个性头像地址写入cookie
			WebUtils.addCookie(request, response, "iconurl", user.getIconurl(), CustomConstants.SESSION_EXPIRE,
					domainName);
		}
	}

	/**
	 *
	 * 创建sessionId
	 * 
	 * @author renxingchen
	 * @return
	 */
	public static String createSessionId() {
		String sessionId;
		sessionId = UUID.randomUUID().toString();
		while (true) {
			// 生成随机三位字母
			for (int i = 0; i < 3; i++) {
				sessionId = sessionId + letter.charAt(random.nextInt(52));
			}
			// 生成一个随机的三位数
			sessionId = sessionId + GetOrderIdUtils.getRandomNum(1000);
			sessionId = sessionId.replace("-", "").toUpperCase();
			if (StringUtils.isNotEmpty(RedisUtils.get(sessionId))) {
				continue;
			} else {
				RedisUtils.set(sessionId, "", 86400);
				break;
			}
		}
		return sessionId;
	}

	/**
	 *
	 * 创建token
	 * 
	 * @author renxingchen
	 * @return
	 */
	public static String createToken() {
		String token;
		token = UUID.randomUUID().toString();
		// 生成随机三位字母
		for (int i = 0; i < 3; i++) {
			token = token + letter.charAt(random.nextInt(52));
		}
		// 生成一个随机的三位数
		token = token + GetOrderIdUtils.getRandomNum(1000);
		return token.replace("-", "").toUpperCase();
	}

	/**
	 *
	 * 获取当前的登录用户
	 * 
	 * @author renxingchen
	 * @param request
	 * @return
	 */
	public static WebViewUser getUser(HttpServletRequest request) {
		String sessionId = getCookie(request, CustomConstants.SESSIONID);
		if (StringUtils.isNotBlank(sessionId)) {
			if (StringUtils.isNotBlank(RedisUtils.get(sessionId))) {
				HyjfSession hyjfSession = JSON.parseObject(RedisUtils.get(sessionId), HyjfSession.class);
				return hyjfSession.getUser();
			}
		}
		return null;
	}

	/**
	 *
	 * 获取当前的登录用户id
	 *
	 * @author renxingchen
	 * @param request
	 * @return
	 */
	public static Integer getUserId(HttpServletRequest request) {
		WebViewUser user = getUser(request);
		if (null != user) {
			return user.getUserId();
		}
		return null;
	}

	/**
	 * 添加cookie
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            有效期(单位: 秒)
	 * @param path
	 *            路径
	 * @param domain
	 *            域
	 * @param secure
	 *            是否启用加密
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			Integer maxAge, String path, String domain, Boolean secure) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(URLEncoder.encode(value, "UTF-8"), "UTF-8");
			Cookie cookie = new Cookie(name, value);
			if (maxAge != null) {
				cookie.setMaxAge(maxAge);
			}
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			if (secure != null) {
				cookie.setSecure(secure);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加cookie
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            有效期(单位: 秒)
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			Integer maxAge) {
		addCookie(request, response, name, value, maxAge, cookiePath, cookieDomain, null);
	}

	/**
	 * 添加cookie
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            有效期(单位: 秒)
	 * @param domain
	 *            域
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			Integer maxAge, String domain) {
		addCookie(request, response, name, value, maxAge, cookiePath, domain, null);
	}

	/**
	 * 添加cookie
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
		addCookie(request, response, name, value, null, cookiePath, cookieDomain, null);
	}

	/**
	 * 获取cookie
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie名称
	 * @return 若不存在则返回null
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Assert.hasText(name);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				name = URLEncoder.encode(name, "UTF-8");
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取cookie
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie名称
	 * @return 若不存在则返回null
	 */
	public static String getCookie(HttpServletRequest request, String name, String domain) {
		Assert.notNull(request);
		Assert.hasText(name);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				name = URLEncoder.encode(name, "UTF-8");
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 移除cookie
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param path
	 *            路径
	 * @param domain
	 *            域
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path,
			String domain) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			Cookie cookie = new Cookie(name, null);
			cookie.setMaxAge(0);
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除cookie
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		removeCookie(request, response, name, cookiePath, domainName);
	}

}
