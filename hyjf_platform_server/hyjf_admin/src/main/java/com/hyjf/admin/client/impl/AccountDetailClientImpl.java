/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AccountDetailClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.response.admin.AdminAccountDetailDataRepairResponse;
import com.hyjf.am.response.trade.AccountListResponse;
import com.hyjf.am.response.trade.AccountTradeResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.resquest.admin.AccountListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author nixiaoling
 * @version UserCenterClientImpl, v0.1 2018/6/20 15:38
 */
@Service
public class AccountDetailClientImpl implements AccountDetailClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查找资金明细列表
     *
     * @param request
     * @return
     */
    @Override
    public AccountDetailResponse findAccountDetailList(AccountDetailRequest request) {
        AccountDetailResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/adminaccountdetail/accountdetaillist", request, AccountDetailResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查询交易明细最小的id
     */
    @Override
    public AdminAccountDetailDataRepairResponse accountdetailDataRepair(int userId) {
        AdminAccountDetailDataRepairResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/adminaccountdetail/queryaccountdetailidbyuserid/" + userId, AdminAccountDetailDataRepairResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查询出20170120还款后,交易明细有问题的用户ID
     */
    @Override
    public AdminAccountDetailDataRepairResponse queryAccountDetailErrorUserList() {
        AdminAccountDetailDataRepairResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/adminaccountdetail/queryaccountdetailerroruserlist", AdminAccountDetailDataRepairResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    // 根据Id查询此条交易明细
    @Override
    public AccountListResponse selectAccountById(int accountId) {
        AccountListResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/adminaccountdetail/selectaccountbyid/" + accountId, AccountListResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    // 查询此用户的下一条交易明细
    @Override
    public AccountListResponse selectNextAccountList(int accountId, int userId) {
        AccountListResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/adminaccountdetail/selectnextaccountlist/" + userId + "/" + accountId, AccountListResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    // 根据查询用交易类型查询用户操作金额
    @Override
    public AccountTradeResponse selectAccountTradeByValue(String tradeValue) {
        AccountTradeResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/adminaccountdetail/selectaccounttradebyvalue/" + tradeValue, AccountTradeResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    // 更新用户的交易明细
    @Override
    public int updateAccountList(AccountListRequest accountListRequest) {
        int intUpdFlg = restTemplate.
                postForEntity("http://AM-TRADE/am-trade/adminaccountdetail/updateaccountlist", accountListRequest, Integer.class).
                getBody();
        return intUpdFlg;
    }
}
