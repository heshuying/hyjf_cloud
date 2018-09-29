package com.hyjf.am.config.controller.admin.message;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.SmsConfig;
import com.hyjf.am.config.service.SmsConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.vo.config.SmsConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	/**
	 * 查询短信加固数据
	 * @author xiehuili
	 * @return
	 */
	@RequestMapping("/initSmsConfig")
	public SmsConfigResponse initSmsConfig(@RequestBody SmsConfigRequest request) {
		logger.info("查找短信固定...");
		SmsConfigResponse response =new SmsConfigResponse();
		SmsConfigVO smscon = smsConfigService.initSmsConfig(request);
		if(smscon != null){
			response.setResult(smscon);
		}
		return response;
	}
	/**
	 * 添加短信加固数据
	 * @param request
	 * @author xiehuili
	 * @return
	 */
	@RequestMapping("/insertSmsConfig")
	public SmsConfigResponse insertSmsConfig(@RequestBody SmsConfigRequest request) {
		logger.info("添加短信固定...");
		SmsConfigResponse response =new SmsConfigResponse();
		int count  = smsConfigService.insertSmsConfig(request);
		if(count > 0){
			response.setRtn(Response.SUCCESS);
			return response;
		}
		return null;
	}
	/**
	 * 修改短信加固数据
	 * @param request
	 * @author xiehuili
	 * @return
	 */
	@RequestMapping("/updateSmsConfig")
	public SmsConfigResponse updateSmsConfig(@RequestBody SmsConfigRequest request) {
		logger.info("添加短信固定...");
		SmsConfigResponse response =new SmsConfigResponse();
		int count  = smsConfigService.updateSmsConfig(request);
		if(count > 0){
			response.setRtn(Response.SUCCESS);
			return response;
		}
		return null;
	}

}
