/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.cs.trade.client.StzhWhiteListClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangjun
 * @version StzhWhiteListClientImpl, v0.1 2018/7/2 17:43
 */
@Service
public class StzhWhiteListClientImpl implements StzhWhiteListClient {
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 获取受托支付电子账户列表
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    @Override
    public STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/select_stzf_white_list/" + instCode + "/" + entrustedAccountId;
        STZHWhiteListResponse response = restTemplate.getForEntity(url, STZHWhiteListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
