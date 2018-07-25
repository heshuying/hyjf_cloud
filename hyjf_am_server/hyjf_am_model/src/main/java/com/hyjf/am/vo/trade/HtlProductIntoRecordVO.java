/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: sunpeikai
 * @version: HtlProductIntoRecordVO, v0.1 2018/7/25 15:10
 */
public class HtlProductIntoRecordVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    //主键
    private Integer id;
    //用户id
    private Integer userId;
    //用户名
    private String username;
    //推荐人id
    private Integer referee;
    //推荐人
    private String refername;
    //产品id
    private Integer productId;
    //投资金额
    private BigDecimal amount;
    //投资时间
    private String investTime;
    //已赎回金额
    private BigDecimal redeemed;
    //剩余金额
    private BigDecimal restAmount;
    //资金赎回状态
    private Integer status;
    //订单号
    private String orderId;
    //订单时间
    private String orderDate;
    //操作客户端
    private Integer client;
    //投资状态(log表中)
    private String tenderStatus;
    //手机号
    private String mobile;
    //本金
    private BigDecimal balance;
    //投资状态
    private Integer investStatus;
    private String regionName;
    private String branceName;
    private String departmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getReferee() {
        return referee;
    }

    public void setReferee(Integer referee) {
        this.referee = referee;
    }

    public String getRefername() {
        return refername;
    }

    public void setRefername(String refername) {
        this.refername = refername;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public BigDecimal getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(BigDecimal redeemed) {
        this.redeemed = redeemed;
    }

    public BigDecimal getRestAmount() {
        return restAmount;
    }

    public void setRestAmount(BigDecimal restAmount) {
        this.restAmount = restAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(String tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getInvestStatus() {
        return investStatus;
    }

    public void setInvestStatus(Integer investStatus) {
        this.investStatus = investStatus;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBranceName() {
        return branceName;
    }

    public void setBranceName(String branceName) {
        this.branceName = branceName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
