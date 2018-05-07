package com.hyjf.cs.borrow.service.impl;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.borrow.client.RechargeClient;
import com.hyjf.cs.borrow.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl  implements BaseService {

	Logger _log = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Autowired
	RechargeClient rechargeClient;

	/***
	 * 获取用户在银行的开户信息
	 */
	@Override
	public  BankOpenAccountVO getBankOpenAccount(Integer userId) {
		BankOpenAccountVO bankAccount = this.rechargeClient.selectById(userId);
		return bankAccount;
	}
}
