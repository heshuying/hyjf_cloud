package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Product implements Serializable {
    /**
     * ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 编号
     *
     * @mbggenerated
     */
    private String pnumber;

    /**
     * 利率（年化）
     *
     * @mbggenerated
     */
    private BigDecimal interestRate;

    /**
     * 投资上限-投资人
     *
     * @mbggenerated
     */
    private BigDecimal pupper;

    /**
     * 投资下限-投资人
     *
     * @mbggenerated
     */
    private BigDecimal plower;

    /**
     * 已卖金额
     *
     * @mbggenerated
     */
    private BigDecimal total;

    /**
     * 产品上限
     *
     * @mbggenerated
     */
    private BigDecimal allpupper;

    /**
     * 是否可转入 0:可转入 1:不可转入
     *
     * @mbggenerated
     */
    private Integer isTender;

    /**
     * 是否可赎回 0:可赎回 1:不可赎回
     *
     * @mbggenerated
     */
    private Integer isRedeem;

    /**
     * 不可操作注释
     *
     * @mbggenerated
     */
    private String errorRemark;

    /**
     * 对公账户id
     *
     * @mbggenerated
     */
    private String pnumberNew;

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

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber == null ? null : pnumber.trim();
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getPupper() {
        return pupper;
    }

    public void setPupper(BigDecimal pupper) {
        this.pupper = pupper;
    }

    public BigDecimal getPlower() {
        return plower;
    }

    public void setPlower(BigDecimal plower) {
        this.plower = plower;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAllpupper() {
        return allpupper;
    }

    public void setAllpupper(BigDecimal allpupper) {
        this.allpupper = allpupper;
    }

    public Integer getIsTender() {
        return isTender;
    }

    public void setIsTender(Integer isTender) {
        this.isTender = isTender;
    }

    public Integer getIsRedeem() {
        return isRedeem;
    }

    public void setIsRedeem(Integer isRedeem) {
        this.isRedeem = isRedeem;
    }

    public String getErrorRemark() {
        return errorRemark;
    }

    public void setErrorRemark(String errorRemark) {
        this.errorRemark = errorRemark == null ? null : errorRemark.trim();
    }

    public String getPnumberNew() {
        return pnumberNew;
    }

    public void setPnumberNew(String pnumberNew) {
        this.pnumberNew = pnumberNew == null ? null : pnumberNew.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}