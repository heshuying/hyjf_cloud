package com.hyjf.am.vo.message;

import com.hyjf.am.vo.trade.OperationReportJobVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: walter.tyy
 * @Date: 2018/11/16 16:36
 * @Description: OperationReportJobBean
 */
public class OperationReportJobBean implements Serializable {
    //投资人按照地域分布
    List<OperationReportJobVO> cityGroup;
    // 投资人按照性别分布
    List<OperationReportJobVO> sexGroup;
    // 投资人按照年龄分布
    List<OperationReportJobVO> ageRangeUserIds;
    // 每月交易总额
    private BigDecimal accountMonth;

    // 每月交易笔数
    private int tradeCountMonth;

    //借贷笔数
    private int loanNum;

    //获取截至日期的投资金额
    private double investLastDate;

    // 投资人总数
    private int tenderCount;

    // 待偿还金额
    private BigDecimal willPayMoney;

    // 当月满标时间
    private float fullBillTimeCurrentMonth;
    // 业绩总览
    private   List<OperationReportJobVO> listPerformanceSum;
    // 全年度12个月成交金额
    private List<OperationReportJobVO> listMonthDealMoney;
    //全年赚取收益,去年全年赚取收益,全年平均年率
    private List<OperationReportJobVO> listOperationReportInfoCustomize;
    //充值金额、充值笔数
    private List<OperationReportJobVO> listRechargeMoneyAndSum;
    //渠道分析
    private List<OperationReportJobVO> listCompleteCount;
    //性别分布
    private List<OperationReportJobVO> listSexDistribute;
    //年龄分布
    private List<OperationReportJobVO> listAgeDistribute;
    //金额分布
    private List<OperationReportJobVO> listMoneyDistribute;
    //十大投资人投资金额
    private List<OperationReportJobVO> listTenMostMoney;
    //地区
    private String area;
    //大赢家，收益最高
    private List<OperationReportJobVO> listOneInterestsMost;
    //超活跃，投资笔数最多
    private List<OperationReportJobVO> listtOneInvestMost;

    public List<OperationReportJobVO> getCityGroup() {
        return cityGroup;
    }

    public void setCityGroup(List<OperationReportJobVO> cityGroup) {
        this.cityGroup = cityGroup;
    }

    public List<OperationReportJobVO> getSexGroup() {
        return sexGroup;
    }

    public void setSexGroup(List<OperationReportJobVO> sexGroup) {
        this.sexGroup = sexGroup;
    }

    public List<OperationReportJobVO> getAgeRangeUserIds() {
        return ageRangeUserIds;
    }

    public void setAgeRangeUserIds(List<OperationReportJobVO> ageRangeUserIds) {
        this.ageRangeUserIds = ageRangeUserIds;
    }

    public BigDecimal getAccountMonth() {
        return accountMonth;
    }

    public void setAccountMonth(BigDecimal accountMonth) {
        this.accountMonth = accountMonth;
    }

    public int getTradeCountMonth() {
        return tradeCountMonth;
    }

    public void setTradeCountMonth(int tradeCountMonth) {
        this.tradeCountMonth = tradeCountMonth;
    }

    public int getLoanNum() {
        return loanNum;
    }

    public void setLoanNum(int loanNum) {
        this.loanNum = loanNum;
    }

    public double getInvestLastDate() {
        return investLastDate;
    }

    public void setInvestLastDate(double investLastDate) {
        this.investLastDate = investLastDate;
    }

    public int getTenderCount() {
        return tenderCount;
    }

    public void setTenderCount(int tenderCount) {
        this.tenderCount = tenderCount;
    }

    public BigDecimal getWillPayMoney() {
        return willPayMoney;
    }

    public void setWillPayMoney(BigDecimal willPayMoney) {
        this.willPayMoney = willPayMoney;
    }

    public float getFullBillTimeCurrentMonth() {
        return fullBillTimeCurrentMonth;
    }

    public void setFullBillTimeCurrentMonth(float fullBillTimeCurrentMonth) {
        this.fullBillTimeCurrentMonth = fullBillTimeCurrentMonth;
    }

    public List<OperationReportJobVO> getListPerformanceSum() {
        return listPerformanceSum;
    }

    public void setListPerformanceSum(List<OperationReportJobVO> listPerformanceSum) {
        this.listPerformanceSum = listPerformanceSum;
    }

    public List<OperationReportJobVO> getListMonthDealMoney() {
        return listMonthDealMoney;
    }

    public void setListMonthDealMoney(List<OperationReportJobVO> listMonthDealMoney) {
        this.listMonthDealMoney = listMonthDealMoney;
    }

    public List<OperationReportJobVO> getListOperationReportInfoCustomize() {
        return listOperationReportInfoCustomize;
    }

    public void setListOperationReportInfoCustomize(List<OperationReportJobVO> listOperationReportInfoCustomize) {
        this.listOperationReportInfoCustomize = listOperationReportInfoCustomize;
    }

    public List<OperationReportJobVO> getListRechargeMoneyAndSum() {
        return listRechargeMoneyAndSum;
    }

    public void setListRechargeMoneyAndSum(List<OperationReportJobVO> listRechargeMoneyAndSum) {
        this.listRechargeMoneyAndSum = listRechargeMoneyAndSum;
    }

    public List<OperationReportJobVO> getListCompleteCount() {
        return listCompleteCount;
    }

    public void setListCompleteCount(List<OperationReportJobVO> listCompleteCount) {
        this.listCompleteCount = listCompleteCount;
    }

    public List<OperationReportJobVO> getListSexDistribute() {
        return listSexDistribute;
    }

    public void setListSexDistribute(List<OperationReportJobVO> listSexDistribute) {
        this.listSexDistribute = listSexDistribute;
    }

    public List<OperationReportJobVO> getListAgeDistribute() {
        return listAgeDistribute;
    }

    public void setListAgeDistribute(List<OperationReportJobVO> listAgeDistribute) {
        this.listAgeDistribute = listAgeDistribute;
    }

    public List<OperationReportJobVO> getListMoneyDistribute() {
        return listMoneyDistribute;
    }

    public void setListMoneyDistribute(List<OperationReportJobVO> listMoneyDistribute) {
        this.listMoneyDistribute = listMoneyDistribute;
    }

    public List<OperationReportJobVO> getListTenMostMoney() {
        return listTenMostMoney;
    }

    public void setListTenMostMoney(List<OperationReportJobVO> listTenMostMoney) {
        this.listTenMostMoney = listTenMostMoney;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<OperationReportJobVO> getListOneInterestsMost() {
        return listOneInterestsMost;
    }

    public void setListOneInterestsMost(List<OperationReportJobVO> listOneInterestsMost) {
        this.listOneInterestsMost = listOneInterestsMost;
    }

    public List<OperationReportJobVO> getListtOneInvestMost() {
        return listtOneInvestMost;
    }

    public void setListtOneInvestMost(List<OperationReportJobVO> listtOneInvestMost) {
        this.listtOneInvestMost = listtOneInvestMost;
    }
}
