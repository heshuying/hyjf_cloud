package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.response.trade.BankMerchantAccountListResponse;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.cs.trade.client.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountClientImpl implements AccountClient {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AccountVO getAccountByUserId(Integer userId) {
        AccountResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId ,AccountResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public Integer updateOfPlanRepayAccount(AccountVO accountVO) {
        Integer result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updateOfPlanRepayAccount/", accountVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public int updateOfRepayCouponHjh(AccountVO account) {
        AccountResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updateofrepaycouponhjh/", account,
                AccountResponse.class).getBody();
        if (result == null) {
            return result.getUpdateFlag();
        }
        return 0;
    }

    @Override
    public int countAccountWebList(String nid, String trade) {
        AccountWebListResponse result = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/account/countaccountweblist/"+ nid + "/" + trade,
                AccountWebListResponse.class).getBody();
        if (result == null) {
            return result.getRecordTotal();
        }
        return 0;
    }

    @Override
    public int insertAccountWebList(AccountWebListVO accountWebList) {
        AccountWebListResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/insertaccountweblist",accountWebList,
                AccountWebListResponse.class).getBody();
        if (result == null) {
            return result.getRecordTotal();
        }
        return 0;
    }

    @Override
    public BankMerchantAccountVO getBankMerchantAccount(String accountCode) {
        BankMerchantAccountResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/account/getbankmerchantaccount/"+accountCode,
                BankMerchantAccountResponse.class).getBody();
        if (response == null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int updateBankMerchatAccount(BankMerchantAccountVO account) {
        BankMerchantAccountResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updatebankmerchantaccount",account,
                BankMerchantAccountResponse.class).getBody();
        if (result == null) {
            return result.getRecordTotal();
        }
        return 0;
    }

    @Override
    public int insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountList) {
        BankMerchantAccountListResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/insertbankmerchantaccount",bankMerchantAccountList,
                BankMerchantAccountListResponse.class).getBody();
        if (result == null) {
            return result.getFlag();
        }
        return 0;
    }

    @Override
    public int updateOfRepayTender(AccountVO account) {
        AccountResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updateofrepaytender",account,
                AccountResponse.class).getBody();
        if (result == null) {
            return result.getUpdateFlag();
        }
        return 0;
    }

    @Override
    public int updateOfLoansTender(AccountVO account) {
        AccountResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updateofloanstender",account,
                AccountResponse.class).getBody();
        if (result == null) {
            return result.getUpdateFlag();
        }
        return 0;
    }
}
