package com.hyjf.wbs.trade.service;

import com.hyjf.wbs.qvo.FundDetailsQO;
import com.hyjf.wbs.qvo.FundDetailsVO;

import java.util.List;

/**
 * @author cui
 * @version FundDetailsService, v0.1 2019/7/1 9:56
 */
public interface FundDetailsService {
    /**
     * 查询资金明细信息
     * @param fundDetailsQO
     * @return
     */
    List<FundDetailsVO> queryFundDetails(FundDetailsQO fundDetailsQO);
}
