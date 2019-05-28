package com.hyjf.cs.user.controller.wechat.smscode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.impl.AmConfigClientImpl;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import com.hyjf.cs.user.util.GetCilentIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "weChat验证码",tags = "weChat端-验证码")
@RestController
@RequestMapping("/hyjf-wechat")
public class WeChatSmsCodeController extends BaseUserController {

	@Autowired
	private SmsCodeService sendSmsCode;

	@Autowired
	AmConfigClientImpl amConfigClient;
	/**
	 * 发送验证码
	 *
	 * @param request
	 * @param
	 * @return
	 */
	@ApiOperation(value = " 发送短信验证码",notes = " 发送短信验证码")
	@PostMapping(value = "/userRegist/sendVerificationCodeAction.do")
	public JSONObject sendVerificationCodeAction(@RequestHeader(value = "userId", required = false) Integer userId,HttpServletRequest request) {
		JSONObject ret = new JSONObject();
		ret.put("request", "/hyjf-wechat/userRegist/sendVerificationCodeAction");

		// 无感知图形验证码验证开始  Add By huanghui 20181227
		// 注册页面类型(register : 注册页, 使用图形验证码; landingpage : 着陆页,使用无感知图形验证码.)
		String pageType = request.getParameter("pageType");

		// 验证码类型
		String verificationType = request.getParameter("verificationType");
		// 手机号
		String mobile = request.getParameter("mobile");

		SmsConfigVO smsConfig = amConfigClient.findSmsConfig();
		ret = sendSmsCode.wechatCheckParam(verificationType, mobile, GetCilentIP.getIpAddr(request),ret,smsConfig);
		if (null!=ret.get("status")){
			return ret;
		}
		// 业务逻辑
		try {
			if("TPL_ZHUCE".equals(verificationType)){
				if ("register".equals(pageType)){
					sendSmsCode.sendSmsCode(verificationType, mobile, String.valueOf(ClientConstants.WECHAT_CLIENT), GetCilentIP.getIpAddr(request));
					ret.put("status", "000");
					ret.put("statusDesc", "发送验证码成功");
				}else {
					// 着陆页注册, 使用无感知验证码

					// 拼装所需参数
					String aid = "2026782647";
					String AppSecretKey = "0K6K0OwY9HELsYAQ2u_gzQA**";
					String ticket = request.getParameter("ticket");
					String randstr = request.getParameter("randstr");
					String userIp = GetCilentIP.getIpAddr(request);
					String verifyUrl = "https://ssl.captcha.qq.com/ticket/verify?aid=" + aid + "&AppSecretKey=" + AppSecretKey + "&Ticket=" + ticket + "&Randstr=" + randstr + "&UserIP=" + userIp;
					logger.info(mobile + "=>无感知验证码请求地址:" + verifyUrl);
					String backRes = HttpDeal.get(verifyUrl);
					logger.info(mobile + "=>无感知验证码请求返回:" + backRes);

					// 解析返回的JSON串
					JSONObject jsonObject = JSON.parseObject(backRes);
					String backResponse = (String) jsonObject.get("response");
					String backErrMsg = (String) jsonObject.get("err_msg");

					if ("1".equals(backResponse)){
						// 无感知图形验证码验证通过
						sendSmsCode.sendSmsCode(verificationType, mobile, String.valueOf(ClientConstants.WECHAT_CLIENT), GetCilentIP.getIpAddr(request));
						ret.put("status", "000");
						ret.put("statusDesc", "发送验证码成功");
					}else {
						String getBackErrMsg = this.backErrMsg(backErrMsg);
						ret.put("status", "99");
						ret.put("statusDesc", "图形验证码:" + getBackErrMsg);
						return ret;
					}
				}
			}else if(CommonConstant.PARAM_TPL_DUANXINDENGLU.equals(verificationType)){
				logger.info("开始发送登录验证码  ，手机号：{}   IP  {}",mobile,GetCilentIP.getIpAddr(request));
				// 发送登录短信验证码
				sendSmsCode.sendSmsCode(verificationType, mobile, String.valueOf(ClientConstants.WECHAT_CLIENT), GetCilentIP.getIpAddr(request));
				ret.put("status", "000");
				ret.put("statusDesc", "发送验证码成功");
			}
		} catch (Exception e) {
			ret.put("status", "99");
			ret.put("statusDesc", e.getMessage());
		}
		return ret;
	}

	/**
	 * 匹配返回的错误信息
	 * @param errCode
	 * @return
	 */
	private String backErrMsg(String errCode){
		String backMsg = "";
		switch (errCode){
			case "OK":
				backMsg = "验证通过";
				break;
			case "user code len error":
				backMsg = "验证码长度不匹配";
				break;
			case "captcha no match":
				backMsg = "验证码答案不匹配/Randstr参数不匹配";
				break;
			case "verify timeout":
				backMsg = "验证码签名超时";
				break;
			case "Sequnce repeat":
				backMsg = "验证码签名重放";
				break;
			case "Sequnce invalid":
				backMsg = "验证码签名序列";
				break;
			case "Cookie invalid":
				backMsg = "验证码cookie信息不合法";
				break;
			case "verify ip no match":
				backMsg = "ip不匹配";
				break;
			case "decrypt fail":
				backMsg = "验证码签名解密失败";
				break;
			case "appid no match":
				backMsg = "验证码强校验appid错误";
				break;
			case "param err":
				backMsg = "AppSecretKey参数校验错误";
				break;
			case "cmd no match":
				backMsg = "验证码系统命令号不匹配";
				break;
			case "uin no match":
				backMsg = "号码不匹配";
				break;
			case "seq redirect":
				backMsg = "重定向验证";
				break;
			case "opt no vcode":
				backMsg = "操作使用pt免验证码校验错误";
				break;
			case "diff":
				backMsg = "差别，验证错误";
				break;
			case "captcha type not match":
				backMsg = "验证码类型与拉取时不一致";
				break;
			case "verify type error":
				backMsg = "验证类型错误";
				break;
			case "invalid pkg":
				backMsg = "非法请求包";
				break;
			case "bad visitor":
				backMsg = "策略拦截";
				break;
			case "system busy":
				backMsg = "系统内部错误";
				break;
			default:
				backMsg = "验证错误";
				break;
		}
		return backMsg;
	}

	/**
	 * 短信验证码校验
	 *
	 * 用户注册数据提交（获取session数据并保存） 1.校验验证码
	 * 2.若验证码正确，则获取session数据，并将相应的注册数据写入数据库（三张表），跳转相应的注册成功界面
	 */
	@ApiOperation(value = "短信验证码校验", notes = "短信验证码校验")
	@PostMapping(value = "/wechatUser/validateVerificationCodeAction.do", produces = "application/json; charset=utf-8")
	public JSONObject validateVerificationCodeAction(HttpServletRequest request, HttpServletResponse response) {
		JSONObject ret = new JSONObject();
		ret.put("request", "/hyjf-wechat/userRegist/validateVerificationCodeAction");

		// 验证方式
		String verificationType = request.getParameter("verificationType");
		// 验证码
		String verificationCode = request.getParameter("verificationCode");
		// 手机号
		String mobile = request.getParameter("mobile");
		SmsConfigVO smsConfig = amConfigClient.findSmsConfig();
		ret = sendSmsCode.wechatCheckParam(verificationType, mobile, GetCilentIP.getIpAddr(request),ret, smsConfig);
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
			int cnt = sendSmsCode.updateCheckMobileCode(mobile, verificationCode, verificationType, CustomConstants.CLIENT_WECHAT, CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_YIYAN,true);

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
