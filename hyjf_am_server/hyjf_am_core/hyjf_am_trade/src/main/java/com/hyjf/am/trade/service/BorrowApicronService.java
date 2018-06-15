/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.BorrowApicron;

import java.util.List;

/**
 * @author ${yaoy}
 * @version BorrowApicronService, v0.1 2018/6/14 16:48
 */
public interface BorrowApicronService {
    List<BorrowApicron> getBorrowApicronList(Integer status, Integer apiType);

    int updateBorrowApicron(Integer id, Integer status, String data);

    int updateBorrowApicron2(Integer id, Integer status);

    List<BorrowApicron> getBorrowApicronListWithRepayStatus(Integer status, Integer apiType);

    int updateBorrowApicronOfRepayStatus(Integer id, Integer status);
}
