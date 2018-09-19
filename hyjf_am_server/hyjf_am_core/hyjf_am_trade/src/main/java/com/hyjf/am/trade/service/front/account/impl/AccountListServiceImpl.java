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
		ApiTransactionDetailsCustomize transactionDetailsCustomize = new ApiTransactionDetailsCustomize();

		//必传
		transactionDetailsCustomize.setStartDate(detailsRequest.getStartDate());
		//必传
		transactionDetailsCustomize.setEndDate(detailsRequest.getEndDate());
		//通过必传的phone
		transactionDetailsCustomize.setUserId(detailsRequest.getUserId());
		//必传
		transactionDetailsCustomize.setAccountId(detailsRequest.getAccountId());
		//必传
		transactionDetailsCustomize.setLimitStart(detailsRequest.getLimitStart());
		//必传
		transactionDetailsCustomize.setLimitEnd(detailsRequest.getLimitEnd());
		//选传
		transactionDetailsCustomize.setNid(detailsRequest.getNid());
		//默认为1 江西银行
		transactionDetailsCustomize.setIsBank("1");
		//选传 交易状态 :0失败 1成功
		transactionDetailsCustomize.setTradeStatus(detailsRequest.getTradeStatus());
		//选传 收支类型 :1收入 2支出 3冻结 4解冻
		transactionDetailsCustomize.setTypeSearch(detailsRequest.getTypeSearch());
		//选传 交易类型ID
		transactionDetailsCustomize.setTradeTypeSearch(detailsRequest.getTradeTypeSearch());

		List<ApiTransactionDetailsCustomize> accountInfos = this.apiTransactionDetailsCustomizeMapper.queryApiAccountDetails(transactionDetailsCustomize);
		return accountInfos;
	}
}
