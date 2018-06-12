package com.hyjf.cs.user.controller.user.web.smscode;

import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import com.hyjf.cs.user.util.GetCilentIP;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiasq
 * @version WebSmsCodeController, v0.1 2018/4/25 9:01
 */
@Api(value = "验证码")
@RestController
@RequestMapping("/web/smsCode")
public class WebSmsCodeController {
	private static final Logger logger = LoggerFactory.getLogger(WebSmsCodeController.class);

	@Autowired
	private SmsCodeService sendSmsCode;

	/**
	 * 发送短信验证码 范围：注册，修改手机号(验证原手机，修改新手机)
	 * @param validCodeType 消息模板
	 * @param mobile
	 * @param token
	 * @param request
	 * @return
	 * @throws MQException
	 */
	@RequestMapping(value = "/send")
	public BaseResultBean sendSmsCode(@RequestParam String validCodeType,
									  @RequestParam String mobile,
									  @RequestHeader(value = "token", required = false) String token,
									  HttpServletRequest request)
			throws MQException {
		logger.info("sendSmsCode start, validCodeType is :{}, mobile is: {}", validCodeType, mobile);
		BaseResultBean resultBean = new BaseResultBean();
		sendSmsCode.sendSmsCode(validCodeType, mobile, token, GetCilentIP.getIpAddr(request));
		return resultBean;
	}
}
