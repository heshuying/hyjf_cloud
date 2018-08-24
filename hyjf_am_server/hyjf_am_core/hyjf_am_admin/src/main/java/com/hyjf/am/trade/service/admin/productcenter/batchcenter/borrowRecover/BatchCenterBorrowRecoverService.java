package com.hyjf.am.trade.service.admin.productcenter.batchcenter.borrowRecover;

import java.util.List;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;

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

    /**
     * 获取列表求和
     * @param request
     * @return
     */
    BatchBorrowRecoverVo getListSum(BatchBorrowRecoverRequest request);

    /**
     * 根据ID获取任务表数据
     * @param id
     * @return
     */
    BorrowApicron getRecoverApicronByID(String id);

    /**
     * 发提成处理- 计算提成,更新借款API表
     * @auth Zha Daojian
     * @param
     * @return
     */
    boolean updateBorrowApicronByPrimaryKeySelective(String apicornId);
}
