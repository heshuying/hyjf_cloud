package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.service.BaseService;

import java.math.BigDecimal;

/**
 * @author fuqiang
 * @version BorrowUserStatisticsService, v0.1 2018/11/20 14:23
 */
public interface BorrowUserStatisticsService extends BaseService {
    /**
     *累计借款人
     * @return
     */
    int countBorrowUser();

    /**
     * 当前借款人
     * @return
     */
    int countCurrentBorrowUser();

    /**
     * 当前出借人
     * @return
     */
    int countCurrentTenderUser();

    /**
     * 代还总金额
     * @return
     */
    BigDecimal sumBorrowUserMoney();

    /**
     * 前十大借款人待还金额
     * @return
     */
    BigDecimal sumBorrowUserMoneyTopTen();

    /**
     * 最大单一借款人待还金额
     * @return
     */
    BigDecimal sumBorrowUserMoneyTopOne();
}
