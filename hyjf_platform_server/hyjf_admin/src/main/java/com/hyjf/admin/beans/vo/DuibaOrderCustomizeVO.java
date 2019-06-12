/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author nxl
 * @version AccountDetailVO, v0.1 2018/6/30 10:13
 */
public class DuibaOrderCustomizeVO implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 兑吧订单号
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "兑吧订单号")
    private String duibaOrderId;

    /**
     * 汇盈订单号
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "汇盈订单号")
    private String hyOrderId;

    /**
     * 订单兑换人用户名
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "订单兑换人用户名")
    private String userName;

    /**
     * 姓名
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "姓名")
    private String trueName;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 兑换内容
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "兑换内容")
    private String exchangeContent;

    /**
     * 商品类型
     *
     * @mbggenerated
     */
    private String productType;

    /**
     * 商品类型
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "商品类型")
    private String productTypeStr;


    /**
     * 售价
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "售价")
    private BigDecimal sellingPrice;

    /**
     * 划线价
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "划线价")
    private BigDecimal markingPrice;

    /**
     * 成本
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "成本")
    private BigDecimal cost;

    /**
     * 订单状态
     *
     * @mbggenerated
     */
    private Integer orderStatus;

    /**
     * 订单状态
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "订单状态")
    private String orderStatusStr;

    /**
     * 下单时间
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "下单时间")
    private Integer orderTime;

    /**
     * 完成时间
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "完成时间")
    private Integer completionTime;

    /**
     * 发货状态
     *
     * @mbggenerated
     */
    private Integer deliveryStatus;

    /**
     * 发货状态
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "发货状态")
    private String deliveryStatusStr;

    /**
     * 收货信息
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "收货信息")
    private String receivingInformation;

    /**
     * 虚拟商品充值状态
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "虚拟商品充值状态")
    private String rechargeState;

    /**
     * 处理状态
     *
     * @mbggenerated
     */
    private Integer processingState;

    /**
     * 处理状态
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "处理状态")
    private String processingStateStr;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createBy;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateBy;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 备注
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    private String orderTypeTab;

    /**
     * 商品编码
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "商品编码")
    private String commodityCode;

    /**
     * 汇率
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "汇率")
    private BigDecimal exchangeRate;

    /**
     * 积分
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "积分")
    private Integer integralPrice;

    /**
     * 订单激活状态
     *
     * @mbggenerated
     */
    private Integer activationType;

    /**
     * 优惠卷用户表id
     *
     * @mbggenerated
     */
    private Integer couponUserId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDuibaOrderId() {
        return duibaOrderId;
    }

    public void setDuibaOrderId(String duibaOrderId) {
        this.duibaOrderId = duibaOrderId == null ? null : duibaOrderId.trim();
    }

    public String getHyOrderId() {
        return hyOrderId;
    }

    public void setHyOrderId(String hyOrderId) {
        this.hyOrderId = hyOrderId == null ? null : hyOrderId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getExchangeContent() {
        return exchangeContent;
    }

    public void setExchangeContent(String exchangeContent) {
        this.exchangeContent = exchangeContent == null ? null : exchangeContent.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getMarkingPrice() {
        return markingPrice;
    }

    public void setMarkingPrice(BigDecimal markingPrice) {
        this.markingPrice = markingPrice;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Integer orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Integer completionTime) {
        this.completionTime = completionTime;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getReceivingInformation() {
        return receivingInformation;
    }

    public void setReceivingInformation(String receivingInformation) {
        this.receivingInformation = receivingInformation == null ? null : receivingInformation.trim();
    }

    public String getRechargeState() {
        return rechargeState;
    }

    public void setRechargeState(String rechargeState) {
        this.rechargeState = rechargeState == null ? null : rechargeState.trim();
    }

    public Integer getProcessingState() {
        return processingState;
    }

    public void setProcessingState(Integer processingState) {
        this.processingState = processingState;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getIntegralPrice() {
        return integralPrice;
    }

    public void setIntegralPrice(Integer integralPrice) {
        this.integralPrice = integralPrice;
    }

    public Integer getActivationType() {
        return activationType;
    }

    public void setActivationType(Integer activationType) {
        this.activationType = activationType;
    }

    public Integer getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(Integer couponUserId) {
        this.couponUserId = couponUserId;
    }

    public String getProductTypeStr() {
        return productTypeStr;
    }

    public void setProductTypeStr(String productTypeStr) {
        this.productTypeStr = productTypeStr;
    }

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

    public String getDeliveryStatusStr() {
        return deliveryStatusStr;
    }

    public void setDeliveryStatusStr(String deliveryStatusStr) {
        this.deliveryStatusStr = deliveryStatusStr;
    }

    public String getProcessingStateStr() {
        return processingStateStr;
    }

    public void setProcessingStateStr(String processingStateStr) {
        this.processingStateStr = processingStateStr;
    }

    public String getOrderTypeTab() {
        return orderTypeTab;
    }

    public void setOrderTypeTab(String orderTypeTab) {
        this.orderTypeTab = orderTypeTab;
    }
}
