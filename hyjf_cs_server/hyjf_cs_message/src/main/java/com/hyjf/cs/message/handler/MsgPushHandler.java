package com.hyjf.cs.message.handler;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.user.UserAliasVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.config.PropertiesConfig;
import com.hyjf.cs.message.jpush.JPush;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgDao;
import com.hyjf.cs.message.mongo.mc.MessagePushMsgHistoryDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class MsgPushHandler {
	private final static Logger logger = LoggerFactory.getLogger(MsgPushHandler.class);

	private static final Integer SUCCESS_SEND = 0;
	private static final Integer ERROR_SEND = -1;

	@Autowired
	private AmUserClient amUserClient;
	@Autowired
	private AmConfigClient amConfigClient;
	@Autowired
	private MessagePushMsgHistoryDao messagePushMsgHistoryDao;
	@Autowired
	private MessagePushMsgDao messagePushMsgDao;

	/**
	 * 
	 * 手动推送消息发送
	 *
	 * @param msgId
	 *            messagePushMsg 主键id
	 * @return 返回结果,0表示发送成功 -1标识失败
	 * @throws Exception
	 */
	public Integer sendMessages(String msgId) {

		MessagePushMsg message = messagePushMsgDao.findById(msgId);

		if (message == null) {
			logger.error("手动推送消息发送失败，消息不存在, msgId is :{}", msgId);
			return ERROR_SEND;
		}

		List<MessagePushMsgHistory> list = addMessageHistoryRecord(message);
		if (!CollectionUtils.isEmpty(list)) {
			for (MessagePushMsgHistory history : list) {
				// 发送
				try {
					send(history);
				} catch (Exception e) {
					logger.error("发送失败...", e);
					return ERROR_SEND;
				}
			}
		}

		return SUCCESS_SEND;
	}

	/**
	 * 给指定用户usersId的用户推送消息
	 * 
	 * @param tplCode
	 * @param replaceStrs
	 * @param usersId
	 * @return
	 */
	public Integer sendMessages(String tplCode, Map<String, String> replaceStrs, Integer usersId) {
		if (usersId == null || usersId == 0) {
			return ERROR_SEND;
		}
		UserVO userVO = amUserClient.findUserById(usersId);
		if (userVO == null) {
			return ERROR_SEND;
		}

		UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userVO.getUserId());
		// 为保护客户隐私，只显示客户姓氏，不显示客户全名。
		if (userInfoVO == null) {
			logger.warn("未找到用户信息， userId is: {}", userVO.getUserId());
			return ERROR_SEND;
		}
		String trueName = userInfoVO.getTruename();
		String replaceName = StringUtils.isBlank(trueName)?"":trueName.substring(0, 1);
		replaceStrs.put("val_name", replaceName);
		replaceStrs.put("val_sex", userInfoVO.getSex() == 1 ? "先生" : "女士");

		return sendMessages(tplCode, replaceStrs, userVO.getMobile());
	}

	/**
	 * 给指定电话号码的用户推送消息
	 *
	 * @param tplCode
	 *            模板Code
	 * @param replaceStrs
	 *            需要替换的字符串Map<需替换的字符串,替换值> 例:[value_title]:HDD151210404
	 * @param mobile
	 *            用户电话号码
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer sendMessages(String tplCode, Map<String, String> replaceStrs, String mobile) {
		// 获取模板信息
		if (StringUtils.isEmpty(mobile)) {
			return ERROR_SEND;
		}
		// 获取模板
		MessagePushTemplateVO messagePushTemplate = null;
		try {
			messagePushTemplate = amConfigClient.findMessagePushTemplateByCode(tplCode);
		} catch (Exception e) {
			logger.error("未找到对应的模板:" + tplCode, e);
			return ERROR_SEND;
		}

		if(messagePushTemplate == null){
			logger.warn("未找到对应的模板, tplCode is :{}", tplCode);
			return ERROR_SEND;
		}

		try {
			String messageStr = messagePushTemplate.getTemplateContent();
			if (Validator.isNotNull(messageStr)) {
				if (replaceStrs != null && replaceStrs.size() > 0) {
					for (String s : replaceStrs.keySet()) {
						messageStr = StringUtils.replace(messageStr, "[" + s + "]", replaceStrs.get(s));
					}
				}
			}
			// 推送消息
			MessagePushMsgHistory msgHistory = addMessageHistoryRecord(mobile, messageStr, messagePushTemplate);

			if (msgHistory != null) {
				send(msgHistory);
				return SUCCESS_SEND;
			}
		} catch (Exception e) {
			logger.error("消息推送失败...", e);
			return ERROR_SEND;
		}
		return SUCCESS_SEND;
	}

	//
	/**
	 * 添加发送记录（模板消息）
	 *
	 * @param mobile
	 *            手机号
	 * @param message
	 *            发送内容
	 * @param messagePushTemplate
	 *            模板
	 */
	public MessagePushMsgHistory addMessageHistoryRecord(String mobile, String message,
			MessagePushTemplateVO messagePushTemplate) {

		UserAliasVO userAliasVO = amUserClient.findAliasByMobile(mobile);
		// 存储历史记录
		if (userAliasVO != null) {
			MessagePushMsgHistory history = new MessagePushMsgHistory();
			history.setCreateTime(GetDate.getNowTime10() + "");
			history.setCreateUserId(null);
			history.setCreateUserName("系统自动触发");
			history.setLastupdateTime(GetDate.getNowTime10() + "");
			history.setLastupdateUserId(null);
			history.setLastupdateUserName("系统自动触发");
			history.setMsgAction(messagePushTemplate.getTemplateAction());
			history.setMsgActionUrl(messagePushTemplate.getTemplateActionUrl());
			history.setMsgCode(messagePushTemplate.getTemplateCode()); // 消息编码为模板编码
			history.setMsgTitle(messagePushTemplate.getTemplateTitle());
			history.setMsgContent(message);
			history.setMsgDestination(mobile);
			history.setMsgDestinationType(CustomConstants.MSG_PUSH_DESTINATION_TYPE_1);
			history.setMsgFirstreadPlat(null);
			history.setMsgImageUrl(messagePushTemplate.getTemplateImageUrl());
			history.setMsgJpushId(null);
			history.setMsgJpushProId(null);
			history.setMsgReadCountAndroid(0);
			history.setMsgReadCountIos(0);
			history.setMsgRemark("");
			history.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_0);
			history.setMsgTerminal(messagePushTemplate.getTemplateTerminal());
			history.setMsgUserId(null);
			history.setSendTime(null);
			history.setTagCode(messagePushTemplate.getTagCode());
			history.setTagId(messagePushTemplate.getTagId());
			if (userAliasVO.getClient().equals(CustomConstants.CLIENT_ANDROID)) {
				history.setMsgDestinationCountAndroid(1);// 安卓目标推送数
				history.setMsgDestinationCountIos(0);// IOS目标推送数
			} else if (userAliasVO.getClient().equals(CustomConstants.CLIENT_IOS)) {
				history.setMsgDestinationCountAndroid(0);// 安卓目标推送数
				history.setMsgDestinationCountIos(1);// IOS目标推送数
			}
			messagePushMsgHistoryDao.save(history);

			return history;
		} else {
			logger.error("推送失败，未找到目标用户.....");
			throw new RuntimeException("推送失败，未找到目标用户.....");
		}
	}

	/**
	 * 添加发送记录（手动添加消息）
	 *
	 * @param message
	 *            消息
	 */
	private List<MessagePushMsgHistory> addMessageHistoryRecord(MessagePushMsg message) {
		List<MessagePushMsgHistory> histories = new ArrayList<MessagePushMsgHistory>();
		if (message == null || message.getMsgDestinationType() == null) {
			logger.warn("MsgDestinationType is null, message send fail");
			return null;
		}

		if (message.getMsgDestinationType().intValue() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {
			// 发给所有人
			logger.info("发给所有人...");
			histories.add(saveMessagePushMsgHistory(message, null));
		}
		if (message.getMsgDestinationType().intValue() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
			logger.info("单条或者多条推送， 目的地: {}",  message.getMsgDestination());
			// 发给固定人群
			String[] mobiles = message.getMsgDestination().split(",");
			// 获取设备唯一编码
			if (mobiles != null && mobiles.length != 0) {
				List<UserAliasVO> msgPushCommonList = amUserClient.findAliasesByMobiles(Arrays.asList(mobiles));
				// 存储历史记录
				if (!CollectionUtils.isEmpty(msgPushCommonList)) {
					for (UserAliasVO userAliasVO: msgPushCommonList) {
						histories.add(saveMessagePushMsgHistory(message, userAliasVO));
					}
				}
			}
		}
		message.setMsgSendStatus(CustomConstants.MSG_PUSH_MSG_STATUS_1);
		message.setSendTime(GetDate.getNowTime10());
		messagePushMsgDao.save(message);
		return histories;
	}

	/**
	 *
	 * @param message
	 * @param userAliasVO 空表示全部推送
	 * @return
	 */
	private MessagePushMsgHistory saveMessagePushMsgHistory(MessagePushMsg message, UserAliasVO userAliasVO){
		MessagePushMsgHistory history = new MessagePushMsgHistory();

		if(userAliasVO == null){
			history.setMsgDestination(message.getMsgDestination());
			history.setMsgDestinationCountAndroid(amUserClient.countAliasByClient(CustomConstants.CLIENT_ANDROID));// 安卓目标推送数
			history.setMsgDestinationCountIos(amUserClient.countAliasByClient(CustomConstants.CLIENT_IOS));// IOS目标推送数
		} else {
			history.setMsgDestination(userAliasVO.getMobile());
			if (userAliasVO.getClient().equals(CustomConstants.CLIENT_ANDROID)) {
				history.setMsgDestinationCountAndroid(1);// 安卓目标推送数
				history.setMsgDestinationCountIos(0);// IOS目标推送数
			} else if (userAliasVO.getClient().equals(CustomConstants.CLIENT_IOS)) {
				history.setMsgDestinationCountAndroid(0);// 安卓目标推送数
				history.setMsgDestinationCountIos(1);// IOS目标推送数
			}
		}

		history.setCreateTime(message.getCreateTime() + "");
		history.setCreateUserId(message.getCreateUserId());
		history.setCreateUserName(message.getCreateUserName());
		history.setLastupdateTime(GetDate.getNowTime10() + "");
		history.setLastupdateUserId(message.getCreateUserId());
		history.setLastupdateUserName(message.getCreateUserName());
		history.setMsgAction(message.getMsgAction());
		history.setMsgActionUrl(message.getMsgActionUrl());
		history.setMsgCode(message.getMsgCode());
		history.setMsgContent(message.getMsgContent());
		history.setMsgDestinationType(message.getMsgDestinationType());
		history.setMsgFirstreadPlat(null);
		history.setMsgImageUrl(message.getMsgImageUrl());
		history.setMsgJpushId(null);
		history.setMsgJpushProId(null);
		history.setMsgReadCountAndroid(0);
		history.setMsgReadCountIos(0);
		history.setMsgRemark("");
		history.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_0);
		history.setMsgTerminal(message.getMsgTerminal());
		history.setMsgTitle(message.getMsgTitle());
		history.setMsgUserId(null);
		history.setSendTime(null);
		history.setTagCode(message.getTagCode());
		history.setTagId(message.getTagId());
		// 插入数据库
		messagePushMsgHistoryDao.insert(history);
		return history;
	}


	/**
	 * 极光推送及更新发送状态
	 *
	 * @param msg
	 */
	public void send(MessagePushMsgHistory msg) throws Exception {
		if (msg == null) {
			logger.warn("msg must be not null!");
			return;
		}
		logger.info("开始推送: msg_id is :{}, msg_content is:{}", msg.getId(), msg.getMsgContent());

		// 错误消息
		String errorMsg = "";
		// 包区分 39新极光 79老极光
		String packageCode = "39";

		CustomizePushResult customizePushResult = null;
		try {
			// 判断是否发送所有人 0发送所有人 1个人
			if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_0) {
				// 广播模式，测试环境不推送
				logger.info("env.test: {}", PropertiesConfig.hyjfEnvProperties.isTest());
				if(PropertiesConfig.hyjfEnvProperties.isTest()){
					logger.warn("广播模式下测试环境不推送，请勿再试.....");
					return;
				}

				// 广播模式判断客户端
				String clientStr = msg.getMsgTerminal();
				if (StringUtils.isBlank(clientStr)) {
					logger.warn("client must be not empty...");
					return;
				}

				customizePushResult = sendBroadcast(msg, clientStr, packageCode);
			} else if (msg.getMsgDestinationType() == CustomConstants.MSG_PUSH_DESTINATION_TYPE_1) {
				// 个人用户推送
				String mobile = msg.getMsgDestination();
				if(!PropertiesConfig.isPassSend(mobile)){
					logger.warn("个人推送未加入白名单，不推送, mobile is:{}....", mobile);
					return;
				}

				UserAliasVO userAliasVO = amUserClient.findAliasByMobile(mobile);
				if (userAliasVO != null) {
					msg.setMsgUserId(userAliasVO.getUserId());
					packageCode = userAliasVO.getPackageCode();
					String alias = userAliasVO.getAlias();
					if (StringUtils.isEmpty(alias)) {
						errorMsg = "该用户设备不存在";
					} else {
						// 别名发送
						logger.debug("alias is :{}, packageCode is :{}, client is: {}", alias, packageCode, userAliasVO.getClient());
						customizePushResult = sendMessageToAlias(msg, alias, userAliasVO.getClient(), packageCode);
					}
				} else {
					errorMsg = "该用户设备不存在";
				}
			}
		} catch (APIConnectionException e) {
			logger.error("调用极光接口异常,连接超时", e);
			errorMsg = "调用极光接口异常,连接超时";
		} catch (APIRequestException e) {
			logger.error("推送错误...", e);
			errorMsg = JPushExceptionEnum.getErrorMessageByCode(e.getErrorCode());
		}

		// 成功
		if (customizePushResult != null && (StringUtils.isNotBlank(customizePushResult.getAndroidMsgId())
				|| StringUtils.isNotBlank(customizePushResult.getIosMsgId()))) {
			msg.setSendTime(GetDate.getNowTime10());
			msg.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_1);
			msg.setMsgJpushId(customizePushResult.getAndroidMsgId());
			// 苹果马甲版本多，使用反射赋值。
			this.setField(msg, JPushCientEnum.getFieldName(packageCode), customizePushResult.getIosMsgId());

			logger.info("推送成功：安卓-msgId: " + customizePushResult.getAndroidMsgId());
			logger.info("推送成功：苹果-msgId: {}", customizePushResult.getIosMsgId());
		} else {
			msg.setSendTime(GetDate.getNowTime10());
			msg.setMsgSendStatus(CustomConstants.MSG_PUSH_SEND_STATUS_2);
			msg.setMsgRemark(errorMsg);
			logger.info("发送消息失败：" + errorMsg);
		}
		messagePushMsgHistoryDao.save(msg);
	}

	/**
	 * 广播发送
	 */
	private CustomizePushResult sendBroadcast(MessagePushMsgHistory msg, String clientStr, String packageCode) throws APIConnectionException, APIRequestException {
		PushPayload payload = null;
		if (CustomConstants.CLIENT_ANDROID.equals(clientStr)) {
			// 单独发送安卓客户端
			payload = JPush.buildPushObject_android_tag_alertWithTitle(
					HtmlUtil.getTextFromHtml(msg.getMsgContent()), msg.getId(), msg.getMsgAction(),
					msg.getMsgActionUrl());
		} else if (CustomConstants.CLIENT_IOS.equals(clientStr)) {
			// 单发ios客户端
			payload = JPush.buildPushObject_ios_tag_alert(HtmlUtil.getTextFromHtml(msg.getMsgContent()),
					msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl());
		} else {
			// 所有客户端
			payload = JPush.buildPushObject_android_and_ios(HtmlUtil.getTextFromHtml(msg.getMsgContent()),
					msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl());
		}

		if(payload == null){
			logger.warn("send broadcast fail, payload must be not null...");
			return null;
		}

		CustomizePushResult customizePushResult = new CustomizePushResult();

		// ios
		if (clientStr.contains(CustomConstants.CLIENT_IOS)) {
			PushResult result = JPushCientEnum.getJPushClient(packageCode).sendPush(payload);
			customizePushResult.setIosMsgId(String.valueOf(result.msg_id));
		}

		// andriod
		if (clientStr.contains(CustomConstants.CLIENT_ANDROID)) {
			PushResult result = JPush.getClientInstance().sendPush(payload);
			customizePushResult.setAndroidMsgId(String.valueOf(result.msg_id));
		}

		return customizePushResult;
	}

	/**
	 * 别名推送
	 * @param msg
	 * @param alias
	 * @param client
	 * @param packageCode
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	private CustomizePushResult sendMessageToAlias(MessagePushMsgHistory msg, String alias, String client, String packageCode) throws APIConnectionException, APIRequestException {
		PushPayload payload = JPush.buildPushObject_all_alias_alert(HtmlUtil.getTextFromHtml(msg.getMsgContent()),
				msg.getId(), msg.getMsgAction(), msg.getMsgActionUrl(), alias);
		CustomizePushResult customizePushResult = new CustomizePushResult();
		if (CustomConstants.CLIENT_ANDROID.equals(client)) {
			PushResult result = JPush.getClientInstance().sendPush(payload);
			customizePushResult.setAndroidMsgId(String.valueOf(result.msg_id));
		} else if (CustomConstants.CLIENT_IOS.equals(client)) {
			if (StringUtils.isNotEmpty(packageCode)) {
				logger.info("packageCode: " + packageCode);
				PushResult result = JPushCientEnum.getJPushClient(packageCode).sendPush(payload);
				customizePushResult.setIosMsgId(String.valueOf(result.msg_id));
			}
		}
		return customizePushResult;
	}

	/**
	 * 返回结果msgId
	 */
	class CustomizePushResult{
		private String androidMsgId;
		private String iosMsgId;

		public String getIosMsgId() {
			return iosMsgId;
		}

		public void setIosMsgId(String iosMsgId) {
			this.iosMsgId = iosMsgId;
		}

		public String getAndroidMsgId() {
			return androidMsgId;
		}

		public void setAndroidMsgId(String androidMsgId) {
			this.androidMsgId = androidMsgId;
		}
	}

	/**
	 * 反射调用set方法赋值
	 * @param msg
	 * @param fieldName
	 * @param value
	 */
	private void setField(Object msg, String fieldName, String value){
		String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Class clazz =  msg.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getName().equals(fieldName)) {
				try {
					clazz.getMethod(setMethodName, field.getType()).invoke(msg, value);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage());
				} catch (NoSuchMethodException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

}
