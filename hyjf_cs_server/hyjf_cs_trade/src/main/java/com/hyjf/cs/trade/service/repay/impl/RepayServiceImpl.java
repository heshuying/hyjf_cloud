package com.hyjf.cs.trade.service.repay.impl;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.repay.RepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version RepayServiceImpl, v0.1 2018/8/27 15:39
 * @Author: Zha Daojian
 */
@Service
public class RepayServiceImpl implements RepayService {

    @Autowired
    AmTradeClient amTradeClient;
    /**
     * 获取批次放款列表
     *
     * @param request
     * @return java.util.List<com.hyjf.am.vo.admin.BatchBorrowRecoverVo>
     * @author Zha Daojian
     * @date 2018/8/27 15:37
     **/
    @Override
    public List<BatchBorrowRecoverVo> getBatchBorrowRecoverList(BatchBorrowRecoverRequest request) {
        return amTradeClient.getBatchBorrowRecoverList(request);
    }

    /**
     * 获取批次放款列表条数
     *
     * @param request
     * @return
     * @author Zha Daojian
     * @date 2018/8/27 15:57
     **/
    @Override
    public Integer countBatchCenter(BatchBorrowRecoverRequest request) {
         return amTradeClient.getCountBatchCenter(request);
    }
}
