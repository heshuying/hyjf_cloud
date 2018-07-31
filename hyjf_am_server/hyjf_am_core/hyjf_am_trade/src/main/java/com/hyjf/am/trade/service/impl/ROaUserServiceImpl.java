package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.AccountListExample;
import com.hyjf.am.trade.dao.model.auto.ROaUsers;
import com.hyjf.am.trade.service.AccountListService;
import com.hyjf.am.trade.service.ROaUserService;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
