package com.hyjf.cs.message.client;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author xiasq
 * @version AmConfigClientImpl, v0.1 2018/5/4 10:00
 */
public interface AmConfigClient {

	/**
	 * 查询短信模板
	 * 
	 * @param tplCode
	 * @return
	 */
	SmsTemplateVO findSmsTemplateByCode(String tplCode);

	/**
	 * 查询短信模板
	 * 
	 * @param tplCode
	 * @return
	 */
	SmsNoticeConfigVO findSmsNoticeByCode(String tplCode);

	/**
	 * 查询邮件模板
	 * 
	 * @param mailCode
	 * @return
	 */
	SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode);

	/**
	 * 查询app消息模板
	 *
	 * @param tplCode
	 * @return
	 */
	MessagePushTemplateVO findMessagePushTemplateByCode(String tplCode);

	/**
	 * 查询邮件配置
	 * 
	 * @return
	 */
	SiteSettingsVO findSiteSetting();

	/**
	 * 在筛选条件下查询出用户
	 * 
	 * @param params
	 * @return
	 */
	List<UserVO> queryUser(JSONObject params);

	/**
	 * 获取所有模版
	 *
	 * @return
	 */
	List<MessagePushTemplateVO> getAllTemplates();
}
