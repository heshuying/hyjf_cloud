package com.hyjf.am.trade.dao.model.customize.wdzj;

public class WDZJPreapysListCustomize {

	String projectId;
	int deadline;
	String deadlineUnit;
	public String getProjectId() {
		return WDZJBorrowListDataCustomize.encryptBorrowNid(projectId);
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	public String getDeadlineUnit() {
		return deadlineUnit;
	}
	public void setDeadlineUnit(String deadlineUnit) {
		this.deadlineUnit = deadlineUnit;
	}
}
