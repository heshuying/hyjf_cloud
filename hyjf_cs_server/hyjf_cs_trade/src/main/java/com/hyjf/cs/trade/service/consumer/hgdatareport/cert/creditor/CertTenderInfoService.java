package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.creditor;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;


/**
 * @author nxl
 */

public interface CertTenderInfoService extends BaseHgCertReportService {
    /**
     * 获取标的的还款信息
     * @param borrwoNid
     * @return
     */
    JSONArray getBorrowTender(String borrwoNid, JSONArray json, boolean isOld);


}