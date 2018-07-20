package com.hyjf.cs.user.controller.wechat.smscode;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import com.hyjf.cs.user.util.GetCilentIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xiasq
 * @version WebSmsCodeController, v0.1 2018/4/25 9:01
 */
@Api(value = "weChat验证码",description = "weChat端-验证码")
@RestController
@RequestMapping("/hyjf-wechat/smsCode")
public class WeChatSmsCodeController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(WeChatSmsCodeController.class);

	@Autowired
	private SmsCodeService sendSmsCode;

	/**
	 * 发送短信验证码 范围：注册，修改手机号(验证原手机，修改新手机)
	 * @param param
	 * @param token
	 * @param request
	 * @return
	 * @throws MQException
	 */
	@ApiOperation(value = " 发送短信验证码",notes = " 发送短信验证码")
	@PostMapping(value = "/send", produces = "application/json; charset=utf-8")
	@ApiImplicitParam(name = "param",value = "{validCodeType:string,mobile:string,platform:String}", dataType = "Map")
	public WeChatResult sendSmsCode(@RequestBody Map<String,String> param,
                                    @RequestHeader(value = "token", required = false) String token,
                                    HttpServletRequest request)
			throws MQException {
		logger.info("weChat端发送短信验证码接口, param is :{}", JSONObject.toJSONString(param));
		String validCodeType = param.get("validCodeType");
		String mobile = param.get("mobile");
		String platform = param.get("platform");
		WeChatResult resultBean = new WeChatResult();
		sendSmsCode.sendSmsCodeCheckParam(validCodeType, mobile, token, GetCilentIP.getIpAddr(request));
		sendSmsCode.sendSmsCode(validCodeType, mobile,platform, token, GetCilentIP.getIpAddr(request));
		return resultBean;
	}

	/**
	 * 短信验证码校验
	 *
	 * 用户注册数据提交（获取session数据并保存） 1.校验验证码
	 * 2.若验证码正确，则获取session数据，并将相应的注册数据写入数据库（三张表），跳转相应的注册成功界面
	 */
	@ApiOperation(value = "短信验证码校验", notes = "短信验证码校验")
	@PostMapping(value = "/checkcode", produces = "application/json; charset=utf-8")
	public JSONObject checkode(HttpServletRequest request) {
		logger.info("WeChat端短信验证码校验接口,SmsCodeVO  is :{}",JSONObject.toJSONString(request));
		JSONObject ret = new JSONObject();
		ret.put("request", "/checkcode");
		// 验证方式
		String verificationType = request.getParameter("verificationType");
		// 验证码
		String verificationCode = request.getParameter("verificationCode");
		// 手机号
		String mobile = request.getParameter("mobile");
		sendSmsCode.checkParam(verificationType,verificationCode,mobile);
		int cnt = sendSmsCode.updateCheckMobileCode(mobile, verificationCode, verificationType, CustomConstants.CLIENT_WECHAT, CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_YIYAN);
		CheckUtil.check(cnt > 0,MsgEnum.STATUS_ZC000015);
		ret.put("status", "000");
		ret.put("statusDesc", "验证验证码成功");
		return  ret;
	}

}
