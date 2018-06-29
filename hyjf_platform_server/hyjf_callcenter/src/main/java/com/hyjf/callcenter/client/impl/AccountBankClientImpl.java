/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.client.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.client.AccountBankClient;

/**
 * @author wangjun
 * @version AccountBankClientImpl, v0.1 2018/6/6 13:53
 */
@Service
public class AccountBankClientImpl implements AccountBankClient {
    private static final Logger logger = LoggerFactory.getLogger(AccountBankClientImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<BankCardVO> getTiedCardOfAccountBank(UserVO user) {
        BankCardResponse bankCardResponse = restTemplate
                .getForEntity("http://AM-USER/am-user/callcenter/getTiedCardForBank/" + user.getUserId(), BankCardResponse.class)
                .getBody();
        if (bankCardResponse != null) {
            return bankCardResponse.getResultList();
        }
        return null;
    }
}
