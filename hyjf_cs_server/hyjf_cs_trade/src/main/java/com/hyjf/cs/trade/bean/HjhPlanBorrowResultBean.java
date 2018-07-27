package com.hyjf.cs.trade.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jijun
 * @version HjhPlanBorrowResultBean
 * @date 20180727
 * 计划标的组成响应
 */
public class HjhPlanBorrowResultBean extends BaseResultBeanFrontEnd {

	// 加入人次
	private Integer userCount;
	// 加入金额
	private String account;
	private Boolean isEnd;

	private List<BorrowList> borrowList;

	public HjhPlanBorrowResultBean() {
		super();
		this.borrowList = new ArrayList<BorrowList>();
	}

    public static final class BorrowList {
        // 项目编号
		private String borrowNid;
        // 历史年回报率
		private String borrowApr;
        // 项目期限
		private String borrowPeriod;
		//真实姓名
		private String tureName;

		public String getTureName() {
			return tureName;
		}

		public void setTureName(String tureName) {
			this.tureName = tureName;
		}

		public String getBorrowNid() {
			return borrowNid;
		}

		public void setBorrowNid(String borrowNid) {
			this.borrowNid = borrowNid;
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
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Boolean isEnd() {
		return isEnd;
	}

	public void setEnd(Boolean end) {
		isEnd = end;
	}

	public List<BorrowList> getBorrowList() {
		return borrowList;
	}

	public void setBorrowList(List<BorrowList> borrowList) {
		this.borrowList = borrowList;
	}
}
