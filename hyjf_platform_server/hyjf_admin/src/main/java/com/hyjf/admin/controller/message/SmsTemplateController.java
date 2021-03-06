/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.SmsTemplateService;
import com.hyjf.am.response.config.SmsTemplateResponse;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.am.vo.config.SmsTemplateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsTemplateController, v0.1 2018/6/25 10:09
 */
@Api(tags = "消息中心-短信模板")
@RestController
@RequestMapping("/hyjf-admin/message/smsTemplate")
public class SmsTemplateController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	/** 权限关键字 */
	public static final String PERMISSIONS = "template";

	@Autowired
	private SmsTemplateService smsTemplateService;

	/**
	 * 查询所有短信模版
	 *
	 * @return
	 */
	@ApiOperation(value = "查询所有短信模版", notes = "查询所有短信模版")
	@GetMapping("/smsTemplateList")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<SmsTemplateVO>> smsTemplateList() {
		List<SmsTemplateVO> voList = smsTemplateService.findAll();
		if (CollectionUtils.isEmpty(voList)) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(ListResult.build(voList, voList.size()));
	}

	/**
	 * 根据条件查询所有短信模版
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据条件查询所有短信模版", notes = "根据条件查询所有短信模版")
	@PostMapping("/findSmsTemplate")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<SmsTemplateVO>> findSmsTemplate(@RequestBody SmsTemplateRequest request) {
		SmsTemplateResponse response = smsTemplateService.findSmsTemplate(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult(response);
	}

	/**
	 * 新增短信模版
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新增短信模版", notes = "新增短信模版")
	@PostMapping("/insertTemplate")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public JSONObject insertSmsTemplate(@RequestBody SmsTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			int num = smsTemplateService.insertSmsTemplate(request);
			if (num > 0) {
				jsonObject.put("status", "000");
				jsonObject.put("statusDesc", "添加短信模板成功");
				return jsonObject;
			} else {
				jsonObject.put("status", "99");
				jsonObject.put("statusDesc", "添加短信模板失败");
				return jsonObject;
			}
		} catch (Exception e) {
			logger.error("添加短信模板失败...", e);
			jsonObject.put("status", "99");
			jsonObject.put("statusDesc", "添加短信模板失败");
			return jsonObject;
		}
	}

	/**
	 * 短信模板详情
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "短信模板详情", notes = "短信模板详情")
	@PostMapping("/infoAction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult infoAction(@RequestBody SmsTemplateRequest request) {
		SmsTemplateVO vo = smsTemplateService.selectSmsTemByTplCode(request);
		return new AdminResult(vo);
	}

	/**
	 * 修改短信模版
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "修改短信模版", notes = "修改短信模版")
	@PostMapping("/updateSmsTemplate")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public JSONObject updateSmsTemplate(@RequestBody SmsTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			int num = smsTemplateService.updateSmsTemplate(request);
			if (num > 0) {
				jsonObject.put("status", "000");
				jsonObject.put("statusDesc", "修改短信模版成功");
				return jsonObject;
			} else {
				jsonObject.put("status", "99");
				jsonObject.put("statusDesc", "修改短信模版失败");
				return jsonObject;
			}
		} catch (Exception e) {
			logger.error("修改短信模版失败...", e);
			jsonObject.put("status", "99");
			jsonObject.put("statusDesc", "修改短信模版失败");
			return jsonObject;
		}
	}

	@ApiOperation(value = "开关闭短信模板", notes = "开关闭短信模板")
	@PostMapping("/updateStatus")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public JSONObject updateStatus(@RequestBody SmsTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			int num = smsTemplateService.updateStatus(request);
			if (num > 0) {
				jsonObject.put("status", "000");
				jsonObject.put("statusDesc", "开关闭短信模板成功");
				return jsonObject;
			} else {
				jsonObject.put("status", "99");
				jsonObject.put("statusDesc", "开关闭短信模板失败");
				return jsonObject;
			}
		} catch (Exception e) {
			logger.error("开关闭短信模板失败...", e);
			jsonObject.put("status", "99");
			jsonObject.put("statusDesc", "开关闭短信模板失败");
			return jsonObject;
		}
	}

}
