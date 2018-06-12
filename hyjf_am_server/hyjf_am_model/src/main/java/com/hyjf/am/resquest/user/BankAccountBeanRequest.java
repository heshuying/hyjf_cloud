package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.trade.AccountRechargeVO;

public class BankAccountBeanRequest {

    AccountRechargeVO accountRecharge;
    String ip;

    public AccountRechargeVO getAccountRecharge() {
        return accountRecharge;
    }

    public void setAccountRecharge(AccountRechargeVO accountRecharge) {
        this.accountRecharge = accountRecharge;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
