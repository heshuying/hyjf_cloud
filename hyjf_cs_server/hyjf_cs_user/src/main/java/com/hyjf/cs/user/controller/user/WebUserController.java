package com.hyjf.cs.user.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.redis.RedisUtil;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	SystemConfig systemConfig;

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

		UserVO userVO = userService.register(registerVO,GetCilentIP.getIpAddr(request));

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
		BankCallBean bean = userService.userCreditAuthInves(token,ClientConstant.WEB_CLIENT, BankCallConstant.QUERY_TYPE_1,ClientConstant.CHANNEL_PC,lastSrvAuthCode,smsCode);
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView = BankCallUtils.callApi(bean);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReturnMessageException(AuthorizedError.CALL_BANK_ERROR);
		}
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
		BankCallBean bean =  userService.userCreditAuthInves(token, ClientConstant.WEB_CLIENT, BankCallConstant.QUERY_TYPE_2,ClientConstant.CHANNEL_PC,lastSrvAuthCode,smsCode);
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView = BankCallUtils.callApi(bean);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReturnMessageException(AuthorizedError.CALL_BANK_ERROR);
		}
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

	/**
	 * 获取用戶通知配置信息
	 * @param token
	 * @param request
	 * @return
	 */
	@RequestMapping("/userNoticeSettingInit")
	public ApiResult<UserVO> userNoticeSettingInit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
		ApiResult<UserVO> result = new ApiResult<UserVO>();

		WebViewUser user = (WebViewUser) redisUtil.get(token);
		UserVO userVO = userService.queryUserByUserId(user.getUserId());
		result.setResult(userVO);

		return result;
	}

	/**
	 * 保存用户通知设置
	 * @param token
	 * @param userVO
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "保存用户通知设置", notes = "保存用户通知设置")
	@PostMapping(value = "/saveUserNoticeSetting", produces = "application/json; charset=utf-8")
	public ApiResult<UserVO> saveUserNoticeSetting(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid UserVO userVO, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("用戶通知設置, userVO :{}", JSONObject.toJSONString(userVO));
		ApiResult<UserVO> result = new ApiResult<UserVO>();

		WebViewUser user = (WebViewUser) redisUtil.get(token);
		userVO.setUserId(user.getUserId());
		int ret = userService.updateUserByUserId(userVO);

		if (ret <= 0) {
			logger.error("保存用户通知设置失败");
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc("保存用户通知设置失败");
		}

		return result;
	}

	/**
	 *账户设置查询
	 * @param token
	 * @return
	 */
	@GetMapping(value = "init")
	public String init(@RequestHeader(value = "token") String token) {
		String result = userService.safeInit(token);
		return result;
	}

	/**
	 * 用户手机号修改基础信息获取
	 * @param token
	 * @param request
	 * @return
	 */
	@RequestMapping("/mobileModifyInit")
	public ApiResult<MobileModifyResultBean> mobileModifyInit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
		ApiResult<MobileModifyResultBean> result = new ApiResult<MobileModifyResultBean>();

		WebViewUser user = (WebViewUser) redisUtil.get(token);
		MobileModifyResultBean resultBean = userService.queryForMobileModify(user.getUserId());
		result.setResult(resultBean);
		
		return result;
	}
	
	/**
	 * 用户手机号码修改
	 */
	@ApiOperation(value = "手机号码修改", notes = "手机号码修改")
	@PostMapping(value = "/mobileModify", produces = "application/json; charset=utf-8")
	public ApiResult<UserVO> mobileModify(@RequestHeader(value = "token", required = true) String token, @RequestParam(required=true) String newMobile, @RequestParam(required=true) String smsCode, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("用户手机号码修改, newMobile :{}, smsCode:{}", newMobile, smsCode);
		ApiResult<UserVO> result = new ApiResult<UserVO>();

		WebViewUser user = (WebViewUser) redisUtil.get(token);
		boolean checkRet = userService.checkForMobileModify(newMobile, smsCode);
		if(checkRet) {
			UserVO userVO = new UserVO();
			userVO.setUserId(user.getUserId());
			userVO.setMobile(newMobile);
			userService.updateUserByUserId(userVO);
		}
		
		return result;
	}

}
