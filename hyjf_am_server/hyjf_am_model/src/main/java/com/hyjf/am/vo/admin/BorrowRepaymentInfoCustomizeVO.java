package com.hyjf.am.vo.admin;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoCustomizeVO, v0.1 2018/7/9 9:25
 */
public class BorrowRepaymentInfoCustomizeVO {
    // ========================参数=============================
    private String assetId;//资产编号
    private String accedeOrderId;//出借订单号
    private String nid;// 出借nid
    private String borrowNid;// 借款编号
    private String planNid;//计划编号
    private String userId;// 借款人ID
    private String borrowUserName;// 借款人用户名
    private String repayOrgName;// 垫付机构用户名
    private String borrowStyle;// 类型
    private String borrowName;// 借款标题
    private String projectType;// 项目类型id
    private String projectTypeName;// 项目类型名称
    private String borrowPeriod;// 借款期限
    private String borrowApr;// 年化收益
    private String borrowAccount;// 借款金额
    private String borrowAccountYes;// 借到金额
    private String repayType;// 还款方式
    private String recoverUserId;// 出借人ID
    private String recoverTrueName;// 出借人姓名
    private String recoverUserName;// 出借人用户名
    private String recoverUserAttribute;// 出借人用户属性（当前）
    private String recoverRegionName;// 出借人所属一级分部（当前）
    private String recoverBranchName;// 出借人所属二级分部（当前）
    private String recoverDepartmentName;// 出借人所属团队（当前）
    private String referrerName;// 推荐人（当前）
    private String referrerUserId;// 推荐人ID（当前）
    private String referrerTrueName;// 推荐人姓名（当前）
    private String referrerRegionName;// 推荐人所属一级分部（当前）
    private String referrerBranchName;//推荐人所属二级分部（当前）
    private String referrerDepartmentName; //推荐人所属团队（当前）
    private String recoverPeriod;// 出借期限
    private String recoverTotal;// 出借金额
    private String recoverCapital;// 应还本金
    private String recoverInterest;// 应还利息
    private String recoverAccount;// 应还本息
    private String recoverFee;// 管理费
    private String recoverCapitalYes;// 已还本金
    private String recoverInterestYes;// 已还利息
    private String recoverAccountYes;// 已换本息
    private String recoverCapitalWait;// 未还本金
    private String recoverInterestWait;// 未还利息
    private String recoverAccountWait;// 未还本息
    private String status;// 还款状态
    private String recoverLastTime;// 最后还款日
    private String repayActionTime; //实际还款时间
    private String repayOrdid; //还款订单号
    private String repayBatchNo; //还款批次号
    private String instName; //机构名称

    
    
    
    private String repayAccountAll;
    private String repayAccountCapital;
    private String repayAccountInterest;
    private String borrowManager;
    private String chargeDays;
    private String jianxi;
    private String chargeInterest;
    private String lateDays;
    private String lateInterest;
    private String repayMoneySource;
    private String repayUsername;
    private String autoRepay;
    private String submitter;
    
    
    
    public String getRepayAccountAll() {
		return repayAccountAll;
	}

	public void setRepayAccountAll(String repayAccountAll) {
		this.repayAccountAll = repayAccountAll;
	}

	public String getRepayAccountCapital() {
		return repayAccountCapital;
	}

	public void setRepayAccountCapital(String repayAccountCapital) {
		this.repayAccountCapital = repayAccountCapital;
	}

	public String getRepayAccountInterest() {
		return repayAccountInterest;
	}

	public void setRepayAccountInterest(String repayAccountInterest) {
		this.repayAccountInterest = repayAccountInterest;
	}

	public String getBorrowManager() {
		return borrowManager;
	}

	public void setBorrowManager(String borrowManager) {
		this.borrowManager = borrowManager;
	}

	public String getChargeDays() {
		return chargeDays;
	}

	public void setChargeDays(String chargeDays) {
		this.chargeDays = chargeDays;
	}

	public String getJianxi() {
		return jianxi;
	}

	public void setJianxi(String jianxi) {
		this.jianxi = jianxi;
	}

	public String getChargeInterest() {
		return chargeInterest;
	}

	public void setChargeInterest(String chargeInterest) {
		this.chargeInterest = chargeInterest;
	}

	public String getLateDays() {
		return lateDays;
	}

	public void setLateDays(String lateDays) {
		this.lateDays = lateDays;
	}

	public String getLateInterest() {
		return lateInterest;
	}

	public void setLateInterest(String lateInterest) {
		this.lateInterest = lateInterest;
	}

	public String getRepayMoneySource() {
		return repayMoneySource;
	}

	public void setRepayMoneySource(String repayMoneySource) {
		this.repayMoneySource = repayMoneySource;
	}

	public String getRepayUsername() {
		return repayUsername;
	}

	public void setRepayUsername(String repayUsername) {
		this.repayUsername = repayUsername;
	}

	public String getAutoRepay() {
		return autoRepay;
	}

	public void setAutoRepay(String autoRepay) {
		this.autoRepay = autoRepay;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	/**
     * 还款冻结订单号
     */
    private String freezeOrderId;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

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

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
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

    public String getRepayOrgName() {
        return repayOrgName;
    }

    public void setRepayOrgName(String repayOrgName) {
        this.repayOrgName = repayOrgName;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
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

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getRecoverUserId() {
        return recoverUserId;
    }

    public void setRecoverUserId(String recoverUserId) {
        this.recoverUserId = recoverUserId;
    }

    public String getRecoverTrueName() {
        return recoverTrueName;
    }

    public void setRecoverTrueName(String recoverTrueName) {
        this.recoverTrueName = recoverTrueName;
    }

    public String getRecoverUserName() {
        return recoverUserName;
    }

    public void setRecoverUserName(String recoverUserName) {
        this.recoverUserName = recoverUserName;
    }

    public String getRecoverUserAttribute() {
        return recoverUserAttribute;
    }

    public void setRecoverUserAttribute(String recoverUserAttribute) {
        this.recoverUserAttribute = recoverUserAttribute;
    }

    public String getRecoverRegionName() {
        return recoverRegionName;
    }

    public void setRecoverRegionName(String recoverRegionName) {
        this.recoverRegionName = recoverRegionName;
    }

    public String getRecoverBranchName() {
        return recoverBranchName;
    }

    public void setRecoverBranchName(String recoverBranchName) {
        this.recoverBranchName = recoverBranchName;
    }

    public String getRecoverDepartmentName() {
        return recoverDepartmentName;
    }

    public void setRecoverDepartmentName(String recoverDepartmentName) {
        this.recoverDepartmentName = recoverDepartmentName;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getReferrerUserId() {
        return referrerUserId;
    }

    public void setReferrerUserId(String referrerUserId) {
        this.referrerUserId = referrerUserId;
    }

    public String getReferrerTrueName() {
        return referrerTrueName;
    }

    public void setReferrerTrueName(String referrerTrueName) {
        this.referrerTrueName = referrerTrueName;
    }

    public String getReferrerRegionName() {
        return referrerRegionName;
    }

    public void setReferrerRegionName(String referrerRegionName) {
        this.referrerRegionName = referrerRegionName;
    }

    public String getReferrerBranchName() {
        return referrerBranchName;
    }

    public void setReferrerBranchName(String referrerBranchName) {
        this.referrerBranchName = referrerBranchName;
    }

    public String getReferrerDepartmentName() {
        return referrerDepartmentName;
    }

    public void setReferrerDepartmentName(String referrerDepartmentName) {
        this.referrerDepartmentName = referrerDepartmentName;
    }

    public String getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(String recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public String getRecoverTotal() {
        return recoverTotal;
    }

    public void setRecoverTotal(String recoverTotal) {
        this.recoverTotal = recoverTotal;
    }

    public String getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(String recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public String getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(String recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public String getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(String recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public String getRecoverFee() {
        return recoverFee;
    }

    public void setRecoverFee(String recoverFee) {
        this.recoverFee = recoverFee;
    }

    public String getRecoverCapitalYes() {
        return recoverCapitalYes;
    }

    public void setRecoverCapitalYes(String recoverCapitalYes) {
        this.recoverCapitalYes = recoverCapitalYes;
    }

    public String getRecoverInterestYes() {
        return recoverInterestYes;
    }

    public void setRecoverInterestYes(String recoverInterestYes) {
        this.recoverInterestYes = recoverInterestYes;
    }

    public String getRecoverAccountYes() {
        return recoverAccountYes;
    }

    public void setRecoverAccountYes(String recoverAccountYes) {
        this.recoverAccountYes = recoverAccountYes;
    }

    public String getRecoverCapitalWait() {
        return recoverCapitalWait;
    }

    public void setRecoverCapitalWait(String recoverCapitalWait) {
        this.recoverCapitalWait = recoverCapitalWait;
    }

    public String getRecoverInterestWait() {
        return recoverInterestWait;
    }

    public void setRecoverInterestWait(String recoverInterestWait) {
        this.recoverInterestWait = recoverInterestWait;
    }

    public String getRecoverAccountWait() {
        return recoverAccountWait;
    }

    public void setRecoverAccountWait(String recoverAccountWait) {
        this.recoverAccountWait = recoverAccountWait;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public String getRepayActionTime() {
        return repayActionTime;
    }

    public void setRepayActionTime(String repayActionTime) {
        this.repayActionTime = repayActionTime;
    }

    public String getRepayOrdid() {
        return repayOrdid;
    }

    public void setRepayOrdid(String repayOrdid) {
        this.repayOrdid = repayOrdid;
    }

    public String getRepayBatchNo() {
        return repayBatchNo;
    }

    public void setRepayBatchNo(String repayBatchNo) {
        this.repayBatchNo = repayBatchNo;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getFreezeOrderId() {
        return freezeOrderId;
    }

    public void setFreezeOrderId(String freezeOrderId) {
        this.freezeOrderId = freezeOrderId;
    }
}
