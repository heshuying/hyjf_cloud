package com.hyjf.am.trade.service.agreement;

import com.hyjf.am.resquest.admin.BorrowApicronRequest;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;

import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/12/26
 * @Description: FDD 推送协议
 */
public interface FddPushService extends BaseService{

    /**
     * 获取需要推送协议的标的列表
     * @return
     */
    List<BorrowApicronVO> getFddPushBorrowList();

    /**
     * 开始推送法大大协议
     * @param request
     */
    void updateFddPush(BorrowApicronRequest request);

    /**
     * 获取分期还款信息
     * @param planinfo
     * @return
     */
    BorrowRecoverPlanVO getBorrowRecoverPlanByNidandPeriod(BorrowRecoverPlanVO planinfo);
}
