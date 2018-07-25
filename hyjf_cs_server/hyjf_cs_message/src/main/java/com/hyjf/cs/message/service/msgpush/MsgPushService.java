/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush;

import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;

import java.util.List;

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

	/**
	 * 获得消息列表数量
	 * @param tagId
	 * @param userId
	 * @param platform
	 * @return
	 */
	Integer countMsgHistoryRecord(Integer tagId,Integer userId,String platform);

	/**
	 * 获得消息列表
	 * @param tagId
	 * @param userId
	 * @param platform
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	List<MessagePushMsgHistory> getMsgHistoryList(Integer tagId,Integer userId,String platform,int limitStart,int limitEnd);
}
