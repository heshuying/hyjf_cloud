/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.constants;


import com.hyjf.common.constants.ErrorCode;

/**
 * @author zhangqingqing
 * @version LoginError, v0.1 2018/4/25 14:58
 */
public enum RechargeError implements ErrorCode {
    NOT_OPENACCOUNT_ERROR("1", "用户未开户！"),
    NOT_PASSWD_ERROR("1", "用户未设置交易密码！"),
    BANKCARD_ERROR("1", "查询银行卡信息失败！"),
    MONEY_NOT_NULL_ERROR("1", "充值金额不能为空！"),
    FORMAT_ERROR("1", "充值金额格式错误！"),
    MORE_DECIMAL_ERROR("1", "充值值金额不能大于两位小数！"),
    CALL_BANK_ERROR("1", "调用银行接口失败");


    private String errCode;
    private String message;

    RechargeError(String errCode, String message) {
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