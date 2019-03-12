package com.hyjf.cs.user.controller.app.bestserver;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.util.SignValue;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.result.ServerResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author xiasq
 * @version ServerController, v0.1 2018/4/25 19:21
 */
@RestController
@Api(tags = "app端-最优服务器")
@RequestMapping("/hyjf-app/server")
public class ServerController extends BaseUserController {
	private Logger logger = LoggerFactory.getLogger(ServerController.class);

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private CommonProducer commonProducer;

	/**
	 * 获取最优服务器
	 *
	 * @return
	 */
	@ApiOperation(value = "获取最优服务器", notes = "获取最优服务器")
	@PostMapping("/getBestServerAction")
	public AppResult getBestServer(@RequestHeader String platform, @RequestHeader String randomString,
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
				if (version.substring(0, 5).equals(systemConfig.getNewVersion())) {
					logger.info("-------------version格式化：" + version.substring(0, 5));
					// 唯一标识
					String sign = "6bcbd50a-27c4-4aac-b448-ea6b1b9228f43GYE604";

					// 初始化密钥
					String initKey = "XtG6KHmz";

					// 保存到Redis中
					SignValue signValue = new SignValue(initKey);
					signValue.setVersion(version);
					RedisUtils.set(RedisConstants.SIGN + sign, JSON.toJSONString(signValue), RedisUtils.signExpireTime);

					resultBean.setServerIp(DES.encryptDES_ECB(systemConfig.getTestServerIp(), initKey));
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
					RedisUtils.set(RedisConstants.SIGN + sign, JSON.toJSONString(signValue), RedisUtils.signExpireTime);

					resultBean.setServerIp(DES.encryptDES_ECB(systemConfig.getAppFrontHost(), initKey));
					resultBean.setInitKey(DES.encryptDES_ECB(initKey, appKey));
					resultBean.setSign(sign);
					// 保存InitKey
				}

				logger.debug(">>>>>issafe=== false：appId={}, appKey={}, randomString={}, secretKey={}",appId, appKey, randomString, secretKey);
			} else {
				logger.info(">>>>>issafe=== false：appId={}, appKey={}, randomString={}, secretKey={}",appId, appKey, randomString, secretKey);
				resultBean.setStatus("1");
				resultBean.setStatusDesc("安全码错误");
			}
		} catch (Exception e) {
			resultBean.setStatus("1");
			resultBean.setStatusDesc("获取最优服务器发生错误");
		}
		return resultBean;
	}

	/**
	 * 获取算法密钥
	 *
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value = "获取算法密钥", notes = "获取算法密钥")
	@PostMapping("/getKeyAction")
	public AppResult getKey(@RequestHeader String sign, @RequestHeader String version) {
		ServerResultBean resultBean = new ServerResultBean();

		try {
			if (version.substring(0, 5).equals(systemConfig.getNewVersion())
					&& "6bcbd50a-27c4-4aac-b448-ea6b1b9228f43GYE604".equals(sign)) {
				String key = "iUq3OGYv";
				// 保存Key
				String value = RedisUtils.get(RedisConstants.SIGN + sign);
				logger.info("value is :{}", value);
				SignValue signValue = JSON.parseObject(value, SignValue.class);
				signValue.setKey(key);
				signValue.setVersion(version);
				RedisUtils.set(RedisConstants.SIGN + sign, JSON.toJSONString(signValue));
				resultBean.setKey(DES.encryptDES_ECB(key, signValue.getInitKey()));
				logger.info("---------获取key的值：key:" + key + "加密key:" + DES.encryptDES_ECB(key, signValue.getInitKey()));
			} else {
				String key = GetCode.getRandomCode(8);

				// 保存Key
				String value = RedisUtils.get(RedisConstants.SIGN + sign);
				logger.info("value is :{}", value);
				SignValue signValue = JSON.parseObject(value, SignValue.class);
				signValue.setKey(key);
				signValue.setVersion(version);
				RedisUtils.set(RedisConstants.SIGN + sign, JSON.toJSONString(signValue));

				resultBean.setKey(DES.encryptDES_ECB(key, signValue.getInitKey()));
			}
			// 获取渠道编号
			String[] temp = version.split("\\.");
			if (temp.length > 3) {
				AppAccesStatisticsVO vo = new AppAccesStatisticsVO();
				int sourceId = Integer.parseInt(temp[3]);
				vo.setSourceId(sourceId);
				vo.setAccessTime(new Date());
				commonProducer.messageSend(
						new MessageContent(MQConstant.APP_ACCESS_STATISTICS_TOPIC, sign, vo));
			}

		} catch (Exception e) {
			resultBean.setStatus("1");
			resultBean.setStatusDesc("获取算法密钥发生错误");
		}
		return resultBean;
	}

}
