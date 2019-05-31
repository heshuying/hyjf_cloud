/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wenxin
 * @version DuibaOrderVO, v0.1 2019/5/29 17:33
 */
public class DuibaOrderVO implements Serializable {
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
    private String duibaOrderId;

    /**
     * 汇盈订单号
     *
     * @mbggenerated
     */
    private String hyOrderId;

    /**
     * 订单兑换人用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 姓名
     *
     * @mbggenerated
     */
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
    private String exchangeContent;

    /**
     * 商品类型
     *
     * @mbggenerated
     */
    private String productType;

    /**
     * 售价
     *
     * @mbggenerated
     */
    private BigDecimal sellingPrice;

    /**
     * 划线价
     *
     * @mbggenerated
     */
    private BigDecimal markingPrice;

    /**
     * 成本
     *
     * @mbggenerated
     */
    private BigDecimal cost;

    /**
     * 订单状态
     *
     * @mbggenerated
     */
    private Integer orderStatus;

    /**
     * 下单时间
     *
     * @mbggenerated
     */
    private Integer orderTime;

    /**
     * 完成时间
     *
     * @mbggenerated
     */
    private Integer completionTime;

    /**
     * 发货状态
     *
     * @mbggenerated
     */
    private Integer deliveryStatus;

    /**
     * 收货信息
     *
     * @mbggenerated
     */
    private String receivingInformation;

    /**
     * 虚拟商品充值状态
     *
     * @mbggenerated
     */
    private String rechargeState;

    /**
     * 处理状态
     *
     * @mbggenerated
     */
    private Integer processingState;

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
    private String remark;

    private String orderTypeTab;

    /**
     * 商品编码
     *
     * @mbggenerated
     */
    private String commodityCode;

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

    public String getOrderTypeTab() {
        return orderTypeTab;
    }

    public void setOrderTypeTab(String orderTypeTab) {
        this.orderTypeTab = orderTypeTab;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }
}
