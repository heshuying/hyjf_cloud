/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayClient, v0.1 2018/6/25 17:40
 */
public interface BatchHjhBorrowRepayClient {

    /**
     * 计划锁定
     *  @param accedeOrderId
     * @param inverestUserInfo
     * @param commissioUserInfoVO
     * @param bankOpenAccountVO
     * @param userInfoCustomizeVOS
     */
    void updateForLock(String accedeOrderId, UserInfoVO inverestUserInfo, UserInfoVO commissioUserInfoVO, BankOpenAccountVO bankOpenAccountVO, List<UserInfoCustomizeVO> userInfoCustomizeVOS);

    /**
     * 计划退出
     *
     * @param accedeOrderId
     */
    void updateForQuit(String accedeOrderId);
}

