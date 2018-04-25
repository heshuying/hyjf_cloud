package com.hyjf.cs.user.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.GetCilentIP;

/**
 * @author xiasq
 * @version AppUserController, v0.1 2018/4/25 15:43
 */

@RestController
@RequestMapping("/app/user")
public class AppUserController {
	private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

	@Autowired
	private UserService userService;

	/**
	 * 登录
	 * 
	 * @param loginUserName
	 * @param loginPassword
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/login", produces = "application/json; charset=utf-8")
	public BaseResultBean login(@RequestHeader String key, @RequestParam String username, @RequestParam String password,
			HttpServletRequest request) {
		BaseResultBean resultBean = new BaseResultBean();

		// 账户密码解密
		String loginUserName = DES.decodeValue(key, username);
		String loginPassword = DES.decodeValue(key, password);

		if (Validator.isNull(loginUserName) || Validator.isNull(loginPassword)) {
			resultBean.setStatus(LoginError.CHECK_NULL_ERROR.getErrCode());
			resultBean.setStatusDesc(LoginError.CHECK_NULL_ERROR.getErrCode());
			return resultBean;
		}

		// app 只支持手机号登录
		if (!CommonUtils.isMobile(loginUserName)) {
			resultBean.setStatus(LoginError.USER_LOGIN_ERROR.getErrCode());
			resultBean.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMessage());
			return resultBean;
		}

		userService.login(loginUserName, loginPassword, GetCilentIP.getIpAddr(request));
		return resultBean;
	}
}
