package com.hyjf.am.trade.utils.constant;

/**
 * @author xiasq
 * @version BorrowSendTypeEnum, v0.1 2018/7/10 16:01
 */
public enum BorrowSendTypeEnum {
	FUSHENSEND_CD("AUTO_FULL"),
    FABIAO_CD("AUTO_BAIL");

	private String value;

	/** 构造器默认也只能是private, 从而保证构造函数只能在内部使用 */
	BorrowSendTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
