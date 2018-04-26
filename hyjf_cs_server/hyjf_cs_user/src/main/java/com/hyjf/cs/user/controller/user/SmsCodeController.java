package com.hyjf.cs.user.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.SmsCodeService;
import com.hyjf.cs.user.util.GetCilentIP;

/**
 * @author xiasq
 * @version SmsCodeController, v0.1 2018/4/25 9:01
 */

@RestController
@RequestMapping("/api/smsCode")
public class SmsCodeController {
	private static final Logger logger = LoggerFactory.getLogger(SmsCodeController.class);

	@Autowired
	private SmsCodeService sendSmsCode;

	/**
	 * 发送短信验证码 范围：注册，修改手机号(验证原手机，修改新手机)
	 *
	 * @param validCodeType
	 * @param mobile
	 * @param request
	 * @return
	 * @throws MQException
	 */
	@RequestMapping(value = "/send")
	public BaseResultBean sendSmsCode(@RequestParam String validCodeType, @RequestParam String mobile,
			@RequestHeader(value = "token", required = false) String token, HttpServletRequest request)
			throws MQException {
		logger.info("sendSmsCode start, validCodeType is :{}, mobile is: {}", validCodeType, mobile);
		BaseResultBean resultBean = new BaseResultBean();
		sendSmsCode.sendSmsCode(validCodeType, mobile, token, GetCilentIP.getIpAddr(request));
		return resultBean;
	}
}
