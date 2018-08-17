/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.WeChatLandingPageResponse;
import com.hyjf.cs.user.client.AmDataCollectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * @author zhangqingqing
 * @version AmDataCollect, v0.1 2018/6/25 10:26
 */
@Service
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
    public BigDecimal selectInterestSum(){
        String url = "http://CS-MESSAGE/cs-message/wx/landingPage/selectInterestSum";
        WeChatLandingPageResponse response = restTemplate.getForEntity(url, WeChatLandingPageResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult().getProfitSum();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String getRetCode(String logOrdId) {
        String response = restTemplate.getForEntity(dataCollectService+"/search/getRetCode/" + logOrdId,String.class).getBody();
        return response;
    }
}
