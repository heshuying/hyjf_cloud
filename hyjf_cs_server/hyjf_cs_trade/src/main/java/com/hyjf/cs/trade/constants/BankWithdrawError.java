/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.constants;


import com.hyjf.common.constants.MsgCode;

/**
 * @author zhangqingqing
 * @version LoginError, v0.1 2018/4/25 14:58
 */
public enum BankWithdrawError implements MsgCode {
    USER_LOGIN_ERROR("1", "登录失败,请重新登陆"),
    PARAM_ERROR("1", "参数错误，请重试"),
    ACCOUNT_ERROR("1", "你的账户信息存在异常，请联系客服人员处理。"),
    WITHDRAW_TRSANSAMT_ERROR("1", "请输入提现金额。"),
    WITHDRAW_SERVICE_CHARGE_ERROR("1", "提现金额需大于1元！"),
    WITHDRAW_CARD_NO_ERROR("1", "银行卡号不正确，请确认后再次提现。"),
    WITHDRAW_PAYALLIANCECODE_ERROR("1", "大额提现时,开户行号不能为空!"),
    NOT_CARD_NO_ERROR("1", "用户未绑卡！"),

    NOT_REGIST_ERROR("1", "用户未开户！"),
    NOT_SET_PWD_ERROR("1", "用户未设置交易密码"),
    CANNOT_REPEAT_ERROR("1", "用户已授权,无需重复授权"),
    CALL_BANK_ERROR("1", "调用银行接口失败"),
	BANK_WITHDRAW_EXCEPTION_HANDLE_ERROR("1", "江西银行提现掉单处理出错！"),
	BANK_WITHDRAW_EXCEPTION_HANDLE_SUCCESS("0", "江西银行提现掉单处理成功！");

    private String code;
    private String msg;

    BankWithdrawError(String code, String msg) {
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