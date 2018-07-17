/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.datacollect;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hyjf.am.vo.BaseVO;

/**
 * @author fuqiang
 * @version PcChannelStatisticsVO, v0.1 2018/7/16 11:20
 */
public class PcChannelStatisticsVO extends BaseVO implements Serializable {
	private Integer id;

	private Integer sourceId;

	private String sourceName;

	private Integer accessNumber;

	private Integer registNumber;

	private Integer openAccountNumber;

	private Integer tenderNumber;

	private BigDecimal cumulativeRecharge;

	private BigDecimal cumulativeInvestment;

	private BigDecimal hztTenderPrice;

	private BigDecimal hxfTenderPrice;

	private BigDecimal htlTenderPrice;

	private BigDecimal htjTenderPrice;

	private BigDecimal rtbTenderPrice;

	private BigDecimal hzrTenderPrice;

	private Date addTime;

	private static final long serialVersionUID = 1L;

	public PcChannelStatisticsVO(Integer sourceId, String sourceName, Integer accessNumber, Integer registNumber,
			Integer openAccountNumber, Integer tenderNumber, BigDecimal cumulativeRecharge, BigDecimal hztTenderPrice,
			BigDecimal hxfTenderPrice, BigDecimal htlTenderPrice, BigDecimal htjTenderPrice, BigDecimal rtbTenderPrice,
			BigDecimal hzrTenderPrice, Date addTime) {
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.accessNumber = accessNumber;
		this.registNumber = registNumber;
		this.openAccountNumber = openAccountNumber;
		this.tenderNumber = tenderNumber;
		this.cumulativeRecharge = cumulativeRecharge;
		this.hztTenderPrice = hztTenderPrice;
		this.hxfTenderPrice = hxfTenderPrice;
		this.htlTenderPrice = htlTenderPrice;
		this.htjTenderPrice = htjTenderPrice;
		this.rtbTenderPrice = rtbTenderPrice;
		this.hzrTenderPrice = hzrTenderPrice;
		this.addTime = addTime;
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

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName == null ? null : sourceName.trim();
	}

	public Integer getAccessNumber() {
		return accessNumber;
	}

	public void setAccessNumber(Integer accessNumber) {
		this.accessNumber = accessNumber;
	}

	public Integer getRegistNumber() {
		return registNumber;
	}

	public void setRegistNumber(Integer registNumber) {
		this.registNumber = registNumber;
	}

	public Integer getOpenAccountNumber() {
		return openAccountNumber;
	}

	public void setOpenAccountNumber(Integer openAccountNumber) {
		this.openAccountNumber = openAccountNumber;
	}

	public Integer getTenderNumber() {
		return tenderNumber;
	}

	public void setTenderNumber(Integer tenderNumber) {
		this.tenderNumber = tenderNumber;
	}

	public BigDecimal getCumulativeRecharge() {
		return cumulativeRecharge;
	}

	public void setCumulativeRecharge(BigDecimal cumulativeRecharge) {
		this.cumulativeRecharge = cumulativeRecharge;
	}

	public BigDecimal getCumulativeInvestment() {
		return cumulativeInvestment;
	}

	public void setCumulativeInvestment(BigDecimal cumulativeInvestment) {
		this.cumulativeInvestment = cumulativeInvestment;
	}

	public BigDecimal getHztTenderPrice() {
		return hztTenderPrice;
	}

	public void setHztTenderPrice(BigDecimal hztTenderPrice) {
		this.hztTenderPrice = hztTenderPrice;
	}

	public BigDecimal getHxfTenderPrice() {
		return hxfTenderPrice;
	}

	public void setHxfTenderPrice(BigDecimal hxfTenderPrice) {
		this.hxfTenderPrice = hxfTenderPrice;
	}

	public BigDecimal getHtlTenderPrice() {
		return htlTenderPrice;
	}

	public void setHtlTenderPrice(BigDecimal htlTenderPrice) {
		this.htlTenderPrice = htlTenderPrice;
	}

	public BigDecimal getHtjTenderPrice() {
		return htjTenderPrice;
	}

	public void setHtjTenderPrice(BigDecimal htjTenderPrice) {
		this.htjTenderPrice = htjTenderPrice;
	}

	public BigDecimal getRtbTenderPrice() {
		return rtbTenderPrice;
	}

	public void setRtbTenderPrice(BigDecimal rtbTenderPrice) {
		this.rtbTenderPrice = rtbTenderPrice;
	}

	public BigDecimal getHzrTenderPrice() {
		return hzrTenderPrice;
	}

	public void setHzrTenderPrice(BigDecimal hzrTenderPrice) {
		this.hzrTenderPrice = hzrTenderPrice;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
