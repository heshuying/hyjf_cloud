package com.hyjf.admin.beans;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * 承接信息bean
 * @author zhangyk
 * @date 2018/7/12 19:50
 */
public class BorrowCreditTenderBean extends BaseVO implements Serializable {

    /*用户id*/
    private String userId;

    /*订单号*/
    private String assignNid;

    /*债转编号*/
    private String creditNid;

    /*项目编号*/
    private String bidNid;

    /*承接人*/
    private String userName;

    /*出让人*/
    private String creditUserName;

    /*承接本金*/
    private String assignCapital;

    /*折让率*/
    private String creditDiscount;

    /*认购价格*/
    private String assignPrice;

    /*垫付利息*/
    private String assignInterestAdvance;

    /*债转服务费*/
    private String creditFee;

    /*实付金额*/
    private String assignPay;

    /*合同状态*/
    private String contractStatus;

    /*承接时间*/
    private String addTime;

    /*合同编号*/
    private String contractNumber;

    /*合同下载链接*/
    private String downloadUrl;

    /*预览链接*/
    private String viewUrl;

    /*承接平台*/
    private String client;

    /*原来承接订单号*/
    private String creditTenderNid;

    /** 银行交易状态（S-成功;F-失败;A-初始;W:未开始）*/
    private String state;
    /** 债权结束状态描述*/
    private String stateDesc;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName;
    }

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getCreditDiscount() {
        return creditDiscount;
    }

    public void setCreditDiscount(String creditDiscount) {
        this.creditDiscount = creditDiscount;
    }

    public String getAssignPrice() {
        return assignPrice;
    }

    public void setAssignPrice(String assignPrice) {
        this.assignPrice = assignPrice;
    }

    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public String getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(String creditFee) {
        this.creditFee = creditFee;
    }

    public String getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(String assignPay) {
        this.assignPay = assignPay;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCreditTenderNid() {
        return creditTenderNid;
    }

    public void setCreditTenderNid(String creditTenderNid) {
        this.creditTenderNid = creditTenderNid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }
}
