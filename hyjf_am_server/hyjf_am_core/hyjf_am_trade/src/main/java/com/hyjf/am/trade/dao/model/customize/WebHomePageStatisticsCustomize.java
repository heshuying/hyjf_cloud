/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

/**
 * @author yaoyong
 * @version WebHomePageStatisticsCustomize, v0.1 2018/9/20 10:40
 */
public class WebHomePageStatisticsCustomize {

    //系统累计投资金额
    public String totalSum;
    //风险保证金
    public String bailTotal;
    //累计创造收益
    public String totalInterest;

    public WebHomePageStatisticsCustomize() {
        super();
    }

    public String getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum = totalSum;
    }

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getBailTotal() {
        return bailTotal;
    }

    public void setBailTotal(String bailTotal) {
        this.bailTotal = bailTotal;
    }
}
