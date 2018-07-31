/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by :
 */

package com.hyjf.am.trade.dao.model.customize.trade;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class DebtPlanBorrowCustomize implements Serializable {

	/**序列化id*/
	private static final long serialVersionUID = 5748630051215873837L;
	// 项目id
	private String borrowNid;
	// 项目还款方式
	private String borrowStyleName;
	// 项目年华收益率
	private String borrowApr;
	// 类型 0专属项目 1债转
	private String type;
	// 项目期限
	private String borrowPeriod;
	// 借款金额
	private String borrowAmount;
	// 借款用途
	private String borrowPurpose;
	// 借款用途
	private String borrowUserName;
	
	public String getBorrowUserName() {
		return borrowUserName;
	}
	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}
	// 真实姓名
	private String trueName;
	//借款人类型
	private String companyOrPersonal;
	
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getCompanyOrPersonal() {
		return companyOrPersonal;
	}
	public void setCompanyOrPersonal(String companyOrPersonal) {
		this.companyOrPersonal = companyOrPersonal;
	}
	
	public String getBorrowNid() {
		return borrowNid;
	}
	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}
	public String getBorrowStyleName() {
		return borrowStyleName;
	}
	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}
	public String getBorrowApr() {
		return borrowApr;
	}
	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}
	public String getBorrowPeriod() {
		return borrowPeriod;
	}
	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBorrowAmount() {
		return borrowAmount;
	}
	public void setBorrowAmount(String borrowAmount) {
		this.borrowAmount = borrowAmount;
	}
	public String getBorrowPurpose() {
		return borrowPurpose;
	}
	public void setBorrowPurpose(String borrowPurpose) {
		this.borrowPurpose = borrowPurpose;
	}
    
}
