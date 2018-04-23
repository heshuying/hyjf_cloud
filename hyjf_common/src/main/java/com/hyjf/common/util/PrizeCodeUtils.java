package com.hyjf.common.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class PrizeCodeUtils {

	/**
	 * 生成用户兑奖号
	 * 
	 * @param userPrizeId
	 * @return
	 */
	public static String getUserPrizeCode(Integer userPrizeId,
			String prizeSelfCode) {
		Integer cds = null;
		if (StringUtils.equals(prizeSelfCode, CustomConstants.PRIZE_HEADPHONES)) {
			cds = 1000;
		} else if (StringUtils.equals(prizeSelfCode,
				CustomConstants.PRIZE_PAD_MINI)) {
			cds = 2000;
		} else if (StringUtils.equals(prizeSelfCode,
				CustomConstants.PRIZE_PHONE6_PLUS)) {
			cds = 3000;
		}
		Integer prizeCode = cds + userPrizeId;
		return prizeCode.toString();
	}

	/**
	 * 生成系统中奖号
	 * 
	 * @param times
	 * @param count
	 * @return
	 */
	public static String getSystemPrizeCode(Long times, Integer count,
			String prizeSelfCode) {
		Long cds = null;
		if (StringUtils.equals(prizeSelfCode, CustomConstants.PRIZE_HEADPHONES)) {
			cds = 1000L;
		} else if (StringUtils.equals(prizeSelfCode,
				CustomConstants.PRIZE_PAD_MINI)) {
			cds = 2000L;
		} else if (StringUtils.equals(prizeSelfCode,
				CustomConstants.PRIZE_PHONE6_PLUS)) {
			cds = 3000L;
		}
		Long yu = times % count + 1;
		Long prizeCode = cds + yu;
		return prizeCode.toString();

	}

	public static void main(String[] args) {
		/*
		 * String prizeCode = PrizeCodeUtils.getUserPrizeCode(234);
		 * System.out.println(prizeCode); String prizeCode2 =
		 * PrizeCodeUtils.getSystemPrizeCode(567899999, 400);
		 * System.out.println(prizeCode2);
		 */
		BigDecimal big1 = new BigDecimal("101.3");
		BigDecimal big2 = new BigDecimal("2");
		BigDecimal div = big1.divide(big2, 0, BigDecimal.ROUND_DOWN);
		System.out.println(div);
	}

}
