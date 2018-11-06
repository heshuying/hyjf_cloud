package com.hyjf.am.vo.trade.account;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountRechargeVO extends BaseVO implements Serializable {
    /** 订单号 */
    public String logOrderId;

    /** 操作用户userId */
    public String logUserId;

    /** 交易金额 */
    public String txAmount;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 状态
     */
    private String statusName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户属性（当前）
     */
    private String userAttribute;

    /**
     * 用户所属一级分部（当前）
     */
    private String userRegionName;

    /**
     * 用户所属二级分部（当前）
     */
    private String userBranchName;

    /**
     * 用户所属团队（当前）
     */
    private String userDepartmentName;

    /**
     * 推荐人用户名（当前）
     */
    private String referrerName;

    /**
     * 用户角色
     */
    private String roleId;

    /**
     * 推荐人姓名（当前）
     */
    private String referrerTrueName;

    /**
     * 推荐人所属一级分部（当前）
     */
    private String referrerRegionName;

    /**
     * 推荐人所属二级分部（当前）
     */
    private String referrerBranchName;

    /**
     * 推荐人所属团队（当前）
     */
    private String referrerDepartmentName;

    private Integer id;

    private String nid;

    private Integer userId;

    private String username;

    private String accountId;

    private Integer txDate;

    private Integer txTime;

    private Integer seqNo;

    private String bankSeqNo;

    private Integer isBank;

    private Integer status;

    private BigDecimal money;

    private BigDecimal fee;

    private BigDecimal balance;

    private String payment;

    private String gateType;

    private Integer type;

    private String remark;

    private String operator;

    private String addIp;

    private Integer client;

    private String cardid;

    private String message;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getLogOrderId() {
        return logOrderId;
    }

    public void setLogOrderId(String logOrderId) {
        this.logOrderId = logOrderId;
    }

    public String getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(String logUserId) {
        this.logUserId = logUserId;
    }

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(String userAttribute) {
        this.userAttribute = userAttribute;
    }

    public String getUserRegionName() {
        return userRegionName;
    }

    public void setUserRegionName(String userRegionName) {
        this.userRegionName = userRegionName;
    }

    public String getUserBranchName() {
        return userBranchName;
    }

    public void setUserBranchName(String userBranchName) {
        this.userBranchName = userBranchName;
    }

    public String getUserDepartmentName() {
        return userDepartmentName;
    }

    public void setUserDepartmentName(String userDepartmentName) {
        this.userDepartmentName = userDepartmentName;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getReferrerTrueName() {
        return referrerTrueName;
    }

    public void setReferrerTrueName(String referrerTrueName) {
        this.referrerTrueName = referrerTrueName;
    }

    public String getReferrerRegionName() {
        return referrerRegionName;
    }

    public void setReferrerRegionName(String referrerRegionName) {
        this.referrerRegionName = referrerRegionName;
    }

    public String getReferrerBranchName() {
        return referrerBranchName;
    }

    public void setReferrerBranchName(String referrerBranchName) {
        this.referrerBranchName = referrerBranchName;
    }

    public String getReferrerDepartmentName() {
        return referrerDepartmentName;
    }

    public void setReferrerDepartmentName(String referrerDepartmentName) {
        this.referrerDepartmentName = referrerDepartmentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getBankSeqNo() {
        return bankSeqNo;
    }

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo;
    }

    public Integer getIsBank() {
        return isBank;
    }

    public void setIsBank(Integer isBank) {
        this.isBank = isBank;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getGateType() {
        return gateType;
    }

    public void setGateType(String gateType) {
        this.gateType = gateType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}