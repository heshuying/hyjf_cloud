package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.AccountRechargeResponse;
import com.hyjf.am.response.trade.AccountwithdrawResponse;
import com.hyjf.am.resquest.user.BankWithdrawBeanRequest;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.WithdrawClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version WithdrawClientImpl, v0.1 2018/6/13 11:18
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
@Service
public class WithdrawClientImpl implements WithdrawClient {
    private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<AccountWithdrawVO> selectAccountWithdrawByOrdId(String ordId) {
        AccountwithdrawResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/accountWithdraw/findByOrdId/" + ordId, AccountwithdrawResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public void insertAccountWithdrawLog(AccountWithdrawVO record) {
        restTemplate.put("http://AM-TRADE/am-trade/accountWithdraw/insertAccountWithdrawLog",record);
    }

    @Override
    public AccountWithdrawVO getAccountWithdrawByOrdId(String logOrderId) {
        AccountwithdrawResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/accountWithdraw/getAccountWithdrawByOrdId/" + logOrderId, AccountwithdrawResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


    @Override
    public int updateAccountwithdrawLog(AccountWithdrawVO accountwithdraw) {
        int result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/accountWithdraw/updateAccountwithdrawLog", accountwithdraw, Integer.class).getBody();
        return result;
    }

    @Override
    public int updatUserBankWithdrawHandler(BankWithdrawBeanRequest bankWithdrawBeanRequest) {
        int result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/accountWithdraw/updatUserBankWithdrawHandler", bankWithdrawBeanRequest, Integer.class).getBody();
        return result;
    }

    @Override
    public int getBorrowTender(Integer userId) {
        String url = "http://AM-TRADE/am-trade/accountWithdraw/getBorrowTender/"+userId;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    @Override
    public List<AccountRechargeVO> getTodayRecharge(Integer userId) {
        AccountRechargeResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/accountWithdraw/getTodayRecharge/" + userId, AccountRechargeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
