package com.hyjf.cs.message.client.impl;

import java.util.List;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.vo.admin.AdminMsgPushCommonCustomizeVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.user.UserAliasResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.user.UserAliasVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.message.client.AmUserClient;

/**
 * @author xiasq
 * @version AmUserClientImpl, v0.1 2018/4/19 12:44
 */
@Service
public class AmUserClientImpl implements AmUserClient {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${am.user.service.name}")
	private String amUserServiceName;

	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobile
	 * @return
	 */
	@Override
	public UserVO findUserByMobile(final String mobile) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findByMobile/" + mobile, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据userId查询用户
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public UserVO findUserById(final int userId) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findById/" + userId, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据userId查询用户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoVO findUsersInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据手机号查询推送别名
	 * 
	 * @param mobile
	 * @return
	 */
	@Override
	public UserAliasVO findAliasByMobile(final String mobile) {
		UserAliasResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findAliasByMobile/" + mobile, UserAliasResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据手机号查询推送别名 - 批量
	 * 
	 * @param mobiles
	 * @return
	 */
	@Override
	public List<UserAliasVO> findAliasesByMobiles(final List<String> mobiles) {
		UserAliasResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findAliasesByMobiles/" + mobiles, UserAliasResponse.class)
				.getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据设备类型统计用户人数
	 * 
	 * @param clientAndroid
	 * @return
	 */
	@Override
	public int countAliasByClient(String clientAndroid) {
		UserAliasResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findSmsTemplateByCode/" + clientAndroid,
						UserAliasResponse.class)
				.getBody();
		if (response != null) {
			return response.getCount();
		}
		return 0;
	}
	/**
	 * 查看用户详情
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoCustomizeVO queryUserInfoCustomizeByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/userInfo/queryUserInfoCustomizeByUserId/" + userId;
		UserInfoCustomizeResponse response = restTemplate.getForEntity(url,UserInfoCustomizeResponse.class).getBody();
		if (response!=null){
			return response.getResult();
		}
		return null;
	}

	/**
	 * 通过手机号获取设备标识码
	 *
	 * @param mobile
	 * @return
	 */
	@Override
	public AdminMsgPushCommonCustomizeVO getMobileCodeByNumber(String mobile) {
		String url = "http://AM-USER/am-user/userInfo/getMobileCodeByNumber/" + mobile;
		AdminMsgPushCommonCustomizeVO response = restTemplate.getForEntity(url,AdminMsgPushCommonCustomizeVO.class).getBody();
		return response;
	}


	/**
	 * 获取用户表总记录数
	 *
	 * @return
	 */
	@Override
	public Integer countAllUser(){
		UserResponse response = restTemplate.getForEntity("http://AM-USER/am-user/user/countAll",UserResponse.class).getBody();
		if (!Response.isSuccess(response)) {
			return 0;
		}
		return response.getCount();
	}
}
