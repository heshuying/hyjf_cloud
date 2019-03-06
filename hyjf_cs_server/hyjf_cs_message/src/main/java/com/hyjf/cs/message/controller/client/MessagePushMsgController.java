/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.admin.MessagePushMsgResponse;
import com.hyjf.am.resquest.message.MessagePushMsgRequest;
import com.hyjf.am.vo.admin.MessagePushMsgVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;
import com.hyjf.cs.message.service.msgpush.MessagePushMsgService;
import com.hyjf.cs.message.service.msgpush.MsgPushService;
import com.hyjf.cs.message.service.msgpushstatics.MsgPushStaticsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

/**
 * 消息推送定时任务
 * 
 * @author fuqiang
 * @version MessagePushMsgController, v0.1 2018/6/21 15:52
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/appMessage")
public class MessagePushMsgController extends BaseController {

	@Autowired
	private MsgPushService msgPushService;

	@Autowired
	private MsgPushStaticsService msgPushStaticsService;

	@Autowired
	private MessagePushMsgService messagePushMsgService;

	@RequestMapping("/pushAll")
	public void messagePush() {
		logger.info("消息推送定时任务开始......");
		msgPushService.pushMessage();
	}

	@RequestMapping("/pushStatics")
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
		List<MessagePushMsg> msgList = msgPushService.getMsgStaticsListByTime(startTime, endTime);
		// 插入统计数据
		for (int i = 0; i < msgList.size(); i++) {
			this.msgPushService.insertMessagePush(msgList.get(i));
		}

		// 查询7天统计数据
		String yesDate = GetDate.getCountDate(5, -6);
		String startTime1 = GetDate.getDayStart(yesDate);
		String endTime1 =GetDate.getDayEnd(curDate);
		List<MessagePushTemplateStatics> templateStaticsList = this.msgPushStaticsService.getTemplateStaticsListByTime(startTime1, endTime1);
		// 更新统计数据
		for (int i = 0; i < templateStaticsList.size(); i++) {
			this.msgPushStaticsService.updatemsgPushStatics(templateStaticsList.get(i),startTime,endTime);
		}
	}

	@RequestMapping("/pushPlatStatics")
	public void messagePlatPushStatics() {
		try {
			int N = 7;// 更新或插入7天之内的数据
			Date today = GetDate.stringToDate(GetDate.dateToString2(new Date()) + " 00:00:00");
			Date todayStart = GetDate.stringToDate(GetDate.dateToString2(today) + " 00:00:00");
			Date todayEnd = GetDate.stringToDate(GetDate.dateToString2(today) + " 23:59:59");
			today = GetDate.getSomeDayBeforeOrAfter(today, 1);
			todayStart = GetDate.getSomeDayBeforeOrAfter(todayStart, 1);
			todayEnd = GetDate.getSomeDayBeforeOrAfter(todayEnd, 1);
			// 获取标签类型
			List<MessagePushTagVO> tags = msgPushService.getPushTags();
			for (int i = 0; i < N; i++) {
				today = GetDate.getSomeDayBeforeOrAfter(today, -1);
				todayStart = GetDate.getSomeDayBeforeOrAfter(todayStart, -1);
				todayEnd = GetDate.getSomeDayBeforeOrAfter(todayEnd, -1);
				if (tags != null && tags.size() != 0) {
					for (int j = 0; j < tags.size(); j++) {
						// 根据标签类型,获取时间范围内所有的msghistory
						List<MessagePushMsgHistory> msgHistoryList = msgPushService.getMessagePushMsgHistorys(tags.get(j), todayStart, todayEnd);
						// 根据msg和msghistory插入当天统计信息
						msgPushService.insertPushPlatStatics(tags.get(j), msgHistoryList, today);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	/**
	 * 根据条件查询手动发放短信列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectMessagePushMsg")
	public MessagePushMsgResponse selectMessagePushMsg(@RequestBody MessagePushMsgRequest request) {
		MessagePushMsgResponse response = new MessagePushMsgResponse();
		long count = messagePushMsgService.countMessagePushMsg(request);
		List<MessagePushMsg> list = messagePushMsgService.selectMessagePushMsg(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<MessagePushMsgVO> msgVOS = CommonUtils.convertBeanList(list,MessagePushMsgVO.class);
			response.setResultList(msgVOS);
			response.setCount((int)count);
		}
		return response;
	}


	/**
	 * 根据id查询手动发送短信
	 * @param id
	 * @return
	 */
	@RequestMapping("/getMessagePushMsgById/{id}")
	public MessagePushMsgResponse getMessagePushMsgById (@PathVariable String id){
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
	@RequestMapping("/insertMessagePushMsg")
	public MessagePushMsgResponse insertMessagePushMsg(@RequestBody MessagePushMsgVO msgVO) {
		MessagePushMsgResponse response = new MessagePushMsgResponse();
		MessagePushMsg msg = new MessagePushMsg();
		BeanUtils.copyProperties(msgVO,msg);
		msg.setCreateTime(GetDate.getNowTime10());
		msg.setLastupdateTime(GetDate.getNowTime10());
		Integer count = messagePushMsgService.insertMessagePushMsg(msg);
		response.setCount(count);
		return response;
	}

	/**
	 * 修改手动发送短信
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateMessagePushMsg")
	public MessagePushMsgResponse updateMessagePushMsg(@RequestBody MessagePushMsgRequest request) {
		MessagePushMsgResponse response = new MessagePushMsgResponse();
		MessagePushMsg messagePushMsg = new MessagePushMsg();
		BeanUtils.copyProperties(request,messagePushMsg);
		messagePushMsg.setLastupdateTime(GetDate.getNowTime10());
		messagePushMsg.setMsgTerminal(request.getMsgTerminal());
		Integer count = messagePushMsgService.updateMessagePushMsg(messagePushMsg);
		response.setCount(count);
		return response;
	}

	/**
	 * 删除手动发送短信
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteMessagePushMsg")
	public MessagePushMsgResponse deleteMessagePushMsg(@RequestBody MessagePushMsgRequest request) {
		MessagePushMsgResponse response = new MessagePushMsgResponse();
		List<MessagePushMsgVO> recordList = request.getRecordList();
		List<MessagePushMsg> messagePushMsgs = CommonUtils.convertBeanList(recordList,MessagePushMsg.class);
		Integer count = messagePushMsgService.deleteMessagePushMsg(messagePushMsgs);
		response.setCount(count);
		return response;
	}

}
