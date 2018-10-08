/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmp;
import com.hyjf.am.trade.service.BaseService;

/**
 * @author liubin
 * @version HjhPlanBorrowTmpService, v0.1 2018/7/4 19:22
 */
public interface HjhPlanBorrowTmpService extends BaseService {

    int insertHjhPlanBorrowTmp(HjhPlanBorrowTmp hjhPlanBorrowTmp);

    int deleteHjhPlanBorrowTmp(HjhPlanBorrowTmp hjhPlanBorrowTmp);

    int deleteHjhPlanBorrowTmpByOrderId(String orderId);

    int deleteHjhPlanBorrowTmpByAccedeBorrow(String accedeOrderId, String borrowNid);

    int updateHjhPlanBorrowTmpByPK(HjhPlanBorrowTmp hjhPlanBorrowTmp);

    int updateHjhPlanBorrowTmp(HjhPlanBorrowTmp hjhPlanBorrowTmp);
}
