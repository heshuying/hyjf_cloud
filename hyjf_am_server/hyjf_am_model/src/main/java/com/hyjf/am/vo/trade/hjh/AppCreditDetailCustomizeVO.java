
package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author Administrator
 */

public class AppCreditDetailCustomizeVO extends BaseVO {

    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    /**
     * 债转编号
     */
    private Integer creditNid;

    /**
     * 画面表示的债转编号:HZR+债转编号
     */
    private String creditNidPage;

    /**
     * 投资期限
     */
    private String borrowPeriod;
    
    /**
     * 投资期限
     */
    private String creditTerm;
    
    /**
     * 债权已 持有天数
     */
    private String creditTermHold;
    

    /**
     * 债转本金
     */
    private String creditCapital;

    /**
     * 项目剩余
     */
    private String investAccount;

    /**
     * 转让状态:转让状态，0.进行中，1.停止
     */
    private String status;

    /**
     * 折价比率
     */
    private String creditDiscount;

    /**
     * 还款方式
     */
    private String repayStyle;

    /**
     * 原项目
     */
    private String bidNid;

    /**
     * 原标年化利率
     */
    private String bidApr;

    /**
     * 转让时间
     */
    private String creditTime;

    /**
     * 截止时间
     */
    private String endTime;

    /**
     * 成功时间
     */
    private String assignTime;

    /**
     * 投资进度
     */
    private String borrowSchedule;

    public Integer getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(Integer creditNid) {
        this.creditNid = creditNid;
    }

    public String getCreditNidPage() {
        return creditNidPage;
    }

    public void setCreditNidPage(String creditNidPage) {
        this.creditNidPage = creditNidPage;
    }

    public String getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(String creditCapital) {
        this.creditCapital = creditCapital;
    }

    public String getInvestAccount() {
        return investAccount;
    }

    public void setInvestAccount(String investAccount) {
        this.investAccount = investAccount;
    }

    public String getCreditDiscount() {
        return creditDiscount;
    }

    public void setCreditDiscount(String creditDiscount) {
        this.creditDiscount = creditDiscount;
    }

    public String getRepayStyle() {
        return repayStyle;
    }

    public void setRepayStyle(String repayStyle) {
        this.repayStyle = repayStyle;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getBidApr() {
        return bidApr;
    }

    public void setBidApr(String bidApr) {
        this.bidApr = bidApr;
    }

    public String getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(String creditTime) {
        this.creditTime = creditTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBorrowSchedule() {
        return borrowSchedule;
    }

    public void setBorrowSchedule(String borrowSchedule) {
        this.borrowSchedule = borrowSchedule;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(String creditTerm) {
        this.creditTerm = creditTerm;
    }

    public String getCreditTermHold() {
        return creditTermHold;
    }

    public void setCreditTermHold(String creditTermHold) {
        this.creditTermHold = creditTermHold;
    }

    
}
