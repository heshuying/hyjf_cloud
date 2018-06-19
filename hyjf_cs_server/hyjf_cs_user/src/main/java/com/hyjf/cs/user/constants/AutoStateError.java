/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.constants;

import com.hyjf.common.constants.MsgCode;
import org.apache.log4j.spi.ErrorCode;

/**
 * @author zhangqingqing
 * @version AutoStateError, v0.1 2018/6/12 14:13
 */
public enum AutoStateError implements MsgCode {

    PARAM_ERROR("1", "请求参数非法"),
    CHECK_ERROR("1", "验签失败！"),
    CHECK_USER_INFO_ERROR("1", "根据电子账户号查询用户信息失败"),
    GET_USER_ERROR("1", "查询用户失败"),
    NOT_PASSWD_ERROR("1", "未设置交易密码"),
    AUTH_ERROR("1", "授权状态查询接口失败！");


    private String code;
    private String msg;

    AutoStateError(String code, String msg) {
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
