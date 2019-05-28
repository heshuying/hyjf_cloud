package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowApicronLog implements Serializable {
    private Integer id;

    /**
     * 标的编号_用户ID_期数
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 还款人用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 标的编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 还款状态:0初始4校验失败5还款失败6还款成功8还款部分成功 其余状态关联获取
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 当前期数
     *
     * @mbggenerated
     */
    private Integer periodNow;

    /**
     * 是否是担保机构还款(0:否,1:是)
     *
     * @mbggenerated
     */
    private Integer isRepayOrgFlag;

    /**
     * 还款人用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 借款金额
     *
     * @mbggenerated
     */
    private BigDecimal borrowAccount;

    /**
     * 总期数
     *
     * @mbggenerated
     */
    private Integer borrowPeriod;

    /**
     * 还款批次号
     *
     * @mbggenerated
     */
    private String batchNo;

    /**
     * 应还款金额
     *
     * @mbggenerated
     */
    private BigDecimal batchAmount;

    /**
     * 批次还款总记录数
     *
     * @mbggenerated
     */
    private Integer batchCounts;

    /**
     * 还款总服务费
     *
     * @mbggenerated
     */
    private BigDecimal batchServiceFee;

    /**
     * 应还款金额
     *
     * @mbggenerated
     */
    private BigDecimal txAmount;

    /**
     * 交易笔数
     *
     * @mbggenerated
     */
    private Integer txCounts;

    /**
     * 成功笔数
     *
     * @mbggenerated
     */
    private Integer sucCounts;

    /**
     * 成功交易金额
     *
     * @mbggenerated
     */
    private BigDecimal sucAmount;

    /**
     * 失败笔数
     *
     * @mbggenerated
     */
    private Integer failCounts;

    /**
     * 失败交易金额
     *
     * @mbggenerated
     */
    private BigDecimal failAmount;

    /**
     * 应收服务费
     *
     * @mbggenerated
     */
    private BigDecimal serviceFee;

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
     * 银行交易流水号(交易日期+交易时间+交易流水号)
     *
     * @mbggenerated
     */
    private String bankSeqNo;

    /**
     * 计划编号
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 放款订单号
     *
     * @mbggenerated
     */
    private String ordid;

    /**
     * 批次任务是否属于一次性还款 0：否 1：是
     *
     * @mbggenerated
     */
    private Integer isAllrepay;

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

    /**
     * 错误信息
     *
     * @mbggenerated
     */
    private String data;

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

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPeriodNow() {
        return periodNow;
    }

    public void setPeriodNow(Integer periodNow) {
        this.periodNow = periodNow;
    }

    public Integer getIsRepayOrgFlag() {
        return isRepayOrgFlag;
    }

    public void setIsRepayOrgFlag(Integer isRepayOrgFlag) {
        this.isRepayOrgFlag = isRepayOrgFlag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public BigDecimal getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(BigDecimal borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public BigDecimal getBatchAmount() {
        return batchAmount;
    }

    public void setBatchAmount(BigDecimal batchAmount) {
        this.batchAmount = batchAmount;
    }

    public Integer getBatchCounts() {
        return batchCounts;
    }

    public void setBatchCounts(Integer batchCounts) {
        this.batchCounts = batchCounts;
    }

    public BigDecimal getBatchServiceFee() {
        return batchServiceFee;
    }

    public void setBatchServiceFee(BigDecimal batchServiceFee) {
        this.batchServiceFee = batchServiceFee;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public Integer getTxCounts() {
        return txCounts;
    }

    public void setTxCounts(Integer txCounts) {
        this.txCounts = txCounts;
    }

    public Integer getSucCounts() {
        return sucCounts;
    }

    public void setSucCounts(Integer sucCounts) {
        this.sucCounts = sucCounts;
    }

    public BigDecimal getSucAmount() {
        return sucAmount;
    }

    public void setSucAmount(BigDecimal sucAmount) {
        this.sucAmount = sucAmount;
    }

    public Integer getFailCounts() {
        return failCounts;
    }

    public void setFailCounts(Integer failCounts) {
        this.failCounts = failCounts;
    }

    public BigDecimal getFailAmount() {
        return failAmount;
    }

    public void setFailAmount(BigDecimal failAmount) {
        this.failAmount = failAmount;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
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

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid == null ? null : ordid.trim();
    }

    public Integer getIsAllrepay() {
        return isAllrepay;
    }

    public void setIsAllrepay(Integer isAllrepay) {
        this.isAllrepay = isAllrepay;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}