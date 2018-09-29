package com.hyjf.cs.message.client;

import com.hyjf.am.vo.admin.AdminMsgPushCommonCustomizeVO;
import com.hyjf.am.vo.user.*;

import java.util.HashMap;
import java.util.List;

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

	/**
	 * 通过手机号获取设备标识码
	 *
	 * @param mobile
	 * @return
	 */
	AdminMsgPushCommonCustomizeVO getMobileCodeByNumber(String mobile);


	/**
	 * 获取用户表总记录数
	 *
	 * @return
	 */
	Integer countAllUser();

	/**
	 * 查询用户utm信息
	 * @param userId
	 * @return
	 */
    UtmRegVO findUtmRegByUserId(Integer userId);

	/**
	 * 检查用户是不是新手
	 * @param userId
	 * @return
	 */
	int countNewUserTotal(Integer userId);

	/**
	 * 更新用户首次投资信息
	 * @param params
	 */
	Integer updateFirstUtmReg(HashMap<String,Object> params);
}
