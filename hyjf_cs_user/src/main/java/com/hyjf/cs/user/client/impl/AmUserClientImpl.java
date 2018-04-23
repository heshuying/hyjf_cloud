package com.hyjf.cs.user.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.user.request.RegisterUserRequest;
import com.hyjf.am.user.request.SmsCodeRequest;
import com.hyjf.am.user.response.UserInfoResponse;
import com.hyjf.am.user.response.UserResponse;
import com.hyjf.am.user.vo.UserInfoVO;
import com.hyjf.am.user.vo.UserVO;
import com.hyjf.cs.user.client.AmUserClient;

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
	public int checkMobileCode(String mobile, String verificationCode, String verificationType, String platform,
			Integer searchStatus, Integer updateStatus) {
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(verificationCode);
		request.setVerificationType(verificationType);
		request.setPlatform(platform);
		request.setStatus(searchStatus);
		request.setUpdateStatus(updateStatus);

		Integer result = restTemplate
				.postForEntity("http://AM-USER/am-user/user/checkMobileCode/", request, Integer.class).getBody();
		if (result == null) {
			return 0;
		}
		return result;
	}

	/**
	 * 根据推荐人手机号或userId查询推荐人
	 */
	@Override
	public int countUserByRecommendName(String reffer) {
		UserResponse response = null;

		response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findUserByRecommendName/" + reffer, UserResponse.class)
				.getBody();
		if (response != null && response.getResult() != null) {
			return 1;
		}
		return 0;
	}

	@Override
	public UserVO register(RegisterUserRequest request) {
		UserResponse response = null;

		response = restTemplate.postForEntity("http://AM-USER/am-user/user/register", request, UserResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserVO findUserById(int userId) {
		UserResponse response = null;

		response = restTemplate.getForEntity("http://AM-USER/am-user/user/findById" + userId, UserResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform) {
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(checkCode);
		request.setVerificationType(validCodeType);
		request.setStatus(status);
		request.setPlatform(platform);
		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/sms/saveSmsCode", request, Integer.class)
				.getBody();
		if (result != null) {
			return 1;
		}
		return 0;
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
}
