/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.client.messagePushTemplateClient;

import java.util.List;

import com.hyjf.am.vo.config.MessagePushTemplateVO;

/**
 * 消息推送模版
 * 
 * @author fuqiang
 * @version MessagePushTemplateClient, v0.1 2018/6/21 18:32
 */
public interface MessagePushTemplateClient {
	/**
	 * 获取所有模版
	 * 
	 * @return
	 */
	List<MessagePushTemplateVO> getAllTemplates();

}
