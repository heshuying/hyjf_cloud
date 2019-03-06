package com.hyjf.pay.lib.anrong.util;

import com.hyjf.common.spring.SpringUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.anrong.bean.AnRongBean;
import com.hyjf.pay.lib.config.URLSystemConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * 向安融发送请求util类
 * @author sss
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年10月10日
 * @see
 */
public class AnRongCallUtils implements Serializable {
	private static Logger log = LoggerFactory.getLogger(AnRongCallUtils.class);
    private static final long serialVersionUID = -7729847094997019720L;

	/** 接口路径(后台) */
	private static final String REQUEST_MAPPING_CALLAPIBG = "/callApiBg.json";
	//private static PaySystemConfig paySystemConfig = SpringUtils.getBean(PaySystemConfig.class);
	private static URLSystemConfig urlSystemConfig = SpringUtils.getBean(URLSystemConfig.class);
	private static RestTemplate restTemplate = SpringUtils.getBean(RestTemplate.class);


	/**
	 * 调用接口
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static String callApiBg(AnRongBean bean) {
		log.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean) + "]");
		String ret = null;
		try {
			// bean转换成参数
			bean.convert();

			if (Validator.isNull(urlSystemConfig.getPayUrl())) {
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
			// 调用安融接口
			//String result = HttpDeal.post(paySystemConfig.getPayUrl().replace("chinapnr", "anrongcall") + REQUEST_MAPPING_CALLAPIBG, allParams);
			String result = restTemplate
				.postForEntity(urlSystemConfig.getAnrongUrl() + REQUEST_MAPPING_CALLAPIBG, allParams, String.class).getBody();
			ret = result;
		} catch (Exception e) {
			log.error(String.valueOf(e));
		} finally {
			log.debug("[调用接口结束, 消息类型:" + (bean == null ? "" : bean) + "]");
		}
		return ret;
	}
	/**
	 * 调用接口
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static String callApiBgSend( Map<String, String> params ) {

		String ret = null;
		try {
			log.info("[调用接口开始, 消息类型:" + (params == null ? "" : params.toString()) + "]");
			// bean转换成参数
	
			// 调用安融接口
			//String result = HttpDeal.post(paySystemConfig.getPayUrl().replace("chinapnr", "anrongcall") + REQUEST_MAPPING_CALLAPIBG, allParams);
			String result = restTemplate
				.postForEntity(urlSystemConfig.getPayUrl().replace("chinapnr", "anrongcall") + REQUEST_MAPPING_CALLAPIBG, params, String.class).getBody();
			ret = result;
		} catch (Exception e) {
			log.error(String.valueOf(e));
		} finally {
			log.debug("[调用接口结束, 消息类型:" + (params == null ? "" : params.toString()) + "]");
		}
		return ret;
	}
}
