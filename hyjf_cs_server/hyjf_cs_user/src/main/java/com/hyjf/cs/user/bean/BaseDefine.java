package com.hyjf.cs.user.bean;

import com.hyjf.common.util.PropUtils;

public class BaseDefine {
	/** @RequestMapping值 */
	// public static final String REQUEST_HOME = "/hyjf-api-web";
	public static final String HOST = PropUtils.getSystem("hyjf.api.web.url").trim();
	public static final String HOST_ASYN = PropUtils.getSystem("http.hyjf.web.host").trim();

	public static final String CALL_BACK_VIEW = "/callback/callback_post";
	public static final String CALL_BACK_TRANSPASSWORD_VIEW = "/callback/callback_transpassword";
	
    public static final String REQUEST_HOME = "/hyjf-app";
    
    public static final String SURONG_REQUEST_HOME = "/hyjf-api-web";
    
    public static final String AOP_INTERFACE_ACCESSKEY = "aop.interface.accesskey";
    
    public static final String CALL_BACK_FORM = "callBackForm";
    
    public static final String METHOD_APPLY_CHECK = "applyCheck";
    
    public static final String METHOD_VIP_APPLY = "vipApply";
    
    public static final String METHOD_USER_VIP_DETAIL_ACTIVE_INIT = "userVipDetailInit";
    
    public static final String METHOD_VIP_LEVEL_CAPTION_ACTIVE_INIT = "vipLevelCaptionInit";
    
    
    
    public static final String METHOD_GET_INVEST_INFO_MAPPING ="getInvestInfo";
    
    public static final String METHOD_GET_PROJECT_AVAILABLE_USER_COUPON_ACTION="getProjectAvailableUserCoupon";
    
    public static final String METHOD_CHECK_PARAM_ACTION="checkParam";
    
    public static final String METHOD_VALIDATE_COUPON_ACTION="validateCoupon";
    
    public static final String METHOD_COUPON_TENDER_ACTION="couponTender";
    
    //我的优惠券列表
    public static final String METHOD_COUPON_USER_LIST= "getUserCoupons";
    //我的优惠券详情页
    public static final String METHOD_COUPON_USER_DETAIL = "getUserCouponDetail";
    //我的账户vip信息
    public static final String METHOD_MYACCOUNT_VIPINFO = "getUserVIPInfo";
    //我的账户优惠券信息
    public static final String METHOD_MYACCOUNT_COUPONINFO = "getCouponInfo";
    //我的投资列表（投资中）
    public static final String METHOD_MYTENDER_INVEST_LIST = "getInvestList";
    //我的投资列表（换裤子）
    public static final String METHOD_MYTENDER_REPAY_LIST = "getRepayList";
    //我的投资列表（已还款）
    public static final String METHOD_MYTENDER_REPAYED_LIST = "getAlreadyRepayList";
    
    public static final String METHOD_INVITE_USER = "inviteUser";
    // 奖品兑换奖品列表
    public static final String METHOD_PRIZE_CHANGE_LIST = "getPrizeChangeList";
    // 奖品兑换条件校验
    public static final String METHOD_PRIZE_CHANGE_CHECK = "prizeChangeCheck";
    // 奖品兑换
    public static final String METHOD_DO_PRIZE_CHANGE = "doPrizeChange";
    // 抽奖精品列表
    public static final String METHOD_PRIZE_DRAW_LIST = "getPrizeDrawList";
    // 抽奖
    public static final String METHOD_DO_PRIZE_DRAW = "doPrizeDraw";
    
    // 优惠券放款
    public static final String METHOD_COUPON_LOANS = "borrowLoansForCoupon";
    // 直投类优惠券还款
    public static final String METHOD_COUPON_REPAY = "borrowRepayForCoupon";
    // 汇添金优惠券还款
    public static final String METHOD_COUPON_REPAY_HTJ = "couponRepayForHtj";
    // 体验金按收益期限还款
    public static final String METHOD_COUPON_REPAY_TYJ = "borrowRepayForTyjTo";
    // 投资更新V值
    public static final String METHOD_VIP_VALUE = "tenderVipValue";
    
    public static final String METHOD_GET_USER_RECOMMEND_INFO = "getRecommendInfo";
    
    public static final String METHOD_GET_USER_RECOMMEND_STAR_PRIZE_LIST = "/getUserRecommendStarPrizeList";
    
    public static final String METHOD_GET_USER_RECOMMEND_STAR_USED_PRIZE_LIST = "/getUserRecommendStarUsedPrizeList";
    public static final String METHOD_GET_USER_FLAG = "/getUserFlag";
    // 新年活动：获取用户财神卡数量
    public static final String METHOD_GET_USERCARD_COUNT  = "getUserCardCount";
    // 新年活动：抽奖
    public static final String METHOD_NEWYEAR_PRIZEDRAW = "doNewYearPrizeDraw";
    // 新年活动：手机号码检查
    public static final String METHOD_PHONENUM_CHECK = "doPhoneNumCheck";
    // 新年活动： 发送财神卡给好友
    public static final String METHOD_CARD_SEND = "doCardSend";
    // 优惠券投资送财神卡
    public static final String METHOD_NEWYEAR_TENDER = "newyearTender";
    // 优惠券投资送财神卡
    public static final String METHOD_NEWYEAR_SEND_CARD = "newyearSendCard";
    // 优惠券投资送财神卡
    public static final String METHOD_NEWYEAR_REGIST = "newyearRegist";
    
    
    // 新年活动：获取当天谜题信息
    public static final String METHOD_GET_PRESENT_RIDDLES = "/getPresentRiddles";
    // 新年活动：返回今天用户是否答题表示
    public static final String METHOD_GET_TODAY_USER_ANSWER_FLAG = "/getTodayUserAnswerFlag";
    // 新年活动：获取用户累计获得优惠券信息
    public static final String METHOD_GET_USER_PRESENT_CUMULATIVE_COUPON = "/getUserPresentCumulativeCoupon";
    // 新年活动：获取用户灯笼点亮列表
    public static final String METHOD_GET_USER_LANTERN_ILLUMINE_LIST = "/getUserLanternIllumineList";
    // 新年活动：记录用户答题信息
    public static final String METHOD_UPDATE_USER_ANSWER_RECORD = "/updateUserAnswerRecord";
    // 新年活动：记录用户答题信息
    public static final String METHOD_INSERT_USER_ANSWER_RECORD_INIT = "/insertUserAnswerRecordInit";
    // 新年活动：校验用户答题
    public static final String METHOD_CHECK = "/check";
    //第三方还款明细查询
    public static final String METHOD_BORROW_LIST_ACTION = "/server/repayment/repaymentInfoList";
    
    
    /**
     * 活动列表
     */
    public static final String METHOD_GET_ACTLIST = "getActListData";
    // -------------------汇添金相关start------------------------
 	// 获取汇添金计划列表
 	public static final String METHOD_SEARCH_PLAN_LIST = "searchPlanList";
 	// 获取汇添金计划详情
 	public static final String METHOD_SEARCH_PLAN_DETAIL = "searchPlanDetail";
 	// 查询相应的汇添金计划的加入记录
 	public static final String METHOD_SEARCH_PLAN_ACCEDE = "searchPlanAccede";
 	// 查询相应的汇添金债权列表
 	public static final String METHOD_SEARCH_PLAN_BORROW = "searchPlanBorrow";
 	// 跳转投资页面（获取投资信息）
 	public static final String METHOD_GET_PLAN_INVEST_INFO = "getPlanInvestInfo";
 	// 计算预期收益
 	public static final String METHOD_GET_PLAN_INVEST_EARNINGS = "getPlanInvestEarnings";
 	// 投资二次确认
 	public static final String METHOD_APPOINT_CHECK = "appointCheck";
 	// 加入计划
 	public static final String METHOD_JOIN_PLAN = "joinPlan";
 	// 预约授权
 	public static final String METHOD_APPOINTMENT = "appointment";
 	// 预约授权校验
 	public static final String METHOD_CHECK_APPOINTMENT = "appointmentCheck";
 	// 查询用户投资的汇添金
 	public static final String METHOD_SEARCH_USER_PROJECT_LIST = "searchUserProjectList";
 	
 	// 获取汇计划详情
 	public static final String METHOD_HJH_SEARCH_PLAN_DETAIL = "searchHjhPlanDetail";
 	// 查询相应的汇计划的加入记录
 	public static final String METHOD_HJH_SEARCH_PLAN_ACCEDE = "searchHjhPlanAccede";
 	// 查询相应的汇计划债权列表
 	public static final String METHOD_HJH_SEARCH_PLAN_BORROW = "searchHjhPlanBorrow";

 	// -------------------汇添金相关end------------------------
    
    /*****************************************汇添金优惠券验签---START************************************************/
    //获取用户优惠券总张数
    public static final String METHOD_PLAN_COUNT_COUPON_USERS="plan/countCouponUsers";
    //获取用户优惠券总张数
    public static final String METHOD_PLAN_GET_PROJECT_AVAILABLE_USER_COUPON_ACTION="plan/getProjectAvailableUserCoupon";
    //获取用户优惠券总张数
    public static final String METHOD_PLAN_GET_USER_COUPON_AVAILABLE_COUNT="plan/getUserCouponAvailableCount";
    //获取用户最优优惠券信息
    public static final String METHOD_PLAN_GET_BEST_COUPON="plan/getBestCoupon";
    //计算优惠券预期收益
    public static final String METHOD_PLAN_GET_COUPON_INTEREST="plan/getCouponInterest";
    
    //优惠券投资
    public static final String METHOD_PLAN_COUPON_TENDER_ACTION="plan/couponTender";
    //更新汇添金优惠券收益及还款时间
    public static final String METHOD_PLAN_COUPON_RECOVER="plan/updateCouponRecover";
    
    /*****************************************汇添金优惠券验签---END************************************************/
    
    /*****************************************风险测评验签---START************************************************/
    
    /** 返回用户是否测评标识 @RequestMapping值*/
    public static final String METHOD_GET_USEREVALATIONRESULT_BY_USERID = "/getUserEvalationResultByUserId";
    /** 调查问卷初始化 @RequestMapping值*/
    public static final String METHOD_GET_QUESTION_LIST = "/getQuestionList";
    /** 用户调查问卷提交答案 @RequestMapping值*/
    public static final String METHOD_USER_EVALATION_END = "/userEvalationEnd";
    /** 用户调查问卷行为记录 @RequestMapping值*/
    public static final String METHOD_USER_EVALUATION_BEHAVIOR = "/userEvaluationBehavior";
    /** 用户调查问卷行为记录 @RequestMapping值*/
    public static final String METHOD_USER_EVALUATION_BEHAVIOR_STATUS = "/userEvaluationBehaviorStatus";
    
    
    /*****************************************风险测评验签---END************************************************/
    

 	/***********************融东风相关接口-START************************/
    // 标的状态同步
    public static final String METHOD_BORROW_STATUS_SYN = "borrowStatusSyn";
    // 标的还款计划
    public static final String METHOD_BORROW_REPAY_PLAN = "borrowRepayPlan";
    // 标的还款信息确认
    public static final String METHOD_BORROW_REPAY_CONFIRM = "borrowRepayConfirm";
    // 标的还款
    public static final String METHOD_BORROW_REPAY = "borrowRepay";
    
    // 标的还款信息查询
    public static final String METHOD_BORROW_REPAY_SEARCH = "borrowRepaySearch";
    
 	/***********************融东风相关接口-END****************************/
    
    
    /***********************银行存管相关接口-START************************/
    // 同步余额
    public static final String METHOD_SYNBALANCE = "synBalance";
    
    /***********************银行存管相关接口-END****************************/
    
    /***********************十月份活动-START************************/
    // 活动2 根据用户ID加载答题信息
    public static final String GET_USER_QUESTION_ACTION = "/getUserQuestion";
    // 活动2 用户答题
    public static final String ANSWER_CHECK_ACTION = "/answerCheck";
    /***********************十月份活动-END****************************/
    /** 获取活动列表数据*/
    public static final String GET_ACTIVITY_LIST = "/act1";
    /***********************第三方服务相关接口-START************************/
    
    
    
    // 第三方服务接口同步余额
    public static final String METHOD_SERVER_SYNBALANCE = "/server/synBalance/synbalance";
    //还款接口
	public static final String METHOD_REPAY = "userRepay";
	//获取还款信息接口
	public static final String METHOD_REPAY_INFO = "userRepayInfo";
	//获取还款结果接口
	public static final String METHOD_REPAY_RESULT = "userRepayResult";
    
    // 第三方服务接口设置交易密码
    public static final String METHOD_SERVER_SET_PASSWORD = "/server/user/transpassword/setPassword";
    // 第三方服务接口修改交易密码
    public static final String METHOD_SERVER_RESET_PASSWORD = "/server/user/transpassword/resetPassword";
    // 第三方服务接口用户绑卡
    public static final String METHOD_SERVER_BIND_CARD = "/server/user/bankcard/bindCard";
    // 第三方服务接口用户绑卡发送短信验证码
    public static final String METHOD_SERVER_BIND_CARD_SEND_PLUS_CODE = "/server/user/bankcard/sendPlusCode";
    // 第三方服务接口用户绑卡
    public static final String METHOD_SERVER_DELETE_CARD = "/server/user/bankcard/deleteCard";
    // 第三方服务接口发送短信验证码
    public static final String METHOD_SERVER_SEND_SMS = "/server/user/openaccount/sendCode";
    // 第三方服务接口用户注册
    public static final String METHOD_SERVER_REGISTER = "/server/user/register/register";
    // 第三方服务接口用户开户
    public static final String METHOD_SERVER_OPEN_ACCOUNT = "/server/user/openaccount/open";
    // 第三方服务接口用户静默开户
    public static final String METHOD_SERVER_OPEN_ACCOUNT_SILENT = "/server/user/openaccount/openSilent";
    // 第三方服务接口查询资产状态
    public static final String METHOD_SERVER_ASSET_STATUS = "/server/asset/status";
    // 第三方服务接口 短信充值发送短信验证码
    public static final String METHOD_SERVER_SEND_RECHARGE_SMS = "/server/user/recharge/sendSms";
    // 第三方服务接口 短信充值
    public static final String METHOD_SERVER_RECHARGE = "/server/user/recharge/recharge";
    // 第三方服务接口 用户提现
    public static final String METHOD_SERVER_WITHDRAW = "/server/user/withdraw/withdraw";
    // 第三方服务接口 用户注册开户
    public static final String  METHOD_SERVER_REGISTER_OPENACCOUNT = "/serveer/user/registeropenaccount/";
    // 第三方服务接口 用户免密提现
    public static final String METHOD_SERVER_NON_CASH_WITHDRAW = "//server/user/non_cash_withdraw/cash";
    // 第三方服务接口 用户四合一授权
    public static final String METHOD_SERVER_CASHWITHDRAWAL = "/server/userCashauth/cashWithdrawal";
    //第三方用户测评结果记录
    public static final String METHOD_SAVE_USER_EVALUATION_RESULTS = "/server/user/evaluation/saveUserEvaluationResults";
    // 第三方服务接口 缴费授权
    public static final String METHOD_PAYMENT_AUTH_PAGE = "/server/user/paymentAuthPage/page";

    // 第三方服务接口用户提现记录查询接口
    public static final String METHOD_SERVER_GET_USER_WITHDRAW_RECORD = "/server/user/withdraw/getUserWithdrawRecord";
    /***********************第三方服务相关接口-END****************************/
    
    
    /***********************双十一活动-START************************/
    // 双十一活动 我画你猜
    public static final String JAN_GET_USER_QUESTION_ACTION= "/janGetUserQuestion";
    
    public static final String JAN_ANSWER_CHECK_ACTION = "/janAnswerCheck";
    
    public static final String GRAB_COUPONS_ACTION = "/grabCoupons";
    
    public static final String METHOD_DO_BARGAIN = "doBargain";
    
    public static final String METHOD_DO_BARGAIN_DOUBLE = "doBargainDouble";
    
    public static final String METHOD_DO_PRIZE_BUY = "doPrizeBuy";
    
    public static final String METHOD_DO_SMSCODE_SEND = "doSmsCodeSend";
    
    /***********************双十一活动-END****************************/
    /***********************受托支付-START************************/
    // 借款人受托支付申请 jsp
    public static final String CALL_BACK_TRUSTEEPAY_VIEW = "/callback/callback_trusteepay";
    public static final String METHOD_SERVER_TRUSTEE_PAY = "/server/trusteePay/page";
    public static final String METHOD_SERVER_TRUSTEE_PAY_QUERY = "/server/trusteePay/trusteePayQuery";
    /***********************受托支付-END****************************/
    public static final String GET_WEEKLY_DATA = "/weekly";
    /***********************双十二活动-START************************/
    public static final String METHOD_DO_BALLOON_RECEIVE = "balloonReceive";
    public static final String GET_ACT_CORPS = "/corps12";
    /***********************双十二活动-END****************************/
    public static final String GET_ACT_2018SPRING="/act2018";
    //上市活动
    public static final String GET_ACT_LIST = "/act2018list";

    // 自动投资 债转  短信验证码
    public static final String METHOD_BORROW_AUTH_SEND_SMS = "/server/autoPlus/sendcode";
    // 自动投资 增强
    public static final String METHOD_BORROW_AUTH_INVES = "/server/autoPlus/userAuthInves";
    // 自动 债转  授权增强
    public static final String METHOD_BORROW_AUTH_CREDIT = "/server/autoPlus/userCreditAuthInves";
    /** 获取集团组织架构信息 @RequestMapping值 */
	public static final String ORGANIZATION_LIST = "/syncCompanyInfo";
	/** 获取员工信息信息 @RequestMapping值 */
	public static final String EMPINFO_LIST = "/syncEmpInfo";
	/** 获取用户开户信息 @RequestMapping值 */
	public static final String INVEST_REPAY_LIST = "/investRepaysList";
    
    // 还款授权
    public static final String METHOD_REPAY_AUTH = "/server/repayAuth/repayAuth";
    

    //查询用户信息
    public static final String METHOD_SERVER_SYNCUSERINFO = "/server/user/syncUserInfo";

    //授权状态查询
    public static final String METHOD_BORROW_AUTH_STATE = "/server/authState/query";
}
