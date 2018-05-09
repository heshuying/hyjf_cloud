package com.hyjf.pay.lib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaySystemConfig {

    @Value("${hyjf.pay.bank.url}")
    public String bankUrl;

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }


}
