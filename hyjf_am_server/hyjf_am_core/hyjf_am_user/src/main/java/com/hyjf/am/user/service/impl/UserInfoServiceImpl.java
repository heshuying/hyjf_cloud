package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.customize.UserCrmInfoCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.dao.model.customize.crm.UserCrmInfoCustomize;
import com.hyjf.am.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author xiasq
 * @version UserInfoServiceImpl, v0.1 2018/4/23 9:56
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserCrmInfoCustomizeMapper userCrmInfoCustomizeMapper;

	@Override
	public UserInfo findUserInfoById(int userId) {
		UserInfoExample UserInfoExample = new UserInfoExample();
		UserInfoExample.createCriteria().andUserIdEqualTo(userId);
		List<UserInfo> usersList = userInfoMapper.selectByExample(UserInfoExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}

	/**
	 * @param idNo
	 * @Description 根据身份证号查询用户信息
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/6 11:43
	 */
	@Override
	public UserInfo findUserInfoByIdNo(String idNo) {
		UserInfoExample UserInfoExample = new UserInfoExample();
		UserInfoExample.createCriteria().andIdcardEqualTo(idNo);
		List<UserInfo> usersList = userInfoMapper.selectByExample(UserInfoExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}

	@Override
	public UserInfo selectUserInfoByNameAndCard(String trueName, String idCrad) {
		UserInfoExample example = new UserInfoExample();
		UserInfoExample.Criteria crt = example.createCriteria();
		crt.andTruenameEqualTo(trueName);
		crt.andIdcardEqualTo(idCrad);
		List<UserInfo> list = userInfoMapper.selectByExample(example);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 根据用户ID查询crm信息
	 * @param userId
	 * @return
	 */
	@Override
	public UserCrmInfoCustomize findUserCrmInfoByUserId(Integer userId) {
		List<UserCrmInfoCustomize> list = userCrmInfoCustomizeMapper.findUserCrmInfoByUserId(userId);
		return null;
	}

}
