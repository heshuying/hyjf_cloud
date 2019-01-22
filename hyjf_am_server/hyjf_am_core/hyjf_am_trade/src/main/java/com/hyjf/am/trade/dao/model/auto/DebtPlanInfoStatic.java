package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtPlanInfoStatic implements Serializable {
    private Integer id;

    /**
     * 待成交资产-专属资产
     *
     * @mbggenerated
     */
    private BigDecimal waitInvest;

    /**
     * 待成交资产-债权转让
     *
     * @mbggenerated
     */
    private BigDecimal waitCredit;

    /**
     * 已成交资产数量-专属资产
     *
     * @mbggenerated
     */
    private Integer yesInvest;

    /**
     * 已成交资产数量-债权转让
     *
     * @mbggenerated
     */
    private Integer yesCredit;

    /**
     * 持有债权待还总额
     *
     * @mbggenerated
     */
    private BigDecimal waitRepay;

    /**
     * 持有债权已还总额
     *
     * @mbggenerated
     */
    private BigDecimal yesRepay;

    /**
     * 应还总额
     *
     * @mbggenerated
     */
    private BigDecimal planRepayWait;

    /**
     * 已还总额
     *
     * @mbggenerated
     */
    private BigDecimal planRepayYes;

    /**
     * 投资人加入总额
     *
     * @mbggenerated
     */
    private BigDecimal planAccedeAll;

    /**
     * 到期公允价值总额
     *
     * @mbggenerated
     */
    private BigDecimal expireFairValue;

    /**
     * 待成交资产-汇添金专属资产-期限分布-一月
     *
     * @mbggenerated
     */
    private Integer investPeriodOne;

    /**
     * 待成交资产-汇添金专属资产-期限分布-2-4月
     *
     * @mbggenerated
     */
    private Integer investPeriodTwoFour;

    /**
     * 待成交资产-汇添金专属资产-期限分布-5-8月
     *
     * @mbggenerated
     */
    private Integer investPeriodFiveEight;

    /**
     * 待成交资产-汇添金专属资产-期限分布-9-12月
     *
     * @mbggenerated
     */
    private Integer investPeriodNineTwel;

    /**
     * 待成交资产-汇添金专属资产-期限分布-12-24月
     *
     * @mbggenerated
     */
    private Integer investPeriodTwelTf;

    /**
     * 待成交资产-汇添金专属资产-期限分布-24-月
     *
     * @mbggenerated
     */
    private Integer investPeriodTf;

    /**
     * 待成交资产-债权转让-期限分布-一月
     *
     * @mbggenerated
     */
    private Integer creditPeriodOne;

    /**
     * 待成交资产-债权转让-期限分布-2-4月
     *
     * @mbggenerated
     */
    private Integer creditPeriodTwoFour;

    /**
     * 待成交资产-债权转让-期限分布-5-8月
     *
     * @mbggenerated
     */
    private Integer creditPeriodFiveEight;

    /**
     * 待成交资产-债权转让-期限分布-9-12月
     *
     * @mbggenerated
     */
    private Integer creditPeriodNineTwel;

    /**
     * 待成交资产-债权转让-期限分布-12-24月
     *
     * @mbggenerated
     */
    private Integer creditPeriodTwelTf;

    /**
     * 待成交资产-债权转让-期限分布-24-月
     *
     * @mbggenerated
     */
    private Integer creditPeriodTf;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 日期
     *
     * @mbggenerated
     */
    private String dataDate;

    /**
     * 统计数据月份
     *
     * @mbggenerated
     */
    private String dataMonth;

    /**
     * 统计数据小时
     *
     * @mbggenerated
     */
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