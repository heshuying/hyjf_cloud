package com.hyjf.cs.message.jpush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyjf.cs.message.config.PropertiesConfig;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.ReceivedsResult;

/**
 * 极光接口 服务APP：所有安卓应用、iOS包（渠道号79）
 * 
 * @author Michael
 */
public class JPush {
	protected static final Logger logger = LoggerFactory.getLogger(JPush.class);
	private static final String ANDROID_TITLE = "汇盈金服";
	private static JPushClient jpushClient = null;
	private static String soundName = "music.caf";

	/**
	 * 单例 实例化
	 * 
	 * @return
	 */
	public static JPushClient getClientInstance() {
		if (jpushClient == null) {
			ClientConfig clientConfig = ClientConfig.getInstance();
			clientConfig.setApnsProduction(true);
			jpushClient = new JPushClient(PropertiesConfig.jPushProperties.getMasterSecret(),
					PropertiesConfig.jPushProperties.getAppKey(), null, clientConfig);
		}
		return jpushClient;
	}

	/**
	 * 推送 所有平台、所有用户 param alert 内容
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_all_all_alert(String alert) {
		return PushPayload.alertAll(alert);
	}

	/**
	 * 根据别名推送消息（推送到个人用）
	 * 
	 * @param alert
	 *            内容
	 * @param msgId
	 *            消息ID
	 * @param operation
	 *            操作指令（例：0打开app）
	 * @param msgAction
	 *            指令内容
	 * @param alias
	 *            别名集合
	 * @return
	 */
	public static PushPayload buildPushObject_all_alias_alert(String alert, String msgId, Integer operation,
			String msgAction, String... alias) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(
						Notification.newBuilder().setAlert(alert)
								.addPlatformNotification(AndroidNotification.newBuilder()
										.setTitle(ANDROID_TITLE).addExtra("msgId", msgId)
										.addExtra("operation", operation).addExtra("msgAction", msgAction).build())
								.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).setSound(soundName)
										.addExtra("msgId", msgId).addExtra("operation", operation)
										.addExtra("msgAction", msgAction).build())
								.build())
				.build();
	}

	/**
	 * 推送安卓用户 消息
	 * 
	 * @param alert
	 *            内容
	 * @param msgId
	 *            消息ID
	 * @param operation
	 *            操作指令（例：0打开app）
	 * @param msgAction
	 *            指令内容
	 * @return
	 */
	public static PushPayload buildPushObject_android_tag_alertWithTitle(String alert, String msgId, Integer operation,
			String msgAction) {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(alert)
								.setTitle(ANDROID_TITLE).addExtra("msgId", msgId).addExtra("operation", operation)
								.addExtra("msgAction", msgAction).build())
						.build())
				.setMessage(Message.content(alert)).build();
	}

	/**
	 * 根据标签推送安卓用户 消息
	 * 
	 * @param alert
	 *            内容
	 * @param msgId
	 *            消息ID
	 * @param operation
	 *            操作指令（例：0打开app）
	 * @param msgAction
	 *            指令内容
	 * @return
	 */
	public static PushPayload buildPushObject_android_tag_alertWithTitle(String tag, String alert, Integer msgId,
			Integer operation, String msgAction) {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.tag(tag))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(alert)
								.setTitle(ANDROID_TITLE).addExtra("msgId", msgId).addExtra("operation", operation)
								.addExtra("msgAction", msgAction).build())
						.build())
				.setMessage(Message.content(alert)).build();
	}

	/**
	 * 推送IOS所有用户 消息
	 * 
	 * @param alert
	 *            消息内容
	 * @param msgId
	 *            消息ID
	 * @param operation
	 *            操作指令（例：0打开app）
	 * @param msgAction
	 *            指令内容
	 * @return
	 */
	public static PushPayload buildPushObject_ios_tag_alert(String alert, String msgId, Integer operation,
			String msgAction) {
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().setAlert(alert).setBadge(1)
								.setSound(soundName).addExtra("msgId", msgId).addExtra("operation", operation)
								.addExtra("msgAction", msgAction).build())
						.build())
				.setMessage(Message.content(alert))
				.setOptions(
						Options.newBuilder().setApnsProduction(!PropertiesConfig.hyjfEnvProperties.isTest()).build())
				.build();
	}

	/**
	 * 根据标签推送IOS消息
	 * 
	 * @param tag
	 *            标签
	 * @param alert
	 *            消息内容
	 * @param msgId
	 *            消息ID
	 * @param operation
	 *            操作指令（例：0打开app）
	 * @param msgAction
	 *            指令内容
	 * @return
	 */
	public static PushPayload buildPushObject_ios_tag_alert(String tag, String alert, Integer msgId, Integer operation,
			String msgAction) {
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.tag(tag))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().setAlert(alert).setBadge(1)
								.setSound(soundName).addExtra("msgId", msgId).addExtra("operation", operation)
								.addExtra("msgAction", msgAction).build())
						.build())
				.setMessage(Message.content(alert))
				.setOptions(
						Options.newBuilder().setApnsProduction(!PropertiesConfig.hyjfEnvProperties.isTest()).build())
				.build();
	}

	/**
	 * 推送 安卓和ios所有用户 平台消息
	 * 
	 * @param alert
	 *            消息内容
	 * @param msgId
	 *            消息ID
	 * @param operation
	 *            操作指令（例：0打开app）
	 * @param msgAction
	 *            指令内容
	 * @return
	 */
	public static PushPayload buildPushObject_android_and_ios(String alert, String msgId, Integer operation,
			String msgAction) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.all())
				.setNotification(
						Notification.newBuilder().setAlert(alert)
								.addPlatformNotification(AndroidNotification.newBuilder()
										.setTitle(ANDROID_TITLE).addExtra("msgId", msgId)
										.addExtra("operation", operation).addExtra("msgAction", msgAction).build())
								.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).setSound(soundName)
										.addExtra("msgId", msgId).addExtra("operation", operation)
										.addExtra("msgAction", msgAction).build())
								.build())
				.setMessage(Message.content(alert)).build();
	}

	/**
	 * 根据标签 推送 安卓和ios所有用户 平台消息
	 * 
	 * @param tag
	 *            标签
	 * @param alert
	 *            消息内容
	 * @param msgId
	 *            消息ID
	 * @param operation
	 *            操作指令（例：0打开app）
	 * @param msgAction
	 *            指令内容
	 * @return
	 */
	public static PushPayload buildPushObject_android_and_ios(String tag, String alert, Integer msgId,
			Integer operation, String msgAction) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.tag(tag))
				.setNotification(
						Notification.newBuilder().setAlert(alert)
								.addPlatformNotification(AndroidNotification.newBuilder().setTitle(ANDROID_TITLE)
										.addExtra("msgId", msgId).addExtra("operation", operation)
										.addExtra("msgAction", msgAction).build())
								.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).setSound(soundName)
										.addExtra("msgId", msgId).addExtra("operation", operation)
										.addExtra("msgAction", msgAction).build())
								.build())
				.setMessage(Message.content(alert)).build();
	}

	/**
	 * 给安卓用户发送短信
	 * 
	 * @param alert
	 * @param title
	 * @param alias
	 * @return
	 */
	public static PushResult sendAndroidSms(String alert, String title, String alias) {
		PushResult result = null;
		try {
			SMS sms = SMS.content(alert, 10);
			result = getClientInstance().sendAndroidMessageWithAlias(title, alert, sms, alias);
		} catch (APIConnectionException e) {
			logger.error(e.getMessage());
		} catch (APIRequestException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 给ios用户发送短信
	 * 
	 * @param alert
	 * @param title
	 * @param alias
	 * @return
	 */
	public static PushResult sendIosSms(String alert, String title, String alias) {
		PushResult result = null;
		try {
			result = getClientInstance().sendIosMessageWithAlias(title, alert, alias);
		} catch (APIConnectionException e) {
			logger.error(e.getMessage());
		} catch (APIRequestException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 根据msgId获取统计信息
	 * 
	 * @param msgId
	 *            多个msgid用，分割
	 * @return
	 */
	public static ReceivedsResult getMessageReport(String msgId) {
		ReceivedsResult result = null;
		try {
			result = getClientInstance().getReportReceiveds(msgId);
		} catch (APIConnectionException e) {
			logger.error(e.getMessage());

		} catch (APIRequestException e) {
			logger.error(e.getMessage());
		}
		return result;
	}


	/**
	 * 调用样例
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// ios用户
		PushPayload payload = buildPushObject_ios_tag_alert("汇盈金服测试样例1", "1", 1, "0");
		PushResult result = null;
		try {
			result = JPush.getClientInstance().sendPush(payload);
		} catch (APIConnectionException e) {
			logger.error(e.getMessage());
		} catch (APIRequestException e) {
			logger.error(e.getMessage());
		}
	}
}
