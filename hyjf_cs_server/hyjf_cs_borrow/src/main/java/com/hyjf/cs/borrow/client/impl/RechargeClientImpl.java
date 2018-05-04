package com.hyjf.cs.borrow.client.impl;

import com.hyjf.am.borrow.dao.model.auto.*;
import com.hyjf.am.response.borrow.*;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.user.dao.model.auto.Users;
import com.hyjf.am.user.dao.model.auto.UsersInfo;
import com.hyjf.am.vo.borrow.*;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.borrow.client.RechargeClient;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RechargeClientImpl implements RechargeClient {
    private static Logger logger = LoggerFactory.getLogger(RechargeClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BankCardVO selectBankCardByUserId(Integer userId) {
        BankCardResponse response = restTemplate
                .getForEntity("http://AM-BORROW/am-borrow/recharge/selectByUserId/" + userId, BankCardResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BanksConfigVO getBanksConfigByBankId(Integer bankId) {
        BanksConfigResponse response = restTemplate
                .getForEntity("http://AM-BORROW/am-borrow/recharge/getBanksConfigByBankId/" + bankId, BanksConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId) {
        CorpOpenAccountRecordResponse response = restTemplate
                .getForEntity("http://AM-BORROW/am-borrow/recharge/getCorpOpenAccountRecord/" + userId, CorpOpenAccountRecordResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public AccountVO getAccount(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity("http://AM-BORROW/am-borrow/recharge/getAccount/" + userId, AccountResponse.class).getBody();
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
    public int insertRechargeInfo(BankCallBean bean) {
        Integer response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/insertRechargeInfo/",bean, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    @Override
    public AccountRechargeVO selectByExample(AccountRechargeExample example) {
        AccountRechargeResponse response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/selectByExample",example,AccountRechargeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int updateByExampleSelective(AccountRechargeVO accountRecharge, AccountRechargeExample accountRechargeExample) {

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("accountRecharge",accountRecharge);
        paramMap.put("accountRechargeExample",accountRechargeExample);
        Integer response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/updateByExampleSelective/",paramMap, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    @Override
    public int updateBankRechargeSuccess(Account newAccount) {
        Integer response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/updateBankRechargeSuccess/",newAccount, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }

    @Override
    public int insertSelective(AccountList accountList) {
        Integer response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/insertSelective/",accountList, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }



    @Override
    public void updateByPrimaryKeySelective(AccountRechargeVO accountRecharge) {
        restTemplate.put("http://AM-BORROW/am-borrow/recharge/updateByPrimaryKeySelective",accountRecharge);
    }

    @Override
    public AccountVO selectByExample(AccountExample example) {
        AccountResponse response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/selectByExample",example,AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BankReturnCodeConfigVO getBankReturnCodeConfig(BankReturnCodeConfigExample example) {
        BankReturnCodeConfigResponse response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/getBankReturnCodeConfig",example,BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    public BankOpenAccountVO selectByExample(BankOpenAccountExample example) {
        BankOpenAccountResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/selectByExample/" + example, BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    public UserInfoVO findUsersInfoById(int userId) {
        UserInfoResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
