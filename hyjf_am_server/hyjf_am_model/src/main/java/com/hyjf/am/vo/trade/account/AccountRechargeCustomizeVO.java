package com.hyjf.am.vo.trade.account;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 充值管理自定义返回值
 * @Author : huanghui
 */
public class AccountRechargeCustomizeVO extends BaseVO implements Serializable {

    /**
     *
     */
    private Integer id;

    /**
     * 订单号
     */
    private String nid;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户角色
     */
    private String roleId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户名
     */
    private String username;

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
     * 推荐人姓名（当前）
     */
    private String referrerUserId;

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

    /**
     * 充值银行
     */
    private String bankName;

    /**
     * 所属银行id
     */
    private String payment;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态 WHEN 0 THEN '充值中' WHEN 1 THEN '成功' ELSE '失败'
     */
    private String statusName;

    /**
     * 充值金额
     */
    private BigDecimal money;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 充值手续费收取方式,U向用户收取,M向商户收取
     */
    private String feeFrom;

    /**
     * 实际到账余额
     */
    private BigDecimal balance;

    /**
     * 网关类型：QP快捷支付
     */
    private String gateType;

    /**
     * 类型.1网上充值.0线下充值
     */
    private String type;

    /**
     * 备注
     */
    private String remark;

    /**
     *
     */
    private String createTime;

    /**
     *
     */
    private String operator;

    /**
     * 审核时间
     */
    private String verifyTime;

    /**
     * 审核备注
     */
    private String verifyRemark;

    /**
     * 0pc 1app
     */
    private String client;

    /**
     * 银行卡号
     */
    private String cardid;

    private String message;// 失败原因

    /**
     * 电子账号
     */
    private String accountId;

    /**
     * 流水号
     */
    private String seqNo;

    /**
     * 资金托管平台
     */
    private String isBank;

    private String txDate;

    private String txTime;

    private String outtime;

    private String bankSeqNo;

    private String userProperty;

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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getReferrerUserId() {
        return referrerUserId;
    }

    public void setReferrerUserId(String referrerUserId) {
        this.referrerUserId = referrerUserId;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public String getFeeFrom() {
        return feeFrom;
    }

    public void setFeeFrom(String feeFrom) {
        this.feeFrom = feeFrom;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getGateType() {
        return gateType;
    }

    public void setGateType(String gateType) {
        this.gateType = gateType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
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

    public String getTxDate() {
        return txDate;
    }

    public void setTxDate(String txDate) {
        this.txDate = txDate;
    }

    public String getTxTime() {
        return txTime;
    }

    public void setTxTime(String txTime) {
        this.txTime = txTime;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public String getBankSeqNo() {
        return bankSeqNo;
    }

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo;
    }

    public String getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }
}
