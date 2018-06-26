package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.cs.common.bean.result.WebResult;

import java.util.Map;

/**
 * @Description web端投资
 * @Author sunss
 * @Date 2018/6/24 14:23
 */
public interface BorrowTenderService extends BaseTradeService{

    /**
     * @Description 散标投资
     * @Author sunss
     * @Date 2018/6/24 14:35
     */
    WebResult<Map<String, Object>> borrowTender(TenderRequest tender);
}
