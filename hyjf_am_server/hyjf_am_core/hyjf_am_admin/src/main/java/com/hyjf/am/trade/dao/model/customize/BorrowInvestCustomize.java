/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

/**
 * @author wangjun
 * @version BorrowInvestCustomize, v0.1 2018/7/10 9:26
 */
public class BorrowInvestCustomize {
    /**
     * 借款标题
     */
    private String borrowName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 推荐人
     */
    private String referrerName;
    /**
     * 项目编号
     */
    private String borrowNid;
    /**
     * 计划编号
     */
    private String planNid;
    /**
     * 年化利率
     */
    private String borrowApr;
    /**
     * 借款期限
     */
    private String borrowPeriod;
    /**
     * 还款方式
     */
    private String borrowStyleName;
    /**
     * 出借金额
     */
    private String account;
    /**
     * 渠道
     */
    private String utmSource;
    /**
     * 操作平台
     */
    private String operatingDeck;
    /**
     * 出借时间
     */
    private String createTime;

    /**
     * 借款人ID
     */
    private String userId;

    /**
     * NID
     */
    private String nid;
    /**
     * 是否邮件发送1可以发，0不可以发
     */
    private String resendMessage;

    /**
     * 项目类型
     */
    private String borrowProjectTypeName;

    /**
     * 出借订单号
     */
    private String tenderOrderNum;

    /**
     * 冻结订单号
     */
    private String freezeOrderNum;

    /**
     * 出借人用户名
     */
    private String tenderUsername;

    /**
     * 出借人用户ID
     */
    private String tenderUserId;

    /**
     * 推荐人ID（当前）
     */
    private String referrerUserId;
    /**
     * 推荐人ID（出借时）
     */
    private String tenderReferrerUserId;
    /**
     * 出借人用户属性（出借时）
     */
    private String tenderUserAttribute;
    /**
     * 推荐人用户属性（出借时）
     */
    private String inviteUserAttribute;
    /**
     * 推荐人（出借时）
     */
    private String tenderReferrerUsername;

    /**
     * 一级分部（出借时）
     */
    private String departmentLevel1Name;

    /**
     * 二级分部（出借时）
     */
    private String departmentLevel2Name;

    /**
     * 团队（出借时）
     */
    private String teamName;

    /**
     * 出借人用户属性（当前）
     */
    private String tenderUserAttributeNow;

    /**
     * 出借人所属一级分部（当前）
     */
    private String tenderRegionName;

    /**
     * 出借人所属二级分部（当前）
     */
    private String tenderBranchName;

    /**
     * 出借人所属团队（当前）
     */
    private String tenderDepartmentName;

    /**
     * 推荐人姓名（当前）
     */
    private String referrerTrueName;

    /**
     * 推荐人所属一级分部（当前）
     */
    private String referrerRegionName;

    /**
     * 推荐人所属二级分部（当前）
     */
    private String referrerBranchName;

    /**
     * 推荐人所属团队（当前）
     */
    private String referrerDepartmentName;
    /**
     * 出借类别 0手动投标，1或者别的预约投标
     */
    private String investType;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;
    /**
     * 汇计划加入订单号
     */
    private String accedeOrderIdSrch;
    /**
     * 机构名称
     */
    private String instName;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 复审通过时间
     */
    private String reAuthPassTime;

    /**
     * 合同状态
     */
    private String contractStatus;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 模板编号
     */
    private String templetId;

    /**
     * 合同生成时间
     */
    private String contractCreateTime;

    /**
     * 合同签署时间
     */
    private String contractSignTime;

    /**
     * 合同下载地址
     */
    private String downloadUrl;

    /**
     * 合同查看地址
     */
    private String viewpdfUrl;

    /**
     * 脱敏合同地址
     */
    private String imgUrl;

    /**
     * 是否复投投标  1；是   0：否
     */
    private String tenderType;
    private String utmSource1;
    private String utmSource2;

    /** 银行交易状态（S-成功;F-失败;A-待处理;D-正在处理;C-撤销;）*/
    private String state;
    /** 债权结束状态描述*/
    private String stateDesc;
    
    public String getUtmSource1() {
		return utmSource1;
	}

	public void setUtmSource1(String utmSource1) {
		this.utmSource1 = utmSource1;
	}

	public String getUtmSource2() {
		return utmSource2;
	}

	public void setUtmSource2(String utmSource2) {
		this.utmSource2 = utmSource2;
	}

	public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
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

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getOperatingDeck() {
        return operatingDeck;
    }

    public void setOperatingDeck(String operatingDeck) {
        this.operatingDeck = operatingDeck;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getResendMessage() {
        return resendMessage;
    }

    public void setResendMessage(String resendMessage) {
        this.resendMessage = resendMessage;
    }

    public String getBorrowProjectTypeName() {
        return borrowProjectTypeName;
    }

    public void setBorrowProjectTypeName(String borrowProjectTypeName) {
        this.borrowProjectTypeName = borrowProjectTypeName;
    }

    public String getTenderOrderNum() {
        return tenderOrderNum;
    }

    public void setTenderOrderNum(String tenderOrderNum) {
        this.tenderOrderNum = tenderOrderNum;
    }

    public String getFreezeOrderNum() {
        return freezeOrderNum;
    }

    public void setFreezeOrderNum(String freezeOrderNum) {
        this.freezeOrderNum = freezeOrderNum;
    }

    public String getTenderUsername() {
        return tenderUsername;
    }

    public void setTenderUsername(String tenderUsername) {
        this.tenderUsername = tenderUsername;
    }

    public String getTenderUserId() {
        return tenderUserId;
    }

    public void setTenderUserId(String tenderUserId) {
        this.tenderUserId = tenderUserId;
    }

    public String getReferrerUserId() {
        return referrerUserId;
    }

    public void setReferrerUserId(String referrerUserId) {
        this.referrerUserId = referrerUserId;
    }

    public String getTenderReferrerUserId() {
        return tenderReferrerUserId;
    }

    public void setTenderReferrerUserId(String tenderReferrerUserId) {
        this.tenderReferrerUserId = tenderReferrerUserId;
    }

    public String getTenderUserAttribute() {
        return tenderUserAttribute;
    }

    public void setTenderUserAttribute(String tenderUserAttribute) {
        this.tenderUserAttribute = tenderUserAttribute;
    }

    public String getInviteUserAttribute() {
        return inviteUserAttribute;
    }

    public void setInviteUserAttribute(String inviteUserAttribute) {
        this.inviteUserAttribute = inviteUserAttribute;
    }

    public String getTenderReferrerUsername() {
        return tenderReferrerUsername;
    }

    public void setTenderReferrerUsername(String tenderReferrerUsername) {
        this.tenderReferrerUsername = tenderReferrerUsername;
    }

    public String getDepartmentLevel1Name() {
        return departmentLevel1Name;
    }

    public void setDepartmentLevel1Name(String departmentLevel1Name) {
        this.departmentLevel1Name = departmentLevel1Name;
    }

    public String getDepartmentLevel2Name() {
        return departmentLevel2Name;
    }

    public void setDepartmentLevel2Name(String departmentLevel2Name) {
        this.departmentLevel2Name = departmentLevel2Name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTenderUserAttributeNow() {
        return tenderUserAttributeNow;
    }

    public void setTenderUserAttributeNow(String tenderUserAttributeNow) {
        this.tenderUserAttributeNow = tenderUserAttributeNow;
    }

    public String getTenderRegionName() {
        return tenderRegionName;
    }

    public void setTenderRegionName(String tenderRegionName) {
        this.tenderRegionName = tenderRegionName;
    }

    public String getTenderBranchName() {
        return tenderBranchName;
    }

    public void setTenderBranchName(String tenderBranchName) {
        this.tenderBranchName = tenderBranchName;
    }

    public String getTenderDepartmentName() {
        return tenderDepartmentName;
    }

    public void setTenderDepartmentName(String tenderDepartmentName) {
        this.tenderDepartmentName = tenderDepartmentName;
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

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getAccedeOrderIdSrch() {
        return accedeOrderIdSrch;
    }

    public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
        this.accedeOrderIdSrch = accedeOrderIdSrch;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getReAuthPassTime() {
        return reAuthPassTime;
    }

    public void setReAuthPassTime(String reAuthPassTime) {
        this.reAuthPassTime = reAuthPassTime;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId;
    }

    public String getContractCreateTime() {
        return contractCreateTime;
    }

    public void setContractCreateTime(String contractCreateTime) {
        this.contractCreateTime = contractCreateTime;
    }

    public String getContractSignTime() {
        return contractSignTime;
    }

    public void setContractSignTime(String contractSignTime) {
        this.contractSignTime = contractSignTime;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getViewpdfUrl() {
        return viewpdfUrl;
    }

    public void setViewpdfUrl(String viewpdfUrl) {
        this.viewpdfUrl = viewpdfUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }
}
