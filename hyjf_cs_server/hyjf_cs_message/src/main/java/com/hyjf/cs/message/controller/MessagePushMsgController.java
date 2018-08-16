/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller;

import java.util.List;

import com.hyjf.am.response.admin.MessagePushMsgResponse;
import com.hyjf.am.resquest.message.MessagePushMsgRequest;
import com.hyjf.am.vo.admin.MessagePushMsgVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;
import com.hyjf.cs.message.service.msgpush.MessagePushMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.MessagePush;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgDao;
import com.hyjf.cs.message.mongo.mc.MessagePushTemplateStaticsDao;
import com.hyjf.cs.message.service.msgpush.MsgPushService;
import com.hyjf.cs.message.service.msgpushstatics.MsgPushStaticsService;

/**
 * 消息推送定时任务
 * 
 * @author fuqiang
 * @version MessagePushMsgController, v0.1 2018/6/21 15:52
 */
@RestController
@RequestMapping("/cs-message/app_message")
public class MessagePushMsgController extends BaseController {

	@Autowired
	private MsgPushService msgPushService;

	@Autowired
	private MsgPushStaticsService msgPushStaticsService;

	@Autowired
	private MessagePushMsgDao messagePushMsgDao;

	@Autowired
	private MessagePushTemplateStaticsDao msgStaticsDao;

	@Autowired
	private MessagePushMsgService messagePushMsgService;

	@RequestMapping("/push_all")
	public void messagePush() {
		logger.info("消息推送定时任务开始......");
		msgPushService.pushMessage();
	}

	@RequestMapping("/push_statics")
	public void messagePushStatics() {
		logger.info("消息推送统计定时任务开始......");
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

	/**
	 * 根据条件查询手动发放短信列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectmessagepushmsg")
	public MessagePushMsgResponse selectMessagePushMsg(@RequestBody MessagePushMsgRequest request) {
		MessagePushMsgResponse response = new MessagePushMsgResponse();
		List<MessagePushMsg> list = messagePushMsgService.selectMessagePushMsg(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<MessagePushMsgVO> msgVOS = CommonUtils.convertBeanList(list,MessagePushMsgVO.class);
			response.setResultList(msgVOS);
		}
		return response;
	}


	/**
	 * 根据id查询手动发送短信
	 * @param id
	 * @return
	 */
	@RequestMapping("/getmessagepushmsgbyid/{id}")
	public MessagePushMsgResponse getMessagePushMsgById (@PathVariable Integer id){
		MessagePushMsgResponse response = new MessagePushMsgResponse();
		MessagePushMsg messagePushMsg = messagePushMsgService.getMessagePushMsgById(id);
		if (messagePushMsg != null) {
			MessagePushMsgVO messagePushMsgVO = new MessagePushMsgVO();
			BeanUtils.copyProperties(messagePushMsg,messagePushMsgVO);
			response.setResult(messagePushMsgVO);
		}
		return response;
	}

	/**
	 * 添加手动发送短信
	 * @param msgVO
	 * @return
	 */
	@RequestMapping("/insertmessagepushmsg")
	public MessagePushMsgResponse insertMessagePushMsg(@RequestBody MessagePushMsgVO msgVO) {
		MessagePushMsgResponse response = new MessagePushMsgResponse();
		MessagePushMsg msg = new MessagePushMsg();
		BeanUtils.copyProperties(msgVO,msg);
		Integer count = messagePushMsgService.insertMessagePushMsg(msg);
		response.setCount(count);
		return response;
	}

	/**
	 * 修改手动发送短信
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatemessagepushmsg")
	public MessagePushMsgResponse updateMessagePushMsg(@RequestBody MessagePushMsgRequest request) {
		MessagePushMsgResponse response = new MessagePushMsgResponse();
		MessagePushMsg messagePushMsg = new MessagePushMsg();
		BeanUtils.copyProperties(request,messagePushMsg);
		Integer count = messagePushMsgService.updateMessagePushMsg(messagePushMsg);
		response.setCount(count);
		return response;
	}

	/**
	 * 删除手动发送短信
	 * @param recordList
	 * @return
	 */
	@RequestMapping("/deletemessagepushmsg/{recordList}")
	public MessagePushMsgResponse deleteMessagePushMsg(@PathVariable List<Integer> recordList) {
		MessagePushMsgResponse response = new MessagePushMsgResponse();
		Integer count = messagePushMsgService.deleteMessagePushMsg(recordList);
		response.setCount(count);
		return response;
	}

}
