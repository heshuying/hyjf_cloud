/**
 * Description:银行卡绑定列表前端显示所用PO
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.resquest.user;

import java.math.BigDecimal;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.user.AdminPreRegistListVO;
import com.hyjf.am.vo.user.AdminUserAuthLogListVO;

/**
 * @author wangqi
 */

public class AdminUserAuthLogListRequest extends BasePage  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 添加时机 开始
	public String addTimeStart;

	// 添加时间 结束
	public String addTimeEnd;
	
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	public String getAddTimeStart() {
		return addTimeStart;
	}

	public void setAddTimeStart(String addTimeStart) {
		this.addTimeStart = addTimeStart;
	}

	public String getAddTimeEnd() {
		return addTimeEnd;
	}

	public void setAddTimeEnd(String addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}

	public int getPaginatorPage() {
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}
	/** 订单号 */
	public String orderId;
	/** 订单号 */
	public String orderStatus;
	/** 签约类型 */
	public String authType;
	/** 用户名 */
	private String userName;
	/** 单笔投标金额上限 */
	private BigDecimal autoInvesMoney;
	/** 总投标金额上限 */
	private BigDecimal autoInvesMoneyAll;
	/** 操作平台 */
	private String operateEsb;
	/** 授权时间 */
	private String creditTime;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getAutoInvesMoney() {
		return autoInvesMoney;
	}

	public void setAutoInvesMoney(BigDecimal autoInvesMoney) {
		this.autoInvesMoney = autoInvesMoney;
	}

	public BigDecimal getAutoInvesMoneyAll() {
		return autoInvesMoneyAll;
	}

	public void setAutoInvesMoneyAll(BigDecimal autoInvesMoneyAll) {
		this.autoInvesMoneyAll = autoInvesMoneyAll;
	}

	public String getOperateEsb() {
		return operateEsb;
	}

	public void setOperateEsb(String operateEsb) {
		this.operateEsb = operateEsb;
	}

	public String getCreditTime() {
		return creditTime;
	}

	public void setCreditTime(String creditTime) {
		this.creditTime = creditTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	
}


