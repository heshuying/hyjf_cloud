/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.assetpush;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.assetpush.PushBean;
import com.hyjf.cs.trade.bean.assetpush.PushRequestBean;
import com.hyjf.cs.trade.bean.assetpush.PushResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.borrow.ApiAssetPushService;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import com.hyjf.cs.trade.util.SignUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 资产推送接口
 *
 * @author fuqiang
 * @version ApiAssetPushController, v0.1 2018/6/11 17:52
 */
@Api(tags = "api端-资产推送接口")
@RestController
@RequestMapping("/hyjf-api/server/assetpush")
public class ApiAssetPushController extends BaseTradeController {

	private static final Logger logger = LoggerFactory.getLogger(ApiAssetPushController.class);

	@Autowired
	private ApiAssetPushService pushService;

	@PostMapping("/push.do")
	@ApiParam(required = true, name = "pushRequestBean", value = "个人资产信息")
	@ApiOperation(value = "个人资产推送", httpMethod = "POST", notes = "个人资产推送")
	@HystrixCommand(commandKey = "个人资产推送(api)-assetPush", fallbackMethod = "fallBackAssetPush", ignoreExceptions = CheckException.class, commandProperties = {
			// 设置断路器生效
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
			// 一个统计窗口内熔断触发的最小个数3/10s
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
			@HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			// 熔断5秒后去尝试请求
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
			// 失败率达到30百分比后熔断
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30") })
	public JSONObject push(@RequestBody PushRequestBean pushRequestBean) {
		logger.info(this.getClass().getName(), "api端-资产推送接口 个人资产推送 start", pushRequestBean.toString(),
				"/hyjf-api/server/assetpush/push.do");

		JSONObject result = new JSONObject();
		// 验证请求参数
		List<PushBean> reqData = pushRequestBean.getReqData();
		if (Validator.isNull(reqData) || Validator.isNull(pushRequestBean.getInstCode())
				|| Validator.isNull(pushRequestBean.getAssetType())
				|| Validator.isNull(pushRequestBean.getChkValue())) {
			logger.warn("------请求参数非法-------" + pushRequestBean);
			result.put("status", ErrorCodeConstant.STATUS_CE000001);
			result.put("statusDesc", "请求参数非法");
			return result;
		}

		// 验签
		if (!SignUtil.verifyRequestSign(pushRequestBean, "/push")) {
			logger.warn("------------------验签失败！---------------------");
			result.put("status", ErrorCodeConstant.STATUS_CE000002);
			result.put("statusDesc", "验签失败！");
			return result;
		}

		logger.info(pushRequestBean.getInstCode() + " 开始推送资产 ");

		if (CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())) {
			logger.warn(pushRequestBean.getInstCode() + "  " + pushRequestBean.getAssetType() + " ------平台不能推送资产");
			result.put("status", ErrorCodeConstant.STATUS_ZT000010);
			result.put("statusDesc", "不能推送本平台资产！");
			return result;
		}

		PushResultBean resultBean = pushService.assetPush(pushRequestBean);

		logger.info(this.getClass().getName(), "api端-资产推送接口 个人资产推送 end", pushRequestBean.toString(),
				"/hyjf-api/server/assetpush/push.do");
		result.put("status", resultBean.getStatus());
		result.put("statusDesc", resultBean.getStatusDesc());
		result.put("data", resultBean);
		return result;
	}

	@PostMapping("/pushcompany.do")
	@ApiParam(required = true, name = "pushRequestBean", value = "企业资产信息")
	@ApiOperation(value = "企业资产推送", httpMethod = "POST", notes = "企业资产推送")
	@HystrixCommand(commandKey = "企业资产推送(api)-companyAssetPush", fallbackMethod = "fallBackAssetPush", ignoreExceptions = CheckException.class, commandProperties = {
			// 设置断路器生效
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
			// 一个统计窗口内熔断触发的最小个数3/10s
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
			@HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			// 熔断5秒后去尝试请求
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
			// 失败率达到30百分比后熔断
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30") })
	public JSONObject pushCompany(@RequestBody PushRequestBean pushRequestBean) {
		logger.info(this.getClass().getName(), "api端-资产推送接口 企业资产推送 start", pushRequestBean.toString(),
				"/hyjf-api/server/assetpush/pushcompany.do");
		JSONObject result = new JSONObject();

		// 验证请求参数
		List<PushBean> reqData = pushRequestBean.getReqData();
		if (Validator.isNull(reqData) || Validator.isNull(pushRequestBean.getInstCode())
				|| Validator.isNull(pushRequestBean.getAssetType())
				|| Validator.isNull(pushRequestBean.getChkValue())) {
			logger.warn("------请求参数非法-------" + pushRequestBean);
			result.put("status", ErrorCodeConstant.STATUS_CE000001);
			result.put("statusDesc", "请求参数非法");
			return result;
		}

		// 验签
		if (!SignUtil.verifyRequestSign(pushRequestBean, "/pushcompany")) {
			logger.warn("------------------验签失败！---------------------");
			result.put("status", ErrorCodeConstant.STATUS_CE000002);
			result.put("statusDesc", "验签失败！");
			return result;
		}

		logger.info(pushRequestBean.getInstCode() + " 开始推送资产 ");
		if (CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())) {
			logger.warn(pushRequestBean.getInstCode() + "  " + pushRequestBean.getAssetType() + " ------不能推送本平台资产");
			result.put("status", ErrorCodeConstant.STATUS_ZT000010);
			result.put("statusDesc", "不能推送本平台资产！");
			return result;
		}

		PushResultBean resultBean = pushService.companyAssetPush(pushRequestBean);

		logger.info(this.getClass().getName(), "api端-资产推送接口 企业资产推送 end", "/hyjf-api/server/assetpush/pushcompany.do");
		result.put("status", resultBean.getStatus());
		result.put("statusDesc", resultBean.getStatusDesc());
		result.put("data", resultBean);
		return result;
	}

	/**
	 * 熔断方法默认返回
	 *
	 * @param pushRequestBean
	 * @return
	 */
	public JSONObject fallBackAssetPush(PushRequestBean pushRequestBean) {
		logger.warn("已进入 个人/企业资产推送(api) fallBackAssetPush 熔断方法， pushRequestBean is: {}", pushRequestBean);
        JSONObject result = new JSONObject();
        result.put("status", ErrorCodeConstant.STATUS_CE999999);
        result.put("statusDesc", "系统维护，请稍后重试!");
        result.put("data", new PushResultBean());
		return result;
	}
}
