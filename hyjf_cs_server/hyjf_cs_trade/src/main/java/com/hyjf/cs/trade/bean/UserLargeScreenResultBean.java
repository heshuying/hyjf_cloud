package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.api.EchartsResultVO;
import com.hyjf.am.vo.api.UserCapitalDetailsVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class UserLargeScreenResultBean implements Serializable {

    @ApiModelProperty(value = "规模业绩（新客组）")
    private BigDecimal scalePerformanceNew = BigDecimal.ZERO;
    @ApiModelProperty(value = "规模业绩（老客组）")
    private BigDecimal scalePerformanceOld = BigDecimal.ZERO;
    @ApiModelProperty(value = "坐席月规模业绩（新客组）")
    private List<EchartsResultVO> monthScalePerformanceListNew;
    @ApiModelProperty(value = "坐席月规模业绩（老客组）")
    private List<EchartsResultVO> monthScalePerformanceListOld;
    @ApiModelProperty(value = "坐席月回款情况（新客组）")
    private List<EchartsResultVO> monthReceivedPaymentsNew;
    @ApiModelProperty(value = "坐席月回款情况（老客组）")
    private List<EchartsResultVO> monthReceivedPaymentsOld;
    @ApiModelProperty(value = "运营部总业绩（元）")
    private BigDecimal totalAmount = BigDecimal.ZERO;
    @ApiModelProperty(value = "本月业绩完成率")
    private BigDecimal achievementRate = BigDecimal.ZERO;
    @ApiModelProperty(value = "本月运营部总业绩")
    private BigDecimal achievementDistribution = BigDecimal.ZERO;
    @ApiModelProperty(value = "用户资金明细")
    private BigDecimal userCapitalDetails = BigDecimal.ZERO;
    @ApiModelProperty(value = "本月运营部业绩完成分布")
    private List<EchartsResultVO> achievementDistributionList;
    @ApiModelProperty(value = "新客组月目标")
    private BigDecimal newPassengerGoal;
    @ApiModelProperty(value = "老客组月目标")
    private BigDecimal oldPassengerGoal;
    @ApiModelProperty(value = "用户资金明细")
    private  List<UserCapitalDetailsVO> userCapitalDetailList;
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

    public List<EchartsResultVO> getMonthScalePerformanceListNew() {
        return monthScalePerformanceListNew;
    }

    public void setMonthScalePerformanceListNew(List<EchartsResultVO> monthScalePerformanceListNew) {
        this.monthScalePerformanceListNew = monthScalePerformanceListNew;
    }

    public List<EchartsResultVO> getMonthScalePerformanceListOld() {
        return monthScalePerformanceListOld;
    }

    public void setMonthScalePerformanceListOld(List<EchartsResultVO> monthScalePerformanceListOld) {
        this.monthScalePerformanceListOld = monthScalePerformanceListOld;
    }

    public List<EchartsResultVO> getMonthReceivedPaymentsNew() {
        return monthReceivedPaymentsNew;
    }

    public void setMonthReceivedPaymentsNew(List<EchartsResultVO> monthReceivedPaymentsNew) {
        this.monthReceivedPaymentsNew = monthReceivedPaymentsNew;
    }

    public List<EchartsResultVO> getMonthReceivedPaymentsOld() {
        return monthReceivedPaymentsOld;
    }

    public void setMonthReceivedPaymentsOld(List<EchartsResultVO> monthReceivedPaymentsOld) {
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

    public List<EchartsResultVO> getAchievementDistributionList() {
        return achievementDistributionList;
    }

    public void setAchievementDistributionList(List<EchartsResultVO> achievementDistributionList) {
        this.achievementDistributionList = achievementDistributionList;
    }

    public BigDecimal getNewPassengerGoal() {
        return newPassengerGoal;
    }

    public void setNewPassengerGoal(BigDecimal newPassengerGoal) {
        this.newPassengerGoal = newPassengerGoal;
    }

    public BigDecimal getOldPassengerGoal() {
        return oldPassengerGoal;
    }

    public void setOldPassengerGoal(BigDecimal oldPassengerGoal) {
        this.oldPassengerGoal = oldPassengerGoal;
    }

    public List<UserCapitalDetailsVO> getUserCapitalDetailList() {
        return userCapitalDetailList;
    }

    public void setUserCapitalDetailList(List<UserCapitalDetailsVO> userCapitalDetailList) {
        this.userCapitalDetailList = userCapitalDetailList;
    }
}
