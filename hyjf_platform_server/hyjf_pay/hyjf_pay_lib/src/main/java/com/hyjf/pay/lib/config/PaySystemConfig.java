package com.hyjf.pay.lib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaySystemConfig {

    @Value("${hyjf.pay.bank.url}")
    public String bankUrl;

    @Value("${hyjf.pay.url}")
    public String payUrl;

    @Value("${hyjf.chinapnr.return.url}")
    public  String chinapnrReturnUril;

    @Value("${hyjf.chinapnr.bindreturn.url}")
    public  String chinapnrBindreturnUrl;

    @Value("${hyjf.chinapnr.callback.url}")
    public  String chinapnrCallBack;

    public String getChinapnrCallBack() {
        return chinapnrCallBack;
    }

    public void setChinapnrCallBack(String chinapnrCallBack) {
        this.chinapnrCallBack = chinapnrCallBack;
    }

    public String getChinapnrBindreturnUrl() {
        return chinapnrBindreturnUrl;
    }

    public void setChinapnrBindreturnUrl(String chinapnrBindreturnUrl) {
        this.chinapnrBindreturnUrl = chinapnrBindreturnUrl;
    }

    public String getChinapnrReturnUril() {
        return chinapnrReturnUril;
    }

    public void setChinapnrReturnUril(String chinapnrReturnUril) {
        this.chinapnrReturnUril = chinapnrReturnUril;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }
}
