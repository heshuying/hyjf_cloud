package com.hyjf.cs.user.service.myasset.impl;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.myasset.MyAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyAssetServiceImpl extends BaseUserServiceImpl implements MyAssetService{

	@Autowired
	private AmUserClient amUserClient;
	
	private AmTradeClient amTradeClient;
	
	@Override
	public UserInfoVO getUsersInfoByUserId(Integer userId) {
		return amUserClient.findUserInfoById(userId);
	}

	@Override
	public AccountVO getAccount(Integer userId) {
		return amTradeClient.getAccount(userId);
	}
	
	
	
}
