/**
 * Description:等额本息工具类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 郭勇
 * @version: 1.0
 * Created at: 2015年11月30日 下午3:45:46
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.calculate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 等额本息工具类 概念：等额本息还款，也称定期付息，即借款人每月按相等的金额偿还贷款本息，其中每月贷款利息按月初剩余贷款本金计算并逐月结清。
 * 把按揭贷款的本金总额与利息总额相加，
 * 然后平均分摊到还款期限的每个月中。作为还款人，每个月还给银行固定金额，但每月还款额中的本金比重逐月递增、利息比重逐月递减。
 */

public class AverageCapitalPlusInterestUtils {

	/**
	 * 每月偿还本金和利息
	 * 
	 * 公式：每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 每月偿还本金和利息,不四舍五入，直接截取小数点最后两位
	 */
	public static BigDecimal getPerMonthPrincipalInterest(BigDecimal invest, BigDecimal yearRate, int totalmonth) {
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 12, BigDecimal.ROUND_DOWN);
		BigDecimal monthIncome = invest
				.multiply(monthRate
						.multiply(new BigDecimal(Math.pow(new BigDecimal(1).add(monthRate).doubleValue(), totalmonth))))
				.divide(new BigDecimal(Math.pow(new BigDecimal(1).add(monthRate).doubleValue(), totalmonth))
						.subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_DOWN);
		return monthIncome;
	}

	/**
	 * 每月偿还利息
	 * 
	 * 公式：每月偿还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 每月偿还利息，map<期数,金额>
	 */
	public static Map<Integer, BigDecimal> getPerMonthInterest(BigDecimal invest, BigDecimal yearRate, int totalmonth) {
		Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 12, BigDecimal.ROUND_DOWN);

		// 每月本息
		BigDecimal monthIncome = invest
				.multiply(monthRate
						.multiply(new BigDecimal(Math.pow(new BigDecimal(1).add(monthRate).doubleValue(), totalmonth))))
				.divide(new BigDecimal(Math.pow(new BigDecimal(1).add(monthRate).doubleValue(), totalmonth))
						.subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_DOWN);
		// 每月利息
		BigDecimal monthInterest;
		// 前totalmonth-1月的本金和，目的为算最后一期本金
		BigDecimal principalCount = new BigDecimal(0);
		for (int i = 1; i <= totalmonth; i++) {
			BigDecimal multiply = invest.multiply(monthRate);
			BigDecimal sub = new BigDecimal(Math.pow(new BigDecimal(1).add(monthRate).doubleValue(), totalmonth))
					.subtract(new BigDecimal(Math.pow(new BigDecimal(1).add(monthRate).doubleValue(),
							new BigDecimal(i).subtract(new BigDecimal(1)).doubleValue())));
			monthInterest = multiply.multiply(sub).divide(
					new BigDecimal(Math.pow(new BigDecimal(1).add(monthRate).doubleValue(), totalmonth) - 1), 2,
					BigDecimal.ROUND_DOWN);
			if (i < totalmonth) {
				principalCount = principalCount.add(monthIncome.subtract(monthInterest));
				map.put(i, monthInterest);
			} else if (i == totalmonth) {
				BigDecimal lastMonthprincipal = invest.subtract(principalCount);
				map.put(i, monthIncome.subtract(lastMonthprincipal));
			}
			
		}
		return map;
	}

	/**
	 * 每月偿还本金
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 每月偿还本金 map<期数,金额>
	 */
	public static Map<Integer, BigDecimal> getPerMonthPrincipal(BigDecimal invest, BigDecimal yearRate,
			int totalmonth) {
		double monthRate = yearRate.divide(new BigDecimal(12), 18, BigDecimal.ROUND_DOWN).doubleValue();
		BigDecimal monthIncome = invest.multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))
				.divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 2, BigDecimal.ROUND_DOWN);
		Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalmonth);
		Map<Integer, BigDecimal> mapPrincipal = new HashMap<Integer, BigDecimal>();

		for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
			mapPrincipal.put(entry.getKey(), monthIncome.subtract(entry.getValue()));
		}
		return mapPrincipal;
	}

	/**
	 * 应还总本金
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 应还总本金
	 */
	public static BigDecimal getPrincipalCount(BigDecimal invest, BigDecimal yearRate, int totalmonth) {
		Map<Integer, BigDecimal> monthlyPrincipal = getPerMonthPrincipal(invest, yearRate, totalmonth);
		BigDecimal count = new BigDecimal(0);
		for (Map.Entry<Integer, BigDecimal> entry : monthlyPrincipal.entrySet()) {
			count = count.add(entry.getValue());
		}
		return count;
	}

	/**
	 * 等额本息计算获取还款方式为等额本息的总利息
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 总利息
	 */
	public static BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, int totalmonth) {
		BigDecimal count = new BigDecimal(0);
		Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalmonth);

		for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
			count = count.add(entry.getValue());
		}
		return count;
	}

	/**
	 * 应还本息总和
	 * 
	 * @param invest
	 *            总借款额（贷款本金）
	 * @param yearRate
	 *            年利率
	 * @param month
	 *            还款总月数
	 * @return 应还本息总和
	 */
	public static BigDecimal getPrincipalInterestCount(BigDecimal invest, BigDecimal yearRate, int totalmonth) {
		double monthRate = yearRate.divide(new BigDecimal(12), 18, BigDecimal.ROUND_DOWN).doubleValue();
		BigDecimal perMonthInterest = invest.multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))
				.divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 2, BigDecimal.ROUND_DOWN);
		BigDecimal count = perMonthInterest.multiply(new BigDecimal(totalmonth));
		count = count.setScale(2, BigDecimal.ROUND_DOWN);
		return count;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BigDecimal invest = new BigDecimal(12000); // 本金
		int month = 12;
		BigDecimal yearRate = new BigDecimal(0.15); // 年利率
		BigDecimal perMonthPrincipalInterest = getPerMonthPrincipalInterest(invest, yearRate, month);
		System.out.println("等额本息---每月还款本息：" + perMonthPrincipalInterest);
		Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, month);
		System.out.println("等额本息---每月还款利息：" + mapInterest);
		Map<Integer, BigDecimal> mapPrincipal = getPerMonthPrincipal(invest, yearRate, month);
		System.out.println("等额本息---每月还款本金：" + mapPrincipal);
		BigDecimal count = getInterestCount(invest, yearRate, month);
		System.out.println("等额本息---总利息：" + count);
		BigDecimal principalInterestCount = getPrincipalInterestCount(invest, yearRate, month);
		System.out.println("等额本息---应还本息总和：" + principalInterestCount);
		BigDecimal principalCount = getPrincipalCount(invest, yearRate, month);
		System.out.println("等额本息---应还总本金：" + principalCount);
	}
}
