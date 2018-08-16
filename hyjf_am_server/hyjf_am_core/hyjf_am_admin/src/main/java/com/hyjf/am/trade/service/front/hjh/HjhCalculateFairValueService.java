/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.service.BaseService;

/**
 * 汇计划加入订单计算公允价值Service
 *
 * @author liuyang
 * @version HjhCalculateFairValueService, v0.1 2018/6/27 14:09
 */
public interface HjhCalculateFairValueService extends BaseService {
    /**
     * 根据加入订单号查询
     * @param accedeOrderId
     * @return
     */
    HjhAccede selectHjhAccedeByAccedeOrderId(String accedeOrderId);

    /**
     * 计算计划加入订单的公允价值
     * @param hjhAccede
     * @param calculateType
     */
    void calculateFairValue(HjhAccede hjhAccede, Integer calculateType);
}
