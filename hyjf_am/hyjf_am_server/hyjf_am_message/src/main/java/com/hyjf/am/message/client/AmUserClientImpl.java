package com.hyjf.am.message.client;

import com.hyjf.am.vo.user.UserAliasVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author xiasq
 * @version AmUserClientImpl, v0.1 2018/4/19 12:44
 */

@Service
public class AmUserClientImpl implements AmUserClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserVO findUserByMobile(String mobile) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findByMobile/" + mobile, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserVO findUserById(int userId) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findById" + userId, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserInfoVO findUserInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userInfo/findById" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserAliasVO findAliasByMobile(String mobile) {
		return null;
	}

	@Override
	public List<UserAliasVO> findAliasesByMobiles(List<String> mobiles) {
		return null;
	}

	@Override
	public int countAliasByClient(String clientAndroid) {
		return 0;
	}
}
