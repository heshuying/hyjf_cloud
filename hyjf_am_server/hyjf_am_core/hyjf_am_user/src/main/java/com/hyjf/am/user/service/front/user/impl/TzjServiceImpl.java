package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.service.front.user.TzjService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * @author xiasq
 * @version TzjServiceImpl, v0.1 2018/7/9 14:08
 */
@Service
public class TzjServiceImpl extends BaseServiceImpl implements TzjService {

	/**
	 * 查询当天注册人数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public int getRegistCount(Date startTime, Date endTime) {
		return tzjCustomizeMapper.getRegistCount(startTime, endTime);
	}

	/**
	 * 查询当天开户人数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public int getOpenCount(Date startTime, Date endTime) {
		return tzjCustomizeMapper.getOpenCount(startTime, endTime);
	}

	/**
	 * 查询当天绑卡人数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public int getCardBindCount(Date startTime, Date endTime) {
		return tzjCustomizeMapper.getCardBindCount(startTime, endTime);
	}

	/**
	 * 查询投之家当天注册用户
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Set<Integer> queryRegisterUsers(Date startTime, Date endTime) {
		return tzjCustomizeMapper.queryRegisterUsers(startTime, endTime);
	}

	/**
	 * 查询投之家所有注册用户
	 * 
	 * @return
	 */
	@Override
	public Set<Integer> queryAllTzjUsers() {
		return tzjCustomizeMapper.queryAllTzjUser();
	}
}
