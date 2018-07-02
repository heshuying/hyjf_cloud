package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.customize.BatchHjhBorrowRepayCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountListExample;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.service.AccountListService;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 收支明细
 * @author jijun
 * @date 2018/06/16
 */
@Service
public class AccountListServiceImpl extends BaseServiceImpl implements AccountListService {

	@Override
	public int addAccountList(AccountList accountList) {
		return accountListMapper.insertSelective(accountList);
	}

	@Override
	public AccountList countAccountListByOrdId(String ordId, String type) {
		AccountListExample accountListExample = new AccountListExample();
		accountListExample.createCriteria().andNidEqualTo(ordId).andTradeEqualTo("cash_success");
		List<AccountList> accountlist = this.accountListMapper.selectByExample(accountListExample);
		if(accountlist!=null&&accountlist.size()>0){
			return accountlist.get(0);
		}
		return null;
	}

	@Override
	public Integer insertAccountListSelective(AccountListVO accountListVO) {
		AccountList accountList = new AccountList();
		BeanUtils.copyProperties(accountListVO,accountList);
		boolean result =  this.accountListMapper.insertSelective(accountList) >0 ? true:false;
		return result?1:0;
	}

	@Override
	public Integer updateOfPlanRepayAccount(AccountVO accountVO) {
		Account account = new Account();
		BeanUtils.copyProperties(accountVO,account);
		boolean result =  this.batchHjhBorrowRepayCustomizeMapper.updateOfPlanRepayAccount(account) >0 ? true:false;
		return result?1:0;
	}

	@Override
	public AccountList countAccountListByOrderId(String orderId) {
		AccountListExample accountListExample = new AccountListExample();
		accountListExample.createCriteria().andNidEqualTo(orderId).andTradeEqualTo("increase_interest_profit");
		List<AccountList> accountlist = this.accountListMapper.selectByExample(accountListExample);
		if (!CollectionUtils.isEmpty(accountlist)) {
			return accountlist.get(0);
		}
		return null;
	}

	@Override
	public int insertAccountList(AccountList accountList) {
		int count = accountListMapper.insertSelective(accountList);
		if (count >= 0) {
			return count;
		}
		return 0;
	}


}
