package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class UserLargeScreenBean extends BaseResultBean {

    @ApiModelProperty(value = "规模业绩（新客组）")
    private BigDecimal scalePerformanceNew;
    @ApiModelProperty(value = "规模业绩（老客组）")
    private BigDecimal scalePerformanceOld;
    @ApiModelProperty(value = "坐席月规模业绩（新客组）")
    private BigDecimal monthScalePerformanceNew;
    @ApiModelProperty(value = "坐席月规模业绩（老客组）")
    private BigDecimal monthScalePerformanceOld;
    @ApiModelProperty(value = "坐席月回款情况（新客组）")
    private BigDecimal monthReceivedPaymentsNew;
    @ApiModelProperty(value = "坐席月回款情况（老客组）")
    private BigDecimal monthReceivedPaymentsOld;
    @ApiModelProperty(value = "运营部总业绩（元）")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "本月业绩完成率")
    private BigDecimal achievementRate;
    @ApiModelProperty(value = "本月运营部业绩完成分布")
    private BigDecimal achievementDistribution;
    @ApiModelProperty(value = "用户资金明细")
    private BigDecimal userCapitalDetails;

    public BigDecimal getScalePerformanceNew() {
        return scalePerformanceNew;
    }

    public void setScalePerformanceNew(BigDecimal scalePerformanceNew) {
        this.scalePerformanceNew = scalePerformanceNew;
    }

    public BigDecimal getScalePerformanceOld() {
        return scalePerformanceOld;
    }

    public void setScalePerformanceOld(BigDecimal scalePerformanceOld) {
        this.scalePerformanceOld = scalePerformanceOld;
    }

    public BigDecimal getMonthScalePerformanceNew() {
        return monthScalePerformanceNew;
    }

    public void setMonthScalePerformanceNew(BigDecimal monthScalePerformanceNew) {
        this.monthScalePerformanceNew = monthScalePerformanceNew;
    }

    public BigDecimal getMonthScalePerformanceOld() {
        return monthScalePerformanceOld;
    }

    public void setMonthScalePerformanceOld(BigDecimal monthScalePerformanceOld) {
        this.monthScalePerformanceOld = monthScalePerformanceOld;
    }

    public BigDecimal getMonthReceivedPaymentsNew() {
        return monthReceivedPaymentsNew;
    }

    public void setMonthReceivedPaymentsNew(BigDecimal monthReceivedPaymentsNew) {
        this.monthReceivedPaymentsNew = monthReceivedPaymentsNew;
    }

    public BigDecimal getMonthReceivedPaymentsOld() {
        return monthReceivedPaymentsOld;
    }

    public void setMonthReceivedPaymentsOld(BigDecimal monthReceivedPaymentsOld) {
        this.monthReceivedPaymentsOld = monthReceivedPaymentsOld;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAchievementRate() {
        return achievementRate;
    }

    public void setAchievementRate(BigDecimal achievementRate) {
        this.achievementRate = achievementRate;
    }

    public BigDecimal getAchievementDistribution() {
        return achievementDistribution;
    }

    public void setAchievementDistribution(BigDecimal achievementDistribution) {
        this.achievementDistribution = achievementDistribution;
    }

    public BigDecimal getUserCapitalDetails() {
        return userCapitalDetails;
    }

    public void setUserCapitalDetails(BigDecimal userCapitalDetails) {
        this.userCapitalDetails = userCapitalDetails;
    }
}
