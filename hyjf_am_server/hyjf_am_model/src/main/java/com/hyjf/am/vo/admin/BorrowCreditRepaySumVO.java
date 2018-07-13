package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * 还款信息统计数据bean
 * @author zhangyk
 * @date 2018/7/11 19:38
 */
public class BorrowCreditRepaySumVO extends BaseVO implements Serializable {


    /*应收本金总计*/
    private String sumAssignCapital;

    /*应收利息总计*/
    private String sumAssignInterest;

    /*应收本息总计*/
    private String sumAssignAccount;

    /*已收本息总计*/
    private String sumAssignRepayAccount;

    /*还款服务费总计*/
    private String sumCreditFee;


    public String getSumAssignCapital() {
        return sumAssignCapital;
    }

    public void setSumAssignCapital(String sumAssignCapital) {
        this.sumAssignCapital = sumAssignCapital;
    }

    public String getSumAssignInterest() {
        return sumAssignInterest;
    }

    public void setSumAssignInterest(String sumAssignInterest) {
        this.sumAssignInterest = sumAssignInterest;
    }

    public String getSumAssignAccount() {
        return sumAssignAccount;
    }

    public void setSumAssignAccount(String sumAssignAccount) {
        this.sumAssignAccount = sumAssignAccount;
    }

    public String getSumAssignRepayAccount() {
        return sumAssignRepayAccount;
    }

    public void setSumAssignRepayAccount(String sumAssignRepayAccount) {
        this.sumAssignRepayAccount = sumAssignRepayAccount;
    }

    public String getSumCreditFee() {
        return sumCreditFee;
    }

    public void setSumCreditFee(String sumCreditFee) {
        this.sumCreditFee = sumCreditFee;
    }
}
