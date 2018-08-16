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
 * @version: TenderCancelExceptionRequest, v0.1 2018/7/11 9:50
 */
@ApiModel(value = "投资撤销请求参数")
public class TenderCancelExceptionRequest extends BasePage implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 657155690866140307L;

    @ApiModelProperty(value = "冻结订单号")
    private String orderId;

    @ApiModelProperty(value = "标的号")
    private String borrowNid = "";

    @ApiModelProperty(value = "用户名")
    private String userName = "";

    @ApiModelProperty(value = "时间开始")
    private String timeStartSrch;

    @ApiModelProperty(value = "时间结束")
    private String timeEndSrch;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
