/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author fq
 * @version ChannelReconciliationRequest, v0.1 2018/9/21 9:28
 */
public class ChannelReconciliationRequest extends BasePage {

    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "订单号")
    private String orderCode;
    @ApiModelProperty(value = "标的编号")
    private String borrowNid;
    @ApiModelProperty(value = "出借开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date investStartTime;
    @ApiModelProperty(value = "出借结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date investEndTime;
    @ApiModelProperty(value = "是否首投 0:是 1:否")
    private Integer firstFlag;
    @ApiModelProperty(value = "注册开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registStartTime;
    @ApiModelProperty(value = "注册结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registEndTime;
    @ApiModelProperty(value = "渠道")
    private String[] utmPlat;

    private String sourceId;

    private int limitStart;

    private int limitEnd;

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public Date getInvestStartTime() {
        return investStartTime;
    }

    public void setInvestStartTime(Date investStartTime) {
        this.investStartTime = investStartTime;
    }

    public Date getInvestEndTime() {
        return investEndTime;
    }

    public void setInvestEndTime(Date investEndTime) {
        this.investEndTime = investEndTime;
    }

    public Integer getFirstFlag() {
        return firstFlag;
    }

    public void setFirstFlag(Integer firstFlag) {
        this.firstFlag = firstFlag;
    }

    public Date getRegistStartTime() {
        return registStartTime;
    }

    public void setRegistStartTime(Date registStartTime) {
        this.registStartTime = registStartTime;
    }

    public Date getRegistEndTime() {
        return registEndTime;
    }

    public void setRegistEndTime(Date registEndTime) {
        this.registEndTime = registEndTime;
    }

    public String[] getUtmPlat() {
        return utmPlat;
    }

    public void setUtmPlat(String[] utmPlat) {
        this.utmPlat = utmPlat;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    // 用户id集合
    private List<Integer> userIdList;

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
    }


}
