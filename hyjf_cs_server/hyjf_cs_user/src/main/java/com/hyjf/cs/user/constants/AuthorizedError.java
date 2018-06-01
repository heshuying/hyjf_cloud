/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.constants;


import com.hyjf.common.constants.ErrorCode;

/**
 * @author zhangqingqing
 * @version LoginError, v0.1 2018/4/25 14:58
 */
public enum AuthorizedError implements ErrorCode {
    USER_LOGIN_ERROR("1", "登录失败,请重新登陆"),
    PARAM_ERROR("1", "参数错误，请重试"),
    NOT_REGIST_ERROR("1", "用户未开户！"),
    NOT_SET_PWD_ERROR("1", "用户未设置交易密码"),
    CANNOT_REPEAT_ERROR("1", "用户已授权,无需重复授权"),
    CALL_BANK_ERROR("1", "调用银行接口失败");


    private String errCode;
    private String message;

    AuthorizedError(String errCode, String message) {
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