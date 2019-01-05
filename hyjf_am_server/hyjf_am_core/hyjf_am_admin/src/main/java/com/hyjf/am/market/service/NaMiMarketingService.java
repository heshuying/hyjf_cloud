package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.PerformanceReturnDetail;
import com.hyjf.am.response.admin.NaMiMarketingResponse;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;
import com.hyjf.am.vo.admin.NaMiMarketingVO;
import com.hyjf.am.vo.admin.PerformanceReturnDetailVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version NaMiMarketingService, v0.1 2018/12/26 15:45
 */

public interface NaMiMarketingService {
    /**
     * 查询邀请明细条数
     * @param paraMap
     * @return
     */
    List<Integer> selectNaMiMarketingCount(Map<String, Object> paraMap);
    /**
     * 查询邀请明细列表
     * @param paraMap
     * @return
     */
    List<NaMiMarketingVO> selectNaMiMarketingList(Map<String, Object> paraMap);

    /**
     * 查询业绩返现详情条数
     * @param paraMap
     * @return
     */
    Integer selectNaMiMarketingPerfanceCount(Map<String, Object> paraMap);

    /**
     * 查询业绩返现详情列表
     * @param paraMap
     * @return
     */
    List<NaMiMarketingVO> selectNaMiMarketingPerfanceList(Map<String, Object> paraMap);
    /**
     * 查询业绩返现详情
     * @return
     */
    List<PerformanceReturnDetail> selectNaMiMarketingPerfanceInfo(NaMiMarketingRequest request);

    /**
     * 查询邀请人返现明细 条数
     * @param paraMap
     * @return
     */
    int selectNaMiMarketingRefferCount(Map<String, Object> paraMap);

    List<NaMiMarketingVO> selectNaMiMarketingRefferList(Map<String, Object> paraMap);

    /**
     * 查询邀请人返现统计 条数
     * @param paraMap
     * @return
     */
    int selectNaMiMarketingRefferTotalCount(Map<String, Object> paraMap);

    /**
     * 查询 月份列表
     * @return
     */
    public List<String> selectMonthList();

    /**
     * 查询邀请人返现统计 列表
     * @param paraMap
     * @return
     */
    List<NaMiMarketingVO> selectNaMiMarketingRefferTotalList(Map<String, Object> paraMap);

    /**
     * 查询邀请人返现统计 合计
     * @param paraMap
     * @return
     */
    BigDecimal selectNaMiMarketingRefferTotalAmount(Map<String, Object> paraMap);
}
