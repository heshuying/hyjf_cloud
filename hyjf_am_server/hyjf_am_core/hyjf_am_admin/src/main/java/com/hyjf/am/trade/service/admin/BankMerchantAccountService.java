/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import java.util.List;

import com.hyjf.am.resquest.admin.BankMerchantAccountListRequest;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccount;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountService, v0.1 2018/7/9 16:18
 */
public interface BankMerchantAccountService {
    int queryRecordTotal(BankMerchantAccountListRequest form);

    List<BankMerchantAccount> selectRecordList(BankMerchantAccountListRequest form, int limitStart, int limitEnd);

    List<BankMerchantAccount> updateBankMerchantAccount(List<BankMerchantAccount> recordList, Integer userId);
}
