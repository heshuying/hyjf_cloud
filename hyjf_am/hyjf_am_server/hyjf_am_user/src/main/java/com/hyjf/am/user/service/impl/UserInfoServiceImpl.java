package com.hyjf.am.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.service.UserInfoService;

/**
 * @author xiasq
 * @version UserInfoServiceImpl, v0.1 2018/4/23 9:56
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper UserInfoMapper;

	@Override
	public UserInfo findUserInfoById(int userId) {
		UserInfoExample UserInfoExample = new UserInfoExample();
		UserInfoExample.createCriteria().andUserIdEqualTo(userId);
		List<UserInfo> usersList = UserInfoMapper.selectByExample(UserInfoExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}
}
