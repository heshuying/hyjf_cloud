/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.withdraw.impl;

import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.user.AccountBankVO;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.withdraw.UserWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserWithdrawServiceImpl, v0.1 2018/7/23 15:18
 */
@Service
public class UserWithdrawServiceImpl extends BaseServiceImpl implements UserWithdrawService {
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private AmUserClient amUserClient;

    /**
     * 根据userId获取accountBank
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public List<AccountBankVO> getBankCardByUserId(Integer userId) {
        return amUserClient.getBankCardByUserId(userId);
    }
    /**
     * 根据银行名查询银行配置
     * @auth sunpeikai
     * @param bank 银行code，例如：招商银行,CMB
     * @return
     */
    @Override
    public BankConfigVO getBankInfo(String bank) {
        return amConfigClient.getBankConfigByCode(bank);
    }
}
