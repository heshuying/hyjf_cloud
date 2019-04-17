package com.hyjf.cs.message.client.impl;

import java.util.List;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.resquest.config.MessagePushTagRequest;
import com.hyjf.am.resquest.config.SmsNoticeConfigRequest;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.am.vo.admin.ContentHelpCustomizeVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.message.client.AmConfigClient;

/**
 * @author xiasq
 * @version AmConfigClientImpl, v0.1 2018/5/4 10:00
 */
@Cilent
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
				.getForEntity("http://AM-CONFIG/am-config/site_settings/findOne/", SiteSettingsResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public  List<ContentHelpCustomizeVO> queryContentCustomize(){
		CategoryResponse response =  restTemplate
				.getForEntity("http://AM-CONFIG/am-config/content/help/contenthelps/", CategoryResponse.class).getBody();
		if (response != null) {
			return (List<ContentHelpCustomizeVO>)response.getRecordList();
		}
		return null;

	}
	@Override
	public ContentHelpVO help(Integer id){
		ParameterizedTypeReference<ContentHelpVO> responseType = new ParameterizedTypeReference<ContentHelpVO>(){};
		ResponseEntity<ContentHelpVO> user = restTemplate.exchange("http://AM-CONFIG/am-config/content/help/help/"+id,
				HttpMethod.GET, null, responseType);
		return user.getBody();
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
	public List<MessagePushTagVO> getPushTags() {
		MessagePushTagResponse response = restTemplate.postForEntity(
				"http://AM-CONFIG/am-config/messagePushTag/searchList", new MessagePushTagRequest(), MessagePushTagResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public SmsTemplateVO findSmsTemplate(SmsTemplateRequest request) {
		SmsTemplateResponse response = restTemplate.postForObject("http://AM-CONFIG/am-config/smsTemplate/findSmsTemplate", request, SmsTemplateResponse.class);
		if (response != null) {
			List<SmsTemplateVO> resultList = response.getResultList();
			if (!CollectionUtils.isEmpty(resultList)) {
				return resultList.get(0);
			}
		}
		return null;
	}

	@Override
	public SmsNoticeConfigVO findSmsNotice(SmsNoticeConfigRequest request) {
		SmsNoticeConfigResponse response = restTemplate
				.postForObject("http://AM-CONFIG/am-config/smsNoticeConfig/find_notice_by_name", request,
						SmsNoticeConfigResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public MessagePushTagVO selectMsgTagByTagId(String tagId) {
		MessagePushTagResponse response = restTemplate
				.getForObject("http://AM-CONFIG/am-config/messagePushTag/selectMsgTagByTagId/" + tagId,
						MessagePushTagResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public IntegerResponse updateAppNewsConfig(UserVO users) {
		IntegerResponse response = restTemplate.postForEntity("http://AM-USER/am-user/user/updateByUserId", users, IntegerResponse.class).getBody();
		return response;
	}

	@Override
	public IdCardCustomize getIdCardCustomize(IdCardCustomize idCardCustomize){
		return  restTemplate.postForEntity("http://AM-CONFIG/am-config/content/idcard/idcarddetail",idCardCustomize, IdCardCustomize.class).getBody();
	}

	@Override
	public String selectMinEventTime() {
		String url = "http://AM-CONFIG/am-config/content/contentevent/selectMinEventTime" ;
		StringResponse response = restTemplate.getForEntity(url, StringResponse.class).getBody();
		if (response == null || !Response.isSuccess(response)) {
			return null;
		}
		return response.getResultStr();
	}

}
