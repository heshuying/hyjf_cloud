/**
 * Description:到期还本还息工具类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 * Created at: 2015年12月1日 上午10:38:49
 * Modification History:
 * Modified by :
 */

package com.hyjf.common.calculate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * 到期还本还息工具类
 */
public class DuePrincipalAndInterestUtils {

	/**
	 * 到期还本还息（按月计息） 应还利息 公式：应还利息=投资本金*年化收益÷12*融资月数
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 到期还本还息（按月计息） 应还利息
	 */
	public static BigDecimal getMonthInterest(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		// double monthRate = yearRate.divide(new BigDecimal(12), 18,
		// BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal result = invest.multiply(yearRate).multiply(new BigDecimal(totalMonth)).divide(new BigDecimal(12), 18, BigDecimal.ROUND_HALF_UP);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}

	/**
	 * 到期还本还息（按月计息） 应还本息 公式：应还本息=本金+应还总利息=投资本金*【1+（年化收益÷12*融资月数）】；
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 到期还本还息（按月计息） 应还本息
	 */
	public static BigDecimal getMonthPrincipalInterest(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		BigDecimal interest = invest.multiply(yearRate).multiply(new BigDecimal(totalMonth)).divide(new BigDecimal(12), 18, BigDecimal.ROUND_HALF_UP);
		BigDecimal result = invest.add(interest);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}

	/**
	 * 到期还本还息（按天计息） 应还利息 公式：应还利息=投资本金*年化收益÷360*融资天数
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param totalDay
	 *            还款总天数
	 * @return 到期还本还息（按月计息） 应还利息
	 */
	public static BigDecimal getDayInterest(BigDecimal invest, BigDecimal yearRate, int totalDay) {
		BigDecimal result = invest.multiply(yearRate).multiply(new BigDecimal(totalDay)).divide(new BigDecimal(360), 18, BigDecimal.ROUND_DOWN);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}

	/**
	 * 到期还本还息（按天计息） 应还利息 公式：应还利息=投资本金*年化收益÷365*融资天数，体验金是365天 注：体验金专用
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param totalDay
	 *            还款总天数
	 * @return 到期还本还息（按月计息） 应还利息
	 */
	public static BigDecimal getDayInterestExperience(BigDecimal invest, BigDecimal yearRate, int totalDay) {
		BigDecimal result = invest.multiply(yearRate).multiply(new BigDecimal(totalDay)).divide(new BigDecimal(365), 18, BigDecimal.ROUND_DOWN);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}

	/**
	 * 到期还本还息（按月计息） 应还本息 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param totalDay
	 *            还款总天数
	 * @return 到期还本还息（按月计息） 应还本息
	 */
	public static BigDecimal getDayPrincipalInterest(BigDecimal invest, BigDecimal yearRate, int totalDay) {
		BigDecimal interest = invest.multiply(yearRate).multiply(new BigDecimal(totalDay)).divide(new BigDecimal(360), 18, BigDecimal.ROUND_DOWN);
		BigDecimal result = invest.add(interest);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}

	/**
	 * 到期还本还息（按月计息） 垫付利息 垫息总额=债权本金*年化收益÷12*融资期限-债权本金*年化收益÷360*剩余天数
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 到期还本还息（按月计息） 应还本息
	 */
	public static BigDecimal getMonthAssignInterestAdvance(BigDecimal creditCapital, BigDecimal yearRate, Integer totalDay, BigDecimal lastDays) {
		BigDecimal assignInterestAdvance = (creditCapital.multiply(yearRate).multiply(new BigDecimal(totalDay)).divide(new BigDecimal("12"), 18, BigDecimal.ROUND_DOWN)).setScale(2,
				BigDecimal.ROUND_DOWN).subtract((creditCapital.multiply(yearRate).multiply(lastDays.divide(new BigDecimal("360"), 18, BigDecimal.ROUND_DOWN))).setScale(2, BigDecimal.ROUND_DOWN));
		assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
		return assignInterestAdvance;
	}

	/**
	 * 到期还本还息（按天计息） 垫付利息 公式：垫付利息=本金+应还总利息=投资本金*（1*（年化收益÷360*融资天数））；
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 到期还本还息（按月计息） 应还本息
	 */
	public static BigDecimal getDayAssignInterestAdvance(BigDecimal creditCapital, BigDecimal yearRate, Integer totalDay, BigDecimal lastDays) {
		BigDecimal assignInterestAdvance = (creditCapital.multiply(yearRate).multiply(new BigDecimal(totalDay)).divide(new BigDecimal("360"),18,BigDecimal.ROUND_DOWN)).subtract(
				(creditCapital.multiply(yearRate).multiply(lastDays.divide(new BigDecimal("360"), 18, BigDecimal.ROUND_DOWN))).setScale(2, BigDecimal.ROUND_DOWN));
		assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
		return assignInterestAdvance;
	}

	/**
	 * 到期还本还息（按天计息） 垫付利息 公式：垫付利息=本金+应还总利息=投资本金*（1*（年化收益÷360*融资天数））；
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 到期还本还息（按月计息） 应还本息
	 */
	public static BigDecimal getDayAssignInterestAdvanceAfterCredit(BigDecimal creditCapital, BigDecimal sellerCapitalWait, BigDecimal sellerInterestAdvanceWait, BigDecimal yearRate,
			Integer totalDay, BigDecimal lastDays) {
		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		if (sellerCapitalWait.compareTo(creditCapital) == 0) {
			assignInterestAdvance = sellerInterestAdvanceWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterestAdvance = (creditCapital.multiply(yearRate).multiply(new BigDecimal(totalDay)).divide(new BigDecimal("360"),18,BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN).subtract(
					(creditCapital.multiply(yearRate).multiply(lastDays.divide(new BigDecimal("360"), 18, BigDecimal.ROUND_DOWN))).setScale(2, BigDecimal.ROUND_DOWN));
			assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterestAdvance;
	}

	/**
	 * 到期还本还息（按天计息） 应还利息 公式：应还利息=投资本金*年化收益÷360*融资天数
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param totalDay
	 *            还款总天数
	 * @return 到期还本还息（按月计息） 应还利息
	 */
	public static BigDecimal getDayInterestAfterCredit(BigDecimal invest, BigDecimal sellerCapitalWait, BigDecimal sellerInterestWait, BigDecimal yearRate, int totalDay) {
		BigDecimal result = BigDecimal.ZERO;
		if (invest.compareTo(sellerCapitalWait) == 0) {
			result = sellerInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			result = invest.multiply(yearRate).multiply(new BigDecimal(totalDay)).divide(new BigDecimal(360), 18, BigDecimal.ROUND_DOWN);
			result = result.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return result;
	}

	/**
	 * 到期还本还息（按月计息） 应还本息 公式：应还本息=本金+应还总利息=投资本金*【1+（年化收益÷12*融资月数）】；
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 到期还本还息（按月计息） 应还本息
	 */
	public static BigDecimal getMonthPrincipalInterestAfterCredit(BigDecimal invest, BigDecimal sellerCapitalWait, BigDecimal sellerInterestWait, BigDecimal yearRate, int totalMonth) {
		BigDecimal interest = BigDecimal.ZERO;
		BigDecimal result = BigDecimal.ZERO;
		if (sellerCapitalWait.compareTo(invest) == 0) {
			interest = sellerInterestWait;
		} else {
			interest = invest.multiply(yearRate).multiply(new BigDecimal(totalMonth)).divide(new BigDecimal(12), 18, BigDecimal.ROUND_DOWN);
		}
		result = invest.add(interest);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}

	/**
	 * 到期还本还息（按月计息） 应还利息 公式：应还利息=投资本金*年化收益÷12*融资月数
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 到期还本还息（按月计息） 应还利息
	 */
	public static BigDecimal getMonthInterestAfterCredit(BigDecimal invest, BigDecimal sellerCapitalWait, BigDecimal sellerInterestWait, BigDecimal yearRate, int totalMonth) {
		BigDecimal result = BigDecimal.ZERO;
		if (sellerCapitalWait.compareTo(invest) == 0) {
			result = sellerInterestWait;
		} else {
			result = invest.multiply(yearRate).multiply(new BigDecimal(totalMonth)).divide(new BigDecimal(12), 18, BigDecimal.ROUND_DOWN);
		}
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}

	/**
	 * 到期还本还息（按月计息） 垫付利息 垫息总额=债权本金*年化收益÷12*融资期限-债权本金*年化收益÷360*剩余天数
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 到期还本还息（按月计息） 应还本息
	 */
	public static BigDecimal getMonthAssignInterestAdvanceAfterCredit(BigDecimal creditCapital, BigDecimal sellerCapitalWait, BigDecimal sellerInterestAdvanceWait, BigDecimal yearRate,
			Integer totalDay, BigDecimal lastDays) {
		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		if (creditCapital.compareTo(sellerCapitalWait) == 0) {
			assignInterestAdvance = sellerInterestAdvanceWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterestAdvance = (creditCapital.multiply(yearRate).multiply(new BigDecimal(totalDay)).divide(new BigDecimal("12"), 18, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN)
					.subtract((creditCapital.multiply(yearRate).multiply(lastDays.divide(new BigDecimal("360"), 18, BigDecimal.ROUND_DOWN))).setScale(2, BigDecimal.ROUND_DOWN));
			assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterestAdvance;
	}

	/**
	 * 到期还本还息（按月计息） 应还本息 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
	 *
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param totalDay
	 *            还款总天数
	 * @return 到期还本还息（按月计息） 应还本息
	 */
	public static BigDecimal getDayPrincipalInterestAfterCredit(BigDecimal invest, BigDecimal sellerCapitalWait, BigDecimal sellerInterestWait, BigDecimal yearRate, int totalDay) {
		BigDecimal interest = BigDecimal.ZERO;
		BigDecimal result = BigDecimal.ZERO;
		if (sellerCapitalWait.compareTo(invest) == 0) {
			result = invest.add(sellerInterestWait);
		} else {
			interest = invest.multiply(yearRate).multiply(new BigDecimal(totalDay)).divide(new BigDecimal(360), 18, BigDecimal.ROUND_DOWN);
			result = invest.add(interest);
		}
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}

	public static void main(String[] args) {
		BigDecimal invest = new BigDecimal(6000); // 本金
		int month = 4;
		BigDecimal yearRate = new BigDecimal(0.1231); // 年利率
		int totalDay = 0;
		BigDecimal monthI = getMonthInterest(invest, yearRate, month);
		System.out.println("到期还本还息（按月计息） 还款利息:" + monthI);
		BigDecimal monthPI = getMonthPrincipalInterest(invest, yearRate, month);
		System.out.println("到期还本还息（按月计息） 还款本息:" + monthPI);
		System.out.println("-------------------------------------");
		BigDecimal dayI = getDayInterest(invest, yearRate, totalDay);
		System.out.println("到期还本还息（按日计息） 还款利息:" + dayI);
		BigDecimal dayPI = getDayPrincipalInterest(invest, yearRate, totalDay);
		System.out.println("到期还本还息（按日计息） 还款本息:" + dayPI);

	}

}
