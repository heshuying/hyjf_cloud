package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProduct;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;


/**
 * @author nxl
 */

public interface CertLendProductService extends BaseHgCertReportService {

    /**
     * 查询计划产品信息,组装上报信息
     * @param planNid
     * @return
     */
    JSONArray getPlanProdouct(String planNid);
    /**
     * 获取所有智投信息，组装上报数据
     * @return
     */
    JSONArray getAllPlan();
}