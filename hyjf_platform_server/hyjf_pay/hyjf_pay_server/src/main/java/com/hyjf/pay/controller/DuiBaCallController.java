package com.hyjf.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.http.HttpDealBank;
import com.hyjf.common.util.ConvertUtils;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.pay.base.BaseController;
import com.hyjf.pay.config.SystemConfig;
import com.hyjf.pay.entity.DuiBaReturnLog;
import com.hyjf.pay.entity.DuiBaSendLog;
import com.hyjf.pay.lib.duiba.bean.DuiBaCallBean;
import com.hyjf.pay.lib.duiba.bean.DuiBaCallResultBean;
import com.hyjf.pay.lib.duiba.sdk.*;
import com.hyjf.pay.lib.duiba.util.DuiBaCallConstant;
import com.hyjf.pay.service.DuiBaLogService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/duiba")
public class DuiBaCallController extends BaseController {
	Logger logger = LoggerFactory.getLogger(DuiBaCallController.class);

	@Autowired
	DuiBaLogService duiBaLogService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 兑吧回调扣积分接口
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@GetMapping(value = "deductPoints")
	@HystrixCommand(commandKey = "兑吧回调扣积分接口-deductPoints", commandProperties = {
			// 设置断路器生效
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
			// 一个统计窗口内熔断触发的最小个数3/10s
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
			// 熔断5秒后去尝试请求
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
			// 失败率达到30百分比后熔断
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
			// 超时时间
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "40000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "200"),
					@HystrixProperty(name = "maxQueueSize", value = "50") })
	public String deductPoints(HttpServletRequest request) {
		logger.info("兑吧调用扣积分接口开始");
		CreditTool tool = new CreditTool(systemConfig.getDuiBaAppKey(), systemConfig.getDuiBaAppSecret());
		boolean success = false;
		String errorMessage = "";
		String bizId = null;
		Long credits = 0L;
		CreditConsumeResult result = null;
		try {
			CreditConsumeParams params = tool.parseCreditConsume(request);
			logger.info("兑吧扣积分接口，兑吧订单号：{}", params.getOrderNum());
			DuiBaReturnLog returnLog = new DuiBaReturnLog();
			returnLog.setContent(ConvertUtils.convertObjectToMap(params));
			returnLog.setCreateTime(DateUtils.getNowDate());
			returnLog.setMsgType(DuiBaCallConstant.API_DEDUCT_POINTS);
			returnLog.setLogUserId(Integer.valueOf(params.getUid()));
			returnLog.setLogOrdId(params.getOrderNum());
			returnLog.setRemark("用户扣积分");
			duiBaLogService.insertDuiBaReturnLog(returnLog);
			String url = "http://CS-USER/hyjf-app/pointsshop/duiba/deductpoints";
			CreditConsumeResult postResult = restTemplate.postForEntity(url, params, CreditConsumeResult.class).getBody();
			// 根据结果向兑吧响应
			if(null != postResult ){
				if(postResult.isSuccess()){
					success = true;
					bizId = postResult.getBizId();
					credits = postResult.getCredits();
				} else {
					success = false;
					errorMessage = postResult.getErrorMessage();
					credits = postResult.getCredits();
				}
			} else {
				success = false;
				errorMessage = "兑换出现了问题，请联系相关人员处理。";
				credits = Long.valueOf(0);
			}
		} catch (Exception e) {
			success = false;
			errorMessage = "兑换出现了问题，请联系相关人员处理。";
			credits = Long.valueOf(0);
			logger.error("兑吧调用扣积分接口发生错误：", e);
		}
		result = new CreditConsumeResult(success);
		result.setBizId(bizId);
		result.setErrorMessage(errorMessage);
		result.setCredits(credits);
		logger.info("兑吧扣积分接口回调:{}", JSONObject.toJSONString(result));
		// 返回扣积分结果json信息
		return result.toString();
	}

	/**
	 * 兑吧兑换结果通知接口
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@GetMapping(value = "exchangeResult")
	@HystrixCommand(commandKey = "兑吧兑换结果通知接口-exchangeResult", commandProperties = {
			// 设置断路器生效
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
			// 一个统计窗口内熔断触发的最小个数3/10s
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
			// 熔断5秒后去尝试请求
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
			// 失败率达到30百分比后熔断
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
			// 超时时间
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "40000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "200"),
					@HystrixProperty(name = "maxQueueSize", value = "50") })
	public String exchangeResult(HttpServletRequest request) {
		logger.info("兑吧兑换结果通知接口开始");
		CreditTool tool = new CreditTool(systemConfig.getDuiBaAppKey(), systemConfig.getDuiBaAppSecret());
		String status = "success";
		try {
			CreditNotifyParams params = tool.parseCreditNotify(request);
			logger.info("兑吧兑换结果通知接口，兑吧订单号：{}", params.getOrderNum());
			DuiBaReturnLog returnLog = new DuiBaReturnLog();
			returnLog.setContent(ConvertUtils.convertObjectToMap(params));
			returnLog.setCreateTime(DateUtils.getNowDate());
			returnLog.setMsgType(DuiBaCallConstant.API_EXCHANGE_RESULT);
			returnLog.setRemark("兑吧兑换结果通知");
			returnLog.setLogUserId(Integer.valueOf(params.getUid()));
			returnLog.setLogOrdId(params.getOrderNum());
			duiBaLogService.insertDuiBaReturnLog(returnLog);
			if (params.isSuccess()) {
				// 兑换成功（更新优惠卷为有效）
				// 传兑吧订单号
				String url = "http://ADMIN/hyjf-admin/pointsshop/duiba/order/activationSuccess/" + params.getOrderNum();
				StringResponse couponUserStr = restTemplate.getForEntity(url, StringResponse.class).getBody();
				if (couponUserStr != null) {
					if(!status.equals(couponUserStr.getResultStr())){
						// 更新优惠卷为有效处理失败
						logger.error("更新优惠卷为有效处理失败！orderNum：" + params.getOrderNum());
					}
				}
			} else {
				// 1.兑换失败，根据orderNum，对用户的金币进行返还，回滚操作 2.将发放的优惠卷设置成无效, 3.兑换失败将对应的"订单"设置成无效并给出失败信息（更新虚拟商品充值状态处理中（异常码））
				// 传兑吧订单号
				String url = "http://ADMIN/hyjf-admin/pointsshop/duiba/order/activationError/" + params.getOrderNum() + "/" + params.getErrorMessage();
				StringResponse couponUserStr = restTemplate.getForEntity(url, StringResponse.class).getBody();
				if (couponUserStr != null) {
					if(!status.equals(couponUserStr.getResultStr())){
						// 回滚失败
						logger.error("订单回滚失败！orderNum：" + params.getOrderNum());
					}
				}
			}
		} catch (Exception e) {
			logger.error("兑吧兑换结果通知接口发生错误：", e);
		}

		return "ok";
	}

	/**
	 * 兑吧自有商品充值接口
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@GetMapping(value = "recharge")
	@HystrixCommand(commandKey = "兑吧自有商品充值接口-recharge", commandProperties = {
			// 设置断路器生效
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
			// 一个统计窗口内熔断触发的最小个数3/10s
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
			// 熔断5秒后去尝试请求
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
			// 失败率达到30百分比后熔断
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
			// 超时时间
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "40000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "200"),
					@HystrixProperty(name = "maxQueueSize", value = "50") })
	public VirtualResult recharge(HttpServletRequest request) {
		logger.info("兑吧自有商品充值接口开始");
		CreditTool tool = new CreditTool(systemConfig.getDuiBaAppKey(), systemConfig.getDuiBaAppSecret());
		VirtualResult result = new VirtualResult("fail");
		try {
			VirtualParams params = tool.virtualConsume(request);
			logger.info("兑吧自有商品充值接口，兑吧订单号：{}", params.getOrderNum());
			DuiBaReturnLog returnLog = new DuiBaReturnLog();
			returnLog.setContent(ConvertUtils.convertObjectToMap(params));
			returnLog.setCreateTime(DateUtils.getNowDate());
			returnLog.setMsgType(DuiBaCallConstant.API_RECHARGE);
			returnLog.setRemark("兑吧自有商品充值");
			returnLog.setLogUserId(Integer.valueOf(params.getUid()));
			returnLog.setLogOrdId(params.getOrderNum());
			duiBaLogService.insertDuiBaReturnLog(returnLog);
			// 发放优惠卷， 传兑吧订单号
			String url = "http://ADMIN/hyjf-admin/pointsshop/duiba/order/selectReleaseCoupons/" + params.getOrderNum();
			result = restTemplate.getForObject(url, VirtualResult.class);
		} catch (Exception e) {
            // 发放失败
			result.setErrorMessage(e.getMessage());
			logger.error("兑吧自有商品充值接口回调发生错误：", e);
		}
		logger.info("兑吧自有商品充值接口回调:{}", JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * 请求兑吧接口
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@PostMapping(value = "call")
	@HystrixCommand(commandKey = "请求兑吧接口-call", fallbackMethod = "fallBackDuiBaApi", ignoreExceptions = CheckException.class, commandProperties = {
			// 设置断路器生效
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
			// 一个统计窗口内熔断触发的最小个数3/10s
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
			// 熔断5秒后去尝试请求
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
			// 失败率达到30百分比后熔断
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
			// 超时时间
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "40000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "200"),
					@HystrixProperty(name = "maxQueueSize", value = "50") })
	public String call(@RequestBody DuiBaCallBean request) {
		logger.info("调用兑吧接口开始，兑吧订单号:" + request.getOrderNum());
		String result = "";
		CreditTool tool = new CreditTool(systemConfig.getDuiBaAppKey(), systemConfig.getDuiBaAppSecret());

		try {
			// 向兑吧发送请求前，先插入发送日志
			DuiBaSendLog duiBaSendLog = new DuiBaSendLog();
			duiBaSendLog.setOrderNum(request.getOrderNum());
			duiBaSendLog.setMsgType(request.getMsgType());
			duiBaSendLog.setLogUserId(request.getUserId());
			duiBaSendLog.setLogOrdId(request.getBizId());
			duiBaSendLog.setCreateTime(DateUtils.getNowDate());
			duiBaSendLog.setRemark(request.getRemark());
			duiBaLogService.insertDuiBaSendLog(duiBaSendLog);

			// 创建请求url并请求
			String url = tool.buildCreditOrderStatusRequest(request.getOrderNum(), request.getBizId());
			result = HttpDealBank.get(url);

			// 返回结果时，先插入返回日志
			if (StringUtils.isNotBlank(result)) {
				DuiBaReturnLog duiBaReturnLog = new DuiBaReturnLog();
				duiBaReturnLog.setOrderNum(request.getOrderNum());
				duiBaReturnLog.setMsgType(request.getMsgType());
				duiBaReturnLog.setLogUserId(request.getUserId());
				duiBaReturnLog.setLogOrdId(request.getBizId());
				duiBaReturnLog.setCreateTime(DateUtils.getNowDate());
				duiBaReturnLog.setRemark("兑吧订单查询结果");
				DuiBaCallResultBean queryResult = JSONObject.parseObject(result, DuiBaCallResultBean.class);
				duiBaReturnLog.setContent(ConvertUtils.convertObjectToMap(queryResult));
				duiBaLogService.insertDuiBaReturnLog(duiBaReturnLog);
			}

		} catch (Exception e) {
			logger.error("调用兑吧接口异常：", e);
		}
		return result;
	}

	public String fallBackDuiBaApi(DuiBaCallBean request) {
		logger.info("==================兑吧 fallBackDuiBaApi 方法================");
		return "";
	}
}