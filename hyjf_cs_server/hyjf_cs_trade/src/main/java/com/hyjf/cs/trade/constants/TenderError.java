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
    PLAN_CLOSE_ERROR("1", "此计划项目已经关闭！"),
    NOT_FIND_PLAN_ERROR("1", "计划项目不存在！"),
    MONEY_NULL_ERROR("1", "请输入加入金额！"),
    MONEY_STYLE_ERROR("1", "金额格式不正确！"),
    MONEY_ZERO_ERROR("1", "投资金额不能为0元！"),
    COUPON_USER_ONLY_ERROR("1", "该优惠券只能单独使用！"),
    NO_JOIN_MONEY_ERROR("1", "您来晚了，下次再来抢吧！"),
    JOIN_MONEY_LESS_ERROR("1", "剩余可加入金额为{}元！"),
    JOIN_MONEY_ONLY_LESS_ERROR("1", "剩余可加入只剩{}元，须全部购买！"),
    MIN_INVESTMENT_ERROR("1", "{}元起投！"),
    MAX_INVESTMENT_ERROR("1", "项目最大加入额为{}元！"),
    JOIN_MONEY_THAN_ACCOUNT_ERROR("1", "加入金额不能大于开放额度！"),
    NO_MONEY_ERROR("1", "可用金额不足！"),
    PLAN_MONEY_LESS_ERROR("1", "项目太抢手了！剩余可加入金额只有{}元！"),
    MONEY_INTEGRAL_MULTIPLE_ERROR("1", "加入递增金额须为{}元的整数倍"),
    USER_EVALUATION_ERROR("1", "根据监管要求，投资前必须进行风险测评。"),
    JOIN_PLAN_LATE_ERROR("1", "您来晚了，下次再来抢吧。"),
    JOIN_PLAN_ERROR("1", "抱歉，投资失败，请重试！"),

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

    public void replaceMsg(String msg) {
        this.msg = msg.replace("{}",msg);
    }
}