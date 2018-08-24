/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePush;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgDao;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgHistoryDao;
import com.hyjf.cs.message.mongo.mc.MessagePushTemplateStaticsDao;
import com.hyjf.cs.message.mq.base.MessageContent;
import com.hyjf.cs.message.mq.producer.AppMessageProducer;
import com.hyjf.cs.message.service.msgpush.MsgPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author fuqiang
 * @version MsgPushServiceImpl, v0.1 2018/6/21 15:55
 */
@Service
public class MsgPushServiceImpl implements MsgPushService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessagePushMsgDao messagePushMsgDao;

	@Autowired
	private MessagePushTemplateStaticsDao msgStaticsDao;

	@Autowired
	private AppMessageProducer appMessageProducer;

	@Autowired
	private AmConfigClient amConfigClient;

	@Autowired
	private MessagePushMsgHistoryDao msgHistoryDao;

	@Override
	public void pushMessage() {
		List<MessagePush> list = messagePushMsgDao.findAllMessage();
		try {
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					// 添加到发送队列
					AppMsMessage message = new AppMsMessage(MessageConstant.APP_MS_SEND_FOR_MSG, list.get(i).getId());
					appMessageProducer.messageSend(
							new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(message)));
				}
			}
		} catch (MQException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MessagePushTemplateVO> getAllTemplates() {
		// 获取所有模板
		List<MessagePushTemplateVO> templateList = amConfigClient.getAllTemplates();
		// 插入统计数据
		for (int i = 0; i < templateList.size(); i++) {
			this.insertTemplateStatics(templateList.get(i));
		}
		return null;
	}

	@Override
	public Integer countMsgHistoryRecord(Integer tagId, Integer userId, String platform) {
		return msgHistoryDao.countMsgHistoryRecord(tagId, userId, platform);
	}

	@Override
	public List<MessagePushMsgHistory> getMsgHistoryList(Integer tagId, Integer userId, String platform, int limitStart, int limitEnd) {
		return msgHistoryDao.getMsgHistoryList(tagId, userId, platform, limitStart, limitEnd);
	}

	@Override
	public MessagePushMsgHistory getMsgPushMsgHistoryById(String msgId) {
		return msgHistoryDao.getMsgPushMsgHistoryById(msgId);
	}

	@Override
	public void updateMsgPushMsgHistory(MessagePushMsgHistory msgHistory) {
		msgHistoryDao.updateMsgPushMsgHistory(msgHistory);
	}

	@Override
	public void updateAllMsgPushMsgHistory(Integer userId, String platform) {
		logger.info("全部已读什么都不做，等二期处理....");
	}

	@Override
	public List<MessagePush> getMsgStaticsListByTime(Integer startTime, Integer endTime) {
		return messagePushMsgDao.getMsgStaticsListByTime(startTime, endTime);
	}

	@Override
	public void insertMessagePush(MessagePush messagePush) {
		messagePushMsgDao.insert(messagePush);
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
