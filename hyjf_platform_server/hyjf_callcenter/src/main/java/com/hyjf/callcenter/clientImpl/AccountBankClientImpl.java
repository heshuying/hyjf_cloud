/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.clientImpl;

import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.callcenter.client.AccountBankClient;
import com.hyjf.ribbon.EurekaInvokeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjun
 * @version AccountBankClientImpl, v0.1 2018/6/6 13:53
 */
@Service
public class AccountBankClientImpl implements AccountBankClient {
    private static final Logger logger = LoggerFactory.getLogger(AmCallcenterBaseClientImpl.class);
    private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();
    @Override
    public List<BankCard> getTiedCardOfAccountBank(User user) {
        List<BankCard> bankCardList = restTemplate
                .getForEntity("http://AM-USER/am-user/callcenter/getTiedCardForBank/" + user.getUserId(), List.class)
                .getBody();
        if (bankCardList != null && bankCardList.size()>0) {
            return bankCardList;
        }
        return null;
    }
}
