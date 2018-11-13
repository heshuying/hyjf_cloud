package com.hyjf.am.trade.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author fuqiang
 * @version WrbConstant, v0.1 2018/11/13 16:25
 */
@Component
public class WrbConstant {
    /** 风车理财提供的密钥字符串 */
    public static String KEY;
    /** 汇盈金服使用的加解密的key， 主要用来屏蔽敏感字段，比如userId */
    public static String PF_KEY;

    @Value("${hyjf.env.test}")
    public void setKEY(boolean hyjfEnvTest) {
        if (hyjfEnvTest) {
            WrbConstant.KEY = "uaIH/eUCWHMxOxUyeUWirnzjf+kEEG1/";
        } else {
            WrbConstant.KEY = "ByAsy7z4UV61V83a9JR2aFcWI3/WKZez";
        }
    }

    @Value("${hyjf.env.test}")
    public void setPfKey(boolean hyjfEnvTest) {
        if (hyjfEnvTest) {
            WrbConstant.PF_KEY = "uaIH/eUCWHMxOxUyeUWirnzjf+kEEG1/";
        } else {
            WrbConstant.PF_KEY = "ByAsy7z4UV61V83a9JR2aFcWI3/WKZez";
        }
    }
}
