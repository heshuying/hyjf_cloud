/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception;

import com.hyjf.am.vo.user.BankOpenAccountVO;

/**
 * @author: sunpeikai
 * @version: BorrowRegistRepairService, v0.1 2018/7/3 18:03
 */
public interface BorrowRegistRepairService {

    /**
     * 根据userId获取BankOpenAccount
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    BankOpenAccountVO searchBankOpenAccount(Integer userId);
}
