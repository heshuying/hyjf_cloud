/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client;

import com.hyjf.am.vo.datacollect.BorrowUserStatisticVO;
import com.hyjf.am.vo.datacollect.OperationReportEntityVO;

import java.math.BigDecimal;

/**
 * @author fuqiang
 * @version CsMessageClient, v0.1 2018/7/18 13:56
 */
public interface CsMessageClient {
    /**
     * 获取借款用户数据
     * @return
     */
    BorrowUserStatisticVO selectBorrowUserStatistic();

    /**
     * 获取累计投资金额
     *
     * @return
     */
    String getTotalInvestmentAmount();
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

    /**
     * 统计前一个月的数据
     * @param i 时间戳
     * @return
     */
    OperationReportEntityVO getOperationReport(int i);

}
