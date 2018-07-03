/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

import java.util.List;

/**
 * @author wangjun
 * @version AdminCommonClient, v0.1 2018/7/3 10:29
 */
public interface AdminCommonClient {
    /**
     * 还款方式下拉列表
     * @return
     */
    List<BorrowStyleVO> selectBorrowStyleList();
}
