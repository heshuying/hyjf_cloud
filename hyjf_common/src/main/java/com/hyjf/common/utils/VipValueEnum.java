/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月23日 下午1:25:59
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.utils;

import java.math.BigDecimal;

/**
 * @author Administrator
 */

public enum VipValueEnum {

	// 一月标
	ONE_MONTH(1, new BigDecimal("0.10")),
	// 二月标
	TWO_MONTH(2, new BigDecimal("0.30")),
	// 三月标
	THR_MONTH(3, new BigDecimal("0.50")),
	// 四月标
	FOU_MONTH(4, new BigDecimal("0.70")),
	// 五月标
	FIV_MONTH(5, new BigDecimal("0.90")),
	// 六月标
	SIX_MONTH(6, new BigDecimal("1.10")),
	// 12月标
	TWELVE_MONTH(12, new BigDecimal("2.30"));

	// 成员变量
	private BigDecimal value;
	private int key;

	// 构造方法
	private VipValueEnum(int key, BigDecimal value) {
		this.key = key;
		this.value = value;
	}

	// 普通方法
	public static BigDecimal getName(int key) {
		for (VipValueEnum c : VipValueEnum.values()) {
			if (c.getKey() == key) {
				return c.value;
			}
		}
		return null;
	}

	

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public static void main(String[] args) {
		BigDecimal VipValue = new BigDecimal("1.83");
		//ROUND_DOWN
		System.out.println(VipValue.setScale(0, BigDecimal.ROUND_DOWN));
	}
}
