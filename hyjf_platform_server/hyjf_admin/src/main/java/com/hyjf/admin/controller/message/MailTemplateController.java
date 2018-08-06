/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.MailTemplateService;
import com.hyjf.am.resquest.config.MailTemplateRequest;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
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
 * @version MailTemplateController, v0.1 2018/6/25 14:46
 */
@Api(value = "邮件模板", tags = "邮件模板")
@RestController
@RequestMapping("/hyjf-admin/mail/template")
public class MailTemplateController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MailTemplateService mailTemplateService;

	/**
	 * 查询所有邮件配置模板
	 *
	 * @return
	 */
	@ApiOperation(value = "查询所有邮件配置模板", notes = "查询所有邮件配置模板")
	@RequestMapping("/findAll")
	public JSONObject findAll() {
		JSONObject jsonObject = new JSONObject();
		List<SmsMailTemplateVO> voList = mailTemplateService.findAll();
		jsonObject.put("mailTemplateList", voList);
		return jsonObject;
	}

	/**
	 * 根据条件查询邮件配置模板
	 *
	 * @return
	 */
	@ApiOperation(value = "根据条件查询邮件配置模板", notes = "根据条件查询邮件配置模板")
	@RequestMapping("/findMailTemplate")
	public JSONObject findMailTemplate(@RequestBody MailTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<SmsMailTemplateVO> voList = mailTemplateService.findMailTemplate(request);
		jsonObject.put("mailTemplateList", voList);
		return jsonObject;
	}

	/**
	 * 新增邮件模板
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新增邮件模板", notes = "新增邮件模板")
	@RequestMapping("/insertMailTemplate")
	public JSONObject insertMailTemplate(@RequestBody MailTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			mailTemplateService.insertMailTemplate(request);
            jsonObject.put("status", "000");
            jsonObject.put("statusDesc", "添加邮件模板成功");
            return jsonObject;
		} catch (Exception e) {
		    logger.error("添加邮件模板失败......", e);
            jsonObject.put("status", "99");
            jsonObject.put("statusDesc", "添加邮件模板失败");
            return jsonObject;
		}
	}
}
