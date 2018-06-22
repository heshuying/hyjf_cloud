/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.MessagePush;
import com.hyjf.cs.message.bean.MessagePushTemplateStatics;
import com.hyjf.cs.message.mongo.MessagePushMsgDao;
import com.hyjf.cs.message.mongo.MessagePushTemplateStaticsDao;
import com.hyjf.cs.message.service.msgpush.MsgPushService;
import com.hyjf.cs.message.service.msgpushstatics.MsgPushStaticsService;

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

	@Autowired
	private MsgPushStaticsService msgPushStaticsService;

	@Autowired
	private MessagePushMsgDao messagePushMsgDao;

	@Autowired
	private MessagePushTemplateStaticsDao msgStaticsDao;

	@RequestMapping("/msgPush")
	public void messagePush() {
		logger.info("消息推送定时任务开始......");
		msgPushService.pushMessage();
	}

	@RequestMapping("/msgPushStatics")
	public void messagePushStatics() {
		logger.info("消息推送统计定时任务开始......");
		List<MessagePushTemplateVO> templateList = this.msgPushService.getAllTemplates();

		// 时间戳定义
		Integer startTime = null;
		Integer endTime = null;
		// 今天
		String curDate = GetDate.date2Str(GetDate.date_sdf);
		startTime =  GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayStart(curDate));
		endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayEnd(curDate));
		// 当天内的发送消息
		List<MessagePush> msgList = messagePushMsgDao.getMsgStaticsListByTime(startTime, endTime);
		// 插入统计数据
		for (int i = 0; i < msgList.size(); i++) {
			this.messagePushMsgDao.insert(msgList.get(i));
		}

		// 查询7天统计数据
		String yesDate = GetDate.getCountDate(5, -6);
		startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp(GetDate.getDayStart(yesDate));
		endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp(GetDate.getDayEnd(curDate));
		List<MessagePushTemplateStatics> templateStaticsList = this.msgStaticsDao.getTemplateStaticsListByTime(startTime, endTime);
		// 更新统计数据
		for (int i = 0; i < templateStaticsList.size(); i++) {
			this.msgPushStaticsService.updatemsgPushStatics(templateStaticsList.get(i),startTime,endTime);
		}
	}
}
