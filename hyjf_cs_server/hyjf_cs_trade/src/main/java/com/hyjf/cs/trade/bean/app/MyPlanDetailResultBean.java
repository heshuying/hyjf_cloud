package com.hyjf.cs.trade.bean.app;


import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;

import java.util.ArrayList;
import java.util.List;


public class MyPlanDetailResultBean extends BaseResultBeanFrontEnd {
	//	订单id
	private String accedeOrderId = "";
	//法大大协议状态
	private String fddStatus = "";
	
	/** 计划信息 */
	private ProjectIntr projectIntr;
	/** 加入信息 */
	private InvestIntr investIntr;
	/** 优惠券信息数据 无数据，属于预留 */
	private CouponIntr couponIntr;
	/** 回款计划列表 */
	private List<RepayPlan> repayPlan;
	/** 持有项目列表 */
	private List<BorrowComposition> borrowComposition;

	public MyPlanDetailResultBean() {
		projectIntr = new ProjectIntr();
		investIntr = new InvestIntr();
		couponIntr = new CouponIntr();
		repayPlan = new ArrayList<>();
		borrowComposition = new ArrayList<>();
	}

	// 计划信息
	public static class ProjectIntr {
		// 项目进度状态
		private String status = "";
		// 历史年回报率
		private String borrowApr = "";
		// 项目期限
		private String borrowPeriod = "";
		// 项目期限单位
		private String borrowPeriodUnit = "";
		// 还款方式
		private String repayStyle = "";
		// 计息时间
		private String onAccrual = "";
		//计划名称
		private String planName = "";
		
		
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
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

		public String getBorrowPeriodUnit() {
			return borrowPeriodUnit;
		}

		public void setBorrowPeriodUnit(String borrowPeriodUnit) {
			this.borrowPeriodUnit = borrowPeriodUnit;
		}

		public String getRepayStyle() {
			return repayStyle;
		}

		public void setRepayStyle(String repayStyle) {
			this.repayStyle = repayStyle;
		}

		public String getOnAccrual() {
			return onAccrual;
		}

		public void setOnAccrual(String onAccrual) {
			this.onAccrual = onAccrual;
		}

		public String getPlanName() {
			return planName;
		}

		public void setPlanName(String planName) {
			this.planName = planName;
		}
	}

	// 加入信息
	public static class InvestIntr {
		// 加入本金
		private String capital = "";
		// 已收本息
		private String capitalInterest = "";
		// 待收本金
		private String capitalOnCall = "";
		// 待收利息
		private String interestOnCall = "";
		// 加入时间
		private String addDate = "";

		public String getCapital() {
			return capital;
		}

		public void setCapital(String capital) {
			this.capital = capital;
		}

		public String getCapitalInterest() {
			return capitalInterest;
		}

		public void setCapitalInterest(String capitalInterest) {
			this.capitalInterest = capitalInterest;
		}

		public String getCapitalOnCall() {
			return capitalOnCall;
		}

		public void setCapitalOnCall(String capitalOnCall) {
			this.capitalOnCall = capitalOnCall;
		}

		public String getInterestOnCall() {
			return interestOnCall;
		}

		public void setInterestOnCall(String interestOnCall) {
			this.interestOnCall = interestOnCall;
		}

		public String getAddDate() {
			return addDate;
		}

		public void setAddDate(String addDate) {
			this.addDate = addDate;
		}
	}

	// 优惠券信息数据
	public static class CouponIntr {
		// 优惠券类型
		private String couponType = "";
		// 面额
		private String couponAmount = "";
		// 待收本金
		private String capitalOnCall = "";
		// 待收利息
		private String interestOnCall = "";
		// 待收总额
		private String capitalInterestOnCall = "";

		public String getCouponType() {
			return couponType;
		}

		public void setCouponType(String couponType) {
			this.couponType = couponType;
		}

		public String getCouponAmount() {
			return couponAmount;
		}

		public void setCouponAmount(String couponAmount) {
			this.couponAmount = couponAmount;
		}

		public String getCapitalOnCall() {
			return capitalOnCall;
		}

		public void setCapitalOnCall(String capitalOnCall) {
			this.capitalOnCall = capitalOnCall;
		}

		public String getInterestOnCall() {
			return interestOnCall;
		}

		public void setInterestOnCall(String interestOnCall) {
			this.interestOnCall = interestOnCall;
		}

		public String getCapitalInterestOnCall() {
			return capitalInterestOnCall;
		}

		public void setCapitalInterestOnCall(String capitalInterestOnCall) {
			this.capitalInterestOnCall = capitalInterestOnCall;
		}
	}

	// 回款计划
	public static class RepayPlan {
		// 回款时间
		private String time = "";
		// 回款期数
		private String number = "";
		// 回款金额
		private String account = "";
		// 回款状态
		private String status = "";

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}

	// 持有项目
	public static class BorrowComposition {
		// 资产编号
		private String borrowNid = "";
		// 投资金额
		private String account = "";
		// 投资时间
		private String tenderTime= "";
		// 0-普通  1-债转
		private String type = "";
		// 债转订单号
		private String nid = "";

		public String getNid() {
			return nid;
		}

		public void setNid(String nid) {
			this.nid = nid;
		}

		public String getBorrowNid() {
			return borrowNid;
		}

		public void setBorrowNid(String borrowNid) {
			this.borrowNid = borrowNid;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getTenderTime() {
			return tenderTime;
		}

		public void setTenderTime(String tenderTime) {
			this.tenderTime = tenderTime;
		}
		public String getType(){
			return type;
		}
		public void setType(String type){
			this.type=type;
		}
	}

	public ProjectIntr getProjectIntr() {
		return projectIntr;
	}

	public void setProjectIntr(ProjectIntr projectIntr) {
		this.projectIntr = projectIntr;
	}

	public InvestIntr getInvestIntr() {
		return investIntr;
	}

	public void setInvestIntr(InvestIntr investIntr) {
		this.investIntr = investIntr;
	}

	public CouponIntr getCouponIntr() {
		return couponIntr;
	}

	public void setCouponIntr(CouponIntr couponIntr) {
		this.couponIntr = couponIntr;
	}

	public List<RepayPlan> getRepayPlan() {
		return repayPlan;
	}

	public void setRepayPlan(List<RepayPlan> repayPlan) {
		this.repayPlan = repayPlan;
	}

	public List<BorrowComposition> getBorrowComposition() {
		return borrowComposition;
	}

	public void setBorrowComposition(List<BorrowComposition> borrowComposition) {
		this.borrowComposition = borrowComposition;
	}

	public String getAccedeOrderId() {
		return accedeOrderId;
	}

	public void setAccedeOrderId(String accedeOrderId) {
		this.accedeOrderId = accedeOrderId;
	}

    public String getFddStatus() {
        return fddStatus;
    }

    public void setFddStatus(String fddStatus) {
        this.fddStatus = fddStatus;
    }
	
	
}
