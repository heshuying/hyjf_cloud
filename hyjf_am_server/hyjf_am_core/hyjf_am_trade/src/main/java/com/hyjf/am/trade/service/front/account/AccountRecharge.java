/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.account;


import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;

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
     * /**
     * 根据用户id获取用户充值
     * @param userId
     * @return
     */
    List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> getAccountRechargeByUserId(Integer userId);

    /**
     * 资金中心 - 充值管理 - 获取充值列表条数
     * @param request
     * @return
     * @Author : huanghui
     */
    Integer getAccountRechargeListCount(AccountRechargeRequest request);

    /**
     * 资金中心 - 充值管理 - 获取充值列表
     * @param request
     * @return
     * @Author : huanghui
     */
    List<AccountRechargeVO> getAccountRechargeList(AccountRechargeRequest request);

    /**
     * 更新用户充值订单状态
     * @param userId
     * @param nid
     * @return
     * @Author : huanghui
     */
    boolean updateRechargeStatus(Integer userId, String nid);

    /**
     * 充值掉单后,更新用户的账户信息
     * @param userId
     * @param nid
     * @return
     * @Author : huanghui
     */
    boolean updateAccountAfterRecharge(Integer userId, String nid);

    /**
     * 充值失败后,更新用户订单状态
     * @param userId
     * @param nid
     * @return
     * @Author : huanghui
     */
    boolean updateAccountAfterRechargeFail(Integer userId, String nid);
}
