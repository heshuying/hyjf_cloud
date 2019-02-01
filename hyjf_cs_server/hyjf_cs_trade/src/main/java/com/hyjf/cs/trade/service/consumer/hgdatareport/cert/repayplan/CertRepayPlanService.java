package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.repayplan;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;


/**
 * @author nxl
 */

public interface CertRepayPlanService extends BaseHgCertReportService {
    /**
     * 获取标的的还款信息
     * @param borrowNid
     * @param json
     * @param isOld
     * @return
     */
    JSONArray getBorrowReyapPlan(String borrowNid, JSONArray json, boolean isOld);


}