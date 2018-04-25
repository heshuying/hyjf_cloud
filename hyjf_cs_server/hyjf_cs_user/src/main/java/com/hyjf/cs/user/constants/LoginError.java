package com.hyjf.cs.user.constants;

import com.hyjf.common.constants.ErrorCode;

/**
 * @author xiasq
 * @version LoginError, v0.1 2018/4/25 14:58
 */
public enum LoginError implements ErrorCode {
    USER_LOGIN_ERROR("1", "登录失败,账号或密码错误"),
    USER_INVALID_ERROR("1", "抱歉，您的账户已被禁用，如有疑问请联系客服！"),
    PWD_ERROR_TOO_MANEY_ERROR("1", "登录失败,当日密码错误已打上限，请明日再试！");


    private String errCode;
    private String message;

    LoginError(String errCode, String message) {
        this.errCode = errCode;
        this.message = message;
    }

    @Override
    public String getErrCode() {
        return this.errCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
