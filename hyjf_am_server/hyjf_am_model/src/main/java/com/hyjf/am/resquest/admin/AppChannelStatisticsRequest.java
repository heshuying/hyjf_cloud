/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version AppChannelStatisticsRequest, v0.1 2018/9/21 10:11
 */
public class AppChannelStatisticsRequest extends BasePage implements Serializable {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道ID")
    private String sourceId;

    @ApiModelProperty(value = "渠道")
    private String channelName;

    @ApiModelProperty(value = "访问数")
    private String visitCount;

    @ApiModelProperty(value = "注册数")
    private String registerCount;

    @ApiModelProperty(value = "无主单注册数")
    private String registerAttrCount;

    @ApiModelProperty(value = "开户数")
    private String openAccountCount;

    @ApiModelProperty(value = "投资人数")
    private String investNumber;

    @ApiModelProperty(value = "累计充值")
    private String cumulativeCharge;

    @ApiModelProperty(value = "累计投资")
    private String cumulativeInvest;

    @ApiModelProperty(value = "汇直投投资金额")
    private String hztInvestSum;

    @ApiModelProperty(value = "汇消费投资金额")
    private String hxfInvestSum;

    @ApiModelProperty(value = "汇天利投资金额")
    private String htlInvestSum;

    @ApiModelProperty(value = "汇添金投资金额")
    private String htjInvestSum;

    @ApiModelProperty(value = "汇金理财投资金额")
    private String rtbInvestSum;

    @ApiModelProperty(value = "汇转让投资金额")
    private String hzrInvestSum;

    @ApiModelProperty(value = "开户数ios")
    private String accountNumberIos;

    @ApiModelProperty(value = "开户数pc")
    private String accountNumberPc;

    @ApiModelProperty(value = "开户数wechat")
    private String accountNumberWechat;

    @ApiModelProperty(value = "开户数Android")
    private String accountNumberAndroid;

    @ApiModelProperty(value = "投资人数Android")
    private String tenderNumberAndroid;

    @ApiModelProperty(value = "投资人数pc")
    private String tenderNumberPc;

    @ApiModelProperty(value = "投资人数wechat")
    private String tenderNumberWechat;

    @ApiModelProperty(value = "投资人数ios")
    private String tenderNumberIos;

    @ApiModelProperty(value = "累计充值(无主单)")
    private String cumulativeAttrCharge;

    @ApiModelProperty(value = "开户数(无主单)")
    private String openAccountAttrCount;

    @ApiModelProperty(value = "投资人数(无主单)")
    private String investAttrNumber;

    @ApiModelProperty(value = "累计投资(无主单)")
    private String cumulativeAttrInvest;

    @ApiModelProperty(value = "渠道类别")
    private String utmIds;

    @ApiModelProperty(value = "渠道")
    private String[] utmIdsSrch;

    @ApiModelProperty(value = "时间开始")
    private String timeStartSrch;

    @ApiModelProperty(value = "时间结束")
    private String timeEndSrch;

    @ApiModelProperty(value = "渠道ID")
    private String sourceIdSrch;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(String visitCount) {
        this.visitCount = visitCount;
    }

    public String getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(String registerCount) {
        this.registerCount = registerCount;
    }

    public String getRegisterAttrCount() {
        return registerAttrCount;
    }

    public void setRegisterAttrCount(String registerAttrCount) {
        this.registerAttrCount = registerAttrCount;
    }

    public String getOpenAccountCount() {
        return openAccountCount;
    }

    public void setOpenAccountCount(String openAccountCount) {
        this.openAccountCount = openAccountCount;
    }

    public String getInvestNumber() {
        return investNumber;
    }

    public void setInvestNumber(String investNumber) {
        this.investNumber = investNumber;
    }

    public String getCumulativeCharge() {
        return cumulativeCharge;
    }

    public void setCumulativeCharge(String cumulativeCharge) {
        this.cumulativeCharge = cumulativeCharge;
    }

    public String getCumulativeInvest() {
        return cumulativeInvest;
    }

    public void setCumulativeInvest(String cumulativeInvest) {
        this.cumulativeInvest = cumulativeInvest;
    }

    public String getHztInvestSum() {
        return hztInvestSum;
    }

    public void setHztInvestSum(String hztInvestSum) {
        this.hztInvestSum = hztInvestSum;
    }

    public String getHxfInvestSum() {
        return hxfInvestSum;
    }

    public void setHxfInvestSum(String hxfInvestSum) {
        this.hxfInvestSum = hxfInvestSum;
    }

    public String getHtlInvestSum() {
        return htlInvestSum;
    }

    public void setHtlInvestSum(String htlInvestSum) {
        this.htlInvestSum = htlInvestSum;
    }

    public String getHtjInvestSum() {
        return htjInvestSum;
    }

    public void setHtjInvestSum(String htjInvestSum) {
        this.htjInvestSum = htjInvestSum;
    }

    public String getRtbInvestSum() {
        return rtbInvestSum;
    }

    public void setRtbInvestSum(String rtbInvestSum) {
        this.rtbInvestSum = rtbInvestSum;
    }

    public String getHzrInvestSum() {
        return hzrInvestSum;
    }

    public void setHzrInvestSum(String hzrInvestSum) {
        this.hzrInvestSum = hzrInvestSum;
    }

    public String getAccountNumberIos() {
        return accountNumberIos;
    }

    public void setAccountNumberIos(String accountNumberIos) {
        this.accountNumberIos = accountNumberIos;
    }

    public String getAccountNumberPc() {
        return accountNumberPc;
    }

    public void setAccountNumberPc(String accountNumberPc) {
        this.accountNumberPc = accountNumberPc;
    }

    public String getAccountNumberWechat() {
        return accountNumberWechat;
    }

    public void setAccountNumberWechat(String accountNumberWechat) {
        this.accountNumberWechat = accountNumberWechat;
    }

    public String getAccountNumberAndroid() {
        return accountNumberAndroid;
    }

    public void setAccountNumberAndroid(String accountNumberAndroid) {
        this.accountNumberAndroid = accountNumberAndroid;
    }

    public String getTenderNumberAndroid() {
        return tenderNumberAndroid;
    }

    public void setTenderNumberAndroid(String tenderNumberAndroid) {
        this.tenderNumberAndroid = tenderNumberAndroid;
    }

    public String getTenderNumberPc() {
        return tenderNumberPc;
    }

    public void setTenderNumberPc(String tenderNumberPc) {
        this.tenderNumberPc = tenderNumberPc;
    }

    public String getTenderNumberWechat() {
        return tenderNumberWechat;
    }

    public void setTenderNumberWechat(String tenderNumberWechat) {
        this.tenderNumberWechat = tenderNumberWechat;
    }

    public String getTenderNumberIos() {
        return tenderNumberIos;
    }

    public void setTenderNumberIos(String tenderNumberIos) {
        this.tenderNumberIos = tenderNumberIos;
    }

    public String getCumulativeAttrCharge() {
        return cumulativeAttrCharge;
    }

    public void setCumulativeAttrCharge(String cumulativeAttrCharge) {
        this.cumulativeAttrCharge = cumulativeAttrCharge;
    }

    public String getOpenAccountAttrCount() {
        return openAccountAttrCount;
    }

    public void setOpenAccountAttrCount(String openAccountAttrCount) {
        this.openAccountAttrCount = openAccountAttrCount;
    }

    public String getInvestAttrNumber() {
        return investAttrNumber;
    }

    public void setInvestAttrNumber(String investAttrNumber) {
        this.investAttrNumber = investAttrNumber;
    }

    public String getCumulativeAttrInvest() {
        return cumulativeAttrInvest;
    }

    public void setCumulativeAttrInvest(String cumulativeAttrInvest) {
        this.cumulativeAttrInvest = cumulativeAttrInvest;
    }

    public String getUtmIds() {
        return utmIds;
    }

    public void setUtmIds(String utmIds) {
        this.utmIds = utmIds;
    }

    public String[] getUtmIdsSrch() {
        return utmIdsSrch;
    }

    public void setUtmIdsSrch(String[] utmIdsSrch) {
        this.utmIdsSrch = utmIdsSrch;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getSourceIdSrch() {
        return sourceIdSrch;
    }

    public void setSourceIdSrch(String sourceIdSrch) {
        this.sourceIdSrch = sourceIdSrch;
    }
}
