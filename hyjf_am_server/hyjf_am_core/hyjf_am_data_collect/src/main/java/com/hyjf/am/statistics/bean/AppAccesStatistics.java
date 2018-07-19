package com.hyjf.am.statistics.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiasq
 * @version AppAccesStatistics, v0.1 2018/7/19 14:15
 * app登录渠道统计
 */
@Document(collection = "t_app_access_statistics")
public class AppAccesStatistics implements Serializable {
    private String id;

    private Integer sourceId;

    private String channelName;

    private Integer visitCount;

    private Integer registerCount;

    private Integer openAccountCount;

    private Integer investNumber;

    private BigDecimal cumulativeCharge;

    private BigDecimal cumulativeInvest;

    private BigDecimal hztInvestSum;

    private BigDecimal hxfInvestSum;

    private BigDecimal htlInvestSum;

    private BigDecimal htjInvestSum;

    private BigDecimal rtbInvestSum;

    private BigDecimal hzrInvestSum;

    private Date updateTime;

    private BigDecimal registerAttrCount;

    private Integer accountNumberIos;

    private Integer accountNumberPc;

    private Integer accountNumberAndroid;

    private Integer accountNumberWechat;

    private Integer tenderNumberAndroid;

    private Integer tenderNumberIos;

    private Integer tenderNumberPc;

    private Integer tenderNumberWechat;

    private BigDecimal cumulativeAttrCharge;

    private Integer openAccountAttrCount;

    private Integer investAttrNumber;

    private BigDecimal cumulativeAttrInvest;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(Integer registerCount) {
        this.registerCount = registerCount;
    }

    public Integer getOpenAccountCount() {
        return openAccountCount;
    }

    public void setOpenAccountCount(Integer openAccountCount) {
        this.openAccountCount = openAccountCount;
    }

    public Integer getInvestNumber() {
        return investNumber;
    }

    public void setInvestNumber(Integer investNumber) {
        this.investNumber = investNumber;
    }

    public BigDecimal getCumulativeCharge() {
        return cumulativeCharge;
    }

    public void setCumulativeCharge(BigDecimal cumulativeCharge) {
        this.cumulativeCharge = cumulativeCharge;
    }

    public BigDecimal getCumulativeInvest() {
        return cumulativeInvest;
    }

    public void setCumulativeInvest(BigDecimal cumulativeInvest) {
        this.cumulativeInvest = cumulativeInvest;
    }

    public BigDecimal getHztInvestSum() {
        return hztInvestSum;
    }

    public void setHztInvestSum(BigDecimal hztInvestSum) {
        this.hztInvestSum = hztInvestSum;
    }

    public BigDecimal getHxfInvestSum() {
        return hxfInvestSum;
    }

    public void setHxfInvestSum(BigDecimal hxfInvestSum) {
        this.hxfInvestSum = hxfInvestSum;
    }

    public BigDecimal getHtlInvestSum() {
        return htlInvestSum;
    }

    public void setHtlInvestSum(BigDecimal htlInvestSum) {
        this.htlInvestSum = htlInvestSum;
    }

    public BigDecimal getHtjInvestSum() {
        return htjInvestSum;
    }

    public void setHtjInvestSum(BigDecimal htjInvestSum) {
        this.htjInvestSum = htjInvestSum;
    }

    public BigDecimal getRtbInvestSum() {
        return rtbInvestSum;
    }

    public void setRtbInvestSum(BigDecimal rtbInvestSum) {
        this.rtbInvestSum = rtbInvestSum;
    }

    public BigDecimal getHzrInvestSum() {
        return hzrInvestSum;
    }

    public void setHzrInvestSum(BigDecimal hzrInvestSum) {
        this.hzrInvestSum = hzrInvestSum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getRegisterAttrCount() {
        return registerAttrCount;
    }

    public void setRegisterAttrCount(BigDecimal registerAttrCount) {
        this.registerAttrCount = registerAttrCount;
    }

    public Integer getAccountNumberIos() {
        return accountNumberIos;
    }

    public void setAccountNumberIos(Integer accountNumberIos) {
        this.accountNumberIos = accountNumberIos;
    }

    public Integer getAccountNumberPc() {
        return accountNumberPc;
    }

    public void setAccountNumberPc(Integer accountNumberPc) {
        this.accountNumberPc = accountNumberPc;
    }

    public Integer getAccountNumberAndroid() {
        return accountNumberAndroid;
    }

    public void setAccountNumberAndroid(Integer accountNumberAndroid) {
        this.accountNumberAndroid = accountNumberAndroid;
    }

    public Integer getAccountNumberWechat() {
        return accountNumberWechat;
    }

    public void setAccountNumberWechat(Integer accountNumberWechat) {
        this.accountNumberWechat = accountNumberWechat;
    }

    public Integer getTenderNumberAndroid() {
        return tenderNumberAndroid;
    }

    public void setTenderNumberAndroid(Integer tenderNumberAndroid) {
        this.tenderNumberAndroid = tenderNumberAndroid;
    }

    public Integer getTenderNumberIos() {
        return tenderNumberIos;
    }

    public void setTenderNumberIos(Integer tenderNumberIos) {
        this.tenderNumberIos = tenderNumberIos;
    }

    public Integer getTenderNumberPc() {
        return tenderNumberPc;
    }

    public void setTenderNumberPc(Integer tenderNumberPc) {
        this.tenderNumberPc = tenderNumberPc;
    }

    public Integer getTenderNumberWechat() {
        return tenderNumberWechat;
    }

    public void setTenderNumberWechat(Integer tenderNumberWechat) {
        this.tenderNumberWechat = tenderNumberWechat;
    }

    public BigDecimal getCumulativeAttrCharge() {
        return cumulativeAttrCharge;
    }

    public void setCumulativeAttrCharge(BigDecimal cumulativeAttrCharge) {
        this.cumulativeAttrCharge = cumulativeAttrCharge;
    }

    public Integer getOpenAccountAttrCount() {
        return openAccountAttrCount;
    }

    public void setOpenAccountAttrCount(Integer openAccountAttrCount) {
        this.openAccountAttrCount = openAccountAttrCount;
    }

    public Integer getInvestAttrNumber() {
        return investAttrNumber;
    }

    public void setInvestAttrNumber(Integer investAttrNumber) {
        this.investAttrNumber = investAttrNumber;
    }

    public BigDecimal getCumulativeAttrInvest() {
        return cumulativeAttrInvest;
    }

    public void setCumulativeAttrInvest(BigDecimal cumulativeAttrInvest) {
        this.cumulativeAttrInvest = cumulativeAttrInvest;
    }
}
