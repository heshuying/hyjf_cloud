package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author hesy
 * @version BankCreditEndListRequest, v0.1 2018/7/12 14:23
 */
public class BankCreditEndListRequest extends BasePage {
    @ApiModelProperty(value = "项目编号")
    private String borrowNidSrch;
    @ApiModelProperty(value = "批次号(检索用)")
    private String batchNoSrch;
    @ApiModelProperty(value = "订单号(检索用)")
    private String orgOrderIdSrch;
    @ApiModelProperty(value = "借款人用户名")
    private String borrowUserNameSrch;
    @ApiModelProperty(value = "出借人用户名")
    private String tenderUsernameSrch;
    @ApiModelProperty(value = "债权结束类型 1:还款，2:散标债转，3:计划债转")
    private String creditEndTypeSrch;
    @ApiModelProperty(value = "债权结束状态 S-成功;F-失败;A-初始")
    private String stateSrch;
    @ApiModelProperty(value = "提交开始时间")
    private String commitTimeStartSrch;
    @ApiModelProperty(value = "提交结束时间")
    private String commitTimeEndSrch;

    /**
     * 检索条件 limitStart
     */
    private Integer limitStart;
    /**
     * 检索条件 limitEnd
     */
    private Integer limitEnd;

    public String getBatchNoSrch() {
        return batchNoSrch;
    }

    public void setBatchNoSrch(String batchNoSrch) {
        this.batchNoSrch = batchNoSrch;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getOrgOrderIdSrch() {
        return orgOrderIdSrch;
    }

    public void setOrgOrderIdSrch(String orgOrderIdSrch) {
        this.orgOrderIdSrch = orgOrderIdSrch;
    }

    public String getBorrowUserNameSrch() {
        return borrowUserNameSrch;
    }

    public void setBorrowUserNameSrch(String borrowUserNameSrch) {
        this.borrowUserNameSrch = borrowUserNameSrch;
    }

    public String getTenderUsernameSrch() {
        return tenderUsernameSrch;
    }

    public void setTenderUsernameSrch(String tenderUsernameSrch) {
        this.tenderUsernameSrch = tenderUsernameSrch;
    }

    public String getCreditEndTypeSrch() {
        return creditEndTypeSrch;
    }

    public void setCreditEndTypeSrch(String creditEndTypeSrch) {
        this.creditEndTypeSrch = creditEndTypeSrch;
    }

    public String getStateSrch() {
        return stateSrch;
    }

    public void setStateSrch(String stateSrch) {
        this.stateSrch = stateSrch;
    }

    public String getCommitTimeStartSrch() {
        return commitTimeStartSrch;
    }

    public void setCommitTimeStartSrch(String commitTimeStartSrch) {
        this.commitTimeStartSrch = commitTimeStartSrch;
    }

    public String getCommitTimeEndSrch() {
        return commitTimeEndSrch;
    }

    public void setCommitTimeEndSrch(String commitTimeEndSrch) {
        this.commitTimeEndSrch = commitTimeEndSrch;
    }
}
