/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayToHjhQuitClient, v0.1 2018/6/25 9:41
 */
public interface BorrowRepayToHjhQuitClient {
    /**
     * 查询待退出计划的标的
     * @return
     */
    List<HjhAccedeVO> selectWaitQuitHjhList();
}
