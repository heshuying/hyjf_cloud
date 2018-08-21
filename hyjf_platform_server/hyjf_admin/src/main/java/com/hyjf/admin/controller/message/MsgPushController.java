/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MsgPushTemplateService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fuqiang
 * @version MsgPushController, v0.1 2018/6/26 9:31
 */
@Api(tags = "消息中心-消息推送")
@RestController
@RequestMapping("/hyjf-admin/msgpush/template")
public class MsgPushController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MsgPushTemplateService msgPushTemplateService;

	/**
	 * 查询所有消息推送模板
	 *
	 * @return
	 */
	@ApiOperation(value = "查询所有消息推送模板", notes = "查询所有消息推送模板")
	@GetMapping("/findAll")
	public AdminResult<ListResult<MessagePushTemplateVO>> findAll() {
		MessagePushTemplateResponse response = msgPushTemplateService.findAll();
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	/**
	 * 根据条件查询消息推送模板
	 *
	 * @return
	 */
	@ApiOperation(value = "根据条件查询消息推送模板", notes = "根据条件查询消息推送模板")
	@PostMapping("/findMsgPushTemplate")
	public AdminResult<ListResult<MessagePushTemplateVO>> findMailTemplate(@RequestBody MsgPushTemplateRequest request) {
		MessagePushTemplateResponse response = msgPushTemplateService.findMsgPushTemplate(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	/**
	 * 新增消息推送模板
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新增消息推送模板", notes = "新增消息推送模板")
	@PostMapping("/insertMsgPushTemplate")
	public JSONObject insertMailTemplate(@RequestBody MsgPushTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			msgPushTemplateService.insertMsgPushTemplate(request);
			jsonObject.put("status", "000");
			jsonObject.put("statusDesc", "添加消息推送模板成功");
			return jsonObject;
		} catch (Exception e) {
			logger.error("添加消息推送模板失败......", e);
			jsonObject.put("status", "99");
			jsonObject.put("statusDesc", "添加消息推送模板失败");
			return jsonObject;
		}
	}
}
