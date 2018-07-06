/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.BorrowFirstRequest;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowFirstCustomize;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowFirstCustomizeMapper, v0.1 2018/7/3 16:01
 */
public interface BorrowFirstCustomizeMapper {
    /**
     * 获取借款列表
     *
     * @param borrowFirstRequest
     * @return
     */
    List<BorrowFirstCustomize> selectBorrowFirstList(BorrowFirstRequest borrowFirstRequest);

    /**
     * COUNT
     *
     * @param borrowFirstRequest
     * @return
     */
    Integer countBorrowFirst(BorrowFirstRequest borrowFirstRequest);

    /**
     * 统计页面值总和
     * @param borrowFirstRequest
     * @return
     */
    String sumBorrowFirstAccount(BorrowFirstRequest borrowFirstRequest);
}
