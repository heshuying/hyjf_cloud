package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @version AppChannelStatisticsDetailRequest, v0.1 2018/8/22 16:36
 * @Author: Zha Daojian
 */
public class AppChannelStatisticsDetailRequest extends BasePage implements Serializable {

    private Integer sourceId;

    private String sourceName;

    private Integer userId;

    private String userName;

    private Date registerTime;

    private Date openAccountTime;

    private Integer firstInvestTime;

    private BigDecimal investAmount;

    private String investProjectType;

    private String investProjectPeriod;

    private BigDecimal cumulativeInvest;

    private static final long serialVersionUID = 1L;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
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
        this.userName = userName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getOpenAccountTime() {
        return openAccountTime;
    }

    public void setOpenAccountTime(Date openAccountTime) {
        this.openAccountTime = openAccountTime;
    }

    public Integer getFirstInvestTime() {
        return firstInvestTime;
    }

    public void setFirstInvestTime(Integer firstInvestTime) {
        this.firstInvestTime = firstInvestTime;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
    }

    public String getInvestProjectType() {
        return investProjectType;
    }

    public void setInvestProjectType(String investProjectType) {
        this.investProjectType = investProjectType;
    }

    public String getInvestProjectPeriod() {
        return investProjectPeriod;
    }

    public void setInvestProjectPeriod(String investProjectPeriod) {
        this.investProjectPeriod = investProjectPeriod;
    }

    public BigDecimal getCumulativeInvest() {
        return cumulativeInvest;
    }

    public void setCumulativeInvest(BigDecimal cumulativeInvest) {
        this.cumulativeInvest = cumulativeInvest;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
