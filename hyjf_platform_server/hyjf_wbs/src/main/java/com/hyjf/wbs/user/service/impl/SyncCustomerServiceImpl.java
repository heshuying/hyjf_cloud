/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.service.impl;

import java.util.Map;

import com.hyjf.common.exception.CheckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.wbs.WbsConstants;
import com.hyjf.wbs.common.WbsNewBankerSender;
import com.hyjf.wbs.exceptions.WbsException;
import com.hyjf.wbs.qvo.CustomerSyncQO;
import com.hyjf.wbs.user.service.SyncCustomerService;

/**
 * @author cui
 * @version SyncCustomerServiceImpl, v0.1 2019/4/16 17:09
 */
@Service
public class SyncCustomerServiceImpl implements SyncCustomerService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void sync(CustomerSyncQO customerSyncQO) {

		WbsNewBankerSender sender = new WbsNewBankerSender(WbsConstants.INTERFACE_NAME_SYNC_CUSTOMER, customerSyncQO);

		String content = sender.send();

		JSONObject jasonObject = JSONObject.parseObject(content);
		Map map = jasonObject;
		if (map != null && WbsConstants.WBS_RESPONSE_STATUS_SUCCESS
				.equals(String.valueOf(map.get(WbsConstants.WBS_RESPONSE_STATUS_KEY)))) {
			return;
		} else {
			logger.error("客户信息回调接口返回失败！详细信息【{}】", map.get(WbsConstants.WBS_RESPONSE_ERROR_MSG_KEY));
			throw new CheckException("99", String.valueOf(map.get(WbsConstants.WBS_RESPONSE_ERROR_MSG_KEY)));
		}
	}

}
