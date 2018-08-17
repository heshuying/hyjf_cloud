package com.hyjf.am.vo.trade.assetmanage;

import com.hyjf.common.util.CustomConstants;

import java.util.List;

/**
 * @Description 我的计划列表返回
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
public class MyPlanListResultBean {

	// 计划总数
	private int projectTotal;
	// 计划列表
	private List<ProjectList> projectList;

	private String type;

	// 待收总额
	private String money;
	private String status;

	private String statusDesc;

	private String request;

	public MyPlanListResultBean() {
		this.status = CustomConstants.APP_STATUS_FAIL;;
	}

	public static class ProjectList{
		/** 计划的状态 */
		private String type;
		/** 项目id */
		private String borrowNid;
		/** 项目名称 */
		private String borrowName;

		/** 项目详情url */
		private String borrowUrl;

		/**标的第一项 */
		private String borrowTheFirst;
		/**标的第一项描述 */
		private String borrowTheFirstDesc;
		/**标的第二项 */
		private String borrowTheSecond;
		/**标的第二项描述 */
		private String borrowTheSecondDesc;
		/**标的第三项 */
		private String borrowTheThird;
		/**标的第三项描述*/
		private String borrowTheThirdDesc;

		/** 标签：无，加息券，体验金 */
		private String label;

		public String getBorrowUrl() {
			return borrowUrl;
		}

		public void setBorrowUrl(String borrowUrl) {
			this.borrowUrl = borrowUrl;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getBorrowNid() {
			return borrowNid;
		}

		public void setBorrowNid(String borrowNid) {
			this.borrowNid = borrowNid;
		}

		public String getBorrowName() {
			return borrowName;
		}

		public void setBorrowName(String borrowName) {
			this.borrowName = borrowName;
		}

		public String getBorrowTheFirst() {
			return borrowTheFirst;
		}

		public void setBorrowTheFirst(String borrowTheFirst) {
			this.borrowTheFirst = borrowTheFirst;
		}

		public String getBorrowTheFirstDesc() {
			return borrowTheFirstDesc;
		}

		public void setBorrowTheFirstDesc(String borrowTheFirstDesc) {
			this.borrowTheFirstDesc = borrowTheFirstDesc;
		}

		public String getBorrowTheSecond() {
			return borrowTheSecond;
		}

		public void setBorrowTheSecond(String borrowTheSecond) {
			this.borrowTheSecond = borrowTheSecond;
		}

		public String getBorrowTheSecondDesc() {
			return borrowTheSecondDesc;
		}

		public void setBorrowTheSecondDesc(String borrowTheSecondDesc) {
			this.borrowTheSecondDesc = borrowTheSecondDesc;
		}

		public String getBorrowTheThird() {
			return borrowTheThird;
		}

		public void setBorrowTheThird(String borrowTheThird) {
			this.borrowTheThird = borrowTheThird;
		}

		public String getBorrowTheThirdDesc() {
			return borrowTheThirdDesc;
		}

		public void setBorrowTheThirdDesc(String borrowTheThirdDesc) {
			this.borrowTheThirdDesc = borrowTheThirdDesc;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getProjectTotal() {
		return projectTotal;
	}

	public void setProjectTotal(int projectTotal) {
		this.projectTotal = projectTotal;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public List<ProjectList> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<ProjectList> projectList) {
		this.projectList = projectList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
}
