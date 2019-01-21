package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtPlanInfoStaticCount implements Serializable {
    private Integer id;

    /**
     * 加入金额分布-金额分布0-1万
     *
     * @mbggenerated
     */
    private BigDecimal accedeMoneyOne;

    /**
     * 加入金额分布-金额分布1-2万
     *
     * @mbggenerated
     */
    private BigDecimal accedeMoneyTwo;

    /**
     * 加入金额分布-金额分布2-3万
     *
     * @mbggenerated
     */
    private BigDecimal accedeMoneyThree;

    /**
     * 加入金额分布-金额分布3-4万
     *
     * @mbggenerated
     */
    private BigDecimal accedeMoneyFour;

    /**
     * 加入金额分布-金额分布4-5万
     *
     * @mbggenerated
     */
    private BigDecimal accedeMoneyFive;

    /**
     * 加入金额分布-金额分布5万以上
     *
     * @mbggenerated
     */
    private BigDecimal accedeMoneyFiveUp;

    /**
     * 加入金额分布-人次分布0-1万
     *
     * @mbggenerated
     */
    private Integer accedeMoneyCountOne;

    /**
     * 加入金额分布-人次分布1-2万
     *
     * @mbggenerated
     */
    private Integer accedeMoneyCountTwo;

    /**
     * 加入金额分布-人次分布2-3万
     *
     * @mbggenerated
     */
    private Integer accedeMoneyCountThree;

    /**
     * 加入金额分布-人次分布3-4万
     *
     * @mbggenerated
     */
    private Integer accedeMoneyCountFour;

    /**
     * 加入金额分布-人次分布4-5万
     *
     * @mbggenerated
     */
    private Integer accedeMoneyCountFive;

    /**
     * 加入金额分布-人次分布5万以上
     *
     * @mbggenerated
     */
    private Integer accedeMoneyCountFiveUp;

    /**
     * 加入次数分布-人次分布1次
     *
     * @mbggenerated
     */
    private Integer accedeCountOne;

    /**
     * 加入次数分布-人次分布2-4次
     *
     * @mbggenerated
     */
    private Integer accedeCountTwoFour;

    /**
     * 加入次数分布-人次分布5-8次
     *
     * @mbggenerated
     */
    private Integer accedeCountFiveEgiht;

    /**
     * 加入次数分布-人次分布9-15次
     *
     * @mbggenerated
     */
    private Integer accedeCountNineFifteen;

    /**
     * 加入次数分布-人次分布16-30次
     *
     * @mbggenerated
     */
    private Integer accedeCountSixteenThirty;

    /**
     * 加入次数分布-人次分布31次以上
     *
     * @mbggenerated
     */
    private Integer accedeCountThirtyfirstUp;

    /**
     * 平台加入金额-pc
     *
     * @mbggenerated
     */
    private BigDecimal accedeClientMoneyPc;

    /**
     * 平台加入金额-wei
     *
     * @mbggenerated
     */
    private BigDecimal accedeClientMoneyWei;

    /**
     * 平台加入金额-ios
     *
     * @mbggenerated
     */
    private BigDecimal accedeClientMoneyIos;

    /**
     * 平台加入金额-android
     *
     * @mbggenerated
     */
    private BigDecimal accedeClientMoneyAndroid;

    /**
     * 平台加入人次-pc
     *
     * @mbggenerated
     */
    private Integer accedeClientCountPc;

    /**
     * 平台加入人次-wei
     *
     * @mbggenerated
     */
    private Integer accedeClientCountWei;

    /**
     * 平台加入人次-ios
     *
     * @mbggenerated
     */
    private Integer accedeClientCountIos;

    /**
     * 平台加入人次-android
     *
     * @mbggenerated
     */
    private Integer accedeClientCountAndroid;

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

    public BigDecimal getAccedeMoneyOne() {
        return accedeMoneyOne;
    }

    public void setAccedeMoneyOne(BigDecimal accedeMoneyOne) {
        this.accedeMoneyOne = accedeMoneyOne;
    }

    public BigDecimal getAccedeMoneyTwo() {
        return accedeMoneyTwo;
    }

    public void setAccedeMoneyTwo(BigDecimal accedeMoneyTwo) {
        this.accedeMoneyTwo = accedeMoneyTwo;
    }

    public BigDecimal getAccedeMoneyThree() {
        return accedeMoneyThree;
    }

    public void setAccedeMoneyThree(BigDecimal accedeMoneyThree) {
        this.accedeMoneyThree = accedeMoneyThree;
    }

    public BigDecimal getAccedeMoneyFour() {
        return accedeMoneyFour;
    }

    public void setAccedeMoneyFour(BigDecimal accedeMoneyFour) {
        this.accedeMoneyFour = accedeMoneyFour;
    }

    public BigDecimal getAccedeMoneyFive() {
        return accedeMoneyFive;
    }

    public void setAccedeMoneyFive(BigDecimal accedeMoneyFive) {
        this.accedeMoneyFive = accedeMoneyFive;
    }

    public BigDecimal getAccedeMoneyFiveUp() {
        return accedeMoneyFiveUp;
    }

    public void setAccedeMoneyFiveUp(BigDecimal accedeMoneyFiveUp) {
        this.accedeMoneyFiveUp = accedeMoneyFiveUp;
    }

    public Integer getAccedeMoneyCountOne() {
        return accedeMoneyCountOne;
    }

    public void setAccedeMoneyCountOne(Integer accedeMoneyCountOne) {
        this.accedeMoneyCountOne = accedeMoneyCountOne;
    }

    public Integer getAccedeMoneyCountTwo() {
        return accedeMoneyCountTwo;
    }

    public void setAccedeMoneyCountTwo(Integer accedeMoneyCountTwo) {
        this.accedeMoneyCountTwo = accedeMoneyCountTwo;
    }

    public Integer getAccedeMoneyCountThree() {
        return accedeMoneyCountThree;
    }

    public void setAccedeMoneyCountThree(Integer accedeMoneyCountThree) {
        this.accedeMoneyCountThree = accedeMoneyCountThree;
    }

    public Integer getAccedeMoneyCountFour() {
        return accedeMoneyCountFour;
    }

    public void setAccedeMoneyCountFour(Integer accedeMoneyCountFour) {
        this.accedeMoneyCountFour = accedeMoneyCountFour;
    }

    public Integer getAccedeMoneyCountFive() {
        return accedeMoneyCountFive;
    }

    public void setAccedeMoneyCountFive(Integer accedeMoneyCountFive) {
        this.accedeMoneyCountFive = accedeMoneyCountFive;
    }

    public Integer getAccedeMoneyCountFiveUp() {
        return accedeMoneyCountFiveUp;
    }

    public void setAccedeMoneyCountFiveUp(Integer accedeMoneyCountFiveUp) {
        this.accedeMoneyCountFiveUp = accedeMoneyCountFiveUp;
    }

    public Integer getAccedeCountOne() {
        return accedeCountOne;
    }

    public void setAccedeCountOne(Integer accedeCountOne) {
        this.accedeCountOne = accedeCountOne;
    }

    public Integer getAccedeCountTwoFour() {
        return accedeCountTwoFour;
    }

    public void setAccedeCountTwoFour(Integer accedeCountTwoFour) {
        this.accedeCountTwoFour = accedeCountTwoFour;
    }

    public Integer getAccedeCountFiveEgiht() {
        return accedeCountFiveEgiht;
    }

    public void setAccedeCountFiveEgiht(Integer accedeCountFiveEgiht) {
        this.accedeCountFiveEgiht = accedeCountFiveEgiht;
    }

    public Integer getAccedeCountNineFifteen() {
        return accedeCountNineFifteen;
    }

    public void setAccedeCountNineFifteen(Integer accedeCountNineFifteen) {
        this.accedeCountNineFifteen = accedeCountNineFifteen;
    }

    public Integer getAccedeCountSixteenThirty() {
        return accedeCountSixteenThirty;
    }

    public void setAccedeCountSixteenThirty(Integer accedeCountSixteenThirty) {
        this.accedeCountSixteenThirty = accedeCountSixteenThirty;
    }

    public Integer getAccedeCountThirtyfirstUp() {
        return accedeCountThirtyfirstUp;
    }

    public void setAccedeCountThirtyfirstUp(Integer accedeCountThirtyfirstUp) {
        this.accedeCountThirtyfirstUp = accedeCountThirtyfirstUp;
    }

    public BigDecimal getAccedeClientMoneyPc() {
        return accedeClientMoneyPc;
    }

    public void setAccedeClientMoneyPc(BigDecimal accedeClientMoneyPc) {
        this.accedeClientMoneyPc = accedeClientMoneyPc;
    }

    public BigDecimal getAccedeClientMoneyWei() {
        return accedeClientMoneyWei;
    }

    public void setAccedeClientMoneyWei(BigDecimal accedeClientMoneyWei) {
        this.accedeClientMoneyWei = accedeClientMoneyWei;
    }

    public BigDecimal getAccedeClientMoneyIos() {
        return accedeClientMoneyIos;
    }

    public void setAccedeClientMoneyIos(BigDecimal accedeClientMoneyIos) {
        this.accedeClientMoneyIos = accedeClientMoneyIos;
    }

    public BigDecimal getAccedeClientMoneyAndroid() {
        return accedeClientMoneyAndroid;
    }

    public void setAccedeClientMoneyAndroid(BigDecimal accedeClientMoneyAndroid) {
        this.accedeClientMoneyAndroid = accedeClientMoneyAndroid;
    }

    public Integer getAccedeClientCountPc() {
        return accedeClientCountPc;
    }

    public void setAccedeClientCountPc(Integer accedeClientCountPc) {
        this.accedeClientCountPc = accedeClientCountPc;
    }

    public Integer getAccedeClientCountWei() {
        return accedeClientCountWei;
    }

    public void setAccedeClientCountWei(Integer accedeClientCountWei) {
        this.accedeClientCountWei = accedeClientCountWei;
    }

    public Integer getAccedeClientCountIos() {
        return accedeClientCountIos;
    }

    public void setAccedeClientCountIos(Integer accedeClientCountIos) {
        this.accedeClientCountIos = accedeClientCountIos;
    }

    public Integer getAccedeClientCountAndroid() {
        return accedeClientCountAndroid;
    }

    public void setAccedeClientCountAndroid(Integer accedeClientCountAndroid) {
        this.accedeClientCountAndroid = accedeClientCountAndroid;
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