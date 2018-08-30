package com.hyjf.cs.trade.service.repay;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;

import java.util.List;

/**
 * @version RepayService, v0.1 2018/8/27 15:39
 * @Author: Zha Daojian
 */
public interface RepayService {

    /**
     * 获取批次放款列表
     * @author Zha Daojian
     * @date 2018/8/27 15:37
     * @param request
     * @return java.util.List<com.hyjf.am.vo.admin.BatchBorrowRecoverVo>
     **/
    List<BatchBorrowRecoverVo> getBatchBorrowRecoverList(BatchBorrowRecoverRequest request);


    /**
     * 获取批次放款列表条数
    * @author Zha Daojian
    * @date 2018/8/27 15:57
    * @param request
    * @return
    **/
    Integer countBatchCenter(BatchBorrowRecoverRequest request);
}
