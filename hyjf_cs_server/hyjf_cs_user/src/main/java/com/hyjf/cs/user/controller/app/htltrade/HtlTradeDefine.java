package com.hyjf.cs.user.controller.app.htltrade;

import com.hyjf.cs.user.bean.BaseDefine;

public class HtlTradeDefine extends BaseDefine {


	/** REQUEST_MAPPING */
	public static final String REQUEST_MAPPING = "/htl";

	/** 获取我的账单  */
	public static final String HTLTRADE_ACTION = "/htlList";
	
    /** @RequestMapping值 */
    public static final String RETURN_REQUEST= REQUEST_HOME + REQUEST_MAPPING + HTLTRADE_ACTION;
}
