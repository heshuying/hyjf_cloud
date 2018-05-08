/**
 * Description:银行存管接口util类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: wangkun
 * @version: 1.0
 * Created at: 2015年11月23日 上午11:48:46
 * Modification History:
 * Modified by :
 */
package com.hyjf.pay.lib.bank.util;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.PropUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import com.hyjf.pay.lib.config.SystemConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.util.Map;

public class BankCallUtils implements Serializable {
	private static Logger _log = LoggerFactory.getLogger(BankCallUtils.class);

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6921342106125704382L;

	/** THIS_CLASS */
	private static final String THIS_CLASS = BankCallUtils.class.getName();

	/** 跳转的jsp页面 */
	private static final String SEND_JSP = "/bank/bank_send";

	/** 接口路径(页面) */
	private static final String REQUEST_MAPPING_CALLAPIPAGE = "/callApiPage.json";

	/** 接口路径(后台) */
	private static final String REQUEST_MAPPING_CALLAPIBG = "/callApiBg.json";

	/** 接口路径(后台)查询用 */
	private static final String REQUEST_MAPPING_CALLAPIBG_FORQUERY = "/callApiBgForQuery.json";

	@Autowired
	public static SystemConfig systemConfig;
	/**
	 * 调用接口(页面)
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static ModelAndView callApi(BankCallBean bean) throws Exception {
		String methodName = "callApi";
		_log.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		// 跳转页面
		ModelAndView modelAndView = new ModelAndView(SEND_JSP);
		try {
			// 取出调用汇付接口的url
			String payurl =  systemConfig.getBankUrl();
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
			String result = HttpDeal.post(payurl + REQUEST_MAPPING_CALLAPIPAGE, allParams);
			// 将返回字符串转换成Map
			if (Validator.isNotNull(result)) {
				@SuppressWarnings("unchecked")
				Map<String, String> map = JSONObject.parseObject(result, Map.class);
				modelAndView.addAllObjects(map);
			}
		} catch (Exception e) {
			_log.error(String.valueOf(e));
			throw e;
		} finally {
			_log.debug("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		}
		return modelAndView;
	}

	/**
	 * 调用接口
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static BankCallBean callApiBg(BankCallBean bean) {

		String methodName = "callApiBg";
		_log.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		BankCallBean ret = null;
		try {
			// bean转换成参数
			bean.convert();
			// 取出调用汇付接口的url
			String payurl = systemConfig.getBankUrl();
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
			_log.info("调用银行接口url: {}", url);
			String result = HttpDeal.post(url, allParams);
			if (Validator.isNotNull(result)) {
				// 将返回字符串转换成BankCallBean
				ret = JSONObject.parseObject(result, BankCallBean.class);
			}
		} catch (Exception e) {
			_log.error(String.valueOf(e));
		} finally {
			_log.debug("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		}
		return ret;
	}

	/**
	 * 取得页面同步返回 URL
	 */
	public static String getPageRetUrl() {
		return PropUtils.getSystem(BankCallConstant.BANK_PAGE_RETURN_URL);
	}

	/**
	 * 取得页面异步返回 URL
	 */
	public static String getPageNotifyUrl() {
		return PropUtils.getSystem(BankCallConstant.BANK_PAGE_CALLBACK_URL);
	}

	/**
	 * 取得页面异步返回 URL
	 */
	public static String getApiNotifyURL() {
		return PropUtils.getSystem(BankCallConstant.BANK_API_CALLBACK_URL);
	}

	/**
	 * 忘记密码url
	 * 
	 * @return
	 */
	public static String getForgotPwdUrl() {
		return PropUtils.getSystem(BankCallConstant.BANK_PAGE_FORGOTPWD_URL);
	}
}
