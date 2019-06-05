package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AccountRechargeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.RechargeManagementCustomize;
import com.hyjf.am.trade.service.admin.finance.RechargeManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 充值管理
 * @Author : huanghui
 */
@Service
public class RechargeManagementServiceImpl implements RechargeManagementService {

    Logger logger = LoggerFactory.getLogger(RechargeManagementServiceImpl.class);

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

    // 充值状态:充值中
    private static final int RECHARGE_STATUS_WAIT = 1;
    // 充值状态:成功
    private static final int RECHARGE_STATUS_SUCCESS = 2;
    // 充值状态:失败
    private static final int RECHARGE_STATUS_FAIL = 3;

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
        criteria.andStatusEqualTo(RECHARGE_STATUS_FAIL);
        logger.info("修改充值状态:userId" + userId + ";Nid:" + nid + ";Status:" + RECHARGE_STATUS_FAIL);
        List<AccountRecharge> rechargeList = accountRechargeMapper.selectByExample(accountRechargeExample);
        logger.info("Size:" + rechargeList.size() + ";修改充值状态的充值信息:" + rechargeList.get(0).toString());
        if (rechargeList != null && rechargeList.size() ==1){
            AccountRecharge recharge = rechargeList.get(0);
            // 将充值状态改为充值中
            recharge.setStatus(RECHARGE_STATUS_WAIT);
            boolean isUpdateFlag = accountRechargeMapper.updateByPrimaryKeySelective(recharge) > 0 ? true : false;
            logger.info("充值记录状态修改为:" + RECHARGE_STATUS_WAIT + ";修改状态:" + isUpdateFlag);
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
    public boolean updateAccountAfterRecharge(Integer userId, String nid) throws Exception {


        // 根据用户ID查询用户账户信息
        Account account = this.getAccountByUserId(userId);
        // 根据充值订单号查询用户充值信息
        AccountRecharge accountRecharge = this.getAccountRecharge(nid, userId);
        logger.info("充值掉单后Fix时根据订单号查询到的订单为:" + accountRecharge);
        if (accountRecharge != null) {
            // 更新提现记录状态:更新为充值成功
            accountRecharge.setStatus(RECHARGE_STATUS_SUCCESS);
            boolean isRechargeFlag = this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge) > 0 ? true : false;
            if (!isRechargeFlag) {
                throw new Exception("后台确认充值:更新用户充值记录失败~~~~! 用户ID:" + userId);
            }

            // 更新用户账户信息
            Account newAccount = new Account();
            newAccount.setUserId(account.getUserId());
            newAccount.setBankTotal(accountRecharge.getBalance()); // 累加到账户总资产
            newAccount.setBankBalance(accountRecharge.getBalance()); // 累加可用余额
            newAccount.setBankBalanceCash(accountRecharge.getBalance());// 银行账户可用户
            boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount) > 0 ? true : false;
            if (!isAccountUpdateFlag) {
                throw new Exception("后台确认充值:更新用户账户信息失败~~~~! 用户ID:" + userId);
            }

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
            accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计出借
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
            logger.info("充值掉单处理后的状态为:" + isAccountListUpdateFlag + ";数据条件:" + accountList.toString());
            if (!isAccountListUpdateFlag) {
                throw new Exception("手动处理充值掉单,插入用户交易明细失败~~,用户ID:" + userId);
            }
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
        AccountRecharge accountRecharge = this.getAccountRecharge(nid, userId);
        logger.info("充值失败后Fix时根据订单号查询到的订单为:" + accountRecharge);
        if (accountRecharge != null){
            if (accountRecharge.getStatus() == RECHARGE_STATUS_WAIT){
                accountRecharge.setStatus(RECHARGE_STATUS_FAIL);
                isUpdateRechargeFalse = this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge) > 0 ? true : false;
            }
            logger.info("Fix 充值失败后状态由:" + RECHARGE_STATUS_WAIT + ";更新为:" + RECHARGE_STATUS_FAIL + ";更新装填为:" + isUpdateRechargeFalse + ";");
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
    private AccountRecharge getAccountRecharge(String nid, Integer userId) {
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria cra = example.createCriteria();
        cra.andNidEqualTo(nid);
        cra.andUserIdEqualTo(userId);
        cra.andStatusEqualTo(RECHARGE_STATUS_WAIT);
        List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> rechargeList = this.accountRechargeMapper.selectByExample(example);
        if (rechargeList != null && rechargeList.size() > 0) {
            return rechargeList.get(0);
        }
        return null;
    }

    @Override
    public Account getAccountByUserId(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<Account> accountList = this.accountMapper.selectByExample(example);
        if (accountList != null && accountList.size() > 0) {
            return accountList.get(0);
        }
        return null;
    }

    /**
     * 根据订单号nid获取充值信息
     *
     * @param nid
     * @return
     */
    @Override
    public AccountRecharge getAccountRechargeByNid(String nid) {

        AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
        AccountRechargeExample.Criteria aCriteria = accountRechargeExample.createCriteria();
        aCriteria.andNidEqualTo(nid);
        aCriteria.andStatusEqualTo(RECHARGE_STATUS_WAIT);
        List<AccountRecharge> accountRecharges = this.accountRechargeMapper.selectByExample(accountRechargeExample);
        if (accountRecharges != null && accountRecharges.size() == 1) {
            return accountRecharges.get(0);
        }
        return null;
    }

    /**
     * 根据用户Id查询用户第一笔充值记录
     *
     * @param userId
     * @return
     */
    @Override
    public AccountRecharge selectAccountRechargeByUserId(Integer userId) {
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andStatusEqualTo(2);
        example.setOrderByClause("createTime ASC");
        List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> list = this.accountRechargeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
}
