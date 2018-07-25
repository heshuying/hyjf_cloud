/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.mc;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanyy
 * @version HalfYearOperationReportEntity, v0.1 2018/6/27 10:06
 */
@Document(collection = "halfyearoperationreport")
public class HalfYearOperationReportEntity implements Serializable{

    private String id;

    private String operationReportId;

    private String cnName;

    private String enName;

    private BigDecimal firstMonthAmount;

    private BigDecimal secondMonthAmount;

    private BigDecimal thirdMonthAmount;

    private BigDecimal fourthMonthAmount;

    private BigDecimal fifthMonthAmount;

    private BigDecimal sixthMonthAmount;

    private BigDecimal halfYearAmount;

    private BigDecimal halfYearProfit;

    private Integer halfYearSuccessDeal;

    private Integer halfYearRechargeDeal;

    private BigDecimal halfYearRechargeAmount;

    private Integer halfYearSuccessMonth;

    private BigDecimal halfYearSuccessMonthAmount;

    private BigDecimal halfYearAvgProfit;

    private Integer halfYearAppDealNum;

    private BigDecimal halfYearAppDealProportion;

    private Integer halfYearWechatDealNum;

    private BigDecimal halfYearWechatDealProportion;

    private Integer halfYearPcDealNum;

    private BigDecimal halfYearPcDealProportion;

    private Integer lessThirtyDayNum;

    private BigDecimal lessThirtyDayProportion;

    private Integer thirtyDayNum;

    private BigDecimal thirtyDayProportion;

    private Integer oneMonthNum;

    private BigDecimal oneMonthProportion;

    private Integer twoMonthNum;

    private BigDecimal twoMonthProportion;

    private Integer threeMonthNum;

    private BigDecimal threeMonthProportion;

    private Integer fourMonthNum;

    private BigDecimal fourMonthProportion;

    private Integer fiveMonthNum;

    private BigDecimal fiveMonthProportion;

    private Integer sixMonthNum;

    private BigDecimal sixMonthProportion;

    private Integer nineMonthNum;

    private BigDecimal nineMonthProportion;

    private Integer tenMonthNum;

    private BigDecimal tenMonthProportion;

    private Integer twelveMonthNum;

    private BigDecimal twelveMonthProportion;

    private Integer fifteenMonthNum;

    private BigDecimal fifteenMonthProportion;

    private Integer eighteenMonthNum;

    private BigDecimal eighteenMonthProportion;

    private Integer twentyFourMonthNum;

    private BigDecimal twentyFourMonthProportion;

    private Long phoneNum;

    private Long qqCustomerServiceNum;

    private Long wechatCustomerServiceNum;

    private Integer dealQuestionNum;

    private Integer updateTime;

    private Integer updateUserId;

    private Integer createTime;

    private Integer createUserId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationReportId() {
        return operationReportId;
    }

    public void setOperationReportId(String operationReportId) {
        this.operationReportId = operationReportId;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName == null ? null : cnName.trim();
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }

    public BigDecimal getFirstMonthAmount() {
        return firstMonthAmount;
    }

    public void setFirstMonthAmount(BigDecimal firstMonthAmount) {
        this.firstMonthAmount = firstMonthAmount;
    }

    public BigDecimal getSecondMonthAmount() {
        return secondMonthAmount;
    }

    public void setSecondMonthAmount(BigDecimal secondMonthAmount) {
        this.secondMonthAmount = secondMonthAmount;
    }

    public BigDecimal getThirdMonthAmount() {
        return thirdMonthAmount;
    }

    public void setThirdMonthAmount(BigDecimal thirdMonthAmount) {
        this.thirdMonthAmount = thirdMonthAmount;
    }

    public BigDecimal getFourthMonthAmount() {
        return fourthMonthAmount;
    }

    public void setFourthMonthAmount(BigDecimal fourthMonthAmount) {
        this.fourthMonthAmount = fourthMonthAmount;
    }

    public BigDecimal getFifthMonthAmount() {
        return fifthMonthAmount;
    }

    public void setFifthMonthAmount(BigDecimal fifthMonthAmount) {
        this.fifthMonthAmount = fifthMonthAmount;
    }

    public BigDecimal getSixthMonthAmount() {
        return sixthMonthAmount;
    }

    public void setSixthMonthAmount(BigDecimal sixthMonthAmount) {
        this.sixthMonthAmount = sixthMonthAmount;
    }

    public BigDecimal getHalfYearAmount() {
        return halfYearAmount;
    }

    public void setHalfYearAmount(BigDecimal halfYearAmount) {
        this.halfYearAmount = halfYearAmount;
    }

    public BigDecimal getHalfYearProfit() {
        return halfYearProfit;
    }

    public void setHalfYearProfit(BigDecimal halfYearProfit) {
        this.halfYearProfit = halfYearProfit;
    }

    public Integer getHalfYearSuccessDeal() {
        return halfYearSuccessDeal;
    }

    public void setHalfYearSuccessDeal(Integer halfYearSuccessDeal) {
        this.halfYearSuccessDeal = halfYearSuccessDeal;
    }

    public Integer getHalfYearRechargeDeal() {
        return halfYearRechargeDeal;
    }

    public void setHalfYearRechargeDeal(Integer halfYearRechargeDeal) {
        this.halfYearRechargeDeal = halfYearRechargeDeal;
    }

    public BigDecimal getHalfYearRechargeAmount() {
        return halfYearRechargeAmount;
    }

    public void setHalfYearRechargeAmount(BigDecimal halfYearRechargeAmount) {
        this.halfYearRechargeAmount = halfYearRechargeAmount;
    }

    public Integer getHalfYearSuccessMonth() {
        return halfYearSuccessMonth;
    }

    public void setHalfYearSuccessMonth(Integer halfYearSuccessMonth) {
        this.halfYearSuccessMonth = halfYearSuccessMonth;
    }

    public BigDecimal getHalfYearSuccessMonthAmount() {
        return halfYearSuccessMonthAmount;
    }

    public void setHalfYearSuccessMonthAmount(BigDecimal halfYearSuccessMonthAmount) {
        this.halfYearSuccessMonthAmount = halfYearSuccessMonthAmount;
    }

    public BigDecimal getHalfYearAvgProfit() {
        return halfYearAvgProfit;
    }

    public void setHalfYearAvgProfit(BigDecimal halfYearAvgProfit) {
        this.halfYearAvgProfit = halfYearAvgProfit;
    }

    public Integer getHalfYearAppDealNum() {
        return halfYearAppDealNum;
    }

    public void setHalfYearAppDealNum(Integer halfYearAppDealNum) {
        this.halfYearAppDealNum = halfYearAppDealNum;
    }

    public BigDecimal getHalfYearAppDealProportion() {
        return halfYearAppDealProportion;
    }

    public void setHalfYearAppDealProportion(BigDecimal halfYearAppDealProportion) {
        this.halfYearAppDealProportion = halfYearAppDealProportion;
    }

    public Integer getHalfYearWechatDealNum() {
        return halfYearWechatDealNum;
    }

    public void setHalfYearWechatDealNum(Integer halfYearWechatDealNum) {
        this.halfYearWechatDealNum = halfYearWechatDealNum;
    }

    public BigDecimal getHalfYearWechatDealProportion() {
        return halfYearWechatDealProportion;
    }

    public void setHalfYearWechatDealProportion(BigDecimal halfYearWechatDealProportion) {
        this.halfYearWechatDealProportion = halfYearWechatDealProportion;
    }

    public Integer getHalfYearPcDealNum() {
        return halfYearPcDealNum;
    }

    public void setHalfYearPcDealNum(Integer halfYearPcDealNum) {
        this.halfYearPcDealNum = halfYearPcDealNum;
    }

    public BigDecimal getHalfYearPcDealProportion() {
        return halfYearPcDealProportion;
    }

    public void setHalfYearPcDealProportion(BigDecimal halfYearPcDealProportion) {
        this.halfYearPcDealProportion = halfYearPcDealProportion;
    }

    public Integer getLessThirtyDayNum() {
        return lessThirtyDayNum;
    }

    public void setLessThirtyDayNum(Integer lessThirtyDayNum) {
        this.lessThirtyDayNum = lessThirtyDayNum;
    }

    public BigDecimal getLessThirtyDayProportion() {
        return lessThirtyDayProportion;
    }

    public void setLessThirtyDayProportion(BigDecimal lessThirtyDayProportion) {
        this.lessThirtyDayProportion = lessThirtyDayProportion;
    }

    public Integer getThirtyDayNum() {
        return thirtyDayNum;
    }

    public void setThirtyDayNum(Integer thirtyDayNum) {
        this.thirtyDayNum = thirtyDayNum;
    }

    public BigDecimal getThirtyDayProportion() {
        return thirtyDayProportion;
    }

    public void setThirtyDayProportion(BigDecimal thirtyDayProportion) {
        this.thirtyDayProportion = thirtyDayProportion;
    }

    public Integer getOneMonthNum() {
        return oneMonthNum;
    }

    public void setOneMonthNum(Integer oneMonthNum) {
        this.oneMonthNum = oneMonthNum;
    }

    public BigDecimal getOneMonthProportion() {
        return oneMonthProportion;
    }

    public void setOneMonthProportion(BigDecimal oneMonthProportion) {
        this.oneMonthProportion = oneMonthProportion;
    }

    public Integer getTwoMonthNum() {
        return twoMonthNum;
    }

    public void setTwoMonthNum(Integer twoMonthNum) {
        this.twoMonthNum = twoMonthNum;
    }

    public BigDecimal getTwoMonthProportion() {
        return twoMonthProportion;
    }

    public void setTwoMonthProportion(BigDecimal twoMonthProportion) {
        this.twoMonthProportion = twoMonthProportion;
    }

    public Integer getThreeMonthNum() {
        return threeMonthNum;
    }

    public void setThreeMonthNum(Integer threeMonthNum) {
        this.threeMonthNum = threeMonthNum;
    }

    public BigDecimal getThreeMonthProportion() {
        return threeMonthProportion;
    }

    public void setThreeMonthProportion(BigDecimal threeMonthProportion) {
        this.threeMonthProportion = threeMonthProportion;
    }

    public Integer getFourMonthNum() {
        return fourMonthNum;
    }

    public void setFourMonthNum(Integer fourMonthNum) {
        this.fourMonthNum = fourMonthNum;
    }

    public BigDecimal getFourMonthProportion() {
        return fourMonthProportion;
    }

    public void setFourMonthProportion(BigDecimal fourMonthProportion) {
        this.fourMonthProportion = fourMonthProportion;
    }

    public Integer getFiveMonthNum() {
        return fiveMonthNum;
    }

    public void setFiveMonthNum(Integer fiveMonthNum) {
        this.fiveMonthNum = fiveMonthNum;
    }

    public BigDecimal getFiveMonthProportion() {
        return fiveMonthProportion;
    }

    public void setFiveMonthProportion(BigDecimal fiveMonthProportion) {
        this.fiveMonthProportion = fiveMonthProportion;
    }

    public Integer getSixMonthNum() {
        return sixMonthNum;
    }

    public void setSixMonthNum(Integer sixMonthNum) {
        this.sixMonthNum = sixMonthNum;
    }

    public BigDecimal getSixMonthProportion() {
        return sixMonthProportion;
    }

    public void setSixMonthProportion(BigDecimal sixMonthProportion) {
        this.sixMonthProportion = sixMonthProportion;
    }

    public Integer getNineMonthNum() {
        return nineMonthNum;
    }

    public void setNineMonthNum(Integer nineMonthNum) {
        this.nineMonthNum = nineMonthNum;
    }

    public BigDecimal getNineMonthProportion() {
        return nineMonthProportion;
    }

    public void setNineMonthProportion(BigDecimal nineMonthProportion) {
        this.nineMonthProportion = nineMonthProportion;
    }

    public Integer getTenMonthNum() {
        return tenMonthNum;
    }

    public void setTenMonthNum(Integer tenMonthNum) {
        this.tenMonthNum = tenMonthNum;
    }

    public BigDecimal getTenMonthProportion() {
        return tenMonthProportion;
    }

    public void setTenMonthProportion(BigDecimal tenMonthProportion) {
        this.tenMonthProportion = tenMonthProportion;
    }

    public Integer getTwelveMonthNum() {
        return twelveMonthNum;
    }

    public void setTwelveMonthNum(Integer twelveMonthNum) {
        this.twelveMonthNum = twelveMonthNum;
    }

    public BigDecimal getTwelveMonthProportion() {
        return twelveMonthProportion;
    }

    public void setTwelveMonthProportion(BigDecimal twelveMonthProportion) {
        this.twelveMonthProportion = twelveMonthProportion;
    }

    public Integer getFifteenMonthNum() {
        return fifteenMonthNum;
    }

    public void setFifteenMonthNum(Integer fifteenMonthNum) {
        this.fifteenMonthNum = fifteenMonthNum;
    }

    public BigDecimal getFifteenMonthProportion() {
        return fifteenMonthProportion;
    }

    public void setFifteenMonthProportion(BigDecimal fifteenMonthProportion) {
        this.fifteenMonthProportion = fifteenMonthProportion;
    }

    public Integer getEighteenMonthNum() {
        return eighteenMonthNum;
    }

    public void setEighteenMonthNum(Integer eighteenMonthNum) {
        this.eighteenMonthNum = eighteenMonthNum;
    }

    public BigDecimal getEighteenMonthProportion() {
        return eighteenMonthProportion;
    }

    public void setEighteenMonthProportion(BigDecimal eighteenMonthProportion) {
        this.eighteenMonthProportion = eighteenMonthProportion;
    }

    public Integer getTwentyFourMonthNum() {
        return twentyFourMonthNum;
    }

    public void setTwentyFourMonthNum(Integer twentyFourMonthNum) {
        this.twentyFourMonthNum = twentyFourMonthNum;
    }

    public BigDecimal getTwentyFourMonthProportion() {
        return twentyFourMonthProportion;
    }

    public void setTwentyFourMonthProportion(BigDecimal twentyFourMonthProportion) {
        this.twentyFourMonthProportion = twentyFourMonthProportion;
    }

    public Long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Long getQqCustomerServiceNum() {
        return qqCustomerServiceNum;
    }

    public void setQqCustomerServiceNum(Long qqCustomerServiceNum) {
        this.qqCustomerServiceNum = qqCustomerServiceNum;
    }

    public Long getWechatCustomerServiceNum() {
        return wechatCustomerServiceNum;
    }

    public void setWechatCustomerServiceNum(Long wechatCustomerServiceNum) {
        this.wechatCustomerServiceNum = wechatCustomerServiceNum;
    }

    public Integer getDealQuestionNum() {
        return dealQuestionNum;
    }

    public void setDealQuestionNum(Integer dealQuestionNum) {
        this.dealQuestionNum = dealQuestionNum;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}
