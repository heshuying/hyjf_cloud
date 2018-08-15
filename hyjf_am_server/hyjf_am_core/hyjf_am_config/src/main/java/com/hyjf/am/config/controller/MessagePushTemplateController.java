/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;
import com.hyjf.am.config.service.MessagePushTagServcie;
import com.hyjf.am.config.service.MessagePushTemplateServcie;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消息推送模板
 *
 * @author fuqiang
 * @version MessagePushTemplateController, v0.1 2018/5/8 10:32
 */
@RestController
@RequestMapping("/am-config/messagePushTemplate")
public class MessagePushTemplateController {

	Logger logger = LoggerFactory.getLogger(MessagePushTemplateController.class);

	@Autowired
	private MessagePushTemplateServcie templateServcie;

	@Autowired
	private MessagePushTagServcie messagePushTagServcie;

	/**
	 * 根据tplCode查询消息推送模板
	 *
	 * @param tplCode
	 * @return
	 */
	@RequestMapping("/findMessagePushTemplateByCode/{tplCode}")
	public MessagePushTemplateResponse findMessagePushTemplateByCode(@PathVariable String tplCode) {
		logger.info("查询消息推送模板开始... tplCode is :{}", tplCode);
		MessagePushTemplateResponse response = new MessagePushTemplateResponse();
		MessagePushTemplateVO messagePushTemplateVO = null;
		MessagePushTemplate messagePushTemplate = templateServcie.findMessagePushTemplateByCode(tplCode);
		if (messagePushTemplate != null) {
			messagePushTemplateVO = new MessagePushTemplateVO();
			BeanUtils.copyProperties(messagePushTemplate, messagePushTemplateVO);
		}
		logger.info("messagePushTemplateVO is :{}", messagePushTemplateVO);
		response.setResult(messagePushTemplateVO);
		return response;
	}

	@RequestMapping("/getAllTemplates")
	public MessagePushTemplateResponse getAllTemplates() {
		logger.info("查询所有消息推送模板开始...");
		MessagePushTemplateResponse response = new MessagePushTemplateResponse();
		List<MessagePushTemplate> list = templateServcie.getAllTemplates();

		if (!CollectionUtils.isEmpty(list)) {
			response.setResultList(CommonUtils.convertBeanList(list, MessagePushTemplateVO.class));
		}
		return response;
	}

	/**
	 * 根据条件查询消息推送模板
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/findMsgPushTemplate")
	public MessagePushTemplateResponse findMsgPushTemplate(MsgPushTemplateRequest request) {
		logger.info("根据条件查询消息推送模板开始...");
		MessagePushTemplateResponse response = new MessagePushTemplateResponse();
		List<MessagePushTemplate> list = templateServcie.findMsgPushTemplate(request);

		if (!CollectionUtils.isEmpty(list)) {
			response.setResultList(CommonUtils.convertBeanList(list, MessagePushTemplateVO.class));
		}
		return response;
	}

	@RequestMapping("/insertMsgPushTemplate")
	public void insertMsgPushTemplate(MsgPushTemplateRequest request) {
		templateServcie.insertMsgPushTemplate(request);
	}

	/**
	 * 获取消息模板列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchList")
	public MessagePushTemplateResponse searchList(MsgPushTemplateRequest request) {
		MessagePushTemplateResponse response = new MessagePushTemplateResponse();
		Integer count = templateServcie.countRecord(request);
		Paginator paginator = new Paginator(request.getCurrPage(), count, request.getPageSize());
		if (request.getPageSize() == 0) {
			paginator = new Paginator(request.getCurrPage(), count);
		}
		List<MessagePushTemplate> list = templateServcie.searchList(request,paginator.getOffset(),paginator.getLimit());
		response.setCount(count);
		if (!CollectionUtils.isEmpty(list)) {
			if (count > 0) {
				List<MessagePushTemplateVO> templateVOS = CommonUtils.convertBeanList(list,MessagePushTemplateVO.class);
				response.setResultList(templateVOS);
				response.setRtn(Response.SUCCESS);
			}
		}
		return response;
	}

	@RequestMapping("/findMsgPushTemplateById/{id}")
	public MessagePushTemplateResponse findMsgPushTemplateById(@PathVariable Integer id) {
		MessagePushTemplateResponse response = new MessagePushTemplateResponse();
		MessagePushTemplate messagePushTemplate = templateServcie.findMsgPushTemplateById(id);
		if (messagePushTemplate != null) {
			MessagePushTemplateVO templateVO = new MessagePushTemplateVO();
			BeanUtils.copyProperties(messagePushTemplate,templateVO);
			response.setResult(templateVO);
		}
		return response;
	}
	
}
