package com.hyjf.cs.user.controller.app.project;

import com.hyjf.am.vo.app.AppRepayPlanListCustomizeVO;

import java.io.Serializable;

public class RepayPlanListBean extends AppRepayPlanListCustomizeVO implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3278149257478770256L;
	/** 用户id */
	private String userId;
	/** 项目id */
	private String borrowNid;
	private String orderId;
    
    /** 投资类型*/
    private String investType;
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int page = 1;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int pageSize = 10;

	public RepayPlanListBean() {
		super();
	}

	public int getPage() {
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		if (pageSize == 0) {
			pageSize = 10;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }
    
}
