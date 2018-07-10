/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BankMerchantAccountService;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.resquest.admin.BankMerchantAccountListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountServiceImpl, v0.1 2018/7/9 16:11
 */
@Service
public class BankMerchantAccountServiceImpl implements BankMerchantAccountService {

    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public BankMerchantAccountResponse selectBankMerchantAccount(BankMerchantAccountListRequest form) {
        return amTradeClient.selectBankMerchantAccount(form);
    }
}
