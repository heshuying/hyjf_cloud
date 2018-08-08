package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.trade.client.BankOpenClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@Service
public class BankOpenClientImpl implements BankOpenClient {
	private static Logger logger = LoggerFactory.getLogger(BankOpenClient.class);

	@Autowired
	private RestTemplate restTemplate;


	@Override
	public BankOpenAccountVO selectById(int userId) {
		BankOpenAccountResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankopen/selectById/" + userId, BankOpenAccountResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public BankOpenAccountVO selectByAccountId(String accountId) {
		BankOpenAccountResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankopen/selectByAccountId/" + accountId, BankOpenAccountResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}



}
