/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.constants;

import com.hyjf.common.constants.ErrorCode;

/**
 * @author zhangqingqing
 * @version AutoStateError, v0.1 2018/6/12 14:13
 */
public enum AutoStateError implements ErrorCode {

    PARAM_ERROR("CE000001", "请求参数非法"),
    CHECK_ERROR("CE000002", "验签失败！"),
    CHECK_USER_INFO_ERROR("CE000004", "根据电子账户号查询用户信息失败"),
    GET_USER_ERROR("CE000007", "查询用户失败"),
    NOT_PASSWD_ERROR("TP000002", "未设置交易密码"),
    AUTH_ERROR("CE999999", "授权状态查询接口失败！");


    private String errCode;
    private String message;

    AutoStateError(String errCode, String message) {
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
