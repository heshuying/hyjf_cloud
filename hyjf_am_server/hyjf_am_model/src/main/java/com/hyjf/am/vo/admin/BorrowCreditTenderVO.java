package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * 承接信息bean
 * @author zhangyk
 * @date 2018/7/12 19:50
 */
public class BorrowCreditTenderVO extends BaseVO implements Serializable {

    /*用户id*/
    private String userId;

    /*订单号*/
    private String assignNid;

    /*债转编号*/
    private String creditNid;

    /*项目编号*/
    private String bidNid;

    /*承接人*/
    private String userName;

    /*出让人*/
    private String creditUserName;

    /*承接本金*/
    private String assignCapital;

    /*折让率*/
    private String creditDiscount;

    /*认购价格*/
    private String assignPrice;

    /*垫付利息*/
    private String assignInterestAdvance;

    /*债转服务费*/
    private String creditFee;

    /*实付金额*/
    private String assignPay;

    /*合同状态*/
    private String contractStatus;

    /*承接时间*/
    private String addTime;

    /*合同编号*/
    private String contractNumber;

    /*合同下载链接*/
    private String downloadUrl;

    /*预览链接*/
    private String viewUrl;

    /*承接平台*/
    private String client;

    /*原来承接订单号*/
    private String creditTenderNid;



    /**
     * 出让人用户属性及部门信息
     */
    private String recommendAttrCreditSelf;
    private String regionNameCreditSelf;
    private String branchNameCreditSelf;
    private String departmentNameCreditSelf;


    /**
     * 承接人用户属性及部门信息
     */
    private String recommendAttrSelf;
    private String regionNameSelf;
    private String branchNameSelf;
    private String departmentNameSelf;

    /**
     * 承接用户推荐人属性及部门信息
     */
    private String recommendName;
    private String recommendAttr;
    private String regionName;
    private String branchName;
    private String departmentName;

    /**
     * 出让用户推荐人用户名及部门信息
     */
    private String recommendNameCredit;
    private String recommendAttrCredit;
    private String regionNameCredit;
    private String branchNameCredit;
    private String departmentNameCredit;

    /**
     * 承接人承接时推荐人信息
     */
    private String inviteUserName;
    private String inviteUserAttribute;
    private String inviteUseRegionname;
    private String inviteUserBranchname;
    private String inviteUserDepartmentName;
    /**
     * 出让人承接时推荐人信息
     */
    private String inviteUserCreditName;
    private String inviteUserCreditAttribute;
    private String inviteUserCreditRegionName;
    private String inviteUserCreditBranchName;
    private String inviteUserCreditDepartmentName;

    /** 银行交易状态（S-成功;F-失败;A-初始;W:未开始）*/
    private String state;
    /** 债权结束状态描述*/
    private String stateDesc;

    public String getRecommendAttrCreditSelf() {
        return recommendAttrCreditSelf;
    }

    public void setRecommendAttrCreditSelf(String recommendAttrCreditSelf) {
        this.recommendAttrCreditSelf = recommendAttrCreditSelf;
    }

    public String getRegionNameCreditSelf() {
        return regionNameCreditSelf;
    }

    public void setRegionNameCreditSelf(String regionNameCreditSelf) {
        this.regionNameCreditSelf = regionNameCreditSelf;
    }

    public String getBranchNameCreditSelf() {
        return branchNameCreditSelf;
    }

    public void setBranchNameCreditSelf(String branchNameCreditSelf) {
        this.branchNameCreditSelf = branchNameCreditSelf;
    }

    public String getDepartmentNameCreditSelf() {
        return departmentNameCreditSelf;
    }

    public void setDepartmentNameCreditSelf(String departmentNameCreditSelf) {
        this.departmentNameCreditSelf = departmentNameCreditSelf;
    }

    public String getRecommendAttrSelf() {
        return recommendAttrSelf;
    }

    public void setRecommendAttrSelf(String recommendAttrSelf) {
        this.recommendAttrSelf = recommendAttrSelf;
    }

    public String getRegionNameSelf() {
        return regionNameSelf;
    }

    public void setRegionNameSelf(String regionNameSelf) {
        this.regionNameSelf = regionNameSelf;
    }

    public String getBranchNameSelf() {
        return branchNameSelf;
    }

    public void setBranchNameSelf(String branchNameSelf) {
        this.branchNameSelf = branchNameSelf;
    }

    public String getDepartmentNameSelf() {
        return departmentNameSelf;
    }

    public void setDepartmentNameSelf(String departmentNameSelf) {
        this.departmentNameSelf = departmentNameSelf;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getRecommendAttr() {
        return recommendAttr;
    }

    public void setRecommendAttr(String recommendAttr) {
        this.recommendAttr = recommendAttr;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRecommendNameCredit() {
        return recommendNameCredit;
    }

    public void setRecommendNameCredit(String recommendNameCredit) {
        this.recommendNameCredit = recommendNameCredit;
    }

    public String getRecommendAttrCredit() {
        return recommendAttrCredit;
    }

    public void setRecommendAttrCredit(String recommendAttrCredit) {
        this.recommendAttrCredit = recommendAttrCredit;
    }

    public String getRegionNameCredit() {
        return regionNameCredit;
    }

    public void setRegionNameCredit(String regionNameCredit) {
        this.regionNameCredit = regionNameCredit;
    }

    public String getBranchNameCredit() {
        return branchNameCredit;
    }

    public void setBranchNameCredit(String branchNameCredit) {
        this.branchNameCredit = branchNameCredit;
    }

    public String getDepartmentNameCredit() {
        return departmentNameCredit;
    }

    public void setDepartmentNameCredit(String departmentNameCredit) {
        this.departmentNameCredit = departmentNameCredit;
    }

    public String getInviteUserName() {
        return inviteUserName;
    }

    public void setInviteUserName(String inviteUserName) {
        this.inviteUserName = inviteUserName;
    }

    public String getInviteUserAttribute() {
        return inviteUserAttribute;
    }

    public void setInviteUserAttribute(String inviteUserAttribute) {
        this.inviteUserAttribute = inviteUserAttribute;
    }

    public String getInviteUseRegionname() {
        return inviteUseRegionname;
    }

    public void setInviteUseRegionname(String inviteUseRegionname) {
        this.inviteUseRegionname = inviteUseRegionname;
    }

    public String getInviteUserBranchname() {
        return inviteUserBranchname;
    }

    public void setInviteUserBranchname(String inviteUserBranchname) {
        this.inviteUserBranchname = inviteUserBranchname;
    }

    public String getInviteUserDepartmentName() {
        return inviteUserDepartmentName;
    }

    public void setInviteUserDepartmentName(String inviteUserDepartmentName) {
        this.inviteUserDepartmentName = inviteUserDepartmentName;
    }

    public String getInviteUserCreditName() {
        return inviteUserCreditName;
    }

    public void setInviteUserCreditName(String inviteUserCreditName) {
        this.inviteUserCreditName = inviteUserCreditName;
    }

    public String getInviteUserCreditAttribute() {
        return inviteUserCreditAttribute;
    }

    public void setInviteUserCreditAttribute(String inviteUserCreditAttribute) {
        this.inviteUserCreditAttribute = inviteUserCreditAttribute;
    }

    public String getInviteUserCreditRegionName() {
        return inviteUserCreditRegionName;
    }

    public void setInviteUserCreditRegionName(String inviteUserCreditRegionName) {
        this.inviteUserCreditRegionName = inviteUserCreditRegionName;
    }

    public String getInviteUserCreditBranchName() {
        return inviteUserCreditBranchName;
    }

    public void setInviteUserCreditBranchName(String inviteUserCreditBranchName) {
        this.inviteUserCreditBranchName = inviteUserCreditBranchName;
    }

    public String getInviteUserCreditDepartmentName() {
        return inviteUserCreditDepartmentName;
    }

    public void setInviteUserCreditDepartmentName(String inviteUserCreditDepartmentName) {
        this.inviteUserCreditDepartmentName = inviteUserCreditDepartmentName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName;
    }

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getCreditDiscount() {
        return creditDiscount;
    }

    public void setCreditDiscount(String creditDiscount) {
        this.creditDiscount = creditDiscount;
    }

    public String getAssignPrice() {
        return assignPrice;
    }

    public void setAssignPrice(String assignPrice) {
        this.assignPrice = assignPrice;
    }

    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public String getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(String creditFee) {
        this.creditFee = creditFee;
    }

    public String getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(String assignPay) {
        this.assignPay = assignPay;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCreditTenderNid() {
        return creditTenderNid;
    }

    public void setCreditTenderNid(String creditTenderNid) {
        this.creditTenderNid = creditTenderNid;
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
