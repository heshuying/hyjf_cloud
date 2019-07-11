package com.hyjf.pay.lib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class URLSystemConfig {

    // 江西银行接口调用地址
    @Value("${hyjf.pay.bank.url}")
    public String bankUrl;

    // 汇付接口调用地址
    @Value("${hyjf.pay.url}")
    public String payUrl;

    // 法大大接口调用地址hyjf.pay.url
    @Value("${hyjf.pay.fdd.url}")
    public String fddPayUrl;
    // 安融接口调用地址hyjf.pay.anrong.url
    @Value("${hyjf.pay.anrong.url}")
    public String anrongUrl;

//    @Value("${hyjf.chinapnr.return.url}")
//    public  String chinapnrReturnUril;
//
//    @Value("${hyjf.chinapnr.bindreturn.url}")
//    public  String chinapnrBindreturnUrl;
//
//    @Value("${hyjf.chinapnr.callback.url}")
//    public  String chinapnrCallBack;


    public String getAnrongUrl() {
		return anrongUrl;
	}

	public void setAnrongUrl(String anrongUrl) {
		this.anrongUrl = anrongUrl;
	}

	public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getFddPayUrl() {
        return fddPayUrl;
    }

    public void setFddPayUrl(String fddPayUrl) {
        this.fddPayUrl = fddPayUrl;
    }
}
