package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author xiasq
 * @version SmsCodeVO, v0.1 2018/4/11 13:52
 */
public class SmsCodeVO extends BaseVO implements Serializable {
    private String mobile;
    // 验证码
    private String verificationCode;
    // 验证码类型
    private String verificationType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }
}
