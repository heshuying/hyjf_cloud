/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import java.io.Serializable;

import com.hyjf.admin.beans.BaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author jun
 * @version WithdrawBeanRequest, v0.1 2018/7/12 9:53
 */
public class WithdrawBeanAPIRequest extends BaseRequest implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查询条件-用户名")
    private String usernameSrch;

    @ApiModelProperty(value = "查询条件-订单号")
    private String ordidSrch;

    @ApiModelProperty(value = "查询条件-平台")
    private String clientSrch;

    @ApiModelProperty(value = "查询条件-状态")
    private String statusSrch;

    @ApiModelProperty(value = "查询条件-用户属性")
    private String userProperty;

    @ApiModelProperty(value="查询条件-提现异常状态")
    private String statusExceptionSrch;

    @ApiModelProperty(value="查询条件-添加时间(开始)")
    private String addtimeStartSrch;

    @ApiModelProperty(value="查询条件-添加时间(结束)")
    private String addtimeEndSrch;

    @ApiModelProperty(value="查询条件-提现来源")
    private String bankFlagSrch;

    @ApiModelProperty(value="查询条件-银行电子账号")
    private String accountIdSrch;

    @ApiModelProperty(value="查询条件-银行流水号")
    private String bankSeqNoSrch;

    @ApiModelProperty(value="查询条件-提现类型 0主动提现  1代提现")
    private Integer withdrawType;

    @ApiModelProperty(value="查询条件-用户ID")
    private Integer userId;

    @ApiModelProperty(value="查询条件-投资nid")
    private String nid;

    @ApiModelProperty(value="查询条件-状态")
    private Integer status;

    /** 翻页开始 */
    protected int limitStart = -1;
    /** 翻页结束 */
    protected int limitEnd = -1;

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
}
