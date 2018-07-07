/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AmDataCollectClient;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.vo.statistics.AccountWebListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangqingqing
 * @version AmDataCollect, v0.1 2018/6/25 10:26
 */
@Service
public class  AmDataCollectClientImpl  implements AmDataCollectClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${am.dataCollect.service.name}")
    private String dataCollectService;

    @Override
    public AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList) {
        AccountWebListResponse response = restTemplate
                .postForEntity(dataCollectService+"/search/queryAccountWebList", accountWebList, AccountWebListResponse.class)
                .getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public String selectBorrowInvestAccount(AccountWebListVO accountWebList) {
        String response = restTemplate
                .postForEntity(dataCollectService+"/search/selectBorrowInvestAccount", accountWebList, String.class)
                .getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
}
