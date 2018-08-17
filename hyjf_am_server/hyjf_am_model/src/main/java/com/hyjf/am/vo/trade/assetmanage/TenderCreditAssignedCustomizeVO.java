package com.hyjf.am.vo.trade.assetmanage;

import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class TenderCreditAssignedCustomizeVO {
    /* 主键 */
    private String assignId;
    /* 用户名称 */
    private String userId;
    /* 出让人id */
    private String creditUserId;
    /* 状态 */
    private String status;
    /* 原标标号 */
    private String bidNid;
    /* 债转标号 */
    private String creditNid;
    /* 债转投标单号 */
    private String creditTenderNid;
    /* 认购单号 */
    private String assignNid;
    /* 认购单号 */
    private String assignNidMD5;
    /* 投资本金 */
    private String assignCapital;
    /* 回收总额 */
    private String assignAccount;
    /* 债转利息 */
    private String assignInterest;
    /* 垫付利息 */
    private String assignInterestAdvance;
    /* 购买价格 */
    private String assignPrice;
    /* 支付金额 */
    private String assignPay;
    /* 已还总额 */
    private String assignRepayAccount;
    /* 已还本金 */
    private String assignRepayCapital;
    /* 已还利息 */
    private String assignRepayInterest;
    /* 最后还款日 */
    private String assignRepayEndTime;
    /* 上次还款时间 */
    private String assignRepayLastTime;
    /* 下次还款时间 */
    private String assignRepayNextTime;
    /* 最终实际还款时间 */
    private String assignRepayYesTime;
    /* 还款期数 */
    private String assignRepayPeriod;
    /* 认购日期 */
    private String assignCreateDate;
    /* 服务费 */
    private String creditFee;
    /* 添加时间 */
    private String createTime;
    /* 添加日期 */
    private String createTimeDay;
    /* 添加时分秒 */
    private String createTimeHour;
    /* 剩余期限 */
    private String creditTerm;
    /* 年化率 */
    private String bidApr;
    /* 折让率 */
    private String creditDiscount;
    /* 投资时间 */
    private String tenderTime;
    /* 债转投资人姓名 */
    private String creditAssignUserTrueName;

    public String getAssignId() {
        return assignId;
    }

    public void setAssignId(String assignId) {
        this.assignId = assignId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(String creditUserId) {
        this.creditUserId = creditUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getCreditTenderNid() {
        return creditTenderNid;
    }

    public void setCreditTenderNid(String creditTenderNid) {
        this.creditTenderNid = creditTenderNid;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getAssignNidMD5() {
        return assignNidMD5;
    }

    public void setAssignNidMD5(String assignNidMD5) {
        this.assignNidMD5 = assignNidMD5;
    }

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getAssignAccount() {
        return assignAccount;
    }

    public void setAssignAccount(String assignAccount) {
        this.assignAccount = assignAccount;
    }

    public String getAssignInterest() {
        return assignInterest;
    }

    public void setAssignInterest(String assignInterest) {
        this.assignInterest = assignInterest;
    }

    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public String getAssignPrice() {
        return assignPrice;
    }

    public void setAssignPrice(String assignPrice) {
        this.assignPrice = assignPrice;
    }

    public String getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(String assignPay) {
        this.assignPay = assignPay;
    }

    public String getAssignRepayAccount() {
        return assignRepayAccount;
    }

    public void setAssignRepayAccount(String assignRepayAccount) {
        this.assignRepayAccount = assignRepayAccount;
    }

    public String getAssignRepayCapital() {
        return assignRepayCapital;
    }

    public void setAssignRepayCapital(String assignRepayCapital) {
        this.assignRepayCapital = assignRepayCapital;
    }

    public String getAssignRepayInterest() {
        return assignRepayInterest;
    }

    public void setAssignRepayInterest(String assignRepayInterest) {
        this.assignRepayInterest = assignRepayInterest;
    }

    public String getAssignRepayEndTime() {
        return assignRepayEndTime;
    }

    public void setAssignRepayEndTime(String assignRepayEndTime) {
        this.assignRepayEndTime = assignRepayEndTime;
    }

    public String getAssignRepayLastTime() {
        return assignRepayLastTime;
    }

    public void setAssignRepayLastTime(String assignRepayLastTime) {
        this.assignRepayLastTime = assignRepayLastTime;
    }

    public String getAssignRepayNextTime() {
        return assignRepayNextTime;
    }

    public void setAssignRepayNextTime(String assignRepayNextTime) {
        this.assignRepayNextTime = assignRepayNextTime;
    }

    public String getAssignRepayYesTime() {
        return assignRepayYesTime;
    }

    public void setAssignRepayYesTime(String assignRepayYesTime) {
        this.assignRepayYesTime = assignRepayYesTime;
    }

    public String getAssignRepayPeriod() {
        return assignRepayPeriod;
    }

    public void setAssignRepayPeriod(String assignRepayPeriod) {
        this.assignRepayPeriod = assignRepayPeriod;
    }

    public String getAssignCreateDate() {
        return assignCreateDate;
    }

    public void setAssignCreateDate(String assignCreateDate) {
        this.assignCreateDate = assignCreateDate;
    }

    public String getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(String creditFee) {
        this.creditFee = creditFee;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeDay() {
        return createTimeDay;
    }

    public void setCreateTimeDay(String createTimeDay) {
        this.createTimeDay = createTimeDay;
    }

    public String getCreateTimeHour() {
        return createTimeHour;
    }

    public void setCreateTimeHour(String createTimeHour) {
        this.createTimeHour = createTimeHour;
    }

    public String getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(String creditTerm) {
        this.creditTerm = creditTerm;
    }

    public String getBidApr() {
        return bidApr;
    }

    public void setBidApr(String bidApr) {
        this.bidApr = bidApr;
    }

    public String getCreditDiscount() {
        return creditDiscount;
    }

    public void setCreditDiscount(String creditDiscount) {
        this.creditDiscount = creditDiscount;
    }

    public String getTenderTime() {
        return tenderTime;
    }

    public void setTenderTime(String tenderTime) {
        this.tenderTime = tenderTime;
    }

    public String getCreditAssignUserTrueName() {
        return creditAssignUserTrueName;
    }

    public void setCreditAssignUserTrueName(String creditAssignUserTrueName) {
        this.creditAssignUserTrueName = creditAssignUserTrueName;
    }
}
