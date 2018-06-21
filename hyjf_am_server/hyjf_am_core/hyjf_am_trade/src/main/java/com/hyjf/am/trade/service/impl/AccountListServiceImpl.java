package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.model.auto.AccountListExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.service.AccountListService;

import java.util.List;

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


}