/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

import com.hyjf.am.response.BooleanResponse;

/**
 * @author yaoyong
 * @version TenderMatchDaysService, v0.1 2018/12/18 16:34
 */
public interface TenderMatchDaysService {
    /**
     * 计算自动投资的匹配期(每日)
     * @return
     */
    BooleanResponse tenderMatchDays();
}
