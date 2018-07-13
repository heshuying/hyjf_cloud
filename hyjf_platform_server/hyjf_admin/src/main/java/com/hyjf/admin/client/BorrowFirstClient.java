/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BorrowFireRequest;
import com.hyjf.am.resquest.admin.BorrowFirstRequest;
import com.hyjf.am.vo.admin.BorrowFirstCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowConfigVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowFirstClient, v0.1 2018/7/3 15:13
 */
public interface BorrowFirstClient {
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
    List<BorrowFirstCustomizeVO> selectBorrowFirstList(BorrowFirstRequest borrowFirstRequest);

    /**
     * 统计页面值总和
     *
     * @param borrowFirstRequest
     * @return
     */
    String sumBorrowFirstAccount(BorrowFirstRequest borrowFirstRequest);

    /**
     * 根据code获取配置
     *
     * @param configCd
     * @return
     */
    BorrowConfigVO getBorrowConfig(String configCd);

    /**
     * 根据标的编号查询详细信息
     *
     * @param borrowNid
     * @return
     */
    BorrowVO selectBorrowByNid(String borrowNid);

    /**
     * 根据标的编号查询borrowInfo
     *
     * @param borrowNid
     * @return
     */
    BorrowInfoVO selectBorrowInfoByNid(String borrowNid);

    /**
     * 获取用户姓名
     *
     * @param userId
     * @return
     */
    String getUserName(Integer userId);

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
    boolean updateOntimeRecord(BorrowFireRequest borrowFireRequest);

    /**
     * 加入计划
     *
     * @param borrowFireRequest
     */
    boolean sendToMQ(BorrowFireRequest borrowFireRequest);
}
