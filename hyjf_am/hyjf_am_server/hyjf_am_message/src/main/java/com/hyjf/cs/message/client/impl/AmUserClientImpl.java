package com.hyjf.cs.message.client.impl;

import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.am.vo.user.UserAliasVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.user.UserResponse;
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

	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
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
	 * 根据userId查询用户
	 * @param mobile
	 * @return
	 */
	@Override
	public UserVO findUserById(int userId) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findById" + userId, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据手机号查询推送别名
	 * @param mobile
	 * @return
	 */
	@Override
	public UserAliasVO findAliasByMobile(String mobile) {
		//todo xiashuqing
		return null;
	}

	/**
	 * 根据手机号查询推送别名 - 批量
	 * @param mobile
	 * @return
	 */
	@Override
	public List<UserAliasVO> findAliasesByMobiles(List<String> mobiles) {
		//todo xiashuqing
		return null;
	}

	/**
	 * 根据设备类型统计用户人数
	 * @param clientAndroid
	 * @return
	 */
	@Override
	public int countAliasByClient(String clientAndroid) {
		//todo xiashuqing
		return 0;
	}
}
