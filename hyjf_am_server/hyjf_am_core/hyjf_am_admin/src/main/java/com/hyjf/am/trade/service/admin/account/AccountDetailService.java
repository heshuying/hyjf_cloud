/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.account;

import com.hyjf.am.resquest.admin.AccountListRequest;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.dao.model.customize.AdminAccountDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.AdminAccountDetailDataRepairCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AccountDetailService, v0.1 2018/6/29 13:56
 */
public interface AccountDetailService {

    /**
     * 资金明细 （列表）
     *
     * @param mapParam
     * @return
     */
    List<AdminAccountDetailCustomize> queryAccountDetails(Map<String, Object> mapParam);

    /**
     * 查询总数
     *
     * @param mapParam
     * @return
     */
    int countAccountDetail(Map<String, Object> mapParam);

    /**
     * 查询出款后,交易明细有问题的用户ID
     * @return
     */
    List<AdminAccountDetailDataRepairCustomize> queryAccountDetailErrorUserList();

    /**
     * 查询交易明细最小的id
     * @param userId
     * @return
     */
    AdminAccountDetailDataRepairCustomize queryAccountDetailIdByUserId(int userId);

    /**
     * 根据Id查询此条交易明细
     * @param accountId
     * @return
     */
    AccountList selectAccountById(int accountId);

    /**
     * 查询此用户的下一条交易明细
     * @param accountId
     * @param userId
     * @return
     */
    AccountList selectNextAccountList(int accountId, int userId);

    /**
     * 根据查询用交易类型查询用户操作金额
     * @param tradeValue
     * @return
     */
    AccountTrade selectAccountTradeByValue(String tradeValue);

    /**
     * 更新用户的交易明细
     * @param accountListRequest
     * @return
     */
    int updateAccountList(AccountListRequest accountListRequest);

    /**
     * 修改Account表的字段
     * @param userId
     * @param accountId
     * @return
     */
    Integer updateAccountNumberByUserId(Integer userId, String accountId);

    /**
     * 用户销户成功后,删除用户账户表
     *
     * @param userId
     * @return
     */
    int deleteUserAccountAction(String userId);
}
