package com.hyjf.cs.user.client.impl;

import java.io.UnsupportedEncodingException;

import com.hyjf.am.response.user.*;
import com.hyjf.am.vo.user.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.borrow.BankReturnCodeConfigResponse;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.resquest.user.BindEmailLogRequest;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.resquest.user.UsersContractRequest;
import com.hyjf.am.vo.borrow.BankReturnCodeConfigVO;
import com.hyjf.common.exception.ReturnMessageException;
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
				.getForEntity("http://AM-USER/am-user/user/findById/" + userId, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserInfoVO findUserInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
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
				.getForEntity("http://AM-CONFIG/am-config/config/getBankReturnCodeConfig/"+retCode,BankReturnCodeConfigResponse.class).getBody();
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
    public void updateUserAuthInves(BankRequest bean){
		 restTemplate.put("http://AM-USER/am-user/user/updateUserAuthInves", bean) ;
	}

	/**
	 * 根据userId修改
	 * @param user
	 * @return
	 */
	@Override
	public int updateUserById(UserVO user) {
		if(user == null || user.getUserId() == null){
			return 0;
		}
		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/user/updateByUserId", user, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
	}

	/**
	 * 修改登陆密码
	 * @param userId
	 * @param oldPW
	 * @param newPW
	 * @return
	 */
	@Override
	public JSONObject updatePassWd(Integer userId, String oldPW, String newPW) {
		logger.info("AmUserClient.updatePassWd run...userId is :{}, oldPW is :{}, newPW is :{}",userId,oldPW,newPW);
		JSONObject result = new JSONObject();
		if(userId == null || StringUtils.isBlank(oldPW) || StringUtils.isBlank(newPW)){
			result.put("status", "1");
			result.put("statusDesc", "请求参数非法");
			return result;
		}
		String url = "http://AM-USER/am-user/user/updatePassWd/" + userId + "/" + oldPW + "/" + newPW;
		logger.info("url:{}", url);
		ResponseEntity<JSONObject> resp = restTemplate.getForEntity(url, JSONObject.class);
		result = resp.getBody();
		if (result == null) {
			result.put("status", "1");
			result.put("statusDesc", "修改密码失败,未作任何操作");
		}
		return result;
	}

	@Override
	public HjhInstConfigVO selectInstConfigByInstCode(String instCode) {
		HjhInstConfigResponse response = restTemplate
				.getForEntity("http://AM-BORROW/am-borrow/borrow/selectInstConfigByInstCode/"+instCode, HjhInstConfigResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserInfoVO findUsersInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public AccountChinapnrVO getAccountChinapnr(Integer userId) {
		AccountChinapnrResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/getAccountChinapnr/" + userId, AccountChinapnrResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	
	/**
	 * 校验邮箱是否存在
	 */
	@Override
	public boolean checkEmailUsed(Integer userId) {
		boolean result = restTemplate
				.getForEntity("http://AM-USER/am-user/user/checkEmailUsed/" + userId, boolean.class).getBody();
		return result;
	}
	
	/**
	 * 插入绑卡记录
	 * @param bean
	 * @return
	 */
	@Override
	public void insertBindEmailLog(BindEmailLogRequest bean) {
		restTemplate.postForEntity("http://AM-USER/am-user/user/insertBindEmailLog/",  bean, int.class).getBody();
	}
	
	/**
	 * 获取绑定邮箱记录
	 * @param userId
	 * @return
	 */
	@Override
	public BindEmailLogVO getBindEmailLog(Integer userId) {
		// TODO: 微服务待实现
		BindEmailLogResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/getBindEmailLog/" + userId, BindEmailLogResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	
	/**
	 * 更新绑定邮箱
	 * @param bean
	 * @return
	 */
	@Override
	public void updateBindEmail(BindEmailLogRequest bean) {
		restTemplate.postForEntity("http://AM-USER/am-user/user/updateBindEmail/",  bean, int.class).getBody();
	}
	
	/**
	 * 保存、更新紧急联系人
	 * @param bean
	 * @return
	 */
	@Override
	public int updateUserContract(UsersContractRequest bean) {
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/user/updateUserContract/",  bean, int.class).getBody();
		return result;
	}

	/**
	 * @Author: zhangqingqing
	 * @Desc : 查询紧急联系人
	 * @Param: * @param userId
	 * @Date: 14:25 2018/6/4
	 * @Return: com.hyjf.am.vo.user.UsersContactVO
	 */
	@Override
	public UsersContactVO selectUserContact(Integer userId) {
		UsersContactResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/selectUserContact/" + userId, UsersContactResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}



}
