package com.hyjf.am.trade.service.front.account.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.account.AccountService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BankMerchantAccountInfoVO;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version AccountServiceImpl, v0.1 2018/4/25 10:41
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl implements AccountService {

    // 提现状态:提现中
    private static final int WITHDRAW_STATUS_WAIT = 1;
    // 提现状态:失败
    private static final int WITHDRAW_STATUS_FAIL = 3;
    // 提现状态:成功
    private static final int WITHDRAW_STATUS_SUCCESS = 2;

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

    @Override
    public BankMerchantAccountInfoVO getBankMerchantAccountInfo(String accountCode) {
        BankMerchantAccountInfoExample example=new BankMerchantAccountInfoExample();
        example.createCriteria().andAccountCodeEqualTo(accountCode);
        List<BankMerchantAccountInfo> bankMerchantAccountInfo = bankMerchantAccountInfoMapper.selectByExample(example);
        if (bankMerchantAccountInfo != null && bankMerchantAccountInfo.size() > 0) {
            BankMerchantAccountInfoVO bankMerchantAccountInfoVO = CommonUtils.convertBean(bankMerchantAccountInfo.get(0),BankMerchantAccountInfoVO.class);
            return bankMerchantAccountInfoVO;
        } else {
            return null;
        }
    }

    @Override
    public int updateBankMerchantAccountIsSetPassword(String accountId, Integer flag) {
        BankMerchantAccountExample example=new BankMerchantAccountExample();
        example.createCriteria().andAccountCodeEqualTo(accountId);
        BankMerchantAccount bankMerchantAccount=new BankMerchantAccount();
        bankMerchantAccount.setIsSetPassword(flag);
        int cnt = bankMerchantAccountMapper.updateByExampleSelective(bankMerchantAccount, example);
        return cnt;
    }

    @Override
    public int updateBankMerchantAccount(BankMerchantAccount bankMerchantAccount) {
        BankMerchantAccountExample example=new BankMerchantAccountExample();
        example.createCriteria().andAccountCodeEqualTo(bankMerchantAccount.getAccountCode());
        int cnt = bankMerchantAccountMapper.updateByExampleSelective(bankMerchantAccount, example);
        return cnt;
    }

    /**
     * 根据订单号查询提现订单
     * @param nid
     * @param userId
     * @return
     */
    @Override
    public AccountWithdraw queryAccountwithdrawByNid(String nid, Integer userId) {
        AccountWithdrawExample example = new AccountWithdrawExample();
        AccountWithdrawExample.Criteria cra = example.createCriteria();
        cra.andNidEqualTo(nid);
        cra.andUserIdEqualTo(userId);
        cra.andStatusEqualTo(WITHDRAW_STATUS_WAIT);
        List<AccountWithdraw> accountwithdrawList = accountWithdrawMapper.selectByExample(example);
        if (accountwithdrawList != null && accountwithdrawList.size() == 1) {
            return accountwithdrawList.get(0);
        }
        return null;
    }


    /**
     * 提现成功后,更新用户账户信息,提现记录
     * @param param
     * @return
     */
    @Override
    public boolean updateAccountAfterWithdraw(Map<String, String> param) {
        Date nowTime = GetDate.getDate();
        // 根据用户ID查询用户账户信息
        if (StringUtils.isEmpty(param.get("userId"))){
            logger.info("提现后,更新用户提现记录表失败!");
            return false;
        }
        Integer userId= Integer.valueOf(param.get("userId"));
        Account account = this.getAccountByUserId(userId);
        // 根据提现订单号查询用户充值信息
        String nid = param.get("nid");
        AccountWithdraw accountwithdraw = this.queryAccountwithdrawByNid(nid, userId);
        if (accountwithdraw != null) {
            // 如果信息未被处理
            if (accountwithdraw.getStatus() == WITHDRAW_STATUS_SUCCESS) {
                // 如果是已经提现成功了
                logger.info("提现订单已被处理:提现订单号:" + nid + ",用户ID:" + userId);
                return false;
            } else {
                // 查询是否已经处理过
                AccountListExample accountListExample = new AccountListExample();
                accountListExample.createCriteria().andNidEqualTo(nid).andTradeEqualTo("cash_success");
                int accountlistCnt = this.accountListMapper.countByExample(accountListExample);
                // 未被处理
                if (accountlistCnt == 0) {
                    try {
                        accountwithdraw.setStatus(WITHDRAW_STATUS_SUCCESS);// 4:成功
                        accountwithdraw.setUpdateTime(nowTime);
                        boolean isAccountwithdrawFlag = this.accountWithdrawMapper.updateByPrimaryKeySelective(accountwithdraw) > 0 ? true : false;
                        if (!isAccountwithdrawFlag) {
                            throw new Exception("提现后,更新用户提现记录表失败!");
                        }
                        Account newAccount = new Account();
                        // 更新账户信息
                        newAccount.setUserId(userId);// 用户Id
                        newAccount.setBankTotal(accountwithdraw.getTotal()); // 累加到账户总资产
                        newAccount.setBankBalance(accountwithdraw.getTotal()); // 累加可用余额
                        newAccount.setBankBalanceCash(accountwithdraw.getTotal());// 江西银行可用余额
                        boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankWithdrawSuccess(newAccount) > 0 ? true : false;
                        if (!isAccountUpdateFlag) {
                            throw new Exception("提现后,更新用户Account表失败!");
                        }
                        // 写入收支明细
                        AccountList accountList = new AccountList();
                        // 重新获取用户信息
                        account = this.getAccountByUserId(userId);
                        accountList.setNid(nid);
                        accountList.setUserId(userId);
                        accountList.setAmount(accountwithdraw.getTotal());
                        accountList.setType(2);
                        accountList.setTrade("cash_success");
                        accountList.setTradeCode("balance");
                        if(account.getTotal()==null) {
                        	accountList.setTotal(new BigDecimal(0));
                        }else {
                        	accountList.setTotal(account.getTotal());
                        }
                        accountList.setBalance(account.getBalance());
                        accountList.setFrost(account.getFrost());
                        accountList.setAwait(account.getAwait());
                        accountList.setRepay(account.getRepay());
                        accountList.setBankTotal(account.getBankTotal()); // 银行总资产
                        accountList.setBankBalance(account.getBankBalance()); // 银行可用余额
                        accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
                        accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
                        accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
                        accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
                        accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
                        accountList.setBankAwait(account.getBankAwait());// 银行待收总额
                        accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
                        accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计出借
                        accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
                        accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
                        accountList.setPlanFrost(account.getPlanFrost());
                        accountList.setSeqNo(String.valueOf(accountwithdraw.getSeqNo()));
                        accountList.setTxDate(accountwithdraw.getTxDate());
                        accountList.setTxTime(accountwithdraw.getTxTime());
                        accountList.setBankSeqNo(String.valueOf(accountwithdraw.getTxDate() + accountwithdraw.getTxTime() + accountwithdraw.getSeqNo()));
                        accountList.setAccountId(accountwithdraw.getAccountId());
                        accountList.setRemark("网站提现");
                        accountList.setCreateTime(nowTime);
                        accountList.setOperator(param.get("userName"));
                        accountList.setIp(param.get("ip"));
                        accountList.setIsBank(1);
                        accountList.setWeb(0);
                        accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
                        accountList.setTradeStatus(1);// 0失败1成功2失败
                        boolean isAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
                        if (!isAccountListFlag) {
                            throw new Exception("提现成功后,插入交易明细表失败~!");
                        }
                        return isAccountListFlag;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 充值失败,更新充值订单
     * @param userId
     * @param nid
     * @return
     */
    @Override
    public boolean updateAccountAfterWithdrawFail(Integer userId, String nid) throws Exception{
        // 根据提现订单号查询用户充值信息
        AccountWithdraw accountwithdraw = this.queryAccountwithdrawByNid(nid, userId);
        if (accountwithdraw != null) {
            if (accountwithdraw.getStatus() == WITHDRAW_STATUS_WAIT) {
                accountwithdraw.setStatus(WITHDRAW_STATUS_FAIL);
                boolean isAccountWithdrawFlag = this.accountWithdrawMapper.updateByPrimaryKeySelective(accountwithdraw) > 0 ? true : false;
                if (!isAccountWithdrawFlag) {
                    throw new Exception("提现失败后,手动确认,更新提现记录状态失败~~~!,提现订单号:" + nid + ",用户ID:" + userId);

                }
                return isAccountWithdrawFlag;
            }
        }
        return false;
    }


    /**
     * 获取账户信息
     * @param userId
     * @return
     */
    private Account getAccountByUserId(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<Account> accountList = this.accountMapper.selectByExample(example);
        if (accountList != null && accountList.size() > 0) {
            return accountList.get(0);
        }
        return new Account();
    }

}
