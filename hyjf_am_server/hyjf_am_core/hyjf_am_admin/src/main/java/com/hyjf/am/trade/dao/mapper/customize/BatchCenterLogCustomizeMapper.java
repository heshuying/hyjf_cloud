package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverLogVo;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;

import java.util.List;

/**
 * 批次还款记录
 * @Auther:hesy
 */
public interface BatchCenterLogCustomizeMapper {

    /**
     * 获取列表数量
     * @param request
     * @return
     */
    Integer countBorrowBatchCenterListTotal(BatchBorrowRecoverRequest request);

    /**
     * 获取列表
     * @param request
     * @return
     */
    List<BatchBorrowRecoverLogVo> queryBatchCenterList(BatchBorrowRecoverRequest request);


}
