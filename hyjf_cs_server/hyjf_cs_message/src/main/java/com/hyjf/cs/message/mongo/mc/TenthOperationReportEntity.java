/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.mc;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 运营报告十大投资
 * @author tanyy
 * @version TenthOperationReportEntity, v0.1 2018/7/23 10:03
 */
@Document(collection = "tenthoperationreport")
public class TenthOperationReportEntity implements Serializable {
    private String id;

    private String operationReportId;

    private Integer operationReportType;

    private String firstTenderUsername;

    private BigDecimal firstTenderAmount;

    private String secondTenderUsername;

    private BigDecimal secondTenderAmount;

    private String thirdTenderUsername;

    private BigDecimal thirdTenderAmount;

    private String fourthTenderUsername;

    private BigDecimal fourthTenderAmount;

    private String fifthTenderUsername;

    private BigDecimal fifthTenderAmount;

    private String sixthTenderUsername;

    private BigDecimal sixthTenderAmount;

    private String seventhTenderUsername;

    private BigDecimal seventhTenderAmount;

    private String eighthTenderUsername;

    private BigDecimal eighthTenderAmount;

    private String ninthTenderUsername;

    private BigDecimal ninthTenderAmount;

    private String tenthTenderUsername;

    private BigDecimal tenthTenderAmount;

    private BigDecimal tenTenderAmount;

    private BigDecimal tenTenderProportion;

    private BigDecimal otherTenderAmount;

    private BigDecimal otherTenderProportion;

    private String mostTenderUsername;

    private BigDecimal mostTenderAmount;

    private Integer mostTenderUserAge;

    private String mostTenderUserArea;

    private String bigMinnerUsername;

    private BigDecimal bigMinnerProfit;

    private Integer bigMinnerUserAge;

    private String bigMinnerUserArea;

    private String activeTenderUsername;

    private Long activeTenderNum;

    private Integer activeTenderUserAge;

    private String activeTenderUserArea;

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

    public Integer getOperationReportType() {
        return operationReportType;
    }

    public void setOperationReportType(Integer operationReportType) {
        this.operationReportType = operationReportType;
    }

    public String getFirstTenderUsername() {
        return firstTenderUsername;
    }

    public void setFirstTenderUsername(String firstTenderUsername) {
        this.firstTenderUsername = firstTenderUsername == null ? null : firstTenderUsername.trim();
    }

    public BigDecimal getFirstTenderAmount() {
        return firstTenderAmount;
    }

    public void setFirstTenderAmount(BigDecimal firstTenderAmount) {
        this.firstTenderAmount = firstTenderAmount;
    }

    public String getSecondTenderUsername() {
        return secondTenderUsername;
    }

    public void setSecondTenderUsername(String secondTenderUsername) {
        this.secondTenderUsername = secondTenderUsername == null ? null : secondTenderUsername.trim();
    }

    public BigDecimal getSecondTenderAmount() {
        return secondTenderAmount;
    }

    public void setSecondTenderAmount(BigDecimal secondTenderAmount) {
        this.secondTenderAmount = secondTenderAmount;
    }

    public String getThirdTenderUsername() {
        return thirdTenderUsername;
    }

    public void setThirdTenderUsername(String thirdTenderUsername) {
        this.thirdTenderUsername = thirdTenderUsername == null ? null : thirdTenderUsername.trim();
    }

    public BigDecimal getThirdTenderAmount() {
        return thirdTenderAmount;
    }

    public void setThirdTenderAmount(BigDecimal thirdTenderAmount) {
        this.thirdTenderAmount = thirdTenderAmount;
    }

    public String getFourthTenderUsername() {
        return fourthTenderUsername;
    }

    public void setFourthTenderUsername(String fourthTenderUsername) {
        this.fourthTenderUsername = fourthTenderUsername == null ? null : fourthTenderUsername.trim();
    }

    public BigDecimal getFourthTenderAmount() {
        return fourthTenderAmount;
    }

    public void setFourthTenderAmount(BigDecimal fourthTenderAmount) {
        this.fourthTenderAmount = fourthTenderAmount;
    }

    public String getFifthTenderUsername() {
        return fifthTenderUsername;
    }

    public void setFifthTenderUsername(String fifthTenderUsername) {
        this.fifthTenderUsername = fifthTenderUsername == null ? null : fifthTenderUsername.trim();
    }

    public BigDecimal getFifthTenderAmount() {
        return fifthTenderAmount;
    }

    public void setFifthTenderAmount(BigDecimal fifthTenderAmount) {
        this.fifthTenderAmount = fifthTenderAmount;
    }

    public String getSixthTenderUsername() {
        return sixthTenderUsername;
    }

    public void setSixthTenderUsername(String sixthTenderUsername) {
        this.sixthTenderUsername = sixthTenderUsername == null ? null : sixthTenderUsername.trim();
    }

    public BigDecimal getSixthTenderAmount() {
        return sixthTenderAmount;
    }

    public void setSixthTenderAmount(BigDecimal sixthTenderAmount) {
        this.sixthTenderAmount = sixthTenderAmount;
    }

    public String getSeventhTenderUsername() {
        return seventhTenderUsername;
    }

    public void setSeventhTenderUsername(String seventhTenderUsername) {
        this.seventhTenderUsername = seventhTenderUsername == null ? null : seventhTenderUsername.trim();
    }

    public BigDecimal getSeventhTenderAmount() {
        return seventhTenderAmount;
    }

    public void setSeventhTenderAmount(BigDecimal seventhTenderAmount) {
        this.seventhTenderAmount = seventhTenderAmount;
    }

    public String getEighthTenderUsername() {
        return eighthTenderUsername;
    }

    public void setEighthTenderUsername(String eighthTenderUsername) {
        this.eighthTenderUsername = eighthTenderUsername == null ? null : eighthTenderUsername.trim();
    }

    public BigDecimal getEighthTenderAmount() {
        return eighthTenderAmount;
    }

    public void setEighthTenderAmount(BigDecimal eighthTenderAmount) {
        this.eighthTenderAmount = eighthTenderAmount;
    }

    public String getNinthTenderUsername() {
        return ninthTenderUsername;
    }

    public void setNinthTenderUsername(String ninthTenderUsername) {
        this.ninthTenderUsername = ninthTenderUsername == null ? null : ninthTenderUsername.trim();
    }

    public BigDecimal getNinthTenderAmount() {
        return ninthTenderAmount;
    }

    public void setNinthTenderAmount(BigDecimal ninthTenderAmount) {
        this.ninthTenderAmount = ninthTenderAmount;
    }

    public String getTenthTenderUsername() {
        return tenthTenderUsername;
    }

    public void setTenthTenderUsername(String tenthTenderUsername) {
        this.tenthTenderUsername = tenthTenderUsername == null ? null : tenthTenderUsername.trim();
    }

    public BigDecimal getTenthTenderAmount() {
        return tenthTenderAmount;
    }

    public void setTenthTenderAmount(BigDecimal tenthTenderAmount) {
        this.tenthTenderAmount = tenthTenderAmount;
    }

    public BigDecimal getTenTenderAmount() {
        return tenTenderAmount;
    }

    public void setTenTenderAmount(BigDecimal tenTenderAmount) {
        this.tenTenderAmount = tenTenderAmount;
    }

    public BigDecimal getTenTenderProportion() {
        return tenTenderProportion;
    }

    public void setTenTenderProportion(BigDecimal tenTenderProportion) {
        this.tenTenderProportion = tenTenderProportion;
    }

    public BigDecimal getOtherTenderAmount() {
        return otherTenderAmount;
    }

    public void setOtherTenderAmount(BigDecimal otherTenderAmount) {
        this.otherTenderAmount = otherTenderAmount;
    }

    public BigDecimal getOtherTenderProportion() {
        return otherTenderProportion;
    }

    public void setOtherTenderProportion(BigDecimal otherTenderProportion) {
        this.otherTenderProportion = otherTenderProportion;
    }

    public String getMostTenderUsername() {
        return mostTenderUsername;
    }

    public void setMostTenderUsername(String mostTenderUsername) {
        this.mostTenderUsername = mostTenderUsername == null ? null : mostTenderUsername.trim();
    }

    public BigDecimal getMostTenderAmount() {
        return mostTenderAmount;
    }

    public void setMostTenderAmount(BigDecimal mostTenderAmount) {
        this.mostTenderAmount = mostTenderAmount;
    }

    public Integer getMostTenderUserAge() {
        return mostTenderUserAge;
    }

    public void setMostTenderUserAge(Integer mostTenderUserAge) {
        this.mostTenderUserAge = mostTenderUserAge;
    }

    public String getMostTenderUserArea() {
        return mostTenderUserArea;
    }

    public void setMostTenderUserArea(String mostTenderUserArea) {
        this.mostTenderUserArea = mostTenderUserArea == null ? null : mostTenderUserArea.trim();
    }

    public String getBigMinnerUsername() {
        return bigMinnerUsername;
    }

    public void setBigMinnerUsername(String bigMinnerUsername) {
        this.bigMinnerUsername = bigMinnerUsername == null ? null : bigMinnerUsername.trim();
    }

    public BigDecimal getBigMinnerProfit() {
        return bigMinnerProfit;
    }

    public void setBigMinnerProfit(BigDecimal bigMinnerProfit) {
        this.bigMinnerProfit = bigMinnerProfit;
    }

    public Integer getBigMinnerUserAge() {
        return bigMinnerUserAge;
    }

    public void setBigMinnerUserAge(Integer bigMinnerUserAge) {
        this.bigMinnerUserAge = bigMinnerUserAge;
    }

    public String getBigMinnerUserArea() {
        return bigMinnerUserArea;
    }

    public void setBigMinnerUserArea(String bigMinnerUserArea) {
        this.bigMinnerUserArea = bigMinnerUserArea == null ? null : bigMinnerUserArea.trim();
    }

    public String getActiveTenderUsername() {
        return activeTenderUsername;
    }

    public void setActiveTenderUsername(String activeTenderUsername) {
        this.activeTenderUsername = activeTenderUsername == null ? null : activeTenderUsername.trim();
    }

    public Long getActiveTenderNum() {
        return activeTenderNum;
    }

    public void setActiveTenderNum(Long activeTenderNum) {
        this.activeTenderNum = activeTenderNum;
    }

    public Integer getActiveTenderUserAge() {
        return activeTenderUserAge;
    }

    public void setActiveTenderUserAge(Integer activeTenderUserAge) {
        this.activeTenderUserAge = activeTenderUserAge;
    }

    public String getActiveTenderUserArea() {
        return activeTenderUserArea;
    }

    public void setActiveTenderUserArea(String activeTenderUserArea) {
        this.activeTenderUserArea = activeTenderUserArea == null ? null : activeTenderUserArea.trim();
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
