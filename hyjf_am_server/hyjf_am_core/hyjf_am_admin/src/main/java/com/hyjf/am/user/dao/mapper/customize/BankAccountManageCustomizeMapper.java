/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.user.dao.model.customize.BankAccountManageCustomize;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageCustomizeMapper, v0.1 2018/6/29 14:05
 */
public interface BankAccountManageCustomizeMapper {

    /**
     * 行账户管理页面查询件数(条件为空的场合)
     * @param bankAccountManageRequest
     * @return
     */
    Integer queryAccountCountAll(BankAccountManageRequest bankAccountManageRequest);
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
}
