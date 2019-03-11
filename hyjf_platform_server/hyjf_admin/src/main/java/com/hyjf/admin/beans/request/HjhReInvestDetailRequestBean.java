package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author : huanghui
 */
public class HjhReInvestDetailRequestBean extends BasePage {

    @ApiModelProperty(value = "日期,默认值")
    private String date;

    @ApiModelProperty(value = "智投编号,默认值")
    private String planNid;

    @ApiModelProperty(value = "智投订单号")
    private String accedeOrderIdSrch;

    @ApiModelProperty(value = "用户名")
    private String userNameSrch;

    @ApiModelProperty(value = "项目编号")
    private String borrowNidSrch;

    @ApiModelProperty(value = "借款期限")
    private String lockPeriodSrch;

    @ApiModelProperty(value = "出借方式")
    private String investTypeSrch;

    @ApiModelProperty(value = "还款方式")
    private String borrowStyleSrch;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getAccedeOrderIdSrch() {
        return accedeOrderIdSrch;
    }

    public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
        this.accedeOrderIdSrch = accedeOrderIdSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getLockPeriodSrch() {
        return lockPeriodSrch;
    }

    public void setLockPeriodSrch(String lockPeriodSrch) {
        this.lockPeriodSrch = lockPeriodSrch;
    }

    public String getInvestTypeSrch() {
        return investTypeSrch;
    }

    public void setInvestTypeSrch(String investTypeSrch) {
        this.investTypeSrch = investTypeSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }
}
