/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */

public class BorrowCustomizeVO  extends BaseVO implements Serializable{

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 借款编码
	 */
	private String borrowNid;
	/**
	 * 借款用户
	 */
	private String username;
	private Integer userId;
	/**
	 * 垫付机构用户名
	 */
	private String repayOrgUserName;
	/**
	 * 项目申请人
	 */
	private String applicant;
	/**
	 * 借款标题
	 */
	private String borrowName;

	/**
	 * 项目标题
	 */
	private String projectName;
	
	/**
	 * 借款金额
	 */
	private String account;
	/**
	 * 年利率
	 */
	private String borrowApr;
	/**
	 * 借款期限
	 */
	private String borrowPeriod;
	/**
	 * 借款期限
	 */
	private String borrowPeriodType;
	/**
	 * 还款方式
	 */
	private String borrowStyle;
	/**
	 * 还款方式名称
	 */
	private String borrowStyleName;
	/**
	 * 项目类型
	 */
	private String projectType;
	/**
	 * 项目类型名称
	 */
	private String projectTypeName;
	/**
	 * 发布时间
	 */
	private Date addtime;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 满标时间
	 */
	private String borrowFullTime;
	/**
	 * 放款时间
	 */
	private String recoverLastTime;
	/**
	 * 放款时间
	 */
	private String recoverLastDay;

	/**
	 * 已借到金额
	 */
	private String borrowAccountYes;

	/**
	 * 剩余金额
	 */
	private String borrowAccountWait;

	/**
	 * 借款进度
	 */
	private String borrowAccountScale;
	/***
	 * 复审用户
	 */
	private String reverifyUserName;

	/***
	 * 复审时间
	 */
	private String reverifyTime;
	/***
	 * 发行人
	 */
	private String borrowPublisher;
	// 资产编号
	private String borrowAssetNumber;
	// 产品加息收益率
	private String borrowExtraYield;
	// 协议期数
	private String contractPeriod;
	// 最小出借金额万
	private String tenderAccountMinWan;
	/* 倍增金额 increaseMoney ----------- */
	private String increaseMoney;
	// 标的删除标识
	private String delFlag;
	// 标的撤销标识
	private String revokeFlag;
	// 垫付机构名称
	private String repay_org_name;
	// 计划编号
	private String planNid;
	// 资金来源
	private String instName;
	// 标的标签
	private String labelName;
	//ADD BY LIUSHOUYI 合规检查 START
	private String userType;
	//用户姓名
	private String truename;
	//公司名称
	private String businame;
	//ADD BY LIUSHOUYI 合规检查 END

	// ADD BY ZHANGYK START
	/**
	 * 初审时间
	 */
	private String verifyTime;
	// ADD BY ZHANGYK END

	private Integer repayOrgUserId;
	private String manageFeeRate;
	private String differentialRate;
	private Integer lateFreeDays;
    private String lateInterestRate;
    private Integer id;
    private String borrowUserName;
    private Integer borrowValidTime;
    private Integer labelId;
    private Integer isShow;
    private Integer isEngineUsed;
    private Integer isInstallment;
    private Integer isMonth;
    private Integer registStatus;
    private Date registTime;
    private Integer registUserId;
    private String registUserName;
    private Integer verifyStatus;
    private Integer verifyOverTime;
    private String verifyUserid;
    private String verifyUserName;
    private String verifyRemark;
    private String verifyContents;
    private Integer borrowStatus;
    private Integer ontime;
    private String borrowEndTime;
    private Integer borrowFullStatus;
    private Integer tenderTimes;
    private String borrowService;
    private Integer reverifyStatus;
    private String reverifyUserid;
    private String reverifyRemark;
    private String reverifyContents;
    private Integer repayLastTime;
    private Integer repayNextTime;
    private Integer repayStatus;
    private Integer repayFullStatus;
    private BigDecimal repayFeeNormal;
    private BigDecimal repayAccountAll;
    private BigDecimal repayAccountInterest;
    private BigDecimal repayAccountCapital;
    private BigDecimal repayAccountYes;
    private BigDecimal repayAccountInterestYes;
    private BigDecimal repayAccountCapitalYes;
    private BigDecimal repayAccountWait;
    private BigDecimal repayAccountInterestWait;
    private BigDecimal repayAccountCapitalWait;
    private String borrowManager;
    private String serviceFeeRate;
    private String createUserName;
    private String addIp;
    private Date createTime;
    private Date updatetime;
    private Boolean increaseInterestFlag;
	// 备案撤销状态 0 不可用 1 可用
    private String registCancelEnable;
    // 标的可删除状态 0 不可用 1 可用
    private String borrowDeleteEnable;

    /**
	 * recoverLastTime
	 * 
	 * @return the recoverLastTime
	 */

	public String getRecoverLastTime() {
		return recoverLastTime;
	}

	/**
	 * @param recoverLastTime
	 *            the recoverLastTime to set
	 */

	public void setRecoverLastTime(String recoverLastTime) {
		this.recoverLastTime = recoverLastTime;
	}

	/**
	 * borrowAccountYes
	 * 
	 * @return the borrowAccountYes
	 */

	public String getBorrowAccountYes() {
		return borrowAccountYes;
	}

	/**
	 * @param borrowAccountYes
	 *            the borrowAccountYes to set
	 */

	public void setBorrowAccountYes(String borrowAccountYes) {
		this.borrowAccountYes = borrowAccountYes;
	}

	/**
	 * borrowAccountWait
	 * 
	 * @return the borrowAccountWait
	 */

	public String getBorrowAccountWait() {
		return borrowAccountWait;
	}

	/**
	 * @param borrowAccountWait
	 *            the borrowAccountWait to set
	 */

	public void setBorrowAccountWait(String borrowAccountWait) {
		this.borrowAccountWait = borrowAccountWait;
	}

	/**
	 * borrowAccountScale
	 * 
	 * @return the borrowAccountScale
	 */

	public String getBorrowAccountScale() {
		return borrowAccountScale;
	}

	/**
	 * @param borrowAccountScale
	 *            the borrowAccountScale to set
	 */

	public void setBorrowAccountScale(String borrowAccountScale) {
		this.borrowAccountScale = borrowAccountScale;
	}

	/**
	 * borrowFullTime
	 * 
	 * @return the borrowFullTime
	 */

	public String getBorrowFullTime() {
		return borrowFullTime;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
	 * @param borrowFullTime
	 *            the borrowFullTime to set
	 */

	public void setBorrowFullTime(String borrowFullTime) {
		this.borrowFullTime = borrowFullTime;
	}

	/**
	 * borrowStyle
	 * 
	 * @return the borrowStyle
	 */

	public String getBorrowStyle() {
		return borrowStyle;
	}

	/**
	 * @param borrowStyle
	 *            the borrowStyle to set
	 */

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	/**
	 * borrowStyleName
	 * 
	 * @return the borrowStyleName
	 */

	public String getBorrowStyleName() {
		return borrowStyleName;
	}

	/**
	 * @param borrowStyleName
	 *            the borrowStyleName to set
	 */

	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}

	/**
	 * projectType
	 * 
	 * @return the projectType
	 */

	public String getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	/**
	 * projectTypeName
	 * 
	 * @return the projectTypeName
	 */

	public String getProjectTypeName() {
		return projectTypeName;
	}

	/**
	 * @param projectTypeName
	 *            the projectTypeName to set
	 */

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	/**
	 * status
	 * 
	 * @return the status
	 */

	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * borrowNid
	 * 
	 * @return the borrowNid
	 */

	public String getBorrowNid() {
		return borrowNid;
	}

	/**
	 * @param borrowNid
	 *            the borrowNid to set
	 */

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	/**
	 * username
	 * 
	 * @return the username
	 */

	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * borrowName
	 * 
	 * @return the borrowName
	 */

	public String getBorrowName() {
		return borrowName;
	}

	/**
	 * @param borrowName
	 *            the borrowName to set
	 */

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	/**
	 * account
	 * 
	 * @return the account
	 */

	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * borrowApr
	 * 
	 * @return the borrowApr
	 */

	public String getBorrowApr() {
		return borrowApr;
	}

	/**
	 * @param borrowApr
	 *            the borrowApr to set
	 */

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	/**
	 * borrowPeriod
	 * 
	 * @return the borrowPeriod
	 */

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	/**
	 * @param borrowPeriod
	 *            the borrowPeriod to set
	 */

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	/**
	 * addtime
	 * 
	 * @return the addtime
	 */

	public Date getAddtime() {
		return addtime;
	}

	/**
	 * @param addtime
	 *            the addtime to set
	 */

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	/**
	 * serialversionuid
	 * 
	 * @return the serialversionuid
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getRecoverLastDay() {
		return recoverLastDay;
	}

	public void setRecoverLastDay(String recoverLastDay) {
		this.recoverLastDay = recoverLastDay;
	}

	public String getReverifyTime() {
		return reverifyTime;
	}

	public void setReverifyTime(String reverifyTime) {
		this.reverifyTime = reverifyTime;
	}

	public String getBorrowPublisher() {
		return borrowPublisher;
	}

	public void setBorrowPublisher(String borrowPublisher) {
		this.borrowPublisher = borrowPublisher;
	}

	public String getBorrowAssetNumber() {
		return borrowAssetNumber;
	}

	public void setBorrowAssetNumber(String borrowAssetNumber) {
		this.borrowAssetNumber = borrowAssetNumber;
	}

	public String getBorrowExtraYield() {
		return borrowExtraYield;
	}

	public void setBorrowExtraYield(String borrowExtraYield) {
		this.borrowExtraYield = borrowExtraYield;
	}

	public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public String getBorrowPeriodType() {
		return borrowPeriodType;
	}

	public void setBorrowPeriodType(String borrowPeriodType) {
		this.borrowPeriodType = borrowPeriodType;
	}

	public String getTenderAccountMinWan() {
		return tenderAccountMinWan;
	}

	public void setTenderAccountMinWan(String tenderAccountMinWan) {
		this.tenderAccountMinWan = tenderAccountMinWan;
	}

	public String getIncreaseMoney() {
		return increaseMoney;
	}

	public void setIncreaseMoney(String increaseMoney) {
		this.increaseMoney = increaseMoney;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRevokeFlag() {
		return revokeFlag;
	}

	public void setRevokeFlag(String revokeFlag) {
		this.revokeFlag = revokeFlag;
	}

	public String getRepay_org_name() {
		return repay_org_name;
	}

	public void setRepay_org_name(String repay_org_name) {
		this.repay_org_name = repay_org_name;
	}

	public String getReverifyUserName() {
		return reverifyUserName;
	}

	public void setReverifyUserName(String reverifyUserName) {
		this.reverifyUserName = reverifyUserName;
	}

	public String getRepayOrgUserName() {
		return repayOrgUserName;
	}

	public void setRepayOrgUserName(String repayOrgUserName) {
		this.repayOrgUserName = repayOrgUserName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * planNid
	 * @return the planNid
	 */
	
	public String getPlanNid() {
		return planNid;
	}

	/**
	 * @param planNid the planNid to set
	 */
	
	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}

	/**
	 * instName
	 * @return the instName
	 */
	
	public String getInstName() {
		return instName;
	}

	/**
	 * @param instName the instName to set
	 */
	
	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	/**
	 * truename
	 * @return the truename
	 */
		
	public String getTruename() {
		return truename;
			
	}

	/**
	 * @param truename the truename to set
	 */
		
	public void setTrueName(String truename) {
		this.truename = truename;
			
	}

	/**
	 * userType
	 * @return the userType
	 */
		
	public String getUserType() {
		return userType;
			
	}

	/**
	 * @param userType the userType to set
	 */
		
	public void setUserType(String userType) {
		this.userType = userType;
			
	}

	/**
	 * businame
	 * @return the businame
	 */
		
	public String getBusiname() {
		return businame;
			
	}

	/**
	 * @param businame the businame to set
	 */
		
	public void setBusiname(String businame) {
		this.businame = businame;
			
	}

	public String getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	public Integer getRepayOrgUserId() {
		return repayOrgUserId;
	}

	public void setRepayOrgUserId(Integer repayOrgUserId) {
		this.repayOrgUserId = repayOrgUserId;
	}

	public String getManageFeeRate() {
		return manageFeeRate;
	}

	public void setManageFeeRate(String manageFeeRate) {
		this.manageFeeRate = manageFeeRate;
	}

	public String getDifferentialRate() {
		return differentialRate;
	}

	public void setDifferentialRate(String differentialRate) {
		this.differentialRate = differentialRate;
	}

	public Integer getLateFreeDays() {
		return lateFreeDays;
	}

	public void setLateFreeDays(Integer lateFreeDays) {
		this.lateFreeDays = lateFreeDays;
	}

    public String getLateInterestRate() {
        return lateInterestRate;
    }

    public void setLateInterestRate(String lateInterestRate) {
        this.lateInterestRate = lateInterestRate;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public Integer getBorrowValidTime() {
        return borrowValidTime;
    }

    public void setBorrowValidTime(Integer borrowValidTime) {
        this.borrowValidTime = borrowValidTime;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsEngineUsed() {
        return isEngineUsed;
    }

    public void setIsEngineUsed(Integer isEngineUsed) {
        this.isEngineUsed = isEngineUsed;
    }

    public Integer getIsInstallment() {
        return isInstallment;
    }

    public void setIsInstallment(Integer isInstallment) {
        this.isInstallment = isInstallment;
    }

    public Integer getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(Integer isMonth) {
        this.isMonth = isMonth;
    }

    public Integer getRegistStatus() {
        return registStatus;
    }

    public void setRegistStatus(Integer registStatus) {
        this.registStatus = registStatus;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Integer getRegistUserId() {
        return registUserId;
    }

    public void setRegistUserId(Integer registUserId) {
        this.registUserId = registUserId;
    }

    public String getRegistUserName() {
        return registUserName;
    }

    public void setRegistUserName(String registUserName) {
        this.registUserName = registUserName;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Integer getVerifyOverTime() {
        return verifyOverTime;
    }

    public void setVerifyOverTime(Integer verifyOverTime) {
        this.verifyOverTime = verifyOverTime;
    }

    public String getVerifyUserid() {
        return verifyUserid;
    }

    public void setVerifyUserid(String verifyUserid) {
        this.verifyUserid = verifyUserid;
    }

    public String getVerifyUserName() {
        return verifyUserName;
    }

    public void setVerifyUserName(String verifyUserName) {
        this.verifyUserName = verifyUserName;
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark;
    }

    public String getVerifyContents() {
        return verifyContents;
    }

    public void setVerifyContents(String verifyContents) {
        this.verifyContents = verifyContents;
    }

    public Integer getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Integer borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public Integer getOntime() {
        return ontime;
    }

    public void setOntime(Integer ontime) {
        this.ontime = ontime;
    }

    public String getBorrowEndTime() {
        return borrowEndTime;
    }

    public void setBorrowEndTime(String borrowEndTime) {
        this.borrowEndTime = borrowEndTime;
    }

    public Integer getBorrowFullStatus() {
        return borrowFullStatus;
    }

    public void setBorrowFullStatus(Integer borrowFullStatus) {
        this.borrowFullStatus = borrowFullStatus;
    }

    public Integer getTenderTimes() {
        return tenderTimes;
    }

    public void setTenderTimes(Integer tenderTimes) {
        this.tenderTimes = tenderTimes;
    }

    public String getBorrowService() {
        return borrowService;
    }

    public void setBorrowService(String borrowService) {
        this.borrowService = borrowService;
    }

    public Integer getReverifyStatus() {
        return reverifyStatus;
    }

    public void setReverifyStatus(Integer reverifyStatus) {
        this.reverifyStatus = reverifyStatus;
    }

    public String getReverifyUserid() {
        return reverifyUserid;
    }

    public void setReverifyUserid(String reverifyUserid) {
        this.reverifyUserid = reverifyUserid;
    }

    public String getReverifyRemark() {
        return reverifyRemark;
    }

    public void setReverifyRemark(String reverifyRemark) {
        this.reverifyRemark = reverifyRemark;
    }

    public String getReverifyContents() {
        return reverifyContents;
    }

    public void setReverifyContents(String reverifyContents) {
        this.reverifyContents = reverifyContents;
    }

    public Integer getRepayLastTime() {
        return repayLastTime;
    }

    public void setRepayLastTime(Integer repayLastTime) {
        this.repayLastTime = repayLastTime;
    }

    public Integer getRepayNextTime() {
        return repayNextTime;
    }

    public void setRepayNextTime(Integer repayNextTime) {
        this.repayNextTime = repayNextTime;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Integer getRepayFullStatus() {
        return repayFullStatus;
    }

    public void setRepayFullStatus(Integer repayFullStatus) {
        this.repayFullStatus = repayFullStatus;
    }

    public BigDecimal getRepayFeeNormal() {
        return repayFeeNormal;
    }

    public void setRepayFeeNormal(BigDecimal repayFeeNormal) {
        this.repayFeeNormal = repayFeeNormal;
    }

    public BigDecimal getRepayAccountAll() {
        return repayAccountAll;
    }

    public void setRepayAccountAll(BigDecimal repayAccountAll) {
        this.repayAccountAll = repayAccountAll;
    }

    public BigDecimal getRepayAccountInterest() {
        return repayAccountInterest;
    }

    public void setRepayAccountInterest(BigDecimal repayAccountInterest) {
        this.repayAccountInterest = repayAccountInterest;
    }

    public BigDecimal getRepayAccountCapital() {
        return repayAccountCapital;
    }

    public void setRepayAccountCapital(BigDecimal repayAccountCapital) {
        this.repayAccountCapital = repayAccountCapital;
    }

    public BigDecimal getRepayAccountYes() {
        return repayAccountYes;
    }

    public void setRepayAccountYes(BigDecimal repayAccountYes) {
        this.repayAccountYes = repayAccountYes;
    }

    public BigDecimal getRepayAccountInterestYes() {
        return repayAccountInterestYes;
    }

    public void setRepayAccountInterestYes(BigDecimal repayAccountInterestYes) {
        this.repayAccountInterestYes = repayAccountInterestYes;
    }

    public BigDecimal getRepayAccountCapitalYes() {
        return repayAccountCapitalYes;
    }

    public void setRepayAccountCapitalYes(BigDecimal repayAccountCapitalYes) {
        this.repayAccountCapitalYes = repayAccountCapitalYes;
    }

    public BigDecimal getRepayAccountWait() {
        return repayAccountWait;
    }

    public void setRepayAccountWait(BigDecimal repayAccountWait) {
        this.repayAccountWait = repayAccountWait;
    }

    public BigDecimal getRepayAccountInterestWait() {
        return repayAccountInterestWait;
    }

    public void setRepayAccountInterestWait(BigDecimal repayAccountInterestWait) {
        this.repayAccountInterestWait = repayAccountInterestWait;
    }

    public BigDecimal getRepayAccountCapitalWait() {
        return repayAccountCapitalWait;
    }

    public void setRepayAccountCapitalWait(BigDecimal repayAccountCapitalWait) {
        this.repayAccountCapitalWait = repayAccountCapitalWait;
    }

    public String getBorrowManager() {
        return borrowManager;
    }

    public void setBorrowManager(String borrowManager) {
        this.borrowManager = borrowManager;
    }

    public String getServiceFeeRate() {
        return serviceFeeRate;
    }

    public void setServiceFeeRate(String serviceFeeRate) {
        this.serviceFeeRate = serviceFeeRate;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Boolean getIncreaseInterestFlag() {
        return increaseInterestFlag;
    }

    public void setIncreaseInterestFlag(Boolean increaseInterestFlag) {
        this.increaseInterestFlag = increaseInterestFlag;
    }

	public String getRegistCancelEnable() {
		return registCancelEnable;
	}

	public void setRegistCancelEnable(String registCancelEnable) {
		this.registCancelEnable = registCancelEnable;
	}

	public String getBorrowDeleteEnable() {
		return borrowDeleteEnable;
	}

	public void setBorrowDeleteEnable(String borrowDeleteEnable) {
		this.borrowDeleteEnable = borrowDeleteEnable;
	}
}
