package com.hyjf.pay.lib.fadada.util;

import java.io.Serializable;

public class DzqzConstant implements Serializable{

    /**法大大接入方ID*/
    public static final  String HYJF_FDD_APP_ID = "hyjf.fdd.app.id";
    /**法大大秘钥*/
    public static final  String HYJF_FDD_APP_SECRET = "hyjf.fdd.app.secret";
    /**法大大版本号*/
    public static final  String HYJF_FDD_VERSION = "hyjf.fdd.version";
    /**法大大接口URL*/
    public static final  String HYJF_FDD_URL = "hyjf.fdd.url";
    /**法大大pay工程实现URL*/
    public static final  String HYJF_PAY_FDD_URL = "hyjf.pay.fdd.url";

    /**法大大pay工程实现URL*/
    public static final  String HYJF_PAY_FDD_NOTIFY_URL = "hyjf.pay.fdd.nofify.url";

    /**法大大接口实现方法*/
    public static final String REQUEST_MAPPING_CALLAPIBG = "/callApiBg";
    /**法大大接口异步通知实现方法*/
    public static final String REQUEST_MAPPING_CALLAPI_SIGNNODIFY = "/callApiSignNodify";
    /** GET */
    public static final String GET = "get";

    /**合同生成接口*/
    public static final String FDD_GENERATECONTRACT = "generate_contract";
    /**自动签署合同接口*/
    public static final String FDD_EXTSIGN_AUTO = "extsign_auto";
    /**签署结果查询接口*/
    public static final String FDD_QUERY_SIGNSTATUS = "query_signstatus";
    /**下载已签署合同*/
    public static final String FDD_DOWNLOADCONTRACT = "downLoadContract";




}
