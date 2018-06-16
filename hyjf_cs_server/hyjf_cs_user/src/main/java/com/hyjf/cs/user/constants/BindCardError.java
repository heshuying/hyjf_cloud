package com.hyjf.cs.user.constants;

import com.hyjf.common.constants.MsgCode;
import com.hyjf.common.validator.CheckUtil;

/**
 * @author hesy
 */
public enum BindCardError implements MsgCode {
    CARD_NO_ERROR("1", "银行卡号未填写"),
    MOBILE_ERROR("1", "手机号未填写"),
    SMSCODE_ERROR("1", "短信验证码未填写"),
    AUTH_CODE_ERROR("1", "短信授权码为空"),
	BANK_NOT_OPEN_ERROR("1", "用户未开户"),
	BANK_CALL_ERROR("1", "请求银行接口失败"),
	CARD_SAVE_ERROR("1", "银行卡信息保存失败"),
	BANK_BALANCE_ERROR("1", "账户尚有余额，不能解绑银行卡"),
	CARD_NOT_EXIST_ERROR("1", "没有要解绑的银行卡"),
	CARD_DELETE_ERROR("1", "银行卡删除失败");

    private String code;
    private String msg;

    BindCardError(String code, String msg) {
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
