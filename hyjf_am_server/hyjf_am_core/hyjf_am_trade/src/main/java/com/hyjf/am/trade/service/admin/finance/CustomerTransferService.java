/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.trade.dao.model.auto.Account;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: CustomerTransferService, v0.1 2018/7/6 10:15
 */
public interface CustomerTransferService {

    /**
     * 根据userId查询账户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    List<Account> searchAccountByUserId(Integer userId);
}
