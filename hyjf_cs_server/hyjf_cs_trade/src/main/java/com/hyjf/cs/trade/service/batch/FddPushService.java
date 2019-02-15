package com.hyjf.cs.trade.service.batch;

import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/12/25
 * @Description:
 */
public interface FddPushService extends BaseService {

    /**
     * 开始推送任务
     */
    void updateFddPush(BorrowApicronVO borrowApicronVO);

    /**
     * 获取需要推送的任务标的
     * @return
     */
    List<BorrowApicronVO> getFddPushBorrowList();
}
