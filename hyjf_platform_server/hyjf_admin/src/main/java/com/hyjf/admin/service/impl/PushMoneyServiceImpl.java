/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.PushMoneyRequestBean;
import com.hyjf.admin.service.PushMoneyService;
import com.hyjf.am.response.trade.PushMoneyResponse;

/**
 * @author fuqiang
 * @version PushMoneyServiceImpl, v0.1 2018/7/10 11:00
 */
@Service
public class PushMoneyServiceImpl implements PushMoneyService {
	@Autowired
	private AmTradeClient amTradeClient;

	@Override
	public PushMoneyResponse getRecordList() {
		return amTradeClient.getRecordList();
	}

	@Override
	public PushMoneyResponse insertPushMoney(PushMoneyRequestBean requestBean) {
		return amTradeClient.insertPushMoney(requestBean);
	}

	@Override
	public PushMoneyResponse updatePushMoney(PushMoneyRequestBean requestBean) {
		return amTradeClient.updatePushMoney(requestBean);
	}
}
