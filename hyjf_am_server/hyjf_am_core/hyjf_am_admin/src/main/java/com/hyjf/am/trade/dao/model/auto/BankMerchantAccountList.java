package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankMerchantAccountList implements Serializable {
    private Integer id;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String orderId;

    private String borrowNid;

    /**
     * 分公司
     *
     * @mbggenerated
     */
    private String regionName;

    /**
     * 分部
     *
     * @mbggenerated
     */
    private String branchName;

    /**
     * 团队
     *
     * @mbggenerated
     */
    private String departmentName;

    /**
     * 对方用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 对方电子账号
     *
     * @mbggenerated
     */
    private String accountId;

    /**
     * 操作金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 账户类型
     *
     * @mbggenerated
     */
    private String bankAccountType;

    /**
     * 商户子账户电子账号
     *
     * @mbggenerated
     */
    private String bankAccountCode;

    /**
     * 银行账户可用金额
     *
     * @mbggenerated
     */
    private BigDecimal bankAccountBalance;

    /**
     * 银行账户冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal bankAccountFrost;

    /**
     * 交易类型
     *
     * @mbggenerated
     */
    private Integer transType;

    /**
     * 收支类型1收入2支出
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * 交易状态0: 失败 1：成功 2:处理中
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 流水号
     *
     * @mbggenerated
     */
    private String seqNo;

    /**
     * 交易日期
     *
     * @mbggenerated
     */
    private Integer txDate;

    /**
     * 交易时间
     *
     * @mbggenerated
     */
    private Integer txTime;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 创建用户id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建用户名
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private String updateUserName;

    /**
     * 备注字段
     *
     * @mbggenerated
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType == null ? null : bankAccountType.trim();
    }

    public String getBankAccountCode() {
        return bankAccountCode;
    }

    public void setBankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode == null ? null : bankAccountCode.trim();
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

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo == null ? null : seqNo.trim();
    }

    public Integer getTxDate() {
        return txDate;
    }

    public void setTxDate(Integer txDate) {
        this.txDate = txDate;
    }

    public Integer getTxTime() {
        return txTime;
    }

    public void setTxTime(Integer txTime) {
        this.txTime = txTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}