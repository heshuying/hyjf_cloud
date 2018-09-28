/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author nxl
 * @version AccountDetailVO, v0.1 2018/6/30 10:13
 */
public class AccountDetailRequestBean extends BasePage {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "推荐人")
    private String referrerName;
    @ApiModelProperty(value = "订单号")
    private String nid;
    @ApiModelProperty(value = "电子账号")
    private String accountId;
    @ApiModelProperty(value = "流水号")
    private String seqNo;
    @ApiModelProperty(value = "资金托管平台 汇付天下：0  江西银行：1")
    private String isBank;
    @ApiModelProperty(value = "对账状态")
    private String checkStatus;
    @ApiModelProperty(value = "交易状态")
    private String tradeStatus;
    @ApiModelProperty(value = "收支类型")
    private String typeSearch;
    @ApiModelProperty(value = "交易类型")
    private String tradeTypeSearch;
    @ApiModelProperty(value = "综合信息")
    private String remarkSrch;
    @ApiModelProperty(value = "创建时间 起始")
    private String startDate; // 创建时间 起始
    @ApiModelProperty(value = "创建时间 结束")
    private String endDate; // 创建时间 结束

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

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTypeSearch() {
        return typeSearch;
    }

    public void setTypeSearch(String typeSearch) {
        this.typeSearch = typeSearch;
    }

    public String getTradeTypeSearch() {
        return tradeTypeSearch;
    }

    public void setTradeTypeSearch(String tradeTypeSearch) {
        this.tradeTypeSearch = tradeTypeSearch;
    }

    public String getRemarkSrch() {
        return remarkSrch;
    }

    public void setRemarkSrch(String remarkSrch) {
        this.remarkSrch = remarkSrch;
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
}
