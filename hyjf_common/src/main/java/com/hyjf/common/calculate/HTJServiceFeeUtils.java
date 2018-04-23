/**
 * Description:汇添金服务费工具类
 * Copyright: Copyright (HYJF Corporation)2016
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2016年10月20日 下午1:52:40
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.calculate;

import java.math.BigDecimal;

/**
 * @author 王坤
 */

public class HTJServiceFeeUtils {

	/**
	 * 清算服务费率的计算 清算服务费率=（到期公允价值+可用余额+冻结金额-计划加入金额-计划预期收益 ）/到期公允价值*100
	 * 
	 * @Title calculdateServiceFeeRate
	 * @param accedeAccount
	 *            加入金额
	 * @param accedeBalance
	 *            可用余额
	 * @param accedeFrost
	 *            加入冻结
	 * @param expectInterest
	 *            预期收益
	 * @param expireFairValue
	 *            到期公允价值
	 * @return
	 */
	public static BigDecimal calculdateServiceFeeRate(BigDecimal accedeAccount, BigDecimal accedeBalance, BigDecimal accedeFrost, BigDecimal expectInterest, BigDecimal expireFairValue) {
		BigDecimal serviceFeeRate = BigDecimal.ZERO;
		BigDecimal surplusValue = expireFairValue.add(accedeBalance).add(accedeFrost).subtract(accedeAccount).subtract(expectInterest);
		if (surplusValue.compareTo(BigDecimal.ZERO) > 0) {
			serviceFeeRate = surplusValue.divide(expireFairValue, 8, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN);
		}
		return serviceFeeRate;

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

	/**
	 * 按天计息到期还本还息，待收收益计算 总利息=出让前的待收收益*（承接本金÷出让前的待收本金
	 * 
	 * @param sellerInterest
	 *            出让前的待收收益
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getEndDayAssignInterest(BigDecimal sellerInterest, BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal assignCapital) {

		BigDecimal assignInterest = BigDecimal.ZERO;
		if (sellerCapitalWait.compareTo(assignCapital) == 0) {
			assignInterest = sellerInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterest = sellerInterest.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
			assignInterest = assignInterest.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterest;
	}

	/**
	 * 按天计息到期还本还息，垫付利息计算 垫付利息=出让前的待收收益*（承接本金÷出让前的待收本金）*（持有天数÷出让债权总天数）
	 * 
	 * @param sellerInterest
	 *            出让前的待收收益
	 * @param sellerInterestAdvanceWait
	 *            待承接垫付利息
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param sellerCapitalWait
	 *            剩余待承接本金
	 * @param sellerHoldDays
	 *            持有天数
	 * @param sellerTotalDays
	 *            总天数
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getEndDayAssignInterestAdvance(BigDecimal sellerInterest, BigDecimal sellerInterestAdvanceWait, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal sellerHoldDays, BigDecimal sellerTotalDays, BigDecimal assignCapital) {

		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		if (sellerHoldDays.compareTo(BigDecimal.ZERO) > 0) {
			if (sellerCapitalWait.compareTo(assignCapital) == 0) {
				assignInterestAdvance = sellerInterestAdvanceWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestAdvance = sellerInterest.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN)).multiply(sellerHoldDays.divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN));
				assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestAdvance;
	}

	/**
	 * 按天计息到期还本还息，延期利息计算 延期利息= 待收收益*延期天数/项目天数*（承接本金/出让人总出让本金）
	 * 
	 * @param sellerPeriodInterest
	 * @param delayDays
	 * @param sellerTotalDays
	 * @param assignCapital
	 * @param sellerCapital
	 * @param sellerCapitalWait
	 * @param sellerDelayInterestWait
	 * @return
	 */
	public static BigDecimal getEndDayAssignRepayInterestDelay(BigDecimal sellerPeriodInterest, BigDecimal delayDays, BigDecimal sellerTotalDays, BigDecimal assignCapital, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal sellerDelayInterestWait) {

		BigDecimal assignInterestDelay = BigDecimal.ZERO;
		if (delayDays.compareTo(BigDecimal.ZERO) > 0) {
			if (sellerCapitalWait.compareTo(assignCapital) == 0) {
				assignInterestDelay = sellerDelayInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestDelay = sellerPeriodInterest.multiply(delayDays).divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN).multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
				assignInterestDelay = assignInterestDelay.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestDelay;
	}

	/**
	 * 按天计息到期还本还息，逾期利息计算 逾期利息= 待收收益*延期天数/项目天数*（承接本金/出让人总出让本金）
	 * 
	 * @param sellerPeriodInterest
	 * @param delayDays
	 * @param sellerTotalDays
	 * @param assignCapital
	 * @param sellerCapital
	 * @param sellerLateInterestWait
	 * @param sellerCapitalWait
	 * @return
	 */
	public static BigDecimal getEndDayAssignRepayInterestLate(BigDecimal sellerPeriodInterest, BigDecimal lateDays, BigDecimal sellerTotalDays, BigDecimal assignCapital, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal sellerLateInterestWait) {

		BigDecimal assignInterestLate = BigDecimal.ZERO;
		if (lateDays.compareTo(BigDecimal.ZERO) > 0) {
			if (sellerCapitalWait.compareTo(assignCapital) == 0) {
				assignInterestLate = sellerLateInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestLate = sellerPeriodInterest.multiply(lateDays).divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN).multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
				assignInterestLate = assignInterestLate.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestLate;
	}

	/**
	 * 
	 * @param sellerInterest
	 * @param sellerInterestWait
	 * @param sellerCapital
	 * @param sellerCapitalWait
	 * @param assignCapital
	 * @return
	 */
	public static BigDecimal getEndAssignInterest(BigDecimal sellerInterest, BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal assignCapital) {
		BigDecimal assignInterest = BigDecimal.ZERO;
		if (sellerCapitalWait.compareTo(assignCapital) == 0) {
			assignInterest = sellerInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterest = sellerInterest.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
			assignInterest = assignInterest.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterest;
	}

	/**
	 * 按月计息到期还本还息计算垫付利息
	 * 
	 * @param sellerInterest
	 * @param sellerInterestAdvanceWait
	 * @param sellerCapital
	 * @param sellerCapitalWait
	 * @param sellerHoldDays
	 * @param sellerTotalDays
	 * @param assignCapital
	 * @return
	 */
	public static BigDecimal getEndAssignInterestAdvance(BigDecimal sellerInterest, BigDecimal sellerInterestAdvanceWait, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal sellerHoldDays, BigDecimal sellerTotalDays, BigDecimal assignCapital) {
		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		if (sellerCapitalWait.compareTo(assignCapital) == 0) {
			assignInterestAdvance = sellerInterestAdvanceWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterestAdvance = sellerInterest.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN)).multiply(sellerHoldDays).divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN);
			assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterestAdvance;
	}

	/**
	 * 先息后本，待收收益计算 总利息=出让前的待收收益*（承接本金÷出让前的待收本金
	 * 
	 * @param sellerInterest
	 *            出让前的待收收益
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getEndMonthAssignInterest(BigDecimal sellerInterest, BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal assignCapital) {

		BigDecimal assignInterest = BigDecimal.ZERO;
		if (sellerCapitalWait.compareTo(assignCapital) == 0) {
			assignInterest = sellerInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterest = sellerInterest.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
			assignInterest = assignInterest.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterest;
	}

	/**
	 * 先息后本，垫付利息计算 垫付利息=出让前的待收收益*（承接本金÷出让前的待收本金）*（持有天数÷出让债权总天数）
	 * 
	 * @param sellerInterest
	 *            出让前的待收收益
	 * @param sellerInterestAdvanceWait
	 *            待承接垫付利息
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param sellerCapitalWait
	 *            剩余待承接本金
	 * @param sellerHoldDays
	 *            持有天数
	 * @param sellerTotalDays
	 *            总天数
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getEndMonthAssignInterestAdvance(BigDecimal sellerInterest, BigDecimal sellerInterestAdvanceWait, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal sellerHoldDays, BigDecimal sellerTotalDays, BigDecimal assignCapital) {

		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		if (sellerHoldDays.compareTo(BigDecimal.ZERO) > 0) {
			if (sellerCapitalWait.compareTo(assignCapital) == 0) {
				assignInterestAdvance = sellerInterestAdvanceWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestAdvance = sellerInterest.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN)).multiply(sellerHoldDays.divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN));
				assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestAdvance;
	}

	/**
	 * 等额本金，待收收益计算 总利息=出让前的待收收益*（承接本金÷出让前的待收本金
	 * 
	 * @param sellerInterest
	 *            出让前的待收收益
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getPrincipalAssignInterest(BigDecimal sellerInterest, BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal assignCapital) {

		BigDecimal assignInterest = BigDecimal.ZERO;
		if (sellerCapitalWait.compareTo(assignCapital) == 0) {
			assignInterest = sellerInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterest = sellerInterest.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
			assignInterest = assignInterest.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterest;
	}

	/**
	 * 等额本金，垫付利息计算 垫付利息=出让前的待收收益*（承接本金÷出让前的待收本金）*（持有天数÷出让债权总天数）
	 * 
	 * @param sellerInterest
	 *            出让前的待收收益
	 * @param sellerInterestAdvanceWait
	 *            待承接垫付利息
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param sellerCapitalWait
	 *            剩余待承接本金
	 * @param sellerHoldDays
	 *            持有天数
	 * @param sellerTotalDays
	 *            总天数
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getPrincipalAssignInterestAdvance(BigDecimal sellerInterest, BigDecimal sellerInterestAdvanceWait, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal sellerHoldDays, BigDecimal sellerTotalDays, BigDecimal assignCapital) {

		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		if (sellerHoldDays.compareTo(BigDecimal.ZERO) > 0) {
			if (sellerCapitalWait.compareTo(assignCapital) == 0) {
				assignInterestAdvance = sellerInterestAdvanceWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestAdvance = sellerInterest.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN)).multiply(sellerHoldDays.divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN));
				assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestAdvance;
	}

	/**
	 * 等额本息，待收收益计算 总利息=出让前的待收收益*（承接本金÷出让前的待收本金
	 * 
	 * @param sellerInterest
	 *            出让前的待收收益
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getMonthAssignInterest(BigDecimal sellerInterest, BigDecimal sellerInterestWait, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal assignCapital) {

		BigDecimal assignInterest = BigDecimal.ZERO;
		if (sellerCapitalWait.compareTo(assignCapital) == 0) {
			assignInterest = sellerInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterest = sellerInterest.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
			assignInterest = assignInterest.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterest;
	}

	/**
	 * 等额本息，垫付利息计算 垫付利息=出让前的待收收益*（承接本金÷出让前的待收本金）*（持有天数÷出让债权总天数）
	 * 
	 * @param sellerInterest
	 *            出让前的待收收益
	 * @param sellerInterestAdvanceWait
	 *            待承接垫付利息
	 * @param sellerCapital
	 *            出让前的待收本金
	 * @param sellerCapitalWait
	 *            剩余待承接本金
	 * @param sellerHoldDays
	 *            持有天数
	 * @param sellerTotalDays
	 *            总天数
	 * @param assignCapital
	 *            承接本金
	 * @return
	 */
	public static BigDecimal getMonthAssignInterestAdvance(BigDecimal sellerInterest, BigDecimal sellerInterestAdvanceWait, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal sellerHoldDays, BigDecimal sellerTotalDays, BigDecimal assignCapital) {

		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		if (sellerHoldDays.compareTo(BigDecimal.ZERO) > 0) {
			if (sellerCapitalWait.compareTo(assignCapital) == 0) {
				assignInterestAdvance = sellerInterestAdvanceWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestAdvance = sellerInterest.multiply(assignCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN)).multiply(sellerHoldDays.divide(sellerTotalDays, 8, BigDecimal.ROUND_DOWN));
				assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestAdvance;
	}

	/**
	 * 等额本息计算延期利息
	 * 
	 * @param sellerInterest
	 * @param sellerInterestAdvanceWait
	 * @param sellerCapital
	 * @param sellerCapitalWait
	 * @param sellerHoldDays
	 * @param sellerTotalDays
	 * @param assignCapital
	 * @return
	 */
	public static BigDecimal getMonthAssignRepayInterestDelay(BigDecimal sellerPeriodInterest, BigDecimal delayDays, BigDecimal sellerPeriodTotalDays, BigDecimal assignCapital, BigDecimal assignPeriodCapital, BigDecimal sellerPeriodCapital, BigDecimal sellerCapitalWait,
			BigDecimal sellerPeriodDelayInterestWait) {
		BigDecimal assignInterestDelay = BigDecimal.ZERO;
		if (delayDays.compareTo(BigDecimal.ZERO) > 0) {
			if (assignCapital.compareTo(sellerCapitalWait) == 0) {
				assignInterestDelay = sellerPeriodDelayInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestDelay = sellerPeriodInterest.multiply(delayDays).divide(sellerPeriodTotalDays, 8, BigDecimal.ROUND_DOWN).multiply(assignPeriodCapital.divide(sellerPeriodCapital, 8, BigDecimal.ROUND_DOWN));
				assignInterestDelay = assignInterestDelay.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestDelay;
	}

	/**
	 * 等额本息计算逾期利息
	 * 
	 * @param sellerInterest
	 * @param sellerInterestAdvanceWait
	 * @param sellerCapital
	 * @param sellerCapitalWait
	 * @param sellerHoldDays
	 * @param sellerTotalDays
	 * @param assignCapital
	 * @return
	 */
	public static BigDecimal getMonthAssignRepayInterestLate(BigDecimal sellerPeriodInterest, BigDecimal lateDays, BigDecimal sellerPeriodTotalDays, BigDecimal assignCapital, BigDecimal assignPeriodCapital, BigDecimal sellerPeriodCapital, BigDecimal sellerCapitalWait,
			BigDecimal sellerPeriodLateInterestWait) {
		BigDecimal assignInterestLate = BigDecimal.ZERO;
		if (lateDays.compareTo(BigDecimal.ZERO) > 0) {
			if (assignCapital.compareTo(sellerCapitalWait) == 0) {
				assignInterestLate = sellerPeriodLateInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestLate = sellerPeriodInterest.multiply(lateDays).divide(sellerPeriodTotalDays, 8, BigDecimal.ROUND_DOWN).multiply(assignPeriodCapital.divide(sellerPeriodCapital, 8, BigDecimal.ROUND_DOWN));
				assignInterestLate = assignInterestLate.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestLate;
	}

	/**
	 * 计算承接时分期的本金
	 * 
	 * @param assignTotalCapital
	 * @param sellerCapital
	 * @param sellerPeriodCapital
	 * @param sellerPeriodCapitalWait
	 * @param sellerPeriodCapitalWait2
	 * @return
	 */
	public static BigDecimal getAssignPeriodCapital(BigDecimal assignTotalCapital, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal sellerPeriodCapital, BigDecimal sellerPeriodCapitalWait) {
		BigDecimal assignPeriodCapital = BigDecimal.ZERO;
		if (assignTotalCapital.compareTo(sellerCapitalWait) == 0) {
			assignPeriodCapital = sellerPeriodCapitalWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignPeriodCapital = sellerPeriodCapital.multiply(assignTotalCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
			assignPeriodCapital = assignPeriodCapital.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignPeriodCapital;
	}

	/**
	 * 计算承接时分期的利息
	 * 
	 * @param assignTotalCapital
	 * @param sellerCapital
	 * @param sellerCapitalWait
	 * @param sellerPeriodCapital
	 * @param sellerPeriodInterest
	 * @param sellerPeriodInterestWait
	 * @return
	 */
	public static BigDecimal getAssignPeriodInterest(BigDecimal assignTotalCapital, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal sellerPeriodCapital, BigDecimal sellerPeriodInterest, BigDecimal sellerPeriodInterestWait) {
		BigDecimal assignPeriodInterest = BigDecimal.ZERO;
		if (assignTotalCapital.compareTo(sellerCapitalWait) == 0) {
			assignPeriodInterest = sellerPeriodInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignPeriodInterest = sellerPeriodInterest.multiply(assignTotalCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
			assignPeriodInterest = assignPeriodInterest.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignPeriodInterest;
	}

	/**
	 * 先息后本计算延期利息
	 * 
	 * @param sellerInterest
	 * @param sellerInterestAdvanceWait
	 * @param sellerCapital
	 * @param sellerCapitalWait
	 * @param sellerHoldDays
	 * @param sellerTotalDays
	 * @param assignCapital
	 * @param sellerPeriodDelayInterestWait
	 * @param sellerCapitalWait
	 * @param sellerPeriodDelayInterestWait2
	 * @param sellerPeriodDelayInterestWait
	 * @return
	 */
	public static BigDecimal getEndMonthAssignRepayInterestDelay(BigDecimal sellerPeriodInterest, BigDecimal delayDays, BigDecimal sellerPeriodTotalDays, BigDecimal assignCapital, BigDecimal assignPeriodCapital, BigDecimal sellerPeriodCapital, BigDecimal sellerCapitalWait,
			BigDecimal sellerPeriodDelayInterestWait) {
		BigDecimal assignInterestDelay = BigDecimal.ZERO;
		if (delayDays.compareTo(BigDecimal.ZERO) > 0) {
			if (assignCapital.compareTo(sellerCapitalWait) == 0) {
				assignInterestDelay = sellerPeriodDelayInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestDelay = sellerPeriodInterest.multiply(delayDays).divide(sellerPeriodTotalDays, 8, BigDecimal.ROUND_DOWN).multiply(assignPeriodCapital.divide(sellerPeriodCapital, 8, BigDecimal.ROUND_DOWN));
				assignInterestDelay = assignInterestDelay.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestDelay;
	}

	/**
	 * 先息后本计算逾期利息
	 * 
	 * @param sellerInterest
	 * @param sellerInterestAdvanceWait
	 * @param sellerCapital
	 * @param sellerCapitalWait
	 * @param sellerHoldDays
	 * @param sellerTotalDays
	 * @param assignCapital
	 * @param sellerPeriodLateInterestWait
	 * @param sellerCapitalWait
	 * @param sellerPeriodCapital
	 * @return
	 */
	public static BigDecimal getEndMonthAssignRepayInterestLate(BigDecimal sellerPeriodInterest, BigDecimal lateDays, BigDecimal sellerPeriodTotalDays, BigDecimal assignCapital, BigDecimal assignPeriodCapital, BigDecimal sellerPeriodCapital, BigDecimal sellerCapitalWait,
			BigDecimal sellerPeriodLateInterestWait) {
		BigDecimal assignInterestLate = BigDecimal.ZERO;
		if (lateDays.compareTo(BigDecimal.ZERO) > 0) {
			if (assignCapital.compareTo(sellerCapitalWait) == 0) {
				assignInterestLate = sellerPeriodLateInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestLate = sellerPeriodInterest.multiply(lateDays).divide(sellerPeriodTotalDays, 8, BigDecimal.ROUND_DOWN).multiply(assignPeriodCapital.divide(sellerPeriodCapital, 8, BigDecimal.ROUND_DOWN));
				assignInterestLate = assignInterestLate.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestLate;
	}

	/**
	 * 计算承接时分期的垫付利息
	 * 
	 * @param assignTotalCapital
	 * @param sellerCapital
	 * @param sellerCapitalWait
	 * @param sellerPeriodCapital
	 * @param sellerPeriodInterest
	 * @param sellerPeriodInterestWait
	 * @return
	 */
	public static BigDecimal getAssignPeriodAdvanceInterest(BigDecimal assignTotalCapital, BigDecimal sellerCapital, BigDecimal sellerCapitalWait, BigDecimal sellerPeriodCapital, BigDecimal sellerPeriodInterest, BigDecimal sellerPeriodInterestWait) {
		BigDecimal assignPeriodInterest = BigDecimal.ZERO;
		if (assignTotalCapital.compareTo(sellerCapitalWait) == 0) {
			assignPeriodInterest = sellerPeriodInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignPeriodInterest = sellerPeriodInterest.multiply(assignTotalCapital.divide(sellerCapital, 8, BigDecimal.ROUND_DOWN));
			assignPeriodInterest = assignPeriodInterest.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignPeriodInterest;
	}

	/**
	 * 到期公允价值=预期收益*（计划清算日-该项目放款日）/项目天数+投资本金（承接本金）
	 * 
	 * @Title calculationExpireFairValue
	 * @param capital
	 * @param expectInterest
	 * @param holdDays
	 * @param totalDays
	 * @param lateInterest
	 * @param delayInterest
	 * @return
	 */
	public static BigDecimal calculationExpireFairValue(BigDecimal capital, BigDecimal expectInterest, BigDecimal holdDays, BigDecimal totalDays, BigDecimal delayInterest, BigDecimal lateInterest) {
		BigDecimal expireFairValue = BigDecimal.ZERO;
		expireFairValue = capital.add(expectInterest.multiply(holdDays.divide(totalDays, 8, BigDecimal.ROUND_DOWN))).add(delayInterest).add(lateInterest);
		expireFairValue = expireFairValue.setScale(2, BigDecimal.ROUND_DOWN);

		return expireFairValue;
	}

	/**
	 * 分期项目计算到期公允价值
	 * 
	 * @Title calculationMonthExpireFairValue
	 * @param capital
	 * @param interest
	 * @param holdDays
	 * @param totalDays
	 * @param lateInterest
	 * @param delayInterest
	 * @return
	 */
	public static BigDecimal calculationMonthExpireFairValue(BigDecimal capital, BigDecimal interest, BigDecimal holdDays, BigDecimal totalDays, BigDecimal delayInterest, BigDecimal lateInterest) {
		BigDecimal expireFairValue = BigDecimal.ZERO;
		expireFairValue = capital.add(interest.multiply(holdDays.divide(totalDays, 8, BigDecimal.ROUND_DOWN))).add(delayInterest).add(lateInterest);
		expireFairValue = expireFairValue.setScale(2, BigDecimal.ROUND_DOWN);
		return expireFairValue;
	}

	public static BigDecimal getPrincipalAssignInterestDelay(BigDecimal sellerPeriodInterest, BigDecimal delayDays, BigDecimal sellerPeriodTotalDays, BigDecimal assignCapital, BigDecimal assignPeriodCapital, BigDecimal sellerPeriodCapital, BigDecimal sellerCapitalWait,
			BigDecimal sellerPeriodDelayInterestWait) {
		BigDecimal assignInterestDelay = BigDecimal.ZERO;
		if (delayDays.compareTo(BigDecimal.ZERO) > 0) {
			if (assignCapital.compareTo(sellerCapitalWait) == 0) {
				assignInterestDelay = sellerPeriodDelayInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestDelay = sellerPeriodInterest.multiply(delayDays).divide(sellerPeriodTotalDays, 8, BigDecimal.ROUND_DOWN).multiply(assignPeriodCapital.divide(sellerPeriodCapital, 8, BigDecimal.ROUND_DOWN));
				assignInterestDelay = assignInterestDelay.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestDelay;
	}

	public static BigDecimal getPrincipalAssignInterestLate(BigDecimal sellerPeriodInterest, BigDecimal lateDays, BigDecimal sellerPeriodTotalDays, BigDecimal assignCapital, BigDecimal assignPeriodCapital, BigDecimal sellerPeriodCapital, BigDecimal sellerCapitalWait,
			BigDecimal sellerPeriodLateInterestWait) {
		BigDecimal assignInterestLate = BigDecimal.ZERO;
		if (lateDays.compareTo(BigDecimal.ZERO) > 0) {
			if (assignCapital.compareTo(sellerCapitalWait) == 0) {
				assignInterestLate = sellerPeriodLateInterestWait.setScale(2, BigDecimal.ROUND_DOWN);
			} else {
				assignInterestLate = sellerPeriodInterest.multiply(lateDays).divide(sellerPeriodTotalDays, 8, BigDecimal.ROUND_DOWN).multiply(assignPeriodCapital.divide(sellerPeriodCapital, 8, BigDecimal.ROUND_DOWN));
				assignInterestLate = assignInterestLate.setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		return assignInterestLate;
	}
}
