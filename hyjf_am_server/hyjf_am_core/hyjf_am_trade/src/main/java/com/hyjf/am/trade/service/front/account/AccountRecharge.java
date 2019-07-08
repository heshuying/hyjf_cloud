/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.account;


import java.math.BigDecimal;
import java.util.List;

/**
 * @author fuqiang
 * @version AccountRecharge, v0.1 2018/7/17 11:11
 */
public interface AccountRecharge {
    /**
     * 获取充值金额
     * @param list 用户id集合
     * @return
     */
    BigDecimal getRechargePrice(List<Integer> list);

    /**
     *
     * 根据用户id获取用户充值
     * @param userId
     * @return
     */
    List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> getAccountRechargeByUserId(Integer userId);

    /**
     * 根据用户ID查询用户充值成功记录
     * @param userId
     * @return
     */
    List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> selectRechargeListByUserId(Integer userId);

    /**
     * 根据用户Id查询用户第一笔充值记录
     *
     * @param userId
     * @return
     */
    com.hyjf.am.trade.dao.model.auto.AccountRecharge selectAccountRechargeByUserId(Integer userId);
}
