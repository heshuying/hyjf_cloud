package com.hyjf.cs.trade.service.aems.overdue;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.cs.trade.bean.AemsOverdueRequestBean;
import com.hyjf.cs.trade.bean.AemsOverdueResultBean;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @author xiehuili on 2019/3/12.
 */
public interface AemsOverdueService extends BaseTradeService {

    /**
     * 查询逾期相关数据
     * @param requestBean
     * @return
     */
    AemsOverdueResultBean selectRepayOverdue(AemsOverdueRequestBean requestBean);
}
