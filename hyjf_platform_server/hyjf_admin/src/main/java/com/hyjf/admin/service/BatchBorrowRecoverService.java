package com.hyjf.admin.service;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description: 批次中心-批次放款接口
 */
public interface BatchBorrowRecoverService extends BaseService{
    /**
     * 获取批次放款列表
     * @param request
     * @return
     */
    BatchBorrowRecoverReponse queryBatchBorrowRecoverList(BatchBorrowRecoverRequest request);
}
