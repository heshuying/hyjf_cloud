/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.beans.request.PushMoneyRequestBean;
import com.hyjf.admin.client.PushMoneyClient;
import com.hyjf.am.response.trade.PushMoneyResponse;

/**
 * @author fuqiang
 * @version PushMoneyClientImpl, v0.1 2018/7/10 11:10
 */
@Service
public class PushMoneyClientImpl implements PushMoneyClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public PushMoneyResponse getRecordList() {
		return restTemplate.getForObject("http://AM-TRADE/am-trade/pushmoney/getrecordlist", PushMoneyResponse.class);
	}

	@Override
	public PushMoneyResponse insertPushMoney(PushMoneyRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-TRADE/am-trade/pushmoney/insertpushmoney", requestBean,
				PushMoneyResponse.class);
	}

	@Override
	public PushMoneyResponse updatePushMoney(PushMoneyRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-TRADE/am-trade/pushmoney/updatepushmoney", requestBean,
				PushMoneyResponse.class);
	}
}
