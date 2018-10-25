/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.MessagePushTag;
import com.hyjf.am.config.service.MessagePushTagServcie;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.MessagePushTagResponse;
import com.hyjf.am.resquest.config.MessagePushTagRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTagController, v0.1 2018/6/26 12:01
 */
@RestController
@RequestMapping("/am-config/messagePushTag")
public class MessagePushTagController extends BaseConfigController{

	@Autowired
	private MessagePushTagServcie messagePushTagServcie;

	/**
	 * 根据名称获取消息模板标签
	 *
	 * @param tagName
	 * @return
	 */
	@RequestMapping("/findMsgTagByTagName/{tagName}")
	public MessagePushTagResponse findMsgTagByTagName(@PathVariable String tagName) {
		MessagePushTagResponse response = new MessagePushTagResponse();
		MessagePushTag messagePushTag = messagePushTagServcie.findMsgTagByTagName(tagName);
		if (messagePushTag != null) {
			MessagePushTagVO messagePushTagVO = new MessagePushTagVO();
			BeanUtils.copyProperties(messagePushTag, messagePushTagVO);
			response.setResult(messagePushTagVO);
		}
		return response;
	}

	/**
	 * 根据名称获取消息模板标签
	 *
	 * @param tagName
	 * @return
	 */
	@RequestMapping("/selectMsgTagByTagId/{tagId}")
	public MessagePushTagResponse selectMsgTagByTagId(@PathVariable Integer tagId) {
		MessagePushTagResponse response = new MessagePushTagResponse();
		MessagePushTag messagePushTag = messagePushTagServcie.getRecord(tagId);
		if (messagePushTag != null) {
			MessagePushTagVO messagePushTagVO = new MessagePushTagVO();
			BeanUtils.copyProperties(messagePushTag, messagePushTagVO);
			response.setResult(messagePushTagVO);
		}
		return response;
	}

	/**
	 * 获取消息推送标签管理列表
	 * @param request
	 * @return
	 */
	@PostMapping("/searchList")
	public MessagePushTagResponse searchList(@RequestBody MessagePushTagRequest request) {
		MessagePushTagResponse response = new MessagePushTagResponse();
		Integer count = messagePushTagServcie.countRecord(request);
		Paginator paginator = new Paginator(request.getCurrPage(), count, request.getPageSize());
		if (request.getPageSize() == 0) {
			paginator = new Paginator(request.getCurrPage(), count);
		}
		List<MessagePushTag> messagePushTagList = messagePushTagServcie.searchList(request,paginator.getOffset(),paginator.getLimit());
		response.setCount(count);
		if (count > 0) {
			List<MessagePushTagVO> list = CommonUtils.convertBeanList(messagePushTagList,MessagePushTagVO.class);
			response.setResultList(list);
			response.setRtn(Response.SUCCESS);
		}
		return response;
	}

 }
