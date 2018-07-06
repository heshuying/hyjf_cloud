/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.response.BorrowFirstResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.resquest.admin.BorrowFirstRequest;

/**
 * @author wangjun
 * @version BorrowFirstService, v0.1 2018/7/3 15:11
 */
public interface BorrowFirstService {
    /**
     * 借款初审列表
     *
     * @param borrowFirstRequest
     * @return
     */
    BorrowFirstResponseBean getBorrowFirstList(BorrowFirstRequest borrowFirstRequest);

    /**
     * 已交保证金详细画面信息
     *
     * @return
     */
    AdminResult getBailInfo(String borrowNid);

    /**
     * 交保证金
     *
     * @param borrowNid
     * @return
     */
    AdminResult insertBorrowBail(String borrowNid, String currUserId);

    /**
     * 获取发标信息
     *
     * @param borrowNid
     * @return
     */
    AdminResult getFireInfo(String borrowNid);

    /**
     * 发标
     *
     * @param borrowNid
     * @return
     */
    AdminResult updateBorrowFireInfo(String borrowNid, String verifyStatus, String ontime);
}
