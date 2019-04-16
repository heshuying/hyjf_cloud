/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.dto;

/**
 * 客户同步回调请求具体参数
 * @author cui
 * @version CustomerSyncQO, v0.1 2019/4/16 14:43
 */
public class CustomerSyncQO {

    //资产端客户编号
    private String assetCustomerId;
    //财富端客户id
    private String customerId;
    //财富端id
    private Integer entId;
    //资产端用户名
    private String userName;
    //员工编号
    private String empNo;
    //平台注册时间
    private String platformRegistrationTime;
    //平台开户时间
    private String platformAccountOpeningTime;
    //开户行
    private String bankOfDeposit;
    //银行卡号
    private String bankCardNumber;
    //银行预留手机号
    private String bankReservedPhoneNumber;
    //沉淀资金
    private Double precipitatedCapital;
    //待收资金
    private Double fundsToBeCollected;


    public String getAssetCustomerId() {
        return assetCustomerId;
    }

    public void setAssetCustomerId(String assetCustomerId) {
        this.assetCustomerId = assetCustomerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getPlatformRegistrationTime() {
        return platformRegistrationTime;
    }

    public void setPlatformRegistrationTime(String platformRegistrationTime) {
        this.platformRegistrationTime = platformRegistrationTime;
    }

    public String getPlatformAccountOpeningTime() {
        return platformAccountOpeningTime;
    }

    public void setPlatformAccountOpeningTime(String platformAccountOpeningTime) {
        this.platformAccountOpeningTime = platformAccountOpeningTime;
    }

    public String getBankOfDeposit() {
        return bankOfDeposit;
    }

    public void setBankOfDeposit(String bankOfDeposit) {
        this.bankOfDeposit = bankOfDeposit;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBankReservedPhoneNumber() {
        return bankReservedPhoneNumber;
    }

    public void setBankReservedPhoneNumber(String bankReservedPhoneNumber) {
        this.bankReservedPhoneNumber = bankReservedPhoneNumber;
    }

    public Double getPrecipitatedCapital() {
        return precipitatedCapital;
    }

    public void setPrecipitatedCapital(Double precipitatedCapital) {
        this.precipitatedCapital = precipitatedCapital;
    }

    public Double getFundsToBeCollected() {
        return fundsToBeCollected;
    }

    public void setFundsToBeCollected(Double fundsToBeCollected) {
        this.fundsToBeCollected = fundsToBeCollected;
    }
}
