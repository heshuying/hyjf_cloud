package com.hyjf.cs.user.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.client.AmBankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;


/**
 * 
 * @author Administrator
 *
 */
@Service
public class AmBankOpenClientImpl implements AmBankOpenClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);

	@Autowired
	private RestTemplate restTemplate;

	
	@Override
	public UserInfoVO findUserInfoByCardNo(String cradNo) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankopen/findByCardId/" + cradNo, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	
	@Override
	public int updateUserAccountLog(BankOpenRequest request) {
		Integer result = restTemplate
				.postForEntity("http://AM-USER/am-user/bankopen/updateUserAccountLog", request, Integer.class).getBody();
		if (result != null) {
			return result;
		}
		return 0;
	}
	
}
