/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.am.vo.config.SmsTemplateVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.message.bean.mc.SmsOntime;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.client.AmTradeClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.mongo.mc.SmsOntimeMongoDao;
import com.hyjf.cs.message.mq.base.CommonProducer;
import com.hyjf.cs.message.mq.base.MessageContent;
import com.hyjf.cs.message.service.message.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
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

	/** 任务状态:已完成 */
	private static final Integer STATUS_SUCCESS = 2;

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsOntimeMongoDao smsOntimeMongoDao;

	@Autowired
	private AmConfigClient amConfigClient;

	@Autowired
	private AmTradeClient amTradeClient;

	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	private CommonProducer smsProducer;

	@Override
	public List<SmsOntime> getOntimeList(Integer statusWait) {
		Criteria criteria = new Criteria();
		criteria.and("status").is(statusWait);
		criteria.and("endtime").gte(GetDate.getSearchStartTime(new Date())).lte(GetDate.getNowTime10());
		Query query = new Query(criteria);
		List<SmsOntime> list = smsOntimeMongoDao.find(query);
		if (!CollectionUtils.isEmpty(list)) {
			return list;
		}
		return null;
	}

	@Override
	public void sendMessage(SmsOntime smsOntime) throws Exception {
		int errorCnt = 0;
		// 错误信息
		StringBuffer sbError = new StringBuffer();
		logger.info("定时发短信任务开始。手机号：{}", smsOntime.getMobile());
		// 更新任务API状态为进行中
		this.updatetOntime(smsOntime, STATUS_RUNNING, null);
		if (smsOntime.getContent() == null) {
			sbError.append("发送消息不能为空");
			return;
		}
		if (smsOntime.getChannelType() == null) {
			sbError.append("发送消息渠道不能为空");
			return;
		}
		String mobile = smsOntime.getMobile();
		String send_message = smsOntime.getContent();
		String channelType = smsOntime.getChannelType();
		if (StringUtils.isEmpty(mobile)) {
			// 在筛选条件下查询出用户
			List<String> mobiles = this.queryUser(smsOntime);
			// 用户数
			logger.info("发送用户数" + mobiles.size());
			//群发短信
			errorCnt = massTexting(mobiles,send_message,channelType,MessageConstant.SMS_SEND_FOR_USERS_NO_TPL);
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
							new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),smsMessage));
				}
			} catch (Exception e) {
				sbError.append(e.getMessage()).append("<br/>");
				logger.error("发送短信失败...", e);
				errorCnt++;
			}
		}

		// 有错误时
		if (errorCnt > 0) {
			throw new Exception("定时发送短信时发生错误。" + "[错误记录id：" + smsOntime.getId() + "]," + "[错误件数：" + errorCnt + "]");
		}
		// 更新任务API状态为完成
		updatetOntime(smsOntime, STATUS_SUCCESS, null);
	}

	/**
	 * 获取查询出来的用户手机号码和数量
	 * @return
	 */
	private List<String> queryUser(SmsOntime smsOntime) {
		SmsCodeUserRequest request = new SmsCodeUserRequest();

		if (smsOntime.getAddMoneyCount() != null) {
			request.setAdd_money_count(smsOntime.getAddMoneyCount().toString());
		}

		if (StringUtils.isNotEmpty(smsOntime.getAddTimeBegin())) {
			int begin = Integer.parseInt(GetDate.get10Time(smsOntime.getAddTimeBegin()));
			request.setAdd_time_begin(String.valueOf(begin));
		}

		if (StringUtils.isNotEmpty(smsOntime.getAddTimeEnd())) {
			int end = Integer.parseInt(GetDate.get10Time(smsOntime.getAddTimeEnd()));
			request.setAdd_time_end(String.valueOf(end));
		}

		if (StringUtils.isNotEmpty(smsOntime.getReTimeBegin())) {
			int re_begin = Integer.parseInt(GetDate.get10Time(smsOntime.getReTimeBegin()));
			request.setRe_time_begin(String.valueOf(re_begin));
		}

		if (StringUtils.isNotEmpty(smsOntime.getReTimeEnd())) {
			int re_end = Integer.parseInt(GetDate.get10Time(smsOntime.getReTimeEnd()));
			request.setRe_time_end(String.valueOf(re_end));
		}
		if (smsOntime.getOpenAccount() != null) {
			request.setOpen_account(smsOntime.getOpenAccount());
		}
		return amTradeClient.queryUser(request);
	}

	/**
	 * 更新定时发短信API任务表
	 * 
	 * @param record
	 * @param status
	 * @param data
	 */
	private void updatetOntime(SmsOntime record, Integer status, String data) {
		record.setStatus(status);
		if (Validator.isNotNull(data) || status == 2) {
			record.setRemark(data);
			record.setStarttime(GetDate.getMyTimeInMillis());
		}
		smsOntimeMongoDao.save(record);
	}

	/**
	 * 手动群发
	 * @param mobiles 多个手机号码 "," 隔开
	 * @param send_message 短信内容
	 * @param channelType 渠道类型
	 * @return 错误条数
	 */
	private int massTexting(List<String> mobiles,String send_message,String channelType,String serviceType){
		int errorCnt = 0;
		// 用户未手写手机号码
		int number = 200;// 分组每组数
		if (mobiles != null && mobiles.size() != 0) {
			int i = mobiles.size() / number;
			for (int j = 0; j <= i; j++) {
				int tosize = (j + 1) * number;
				List<String> smslist;
				if (tosize > mobiles.size()) {
					smslist = mobiles.subList(j * number, mobiles.size());
				} else {
					smslist = mobiles.subList(j * number, tosize);
				}
				String phones = "";
				for (int z = 0; z < smslist.size(); z++) {
					if (StringUtils.isNotEmpty(smslist.get(z))
							&& Validator.isPhoneNumber(smslist.get(z))) {
						if (z < smslist.size() - 1) {
							phones += smslist.get(z) + ",";
						} else {
							phones += smslist.get(z);
						}
					}
				}
				try {
					SmsMessage smsMessage = new SmsMessage(null, null, phones, send_message,
							serviceType, null, null, channelType);
					smsProducer.messageSend(
							new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),smsMessage));
				} catch (Exception e) {
					logger.error("发送短信失败......", e);
					errorCnt++;
				}
			}
		}

		return errorCnt;
	}

	/**
	 * 群发生日祝福短信
	 */
	@Override
	public void sendBirthdayBlessSms() throws Exception {

		SmsTemplateRequest request = new SmsTemplateRequest();
		// 只查询开启状态模板
		request.setStatus(1);
		request.setTplCode("birthday_bless");
		SmsTemplateVO smsTemplate = amConfigClient.findSmsTemplate(request);
		if (smsTemplate == null) {
			logger.warn("无可用短信模板,查询条件为status:[1,开启状态的模板],TPLCode:[{}]","birthday_bless");
			return ;
		}

		int errorCnt = 0;
		//短信内容
		String send_message = smsTemplate.getTplContent();
		String channelType = "normal";

		// 在筛选条件下查询出用户
		List<String> mobiles = this.queryUserByBirthday();
		// 用户数
		logger.info("发送用户数" + mobiles.size());
		//群发短信
		errorCnt = massTexting(mobiles,send_message,channelType,MessageConstant.SMS_SEND_FOR_BIRTHDAY);
		// 有错误时
		if (errorCnt > 0) {
			throw new Exception("群发生日祝福短信时发生错误。" + "[错误件数：" + errorCnt + "]");
		}
	}

	/**
	 * 通过手机号码获取查询出来的用户手机号码
	 * @return
	 */
	private List<String> queryUserByBirthday() {
		SmsCodeUserRequest request = new SmsCodeUserRequest();
		String birthday = GetDate.formatDateMMDD();
		if (StringUtils.isNotBlank(birthday)) {
			request.setBirthday(birthday);
		}
		return amUserClient.queryUserByBirthday(request);
	}
}
