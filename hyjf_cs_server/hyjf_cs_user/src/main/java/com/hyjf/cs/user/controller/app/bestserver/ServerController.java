package com.hyjf.cs.user.controller.app.bestserver;

import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.mq.producer.AppAccessStatisticsProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.util.*;
import com.hyjf.cs.user.result.ServerResultBean;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;

/**
 * @author xiasq
 * @version ServerController, v0.1 2018/4/25 19:21
 */
@RestController
@RequestMapping("/hyjf-app/server")
public class ServerController extends BaseUserController {
	private Logger logger = LoggerFactory.getLogger(ServerController.class);

	/** 从系统配置中获取最新版本号 */
	@Value("${hyjf.app.version.new}")
	private String newVersion;
	/** 从系统配置中获取测试环境地址 */
	@Value("${hyjf.app.serverip.test}")
	private String testServerIp;

	@Value("${hyjf.app.server.host}")
	private String hyjf_app_server_host;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	AppAccessStatisticsProducer producer;

	/**
	 * 获取最优服务器
	 *
	 * @return
	 */
	@RequestMapping("/getBestServerAction")
	public ServerResultBean getBestServer(@RequestHeader String platform, @RequestHeader String randomString,
			@RequestHeader String secretKey, @RequestHeader String appId, @RequestHeader String version) {
		ServerResultBean resultBean = new ServerResultBean();

		String appKey = "";

		if (CommonConstant.CLIENT_ANDROID.equals(platform)) {
			// 取得appKey
			appKey = "f5lnl3lN";
		} else if (CommonConstant.CLIENT_IOS.equals(platform)) {
			// 取得appKey
			appKey = "tN83YxYY";
		} else {
			resultBean.setStatus("1");
			resultBean.setStatusDesc("请求参数非法");
			return resultBean;
		}

		try {

			// 检验安全码
			boolean isSafe = SecretUtil.checkSecretKey(appId, appKey, randomString, secretKey);

			if (isSafe) {
				if (version.substring(0, 5).equals(newVersion)) {
					logger.info("-------------version格式化：" + version.substring(0, 5));
					// 唯一标识
					String sign = "6bcbd50a-27c4-4aac-b448-ea6b1b9228f43GYE604";

					// 初始化密钥
					String initKey = "XtG6KHmz";

					// 保存到Redis中
					SignValue signValue = new SignValue(initKey);
					signValue.setVersion(version);
					RedisUtils.set(sign, JSON.toJSONString(signValue), RedisUtils.signExpireTime);

					resultBean.setServerIp(DES.encryptDES_ECB(testServerIp, initKey));
					resultBean.setInitKey(DES.encryptDES_ECB(initKey, appKey));
					resultBean.setSign(sign);
					// 保存InitKey
				} else {
					// 唯一标识
					String sign = SecretUtil.createSign();

					// 初始化密钥
					String initKey = GetCode.getRandomCode(8);

					// 保存到Redis中
					SignValue signValue = new SignValue(initKey);
					signValue.setVersion(version);
					RedisUtils.set(sign, JSON.toJSONString(signValue), RedisUtils.signExpireTime);

					resultBean.setServerIp(DES.encryptDES_ECB(hyjf_app_server_host, initKey));
					resultBean.setInitKey(DES.encryptDES_ECB(initKey, appKey));
					resultBean.setSign(sign);
					// 保存InitKey
				}
			} else {
				resultBean.setStatus("1");
				resultBean.setStatus("安全码错误");
			}
		} catch (Exception e) {
			resultBean.setStatus("1");
			resultBean.setStatus("获取最优服务器发生错误");
		}
		return resultBean;
	}

	/**
	 * 获取算法密钥
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getKeyAction")
	public ServerResultBean getKey(@RequestHeader String sign, @RequestHeader String version) {
		ServerResultBean resultBean = new ServerResultBean();

		try {
			if (version.substring(0, 5).equals(newVersion)
					&& "6bcbd50a-27c4-4aac-b448-ea6b1b9228f43GYE604".equals(sign)) {
				String key = "iUq3OGYv";
				// 保存Key
				String value = RedisUtils.get(sign);
				logger.info("value is :{}", value);
				SignValue signValue = JSON.parseObject(value, SignValue.class);
				signValue.setKey(key);
				signValue.setVersion(version);
				RedisUtils.set(sign, JSON.toJSONString(signValue));
				resultBean.setKey(DES.encryptDES_ECB(key, signValue.getInitKey()));
				logger.info("---------获取key的值：key:" + key + "加密key:" + DES.encryptDES_ECB(key, signValue.getInitKey()));
			} else {
				String key = GetCode.getRandomCode(8);

				// 保存Key
				String value = RedisUtils.get(sign);
				logger.info("value is :{}", value);
				SignValue signValue = JSON.parseObject(value, SignValue.class);
				signValue.setKey(key);
				signValue.setVersion(version);
				RedisUtils.set(sign, JSON.toJSONString(signValue));

				resultBean.setKey(DES.encryptDES_ECB(key, signValue.getInitKey()));
			}
			// 获取渠道编号
			String[] temp = version.split("\\.");
			if (temp.length > 3) {
				AppAccesStatisticsVO vo = new AppAccesStatisticsVO();
				int sourceId = Integer.parseInt(temp[3]);
				vo.setSourceId(sourceId);
				vo.setAccessTime(new Date());
				producer.messageSend(
						new MessageContent(MQConstant.APP_ACCESS_STATISTICS_TOPIC, sign, JSON.toJSONBytes(vo)));
			}

		} catch (Exception e) {
			resultBean.setStatus("1");
			resultBean.setStatus("获取算法密钥发生错误");
		}
		return resultBean;
	}

}
