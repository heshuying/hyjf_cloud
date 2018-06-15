package com.hyjf.common.util.calculate;

import java.math.BigDecimal;

/**
 * 
 * 汇计划服务费、收益等计算工具类
 */
public class HJHServiceFeeUtils {
	
	/**
	 * 按天计息到期还本还息，垫付利息计算 垫付利息=出让前的待收收益*（承接本金÷出让前的待收本金）*（持有天数÷出让债权总天数）
	 * 
	 * @param sellerInterestWait
	 *            出让人出让前每期的待收收益
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param sellerHoldDays
	 *            持有天数
	 * @param sellerTotalDays
	 *            总天数
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getAssignInterestAdvanceEndDay(BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal assignCapital, BigDecimal sellerHoldDays, BigDecimal sellerTotalDays) {

		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		if (sellerHoldDays.compareTo(BigDecimal.ZERO) <= 0 || sellerCapital.compareTo(BigDecimal.ZERO) <=0 || sellerTotalDays.compareTo(BigDecimal.ZERO) <=0) {
			return assignInterestAdvance;
		}
		
		assignInterestAdvance = sellerInterestWait.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN)).multiply(sellerHoldDays.divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN));
		assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
		
		return assignInterestAdvance;
	}

	
	/**
	 * 按天计息到期还本还息，承接人每期的待收收益=出让人出让前每期的待收收益*（承接本金÷转让本金）
	 * 
	 * @param sellerInterestWait
	 *            出让人出让前每期的待收收益
	 * @param sellerCapital
	 *            转让本金
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getAssignInterestEndDay(BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal assignCapital) {

		BigDecimal assignInterest = BigDecimal.ZERO;
		if (sellerCapital.compareTo(assignCapital) == 0) {
			assignInterest = sellerInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterest = sellerInterestWait.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
			assignInterest = assignInterest.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterest;
	}
	
	/**
	 * 按天计息到期还本还息，延期利息计算 延期利息= 待收收益*延期天数/项目天数*（承接本金/出让人总出让本金）
	 * 
	 * @param sellerInterestWait
	 *            出让人出让前每期的待收收益
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param assignCapital
	 *            承接本金
	 * @param delayDays
	 *            延期天数
	 * @param sellerTotalDays
	 *            总天数


	 * @return
	 */
	public static BigDecimal getAssignRepayInterestDelayEndDay(BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal assignCapital, BigDecimal sellerTotalDays, BigDecimal delayDays) {

		BigDecimal assignInterestDelay = BigDecimal.ZERO;
		if (delayDays.compareTo(BigDecimal.ZERO) <= 0 || sellerCapital.compareTo(BigDecimal.ZERO) <=0 || sellerTotalDays.compareTo(BigDecimal.ZERO) <=0) {
			return assignInterestDelay;
		}

		assignInterestDelay = sellerInterestWait.multiply(delayDays.divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN)).multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
		assignInterestDelay = assignInterestDelay.setScale(2, BigDecimal.ROUND_DOWN);
		return assignInterestDelay;
	}

	/**
	 * 按天计息到期还本还息，逾期利息计算 逾期利息= 待收收益*延期天数/项目天数*（承接本金/出让人总出让本金）
	 * @param sellerInterestWait
	 * @param sellerCapital
	 * @param assignCapital
	 * @param sellerTotalDays
	 * @param lateDays
	 * @return
	 */
	public static BigDecimal getAssignRepayInterestLateEndDay(BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal assignCapital, BigDecimal sellerTotalDays, BigDecimal lateDays) {

		BigDecimal assignInterestLate = BigDecimal.ZERO;
		if (lateDays.compareTo(BigDecimal.ZERO) <= 0 || sellerCapital.compareTo(BigDecimal.ZERO) <=0 || sellerTotalDays.compareTo(BigDecimal.ZERO) <=0) {
			return assignInterestLate;
		}

		assignInterestLate = sellerInterestWait.multiply(lateDays.divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN)).multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
		assignInterestLate = assignInterestLate.setScale(2, BigDecimal.ROUND_DOWN);
		return assignInterestLate;
	}
	
	/**
	 * 按天、按月计息到期还本还息，实际收益率计算  实际收益率=（出让前的待收收益-垫付利息）÷（承接本金+垫付利息）÷剩余天数*360*100%
	 * 注意：若项目有延期/逾期，不要调用该方法，债转后年化收益率=项目年化收益率；
	 * @param sellerInterestWait
	 *            出让人出让前每期的待收收益
	 * @param interestAdvance
	 *            垫付利息
	 * @param remainDays
	 *            剩余天数
	 * @param sellerTotalDays
	 *            总天数
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	/**
	 * @param sellerInterestWait
	 * @param interestAdvance
	 * @param assignCapital
	 * @param remainDays
	 * @return
	 */
	public static BigDecimal getAprActual(BigDecimal sellerInterestWait, BigDecimal interestAdvance, BigDecimal assignCapital, BigDecimal remainDays) {

		BigDecimal aprActual = BigDecimal.ZERO;
		if (remainDays.compareTo(BigDecimal.ZERO) <= 0) {
			return aprActual;
		}
		
		aprActual = (sellerInterestWait.subtract(interestAdvance)).divide((assignCapital.add(interestAdvance)), 8, BigDecimal.ROUND_DOWN)
				.divide(remainDays, 8, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(360)).multiply(new BigDecimal(100));
		aprActual = aprActual.setScale(2, BigDecimal.ROUND_DOWN);
		
		return aprActual;
	}

	
	/**
	 * 按月计息到期还本还息，垫付利息计算 垫付利息=出让前的待收收益*（承接本金÷出让前的待收本金）*（持有天数÷出让债权总天数）
	 * 
	 * @param sellerInterestWait
	 *            出让人出让前每期的待收收益
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param sellerHoldDays
	 *            持有天数
	 * @param sellerTotalDays
	 *            总天数
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getAssignInterestAdvanceEnd(BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal assignCapital, BigDecimal sellerHoldDays, BigDecimal sellerTotalDays) {

		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		if (sellerHoldDays.compareTo(BigDecimal.ZERO) <= 0 || sellerCapital.compareTo(BigDecimal.ZERO) <=0 || sellerTotalDays.compareTo(BigDecimal.ZERO) <=0) {
			return assignInterestAdvance;
		}
		
		assignInterestAdvance = sellerInterestWait.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN)).multiply(sellerHoldDays.divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN));
		assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
		
		return assignInterestAdvance;
	}
	
	/**
	 * 按月计息到期还本还息，承接人每期的待收收益=出让人出让前每期的待收收益*（承接本金÷转让本金）
	 * 
	 * @param sellerInterest
	 *            出让人出让前每期的待收收益
	 * @param sellerCapital
	 *            转让本金
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getAssignInterestEnd(BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal assignCapital) {

		BigDecimal assignInterest = BigDecimal.ZERO;
		if (sellerCapital.compareTo(assignCapital) == 0) {
			assignInterest = sellerInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterest = sellerInterestWait.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
			assignInterest = assignInterest.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterest;
	}


	/**
	 * 计算实际承接本金
	 * 计划订单可投  =债转可投时,实际承接本金 = 债转待承接本金
	 * 计划订单可投!=债转可投时,实际承接本金 = (计划订单可投/（总出让本金+总出让垫付利息）*总出让本金)结果保留2位去尾
	 * @param account
	 *            最大承接金额
	 * @param sellerInterestAdvance2
	 * @param sellerCapital2
	 * @param sellerCapitalWait
	 *            待承接本金
	 * @param sellerInterestAdvanceWait
	 *            待垫付利息
	 * @return
	 */
	public static BigDecimal getAssignCapital(BigDecimal account, BigDecimal sellerCapitalWait, BigDecimal sellerInterestAdvanceWait, BigDecimal sellerCapital, BigDecimal sellerInterestAdvance) {

		BigDecimal assignCapital = BigDecimal.ZERO;
		if (account.compareTo(sellerCapitalWait.add(sellerInterestAdvanceWait)) == 0) {
			assignCapital = sellerCapitalWait;
		} else {
			assignCapital = sellerCapital.multiply(account.divide(sellerCapital.add(sellerInterestAdvance), 16, BigDecimal.ROUND_DOWN));
			assignCapital = assignCapital.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignCapital;
	}
	
	public static void main(String[] args) {
		BigDecimal interestEndDay = getAssignInterestEndDay(new BigDecimal(4.99), new BigDecimal(1000), new BigDecimal(100));
		BigDecimal interestAdvanceEndDay = getAssignInterestAdvanceEndDay(new BigDecimal(4.99), new BigDecimal(1000), new BigDecimal(100), new BigDecimal(10), new BigDecimal(15));
		BigDecimal interestDelayEndDay = getAssignRepayInterestDelayEndDay(new BigDecimal(4.99), new BigDecimal(1000), new BigDecimal(100), new BigDecimal(15), new BigDecimal(5));
		BigDecimal interestLateEndDay = getAssignRepayInterestLateEndDay(new BigDecimal(4.99), new BigDecimal(1000), new BigDecimal(100), new BigDecimal(15), new BigDecimal(16));
		BigDecimal aprActual = getAprActual(new BigDecimal(4.99), new BigDecimal(3.32), new BigDecimal(1000), new BigDecimal(5));
		
		System.out.println("按天计息承接后待收收益：" + interestEndDay);
		System.out.println("按天计息垫付利息：" + interestAdvanceEndDay);
		System.out.println("按天计息延期利息：" + interestDelayEndDay);
		System.out.println("按天计息逾期利息：" + interestLateEndDay);
		System.out.println("按天计息实际收益率：" + aprActual);
	}
	
	/**
	 * 计算本期待收本金
	 * @param assignPay	投资金额（实际支付）
	 * @param liquidationValue	出让债权价值
	 * @param currentPeriodCapital	出让人当期待收本金(不变)
	 * @param currentPeriodCreditCapitalWait	待承接本金（变）
	 * @return
	 */
	public static BigDecimal getCurrentPeriodAssignCapital(BigDecimal assignPay, BigDecimal liquidationValue, BigDecimal currentPeriodCapital, 
									BigDecimal currentPeriodCreditCapitalWait, boolean isLast) {
		BigDecimal assignCapital = BigDecimal.ZERO;
		if (isLast) {
			assignCapital = currentPeriodCreditCapitalWait;
		}else {
			// 待收本金=投资金额（实际支付）/出让债权价值*出让人当期待收本金
			assignCapital = assignPay.multiply(currentPeriodCapital).divide(liquidationValue, 16, BigDecimal.ROUND_DOWN);
			// 如果计算误差问题，承接的本金>待成承接的本金处理（异常还是赋值??） TODO
			if (assignCapital.compareTo(currentPeriodCreditCapitalWait) >= 0) {
				assignCapital = currentPeriodCreditCapitalWait;
			}
			// 小数2位后舍去
			assignCapital = assignCapital.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignCapital;
	}
	
	/**
	 * 计算本期待收收益
	 * @param assignPay	投资金额（实际支付）
	 * @param liquidationValue	出让债权价值
	 * @param currentPeriodInterest	出让人当期待收收益
	 * @param currentPeriodCreditInterestWait	待承接利息（变）
	 * @return
	 */
	public static BigDecimal getCurrentPeriodAssignInterest(BigDecimal assignPay, BigDecimal liquidationValue, BigDecimal currentPeriodInterest, 
									BigDecimal currentPeriodCreditInterestWait, boolean isLast) {

		BigDecimal assignInterest = BigDecimal.ZERO;
		if (isLast) {
			assignInterest = currentPeriodCreditInterestWait;
		}else {
			// 待收收益=投资金额（实际支付）/出让债权价值*出让人当期待收收益
			assignInterest = assignPay.multiply(currentPeriodInterest).divide(liquidationValue, 16, BigDecimal.ROUND_DOWN);
			if (assignInterest.compareTo(currentPeriodCreditInterestWait) >= 0) {
				assignInterest = currentPeriodCreditInterestWait;
			}
			// 小数2位后舍去
			assignInterest = assignInterest.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterest;
	}
}
