/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

/**
 * 订单信息接口响应具体参数
 * @author kou
 * @version TenderAccedeVO, v0.1 2019/4/17 17:43
 */
public class TenderAccedeVO {

    //订单编号
    private String orderNo;
    //财富端id
    private Integer entId;
    //订单状态
    private  Integer  orderStatus;
    //资产端客户id
    private String assetCustomerId;
    //资产端id
    private  Integer  assetId;
    //员工编号
    private  String empNo ;
    //产品分类
    private Integer  productType;
    //产品编号
    private String  productNo;
    //产品名称
    private String  productName;
    //交易金额
    private double  transAmount;
    //交易时间
    private String  transTime;
    //期限数值
    private String  deadlineNum;
    //期限单位
    private Integer  DeadlineUnit;
    //合同编号
    private String  contractNumber;
    //保险公司
    private String  insurer;
    //投保人姓名
    private String  insuredName;
    //投保人手机号
    private String insuredPhone;
    //投保人证件类型
    private Integer  insuredPaperworkType;
    //投保人证件号
    private String insuredPaperworkNo;
    //被投保人姓名
    private String  cusName ;
    //被保人证件类型
    private  Integer  cusPaperworkType;
    //被保人证件号
    private  String cusPaperworkNo;
    //被保人手机号
    private String cusPhone;
    //保单生效开始时间
    private String insurance_effect_time;
    //保单生效结束时间
    private String insurance_Invalid_time;
    //保单号
    private  String insurance_police;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAssetCustomerId() {
        return assetCustomerId;
    }

    public void setAssetCustomerId(String assetCustomerId) {
        this.assetCustomerId = assetCustomerId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(double transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransTime() {
        return transTime;
    }

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getDeadlineNum() {
        return deadlineNum;
    }

    public void setDeadlineNum(String deadlineNum) {
        this.deadlineNum = deadlineNum;
    }

    public Integer getDeadlineUnit() {
        return DeadlineUnit;
    }

    public void setDeadlineUnit(Integer deadlineUnit) {
        DeadlineUnit = deadlineUnit;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getInsuredPhone() {
        return insuredPhone;
    }

    public void setInsuredPhone(String insuredPhone) {
        this.insuredPhone = insuredPhone;
    }

    public Integer getInsuredPaperworkType() {
        return insuredPaperworkType;
    }

    public void setInsuredPaperworkType(Integer insuredPaperworkType) {
        this.insuredPaperworkType = insuredPaperworkType;
    }

    public String getInsuredPaperworkNo() {
        return insuredPaperworkNo;
    }

    public void setInsuredPaperworkNo(String insuredPaperworkNo) {
        this.insuredPaperworkNo = insuredPaperworkNo;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public Integer getCusPaperworkType() {
        return cusPaperworkType;
    }

    public void setCusPaperworkType(Integer cusPaperworkType) {
        this.cusPaperworkType = cusPaperworkType;
    }

    public String getCusPaperworkNo() {
        return cusPaperworkNo;
    }

    public void setCusPaperworkNo(String cusPaperworkNo) {
        this.cusPaperworkNo = cusPaperworkNo;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getInsurance_effect_time() {
        return insurance_effect_time;
    }

    public void setInsurance_effect_time(String insurance_effect_time) {
        this.insurance_effect_time = insurance_effect_time;
    }

    public String getInsurance_Invalid_time() {
        return insurance_Invalid_time;
    }

    public void setInsurance_Invalid_time(String insurance_Invalid_time) {
        this.insurance_Invalid_time = insurance_Invalid_time;
    }

    public String getInsurance_police() {
        return insurance_police;
    }

    public void setInsurance_police(String insurance_police) {
        this.insurance_police = insurance_police;
    }
}
