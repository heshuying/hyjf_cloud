/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.auto.UserAliasMapper;
import com.hyjf.am.user.dao.mapper.auto.UserAliasMapper;
import com.hyjf.am.user.dao.mapper.customize.UserAliasCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.UserAlias;
import com.hyjf.am.user.dao.model.auto.UserAliasExample;
import com.hyjf.am.user.dao.model.auto.UserAlias;
import com.hyjf.am.user.service.UserAliasService;
import com.hyjf.am.vo.user.UserAliasVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version UserAliasServiceImpl, v0.1 2018/5/8 11:05
 */
@Service
public class UserAliasServiceImpl implements UserAliasService {

	@Autowired
	private UserAliasMapper UserAliasMapper;

	@Autowired
	private UserAliasCustomizeMapper userAliasCustomizeMapper;

	@Override
	public UserAlias findAliasByMobile(String mobile) {
		UserAliasExample example = new UserAliasExample();
		example.createCriteria().andAliasEqualTo(mobile);
		List<UserAlias> UserAliasList = UserAliasMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(UserAliasList)) {
			return UserAliasList.get(0);
		}
		return null;
	}

	@Override
	public List<UserAliasVO> findAliasByMobiles(List<String> mobiles) {
		List<UserAliasVO> userAliasVOList = userAliasCustomizeMapper.findAliasByMobiles(mobiles);
		return userAliasVOList;
	}

	@Override
	public Integer countAliasByClient(String clientAndroid) {
		UserAliasExample example = new UserAliasExample();
		example.createCriteria().andClientEqualTo(clientAndroid);
		List<UserAlias> UserAliasList = UserAliasMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(UserAliasList)) {
			return UserAliasList.size();
		}
		return 0;
	}
}
