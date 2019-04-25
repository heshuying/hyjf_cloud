package com.hyjf.am.vo.app.recharge;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/4/24
 */
public class AppRechargeLimitVO implements Serializable {

    private static final long serialVersionUID = -2323359532266579146L;
    // 银行名称
    private String bankName;
    // 银行logo
    private String bankIcon;
    // 每笔限额
    private String single;
    // 每日限额
    private String daily;
    // 每月限额
    private String monthly;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankIcon() {
        return bankIcon;
    }

    public void setBankIcon(String bankIcon) {
        this.bankIcon = bankIcon;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }
}
