/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.customize.BankAccountManageCustomize;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageService, v0.1 2018/6/29 14:02
 */
public interface BankAccountManageService extends BaseService {
    /**
     * 更新账户余额
     *
     * @param accountVO
     * @return
     */
    Integer updateAccount(AccountVO accountVO);

    /**
     * 手动银行对账
     *
     * @param adminBankAccountCheckCustomizeVO
     * @return
     */
    String updateAccountCheck(AdminBankAccountCheckCustomizeVO adminBankAccountCheckCustomizeVO);

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
    /**
     * add by nxl 更新account表的account_id
     * @param account
     * @return
     */
    Integer updAccountId(Account account);

    /**
     * 查询用户账户信息金额信息
     *
     * @param userId
     * @return
     */
    BankAccountManageCustomize queryAccountUserMoney(Integer userId);
}
