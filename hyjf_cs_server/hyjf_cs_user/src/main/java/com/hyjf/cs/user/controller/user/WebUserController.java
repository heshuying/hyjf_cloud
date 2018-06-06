package com.hyjf.cs.user.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.constants.RegisterError;
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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class WebUserController {
    private static final Logger logger = LoggerFactory.getLogger(WebUserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * @param request
     * @Author: zhangqingqing
     * @Desc :注册
     * @Param: * @param registerVO
     * @Date: 16:39 2018/5/30
     * @Return: com.hyjf.cs.user.result.ApiResult<com.hyjf.am.vo.user.UserVO>
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> register(@RequestBody @Valid RegisterVO registerVO, HttpServletRequest request) {
        logger.info("register start, registerVO is :{}", JSONObject.toJSONString(registerVO));
        ApiResult<UserVO> result = new ApiResult<UserVO>();

        UserVO userVO = userService.register(registerVO, GetCilentIP.getIpAddr(request));

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
     * @param loginPassword
     * @param request
     * @Author: zhangqingqing
     * @Desc :登录
     * @Param: * @param loginUserName
     * @Date: 16:39 2018/5/30
     * @Return: com.hyjf.cs.user.result.ApiResult<com.hyjf.am.vo.user.UserVO>
     */
    @ApiOperation(value = "用户登录", notes = "用户登录")
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
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资
     * @Param: * @param token
     * @param request
     * @Date: 16:43 2018/5/30
     * @Return: org.springframework.web.servlet.ModelAndView
     */
    @ApiOperation(value = "用户授权自动投资", notes = "用户授权自动投资")
    @PostMapping("/userAuthInves")
    public ModelAndView userAuthInves(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
        String smsCode = request.getParameter("smsCode");
        BankCallBean bean = userService.userCreditAuthInves(token, ClientConstant.WEB_CLIENT, BankCallConstant.QUERY_TYPE_1, ClientConstant.CHANNEL_PC, lastSrvAuthCode, smsCode);
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
     * @Desc :用户授权自动债转
     * @Param: * @param token
     * @param request
     * @Date: 16:42 2018/5/30
     * @Return: org.springframework.web.servlet.ModelAndView
     */
    @ApiOperation(value = "用户授权自动债转", notes = "用户授权自动债转")
    @PostMapping("/creditUserAuthInves")
    public ModelAndView creditUserAuthInves(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
        String smsCode = request.getParameter("smsCode");
        BankCallBean bean = userService.userCreditAuthInves(token, ClientConstant.WEB_CLIENT, BankCallConstant.QUERY_TYPE_2, ClientConstant.CHANNEL_PC, lastSrvAuthCode, smsCode);
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
     * @Date: 16:42 2018/5/30
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     */
    @ApiOperation(value = "用户授权自动投资同步回调", notes = "用户授权自动投资同步回调")
    @RequestMapping("/userAuthInvesReturn")
    public Map<String, String> userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request, BankCallBean bean) {
        logger.info("userAuthInvesReturn:" + "[投资人自动投标签约增强同步回调开始]");
        String isSuccess = request.getParameter("isSuccess");
        Map<String, String> result = userService.userAuthReturn(token, bean, ClientConstant.INVES_URL_TYPE, isSuccess);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转同步回调
     * @Param: * @param token
     * @param request
     * @param bean
     * @Date: 16:42 2018/5/30
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     */
    @ApiOperation(value = "用户授权自动债转同步回调", notes = "用户授权自动债转同步回调")
    @RequestMapping("/credituserAuthInvesReturn")
    public Map<String, String> userCreditAuthInvesReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request,
                                                         @ModelAttribute BankCallBean bean) {
        logger.info("[投资人自动债转签约增强同步回调开始]");
        String isSuccess = request.getParameter("isSuccess");
        Map<String, String> result = userService.userAuthReturn(token, bean, ClientConstant.CREDIT_URL_TYPE, isSuccess);
        return result;
    }

    /**
     * 用户授权自动投资异步回调
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动投资异步回调", notes = "用户授权自动投资异步回调")
    @RequestMapping("/userAuthInvesBgreturn")
    public String userAuthInvesBgreturn(BankCallBean bean) {
        String result = userService.userBgreturn(bean);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转异步回调
     * @Param: * @param bean
     * @Date: 16:43 2018/5/30
     * @Return: java.lang.String
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @RequestMapping("/credituserAuthInvesBgreturn")
    public String userCreditAuthInvesBgreturn(BankCallBean bean) {
        String result = userService.userBgreturn(bean);
        return result;

    }

    /**
     * 获取用戶通知配置信息
     *
     * @param token
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用戶通知配置信息", notes = "获取用戶通知配置信息")
    @PostMapping("/userNoticeSettingInit")
    public ApiResult<UserVO> userNoticeSettingInit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        ApiResult<UserVO> result = new ApiResult<UserVO>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        UserVO userVO = userService.queryUserByUserId(user.getUserId());
        result.setResult(userVO);

        return result;
    }

    /**
     * 保存用户通知设置
     *
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

        WebViewUser user = RedisUtils.getObj(token, WebViewUser.class);
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
     * @Author: zhangqingqing
     * @Desc :账户设置查询
     * @Param: * @param token
     * @Date: 16:43 2018/5/30
     * @Return: java.lang.String
     */
    @ApiOperation(value = "账户设置查询", notes = "账户设置查询")
    @PostMapping(value = "accountSet")
    public ApiResult<String> accountSet(@RequestHeader(value = "token",required = false) String token) {
        ApiResult<String> apiResult = new ApiResult<>();
      /*  Map map = ImmutableMap.of("userId", String.valueOf(4), "username", "hyjf225650", "ts",
                String.valueOf(Instant.now().getEpochSecond()));
         token = JwtHelper.genToken(map);*/
        WebViewUser webViewUser = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        if (null == webViewUser){
            apiResult.setStatus(ApiResult.STATUS_FAIL);
            apiResult.setStatusDesc("账户设置查询失败");
            apiResult.setLoginFlag(ApiResult.STATUS_FAIL);
        }else{
            String result = userService.safeInit(webViewUser);
            if (null == result){
                apiResult.setStatus(ApiResult.STATUS_FAIL);
                apiResult.setStatusDesc("账户设置查询失败");
            }
            apiResult.setResult(result);
        }



        return apiResult;
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

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
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

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
		boolean checkRet = userService.checkForMobileModify(newMobile, smsCode);
		if(checkRet) {
			UserVO userVO = new UserVO();
			userVO.setUserId(user.getUserId());
			userVO.setMobile(newMobile);
			userService.updateUserByUserId(userVO);
		}
		
		return result;
	}
	
	/**
	 * 发送激活邮件
	 */
	@ApiOperation(value = "发送激活邮件", notes = "发送激活邮件")
	@PostMapping(value = "/sendEmailActive", produces = "application/json; charset=utf-8")
	public ApiResult<Object> sendEmailActive(@RequestHeader(value = "token", required = true) String token, @RequestParam(required=true) String email, HttpServletRequest request) {
		ApiResult<Object> result = new ApiResult<Object>();

        WebViewUser user = new WebViewUser();
		user.setUserId(1);
		userService.checkForEmailSend(email, user.getUserId());
		
		try {
			userService.sendEmailActive(user.getUserId(), email);
		} catch (MQException e) {
			logger.error("发送激活邮件失败", e);
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc("发送激活邮件失败");
		}
		
		return result;
	}
	
	/**
	 * 绑定邮箱
	 */
	@ApiOperation(value = "绑定邮箱", notes = "绑定邮箱")
	@PostMapping(value = "/bindEmail", produces = "application/json; charset=utf-8")
	public ApiResult<Object> bindEmail(@RequestHeader(value = "token", required = true) String token, @RequestParam(required=true) String key, @RequestParam(required=true) String value, @RequestParam(required=true) String email, HttpServletRequest request) {
		ApiResult<Object> result = new ApiResult<Object>();

		WebViewUser user  = new WebViewUser();
		user.setUserId(1);
		
		userService.checkForEmailBind(email, key, value, user);
		
		try {
			userService.updateEmail(user.getUserId(), email);
		} catch (MQException e) {
			logger.error("邮箱激活失败", e);
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc("邮箱激活失败");
		}
		
		return result;
	}
	
	/**
	 * 添加、修改紧急联系人
	 * @param token
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "添加、修改紧急联系人", notes = "添加、修改紧急联系人")
	@PostMapping(value = "/saveContract", produces = "application/json; charset=utf-8")
	public ApiResult<Object> saveContract(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
		ApiResult<Object> result = new ApiResult<Object>();

		String relationId = request.getParameter("relationId");
		String rlName = request.getParameter("rlName");
		String rlPhone = request.getParameter("rlPhone");

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
		userService.checkForContractSave(relationId, rlName, rlPhone, user);
		
		try {
			userService.saveContract(relationId, rlName, rlPhone, user);
		} catch (MQException e) {
			logger.error("紧急联系人保存失败", e);
			result.setStatus(ApiResult.STATUS_FAIL);
			result.setStatusDesc("紧急联系人保存失败");
		}
		
		return result;
	}

    /**
     * @Author: zhangqingqing
     * @Desc : 退出登录
     * @Param: * @param token
     * @Date: 16:29 2018/6/5
     * @Return: com.hyjf.cs.user.result.ApiResult<java.lang.String>
     */
    @PostMapping(value = "logout")
    public ApiResult<String> loginout(@RequestHeader(value = "token") String token){
        ApiResult<String> result = new ApiResult<>();
        // 退出到首页
        result.setResult("index");
        try {
            RedisUtils.del(RedisKey.USER_TOKEN_REDIS + token);
        }catch (Exception e){
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc("退出失败");
        }
        return result;
    }

}
