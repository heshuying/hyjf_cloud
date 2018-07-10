/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BankRedPacketAccountService;
import com.hyjf.am.response.admin.BankMerchantAccountListCustomizeResponse;
import com.hyjf.am.resquest.admin.BankRedPacketAccountListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version BankRedPacketAccountServiceImpl, v0.1 2018/7/10 11:06
 */
@Service
public class BankRedPacketAccountServiceImpl implements BankRedPacketAccountService {

    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public BankMerchantAccountListCustomizeResponse selectBankMerchantAccountList(BankRedPacketAccountListRequest request) {

        return amTradeClient.selectBankMerchantAccountList(request);
    }
}
