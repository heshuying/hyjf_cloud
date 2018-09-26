/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.locked.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hyjf.am.user.dao.mapper.auto.LockedUserInfoMapper;
import com.hyjf.am.user.dao.model.auto.LockedUserInfoExample;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.customize.LockedUserInfoCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.LockedUserInfo;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.service.admin.locked.LockedUserService;

/**
 * @author cui
 * @version LockedUserServiceImpl, v0.1 2018/9/25 11:08
 */
@Service
public class LockedUserServiceImpl implements LockedUserService {

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private LockedUserInfoMapper lockedUserInfoMapper;

	@Autowired
    private LockedUserInfoCustomizeMapper lockedUserInfoCustomizeMapper;

	@Override
	public int countRecordTotal(Map<String, Object> parameterMap) {
        return lockedUserInfoCustomizeMapper.countRecordTotal(parameterMap);
	}

	@Override
	public List<LockedUserInfo> getRecordList(Map<String, Object> parameterMap) {
		return lockedUserInfoCustomizeMapper.getRecordList(parameterMap);
	}

	@Override
	public Integer getLockedUserId(String username) {
		UserExample example1 = new UserExample();
		UserExample example2 = new UserExample();
		example1.createCriteria().andUsernameEqualTo(username);
		UserExample.Criteria c2 = example2.createCriteria().andMobileEqualTo(username);
		example1.or(c2);
		List<User> lstUser = userMapper.selectByExample(example1);
		Preconditions.checkArgument(lstUser.size() == 1);
		return lstUser.get(0).getUserId();
	}

	@Override
	public boolean unlock(LockedUserInfoVO infoVO, boolean isFront) {
		LockedUserInfo user = lockedUserInfoMapper.selectByPrimaryKey(infoVO.getId());

		if (user == null) {
			logger.warn("不存在ID=【{}】的锁定用户", infoVO.getId());
			return false;
		}

		String key = user.getUsername();

		if (isFront) {
			key = String.valueOf(getLockedUserId(user.getUsername()));
		}

		String redisKeyPrefix = isFront ? RedisConstants.PASSWORD_ERR_COUNT_ALL : RedisConstants.PASSWORD_ERR_COUNT_ADMIN;

		RedisUtils.del(redisKeyPrefix + key);

		user.setUnlocked(1);
		user.setUnlockTime(new Date());
		user.setOperator(infoVO.getOperator());

		LockedUserInfoExample example = new LockedUserInfoExample();

		example.or().andIdEqualTo(infoVO.getId());

		int result = lockedUserInfoMapper.updateByExample(user, example);

		String logInfo = isFront ? "解除前台锁定用户【{}】成功！更新记录个数【{}】" : "解除后台锁定用户【{}】成功！更新记录个数【{}】";
		logger.info(logInfo, user.getUsername(), result);

		return result>0;
	}
}
