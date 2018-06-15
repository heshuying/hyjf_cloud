/**
 * Description:先息后本工具类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 郭勇
 * @version: 1.0
 * Created at: 2015年12月1日 上午11:20:58
 * Modification History:
 * Modified by :
 */

package com.hyjf.common.util.calculate;

import java.math.BigDecimal;

/**
 * 先息后款工具类
 */
public class BeforeInterestAfterPrincipalUtils {

	/**
	 * 先息后本 公式：每月应还利息=投资本金*年化收益率/12*投资月数/还款期数
	 *
	 * @param invest
	 *            投资本金
	 * @param yearRate
	 *            年化收益率
	 * @param investMonth
	 *            投资月数
	 * @param repayTerm
	 *            还款期数
	 * @return 每期利息
	 */
	public static BigDecimal getPerTermInterest(BigDecimal invest, BigDecimal yearRate, int investMonth, int repayTerm) {
		BigDecimal interestBigDecimal = invest.multiply(yearRate).multiply(new BigDecimal(investMonth)).divide(new BigDecimal(repayTerm).multiply(new BigDecimal(12)), 8, BigDecimal.ROUND_DOWN);
		interestBigDecimal = interestBigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
		return interestBigDecimal;
	}

	/**
	 * 总利息
	 *
	 * @param invest
	 *            投资本金
	 * @param yearRate
	 *            年化收益率
	 * @param investMonth
	 *            投资月数
	 * @param repayTerm
	 *            还款期数
	 * @return 总利息
	 */
	public static BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, int investMonth, int repayTerm) {

		BigDecimal interestBigDecimal = invest.multiply(yearRate).multiply(new BigDecimal(investMonth)).multiply(new BigDecimal(repayTerm))
				.divide(new BigDecimal(repayTerm).multiply(new BigDecimal(12)), 8, BigDecimal.ROUND_DOWN);
		interestBigDecimal = interestBigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
		return interestBigDecimal;
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
	public static BigDecimal getPrincipalInterestCount(BigDecimal invest, BigDecimal yearRate, int investMonth, int repayTerm) {

		BigDecimal interestBigDecimal = invest.multiply(yearRate).multiply(new BigDecimal(investMonth)).divide(new BigDecimal(repayTerm).multiply(new BigDecimal(12)), 8, BigDecimal.ROUND_DOWN);
		BigDecimal count = interestBigDecimal.multiply(new BigDecimal(repayTerm)).add(invest);
		count = count.setScale(2, BigDecimal.ROUND_DOWN);
		return count;
	}

	/**
     * 垫付利息 垫息总额=投资人认购本金/出让人转让本金*出让人本期利息）-（债权本金*年化收益÷360*本期剩余天数
     *
     * @return 应还本息总和
     */
    public static BigDecimal getAssignInterestAdvance(BigDecimal investCapital, BigDecimal creditCapital, BigDecimal yearRate, BigDecimal interest, BigDecimal lastDays) {
        BigDecimal assignInterestAdvance = (investCapital.multiply(interest).divide(creditCapital, 18, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN).subtract(
                (investCapital.multiply(yearRate).multiply(lastDays).divide(new BigDecimal("360"), 18, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN));
        assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);


		/**
		 * 这里有一个问题，比如投资人有一个投资，分三期，第一期已经还款，第二期还款日在T+5，但是T日借款人提前还款了，
		 * 现在投资人想在T日将第三期债转出去，垫付利息就会是一个负数，导致插入数据库失败
		 * 解决方案： 计算为负数，则为0
		 */
		if(assignInterestAdvance.compareTo(BigDecimal.ZERO) < 0 ){
			assignInterestAdvance = BigDecimal.ZERO;
		}

		return assignInterestAdvance;
    }

	/**
	 * 垫付利息 垫息总额=投资人认购本金/出让人转让本金*出让人本期利息）-（债权本金*年化收益÷360*本期剩余天数
	 *
	 * @return 应还本息总和
	 */
	public static BigDecimal getAssignInterestAdvanceAfterCredit(BigDecimal investCapital, BigDecimal creditCapital, BigDecimal sellerCapitalWait, BigDecimal sellerInterestAdvanceWait,
			BigDecimal yearRate, BigDecimal interest, BigDecimal lastDays) {
		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		if (investCapital.compareTo(sellerCapitalWait) == 0) {
			assignInterestAdvance = sellerInterestAdvanceWait;
			assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
		} else {
			assignInterestAdvance = (investCapital.multiply(interest).divide(creditCapital, 18, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN).subtract(
					(investCapital.multiply(yearRate).multiply(lastDays).divide(new BigDecimal("360"), 18, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN));
			assignInterestAdvance = assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return assignInterestAdvance;
	}

	public static void main(String[] args) {
		BigDecimal invest = new BigDecimal(300); // 本金
		BigDecimal creditCapital = new BigDecimal(2000); // 本金
		int month = 6;
		BigDecimal yearRate = new BigDecimal(0.11); // 年利率
		BigDecimal interest = new BigDecimal(18.33); // 年利率
		int term = 6;
		BigDecimal interestq = getPerTermInterest(invest, yearRate, month, term);
		System.out.println("每月利息：" + interestq);
		BigDecimal interestCount = getAssignInterestAdvance(invest, creditCapital, yearRate, interest, new BigDecimal(27));
		System.out.println("总利息：" + interestCount);

	}
}
