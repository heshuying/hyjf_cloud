package com.hyjf.am.trade.dao.mapper.customize.batch;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fuqiang
 * @version BorrowUserStatisticsMapper, v0.1 2018/11/20 14:33
 */
public interface BorrowUserStatisticsMapper {
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
     * 当前投资人
     * @return
     */
    int countCurrentTenderUser();

    /**
     * 代还总金额
     * @return
     */
    BigDecimal sumBorrowUserMoney(Date date);

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
