package com.hyjf.am.response.admin;

import com.hyjf.am.response.AdminResponse;

import java.math.BigDecimal;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public class UtmResponse<T> extends AdminResponse {
    /** 访问数 */
    private Integer accessNumber;
    /** 注册数 */
    private Integer registNumber;
    /** 开户数 */
    private Integer openAccountNumber;
    /** 投资人数 */
    private Integer tenderNumber;
    /** 累计充值 */
    private BigDecimal cumulativeRecharge;
    /** 汇直投投资金额 */
    private BigDecimal hztTenderPrice;
    /** 汇消费投资金额 */
    private BigDecimal hxfTenderPrice;
    /** 汇天利投资金额 */
    private BigDecimal htlTenderPrice;
    /** 汇添金投资金额 */
    private BigDecimal htjTenderPrice;
    /** 汇金理财投资金额 */
    private BigDecimal rtbTenderPrice;
    /** 汇转让投资金额 */
    private BigDecimal hzrTenderPrice;
    /** app渠道Ios开户数 */
    private Integer accountNumberIos;
    /** app渠道pc开户数 */
    private Integer accountNumberPc;
    /** app渠道android开户数 */
    private Integer accountNumberAndroid;
    /** app渠道微信开户数  */
    private Integer accountNumberWechat;

    private String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
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
}
