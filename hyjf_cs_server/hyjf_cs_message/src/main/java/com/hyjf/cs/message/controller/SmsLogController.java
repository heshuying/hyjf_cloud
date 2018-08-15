/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.SmsLogResponse;
import com.hyjf.am.response.admin.SmsOntimeResponse;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.SmsOntimeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.SmsLog;
import com.hyjf.cs.message.bean.mc.SmsOntime;
import com.hyjf.cs.message.mongo.mc.SmsLogDao;
import com.hyjf.cs.message.service.message.SmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsLogController, v0.1 2018/6/23 14:10
 */
@RestController
@RequestMapping("/cs-message/sms_log")
public class SmsLogController extends BaseController {

	@Autowired
	private SmsLogService smsLogService;

	@Autowired
	private SmsLogDao smsLogDao;

	/**
	 * 查询消息中心短信发送记录
	 * @return
	 */
	@RequestMapping("/list")
	public JSONObject smsLogList() {
		JSONObject jsonObject = new JSONObject();
		List<SmsLog> list = smsLogDao.findAll();
		jsonObject.put("smsLogList", list);
		return jsonObject;
	}

	/**
	 * 根据条件查询消息中心短信发送记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/find")
	public JSONObject findSmsLog(@RequestBody SmsLogRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<SmsLog> list = smsLogService.findSmsLog(request);
		jsonObject.put("smsLogList", list);
		return jsonObject;
	}

	/**
	 * 查询定时发送短信列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/query_time")
	public SmsOntimeResponse queryTime(SmsLogRequest request) {
		SmsOntimeResponse response = new SmsOntimeResponse();
		List<SmsOntime> list = smsLogService.queryTime(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<SmsOntimeVO> voList = CommonUtils.convertBeanList(list, SmsOntimeVO.class);
			response.setResultList(voList);
		}
		return response;
	}

	/**
	 * 查询条件查询短信记录列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/query_log_count")
	public SmsLogResponse queryLogCount(SmsLogRequest request) {
		SmsLogResponse response = new SmsLogResponse();
		Integer logCount = smsLogService.queryLogCount(request);
		if (logCount != null) {
			response.setLogCount(logCount);
		}
		return response;
	}
}
