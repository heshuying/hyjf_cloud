package com.hyjf.cs.message.client;

import com.hyjf.am.response.borrow.BankCardResponse;
import com.hyjf.am.response.config.SiteSettingsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.hyjf.am.vo.config.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiasq
 * @version AmConfigClient, v0.1 2018/5/4 10:00
 */
@Repository
public class AmConfigClient {

	@Autowired
	private GenericRest rest;

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
	public SmsTemplateVO findSmsTemplateByCode(String tplCode) {
		RestResponse<SmsTemplateVO> resp = Rests.exc(() -> {
			String url = Rests.toUrl(amConfigServiceName, "/am-config/smsTemplate/findSmsTemplateByCode/" + tplCode);
			ResponseEntity<RestResponse<SmsTemplateVO>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<SmsTemplateVO>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	/**
	 * 查询短信模板
	 * 
	 * @param tplCode
	 * @return
	 */
	public SmsNoticeConfigVO findSmsNoticeByCode(String tplCode) {
		RestResponse<SmsNoticeConfigVO> resp = Rests.exc(() -> {
			String url = Rests.toUrl(amConfigServiceName, "/am-config/smsNoticeConfig/findSmsNoticeByCode/" + tplCode);
			ResponseEntity<RestResponse<SmsNoticeConfigVO>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<SmsNoticeConfigVO>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	/**
	 * 查询邮件模板
	 * 
	 * @param mailCode
	 * @return
	 */
	public SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode) {
		RestResponse<SmsMailTemplateVO> resp = Rests.exc(() -> {
			String url = Rests.toUrl(amConfigServiceName, "/am-config/smsMailTemplate/findSmsMailByCode/" + mailCode);
			ResponseEntity<RestResponse<SmsMailTemplateVO>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<SmsMailTemplateVO>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	/**
	 * 查询app消息模板
	 *
	 * @param tplCode
	 * @return
	 */
	public MessagePushTemplateVO findMessagePushTemplateByCode(String tplCode) {
		RestResponse<MessagePushTemplateVO> resp = Rests.exc(() -> {
			String url = Rests.toUrl(amConfigServiceName,
					"/am-config/messagePushTemplate/findMessagePushTemplateByCode/" + tplCode);
			ResponseEntity<RestResponse<MessagePushTemplateVO>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<MessagePushTemplateVO>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	/**
	 * 查询邮件配置
	 * 
	 * @return
	 */
	public SiteSettingsVO findSiteSetting() {
		/*RestResponse<SiteSettingsVO> resp = Rests.exc(() -> {
			String url = Rests.toUrl(amConfigServiceName, "/am-config/siteSettings/findOne");
			ResponseEntity<RestResponse<SiteSettingsVO>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<SiteSettingsVO>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();*/
		SiteSettingsResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/siteSettings/findOne/", SiteSettingsResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
}
