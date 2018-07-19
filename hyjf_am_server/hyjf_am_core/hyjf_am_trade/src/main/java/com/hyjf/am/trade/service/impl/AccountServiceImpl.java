package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.common.util.CommonUtils;
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

    /**
     * userIds范围查询
     * @param userIds
     * @return
     */
    @Override
    public List<Account> getAccountByUserIds(List<Integer> userIds) {
        AccountExample example =  new AccountExample();
        AccountExample.Criteria cra = example.createCriteria();
        cra.andUserIdIn(userIds);
        return accountMapper.selectByExample(example);
    }
    @Override
    public int updateOfRepayCouponHjh(AccountVO accountVO) {
        return adminAccountCustomizeMapper.updateOfRepayCouponHjh(CommonUtils.convertBean(accountVO, Account.class));
    }

    @Override
    public Integer countAccountWebList(String nid, String trade) {
        AccountWebListExample example = new AccountWebListExample();
        example.createCriteria().andOrdidEqualTo(nid).andTradeEqualTo(trade);
        return this.accountWebListMapper.countByExample(example);
    }

    @Override
    public Integer insertAccountWebList(AccountWebListVO accountWebList) {
        return accountWebListMapper.insertSelective(CommonUtils.convertBean(accountWebList,AccountWebList.class));
    }

    @Override
    public BankMerchantAccountVO getBankMerchantAccount(String accountCode) {
        BankMerchantAccountVO bankMerchantAccountVO = null;
        BankMerchantAccountExample bankMerchantAccountExample = new BankMerchantAccountExample();
        bankMerchantAccountExample.createCriteria().andAccountCodeEqualTo(accountCode);
        List<BankMerchantAccount> bankMerchantAccounts = bankMerchantAccountMapper.selectByExample(bankMerchantAccountExample);
        if (bankMerchantAccounts != null && bankMerchantAccounts.size() != 0) {
            bankMerchantAccountVO = CommonUtils.convertBean(bankMerchantAccounts.get(0),BankMerchantAccountVO.class);
            return bankMerchantAccountVO;
        } else {
            return null;
        }
    }

    @Override
    public Integer updateBankMerchatAccount(BankMerchantAccountVO bankMerchantAccountVO) {
        return bankMerchantAccountMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(bankMerchantAccountVO,BankMerchantAccount.class));
    }

    @Override
    public Integer insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountList) {
        return bankMerchantAccountListMapper.insertSelective(CommonUtils.convertBean(bankMerchantAccountList,BankMerchantAccountList.class));
    }

    @Override
    public int updateOfRepayTender(AccountVO accountVO) {
        return adminAccountCustomizeMapper.updateOfRepayTender(CommonUtils.convertBean(accountVO,Account.class));
    }

    @Override
    public int updateOfLoansTender(AccountVO accountVO) {
        return adminAccountCustomizeMapper.updateOfLoansTender(CommonUtils.convertBean(accountVO,Account.class));
    }

}
