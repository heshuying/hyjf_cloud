package com.hyjf.cs.message.client;

import com.hyjf.am.vo.user.UserAliasVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
public interface AmUserClient {
	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	UserVO findUserByMobile(String mobile);

	/**
	 * 根据userId查询用户
	 * @param mobile
	 * @return
	 */
	UserVO findUserById(int userId);

	/**
	 * 根据手机号查询推送别名
	 * @param mobile
	 * @return
	 */
	UserAliasVO findAliasByMobile(String mobile);

	/**
	 * 根据手机号查询推送别名 - 批量
	 * @param mobile
	 * @return
	 */
	List<UserAliasVO> findAliasesByMobiles(List<String> mobiles);

	/**
	 * 根据设备类型统计用户人数
	 * @param clientAndroid
	 * @return
	 */
	int countAliasByClient(String clientAndroid);
}
