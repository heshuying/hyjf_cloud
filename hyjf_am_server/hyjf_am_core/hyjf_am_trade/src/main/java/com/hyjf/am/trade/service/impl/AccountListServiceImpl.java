package com.hyjf.am.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.service.AccountListService;

/**
 * 收支明细
 * @author jijun
 * @date 2018/06/16
 */
@Service
public class AccountListServiceImpl implements AccountListService {
	@Autowired
	private AccountListMapper accountListMapper;

	@Override
	public int addAccountList(AccountList accountList) {
		return accountListMapper.insertSelective(accountList);
	}
	


}
