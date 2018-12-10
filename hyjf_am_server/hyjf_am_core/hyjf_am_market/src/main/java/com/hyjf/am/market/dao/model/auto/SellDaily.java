package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SellDaily implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 统计时间 yyyy.MM.dd
     *
     * @mbggenerated
     */
    private String dateStr;

    /**
     * 绘制顺序
     *
     * @mbggenerated
     */
    private Integer drawOrder;

    /**
     * 一级部门
     *
     * @mbggenerated
     */
    private String primaryDivision;

    /**
     * 二级部门
     *
     * @mbggenerated
     */
    private String twoDivision;

    /**
     * 门店数量
     *
     * @mbggenerated
     */
    private Integer storeNum;

    /**
     * 本月累计规模业绩
     *
     * @mbggenerated
     */
    private BigDecimal investTotalMonth;

    /**
     * 本月累计已还款
     *
     * @mbggenerated
     */
    private BigDecimal repaymentTotalMonth;

    /**
     * 上月对应累计规模业绩
     *
     * @mbggenerated
     */
    private BigDecimal investTotalPreviousMonth;

    /**
     * 环比增速（本月累计规模业绩/上月对应累计规模业绩 - 1）
     *
     * @mbggenerated
     */
    private String investRatioGrowth;

    /**
     * 本月累计提现
     *
     * @mbggenerated
     */
    private BigDecimal withdrawTotalMonth;

    /**
     * 提现占比（本月累计提现/本月累计已还款）
     *
     * @mbggenerated
     */
    private String withdrawRate;

    /**
     * 本月累计充值
     *
     * @mbggenerated
     */
    private BigDecimal rechargeTotalMonth;

    /**
     * 本月累计年化业绩
     *
     * @mbggenerated
     */
    private BigDecimal investAnnualTotalMonth;

    /**
     * 上月累计年化业绩
     *
     * @mbggenerated
     */
    private BigDecimal investAnnualTotalPreviousMonth;

    /**
     * 环比增速（本月累计年化业绩/上月累计年化业绩-1）
     *
     * @mbggenerated
     */
    private String investAnnularRatioGrowth;

    /**
     * 昨日规模业绩
     *
     * @mbggenerated
     */
    private BigDecimal investTotalYesterday;

    /**
     * 昨日还款
     *
     * @mbggenerated
     */
    private BigDecimal repaymentTotalYesterday;

    /**
     * 昨日年化业绩
     *
     * @mbggenerated
     */
    private BigDecimal investAnnualTotalYesterday;

    /**
     * 昨日提现
     *
     * @mbggenerated
     */
    private BigDecimal withdrawTotalYesterday;

    /**
     * 昨日充值
     *
     * @mbggenerated
     */
    private BigDecimal rechargeTotalYesterday;

    /**
     * 昨日净资金流（充值-提现）
     *
     * @mbggenerated
     */
    private BigDecimal netCapitalInflowYesterday;

    /**
     * 当日待还
     *
     * @mbggenerated
     */
    private BigDecimal nonRepaymentToday;

    /**
     * 昨日注册数
     *
     * @mbggenerated
     */
    private Integer registerTotalYesterday;

    /**
     * 充值≥3000人数（昨日注册）
     *
     * @mbggenerated
     */
    private Integer rechargeGt3000UserNum;

    /**
     * 出借≥3000人数（昨日注册）
     *
     * @mbggenerated
     */
    private Integer investGt3000UserNum;

    /**
     * 本月累计出借3000以上新客户数
     *
     * @mbggenerated
     */
    private Integer investGt3000MonthUserNum;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr == null ? null : dateStr.trim();
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
        this.primaryDivision = primaryDivision == null ? null : primaryDivision.trim();
    }

    public String getTwoDivision() {
        return twoDivision;
    }

    public void setTwoDivision(String twoDivision) {
        this.twoDivision = twoDivision == null ? null : twoDivision.trim();
    }

    public Integer getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(Integer storeNum) {
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
        this.investRatioGrowth = investRatioGrowth == null ? null : investRatioGrowth.trim();
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
        this.withdrawRate = withdrawRate == null ? null : withdrawRate.trim();
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
        this.investAnnularRatioGrowth = investAnnularRatioGrowth == null ? null : investAnnularRatioGrowth.trim();
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

    public Integer getRegisterTotalYesterday() {
        return registerTotalYesterday;
    }

    public void setRegisterTotalYesterday(Integer registerTotalYesterday) {
        this.registerTotalYesterday = registerTotalYesterday;
    }

    public Integer getRechargeGt3000UserNum() {
        return rechargeGt3000UserNum;
    }

    public void setRechargeGt3000UserNum(Integer rechargeGt3000UserNum) {
        this.rechargeGt3000UserNum = rechargeGt3000UserNum;
    }

    public Integer getInvestGt3000UserNum() {
        return investGt3000UserNum;
    }

    public void setInvestGt3000UserNum(Integer investGt3000UserNum) {
        this.investGt3000UserNum = investGt3000UserNum;
    }

    public Integer getInvestGt3000MonthUserNum() {
        return investGt3000MonthUserNum;
    }

    public void setInvestGt3000MonthUserNum(Integer investGt3000MonthUserNum) {
        this.investGt3000MonthUserNum = investGt3000MonthUserNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}