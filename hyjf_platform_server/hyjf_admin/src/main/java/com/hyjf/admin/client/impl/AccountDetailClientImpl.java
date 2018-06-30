/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AccountDetailClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.response.user.RegistRecordResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.vo.admin.AccountDetailVO;
import com.hyjf.am.vo.user.RegistRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClientImpl, v0.1 2018/6/20 15:38
 */
@Service
public class AccountDetailClientImpl implements AccountDetailClient {
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查找资金明细列表
     *
     * @param request
     * @return
     */
    @Override
    public AccountDetailResponse findAccountDetailList(AccountDetailRequest request) {
        AccountDetailResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/adminAccountDetail/accountDetailList", request, AccountDetailResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }
}
