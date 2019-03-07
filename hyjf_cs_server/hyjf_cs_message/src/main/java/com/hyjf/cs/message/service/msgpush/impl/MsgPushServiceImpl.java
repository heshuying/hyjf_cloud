/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush.impl;

import cn.jpush.api.report.ReceivedsResult;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushPlatStatics;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.jpush.JPush;
import com.hyjf.cs.message.jpush.JPushPro;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgDao;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgHistoryDao;
import com.hyjf.cs.message.mongo.mc.MessagePushPlatStaticsDao;
import com.hyjf.cs.message.mongo.mc.MessagePushTemplateStaticsDao;
import com.hyjf.cs.message.mq.base.CommonProducer;
import com.hyjf.cs.message.mq.base.MessageContent;
import com.hyjf.cs.message.service.msgpush.MsgPushService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	private CommonProducer appMessageProducer;

	@Autowired
	private AmConfigClient amConfigClient;

	@Autowired
	private MessagePushMsgHistoryDao msgHistoryDao;

	@Autowired
	private MessagePushPlatStaticsDao msgPushPlatStaticsDao;

	@Override
	public void pushMessage() {
		List<MessagePushMsg> list = messagePushMsgDao.findAllMessage();
		try {
			if (!CollectionUtils.isEmpty(list)) {
				logger.info("待推送消息列表大小: {}", list.size());
				for (int i = 0; i < list.size(); i++) {
					// 添加到发送队列
					AppMsMessage message = new AppMsMessage(MessageConstant.APP_MS_SEND_FOR_MSG, list.get(i).getId());
					appMessageProducer.messageSend(
							new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(),message));
				}
			} else {
				logger.info("无待推送消息....");
			}
		} catch (MQException e) {
			logger.error(e.getMessage());
		}
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
	public List<MessagePushMsg> getMsgStaticsListByTime(Integer startTime, Integer endTime) {
		return messagePushMsgDao.getMsgStaticsListByTime(startTime, endTime);
	}

	@Override
	public void insertMessagePush(MessagePushMsg msg) {
		MessagePushTemplateStatics msgSta = new MessagePushTemplateStatics();
		msgSta.setMsgId(msg.getId());
		msgSta.setMsgCode(msg.getMsgCode());
		msgSta.setMsgTitle(msg.getMsgTitle());
		msgSta.setTagId(msg.getTagId().toString());
		msgSta.setSendTime(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(msg.getSendTime()));
		msgSta.setAndroidDestinationCount(0);
		msgSta.setIosDestinationCount(0);
		msgSta.setAndroidReadCount(0);
		msgSta.setAndroidSendCount(0);
		msgSta.setIosReadCount(0);
		msgSta.setIosSendCount(0);
		msgSta.setCreateTime(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(GetDate.getNowTime10()));
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("msgCode").is(msgSta.getMsgCode());
		List<MessagePushTemplateStatics> list = msgStaticsDao.find(query);
		if (list == null || list.size() == 0) {
			this.msgStaticsDao.insert(msgSta);
		}
		if (list.size() > 0) {
			Update update = new Update();
			update.set("msgId", msg.getId());
			update.set("mgCode", msg.getMsgCode());
			update.set("mgTitle", msg.getMsgTitle());
			update.set("tagId", msg.getTagId());
			update.set("sendTime", GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(msg.getSendTime()));
			update.set("androidDestinationCount", 0);
			update.set("iosDestinationCount", 0);
			update.set("androidReadCount", 0);
			update.set("androidSendCount", 0);
			update.set("iosReadCount", 0);
			update.set("iosSendCount", 0);
			update.set("createTime", GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(GetDate.getNowTime10()));
			this.msgStaticsDao.update(query, update);
		}
	}

	@Override
	public List<MessagePushTagVO> getPushTags() {
		return amConfigClient.getPushTags();
	}

	@Override
	public List<MessagePushMsgHistory> getMessagePushMsgHistorys(MessagePushTagVO messagePushTagVO, Date todayStart, Date todayEnd) {
		return msgHistoryDao.getMessagePushMsgHistorys(messagePushTagVO, todayStart, todayEnd);
	}

	@Override
	public void insertPushPlatStatics(MessagePushTagVO messagePushTagVO, List<MessagePushMsgHistory> msgHistoryList, Date today) {
		MessagePushPlatStatics platStatics = new MessagePushPlatStatics();
		Update update = new Update();
		platStatics.setAndroidDestinationCount(0);
		platStatics.setAndroidReadCount(0);
		platStatics.setAndroidSendCount(0);
		platStatics.setIosDestinationCount(0);
		platStatics.setIosReadCount(0);
		platStatics.setIosSendCount(0);
		platStatics.setReadCount(0);
		platStatics.setSendCount(0);
		platStatics.setDestinationCount(0);
		platStatics.setStaDate(GetDate.dateToString2(today));
		platStatics.setTagId(String.valueOf(messagePushTagVO.getId()));

		if (msgHistoryList != null && msgHistoryList.size() != 0) {
			for (int i = 0; i < msgHistoryList.size(); i++) {
				// =================送达数和阅读数从history表中取=================
				// =================以下是目标数=================
				platStatics.setAndroidDestinationCount(platStatics.getAndroidDestinationCount() + msgHistoryList.get(i).getMsgDestinationCountAndroid());
				update.set("androidDestinationCount", platStatics.getAndroidDestinationCount() + msgHistoryList.get(i).getMsgDestinationCountAndroid());
				platStatics.setIosDestinationCount(platStatics.getIosDestinationCount() + msgHistoryList.get(i).getMsgDestinationCountIos());
				update.set("iosDestinationCount", platStatics.getIosDestinationCount() + msgHistoryList.get(i).getMsgDestinationCountIos());
				platStatics.setDestinationCount(platStatics.getDestinationCount() + msgHistoryList.get(i).getMsgDestinationCountAndroid());
				update.set("destinationCount", platStatics.getDestinationCount() + msgHistoryList.get(i).getMsgDestinationCountAndroid());
				platStatics.setDestinationCount(platStatics.getDestinationCount() + msgHistoryList.get(i).getMsgDestinationCountIos());
				update.set("destinationCount", platStatics.getDestinationCount() + msgHistoryList.get(i).getMsgDestinationCountIos());
				// -----------------以下是阅读数-----------------
				// 推送给所有人
				if (msgHistoryList.get(i).getMsgDestinationType().intValue() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {
					platStatics.setAndroidReadCount(platStatics.getAndroidReadCount() + msgHistoryList.get(i).getMsgReadCountAndroid());
					update.set("androidReadCount", platStatics.getAndroidReadCount() + msgHistoryList.get(i).getMsgReadCountAndroid());
					platStatics.setIosReadCount(platStatics.getIosReadCount() + msgHistoryList.get(i).getMsgReadCountIos());
					update.set("iosReadCount", platStatics.getIosReadCount() + msgHistoryList.get(i).getMsgReadCountIos());
					platStatics.setReadCount(platStatics.getReadCount() + msgHistoryList.get(i).getMsgReadCountAndroid());
					update.set("readCount", platStatics.getReadCount() + msgHistoryList.get(i).getMsgReadCountAndroid());
					platStatics.setReadCount(platStatics.getReadCount() + msgHistoryList.get(i).getMsgReadCountIos());
					update.set("readCount", platStatics.getReadCount() + msgHistoryList.get(i).getMsgReadCountIos());
				}
				// 推送给个人
				if (msgHistoryList.get(i).getMsgDestinationType().intValue() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
					if (msgHistoryList.get(i).getMsgFirstreadPlat() != null) {
						// 如果首次阅读终端是android且安卓阅读数大于0,则安卓阅读数和总阅读数+1
						if (msgHistoryList.get(i).getMsgFirstreadPlat().intValue() == Integer.parseInt(CustomConstants.CLIENT_ANDROID) && msgHistoryList.get(i).getMsgReadCountAndroid() != null && msgHistoryList.get(i).getMsgReadCountAndroid().intValue() > 0) {
							platStatics.setAndroidReadCount(platStatics.getAndroidReadCount() + 1);
							update.set("androidReadCount", platStatics.getAndroidReadCount() + 1);
							platStatics.setReadCount(platStatics.getReadCount() + 1);
							update.set("readCount", platStatics.getReadCount() + 1);
						}
						// 如果首次阅读终端是ios且ios阅读数大于0,则ios阅读数和总阅读数+1
						if (msgHistoryList.get(i).getMsgFirstreadPlat().intValue() == Integer.parseInt(CustomConstants.CLIENT_IOS) && msgHistoryList.get(i).getMsgReadCountIos() != null && msgHistoryList.get(i).getMsgReadCountIos().intValue() > 0) {
							platStatics.setIosReadCount(platStatics.getIosReadCount() + 1);
							update.set("iosReadCount", platStatics.getIosReadCount() + 1);
							platStatics.setReadCount(platStatics.getReadCount() + 1);
							update.set("readCount", platStatics.getReadCount() + 1);
						}
					}
				}
				// -----------------以下是送达数-----------------
				if (StringUtils.isNotEmpty(msgHistoryList.get(i).getMsgJpushId())) {
					ReceivedsResult result = JPush.getMessageReport(msgHistoryList.get(i).getMsgJpushId());
					if (result != null && result.received_list != null && result.received_list.size() != 0) {
						if (result.received_list.get(0).android_received != 0) {
							// 如果安卓送达数不为0,则安卓送达数和总送达数都加上安卓送达数
							platStatics.setAndroidSendCount(platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							update.set("androidSendCount", platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).android_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).android_received);
						}
						if (result.received_list.get(0).ios_msg_received != 0) {
							// 如果ios送达数不为0,则ios送达数和总送达数都加上ios送达数
							platStatics.setIosSendCount(platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("iosSendCount", platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
						}
					}
				}
				if (StringUtils.isNotEmpty(msgHistoryList.get(i).getMsgJpushProId())) {
					ReceivedsResult result = JPushPro.getMessageReport(msgHistoryList.get(i).getMsgJpushProId());
					if (result != null && result.received_list != null && result.received_list.size() != 0) {
						if (result.received_list.get(0).android_received != 0) {
							// 如果安卓送达数不为0,则安卓送达数和总送达数都加上安卓送达数
							platStatics.setAndroidSendCount(platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							update.set("androidSendCount", platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).android_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).android_received);
						}
						if (result.received_list.get(0).ios_msg_received != 0) {
							// 如果ios送达数不为0,则ios送达数和总送达数都加上ios送达数
							platStatics.setIosSendCount(platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("iosSendCount", platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
						}
					}
				}
				if (StringUtils.isNotEmpty(msgHistoryList.get(i).getMsgJpushYxbId())) {
					ReceivedsResult result = JPushPro.getMessageReport(msgHistoryList.get(i).getMsgJpushYxbId());
					if (result != null && result.received_list != null && result.received_list.size() != 0) {
						if (result.received_list.get(0).android_received != 0) {
							// 如果安卓送达数不为0,则安卓送达数和总送达数都加上安卓送达数
							platStatics.setAndroidSendCount(platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							update.set("androidSendCount", platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).android_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).android_received);
						}
						if (result.received_list.get(0).ios_msg_received != 0) {
							// 如果ios送达数不为0,则ios送达数和总送达数都加上ios送达数
							platStatics.setIosSendCount(platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("iosSendCount", platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
						}
					}
				}
				if (StringUtils.isNotEmpty(msgHistoryList.get(i).getMsgJpushZnbId())) {
					ReceivedsResult result = JPushPro.getMessageReport(msgHistoryList.get(i).getMsgJpushZnbId());
					if (result != null && result.received_list != null && result.received_list.size() != 0) {
						if (result.received_list.get(0).android_received != 0) {
							// 如果安卓送达数不为0,则安卓送达数和总送达数都加上安卓送达数
							platStatics.setAndroidSendCount(platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							update.set("androidSendCount", platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).android_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).android_received);
						}
						if (result.received_list.get(0).ios_msg_received != 0) {
							// 如果ios送达数不为0,则ios送达数和总送达数都加上ios送达数
							platStatics.setIosSendCount(platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("iosSendCount", platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
						}
					}
				}
				if (StringUtils.isNotEmpty(msgHistoryList.get(i).getMsgJpushZybId())) {
					ReceivedsResult result = JPushPro.getMessageReport(msgHistoryList.get(i).getMsgJpushZybId());
					if (result != null && result.received_list != null && result.received_list.size() != 0) {
						if (result.received_list.get(0).android_received != 0) {
							// 如果安卓送达数不为0,则安卓送达数和总送达数都加上安卓送达数
							platStatics.setAndroidSendCount(platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							update.set("androidSendCount", platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).android_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).android_received);
						}
						if (result.received_list.get(0).ios_msg_received != 0) {
							// 如果ios送达数不为0,则ios送达数和总送达数都加上ios送达数
							platStatics.setIosSendCount(platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("iosSendCount", platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
						}
					}
				}
				if (StringUtils.isNotEmpty(msgHistoryList.get(i).getMsgJpushZzbId())) {
					ReceivedsResult result = JPushPro.getMessageReport(msgHistoryList.get(i).getMsgJpushZzbId());
					if (result != null && result.received_list != null && result.received_list.size() != 0) {
						if (result.received_list.get(0).android_received != 0) {
							// 如果安卓送达数不为0,则安卓送达数和总送达数都加上安卓送达数
							platStatics.setAndroidSendCount(platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							update.set("androidSendCount", platStatics.getAndroidSendCount() + result.received_list.get(0).android_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).android_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).android_received);
						}
						if (result.received_list.get(0).ios_msg_received != 0) {
							// 如果ios送达数不为0,则ios送达数和总送达数都加上ios送达数
							platStatics.setIosSendCount(platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("iosSendCount", platStatics.getIosSendCount() + result.received_list.get(0).ios_msg_received);
							platStatics.setSendCount(platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
							update.set("sendCount", platStatics.getSendCount() + result.received_list.get(0).ios_msg_received);
						}
					}
				}
			}
		}
		// 更新数据库
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("tagId").is(messagePushTagVO.getId()).and("staDate").is((GetDate.dateToString(today)));
		query.addCriteria(criteria);
		List<MessagePushPlatStatics> msgPushPlatStaticsList = msgPushPlatStaticsDao.find(query);
		if (!CollectionUtils.isEmpty(msgPushPlatStaticsList)) {
			platStatics.setId(msgPushPlatStaticsList.get(0).getId());

			msgPushPlatStaticsDao.update(query, new Update());
		} else {
			msgPushPlatStaticsDao.insert(platStatics);
		}
	}

	private void insertTemplateStatics(MessagePushTemplateVO template) {
		// 判断是否添加过
		List<MessagePushTemplateStatics> list = msgStaticsDao
				.getMessagePushTemplateStaticsByMsgCode(template.getTemplateCode());
		if (list.size() > 0) {
			return;
		}
		MessagePushTemplateStatics msgSta = new MessagePushTemplateStatics();
		msgSta.setMsgId(template.getId().toString());
		msgSta.setMsgCode(template.getTemplateCode());
		msgSta.setMsgTitle(template.getTemplateTitle());
		msgSta.setTagId(template.getTagId().toString());
		msgSta.setSendTime(GetDate.date2Str(template.getCreateTime(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		msgSta.setAndroidDestinationCount(0);
		msgSta.setIosDestinationCount(0);
		msgSta.setAndroidReadCount(0);
		msgSta.setAndroidSendCount(0);
		msgSta.setIosReadCount(0);
		msgSta.setIosSendCount(0);
		msgSta.setCreateTime(GetDate.dateToString(new Date()));
		// 插入数据
		this.msgStaticsDao.insert(msgSta);
	}
}
