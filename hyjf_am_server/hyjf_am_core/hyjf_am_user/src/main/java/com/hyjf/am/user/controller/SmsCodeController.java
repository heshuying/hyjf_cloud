package com.hyjf.am.user.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.user.SmsCodeResponse;
import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.user.service.SmsService;

/**
 * @author xiasq
 * @version SmsCodeController, v0.1 2018/4/12 17:26
 */

@RestController
@RequestMapping("/am-user/smsCode")
public class SmsCodeController {
	private static Logger logger = LoggerFactory.getLogger(SmsCodeController.class);

	@Autowired
	private SmsService smsService;

	/**
	 * 短信验证码保存
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public SmsCodeResponse saveSmsCode(@RequestBody @Valid SmsCodeRequest request) {
		SmsCodeResponse response = new SmsCodeResponse();
		int cnt = smsService.save(request.getMobile(), request.getVerificationType(), request.getVerificationCode(),
				request.getPlatform(), request.getStatus());
		response.setCnt(cnt);
		return response;
	}

	/**
	 * 检查短信验证码
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/check")
	public int checkMobileCode(@RequestBody @Valid SmsCodeRequest request) {
		logger.info("checkMobileCode...param is :{}", JSONObject.toJSONString(request));
		String mobile = request.getMobile();
		String verificationCode = request.getVerificationCode();
		String verificationType = request.getVerificationType();
		String platform = request.getPlatform();
		Integer status = request.getStatus();
		Integer updateStatus = request.getUpdateStatus();
		int result = smsService.updateCheckMobileCode(mobile, verificationCode, verificationType, platform, status,
				updateStatus);
		return result;
	}
}
