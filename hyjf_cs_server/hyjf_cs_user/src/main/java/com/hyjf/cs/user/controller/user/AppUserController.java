package com.hyjf.cs.user.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.service.AppUserService;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.util.SecretUtil;
import com.hyjf.cs.user.vo.RegisterVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiasq
 * @version AppUserController, v0.1 2018/4/25 15:43
 */
@Api(value = "app端用户接口")
@RestController
@RequestMapping("/app/user")
public class AppUserController {
	private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	AppUserService  appUserService;

	/**
	 * 注册
	 * @param key
	 * @param mobile
	 * @param verificationCode
	 * @param password
	 * @param reffer
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "用户注册", notes = "用户注册")
	@PostMapping(value = "/register", produces = "application/json; charset=utf-8")
	public ApiResult<UserVO> register(@RequestHeader String key, @RequestParam String mobile,
                                              @RequestParam String verificationCode, @RequestParam String password,
                                              @RequestParam(required = false) String reffer, HttpServletRequest request, HttpServletResponse response) {
		logger.info("register start, mobile is :{}", mobile);
        ApiResult<UserVO> result = new ApiResult<>();
		String mobilephone = DES.decodeValue(key, mobile);
		String smsCode = DES.decodeValue(key, verificationCode);
		String pwd = DES.decodeValue(key, password);
		reffer = DES.decodeValue(key, reffer);
		if (StringUtils.isNotBlank(reffer)) {
			int count = amUserClient.countUserByRecommendName(reffer);
			if (count == 0) {
                result.setStatus(ApiResult.STATUS_FAIL);
                result.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMessage());
			}
		}
		RegisterVO registerVO = new RegisterVO();
		registerVO.setMobilephone(mobilephone);
		registerVO.setPassword(pwd);
		registerVO.setReffer(reffer);
		registerVO.setSmsCode(smsCode);
		UserVO userVO = userService.register(registerVO,GetCilentIP.getIpAddr(request));
        result.setResult(userVO);
		if (userVO != null) {
			logger.info("register success, userId is :{}", userVO.getUserId());
		} else {
			logger.error("register failed...");
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMessage());
		}
		return result;
	}

	/**
	 * 登录
	 * @param key
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "登录", notes = "登录")
	@PostMapping(value = "/login", produces = "application/json; charset=utf-8")
	public ApiResult<UserVO> login(@RequestHeader String key, @RequestParam String username, @RequestParam String password,
			HttpServletRequest request) {
		ApiResult<UserVO> result = new ApiResult<UserVO>();
		// 账户密码解密
		String loginUserName = DES.decodeValue(key, username);
		String loginPassword = DES.decodeValue(key, password);

		if (Validator.isNull(loginUserName) || Validator.isNull(loginPassword)||!CommonUtils.isMobile(loginUserName)) {
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMessage());
		}
		userService.login(loginUserName, loginPassword, GetCilentIP.getIpAddr(request));
		return result;
	}

	/**
	 * 用户授权自动债转
	 * @param token
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "用户授权自动债转", notes = "用户授权自动债转")
	@RequestMapping("/userAuthCredit")
	public ModelAndView userAuthCredit(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request, HttpServletResponse response) {
		String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
		String smsCode = request.getParameter("smsCode");
		BankCallBean bean =  userService.userCreditAuthInves(token,ClientConstant.APP_CLIENT, BankCallConstant.QUERY_TYPE_2,ClientConstant.CHANNEL_APP,lastSrvAuthCode,smsCode);
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
	 * 用户授权自动债转同步回调
	 * @param token
	 * @param request
	 * @param bean
	 * @return
	 */
	@ApiOperation(value = "用户授权自动债转同步回调", notes = "用户授权自动债转同步回调")
	@RequestMapping("/userAuthCreditReturn")
	public ApiResult<String> userAuthCreditReturn(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request,BankCallBean bean) {
		ApiResult<String> apiResult = new ApiResult<>();
		String sign = request.getHeader("sign");
		String isSuccess = request.getParameter("isSuccess");
		String result = appUserService.userAuthCreditReturn(token,bean,ClientConstant.CREDIT_AUTO_TYPE,sign,isSuccess);
		apiResult.setResult(result);
		 return apiResult;
	}

	/**
	 * 用户授权自动债转异步回调
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
	@RequestMapping("/userAuthCreditBgreturn")
	public String userCreditAuthInvesBgreturn(HttpServletRequest request, HttpServletResponse response,
											   BankCallBean bean) {
		String result = userService.userBgreturn(bean);
		return result;
	}

	/**
	 * 用户授权自动投资
	 * @param token
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "用户授权自动投资", notes = "用户授权自动投资")
	@RequestMapping("/userAuthInves")
	public ModelAndView userAuthInves(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request, HttpServletResponse response) {
		String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
		String smsCode = request.getParameter("smsCode");
		BankCallBean bean = userService.userCreditAuthInves(token, ClientConstant.APP_CLIENT, BankCallConstant.QUERY_TYPE_1,ClientConstant.CHANNEL_APP,lastSrvAuthCode,smsCode);
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
	 * @Author: zhangqingqing
	 * @Desc :用户授权自动投资同步回调
	 * @Param: * @param token
	 * @param request
	 * @param bean
	 * @Date: 16:45 2018/5/30
	 * @Return: com.hyjf.cs.user.result.ApiResult<java.lang.String>
	 */
	@ApiOperation(value = "用户授权自动投资同步回调", notes = "用户授权自动投资同步回调")
	@RequestMapping("/userAuthInvesReturn")
	public ApiResult<String> userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token,HttpServletRequest request,BankCallBean bean) {
		ApiResult<String> apiResult = new ApiResult<>();
		String sign = request.getHeader("sign");
		String isSuccess = request.getParameter("isSuccess");
		String result = appUserService.userAuthCreditReturn(token,bean,ClientConstant.INVES_AUTO_TYPE,sign,isSuccess);
		apiResult.setResult(result);
		return apiResult;
	}

	/**
	 * @Author: zhangqingqing
	 * @Desc :用户授权自动投资异步回调
	 * @Param: * @param bean
	 * @Date: 16:46 2018/5/30
	 * @Return: java.lang.String
	 */
	@ApiOperation(value = "用户授权自动投资异步回调", notes = "用户授权自动投资异步回调")
	@ResponseBody
	@RequestMapping("/userAuthInvesBgreturn")
	public String userAuthInvesBgreturn(BankCallBean bean) {

		String result = userService.userBgreturn(bean);
		return result;
	}

	/**
	 * 修改登陆密码
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
	@ResponseBody
	@RequestMapping(value = "/updatePasswordAction", method = RequestMethod.POST)
	public JSONObject updatePasswordAction(HttpServletRequest request, HttpServletResponse response) {
		logger.info("AppUserController.updatePasswordAction start.....");
		JSONObject ret = new JSONObject();
		ret.put("request", "/updatePasswordAction");

		// 版本号
		String version = request.getParameter("version");
		// 网络状态
		String netStatus = request.getParameter("netStatus");
		// 平台
		String platform = request.getParameter("platform");
		// 唯一标识
		String sign = request.getParameter("sign");
		// token
		String token = request.getParameter("token");
		// 随机字符串
		String randomString = request.getParameter("randomString");
		// Order
		String order = request.getParameter("order");

		// 新密码
		String newPassword = request.getParameter("newPassword");
		// 旧密码
		String oldPassword = request.getParameter("oldPassword");

		// 检查参数正确性
		if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign) || Validator.isNull(token) || Validator.isNull(randomString) || Validator.isNull(order)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}

		// 取得加密用的Key
		String key = SecretUtil.getKey(sign);
		if (Validator.isNull(key)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}

		// 取得用户登录信息
		Integer userId = SecretUtil.getUserId(sign);
		String username = SecretUtil.getUserName(sign);
		if (username != null || userId != null) {
			try {
				// 解密
				oldPassword = DES.decodeValue(key, oldPassword);
				newPassword = DES.decodeValue(key, oldPassword);
				ret = userService.updatePassWd(userId, oldPassword, newPassword);
			} catch (Exception e) {
				ret.put("status", "1");
				ret.put("statusDesc", "修改密码失败");
				return ret;
			}
		} else {
			ret.put("status", "1");
			ret.put("statusDesc", "用户信息不存在");
			return ret;
		}
		return ret;
	}


}
