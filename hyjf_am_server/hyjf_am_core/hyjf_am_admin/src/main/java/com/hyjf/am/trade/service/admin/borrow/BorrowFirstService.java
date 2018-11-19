/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowBailInfoResponse;
import com.hyjf.am.resquest.admin.BorrowFireRequest;
import com.hyjf.am.resquest.admin.BorrowFirstRequest;
import com.hyjf.am.trade.dao.model.customize.BorrowFirstCustomize;

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
    Response updateOntimeRecord(BorrowFireRequest borrowFireRequest);

    /**
     * 根据流程配置判断是否发送mq到自动初审
     *
     * @param borrowNid
     */
    void sendToMQAutoPreAudit(String borrowNid);

    /**
     * 获取保证金信息
     * @param borrowNid
     * @return
     */
    BorrowBailInfoResponse getBailInfo(String borrowNid);
}
