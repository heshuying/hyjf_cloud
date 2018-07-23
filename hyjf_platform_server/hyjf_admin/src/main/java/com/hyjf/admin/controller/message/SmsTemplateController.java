/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.SmsTemplateService;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.am.vo.config.SmsTemplateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsTemplateController, v0.1 2018/6/25 10:09
 */
@Api(value = "短信模板", description = "短信模板")
@RestController
@RequestMapping("/hyjf-admin/message/smsTemplate")
public class SmsTemplateController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsTemplateService smsTemplateService;

	/**
	 * 查询所有短信模版
	 *
	 * @return
	 */
	@ApiOperation(value = "查询所有短信模版", notes = "查询所有短信模版")
	@RequestMapping("/smsTemplateList")
	public JSONObject smsTemplateList() {
		JSONObject jsonObject = new JSONObject();
		List<SmsTemplateVO> voList = smsTemplateService.findAll();
		jsonObject.put("smsTemplateList", voList);
		return jsonObject;
	}

	/**
	 * 根据条件查询所有短信模版
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据条件查询所有短信模版", notes = "根据条件查询所有短信模版")
	@RequestMapping("/findSmsTemplate")
	public JSONObject findSmsTemplate(@RequestBody SmsTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<SmsTemplateVO> voList = smsTemplateService.findSmsTemplate(request);
		jsonObject.put("smsTemplateList", voList);
		return jsonObject;
	}

	/**
	 * 新增短信模版
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新增短信模版", notes = "新增短信模版")
	@RequestMapping("/insertTemplate")
	public JSONObject insertSmsTemplate(@RequestBody SmsTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			smsTemplateService.insertSmsTemplate(request);
			jsonObject.put("status", "000");
			jsonObject.put("statusDesc", "添加短信模板成功");
			return jsonObject;
		} catch (Exception e) {
			logger.error("添加短信模板失败...", e);
			jsonObject.put("status", "99");
			jsonObject.put("statusDesc", "添加短信模板失败");
			return jsonObject;
		}
	}
}
