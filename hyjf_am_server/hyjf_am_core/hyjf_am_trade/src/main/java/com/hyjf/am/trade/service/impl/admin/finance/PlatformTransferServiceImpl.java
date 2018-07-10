/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.finance;

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
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private AccountWebListMapper accountWebListMapper;

    @Autowired
    private BankMerchantAccountMapper bankMerchantAccountMapper;

    @Autowired
    private BankMerchantAccountListMapper bankMerchantAccountListMapper;

    /**
     * 根据筛选条件查询数据count
     * @auth sunpeikai
     * @param request
     * @return
     */
    @Override
    public Integer getPlatformTransferCount(PlatformTransferListRequest request) {
        AccountRechargeExample example = convertExample(request);
        Integer count = accountRechargeMapper.countByExample(example);
        return count;
    }

    /**
     * 根据筛选条件查询平台转账list
     * @auth sunpeikai
     * @param request
     * @return
     */
    @Override
    public List<AccountRecharge> searchPlatformTransferList(PlatformTransferListRequest request) {
        AccountRechargeExample example = convertExample(request);
        List<AccountRecharge> accountRechargeList = accountRechargeMapper.selectByExample(example);
        return accountRechargeList;
    }

    /**
     * 更新账户信息
     * @auth sunpeikai
     * @param accountVO 账户信息
     * @return
     */
    @Override
    public Integer updateAccount(AccountVO accountVO) {
        Account account = CommonUtils.convertBean(accountVO,Account.class);
        return accountMapper.updateByPrimaryKeySelective(account);
    }

    /**
     * 插入充值表记录
     * @auth sunpeikai
     * @param accountRechargeVO 充值表信息
     * @return
     */
    @Override
    public Integer insertAccountRecharge(AccountRechargeVO accountRechargeVO) {
        AccountRecharge accountRecharge = CommonUtils.convertBean(accountRechargeVO,AccountRecharge.class);
        return accountRechargeMapper.insertSelective(accountRecharge);
    }

    /**
     * 插入收支明细表记录
     * @auth sunpeikai
     * @param accountListVO 收支明细表信息
     * @return
     */
    @Override
    public Integer insertAccountList(AccountListVO accountListVO) {
        AccountList accountList = CommonUtils.convertBean(accountListVO,AccountList.class);
        return accountListMapper.insertSelective(accountList);
    }

    /**
     * 插入网站收支表记录
     * @auth sunpeikai
     * @param accountWebListVO 网站收支表信息
     * @return
     */
    @Override
    public Integer insertAccountWebList(AccountWebListVO accountWebListVO) {
        AccountWebList accountWebList = CommonUtils.convertBean(accountWebListVO,AccountWebList.class);
        return accountWebListMapper.insertSelective(accountWebList);
    }

    /**
     * 根据账户id查询BankMerchantAccount
     * @auth sunpeikai
     * @param accountId 账户id
     * @return
     */
    @Override
    public BankMerchantAccount searchBankMerchantAccountByAccountId(Integer accountId) {
        BankMerchantAccount bankMerchantAccount = bankMerchantAccountMapper.selectByPrimaryKey(accountId);
        return bankMerchantAccount;
    }

    /**
     * 更新红包账户信息
     * @auth sunpeikai
     * @param bankMerchantAccountVO 红包账户信息
     * @return
     */
    @Override
    public Integer updateBankMerchantAccount(BankMerchantAccountVO bankMerchantAccountVO) {
        BankMerchantAccount bankMerchantAccount = CommonUtils.convertBean(bankMerchantAccountVO,BankMerchantAccount.class);
        return bankMerchantAccountMapper.insertSelective(bankMerchantAccount);
    }

    /**
     * 插入红包明细数据
     * @auth sunpeikai
     * @param bankMerchantAccountListVO 红包明细
     * @return
     */
    @Override
    public Integer insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountListVO) {
        BankMerchantAccountList bankMerchantAccountList = CommonUtils.convertBean(bankMerchantAccountListVO,BankMerchantAccountList.class);
        return bankMerchantAccountListMapper.insertSelective(bankMerchantAccountList);
    }

    private AccountRechargeExample convertExample(PlatformTransferListRequest request){
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria criteria = example.createCriteria();
        // 用户名
        if(StringUtils.isNotEmpty(request.getUsernameSearch())){
            criteria.andUsernameLike("%"+request.getUsernameSearch()+"%");
        }
        // 订单号
        if(StringUtils.isNotEmpty(request.getNidSearch())){
            criteria.andNidLike("%"+request.getNidSearch()+"%");
        }
        // 转账状态
        if(StringUtils.isNotEmpty(request.getStatusSearch())){
            criteria.andStatusEqualTo(Integer.valueOf(request.getStatusSearch()));
        }
        // 添加时间开始
        if(StringUtils.isNotEmpty(request.getStartDate())){
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getStartDate())));
        }
        //添加时间结束
        if(StringUtils.isNotEmpty(request.getEndDate())){
            criteria.andCreateTimeLessThan(GetDate.stringToDate(GetDate.getDayEnd(request.getEndDate())));
        }
        example.setOrderByClause("create_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }

        return example;
    }
}
