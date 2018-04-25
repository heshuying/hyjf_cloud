package com.hyjf.am.borrow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.borrow.dao.mapper.auto.AccountMapper;
import com.hyjf.am.borrow.dao.model.auto.Account;
import com.hyjf.am.borrow.service.AccountService;

/**
 * @author xiasq
 * @version AccountServiceImpl, v0.1 2018/4/25 10:41
 */
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public void insert(Account account) {
		accountMapper.insert(account);
	}
}
