package com.hyjf.am.config.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.dao.model.auto.SmsConfig;
import com.hyjf.am.config.service.SmsConfigService;
import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.vo.config.SmsConfigVO;

/**
 * @author xiasq
 * @version SmsConfigController, v0.1 2018/4/24 18:16
 */

@RestController
@RequestMapping("/am-config/smsConfig")
public class SmsConfigController extends BaseConfigController{
	@Autowired
	private SmsConfigService smsConfigService;

	@RequestMapping("/findOne")
	public SmsConfigResponse findOne() {
		logger.info("查找短信配置...");
		SmsConfigResponse response = new SmsConfigResponse();
		SmsConfig smsConfig = smsConfigService.findOne();
		SmsConfigVO smsConfigVO = null;
		if (smsConfig != null) {
			smsConfigVO = new SmsConfigVO();
			BeanUtils.copyProperties(smsConfig, smsConfigVO);
		}
		logger.info("smsConfigVO is : {}", smsConfigVO);
		response.setResult(smsConfigVO);
		return response;
	}
}
