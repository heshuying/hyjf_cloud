/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BankAccountManageClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BankAccountManageCustomizeResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageClientImpl, v0.1 2018/6/29 13:36
 */
@Service
public class BankAccountManageClientImpl implements BankAccountManageClient {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest) {
        String url = "http://AM-USER/am-user/bankAccountManage/queryAccountCount";
        Integer result = restTemplate.postForEntity(url,bankAccountManageRequest,Integer.class).getBody();
        return result;
    }

    @Override
    public List<BankAccountManageCustomizeVO> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest) {
        String url = "http://AM-USER/am-user/bankAccountManage/queryAccountInfos";
        BankAccountManageCustomizeResponse response = restTemplate.postForEntity(url,bankAccountManageRequest,BankAccountManageCustomizeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BankAccountManageCustomizeVO> queryAccountDetails(BankAccountManageRequest bankAccountManageRequest) {
        String url = "http://AM-USER/am-user/bankAccountManage/queryAccountDetails";
        BankAccountManageCustomizeResponse response = restTemplate.postForEntity(url,bankAccountManageRequest,BankAccountManageCustomizeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        String url = "http://AM-USER/am-user/bankaccountmanage/getbankopenaccount/" + userId;
        BankOpenAccountResponse response = restTemplate.getForEntity(url,BankOpenAccountResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public Integer updateAccount(AccountVO accountVO) {
        String url = "http://AM-TRADE/am-trade/bankAccountmanage/updateaccount";
        Integer result = restTemplate.postForEntity(url,accountVO,Integer.class).getBody();
        return result;
    }

    @Override
    public String updateAccountCheck(Integer userId, String startTime, String endTime) {
        AdminBankAccountCheckCustomizeVO adminBankAccountCheckCustomizeVO = new AdminBankAccountCheckCustomizeVO();
        adminBankAccountCheckCustomizeVO.setUserId(userId);
        adminBankAccountCheckCustomizeVO.setStartDate(startTime);
        adminBankAccountCheckCustomizeVO.setEndDate(endTime);
        String url = "http://AM-TRADE/am-trade/bankaccountmanage/updateAccountCheck/";
        String result = restTemplate.postForEntity(url,adminBankAccountCheckCustomizeVO,String.class).getBody();
        if (result != null) {
            return result;
        }
        return null;
    }
}