package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

/**
 * 债转详情合计bean
 * @author zhangyk
 * @date 2018/7/10 17:12
 */
public class BorrowCreditInfoSumVO extends BaseVO {


    /*转让本金总计*/
    private String sumAssignCapital;

    /*转让价格总计*/
    private String sumAssignCapitalPrice;

    /*认购金额总计*/
    private String sumAssignPrice;

    /*垫付利息总计*/
    private String sumAssignInterestAdvance;

    /*服务费总计*/
    private String sumCreditFee;

    /*支付金额*/
    private String sumAssignPay;


    public String getSumAssignCapital() {
        return sumAssignCapital;
    }

    public void setSumAssignCapital(String sumAssignCapital) {
        this.sumAssignCapital = sumAssignCapital;
    }

    public String getSumAssignCapitalPrice() {
        return sumAssignCapitalPrice;
    }

    public void setSumAssignCapitalPrice(String sumAssignCapitalPrice) {
        this.sumAssignCapitalPrice = sumAssignCapitalPrice;
    }

    public String getSumAssignPrice() {
        return sumAssignPrice;
    }

    public void setSumAssignPrice(String sumAssignPrice) {
        this.sumAssignPrice = sumAssignPrice;
    }

    public String getSumAssignInterestAdvance() {
        return sumAssignInterestAdvance;
    }

    public void setSumAssignInterestAdvance(String sumAssignInterestAdvance) {
        this.sumAssignInterestAdvance = sumAssignInterestAdvance;
    }

    public String getSumCreditFee() {
        return sumCreditFee;
    }

    public void setSumCreditFee(String sumCreditFee) {
        this.sumCreditFee = sumCreditFee;
    }

    public String getSumAssignPay() {
        return sumAssignPay;
    }

    public void setSumAssignPay(String sumAssignPay) {
        this.sumAssignPay = sumAssignPay;
    }
}
