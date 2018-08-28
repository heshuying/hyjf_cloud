/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageCustomizeVO, v0.1 2018/6/29 11:52
 */
public class BankAccountManageCustomizeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 8232003216593277079L;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "创建时间 起始")
    private String startDate;

    @ApiModelProperty(value = "创建时间 结束")
    private String endDate;

    private Integer limitStart;

    private Integer limitEnd;

    @ApiModelProperty(value = "account_list 主键")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "推荐人")
    private String referrerName;
    /**
     * 推荐组
     */
    @ApiModelProperty(value = "推荐组")
    private String referrerGroup;

    @ApiModelProperty(value = "订单号")
    private String nid;

    @ApiModelProperty(value = "操作类型")
    private String type;

    @ApiModelProperty(value = "交易类型")
    private String tradeType;

    @ApiModelProperty(value = "操作金额")
    private BigDecimal amount;

    /**
     * 可用余额
     */
    @ApiModelProperty(value = "可用余额")
    private BigDecimal balance;
    /**
     * 冻结金额
     */
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal frost;

    /**
     * 汇添金可用余额
     */
    @ApiModelProperty(value = "可用余额")
    private BigDecimal planBalance;

    /**
     * 汇添金冻结金额
     */
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal planFrost;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "交易时间")
    private String createTime;

    @ApiModelProperty(value = "账户类型是否银行存管")
    private String isBank;

    @ApiModelProperty(value = "电子账号")
    private String accountId;

    @ApiModelProperty(value = "交易流水号")
    private String seqNo;

    @ApiModelProperty(value = "银行总资产")
    private String bankTotal;

    @ApiModelProperty(value = "银行存管余额")
    private BigDecimal bankBalance;

    @ApiModelProperty(value = "银行存管冻结金额")
    private BigDecimal bankFrost;

    @ApiModelProperty(value = "对账状态")
    private String checkStatus;

    @ApiModelProperty(value = "交易状态")
    private String tradeStatus;

    @ApiModelProperty(value = "对账开始时间")
    private String startTime;

    @ApiModelProperty(value = "对账结束时间")
    private String endTime;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public String getReferrerGroup() {
        return referrerGroup;
    }

    public void setReferrerGroup(String referrerGroup) {
        this.referrerGroup = referrerGroup;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFrost() {
        return frost;
    }

    public void setFrost(BigDecimal frost) {
        this.frost = frost;
    }

    public BigDecimal getPlanBalance() {
        return planBalance;
    }

    public void setPlanBalance(BigDecimal planBalance) {
        this.planBalance = planBalance;
    }

    public BigDecimal getPlanFrost() {
        return planFrost;
    }

    public void setPlanFrost(BigDecimal planFrost) {
        this.planFrost = planFrost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
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

    public String getBankTotal() {
        return bankTotal;
    }

    public void setBankTotal(String bankTotal) {
        this.bankTotal = bankTotal;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }

    public BigDecimal getBankFrost() {
        return bankFrost;
    }

    public void setBankFrost(BigDecimal bankFrost) {
        this.bankFrost = bankFrost;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
