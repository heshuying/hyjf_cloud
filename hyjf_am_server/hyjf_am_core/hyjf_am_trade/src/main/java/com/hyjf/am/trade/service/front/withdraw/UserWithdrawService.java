/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.withdraw;

import com.hyjf.am.resquest.trade.ApiUserWithdrawRequest;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserWithdrawService, v0.1 2018/8/30 15:33
 */
public interface UserWithdrawService extends BaseService {
    /**
     * 用户提现更新数据表
     * @auth sunpeikai
     * @param
     * @return
     */
    int updateBeforeCash(ApiUserWithdrawRequest request);

    /**
     * 根据orderId查询出status=2的账户提现信息
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    AccountWithdraw getAccountWithdrawByOrderId(String orderId);

    /**
     * 执行提现后处理
     * @auth sunpeikai
     * @param
     * @return
     */
    String handlerAfterCash(ApiUserWithdrawRequest request) throws Exception;

    /**
     * 查询某用户 id 的提现记录，带分页
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AccountWithdraw> searchAccountWithdrawPaginate(ApiUserWithdrawRequest request);
}
