package com.hyjf.wbs.qvo.csuser;

public enum ResultEnum {
    //通用错误码
    SUCCESS("000", "成功"),
    SUCCESS1("000", "注册成功"),
    SUCCESS2("000", "开户成功！"),
    SUCCESS3("000", "短信发送成功！"),
    SUCCESS4("000", "更新成功"),
    SUCCESS5("000", "绑卡成功！"),
    
    FAIL("99", "失败"),
    NOTLOGIN("999", "请先登录!"),
    LOGININVALID("998", "登录过期,请重新登录!"),
    PARAM("997", "请求参数非法"),
    ERROR_996("996", "对不起,该用户已经被禁用！"),
    ERROR_995("995", "调用银行接口失败！"),

    ERROR_001("001", "登录失败,用户不存在"),
    ERROR_002("002", "登录失败,存在多个相同用户"),
    ERROR_003("003", "登录失败,账户或密码错误"),
    ERROR_004("004", "用户信息不存在"),
    ERROR_005("005", "自动投标已授权"),
    ERROR_006("006", "自动债转已授权"),
    ERROR_007("007", "短信验证码发送失败，请稍后再试！"),
    ERROR_008("008", "验证码或前导业务码不能为空"),
    ERROR_009("009","无效的推荐人"),
    ERROR_010("010","手机号不能为空"),
    ERROR_011("011","验证码不能为空"),
    ERROR_012("012","密码不能为空"),
    ERROR_013("013","密码长度8-16位"),
    ERROR_014("014","密码必须包含数字"),
    ERROR_015("015","必须包含数字、字母、符号至少两种!"),
    ERROR_016("016","请输入您的真实手机号码"),
    ERROR_017("017","该手机号已经注册"),
    ERROR_018("018","验证码无效"),
    ERROR_019("019","注册失败,参数异常"),
    ERROR_020("020","注册发生错误,参数异常"),
    ERROR_043("043","此用户已禁用"),

    ERROR_021("99","用户未登陆，请先登陆！"),
    ERROR_022("99","开户失败！"),
    ERROR_023("99","获取用户信息失败！"),
    ERROR_024("99","用户已开户！"),
    ERROR_025("99","真实姓名不能为空！"),
    ERROR_026("99","真实姓名不能包含空格！"),
    ERROR_027("99","真实姓名不能超过10位！"),
    ERROR_028("99","身份证号不能为空！"),
    ERROR_029("99","身份证已存在!"),
    ERROR_030("99","银行卡号不能为空！"),
    ERROR_031("99","短信验证码不能为空！"),
    ERROR_032("99","用户信息错误，未获取到用户的手机号码！"),
    ERROR_033("99","开户失败,请重新获取验证码!"),
    ERROR_034("99","短信验证码获取失败！"),
    ERROR_035("99","保存开户日志失败！"),
    ERROR_036("99","开户失败，请联系客服！"),
    
    
    ERROR_040("99","充值异常"),
    ERROR_041("99","充值金额必须为正整数"),
    ERROR_042("99","结果页URL获取失败"),
    
    ERROR_037("99","数据非法"),
    ERROR_038("99","保存开户日志失败！"),
    ERROR_039("99","用户不存在！"),
    ERROR_044("99","验签失败"),
    ERROR_045("99","请联系客服"),
    BORROE_ERROR_100("100", "标的信息不存在"),

    USER_ERROR_200("200", "用户未开户"),
    USER_ERROR_201("201", "用户未设置交易密码"),
    USER_ERROR_202("202", "用户自动出借已授权,无需重复授权"),
    USER_ERROR_203("203", "用户自动债转已授权,无需重复授权"),
    USER_ERROR_204("204", "用户自动出借授权失败"),
    USER_ERROR_205("205", "用户自动债转授权失败"),
    USER_ERROR_206("206", "用户已设置交易密码"),
    USER_ERROR_207("207", "交易密码设置失败"),
    USER_ERROR_208("208", "调用银行接口失败"),
    USER_ERROR_209("209", "用户未绑卡"),
    USER_ERROR_210("210", "调用汇付接口失败"),
    USER_ERROR_211("211", "汇付银行卡信息不存在"),
    USER_ERROR_212("212", "不要重复操作"),
    USER_ERROR_213("213", "为更好保障您的出借利益，您须在完成风险测评后才可进行出借"),
    USER_ERROR_214("214", "用户已开户"),

    USER_ERROR_215("215", "修改交易密码失败"),
    USER_ERROR_216("216", "原测评有效期已过，为更好保障您的出借利益，您须在重新完成风险测评后才可进行出借"),
    USER_ERROR_217("217", "请先解绑已有银行卡"),
    USER_ERROR_1001("1001", "用户已评测"), USER_ERROR_1002("1002", "用户还未评测"), USER_ERROR_1003("1003", "用户未绑定银行卡"),
    USER_ERROR_1012("1012", "用户评测已过期"),
    
    USER_WITHDRAW_001("1004", "提现金额应大于手续费金额"), 
    USER_WITHDRAW_002("1005", "提现金额大于可用余额，请确认后再次提现"), 
    USER_WITHDRAW_003("1006", "银行卡号不正确，请确认后再次提现"),
    USER_WITHDRAW_004("1007", "大额提现时,开户行号不能为空"), 
    USER_WITHDRAW_005("1008", "您还未开户，请开户后重新操作"), 
    USER_WITHDRAW_006("1009", "银行卡信息不存在，请核实后重新操作"), 
    USER_WITHDRAW_007("1010", "请不要重复操作"),
    USER_WITHDRAW_008("1011","出借人信息不存在"),
	ERR_PASSWORD_ERROR_TOO_MANEY("EPW000001", "登录失败,当日密码错误已达上限，请明日再试！");

    // 成员变量  
    private String status;
    private String statusDesc;
    
    // 构造方法  
    private ResultEnum(String status, String statusDesc) {
        this.status = status;
        this.statusDesc = statusDesc;
    }

    //覆盖方法  
    @Override
    public String toString() {
        return this.status + "_" + this.statusDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
	
}
