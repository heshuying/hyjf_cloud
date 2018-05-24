package com.hyjf.cs.user.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author xiasq
 * @version WebUserController, v0.1 2018/4/21 15:06
 */
@Api(value = "web端用户接口")
@RestController
@RequestMapping("/web/user")
public class WebUserController {
	private static final Logger logger = LoggerFactory.getLogger(WebUserController.class);

	@Autowired
	private UserService userService;

	/**
	 * 注册
	 * @param registerVO
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "用户注册", notes = "用户注册")
	@PostMapping(value = "/register", produces = "application/json; charset=utf-8")
	public ApiResult<UserVO> register(@RequestBody @Valid RegisterVO registerVO, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("register start, registerVO is :{}", JSONObject.toJSONString(registerVO));
		ApiResult<UserVO> result = new ApiResult<UserVO>();

		UserVO userVO = userService.register(registerVO, request, response);

		if (userVO != null) {
			logger.info("register success, userId is :{}", userVO.getUserId());
			result.setResult(userVO);
		} else {
			logger.error("register failed...");
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(RegisterError.REGISTER_ERROR.getMessage());
		}
		return result;
	}

	/**
	 * 登录
	 * @param loginUserName
	 * @param loginPassword
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/login", produces = "application/json; charset=utf-8")
	public ApiResult<UserVO> login(@RequestParam String loginUserName,
				@RequestParam String loginPassword,
				HttpServletRequest request) {
			logger.info("login start, loginUserName is :{}", loginUserName);
			ApiResult<UserVO> result = new ApiResult<UserVO>();
			UserVO userVO = userService.login(loginUserName, loginPassword, GetCilentIP.getIpAddr(request));

			if (userVO != null) {
			logger.info("login success, userId is :{}", userVO.getUserId());
			result.setResult(userVO);
		} else {
			logger.error("login failed...");
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMessage());
		}

		return result;
	}

	/**
	 * 用户授权自动投资
	 * @param token
	 * @param request
	 * @return
	 */
	@RequestMapping("/userAuthInves")
	public ModelAndView userAuthInves(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
		String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
		String smsCode = request.getParameter("smsCode");
		ModelAndView modelAndView = userService.userCreditAuthInves(token,ClientConstant.WEB_CLIENT, BankCallConstant.QUERY_TYPE_1,ClientConstant.CHANNEL_PC,lastSrvAuthCode,smsCode);
		return modelAndView;
	}

	/**
	 *用户授权自动债转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/creditUserAuthInves")
	public ModelAndView creditUserAuthInves(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request) {
		String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
		String smsCode = request.getParameter("smsCode");
		ModelAndView modelAndView =  userService.userCreditAuthInves(token, ClientConstant.WEB_CLIENT, BankCallConstant.QUERY_TYPE_2,ClientConstant.CHANNEL_PC,lastSrvAuthCode,smsCode);
		return modelAndView;
	}

	/**
	 * 用户授权自动投资同步回调
	 * @param token
	 * @param request
	 * @param bean
	 * @return
	 */
	@RequestMapping("/userAuthInvesReturn")
	public Map<String,String> userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request,BankCallBean bean) {
		logger.info("userAuthInvesReturn:"+"[投资人自动投标签约增强同步回调开始]");
		String isSuccess = request.getParameter("isSuccess");
		Map<String,String> result = userService.userAuthReturn(token,bean,ClientConstant.INVES_URL_TYPE,isSuccess);
		return result;
	}

	/**
	 * 用户授权自动债转同步回调
	 * @param token
	 * @param request
	 * @param bean
	 * @return
	 */
	@RequestMapping("/credituserAuthInvesReturn")
	public Map<String, String> userCreditAuthInvesReturn(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request,
												  @ModelAttribute BankCallBean bean) {
		logger.info("[投资人自动债转签约增强同步回调开始]");
		String isSuccess = request.getParameter("isSuccess");
		Map<String,String> result = userService.userAuthReturn(token,bean,ClientConstant.CREDIT_URL_TYPE,isSuccess);
		return result;
	}

	/**
	 *  用户授权自动投资异步回调
	 * @param bean
	 * @return
	 */
	@RequestMapping("/userAuthInvesBgreturn")
	public String userAuthInvesBgreturn(BankCallBean bean) {
		String result = userService.userBgreturn(bean);
		return result;
	}

	/**
	 * 用户授权自动债转异步回调
	 * @param bean
	 * @return
	 */
	@RequestMapping("/credituserAuthInvesBgreturn")
	public String userCreditAuthInvesBgreturn(BankCallBean bean) {
		String result = userService.userBgreturn(bean);
		return result;

	}
}
