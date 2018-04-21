package com.hyjf.am.user.controller;

import javax.validation.Valid;

import com.hyjf.am.user.request.SmsCodeRequest;
import com.hyjf.am.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version SmsController, v0.1 2018/4/12 17:26
 */

@RestController
@RequestMapping("/am-user/sms")
public class SmsController {

	@Autowired
	private SmsService smsService;

	@RequestMapping(value = "/saveSmsCode", method = RequestMethod.POST)
	public Integer saveSmsCode(@RequestBody @Valid SmsCodeRequest request) {
		return smsService.save(request.getMobile(), request.getVerificationType(), request.getVerificationCode(),
				request.getPlatform(), request.getStatus());
	}
}
