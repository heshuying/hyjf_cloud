package com.hyjf.am.trade.service.admin.productcenter.batchcenter.borrowRecover;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description:批次中心-批次放款
 */
public interface BatchCenterBorrowRecoverService extends BaseService{

    /**
     * 获取列表数量
     * @param request
     * @return
     */
    Integer getListTotal(BatchBorrowRecoverRequest request);

    /**
     * 获取列表
     * @param request
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<BatchBorrowRecoverVo> getList(BatchBorrowRecoverRequest request, int limitStart, int limitEnd);
}
