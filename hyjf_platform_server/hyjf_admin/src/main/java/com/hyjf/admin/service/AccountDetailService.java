/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.response.admin.AdminAccountDetailDataRepairResponse;
import com.hyjf.am.response.trade.AccountListResponse;
import com.hyjf.am.response.trade.AccountTradeResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.resquest.admin.AccountListRequest;

/**
 * @author nxl
 * @version UserCenterService, v0.1 2018/6/20 15:34
 */
public interface AccountDetailService {
    /**
     * 查找资金明细列表
     *
     * @param request
     * @return
     */
    AccountDetailResponse findAccountDetailList(AccountDetailRequest request);

    /**
     * 查询交易明细最小的id
     */
    AdminAccountDetailDataRepairResponse accountdetailDataRepair(int userId);

    /**
     * 查询出20170120还款后,交易明细有问题的用户ID
     */
    AdminAccountDetailDataRepairResponse queryAccountDetailErrorUserList();

    // 根据Id查询此条交易明细
    AccountListResponse selectAccountById(int accountId);

    // 查询此用户的下一条交易明细
    AccountListResponse selectNextAccountList(int accountId, int userId);

    // 根据查询用交易类型查询用户操作金额
    AccountTradeResponse selectAccountTradeByValue(String tradeValue);

    // 更新用户的交易明细
    int updateAccountList(AccountListRequest accountListRequest);
}
