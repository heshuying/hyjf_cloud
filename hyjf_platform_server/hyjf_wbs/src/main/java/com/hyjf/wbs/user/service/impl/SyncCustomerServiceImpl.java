/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.service.impl;

import com.hyjf.wbs.exceptions.WbsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.wbs.WbsConstants;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.qvo.CustomerSyncQO;
import com.hyjf.wbs.qvo.WbsCommonQO;
import com.hyjf.wbs.sign.WbsSignUtil;
import com.hyjf.wbs.user.service.SyncCustomerService;

import java.util.Map;

/**
 * @author cui
 * @version SyncCustomerServiceImpl, v0.1 2019/4/16 17:09
 */
@Service
public class SyncCustomerServiceImpl implements SyncCustomerService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WbsConfig wbsConfig;

	@Override
	public void sync(CustomerSyncQO customerSyncQO) {

		WbsCommonQO wbsCommonQO = new WbsCommonQO();
		wbsCommonQO.setApp_key(wbsConfig.getAppKey());
		wbsCommonQO.setName(WbsConstants.INTERFACE_NAME_SYNC_CUSTOMER);
		wbsCommonQO.setSign(WbsSignUtil.encrypt(customerSyncQO, wbsConfig.getAppSecret()));
		wbsCommonQO.setData(JSONObject.toJSONString(customerSyncQO));

		String jsonRequest = JSONObject.toJSONString(wbsCommonQO);

		String postUrl = wbsConfig.getSyncCustomerUrl();

		String content = HttpClientUtils.postJson(postUrl, jsonRequest);

		JSONObject jasonObject = JSONObject.parseObject(content);
		Map map = jasonObject;
		if (map != null && WbsConstants.WBS_RESPONSE_STATUS_SUCCESS
				.equals(String.valueOf(map.get(WbsConstants.WBS_RESPONSE_STATUS_KEY)))) {
			return;
		} else {
			logger.error("客户信息回调接口返回失败！详细信息【{}】", map.get(WbsConstants.WBS_RESPONSE_ERROR_MSG_KEY));
			throw new WbsException("客户信息回调接口返回失败！");
		}
	}

}
