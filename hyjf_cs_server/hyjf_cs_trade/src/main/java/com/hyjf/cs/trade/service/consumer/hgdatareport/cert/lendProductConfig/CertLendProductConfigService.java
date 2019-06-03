package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProductConfig;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.cert.CertClaimUpdateVO;
import com.hyjf.am.vo.trade.cert.CertClaimVO;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;

import java.util.List;


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
    JSONArray productConfigInfo(String orderId, String isTender,String flag);
    /**
     * 查找产品配置信息历史数据
     * @return
     */
    JSONArray getHistoryDate();

    BorrowTenderVO selectBorrowTenderByOrderId(String orderId);

     List<CertClaimVO> getCertBorrowNoConfig();
    /**
     * 批量更新
     * @param updateVO
     * @return
     */
    Integer updateCertBorrowStatusBatch(CertClaimUpdateVO updateVO);
}