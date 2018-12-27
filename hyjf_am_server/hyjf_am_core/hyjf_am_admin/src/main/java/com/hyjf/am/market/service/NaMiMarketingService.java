package com.hyjf.am.market.service;

import com.hyjf.am.response.admin.NaMiMarketingResponse;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;
import com.hyjf.am.vo.admin.NaMiMarketingVO;
import com.hyjf.am.vo.admin.PerformanceReturnDetailVO;

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
    List<PerformanceReturnDetailVO> selectNaMiMarketingPerfanceInfo(NaMiMarketingRequest request);

}
