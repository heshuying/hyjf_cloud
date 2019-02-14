package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.resquest.trade.BatchCenterCustomizeRequest;
import com.hyjf.am.trade.dao.model.customize.BatchCenterCustomize;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;

import java.util.List;

/**
* api端-第三方-批次放款
* @author Zha Daojian
* @date 2019/2/14 9:16
* @param
* @return
**/
public interface BatchCenterCustomizeMapper {

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
    List<BatchBorrowRecoverVo> queryBatchCenterList(BatchBorrowRecoverRequest request);

    /**
     * 获取列表求和
     * @param request
     * @return
     */
    BatchBorrowRecoverVo sumBatchCenter(BatchBorrowRecoverRequest request);

    Long countBatchCenter (BatchCenterCustomizeRequest batchCenterCustomize);

    List<BatchCenterCustomize> selectBatchCenterList(BatchCenterCustomizeRequest batchCenterCustomize);
}
