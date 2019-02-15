/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年10月16日 上午9:32:07
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * aems标的列表查询
 * @author Zha Daojian
 * @date 2018/12/17 9:12
 * @param
 * @return
 **/
public class AemsBorrowListRequestBean extends BaseBean {

	@ApiModelProperty(value = "项目状态")
	private String borrowStatus;
	/**
	 * borrowStatus
	 * @return the borrowStatus
	 */
	
	public String getBorrowStatus() {
		return borrowStatus;
	}
	/**
	 * @param borrowStatus the borrowStatus to set
	 */
	
	public void setBorrowStatus(String borrowStatus) {
		this.borrowStatus = borrowStatus;
	}
}

	