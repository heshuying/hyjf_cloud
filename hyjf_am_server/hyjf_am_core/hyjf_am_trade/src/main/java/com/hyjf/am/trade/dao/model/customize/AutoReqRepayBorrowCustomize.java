/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author Administrator
 */

public class AutoReqRepayBorrowCustomize implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;
	/**
	 * 借款编码
	 */
	private String borrowNid;
	/**
	 * 借款用户ID
	 */
	private Integer userId;
	/**
	 * 借款用户名
	 */
	private String username;
	/**
	 * 还款担保机构ID
	 */
	private Integer repayOrgUserId;
	/**
	 * 还款担保机构用户名
	 */
	private String repayOrgUsername;
	/**
	 * 扣款账户：1-担保账户；2-借款人账户
	 */
	private String repayerType;
	/**
	 * borrowNid
	 * @return the borrowNid
	 */
	
	public String getBorrowNid() {
		return borrowNid;
	}
	/**
	 * @param borrowNid the borrowNid to set
	 */
	
	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	/**
	 * userId
	 * @return the userId
	 */
	
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * repayerType
	 * @return the repayerType
	 */
	
	public String getRepayerType() {
		return repayerType;
	}
	/**
	 * @param repayerType the repayerType to set
	 */
	
	public void setRepayerType(String repayerType) {
		this.repayerType = repayerType;
	}
	/**
	 * repayOrgUserId
	 * @return the repayOrgUserId
	 */
	
	public Integer getRepayOrgUserId() {
		return repayOrgUserId;
	}
	/**
	 * @param repayOrgUserId the repayOrgUserId to set
	 */
	
	public void setRepayOrgUserId(Integer repayOrgUserId) {
		this.repayOrgUserId = repayOrgUserId;
	}
	
	
	/**
	 * username
	 * @return the username
	 */
	
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * repayOrgUsername
	 * @return the repayOrgUsername
	 */
	
	public String getRepayOrgUsername() {
		return repayOrgUsername;
	}
	/**
	 * @param repayOrgUsername the repayOrgUsername to set
	 */
	
	public void setRepayOrgUsername(String repayOrgUsername) {
		this.repayOrgUsername = repayOrgUsername;
	}
	/**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * @return
	 * @author lb
	 */
		
	@Override
	public String toString() {
		return "AutoReqRepayBorrowCustomize [borrowNid=" + borrowNid + ", userId=" + userId + ", repayOrgUserId="
				+ repayOrgUserId + ", repayerType=" + repayerType + "]";
	}
	
}
