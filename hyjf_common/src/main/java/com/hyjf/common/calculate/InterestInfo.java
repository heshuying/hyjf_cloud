package com.hyjf.common.calculate;

import java.math.BigDecimal;
import java.util.List;

public class InterestInfo {
    /** 本息总额 */
    private BigDecimal repayAccount;
    /** 利息 */
    private BigDecimal repayAccountInterest;
    /** 本金 */
    private BigDecimal repayAccountCapital;
    /** 管理费 */
    private BigDecimal fee;
    /** 还款时间 */
    private Integer repayTime;
    /** 还款日期 */
    private String repayDate;
    /** 期号 */
    private Integer montyNo;
    /** 按月计息 */
    private List<InterestInfo> listMonthly;

    public BigDecimal getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(BigDecimal repayAccount) {
        this.repayAccount = repayAccount;
    }

    public BigDecimal getRepayAccountInterest() {
        return repayAccountInterest;
    }

    public void setRepayAccountInterest(BigDecimal repayAccountInterest) {
        this.repayAccountInterest = repayAccountInterest;
    }

    public BigDecimal getRepayAccountCapital() {
        return repayAccountCapital;
    }

    public void setRepayAccountCapital(BigDecimal repayAccountCapital) {
        this.repayAccountCapital = repayAccountCapital;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Integer repayTime) {
        this.repayTime = repayTime;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public List<InterestInfo> getListMonthly() {
        return listMonthly;
    }

    public void setListMonthly(List<InterestInfo> listMonthly) {
        this.listMonthly = listMonthly;
    }

    public Integer getMontyNo() {
        return montyNo;
    }

    public void setMontyNo(Integer montyNo) {
        this.montyNo = montyNo;
    }

}
