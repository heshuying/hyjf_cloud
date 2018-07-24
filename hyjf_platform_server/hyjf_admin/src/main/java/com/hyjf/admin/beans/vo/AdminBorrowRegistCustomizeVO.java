/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version AdminBorrowRegistCustomizeVO, v0.1 2018/7/23 9:07
 */
public class AdminBorrowRegistCustomizeVO extends BaseVO implements Serializable {
    @ApiModelProperty(value = "借款编码")
    private String borrowNid;

    @ApiModelProperty(value = "借款标题")
    private String borrowName;

    @ApiModelProperty(value = "借款用户")
    private String username;

    @ApiModelProperty(value = "项目类型")
    private String projectType;

    @ApiModelProperty(value = "项目类型名称")
    private String projectTypeName;

    @ApiModelProperty(value = "借款金额")
    private String account;

    @ApiModelProperty(value = "借款期限")
    private String borrowPeriod;

    @ApiModelProperty(value = "年利率")
    private String borrowApr;

    @ApiModelProperty(value = "还款方式")
    private String borrowStyle;

    @ApiModelProperty(value = "还款方式名称")
    private String borrowStyleName;

    @ApiModelProperty(value = "备案状态")
    private String registStatus;

    @ApiModelProperty(value = "备案状态名称")
    private String registStatusName;

    @ApiModelProperty(value = "添加时间")
    private String createTime;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getRegistStatus() {
        return registStatus;
    }

    public void setRegistStatus(String registStatus) {
        this.registStatus = registStatus;
    }

    public String getRegistStatusName() {
        return registStatusName;
    }

    public void setRegistStatusName(String registStatusName) {
        this.registStatusName = registStatusName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
