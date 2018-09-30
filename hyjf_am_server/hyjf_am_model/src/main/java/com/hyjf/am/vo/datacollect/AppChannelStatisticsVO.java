/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.datacollect;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fuqiang
 * @version AppChannelStatisticsVO, v0.1 2018/7/16 14:47
 */
public class AppChannelStatisticsVO extends BaseVO implements Serializable {
	private Integer id;

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

	public AppChannelStatisticsVO(){}

	public AppChannelStatisticsVO(Integer sourceId, String channelName, Integer visitCount, Integer registerCount,
			Integer openAccountCount, Integer investNumber, BigDecimal cumulativeCharge, BigDecimal hztInvestSum,
			BigDecimal hxfInvestSum, BigDecimal htlInvestSum, BigDecimal htjInvestSum, BigDecimal rtbInvestSum,
			BigDecimal hzrInvestSum, Date updateTime, BigDecimal registerAttrCount, Integer accountNumberIos,
			Integer accountNumberPc, Integer accountNumberAndroid, Integer accountNumberWechat,
			Integer tenderNumberAndroid, Integer tenderNumberIos, Integer tenderNumberPc, Integer tenderNumberWechat,
			BigDecimal cumulativeAttrCharge, Integer openAccountAttrCount, Integer investAttrNumber,
			BigDecimal cumulativeAttrInvest) {
		this.sourceId = sourceId;
		this.channelName = channelName;
		this.visitCount = visitCount;
		this.registerCount = registerCount;
		this.openAccountCount = openAccountCount;
		this.investNumber = investNumber;
		this.cumulativeCharge = cumulativeCharge;
		this.hztInvestSum = hztInvestSum;
		this.hxfInvestSum = hxfInvestSum;
		this.htlInvestSum = htlInvestSum;
		this.htjInvestSum = htjInvestSum;
		this.rtbInvestSum = rtbInvestSum;
		this.hzrInvestSum = hzrInvestSum;
		this.updateTime = updateTime;
		this.registerAttrCount = registerAttrCount;
		this.accountNumberIos = accountNumberIos;
		this.accountNumberPc = accountNumberPc;
		this.accountNumberAndroid = accountNumberAndroid;
		this.accountNumberWechat = accountNumberWechat;
		this.tenderNumberAndroid = tenderNumberAndroid;
		this.tenderNumberIos = tenderNumberIos;
		this.tenderNumberPc = tenderNumberPc;
		this.tenderNumberWechat = tenderNumberWechat;
		this.cumulativeAttrCharge = cumulativeAttrCharge;
		this.openAccountAttrCount = openAccountAttrCount;
		this.investAttrNumber = investAttrNumber;
		this.cumulativeAttrInvest = cumulativeAttrInvest;
	}

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
