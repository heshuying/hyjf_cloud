package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.model.auto.ROaUsers;
import com.hyjf.am.user.service.ROaUserService;
import org.springframework.stereotype.Service;

/**
 * @Description crm oa user 表同步
 * @Author sunss
 * @Date 2018/7/26 13:52
 */
@Service
public class ROaUserServiceImpl extends BaseServiceImpl implements ROaUserService {


	/**
	 * 修改
	 *
	 * @param users
	 * @return
	 */
	@Override
	public Integer update(ROaUsers users) {
		return rOaUsersMapper.updateByPrimaryKeySelective(users);
	}

	/**
	 * 新增
	 *
	 * @param users
	 * @return
	 */
	@Override
	public Integer insert(ROaUsers users) {
		return rOaUsersMapper.insertSelective(users);
	}

	/**
	 * 删除
	 *
	 * @param users
	 * @return
	 */
	@Override
	public Integer delete(ROaUsers users) {
		return rOaUsersMapper.deleteByPrimaryKey(users.getId());
	}
}
