package com.hyjf.cs.user.constants;

import com.hyjf.common.constants.ErrorCode;

/**
 * @author hesy
 */
public enum BindCardError implements ErrorCode {
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

    private String errCode;
    private String message;

    BindCardError(String errCode, String message) {
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
