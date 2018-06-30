package com.hyjf.am.user.utils;

public class AnRongDefine{

	/** REQUEST_MAPPING */
	public static final String REQUEST_MAPPING = "/anrong";
	// 处理结果
	/** 处理成功 */
	public static final String SUCCESS = "0";
	/** 处理失败 */
	public static final String FAILED = "1";
	/** 请求url queryUser */
    public static final String URL_ANRONG_QUERY_USER = "/queryUser";
    /** 安融返回结果msp 的key */
    public static final String RESULT_KEY_MSP = "msp";
    /** 安融返回结果msp 的errorCode */
    public static final String RESULT_KEY_ERRORCODE = "errorCode";
    /** 安融返回结果msp 的abnormalCreditDetails */
    public static final String RESULT_KEY_ABNORMALCREDITDETAILS = "abnormalCreditDetails";
    /** 安融返回结果msp 的applyDetails */
    public static final String RESULT_KEY_APPLYDETAILS = "applyDetails";
    /** 安融返回结果msp 的blackDatas */
    public static final String RESULT_KEY_BLACKDATAS = "blackDatas";
    /** 安融返回结果msp 的normalCreditDetails */
    public static final String RESULT_KEY_NORMALCREDITDETAILS = "normalCreditDetails";
    /** 安融返回结果msp 的queryDetails */
    public static final String RESULT_KEY_QUERYDETAILS = "queryDetails";
    /** 安融返回结果msp 的title */
    public static final String RESULT_KEY_TITLE = "title";
    /** 安融返回结果msp 的overdues */
    public static final String RESULT_KEY_OVERDUES = "overdues";
    /** 安融返回结果msp 的fqz */
    public static final String RESULT_KEY_FQZ = "fqz";
    /** 安融返回结果msp 的fqzBean */
    public static final String RESULT_KEY_FQZBEAN = "fqzBean";
    
    // Controller 返回客户端的响应
    /** api返回给客户端的响应反欺诈结果   fqz_success */
    public static final String RESULT_JSON_KEY_FQZ_SUCCESS = "fqz_success";
    /** api返回给客户端的响应反欺诈结果描述   fqz_mess */
    public static final String RESULT_JSON_KEY_FQZ_MESS = "fqz_mess";
    /** api返回给客户端的响应msp结果   msp_success */
    public static final String RESULT_JSON_KEY_MSP_SUCCESS = "msp_success";
    /** api返回给客户端的响应msp结果描述   msp_mess */
    public static final String RESULT_JSON_KEY_MSP_MESS = "msp_mess";
    /** 安融返回结果反欺诈 的validSifa */
    public static final String RESULT_KEY_VALIDSIFA = "validSifa";
    /** 安融返回结果反欺诈 的anliInfos */
    public static final String RESULT_KEY_ANLIINFOS = "anliInfos";
    /** 安融返回结果反欺诈 的shixinInfos */
    public static final String RESULT_KEY_SHIXININFOS = "shixinInfos";
    /** 安融返回结果反欺诈 的zhixingInfos */
    public static final String RESULT_KEY_ZHIXINGINFOS = "zhixingInfos";
    /** 安融返回结果反欺诈 的degreeResult */
    public static final String RESULT_KEY_DEGREERESULT = "degreeResult";
    /** 安融返回结果反欺诈 的cuikuangongshis */
    public static final String RESULT_KEY_CUIKUANGONGSHIS = "cuikuangongshis";
    /** id */
    public static final String STRING_ID = "id";
    /** 共享请求URL /send */
    public static final String URL_ANRONG_SEND = "/send";
    /** TXCODE共享接口 sendMess */
    public static final String TXCODE_SENDMESS = "sendMess";
    /** TXCODE查询接口 queryUser */
    public static final String TXCODE_QUERYUSER = "queryUser";
    
    /** api返回给客户端的响应查询Id reqId */
    public static final String RESULT_JSON_KEY_REQID = "reqId";
    /** api返回给客户端的响应信息 */
    public static final String RESULT_KEY_MESSAGE = "message";
    /** api返回给客户端的响应结果  0000 */
    public static final String RESULT_VALUE_SUCCESS = "0000";
    /** api返回给客户端的json Key success */
    public static final String RESULT_JSON_KEY_SUCCESS = "success";
    /** api返回给客户端的json Key errors */
    public static final String RESULT_KEY_ERRORS = "errors";
    /** 安融请求返回的 Key msg */
    public static final String RESULT_KEY_MSG = "msg";
    /** api返回给客户端的json Key msg */
    public static final String RESULT_JSON_KEY_MSG = "msg";
    
}
