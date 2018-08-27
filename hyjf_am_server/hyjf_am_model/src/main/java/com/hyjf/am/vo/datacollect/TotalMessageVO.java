package com.hyjf.am.vo.datacollect;

/**
 * @author lisheng
 * @version TotalMessageVO, v0.1 2018/7/17 11:37
 */

public class TotalMessageVO {
    //投资总额(亿元)
    String tenderSum;
    //收益总额(亿元)
    String interestSum;
    //累计投资人数(万人)
    int totalTenderSum;
    //当前时间
    String date;

    String isLogin;
    public String getTenderSum() {
        return tenderSum;
    }

    public void setTenderSum(String tenderSum) {
        this.tenderSum = tenderSum;
    }

    public String getInterestSum() {
        return interestSum;
    }

    public void setInterestSum(String interestSum) {
        this.interestSum = interestSum;
    }

    public int getTotalTenderSum() {
        return totalTenderSum;
    }

    public void setTotalTenderSum(int totalTenderSum) {
        this.totalTenderSum = totalTenderSum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }
}
