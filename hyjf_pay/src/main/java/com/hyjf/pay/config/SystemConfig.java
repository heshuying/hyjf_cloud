package com.hyjf.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
public class SystemConfig {
    @Value("${hyjf.bank.page.url}")
    public String pageUrl;
    @Value("${hyjf.bank.page.return.url}")
    public String returnUrl;
    @Value("${hyjf.bank.page.callback.url}")
    public String callbackUrl;
    @Value("${hyjf.bank.page.forgotpwd.url}")
    public String forgotpwdUrl;

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getForgotpwdUrl() {
        return forgotpwdUrl;
    }

    public void setForgotpwdUrl(String forgotpwdUrl) {
        this.forgotpwdUrl = forgotpwdUrl;
    }
}
