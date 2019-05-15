/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author jun
 * @version WithdrawBeanRequest, v0.1 2018/7/12 9:53
 */
public class WithdrawBeanRequest extends BasePage implements Serializable{

    private static final long serialVersionUID = 1L;

    private String usernameSrch;

    private String ordidSrch;

    private String clientSrch;

    private String statusSrch;

    private String userProperty;

    private String statusExceptionSrch;

    private String addtimeStartSrch;

    private String addtimeEndSrch;

    private String bankFlagSrch;

    private String accountIdSrch;

    private String bankSeqNoSrch;

    private Integer withdrawType;

    private Integer userId;

    private String nid;

    private Integer status;

    /** 翻页开始 */
    protected int limitStart = -1;
    /** 翻页结束 */
    protected int limitEnd = -1;
    //机构编码(温金投）
    private String instCode;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    public String getOrdidSrch() {
        return ordidSrch;
    }

    public void setOrdidSrch(String ordidSrch) {
        this.ordidSrch = ordidSrch;
    }

    public String getClientSrch() {
        return clientSrch;
    }

    public void setClientSrch(String clientSrch) {
        this.clientSrch = clientSrch;
    }

    public String getStatusSrch() {
        return statusSrch;
    }

    public void setStatusSrch(String statusSrch) {
        this.statusSrch = statusSrch;
    }

    public String getAddtimeStartSrch() {
        return addtimeStartSrch;
    }

    public void setAddtimeStartSrch(String addtimeStartSrch) {
        this.addtimeStartSrch = addtimeStartSrch;
    }

    public String getAddtimeEndSrch() {
        return addtimeEndSrch;
    }

    public void setAddtimeEndSrch(String addtimeEndSrch) {
        this.addtimeEndSrch = addtimeEndSrch;
    }

    public String getBankFlagSrch() {
        return bankFlagSrch;
    }

    public void setBankFlagSrch(String bankFlagSrch) {
        this.bankFlagSrch = bankFlagSrch;
    }

    public String getAccountIdSrch() {
        return accountIdSrch;
    }

    public void setAccountIdSrch(String accountIdSrch) {
        this.accountIdSrch = accountIdSrch;
    }

    public String getBankSeqNoSrch() {
        return bankSeqNoSrch;
    }

    public void setBankSeqNoSrch(String bankSeqNoSrch) {
        this.bankSeqNoSrch = bankSeqNoSrch;
    }

    public Integer getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(Integer withdrawType) {
        this.withdrawType = withdrawType;
    }

    public String getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }

    public String getStatusExceptionSrch() {
        return statusExceptionSrch;
    }

    public void setStatusExceptionSrch(String statusExceptionSrch) {
        this.statusExceptionSrch = statusExceptionSrch;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
}
