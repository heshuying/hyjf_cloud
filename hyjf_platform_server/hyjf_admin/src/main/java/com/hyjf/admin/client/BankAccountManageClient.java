/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageClient, v0.1 2018/6/29 13:36
 */
public interface BankAccountManageClient {
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
    List<BankAccountManageCustomizeVO> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest);
    /**
     * 资金明细（列表）
     * @param bankAccountManageRequest
     * @return
     */
    List<BankAccountManageCustomizeVO> queryAccountDetails(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 获取用户账户信息
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccount(Integer userId);

    /**
     * 更新用户账户信息
     * @param accountVO
     * @return
     */
    Integer updateAccount(AccountVO accountVO);

    /**
     * 手动银行对账
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    String updateAccountCheck(Integer userId, String startTime, String endTime);
}
