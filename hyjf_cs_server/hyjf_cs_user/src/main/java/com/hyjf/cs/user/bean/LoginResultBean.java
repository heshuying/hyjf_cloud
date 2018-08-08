package com.hyjf.cs.user.bean;


import com.hyjf.cs.user.result.BaseResultBean;

/**
 * 登录返回值
 */
public class LoginResultBean extends BaseResultBean {

    private static final long serialVersionUID = -3296145605895692615L;
    
    private String sign;
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
}
