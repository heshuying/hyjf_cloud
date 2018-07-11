/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.BankMerchantAccountListCustomizeResponse;
import com.hyjf.am.resquest.admin.BankRedPacketAccountListRequest;

/**
 * @author zhangqingqing
 * @version BankRedPacketAccountService, v0.1 2018/7/10 11:06
 */
public interface BankRedPacketAccountService {
    /**
     * 查询红包明细分页
     * @param request
     */
    BankMerchantAccountListCustomizeResponse selectBankMerchantAccountList(BankRedPacketAccountListRequest request);
}
