/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.MessagePush;
import com.hyjf.cs.message.bean.MessagePushTemplateStatics;
import com.hyjf.cs.message.client.messagePushTemplateClient.MessagePushTemplateClient;
import com.hyjf.cs.message.mongo.MessagePushMsgDao;
import com.hyjf.cs.message.mongo.MessagePushTemplateStaticsDao;
import com.hyjf.cs.message.mq.AppMessageProducer;
import com.hyjf.cs.message.mq.Producer;
import com.hyjf.cs.message.service.msgpush.MsgPushService;

/**
 * @author fuqiang
 * @version MsgPushServiceImpl, v0.1 2018/6/21 15:55
 */
@Service
public class MsgPushServiceImpl implements MsgPushService {

	@Autowired
	private MessagePushMsgDao messagePushMsgDao;

	@Autowired
	private MessagePushTemplateStaticsDao msgStaticsDao;

	@Autowired
	private AppMessageProducer appMessageProducer;

	@Autowired
	private MessagePushTemplateClient messagePushTemplateClient;

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

	@Override
	public List<MessagePushTemplateVO> getAllTemplates() {
		// 获取所有模板
		List<MessagePushTemplateVO> templateList = messagePushTemplateClient.getAllTemplates();
		// 插入统计数据
		for (int i = 0; i < templateList.size(); i++) {
			this.insertTemplateStatics(templateList.get(i));
		}
		return null;
	}

	private void insertTemplateStatics(MessagePushTemplateVO template) {
		// 判断是否添加过
		List<MessagePushTemplateStatics> list = msgStaticsDao
				.getMessagePushTemplateStaticsByMsgCode(template.getTemplateCode());
		if (list.size() > 0) {
			return;
		}
		MessagePushTemplateStatics msgSta = new MessagePushTemplateStatics();
		msgSta.setMsgId(template.getId());
		msgSta.setMsgCode(template.getTemplateCode());
		msgSta.setMsgTitle(template.getTemplateTitle());
		msgSta.setTagId(template.getTagId());
		msgSta.setSendTime(template.getCreateTime());
		msgSta.setAndroidDestinationCount(0);
		msgSta.setIosDestinationCount(0);
		msgSta.setAndroidReadCount(0);
		msgSta.setAndroidSendCount(0);
		msgSta.setIosReadCount(0);
		msgSta.setIosSendCount(0);
		msgSta.setCreateTime(GetDate.getNowTime10());
		// 插入数据
		this.msgStaticsDao.insert(msgSta);
	}
}
