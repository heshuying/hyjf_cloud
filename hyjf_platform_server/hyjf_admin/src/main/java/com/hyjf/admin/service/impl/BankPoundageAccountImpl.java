/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BankPoundageAccountService;
import com.hyjf.am.response.admin.BankMerchantAccountListCustomizeResponse;
import com.hyjf.am.resquest.admin.BankRedPacketAccountListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version BankPoundageAccountImpl, v0.1 2018/7/10 14:00
 */
@Service
public class BankPoundageAccountImpl implements BankPoundageAccountService {

    @Autowired
    AmTradeClient amTradeClient;

    /**
     * 查询手续费账户明细
     * @param request
     * @return
     */
    @Override
    public BankMerchantAccountListCustomizeResponse selectBankMerchantAccountList(BankRedPacketAccountListRequest request) {
        return amTradeClient.selectBankMerchantAccountList(request);
    }
}
