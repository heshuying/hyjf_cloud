package com.hyjf.cs.message.client.impl;

import java.util.List;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.vo.market.EventsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.message.client.AmConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author xiasq
 * @version AmConfigClientImpl, v0.1 2018/5/4 10:00
 */
@Service
public class AmConfigClientImpl implements AmConfigClient {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${am.config.service.name}")
	private String amConfigServiceName;

	/**
	 * 查询短信模板
	 * 
	 * @param tplCode
	 * @return
	 */
	@Override
	public SmsTemplateVO findSmsTemplateByCode(String tplCode) {
		SmsTemplateResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/smsTemplate/findSmsTemplateByCode/" + tplCode,
						SmsTemplateResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查询短信模板
	 * 
	 * @param tplCode
	 * @return
	 */
	@Override
	public SmsNoticeConfigVO findSmsNoticeByCode(String tplCode) {
		SmsNoticeConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/smsNoticeConfig/findSmsNoticeByCode/" + tplCode,
						SmsNoticeConfigResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查询邮件模板
	 * 
	 * @param mailCode
	 * @return
	 */
	@Override
	public SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode) {

		SmsMailTemplateResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/smsMailTemplate/findSmsMailByCode/" + mailCode,
						SmsMailTemplateResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查询app消息模板
	 *
	 * @param tplCode
	 * @return
	 */
	@Override
	public MessagePushTemplateVO findMessagePushTemplateByCode(String tplCode) {

		MessagePushTemplateResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/messagePushTemplate/findMessagePushTemplateByCode/" + tplCode,
						MessagePushTemplateResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查询邮件配置
	 * 
	 * @return
	 */
	@Override
	public SiteSettingsVO findSiteSetting() {

		SiteSettingsResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/siteSettings/findOne/", SiteSettingsResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<UserVO> queryUser(JSONObject params) {
		return null;// todo
	}

	@Override
	public List<MessagePushTemplateVO> getAllTemplates() {
		MessagePushTemplateResponse response = restTemplate.getForObject(
				"http://AM-CONFIG/am-config/messagePushTemplate/getAllTemplates", MessagePushTemplateResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}



	@Override
	public EventResponse getEvents(int userId,int begin, int end) {
		EventResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/content/contentevent/getEvents/"+userId+"/" + begin+"/"+end,
						EventResponse.class)
				.getBody();
		return response;
	}


	@Override
	public EventVO selectPercentage(int percentage, int begin, int end, int userId) {
		EventVO response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/wxWeekly/selectPercentage/"+percentage+"/" + begin+"/"+end+"/"+userId,
						EventVO.class)
				.getBody();
		return response;
	}
	@Override
	public IntegerResponse updateAppNewsConfig(UserVO users) {
		IntegerResponse response = restTemplate.postForEntity("http://AM-USER/am-user/user/updateByUserId", users, IntegerResponse.class).getBody();
		return response;
	}
}
