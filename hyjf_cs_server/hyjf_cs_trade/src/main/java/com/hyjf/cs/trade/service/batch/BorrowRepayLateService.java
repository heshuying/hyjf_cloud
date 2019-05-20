/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

import com.hyjf.cs.common.service.BaseService;

/**
 * @author wangjun
 * @version BorrowRepayLateService, v0.1 2019/3/20 11:46
 */
public interface BorrowRepayLateService extends BaseService {
    /**
     * 更新还款逾期标的信息
     */
    void updateBorrowRepayLateInfo();
}
