/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.msgpush.MsgPushService;

/**
 * 消息推送定时任务
 * 
 * @author fuqiang
 * @version MessagePushMsgController, v0.1 2018/6/21 15:52
 */
@RestController
@RequestMapping("/message")
public class MessagePushMsgController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MsgPushService msgPushService;

	@RequestMapping("/msgPush")
	public void messagePush() {
		logger.info("消息推送定时任务开始......");
		msgPushService.pushMessage();
	}
}
