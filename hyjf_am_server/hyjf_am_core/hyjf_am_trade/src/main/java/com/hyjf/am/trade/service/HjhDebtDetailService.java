/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.HjhDebtDetail;

import java.util.List;

/**
 * @author wangjun
 * @version HjhDebtDetailService, v0.1 2018/7/17 10:09
 */
public interface HjhDebtDetailService {
    /**
     * 查询债券详情
     * @param accedeOrderId
     * @return
     */
    List<HjhDebtDetail> selectHjhDebtDetailListByAccedeOrderId(String accedeOrderId);
}
