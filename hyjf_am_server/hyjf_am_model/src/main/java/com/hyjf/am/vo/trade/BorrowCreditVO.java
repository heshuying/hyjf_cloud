/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jijun
 * @since 20180622
 */
public class BorrowCreditVO extends BaseVO implements Serializable{
	
	 private Integer creditId;

	    private Integer creditNid;

	    private Integer creditUserId;

	    private String creditUserName;

	    private String bidNid;

	    private Integer borrowUserId;

	    private String borrowUserName;

	    private BigDecimal bidApr;

	    private String bidName;

	    private String tenderNid;

	    private Integer creditStatus;

	    private Integer creditOrder;

	    private Integer creditPeriod;

	    private Integer creditTerm;

	    private Integer creditTermHold;

	    private BigDecimal creditCapital;

	    private BigDecimal creditAccount;

	    private BigDecimal creditInterest;

	    private BigDecimal creditInterestAdvance;

	    private BigDecimal creditDiscount;

	    private BigDecimal creditIncome;

	    private BigDecimal creditFee;

	    private BigDecimal creditPrice;

	    private BigDecimal creditCapitalAssigned;

	    private BigDecimal creditInterestAssigned;

	    private BigDecimal creditInterestAdvanceAssigned;

	    private BigDecimal creditRepayAccount;

	    private BigDecimal creditRepayCapital;

	    private BigDecimal creditRepayInterest;

	    private Integer creditRepayEndTime;

	    private Integer creditRepayLastTime;

	    private Integer creditRepayNextTime;

	    private Integer creditRepayYesTime;

	    private Integer createDate;

	    private Integer addTime;

	    private Integer endTime;

	    private Integer assignTime;

	    private Integer assignNum;

	    private Integer recoverPeriod;

	    private Integer client;

	    private Integer repayStatus;

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

	    public Integer getCreateDate() {
	        return createDate;
	    }

	    public void setCreateDate(Integer createDate) {
	        this.createDate = createDate;
	    }

	    public Integer getAddTime() {
	        return addTime;
	    }

	    public void setAddTime(Integer addTime) {
	        this.addTime = addTime;
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
}
