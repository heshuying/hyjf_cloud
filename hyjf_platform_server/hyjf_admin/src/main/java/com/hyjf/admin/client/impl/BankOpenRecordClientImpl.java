/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BankOpenRecordClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankAccountRecordResponse;
import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.vo.user.BankOpenAccountRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClientImpl, v0.1 2018/6/20 15:38
 */
@Service
public class BankOpenRecordClientImpl implements BankOpenRecordClient {
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查找汇付银行开户记录列表
     * @author nixiaoling
     * @param request
     * @return
     */
    @Override
    public List<BankOpenAccountRecordVO> findAccountRecordList(AccountRecordRequest request) {
        BankAccountRecordResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/bankOpenAccountRecord/findAccountRecordList", request, BankAccountRecordResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }
    /**
     * 查找江西银行开户记录列表
     * @author nixiaoling
     * @param request
     * @return
     */
    @Override
    public List<BankOpenAccountRecordVO> findBankAccountRecordList(BankAccountRecordRequest request) {
        BankAccountRecordResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/bankOpenAccountRecord/findBankAccountRecordList", request, BankAccountRecordResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }
}
