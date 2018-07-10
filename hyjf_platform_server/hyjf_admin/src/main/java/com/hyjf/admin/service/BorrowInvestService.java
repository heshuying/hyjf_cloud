/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.response.BorrowInvestResponseBean;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;

/**
 * @author wangjun
 * @version BorrowInvestService, v0.1 2018/7/10 9:17
 */
public interface BorrowInvestService{
    /**
     * 投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    BorrowInvestResponseBean getBorrowInvestList(BorrowInvestRequest borrowInvestRequest);
}
