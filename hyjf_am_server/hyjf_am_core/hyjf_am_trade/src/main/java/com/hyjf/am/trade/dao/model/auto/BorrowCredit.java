package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowCredit implements Serializable {
    /**
     * 债转id
     *
     * @mbggenerated
     */
    private Integer creditId;

    /**
     * 债转编号
     *
     * @mbggenerated
     */
    private Integer creditNid;

    /**
     * 出让人用户ID
     *
     * @mbggenerated
     */
    private Integer creditUserId;

    /**
     * 出让人用户名
     *
     * @mbggenerated
     */
    private String creditUserName;

    /**
     * 原标标的编号
     *
     * @mbggenerated
     */
    private String bidNid;

    /**
     * 标的借款人用户ID
     *
     * @mbggenerated
     */
    private Integer borrowUserId;

    /**
     * 标的借款人用户名
     *
     * @mbggenerated
     */
    private String borrowUserName;

    /**
     * 原标年化利率
     *
     * @mbggenerated
     */
    private BigDecimal bidApr;

    /**
     * 原标标题
     *
     * @mbggenerated
     */
    private String bidName;

    /**
     * 原始投资订单号(对应borrow_tender表的nid字段)
     *
     * @mbggenerated
     */
    private String tenderNid;

    /**
     * 转让状态，0.承接进行中，1.承接停止，2完全承接
     *
     * @mbggenerated
     */
    private Integer creditStatus;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Integer creditOrder;

    /**
     * 债转期数
     *
     * @mbggenerated
     */
    private Integer creditPeriod;

    /**
     * 债转期限
     *
     * @mbggenerated
     */
    private Integer creditTerm;

    /**
     * 债券已经持有天数
     *
     * @mbggenerated
     */
    private Integer creditTermHold;

    /**
     * 债转本金
     *
     * @mbggenerated
     */
    private BigDecimal creditCapital;

    /**
     * 债转总额
     *
     * @mbggenerated
     */
    private BigDecimal creditAccount;

    /**
     * 债转总利息
     *
     * @mbggenerated
     */
    private BigDecimal creditInterest;

    /**
     * 需垫付利息
     *
     * @mbggenerated
     */
    private BigDecimal creditInterestAdvance;

    /**
     * 折价率
     *
     * @mbggenerated
     */
    private BigDecimal creditDiscount;

    /**
     * 总收入，本金+垫付利息
     *
     * @mbggenerated
     */
    private BigDecimal creditIncome;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private BigDecimal creditFee;

    /**
     * 出让价格
     *
     * @mbggenerated
     */
    private BigDecimal creditPrice;

    /**
     * 已认购本金
     *
     * @mbggenerated
     */
    private BigDecimal creditCapitalAssigned;

    /**
     * 已承接利息
     *
     * @mbggenerated
     */
    private BigDecimal creditInterestAssigned;

    /**
     * 已承接垫付利息
     *
     * @mbggenerated
     */
    private BigDecimal creditInterestAdvanceAssigned;

    /**
     * 已还款总额
     *
     * @mbggenerated
     */
    private BigDecimal creditRepayAccount;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal creditRepayCapital;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal creditRepayInterest;

    /**
     * 债转最后还款日
     *
     * @mbggenerated
     */
    private Integer creditRepayEndTime;

    /**
     * 上次还款日
     *
     * @mbggenerated
     */
    private Integer creditRepayLastTime;

    /**
     * 下次还款日
     *
     * @mbggenerated
     */
    private Integer creditRepayNextTime;

    /**
     * 最终实际还款日
     *
     * @mbggenerated
     */
    private Integer creditRepayYesTime;

    /**
     * 结束时间
     *
     * @mbggenerated
     */
    private Integer endTime;

    /**
     * 认购时间
     *
     * @mbggenerated
     */
    private Integer assignTime;

    /**
     * 投资次数
     *
     * @mbggenerated
     */
    private Integer assignNum;

    private Integer recoverPeriod;

    /**
     * 客户端,0pc,1微信,2android,3ios,4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 债转还款状态 0 未还款 1还款中 2还款完成 3还款失败
     *
     * @mbggenerated
     */
    private Integer repayStatus;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getCreditId() {
        return creditId;
    }

    public void setCreditId(Integer creditId) {
        this.creditId = creditId;
    }

    public Integer getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(Integer creditNid) {
        this.creditNid = creditNid;
    }

    public Integer getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(Integer creditUserId) {
        this.creditUserId = creditUserId;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName == null ? null : creditUserName.trim();
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid == null ? null : bidNid.trim();
    }

    public Integer getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(Integer borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
    }

    public BigDecimal getBidApr() {
        return bidApr;
    }

    public void setBidApr(BigDecimal bidApr) {
        this.bidApr = bidApr;
    }

    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName == null ? null : bidName.trim();
    }

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid == null ? null : tenderNid.trim();
    }

    public Integer getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(Integer creditStatus) {
        this.creditStatus = creditStatus;
    }

    public Integer getCreditOrder() {
        return creditOrder;
    }

    public void setCreditOrder(Integer creditOrder) {
        this.creditOrder = creditOrder;
    }

    public Integer getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(Integer creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public Integer getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(Integer creditTerm) {
        this.creditTerm = creditTerm;
    }

    public Integer getCreditTermHold() {
        return creditTermHold;
    }

    public void setCreditTermHold(Integer creditTermHold) {
        this.creditTermHold = creditTermHold;
    }

    public BigDecimal getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(BigDecimal creditCapital) {
        this.creditCapital = creditCapital;
    }

    public BigDecimal getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(BigDecimal creditAccount) {
        this.creditAccount = creditAccount;
    }

    public BigDecimal getCreditInterest() {
        return creditInterest;
    }

    public void setCreditInterest(BigDecimal creditInterest) {
        this.creditInterest = creditInterest;
    }

    public BigDecimal getCreditInterestAdvance() {
        return creditInterestAdvance;
    }

    public void setCreditInterestAdvance(BigDecimal creditInterestAdvance) {
        this.creditInterestAdvance = creditInterestAdvance;
    }

    public BigDecimal getCreditDiscount() {
        return creditDiscount;
    }

    public void setCreditDiscount(BigDecimal creditDiscount) {
        this.creditDiscount = creditDiscount;
    }

    public BigDecimal getCreditIncome() {
        return creditIncome;
    }

    public void setCreditIncome(BigDecimal creditIncome) {
        this.creditIncome = creditIncome;
    }

    public BigDecimal getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(BigDecimal creditFee) {
        this.creditFee = creditFee;
    }

    public BigDecimal getCreditPrice() {
        return creditPrice;
    }

    public void setCreditPrice(BigDecimal creditPrice) {
        this.creditPrice = creditPrice;
    }

    public BigDecimal getCreditCapitalAssigned() {
        return creditCapitalAssigned;
    }

    public void setCreditCapitalAssigned(BigDecimal creditCapitalAssigned) {
        this.creditCapitalAssigned = creditCapitalAssigned;
    }

    public BigDecimal getCreditInterestAssigned() {
        return creditInterestAssigned;
    }

    public void setCreditInterestAssigned(BigDecimal creditInterestAssigned) {
        this.creditInterestAssigned = creditInterestAssigned;
    }

    public BigDecimal getCreditInterestAdvanceAssigned() {
        return creditInterestAdvanceAssigned;
    }

    public void setCreditInterestAdvanceAssigned(BigDecimal creditInterestAdvanceAssigned) {
        this.creditInterestAdvanceAssigned = creditInterestAdvanceAssigned;
    }

    public BigDecimal getCreditRepayAccount() {
        return creditRepayAccount;
    }

    public void setCreditRepayAccount(BigDecimal creditRepayAccount) {
        this.creditRepayAccount = creditRepayAccount;
    }

    public BigDecimal getCreditRepayCapital() {
        return creditRepayCapital;
    }

    public void setCreditRepayCapital(BigDecimal creditRepayCapital) {
        this.creditRepayCapital = creditRepayCapital;
    }

    public BigDecimal getCreditRepayInterest() {
        return creditRepayInterest;
    }

    public void setCreditRepayInterest(BigDecimal creditRepayInterest) {
        this.creditRepayInterest = creditRepayInterest;
    }

    public Integer getCreditRepayEndTime() {
        return creditRepayEndTime;
    }

    public void setCreditRepayEndTime(Integer creditRepayEndTime) {
        this.creditRepayEndTime = creditRepayEndTime;
    }

    public Integer getCreditRepayLastTime() {
        return creditRepayLastTime;
    }

    public void setCreditRepayLastTime(Integer creditRepayLastTime) {
        this.creditRepayLastTime = creditRepayLastTime;
    }

    public Integer getCreditRepayNextTime() {
        return creditRepayNextTime;
    }

    public void setCreditRepayNextTime(Integer creditRepayNextTime) {
        this.creditRepayNextTime = creditRepayNextTime;
    }

    public Integer getCreditRepayYesTime() {
        return creditRepayYesTime;
    }

    public void setCreditRepayYesTime(Integer creditRepayYesTime) {
        this.creditRepayYesTime = creditRepayYesTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Integer assignTime) {
        this.assignTime = assignTime;
    }

    public Integer getAssignNum() {
        return assignNum;
    }

    public void setAssignNum(Integer assignNum) {
        this.assignNum = assignNum;
    }

    public Integer getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(Integer recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}