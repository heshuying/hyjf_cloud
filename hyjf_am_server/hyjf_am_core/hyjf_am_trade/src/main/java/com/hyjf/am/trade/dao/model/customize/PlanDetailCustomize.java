package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * 计划详情
 * @author zhangyk
 * @date 2018/6/27 19:25
 */
public class PlanDetailCustomize implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = -6722330566314459673L;
	//计划编号
	private String planNid;
	//计划金额
	private String planAccount;
	//预期收益率(hjh)
	private String planApr;
	//计划期限(hjh)
	private String planPeriod; 
	//剩余可投金额
	private String planAccountWait;
	//计划状态 0 发起中；1 待审核；2审核不通过；3待开放；4募集中；5锁定中；6清算中；7清算完成，8未还款，9还款中，10还款完成
	private String planStatus;
	//预期收益
	private String planInterest;
	//开始申购时间
	private String buyBeginTime;
	//申购结束时间
	private String buyEndTime;
	//满标或者到期时间
	private String fullExpireTime;
	//退出时间
	private String liquidateFactTime;
	//开始申购时间 格式化
    private String buyBeginTimeFormat;
    //申购结束时间 格式化
    private String buyEndTimeFormat;
    //满标或者到期时间 格式化
    private String fullExpireTimeFormat;
    //退出时间 格式化
    private String liquidateFactTimeFormat;
	//退出天数
	private String debtQuitPeriod;
	//计划名称(hjh)
	private String planName;
	
	private String couponConfig;
	//最低投资金额
	private String debtMinInvestment;
	//投资增量
	private String debtInvestmentIncrement;
	//最高投资金额
	private String debtMaxInvestment;
	//倒计时
	private String timer;
	//汇计划加入人次(hjh)
	private String joinPeopleNum;
	//计划可投金额(hjh)
	private String availableInvestAccount;
	//计划介绍(hjh)
	private String planConcept;
	//计划原理(hjh)
	private String planPrinciple;
	//风控保障措施(hjh)
	private String safeguardMeasures;
	//风险保证金措施(hjh)
	private String marginMeasures;
	//常见问题(hjh)
	private String normalQuestions;
	//还款方式(hjh)
	private String borrowStyle;
	//还款方式名称(hjh)
	private String borrowStyleName;
	// 默认0 天标，1 月标
	private String isMonth;
	
	public PlanDetailCustomize() {
		super();
	}

	public String getPlanNid() {
		return planNid;
	}

	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}

	public String getPlanAccount() {
		return planAccount;
	}

	public void setPlanAccount(String planAccount) {
		this.planAccount = planAccount;
	}

	public String getPlanApr() {
		return planApr;
	}

	public void setPlanApr(String planApr) {
		this.planApr = planApr;
	}

	public String getPlanPeriod() {
		return planPeriod;
	}

	public void setPlanPeriod(String planPeriod) {
		this.planPeriod = planPeriod;
	}

	public String getPlanAccountWait() {
		return planAccountWait;
	}

	public void setPlanAccountWait(String planAccountWait) {
		this.planAccountWait = planAccountWait;
	}

	public String getPlanInterest() {
		return planInterest;
	}

	public void setPlanInterest(String planInterest) {
		this.planInterest = planInterest;
	}

	public String getBuyBeginTime() {
		return buyBeginTime;
	}

	public void setBuyBeginTime(String buyBeginTime) {
		this.buyBeginTime = buyBeginTime;
	}

	public String getBuyEndTime() {
		return buyEndTime;
	}

	public void setBuyEndTime(String buyEndTime) {
		this.buyEndTime = buyEndTime;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getFullExpireTime() {
		return fullExpireTime;
	}

	public void setFullExpireTime(String fullExpireTime) {
		this.fullExpireTime = fullExpireTime;
	}

	public String getLiquidateFactTime() {
		return liquidateFactTime;
	}

	public void setLiquidateFactTime(String liquidateFactTime) {
		this.liquidateFactTime = liquidateFactTime;
	}

	public String getDebtQuitPeriod() {
		return debtQuitPeriod;
	}

	public void setDebtQuitPeriod(String debtQuitPeriod) {
		this.debtQuitPeriod = debtQuitPeriod;
	}

    public String getCouponConfig() {
        return couponConfig;
    }

    public void setCouponConfig(String couponConfig) {
        this.couponConfig = couponConfig;
    }

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

    public String getBuyBeginTimeFormat() {
        return buyBeginTimeFormat;
    }

    public void setBuyBeginTimeFormat(String buyBeginTimeFormat) {
        this.buyBeginTimeFormat = buyBeginTimeFormat;
    }

    public String getBuyEndTimeFormat() {
        return buyEndTimeFormat;
    }

    public void setBuyEndTimeFormat(String buyEndTimeFormat) {
        this.buyEndTimeFormat = buyEndTimeFormat;
    }

    public String getFullExpireTimeFormat() {
        return fullExpireTimeFormat;
    }

    public void setFullExpireTimeFormat(String fullExpireTimeFormat) {
        this.fullExpireTimeFormat = fullExpireTimeFormat;
    }

    public String getLiquidateFactTimeFormat() {
        return liquidateFactTimeFormat;
    }

    public void setLiquidateFactTimeFormat(String liquidateFactTimeFormat) {
        this.liquidateFactTimeFormat = liquidateFactTimeFormat;
    }

    public String getDebtMinInvestment() {
        return debtMinInvestment;
    }

    public void setDebtMinInvestment(String debtMinInvestment) {
        this.debtMinInvestment = debtMinInvestment;
    }

    public String getDebtInvestmentIncrement() {
        return debtInvestmentIncrement;
    }

    public void setDebtInvestmentIncrement(String debtInvestmentIncrement) {
        this.debtInvestmentIncrement = debtInvestmentIncrement;
    }

    public String getDebtMaxInvestment() {
        return debtMaxInvestment;
    }

    public void setDebtMaxInvestment(String debtMaxInvestment) {
        this.debtMaxInvestment = debtMaxInvestment;
    }

	public String getTimer() {
		return timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}

	public String getJoinPeopleNum() {
		return joinPeopleNum;
	}

	public void setJoinPeopleNum(String joinPeopleNum) {
		this.joinPeopleNum = joinPeopleNum;
	}

	public String getAvailableInvestAccount() {
		return availableInvestAccount;
	}

	public void setAvailableInvestAccount(String availableInvestAccount) {
		this.availableInvestAccount = availableInvestAccount;
	}

	public String getPlanConcept() {
		return planConcept;
	}

	public void setPlanConcept(String planConcept) {
		this.planConcept = planConcept;
	}

	public String getPlanPrinciple() {
		return planPrinciple;
	}

	public void setPlanPrinciple(String planPrinciple) {
		this.planPrinciple = planPrinciple;
	}

	public String getSafeguardMeasures() {
		return safeguardMeasures;
	}

	public void setSafeguardMeasures(String safeguardMeasures) {
		this.safeguardMeasures = safeguardMeasures;
	}

	public String getMarginMeasures() {
		return marginMeasures;
	}

	public void setMarginMeasures(String marginMeasures) {
		this.marginMeasures = marginMeasures;
	}

	public String getNormalQuestions() {
		return normalQuestions;
	}

	public void setNormalQuestions(String normalQuestions) {
		this.normalQuestions = normalQuestions;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowStyleName() {
		return borrowStyleName;
	}

	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}

	public String getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(String isMonth) {
		this.isMonth = isMonth;
	}
}
