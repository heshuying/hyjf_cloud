package com.hyjf.common.jpush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyjf.common.util.PropUtils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.report.ReceivedsResult;

/**
 * 极光接口 服务APP：ios-pro，渠道号39
 * 
 * @author Michael
 */
public class JPushPro {
	protected static final Logger LOG = LoggerFactory.getLogger(JPushPro.class);

	/**
	 * appkey 值
	 */
	private static final String appKey = "ac465381fbafea276fec7ced";

	/**
	 * 秘钥
	 */
	private static final String masterSecret = "398fbeafdfe24e2055a75d6f";

	private static JPushClient jpushClient = null;

	/**
	 * ios是否为开发环境 true为生产 false 为开发环境
	 */
	private static boolean isProduction = Boolean.valueOf(PropUtils.getSystem("hyjf.jpush.isproduction"));

	/**
	 * 单例 实例化
	 * 
	 * @return
	 */
	public static JPushClient getClientInstance() {
		if (jpushClient == null) {
			ClientConfig clientConfig = ClientConfig.getInstance();
			clientConfig.setApnsProduction(isProduction); // ios 开发环境
			jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
		}
		return jpushClient;
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
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
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
			e.printStackTrace();

		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return result;
	}

}
