/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.config.MessagePushTagRequest;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.config.dao.model.auto.MessagePushTag;
import com.hyjf.am.config.service.MessagePushTagServcie;
import com.hyjf.am.response.config.MessagePushTagResponse;
import com.hyjf.am.vo.config.MessagePushTagVO;

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

 }
