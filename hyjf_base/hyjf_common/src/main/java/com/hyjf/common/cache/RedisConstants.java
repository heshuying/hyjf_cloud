/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.cache;

/**
 * redis专用常量
 * @author fp
 * @version RedisConstants, v0.1 2018/3/27 15:43
 */
public class RedisConstants {
    /**
     * 记录密码错误次数Redis前缀web端
     */
    public static final String PASSWORD_ERR_COUNT_APP = "password_err_count_app_";

    /**
     * 记录密码错误次数Redis前缀web端
     */
    public static final String PASSWORD_ERR_COUNT_WEB = "password_err_count_web_";

    /**
     * 用户一秒内的登录次数(ip)
     */
    public static final String LOGIN_ONE_COUNT_WEB = "login_one_count_web_";

    /**
     * 用户一秒内的登录次数(ip)
     */
    public static final String LOGIN_ONE_COUNT_APP = "login_one_count_app_";

    /**
     * 恶意攻击登录ip黑名单
     */
    public static final String LOGIN_BLACK_LIST_WEB = "login_black_list_web";

    /**
     * 恶意攻击登录ip黑名单
     */
    public static final String LOGIN_BLACK_LIST_APP = "login_black_list_app";


    /**
     * 风险保证金前缀
     */
    public static final String CAPITAL_TOPLIMIT_ = "CAPITAL_TOPLIMIT_";

    /**
     * 汇计划发标redis key
     */
    public static final String GEN_HJH_BORROW_NID = "gen_hjh_borrow_nid";

    /**
     * 加入计划防重校验
     */
    public static final String HJH_TENDER_REPEAT = "HJH_TENDER_REPEAT";

    /**
     * 汇计划可投余额前缀
     */
    public static final String HJH_PLAN = "HJHBAL_";

    /**
     * 汇计划进入锁定期处理中队列
     */
    public static final String HJH_LOCK_REPEAT = "hjh_lockisrepeat";

    /**
     * 散标投资异步防重校验
     */
    public static final String TENDER_ORDERID = "tender_orderid";

    /**
     * 投资优惠券使用rediskey
     */
    public static final String COUPON_TENDER_KEY = "coupon_tender";




    /** 直投类放款任务名称 */
	public static final String ZHITOU_LOAN_TASK = "zhitouLoan";
	/**计划类实时放款 add by cwyang 2017-10-23*/
	public static final String PLAN_REALTIME_LOAN_TASK = "planRealtimeLoan";

	/**直投类实时放款 add by cwyang 2017-10-23*/
	public static final String ZHITOU_REALTIME_LOAN_TASK = "zhitouRealtimeLoan";
	/** 计划类放款任务名称 */
	public static final String PLAN_LOAN_TASK = "planLoan";
	/** 放款请求任务名称 */
	public static final String LOAN_REQUEST_TASK = "loanRequest";

	/** 直投类还款任务名称 */
	public static final String ZHITOU_REPAY_TASK = "zhitouRepay";
	/** 计划类还款任务名称 */
	public static final String PLAN_REPAY_TASK = "planRepay";
	/** 还款请求任务名称 */
	public static final String REPAY_REQUEST_TASK = "repayRequest";

    // 汇计划队列前缀
    public static final String HJH_PLAN_LIST = "QUEUE_";


    // 汇计划标的队列标识 债转（债转标的）
    public static final String HJH_BORROW_CREDIT = "CREDIT";
    // 汇计划标的队列标识 投资（原始标的）
    public static final String HJH_BORROW_INVEST = "INVEST";

    // 汇计划分割线
    public static final String HJH_SLASH = "_";

    // 汇计划自动债转中的标志 redis key
    public static final String HJH_DEBT_SWAPING = "DebtSwaping";

    // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 start
    // _tmp
    public static final String HJH_SLASH_TMP = "_tmp";
    // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 end
}
