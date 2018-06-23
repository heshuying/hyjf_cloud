package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.cs.trade.client.BankInterfaceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author pangchengchao
 * @version BankInterfaceClientImpl, v0.1 2018/6/22 14:07
 */
@Service
public class BankInterfaceClientImpl implements BankInterfaceClient {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Integer getBankInterfaceFlagByType(String type) {
        BankInterfaceResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/bankInterface/getBankInterfaceFlagByType/" + type, BankInterfaceResponse.class).getBody();
        if (response != null) {
            return response.getFlag();
        }
        return null;
    }
}
