/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client.impl;

import com.hyjf.cs.user.client.AmDataCollectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangqingqing
 * @version AmDataCollect, v0.1 2018/6/25 10:26
 */
@Service
public class  AmDataCollectClientImpl  implements AmDataCollectClient{
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据userId查询用户渠道信息
     *
     * @param userId
     * @return
     */
    @Override
    public int isCompBindUser(Integer userId) {
        int response = restTemplate.getForEntity(
                "http://AM-STATISTICS/am-statistics/seach/isCompBindUser/" + userId,
                Integer.class).getBody();
        return response;
    }

    @Override
    public String selectBankSmsSeq(Integer userId, String txcodeAutoBidAuthPlus) {
        String response = restTemplate.getForEntity(
                "http://AM-STATISTICS/am-statistics/seach/selectBankSmsSeq/" + userId+"/"+txcodeAutoBidAuthPlus,
                String.class).getBody();
        return response;
    }
}
