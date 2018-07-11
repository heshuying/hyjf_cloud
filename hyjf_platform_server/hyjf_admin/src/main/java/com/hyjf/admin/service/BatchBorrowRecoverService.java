package com.hyjf.admin.service;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;

import java.util.List;

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

    /**
     * 获取批次状态列表的显示内容
     * @param listAccountDetail
     * @param nameClass
     */
    void queryBatchCenterStatusName(List<BatchBorrowRecoverVo> listAccountDetail,String nameClass);

    /**
     * 获取批次列表的求和
     * @param request
     * @return
     */
    BatchBorrowRecoverVo queryBatchCenterListSum(BatchBorrowRecoverRequest request);
}
