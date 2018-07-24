/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.AccountBank;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountBankService, v0.1 2018/7/23 16:17
 */
public interface AccountBankService extends BaseService {

    /**
     * 根据用户id查询银行卡信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    List<AccountBank> getBankCardByUserId(Integer userId);

    /**
     *用户银行卡信息
     * @param userId
     * @param status
     * @return
     */
    List<AccountBank> selectAccountBank(Integer userId, Integer status);

    /**
     *
     * @param userId
     * @return
     */
    List<AdminBankAccountCheckCustomizeVO> queryAllBankOpenAccount(Integer userId);
}
