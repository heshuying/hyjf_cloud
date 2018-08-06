package com.hyjf.am.trade.dao.model.customize.trade;

import java.io.Serializable;
/**
 * 
 * 优惠券实体
 * @author hsy
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年6月12日
 * @see 下午6:36:04
 */
public class CouponUserListCustomize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 358190081082338992L;

	//优惠券id
    private Integer id;
    
    private String couponUserCode;
    
    private String couponName;
    
    private String content;
    
    private String couponProfitTime;
    
    private String couponType;
    
    private String couponQuota;
    
    private String addTime;
    
    private String endTime;
    
    private String couponSystem;
    
    private String projectType;
    
    private String couponFrom;
    
    private String tenderQuota;
    
    private String projectExpirationType;
    
    private String usedTimeDay;
    
    private String usedTimeHour;
    
    private String projectNid;
    
    //投资时间日
    private String tenderTimeDay;
    //投资时间小时
    private String tenderTimeHour;
    //投资金额
    private String account;
    //借款收益率
    private String borrowApr;
    //借款期限
    private String borrowPeriod;
    
    //还款时间
    private String recoverTime;
    //已还时间
    private String recoverYesTime;
    //还款本息和
    private String recoverAccount;
    //还款状态
    private String recoverStatus;
    // 投资类型
    private String tenderType;
    // 计划编号
    private String debtPlanNid;
    // 锁定期
    private String debtLockPeriod;
    // 预期收益
    private String expectApr;
    // 计划状态
    private String debtPlanStatus;
    
    //优惠券使用标识 0：未使用，1：已使用，2：审核不通过，3：待审核，4：已失效
    private Integer usedFlag;
    
    private Integer activityId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCouponSystem() {
        return couponSystem;
    }

    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getCouponFrom() {
        return couponFrom;
    }

    public void setCouponFrom(String couponFrom) {
        this.couponFrom = couponFrom;
    }

    public String getTenderQuota() {
        return tenderQuota;
    }

    public void setTenderQuota(String tenderQuota) {
        this.tenderQuota = tenderQuota;
    }

    public String getProjectExpirationType() {
        return projectExpirationType;
    }

    public void setProjectExpirationType(String projectExpirationType) {
        this.projectExpirationType = projectExpirationType;
    }

    public String getUsedTimeDay() {
        return usedTimeDay;
    }

    public void setUsedTimeDay(String usedTimeDay) {
        this.usedTimeDay = usedTimeDay;
    }

    public String getUsedTimeHour() {
        return usedTimeHour;
    }

    public void setUsedTimeHour(String usedTimeHour) {
        this.usedTimeHour = usedTimeHour;
    }

	public String getProjectNid() {
		return projectNid;
	}

	public void setProjectNid(String projectNid) {
		this.projectNid = projectNid;
	}

	public String getTenderTimeDay() {
		return tenderTimeDay;
	}

	public void setTenderTimeDay(String tenderTimeDay) {
		this.tenderTimeDay = tenderTimeDay;
	}

	public String getTenderTimeHour() {
		return tenderTimeHour;
	}

	public void setTenderTimeHour(String tenderTimeHour) {
		this.tenderTimeHour = tenderTimeHour;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(String recoverTime) {
		this.recoverTime = recoverTime;
	}

	public String getRecoverAccount() {
		return recoverAccount;
	}

	public void setRecoverAccount(String recoverAccount) {
		this.recoverAccount = recoverAccount;
	}

	public String getRecoverStatus() {
		return recoverStatus;
	}

	public void setRecoverStatus(String recoverStatus) {
		this.recoverStatus = recoverStatus;
	}

    public String getCouponProfitTime() {
        return couponProfitTime;
    }

    public void setCouponProfitTime(String couponProfitTime) {
        this.couponProfitTime = couponProfitTime;
    }

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    public String getDebtPlanNid() {
        return debtPlanNid;
    }

    public void setDebtPlanNid(String debtPlanNid) {
        this.debtPlanNid = debtPlanNid;
    }

    public String getDebtLockPeriod() {
        return debtLockPeriod;
    }

    public void setDebtLockPeriod(String debtLockPeriod) {
        this.debtLockPeriod = debtLockPeriod;
    }

    public String getExpectApr() {
        return expectApr;
    }

    public void setExpectApr(String expectApr) {
        this.expectApr = expectApr;
    }

    public String getDebtPlanStatus() {
        return debtPlanStatus;
    }

    public void setDebtPlanStatus(String debtPlanStatus) {
        this.debtPlanStatus = debtPlanStatus;
    }

    public String getRecoverYesTime() {
        return recoverYesTime;
    }

    public void setRecoverYesTime(String recoverYesTime) {
        this.recoverYesTime = recoverYesTime;
    }

    public Integer getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(Integer usedFlag) {
        this.usedFlag = usedFlag;
    }
    
    
    
}