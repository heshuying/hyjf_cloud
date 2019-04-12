package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProductConfig;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;


/**
 * @author nxl
 */

public interface CertLendProductConfigService extends BaseHgCertReportService {

    /**
     * 组装产品配置上报信息
     * @param orderId（加入订单号或承接订单号）
     * @param flag（1代表加入智投，2代表承接智投）
     * @return
     */
    JSONArray ProductConfigInfo(String orderId, String flag);
}