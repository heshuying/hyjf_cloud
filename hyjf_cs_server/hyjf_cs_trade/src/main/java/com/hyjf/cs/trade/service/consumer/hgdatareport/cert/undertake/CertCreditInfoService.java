package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.undertake;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;

import java.util.List;


/**
 * @author nxl
 */

public interface CertCreditInfoService extends BaseHgCertReportService {
    /**
     * 获取标的的还款信息
     *
     * @param borrwoNid
     * @return
     */
    JSONArray getBorrowTender(String borrwoNid, String flg);

    /**
     * 根据加入计划单号查找加入计划信息
     *
     * @param orderId
     * @return
     */
    HjhAccedeVO getHjhAccedeByOrderId(String orderId);

    /**
     * 获取散标承接信息
     *
     * @param creditTenderList
     * @return
     */
    JSONArray getBorrowCreditTenderInfo(List<CreditTenderVO> creditTenderList, JSONArray json, boolean isOld);

    /**
     * 获取智投承接信息
     *
     * @param hjhDebtCreditTenderList
     * @return
     */
    JSONArray getHjhDebtCreditInfo(List<HjhDebtCreditTenderVO> hjhDebtCreditTenderList, JSONArray json, boolean isOld);

    /**
     * 日期转换,数据存的int10的时间戳
     *
     * @param repayTime
     * @return
     */
    String dateFormatTransformation(String repayTime, String flg);
}