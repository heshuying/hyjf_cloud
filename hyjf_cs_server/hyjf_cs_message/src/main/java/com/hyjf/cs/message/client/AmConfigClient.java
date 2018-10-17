package com.hyjf.cs.message.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.resquest.config.SmsNoticeConfigRequest;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.am.vo.admin.ContentHelpCustomizeVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

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
	 * 智齿客服常见问题列表
	 *
	 * @return
	 */
	List<ContentHelpCustomizeVO> queryContentCustomize();

	/**
	 * 智齿客服常见问题答案
	 *
	 * @return
	 */
	ContentHelpVO help(Integer id);
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

	/**
	 * 开关闭推送服务
	 * @param users
	 * @return
	 */
	IntegerResponse updateAppNewsConfig(UserVO users);


	EventResponse getEvents(int userId, int begin, int end);

	EventVO selectPercentage(int percentage, int begin, int end, int userId);

	/**
	 *获取标签类型
	 * @return
	 */
	List<MessagePushTagVO> getPushTags();

	/**
	 * 查询短信模板
	 * @param tplCode
	 * @return
	 */
    SmsTemplateVO findSmsTemplate(SmsTemplateRequest request);

	/**
	 * 查询短信模板
	 * @param request
	 * @return
	 */
	SmsNoticeConfigVO findSmsNotice(SmsNoticeConfigRequest request);

	/**
	 * 根据tagId获取标签名
	 * @param tagId
	 * @return
	 */
	MessagePushTagVO selectMsgTagByTagId(String tagId);
}
