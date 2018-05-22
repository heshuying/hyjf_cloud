package com.hyjf.cs.user.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterVO;
import org.springframework.web.servlet.ModelAndView;

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
	@Autowired
	private AmUserClient amUserClient;

	/**
	 * 注册
	 *
	 * @param registerVO
	 * @return
	 */
	@PostMapping(value = "/register", produces = "application/json; charset=utf-8")
	public BaseResultBean register(@RequestHeader String key, @RequestParam String mobile,
			@RequestParam String verificationCode, @RequestParam String password,
			@RequestParam(required = false) String reffer, HttpServletRequest request, HttpServletResponse response) {
		logger.info("register start, mobile is :{}", mobile);
		BaseResultBean resultBean = new BaseResultBean();

		String mobilephone = DES.decodeValue(key, mobile);
		String smsCode = DES.decodeValue(key, verificationCode);
		String pwd = DES.decodeValue(key, password);

		reffer = DES.decodeValue(key, reffer);
		if (StringUtils.isNotBlank(reffer)) {
			int count = amUserClient.countUserByRecommendName(reffer);
			if (count == 0) {
				resultBean.setStatus(LoginError.REFFER_INVALID_ERROR.getErrCode());
				resultBean.setStatusDesc(LoginError.REFFER_INVALID_ERROR.getMessage());
				return resultBean;
			}
		}

		RegisterVO registerVO = new RegisterVO();
		registerVO.setMobilephone(mobilephone);
		registerVO.setPassword(pwd);
		registerVO.setReffer(reffer);
		registerVO.setSmsCode(smsCode);
		UserVO userVO = userService.register(registerVO, request, response);

		if (userVO != null) {
			logger.info("register success, userId is :{}", userVO.getUserId());
		} else {
			logger.error("register failed...");
			resultBean.setStatus("1");
			resultBean.setStatusDesc(RegisterError.REGISTER_ERROR.getMessage());
		}
		return resultBean;
	}

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

	/**
	 * 用户授权自动债转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userAuthCredit")
	public ModelAndView userAuthCredit(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView =  userService.userCreditAuthInves(token,ClientConstant.APP_CLIENT, BankCallConstant.QUERY_TYPE_2,request);
		return modelAndView;
	}

	/**
	 * 用户授权自动债转同步回调
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userAuthCreditReturn")
	public ModelAndView userAuthCreditReturn(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request,BankCallBean bean) {
		ModelAndView result = userService.userAuthCreditReturn(token,bean,ClientConstant.CREDIT_AUTO_TYPE,request);
		 return result;
	}

	/**
	 * 用户授权自动债转异步回调
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userAuthCreditBgreturn")
	public String userCreditAuthInvesBgreturn(HttpServletRequest request, HttpServletResponse response,
											   BankCallBean bean) {
		String result = userService.userBgreturn(bean);
		return result;
	}

	/**
	 * 用户授权自动投资
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/userAuthInves")
	public ModelAndView userAuthInves(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = userService.userCreditAuthInves(token, ClientConstant.APP_CLIENT, BankCallConstant.QUERY_TYPE_1,request);
		return modelAndView;
	}

	/**
	 * 用户授权自动投资同步回调
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userAuthInvesReturn")
	public ModelAndView userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request,BankCallBean bean) {
		ModelAndView modelAndView = userService.userAuthCreditReturn(token,bean,ClientConstant.INVES_AUTO_TYPE,request);
		return modelAndView;
	}

	/**
	 * 用户授权自动投资异步回调
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/userAuthInvesBgreturn")
	public String userAuthInvesBgreturn(HttpServletRequest request, HttpServletResponse response,
										BankCallBean bean) {
		String result = userService.userBgreturn(bean);
		return result;
	}

}
