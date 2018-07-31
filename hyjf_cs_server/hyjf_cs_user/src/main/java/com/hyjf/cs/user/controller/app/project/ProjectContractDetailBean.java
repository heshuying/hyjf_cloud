/**
 * Description:用户投资 已回款vO
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年12月4日 下午2:32:36
 * Modification History:
 * Modified by :
 */
package com.hyjf.cs.user.controller.app.project;

import com.hyjf.am.vo.app.AppProjectContractDetailCustomizeVO;

import java.io.Serializable;

public class ProjectContractDetailBean extends AppProjectContractDetailCustomizeVO implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3278149257478770256L;

	/**用户id*/
	private String userId;

	private String orderId;
	

	public ProjectContractDetailBean() {
		super();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
