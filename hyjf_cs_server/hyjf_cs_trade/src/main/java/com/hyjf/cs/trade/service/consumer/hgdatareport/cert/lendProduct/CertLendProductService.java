package com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProduct;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.cert.CertProductUpdateVO;
import com.hyjf.am.vo.trade.cert.CertProductVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.cs.trade.service.consumer.BaseHgCertReportService;

import java.util.List;


/**
 * @author nxl
 */

public interface CertLendProductService extends BaseHgCertReportService {

    /**
     * 查询计划产品信息,组装上报信息
     * @param planNid
     * @return
     */
    JSONArray getPlanProdouct(String planNid,Boolean isOld,String isPlan,JSONArray json);

    /**
     * 获取线上所有计划信息
     * @return
     */
    List<HjhPlanVO> getAllPlanInfo();
    /**
     * 查找未上报的产品信息
     * @return
     */
    List<CertProductVO> selectCertProductList();
    /**
     * 批量更新
     *
     * @param updateVO
     * @return
     */
    Integer updateCertProductBatch(CertProductUpdateVO updateVO);
    /**
     * 组装产品信息历史数据
     * @return
     */
    JSONArray getHistoryDateProduct();

}