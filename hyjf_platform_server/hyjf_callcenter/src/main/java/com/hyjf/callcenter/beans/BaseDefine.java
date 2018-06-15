package com.hyjf.callcenter.beans;

public class BaseDefine {
	/** @RequestMapping值 */
	// public static final String REQUEST_HOME = "/hyjf-api-web";
//	public static final String HOST = PropUtils.getSystem("hyjf.api.web.url").trim();
//	public static final String HOST_ASYN = PropUtils.getSystem("http.hyjf.web.host").trim();

	// public static final String MSG_CALLCENTER = PropUtils.getMessage("ICALLCENTER001").trim(); todo
	public static final String MSG_CALLCENTER = "";
	public static final String MSG_COLON = ":";
	public static final String MSG_CALLCENTER_COLON = MSG_CALLCENTER + MSG_COLON;
	public static final Integer CHK_SEARCH_MAXSIZE = 100;
	public static final Integer CHK_UNIQUENO_SIZE = 15;

//    /** 呼叫中心接口用签验密钥 */
//    public static final String RELEASE_CALLCENTER_MSG_ACCESSKEY = "release.callcenter.msg.accesskey";
}
