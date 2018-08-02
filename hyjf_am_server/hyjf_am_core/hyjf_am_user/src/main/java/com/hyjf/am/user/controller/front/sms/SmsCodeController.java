package com.hyjf.am.user.controller.front.sms;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.user.SmsCodeResponse;
import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author xiasq
 * @version SmsCodeController, v0.1 2018/4/12 17:26
 */

@RestController
@RequestMapping("/am-user/smsCode")
public class SmsCodeController extends BaseController {

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
		boolean isUpdate = request.isUpdate();
		int result = smsService.updateCheckMobileCode(mobile, verificationCode, verificationType, platform, status,
				updateStatus, isUpdate);
		return result;
	}

	/**
	 * 只校验验证码  不修改状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/only_check")
	public int onlyCheckMobileCode(@RequestBody @Valid SmsCodeRequest request) {
		logger.info("onlyCheckMobileCode...param is :{}", JSONObject.toJSONString(request));
		String mobile = request.getMobile();
		String verificationCode = request.getVerificationCode();
		String verificationType = request.getVerificationType();
		String platform = request.getPlatform();
		Integer status = request.getStatus();
		Integer updateStatus = request.getUpdateStatus();
		int result = smsService.checkMobileCode(mobile, verificationCode, verificationType, platform, status,
				updateStatus);
		return result;
	}
}
