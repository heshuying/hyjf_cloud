package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.account.AccountListResponse;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.cs.trade.client.AccountListClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author pangchengchao
 * @version AmAccountListClientImpl, v0.1 2018/6/19 10:56
 */
@Service
public class AccountListClientImpl implements AccountListClient {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public AccountListVO selectAccountListByOrdId(String ordId, String type) {
        AccountListResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/accountList/selectAccountListByOrdId/" + ordId+"/"+type, AccountListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int countAccountListByOrdId(String ordId, String type) {
        AccountListResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/accountList/selectAccountListByOrdId/" + ordId+"/"+type, AccountListResponse.class).getBody();
        if (response != null  && response.getResult() != null) {
            return 1;
        }
        return 0;
    }

    @Override
    public int countAccountListByNidCoupon(String orderId) {
        String url = "http://AM-TRADE/am-trade/accountList/selectAccountListByNidCoupon/"+orderId;
        AccountListResponse response = restTemplate.getForEntity(url,AccountListResponse.class).getBody();
        if (response != null && response.getResult() != null) {
            return 1;
        }
        return 0;
    }

    @Override
    public Integer insertAccountListSelective(AccountListVO accountListVO) {
        Integer result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/accountList/insertAccountListSelective/", accountListVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;

    }

    @Override
    public int countByNidAndTrade(String nid, String trade) {
        String url = "http://AM-TRADE/am-trade/accountList/countbynidandtrade/"+nid+"/"+trade;
        AccountListResponse response = restTemplate.getForEntity(url,AccountListResponse.class).getBody();
        if (response != null && response.getResult() != null) {
            return response.getTotalRecord();
        }
        return 1;
    }


}
