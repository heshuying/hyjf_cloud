package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.AccountRechargeResponse;
import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.trade.BankCardResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.trade.AccountRechargeVO;
import com.hyjf.am.vo.trade.AccountVO;
import com.hyjf.am.vo.trade.BankCardVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.trade.client.RechargeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RechargeClientImpl implements RechargeClient {
    private static Logger logger = LoggerFactory.getLogger(RechargeClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BankCardVO selectBankCardByUserId(Integer userId) {
        BankCardResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/selectByUserId/" + userId, BankCardResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public UserVO getUsers(Integer userId) {
        UserResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/user/findById/" + userId, UserResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BankOpenAccountVO selectById(int userId) {
        BankOpenAccountResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/selectById/" + userId, BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public UserInfoVO findUsersInfoById(int userId) {
        UserInfoResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BankCardVO getBankCardByCardNo(Integer userId, String cardNo){
        BankCardResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/getBankCardByCardNo/" + userId+"/"+cardNo, BankCardResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public AccountVO getAccount(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity("http://AM-BORROW/am-trade/recharge/getAccount/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public AccountRechargeVO selectByOrderId(String orderId) {
        AccountRechargeResponse response = restTemplate
                .getForEntity("http://AM-BORROW/am-trade/recharge/selectByOrderId/"+orderId,AccountRechargeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public void updateByPrimaryKeySelective(AccountRechargeVO accountRecharge) {
        restTemplate.put("http://AM-BORROW/am-trade/recharge/updateByPrimaryKeySelective",accountRecharge);
    }

    @Override
    public BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode) {
        BankReturnCodeConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/config/getBankReturnCodeConfig/"+retCode,BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


    @Override
    public int selectByOrdId(String ordId){
        Integer response = restTemplate
                .getForEntity("http://AM-BORROW/am-trade/recharge/selectByOrdId/"+ordId, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return -1;
    }

    @Override
    public int insertSelectiveBank(BankRequest bankRequest){
        Integer response = restTemplate
                .postForEntity("http://AM-BORROW/am-trade/recharge/insertSelectiveBank",bankRequest, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    @Override
    public boolean updateBanks(BankAccountBeanRequest bankAccountBeanRequest) {
        boolean response = restTemplate
                .postForEntity("http://AM-BORROW/am-trade/recharge/updateBanks",bankAccountBeanRequest, boolean.class).getBody();
        return response;
    }

}
