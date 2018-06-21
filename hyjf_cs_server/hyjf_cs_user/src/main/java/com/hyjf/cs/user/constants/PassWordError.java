package com.hyjf.cs.user.constants;


import com.hyjf.common.constants.MsgCode;

/**
 * @author xiasq
 * @version LoginError, v0.1 2018/4/25 14:58
 */
public enum PassWordError implements MsgCode {
    USER_LOGIN_ERROR("1", "登录失效，请重新登陆"),
    USER_OPENBANK_ERROR("1", "用户未开户！"),
    PASSWORK_ALREADY_ERROR("1", "已设置交易密码"),
    BANK_CONNECT_ERROR("1", "调用银行接口失败！"),
    PASSWORK_SET_ERROR("1", "交易密码设置失败"),
    NEWPASSWORD_NOTNULL_ERROR("1", "新密码不能为空!"),
    PASSWORD_SAME_ERROR("1", "两次密码不一致"),
    PASSWORD_SAME1_ERROR("1", "新密码不能与原密码相同!"),
    OLDPASSWD_INVALID_ERROR("1", "旧密码不正确"),
    PASSWORD_LENGTH_ERROR("1", "密码长度6-16位"),
    PASSWORD_NO_NUMBER_ERROR("1", "密码必须包含数字"),
    PASSWORD_FORMAT_ERROR("1", "密码必须由数字和字母组成，如abc123"),
    PASSWORD_CHANGE_ERROR("1", "修改密码失败,未作任何操作"),
    LOGINPW_NOTNULL_ERROR("1", "原始登录密码不能为空");


    private String errCode;
    private String message;

    PassWordError(String errCode, String message) {
        this.errCode = errCode;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.errCode;
    }

    @Override
    public String getMsg() {
        return this.message;
    }
}
