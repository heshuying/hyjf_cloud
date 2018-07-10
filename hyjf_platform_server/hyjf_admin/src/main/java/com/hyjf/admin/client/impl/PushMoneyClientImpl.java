/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.PushMoneyRequestBean;
import com.hyjf.admin.client.PushMoneyClient;
import com.hyjf.am.response.trade.PushMoneyResponse;

/**
 * @author fuqiang
 * @version PushMoneyClientImpl, v0.1 2018/7/10 11:10
 */
@Service
public class PushMoneyClientImpl implements PushMoneyClient {
	@Override
	public PushMoneyResponse getRecordList() {
		return null;
	}

	@Override
	public PushMoneyResponse insertPushMoney(PushMoneyRequestBean requestBean) {
		return null;
	}

	@Override
	public PushMoneyResponse updatePushMoney(PushMoneyRequestBean requestBean) {
		return null;
	}
}
