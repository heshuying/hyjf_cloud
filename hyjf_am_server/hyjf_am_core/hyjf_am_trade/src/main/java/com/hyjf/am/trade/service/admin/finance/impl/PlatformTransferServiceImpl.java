/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.finance.PlatformTransferService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.bank.util.BankCallParamConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: PlatformTransferServiceImpl, v0.1 2018/7/9 11:11
 */
@Service
public class PlatformTransferServiceImpl extends BaseServiceImpl implements PlatformTransferService {

    @Autowired
    private AccountRechargeMapper accountRechargeMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountListMapper accountListMapper;

    @Autowired
    private BankMerchantAccountMapper bankMerchantAccountMapper;

    @Autowired
    private BankMerchantAccountListMapper bankMerchantAccountListMapper;

    /**
     * 根据筛选条件查询数据count
     *
     * @param request
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getPlatformTransferCount(PlatformTransferListRequest request) {
        AccountRechargeExample example = convertExample(request);
        Integer count = accountRechargeMapper.countByExample(example);
        return count;
    }

    /**
     * 根据筛选条件查询平台转账list
     *
     * @param request
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<AccountRecharge> searchPlatformTransferList(PlatformTransferListRequest request) {
        AccountRechargeExample example = convertExample(request);
        List<AccountRecharge> accountRechargeList = accountRechargeMapper.selectByExample(example);
        return accountRechargeList;
    }

    /**
     * 更新账户信息
     *
     * @param accountVO 账户信息
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateAccount(AccountVO accountVO) {
        Account account = CommonUtils.convertBean(accountVO, Account.class);
        return accountMapper.updateByPrimaryKeySelective(account);
    }

    /**
     * 插入充值表记录
     *
     * @param accountRechargeVO 充值表信息
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertAccountRecharge(AccountRechargeVO accountRechargeVO) {
        AccountRecharge accountRecharge = CommonUtils.convertBean(accountRechargeVO, AccountRecharge.class);
        return accountRechargeMapper.insertSelective(accountRecharge);
    }

    /**
     * 插入收支明细表记录
     *
     * @param accountListVO 收支明细表信息
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertAccountList(AccountListVO accountListVO) {
        AccountList accountList = CommonUtils.convertBean(accountListVO, AccountList.class);
        return accountListMapper.insertSelective(accountList);
    }

    /**
     * 根据账户id查询BankMerchantAccount
     *
     * @param accountId 账户id
     * @return
     * @auth sunpeikai
     */
    @Override
    public BankMerchantAccount searchBankMerchantAccountByAccountId(Integer accountId) {
        BankMerchantAccount bankMerchantAccount = bankMerchantAccountMapper.selectByPrimaryKey(accountId);
        return bankMerchantAccount;
    }

    /**
     * 更新红包账户信息
     *
     * @param bankMerchantAccountVO 红包账户信息
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateBankMerchantAccount(BankMerchantAccountVO bankMerchantAccountVO) {
        BankMerchantAccount bankMerchantAccount = CommonUtils.convertBean(bankMerchantAccountVO, BankMerchantAccount.class);
        return bankMerchantAccountMapper.insertSelective(bankMerchantAccount);
    }

    /**
     * 插入红包明细数据
     *
     * @param bankMerchantAccountListVO 红包明细
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountListVO) {
        BankMerchantAccountList bankMerchantAccountList = CommonUtils.convertBean(bankMerchantAccountListVO, BankMerchantAccountList.class);
        return bankMerchantAccountListMapper.insertSelective(bankMerchantAccountList);
    }

    private AccountRechargeExample convertExample(PlatformTransferListRequest request) {
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria criteria = example.createCriteria();
        // 用户名
        if (StringUtils.isNotEmpty(request.getUsernameSearch())) {
            criteria.andUsernameLike("%" + request.getUsernameSearch() + "%");
        }
        // 订单号
        if (StringUtils.isNotEmpty(request.getNidSearch())) {
            criteria.andNidLike("%" + request.getNidSearch() + "%");
        }
        // 转账状态
        if (StringUtils.isNotEmpty(request.getStatusSearch())) {
            criteria.andStatusEqualTo(Integer.valueOf(request.getStatusSearch()));
        }
        // 添加时间开始
        if (StringUtils.isNotEmpty(request.getStartDate())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getStartDate())));
        }
        //添加时间结束
        if (StringUtils.isNotEmpty(request.getEndDate())) {
            criteria.andCreateTimeLessThan(GetDate.stringToDate(GetDate.getDayEnd(request.getEndDate())));
        }
        example.setOrderByClause("create_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }

        return example;
    }

    @Override
    public Integer getBankMerchantAccountListCountByOrderId(String orderId) {
        BankMerchantAccountListExample accountWithdrawExample = new BankMerchantAccountListExample();
        accountWithdrawExample.createCriteria().andOrderIdEqualTo(orderId);
        List<BankMerchantAccountList> listAccountWithdraw = this.bankMerchantAccountListMapper.selectByExample(accountWithdrawExample);
        if (CollectionUtils.isEmpty(listAccountWithdraw)) {
            return 0;
        }
        return listAccountWithdraw.size();
    }


    @Override
    public Boolean updateAccountByRechargeCallback(Map<String, Object> params) {
        Integer userId = (Integer) params.get("userId");
        String orderId = (String) params.get(BankCallParamConstant.PARAM_LOGORDERID);
        String accountId = (String) params.get(BankCallParamConstant.PARAM_ACCOUNTID);
        String txAmount = (String) params.get(BankCallParamConstant.PARAM_TXAMOUNT);

        BankMerchantAccountListExample accountWithdrawExample = new BankMerchantAccountListExample();
        accountWithdrawExample.createCriteria().andOrderIdEqualTo(orderId);
        List<BankMerchantAccountList> listAccountWithdraw = this.bankMerchantAccountListMapper.selectByExample(accountWithdrawExample);

        if (listAccountWithdraw != null && listAccountWithdraw.size() > 0) {
            // 提现信息
            BankMerchantAccountList accountWithdraw = listAccountWithdraw.get(0);
            // 如果信息未被处理
            if (CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS.equals(accountWithdraw.getStatus())) {
                return Boolean.TRUE;
            } else {
                BankMerchantAccountExample bankMerchantAccountExample = new BankMerchantAccountExample();
                BankMerchantAccountExample.Criteria bankMerchantAccountCriteria = bankMerchantAccountExample.createCriteria();
                bankMerchantAccountCriteria.andAccountCodeEqualTo(accountId);
                BankMerchantAccount bankMerchantAccount = this.bankMerchantAccountMapper.selectByExample(bankMerchantAccountExample).get(0);


                // 提现金额
                BigDecimal transAmt = new BigDecimal(txAmount);

                // 更新账户信息
                bankMerchantAccount.setAccountBalance(bankMerchantAccount.getAccountBalance().add(transAmt));
                bankMerchantAccount.setAvailableBalance(bankMerchantAccount.getAvailableBalance().add(transAmt));
                bankMerchantAccountCriteria.andUpdateTimeEqualTo(bankMerchantAccount.getUpdateTime());
                bankMerchantAccount.setUpdateTime(new Date());
                boolean isAccountUpdateFlag = this.bankMerchantAccountMapper.updateByExampleSelective(bankMerchantAccount, bankMerchantAccountExample) > 0 ? true : false;

                if (!isAccountUpdateFlag) {
                    throw new RuntimeException("圈存成功后,插入交易明细表失败~!, 操作回滚");
                }

                BankMerchantAccountList bankMerchantAccountList = new BankMerchantAccountList();
                bankMerchantAccountList.setId(accountWithdraw.getId());
                bankMerchantAccountList.setOrderId(orderId);
                bankMerchantAccountList.setBankAccountBalance(bankMerchantAccount.getAvailableBalance());
                bankMerchantAccountList.setBankAccountFrost(bankMerchantAccount.getFrost());
                bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS);
                bankMerchantAccountList.setUpdateUserId(userId);
                bankMerchantAccountList.setUpdateTime(new Date());
                boolean isBankMerchantAccountListFlag = bankMerchantAccountListMapper.updateByPrimaryKeySelective(bankMerchantAccountList) > 0 ? true : false;
                if (!isBankMerchantAccountListFlag) {
                    throw new RuntimeException("圈存成功后，查询红包明细表失败,操作回滚");
                }
                return Boolean.TRUE;
            }
        }
        return false;
    }
}
