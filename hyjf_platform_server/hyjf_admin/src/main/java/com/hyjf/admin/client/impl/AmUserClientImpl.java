package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AmUserClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.user.AccountChinapnrResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmUserClientImpl, v0.1 2018/4/19 12:44
 */

@Service
public class AmUserClientImpl implements AmUserClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 根据userName查询user信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<UserVO> searchUserByUsername(String userName) {
		String url = "http://AM-USER/am-user/customertransfer/searchuserbyusername";
		UserResponse response = restTemplate
				.postForEntity(url, userName, UserResponse.class)
				.getBody();
		if(Response.isSuccess(response)){
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据userId查询accountChinapnr开户信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<AccountChinapnrVO> searchAccountChinapnrByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/customertransfer/searchaccountchinapnrbyuserid";
		AccountChinapnrResponse response = restTemplate
				.postForEntity(url, userId, AccountChinapnrResponse.class)
				.getBody();
		if(Response.isSuccess(response)){
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据userId查询用户信息
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public UserVO searchUserByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/customertransfer/searchuserbyuserid/" + userId;
		UserResponse response = restTemplate
				.getForEntity(url, UserResponse.class)
				.getBody();
		if(Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
	}

}
