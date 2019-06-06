package com.hyjf.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <p>
 * 常量文件
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class CustomConstants implements MessageConstants, PropertiesConstants {

    /** action请求后缀 */
    public static final String SUFFIX_DO = ".do";

    /** 后台系统admin的id */
    public static final int USERID_ADMIN = 3;

    public static final String CURRENTHOLD_TYPE = "currentHold";
    public static final String REPAYMENT_TYPE = "repayment";
    public static final String CREDITRECORD_TYPE = "creditRecord";

    public static final String HOLD_PLAN_TYPE = "holdPlan";
    public static final String REPAYMENT_PLAN_TYPE = "repayMentPlan";

    /** 服务地址 */
//    @Value("${hyjf.web.host}")
    public static String HOST = "";
//    public static final String HOST = PropUtils.getSystem("hyjf.web.host");
    /** 微信服务地址 */
//    @Value("${hyjf.wechat.web.host}")
    public static String WECHAT_HOST = "";
//    public static final String WECHAT_HOST = PropUtils.getSystem("hyjf.wechat.web.host");
    
    /** 服务地址 */
//    @Value("${file.domain.url}")
    private static String WEB_DOMAIN_HOST = "";
//    public static final String WEB_DOMAIN_HOST = PropUtils.getSystem("file.domain.url");
    
    /** 忘记密码地址*/
//    @Value("${hyjf.web.bank.forgetpassword}")
    public static String FORGET_PASSWORD_URL = "";
//    public static final String FORGET_PASSWORD_URL = PropUtils.getSystem("hyjf.web.bank.forgetpassword");
    
    /** 忘记密码地址*/
//    @Value("${hyjf.wechat.bank.forgetpassword}")
    private static String WECHAT_FORGET_PASSWORD_URL = "";
//    public static final String WECHAT_FORGET_PASSWORD_URL = PropUtils.getSystem("hyjf.wechat.bank.forgetpassword");
    
    /** 忘记密码callback地址*/
//    @Value("${hyjf.wechat.bank.forgetpassword.callback}")
    private static String WECHAT_FORGET_PASSWORD_CALLBACK_URL = "";
//    public static final String WECHAT_FORGET_PASSWORD_CALLBACK_URL = PropUtils.getSystem("hyjf.wechat.bank.forgetpassword.callback");
    
    /**放款请求合法性校验地址*/
//    @Value("${hyjf.web.bank.batch.loan.verify.url}")
    private static String BANK_LOAN_VERIFY_URL = "";
//    public static final String BANK_LOAN_VERIFY_URL= PropUtils.getSystem("hyjf.web.bank.batch.loan.verify.url");
    
    /**放款请求结果回调地址*/
//    @Value("${hyjf.web.bank.batch.loan.result.url}")
    private static String BANK_LOAN_RESULT_URL = "";
//    public static final String BANK_LOAN_RESULT_URL= PropUtils.getSystem("hyjf.web.bank.batch.loan.result.url");
    
    /**放款请求合法性校验地址*/
//    @Value("${hyjf.web.bank.batch.repay.verify.url}")
    private static String BANK_REPAY_VERIFY_URL = "";
//    public static final String BANK_REPAY_VERIFY_URL= PropUtils.getSystem("hyjf.web.bank.batch.repay.verify.url");
    
    /**放款请求结果回调地址*/
//    @Value("${hyjf.web.bank.batch.repay.result.url}")
    private static String BANK_REPAY_RESULT_URL = "";
//    public static final String BANK_REPAY_RESULT_URL= PropUtils.getSystem("hyjf.web.bank.batch.repay.result.url");
    
    /**批量结束债权合法性校验地址*/
//    @Value("${hyjf.web.bank.batch.creditend.verify.url}")
    private static String BATCHCREDITEND_VERIFY_URL = "";
//    public static final String BATCHCREDITEND_VERIFY_URL= PropUtils.getSystem("hyjf.web.bank.batch.creditend.verify.url");
    
    /**批量结束债权结果回调地址*/
//    @Value("${hyjf.web.bank.batch.creditend.result.url}")
    private static String BATCHCREDITEND_RESULT_URL = "";
//    public static final String BATCHCREDITEND_RESULT_URL= PropUtils.getSystem("hyjf.web.bank.batch.creditend.result.url");

    /** 活动编号 */
  /*  @Value("${hyjf.activity.id}")
    private static String ACTIVITY_ID = "";*/

    /** 开户送68代金券活动编号 */
//    @Value("${hyjf.activity.open.id}")
    private static String OPENACCOUNT_ACTIVITY_ID = "";
//    public static final String OPENACCOUNT_ACTIVITY_ID = PropUtils.getSystem("hyjf.activity.open.id");

    /** 注册送68代金券活动编号 */
//    @Value("${hyjf.activity.regist.id}")
    private static String REGIST_ACTIVITY_ID = "";
//    public static final String REGIST_ACTIVITY_ID = PropUtils.getSystem("hyjf.activity.regist.id");

    /** 出借满1000活动编号 */
//    @Value("${hyjf.activity.invest.id}")
    private static String INVEST_ACTIVITY_ID = "";
//    public static final String INVEST_ACTIVITY_ID = PropUtils.getSystem("hyjf.activity.invest.id");
    
    /** 注册送888元新手红包 */
//    @Value("${hyjf.activity.888.id}")
    public static String REGIST_888_ACTIVITY_ID = "";
//    public static final String REGIST_888_ACTIVITY_ID = PropUtils.getSystem("hyjf.activity.888.id");
    
    /** 投之家用户注册送加息券 */
//    @Value("${hyjf.activity.regist.tzj.id}")
    public static String REGIST_TZJ_ACTIVITY_ID = "";
//    public static final String REGIST_TZJ_ACTIVITY_ID = PropUtils.getSystem("hyjf.activity.regist.tzj.id");
    /** 账户分佣交易密码 */
//    @Value("${hyjf.sub.commission.password}")
    private static String SUB_COMMISSION_PASSWORD = "";
//    public static final String SUB_COMMISSION_PASSWORD =PropUtils.getSystem("hyjf.sub.commission.password");
    /** 管理费账户*/
//    @Value("${hyjf.bank.mers.account}")
    private static String HYJF_BANK_MERS_ACCOUNT = "";
//    public static final String HYJF_BANK_MERS_ACCOUNT = PropUtils.getSystem("hyjf.bank.mers.account");
    /** version=1.1.0 */
    public static final String APP_VERSION_NUM = "1.1.0";

    /** 平台常规数字格式化 */
    public static final String DF_FOR_VIEW_PATTERN = "########0.00";

    /** 平台逗号分隔数字格式化 */
    public static final String DF_FOR_VIEW_PATTERN_COMMA = "########0.00";

    /** 第三方推送用户默认密码 */
    public static final String THIRD_PARTY_PASSWORD = "hyjf.third.party.password";

    /** 页面展示钱格式化 */
    public static DecimalFormat DF_FOR_VIEW_V1 = new DecimalFormat("########0.00");

    /** 页面展示钱格式化 */
    public static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");

    /** 页面展示金额格式化（四位小数） */
    public static DecimalFormat DF_FOR_VIEW_SPEED = new DecimalFormat("#,##0.0000");

    /** 用户信息 */
    public static final String LOGIN_USER_INFO = "LOGIN_USER_INFO";

    /** 用户信息 */
    public static final String MAIN_MENU_TREE = "MAIN_MENU_TREE";

    /** 后台验证有错误，防止二次提交用 */
    public static final String FORM_HAS_ERROR = "FORM_HAS_ERROR";

    /** 删除标识 */
    public static final String FLAG_DELETE = "1";

    public static final String FLAG_NORMAL = "0";

    /** 禁用启用状态 */
    public static final int FLAG_STATUS_ENABLE = 0;

    public static final int FLAG_STATUS_DISABLE = 1;

    /** 用户属性 */
    public static final String FLAG_OPENACCOUNT_YES = "1";// 已开户

    public static final String FLAG_OPENACCOUNT_NO = "0";// 未开户

    public static final String FLAG_PUSH_YES = "1";// 开启推送

    public static final String FLAG_PUSH_NO = "0";// 未开启推送

    public static final String FLAG_BINDQUICKPAYMENT_YES = "1";// 已绑定快捷卡

    public static final String FLAG_BINDQUICKPAYMENT_NO = "0";// 未绑定快捷卡

    // PC
    public static final String CLIENT_PC = "0";

    // 微官网
    public static final String CLIENT_WECHAT = "1";

    // 安卓
    public static final String CLIENT_ANDROID = "2";

    // IOS
    public static final String CLIENT_IOS = "3";

    // 其他
    public static final String CLIENT_OTHER = "4";

    /** 提现状态 */
    // 提现默认值
    public static final int WITHDRAW_STATUS_DEFAULT = 0;
    // 审核中（处理中）
    public static final int WITHDRAW_STATUS_WAIT = 1;
    // 提现成功
    public static final int WITHDRAW_STATUS_SUCCESS = 2;
    // 提现失败
    public static final int WITHDRAW_STATUS_FAIL = 3;

    // EXCEL扩展名
    public static final String EXCEL_EXT = ".xlsx";

    public static final String FUNCTION_ID = "fid";

    // -------------- 借款预前便号 borrow_nid --------------------
    public static final String HBD_KEY = "0";

    public static final String HDD_KEY = "1";

    public static final String HXD_KEY = "2";

    public static final String HCD_KEY = "3";

    public static final String NEW_KEY = "4";

    public static final String HZL_KEY = "5";

    public static final String GYD_KEY = "6";

    public static final String HFD_KEY = "7";

    public static final String HXF_KEY = "8";

    /** 汇直投 */
    public static final String HZT = "HZT";

    public static final String HBD = "HBD";

    public static final String HDD = "HDD";

    public static final String HXD = "HXD";

    public static final String HCD = "HCD";

    public static final String NEW = "NEW";

    public static final String HZL = "HZL";

    public static final String GYD = "GYD";

    public static final String HFD = "HFD";

    /** 汇消费 */
    public static final String HXF = "HXF";

    public static final String ZXH = "ZXH";

    // -------------- 计划相关常量开始 --------------
    /** 提交审核:立即提交审核 */
    public static final String PLAN_ISAUDITS_YES = "yes";

    /** 提交审核:暂不提交审核 */
    public static final String PLAN_ISAUDITS_NO = "no";

    /** 计划状态:发起中 */
    public static final int DEBT_PLAN_STATUS_0 = 0;

    /** 计划状态:待审核 */
    public static final int DEBT_PLAN_STATUS_1 = 1;

    /** 计划状态:审核不通过 */
    public static final int DEBT_PLAN_STATUS_2 = 2;

    /** 计划状态:待开放 */
    public static final int DEBT_PLAN_STATUS_3 = 3;

    /** 计划状态:募集中 */
    public static final int DEBT_PLAN_STATUS_4 = 4;

    /** 计划状态:锁定中 */
    public static final int DEBT_PLAN_STATUS_5 = 5;

    /** 计划状态:清算中 */
    public static final int DEBT_PLAN_STATUS_6 = 6;

    /** 计划状态:清算中-完成 */
    public static final int DEBT_PLAN_STATUS_7 = 7;

    /** 计划状态:清算完成 */
    public static final int DEBT_PLAN_STATUS_8 = 8;

    /** 计划状态:未还款 */
    public static final int DEBT_PLAN_STATUS_9 = 9;

    /** 计划状态:还款中 */
    public static final int DEBT_PLAN_STATUS_10 = 10;

    /** 计划状态:还款完成 */
    public static final int DEBT_PLAN_STATUS_11 = 11;

    /** 计划状态:流标 */
    public static final int DEBT_PLAN_STATUS_12 = 12;

    /** 汇添金专属标:Redits前缀区分 */
    public static final String DEBT_REDITS = "DEBT";

    // -------------- 计划相关常量结束 --------------

    // -------------- 数据字典 --------------------
    /** 客户端 */
    public static final String CLIENT = "CLIENT";

    /** 提现确认状态 */
    public static final String WITHDRAW_STATUS = "WITHDRAW_STATUS";

    /** 借款的状态 */
    public static final String BORROW_STATUS = "BORROW_STATUS";
    
    /** 借款的备案状态 */
    public static final String REGIST_STATUS = "REGIST_STATUS";
    
    /** 借款的备案状态 */
    public static final String VERIFY_STATUS = "VERIFY_STATUS";

    /** 银行审核-待提审 */
    public static final Integer BANK_BORROW_STATUS_4 = 4;

    /** 银行审核-自动审核中 */
    public static final Integer BANK_BORROW_STATUS_5 = 5;

    /** 银行审核-待上传证照 */
    public static final Integer BANK_BORROW_STATUS_6 = 6;

    /** 银行审核-手动审核中 */
    public static final Integer BANK_BORROW_STATUS_7 = 7;

    /** 银行审核-审核失败 */
    public static final Integer BANK_BORROW_STATUS_8 = 8;

    /** 银行审核-状态异常 */
    public static final Integer BANK_BORROW_STATUS_9 = 9;

    /** 银行审核-提审通过,初审 */
    public static final Integer BANK_BORROW_STATUS_0 = 0;

    /** 放款的状态 */
    public static final String BORROW_RECOVER = "BORROW_RECOVER";
    
    /** 放款的状态 */
    public static final String LOAN_STATUS = "LOAN_STATUS";

    /** 性别 */
    public static final String SEX = "SEX";

    /** 审核状态 */
    public static final String BORROW_APPLY_STATUS = "BORROW_APPLY_STATUS";

    /** 房屋类型 */
    public static final String HOUSES_TYPE = "HOUSES_TYPE";

    /** 公司规模 */
    public static final String COMPANY_SIZE = "COMPANY_SIZE";

    /** 标的类型 */
    public static final String BORROW_TARGET_TYPE = "BORROW_TARGET_TYPE";

    /** 标的产品类型 */
    public static final String BORROW_TARPROC_TYPE = "BORROW_TARPROC_TYPE";

    /** 天标还是月标 */
    public static final String ENDDAY_MONTH = "ENDDAY_MONTH";

    /** 汇直投 汇消费 */
    public static final String BORROW_PROJTCT = "BORROW_PROJTCT";

    /** 汇转让状态 */
    public static final String CREDIT_STATUS = "CREDIT_STATUS";

    /** 汇添金汇转让状态 */
    public static final String DEBT_CREDIT_STATUS = "DEBT_CREDIT_STATUS";

    /** 汇天金承接方式 */
    public static final String CREDIT_ASSIGN_TYPE = "PLAN_ASSIGN_TYPE";

    /** 保证金费率 */
    public static final String BORROW_BAIL_RATE = "BORROW_BAIL_RATE";

    /** 子账户类型 */
    public static final String SUB_ACCOUNT_CLASS = "SUB_ACCOUNT_CLASS";
    /** 汇计划债转状态 */
    public static final String HJH_DEBT_CREDIT_STATUS = "HJH_DEBT_CREDIT_STATUS";
    /** 汇计划债转还款状态 */
    public static final String  HJH_DEBT_REPAY_STATUS = "HJH_DEBT_REPAY_STATUS";

    // -------------- 数据字典 --------------------

    /** 白海燕账户id */
    public static int BAI_USERID = 385;

    /** 对公账户id **/

    /******** 前端数据状态 start *******/
    /** 返回状态 */
    public static final String RESULT_FLAG = "resultFlag";

    /** 返回状态成功 */
    public static final String RESULT_SUCCESS = "0";

    /** 返回状态失败 */
    public static final String RESULT_FAIL = "1";

    /** 返回状态 */
    public static final String DATA = "data";

    /** 最大时间 */
    public static final String MAX_VALIDTIME = "maxValidTime";

    /** 返回状态 */
    public static final String MSG = "msg";

    /** 注册状态 */
    public static final String REG_SETP = "reg_setp";

    /******** 前端数据状态 end ****************/

    /** 用户userId */
    public static final String REG_USERID = "connection";

    /******** 前端数据状态 end ****************/
    /** 时间戳 */
    public static final String REG_TIMESTAMP = "timestamp";

    /** 时间戳 */
    public static final String OPEN_ACCOUNT_URL = "openaccountUrl";

    /** 自动任务操作ID */
    // 定时发标
    public static final String OPERATOR_AUTO_TENDER = "010";

    // 拆分标的的定时发标
    public static final String OPERATOR_AUTO_SPLITTENDER = "011";

    // 自动复审
    public static final String OPERATOR_AUTO_REVIEW = "020";

    // 自动放款
    public static final String OPERATOR_AUTO_LOANS = "030";

    // 自动还款
    public static final String OPERATOR_AUTO_REPAY = "040";

    // 汇天力定时任务
    public static final String OPERATOR_AUTO_HTL = "050";

    /** 交易类型 */
    // 服务费
    public static final String TRADE_LOANFEE = "LOANFEE";
    public static final String TRADE_LEDGER_OUT = "fee_share_out";//手续费分账 转出人
    public static final String TRADE_LEDGER_IN = "fee_share_in";//手续费分账 收账人
    public static final String TRADE_LEDGER_REMARK = "分账";
    // 定时发放vip礼包
    public static final String OPERATOR_AUTO_GIFT = "060";

    public static final String TRADE_LOANFEE_NM = "融资服务费";

    // 汇添金服务费
    public static final String HTJ_TRADE_LOANFEE = "HTJLOANFEE";

    public static final String HTJ_TRADE_LOANFEE_NM = "汇添金服务费-还款";

    // 管理费
    public static final String TRADE_REPAYFEE = "REPAYFEE";
    // 利息
    public static final String TRADE_INTEREST = "ACCOUNT_INTEREST";
    
    public static final String TRADE_INTEREST_NM = "存管收益";

    // 融通宝
    public static final String TRADE_REPAY = "INCREASE_INTEREST_REPAY_YES";

    public static final String TRADE_REPAY_NM = "产品加息收益";

    public static final String TRADE_REPAYFEE_NM = "账户管理费";

    public static final String TRADE_REPAYFEE_PLAN_NM = "分期方式管理费";

    // 充值手续费返还
    public static final String TRADE_RECHAFEE = "RECHAFEE";

    public static final String TRADE_RECHAFEE_NM = "充值返现";

    public static final String TRADE_RECHAFEE_REMARK = "充值手续费返还";

    public static final String TRADE_RECHAFEE_DF = "充值垫付手续费";

    // 服务费
    public static final String TRADE_TGTC = "BORROW_SPREADS_TENDER";

    // 汇添金提成
    public static final String TRADE_HTJTC = "HTJTC";

    public static final String TRADE_HTJTC_NM = "汇添金计划提成";

    public static final String TRADE_HTJTC_REMARK = "汇添金计划提成";

    // 用户转账
    public static final String TRADE_TRANSFER = "TRANSFER";

    public static final String TRADE_TRANSFER_NM = "用户转账";

    public static final String TRADE_TGTC_NM = "出借推广提成";

    public static final String TRADE_TGTC_REMARK = "出借推广提成";

    // 充值手续费转账
    public static final String TRADE_RECHARGE_FEE_TRANSFER = "RECHARGE_FEE_TRANSFER";

    public static final String TRADE_RECHARGE_FEE_TRANSFER_NM = "充值手续费转账";

    /** 收支类型 */
    // 收入
    public static final int TYPE_IN = 1;

    // 支出
    public static final int TYPE_OUT = 2;

    /** 还款类型 */
    // 等额本息
    public static final String BORROW_STYLE_MONTH = "month";

    // 等额本金
    public static final String BORROW_STYLE_PRINCIPAL = "principal";

    // 按月计息，到期还本还息
    public static final String BORROW_STYLE_END = "end";

    // 按天计息，到期还本还息
    public static final String BORROW_STYLE_ENDDAY = "endday";

    // 先息后本
    public static final String BORROW_STYLE_ENDMONTH = "endmonth";

    /** 通用状态码 */
    public static final String SIGN_ERROR = "707";// sign异常

    /** 债转全部转让成功 */
    public static final String PARAM_TPL_ZZQBZRCG = "TPL_ZZQBZRCG";

    /** 债转部分转让成功 */
    public static final String PARAM_TPL_ZZBFZRCG = "TPL_ZZBFZRCG";

    /** 债转到期 */
    public static final String PARAM_TPL_ZZDQ = "TPL_ZZDQ";

    public static final String TOKEN_ERROR = "708";// token验证异常

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

    /** 资金相关-优惠券投标成功 */
    public static final String PARAM_TPL_COUPON_TENDER = "TPL_COUPON_TENDER";
    
    /** 优惠券相关-注册送188元新手红包 */
    public static final String PARAM_TPL_TZJ_188HB = "tzj_188hb";
    
    /** 优惠券相关-代金券3日失效短信通知 */
    public static final String PARAM_TPL_THREE_DEADLINE = "djq_deadline_3";
    
    /** 优惠券相关-代金券1日失效短信通知 */
    public static final String PARAM_TPL_ONE_DEADLINE = "djq_deadline_1";

    /** 资金相关-充值成功 */
    public static final String PARAM_TPL_CHONGZHI_SUCCESS = "TPL_CHONGZHI_SUCCESS";

    /** 资金相关-优惠券收到还款 */
    public static final String PARAM_TPL_COUPON_PROFIT = "TPL_COUPON_PROFIT";

    /** 资金相关-收到产品加息还款 */
    public static final String PARAM_TPL_JIAXIHUANKUAN = "TPL_JIAXIHUANKUAN";

    /** 资金相关-提现成功 */
    public static final String PARAM_TPL_TIXIAN_SUCCESS = "TPL_TIXIAN_SUCCESS";

    /** 资金相关-投标成功 */
    public static final String PARAM_TPL_TOUZI_SUCCESS = "TPL_TOUZI_SUCCESS";
    
    /** 资金相关-汇计划投标成功 */
    public static final String PARAM_TPL_TOUZI_HJH_SUCCESS = "TPL_TOUZI_HJH_SUCCESS";
    
    /** 资金相关-汇计划投标成功 */
    public static final String PARAM_TPL_REPAY_HJH_SUCCESS = "TPL_REPAY_HJH_SUCCESS";
    
    /**资金相关-分期投标成功*/
    public static final String PARAM_TPL_TOUZI_PLAN_SUCCESS = "TPL_TOUZI_PLAN_SUCCESS";

    /** 资金相关-收到还款 */
    public static final String PARAM_TPL_SHOUDAOHUANKUAN = "TPL_SHOUDAOHUANKUAN";

    /** 资金相关-收到还款（提前还款） */
    public static final String PARAM_TPL_SHOUDAOHUANKUAN_TIQIAN = "TPL_SHOUDAOHUANKUAN_TIQIAN";

    /** 还款前三天短信提醒 */
    public static final String PARAM_TPL_HUANKUANTIXING = "TPL_HUANKUANTIXING";

    /** 资金相关-产品加息放款 */
    public static final String PARAM_TPL_JIAXIFANGKUAN = "TPL_JIAXIFANGKUAN";

    /** 还款日当天提醒 */
    public static final String PARAM_TPL_HUANKUANGUOQI = "TPL_HUANKUANGUOQI";

    /** 汇转让-转让结束 */
    public static final String PARAM_TPL_ZHUANRANGJIESHU = "TPL_ZHUANRANGJIESHU";

    /** 加息券还款余额不足 */
    public static final String PARAM_TPL_COUPON_JIA_YUE = "TPL_COUPON_JIA_YUE";

    /** 充值垫付手续费余额不足 */
    public static final String PARAM_TPL_RECHARGE_YUE = "TPL_RECHARGE_YUE";

    /** 平台转账-收到推广提成 */
    public static final String PARAM_TPL_SDTGTC = "TPL_SDTGTC";

    /** 平台转账-收到活动奖励 */
    public static final String PARAM_TPL_SDHDJL = "TPL_SDHDJL";

    /** 平台转账-收到手续费返现 */
    public static final String PARAM_TPL_SDSXFFX = "TPL_SDSXFFX";

    /** 管理员注册-新增管理员密码提醒 */
    public static final String PARAM_TPL_GLYMM = "TPL_GLYMM";

    /** 用户管理-重置密码提醒 */
    public static final String PARAM_TPL_RESETPWD = "TPL_RESETPWD";

    /** 发标提醒-自动发标 */
    public static final String PARAM_TPL_ZDFB = "TPL_ZDFB";

    /** 发标提醒-定时发标 */
    public static final String PARAM_TPL_DSFB = "TPL_DSFB";

    /** 项目结标提醒-项目满标 */
    public static final String PARAM_TPL_XMMB = "TPL_XMMB";

    /** 汇添金计划进入锁定中 */
    public static final String HTJ_PARAM_TPL_XMMB = "JYTZ_HTJ_DEAL_SUCCESS";

    /** 汇添金计划收到还款 */
    public static final String HTJ_PARAM_TPL_SDHK = "JYTZ_HTJ_EXIST_SUCCESS";

    /** 汇添金计划满标 */
    public static final String HTJ_PARAM_TPL_JHMB = "JYTZ_HTJ_SUCCESS";

    /** 汇添金计划未满 */
    public static final String HTJ_PARAM_TPL_JHWM = "JYTZ_HTJ_FAILED";

    /**计划订单异常*/
    public static final String JYTZ_PLAN_ORDER_EXCECEPTION = "JYTZ_PLAN_ORDER_EXCEPTION";

    /** 消息推送模板-加息放款 */
    public static final String JYTZ_TPL_JIAXIFANGKUAN = "JYTZ_TPL_JIAXIFANGKUAN";

    /** 项目结标提醒-项目到期 */
    public static final String PARAM_TPL_XMDQ = "TPL_XMDQ";

    /** 自动放款提醒-放款成功 */
    public static final String PARAM_TPL_FANGKUAN_SUCCESS = "TPL_FANGKUAN_SUCCESS";

    /** 自动放款提醒-借款人-放款成功 */
    public static final String PARAM_TPL_JIEKUAN_SUCCESS = "TPL_JIEKUAN_SUCCESS";

    /** 自动放款提醒-放款修复异常 */
    public static final String PARAM_TPL_FANGKUAN_SHIBAI = "TPL_FANGKUAN_SHIBAI";

    /** 投资撤销 */
    public static final String PARAM_TPL_BIDCANCEL = "TPL_BIDCANCEL";

    /** 计划即将清算（二期） */
    public static final String JYTZ_HTJ_JJQS = "JYTZ_HTJ_JJQS";

    /** 汇添金计划自动还款提醒-还款成功 */
    public static final String JYTZ_HTJ_HKCG = "JYTZ_HTJ_HKCG";

    /** 自动放款提醒-放款失败 */
    public static final String PARAM_TPL_FANGKUAN_FAILD = "TPL_FANGKUAN_FAILD";

    /** 还款完成提醒-还款完成 */
    public static final String PARAM_TPL_HUANKUAN_SUCCESS = "TPL_HUANKUAN_SUCCESS";

    /** 关联资产不足，手动出借警报邮件模板编码 */
    public static final String PARAM_TPL_JYTZ_HTJ_SDTZ = "JYTZ_HTJ_SDTZ";

    /** 计划清算未完成，报警模版 */
    public static final String PARAM_TPL_JYTZ_HTJ_QSFAILED = "JYTZ_HTJ_QSFAILED";

    /** 资产部分募集警报 邮件模板编码 */
    public static final String PARAM_TPL_JYTZ_HTJ_ZCBFMJ = "JYTZ_HTJ_ZCBFMJ";

    /** 计划清算完成，即将还款 短信模板编码 */
    public static final String PARAM_TPL_JYTZ_HTJ_JJHK = "JYTZ_HTJ_JJHK";

    /** 关联资产不足，手动出借警报邮件模板编码 */
    public static final String EMAILPARAM_TPL_JYTZ_HTJ_SDTZ = "JYTZ_HTJ_SDTZ";

    /** 资产部分募集警报 邮件模板编码 */
    public static final String EMAILPARAM_TPL_JYTZ_HTJ_ZCBFMJ = "JYTZ_HTJ_ZCBFMJ";

    /** 计划已经清算完成 邮件模板编码 */
    public static final String EMAILPARAM_TPL_JYTZ_HTJ_JJHK = "JYTZ_HTJ_JJHK";

    /** 计划清算未完成，报警模版 */
    public static final String EMAILPARAM_TPL_JYTZ_HTJ_QSFAILED = "JYTZ_HTJ_QSFAILED";

    /** 自动放款发送邮件 */
    public static final String EMAILPARAM_TPL_LOANS = "TPL_EMAIL_LOANS";

    /** 汇计划自动发标修复 */
    public static final String EMAILPARAM_TPL_EMAIL_AUTOISSUERECOVER = "TPL_EMAIL_AUTOISSUERECOVER";

    /** 计划订单状态由投标成功变为锁定中，发送此邮件提醒用户投标成功 */
    public static final String EMAITPL_EMAIL_LOCK_REPAY = "TPL_EMAIL_LOCK_REPAY";

    /** 汇添金计划进入锁定中 */
    public static final String EMAILPARAM_JYTZ_HTJ_DEAL = "JYTZ_HTJ_DEAL_SUCCESS";

    /** 企业用户定向转账 */
    public static final String PARAM_TPL_DXZZ = "TPL_DXZZ";

    /** 还款完成提醒-还款失败 */
    public static final String PARAM_TPL_HUANKUAN_FAILD = "TPL_HUANKUAN_FAILD";

    /** 汇天利-资管公司余额小于100万 */
    public static final String PARAM_TPL_ZJYEXYYBW = "TPL_ZJYEXYYBW";

    /** 用户开户出现手机号重复发送邮件 */
    public static final String EMAILPARAM_TPL_REPEATMOBILE = "TPL_REPEATMOBILE";

    public static final String POST = "POST";

    /** 自动转账后小于最小金额发送邮件 */
    public static final String EMAILPARAM_TPL_LOWLIMIT = "TPL_LOWLIMIT";

    /** 异常-汇天利异常 */
    public static final String PARAM_TPL_HTLYICHANG = "TPL_HTLYICHANG";

    /** 用户转账 */
    public static final String PARAM_TPL_TRANSFER = "PARAM_TPL_TRANSFER";

    /** 充值手续费对账 14天延期 */
    public static final String PARAM_RECHARGE_FEE_RECONCILIATION_DELAY14 = "RECHARGE_FEE_DELAY14";

    /** 充值手续费对账 30天延期 */
    public static final String PARAM_RECHARGE_FEE_RECONCILIATION_DELAY30 = "RECHARGE_FEE_DELAY30";

    /** 借款人充值手续费重复支付 */
    public static final String PARAM_RECHARGE_FEE_REPEAT = "RECHARGE_FEE_REPEAT";

    /** 商户余额不足 */
    public static final String PARAM_TPL_SHYEBZ = "TPL_SHYEBZ";
    
    /** 红包账户余额提醒 */
    public static final String PARAM_RED_PACKET = "RED_PACKET";

    /** 异常-短信超限 */
    public static final String PARAM_TPL_DUANXINCHAOXIAN = "TPL_DUANXINCHAOXIAN";

    /** 邮件模板 */
    /** 短信超限 */
    public static final String EMAILPARAM_TPL_DUANXINCHAOXIAN = "TPL_DUANXINCHAOXIAN";

    /** 绑定邮箱 */
    public static final String EMAILPARAM_TPL_BINDEMAIL = "TPL_BINDEMAIL";
    
    /** 标的还款逾期短信通知*/
    public static final String PARAM_TPL_NOTICE_BORROW_REPAY_OVERDUE = "TPL_BORROW_REPAY_OVERDUE";
    
    /** 汇计划备案失败短信通知*/
    public static final String PARAM_TPL_NOTICE_BORROW_RECORD_FAIL = "TPL_BORROW_RECORD_FAIL";

    /** 汇天利 借款手续费率 */
    public static final String HTL_BORROWERRATE = "0.30";

    /** 汇天利 最大出借手续费率 */
    public static final String HTL_MAXTENDERRATE = "0.00";
    
    /** 计划加入协议邮件模板 */
    public static final String HJD_JOIN_AGREEMENT = "HJD_JOIN_AGREEMENT";

    /**
     * app
     * 所用接口数据常量定义------------------------------------------------------------
     * ---------------start
     */
    /** 通讯状态key */
    public static final String APP_STATUS = "status";

    /** 通讯状态-成功 value */
    public static final String APP_STATUS_SUCCESS = "0";

    /** 通讯状态-失败 val */
    public static final String APP_STATUS_FAIL = "1";

    /** APP特定错误码，慎用(特殊场景使用) */
    public static final String APP_STATUS_OTHER_FAIL = "2";

    /** 通讯状态描述key */
    public static final String APP_STATUS_DESC = "statusDesc";

    /** 通讯状态描述 common */
    public static final String APP_STATUS_DESC_SUCCESS = "成功";

    /** 通讯状态描述 common */
    public static final String APP_STATUS_DESC_FAIL = "失败";

    /** 通讯请求url key */
    public static final String APP_REQUEST = "request";

    /** 分页定义 */
    /** 分页-当前为第几页，默认为第一页，不可为0 */
    public static final String APP_PAGE = "page";

    /** 分页大小-默认为10 */
    public static final String APP_PAGESIZE = "pageSize";

    /** 前端关键字定义 */
    /** 版本号字段定义 */
    public static final String APP_VERSION = "version";

    /** 网络状态 */
    public static final String APP_NET_STATUS = "netStatus";

    /** 平台（Android或iOS）0 pc 1 安卓 2ios 3微信 */
    public static final String APP_PLATFORM = "platform";

    /** 令牌 */
    public static final String APP_TOKEN = "token";

    /** 唯一标识 */
    public static final String APP_SIGN = "sign";

    /** 随机字符串 */
    public static final String APP_RANDOM_STRING = "randomString";

    /** 随机字符串 */
    public static final String APP_ORDER = "order";

    /** 请求参数连接符 */
    public static final String APP_PARM_AND = "&";

    public static final String GET = "GET";

    /** app全局异常页面路径 */
    public static final String ERROR_PAGE = "/jsp/error.jsp";

    /** app系统异常view */
    public static final String ERROR = "/error";

    /** app sign拦截器 */
    public static final String SIGN_INTERCEPTOR = "signInterceptor";

    /** app token拦截器 */
    public static final String TOKEN_INTERCEPTOR = "tokenInterceptor";

    /** 定向转账授权金额 */
    public static final String DIRECT_AUTHAMT = "50000000000.00";

    /** app order拦截器 */
    public static final String ORDER_INTERCEPTOR = "orderInterceptor";

    /** web session超时周期 */
    public static final int SESSION_EXPIRE = 1800;

    /** sessionId */
    public static final String SESSIONID = "sessionId";

    /** 私有域加密key */
    public static final String MERPRIV_KEY = "wangkun1";

    public static final String UTF8 = "UTF-8";

    /** 合同生成配置 */
    /** 出借合同生成配置 */
    public static final String TENDER_CONTRACT = "Tender";
    
    
    public static final String NEW_TENDER_CONTRACT = "NewTender";
    
    public static final String NEW_DIARY_CONTRACT = "NewDiary";
   
    /** 合同生成ftl配置 */
    public static final String CONTRACT_FTL_PATH = "hyjf.contract.ftlpath";

    /** 出借合同生成ftl名称 */
    public static final String CREDIT_CONTRACT_FTL_NAME = "credit.contract.ftl.name";

    /** 债转生成ftl名称 */
    public static final String HJH_CREDIT_CONTRACT_FTL_NAME = "credit.hjh.contract.ftl.name";

    /** 债转合同生成配置 */
    public static final String CREDIT_CONTRACT = "Credit";

    /** 债转合同生成配置 */
    public static final String HJH_CREDIT_CONTRACT = "HJHCredit";

    /** 计划债转合同生成配置 */
    public static final String PLAN_CREDIT_CONTRACT = "PlanCredit";

    /** 借款人协议配置 */
    public static final String BORROWER_CONTRACT = "Borrower";

    /** 借款人汇消费协议配置 */
    public static final String BORROWER_CONTRACT_HXF = "BorrowerHxf";

    /** 债转合同生成ftl名称 */
    public static final String TENDER_CONTRACT_FTL_NAME = "tender.contract.ftl.name";
    
    /** 债转合同生成ftl名称 */
    public static final String NEW_TENDER_CONTRACT_FTL_NAME = "tender.newcontract.ftl.name";

    /** 计划债转合同生成ftl名称 */
    public static final String PLAN_CREDIT_CONTRACT_FTL_NAME = "plan.credit.contract.ftl.name";

    /** 借款人协议 */
    public static final String BORROWER_CONTRACT_FTL_NAME = "borrower.contract.ftl.name";

    /** 借款人汇消费协议 */
    public static final String BORROWER_HXF_CONTRACT_FTL_NAME = "borrower.hxf.contract.ftl.name";

    /** 合同生成字体配置 */
    public static final String CONTRACT_FONT_PATH = "hyjf.contract.font";

    /** 合同生成路径配置 */
    public static final String CONTRACT_PDF_PATH = "hyjf.makepdf.path";

    /** 合同访问路径配置 */
    public static final String CONTRACT_TEMPPDF_PATH = "hyjf.temppdf.path";

    public static final String TRADE_COUPON_DJ = "代金券回款";

    /** 汇添金出借计划服务协议生成配置 */
    public static final String HTJ_TENDER_CONTRACT = "HtjTender";

    /** 汇添金我的汇添金内服务协议生成配置 */
    public static final String HTJ_INVEST_CONTRACT = "HtjInvest";
    
    
    /** 汇添金出借计划服务协议生成ftl名称 */
    public static final String HTJ_TENDER_CONTRACT_FTL_NAME = "htj.tender.contract.ftl.name";

    /** 汇添金我的汇添金内服务协议生成ftl名称 */
    public static final String HTJ_INVEST_CONTRACT_FTL_NAME = "htj.invest.contract.ftl.name";
    /** 新汇计划服务协议生成配置 */
    public static final String NEW_HJH_INVEST_CONTRACT = "NewHjhInvest";

    /** 新汇计划服务协议生成ftl名称 */
    public static final String NEW_HJH_INVEST_CONTRACT_FTL_NAME = "new.hjh.invest.contract.ftl.name";
    
    /** 新汇计划借款生成ftl名称 */
    public static final String NEW_HJH_DIARY_FTL_NAME = "new.hjh.diary.ftl.name";

    /** 嘉诺融通宝协议 */
    public static final String RTB_TENDER_CONTRACT = "RTBTender";

    /** 中商储融通宝协议 */
    public static final String RTB_TENDER_CONTRACT_ZSC = "RTBTenderZSC";

    /** 嘉诺融通宝协议模板 */
    public static final String RTB_CONTRACT_FTL_NAME = "rtb.contract.ftl.name";

    /** 中商储融通宝协议模板 */
    public static final String RTB_CONTRACT_ZSC_FTL_NAME = "rtbzsc.contract.ftl.name";

    /** 优惠券相关常量 */
    /** 删除标识 */
    public static final int FALG_DEL = 1;

    public static final int FALG_NOR = 0;

    /** 转账异常状态成功 */
    public static final int TRANSFER_EXCEPTION_STATUS_YES = 0;

    /** 转账异常状态失败 */
    public static final int TRANSFER_EXCEPTION_STATUS_NOT = 1;

    /** 转账异常状态处理中 */
    public static final int TRANSFER_EXCEPTION_STATUS_WAIT = 2;

    /** 借款的状态 */
    public static final String COUPON_RECIVE_STATUS = "COUPON_RECIVE_STATUS";
    
    /** 货币种类 */
    public static final String CURRENCY_STATUS = "CURRENCY_STATUS";

    /** 借款用途 */
    public static final String FINANCE_PURPOSE = "FINANCE_PURPOSE";

    /** 岗位职业 */
    public static final String POSITION = "POSITION";

    // 优惠券（体验金）
    public static final String TRADE_COUPON_TYJ = "EXPERIENCE_PROFIT";

    // 优惠券（加息券）
    public static final String TRADE_COUPON_JXQ = "INCREASE_INTEREST_PROFIT";

    // 优惠券（代金券）
    public static final String TRADE_COUPON_DJQ = "CASH_COUPON_PROFIT";

    public static final String TRADE_COUPON_HK = "加息券回款";

    public static final String TRADE_COUPON_SY = "体验金收益";

    // 购买vip
    public static final String APPLY_VIP = "APPLY_VIP";

    // ==================消息推送状态==================
    public static final String APPLY_VIP_REMARK = "购买会员";

    /** 消息推送模板-平台奖励-收到推广提成 */
    public static final String JYTZ_TPL_SDTGTC = "JYTZ_TPL_SDTGTC";

    /** 消息推送模板-平台奖励-收到活动奖励 */
    public static final String JYTZ_TPL_SDHDJL = "JYTZ_TPL_SDHDJL";

    /** 消息推送模板-平台奖励-收到手续费返现 */
    public static final String JYTZ_TPL_SDSXFFX = "JYTZ_TPL_SDSXFFX";

    /** 消息推送模板-充值成功 */
    public static final String JYTZ_TPL_CHONGZHI_SUCCESS = "JYTZ_TPL_CHONGZHI_SUCCESS";

    /** 消息推送模板-提现成功 */
    public static final String JYTZ_TPL_TIXIAN_SUCCESS = "JYTZ_TPL_TIXIAN_SUCCESS";

    /** 消息推送模板-提现失败 */
    public static final String JYTZ_CASH_FAILED = "JYTZ_CASH_FAILED";

    /** 消息推送模板-投标成功 */
    public static final String JYTZ_TPL_TOUZI_SUCCESS = "JYTZ_TPL_TOUZI_SUCCESS";
    
    /** 消息推送模板-汇计划自动投标成功 */
    public static final String JYTZ_PLAN_TOUZI_SUCCESS = "JYTZ_PLAN_TOUZI_SUCCESS";
    
    /**计划还款申请全部成功*/
    public static final String JYTZ_PLAN_REPAYALL_SUCCESS = "JYTZ_PLAN_REPAYALL_SUCCESS";
    
    /**计划还款申请部分成功*/
    public static final String JYTZ_PLAN_REPAYPART_SUCCESS = "JYTZ_PLAN_REPAYPART_SUCCESS";
    
    /** 消息推送模板-汇计划自动回款成功 */
    public static final String JYTZ_PLAN_REPAY_SUCCESS = "JYTZ_PLAN_REPAY_SUCCESS";
    
    /** 消息推送模板-汇计划进入锁定期 */
    public static final String JYTZ_PLAN_LOCK_SUCCESS = "JYTZ_PLAN_LOCK_SUCCESS";

    /** 消息推送模板-收到还款 */
    public static final String JYTZ_TPL_SHOUDAOHUANKUAN = "JYTZ_TPL_SHOUDAOHUANKUAN";

    /** 消息推送模板-转让结束 */
    public static final String JYTZ_TPL_ZHUANRANGJIESHU = "JYTZ_TPL_ZHUANRANGJIESHU";

    /** 消息推送模板-收到转让金额 */
    public static final String JYTZ_TPL_SDZRJE = "JYTZ_TPL_SDZRJE";

    /** 消息推送模板-承接债权 */
    public static final String JYTZ_TPL_CJZQ = "JYTZ_TPL_CJZQ";

    /** 消息推送模板-承接债权还款 */
    public static final String JYTZ_TPL_CJZQHK = "JYTZ_TPL_CJZQHK";

    /** 消息推送模板-优惠券到账成功 */
    public static final String JYTZ_COUPON_SUCCESS = "JYTZ_COUPON_SUCCESS";

    /** 消息推送模板-用券成功 */
    public static final String JYTZ_COUPON_TENDER = "JYTZ_COUPON_TENDER";

    /** 消息推送模板-优惠券收益到账成功 */
    public static final String JYTZ_COUPON_PROFIT = "JYTZ_COUPON_PROFIT";

    /** 消息推送模板-优惠券即将失效 */
    public static final String JYTZ_COUPON_DEADLINE = "JYTZ_COUPON_DEADLINE";

    /** 消息推送模板-优惠券已失效 */
    public static final String JYTZ_COUPON_INVALIDED = "JYTZ_COUPON_INVALIDED";
    /** 消息推送模板-投资撤销 */
    public static final String JYTZ_TPL_BIDCANCEL = "JYTZ_TPL_BIDCANCEL";
    
    /** 消息推送模板-第三方接口注册用户密码发送*/
    public static final String THIRD_PARTY_REGIEST_PASSWORD = "THIRD_PARTY_REGIEST_PASSWORD";

    public static final String MSG_PUSH_PACKAGE_CODE_39 = "39";

    /** 消息推送-设备所属渠道 */
    public static final String MSG_PUSH_PACKAGE_CODE_79 = "79";

    /** 消息推送-设备所属渠道-专业版 */
    public static final String MSG_PUSH_PACKAGE_CODE_ZYB = "150";

    /** 消息推送-设备所属渠道-至尊版 */
    public static final String MSG_PUSH_PACKAGE_CODE_ZZB = "151";

    /** 消息推送-设备所属渠道-悦享版 */
    public static final String MSG_PUSH_PACKAGE_CODE_YXB = "153";

    /** 消息推送-设备所属渠道-周年版 */
    public static final String MSG_PUSH_PACKAGE_CODE_ZNB = "152";
    
    /** 消息推送-设备所属渠道-测试用*/
    public static final String MSG_PUSH_PACKAGE_CODE_TEST = "149";

    /** 操作履历 新增 */
    public static final int OPERATION_CODE_INSERT = 1;

    /** 操作履历 修改 */
    public static final int OPERATION_CODE_MODIFY = 2;

    /** 操作履历 删除 */
    public static final int OPERATION_CODE_DELETE = 3;

    /** 优惠券发行状态 待审核 */
    public static final int COUPON_STATUS_WAITING_PUBLISH = 1;

    /** 优惠券发行状态 审核通过 */
    public static final int COUPON_STATUS_PUBLISHED = 2;

    /** 优惠券发行状态 审核未通过 */
    public static final int COUPON_STATUS_NOCHECKED = 3;

    /** 用户优惠券状态 待审核 */
    public static final int USER_COUPON_STATUS_WAITING_PUBLISH = 3;

    /** 用户优惠券状态 未使用 */
    public static final int USER_COUPON_STATUS_UNUSED = 0;

    /** 用户优惠券状态 已使用 */
    public static final int USER_COUPON_STATUS_USED = 1;

    /** 用户优惠券状态 审核未通过 */
    public static final int USER_COUPON_STATUS_NOCHECKED = 2;

    /** 耳机 */
    public static final String PRIZE_HEADPHONES = "P0001";

    /** pad mini */
    public static final String PRIZE_PAD_MINI = "P0002";

    /** 6sp */
    public static final String PRIZE_PHONE6_PLUS = "P0003";

    /** 预约redis标号后缀APPOINT */
    public static final String APPOINT = "appoint";

    /** 汇添金redis标号后缀PLAN */
    public static final String PLAN = "plan";

    /** 用户优惠券状态 已失效 */
    public static final int USER_COUPON_STATUS_INVALID = 4;

    /** 客户端未读 */
    public static final int USER_COUPON_READ_FLAG_NO = 0;

    /** 客户端已读 */
    public static final int USER_COUPON_READ_FLAG_YES = 1;

    /** 优惠券来源 1 手动发放 2 活动发放 3 VIP礼包 4 积分兑换*/
    public static final int USER_COUPON_SOURCE_MANUAL = 1;

    /** 优惠券来源 1 手动发放 2 活动发放 3 VIP礼包 4 积分兑换*/
    public static final int USER_COUPON_SOURCE_ACTIVE = 2;

    /** 优惠券来源 1 手动发放 2 活动发放 3 VIP礼包 4 积分兑换*/
    public static final int USER_COUPON_SOURCE_VIP = 3;

    /** 优惠券来源 1 手动发放 2 活动发放 3 VIP礼包 4 积分兑换*/
    public static final int USER_COUPON_SOURCE_INTEGRAL = 4;

    // ==================消息推送状态==================
    /** 消息推送-消息的发送状态-待发送 */
    public static final int MSG_PUSH_MSG_STATUS_0 = 0;

    /** 消息推送-消息的发送状态-已发送(无论发送成功与否都为已发送) */
    public static final int MSG_PUSH_MSG_STATUS_1 = 1;

    /** 消息推送-消息推送时间类型-立即发送 */
    public static final int MSG_PUSH_SEND_TYPE_0 = 0;

    /** 消息推送-消息推送时间类型-定时发送 */
    public static final int MSG_PUSH_SEND_TYPE_1 = 1;

    /** 消息推送-消息推送目的地类型-所有人 */
    public static final int MSG_PUSH_DESTINATION_TYPE_0 = 0;

    /** 消息推送-消息推送目的地类型-单个或多个用户 */
    public static final int MSG_PUSH_DESTINATION_TYPE_1 = 1;

    /** 消息推送-消息状态-新建 */
    public static final int MSG_PUSH_STATUS_0 = 0;

    /** 消息推送-消息状态-启用 */
    public static final int MSG_PUSH_STATUS_1 = 1;

    /** 消息推送-消息状态-禁用 */
    public static final int MSG_PUSH_STATUS_2 = 2;

    /** 消息推送-消息的发送状态-待发送 */
    public static final int MSG_PUSH_SEND_STATUS_0 = 0;

    /** 消息推送-消息的发送状态-发送成功 */
    public static final int MSG_PUSH_SEND_STATUS_1 = 1;

    /** 消息推送-消息的发送状态-发送失败 */
    public static final int MSG_PUSH_SEND_STATUS_2 = 2;

    /** 消息推送-消息模版-后续动作-打开APP */
    public static final int MSG_PUSH_TEMP_ACT_0 = 0;

    /** 消息推送-消息模版-后续动作-打开H5页面 */
    public static final int MSG_PUSH_TEMP_ACT_1 = 1;

    /** 消息推送-消息模版-后续动作-打开微信页面 */
    public static final int MSG_PUSH_TEMP_ACT_3 = 3;
    /** 消息推送-消息模版-后续动作-指定原生页面 */
    public static final int MSG_PUSH_TEMP_ACT_2 = 2;

    /** 消息推送-消息模版-后续动作,2指定原生页面-原生页面类型-APP首页 */
    public static final int MSG_PUSH_NATUREURLS_0 = 0;

    /** 消息推送-消息模版-后续动作,2指定原生页面-原生页面类型-财富汇列表-汇直投 */
    public static final int MSG_PUSH_NATUREURLS_1 = 1;

    /** 消息推送-消息模版-后续动作,2指定原生页面-原生页面类型-我的账户 */
    public static final int MSG_PUSH_NATUREURLS_2 = 2;

    /** 消息推送-消息模版-后续动作,2指定原生页面-原生页面类型-优惠券列表 */
    public static final int MSG_PUSH_NATUREURLS_3 = 3;

    /** 消息推送-消息模版-后续动作,2指定原生页面-原生页面类型-交易记录 */
    public static final int MSG_PUSH_NATUREURLS_4 = 4;

    /** 消息推送-消息模版-后续动作,2指定原生页面-原生页面类型-我的出借 */
    public static final int MSG_PUSH_NATUREURLS_5 = 5;

    /** 消息推送模板-成功购买VIP，并获得VIP资格 */
    public static final String JYTZ_VIP_BECOMEVIP = "JYTZ_VIP_BECOMEVIP";

    /** 消息推送模板-会员资格到期提醒 */
    public static final String JYTZ_VIP_DEADLINE = "JYTZ_VIP_DEADLINE";

    /** 消息推送模板-会员资格已过期提醒 */
    public static final String JYTZ_VIP_INVALIDED = "JYTZ_VIP_INVALIDED";

    /** 消息推送模板-会员等级变更提醒 */
    public static final String JYTZ_VIP_UPGRADED = "JYTZ_VIP_UPGRADED";

    /** 消息推送模板-收到加息还款 */
    public static final String JYTZ_TPL_JIAXIHUANKUAN = "JYTZ_TPL_JIAXIHUANKUAN";

    // ----------------十月份推荐星活动------------------------------
    /** 奖品类别：实物奖品 */
    public static final String CONF_PRIZE_TYPE_ENTITY = "1";

    /** 奖品类别：优惠券 */
    public static final String CONF_PRIZE_TYPE_COUPON = "2";

    /** 奖品获得方式：兑奖 */
    public static final String CONF_PRIZE_KIND_CHANGE = "1";

    /** 奖品获得方式：抽奖 */
    public static final String CONF_PRIZE_KIND_DRAW = "2";

    /** 奖品状态：启用 */
    public static final String CONF_PRIZE_STATUS_ON = "0";

    /** 奖品状态：禁用 */
    public static final String CONF_PRIZE_STATUS_OFF = "1";

    /** 奖品发放状态：人工发放 */
    public static final String PRIZE_SEND_FLAG_MANUAL = "2";

    /** 奖品发放状态：已发放 */
    public static final String PRIZE_SEND_FLAG_YES = "1";

    /** 奖品发放状态：未发放 */
    public static final String PRIZE_SEND_FLAG_NO = "0";

    /** 普通短信通道 */
    public static final String CHANNEL_TYPE_NORMAL = "normal";

    /** 营销短信通道 */
    public static final String CHANNEL_TYPE_MARKETING = "marketing";

    /** 债转还款状态 */
    public static final String DEBT_REPAY_STATUS = "DEBT_REPAY_STATUS";

    /** 借款人评级 */
    public static final String BORROW_LEVEL = "BORROW_LEVEL";

	/** 批次状态:请求中1 */
    public static final Integer BANK_BATCH_STATUS_SENDING = 1;
	
	/** 批次状态:请求成功2 */
    public static final Integer BANK_BATCH_STATUS_SENDED = 2;
    
    /** 批次状态:校验成功3 */
    public static final Integer BANK_BATCH_STATUS_VERIFY_SUCCESS = 3;
    
    /** 批次状态:校验失败 4*/
    public static final Integer BANK_BATCH_STATUS_VERIFY_FAIL = 4;

	/** 批次状态:失败5 */
    public static final Integer BANK_BATCH_STATUS_FAIL = 5;
    
    /** 批次状态:请求成功6 */
    public static final Integer BANK_BATCH_STATUS_SUCCESS = 6;

	/** 批次状态:请求失败7 */
    public static final Integer BANK_BATCH_STATUS_SEND_FAIL = 7;

	/**批次状态:请求部分失败8 */
    public static final Integer BANK_BATCH_STATUS_PART_FAIL = 8;
    
    /** 批次状态:请求处理9 */
    public static final Integer BANK_BATCH_STATUS_DOING = 9;
    
    
    /**************************************银行存管  银行账户收支类型******************************************/
    /**银行账户收支类型  收入*/
    public static final Integer BANK_MER_TYPE_INCOME = 0;
    /**银行账户收支类型  支出*/
    public static final Integer BANK_MER_TYPE_EXPENDITURE = 1;
    /**银行账户收支类型  冻结*/
    public static final Integer BANK_MER_TYPE_FROZEN = 2;
    /**银行账户收支类型  解冻*/
    public static final Integer BANK_MER_TYPE_RELEASE = 3;
    
    /**************************************银行存管  银行账户交易类型******************************************/
    /**银行账户交易类型  手动*/
    public static final Integer BANK_MER_TRANS_TYPE_MANUAL = 0;
    /**银行账户交易类型  分账*/
    public static final Integer BANK_MER_TRANS_TYPE_MANUAL_LEDGER = 2;
    /**银行账户交易类型  自动*/
    public static final Integer BANK_MER_TRANS_TYPE_AUTOMATIC = 1;
    /**************************************银行存管  银行账户交易状态******************************************/
    /**银行账户交易状态  交易成功*/
    public static final Integer BANK_MER_TRANS_STATUS_SUCCESS = 0;
    /**银行账户交易状态  交易失败*/
    public static final Integer BANK_MER_TRANS_STATUS_FAIL = 1;
    /**银行账户交易状态  交易处理中*/
    public static final Integer BANK_MER_TRANS_UNDERWAY = 2;
    
    /**************************************银行存管 出借校验返回错误码****************************/
    /**未开户*/
    public static final String BANK_TENDER_RETURN_OPENACCOUNT_FAIL = "708";
    /**未设置交易密码*/
    public static final String BANK_TENDER_RETURN_SETPWD_FAIL = "709";
    /**未测评*/
    public static final String BANK_TENDER_RETURN_ANSWER_FAIL = "710";
    /**未获得自动授权*/
    public static final String BANK_TENDER_RETURN_AUTH_FAIL = "711";
    /**未获得自动授权*/
    public static final String BANK_TENDER_RETURN_AUTH_CREDIT_FAIL = "712";
    /**未获得自动授权--dzs*/
    public static final String BANK_TENDER_RETURN_AUTH_ALL_FAIL = "713";
    /**测评过期*/
    public static final String BANK_TENDER_RETURN_ANSWER_EXPIRED = "714";
    /**测评限额超额(原系统715被占用改为719：未获得缴费授权 BANK_TENDER_RETURN_AUTH_PAYMENT_FAIL)*/
    public static final String BANK_TENDER_RETURN_LIMIT_EXCESS = "715";
    /**测评标的用户类型不匹配*/
    public static final String BANK_TENDER_RETURN_CUSTOMER_STANDARD_FAIL = "716";
    /**测评限额超额（代收本金）*/
    public static final String BANK_TENDER_RETURN_LIMIT_EXCESS_PRINCIPAL = "717";
    /**测评标的等级（转让）*/
    public static final String TENDER_CHECK_LEVE_HZR = "HZR";
    /**测评标的等级（计划）*/
    public static final String TENDER_CHECK_LEVE_HJH = "HJH";
    /**测评标的等级（散标）*/
    public static final String TENDER_CHECK_LEVE_HSB = "HSB";
    /**测评开关开启状态*/
    public static final String EVALUATION_CHECK = "1";
    /**************************************汇计划机构代码****************************/
    
    /** 汇盈平台  */
    public static final String INST_CODE_HYJF = "10000000";
    
    /** 51速贷  */
    public static final String INST_CODE_51SUDAI = "10000001";
    
    /** 融东风  */
    public static final String INST_CODE_RDF = "RDF00001";
    
    /**************************************REDIS用key常量****************************/
    public static final String UNDERLINE = "_";

    public static final String COLON = ":";
    /** 定时发标时间key名 */
    public static final String REDIS_KEY_ONTIME = "ontime";
    /** 定时发标状态修改锁key名 */
    public static final String REDIS_KEY_ONTIME_LOCK = "ontime_lock";
    /** 定时发标状态修改状态key名 */
    public static final String REDIS_KEY_ONTIME_STATUS = "ontime_status";

    /******************************第三方平台编号****************************/
    public static final Integer PLATFORM_ID_HJS = 2000000011;

    /** 汇计划清算期限 */
    public static final Integer HJH_LIQUIDATE_DAYS = 3;

    /** 加入汇计划时，冻结加入资金 */
    public static final String HJH_PROCESS_A = "a";
    /** 计划订单-自动投标冻结 */
    public static final String HJH_PROCESS_B = "b";
    /** 计划订单-自动复投冻结 */
    public static final String HJH_PROCESS_BF = "bf";
    /** 计划订单-自动投标/复投的标的放款" */
    public static final String HJH_PROCESS_C = "c";
    /** 计划订单-自动承接清算出的债权（出借） */
    public static final String HJH_PROCESS_D = "d";
    /** 计划订单-自动承接清算出的债权（复投） */
    public static final String HJH_PROCESS_DF = "df";
    /** 计划订单-自动出借/复投的订单撤销 */
    public static final String HJH_PROCESS_E = "e";
    /** 计划订单进入锁定期 */
    public static final String HJH_PROCESS_A1 = "a1";
    /** 计划订单锁定期-债权回款 */
    public static final String HJH_PROCESS_F = "f";
    /** 汇计划清算-收到承接债权资金（不复投） */
    public static final String HJH_PROCESS_H = "h";
    /** 汇计划清算，收到项目回款（不复投） */
    public static final String HJH_PROCESS_I = "i";
    /** 汇计划清算完成，计划退出 */
    public static final String HJH_PROCESS_L = "l";
    /** 汇计划一个计划订单的连续失败次数容忍次数 */
	public static final int HJH_SERIAL_FAILE_COUNT = 10;

    /** 汇计划一个计划订单的连续承接失败次数容忍次数 */
	public static final int HJH_ASSIGN_SERIAL_FAILE_COUNT = 5;

    /** 汇计划可以出借的最小金额 */
	public static final BigDecimal HJH_TENDER_MIN_ACCOUNT = BigDecimal.valueOf(0.01).setScale(2, BigDecimal.ROUND_DOWN);

    /** 汇计划可以复投的最小金额 */
	public static final BigDecimal HJH_RETENDER_MIN_ACCOUNT = BigDecimal.valueOf(10).setScale(2, BigDecimal.ROUND_DOWN);

    /** 出借页面的阈值 - 目前固定1000元 */
	public static final String TENDER_THRESHOLD = "1000";


    /**  汇盈金服的机构编号 */
    public static final String HYJF_INST_CODE = "10000000";
    
    /******************************存款业务红包流水明细文件下载ftp服务器配置****************************/
/*    public static final String FTP_HOST_NAME = "sftp.credit2go.cn";
    public static final String FTP_HOST_USERNAME = "huiyingjinfu";
    public static final String FTP_HOST_PASSWORD = "GebH3ptTpcO";
    public static final String FTP_HOST_DOWNLOADPATH = "/C2P/";
    public static final Integer FTP_HOST_PORT = 5132;
    *//** 本地存储路径 *//*
    public static final String DOWNLOADPATH = "D:/test";
    *//** 文件下载名称+日期 *//*
    public static final String ALEVEFILENAME = "3005-ALEVE0082-";
    public static final String EVEFILENAME = "3005-EVE0082-";*/
    
    /** 江西银行商户子账户配置表-红包明细 */
    public static final String ACCOUNT_NAME_EVE = "红包账户";
    
    /** 江西银行商户子账户配置表-管理费账户 */
    public static final String ACCOUNT_NAME_ALEVE = "管理费账户";
    
    /** 融通宝 */
    public static final String RTB = "融通宝";

    /**  风车理财渠道编号 */
    public static final String WRB_CHANNEL_CODE = "wrb";
    /**  风车理财出借标记 */
    public static final String TENDER_FROM_TAG = "wrb";
    /** 缴费授权状态  */
    public static final String PAYMENT_AUTH_STATUS = "paymentAuthStatus";



    /** 短信验证码状态,新验证码 */
    public static final Integer CKCODE_NEW = 0;
    /** 短信验证码状态,失效 */
    public static final Integer CKCODE_FAILED = 7;
    /** 短信验证码状态,已验 */
    public static final Integer CKCODE_YIYAN = 8;
    /** 短信验证码状态,已用 */
    public static final Integer CKCODE_USED = 9;


    /**
     * 优惠券出借  出借类别：1：直投类，2：汇添金 3：汇计划
     */
    public static final Integer COUPON_TENDER_TYPE_HJH = 3;
    /**
     * 优惠券出借  出借类别：1：直投类，2：汇添金 3：汇计划
     */
    public static final Integer COUPON_TENDER_TYPE_HZT = 1;

    /**
     * 充值状态
     */
    public static final String RECHARGE_STATUS = "RECHARGE_STATUS";

    /**
     * 托管机构
     */
    public static final String BANK_TYPE = "BANK_TYPE";

    /**
     * 用户类型
     */
    public static final String USER_PROPERTY = "USER_PROPERTY";

    /** 汇添金+汇天利交易笔数 */
    public static final Integer HTJ_HTL_COUNT = 47083;

    /** @RequestMapping值 */
    public static final String REQUEST_HOME = "/hyjf-app";

    /** 首页接口  @RequestMapping值 */
    public static final String REQUEST_MAPPING = "/homepage";

    /** 首页项目列表  @RequestMapping值 */
    public static final String START_PAGE_ACTION = "/getStartPage";
    
    /** 首页列表缓存存活时间 秒 */
    public static final int HOME_CACHE_LIVE_TIME = 12*60*60;
    /** 标的详情缓存刷新时间 秒 */
    public static final int PROJECT_DETAIL_CACHE_TIME = 5;
    /** 标的信息(borrowUser,borrowMan)缓存 秒*/
    public static final int PROJECT_BORROW_USER_CACHE_TIME = 60 * 5;
    /** 散标投资记录 秒 */
    public static final int PROJECT_BORROW_INVEST_CACHE_TIME = 60;

    // 迁移 合规数据上报 共通 常量定义 jijun 20190115 start
    /** 合规数据上报 */
    public static final String HG_DATAREPORT = "合规数据上报";
    /** 国家互联网应急中心 缩写 */
    public static final String HG_DATAREPORT_CERT = "CERT";
    /** 中国互金协会 缩写 */
    public static final String HG_DATAREPORT_NIFA = "NIFA";
    /** 北京互金协会 缩写 */
    public static final String HG_DATAREPORT_BIFA = "BIFA";
    // 迁移 合规数据上报 共通 常量定义 jijun 20190115 end

    /** 散标债转标识 */
    public static Integer BORROW_CREDIT_STATUS = 1;
    /** 智投债转标识 */
    public static Integer HJH_CREDIT_STATUS = 2;
    /**  互联网债权类融资项目信息  */
    public static String NIFA_BORROW_INFO_TYPE = "24EXPORTBUSINESSZHAIQ";
    /**  互联网债权类融资借款人信息  */
    public static String NIFA_BORROWER_INFO_TYPE = "24EXPORTBUSINESSZHAIQ_BOR";
    /**  互联网债权类融资出借人信息  */
    public static String NIFA_LENDER_INFO_TYPE = "24EXPORTBUSINESSZHAIQ_INV";
    /**  互联网金融产品及收益权转让融资项目信息  */
    public static String NIFA_CREDIT_INFO_TYPE = "26EXPORTBUSINESSJINR";
    /**  互联网金融产品及收益权转让融资受让人信息  */
    public static String NIFA_CREDITER_INFO_TYPE = "26EXPORTBUSINESSJINR_INV";

    /** 商品类型 */
    public static String PRODUCT_TYPE = "DUIBA_PRODUCT_TYPE";
    /** 订单状态 */
    public static String ORDER_STATUS = "ORDER_STATUS";
    /** 发货状态 */
    public static String DELIVERY_STATUS = "DELIVERY_STATUS";
    /** 处理状态 */
    public static String PROCESSING_STATE = "PROCESSING_STATE";
    /** 积分类型 */
    public static String INTEGRAL_TYPE = "INTEGRAL_TYPE";
    /** 积分业务名称 */
    public static String INTEGRAL_BUSINESS_NAME = "INTEGRAL_BUSINESS_NAME";
    /** 审核状态 */
    public static String AUDIT_STATUS = "AUDIT_STATUS";
    /** 操作类型 */
    public static String OPERATION_TYPE = "OPERATION_TYPE";


}
