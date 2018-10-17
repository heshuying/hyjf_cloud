/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowRegistListRequest, v0.1 2018/6/29 14:19
 */
@ApiModel(value = "标的备案异常列表请求参数")
public class BorrowRegistListRequest extends BasePage implements Serializable {

    @ApiModelProperty(value = "借款编号(检索用)")
    private String borrowNidSrch;

    @ApiModelProperty(value = "借款编号(异常处理用)")
    private String borrowNid;

    @ApiModelProperty(value = "借款标题(检索用)")
    private String borrowNameSrch;

    @ApiModelProperty(value = "借款编号(检索用)")
    private String borrowPeriodSrch;

    @ApiModelProperty(value = "借款人(检索用)")
    private String usernameSrch;

    @ApiModelProperty(value = "发布时间开始(检索用)")
    private String timeStartSrch;

    @ApiModelProperty(value = "发布时间结束(检索用)")
    private String timeEndSrch;

    @ApiModelProperty(value = "标的备案状态")
    private String registStatusSrch;

    @ApiModelProperty(value = "项目类型(检索用)")
    private String projectTypeSrch;

    @ApiModelProperty(value = "还款方式(检索用)")
    private String borrowStyleSrch;

    @ApiModelProperty(value = "借款人用户名")
    private String userName;

    private int limitStart = -1;

    private int limitEnd = -1;


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

    public String getRegistStatusSrch() {
        return registStatusSrch;
    }

    public void setRegistStatusSrch(String registStatusSrch) {
        this.registStatusSrch = registStatusSrch;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    public String getBorrowNameSrch() {
        return borrowNameSrch;
    }

    public void setBorrowNameSrch(String borrowNameSrch) {
        this.borrowNameSrch = borrowNameSrch;
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

    public String getBorrowPeriodSrch() {
        return borrowPeriodSrch;
    }

    public void setBorrowPeriodSrch(String borrowPeriodSrch) {
        this.borrowPeriodSrch = borrowPeriodSrch;
    }

    public String getProjectTypeSrch() {
        return projectTypeSrch;
    }

    public void setProjectTypeSrch(String projectTypeSrch) {
        this.projectTypeSrch = projectTypeSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
