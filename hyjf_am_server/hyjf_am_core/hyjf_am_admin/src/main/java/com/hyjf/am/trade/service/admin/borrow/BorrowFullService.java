/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow;

import java.util.List;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BorrowFullRequest;
import com.hyjf.am.trade.dao.model.customize.BorrowFullCustomize;

/**
 * @author wangjun
 * @version BorrowFullService, v0.1 2018/7/6 15:17
 */
public interface BorrowFullService {
    /**
     * 获取借款复审列表count
     *
     * @param borrowFullRequest
     * @return
     */
    Integer countBorrowFull(BorrowFullRequest borrowFullRequest);

    /**
     * 查询借款复审列表
     *
     * @param borrowFullRequest
     * @return
     */
    List<BorrowFullCustomize> selectBorrowFullList(BorrowFullRequest borrowFullRequest);

    /**
     * 复审列表合计
     *
     * @param borrowFullRequest
     * @return
     */
    BorrowFullCustomize getSumAccount(BorrowFullRequest borrowFullRequest);

    /**
     * 复审详细信息
     *
     * @param borrowNid
     * @return
     */
    BorrowFullCustomize getFullInfo(String borrowNid);

    /**
     * 借款复审详细列表count
     *
     * @param borrowNid
     * @return
     */
    Integer countFullList(String borrowNid);

    /**
     * 复审详细信息列表
     *
     * @param borrowFullRequest
     * @return
     */
    List<BorrowFullCustomize> getFullList(BorrowFullRequest borrowFullRequest);

    /**
     * 复审详细列表合计
     *
     * @param borrowNid
     * @return
     */
    BorrowFullCustomize getSumAmount(String borrowNid);

    /**
     * 复审提交
     *
     * @param borrowFullRequest
     * @return
     */
    Response updateBorrowFull(BorrowFullRequest borrowFullRequest);

    /**
     * 流标
     *
     * @param borrowFullRequest
     * @return
     */
    Response updateBorrowOver(BorrowFullRequest borrowFullRequest);
}
