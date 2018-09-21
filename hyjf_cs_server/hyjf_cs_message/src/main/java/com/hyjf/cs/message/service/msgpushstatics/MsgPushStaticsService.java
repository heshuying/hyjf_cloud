/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpushstatics;

import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;

import java.util.List;

/**
 * @author fuqiang
 * @version MsgPushStaticsService, v0.1 2018/6/22 10:01
 */
public interface MsgPushStaticsService {
	/**
	 * 更新模板统计数据
	 * 
	 * @param messagePushTemplateStatics
	 * @param startTime
	 * @param endTime
	 */
	void updatemsgPushStatics(MessagePushTemplateStatics messagePushTemplateStatics, Integer startTime,
			Integer endTime);

	/**
	 * 查询相应时间内统计数据
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    List<MessagePushTemplateStatics> getTemplateStaticsListByTime(String startTime, String endTime);
}
