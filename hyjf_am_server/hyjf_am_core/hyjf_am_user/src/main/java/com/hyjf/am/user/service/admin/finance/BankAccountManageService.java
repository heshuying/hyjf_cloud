/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.finance;

import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.customize.BankAccountManageCustomize;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageService, v0.1 2018/6/29 14:02
 */
public interface BankAccountManageService extends BaseService {
    /**
     * 行账户管理页面查询件数
     * @param bankAccountManageRequest
     * @return
     */
    Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 账户管理页面查询列表
     * @param bankAccountManageRequest
     * @return
     */
    List<BankAccountManageCustomize> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 查询用户是否开户
     * @param userId
     * @return
     */
    BankOpenAccount getBankOpenAccount(Integer userId);
}
