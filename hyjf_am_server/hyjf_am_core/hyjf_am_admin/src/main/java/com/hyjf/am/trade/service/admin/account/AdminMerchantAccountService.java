/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.account;

import com.hyjf.am.trade.dao.model.customize.AdminMerchantAccountSumCustomize;

/**
 * @author zhangqingqing
 * @version MerchantAccountService, v0.1 2018/8/29 21:16
 */
public interface AdminMerchantAccountService {

    /**
     * 统计总额
     * @return
     */
    AdminMerchantAccountSumCustomize searchAccountSum();
}
