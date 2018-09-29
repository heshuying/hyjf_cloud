/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.datacollect;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @author fuqiang
 * @version OperationReportEntityVO, v0.1 2018/7/18 11:34
 */
public class OperationReportEntityVO extends BaseVO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String id;
    // 数据统计的月份，用于检索
    private int statisticsMonth;
    // 插入数据的时间
    private int insertDate;

    // 每月交易总额
    private BigDecimal accountMonth;
    // 每月交易笔数
    private int tradeCountMonth;
    //累计交易笔数
    private int tradeCount;

    // 投资人总数
    private int tenderCount;
    // 累计投资总额
    private BigDecimal totalCount;
    //累计投资收益
    private BigDecimal totalInterest;
    //借贷笔数
    private int loanNum;

    //人均投资金额
    private int perInvest;

    // 当月满标时间
    private float fullBillTimeCurrentMonth;
    // 待偿还金额
    private BigDecimal willPayMoney;
    // 累计借款人
    private int borrowuserCountTotal;
    // 前十大借款人待还金额占比
    private BigDecimal borrowuserMoneyTopten;
    // 当前借款人
    private int borrowuserCountCurrent;
    // 最大单一借款人待还金额占比
    private BigDecimal borrowuserMoneyTopone;
    // 当前投资人
    private int tenderuserCountCurrent;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatisticsMonth() {
        return statisticsMonth;
    }

    public void setStatisticsMonth(int statisticsMonth) {
        this.statisticsMonth = statisticsMonth;
    }

    public int getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(int insertDate) {
        this.insertDate = insertDate;
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

    public int getTenderCount() {
        return tenderCount;
    }

    public void setTenderCount(int tenderCount) {
        this.tenderCount = tenderCount;
    }

    public BigDecimal getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigDecimal totalCount) {
        this.totalCount = totalCount;
    }

    public float getFullBillTimeCurrentMonth() {
        return fullBillTimeCurrentMonth;
    }

    public void setFullBillTimeCurrentMonth(float fullBillTimeCurrentMonth) {
        this.fullBillTimeCurrentMonth = fullBillTimeCurrentMonth;
    }

    public BigDecimal getWillPayMoney() {
        return willPayMoney;
    }

    public void setWillPayMoney(BigDecimal willPayMoney) {
        this.willPayMoney = willPayMoney;
    }

    public int getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(int tradeCount) {
        this.tradeCount = tradeCount;
    }

    public BigDecimal getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(BigDecimal totalInterest) {
        this.totalInterest = totalInterest;
    }

    public int getLoanNum() {
        return loanNum;
    }

    public void setLoanNum(int loanNum) {
        this.loanNum = loanNum;
    }

    public int getPerInvest() {
        return perInvest;
    }

    public void setPerInvest(int perInvest) {
        this.perInvest = perInvest;
    }

    public int getBorrowuserCountTotal() {
        return borrowuserCountTotal;
    }

    public void setBorrowuserCountTotal(int borrowuserCountTotal) {
        this.borrowuserCountTotal = borrowuserCountTotal;
    }

    public BigDecimal getBorrowuserMoneyTopten() {
        return borrowuserMoneyTopten;
    }

    public void setBorrowuserMoneyTopten(BigDecimal borrowuserMoneyTopten) {
        this.borrowuserMoneyTopten = borrowuserMoneyTopten;
    }

    public int getBorrowuserCountCurrent() {
        return borrowuserCountCurrent;
    }

    public void setBorrowuserCountCurrent(int borrowuserCountCurrent) {
        this.borrowuserCountCurrent = borrowuserCountCurrent;
    }

    public BigDecimal getBorrowuserMoneyTopone() {
        return borrowuserMoneyTopone;
    }

    public void setBorrowuserMoneyTopone(BigDecimal borrowuserMoneyTopone) {
        this.borrowuserMoneyTopone = borrowuserMoneyTopone;
    }

    public int getTenderuserCountCurrent() {
        return tenderuserCountCurrent;
    }

    public void setTenderuserCountCurrent(int tenderuserCountCurrent) {
        this.tenderuserCountCurrent = tenderuserCountCurrent;
    }

    public int getHour(float f){
        return (int)Math.floor(f/60);
    }
    public int getMinutes(float f){
        return (int)Math.floor(f%60);
    }
    public int getSeconds(float f){
        String str=String.valueOf(f);
        int idx = str.lastIndexOf(".");

        int dd=Integer.valueOf(str.substring(idx+1,idx+2));
        return dd*6;

    }

    public String format(String input){
        return input.substring(0,4)+"."+input.substring(4,6);
    }
    public String formatnew(String input){
        return "'"+input.substring(0,4)+"-"+input.substring(4,6)+"'";
    }

    @Override
    public String toString() {
        return "OperationReportEntity{" +
                "id='" + id + '\'' +
                ", statisticsMonth=" + statisticsMonth +
                ", insertDate=" + insertDate +
                ", accountMonth=" + accountMonth +
                ", tradeCountMonth=" + tradeCountMonth +
                ", tradeCount=" + tradeCount +
                ", tenderCount=" + tenderCount +
                ", totalCount=" + totalCount +
                ", totalInterest=" + totalInterest +
                ", loanNum=" + loanNum +
                ", perInvest=" + perInvest +
                ", fullBillTimeCurrentMonth=" + fullBillTimeCurrentMonth +
                ", willPayMoney=" + willPayMoney +
                ", borrowuserCountTotal=" + borrowuserCountTotal +
                ", borrowuserMoneyTopten=" + borrowuserMoneyTopten +
                ", borrowuserCountCurrent=" + borrowuserCountCurrent +
                ", borrowuserMoneyTopone=" + borrowuserMoneyTopone +
                ", tenderuserCountCurrent=" + tenderuserCountCurrent +
                '}';
    }
}
