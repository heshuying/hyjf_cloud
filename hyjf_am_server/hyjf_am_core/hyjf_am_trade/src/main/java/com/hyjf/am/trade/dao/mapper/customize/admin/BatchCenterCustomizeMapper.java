package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description: 后台列表-批次中心
 */
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


}
