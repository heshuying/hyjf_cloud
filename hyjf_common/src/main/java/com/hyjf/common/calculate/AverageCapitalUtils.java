/**
 * Description:等额本金工具类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 郭勇
 * @version: 1.0
 * Created at: 2015年12月1日 上午8:38:23
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.calculate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 等额本金工具类
 * 概念：等额本金是指一种贷款的还款方式，是在还款期内把贷款数总额等分，每月偿还同等数额的本金和剩余贷款在该月所产生的利息，这样由于每月的还款本金额固定，
 * 而利息越来越少，借款人起初还款压力较大，但是随时间的推移每月还款数也越来越少。
 */
public class AverageCapitalUtils {

	/**
	 * 每月偿还本金和利息
	 * 
	 * 公式：每月偿还本金=(贷款本金÷还款月数)+(贷款本金-已归还本金累计额)×月利率
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 每月偿还本金和利息,不四舍五入，直接截取小数点最后两位 map<期数,金额>
	 */
	public static Map<Integer, BigDecimal> getPerMonthPrincipalInterest(BigDecimal invest, BigDecimal yearRate,
			int totalMonth) {
		Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
		// 每月本金
		BigDecimal monthPri = invest.divide(new BigDecimal(totalMonth), 18, BigDecimal.ROUND_DOWN);
		// 月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 12, BigDecimal.ROUND_DOWN);

		for (int i = 1; i <= totalMonth; i++) {
			BigDecimal monthRes = monthPri.add(invest
					.subtract(monthPri.multiply(new BigDecimal(i).subtract(new BigDecimal(1)))).multiply(monthRate));
			monthRes = monthRes.setScale(2, BigDecimal.ROUND_DOWN);
			map.put(i, monthRes);
		}
		return map;
	}

	/**
	 * 总本息
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 总本息,不四舍五入，直接截取小数点最后两位 map<期数,金额>
	 */
	public static BigDecimal getPrincipalInterestCount(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		BigDecimal count = new BigDecimal(0);
		// 每月本金
		BigDecimal monthPri = invest.divide(new BigDecimal(totalMonth), 18, BigDecimal.ROUND_DOWN);
		// 月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 12, BigDecimal.ROUND_DOWN);

		for (int i = 1; i <= totalMonth; i++) {
			BigDecimal monthRes = monthPri.add(invest
					.subtract(monthPri.multiply(new BigDecimal(i).subtract(new BigDecimal(1)))).multiply(monthRate));
			count = count.add(monthRes);

		}
		count = count.setScale(2, BigDecimal.ROUND_DOWN);
		return count;
	}

	/**
	 * 每月偿还利息
	 * 
	 * 公式：每月应还利息=剩余本金×月利率=(贷款本金-已归还本金累计额)×月利率
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 每月偿还利息 map<期数,金额>
	 */
	public static Map<Integer, BigDecimal> getPerMonthInterest(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		Map<Integer, BigDecimal> inMap = new HashMap<Integer, BigDecimal>();
		// 每月本金
		BigDecimal monthPri = invest.divide(new BigDecimal(totalMonth), 18, BigDecimal.ROUND_DOWN);
		// 月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 18, BigDecimal.ROUND_DOWN);
		for (int i = 1; i <= totalMonth; i++) {
			BigDecimal monthRes = invest.subtract(monthPri.multiply(new BigDecimal(i).subtract(new BigDecimal(1))))
					.multiply(monthRate);
			monthRes = monthRes.setScale(2, BigDecimal.ROUND_DOWN);
			inMap.put(i, monthRes);
		}
		return inMap;
	}

	/**
	 * 每月偿还本金
	 * 
	 * 公式：每月应还本金=贷款本金÷还款月数
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 每月偿还本金
	 */
	public static BigDecimal getPerMonthPrincipal(BigDecimal invest, int totalMonth) {
		BigDecimal monthlyPrincipal = invest.divide(new BigDecimal(totalMonth), 18, BigDecimal.ROUND_DOWN);
		monthlyPrincipal = monthlyPrincipal.setScale(2, BigDecimal.ROUND_DOWN);
		return monthlyPrincipal;
	}

	/**
	 * 总利息
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 总利息
	 */
	public static BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
		BigDecimal count = new BigDecimal(0);
		Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalMonth);

		for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
			count = count.add(entry.getValue());
		}
		return count;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BigDecimal invest = new BigDecimal(10000); // 本金
		int month = 12;
		BigDecimal yearRate = new BigDecimal(0.15); // 年利率
		Map<Integer, BigDecimal> getPerMonthPrincipalInterest = getPerMonthPrincipalInterest(invest, yearRate, month);
		System.out.println("等额本金---每月本息：" + getPerMonthPrincipalInterest);
		BigDecimal benjin = getPerMonthPrincipal(invest, month);
		System.out.println("等额本金---每月本金:" + benjin);
		Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, month);
		System.out.println("等额本金---每月利息:" + mapInterest);
		BigDecimal count = getInterestCount(invest, yearRate, month);
		System.out.println("等额本金---总利息：" + count);
		BigDecimal piCount = getPrincipalInterestCount(invest, yearRate, month);
		System.out.println("等额本金---总本息：" + piCount);
	}
}
