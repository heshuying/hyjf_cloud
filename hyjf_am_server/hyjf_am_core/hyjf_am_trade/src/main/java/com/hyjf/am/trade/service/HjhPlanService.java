/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;

import java.util.List;

/**
 * @author fuqiang
 * @version HjhPlanService, v0.1 2018/6/13 17:23
 */
public interface HjhPlanService {
    /**
     * 获取现金贷资产方信息配置
     *
     * @param instCode
     * @return
     */
    List<HjhInstConfig> selectHjhInstConfigByInstCode(String instCode);

    /**
     * 获取标签
     *
     * @param borrowStyle
     * @return
     */
    List<HjhLabel> seleHjhLabelByBorrowStyle(String borrowStyle);
}
