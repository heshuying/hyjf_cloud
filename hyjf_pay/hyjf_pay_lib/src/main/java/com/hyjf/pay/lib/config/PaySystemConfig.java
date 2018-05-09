package com.hyjf.pay.lib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaySystemConfig {

    @Value("${hyjf.pay.bank.url}")
    public String bankUrl;
    
    

//	/** 银行代码 */
//	public static final String BANK_BANKCODE = "hyjf.bank.bankcode";
//	/** 交易渠道 */
//	public static final String BANK_COINST_CHANNEL = "hyjf.bank.coinst.channel";
//	/** 接口默认版本号 */
//	public static final String BANK_VERSION = "hyjf.bank.version";
//	/** 平台机构代码 */
//	public static final String BANK_INSTCODE = "hyjf.bank.instcode";

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }


}
