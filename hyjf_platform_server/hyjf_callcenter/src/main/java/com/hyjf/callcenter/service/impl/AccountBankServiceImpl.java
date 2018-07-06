/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.service.impl;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.client.AmUserClient;
import com.hyjf.callcenter.service.AccountBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version AccountBankServiceImpl, v0.1 2018/6/6 13:47
 */
@Service
public class AccountBankServiceImpl implements AccountBankService {
    /**
     * 按照用户名/手机号查询江西银行绑卡关系
     * @param user
     * @return List<BankCard>
     * @author wangjun
     */
    @Autowired
    private AmUserClient amUserClient;

    @Override
    public List<BankCardVO> getTiedCardOfAccountBank(UserVO user) {
        return amUserClient.getTiedCardOfAccountBank(user);
    }
}
