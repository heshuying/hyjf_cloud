/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.common.exception.CheckException;
import com.hyjf.wbs.user.dao.mapper.auto.BankCardMapper;
import com.hyjf.wbs.user.dao.mapper.auto.UserMapper;
import com.hyjf.wbs.user.dao.model.auto.BankCard;
import com.hyjf.wbs.user.dao.model.auto.BankCardExample;
import com.hyjf.wbs.user.dao.model.auto.User;
import com.hyjf.wbs.user.service.UserService;

/**
 * @author cui
 * @version UserServiceImpl, v0.1 2019/4/25 17:09
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BankCardMapper bankCardMapper;

	@Override
	public User findUserById(Integer userIdd) {
		return userMapper.selectByPrimaryKey(userIdd);
	}

	@Override
	public BankCard selectBankCardByUserId(Integer userIdd) {
		BankCardExample example=new BankCardExample();
		example.or().andUserIdEqualTo(userIdd).andStatusEqualTo(1);
		List<BankCard> lstBankCard=bankCardMapper.selectByExample(example);
		if(lstBankCard.size()>0){
			return lstBankCard.get(0);
		}
		throw new CheckException("999","未找到userId=【"+userIdd+"】的开户卡信息");
	}
}
