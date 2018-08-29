package com.hyjf.am.user.service.front.crm.impl;

import com.hyjf.am.user.dao.model.auto.ROaUsers;
import com.hyjf.am.user.dao.model.auto.ROaUsersExample;
import com.hyjf.am.user.service.front.crm.CrmUserService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description crm oa user 表同步
 * @Author sunss
 * @Date 2018/7/26 13:52
 */
@Service
public class CrmUserServiceImpl extends BaseServiceImpl implements CrmUserService {

	/**
	 * 修改
	 *
	 * @param users
	 * @return
	 */
	@Override
	public Integer update(ROaUsers users) {
		return rOaUsersMapper.updateByPrimaryKey(users);
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
	public Integer delete(Integer users) {
		return rOaUsersMapper.deleteByPrimaryKey(users);
	}

	/**
	 * 修改用户
	 *
	 * @param user
	 * @param example
	 */
	@Override
	public void updateByExample(ROaUsers user, ROaUsersExample example) {
		rOaUsersMapper.updateByExample(user, example);
	}
}
