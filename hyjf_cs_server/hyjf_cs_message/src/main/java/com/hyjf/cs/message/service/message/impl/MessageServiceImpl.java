/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.message.bean.mc.SmsOntime;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.mongo.mc.SmsOntimeMongoDao;
import com.hyjf.cs.message.mq.base.MessageContent;
import com.hyjf.cs.message.mq.producer.SmsProducer;
import com.hyjf.cs.message.service.message.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author fuqiang
 * @version MessageServiceImpl, v0.1 2018/6/22 11:14
 */
@Service
public class MessageServiceImpl implements MessageService {

	/** 任务状态:执行中 */
	private static final Integer STATUS_RUNNING = 1;

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsOntimeMongoDao smsOntimeMongoDao;

	@Autowired
	private AmConfigClient amConfigClient;

	@Autowired
	private SmsProducer smsProducer;

	@Override
	public List<SmsOntime> getOntimeList(Integer statusWait) {
		Criteria criteria = new Criteria();
		criteria.and("status").is(statusWait);
		Query query = new Query(criteria);
		List<SmsOntime> list = smsOntimeMongoDao.find(query);
		if (!CollectionUtils.isEmpty(list)) {
			return list;
		}
		return null;
	}

	@Override
	public void sendMessage(SmsOntime apicron) throws Exception {
		int errorCnt = 0;
		// 错误信息
		StringBuffer sbError = new StringBuffer();
		logger.info("定时发短信任务开始。手机号：{}", apicron.getMobile());
		// 更新任务API状态为进行中
		this.updatetOntime(apicron.getId(), STATUS_RUNNING, null);
		if (apicron.getContent() == null) {
			sbError.append("发送消息不能为空");
			return;
		}
		if (apicron.getChannelType() == null) {
			sbError.append("发送消息渠道不能为空");
			return;
		}
		String mobile = apicron.getMobile();
		String send_message = apicron.getContent();
		String channelType = apicron.getChannelType();
		if (StringUtils.isEmpty(mobile)) {
			// 在筛选条件下查询出用户
			List<UserVO> msgs = this.queryUser(apicron);
			// 用户数
			System.out.println("发送用户数" + msgs.size());
			// 用户未手写手机号码
			int number = 200;// 分组每组数
			if (msgs != null && msgs.size() != 0) {
				int i = msgs.size() / number;
				for (int j = 0; j <= i; j++) {
					int tosize = (j + 1) * number;
					List<UserVO> smslist;
					if (tosize > msgs.size()) {
						smslist = msgs.subList(j * number, msgs.size());
					} else {
						smslist = msgs.subList(j * number, tosize);
					}
					String phones = "";
					for (int z = 0; z < smslist.size(); z++) {
						if (StringUtils.isNotEmpty(smslist.get(z).getMobile())
								&& Validator.isPhoneNumber(smslist.get(z).getMobile())) {
							if (z < smslist.size() - 1) {
								phones += smslist.get(z).getMobile() + ",";
							} else {
								phones += smslist.get(z).getMobile();
							}
						}
					}
					try {
						SmsMessage smsMessage = new SmsMessage(null, null, phones, send_message,
								MessageConstant.SMS_SEND_FOR_USERS_NO_TPL, null, null, channelType);
						smsProducer.messageSend(
								new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(smsMessage)));
					} catch (Exception e) {
						logger.error("发送短信失败......", e);
						errorCnt++;
					}
				}
			}
		} else {
			// 发送短信
			try {
				String[] mobiles = mobile.split(",");
				for (int i = 0; i < (mobiles.length / 200) + 1; i++) {
					String mbl = "";
					for (int j = i * 200; j < ((i + 1) * 200) && j < mobiles.length; j++) {
						mbl += mobiles[j] + ",";
					}
					if (mbl.endsWith(",")) {
						mbl = mbl.substring(0, mbl.length() - 1);
					}
					SmsMessage smsMessage = new SmsMessage(null, null, mbl, send_message,
							MessageConstant.SMS_SEND_FOR_USERS_NO_TPL, null, null, channelType);
					smsProducer.messageSend(
							new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(smsMessage)));
				}
			} catch (Exception e) {
				sbError.append(e.getMessage()).append("<br/>");
				logger.error("发送短信失败...", e);
				errorCnt++;
			}
		}

		// 有错误时
		if (errorCnt > 0) {
			throw new Exception("定时发送短信时发生错误。" + "[错误记录id：" + apicron.getId() + "]," + "[错误件数：" + errorCnt + "]");
		}
		// 更新任务API状态为完成
		updatetOntime(apicron.getId(), STATUS_RUNNING, null);
	}

	/**
	 * 获取查询出来的用户手机号码和数量
	 * 
	 * @param apicron
	 * @return
	 */
	private List<UserVO> queryUser(SmsOntime apicron) {
		JSONObject params = new JSONObject();
		if (apicron.getAddMoneyCount() != null) {
			params.put("add_money_count", apicron.getAddMoneyCount());
		}

		if (StringUtils.isNotEmpty(apicron.getAddTimeBegin())) {
			int begin = Integer.parseInt(GetDate.get10Time(apicron.getAddTimeBegin()));
			params.put("add_time_begin", begin);
		}

		if (StringUtils.isNotEmpty(apicron.getAddTimeEnd())) {
			int end = Integer.parseInt(GetDate.get10Time(apicron.getAddTimeEnd()));
			params.put("add_time_end", end);
		}

		if (StringUtils.isNotEmpty(apicron.getReTimeBegin())) {
			int re_begin = Integer.parseInt(GetDate.get10Time(apicron.getReTimeBegin()));
			params.put("re_time_begin", re_begin);
		}

		if (StringUtils.isNotEmpty(apicron.getReTimeEnd())) {
			int re_end = Integer.parseInt(GetDate.get10Time(apicron.getReTimeEnd()));
			params.put("re_time_end", re_end);
		}
		if (apicron.getOpenAccount() != null) {
			params.put("open_account", apicron.getOpenAccount());
		} else {
			/* sm.setOpen_account(3); */
			params.put("open_account", 4);
		}
		return amConfigClient.queryUser(params);
	}

	/**
	 * 更新定时发短信API任务表
	 * 
	 * @param id
	 * @param status
	 * @param data
	 */
	private void updatetOntime(String id, Integer status, String data) {
		SmsOntime record = new SmsOntime();
		record.setId(id);
		record.setStatus(status);
		if (Validator.isNotNull(data) || status == 2) {
			record.setRemark(data);
			record.setStarttime(GetDate.getMyTimeInMillis());
		}
		smsOntimeMongoDao.save(record);
	}
}
