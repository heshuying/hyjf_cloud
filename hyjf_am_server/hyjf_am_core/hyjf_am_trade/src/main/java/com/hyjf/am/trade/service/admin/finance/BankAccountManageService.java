/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageService, v0.1 2018/6/29 14:02
 */
public interface BankAccountManageService extends BaseService {
    /**
     * 行账户管理页面查询件数
     * @param accountVO
     * @return
     */
    Integer updateAccount(AccountVO accountVO);

    /**
     * 手动银行对账
     * @param adminBankAccountCheckCustomizeVO
     * @return
     */
    String updateAccountCheck(AdminBankAccountCheckCustomizeVO adminBankAccountCheckCustomizeVO);

}
