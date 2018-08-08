package com.hyjf.am.user.service.front.user;

import java.util.Date;
import java.util.Set;

/**
 * @author xiasq
 * @version TzjService, v0.1 2018/7/9 14:08
 */
public interface TzjService {

	/**
	 * 查询当天注册人数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int getRegistCount(Date startTime, Date endTime);

	/**
	 * 查询当天开户人数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int getOpenCount(Date startTime, Date endTime);

	/**
	 * 查询当天绑卡人数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int getCardBindCount(Date startTime, Date endTime);

	/**
	 * 查询投之家注册用户
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Set<Integer> queryRegisterUsers(Date startTime, Date endTime);

	/**
	 * 查询投之家所有注册用户
	 * @return
	 */
	Set<Integer> queryAllTzjUsers();
}
