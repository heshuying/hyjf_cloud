package com.hyjf.cs.message.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.config.*;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.message.client.AmConfigClient;

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
}
