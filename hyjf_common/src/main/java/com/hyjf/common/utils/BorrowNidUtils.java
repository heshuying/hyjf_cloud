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

import com.hyjf.common.util.CustomConstants;

/**
 * @author Administrator
 */

public enum BorrowNidUtils {

	// 0:汇保贷<HBD>
	HBD(CustomConstants.HBD_KEY, CustomConstants.HBD),
	// 1:汇典贷<HDD>
	HDD(CustomConstants.HDD_KEY, CustomConstants.HDD),
	// 2:汇小贷<HXD>
	HXD(CustomConstants.HXD_KEY, CustomConstants.HXD),
	// 3:汇车贷<HCD>
	HCD(CustomConstants.HCD_KEY, CustomConstants.HCD),
	// 4:新手标<NEW>
	NEW(CustomConstants.NEW_KEY, CustomConstants.NEW),
	// 5:汇租赁<HZL>
	HZL(CustomConstants.HZL_KEY, CustomConstants.HZL),
	// 6:供应贷<GYD>
	GYD(CustomConstants.GYD_KEY, CustomConstants.GYD),
	// 7:汇房贷<HFD>
	HFD(CustomConstants.HFD_KEY, CustomConstants.HFD),
	// 8:汇消费<HXF>
	HXF(CustomConstants.HXF_KEY, CustomConstants.HXF);

	// 成员变量
	private String name;
	private String index;

	// 构造方法
	private BorrowNidUtils(String index, String name) {
		this.index = index;
		this.name = name;
	}

	// 普通方法
	public static String getName(String index) {
		for (BorrowNidUtils c : BorrowNidUtils.values()) {
			if ((c.getIndex()).equals(index)) {
				return c.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public static void main(String[] args) {
		System.out.println(BorrowNidUtils.getName(CustomConstants.HDD_KEY));
	}
}
