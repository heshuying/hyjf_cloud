/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean.api;

import java.io.Serializable;

import com.hyjf.cs.trade.bean.BaseBean;
import com.hyjf.cs.trade.bean.BaseResultBean;

/**
 * @author libin
 * @version AutoTenderResultBean.java, v0.1 2018年8月24日 上午10:53:56
 */
public class AutoTenderResultBean extends BaseResultBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
