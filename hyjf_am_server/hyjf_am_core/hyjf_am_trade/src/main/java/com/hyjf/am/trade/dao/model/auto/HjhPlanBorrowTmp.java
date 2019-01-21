package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhPlanBorrowTmp implements Serializable {
    private Integer id;

    /**
     * 汇计划加入订单号
     *
     * @mbggenerated
     */
    private String accedeOrderId;

    /**
     * 自动投标订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 计划nid
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 加入金额
     *
     * @mbggenerated
     */
    private BigDecimal accedeAccount;

    /**
     * 已投资金额
     *
     * @mbggenerated
     */
    private BigDecimal alreadyInvest;

    /**
     * 资产编号
     *
     * @mbggenerated
     */
    private String assetId;

    /**
     * 机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 机构产品类型
     *
     * @mbggenerated
     */
    private Integer assetType;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 借款金额
     *
     * @mbggenerated
     */
    private BigDecimal borrowAccount;

    /**
     * 投入金额
     *
     * @mbggenerated
     */
    private BigDecimal account;

    /**
     * 借款期限
     *
     * @mbggenerated
     */
    private Integer borrowPeriod;

    /**
     * 还款方式
     *
     * @mbggenerated
     */
    private String borrowStyle;

    /**
     * 标的类型：0原始标的,1债转标的
     *
     * @mbggenerated
     */
    private Integer borrowType;

    /**
     * 债转原用户id
     *
     * @mbggenerated
     */
    private Integer sellUserId;

    /**
     * 债转原投资订单号
     *
     * @mbggenerated
     */
    private String sellOrderId;

    /**
     * 状态
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 是否标的的最后一笔投资/承接(0:非最后一笔；1:最后一笔)
     *
     * @mbggenerated
     */
    private Integer isLast;

    /**
     * 返回状态码
     *
     * @mbggenerated
     */
    private String respCode;

    /**
     * 返回状态详细
     *
     * @mbggenerated
     */
    private String respDesc;

    /**
     * 创建人id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新人id
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public BigDecimal getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(BigDecimal accedeAccount) {
        this.accedeAccount = accedeAccount;
    }

    public BigDecimal getAlreadyInvest() {
        return alreadyInvest;
    }

    public void setAlreadyInvest(BigDecimal alreadyInvest) {
        this.alreadyInvest = alreadyInvest;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public BigDecimal getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(BigDecimal borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public Integer getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(Integer borrowType) {
        this.borrowType = borrowType;
    }

    public Integer getSellUserId() {
        return sellUserId;
    }

    public void setSellUserId(Integer sellUserId) {
        this.sellUserId = sellUserId;
    }

    public String getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(String sellOrderId) {
        this.sellOrderId = sellOrderId == null ? null : sellOrderId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsLast() {
        return isLast;
    }

    public void setIsLast(Integer isLast) {
        this.isLast = isLast;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode == null ? null : respCode.trim();
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc == null ? null : respDesc.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}