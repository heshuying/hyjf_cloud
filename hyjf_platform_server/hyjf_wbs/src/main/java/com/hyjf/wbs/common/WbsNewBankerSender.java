/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.spring.SpringUtils;
import com.hyjf.wbs.WbsConstants;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.qvo.WbsCommonExQO;
import com.hyjf.wbs.qvo.WbsCommonQO;
import com.hyjf.wbs.qvo.csuser.ResultEnum;
import com.hyjf.wbs.sign.WbsSignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * newbanker接口调用通用类
 * 
 * @author cui
 * @version WbsNewBankerSender, v0.1 2019/4/25 9:45
 */
public class WbsNewBankerSender {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 调用接口名称
	private String interfaceName;

	// 请求参数
	private Object parameter;

	private final WbsConfig wbsConfig;

	public WbsNewBankerSender(String interfaceName, Object object) {
		this.interfaceName = interfaceName;
		this.parameter = object;
		this.wbsConfig = SpringUtils.getBean(WbsConfig.class);
	}

	public String send() {

		WbsCommonQO wbsCommonQO = new WbsCommonQO();
		wbsCommonQO.setApp_key(wbsConfig.getAppKey());
		wbsCommonQO.setName(interfaceName);

		String dataJson = JSON.toJSONString(parameter);
		logger.info("【{}】原始数据【{}】",interfaceName,dataJson);
		try {
			wbsCommonQO.setData(URLEncoder.encode(dataJson, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("为数据【{}】UTF-8编码出错", dataJson);
			throw new CheckException(ResultEnum.FAIL.getStatus(), "编码出错！" + e.getMessage());
		}
		wbsCommonQO.setAccess_token("");
		wbsCommonQO.setVersion("");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(new Date());

		wbsCommonQO.setTimestamp(nowTime);

		WbsCommonExQO commonExQO = new WbsCommonExQO();
		BeanUtils.copyProperties(wbsCommonQO, commonExQO);
		commonExQO.setSign(WbsSignUtil.encrypt(wbsCommonQO, wbsConfig.getAppSecret()));

		String jsonRequest = JSONObject.toJSONString(commonExQO);

		logger.info("【{}】请求数据【{}】", interfaceName, jsonRequest);

		String postUrl = wbsConfig.getSyncCustomerUrl();

		String content = HttpClientUtils.postJson(postUrl, jsonRequest);

		logger.info("【{}】返回数据【{}】", interfaceName, content);

		return content;
	}
}
