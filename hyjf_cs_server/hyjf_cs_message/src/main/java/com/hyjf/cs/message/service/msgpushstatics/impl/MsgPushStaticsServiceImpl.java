/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpushstatics.impl;

import cn.jpush.api.report.ReceivedsResult;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;
import com.hyjf.cs.message.jpush.JPush;
import com.hyjf.cs.message.jpush.JPushPro;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgHistoryDao;
import com.hyjf.cs.message.mongo.mc.MessagePushTemplateStaticsDao;
import com.hyjf.cs.message.service.msgpushstatics.MsgPushStaticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version MsgPushStaticsServiceImpl, v0.1 2018/6/22 10:01
 */
@Service
public class MsgPushStaticsServiceImpl implements MsgPushStaticsService {

	@Autowired
	private MessagePushTemplateStaticsDao templateStaticsDao;

	@Autowired
	private MessagePushMsgHistoryDao msgHistoryDao;

	@Autowired
	private MessagePushTemplateStaticsDao staticsDao;

	@Override
	public void updatemsgPushStatics(MessagePushTemplateStatics msgTemplateStatics, Integer startTime,
			Integer endTime) {
		// ios目标数
		int iosDestinationCount = 0;
		// 安卓目标数
		int androidDestinationCount = 0;
		// ios阅读数
		int iosReadCount = 0;
		// ios送达数
		int iosSendCount = 0;
		// 安卓阅读数
		int androidReadCount = 0;
		// 安卓送达数
		int androidSendCount = 0;

		// 查询发送记录（成功）
		List<MessagePushMsgHistory> historyList = msgHistoryDao
				.getMsgHistoryListByMsgCode(msgTemplateStatics.getMsgCode(), startTime, endTime);
		if (historyList == null || historyList.size() == 0) {
			return;
		}
		/**
		 * 如果推送到个人，消息可能为一条或多条 如果推送全部人，消息应该为一条记录
		 */
		for (int i = 0; i < historyList.size(); i++) {
			MessagePushMsgHistory hisInfo = historyList.get(i);
			// 推送给个人
			if (hisInfo.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
				if (hisInfo.getMsgFirstreadPlat() != null) {
					if (hisInfo.getMsgFirstreadPlat().intValue() == Integer.parseInt(CustomConstants.CLIENT_ANDROID)) {
						androidReadCount = androidReadCount + 1;
					}
					if (hisInfo.getMsgFirstreadPlat().intValue() == Integer.parseInt(CustomConstants.CLIENT_IOS)) {
						iosReadCount = iosReadCount + 1;
					}
				}
			} else {
				iosReadCount = hisInfo.getMsgReadCountIos();
				androidReadCount = hisInfo.getMsgReadCountAndroid();
			}
			// 目标数
			iosDestinationCount = iosDestinationCount + hisInfo.getMsgDestinationCountIos();
			androidDestinationCount = androidDestinationCount + hisInfo.getMsgDestinationCountAndroid();
			// 推送数
			// -----------------调用极光接口-----------------
			if (StringUtils.isNotEmpty(hisInfo.getMsgJpushId())) {
				ReceivedsResult result = JPush.getMessageReport(hisInfo.getMsgJpushId());
				if (result != null && result.received_list != null && result.received_list.size() != 0) {
					androidSendCount = androidSendCount + result.received_list.get(0).android_received;
					iosSendCount = iosSendCount + result.received_list.get(0).ios_msg_received;
				}
			}
			if (StringUtils.isNotEmpty(hisInfo.getMsgJpushProId())) {
				ReceivedsResult result = JPushPro.getMessageReport(hisInfo.getMsgJpushProId());
				if (result != null && result.received_list != null && result.received_list.size() != 0) {
					androidSendCount = androidSendCount + result.received_list.get(0).android_received;
					iosSendCount = iosSendCount + result.received_list.get(0).ios_msg_received;
				}
			}
			if (StringUtils.isNotEmpty(hisInfo.getMsgJpushYxbId())) {
				ReceivedsResult result = JPushPro.getMessageReport(hisInfo.getMsgJpushYxbId());
				if (result != null && result.received_list != null && result.received_list.size() != 0) {
					androidSendCount = androidSendCount + result.received_list.get(0).android_received;
					iosSendCount = iosSendCount + result.received_list.get(0).ios_msg_received;
				}
			}
			if (StringUtils.isNotEmpty(hisInfo.getMsgJpushZnbId())) {
				ReceivedsResult result = JPushPro.getMessageReport(hisInfo.getMsgJpushZnbId());
				if (result != null && result.received_list != null && result.received_list.size() != 0) {
					androidSendCount = androidSendCount + result.received_list.get(0).android_received;
					iosSendCount = iosSendCount + result.received_list.get(0).ios_msg_received;
				}
			}
			if (StringUtils.isNotEmpty(hisInfo.getMsgJpushZybId())) {
				ReceivedsResult result = JPushPro.getMessageReport(hisInfo.getMsgJpushZybId());
				if (result != null && result.received_list != null && result.received_list.size() != 0) {
					androidSendCount = androidSendCount + result.received_list.get(0).android_received;
					iosSendCount = iosSendCount + result.received_list.get(0).ios_msg_received;
				}
			}
			if (StringUtils.isNotEmpty(hisInfo.getMsgJpushZzbId())) {
				ReceivedsResult result = JPushPro.getMessageReport(hisInfo.getMsgJpushZzbId());
				if (result != null && result.received_list != null && result.received_list.size() != 0) {
					androidSendCount = androidSendCount + result.received_list.get(0).android_received;
					iosSendCount = iosSendCount + result.received_list.get(0).ios_msg_received;
				}
			}
		}
		msgTemplateStatics.setAndroidReadCount(androidReadCount);
		msgTemplateStatics.setAndroidSendCount(androidSendCount);
		msgTemplateStatics.setIosReadCount(iosReadCount);
		msgTemplateStatics.setIosSendCount(iosSendCount);
		msgTemplateStatics.setIosDestinationCount(iosDestinationCount);
		msgTemplateStatics.setAndroidDestinationCount(androidDestinationCount);
		msgTemplateStatics.setSendTime(GetDate.dateToString(new Date()));

		this.templateStaticsDao.save(msgTemplateStatics);
	}

	@Override
	public List<MessagePushTemplateStatics> getTemplateStaticsListByTime(Integer startTime, Integer endTime) {
		return staticsDao.getTemplateStaticsListByTime(startTime, endTime);
	}
}
