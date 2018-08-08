package com.hyjf.cs.message.client;

import java.util.List;

import com.hyjf.am.response.config.SmsNoticeConfigResponse;
import com.hyjf.am.response.user.UserAliasResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.hyjf.am.vo.user.UserAliasVO;
import com.hyjf.am.vo.user.UserVO;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
public interface AmUserClient {

	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobile
	 * @return
	 */
	UserVO findUserByMobile(final String mobile);

	/**
	 * 根据userId查询用户
	 * 
	 * @param userId
	 * @return
	 */
	UserVO findUserById(final int userId);
	/**
	 * 根据userId查询用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfoVO findUsersInfoById(int userId);

	/**
	 * 根据手机号查询推送别名
	 * 
	 * @param mobile
	 * @return
	 */
	UserAliasVO findAliasByMobile(final String mobile);

	/**
	 * 根据手机号查询推送别名 - 批量
	 * 
	 * @param mobiles
	 * @return
	 */
	List<UserAliasVO> findAliasesByMobiles(final List<String> mobiles);

	/**
	 * 根据设备类型统计用户人数
	 * 
	 * @param clientAndroid
	 * @return
	 */
	int countAliasByClient(String clientAndroid);

	/**
	 * 查看用户详情
	 * @param userId
	 * @return
	 */
	 UserInfoCustomizeVO queryUserInfoCustomizeByUserId(Integer userId);
}
