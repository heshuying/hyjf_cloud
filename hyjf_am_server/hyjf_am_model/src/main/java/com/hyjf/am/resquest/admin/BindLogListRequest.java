/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: BindLogListRequest, v0.1 2018/7/5 15:37
 */
@ApiModel(value = "绑定日志请求参数")
public class BindLogListRequest extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "转出账户(检索用)")
    private String turnOutUsername;
    @ApiModelProperty(value = "转出账户手机(检索用)")
    private String turnOutMobile;
    @ApiModelProperty(value = "转入账户(检索用)")
    private String shiftToUsername;
    @ApiModelProperty(value = "转入账户手机(检索用)")
    private String shiftToMobile;
    @ApiModelProperty(value = "关联状态(检索用)")
    private String statusSearch;
    @ApiModelProperty(value = "开始时间(检索用)")
    private String startDate;
    @ApiModelProperty(value = "结束时间(检索用)")
    private String endDate;
    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    public String getTurnOutUsername() {
        return turnOutUsername;
    }

    public void setTurnOutUsername(String turnOutUsername) {
        this.turnOutUsername = turnOutUsername;
    }

    public String getTurnOutMobile() {
        return turnOutMobile;
    }

    public void setTurnOutMobile(String turnOutMobile) {
        this.turnOutMobile = turnOutMobile;
    }

    public String getShiftToUsername() {
        return shiftToUsername;
    }

    public void setShiftToUsername(String shiftToUsername) {
        this.shiftToUsername = shiftToUsername;
    }

    public String getShiftToMobile() {
        return shiftToMobile;
    }

    public void setShiftToMobile(String shiftToMobile) {
        this.shiftToMobile = shiftToMobile;
    }

    public String getStatusSearch() {
        return statusSearch;
    }

    public void setStatusSearch(String statusSearch) {
        this.statusSearch = statusSearch;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
}
