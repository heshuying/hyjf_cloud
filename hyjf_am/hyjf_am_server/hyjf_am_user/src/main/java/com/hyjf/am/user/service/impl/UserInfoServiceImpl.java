package com.hyjf.am.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.user.dao.mapper.auto.UsersInfoMapper;
import com.hyjf.am.user.dao.model.auto.UsersInfo;
import com.hyjf.am.user.dao.model.auto.UsersInfoExample;
import com.hyjf.am.user.service.UserInfoService;

/**
 * @author xiasq
 * @version UserInfoServiceImpl, v0.1 2018/4/23 9:56
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UsersInfoMapper usersInfoMapper;

	@Override
	public UsersInfo findUserInfoById(int userId) {
		UsersInfoExample usersInfoExample = new UsersInfoExample();
		usersInfoExample.createCriteria().andUserIdEqualTo(userId);
		List<UsersInfo> usersList = usersInfoMapper.selectByExample(usersInfoExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}
}
