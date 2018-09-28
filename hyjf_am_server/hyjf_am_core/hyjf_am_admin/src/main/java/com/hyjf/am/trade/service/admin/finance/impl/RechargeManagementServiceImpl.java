package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AccountRechargeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.AccountRechargeExample;
import com.hyjf.am.trade.dao.model.customize.RechargeManagementCustomize;
import com.hyjf.am.trade.service.admin.finance.RechargeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 充值管理
 * @Author : huanghui
 */
@Service
public class RechargeManagementServiceImpl implements RechargeManagementService {

    @Autowired
    private AccountRechargeCustomizeMapper accountRechargeCustomizeMapper;

    @Autowired
    private AccountRechargeMapper accountRechargeMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AdminAccountCustomizeMapper adminAccountCustomizeMapper;

    @Autowired
    private AccountListMapper accountListMapper;

    /**
     * 充值列表总条数
     * @param request
     * @return
     * @Author : huanghui
     */
    @Override
    public Integer getAccountRechargeListCount(AccountRechargeRequest request){
        return this.accountRechargeCustomizeMapper.getAccountRechargeListCount(request);
    }

    /**
     * 充值列表
     * @param request
     * @return
     * @Author : huanghui
     */
    @Override
    public List<RechargeManagementCustomize> getAccountRechargeList(AccountRechargeRequest request) {
        List<RechargeManagementCustomize> rechargeVOList = accountRechargeCustomizeMapper.getAccountRechargeList(request);
        return rechargeVOList;
    }

    /**
     * 更新用户充值订单状态
     * @param userId
     * @param nid
     * @return
     * @Author : huanghui
     */
    @Override
    public boolean updateRechargeStatus(Integer userId, String nid) {
        AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
        AccountRechargeExample.Criteria criteria = accountRechargeExample.createCriteria();

        criteria.andUserIdEqualTo(userId);
        criteria.andNidEqualTo(nid);
        criteria.andStatusEqualTo(3);
        List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> rechargeList = accountRechargeMapper.selectByExample(accountRechargeExample);

        if (rechargeList != null && rechargeList.size() ==1){
            com.hyjf.am.trade.dao.model.auto.AccountRecharge recharge = rechargeList.get(0);
            // 将充值状态改为充值中
            recharge.setStatus(1);
            boolean isUpdateFlag = accountRechargeMapper.updateByPrimaryKeySelective(recharge) > 0 ? true : false;
            if (!isUpdateFlag){
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 充值掉单后,更新用户的账户信息
     * @param userId
     * @param nid
     * @return
     * @Author : huanghui
     */
    @Override
    public boolean updateAccountAfterRecharge(Integer userId, String nid) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date dNow = new Date();

        String nowDate = dateFormat.format(dNow);
        Date today = null;
        try {
            today = dateFormat.parse(nowDate);
        }catch (ParseException e){
            e.printStackTrace();
        }

        // 根据用户ID查询用户账户信息
        Account account = this.getAccountByUserId(userId);
        // 根据充值订单号查询用户充值信息
        com.hyjf.am.trade.dao.model.auto.AccountRecharge accountRecharge = this.getAccountRecharge(nid, userId);

        if (accountRecharge != null) {
            // 更新提现记录状态:更新为充值成功
            accountRecharge.setStatus(2);
            boolean isRechargeFlag = this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge) > 0 ? true : false;
//            if (!isRechargeFlag) {
//                throw new Exception("后台确认充值:更新用户充值记录失败~~~~! 用户ID:" + userId);
//            }
            // 更新用户账户信息
            Account newAccount = new Account();
            newAccount.setUserId(account.getUserId());
            newAccount.setBankTotal(accountRecharge.getBalance()); // 累加到账户总资产
            newAccount.setBankBalance(accountRecharge.getBalance()); // 累加可用余额
            newAccount.setBankBalanceCash(accountRecharge.getBalance());// 银行账户可用户
            boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount) > 0 ? true : false;
//            if (!isAccountUpdateFlag) {
//                throw new Exception("后台确认充值:更新用户账户信息失败~~~~! 用户ID:" + userId);
//            }
            // 插入交易明细
            // 重新获取用户账户信息
            account = this.getAccountByUserId(userId);
            // 生成交易明细
            AccountList accountList = new AccountList();
            accountList.setNid(accountRecharge.getNid());
            accountList.setUserId(userId);
            accountList.setAmount(accountRecharge.getBalance());
            accountList.setTxDate(accountRecharge.getTxDate());// 交易日期
            accountList.setTxTime(accountRecharge.getTxTime());// 交易时间
            accountList.setSeqNo(String.valueOf(accountRecharge.getSeqNo()));// 交易流水号
            accountList.setBankSeqNo(String.valueOf((accountRecharge.getTxDate() + accountRecharge.getTxTime() + accountRecharge.getSeqNo())));
            accountList.setType(1);
            accountList.setTrade("recharge");
            accountList.setTradeCode("balance");
            accountList.setAccountId(accountRecharge.getAccountId());
            accountList.setBankTotal(account.getBankTotal()); // 银行总资产
            accountList.setBankBalance(account.getBankBalance()); // 银行可用余额
            accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
            accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
            accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
            accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
            accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
            accountList.setBankAwait(account.getBankAwait());// 银行待收总额
            accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
            accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计投资
            accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
            accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
            accountList.setPlanFrost(account.getPlanFrost());
            accountList.setTotal(account.getTotal());
            accountList.setBalance(account.getBalance());
            accountList.setFrost(account.getFrost());
            accountList.setAwait(account.getAwait());
            accountList.setRepay(account.getRepay());
            accountList.setRemark("快捷充值");
            accountList.setCreateTime(new Date());
//            accountList.setBaseUpdate(nowTime);
            accountList.setOperator(String.valueOf(userId));
            accountList.setIp("");
//            accountList.setIsUpdate(0);
//            accountList.setBaseUpdate(0);
//            accountList.setInterest(null);
            accountList.setWeb(0);
            accountList.setIsBank(1);// 是否是银行的交易记录 0:否 ,1:是
            accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
            accountList.setTradeStatus(1);// 成功状态
            // 插入交易明细
            boolean isAccountListUpdateFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
//            if (!isAccountListUpdateFlag) {
//                throw new Exception("手动处理充值掉单,插入用户交易明细失败~~,用户ID:" + userId);
//            }
            return isAccountListUpdateFlag;
        }
        return false;
    }

    /**
     * 充值失败后,更新用户订单状态
     * @param userId
     * @param nid
     * @return
     * @Author : huanghui
     */
    @Override
    public boolean updateAccountAfterRechargeFail(Integer userId, String nid) {
        boolean isUpdateRechargeFalse = false;

        // 根据订单号,用户ID查询用户充值订单
        com.hyjf.am.trade.dao.model.auto.AccountRecharge accountRecharge = this.getAccountRecharge(nid, userId);
        if (accountRecharge != null){
            if (accountRecharge.getStatus() == 1){
                accountRecharge.setStatus(3);
                isUpdateRechargeFalse = this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge) > 0 ? true : false;
            }
        }
        return isUpdateRechargeFalse;
    }

    /**
     * 根据订单号查询用户充值记录
     *
     * @param nid
     * @return
     * @Author : huanghui
     */
    private com.hyjf.am.trade.dao.model.auto.AccountRecharge getAccountRecharge(String nid, Integer userId) {
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria cra = example.createCriteria();
        cra.andNidEqualTo(nid);
        cra.andUserIdEqualTo(userId);
        cra.andStatusEqualTo(1);
        List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> rechargeList = this.accountRechargeMapper.selectByExample(example);
        if (rechargeList != null && rechargeList.size() > 0) {
            return rechargeList.get(0);
        }
        return null;
    }

    private Account getAccountByUserId(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<Account> accountList = this.accountMapper.selectByExample(example);
        if (accountList != null && accountList.size() > 0) {
            return accountList.get(0);
        }
        return null;
    }
}
