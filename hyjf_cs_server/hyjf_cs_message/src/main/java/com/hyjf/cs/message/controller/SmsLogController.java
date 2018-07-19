/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.SmsLog;
import com.hyjf.cs.message.mongo.mc.SmsLogDao;
import com.hyjf.cs.message.service.message.SmsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsLogController, v0.1 2018/6/23 14:10
 */
@Api(value = "消息中心短信发送记录")
@RestController
@RequestMapping("/cs-message/message/smsLog")
public class SmsLogController extends BaseController {

	@Autowired
	private SmsLogService smsLogService;

	@Autowired
	private SmsLogDao smsLogDao;

	@ApiOperation(value = "消息中心短信发送记录", notes = "查询消息中心短信发送记录")
	@RequestMapping("/smsLogList")
	public JSONObject smsLogList() {
		JSONObject jsonObject = new JSONObject();
		List<SmsLog> list = smsLogDao.findAll();
		jsonObject.put("smsLogList", list);
		return jsonObject;
	}

	@ApiOperation(value = "消息中心短信发送记录", notes = "根据条件查询消息中心短信发送记录")
	@RequestMapping("/findSmsLog")
	public JSONObject findSmsLog(@RequestBody SmsLogRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<SmsLog> list = smsLogService.findSmsLog(request);
		jsonObject.put("smsLogList", list);
		return jsonObject;
	}
}
