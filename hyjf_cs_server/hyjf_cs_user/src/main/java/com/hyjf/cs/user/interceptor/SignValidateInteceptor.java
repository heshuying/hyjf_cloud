package com.hyjf.cs.user.interceptor;

import com.hyjf.common.util.AppUserToken;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.cs.user.util.ResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class SignValidateInteceptor extends HandlerInterceptorAdapter {

	public SignValidateInteceptor() {
	}

	private List<String> allowUrls;

	public List<String> getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls) {
		this.allowUrls = allowUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		HandlerMethod methodHandler = (HandlerMethod) handler;
		SignValidate signValidate = methodHandler.getMethodAnnotation(SignValidate.class);
		// 如果方法中添加了@SignValidate 这里的signValidate不为null
		// 需要检查sign
		String sign = request.getParameter("sign");
		Integer userId = null;
		String accountId = null;
		if (StringUtils.isBlank(sign)) {
			sign = (String) request.getAttribute("sign");
		}
//		System.out.println("sign:" + sign);
		if (StringUtils.isNotBlank(sign)) {
			// 获取用户ID
			AppUserToken token = SecretUtil.getAppUserToken(sign);
			if (token != null) {
				userId = token.getUserId();
				accountId = token.getAccountId();
			}
			if (userId != null && userId - 0 > 0) {
				// 需要刷新 sign
				SecretUtil.refreshSign(sign);
			}
			request.setAttribute("userId", userId);
			request.setAttribute("accountId", accountId);
		}
		// 方法上加了注解 需要登录的验证
		if (signValidate != null) {
			String reditUrl = "/login?error=";
			if (StringUtils.isNotBlank(sign)) {
				// 如果查询不到 证明过期了 需要重新登录
				if (userId == null || userId - 0 == 0) {
					// 跳转到登录页面
					reditUrl += ResultEnum.LOGININVALID.getStatus();
				}else{
					// 放行
					return true;
				}
			} else {
				// 跳转页面
				reditUrl += ResultEnum.NOTLOGIN.getStatus();
			}
			response.sendRedirect(reditUrl);
			return false;
		}
		return super.preHandle(request, response, methodHandler);
	}
}
