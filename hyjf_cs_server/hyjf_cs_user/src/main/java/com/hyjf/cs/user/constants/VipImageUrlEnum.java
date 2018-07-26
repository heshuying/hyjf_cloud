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

package com.hyjf.cs.user.constants;

/**
 * @author Administrator
 */

public enum VipImageUrlEnum {

    // 没有开通vip
    VIP0(0, "vip0.png"),
	// 白银会员
	VIP1(1, "vip1.png"),
	// 黄金会员
	VIP2(2, "vip2.png"),
	// 铂金会员
	VIP3(3, "vip3.png"),
	// 黑金会员
	VIP4(4, "vip4.png"),
	// 钻石会员
	VIP5(5, "vip5.png"),
	// 至尊会员
	VIP6(6, "vip6.png");
	

	// 成员变量
	private String value;
	private int key;

	// 构造方法
	private VipImageUrlEnum(int key, String value) {
		this.key = key;
		this.value = value;
	}

	// 普通方法
	public static String getName(int key) {
		for (VipImageUrlEnum c : VipImageUrlEnum.values()) {
			if (c.getKey() == key) {
				return c.value;
			}
		}
		return null;
	}

	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public static void main(String[] args) {
		//ROUND_DOWN
		System.out.println(VipImageUrlEnum.getName(2));
	}
}
