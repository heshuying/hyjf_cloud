package com.hyjf.am.vo.datacollect;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.util.GetDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiasq
 * @version AppUtmRegVO, v0.1 2018/5/15 17:16
 */
public class AppUtmRegVO extends BaseVO implements Serializable {
    private Long id;

    private Integer sourceId;

    private String sourceName;

    private Integer userId;

    private String userName;

    private Date registerTime;

    private Date openAccountTime;

    private Integer firstInvestTime;

    private String firstInvestTimeT;

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
        this.sourceName = sourceName == null ? null : sourceName.trim();
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
        this.userName = userName == null ? null : userName.trim();
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
        this.investProjectType = investProjectType == null ? null : investProjectType.trim();
    }

    public String getInvestProjectPeriod() {
        return investProjectPeriod;
    }

    public void setInvestProjectPeriod(String investProjectPeriod) {
        this.investProjectPeriod = investProjectPeriod == null ? null : investProjectPeriod.trim();
    }

    public BigDecimal getCumulativeInvest() {
        return cumulativeInvest;
    }

    public void setCumulativeInvest(BigDecimal cumulativeInvest) {
        this.cumulativeInvest = cumulativeInvest;
    }

    public String getFirstInvestTimeT() {
        if(firstInvestTime != null&&firstInvestTime!=0){
            return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(firstInvestTime);
        }
        return null;
    }

    public void setFirstInvestTimeT(String firstInvestTimeT) {
        this.firstInvestTimeT = firstInvestTimeT;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}