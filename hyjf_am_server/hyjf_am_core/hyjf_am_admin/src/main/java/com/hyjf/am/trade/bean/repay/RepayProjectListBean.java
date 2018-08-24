package com.hyjf.am.trade.bean.repay;

import java.io.Serializable;

import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.common.paginator.Paginator;

public class RepayProjectListBean extends WebUserRepayProjectListCustomizeVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6507895388254615953L;

	// 用户id
	public String userId;
	//用户角色 2借款人，3垫付机构
	public String roleId;
	
	// 应还时间开始
	public String startDate;
	// 应还时间结束
	public String endDate;
	/**
	 * 还款时间排序 0:升序 1:降序
	 */
	private String repayOrder;
	/**
	 * 到账时间排序 0:升序 1:降序
	 */
	private String checkTimeOrder;
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;
	private int pageSize = 8;
	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;
	
	private String repayStatus;
	
	public String getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	public String getRepayOrder() {
		return repayOrder;
	}

	public void setRepayOrder(String repayOrder) {
		this.repayOrder = repayOrder;
	}

	public String getCheckTimeOrder() {
		return checkTimeOrder;
	}

	public void setCheckTimeOrder(String checkTimeOrder) {
		this.checkTimeOrder = checkTimeOrder;
	}

	public RepayProjectListBean() {
		super();
	}

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}
	
	public int getPageSize() {
        if (pageSize == 0) {
            pageSize = 8;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
