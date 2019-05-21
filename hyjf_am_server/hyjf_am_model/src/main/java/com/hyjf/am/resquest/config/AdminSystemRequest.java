package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.config.AdminSystemVO;

/**
 * @author dongzeshan
 * @version AccountListResponse, v0.1 2018/6/20 10:52
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
public class AdminSystemRequest extends AdminSystemVO {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 短信验证码
     */
    private String smsCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
