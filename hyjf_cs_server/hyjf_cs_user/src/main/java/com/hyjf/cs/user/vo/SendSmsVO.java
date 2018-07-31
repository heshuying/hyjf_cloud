/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.vo;

import java.io.Serializable;

/**
 * 发送短信验证码vo
 * @author fp
 * @version SendSmsVo, v0.1 2018/3/23 14:13
 */
public class SendSmsVO implements Serializable {
    private static final long serialVersionUID = -1888109166151152404L;
    // 唯一标识
    private String sign;
    // 手机号
    private String mobile;

    //短信验证码
    private String smscode;

    private String platform;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSmscode() {
        return smscode;
    }
    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
