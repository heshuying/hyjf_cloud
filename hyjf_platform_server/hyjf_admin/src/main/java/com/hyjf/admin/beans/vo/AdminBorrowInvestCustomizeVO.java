<<<<<<< HEAD
/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowInvestCustomizeVO, v0.1 2018/7/10 15:33
 */
public class AdminBorrowInvestCustomizeVO extends BaseVO implements Serializable {
    /**
     * 借款标题
     */
    @ApiModelProperty(value = "借款标题")
    private String borrowName;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 推荐人
     */
    @ApiModelProperty(value = "推荐人")
    private String referrerName;
    /**
     * 借款编号
     */
    @ApiModelProperty(value = "借款编号")
    private String borrowNid;
    /**
     * 计划编号
     */
    @ApiModelProperty(value = "计划编号")
    private String planNid;
    /**
     * 年化利率
     */
    @ApiModelProperty(value = "年化利率")
    private String borrowApr;
    /**
     * 借款期限
     */
    @ApiModelProperty(value = "借款期限")
    private String borrowPeriod;
    /**
     * 还款方式
     */
    @ApiModelProperty(value = "还款方式")
    private String borrowStyleName;
    /**
     * 投资金额
     */
    @ApiModelProperty(value = "投资金额")
    private String account;
    /**
     * 渠道
     */
    @ApiModelProperty(value = "渠道")
    private String utmSource;
    /**
     * 操作平台
     */
    @ApiModelProperty(value = "操作平台")
    private String operatingDeck;
    /**
     * 投资时间
     */
    @ApiModelProperty(value = "投资时间")
    private String createTime;

    /**
     * 借款人ID
     */
    @ApiModelProperty(value = "借款人ID")
    private String userId;

    /**
     * NID
     */
    @ApiModelProperty(value = "NID")
    private String nid;
    /**
     * 是否邮件发送1可以发，0不可以发
     */
    @ApiModelProperty(value = "是否邮件发送1可以发，0不可以发")
    private String resendMessage;

    /**
     * 项目类型
     */
    @ApiModelProperty(value = "项目类型")
    private String borrowProjectTypeName;

    /**
     * 投资订单号
     */
    @ApiModelProperty(value = "投资订单号")
    private String tenderOrderNum;

    /**
     * 冻结订单号
     */
    @ApiModelProperty(value = "冻结订单号")
    private String freezeOrderNum;

    /**
     * 投资人用户名
     */
    @ApiModelProperty(value = "投资人用户名")
    private String tenderUsername;

    /**
     * 投资人用户ID
     */
    @ApiModelProperty(value = "投资人用户ID")
    private String tenderUserId;

    /**
     * 推荐人ID（当前）
     */
    @ApiModelProperty(value = "推荐人ID（当前）")
    private String referrerUserId;
    /**
     * 推荐人ID（投资时）
     */
    @ApiModelProperty(value = "推荐人ID（投资时）")
    private String tenderReferrerUserId;
    /**
     * 投资人用户属性（投资时）
     */
    @ApiModelProperty(value = "投资人用户属性（投资时）")
    private String tenderUserAttribute;
    /**
     * 推荐人用户属性（投资时）
     */
    @ApiModelProperty(value = "推荐人用户属性（投资时）")
    private String inviteUserAttribute;
    /**
     * 推荐人（投资时）
     */
    @ApiModelProperty(value = "推荐人（投资时）")
    private String tenderReferrerUsername;

    /**
     * 一级分部（投资时）
     */
    @ApiModelProperty(value = "一级分部（投资时）")
    private String departmentLevel1Name;

    /**
     * 二级分部（投资时）
     */
    @ApiModelProperty(value = "二级分部（投资时）")
    private String departmentLevel2Name;

    /**
     * 团队（投资时）
     */
    @ApiModelProperty(value = "团队（投资时）")
    private String teamName;

    /**
     * 投资人用户属性（当前）
     */
    @ApiModelProperty(value = "投资人用户属性（当前）")
    private String tenderUserAttributeNow;

    /**
     * 投资人所属一级分部（当前）
     */
    @ApiModelProperty(value = "投资人所属一级分部（当前）")
    private String tenderRegionName;

    /**
     * 投资人所属二级分部（当前）
     */
    @ApiModelProperty(value = "投资人所属二级分部（当前）")
    private String tenderBranchName;

    /**
     * 投资人所属团队（当前）
     */
    @ApiModelProperty(value = " 投资人所属团队（当前）")
    private String tenderDepartmentName;

    /**
     * 推荐人姓名（当前）
     */
    @ApiModelProperty(value = "推荐人姓名（当前）")
    private String referrerTrueName;

    /**
     * 推荐人所属一级分部（当前）
     */
    @ApiModelProperty(value = "推荐人所属一级分部（当前）")
    private String referrerRegionName;

    /**
     * 推荐人所属二级分部（当前）
     */
    @ApiModelProperty(value = "推荐人所属二级分部（当前）")
    private String referrerBranchName;

    /**
     * 推荐人所属团队（当前）
     */
    @ApiModelProperty(value = "推荐人所属团队（当前）")
    private String referrerDepartmentName;
    /**
     * 投资类别 0手动投标，1或者别的预约投标
     */
    @ApiModelProperty(value = "投资类别 0手动投标，1或者别的预约投标")
    private String investType;

    /**
     * 汇计划加入订单号
     */
    @ApiModelProperty(value = "汇计划加入订单号")
    private String accedeOrderIdSrch;
    /**
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称")
    private String instName;
    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private String productType;
    /**
     * 复审通过时间
     */
    @ApiModelProperty(value = "复审通过时间")
    private String reAuthPassTime;

    /**
     * 合同状态
     */
    @ApiModelProperty(value = "合同状态")
    private String contractStatus;

    /**
     * 合同编号
     */
    @ApiModelProperty(value = "合同编号")
    private String contractNumber;

    /**
     * 合同名称
     */
    @ApiModelProperty(value = "合同名称")
    private String contractName;

    /**
     * 模板编号
     */
    @ApiModelProperty(value = "模板编号")
    private String templetId;

    /**
     * 合同生成时间
     */
    @ApiModelProperty(value = "合同生成时间")
    private String contractCreateTime;

    /**
     * 合同签署时间
     */
    @ApiModelProperty(value = "合同签署时间")
    private String contractSignTime;

    /**
     * 合同下载地址
     */
    @ApiModelProperty(value = "合同下载地址")
    private String downloadUrl;

    /**
     * 合同查看地址
     */
    @ApiModelProperty(value = "合同查看地址")
    private String viewpdfUrl;

    /**
     * 脱敏合同地址
     */
    @ApiModelProperty(value = "脱敏合同地址")
    private String imgUrl;

    /**
     * 是否复投投资  1；是   0：否
     */
    @ApiModelProperty(value = " 是否复投投资  1；是   0：否")
    private String tenderType;

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
}
=======
/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version AdminBorrowInvestCustomizeVO, v0.1 2018/7/23 15:45
 */
public class AdminBorrowInvestCustomizeVO extends BaseVO implements Serializable {
    @ApiModelProperty(value = "借款标题")
    private String borrowName;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "推荐人")
    private String referrerName;

    @ApiModelProperty(value = "借款编号")
    private String borrowNid;

    @ApiModelProperty(value = "计划编号")
    private String planNid;

    @ApiModelProperty(value = "年化利率")
    private String borrowApr;

    @ApiModelProperty(value = "借款期限")
    private String borrowPeriod;

    @ApiModelProperty(value = "还款方式")
    private String borrowStyleName;

    @ApiModelProperty(value = "投资金额")
    private String account;

    @ApiModelProperty(value = "渠道")
    private String utmSource;

    @ApiModelProperty(value = "操作平台")
    private String operatingDeck;

    @ApiModelProperty(value = "投资时间")
    private String createTime;

    @ApiModelProperty(value = "借款人ID")
    private String userId;

    @ApiModelProperty(value = "投资订单号")
    private String nid;

    @ApiModelProperty(value = "是否邮件发送1可以发，0不可以发")
    private String resendMessage;

    @ApiModelProperty(value = "项目类型")
    private String borrowProjectTypeName;

    @ApiModelProperty(value = "投资订单号")
    private String tenderOrderNum;

    @ApiModelProperty(value = "冻结订单号")
    private String freezeOrderNum;

    @ApiModelProperty(value = "投资人用户名")
    private String tenderUsername;

    @ApiModelProperty(value = "投资人用户ID")
    private String tenderUserId;

    @ApiModelProperty(value = "推荐人ID（当前）")
    private String referrerUserId;

    @ApiModelProperty(value = "推荐人ID（投资时）")
    private String tenderReferrerUserId;

    @ApiModelProperty(value = "投资人用户属性（投资时）")
    private String tenderUserAttribute;

    @ApiModelProperty(value = "推荐人用户属性（投资时）")
    private String inviteUserAttribute;

    @ApiModelProperty(value = "推荐人（投资时）")
    private String tenderReferrerUsername;

    @ApiModelProperty(value = "一级分部（投资时）")
    private String departmentLevel1Name;

    @ApiModelProperty(value = "二级分部（投资时）")
    private String departmentLevel2Name;

    @ApiModelProperty(value = "团队（投资时）")
    private String teamName;

    @ApiModelProperty(value = "投资人用户属性（当前）")
    private String tenderUserAttributeNow;

    @ApiModelProperty(value = "投资人所属一级分部（当前）")
    private String tenderRegionName;

    @ApiModelProperty(value = "投资人所属二级分部（当前）")
    private String tenderBranchName;

    @ApiModelProperty(value = "投资人所属团队（当前）")
    private String tenderDepartmentName;

    @ApiModelProperty(value = "推荐人姓名（当前）")
    private String referrerTrueName;

    @ApiModelProperty(value = "推荐人所属一级分部（当前）")
    private String referrerRegionName;

    @ApiModelProperty(value = "推荐人所属二级分部（当前）")
    private String referrerBranchName;

    @ApiModelProperty(value = "推荐人所属团队（当前）")
    private String referrerDepartmentName;

    @ApiModelProperty(value = "投资类别 0手动投标，1或者别的预约投标")
    private String investType;

    @ApiModelProperty(value = "汇计划加入订单号")
    private String accedeOrderIdSrch;

    @ApiModelProperty(value = "机构名称")
    private String instName;

    @ApiModelProperty(value = "产品类型")
    private String productType;

    @ApiModelProperty(value = "复审通过时间")
    private String reAuthPassTime;

    @ApiModelProperty(value = "合同状态")
    private String contractStatus;

    @ApiModelProperty(value = "合同编号")
    private String contractNumber;

    @ApiModelProperty(value = "合同名称")
    private String contractName;

    @ApiModelProperty(value = "模板编号")
    private String templetId;

    @ApiModelProperty(value = "合同生成时间")
    private String contractCreateTime;

    @ApiModelProperty(value = "合同签署时间")
    private String contractSignTime;

    @ApiModelProperty(value = "合同下载地址")
    private String downloadUrl;

    @ApiModelProperty(value = "合同查看地址")
    private String viewpdfUrl;

    @ApiModelProperty(value = "脱敏合同地址")
    private String imgUrl;

    @ApiModelProperty(value = "是否复投投资  1；是   0：否")
    private String tenderType;

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
}
>>>>>>> origin/master
