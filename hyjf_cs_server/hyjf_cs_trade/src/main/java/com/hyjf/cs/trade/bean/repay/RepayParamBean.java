package com.hyjf.cs.trade.bean.repay;


import com.hyjf.cs.trade.bean.BaseBean;

public class RepayParamBean extends BaseBean {
	//查询类型 0:待还款 1:已还款
	private String repayType;
	//标的号
	private String borrowNid;
	//资产编号
	private String productId;
	

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}
	
	

}
