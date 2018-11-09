/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.cs.trade.client.CsMessageClient;

/**
 * @author yaoyong
 * @version CsMessageClientImpl, v0.1 2018/8/14 19:46
 */
@Service
public class CsMessageClientImpl implements CsMessageClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer insertAccountWebList(AccountWebListVO accountWebList) {
        String url = "http://CS-MESSAGE/cs-message/accountweblist/insertaccountweblist";
        AccountWebListResponse response = restTemplate.postForEntity(url,accountWebList,AccountWebListResponse.class).getBody();
        if (response != null) {
            return response.getRecordTotal();
        }
        return null;
    }
    /**
     * 根据userId查询用户渠道信息
     *
     * @param userId
     * @return
     */
    @Override
    public AppUtmRegVO getAppChannelStatisticsDetailByUserId(Integer userId) {
        AppUtmRegResponse response = restTemplate.getForEntity(
                "http://AM-USER/am-user/app_utm_reg/findByUserId/" + userId,
                AppUtmRegResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
