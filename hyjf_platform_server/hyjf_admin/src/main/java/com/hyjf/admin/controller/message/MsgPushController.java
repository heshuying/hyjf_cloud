/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.MsgPushTemplateService;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version MsgPushController, v0.1 2018/6/26 9:31
 */
@RestController
@RequestMapping("/hyjf-admin/msgpush/template")
public class MsgPushController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MsgPushTemplateService msgPushTemplateService;

	/**
	 * 查询所有消息推送模板
	 *
	 * @return
	 */
	@RequestMapping("/findAll")
	public JSONObject findAll() {
		JSONObject jsonObject = new JSONObject();
		List<MessagePushTemplateVO> voList = msgPushTemplateService.findAll();
		jsonObject.put("msgPushTemplateList", voList);
		return jsonObject;
	}

	/**
	 * 根据条件查询消息推送模板
	 *
	 * @return
	 */
	@RequestMapping("/findMsgPushTemplate")
	public JSONObject findMailTemplate(@RequestBody MsgPushTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<MessagePushTemplateVO> voList = msgPushTemplateService.findMsgPushTemplate(request);
		jsonObject.put("msgPushTemplateList", voList);
		return jsonObject;
	}

	/**
	 * 新增消息推送模板
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertMsgPushTemplate")
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
