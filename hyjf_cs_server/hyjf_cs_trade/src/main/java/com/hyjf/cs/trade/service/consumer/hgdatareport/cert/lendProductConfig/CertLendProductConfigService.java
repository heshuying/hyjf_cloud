package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProductConfig;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;


/**
 * @author nxl
 */

public interface CertLendProductConfigService extends BaseHgCertReportService {

    /**
     * 查询计划产品配置,组装上报信息
     * @param planNid
     * @return
     */
    JSONArray getPlanProdouct(String planNid);
}