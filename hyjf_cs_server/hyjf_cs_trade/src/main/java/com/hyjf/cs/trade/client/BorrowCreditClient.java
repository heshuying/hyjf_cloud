/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.vo.trade.BorrowCreditVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditClient, v0.1 2018/6/24 9:24
 */
public interface BorrowCreditClient {
    /**
     * 获取债转状态为0的数据
     * @return
     */
    List<BorrowCreditVO> selectBorrowCreditList();

    /**
     * 更新债转状态
     * @return
     */
    Integer updateBorrowCredit(BorrowCreditVO borrowCreditVO);

    List<BorrowCreditVO> getBorrowCreditList(BorrowCreditRequest request1);
}
