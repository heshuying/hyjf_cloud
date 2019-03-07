/**
 * Description:收取对象：融资方；收取时间：融资方每期还款时，系统将管理费分账到平台的账户，将还款本息分账到出借方的账户；
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 * Created at: 2015年12月3日 上午11:48:37
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.util.calculate;

import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;

/**
 * 账户管理费工具类
 */

public class AccountManagementFeeUtils {

	private static final Logger logger = LoggerFactory.getLogger(AccountManagementFeeUtils.class);

	/**
	 * 按月计息，到期还本还息 账户管理费
	 * 
	 * 公式：管理费=本金金额*每月账户管理费率*融资月数；每月账户管理费率可后台配置；
	 * 
	 * @param invest
	 *            本金金额
	 * @param monthManagementFeeRate
	 *            每月账户管理费率
	 * @param totalmonth
	 *            融资期限
	 * @param return_rate 
	 * @param string 
	 * @return 账户管理费
	 */
	public static BigDecimal getDueAccountManagementFeeByMonth(BigDecimal invest, BigDecimal monthManagementFeeRate,
			int totalmonth,BigDecimal return_rate, int verifyTime) {
	    Timestamp timest;
	    BigDecimal count = BigDecimal.ZERO;
        try {
            timest = GetDate.getTimestamp( GetDate.parseDate("2016-06-06 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            Long time66=timest.getTime()/1000;
            if (verifyTime<time66) {
                //老的计算方法
                 count = invest.multiply(monthManagementFeeRate).multiply(new BigDecimal(totalmonth));
            }else {
                //新的计算方法
                /*  管理费=应还本金金额*每月账户管理费率*融资月数+本金金额*收益差率*融资月数；*/
                count = (invest.multiply(monthManagementFeeRate).multiply(new BigDecimal(totalmonth))).setScale(2, BigDecimal.ROUND_DOWN).add((
                        invest.multiply(return_rate).multiply(new BigDecimal(totalmonth))).setScale(2, BigDecimal.ROUND_DOWN));
            }
            count = count.setScale(2, BigDecimal.ROUND_DOWN);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
		return count;
	}

	/**
	 * 按天计息，到期还本还息 账户管理费
	 * 
	 * 公式：管理费=本金金额*每月账户管理费率*融资月数；每月账户管理费率可后台配置；
	 * 
	 * @param invest
	 *            本金金额
	 * @param dayManagementFeeRate
	 *            每天账户管理费率
	 * @param totalDay
	 *            融资期限
	 * @param return_rate 
	 * @param man_charge_rate 
	 * @param verifyTime 
	 * @return 账户管理费
	 */
	public static BigDecimal getDueAccountManagementFeeByDay(BigDecimal invest, BigDecimal dayManagementFeeRate,
			int totalDay, BigDecimal return_rate, int verifyTime) {
	    Timestamp timest;
        BigDecimal count = BigDecimal.ZERO;
        try {
            timest = GetDate.getTimestamp( GetDate.parseDate("2016-06-06 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            Long time66=timest.getTime()/1000;
            if (verifyTime<time66) {
                //老的计算方法
                 count = invest.multiply(dayManagementFeeRate).multiply(new BigDecimal(totalDay));
            }else {
                //新的计算方法
                /*   管理费=应还本金金额*每天账户管理费率*融资天数+本金金额*收益差率*融资天数；*/
                 count = (invest.multiply(dayManagementFeeRate).multiply(new BigDecimal(totalDay))).setScale(2, BigDecimal.ROUND_DOWN).add((
                        invest.multiply(return_rate).multiply(new BigDecimal(totalDay))).setScale(2, BigDecimal.ROUND_DOWN));
            }
            count = count.setScale(2, BigDecimal.ROUND_DOWN);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return count;
	}

	/**
	 * 等额本息/等额本金 每月账户管理费
	 * 
	 * 公式：管理费=本金金额*每月账户管理费率*当前还款期数（月）；每月账户管理费率可后台配置；
	 * 
	 * @param invest
	 *            每期本金
	 * @param monthManagementFeeRate
	 *            每月账户管理费率
	 * @param month
	 *            第几期
	 * @param return_rate 
	 * 收益差率
	 * @param man_charge_rate 
	 * 管理费率
	 * @param IsLast 
	 * 是否是最后一组  1是 0 不是
	 * @param account 
	 * 总本金
	 * @param borrowPeriod 
	 * @param verifyTime 
	 * @return 账户管理费
	 */
	public static BigDecimal getMonthAccountManagementFee(BigDecimal invest, BigDecimal monthManagementFeeRate,
			int month,BigDecimal return_rate, int IsLast, BigDecimal account, Integer borrowPeriod, int verifyTime) {
	    Timestamp timest;
        BigDecimal count = BigDecimal.ZERO;
        try {
            timest = GetDate.getTimestamp( GetDate.parseDate("2016-06-06 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            Long time66=timest.getTime()/1000;
            if (verifyTime<time66) {
                //老的计算方法
                count = invest.multiply(monthManagementFeeRate).multiply(new BigDecimal(month));
            }else {
                //新的管理费计算方式最后一期加收益差率 
                /* a、除最后一期外，当期账户管理费=当期应还本金金额*每月账户管理费率*当前还款期次
                         b、最后一期的应付管理费=最后一期应还本金金额*每月账户管理费率*当前还款期次+总本金金额*收益差率*融资月数
                     */
                 if (IsLast==1) {
                     count =  (invest.multiply(monthManagementFeeRate).multiply(new BigDecimal(month))).setScale(2, BigDecimal.ROUND_DOWN).add((
                             account.multiply(return_rate).multiply(new BigDecimal(borrowPeriod))).setScale(2, BigDecimal.ROUND_DOWN));   
                 } else {
                      count = invest.multiply(monthManagementFeeRate).multiply(new BigDecimal(month));
                 }
            }
            count = count.setScale(2, BigDecimal.ROUND_DOWN);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return count;
	}
	/**
	 * 汇添金等额本息/等额本金 每月账户管理费
	 * 
	 * 公式：管理费=本金金额*每月账户管理费率*当前还款期数（月）；每月账户管理费率可后台配置；
	 * 
	 * @param invest
	 *            每期本金
	 * @param monthManagementFeeRate
	 *            每月账户管理费率
	 * @param month
	 *            第几期
	 * @param return_rate 
	 * 收益差率
	 * @param man_charge_rate 
	 * 管理费率
	 * @param IsLast 
	 * 是否是最后一组  1是 0 不是
	 * @param account 
	 * 总本金
	 * @param borrowPeriod 
	 * @param verifyTime 
	 * @param managerFee 
	 * @return 账户管理费
	 */
	public static BigDecimal getHTJMonthAccountManagementFee(BigDecimal invest, BigDecimal monthManagementFeeRate,
			int month,BigDecimal return_rate, int IsLast, BigDecimal account, Integer borrowPeriod, int verifyTime, BigDecimal managerFee) {
	    Timestamp timest;
        BigDecimal count = BigDecimal.ZERO;
        try {
            timest = GetDate.getTimestamp( GetDate.parseDate("2016-06-06 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            Long time66=timest.getTime()/1000;
            if (verifyTime<time66) {
                //老的计算方法
                count = invest.multiply(monthManagementFeeRate).multiply(new BigDecimal(month));
            }else {
                //新的管理费计算方式最后一期加收益差率 
                /* a、除最后一期外，当期账户管理费=当期应还本金金额*每月账户管理费率*当前还款期次
                   b、最后一期的应付管理费=出借金额*最后一期总管理费/放款金额
                */
                 if (IsLast==1) {
                     count = invest.multiply(managerFee).divide(account,2, BigDecimal.ROUND_DOWN);
                 } else {
                     count = invest.multiply(monthManagementFeeRate).multiply(new BigDecimal(month));
                 }
            }
            count = count.setScale(2, BigDecimal.ROUND_DOWN);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return count;
	}

	/**
	 * 先息后本 账户管理费
	 * 
	 * 公式：管理费=本金金额*每月账户管理费率*融资月数/还款期数；
	 * 
	 * @param invest
	 *            总本金金额
	 * @param monthManagementFeeRate
	 *            每月账户管理费率
	 * @param totalDay
	 *            融资期限
	 * @param loanTerm
	 *            还款期数
	 * @param IsLast 
	 * 是否是最后的一笔 1是  0不是 
	 * @param return_rate 
	 * 收益差率
	 * @param man_charge_rate 
	 * 管理费率
	 * @param verifyTime 
	 * @return 账户管理费
	 */
	public static BigDecimal getBeforeInterestAfterPrincipalAccountManagementFee(BigDecimal invest,
			BigDecimal monthManagementFeeRate, int totalmonth, int loanTerm, BigDecimal return_rate, int IsLast, int verifyTime) {
	    Timestamp timest;
        BigDecimal count = BigDecimal.ZERO;
        try {
            timest = GetDate.getTimestamp( GetDate.parseDate("2016-06-06 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            Long time66=timest.getTime()/1000;
            if (verifyTime<time66) {
                //老的计算方法
                count = invest.multiply(monthManagementFeeRate).multiply(new BigDecimal(totalmonth))
                        .divide(new BigDecimal(loanTerm), 2, BigDecimal.ROUND_DOWN);
            }else {
                //新的计算方法
                /*  a、除最后一期外，每期账户管理费=最后一期应还本金金额*每月账户管理费率*融资月数÷还款期数
                          b、最后一期账户管理费=最后一期应还本金金额*每月账户管理费*融资月数÷还款期数+最后一期应还本金金额*收益差率*融资月数
                     */
                  if (IsLast==1) {
                      count = (invest.multiply(monthManagementFeeRate).multiply(new BigDecimal(totalmonth))
                              .divide(new BigDecimal(loanTerm), 2, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN).add((
                                      invest.multiply(return_rate).multiply(new BigDecimal(totalmonth))).setScale(2, BigDecimal.ROUND_DOWN) );
                  }else {
                      count = invest.multiply(monthManagementFeeRate).multiply(new BigDecimal(totalmonth))
                              .divide(new BigDecimal(loanTerm), 2, BigDecimal.ROUND_DOWN);
                  }
            }
            count = count.setScale(2, BigDecimal.ROUND_DOWN);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return count;
	}
}
