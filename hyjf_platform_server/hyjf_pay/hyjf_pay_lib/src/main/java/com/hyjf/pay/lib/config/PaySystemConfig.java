package com.hyjf.pay.lib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaySystemConfig {

    @Value("${hyjf.pay.bank.url}")
    public String bankUrl;

    @Value("${hyjf.bank.instcode}")
    public String bankInstcode;

    @Value("${hyjf.bank.bankcode}")
    public String bankCode;

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }

    public String getBankInstcode() {
        return bankInstcode;
    }

    public void setBankInstcode(String bankInstcode) {
        this.bankInstcode = bankInstcode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
