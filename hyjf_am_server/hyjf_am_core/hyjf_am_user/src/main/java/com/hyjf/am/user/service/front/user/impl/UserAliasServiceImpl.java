/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserAlias;
import com.hyjf.am.user.dao.model.auto.UserAliasExample;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.service.front.user.UserAliasService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.UserAliasVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
		UserAliasExample userAliasExample = new UserAliasExample();
		userAliasExample.createCriteria().andUserIdEqualTo(users.get(0).getUserId());
		List<UserAlias> list = userAliasMapper.selectByExample(userAliasExample);
		if (null!=list&&list.size()>0){
			return list.get(0);
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
		UserAliasExample userAliasExample = new UserAliasExample();
		userAliasExample.createCriteria().andUserIdEqualTo(userId);
		List<UserAlias> list = userAliasMapper.selectByExample(userAliasExample);
		if (null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
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
