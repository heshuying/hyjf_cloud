/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush;

import java.util.List;

import com.hyjf.am.vo.config.MessagePushTemplateVO;

/**
 * @author fuqiang
 * @version MsgPushService, v0.1 2018/6/21 15:55
 */
public interface MsgPushService {
	/**
	 * 消息推送
	 */
	void pushMessage();

	/**
	 * 消息推送统计
	 * @return
	 */
	List<MessagePushTemplateVO> getAllTemplates();
}
