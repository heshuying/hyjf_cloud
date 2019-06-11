/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;

/**
 * WBS系统智投信息推送Service
 *
 * @author liuyang
 * @version WbsHjhPlanInfoService, v0.1 2019/4/16 8:47
 */
public interface WbsHjhPlanInfoService extends BaseService {
    /**
     * 获取WBS系统智投信息
     *
     * @return
     */
    List<HjhPlanVO> selectWbsSendHjhPlanList();

    /**
     * WBS系统发送智投信息
     *
     * @param planNid
     * @param productStatus
     * @param productType
     */
    void sendWbsPlanInfoMQ(String planNid, String productStatus, Integer productType) throws MQException;
}
