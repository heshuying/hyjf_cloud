/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import java.util.List;

import com.hyjf.am.user.dao.model.auto.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.user.dao.model.auto.UserAlias;
import com.hyjf.am.user.dao.model.auto.UserAliasExample;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.service.UserAliasService;
import com.hyjf.am.vo.user.UserAliasVO;

/**
 * @author fuqiang
 * @version UserAliasServiceImpl, v0.1 2018/5/8 11:05
 */
@Service
public class UserAliasServiceImpl extends BaseServiceImpl implements UserAliasService {

	@Override
	public UserAlias findAliasByMobile(String mobile) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andMobileEqualTo(mobile);
		List<User> users = userMapper.selectByExample(userExample);
		if (CollectionUtils.isEmpty(users)) {
			return null;
		}
		return userAliasMapper.selectByPrimaryKey(users.get(0).getUserId());
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
		List<UserAlias> UserAliasList = userAliasMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(UserAliasList)) {
			return UserAliasList.size();
		}
		return 0;
	}

	@Override
	public void clearMobileCode(Integer userId, String sign) {
		UserAliasExample example = new UserAliasExample();
		example.createCriteria().andUserIdEqualTo(userId).andSignEqualTo(sign);
		userAliasMapper.deleteByExample(example);

	}

	@Override
	public UserAlias findAliasesByUserId(Integer userId) {
		return userAliasMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int updateAliases(UserAlias userAlias) {
		int cnt = userAliasMapper.updateByPrimaryKeySelective(userAlias);
		return cnt;
	}

	@Override
	public int insertMobileCode(UserAlias userAlias) {
		int cnt = userAliasMapper.insertSelective(userAlias);
		return cnt;
	}

}
