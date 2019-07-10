package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.customize.RechargeManagementCustomize;

import java.util.List;

/**
 * 充值管理
 * @Author : huanghui
 */
public interface RechargeManagementService {

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
    List<RechargeManagementCustomize> getAccountRechargeList(AccountRechargeRequest request);

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
    boolean updateAccountAfterRecharge(Integer userId, String nid) throws Exception;

    /**
     * 充值失败后,更新用户订单状态
     * @param userId
     * @param nid
     * @return
     * @Author : huanghui
     */
    boolean updateAccountAfterRechargeFail(Integer userId, String nid);

    /**
     * 用户信息
     * @param userId
     * @return
     */
    Account getAccountByUserId(Integer userId);

    /**
     * 根据订单号nid获取充值信息
     * @param nid
     * @return
     */
    AccountRecharge getAccountRechargeByNid(String nid);

	AccountRecharge selectAccountRechargeByUserId(Integer userId);
}
