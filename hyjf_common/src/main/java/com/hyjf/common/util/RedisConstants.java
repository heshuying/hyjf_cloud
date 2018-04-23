package com.hyjf.common.util;

/**
 * <p>
 * 常量文件
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class RedisConstants {
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

    // 风险保证金前缀
    public static final String CAPITAL_TOPLIMIT_ = "CAPITAL_TOPLIMIT_";

    // 汇计划队列前缀
    public static final String HJH_PLAN_LIST = "QUEUE_";

    // 汇计划可投余额前缀
    public static final String HJH_PLAN = "HJHBAL_";

    // 汇计划标的队列标识 债转（债转标的）
    public static final String HJH_BORROW_CREDIT = "CREDIT";
    // 汇计划标的队列标识 投资（原始标的）
    public static final String HJH_BORROW_INVEST = "INVEST";
    
    // 汇计划分割线
    public static final String HJH_SLASH = "_";

    // 汇计划发标redis key
    public static final String GEN_HJH_BORROW_NID = "gen_hjh_borrow_nid";
    
    // 汇计划自动债转中的标志 redis key
    public static final String HJH_DEBT_SWAPING = "DebtSwaping";

}
