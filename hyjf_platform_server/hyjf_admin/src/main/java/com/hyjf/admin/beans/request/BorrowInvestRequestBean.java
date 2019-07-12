/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowInvestRequestBean, v0.1 2018/7/10 17:05
 */
public class BorrowInvestRequestBean extends BaseRequest implements Serializable {
    @ApiModelProperty(value = "项目编号(所有接口需要传borrowNid时都在这个字段赋值)")
    private String borrowNidSrch;

    @ApiModelProperty(value = "借款期限")
    private String borrowPeriod;

    @ApiModelProperty(value = "用户名")
    private String usernameSrch;

    @ApiModelProperty(value = "推荐人")
    private String referrerNameSrch;

    @ApiModelProperty(value = "还款方式")
    private String borrowStyleSrch;

    @ApiModelProperty(value = "操作平台")
    private String clientSrch;

    @ApiModelProperty(value = "出借时间(开始)")
    private String timeStartSrch;

    @ApiModelProperty(value = "出借时间(结束)")
    private String timeEndSrch;

    @ApiModelProperty(value = "出借类别")
    private String investType;

    @ApiModelProperty(value = "计划编号")
    private String planNidSrch;

    @ApiModelProperty(value = "汇计划加入订单号")
    private String accedeOrderIdSrch;

    @ApiModelProperty(value = "是否复投投标  1；是   0：否")
    private String tenderType;

    @ApiModelProperty(value = "复审通过时间(开始)")
    private String reAuthPassStartTime;

    @ApiModelProperty(value = "复审通过时间(结束)")
    private String reAuthPassEndTime;

    @ApiModelProperty(value = "资产来源")
    private String instCodeSrch;

    @ApiModelProperty(value = "产品类型")
    private String productType;

    @ApiModelProperty(value = "是否从其他页面跳转到出借明细（1：是）(汇计划三期)")
    private String isOptFlag;

    @ApiModelProperty(value = "是否具有组织架构查看权限")
    private String isOrganizationView;

    @ApiModelProperty(value = "债权结束状态 S-成功;F-失败;A-初始:W-未开始")
    private String stateSrch;

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    public String getReferrerNameSrch() {
        return referrerNameSrch;
    }

    public void setReferrerNameSrch(String referrerNameSrch) {
        this.referrerNameSrch = referrerNameSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public String getClientSrch() {
        return clientSrch;
    }

    public void setClientSrch(String clientSrch) {
        this.clientSrch = clientSrch;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    public String getAccedeOrderIdSrch() {
        return accedeOrderIdSrch;
    }

    public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
        this.accedeOrderIdSrch = accedeOrderIdSrch;
    }

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    public String getReAuthPassStartTime() {
        return reAuthPassStartTime;
    }

    public void setReAuthPassStartTime(String reAuthPassStartTime) {
        this.reAuthPassStartTime = reAuthPassStartTime;
    }

    public String getReAuthPassEndTime() {
        return reAuthPassEndTime;
    }

    public void setReAuthPassEndTime(String reAuthPassEndTime) {
        this.reAuthPassEndTime = reAuthPassEndTime;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getIsOptFlag() {
        return isOptFlag;
    }

    public void setIsOptFlag(String isOptFlag) {
        this.isOptFlag = isOptFlag;
    }

    public String getIsOrganizationView() {
        return isOrganizationView;
    }

    public void setIsOrganizationView(String isOrganizationView) {
        this.isOrganizationView = isOrganizationView;
    }

    public String getStateSrch() {
        return stateSrch;
    }

    public void setStateSrch(String stateSrch) {
        this.stateSrch = stateSrch;
    }
}
