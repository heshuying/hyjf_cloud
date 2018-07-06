/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.BorrowFireRequest;
import com.hyjf.am.resquest.admin.BorrowFirstRequest;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowFirstCustomize;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowFirstService, v0.1 2018/7/3 15:59
 */
public interface BorrowFirstService {
    /**
     * 借款初审总条数
     *
     * @param borrowFirstRequest
     * @return
     */
    Integer countBorrowFirst(BorrowFirstRequest borrowFirstRequest);

    /**
     * 借款初审列表
     *
     * @param borrowFirstRequest
     * @return
     */
    List<BorrowFirstCustomize> selectBorrowFirstList(BorrowFirstRequest borrowFirstRequest);

    /**
     * 统计页面值总和
     *
     * @param borrowFirstRequest
     * @return
     */
    String getSumBorrowFirstAccount(BorrowFirstRequest borrowFirstRequest);

    /**
     * 交保证金
     *
     * @param borrowNid
     * @return
     */
    boolean insertBorrowBail(String borrowNid, String currUserId);

    /**
     * 更新-发标
     *
     * @param borrowFireRequest
     */
    void updateOntimeRecord(BorrowFireRequest borrowFireRequest);

    /**
     * 加入计划
     *
     * @param borrowFireRequest
     */
    void sendToMQ(BorrowFireRequest borrowFireRequest);
}
