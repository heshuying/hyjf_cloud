package com.hyjf.cs.user.constants;

import com.hyjf.common.constants.MsgCode;

/**
 * @author xiasq
 * @version LoginError, v0.1 2018/4/25 14:58
 */
public enum LoginError implements MsgCode {
    USER_LOGIN_ERROR("1", "登录失败,账号或密码错误"),
    USER_INVALID_ERROR("1", "抱歉，您的账户已被禁用，如有疑问请联系客服！"),
    CHECK_NULL_ERROR("1", "用户名或密码不能为空！"),
    REFFER_INVALID_ERROR("1", "无效的推荐人！"),
    ERROR_PARAM("1", "请求参数非法"),
    PWD_ERROR_TOO_MANEY_ERROR("1", "登录失败,当日密码错误已打上限，请明日再试！");

    private String code;
    private String msg;

    LoginError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
