package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PerformanceReturnDetail implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 账户名
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
     * 邀请人账户名
     *
     * @mbggenerated
     */
    private String refferName;

    /**
     * 投资订单号
     *
     * @mbggenerated
     */
    private String tenderNo;

    /**
     * 单笔投资金额
     *
     * @mbggenerated
     */
    private BigDecimal tenderAmount;

    /**
     * 投资期限
     *
     * @mbggenerated
     */
    private String term;

    /**
     * 产品类型
     *
     * @mbggenerated
     */
    private String productType;

    /**
     * 产品编号
     *
     * @mbggenerated
     */
    private String productNo;

    /**
     * 单笔当月产生的业绩
     *
     * @mbggenerated
     */
    private BigDecimal returnPerformance;

    /**
     * 单笔返现金额
     *
     * @mbggenerated
     */
    private BigDecimal returnAmount;

    /**
     * 加入时间
     *
     * @mbggenerated
     */
    private String joinTime;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRefferName() {
        return refferName;
    }

    public void setRefferName(String refferName) {
        this.refferName = refferName == null ? null : refferName.trim();
    }

    public String getTenderNo() {
        return tenderNo;
    }

    public void setTenderNo(String tenderNo) {
        this.tenderNo = tenderNo == null ? null : tenderNo.trim();
    }

    public BigDecimal getTenderAmount() {
        return tenderAmount;
    }

    public void setTenderAmount(BigDecimal tenderAmount) {
        this.tenderAmount = tenderAmount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public BigDecimal getReturnPerformance() {
        return returnPerformance;
    }

    public void setReturnPerformance(BigDecimal returnPerformance) {
        this.returnPerformance = returnPerformance;
    }

    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime == null ? null : joinTime.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}