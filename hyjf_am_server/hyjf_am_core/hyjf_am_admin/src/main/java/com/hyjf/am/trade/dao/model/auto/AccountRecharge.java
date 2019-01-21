package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountRecharge implements Serializable {
    private Integer id;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    private String username;

    /**
     * 电子账号
     *
     * @mbggenerated
     */
    private String accountId;

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
     * 交易流水号
     *
     * @mbggenerated
     */
    private Integer seqNo;

    /**
     * 交易日期+交易时间+交易流水号
     *
     * @mbggenerated
     */
    private String bankSeqNo;

    /**
     * 资金托管平台(0:汇付,1:江西银行)
     *
     * @mbggenerated
     */
    private Integer isBank;

    /**
     * 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败,4:终止.
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 金额
     *
     * @mbggenerated
     */
    private BigDecimal money;

    /**
     * 费用
     *
     * @mbggenerated
     */
    private BigDecimal fee;

    /**
     * 实际到账余额
     *
     * @mbggenerated
     */
    private BigDecimal balance;

    /**
     * 所属银行
     *
     * @mbggenerated
     */
    private String payment;

    /**
     * 网关类型：QP快捷支付;B2C网关充值;B2B企业充值 
     *
     * @mbggenerated
     */
    private String gateType;

    /**
     * 类型.1网上充值.0线下充值
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    private String operator;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    /**
     * 0pc 1WX 2AND 3IOS 4other
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 银行卡号，导出数据关联时用到
     *
     * @mbggenerated
     */
    private String cardid;

    /**
     * 消息记录
     *
     * @mbggenerated
     */
    private String message;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

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
        this.nid = nid == null ? null : nid.trim();
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
        this.username = username == null ? null : username.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
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
        this.bankSeqNo = bankSeqNo == null ? null : bankSeqNo.trim();
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
        this.payment = payment == null ? null : payment.trim();
    }

    public String getGateType() {
        return gateType;
    }

    public void setGateType(String gateType) {
        this.gateType = gateType == null ? null : gateType.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
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
        this.cardid = cardid == null ? null : cardid.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
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