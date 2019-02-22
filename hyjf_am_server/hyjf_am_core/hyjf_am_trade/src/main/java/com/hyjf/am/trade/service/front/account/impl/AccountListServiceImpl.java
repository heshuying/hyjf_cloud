package com.hyjf.am.trade.service.front.account.impl;

import com.hyjf.am.resquest.ApiTransactionDetailsRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.AccountListExample;
import com.hyjf.am.trade.dao.model.customize.ApiTransactionDetailsCustomize;
import com.hyjf.am.trade.service.front.account.AccountListService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
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
		if(accountlist!=null&&!accountlist.isEmpty()){
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

    @Override
    public Integer countByNidAndTrade(String nid, String trade) {
		AccountListExample accountListExample = new AccountListExample();
		accountListExample.createCriteria().andNidEqualTo(nid).andTradeEqualTo("increase_interest_profit");
		return this.accountListMapper.countByExample(accountListExample);
    }

	/**
	 * 第三方交易明细查询
	 * @param detailsRequest
	 * @return
	 * @Author : huanghui
	 */
	@Override
	public List<ApiTransactionDetailsCustomize> selectTransactionDetails(ApiTransactionDetailsRequest detailsRequest) {
		//默认为1 江西银行
		detailsRequest.setIsBank("1");

		List<ApiTransactionDetailsCustomize> accountInfos = this.apiTransactionDetailsCustomizeMapper.queryApiAccountDetails(detailsRequest);
		return accountInfos;
	}
}
