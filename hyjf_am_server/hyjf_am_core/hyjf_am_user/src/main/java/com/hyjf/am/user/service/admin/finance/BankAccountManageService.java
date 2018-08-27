/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.finance;

import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.service.BaseService;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageService, v0.1 2018/6/29 14:02
 */
public interface BankAccountManageService extends BaseService {
    /**
     * 查询用户是否开户
     *
     * @param userId
     * @return
     */
    BankOpenAccount getBankOpenAccount(Integer userId);
}
