/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author wangjun
 * @version BorrowFireInfoResponseBean, v0.1 2018/7/3 18:50
 */
public class BorrowFireInfoResponseBean {
    @ApiModelProperty(value = "借款编号")
    private String borrowNid;

    @ApiModelProperty(value = "借款标题")
    private String name;

    @ApiModelProperty(value = "借贷总金额")
    private BigDecimal account;

    @ApiModelProperty(value = "借款利率")
    private BigDecimal borrowApr;

    @ApiModelProperty(value = "借款期限")
    private Integer borrowPeriod;

    @ApiModelProperty(value = "还款方式")
    private String borrowStyle;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "发标状态")
    private Integer verifyStatus;

    @ApiModelProperty(value = "定时发标时间")
    private String ontime;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getOntime() {
        return ontime;
    }

    public void setOntime(String ontime) {
        this.ontime = ontime;
    }
}
