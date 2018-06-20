/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.constants;


import com.hyjf.common.constants.MsgCode;

/**
 * @author zhangqingqing
 * @version LoginError, v0.1 2018/4/25 14:58
 */
public enum SynBalanceError implements MsgCode {
    PARAM_ERROR("1", "参数错误，请重试");


    private String code;
    private String msg;

    SynBalanceError(String code, String msg) {
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