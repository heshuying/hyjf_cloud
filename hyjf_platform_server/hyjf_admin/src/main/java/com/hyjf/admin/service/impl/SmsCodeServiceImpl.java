/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.beans.request.SmsCodeRequestBean;
import com.hyjf.admin.beans.request.SmsLogRequestBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.mq.SmsOnTimeProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.SmsCodeService;
import com.hyjf.am.vo.admin.SmsCodeCustomizeVO;
import com.hyjf.am.vo.admin.SmsOntimeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

/**
 * @author fq
 * @version SmsCodeServiceImpl, v0.1 2018/8/15 10:22
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {
	@Autowired
	private CsMessageClient csMessageClient;
	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private SmsOnTimeProducer smsOnTimeProducer;

	@Override
	public List<SmsCodeCustomizeVO> queryUser(SmsCodeRequestBean requestBean) {
		return amUserClient.queryUser(requestBean);
	}

	@Override
	public Integer queryLogCount(SmsLogRequestBean requestBean) {
		return csMessageClient.queryLogCount(requestBean);
	}

	@Override
	public boolean getUserByMobile(String mobile) {
		UserVO user = amUserClient.getUserByMobile(mobile);
		return user == null ? false : true;
	}

	@Override
	public boolean sendSmsOntime(SmsCodeRequestBean form) throws ParseException {
		SmsOntimeVO smsOntime = new SmsOntimeVO();
		smsOntime.setOpenAccount(form.getOpen_account());
		smsOntime.setChannelType(form.getChannelType());
		smsOntime.setMobile(form.getUser_phones());
		smsOntime.setContent(form.getMessage());
		smsOntime.setStatus(0);
		smsOntime.setOpenAccount(form.getOpen_account());
		if (StringUtils.isNotEmpty(form.getAdd_money_count())) {
			smsOntime.setAddMoneyCount(new BigDecimal(form.getAdd_money_count()));

		}
		if (StringUtils.isNotEmpty(form.getAdd_time_begin())) {
			smsOntime.setAddTimeBegin(form.getAdd_time_begin());
		}

		if (StringUtils.isNotEmpty(form.getAdd_time_end())) {
			smsOntime.setAddTimeEnd(form.getAdd_time_end());
		}

		if (StringUtils.isNotEmpty(form.getRe_time_begin())) {
			smsOntime.setReTimeBegin(form.getRe_time_begin());
		}

		if (StringUtils.isNotEmpty(form.getRe_time_end())) {
			smsOntime.setReTimeEnd(form.getRe_time_end());
		}
		smsOntime.setEndtime(Integer.parseInt((GetDate.datetimeFormat.parse(form.getOn_time()).getTime() / 1000) + ""));
		smsOntime.setIp(form.getIp());
		// smsOntime.setCreateUserId(Integer.parseInt(ShiroUtil.getLoginUserId()));
		// smsOntime.setCreateUserName(ShiroUtil.getLoginUsername());
		smsOntime.setCreateTime(GetDate.getNowTime10());
		try {
			smsOnTimeProducer.messageSend(new MessageContent(MQConstant.SMS_ONTIME_TOPIC, UUID.randomUUID().toString(),
					JSON.toJSONBytes(smsOntime)));
		} catch (MQException e) {
			return false;
		}
		return true;
	}
}
