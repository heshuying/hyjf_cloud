/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.trade.dao.model.customize.BankAccountManageCustomize;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageCustomizeMapper, v0.1 2018/6/29 14:05
 */
public interface AdminBankAccountManageCustomizeMapper {

    /**
     * 行账户管理页面查询件数
     *
     * @param bankAccountManageRequest
     * @return
     */
    Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 账户管理页面查询列表
     *
     * @param bankAccountManageRequest
     * @return
     */
    List<BankAccountManageCustomize> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest);
}
