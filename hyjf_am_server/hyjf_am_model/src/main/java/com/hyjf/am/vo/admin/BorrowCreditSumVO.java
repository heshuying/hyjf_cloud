package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * 债权转让统计数据bean
 * @author zhangyk
 * @date 2018/7/9 17:42
 */
public class BorrowCreditSumVO  extends BaseVO implements Serializable {


    /*债权本金总计*/
    private String sumCreditCapital;

    /*转让本金*/
    private String sumCreditCapitalPrice;

    /*转让价格*/
    private String sumCreditPrice;

    /*已转让金额总计*/
    private String sumCreditCapitalAssigned;


    public String getSumCreditCapital() {
        return sumCreditCapital;
    }

    public void setSumCreditCapital(String sumCreditCapital) {
        this.sumCreditCapital = sumCreditCapital;
    }

    public String getSumCreditCapitalPrice() {
        return sumCreditCapitalPrice;
    }

    public void setSumCreditCapitalPrice(String sumCreditCapitalPrice) {
        this.sumCreditCapitalPrice = sumCreditCapitalPrice;
    }

    public String getSumCreditPrice() {
        return sumCreditPrice;
    }

    public void setSumCreditPrice(String sumCreditPrice) {
        this.sumCreditPrice = sumCreditPrice;
    }

    public String getSumCreditCapitalAssigned() {
        return sumCreditCapitalAssigned;
    }

    public void setSumCreditCapitalAssigned(String sumCreditCapitalAssigned) {
        this.sumCreditCapitalAssigned = sumCreditCapitalAssigned;
    }
}
