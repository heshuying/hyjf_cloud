package com.hyjf.cs.user.controller.wechat.smscode;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiasq
 * @version WebSmsCodeController, v0.1 2018/4/25 9:01
 */
@Api(value = "weChat验证码",description = "weChat端-验证码")
@RestController
@RequestMapping("/hyjf-wechat/userRegist")
public class WeChatSmsCodeController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(WeChatSmsCodeController.class);

	@Autowired
	private SmsCodeService sendSmsCode;


	/**
	 * 发送验证码
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = " 发送短信验证码",notes = " 发送短信验证码")
	@PostMapping(value = "/sendVerificationCodeAction.do", produces = "application/json; charset=utf-8")
	public JSONObject sendVerificationCodeAction(@RequestHeader(value = "token", required = false) String token,HttpServletRequest request, HttpServletResponse response) {
		JSONObject ret = new JSONObject();
		ret.put("request", "/hyjf-wechat/userRegist/sendVerificationCodeAction");

		// 验证码类型
		String verificationType = request.getParameter("verificationType");
		// 手机号
		String mobile = request.getParameter("mobile");
		ret = sendSmsCode.wechatCheckParam(verificationType, mobile, GetCilentIP.getIpAddr(request),ret);
		if (null!=ret.get("status")){
			return ret;
		}
		// 业务逻辑

		try {
			if(!verificationType.equals(CommonConstant.PARAM_TPL_BDYSJH)){
				sendSmsCode.sendSmsCode(verificationType, mobile, String.valueOf(ClientConstants.WECHAT_CLIENT), GetCilentIP.getIpAddr(request));
					ret.put("status", "000");
					ret.put("statusDesc", "发送验证码成功");
			}else{
				//判断用户是否登录
				UserVO user = sendSmsCode.getUsers(token);
				// 请求发送短信验证码
				BankCallBean bean = sendSmsCode.callSendCode(user.getUserId(),mobile, BankCallMethodConstant.TXCODE_MOBILE_MODIFY_PLUS, BankCallConstant.CHANNEL_WEI,null);
				if(bean == null){
					ret.put("status", "99");
					ret.put("statusDesc","发送短信验证码异常");
					return ret;
				}
				//返回失败
				if(!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())){
					if(!Validator.isNull(bean.getSrvAuthCode())){
						ret.put("status", "000");
						ret.put("statusDesc", "发送验证码成功");
						//业务授权码
						ret.put("bankCode", bean.getSrvAuthCode());
						return ret;
					}
					ret.put("status", "99");
					ret.put("statusDesc","发送短信验证码失败，失败原因：" + bean.getRetMsg());
					return ret;
				}
				//成功返回业务授权码
				ret.put("status", "000");
				ret.put("statusDesc", "发送验证码成功");
				//业务授权码
				ret.put("bankCode", bean.getSrvAuthCode());
			}

		} catch (Exception e) {
			ret.put("status", "99");
			ret.put("statusDesc", e.getMessage());
		}
		return ret;
	}

	/**
	 * 短信验证码校验
	 *
	 * 用户注册数据提交（获取session数据并保存） 1.校验验证码
	 * 2.若验证码正确，则获取session数据，并将相应的注册数据写入数据库（三张表），跳转相应的注册成功界面
	 */
	@ApiOperation(value = "短信验证码校验", notes = "短信验证码校验")
	@PostMapping(value = "/validateVerificationCodeAction.do", produces = "application/json; charset=utf-8")
	public JSONObject validateVerificationCodeAction(HttpServletRequest request, HttpServletResponse response) {
		JSONObject ret = new JSONObject();
		ret.put("request", "/hyjf-wechat/userRegist/validateVerificationCodeAction");

		// 验证方式
		String verificationType = request.getParameter("verificationType");
		// 验证码
		String verificationCode = request.getParameter("verificationCode");
		// 手机号
		String mobile = request.getParameter("mobile");
		ret = sendSmsCode.wechatCheckParam(verificationType, mobile, GetCilentIP.getIpAddr(request),ret);
		if (Validator.isNull(verificationType)) {
			ret.put("status", "99");
			ret.put("statusDesc", "验证码类型不能为空");
			return ret;
		}
		if (Validator.isNull(verificationCode)) {
			ret.put("status", "99");
			ret.put("statusDesc", "验证码不能为空");
			return ret;
		}
		if (!(verificationType.equals(CommonConstant.PARAM_TPL_ZHUCE) || verificationType.equals(CommonConstant.PARAM_TPL_ZHAOHUIMIMA) || verificationType.equals(CommonConstant.PARAM_TPL_BDYSJH) || verificationType.equals(CommonConstant.PARAM_TPL_YZYSJH))) {
			ret.put("status", "99");
			ret.put("statusDesc", "无效的验证码类型");
			return ret;
		}

		// 业务逻辑
		try {
			if (Validator.isNull(mobile)) {
				ret.put("status", "99");
				ret.put("statusDesc", "手机号不能为空");
				return ret;
			}
			if (!Validator.isMobile(mobile)) {
				ret.put("status", "99");
				ret.put("statusDesc", "请输入您的真实手机号码");
				return ret;
			}
			int cnt = sendSmsCode.updateCheckMobileCode(mobile, verificationCode, verificationType, CustomConstants.CLIENT_WECHAT, CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_YIYAN);

			if (cnt > 0) {
				ret.put("status", "000");
				ret.put("statusDesc", "验证验证码成功");
			} else {
				ret.put("status", "99");
				ret.put("statusDesc", "验证码输入错误");
			}

		} catch (Exception e) {
			ret.put("status", "99");
			ret.put("statusDesc", "验证验证码发生错误");
		}
		return ret;
	}

}
