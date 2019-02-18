package com.hyjf.cs.user.service.aems.syncuserinfo.impl;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.user.service.aems.syncuserinfo.AemsSyncUserService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AemsSyncUserServiceImpl extends BaseUserServiceImpl implements AemsSyncUserService {

    @Override
    public BankOpenAccountVO getUserByAccountId(String accountId) {

        BankOpenAccountVO bankAccount = amUserClient.selectBankOpenAccountByAccountId(accountId);
        if (bankAccount != null) {
            return bankAccount;
        }
        return null;
    }

    /**
     * 获取用户的账户信息
     *
     * @param userId
     * @return 获取用户的账户信息
     */
    public AccountVO getAccount(Integer userId) {
        AccountVO account = amUserClient.getAccount(userId);
        if (account != null) {
            return account;
        }
        return null;
    }
}
