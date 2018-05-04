package com.hyjf.cs.borrow.client.impl;

import com.hyjf.am.borrow.dao.model.auto.*;
import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.user.dao.model.auto.Users;
import com.hyjf.am.user.dao.model.auto.UsersInfo;
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
    public BankCard selectBankCardByUserId(Integer userId) {
        BankCard response = restTemplate
                .getForEntity("http://AM-BORROW/am-borrow/recharge/selectByUserId/" + userId, BankCard.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public BanksConfig getBanksConfigByBankId(Integer bankId) {
        BanksConfig response = restTemplate
                .getForEntity("http://AM-BORROW/am-borrow/recharge/getBanksConfigByBankId/" + bankId, BanksConfig.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CorpOpenAccountRecord getCorpOpenAccountRecord(Integer userId) {
        CorpOpenAccountRecord response = restTemplate
                .getForEntity("http://AM-BORROW/am-borrow/recharge/getCorpOpenAccountRecord/" + userId, CorpOpenAccountRecord.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public Account getAccount(Integer userId) {
        Account response = restTemplate
                .getForEntity("http://AM-BORROW/am-borrow/recharge/getAccount/" + userId, Account.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public Users getUsers(Integer userId) {
        Users response = restTemplate
                .getForEntity("http://AM-USER/am-user/user/findById/" + userId, Users.class).getBody();
        if (response != null) {
            return response;
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
    public AccountRecharge selectByExample(AccountRechargeExample example) {
        AccountRecharge response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/selectByExample",example,AccountRecharge.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public int updateByExampleSelective(AccountRecharge accountRecharge, AccountRechargeExample accountRechargeExample) {

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
    public Users selectByPrimaryKey(Integer userId) {
        Users response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/selectByPrimaryKey",userId,Users.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public void updateByPrimaryKeySelective(AccountRecharge accountRecharge) {
        restTemplate.put("http://AM-BORROW/am-borrow/recharge/updateByPrimaryKeySelective",accountRecharge);
    }

    @Override
    public Account selectByExample(AccountExample example) {
        Account response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/selectByExample",example,Account.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public BankReturnCodeConfig getBankReturnCodeConfig(BankReturnCodeConfigExample example) {
       BankReturnCodeConfig response = restTemplate
                .postForEntity("http://AM-BORROW/am-borrow/recharge/getBankReturnCodeConfig",example,BankReturnCodeConfig.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    public BankOpenAccount selectByExample(BankOpenAccountExample example) {
        BankOpenAccount response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/selectByExample/" + example, BankOpenAccount.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    public UsersInfo findUsersInfoById(int userId) {
        UsersInfo response = restTemplate
                .getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UsersInfo.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
}
