package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.borrow.AccountRechargeVO;
import com.hyjf.am.vo.user.BankCallVO;

public class BankAccountBeanRequest {

    AccountRechargeVO accountRecharge;
    BankCallVO bean;
    String ip;

    public AccountRechargeVO getAccountRecharge() {
        return accountRecharge;
    }

    public void setAccountRecharge(AccountRechargeVO accountRecharge) {
        this.accountRecharge = accountRecharge;
    }

    public BankCallVO getBean() {
        return bean;
    }

    public void setBean(BankCallVO bean) {
        this.bean = bean;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
