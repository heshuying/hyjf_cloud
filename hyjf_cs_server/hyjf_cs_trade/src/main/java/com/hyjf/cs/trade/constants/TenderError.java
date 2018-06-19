package com.hyjf.cs.trade.constants;


import com.hyjf.common.constants.MsgCode;
/**
 * @Description 投资异常
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 11:06
 */
public enum TenderError implements MsgCode {
    USER_LOGIN_ERROR("1", "登录失败,请重新登陆"),
    PARAM_ERROR("1", "参数错误，请重试"),
    WITHDRAW_TRSANSAMT_ERROR("1", "请输入提现金额。"),
    WITHDRAW_SERVICE_CHARGE_ERROR("1", "提现金额需大于1元！"),
    WITHDRAW_CARD_NO_ERROR("1", "银行卡号不正确，请确认后再次提现。"),
    WITHDRAW_PAYALLIANCECODE_ERROR("1", "大额提现时,开户行号不能为空!"),
    NOT_CARD_NO_ERROR("1", "用户未绑卡！"),

    NOT_REGIST_ERROR("1", "用户未开户！"),
    NOT_SET_PWD_ERROR("1", "用户未设置交易密码"),
    CANNOT_REPEAT_ERROR("1", "用户已授权,无需重复授权"),
    CALL_BANK_ERROR("1", "调用银行接口失败"),


	TENDERING_ERROR("1", "用户正在投资中！"),
    USER_ROLE_ERROR("1", "仅限出借人进行投资！"),
    USER_DISABLED_ERROR("1", "抱歉，您的账户已被禁用，如有疑问请联系客服！"),
    USER_AUTO_INVES_ERROR("1", "该产品需开通自动投标功能！"),
    USER_AUTO_CREDIT_ERROR("1", "该产品需开通自动债转功能！"),
    USER_PAYMENT_AUTH_ERROR("1", "该产品需开通服务费授权功能！"),
	BANK_WITHDRAW_EXCEPTION_HANDLE_SUCCESS("0", "江西银行提现掉单处理成功！");

    private String code;
    private String msg;

    TenderError(String code, String msg) {
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