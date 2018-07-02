package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.am.vo.trade.account.AccountVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiasq
 * @version AccountServiceImpl, v0.1 2018/4/25 10:41
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl implements AccountService {

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

	@Override
	public Integer updateOfPlanRepayAccount(AccountVO accountVO) {
		Account account = new Account();
		BeanUtils.copyProperties(accountVO,account);
		boolean result =  batchHjhBorrowRepayCustomizeMapper.updateOfPlanRepayAccount(account) >0 ? true:false;
		return result?1:0;
	}

}
