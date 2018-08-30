package com.hyjf.am.bean.fdd;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;

public class FddGenerateContractBean implements Serializable{


    private static final long serialVersionUID = 5040840191981425010L;
    /**订单号*/
    private String ordid;
    /**交易类型*/
    private int transType;
    /**借款人真实姓名*/
    private String borrowUserTrueName;
    /**投资人用户名*/
    private String tenderUserName;
    /**投资人真实姓名*/
    private String tenderTrueName;
    /**投资人用户ID*/
    private int tenderUserId;
    /**投资人证件号*/
    private String idCard;
    /**乙方借款金额*/
    private BigDecimal borrowAccount;
    /**借款期限*/
    private String borrowDate;
    /**借款利率*/
    private String borrowRate;
    /**还款方式*/
    private String borrowStyle;
    /**甲方出借金额*/
    private BigDecimal tenderAccount;
    /**格式化金额*/
    private String tenderAccountFMT;
    /**格式化金额*/
    private String tenderInterestFmt;
    /**借款预期收益*/
    private BigDecimal tenderInterest;
    /**签署时间*/
    private String signDate;
    /**标的号*/
    private String borrowNid;
    /**投资类型 0：原始 1：债转 2 :计划*/
    private int tenderType;
    /**客户角色 1：接入平台 2-担保公司
     3-投资人 4-借款人*/
    private String clientRole;
    /**计划生效日期*/
    private String planStartDate;
    /**计划到期日期*/
    private String planEndDate;
    /** 承接编号 */
    private String assignNid;
    /**借款人客户ID*/
    private String borrowerCustomerID;
    /**出让人用户ID*/
    private Integer creditUserID;
    /**合同名称*/
    private String contractName;
    /**垫付债转协议参数集合*/
    private JSONObject paramter;
    /**垫付协议 -期数*/
    private Integer repayPeriod;
    /**垫付协议生成标识*/
    private String teString;

    public String getTenderAccountFMT() {
        return tenderAccountFMT;
    }

    public void setTenderAccountFMT(String tenderAccountFMT) {
        this.tenderAccountFMT = tenderAccountFMT;
    }

    public String getTenderInterestFmt() {
        return tenderInterestFmt;
    }

    public void setTenderInterestFmt(String tenderInterestFmt) {
        this.tenderInterestFmt = tenderInterestFmt;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Integer getCreditUserID() {
        return creditUserID;
    }

    public void setCreditUserID(Integer creditUserID) {
        this.creditUserID = creditUserID;
    }

    public String getBorrowerCustomerID() {
        return borrowerCustomerID;
    }

    public void setBorrowerCustomerID(String borrowerCustomerID) {
        this.borrowerCustomerID = borrowerCustomerID;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }
    /**
     * 承接订单号
     */
    private String assignOrderId;
    /**
     * 债转编号
     */
    private String creditNid;

    /**
     * 债转原始投资订单号
     */
    private String creditTenderNid;

    public String getClientRole() {
        return clientRole;
    }

    public void setClientRole(String clientRole) {
        this.clientRole = clientRole;
    }

    public int getTenderType() {
        return tenderType;
    }

    public void setTenderType(int tenderType) {
        this.tenderType = tenderType;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public int getTenderUserId() {
        return tenderUserId;
    }

    public void setTenderUserId(int tenderUserId) {
        this.tenderUserId = tenderUserId;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public String getBorrowUserTrueName() {
        return borrowUserTrueName;
    }

    public void setBorrowUserTrueName(String borrowUserTrueName) {
        this.borrowUserTrueName = borrowUserTrueName;
    }

    public String getTenderUserName() {
        return tenderUserName;
    }

    public void setTenderUserName(String tenderUserName) {
        this.tenderUserName = tenderUserName;
    }

    public String getTenderTrueName() {
        return tenderTrueName;
    }

    public void setTenderTrueName(String tenderTrueName) {
        this.tenderTrueName = tenderTrueName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public BigDecimal getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(BigDecimal borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getBorrowRate() {
        return borrowRate;
    }

    public void setBorrowRate(String borrowRate) {
        this.borrowRate = borrowRate;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public BigDecimal getTenderAccount() {
        return tenderAccount;
    }

    public void setTenderAccount(BigDecimal tenderAccount) {
        this.tenderAccount = tenderAccount;
    }

    public BigDecimal getTenderInterest() {
        return tenderInterest;
    }

    public void setTenderInterest(BigDecimal tenderInterest) {
        this.tenderInterest = tenderInterest;
    }

    public String getAssignOrderId() {
        return assignOrderId;
    }

    public void setAssignOrderId(String assignOrderId) {
        this.assignOrderId = assignOrderId;
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

    public JSONObject getParamter() {
        return paramter;
    }

    public void setParamter(JSONObject paramter) {
        this.paramter = paramter;
    }

    public Integer getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(Integer repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTeString() {
        return teString;
    }

    public void setTeString(String teString) {
        this.teString = teString;
    }
}
