/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;

/**
 * 资金服务: BaseService
 * @author liuyang
 * @version BaseService, v0.1 2018/6/27 9:32
 */
public interface BaseService {
    /**
     * 根据标的编号检索标的借款信息
     * @param borrowNid
     * @return
     */
    Borrow getBorrow(String borrowNid);

    /**
     * 根据标的编号检索标的借款详情
     * @param borrowNid
     * @return
     */
    BorrowInfo getBorrowInfoByNid(String borrowNid);
}
