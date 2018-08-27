package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.resquest.admin.TenderCommissionRequest;

/**
 * @version TenderCommissionService, v0.1 2018/8/7 9:31
 * @Author: Zha Daojian
 */
public interface TenderCommissionService {


    /**
     * 提成新增
     *
     * @return
     */
    boolean insertTenderCommission(TenderCommissionRequest request);

    /**
     * 根据BorrowTender表的id和TenderType查询条数
     *
     * @return
     */
    int countTenderCommissionByTenderIdAndTenderType(TenderCommissionRequest request);
}
