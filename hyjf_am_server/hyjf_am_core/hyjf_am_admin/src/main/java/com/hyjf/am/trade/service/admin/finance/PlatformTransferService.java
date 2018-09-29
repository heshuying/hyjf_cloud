/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccount;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;

import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: PlatformTransferService, v0.1 2018/7/9 11:11
 */
public interface PlatformTransferService {
    /**
     * 根据筛选条件查询数据count
     * @auth sunpeikai
     * @param request
     * @return
     */
    Integer getPlatformTransferCount(PlatformTransferListRequest request);
    /**
     * 根据筛选条件查询平台转账list
     * @auth sunpeikai
     * @param request
     * @return
     */
    List<AccountRecharge> searchPlatformTransferList(PlatformTransferListRequest request);

    /**
     * 更新账户信息
     * @auth sunpeikai
     * @param accountVO 账户信息
     * @return
     */
    Integer updateAccount(AccountVO accountVO);

    /**
     * 插入充值表记录
     * @auth sunpeikai
     * @param accountRechargeVO 充值表信息
     * @return
     */
    Integer insertAccountRecharge(AccountRechargeVO accountRechargeVO);

    /**
     * 插入收支明细表记录
     * @auth sunpeikai
     * @param accountListVO 收支明细表信息
     * @return
     */
    Integer insertAccountList(AccountListVO accountListVO);

    /**
     * 根据账户id查询BankMerchantAccount
     * @auth sunpeikai
     * @param accountId 账户id
     * @return
     */
    BankMerchantAccount searchBankMerchantAccountByAccountId(Integer accountId);

    /**
     * 更新红包账户信息
     * @auth sunpeikai
     * @param bankMerchantAccountVO 红包账户信息
     * @return
     */
    Integer updateBankMerchantAccount(BankMerchantAccountVO bankMerchantAccountVO);

    /**
     * 插入红包明细数据
     * @auth sunpeikai
     * @param bankMerchantAccountListVO 红包明细
     * @return
     */
    Integer insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountListVO);

    /**
     * 根据orderId查询红包明细
     * @author zhangyk
     * @date 2018/8/7 16:42
     */
    Integer  getBankMerchantAccountListCountByOrderId(String orderId);

    /**
     * 圈存异步回调处理
     * @author zhangyk
     * @date 2018/8/7 18:58
     */
    Boolean updateAccountByRechargeCallback(Map<String,Object> params);

    /**
     * 更新充值明细为失败状态
     * @author zhangyk
     * @date 2018/8/8 10:30
     */
    Boolean updateMerchantAccountListFail(String orderId);

    /**
     * 圈提异步回调处理
     * @author zhangyk
     * @date 2018/8/7 18:58
     */
    Boolean updateAccountByWithdrawCallback(Map<String,Object> params);

}
