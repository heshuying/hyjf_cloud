/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.StringResponse;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.user.client.AmDataCollectClient;

/**
 * @author zhangqingqing
 * @version AmDataCollect, v0.1 2018/6/25 10:26
 */
@Cilent
public class  AmDataCollectClientImpl  implements AmDataCollectClient{
    @Autowired
    private RestTemplate restTemplate;

    @Value("${am.dataCollect.service.name}")
    private String dataCollectService;
    
    /**
     * 根据userId查询用户渠道信息
     *
     * @param userId
     * @return
     */
    @Override
    public int isCompBindUser(Integer userId) {
        int response = restTemplate.getForEntity(
                dataCollectService+"/search/isCompBindUser/" + userId,
                Integer.class).getBody();
        return response;
    }

    @Override
    public String selectBankSmsSeq(Integer userId, String txcodeAutoBidAuthPlus) {
        String response = restTemplate.getForEntity(
                dataCollectService+"/search/selectBankSmsSeq/" + userId+"/"+txcodeAutoBidAuthPlus,
                String.class).getBody();
        return response;
    }

    @Override
    public String getRetCode(String logOrdId) {
        StringResponse response = restTemplate.getForEntity(dataCollectService+"/search/getRetCode/" + logOrdId,StringResponse.class).getBody();
        return response.getResultStr();
    }
}
