/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AccountDetailClient;
import com.hyjf.admin.service.AccountDetailService;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.response.admin.AdminAccountDetailDataRepairResponse;
import com.hyjf.am.response.trade.AccountListResponse;
import com.hyjf.am.response.trade.AccountTradeResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.resquest.admin.AccountListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class AccountDetailServiceImpl implements AccountDetailService {
    @Autowired
    private AccountDetailClient accountDetailClient;

    /**
     * 查找资金明细列表
     *
     * @param request
     * @return
     */

    @Override
    public AccountDetailResponse findAccountDetailList(AccountDetailRequest request) {
        AccountDetailResponse accountResponse = accountDetailClient.findAccountDetailList(request);
        return accountResponse;
    }

    /**
     * 查询交易明细最小的id
     */
    @Override
    public AdminAccountDetailDataRepairResponse accountdetailDataRepair(int userId) {
        AdminAccountDetailDataRepairResponse accountResponse = accountDetailClient.accountdetailDataRepair(userId);
        return accountResponse;
    }

    /**
     * 查询出20170120还款后,交易明细有问题的用户ID
     */
    @Override
    public AdminAccountDetailDataRepairResponse queryAccountDetailErrorUserList() {
        AdminAccountDetailDataRepairResponse accountResponse = accountDetailClient.queryAccountDetailErrorUserList();
        return accountResponse;
    }

    // 根据Id查询此条交易明细
    @Override
    public AccountListResponse selectAccountById(int accountId) {
        return accountDetailClient.selectAccountById(accountId);
    }

    // 查询此用户的下一条交易明细
    @Override
    public AccountListResponse selectNextAccountList(int accountId, int userId) {
        return accountDetailClient.selectNextAccountList(accountId, userId);
    }

    // 根据查询用交易类型查询用户操作金额
    @Override
    public AccountTradeResponse selectAccountTradeByValue(String tradeValue) {
        return accountDetailClient.selectAccountTradeByValue(tradeValue);
    }

    // 更新用户的交易明细
    @Override
    public int updateAccountList(AccountListRequest accountListRequest) {
        return accountDetailClient.updateAccountList(accountListRequest);
    }


}
