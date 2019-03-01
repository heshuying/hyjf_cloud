/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushHistoryService;
import com.hyjf.admin.service.MessagePushTemplateStaticsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushTagResponse;
import com.hyjf.am.response.admin.MessagePushTemplateStaticsResponse;
import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fq
 * @version MessagePushTemplateStaticsController, v0.1 2018/8/14 14:13
 */
@Api(tags = "消息中心-app消息推送-模板消息统计报表")
@RestController
@RequestMapping("/hyjf-admin/msgpush/statics/template")
public class MessagePushTemplateStaticsController extends BaseController {
	@Autowired
	private MessagePushTemplateStaticsService service;
	@Autowired
	MessagePushHistoryService messagePushHistoryService;

	@ApiOperation(value = "查询模板消息统计报表", notes = "查询模板消息统计报表")
	@PostMapping("/select")
	public AdminResult select(
			@RequestBody MessagePushTemplateStaticsRequest request) {
		MessagePushTemplateStaticsResponse response = service.selectTemplateStatics(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult(response);
	}

	@ApiOperation(value = "获取标签列表", notes = "获取标签列表")
	@GetMapping("/get_tag_list")
	public AdminResult getMsgTagList() {
		MessagePushTagResponse response = new MessagePushTagResponse();
		// modify by libin sonar start 修改判空逻辑
		List<MessagePushTagVO> allPushTagList = messagePushHistoryService.getAllPushTagList();
		if(allPushTagList == null){
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		// modify by libin sonar end
		response.setResultList(allPushTagList);

		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult(response);
	}
}
