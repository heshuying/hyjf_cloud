/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.market;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @author yaoyong
 * @version SellDailyVO, v0.1 2018/11/16 14:49
 */
public class SellDailyVO extends BaseVO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 统计时间,类型：yyyy.MM.dd
     */
    private String dateStr;

    private Integer drawOrder;

    private String primaryDivision;

    private String twoDivision;

    private int storeNum;

    private BigDecimal investTotalMonth;

    private BigDecimal repaymentTotalMonth;

    private BigDecimal investTotalPreviousMonth;

    private String investRatioGrowth;

    private BigDecimal withdrawTotalMonth;

    private String withdrawRate;

    private BigDecimal rechargeTotalMonth;

    private BigDecimal investAnnualTotalMonth;

    private BigDecimal investAnnualTotalPreviousMonth;

    private String investAnnularRatioGrowth;

    private BigDecimal investTotalYesterday;

    private BigDecimal repaymentTotalYesterday;

    private BigDecimal investAnnualTotalYesterday;

    private BigDecimal withdrawTotalYesterday;

    private BigDecimal rechargeTotalYesterday;

    private BigDecimal netCapitalInflowYesterday;

    private BigDecimal nonRepaymentToday;

    private int registerTotalYesterday;

    private int rechargeGt3000UserNum;

    private int investGt3000UserNum;

    private int investGt3000MonthUserNum;

    private int createTime;

    private int updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Integer getDrawOrder() {
        return drawOrder;
    }

    public void setDrawOrder(Integer drawOrder) {
        this.drawOrder = drawOrder;
    }

    public String getPrimaryDivision() {
        return primaryDivision;
    }

    public void setPrimaryDivision(String primaryDivision) {
        this.primaryDivision = primaryDivision;
    }

    public String getTwoDivision() {
        return twoDivision;
    }

    public void setTwoDivision(String twoDivision) {
        this.twoDivision = twoDivision;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public BigDecimal getInvestTotalMonth() {
        return investTotalMonth;
    }

    public void setInvestTotalMonth(BigDecimal investTotalMonth) {
        this.investTotalMonth = investTotalMonth;
    }

    public BigDecimal getRepaymentTotalMonth() {
        return repaymentTotalMonth;
    }

    public void setRepaymentTotalMonth(BigDecimal repaymentTotalMonth) {
        this.repaymentTotalMonth = repaymentTotalMonth;
    }

    public BigDecimal getInvestTotalPreviousMonth() {
        return investTotalPreviousMonth;
    }

    public void setInvestTotalPreviousMonth(BigDecimal investTotalPreviousMonth) {
        this.investTotalPreviousMonth = investTotalPreviousMonth;
    }

    public String getInvestRatioGrowth() {
        return investRatioGrowth;
    }

    public void setInvestRatioGrowth(String investRatioGrowth) {
        this.investRatioGrowth = investRatioGrowth;
    }

    public BigDecimal getWithdrawTotalMonth() {
        return withdrawTotalMonth;
    }

    public void setWithdrawTotalMonth(BigDecimal withdrawTotalMonth) {
        this.withdrawTotalMonth = withdrawTotalMonth;
    }

    public String getWithdrawRate() {
        return withdrawRate;
    }

    public void setWithdrawRate(String withdrawRate) {
        this.withdrawRate = withdrawRate;
    }

    public BigDecimal getRechargeTotalMonth() {
        return rechargeTotalMonth;
    }

    public void setRechargeTotalMonth(BigDecimal rechargeTotalMonth) {
        this.rechargeTotalMonth = rechargeTotalMonth;
    }

    public BigDecimal getInvestAnnualTotalMonth() {
        return investAnnualTotalMonth;
    }

    public void setInvestAnnualTotalMonth(BigDecimal investAnnualTotalMonth) {
        this.investAnnualTotalMonth = investAnnualTotalMonth;
    }

    public BigDecimal getInvestAnnualTotalPreviousMonth() {
        return investAnnualTotalPreviousMonth;
    }

    public void setInvestAnnualTotalPreviousMonth(BigDecimal investAnnualTotalPreviousMonth) {
        this.investAnnualTotalPreviousMonth = investAnnualTotalPreviousMonth;
    }

    public String getInvestAnnularRatioGrowth() {
        return investAnnularRatioGrowth;
    }

    public void setInvestAnnularRatioGrowth(String investAnnularRatioGrowth) {
        this.investAnnularRatioGrowth = investAnnularRatioGrowth;
    }

    public BigDecimal getInvestTotalYesterday() {
        return investTotalYesterday;
    }

    public void setInvestTotalYesterday(BigDecimal investTotalYesterday) {
        this.investTotalYesterday = investTotalYesterday;
    }

    public BigDecimal getRepaymentTotalYesterday() {
        return repaymentTotalYesterday;
    }

    public void setRepaymentTotalYesterday(BigDecimal repaymentTotalYesterday) {
        this.repaymentTotalYesterday = repaymentTotalYesterday;
    }

    public BigDecimal getInvestAnnualTotalYesterday() {
        return investAnnualTotalYesterday;
    }

    public void setInvestAnnualTotalYesterday(BigDecimal investAnnualTotalYesterday) {
        this.investAnnualTotalYesterday = investAnnualTotalYesterday;
    }

    public BigDecimal getWithdrawTotalYesterday() {
        return withdrawTotalYesterday;
    }

    public void setWithdrawTotalYesterday(BigDecimal withdrawTotalYesterday) {
        this.withdrawTotalYesterday = withdrawTotalYesterday;
    }

    public BigDecimal getRechargeTotalYesterday() {
        return rechargeTotalYesterday;
    }

    public void setRechargeTotalYesterday(BigDecimal rechargeTotalYesterday) {
        this.rechargeTotalYesterday = rechargeTotalYesterday;
    }

    public BigDecimal getNetCapitalInflowYesterday() {
        return netCapitalInflowYesterday;
    }

    public void setNetCapitalInflowYesterday(BigDecimal netCapitalInflowYesterday) {
        this.netCapitalInflowYesterday = netCapitalInflowYesterday;
    }

    public BigDecimal getNonRepaymentToday() {
        return nonRepaymentToday;
    }

    public void setNonRepaymentToday(BigDecimal nonRepaymentToday) {
        this.nonRepaymentToday = nonRepaymentToday;
    }

    public int getRegisterTotalYesterday() {
        return registerTotalYesterday;
    }

    public void setRegisterTotalYesterday(int registerTotalYesterday) {
        this.registerTotalYesterday = registerTotalYesterday;
    }

    public int getRechargeGt3000UserNum() {
        return rechargeGt3000UserNum;
    }

    public void setRechargeGt3000UserNum(int rechargeGt3000UserNum) {
        this.rechargeGt3000UserNum = rechargeGt3000UserNum;
    }

    public int getInvestGt3000UserNum() {
        return investGt3000UserNum;
    }

    public void setInvestGt3000UserNum(int investGt3000UserNum) {
        this.investGt3000UserNum = investGt3000UserNum;
    }

    public int getInvestGt3000MonthUserNum() {
        return investGt3000MonthUserNum;
    }

    public void setInvestGt3000MonthUserNum(int investGt3000MonthUserNum) {
        this.investGt3000MonthUserNum = investGt3000MonthUserNum;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }
}
