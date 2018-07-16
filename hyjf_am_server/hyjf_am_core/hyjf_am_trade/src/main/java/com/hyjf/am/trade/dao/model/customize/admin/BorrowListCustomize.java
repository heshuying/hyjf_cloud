/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize.admin;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowListCustomize, v0.1 2018/7/12 10:23
 */
public class BorrowListCustomize implements Serializable {
    /**
     * 借款编码
     */
    private String borrowNid;
    /**
     * 借款用户
     */
    private String username;

    /**
     * 用户ID
     */
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
    private String createTime;
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
    /**
     * 资产编号
     */
    private String borrowAssetNumber;
    /**
     * 产品加息收益率
     */
    private String borrowExtraYield;
    /**
     * 协议期数
     */
    private String contractPeriod;
    /**
     * 最小投资金额万
     */
    private String tenderAccountMinWan;
    /**
     * 倍增金额
     */
    private String increaseMoney;
    /**
     * 标的删除标识
     */
    private String delFlag;
    /**
     * 标的撤销标识
     */
    private String revokeFlag;
    /**
     * 垫付机构名称
     */
    private String repay_org_name;
    /**
     * 计划编号
     */
    private String planNid;
    /**
     * 资金来源
     */
    private String instName;
    /**
     * 标的标签
     */
    private String labelName;

    /**
     * 借款类型
     */
    private String userType;
    /**
     * 用户真实姓名
     */
    private String truename;
    /**
     * 公司名称
     */
    private String businame;
    /**
     * 初审时间
     */
    private String verifyTime;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRepayOrgUserName() {
        return repayOrgUserName;
    }

    public void setRepayOrgUserName(String repayOrgUserName) {
        this.repayOrgUserName = repayOrgUserName;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getBorrowPeriodType() {
        return borrowPeriodType;
    }

    public void setBorrowPeriodType(String borrowPeriodType) {
        this.borrowPeriodType = borrowPeriodType;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowFullTime() {
        return borrowFullTime;
    }

    public void setBorrowFullTime(String borrowFullTime) {
        this.borrowFullTime = borrowFullTime;
    }

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public String getRecoverLastDay() {
        return recoverLastDay;
    }

    public void setRecoverLastDay(String recoverLastDay) {
        this.recoverLastDay = recoverLastDay;
    }

    public String getBorrowAccountYes() {
        return borrowAccountYes;
    }

    public void setBorrowAccountYes(String borrowAccountYes) {
        this.borrowAccountYes = borrowAccountYes;
    }

    public String getBorrowAccountWait() {
        return borrowAccountWait;
    }

    public void setBorrowAccountWait(String borrowAccountWait) {
        this.borrowAccountWait = borrowAccountWait;
    }

    public String getBorrowAccountScale() {
        return borrowAccountScale;
    }

    public void setBorrowAccountScale(String borrowAccountScale) {
        this.borrowAccountScale = borrowAccountScale;
    }

    public String getReverifyUserName() {
        return reverifyUserName;
    }

    public void setReverifyUserName(String reverifyUserName) {
        this.reverifyUserName = reverifyUserName;
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

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getBusiname() {
        return businame;
    }

    public void setBusiname(String businame) {
        this.businame = businame;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }
}
