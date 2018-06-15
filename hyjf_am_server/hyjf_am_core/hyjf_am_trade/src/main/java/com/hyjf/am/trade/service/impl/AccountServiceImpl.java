package com.hyjf.am.trade.service.impl;

import java.util.List;

import com.hyjf.am.trade.dao.mapper.customize.admin.AdminAccountCustomizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.service.AccountService;

/**
 * @author xiasq
 * @version AccountServiceImpl, v0.1 2018/4/25 10:41
 */
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AdminAccountCustomizeMapper adminAccountCustomizeMapper;

	@Override
	public void insert(Account account) {
		accountMapper.insert(account);
	}

	/**
	 * 获取用户的账户信息
	 *
	 * @param userId
	 * @return 获取用户的账户信息
	 */
	@Override
    public Account getAccount(Integer userId) {
		AccountExample example = new AccountExample();
		AccountExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<Account> listAccount = this.accountMapper.selectByExample(example);
		if (listAccount != null && listAccount.size() > 0) {
			return listAccount.get(0);
		}
		return null;
	}

	@Override
	public int updateOfRTBLoansTender(Account account) {
		return adminAccountCustomizeMapper.updateOfRTBLoansTender(account);
	}

}
