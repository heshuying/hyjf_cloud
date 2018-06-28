package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtPlanInfoStatic implements Serializable {
    private Integer id;

    private BigDecimal waitInvest;

    private BigDecimal waitCredit;

    private Integer yesInvest;

    private Integer yesCredit;

    private BigDecimal waitRepay;

    private BigDecimal yesRepay;

    private BigDecimal planRepayWait;

    private BigDecimal planRepayYes;

    private BigDecimal planAccedeAll;

    private BigDecimal expireFairValue;

    private Integer investPeriodOne;

    private Integer investPeriodTwoFour;

    private Integer investPeriodFiveEight;

    private Integer investPeriodNineTwel;

    private Integer investPeriodTwelTf;

    private Integer investPeriodTf;

    private Integer creditPeriodOne;

    private Integer creditPeriodTwoFour;

    private Integer creditPeriodFiveEight;

    private Integer creditPeriodNineTwel;

    private Integer creditPeriodTwelTf;

    private Integer creditPeriodTf;

    private Date createTime;

    private String dataDate;

    private String dataMonth;

    private String dataHour;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getWaitInvest() {
        return waitInvest;
    }

    public void setWaitInvest(BigDecimal waitInvest) {
        this.waitInvest = waitInvest;
    }

    public BigDecimal getWaitCredit() {
        return waitCredit;
    }

    public void setWaitCredit(BigDecimal waitCredit) {
        this.waitCredit = waitCredit;
    }

    public Integer getYesInvest() {
        return yesInvest;
    }

    public void setYesInvest(Integer yesInvest) {
        this.yesInvest = yesInvest;
    }

    public Integer getYesCredit() {
        return yesCredit;
    }

    public void setYesCredit(Integer yesCredit) {
        this.yesCredit = yesCredit;
    }

    public BigDecimal getWaitRepay() {
        return waitRepay;
    }

    public void setWaitRepay(BigDecimal waitRepay) {
        this.waitRepay = waitRepay;
    }

    public BigDecimal getYesRepay() {
        return yesRepay;
    }

    public void setYesRepay(BigDecimal yesRepay) {
        this.yesRepay = yesRepay;
    }

    public BigDecimal getPlanRepayWait() {
        return planRepayWait;
    }

    public void setPlanRepayWait(BigDecimal planRepayWait) {
        this.planRepayWait = planRepayWait;
    }

    public BigDecimal getPlanRepayYes() {
        return planRepayYes;
    }

    public void setPlanRepayYes(BigDecimal planRepayYes) {
        this.planRepayYes = planRepayYes;
    }

    public BigDecimal getPlanAccedeAll() {
        return planAccedeAll;
    }

    public void setPlanAccedeAll(BigDecimal planAccedeAll) {
        this.planAccedeAll = planAccedeAll;
    }

    public BigDecimal getExpireFairValue() {
        return expireFairValue;
    }

    public void setExpireFairValue(BigDecimal expireFairValue) {
        this.expireFairValue = expireFairValue;
    }

    public Integer getInvestPeriodOne() {
        return investPeriodOne;
    }

    public void setInvestPeriodOne(Integer investPeriodOne) {
        this.investPeriodOne = investPeriodOne;
    }

    public Integer getInvestPeriodTwoFour() {
        return investPeriodTwoFour;
    }

    public void setInvestPeriodTwoFour(Integer investPeriodTwoFour) {
        this.investPeriodTwoFour = investPeriodTwoFour;
    }

    public Integer getInvestPeriodFiveEight() {
        return investPeriodFiveEight;
    }

    public void setInvestPeriodFiveEight(Integer investPeriodFiveEight) {
        this.investPeriodFiveEight = investPeriodFiveEight;
    }

    public Integer getInvestPeriodNineTwel() {
        return investPeriodNineTwel;
    }

    public void setInvestPeriodNineTwel(Integer investPeriodNineTwel) {
        this.investPeriodNineTwel = investPeriodNineTwel;
    }

    public Integer getInvestPeriodTwelTf() {
        return investPeriodTwelTf;
    }

    public void setInvestPeriodTwelTf(Integer investPeriodTwelTf) {
        this.investPeriodTwelTf = investPeriodTwelTf;
    }

    public Integer getInvestPeriodTf() {
        return investPeriodTf;
    }

    public void setInvestPeriodTf(Integer investPeriodTf) {
        this.investPeriodTf = investPeriodTf;
    }

    public Integer getCreditPeriodOne() {
        return creditPeriodOne;
    }

    public void setCreditPeriodOne(Integer creditPeriodOne) {
        this.creditPeriodOne = creditPeriodOne;
    }

    public Integer getCreditPeriodTwoFour() {
        return creditPeriodTwoFour;
    }

    public void setCreditPeriodTwoFour(Integer creditPeriodTwoFour) {
        this.creditPeriodTwoFour = creditPeriodTwoFour;
    }

    public Integer getCreditPeriodFiveEight() {
        return creditPeriodFiveEight;
    }

    public void setCreditPeriodFiveEight(Integer creditPeriodFiveEight) {
        this.creditPeriodFiveEight = creditPeriodFiveEight;
    }

    public Integer getCreditPeriodNineTwel() {
        return creditPeriodNineTwel;
    }

    public void setCreditPeriodNineTwel(Integer creditPeriodNineTwel) {
        this.creditPeriodNineTwel = creditPeriodNineTwel;
    }

    public Integer getCreditPeriodTwelTf() {
        return creditPeriodTwelTf;
    }

    public void setCreditPeriodTwelTf(Integer creditPeriodTwelTf) {
        this.creditPeriodTwelTf = creditPeriodTwelTf;
    }

    public Integer getCreditPeriodTf() {
        return creditPeriodTf;
    }

    public void setCreditPeriodTf(Integer creditPeriodTf) {
        this.creditPeriodTf = creditPeriodTf;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }

    public String getDataMonth() {
        return dataMonth;
    }

    public void setDataMonth(String dataMonth) {
        this.dataMonth = dataMonth == null ? null : dataMonth.trim();
    }

    public String getDataHour() {
        return dataHour;
    }

    public void setDataHour(String dataHour) {
        this.dataHour = dataHour == null ? null : dataHour.trim();
    }
}