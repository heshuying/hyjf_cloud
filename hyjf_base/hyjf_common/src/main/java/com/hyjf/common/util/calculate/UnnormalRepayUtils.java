/**
 * Description: 提前还款、延期还款、逾期还款 获取本息工具类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 * Created at: 2015年12月1日 上午11:53:02
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.util.calculate;

import java.math.BigDecimal;

/**
 * 非正常还款工具类，包含提前还款、延期还款、逾期还款
 */
public class UnnormalRepayUtils {

	/**
	 * 提前还款,获取本息 提前还款是指在还款日之前还款； a、若提前8个工作日内还款，需全额支付应还利息；
	 * b、若提前8个工作日（不包括8个工作日）以上，则需补3天的利息。 实际应还本息=应还本息-本期应还本金*年化收益÷36000 *（提前还款天数-3）；
	 * 
	 * @param termShouldPrincipalInterest
	 *            本期应还本息
	 * @param termShouldPrincipal
	 *            本期应还本金
	 * @param yearRate
	 *            年化收益
	 * @param aheadDays
	 *            提前还款天数
	 * @return 提前还款实际应还本息
	 */
	public static BigDecimal aheadRepayPrincipalInterest(BigDecimal termShouldPrincipalInterest, BigDecimal termShouldPrincipal,BigDecimal yearRate, int aheadDays) {
		
		BigDecimal aheadInterest = termShouldPrincipal.multiply(yearRate).multiply(new BigDecimal(aheadDays).subtract(new BigDecimal(3))).divide(new BigDecimal(36000),2,BigDecimal.ROUND_DOWN);
		BigDecimal result = termShouldPrincipalInterest.subtract(aheadInterest);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}
	
	/**
	 * 融通宝提前还款,获取本息 提前还款是指在还款日之前还款； a、若提前8个工作日内还款，需全额支付应还利息；
	 * b、若提前8个工作日（不包括8个工作日）以上，则需补3天的利息。 实际应还本息=应还本息-本期应还本金*年化收益÷36000 *（提前还款天数-3）；
	 * 
	 * @param termShouldPrincipalInterest
	 *            本期应还本息
	 * @param termShouldPrincipal
	 *            本期应还本金
	 * @param yearRate
	 *            年化收益
	 * @param aheadDays
	 *            提前还款天数
	 * @return 提前还款实际应还本息
	 */
	public static BigDecimal aheadRTBRepayPrincipalInterest(BigDecimal termShouldPrincipalInterest, BigDecimal termShouldPrincipal,BigDecimal yearRate, int aheadDays) {
		
		BigDecimal aheadInterest = termShouldPrincipal.multiply(yearRate).multiply(new BigDecimal(aheadDays)).divide(new BigDecimal(36000),2,BigDecimal.ROUND_DOWN);
		BigDecimal result = termShouldPrincipalInterest.subtract(aheadInterest);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}
	/**
	 * 提前还款实际利息
	 * @param termShouldPrincipalInterest
	 * @param termShouldPrincipal
	 * @param yearRate
	 * @param aheadDays
	 * @return
	 */
	public static BigDecimal aheadRepayInterest(BigDecimal termShouldInterest, BigDecimal termShouldPrincipal,BigDecimal yearRate, int aheadDays) {
		
		BigDecimal aheadInterest = termShouldPrincipal.multiply(yearRate).multiply(new BigDecimal(aheadDays).subtract(new BigDecimal(3))).divide(new BigDecimal(36000),2,BigDecimal.ROUND_DOWN);
		BigDecimal result = termShouldInterest.subtract(aheadInterest);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}

	
	/**
	 * 提前还款减少的利息
	 * @param termShouldPrincipalInterest
	 * @param termShouldPrincipal
	 * @param yearRate
	 * @param aheadDays
	 * @return
	 */
	public static BigDecimal aheadRepayChargeInterest(BigDecimal termShouldPrincipal,BigDecimal yearRate, int aheadDays) {
		
		BigDecimal aheadInterest = termShouldPrincipal.multiply(yearRate).multiply(new BigDecimal(aheadDays).subtract(new BigDecimal(3))).divide(new BigDecimal(36000),2,BigDecimal.ROUND_DOWN);
		return aheadInterest;
	}


	/**
	 * 先息后本还款方式 提前还款减息计算公式
	 * @param termShouldRepayInterest 当月应还利息
	 * @param aheadDays 提现还款天数
	 * @return
	 */
	public static  BigDecimal aheadEndMonthRepayChargeInterest(BigDecimal termShouldRepayInterest, int aheadDays){
		BigDecimal advanceDays = new BigDecimal(aheadDays).subtract(new BigDecimal(3));
		BigDecimal aheadInterest  =((termShouldRepayInterest.multiply(advanceDays)).divide(new BigDecimal(30),18,BigDecimal.ROUND_DOWN)).setScale(2,BigDecimal.ROUND_DOWN);
		return aheadInterest;
	}

	/**
	 * 	 **NEW** 计算机实际天数罚息
	 * （提前还款8日包含8日还款，利息按实际持有天数计算）
	 * （10000*0.085/360）*82+（10000*0.085/360）*3+10000=10200.69
	 * @param termShouldPrincipal
	 * @param yearRate
	 * @param actualDays
	 * @return
	 */
	public static BigDecimal aheadEndRepayInterest(BigDecimal termShouldPrincipal,BigDecimal yearRate, int actualDays) {
		// 借款日利息 （10000*0.085/360）代入计算
		BigDecimal advanceDays = new BigDecimal(actualDays).add(new BigDecimal(3));
		BigDecimal aheadInterest = termShouldPrincipal.multiply(yearRate).divide(new BigDecimal(36000),8,BigDecimal.ROUND_DOWN).multiply(advanceDays);
		aheadInterest = aheadInterest.setScale(2,BigDecimal.ROUND_DOWN);
		return aheadInterest;
	}

	/**
	 * 	 **NEW**计算最后一期利息
	 * （提前还款8日包含8日还款，利息按实际持有天数计算）
	 * （10000*0.08/360）*3*（3-1）-6.66=6.6
	 * @param termShouldPrincipal
	 * @param yearRate
	 * @return
	 */
	public static BigDecimal aheadLastRepayInterest(BigDecimal termShouldPrincipal,BigDecimal yearRate, int totalPeriod) {
		// 借款日利息 （10000*0.085/360）代入计算
		BigDecimal advanceDays = new BigDecimal(3);
		BigDecimal totalInterest = termShouldPrincipal.multiply(yearRate).divide(new BigDecimal(36000),8,BigDecimal.ROUND_DOWN).multiply(advanceDays).multiply(new BigDecimal(totalPeriod));
		totalInterest = totalInterest.setScale(2,BigDecimal.ROUND_DOWN);
		BigDecimal oneInterest = aheadEndRepayInterest(termShouldPrincipal,yearRate,0).multiply(new BigDecimal(totalPeriod-1));
		return totalInterest.subtract(oneInterest).setScale(2, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 融通宝提前还款减少的利息  应还本金*年化收益率÷360 *提前还款天数
	 */
	public static BigDecimal aheadRTBRepayChargeInterest(BigDecimal termShouldPrincipal,BigDecimal yearRate, int aheadDays) {
		
		BigDecimal aheadInterest = termShouldPrincipal.multiply(yearRate).divide(new BigDecimal(36000),8,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(aheadDays)).setScale(2,BigDecimal.ROUND_DOWN);
		return aheadInterest;
	}
	/**
	 * 延期还款
	 * 
	 * 公式：延期利息=本期本金*年化收益÷36000*实际延期天数； 实际应还本息=应还本息+延期利息；
	 * 
	 * @param termShouldPrincipalInterest
	 *            本期应还本息
	 * @param termShouldPrincipal
	 *            本期应还本金
	 * @param yearRate
	 *            年化收益
	 * @param delayDays
	 *            延期天数
	 * @return 延期还款实际应还本息
	 */
	public static BigDecimal delayRepayPrincipalInterest(BigDecimal termShouldPrincipalInterest, BigDecimal termShouldPrincipal,BigDecimal yearRate, int delayDays) {
		// 延期利息
		BigDecimal delayInterest = termShouldPrincipal.multiply(yearRate).multiply(new BigDecimal(delayDays)).divide(new BigDecimal(36000),2,BigDecimal.ROUND_DOWN);
		// 实际应还本息=应还本息+延期利息
		BigDecimal result = termShouldPrincipalInterest.add(delayInterest);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}
	
	/**
	 * 延期利息总和
	 * @param termShouldPrincipalInterest
	 * @param termShouldPrincipal
	 * @param yearRate
	 * @param delayDays
	 * @return
	 */
	public static BigDecimal delayRepayInterestTotal(BigDecimal termShouldInterest, BigDecimal termShouldPrincipal,BigDecimal yearRate, int delayDays) {
		// 延期利息
		BigDecimal delayInterest = termShouldInterest.add(termShouldPrincipal.multiply(yearRate).multiply(new BigDecimal(delayDays))).divide(new BigDecimal(36000),2,BigDecimal.ROUND_DOWN);
		return delayInterest;
	}
	
	/**
	 * 延期利息
	 * @param termShouldPrincipalInterest
	 * @param termShouldPrincipal
	 * @param yearRate
	 * @param delayDays
	 * @return
	 */
	public static BigDecimal delayRepayInterest(BigDecimal termShouldPrincipal,BigDecimal yearRate, int delayDays) {
		// 延期利息
		BigDecimal delayInterest = termShouldPrincipal.multiply(yearRate).multiply(new BigDecimal(delayDays)).divide(new BigDecimal(36000),2,BigDecimal.ROUND_DOWN);
		return delayInterest;
	}

	/**
	 * 逾期还款
	 * 
	 * 公式逾期罚息总额=逾期本息总额*逾期天数*0.06％； 实际应还本息=应还本息+延期利息+逾期罚息；
	 * 
	 * @param termShouldPrincipalInterest
	 *            本期应还本息
	 * @param termShouldPrincipal
	 *            本期应还本金
	 * @param yearRate
	 *            年化收益
	 * @param overdueDays
	 *            逾期天数
	 * @return 逾期还款 实际应还本息
	 */
	public static BigDecimal overdueRepayPrincipalInterest(BigDecimal termShouldPrincipalInterest, BigDecimal termShouldPrincipal,BigDecimal yearRate, int delayDays,int overdueDays) {
		// 逾期罚息总额
		BigDecimal overdueInterest = termShouldPrincipalInterest.multiply(new BigDecimal(overdueDays)).multiply(new BigDecimal("0.0006"));
		overdueInterest = overdueInterest.setScale(2, BigDecimal.ROUND_DOWN);
		// 延期利息
		BigDecimal delayInterest =termShouldPrincipal.multiply(yearRate).multiply(new BigDecimal(delayDays)).divide(new BigDecimal(36000),2,BigDecimal.ROUND_DOWN);
		// 实际应还本息=应还本息+延期利息+逾期罚息；
		BigDecimal result = termShouldPrincipalInterest.add(delayInterest).add(overdueInterest);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}
	
	/**
	 * 逾期总利息
	 * @param termShouldPrincipalInterest
	 * @param termShouldPrincipal
	 * @param yearRate
	 * @param overdueDays
	 * @return
	 */
	public static BigDecimal overdueRepayInterest(BigDecimal termShouldPrincipalInterest, BigDecimal termShouldPrincipal,BigDecimal termShouldInterest,BigDecimal yearRate,int delayDays,int overdueDays) {
		// 逾期罚息总额
		BigDecimal overdueInterest = termShouldPrincipalInterest.multiply(new BigDecimal(overdueDays)).multiply(new BigDecimal("0.0006"));
		// 延期利息
		BigDecimal delayInterest =termShouldPrincipal.multiply(yearRate).multiply(new BigDecimal(delayDays)).divide(new BigDecimal(36000),2,BigDecimal.ROUND_DOWN);
		BigDecimal result =termShouldInterest.add (delayInterest).add(overdueInterest);
		result = result.setScale(2, BigDecimal.ROUND_DOWN);
		return result;
	}
	
	/**
	 * 逾期利息
	 * @param termShouldPrincipalInterest
	 * @param termShouldPrincipal
	 * @param yearRate
	 * @param overdueDays
	 * @return
	 */
	public static BigDecimal overdueRepayOverdueInterest(BigDecimal termShouldPrincipalInterest,int overdueDays) {
		// 逾期罚息总额
		BigDecimal overdueInterest = termShouldPrincipalInterest.multiply(new BigDecimal(overdueDays)).multiply(new BigDecimal("0.0006"));
		overdueInterest = overdueInterest.setScale(2, BigDecimal.ROUND_DOWN);
		return overdueInterest;
	}

	/**
	 * 逾期的延期利息
	 * @param termShouldPrincipalInterest
	 * @param termShouldPrincipal
	 * @param yearRate
	 * @param overdueDays
	 * @return
	 */
	public static BigDecimal overdueRepayDelayInterest(BigDecimal termShouldPrincipal,BigDecimal yearRate, int delayDays) {
		// 延期利息
		BigDecimal delayInterest =termShouldPrincipal.multiply(yearRate).multiply(new BigDecimal(delayDays)).divide(new BigDecimal(36000),2,BigDecimal.ROUND_DOWN);
		delayInterest = delayInterest.setScale(2, BigDecimal.ROUND_DOWN);
		return delayInterest;
	}

	/**
	 * 计算计划相关的逾期利率
	 * @param termShouldPrincipalInterest
	 * @param overdueDays
	 * @param planRate
	 * @return
	 */
	public static BigDecimal overduePlanRepayOverdueInterest(BigDecimal termShouldPrincipalInterest, int overdueDays,
			String planRate) {
		BigDecimal planRateStr;
		if (planRate == null || planRate.equals("0")) {
			planRateStr = new BigDecimal("0.0006");
		}else{
			planRateStr = new BigDecimal(planRate);
		}
		// 逾期罚息总额
		BigDecimal overdueInterest = termShouldPrincipalInterest.multiply(new BigDecimal(overdueDays)).multiply(planRateStr);
		overdueInterest = overdueInterest.setScale(2, BigDecimal.ROUND_DOWN);
		return overdueInterest;
	}
	

}
