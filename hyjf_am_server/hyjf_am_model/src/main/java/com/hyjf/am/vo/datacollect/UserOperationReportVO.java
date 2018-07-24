package com.hyjf.am.vo.datacollect;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanyy
 * @version UserOperationReportVO, v0.1 2018/7/24 13:52
 */
public class UserOperationReportVO implements Serializable {
    private String id;

    private String operationReportId;

    private Integer operationReportType;

    private Integer manTenderNum;

    private BigDecimal manTenderNumProportion;

    private Integer womanTenderNum;

    private BigDecimal womanTenderNumProportion;

    private Integer ageFirstStageTenderNum;

    private BigDecimal ageFirstStageTenderProportion;

    private Integer ageSecondStageTenderNum;

    private BigDecimal ageSecondStageTenderProportion;

    private Integer ageThirdStageTenderNum;

    private BigDecimal ageThirdStageTenderProportion;

    private Integer ageFourthStageTenderNum;

    private BigDecimal ageFourthStageTenderProportion;

    private Integer ageFirveStageTenderNum;

    private BigDecimal ageFirveStageTenderProportion;

    private Integer amountFirstStageTenderNum;

    private BigDecimal amountFirstStageTenderProportion;

    private Integer amountSecondStageTenderNum;

    private BigDecimal amountSecondStageTenderProportion;

    private Integer amountThirdStageTenderNum;

    private BigDecimal amountThirdStageTenderProportion;

    private Integer amountFourthStageTenderNum;

    private BigDecimal amountFourthStageTenderProportion;

    private Integer amountFirveStageTenderNum;

    private BigDecimal amountFirveStageTenderProportion;

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

    public Integer getManTenderNum() {
        return manTenderNum;
    }

    public void setManTenderNum(Integer manTenderNum) {
        this.manTenderNum = manTenderNum;
    }

    public BigDecimal getManTenderNumProportion() {
        return manTenderNumProportion;
    }

    public void setManTenderNumProportion(BigDecimal manTenderNumProportion) {
        this.manTenderNumProportion = manTenderNumProportion;
    }

    public Integer getWomanTenderNum() {
        return womanTenderNum;
    }

    public void setWomanTenderNum(Integer womanTenderNum) {
        this.womanTenderNum = womanTenderNum;
    }

    public BigDecimal getWomanTenderNumProportion() {
        return womanTenderNumProportion;
    }

    public void setWomanTenderNumProportion(BigDecimal womanTenderNumProportion) {
        this.womanTenderNumProportion = womanTenderNumProportion;
    }

    public Integer getAgeFirstStageTenderNum() {
        return ageFirstStageTenderNum;
    }

    public void setAgeFirstStageTenderNum(Integer ageFirstStageTenderNum) {
        this.ageFirstStageTenderNum = ageFirstStageTenderNum;
    }

    public BigDecimal getAgeFirstStageTenderProportion() {
        return ageFirstStageTenderProportion;
    }

    public void setAgeFirstStageTenderProportion(BigDecimal ageFirstStageTenderProportion) {
        this.ageFirstStageTenderProportion = ageFirstStageTenderProportion;
    }

    public Integer getAgeSecondStageTenderNum() {
        return ageSecondStageTenderNum;
    }

    public void setAgeSecondStageTenderNum(Integer ageSecondStageTenderNum) {
        this.ageSecondStageTenderNum = ageSecondStageTenderNum;
    }

    public BigDecimal getAgeSecondStageTenderProportion() {
        return ageSecondStageTenderProportion;
    }

    public void setAgeSecondStageTenderProportion(BigDecimal ageSecondStageTenderProportion) {
        this.ageSecondStageTenderProportion = ageSecondStageTenderProportion;
    }

    public Integer getAgeThirdStageTenderNum() {
        return ageThirdStageTenderNum;
    }

    public void setAgeThirdStageTenderNum(Integer ageThirdStageTenderNum) {
        this.ageThirdStageTenderNum = ageThirdStageTenderNum;
    }

    public BigDecimal getAgeThirdStageTenderProportion() {
        return ageThirdStageTenderProportion;
    }

    public void setAgeThirdStageTenderProportion(BigDecimal ageThirdStageTenderProportion) {
        this.ageThirdStageTenderProportion = ageThirdStageTenderProportion;
    }

    public Integer getAgeFourthStageTenderNum() {
        return ageFourthStageTenderNum;
    }

    public void setAgeFourthStageTenderNum(Integer ageFourthStageTenderNum) {
        this.ageFourthStageTenderNum = ageFourthStageTenderNum;
    }

    public BigDecimal getAgeFourthStageTenderProportion() {
        return ageFourthStageTenderProportion;
    }

    public void setAgeFourthStageTenderProportion(BigDecimal ageFourthStageTenderProportion) {
        this.ageFourthStageTenderProportion = ageFourthStageTenderProportion;
    }

    public Integer getAgeFirveStageTenderNum() {
        return ageFirveStageTenderNum;
    }

    public void setAgeFirveStageTenderNum(Integer ageFirveStageTenderNum) {
        this.ageFirveStageTenderNum = ageFirveStageTenderNum;
    }

    public BigDecimal getAgeFirveStageTenderProportion() {
        return ageFirveStageTenderProportion;
    }

    public void setAgeFirveStageTenderProportion(BigDecimal ageFirveStageTenderProportion) {
        this.ageFirveStageTenderProportion = ageFirveStageTenderProportion;
    }

    public Integer getAmountFirstStageTenderNum() {
        return amountFirstStageTenderNum;
    }

    public void setAmountFirstStageTenderNum(Integer amountFirstStageTenderNum) {
        this.amountFirstStageTenderNum = amountFirstStageTenderNum;
    }

    public BigDecimal getAmountFirstStageTenderProportion() {
        return amountFirstStageTenderProportion;
    }

    public void setAmountFirstStageTenderProportion(BigDecimal amountFirstStageTenderProportion) {
        this.amountFirstStageTenderProportion = amountFirstStageTenderProportion;
    }

    public Integer getAmountSecondStageTenderNum() {
        return amountSecondStageTenderNum;
    }

    public void setAmountSecondStageTenderNum(Integer amountSecondStageTenderNum) {
        this.amountSecondStageTenderNum = amountSecondStageTenderNum;
    }

    public BigDecimal getAmountSecondStageTenderProportion() {
        return amountSecondStageTenderProportion;
    }

    public void setAmountSecondStageTenderProportion(BigDecimal amountSecondStageTenderProportion) {
        this.amountSecondStageTenderProportion = amountSecondStageTenderProportion;
    }

    public Integer getAmountThirdStageTenderNum() {
        return amountThirdStageTenderNum;
    }

    public void setAmountThirdStageTenderNum(Integer amountThirdStageTenderNum) {
        this.amountThirdStageTenderNum = amountThirdStageTenderNum;
    }

    public BigDecimal getAmountThirdStageTenderProportion() {
        return amountThirdStageTenderProportion;
    }

    public void setAmountThirdStageTenderProportion(BigDecimal amountThirdStageTenderProportion) {
        this.amountThirdStageTenderProportion = amountThirdStageTenderProportion;
    }

    public Integer getAmountFourthStageTenderNum() {
        return amountFourthStageTenderNum;
    }

    public void setAmountFourthStageTenderNum(Integer amountFourthStageTenderNum) {
        this.amountFourthStageTenderNum = amountFourthStageTenderNum;
    }

    public BigDecimal getAmountFourthStageTenderProportion() {
        return amountFourthStageTenderProportion;
    }

    public void setAmountFourthStageTenderProportion(BigDecimal amountFourthStageTenderProportion) {
        this.amountFourthStageTenderProportion = amountFourthStageTenderProportion;
    }

    public Integer getAmountFirveStageTenderNum() {
        return amountFirveStageTenderNum;
    }

    public void setAmountFirveStageTenderNum(Integer amountFirveStageTenderNum) {
        this.amountFirveStageTenderNum = amountFirveStageTenderNum;
    }

    public BigDecimal getAmountFirveStageTenderProportion() {
        return amountFirveStageTenderProportion;
    }

    public void setAmountFirveStageTenderProportion(BigDecimal amountFirveStageTenderProportion) {
        this.amountFirveStageTenderProportion = amountFirveStageTenderProportion;
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