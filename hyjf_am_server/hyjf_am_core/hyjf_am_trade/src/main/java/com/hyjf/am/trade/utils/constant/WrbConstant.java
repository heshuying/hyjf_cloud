package com.hyjf.am.trade.utils.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WrbConstant {
    /** 风车理财提供的密钥字符串 */
    public static String KEY;
    /** 汇盈金服使用的加解密的key， 主要用来屏蔽敏感字段，比如userId */
    public static String PF_KEY;

    @Value("${hyjf.wrb.key.test}")
    private String wrbKeyTest;

    @Value("${hyjf.wrb.key.pro}")
    private String wrbKeyPro;

    @Value("${hyjf.env.test}")
    public void setKEY(boolean hyjfEnvTest) {
        if (hyjfEnvTest) {
//            WrbConstant.KEY = "uaIH/eUCWHMxOxUyeUWirnzjf+kEEG1/";
            WrbConstant.KEY = wrbKeyTest;
        } else {
//            WrbConstant.KEY = "ByAsy7z4UV61V83a9JR2aFcWI3/WKZez";
            WrbConstant.KEY = wrbKeyPro;
        }
    }

    @Value("${hyjf.env.test}")
    public void setPfKey(boolean hyjfEnvTest) {
        if (hyjfEnvTest) {
//            WrbConstant.PF_KEY = "uaIH/eUCWHMxOxUyeUWirnzjf+kEEG1/";
            WrbConstant.PF_KEY = wrbKeyTest;
        } else {
//            WrbConstant.PF_KEY = "ByAsy7z4UV61V83a9JR2aFcWI3/WKZez";
            WrbConstant.PF_KEY = wrbKeyPro;
        }
    }
}
