/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.message.bean.MessagePush;
import com.hyjf.cs.message.mongo.MessagePushMsgDao;
import com.hyjf.cs.message.mq.AppMessageProducer;
import com.hyjf.cs.message.mq.Producer;

/**
 * @author fuqiang
 * @version MsgPushServiceImpl, v0.1 2018/6/21 15:55
 */
@Service
public class MsgPushServiceImpl implements MsgPushService {

	@Autowired
	private MessagePushMsgDao messagePushMsgDao;

	@Autowired
	private AppMessageProducer appMessageProducer;

	@Override
	public void pushMessage() {
		List<MessagePush> list = messagePushMsgDao.findAllMessage();
		try {
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					// 添加到发送队列
					AppMsMessage message = new AppMsMessage(MessageConstant.APPMSSENDFORMSG, list.get(i).getId());
					appMessageProducer.messageSend(
							new Producer.MassageContent(MQConstant.APP_MESSAGE_TOPIC, JSON.toJSONBytes(message)));
				}
			}
		} catch (MQException e) {
			e.printStackTrace();
		}
	}
}
