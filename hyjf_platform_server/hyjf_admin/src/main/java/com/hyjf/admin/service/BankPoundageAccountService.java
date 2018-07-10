/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.BankMerchantAccountListCustomizeResponse;
import com.hyjf.am.resquest.admin.BankRedPacketAccountListRequest; /**
 * @author zhangqingqing
 * @version BankPoundageAccountService, v0.1 2018/7/10 14:00
 */
public interface BankPoundageAccountService {

    /**
     * 查询手续费账户明细
     * @param request
     * @return
     */
    BankMerchantAccountListCustomizeResponse selectBankMerchantAccountList(BankRedPacketAccountListRequest request);
}
