/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.totalinterest;

import com.hyjf.cs.message.service.BaseMessageService;

import java.math.BigDecimal;

/**
 * @author fq
 * @version TotalInvestAndInterestService, v0.1 2018/7/31 11:19
 */
public interface TotalInvestAndInterestService extends BaseMessageService {
    /**
     * 定时更新统计数据
     */
    void updateData();

    /**
     * 累计投资总额
     * @return
     */
    BigDecimal selectTenderSum();

    /**
     * 累计收益
     * @return
     */
    BigDecimal selectInterestSum();

    /**
     * 累计投资笔数
     * @return
     */
    int selectTotalTenderSum();

}
