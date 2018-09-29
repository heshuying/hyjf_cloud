/**
 * Description:收取对象：融资方；收取时间：放款时，将融资服务费分账到平台的账户，其他金额分账到融资方的账户；
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 * Created at: 2015年12月3日 上午10:52:27
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.util.calculate;

import java.math.BigDecimal;
import java.util.List;

/**
 * 融资服务费工具类，包含1.到期还本还息/先息后本/等额本息/等额本金；2.按天计息，到期还本息
 */

public class FinancingServiceChargeUtils {

	/**
	 * 到期还本还息/先息后本/等额本息/等额本金  融资服务费
	 * 
	 * 公式：管理费=本金金额*每月账户管理费率*融资月数；每月账户管理费率可后台配置；
	 * 
	 * @param invest
	 *            每笔借到金额
	 * @param serviceRate
	 *            每月服务管理费率
	 * @param totalmonth
	 *            融资期限
	 * @return 融资服务费
	 */
	public static BigDecimal getMonthsPrincipalServiceCharge(BigDecimal invest, BigDecimal serviceRate) {
		BigDecimal count = invest.multiply(serviceRate);
		count = count.setScale(2, BigDecimal.ROUND_DOWN);
		return count;
	}
	
	/**
	 * 按天计息，到期还本息  融资服务费
	 * 
	 * 公式：管理费=本金金额*每月账户管理费率*融资天数；每月账户管理费率可后台配置；
	 * 
	 * @param invest
	 *            借到金额
	 * @param monthServiceRate
	 *            每天服务管理费率
	 * @param totalDays
	 *            融资期限(天)
	 * @return 融资服务费
	 */
	public static BigDecimal getDaysPrincipalServiceCharge(BigDecimal invest, BigDecimal monthServiceRate,
			int totalDays) {
		BigDecimal count = invest.multiply(monthServiceRate).multiply(new BigDecimal(totalDays));
		count = count.setScale(2, BigDecimal.ROUND_DOWN);
		return count;
	}

	/**
	 * 到期还本还息/先息后本/等额本息/等额本金 	融资服务费总和
	 * 
	 * @param investList
	 *            多笔借到金额
	 * @param monthServiceRate
	 *            每月服务管理费率
	 * @param totalmonth
	 *            融资期限
	 * @return 融资服务费总和
	 */
	public static BigDecimal getPrincipalServiceChargeCount(List<BigDecimal> investList, BigDecimal monthServiceRate,
			int totalmonth) {
		BigDecimal count = new BigDecimal(0);
		for (BigDecimal invest : investList) {
			count = count.add(invest.multiply(monthServiceRate).multiply(new BigDecimal(totalmonth)));
		}
		count = count.setScale(2, BigDecimal.ROUND_DOWN);
		return count;
	}
}
