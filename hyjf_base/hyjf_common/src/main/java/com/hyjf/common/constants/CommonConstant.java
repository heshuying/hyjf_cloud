package com.hyjf.common.constants;

/**
 * @author xiasq
 * @version CommonConstant, v0.1 2018/4/25 17:09
 */
public class CommonConstant {

    /**
     * 状态
     */
    public static final String SUCCESS = "success";

    /**PC*/
    public static final String CLIENT_PC = "0";

    /**微官网*/
    public static final String CLIENT_WECHAT = "1";

     /**安卓*/
    public static final String CLIENT_ANDROID = "2";

     /**IOS*/
    public static final String CLIENT_IOS = "3";

     /** 其他*/
    public static final String CLIENT_OTHER = "4";

    /** 短信验证码状态,新验证码 */
    public static final Integer CKCODE_NEW = 0;
    /** 短信验证码状态,失效 */
    public static final Integer CKCODE_FAILED = 7;
    /** 短信验证码状态,已验 */
    public static final Integer CKCODE_YIYAN = 8;
    /** 短信验证码状态,已用 */
    public static final Integer CKCODE_USED = 9;



    /** 短信模板名 */
    /** 注册 */
    public static final String PARAM_TPL_ZHUCE = "TPL_ZHUCE";
    /** 短信模板名-提现验证码 */
    public static final String PARAM_TPL_SMS_WITHDRAW = "TPL_SMS_WITHDRAW";

    /** 找回密码 */
    public static final String PARAM_TPL_ZHAOHUIMIMA = "TPL_ZHAOHUIMIMA";

    /** 更换手机号-验证原手机号 */
    public static final String PARAM_TPL_YZYSJH = "TPL_YZYSJH";

    /** 更换手机号-绑定新手机号 */
    public static final String PARAM_TPL_BDYSJH = "TPL_BDYSJH";



	/** borrow_apicron 字段说明 */
	// api_type = 0 放款
	public static final int APICRON_API_TYPE_BORROW = 0;
	// api_type = 1 还款
	public static final int APICRON_API_TYPE_REPAY = 1;
	// status 初始
	public static final int APICRON_STATUS_INIT = 0;
	// 1 放款/还款 请求中
	public static final int APICRON_STATUS_REQUESTING = 1;
	// 2 放款/还款 请求成功
	public static final int APICRON_STATUS_REQUEST_SUCCESS = 2;
	// 3 放款/还款 校验成功
	public static final int APICRON_STATUS_CHECK_SUCCESS = 3;
	// 4 放款/还款 校验失败
	public static final int APICRON_STATUS_CHECK_FAIL = 4;
	// 5 放款/还款 失败
	public static final int APICRON_STATUS_RESULT_FAIL_ = 5;
	// 6 放款/还款 成功
	public static final int APICRON_STATUS_RESULT_SUCCESS_ = 6;
    // 融通宝加息利息还款状态 0-未完成
	public static final int APICRON_EXTRA_YIELD_REPAY_STATUS_INIT = 0;
    // 融通宝加息利息还款状态 1-已完成
	public static final int APICRON_EXTRA_YIELD_REPAY_STATUS_FINISH = 1;
    // 融通宝加息利息还款状态 2-进行中
    public static final int APICRON_EXTRA_YIELD_REPAY_STATUS_RUNNING = 2;
    // 融通宝加息利息还款状态 9-错误
    public static final int APICRON_EXTRA_YIELD_REPAY_STATUS_ERROR = 9;

    // hyjf inst_code机构编号
    public static final String HYJF_INST_CODE = "10000000";

    /** 注册成功跳转页面 */
    public static final String REGIST_RESULT_SUCCESS = "/user/regist/result/success";

    //-------------------------------活动---------------------------------
    //活动编号不能为空
    public final static String ACTIVITYID_IS_NULL="活动编号不能为空";
    //该活动不存在
    public final static String ACTIVITY_ISNULL="该活动不存在";
    //该活动不存在
    public final static String ACTIVITY_TIME_NOT_START="该活动还未开始";
    //该活动已结束
    public final static String ACTIVITY_TIME_END="您来晚了，活动已过期~~";
    //该活动已结束
    public final static String PLATFORM_LIMIT="本活动限***端参与";
    //该活动用户已参与
    public final static String ACTIVITY_LIMIT="该活动用户已参与";

    //APP改版 start
    /** 设置交易密码验证失败*/
    public static final String JUMP_HTML_FAILED_PATH = "/user/setting/bankPassword/result/failed";
    /** 设置交易密码处理中*/
    public static final String JUMP_HTML_HANDLING_PATH = "/user/setting/bankPassword/result/handling";
    /** 设置交易密码成功*/
    public static final String JUMP_HTML_SUCCESS_PATH = "/user/setting/bankPassword/result/success";

    /** @RequestMapping值 */
    public static final String REQUEST_MAPPING = "/hyjf-app/bank/user/transpassword";

    /** @RequestMapping值 */
    public static final String RETURL_SYN_PASSWORD_ACTION = "/passwordReturn";
    /** @RequestMapping值 */
    public static final String RETURN_ASY_PASSWORD_ACTION = "/passwordBgreturn";


    /** 重置交易密码 */
    public static final String RESETPASSWORD_ACTION = "/resetPassword";
    /** 重置交易密码同步回调 */
    public static final String RETURL_SYN_RESETPASSWORD_ACTION = "/resetPasswordReturn";
    /** 重置交易密码异步回调 */
    public static final String RETURN_ASY_RESETPASSWORD_ACTION = "/resetPasswordBgreturn";

    /**前端的url*/
    public static final String JUMP_HTML_ERROR_PATH = "/user/setting/authorization/result/failed";

    /** JSP 汇付天下跳转画面 */
    public static final String JSP_CHINAPNR_RESULT = "/chinapnr/chinapnr_result";

    /** JSP 跳转到汇付天下画面 */
    public static final String JSP_CHINAPNR_SEND = "/chinapnr/chinapnr_send";

    /** chinapnrForm值 */
    public static final String CHINAPNR_FORM = "chinapnrForm";

    public static final  String PAGE_LIMIT_START = "limitStart";

    public static final  String PAGE_LIMIT_END =   "limitEnd";

    /** ValidateForm请求返回值 */
    public static final String JSON_IF_EVALUATION_KEY = "ifEvaluation";
    /** ValidateForm请求返回值*/
    public static final String JSON_USER_EVALATION_RESULT_KEY = "userEvalationResult";
    /** ValidateForm请求返回值*/
    public static final String JSON_QUESRION_LIST_KEY = "questionList";
    /** ValidateForm请求返回值*/
    public static final String JSON_USER_LOGIN_ERROR_KEY = "userError";
    /** ValidateForm请求返回值*/
    public static final String JSON_USER_LOGIN_ERROR_VLUES = "userError";

    /**
     * ----------------------------------APP端银行页面调用请求类型---------------------------------------
     */
    /**
     * 开户
     */
    public static final String APP_BANK_REQUEST_TYPE_OPEN_ACCOUNT = "1";
    /**
     * 散标出借
     */
    public static final String APP_BANK_REQUEST_TYPE_TENDER = "2";
    /**
     * 银行提现
     */
    public static final String APP_BANK_REQUEST_TYPE_WITHDRAW = "3";
    /**
     * 银行充值
     */
    public static final String APP_BANK_REQUEST_TYPE_RECHARGE = "4";

    /**
     * 设置交易密码
     */
    public static final String APP_BANK_REQUEST_TYPE_SET_PASSWORD = "5";

    /**
     * 设置交易密码
     */
    public static final String APP_BANK_REQUEST_TYPE_RESET_PASSWORD = "6";

    /**
     * 授权自动出借
     */
    public static final String APP_BANK_REQUEST_TYPE_AUTHINVES = "7";

    /**
     * 授权自动债转
     */
    public static final String APP_BANK_REQUEST_TYPE_AUTHCREDIT = "8";

    /**
     * 授权三合一接口
     */
    public static final String APP_BANK_REQUEST_TYPE_AUTHMERGE = "11";
    /**
     * 授权缴费授权
     */
    public static final String APP_BANK_REQUEST_TYPE_AUTHPAYMENT = "12";
    /**
     * 授权还款授权
     */
    public static final String APP_BANK_REQUEST_TYPE_AUTHREPAY = "13";
    /**
     * 解绑银行卡
     */
    public static final String APP_BANK_REQUEST_TYPE_UNBINDCARD = "14";
    /**
     * 修改银行预留手机号
     */
    public static final String APP_BANK_MOBILE_MODIFY = "16";



    /**
     * 数据源控制优先级别(值越小优先级越高)，读方法切面
     */
    public static final String[] DATASOURCE_QUERY_PREFIX = {"select","query","count","search","get","find","check","export"};
    public static final int DATASOURCE_AOP_DS = 1;
    public static final int DATASOURCE_AOP_TRANSACTION = 2;

    /*  ----------------出借类型枚举 开始-------------------*/

    /*计划出借*/
    public static final String TENDER_TYPE_HJH = "HJH";

    /*债转出借*/
    public static final String TENDER_TYPE_CREDIT = "HZR";

    /*  ----------------出借类型枚举 结束-------------------*/
    
}
