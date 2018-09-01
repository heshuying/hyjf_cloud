/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.common.util;

import com.hyjf.common.util.calculate.*;

import java.math.BigDecimal;

/**
 * @Description 标的预期收益计算
 * @Author sss
 * @Date 2018/9/1 11:49
 */
public class BorrowEarningsUtil {

    /**
     * 获取项目的预期收益
     * @param money
     * @param borrowPeriod
     * @param borrowStyle
     * @param borrowApr
     * @return
     */
    public static BigDecimal getBorrowEarnings(BigDecimal money, Integer borrowPeriod, String borrowStyle, BigDecimal borrowApr) {
        BigDecimal earnings = BigDecimal.ZERO;
        switch (borrowStyle) {
            // 还款方式为”按月计息，到期还本还息“：历史回报=投资金额*年化收益÷12*月数；
            case CalculatesUtil.STYLE_END:
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getMonthInterest(money, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2,
                        BigDecimal.ROUND_DOWN);
                break;
            // 还款方式为”按天计息，到期还本还息“：历史回报=投资金额*年化收益÷360*天数；
            case CalculatesUtil.STYLE_ENDDAY:
                earnings = DuePrincipalAndInterestUtils.getDayInterest(money, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            // 还款方式为”先息后本“：历史回报=投资金额*年化收益÷12*月数；
            case CalculatesUtil.STYLE_ENDMONTH:
                earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(money, borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod).setScale(2,
                        BigDecimal.ROUND_DOWN);
                break;
            // 还款方式为”等额本息“：历史回报=投资金额*年化收益÷12*月数；
            case CalculatesUtil.STYLE_MONTH:
                earnings = AverageCapitalPlusInterestUtils.getInterestCount(money, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            // 还款方式为”等额本金“
            case CalculatesUtil.STYLE_PRINCIPAL:
                earnings = AverageCapitalUtils.getInterestCount(money, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            default:
                break;
        }
        return earnings;
    }
}
