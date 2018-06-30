package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.trade.CreaditPageResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.vo.trade.CreditPageVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.cs.trade.client.AccountClient;
import com.hyjf.cs.trade.client.CreditClient;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CreditClientImpl implements CreditClient {


    @Autowired
    private RestTemplate restTemplate;

    /**
     * 判断用户所处的渠道如果不允许债转，可债转金额为0  start
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isAllowChannelAttorn(Integer userId) {
        // 根据userId获取用户注册推广渠道
        UtmPlatResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/user/selectUtmPlatByUserId/" + userId,UtmPlatResponse.class)
                .getBody();
        if (Response.isSuccess(response) && response.getResult().getAttornFlag()==0) {
            return false;
        }
        return true;
    }

    /**
     * 获取可债转金额   转让中本金   累计已转让本金
     *
     * @param userId
     * @return
     */
    @Override
    public CreditPageVO selectCreditPageMoneyTotal(Integer userId) {
        // 根据userId获取用户注册推广渠道
        CreaditPageResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/creditTender/select_credit_page_money_total/" + userId,CreaditPageResponse.class)
                .getBody();
        if (Response.isSuccess(response) ) {
            return response.getResult();
        }
        return null;
    }
}
