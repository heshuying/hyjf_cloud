package com.hyjf.am.trade.dao.model.customize;

/**
 * @author pangchengchao
 * @version AdminRepayDelayCustomize, v0.1 2018/7/6 10:44
 */
public class AdminRepayDelayCustomize {
    private String borrowNid;
    private String userId;
    private String borrowUserName;
    private String borrowName;
    private String projectType;
    private String projectTypeName;
    private String borrowPeriod;
    private String borrowApr;
    private String borrowAccount;
    private String borrowAccountYes;
    private String repayLastTime;
    private String borrowStyle;
    private String borrowStyleName;
    private String repayAccountWait;
    private String interestCapitalWait;
    private String repayOrgName;
    private String recoverLastTime;
    private String repayAccountAll;
    private String principalInterest;
    
    
    public String getPrincipalInterest() {
		return principalInterest;
	}

	public void setPrincipalInterest(String principalInterest) {
		this.principalInterest = principalInterest;
	}

	public String getRepayAccountAll() {
		return repayAccountAll;
	}

	public void setRepayAccountAll(String repayAccountAll) {
		this.repayAccountAll = repayAccountAll;
	}

	public String getRepayOrgName() {
		return repayOrgName;
	}

	public void setRepayOrgName(String repayOrgName) {
		this.repayOrgName = repayOrgName;
	}

	public String getRecoverLastTime() {
		return recoverLastTime;
	}

	public void setRecoverLastTime(String recoverLastTime) {
		this.recoverLastTime = recoverLastTime;
	}

	public String getRepayAccountWait() {
		return repayAccountWait;
	}

	public void setRepayAccountWait(String repayAccountWait) {
		this.repayAccountWait = repayAccountWait;
	}

	public String getInterestCapitalWait() {
		return interestCapitalWait;
	}

	public void setInterestCapitalWait(String interestCapitalWait) {
		this.interestCapitalWait = interestCapitalWait;
	}

	public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(String borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public String getBorrowAccountYes() {
        return borrowAccountYes;
    }

    public void setBorrowAccountYes(String borrowAccountYes) {
        this.borrowAccountYes = borrowAccountYes;
    }

    public String getRepayLastTime() {
        return repayLastTime;
    }

    public void setRepayLastTime(String repayLastTime) {
        this.repayLastTime = repayLastTime;
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
}
