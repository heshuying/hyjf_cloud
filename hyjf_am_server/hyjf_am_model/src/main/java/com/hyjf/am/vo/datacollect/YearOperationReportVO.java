package com.hyjf.am.vo.datacollect;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @author tanyy
 * @version YearOperationReportVO, v0.1 2018/7/24 13:52
 */
public class YearOperationReportVO implements Serializable {
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

    private BigDecimal seventhMonthAmoun;

    private BigDecimal eighteenMonthAmount;

    private BigDecimal ninthMonthAmount;

    private BigDecimal tenthMonthAmount;

    private BigDecimal eleventhMonthAmount;

    private BigDecimal twelveMonthAmount;

    private BigDecimal yearAmount;

    private BigDecimal yearProfit;

    private Integer yearSuccessDeal;

    private Integer yearSuccessMonth;

    private BigDecimal yearSuccessMonthAmount;

    private BigDecimal yearAvgProfit;

    private Integer yearAppDealNum;

    private BigDecimal yearAppDealProportion;

    private Integer yearWechatDealNum;

    private BigDecimal yearWechatDealProportion;

    private Integer yearPcDealNum;

    private BigDecimal yearPcDealProportion;

    private BigDecimal yearAppDealAmount;

    private BigDecimal yearAppAmountProportion;

    private BigDecimal yearWechatDealAmount;

    private BigDecimal yearWechatAmountProportion;

    private BigDecimal yearPcDealAmount;

    private BigDecimal yearPcAmountProportion;

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

    private Integer twentyFourMonthNum;

    private BigDecimal twentyFourMonthProportion;

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

    public BigDecimal getSeventhMonthAmoun() {
        return seventhMonthAmoun;
    }

    public void setSeventhMonthAmoun(BigDecimal seventhMonthAmoun) {
        this.seventhMonthAmoun = seventhMonthAmoun;
    }

    public BigDecimal getEighteenMonthAmount() {
        return eighteenMonthAmount;
    }

    public void setEighteenMonthAmount(BigDecimal eighteenMonthAmount) {
        this.eighteenMonthAmount = eighteenMonthAmount;
    }

    public BigDecimal getNinthMonthAmount() {
        return ninthMonthAmount;
    }

    public void setNinthMonthAmount(BigDecimal ninthMonthAmount) {
        this.ninthMonthAmount = ninthMonthAmount;
    }

    public BigDecimal getTenthMonthAmount() {
        return tenthMonthAmount;
    }

    public void setTenthMonthAmount(BigDecimal tenthMonthAmount) {
        this.tenthMonthAmount = tenthMonthAmount;
    }

    public BigDecimal getEleventhMonthAmount() {
        return eleventhMonthAmount;
    }

    public void setEleventhMonthAmount(BigDecimal eleventhMonthAmount) {
        this.eleventhMonthAmount = eleventhMonthAmount;
    }

    public BigDecimal getTwelveMonthAmount() {
        return twelveMonthAmount;
    }

    public void setTwelveMonthAmount(BigDecimal twelveMonthAmount) {
        this.twelveMonthAmount = twelveMonthAmount;
    }

    public BigDecimal getYearAmount() {
        return yearAmount;
    }

    public void setYearAmount(BigDecimal yearAmount) {
        this.yearAmount = yearAmount;
    }

    public BigDecimal getYearProfit() {
        return yearProfit;
    }

    public void setYearProfit(BigDecimal yearProfit) {
        this.yearProfit = yearProfit;
    }

    public Integer getYearSuccessDeal() {
        return yearSuccessDeal;
    }

    public void setYearSuccessDeal(Integer yearSuccessDeal) {
        this.yearSuccessDeal = yearSuccessDeal;
    }

    public Integer getYearSuccessMonth() {
        return yearSuccessMonth;
    }

    public void setYearSuccessMonth(Integer yearSuccessMonth) {
        this.yearSuccessMonth = yearSuccessMonth;
    }

    public BigDecimal getYearSuccessMonthAmount() {
        return yearSuccessMonthAmount;
    }

    public void setYearSuccessMonthAmount(BigDecimal yearSuccessMonthAmount) {
        this.yearSuccessMonthAmount = yearSuccessMonthAmount;
    }

    public BigDecimal getYearAvgProfit() {
        return yearAvgProfit;
    }

    public void setYearAvgProfit(BigDecimal yearAvgProfit) {
        this.yearAvgProfit = yearAvgProfit;
    }

    public Integer getYearAppDealNum() {
        return yearAppDealNum;
    }

    public void setYearAppDealNum(Integer yearAppDealNum) {
        this.yearAppDealNum = yearAppDealNum;
    }

    public BigDecimal getYearAppDealProportion() {
        return yearAppDealProportion;
    }

    public void setYearAppDealProportion(BigDecimal yearAppDealProportion) {
        this.yearAppDealProportion = yearAppDealProportion;
    }

    public Integer getYearWechatDealNum() {
        return yearWechatDealNum;
    }

    public void setYearWechatDealNum(Integer yearWechatDealNum) {
        this.yearWechatDealNum = yearWechatDealNum;
    }

    public BigDecimal getYearWechatDealProportion() {
        return yearWechatDealProportion;
    }

    public void setYearWechatDealProportion(BigDecimal yearWechatDealProportion) {
        this.yearWechatDealProportion = yearWechatDealProportion;
    }

    public Integer getYearPcDealNum() {
        return yearPcDealNum;
    }

    public void setYearPcDealNum(Integer yearPcDealNum) {
        this.yearPcDealNum = yearPcDealNum;
    }

    public BigDecimal getYearPcDealProportion() {
        return yearPcDealProportion;
    }

    public void setYearPcDealProportion(BigDecimal yearPcDealProportion) {
        this.yearPcDealProportion = yearPcDealProportion;
    }

    public BigDecimal getYearAppDealAmount() {
        return yearAppDealAmount;
    }

    public void setYearAppDealAmount(BigDecimal yearAppDealAmount) {
        this.yearAppDealAmount = yearAppDealAmount;
    }

    public BigDecimal getYearAppAmountProportion() {
        return yearAppAmountProportion;
    }

    public void setYearAppAmountProportion(BigDecimal yearAppAmountProportion) {
        this.yearAppAmountProportion = yearAppAmountProportion;
    }

    public BigDecimal getYearWechatDealAmount() {
        return yearWechatDealAmount;
    }

    public void setYearWechatDealAmount(BigDecimal yearWechatDealAmount) {
        this.yearWechatDealAmount = yearWechatDealAmount;
    }

    public BigDecimal getYearWechatAmountProportion() {
        return yearWechatAmountProportion;
    }

    public void setYearWechatAmountProportion(BigDecimal yearWechatAmountProportion) {
        this.yearWechatAmountProportion = yearWechatAmountProportion;
    }

    public BigDecimal getYearPcDealAmount() {
        return yearPcDealAmount;
    }

    public void setYearPcDealAmount(BigDecimal yearPcDealAmount) {
        this.yearPcDealAmount = yearPcDealAmount;
    }

    public BigDecimal getYearPcAmountProportion() {
        return yearPcAmountProportion;
    }

    public void setYearPcAmountProportion(BigDecimal yearPcAmountProportion) {
        this.yearPcAmountProportion = yearPcAmountProportion;
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