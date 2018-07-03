package com.hyjf.pay.bean;

import com.hyjf.pay.base.BaseDefine;

public class AnRongCallDefine extends BaseDefine {

	/** CONTROLLOR @value值 */
	public static final String CONTROLLOR_CLASS_NAME = "AnRongCallController";

	/** CONTROLLOR @RequestMapping值 /anrongcall */
	public static final String CONTROLLOR_REQUEST_MAPPING = "/anrongcall";
	/** 接口 @RequestMapping值 */
	public static final String CALL_API_BG = "callApiBg";

	//---------------------------------------------
	/** 日志状态  0已发送 */
    public static final String STATUS_SENDED = "0";
    /** 状态 1:处理成功 */
    public static final String STATUS_SUCCESS = "1";
    /** 状态 3:处理中 */
    public static final String STATUS_RUNNING = "3";
    /** 状态 9:处理失败 */
    public static final String STATUS_FAIL = "9";
    

    /** 删除标识 */
    public static final String FLAG_DELETE = "1";

    public static final String FLAG_NORMAL = "0";
    /** txCode */
    public static final String TXCODE = "txCode";

}
