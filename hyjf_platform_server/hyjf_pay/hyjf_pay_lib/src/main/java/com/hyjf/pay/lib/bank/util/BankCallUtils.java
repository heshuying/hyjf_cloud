/**
 * Description:银行存管接口util类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: zhangqingqing
 * @version: 1.0
 * Created at: 2015年11月23日 上午11:48:46
 * Modification History:
 * Modified by :
 */
package com.hyjf.pay.lib.bank.util;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.spring.SpringUtils;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.config.URLSystemConfig;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.util.Map;
@SuppressWarnings("unchecked")
public class BankCallUtils implements Serializable {
	private static Logger logger = LoggerFactory.getLogger(BankCallUtils.class);

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6921342106125704382L;

	/** 跳转的jsp页面 */
	private static final String SEND_JSP = "bank/bank_send.html";

	/** 接口路径(页面) */
	private static final String  REQUEST_MAPPING_CALLAPIPAGE = "/callApiPage.json";

	/** 接口路径(后台) */
	private static final String REQUEST_MAPPING_CALLAPIBG = "/callApiBg.json";

	/** 接口路径(后台)查询用 */
	private static final String REQUEST_MAPPING_CALLAPIBG_FORQUERY = "/callApiBgForQuery.json";
	
	private static URLSystemConfig URLSystemConfig = SpringUtils.getBean(URLSystemConfig.class);
	
	private static RestTemplate restTemplate = SpringUtils.getBean(RestTemplate.class);

	/**
	 * 调用接口(页面)
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static ModelAndView callApi(BankCallBean bean) throws Exception {
		logger.debug("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		// 跳转页面
		ModelAndView modelAndView = new ModelAndView(SEND_JSP);
		try {
			// 取出调用汇付接口的url
			String payurl =  URLSystemConfig.getBankUrl();
			if (Validator.isNull(payurl)) {
				throw new Exception("接口工程URL不能为空");
			}
			Map<String, String> allParams = bean.getAllParams();
			if (StringUtils.isNotBlank(bean.getLogUserId())) {
				allParams.put("logUserId", bean.getLogUserId());
			}
			if (StringUtils.isNotBlank(bean.getLogOrderId())) {
				allParams.put("logOrderId", bean.getLogOrderId());
			}
			if (StringUtils.isNotBlank(bean.getLogType())) {
				allParams.put("logType", bean.getLogType());
			}
			if (StringUtils.isNotBlank(bean.getLogIp())) {
				allParams.put("logIp", bean.getLogIp());
			}
			if (StringUtils.isNotBlank(bean.getLogTime())) {
				allParams.put("logTime", bean.getLogTime());
			}
			if (StringUtils.isNotBlank(bean.getLogRemark())) {
				allParams.put("logRemark", bean.getLogRemark());
			}
			if (StringUtils.isNotBlank(bean.getLogOrderDate())) {
				allParams.put("logOrderDate", bean.getLogOrderDate());
			}
			if (StringUtils.isNotBlank(bean.getLogBankDetailUrl())) {
				allParams.put("logBankDetailUrl", bean.getLogBankDetailUrl());
			}
			// 调用汇付接口
//			String result = HttpDeal.post(payurl + REQUEST_MAPPING_CALLAPIPAGE, allParams);
			String url = payurl + REQUEST_MAPPING_CALLAPIPAGE;
			String result = restTemplate
					.postForEntity(url, allParams, String.class).getBody();
			// 将返回字符串转换成Map
			if (Validator.isNotNull(result)) {
				@SuppressWarnings("unchecked")
				Map<String, String> map = JSONObject.parseObject(result, Map.class);
				modelAndView.addAllObjects(map);
			}
		} catch (Exception e) {
			logger.error(String.valueOf(e));
			throw e;
		} finally {
			logger.debug("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		}
		return modelAndView;
	}

	public static Map<String, Object> callApiMap(BankCallBean bean) throws Exception {
		logger.debug("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		// 跳转页面
		try {
			// 取出调用汇付接口的url
			String payurl =  URLSystemConfig.getBankUrl();
			if (Validator.isNull(payurl)) {
				throw new Exception("接口工程URL不能为空");
			}
			Map<String, String> allParams = bean.getAllParams();
			if (StringUtils.isNotBlank(bean.getLogUserId())) {
				allParams.put("logUserId", bean.getLogUserId());
			}
			if (StringUtils.isNotBlank(bean.getLogOrderId())) {
				allParams.put("logOrderId", bean.getLogOrderId());
			}
			if (StringUtils.isNotBlank(bean.getLogType())) {
				allParams.put("logType", bean.getLogType());
			}
			if (StringUtils.isNotBlank(bean.getLogIp())) {
				allParams.put("logIp", bean.getLogIp());
			}
			if (StringUtils.isNotBlank(bean.getLogTime())) {
				allParams.put("logTime", bean.getLogTime());
			}
			if (StringUtils.isNotBlank(bean.getLogRemark())) {
				allParams.put("logRemark", bean.getLogRemark());
			}
			if (StringUtils.isNotBlank(bean.getLogOrderDate())) {
				allParams.put("logOrderDate", bean.getLogOrderDate());
			}
			if (StringUtils.isNotBlank(bean.getLogBankDetailUrl())) {
				allParams.put("logBankDetailUrl", bean.getLogBankDetailUrl());
			}
			// 调用银行接口
//			String result = HttpDeal.post(payurl + REQUEST_MAPPING_CALLAPIPAGE, allParams);
			String url = payurl + REQUEST_MAPPING_CALLAPIPAGE;
			String result = restTemplate
					.postForEntity(url, allParams, String.class).getBody();
			logger.debug("调用银行接口结果： {}", result);
			// 将返回字符串转换成Map
			if (Validator.isNotNull(result)) {
				@SuppressWarnings("unchecked")
				Map<String, ?> map = JSONObject.parseObject(result, Map.class);
				Map<String, ?> bankForm = (Map)map.get("bankForm");
				JSONObject allParam = JSONObject.parseObject((String)bankForm.get("json"),JSONObject.class);
				Map<String, Object> retMap = new HashedMap();
				retMap.put("allParams",allParam);
				retMap.put("action",(String)bankForm.get("action"));
				return retMap;
			}
		} catch (Exception e) {
			logger.error(String.valueOf(e));
			throw e;
		} finally {
			logger.debug("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		}
		return null;
	}

	/**
	 * 调用接口
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static BankCallBean callApiBg(BankCallBean bean) {
        logger.debug("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		BankCallBean ret = null;
		try {
			// bean转换成参数
			bean.convert();
			// 取出调用汇付接口的url
			String payurl = URLSystemConfig.getBankUrl();
			if (Validator.isNull(payurl)) {
				throw new Exception("接口工程URL不能为空");
			}
			Map<String, String> allParams = bean.getAllParams();
			if (StringUtils.isNotBlank(bean.getLogUserId())) {
				allParams.put("logUserId", bean.getLogUserId());
			}
			if (StringUtils.isNotBlank(bean.getLogOrderId())) {
				allParams.put("logOrderId", bean.getLogOrderId());
			}
			if (StringUtils.isNotBlank(bean.getLogType())) {
				allParams.put("logType", bean.getLogType());
			}
			if (StringUtils.isNotBlank(bean.getLogIp())) {
				allParams.put("logIp", bean.getLogIp());
			}
			if (StringUtils.isNotBlank(bean.getLogTime())) {
				allParams.put("logTime", bean.getLogTime());
			}
			if (StringUtils.isNotBlank(bean.getLogRemark())) {
				allParams.put("logRemark", bean.getLogRemark());
			}
			if (StringUtils.isNotBlank(bean.getLogOrderDate())) {
				allParams.put("logOrderDate", bean.getLogOrderDate());
			}
			if (StringUtils.isNotBlank(bean.getLogBankDetailUrl())) {
				allParams.put("logBankDetailUrl", bean.getLogBankDetailUrl());
			}
			// 调用银行接口
			String url = payurl + REQUEST_MAPPING_CALLAPIBG;
			logger.info("调用银行接口url: {}, txCode: [{}], orderId: [{}]", url, bean == null ? "" : bean.getTxCode(), bean == null ? "" : bean.getLogOrderId());
//			String result = HttpDeal.post(url, allParams);
			String result = restTemplate
					 .postForEntity(url, allParams, String.class).getBody();
			if (Validator.isNotNull(result)) {
				// 将返回字符串转换成BankCallBean
				ret = JSONObject.parseObject(result, BankCallBean.class);
			}
		} catch (Exception e) {
			logger.error(String.valueOf(e));
		} finally {
			logger.debug("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		}
		return ret;
	}


	public static String getClientName(String client){
		if(client==null){
			return "未知";
		}
		if ((ClientConstants.WEB_CLIENT+"").equals(client)) {
			return "PC";
		}
		if ((ClientConstants.APP_CLIENT_IOS+"").equals(client)) {
			return "IOS";
		}
		if ((ClientConstants.APP_CLIENT+"").equals(client)) {
			return "安卓";
		}
		if ((ClientConstants.WECHAT_CLIENT+"").equals(client)) {
			return "微信";
		}
		return "空";
	}
}
