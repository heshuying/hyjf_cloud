package com.hyjf.cs.user.controller.wechat.base;

import com.hyjf.common.util.PropUtils;

public class BaseDefine {
	/** 返回信息@RequestMapping值 */
	public static final String INFO = "info";
	/** 返回信息@RequestMapping值 */
	public static final String ERROR = "error";
	/** 返回结果@RequestMapping值 */
	public static final String STATUS = "status";
	/** 返回结果@RequestMapping值 */
    public static final String RETURL = "retUrl";
	/** 返回信息@RequestMapping值 */
	public static final String MESSAGE = "message";
	/** 成功结果@RequestMapping值 */
	public static final Boolean STATUS_TRUE = true;
	/** 失败结果@RequestMapping值 */
	public static final Boolean STATUS_FALSE = false;
	
	/** 成功结果@RequestMapping值 */
	public static final int ERROR_SUCCESS = 0;
	/** 失败结果@RequestMapping值 */
	public static final int ERROR_FAIL = 1;
	/** 处理中@RequestMapping值 */
	public static final int ERROR_DOING = 2;
	/** hyjf.web.host */
	public static String HOST = PropUtils.getSystem("hyjf.web.host").trim();
	/** JSP 汇付天下跳转画面 */
    public static final String JSP_CHINAPNR_RESULT = "/chinapnr/chinapnr_result";
    /** @RequestMapping值 */
    public static final String REQUEST_HOME = "/hyjf-wechat";
    /** 结果页跳转处理页面 */
    public static final String JUMP_HTML = "/jumpHTML";
}
