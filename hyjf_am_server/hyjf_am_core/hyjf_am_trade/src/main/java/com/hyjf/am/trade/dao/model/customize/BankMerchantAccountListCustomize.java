package com.hyjf.am.trade.dao.model.customize;

import java.math.BigDecimal;

public class BankMerchantAccountListCustomize {

    private Integer id;
    //交易日期
    private String createTime;
    //流水号
    private String seqNo;
    //订单号
    private String orderId;
    //分公司
    private String regionName;
    //分部
    private String branchName;
    //团队
    private String departmentName;
    //用户名
    private String userName;
    //电子帐号
    private String accountId;
    //收支类型
    private Integer type;
    private String typeStr;
    //交易类型
    private Integer transType;
    private String transTypeStr;
    //交易金额
    private BigDecimal amount;
    //交易状态
    private Integer status;
    private String statusStr;
    //账户可用金额
    private BigDecimal bankAccountBalance;
    //账户冻结金额
    private BigDecimal bankAccountFrost;

    private String bankAccountCode;
    //备注
    private String remark;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;


    /**
     * 检索条件 时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    private String timeEndSrch;

    /**
     * 构造方法
     */

    public BankMerchantAccountListCustomize() {
        super();
    }



    public String getCreateTime() {
        return createTime;
    }



    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }



    public String getSeqNo() {
        return seqNo;
    }



    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }



    public String getOrderId() {
        return orderId;
    }



    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    public String getRegionName() {
        return regionName;
    }



    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }



    public String getBranchName() {
        return branchName;
    }



    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }



    public String getDepartmentName() {
        return departmentName;
    }



    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }



    public String getUserName() {
        return userName;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getAccountId() {
        return accountId;
    }



    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }



    public Integer getType() {
        return type;
    }



    public void setType(Integer type) {
        this.type = type;
    }



    public String getTypeStr() {
        return typeStr;
    }



    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }



    public Integer getTransType() {
        return transType;
    }



    public void setTransType(Integer transType) {
        this.transType = transType;
    }



    public String getTransTypeStr() {
        return transTypeStr;
    }



    public void setTransTypeStr(String transTypeStr) {
        this.transTypeStr = transTypeStr;
    }



    public BigDecimal getAmount() {
        return amount;
    }



    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }



    public Integer getStatus() {
        return status;
    }



    public void setStatus(Integer status) {
        this.status = status;
    }



    public String getStatusStr() {
        return statusStr;
    }



    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }



    public BigDecimal getBankAccountBalance() {
        return bankAccountBalance;
    }



    public void setBankAccountBalance(BigDecimal bankAccountBalance) {
        this.bankAccountBalance = bankAccountBalance;
    }



    public BigDecimal getBankAccountFrost() {
        return bankAccountFrost;
    }



    public void setBankAccountFrost(BigDecimal bankAccountFrost) {
        this.bankAccountFrost = bankAccountFrost;
    }



    public String getRemark() {
        return remark;
    }



    public void setRemark(String remark) {
        this.remark = remark;
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



    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }



    public String getBankAccountCode() {
        return bankAccountCode;
    }



    public void setBankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }




}

