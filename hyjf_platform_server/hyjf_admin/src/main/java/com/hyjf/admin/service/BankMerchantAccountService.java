/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.resquest.admin.BankMerchantAccountListRequest; /**
 * @author zhangqingqing
 * @version BankMerchantAccountService, v0.1 2018/7/9 16:10
 */
public interface BankMerchantAccountService {

    BankMerchantAccountResponse selectBankMerchantAccount(BankMerchantAccountListRequest form);
}
