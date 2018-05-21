package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.borrow.BankReturnCodeConfigResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.vo.borrow.BankReturnCodeConfigVO;
import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.client.AmUserClient;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

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

	/**
	 * 根据推荐人手机号或userId查询推荐人
	 */
	@Override
	public int countUserByRecommendName(String reffer) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findReffer/" + reffer, UserResponse.class).getBody();
		if (response != null && response.getResult() != null) {
			return 1;
		}
		return 0;
	}

	@Override
	public UserVO register(RegisterUserRequest request) {
		UserResponse response = restTemplate
				.postForEntity("http://AM-USER/am-user/user/register", request, UserResponse.class).getBody();
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
	public int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform) {
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(checkCode);
		request.setVerificationType(validCodeType);
		request.setStatus(status);
		request.setPlatform(platform);
		SmsCodeResponse response = restTemplate
				.postForEntity("http://AM-USER/am-user/smsCode/save", request, SmsCodeResponse.class).getBody();
		if (response != null) {
			return response.getCnt();
		}
		return 0;
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

		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/smsCode/check/", request, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
	}

	@Override
	public UserVO findUserByUserNameOrMobile(String loginUserName) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findByCondition/" + loginUserName, UserResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public void updateLoginUser(int userId, String ip) {
		/**
		 * ip version等作为请求一部分的时候，用base64转码
		 */
		String args = "";
		try {
			args = new String(Base64.encodeBase64(ip.getBytes()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new ReturnMessageException(Response.FAIL_MSG);
		}
		String url = "http://AM-USER/am-user/user/updateLoginUser/" + userId + "/" + args;
		logger.info("url:{}", url);
		restTemplate.getForEntity(url, String.class);
	}


	@Override
	public HjhUserAuthVO getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/getHjhUserAuthByUserId/"+userId, HjhUserAuthResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode) {
		BankReturnCodeConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/recharge/getBankReturnCodeConfig/"+retCode,BankReturnCodeConfigResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public void insertUserAuthLog(HjhUserAuthLogVO hjhUserAuthLog) {
		restTemplate.put("http://AM-USER/am-user/user/insertLogSelective",hjhUserAuthLog);
	}

	@Override
	public HjhUserAuthLogVO selectByExample(String orderId) {
		HjhUserAuthLogResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/selectByExample/"+orderId, HjhUserAuthLogResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(HjhUserAuthLogVO record){
		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/user/updateLogByPrimaryKeySelective", record, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
	}

	@Override
	public int insertSelective(HjhUserAuthVO record){
		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/user/insertSelective", record, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
	}

	@Override
	public int updateByPrimaryKeySelective(HjhUserAuthVO record){
		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/user/updateByPrimaryKeySelective", record, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
	}

}
