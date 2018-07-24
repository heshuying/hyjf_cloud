/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.AccountDetailService;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.response.admin.AdminAccountDetailDataRepairResponse;
import com.hyjf.am.response.trade.AccountListResponse;
import com.hyjf.am.response.trade.AccountTradeResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.resquest.admin.AccountListRequest;
import com.hyjf.am.vo.trade.AccountTradeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class AccountDetailServiceImpl implements AccountDetailService {
    @Autowired
    private AmTradeClient accountDetailClient;

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
     * 查询出还款后,交易明细有问题的用户ID
     */
    @Override
    public AdminAccountDetailDataRepairResponse queryAccountDetailErrorUserList() {
        AdminAccountDetailDataRepairResponse accountResponse = accountDetailClient.queryAccountDetailErrorUserList();
        return accountResponse;
    }

    /**
     * 根据Id查询此条交易明细
     */
    @Override
    public AccountListResponse selectAccountById(int accountId) {
        return accountDetailClient.selectAccountById(accountId);
    }

    /**
     * 查询此用户的下一条交易明细
     * @param accountId
     * @param userId
     * @return
     */
    @Override
    public AccountListResponse selectNextAccountList(int accountId, int userId) {
        return accountDetailClient.selectNextAccountList(accountId, userId);
    }

    /**
     * 根据查询用交易类型查询用户操作金额
     * @param tradeValue
     * @return
     */
    @Override
    public AccountTradeResponse selectAccountTradeByValue(String tradeValue) {
        return accountDetailClient.selectAccountTradeByValue(tradeValue);
    }

    /**
     * 更新用户的交易明细
     * @param accountListRequest
     * @return
     */
    @Override
    public int updateAccountList(AccountListRequest accountListRequest) {
        return accountDetailClient.updateAccountList(accountListRequest);
    }


    /**
     * @Description 获取交易类型列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<AccountTradeVO> selectTradeTypes(){
        return accountDetailClient.selectTradeTypes();
    }
}
