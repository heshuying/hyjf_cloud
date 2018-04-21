package com.hyjf.cs.user.client;

import com.hyjf.am.user.request.RegisterUserRequest;
import com.hyjf.am.user.vo.UserVO;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
public interface AmUserClient {
	UserVO findUserByMobile(String mobile);

	int updateCheckMobileCode(String mobile, String verificationCode, String verificationType, String platform,
			Integer searchStatus, Integer updateStatus);

	int countUserByRecommendName(String reffer);

	UserVO register(RegisterUserRequest request);

	UserVO getUserById(int userId);

	int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform);

}
